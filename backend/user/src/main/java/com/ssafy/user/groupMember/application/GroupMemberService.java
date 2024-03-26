package com.ssafy.user.groupMember.application;

import com.ssafy.user.bank.entity.Account;
import com.ssafy.user.bank.entity.repository.AccountRepository;
import com.ssafy.user.bank.entity.repository.AccountRepositoryCustom;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.EncryptUtil;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepository;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepositoryImpl;
import com.ssafy.user.groupMember.domain.Invite;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryCustom;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryImpl;
import com.ssafy.user.groupMember.domain.repository.InviteRepository;
import com.ssafy.user.groupMember.domain.repository.InviteRepositoryCustom;
import com.ssafy.user.groupMember.dto.response.AccountDTO;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import com.ssafy.user.groupMember.dto.response.InviteLinkResponse;
import com.ssafy.user.groupMember.dto.response.VerifyInviteCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final EncryptUtil encryptUtil;
    private final GroupMemberRepositoryImpl groupMemberRepositoryImpl;
    private final AccountRepository accountRepository;
    private final AccountRepositoryCustom accountRepositoryCustom;
    private final GroupInfoRepository groupInfoRepository;
    private final InviteRepository inviteRepository;
    private final InviteRepositoryCustom inviteRepositoryCustom;
    private final RedisUtil redisUtil;
    @Value("${user.url}")
    private String url;


    public List<GroupMemberDTO> getAllGroupMembers(int groupId){
        List<GroupMemberDTO> groupMemberDTOs = groupMemberRepositoryImpl.getAllGroupMemberDTO(groupId);

        if (groupMemberDTOs.size() == 0) {
            throw new CustomException(ErrorCode.NO_GROUP_MEMBER_INFO);
        }

        return groupMemberDTOs;
    }






    public String getInviteLink(int groupId) throws Exception {

        String secretKey = encryptUtil.getRandomKey();


        GroupInfo group = groupInfoRepository.findById(groupId).orElseThrow(() -> new CustomException(ErrorCode.NO_GROUP_INFO));


        Invite invite = Invite.builder()
                .group(group)
                .expireDate(LocalDateTime.now().plusDays(7))
                .identifier(encryptUtil.aesEncrypt(secretKey))
                .build();


        inviteRepository.save(invite);

        String uri = "http://localhost:8082/api/user/groups/invite/" + groupId + "-" + encryptUtil.hashEncrypt(String.valueOf(groupId), secretKey);
        return uri;
    }


    // 초대 코드 검증 후 인증 토큰, 계좌 반환
    public VerifyInviteCodeResponse verifyInviteCode(String code, int memberId) throws Exception {
        
        // 초대 코드 검증
        // groupId-해쉬값
        // DB의 groupId와 identifier로 해쉬한 값
        String[] splitCode = code.split("-");
        int groupId = Integer.parseInt(splitCode[0]);
        String hashCode = splitCode[1];
        
        List<Invite> invites = inviteRepositoryCustom.findByGroupIdAndExpiredDate(groupId, LocalDateTime.now());


        Boolean isCodeExist = false;
        
        // 각 초대 정보에 대해 groupId와 identifier로 해쉬해서 일치하는지 확인
        for (Invite invite : invites) {
            String salt = encryptUtil.aesDecrypt(invite.getIdentifier());
            if (hashCode.equals(encryptUtil.hashEncrypt(splitCode[0], salt))) {
                isCodeExist = true;
                break;
            }
        }

        if (!isCodeExist)
            throw new CustomException(ErrorCode.NO_INVITE_LINK);

        
        // 그룹Id + 시크릿 키를 해쉬해서 authToken 만들기
        String secretKey = encryptUtil.getRandomKey();
        String verificationToken = encryptUtil.hashEncrypt(String.valueOf(groupId), secretKey);

        // 해쉬값 : 시크릿키 레디스에 저장
        redisUtil.setValues(verificationToken, encryptUtil.aesEncrypt(secretKey), Duration.ofSeconds(60 * 30));

        // 회원의 계좌 가져오기
        List<AccountDTO> accountDTOs = accountRepositoryCustom.findAccountDTOByMemberId(memberId);


        // 인증 토큰과 계좌 리스트 반환
        return VerifyInviteCodeResponse.builder()
                .authToken(groupId + "-" + verificationToken)
                .accounts(accountDTOs)
                .build();
    }



}
