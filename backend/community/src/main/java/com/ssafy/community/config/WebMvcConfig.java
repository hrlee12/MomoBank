package com.ssafy.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8083", "http://j10a505.p.ssafy.io") // 모든 출처 허용
                .allowedMethods("*") // 모든 HTTP 메소드 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키를 포함시키기 위해 필요
                .maxAge(3600); // 사전 요청(pre-flight request) 캐싱 시간
    }
}
