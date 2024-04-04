package com.ssafy.user.common.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class KafkaUtil {

    public LinkedHashMap<String, Object> dataToMap(Object data) {
        ConsumerRecord<String, LinkedHashMap<String, Object>> record = (ConsumerRecord<String, LinkedHashMap<String, Object>>)data;

        return record.value();
    }

}
