package com.h3c.giovanny.controller;

import com.h3c.giovanny.service.StudentStepService;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @className: PublishMsgTest
 * @description:
 * @author: YangJun
 * @date: 2019/4/29 14:52
 * @version: v1.0
 **/
@RestController
public class PublishMsgTest {
    @Autowired
    private StudentStepService studentStepService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String testMongo(Param param) {
//        int count = studentStepService.findCount();
        System.out.println("dateTimeStr:" + param + "......count:" );
        return "success";
    }

    @Data
    public static class Param {
        private String dateTimeStr;

    }
}
