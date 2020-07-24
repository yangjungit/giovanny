package com.h3c.giovanny.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @className: TestServiceTest
 * @description: //TODO
 * @author: YangJun
 * @date: 2019/12/9 21:47
 * @version: v1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestServiceTest {
    @Autowired
    TestService testService;

    @Test
    public void test1() {
        StudentVO studentList = testService.getStudentList("123456", "1", 1, 10);
        System.out.println(studentList);
    }

}