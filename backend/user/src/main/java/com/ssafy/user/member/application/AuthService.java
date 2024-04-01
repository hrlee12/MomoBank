package com.ssafy.user.member.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.JwtUtil;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import com.ssafy.user.member.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;



    public Map<String, String> login(LoginRequest request) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(request.getId())
                            .orElseThrow(()-> new CustomException(ErrorCode.NO_MEMBER_INFO));

        System.out.println(member.toString());
        System.out.println(request.getPassword());
//        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
        if (!BCrypt.checkpw(request.getPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);
        }

        String accessToken = jwtUtil.createAccessToken(member.getId());
        String refreshToken = jwtUtil.createRefreshToken(member.getId());

        Map<String, String> tokens = new HashMap<>();

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
//    return null;
    }
}
