package com.ssafy.user.groupMember.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryCustom;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepositoryImpl;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberService {

    private GroupMemberRepositoryImpl groupMemberRepositoryImpl;
    public List<GroupMemberDTO> getAllGroupMembers(int groupId){
        List<GroupMemberDTO> groupMemberDTOs = groupMemberRepositoryImpl.getAllGroupMemberDTO(groupId);

        if (groupMemberDTOs.size() == 0) {
            throw new CustomException(ErrorCode.NO_GROUP_MEMBER_INFO);
        }

        return groupMemberDTOs;
    }
}
