package com.ssafy.user.member.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.EncryptUtil;
import com.ssafy.user.common.util.JwtUtil;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import com.ssafy.user.member.dto.request.LoginRequest;
import com.ssafy.user.member.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;


    public Map<String, Object> login(LoginRequest request) throws Exception {
        Member member = memberRepository.findByIdAndIsDeletedFalse(request.getId())
                            .orElseThrow(()-> new CustomException(ErrorCode.NO_MEMBER_INFO));

        System.out.println(member.toString());
        System.out.println(request.getPassword());
//        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
        if (!BCrypt.checkpw(request.getPassword(), member.getPassword())){
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("jwtTokens", makeJwtTokens(request.getId()));
        data.put("member", new LoginResponse(member.getMemberId(), member.getId(), member.getName()));

        return data;
    }

    public Map<String, String> regenerateToken(String refreshToken) throws Exception {

        String memberId = jwtUtil.getMemberId(refreshToken);

        if(!redisUtil.existKey(memberId)){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        };

        String storedToken = redisUtil.getValues(memberId);

        if (!refreshToken.equals(storedToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        return makeJwtTokens(memberId);
    }



    private Map<String, String> makeJwtTokens(String memberId){


        String accessToken = jwtUtil.createAccessToken(memberId);
        String refreshToken = jwtUtil.createRefreshToken(memberId);


        Map<String, String> tokens = new HashMap<>();

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;

    };


}
