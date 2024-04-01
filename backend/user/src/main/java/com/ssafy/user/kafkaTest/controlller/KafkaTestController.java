package com.ssafy.user.kafkaTest.controlller;
import com.ssafy.user.groupInfo.dto.response.GetFeesPerMonthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class KafkaTestController {

//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    @GetMapping("/send")
//    public String sendMessage() {
//        kafkaTemplate.send("testTopic", new GetFeesPerMonthResponse(23,34));
//        return "Message sent to the Kafka Topic testTopic Successfully";
//    }
//    @GetMapping("/text")
//    public String sendText() {
//        return "this is text!!!!";
//    }

}
