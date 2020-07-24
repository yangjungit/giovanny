package com.h3c.giovanny.config;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqConfiguration implements EnvironmentAware {
    @Autowired
    private Environment environment;

    private String queueName;

    private String username;

    private String password;

    private String host;

    private String virtualHost;

    private DirectExchange exReply;

    private DirectExchange exDirect;

    private FanoutExchange fanoutExchange;

    private RabbitAdmin rabbitAdmin;

    @Bean
    public ConnectionFactory myConnectionFactory() {
        log.info("-----------------连接rabbit MQ");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        log.info("-----------------host" + host + "**********************");
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(StringUtils.isEmpty(virtualHost) ? "/" : virtualHost);
        log.info("-----------------" + username + "---------" + password + "-------------");
        return connectionFactory;
    }

    @Bean
    public Queue helloQueue1() {
        Queue queue = new Queue(queueName, true);
        queue.setAdminsThatShouldDeclare(amqpAdmin());
        return queue;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(myConnectionFactory());
        //exchange需要手动初始化（要设置参数），然后手动提供bingding信息
        this.rabbitAdmin = rabbitAdmin;
        return rabbitAdmin;
    }


    @Bean
    public Exchange exReply() {
        if (exReply == null) {
            exReply = new DirectExchange(RabbitDirectExchangeNameEnum.EXCHANGE_EXREPLY.getExchangeName(), false, true);
            exReply.setAdminsThatShouldDeclare(amqpAdmin());
        }
        return exReply;
    }

    @Bean
    public Exchange exDirect() {
        if (exDirect == null) {
            exDirect = new DirectExchange(RabbitDirectExchangeNameEnum.EXCHANGE_EXDIRECT.getExchangeName(), false, true);
            exDirect.setAdminsThatShouldDeclare(amqpAdmin());
        }
        return exDirect;
    }


    @Bean
    public Binding exReplyBindToQueue() {
        Binding binding = BindingBuilder.bind(new Queue(queueName)).to(exReply).with(queueName);
        binding.setAdminsThatShouldDeclare(amqpAdmin());
        return binding;
    }

    @Bean
    public Binding exDirectTwoBindToQueue() {
        Binding binding = BindingBuilder.bind(new Queue(queueName)).to(exDirect).with(queueName);
        binding.setAdminsThatShouldDeclare(amqpAdmin());
        return binding;
    }

    @Bean
    public Binding exDirectBindToQueue() {
        Binding binding = BindingBuilder.bind(new Queue(queueName)).to(exReply).with(queueName);
        binding.setAdminsThatShouldDeclare(amqpAdmin());
        return binding;
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(myConnectionFactory());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public RabbitAdmin getRabbitAdmin() {
        return rabbitAdmin;
    }

    public void setRabbitAdmin(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
