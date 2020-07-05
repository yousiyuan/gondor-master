package com.gondor.backend.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class KafkaListenerHandler {

    @Async
    public void gondorMessageHandler(ConsumerRecord<?, ?> record) throws InterruptedException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (!kafkaMessage.isPresent()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("topic", record.topic());
        map.put("partition", record.partition());
        map.put("offset", record.offset());

        // 模拟数据处理
        Thread.sleep(3000);
        System.out.println(map);
    }

}
