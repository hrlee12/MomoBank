package com.ssafy.bank.member.application;

import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.member.domain.repository.MemberRepository;
import com.ssafy.bank.member.domain.repository.MemberRepositoryCustom;
import com.ssafy.bank.member.dto.request.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryCustom memberRepositoryCustom;
    private final MemberRepository memberRepository;

    public void join(JoinRequest request) throws Exception {


        // 이미 가입된 아이디면 예외처리
        if (memberRepositoryCustom.findMemberToCheckDtoById(request.getId()) != null)
            throw new CustomException(ErrorCode.ALREADY_JOINED_ID);

        // 멤버 엔티티 생성 및 저장
        Member member = Member.builder()
                .id(request.getId())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(toLocalDateTime(request.getBirthdate()))
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
//                .password(request.getPassword())
                .build();


        memberRepository.save(member);
    }

    @Transactional
    public void updatePassword(String id, String currentPassword, String newPassword) {


        Member member = memberRepositoryCustom.findMemberById(id);


        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        // 비밀번호 일치 여부 확인
        if (!BCrypt.checkpw(currentPassword, member.getPassword())){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        // 비밀번호 변경
        member.changePassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
    }




    @Transactional
    public void sendNewPassword(String id, String phoneNumber, String newPassword) {

        // 회원정보 일치 확인
        Member member = memberRepositoryCustom.findMemberByIdAndPhoneNumber(id, phoneNumber);



        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);


        // 비밀번호 변경
        member.changePassword(newPassword);


    }

    @Transactional
    public void updatePhoneNumber(String id, String newPhoneNumber) {

        Member member = memberRepositoryCustom.findMemberById(id);

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        // 전화번호 변경
        member.changePhoneNumber(newPhoneNumber);

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