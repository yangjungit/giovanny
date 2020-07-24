package com.h3c.giovanny.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @className: MongoSettingsProperties
 * @description: 配置文件
 * @author: YangJun
 * @date: 2019/4/11 11:07
 * @version: v1.0
 **/
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoSettingsProperties {
    private String host;
    private int port;
    private String database;
}
