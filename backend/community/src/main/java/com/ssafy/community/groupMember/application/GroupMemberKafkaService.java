package com.ssafy.community.groupMember.application;

import com.ssafy.community.common.util.KafkaUtil;
import com.ssafy.community.feed.domain.GroupInfo;
import com.ssafy.community.feed.domain.GroupMember;
import com.ssafy.community.feed.domain.Member;
import com.ssafy.community.feed.domain.repository.GroupMemberRepository;
import com.ssafy.community.groupMember.domain.repository.GroupInfoRepository;
import com.ssafy.community.groupMember.domain.repository.MemberRepository;
import com.ssafy.community.groupMember.dto.InsertGroupMemberVO;
import com.ssafy.user.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.sql.Insert;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupMemberKafkaService {

    private final GroupMemberRepository groupMemberRepository;
    private final MemberRepository memberRepository;
    private final GroupInfoRepository groupInfoRepository;
    private final KafkaUtil kafkaUtil;

    @KafkaListener(topics = "insertGroupMember", groupId = "community")
    public void insertGroupMember(Object data) {

        LinkedHashMap<String, Object> groupMemberInfo = kafkaUtil.dataToMap(data);


        Member member = memberRepository.findByIdAndIsDeletedFalse((String)groupMemberInfo.get("memberId")).orElseThrow(() -> new NoSuchElementException("해당 회원이 없습니다"));



        GroupInfo group = groupInfoRepository.findByGroupInfoIdAndIsDeletedFalse((Integer)groupMemberInfo.get("groupId")).orElseThrow(() -> new NoSuchElementException("해당 그룹이 없습니다"));



        GroupMember groupMember = GroupMember.builder()
                .groupMemberId((Integer)groupMemberInfo.get("groupMemberId"))
                .groupInfo(group)
                .member(member)
                .role(GroupMember.memberType.모임원)
                .name(member.getName())
                .totalFee(0)
                .build();


        groupMemberRepository.save(groupMember);

    }



    @Transactional
    @KafkaListener(topics = "deleteGroupMember", groupId = "community")
    public void deleteGroupMember(Object data) {
        LinkedHashMap<String, Object> groupMemberInfo = kafkaUtil.dataToMap(data);

        GroupMember groupMember = groupMemberRepository.findByGroupMemberIdAndIsDeletedFalse((Integer) groupMemberInfo.get("groupMemberId")).orElseThrow(() -> new NoSuchElementException("해당 그룹멤버가 없습니다."));

        if (groupMember.getGroupInfo().getGroupInfoId() != (Integer)groupMemberInfo.get("groupId")){
            throw new NoSuchElementException("해당 그룹의 그룹멤버가 아닙니다.");
        }


        groupMember.softDelete();
    }
}
