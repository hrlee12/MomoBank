package com.ssafy.community.kafkaTest.listener;
import com.ssafy.community.report.presentation.ReportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaConsumerListener {

    @Autowired
    ReportController reportController;
    @KafkaListener(topics = "testTopic", groupId = "myGroup")
    public void listen(String message) throws IOException {
        System.out.println("Received Message in group 'myGroup': " + message);
        reportController.makeReport("2020.03");

    }
}