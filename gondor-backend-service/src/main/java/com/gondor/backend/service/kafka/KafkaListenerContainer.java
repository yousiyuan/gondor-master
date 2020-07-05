package com.gondor.backend.service.kafka;

import com.gondor.master.utils.ComUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class KafkaListenerContainer {

    private KafkaListenerHandler kafkaListenerHandler;

    private final KfkOffsetCommitResult offsetCommitCallback = new KfkOffsetCommitResult();

    @Autowired
    public KafkaListenerContainer(KafkaListenerHandler kafkaListenerHandler) {
        this.kafkaListenerHandler = kafkaListenerHandler;
    }

    @KafkaListener(topicPartitions = {@TopicPartition(topic = KafkaConstant.TOPIC_GONDOR, partitions = "0")}, concurrency = "1", containerFactory = "kafkaListenerContainerFactory")
    public void gondorPartition0(ConsumerRecord<?, ?> record, KafkaConsumer<Object, Object> kafkaConsumer) {
        try {
            kafkaListenerHandler.gondorMessageHandler(record);
        } catch (Throwable throwable) {
            log.error(ComUtils.printException(throwable));
        }
        kafkaConsumer.commitAsync(offsetCommitCallback);
    }

    @KafkaListener(topicPartitions = {@TopicPartition(topic = KafkaConstant.TOPIC_GONDOR, partitions = "1")}, concurrency = "1", containerFactory = "kafkaListenerContainerFactory")
    public void gondorPartition1(ConsumerRecord<?, ?> record, KafkaConsumer<Object, Object> kafkaConsumer) {
        try {
            kafkaListenerHandler.gondorMessageHandler(record);
        } catch (Throwable throwable) {
            log.error(ComUtils.printException(throwable));
        }
        kafkaConsumer.commitAsync(offsetCommitCallback);
    }

    @KafkaListener(topicPartitions = {@TopicPartition(topic = KafkaConstant.TOPIC_GONDOR, partitions = "2")}, concurrency = "1", containerFactory = "kafkaListenerContainerFactory")
    public void gondorPartition2(ConsumerRecord<?, ?> record, KafkaConsumer<Object, Object> kafkaConsumer) {
        try {
            kafkaListenerHandler.gondorMessageHandler(record);
        } catch (Throwable throwable) {
            log.error(ComUtils.printException(throwable));
        }
        kafkaConsumer.commitAsync(offsetCommitCallback);
    }

    class KfkOffsetCommitResult implements OffsetCommitCallback {
        @Override
        public void onComplete(Map<org.apache.kafka.common.TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
            if (exception != null) {
                log.error("kafka消费者 - 偏移量提交 失败：{}", offsets, exception);
            } else {
                log.info("kafka消费者 - 偏移量提交 成功：{}", offsets);
            }
        }
    }
}
