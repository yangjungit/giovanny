package com.h3c.giovanny.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @className: TestKafkaProducer
 * @description: 本地Kafka测试
 * @author: YangJun
 * @date: 2019/8/2 14:28
 * @version: v1.0
 **/
@Component
@Slf4j
public class TestKafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void kafkaP1MessageProducer(String topic, String partition, String msg) {
        ListenableFuture listenableFuture = kafkaTemplate.send(topic, partition, msg);
        log.info("listenableFuture:{}", listenableFuture);
    }
}
