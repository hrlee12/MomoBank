package com.ssafy.user.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 유저 정보는 있으나 자원에 접근할 수 있는 권한이 없는 경우 : 403 응답
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("No Authority", accessDeniedException);

        ErrorResponse body = ErrorResponse.builder()
                .status(ErrorCode.NO_AUTHORITY.getStatus().value())
                .message(ErrorCode.NO_AUTHORITY.getMessage())
                .build();

        String bodyString = objectMapper.writeValueAsString(body);

        response.setStatus(403);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(bodyString);
    }
}
