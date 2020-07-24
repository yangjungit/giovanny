package com.h3c.giovanny;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.h3c.giovanny.dao")
@EnableScheduling
public class GiovannyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiovannyApplication.class, args);
    }

}
