package com.ssafy.bank.member.application;

import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.member.domain.repository.MemberRepository;
import com.ssafy.bank.member.domain.repository.MemberRepositoryCustom;
import com.ssafy.bank.member.dto.request.JoinRequest;
import com.ssafy.bank.member.dto.request.PasswordUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryCustom memberRepositoryCustom;
    private final MemberRepository memberRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;



    @Transactional
    public void join(JoinRequest request) throws Exception {


        // 이미 가입된 아이디면 예외처리
        if (memberRepositoryCustom.findMemberToCheckDtoById(request.getId()) != null)
            throw new CustomException(ErrorCode.ALREADY_JOINED_ID);

//        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        // 멤버 엔티티 생성 및 저장
        Member member = Member.builder()
                .id(request.getId())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(toLocalDateTime(request.getBirthdate()))
                .password(request.getPassword())
                .build();


        memberRepository.save(member);


        kafkaTemplate.send("insertMember", request);


    }

    @Transactional
    public void updatePassword(PasswordUpdateRequest request) {


        Member member = memberRepositoryCustom.findMemberById(request.getId());


        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);



        // 비밀번호 변경
        member.changePassword(request.getNewPassword());


        kafkaTemplate.send("changePassword", request);
    }




    @Transactional
    public void sendNewPassword(Map<String, String> request) {

        // 회원정보 일치 확인
        Member member = memberRepositoryCustom.findMemberByIdAndPhoneNumber(request.get("id"), request.get("phoneNumber"));



        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);


        // 비밀번호 변경
        member.changePassword(request.get("newPassword"));


        kafkaTemplate.send("changePassword", request);

    }

    @Transactional
    public void updatePhoneNumber(Map<String, String> request) {

        Member member = memberRepositoryCustom.findMemberById(request.get("id"));

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        // 전화번호 변경
        member.changePhoneNumber(request.get("newPhoneNumber"));



        kafkaTemplate.send("changePhoneNumber", request);

    }


    //     String을 LocalDateTime으로 변환
    private LocalDateTime toLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date + " 00:00:00", formatter);
    }

    private String getRandomPassword() {
        String alphabet = "abcdefghijknmlopqrstuvwxyz";
        String number = "0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(number.length());
            sb.append(number.charAt(index));
        }

        return sb.toString();
    }



}