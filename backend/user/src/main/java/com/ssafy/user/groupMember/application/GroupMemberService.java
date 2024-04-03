package com.ssafy.user.groupMember.application;

import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.repository.AccountRepository;
import com.ssafy.user.bank.domain.repository.AccountRepositoryImpl;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.EncryptUtil;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepository;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.Invite;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepository;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryImpl;
import com.ssafy.user.groupMember.domain.repository.InviteRepository;
import com.ssafy.user.groupMember.domain.repository.InviteRepositoryCustom;
import com.ssafy.user.groupMember.dto.response.AccountDTO;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import com.ssafy.user.groupMember.dto.response.VerifyInviteCodeResponse;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import com.ssafy.user.groupMember.dto.kafka.InsertGroupMemberVO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final EncryptUtil encryptUtil;
    private final GroupMemberRepositoryImpl groupMemberRepositoryImpl;
    private final GroupMemberRepository groupMemberRepository;
    private final AccountRepository accountRepository;
    private final AccountRepositoryImpl accountRepositoryCustomImpl;
    private final GroupInfoRepository groupInfoRepository;
    private final InviteRepository inviteRepository;
    private final InviteRepositoryCustom inviteRepositoryCustom;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${user.url}")
    private String url;


    // 모임원 보기
    public List<GroupMemberDTO> getAllGroupMembers(int groupId) {
        List<GroupMemberDTO> groupMemberDTOs = groupMemberRepositoryImpl.getAllGroupMemberDTO(
            groupId);

        // 모임원이 한명도 없다면 예외 처리
        if (groupMemberDTOs.size() == 0) {
            throw new CustomException(ErrorCode.NO_GROUP_MEMBER_INFO);
        }

        return groupMemberDTOs;
    }


    // 모임 초대코드 생성
    public String getInviteLink(int groupId) throws Exception {

        // 시크릿 키 발급
        String secretKey = encryptUtil.getRandomKey();

        GroupInfo group = groupInfoRepository.findByGroupInfoIdAndIsDeletedFalse(groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_GROUP_INFO));

        // 초대 정보(그룹, 시크릿키, 만료일) 저장
        Invite invite = Invite.builder()
            .group(group)
            .expireDate(LocalDateTime.now().plusDays(7))
            .identifier(encryptUtil.aesEncrypt(secretKey))
            .build();

        inviteRepository.save(invite);

        // 초대코드로 uri 생성
        String uri = "https://j10a505.p.ssafy.io" + "/groups/join/" + groupId + "-" + encryptUtil.hashEncrypt(
            String.valueOf(groupId), secretKey);

        return uri;
    }


    // 초대 코드 검증 후 인증 토큰, 계좌 반환
    public VerifyInviteCodeResponse verifyInviteCode(String code, String memberId)
        throws Exception {

        String[] splitCode = code.split("-");
        int groupId = Integer.parseInt(splitCode[0]);
        String hashCode = splitCode[1];

        GroupMember alreadyGroupMember = groupMemberRepository.findByMember_IdAndGroupInfo_GroupInfoIdAndIsDeletedFalse(
            memberId, groupId).orElse(null);

        if (alreadyGroupMember != null) {
            throw new CustomException(ErrorCode.ALREADY_JOINED_MEMBER);
        }
        ;

        // 초대 코드 검증
        // groupId-해쉬값
        // DB의 groupId와 identifier로 해쉬한 값

        List<Invite> invites = inviteRepositoryCustom.findByGroupInfoIdAndExpiredDate(groupId,
            LocalDateTime.now());

        Boolean isCodeExist = false;

        // 각 초대 정보에 대해 groupId와 identifier로 해쉬해서 일치하는지 확인
        for (Invite invite : invites) {
            String salt = encryptUtil.aesDecrypt(invite.getIdentifier());
            if (hashCode.equals(encryptUtil.hashEncrypt(splitCode[0], salt))) {
                isCodeExist = true;
                break;
            }
        }

        if (!isCodeExist) {
            throw new CustomException(ErrorCode.NO_INVITE_LINK);
        }

        // 그룹Id + 멤버Id + 시크릿 키를 해쉬해서 authToken 만들기
        String secretKey = encryptUtil.getRandomKey();
        String verificationToken = encryptUtil.hashEncrypt(groupId + memberId, secretKey);

        // 해쉬값 : 시크릿키 레디스에 저장
        redisUtil.setValues(verificationToken, encryptUtil.aesEncrypt(secretKey),
            Duration.ofSeconds(60 * 10));

        // 회원의 계좌 가져오기
        List<AccountDTO> accountDTOs = accountRepositoryCustomImpl.findAccountDTOByMemberId(
            memberId);

        // 인증 토큰과 계좌 리스트 반환
        return VerifyInviteCodeResponse.builder()
            .authToken(verificationToken)
            .accounts(accountDTOs)
            .groupId(groupId)
            .build();
    }


    // 그룹에 가입하기
    @Transactional
    public void joinGroup(String authToken, int groupId, int accountId, String memberId)
        throws Exception {

        System.out.println(accountId);

        // 초대코드 인증토큰 검증
        // 토큰 인증 실패
        if (!redisUtil.existKey(authToken)) {
            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);
        }

        String secretKey = encryptUtil.aesDecrypt(redisUtil.getValues(authToken));

        // 토큰 인증 실패
        if (!authToken.equals(encryptUtil.hashEncrypt(groupId + memberId, secretKey))) {
            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);
        }

        // GroupMember 만들기 위한 엔티티 불러오기
        GroupInfo group = groupInfoRepository.findByGroupInfoIdAndIsDeletedFalse(groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_GROUP_INFO));

        Account account = accountRepositoryCustomImpl.findAccountByIdAndMemberId(accountId,
            memberId);

        if (account == null) {
            throw new CustomException(ErrorCode.NO_SUCH_ACCOUNT);
        }

        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_MEMBER_INFO));

        // 스키마 변경 후 account 포함 필요
        GroupMember groupMember = GroupMember.builder()
            .role(GroupMember.memberType.모임원)
            .member(member)
            .groupInfo(group)
            .name(member.getName())
            .account(account)
            .totalFee(0)
            .build();

        groupMemberRepository.save(groupMember);

        // -------------------------------- 그룹장에게 알람보내기 필요. -----------------------------------
        // --------------------- 카프카로 community의 그룹알람 insert, 그룹멤버 insert 필요 --------

        kafkaTemplate.send("insertGroupMember", new InsertGroupMemberVO(groupMember.getGroupMemberId(), memberId, groupId));
    }


    @Transactional
    public void removeGroupMember(int groupId, int groupMemberId) {
        GroupMember groupMember = groupMemberRepository.findByGroupMemberIdAndGroupInfo_GroupInfoIdAndIsDeletedFalse(
                groupMemberId, groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_GROUP_MEMBER_INFO));

        if (groupMember.getRole().equals(GroupMember.memberType.모임장)) {
            throw new CustomException(ErrorCode.GROUP_LEADER_CANNOT_RESIGN);
        }

        groupMember.softDelete();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("groupMemberId", groupMemberId);
        data.put("groupId", groupId);

        //------------------------ 강퇴된 회원에게 알림 보내기 -------------------------

        kafkaTemplate.send("deleteGroupMember", data);
        // ----------- 카프카로 community의 그룹멤버 데이터 softDelete 해주기 -----------
        // ----------- 카프카로 community의 그룹알림에도 데이터 insert 하기  ------------
    }


    @Transactional
    public void leaveGroup(int groupId, int groupMemberId, String memberId) {

        GroupMember groupMember = groupMemberRepository.findByGroupMemberIdAndGroupInfo_GroupInfoIdAndIsDeletedFalse(
                groupMemberId, groupId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_GROUP_MEMBER_INFO));

        if (!memberId.equals(groupMember.getMember().getId())) {
            throw new CustomException(ErrorCode.NOT_OWN_GROUP_MEMBER);
        }

        if (groupMember.getRole().equals(GroupMember.memberType.모임장)) {
            throw new CustomException(ErrorCode.GROUP_LEADER_CANNOT_RESIGN);
        }

        groupMember.softDelete();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("groupMemberId", groupMemberId);
        data.put("groupId", groupId);

        kafkaTemplate.send("deleteGroupMember", data);

        // ------------- 그룹장에게 해당회원이 탈퇴했다는 알람 보내기 ----------------

        // ----------- 카프카로 community의 그룹멤버 데이터 softDelete 해주기 -----------
        // ----------- 카프카로 community의 그룹알림에도 데이터 insert 하기  ------------
    }
}
