package com.h3c.giovanny.kafka.consumer;

import com.h3c.giovanny.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @className: TestKafka
 * @description: Kafka本地测试
 * @author: YangJun
 * @date: 2019/8/2 14:15
 * @version: v1.0
 **/
@Component
@Slf4j
public class TestKafkaConsumer {


    @KafkaListener(topics = Constant.kafkaPeTopic, containerFactory = "kafkaManualAckFactory")
    public void testListener(ConsumerRecord record, Acknowledgment acknowledgment) throws InterruptedException {
        log.info("收到消息,处理消息。record:{}", record);
        Thread.sleep(500);
        acknowledgment.acknowledge();
    }
}
