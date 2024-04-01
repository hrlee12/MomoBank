package com.ssafy.bank.config;

import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @OpenAPIDefinition(
//         info = @Info(title="뱅킹서비스 API 명세서", version = "v1", description = "뱅킹서비스 API v1 문서입니다.")
// )
// @RequiredArgsConstructor
// @Configuration
// public class SwaggerConfig {
//     @Bean
//     public GroupedOpenApi chatOpenApi() {
//         String[] paths = {"/api/v1/**"};
//
//         return GroupedOpenApi.builder()
//                 .group("뱅킹서비스 API v1")
//                 .pathsToMatch(paths)
//                 .build();
//     }
// }



@OpenAPIDefinition(
    info = @Info(title="뱅킹서비스 API 명세서", version = "v1", description = "뱅킹서비스 API v1 문서입니다.")
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