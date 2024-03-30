package com.ssafy.user.common.security;


import com.google.api.Authentication;
import com.ssafy.user.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PrincipalDetailsService principalDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);

            if (token != null && jwtUtil.validateAccessToken(token)) {

                String memberId = jwtUtil.getMemberId(token);
//
                // 회원정보 중 토큰의 아이디와 일치하는 정보 있을 시, userDetails 생성.
                // session로그인하면 자동으로 되는 건데 jwt라 직접 설정해줘야 함.
                UserDetails userDetails = principalDetailsService.loadUserByUsername(memberId);


                if (userDetails != null) {
                    //  접근권한 인증 Token 생성.
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // 현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

        }
            filterChain.doFilter(request, response);
    }
}
