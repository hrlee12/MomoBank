//package com.ssafy.user.common.security;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.user.common.ErrorCode;
//import com.ssafy.user.common.exception.ErrorResponse;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//// 유저 정보 없이 접근한 경우 : 401 응답
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        log.error("No Authenticated Request", authException);
//
//        ErrorResponse body = ErrorResponse.builder()
//                .status(ErrorCode.NO_AUTHENTICATION.getStatus().value())
//                .message(ErrorCode.NO_AUTHENTICATION.getMessage())
//                .build();
//
//        String bodyString = objectMapper.writeValueAsString(body);
//
//        response.setStatus(401);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(bodyString);
//    }
//}
