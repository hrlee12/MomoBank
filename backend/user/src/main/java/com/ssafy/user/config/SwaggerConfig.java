package com.ssafy.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.awt.SystemColor.info;

import java.util.List;
//
// @OpenAPIDefinition(
//     info = @Info(title="유저서비스 API 명세서", version = "v1", description = "유저서비스 API v1 문서입니다.")
// )
// @RequiredArgsConstructor
// @Configuration
// public class SwaggerConfig {
//
//     @Bean
//     public OpenAPI customOpenAPI(@Value("${openapi.service.url}") String url) {
//         return new OpenAPI()
//             .servers(List.of(new Server().url(url)))
//             .components(new Components().addSecuritySchemes("Bearer",
//                 new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//             .addSecurityItem(new SecurityRequirement().addList("Bearer"));
//
//     }
//
// }

@OpenAPIDefinition(
    info = @Info(title="유저서비스 API 명세서", version = "v1", description = "유저서비스 API v1 문서입니다.")
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(
        @Value("${openapi.service.url}") String url) {
        return new OpenAPI()
            .servers(List.of(new Server().url(url)));
    }
}