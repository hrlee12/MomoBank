package com.ssafy.user.common.util;

import com.ssafy.user.common.exception.HtmlToErrorResponseConverter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class RestTemplateUtil {

//    private final RestTemplate restTemplate;


    // RestTemplate apiRequest
    public ResponseEntity send(String uri, HttpMethod method, Object body) {

        RestTemplate restTemplate = new RestTemplate();

        // 기존의 MessageConverters 목록을 가져옵니다.
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>(
            restTemplate.getMessageConverters());

        // 커스텀 Converter를 추가합니다.
        messageConverters.add(new HtmlToErrorResponseConverter());

        // 수정된 MessageConverters 목록을 RestTemplate에 설정합니다.
        restTemplate.setMessageConverters(messageConverters);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity entity = null;

        if (body == null) {
            entity = new HttpEntity(headers);
        }

        entity = new HttpEntity(body, headers);

        ResponseEntity response = restTemplate.exchange(
            uri,
            method, // 요청 메서드 변경 가능
            entity,
            Object.class);

        return response;
    }
}

