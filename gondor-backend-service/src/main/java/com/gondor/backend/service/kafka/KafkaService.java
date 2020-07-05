package com.gondor.backend.service.kafka;

import com.gondor.master.utils.ComUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KafkaService {

    @Autowired
    public KafkaService(KafkaTemplate<Object, Object> kafkaTemplate,
                        ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
    }

    private KafkaTemplate<Object, Object> kafkaTemplate;
    private ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory;

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param value 数据
     */
    public void produce(String topic, Object value) {
        String message = ComUtils.str(value);
        ListenableFuture<SendResult<Object, Object>> sendResultFuture = kafkaTemplate.send(topic, message);
        sendResultFuture.addCallback(KafkaProducerResult::onSuccess, (Throwable throwable) -> KafkaProducerResult.onFailure(message, throwable));
    }

    /**
     * 消费历史消息
     *
     * @param topics    主题
     * @param partition 分区
     * @param offset    偏移量
     * @param consumer  处理kafka队列中的message
     */
    public void consumerHistory(String topics, Integer partition, Integer offset, java.util.function.Consumer<ConsumerRecord<String, Object>> consumer) {
        ContainerProperties containerProperties = kafkaListenerContainerFactory.getContainerProperties();
        ConsumerFactory<Object, Object> consumerFactory = kafkaListenerContainerFactory.getConsumerFactory();

        // 自定义配置
        Map<String, Object> configurationProperties = new HashMap<>(consumerFactory.getConfigurationProperties());
        configurationProperties.remove(ConsumerConfig.MAX_POLL_RECORDS_CONFIG);//移除 单次拉取的最大记录数 配置限制，从指定offset向下消费到最后

        TopicPartition topicPartition = new TopicPartition(topics, partition);
        Consumer<String, Object> kafkaConsumer = new KafkaConsumer<>(configurationProperties, new StringDeserializer(), null);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        kafkaConsumer.seek(topicPartition, offset);
        ConsumerRecords<String, Object> recordCollections = kafkaConsumer.poll(Duration.ofMillis(containerProperties.getPollTimeout()));

        int count = recordCollections.count();
        if (count <= 0) {
            return;
        }

        for (ConsumerRecord<String, Object> record : recordCollections) {
            consumer.accept(record);
        }
    }

    private static class KafkaProducerResult {
        private static void onSuccess(SendResult<Object, Object> sendResult) {
            log.info("kafka生产者 - 消息发送 成功: {}", sendResult.toString());
        }

        private static void onFailure(String value, Throwable throwable) {
            log.error("kafka生产者 - 消息发送 失败: {}\r\n{}", value, ComUtils.printException(throwable));
        }
    }
}
