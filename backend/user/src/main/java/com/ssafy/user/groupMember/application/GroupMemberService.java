package com.ssafy.user.groupMember.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.EncryptUtil;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepositoryImpl;
import com.ssafy.user.groupMember.domain.Invite;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryCustom;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryImpl;
import com.ssafy.user.groupMember.domain.repository.InviteRepository;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final EncryptUtil encryptUtil;
    private final GroupMemberRepositoryImpl groupMemberRepositoryImpl;
    private final GroupInfoRepositoryImpl groupInfoRepositoryImpl;
    private final InviteRepository inviteRepository;



    public List<GroupMemberDTO> getAllGroupMembers(int groupId){
        List<GroupMemberDTO> groupMemberDTOs = groupMemberRepositoryImpl.getAllGroupMemberDTO(groupId);

        if (groupMemberDTOs.size() == 0) {
            throw new CustomException(ErrorCode.NO_GROUP_MEMBER_INFO);
        }

        return groupMemberDTOs;
    }






    public String getInviteLink(int groupId) throws Exception {

        String secretKey = encryptUtil.getRandomKey();

        GroupInfo group = groupInfoRepositoryImpl.getGroupInfoById(groupId);
        System.out.println(group.getGroupName());

        Invite invite = Invite.builder()
                .group(group)
                .expire_date(LocalDateTime.now().plusDays(7))
                .identifier(encryptUtil.aesEncrypt(secretKey))
                .build();


        inviteRepository.save(invite);

        String url = "http://localhost:8082/api/user/groups/invite/" + encryptUtil.hashEncrypt(String.valueOf(groupId), secretKey);
        return url;
    }
}
