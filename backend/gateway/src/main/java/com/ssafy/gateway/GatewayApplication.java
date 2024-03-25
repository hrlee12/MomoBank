package com.ssafy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "게이트웨이 API v1 문서입니다"))
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator RouteLocator (RouteLocatorBuilder builder) {
		return builder
			.routes()
			.route(r -> r.path( "/community/api-docs" ).and().method(HttpMethod.GET).uri( "lb://community-service" ))
			.route(r -> r.path( "/user/api-docs" ).and().method(HttpMethod.GET).uri( "lb://user-service" ))
			.build();
	}
}
