package com.ssafy.community.member.application;

import com.ssafy.community.common.util.KafkaUtil;
import com.ssafy.community.feed.domain.Member;
import com.ssafy.community.groupMember.domain.repository.MemberRepository;
import com.ssafy.community.member.domain.repository.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberKafkaService {

    private final KafkaUtil kafkaUtil;
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final MemberRepository memberRepository;

    @KafkaListener(topics = "insertMember", groupId = "community")
    public void insertMember(Object data) {
        Map<String, Object> memberInfo =  kafkaUtil.dataToMap(data);

        // 이미 가입된 아이디면 예외처리
        if (memberRepositoryCustom.findMemberToCheckDtoById((String)memberInfo.get("id")) != null)
            throw new NoSuchElementException("이미 가입된 아이디입니다.");


        Member member = Member.builder()
                .memberId((Integer)memberInfo.get("memberId"))
                .id((String)memberInfo.get("id"))
                .name((String)memberInfo.get("name"))
                .phoneNumber((String)memberInfo.get("phoneNumber"))
                .birthDate(toLocalDateTime((String)memberInfo.get("birthdate")))
                .password((String)memberInfo.get("password"))
                .build();

        // fcm토큰은 계정계에 저장하지 않음.
        // -> 채널계에서 따로 저장해주기
        member.changeFcmToken((String)memberInfo.get("fcmToken"));

        memberRepository.save(member);
    }



    @Transactional
    @KafkaListener(topics = "changePassword", groupId = "community")
    public void changePassword(Object data) {
        Map<String, Object> userInfo = kafkaUtil.dataToMap(data);

        Member member = memberRepository.findByIdAndIsDeletedFalse((String)userInfo.get("id")).orElseThrow(() -> new NoSuchElementException("비밀번호를 변경할 멤버 정보가 없습니다."));

        member.changePassword((String)userInfo.get("newPassword"));
    }






    @Transactional
    @KafkaListener(topics = "changePhoneNumber", groupId="community")
    public void changePhoneNumber(Object data) {
        Map<String, Object> userInfo = kafkaUtil.dataToMap(data);

        Member member = memberRepositoryCustom.findMemberById((String)userInfo.get("id"));

        if (member == null) {
            throw new NoSuchElementException("해당 회원정보가 없습니다.");
        }

        member.changePhoneNumber((String)userInfo.get("newPhoneNumber"));
    }





    //     String을 LocalDateTime으로 변환
    private LocalDateTime toLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date + " 00:00:00", formatter);
    }
}
