package com.gondor.backend.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.util.Map;

@Slf4j
@EnableKafka //启用kafka
public class KafkaConfig {

    private final KafkaProperties properties;
    private final RecordMessageConverter messageConverter;

    @Autowired
    public KafkaConfig(KafkaProperties kafkaProperties,
                       ObjectProvider messageConverter) {
        this.properties = kafkaProperties;
        this.messageConverter = (RecordMessageConverter) messageConverter.getIfUnique();
    }

    /**
     * 扩展kafka生产者配置(基于配置文件)
     */
    private void kafkaProducerConfig(Map<String, Object> kafkaProducerConfig) {
        // 批量发送的容量大小，长整型(bytes)
        kafkaProducerConfig.put(ProducerConfig.BATCH_SIZE_CONFIG, 999999999);
        // 缓冲区内存最大大小(用于缓冲等待发送到服务器的记录的内存总字节数)
        kafkaProducerConfig.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 999999999);
        // 最大请求容量大小，长整型(bytes)
        kafkaProducerConfig.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 999999999);
        // 客户端请求超时时间，长整型(毫秒数)
        kafkaProducerConfig.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);

        log.info("kafka生产者配置信息：{}", kafkaProducerConfig);
    }

    /**
     * 扩展kafka消费者配置(基于配置文件)
     */
    private void kafkaConsumerConfig(Map<String, Object> kafkaConsumerConfig) {
        kafkaConsumerConfig.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 999999999);
        kafkaConsumerConfig.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 360000);
        kafkaConsumerConfig.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 300000);
        kafkaConsumerConfig.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 300000);
        //kafkaConsumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        log.info("kafka消费者配置信息：{}", kafkaConsumerConfig);
    }

    /**
     * 扩展kafka监听器配置(基于配置文件)
     */
    private void kafkaListenerConfig(ContainerProperties kafkaListenerConfig) {
        kafkaListenerConfig.setPollTimeout(10000);
        kafkaListenerConfig.setShutdownTimeout(100000);
        //kafkaListenerConfig.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        log.info("kafka监听器配置信息：{}", kafkaListenerConfig);
    }

    //region kafka beans
    @Bean("kafkaConsumerFactory")
    public ConsumerFactory<Object, Object> kafkaConsumerFactory() {
        Map<String, Object> kafkaConsumerConfig = properties.buildConsumerProperties();
        this.kafkaConsumerConfig(kafkaConsumerConfig);
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig);
    }

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
                                                                                                 ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        this.kafkaListenerConfig(factory.getContainerProperties());
        configurer.configure(factory, kafkaConsumerFactory);
        return factory;
    }

    @Bean("kafkaProducerFactory")
    public ProducerFactory<Object, Object> kafkaProducerFactory() {
        Map<String, Object> kafkaProducerConfig = properties.buildProducerProperties();
        this.kafkaProducerConfig(kafkaProducerConfig);
        DefaultKafkaProducerFactory<Object, Object> factory = new DefaultKafkaProducerFactory<>(kafkaProducerConfig);
        String transactionIdPrefix = properties.getProducer().getTransactionIdPrefix();
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        return factory;
    }

    @Bean("kafkaTemplate")
    public KafkaTemplate<Object, Object> kafkaTemplate(ProducerFactory<Object, Object> kafkaProducerFactory,
                                                       ProducerListener<Object, Object> kafkaProducerListener) {
        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        if (this.messageConverter != null) {
            kafkaTemplate.setMessageConverter(this.messageConverter);
        }

        kafkaTemplate.setProducerListener(kafkaProducerListener);
        kafkaTemplate.setDefaultTopic(this.properties.getTemplate().getDefaultTopic());
        return kafkaTemplate;
    }
    //endregion

}
