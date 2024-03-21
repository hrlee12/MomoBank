package com.ssafy.user.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil{


    // RestTemplate apiRequest
    public ResponseEntity send(String uri, HttpMethod method, Object body) {

        HttpHeaders headers = new HttpHeaders();


        HttpEntity entity = new HttpEntity(body, headers);





        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity response = restTemplate.exchange(
                uri,
                method, // 요청 메서드 변경 가능
                entity,
                Object.class);

        return response;
    }
}
