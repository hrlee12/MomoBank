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
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableDiscoveryClient
@SpringBootApplication
@EnableWebFlux
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


//	@Bean
//	public SecurityFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
//		http
//				.authorizeExchange(exchanges -> exchanges.path)
//				csrf((csrf) -> csrf.disable());
//		http.cors(Customizer.withDefaults());
//
//		return http;
//	}
}
