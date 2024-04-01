package com.ssafy.user.member.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.KafkaUtil;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import com.ssafy.user.member.domain.repository.MemberRepositoryCustom;
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


    @KafkaListener(topics = "insertMember", groupId = "user")
    public void insertMember(Object data) {
        Map<String, Object> userInfo =  kafkaUtil.dataToMap(data);

        // 이미 가입된 아이디면 예외처리
        if (memberRepositoryCustom.findMemberToCheckDtoById((String)userInfo.get("id")) != null)
            throw new NoSuchElementException("해당 그룹의 그룹멤버가 아닙니다.");


        Member member = Member.builder()
                .memberId((Integer)userInfo.get("memberId"))
                .id((String)userInfo.get("id"))
                .name((String)userInfo.get("name"))
                .phoneNumber((String)userInfo.get("phoneNumber"))
                .birthDate(toLocalDateTime((String)userInfo.get("birthdate")))
                .password((String)userInfo.get("password"))
                .build();


        // fcm토큰은 계정계에 저장하지 않음.
        // -> 채널계에서 따로 저장해주기
        member.changeFcmToken((String)userInfo.get("fcmToken"));

        memberRepository.save(member);
    }



    @Transactional
    @KafkaListener(topics = "changePassword", groupId = "user")
    public void changePassword(Object data) {
        Map<String, Object> userInfo = kafkaUtil.dataToMap(data);

        Member member = memberRepository.findByIdAndIsDeletedFalse((String)userInfo.get("id")).orElseThrow(() -> new NoSuchElementException("비밀번호를 변경할 멤버 정보가 없습니다."));

        member.changePassword((String)userInfo.get("newPassword"));
    }




    @Transactional
    @KafkaListener(topics = "changePhoneNumber", groupId="user")
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
