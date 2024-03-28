package com.ssafy.user.common.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaTestController {

//    private final KafkaTemplate<String, String> kafkaTemplate;
//    @PostMapping
//    public void kafkaSendMessage(@RequestBody Map<String, String> request) {
//        System.out.println("send message : " + request.get("message"));
//        kafkaTemplate.send("syk531", request.get("message"));
//    }
}
