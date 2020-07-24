package com.h3c.giovanny.amqlistener;

import com.h3c.giovanny.config.RabbitDirectExchangeNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @className: AmqListener
 * @description: 接收rabbit mq的消息
 * @author: YangJun
 * @date: 2019/5/6 11:30
 * @version: v1.0
 **/
@Slf4j
@Component
public class AmqListener {
    private final String queueName="giovanny";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = queueName)
    public void receiveMqMeassge(Message message) {
        log.info("message:{}", message);
        byte[] bytes = message.getBody();
        String body = new String(bytes);
        log.info("body:{}",body);
        MessageProperties sendMessageProperties = new MessageProperties();
        sendMessageProperties.setExpiration("60000");
        sendMessageProperties.setMessageId(message.getMessageProperties().getMessageId());
        Message sendMag = new Message(bytes, sendMessageProperties);
        rabbitTemplate.send(RabbitDirectExchangeNameEnum.EXCHANGE_EXREPLY.getExchangeName(),message.getMessageProperties().getReplyTo(),sendMag);

    }
}
