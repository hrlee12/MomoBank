package com.ssafy.user.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class RestTemplateUtil{

    private final RestTemplate restTemplate;


    // RestTemplate apiRequest
    public ResponseEntity send(String uri, HttpMethod method, Object body) {

        HttpHeaders headers = new HttpHeaders();


        HttpEntity entity = null;

        if (body == null)
            entity = new HttpEntity(headers);

        entity = new HttpEntity(body, headers);


        ResponseEntity response = restTemplate.exchange(
            uri,
            method, // 요청 메서드 변경 가능
            entity,
            Object.class);

        return response;
    }
}

