package com.ssafy.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.awt.SystemColor.info;

@OpenAPIDefinition(
        info = @Info(title="유저서비스 API 명세서", version = "v1", description = "유저서비스 API v1 문서입니다.")
)
@RequiredArgsConstructor
@Configuration
@SecuritySchemes({
        @SecurityScheme(
                name = "Bearer Authentication", // Access 토큰에 대한 보안 스키마 이름
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization", // 보통 'Authorization' 헤더에 토큰이 포함됩니다.
                description = "JWT Access Token"
        ),
        @SecurityScheme(
                name = "refreshToken", // Refresh 토큰에 대한 보안 스키마 이름
                type = SecuritySchemeType.APIKEY, // Refresh 토큰은 일반적으로 API 키로 취급될 수 있습니다.
                in = SecuritySchemeIn.HEADER,
                paramName = "X-Refresh-Token", // 'X-Refresh-Token' 또는 다른 사용자 정의 헤더를 사용할 수 있습니다.
                description = "JWT Refresh Token"
        )
})
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("유저서비스 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
