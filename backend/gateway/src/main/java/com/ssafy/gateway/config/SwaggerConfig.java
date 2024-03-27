package com.ssafy.gateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition(
	info = @Info(title="게이트웨이 API 명세서", version = "v1", description = "게이트웨이 API v1 문서입니다.")
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