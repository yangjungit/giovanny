package com.h3c.giovanny.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @className: MongoDBConfig
 * @description: MongoDB配置
 * @author: YangJun
 * @date: 2019/4/11 11:04
 * @version: v1.0
 **/
@Configuration
public class MongoDBConfig {
    @Autowired
    private MongoSettingsProperties properties;

    //覆盖默认的MongoDbFacotry
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory(properties));
    }

    @Bean
    @Autowired
    public MongoDbFactory mongoDbFactory(MongoSettingsProperties properties) {
        // MongoDB地址
        ServerAddress serverAddress = new ServerAddress(properties.getHost(), properties.getPort());
        MongoClient mongoClient = new MongoClient(serverAddress);
        // 连接
        return new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
    }

}
