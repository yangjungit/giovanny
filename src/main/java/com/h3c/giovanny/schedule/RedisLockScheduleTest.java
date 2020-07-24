package com.h3c.giovanny.schedule;

import com.h3c.giovanny.annotation.RedisLockAnnotation;
import com.h3c.giovanny.constant.Constant;
import com.h3c.giovanny.domain.mysql.PeClass;
import com.h3c.giovanny.kafka.producer.TestKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;


/**
 * @className: RedisLockScheduleTest
 * @description: Redis实现分布式锁，定时任务测试
 * @author: YangJun
 * @date: 2019/5/20 10:26
 * @version: v1.0
 **/
@Slf4j
//@Component
public class RedisLockScheduleTest {
    @Autowired
    @Qualifier("executor")
    private Executor executor;

    @Autowired
    private TestKafkaProducer testKafkaProducer;

    @RedisLockAnnotation(holdTimeInMillis = 5000,lockName = "testPE")
    @Scheduled(cron = "0 05 * * * ?")
    public void testPE() {
        // 以一个班级为最小单位。假设有一百个班级同时在上体育课
        log.info("以一个班级为最小单位。假设每次扫描在上体育课的班级有一百个。");
        log.info("每一个班级发一个kafka消息。");
        int beInClass = 100;
        ArrayList<PeClass> peClasses = new ArrayList<>();
        for (int i = 0; i < beInClass; i++) {
            PeClass peClass = new PeClass();
            peClass.setId(i + "");
            peClasses.add(peClass);
        }
        peClasses.forEach(clazz -> {
            log.info("发送kafka消息，classId:{}", clazz.getId());
            testKafkaProducer.kafkaP1MessageProducer(Constant.kafkaPeTopic, clazz.getId(),clazz.getId());
        });

    }


    //    @RedisLockAnnotation
//    @Scheduled(cron = "0 */5 * * * ?")
    public void test1() {
        for (int i = 0; i < 30; i++) {
            final Integer a = i == 10 ? 10 : null;
            while (true) {
                try {
                    executor.execute(() -> {
                        log.info("做点事情，执行定时任务。。。");
                        try {

                            Thread.sleep(100L);

                        } catch (InterruptedException e) {
                            log.error("e:{}", e);
                        }
                        if (a != null && a == 10) {
                            log.info("return");
                            return;
                        }
                        log.info("事情做完了。。。");
                    });
                    break;
                } catch (RejectedExecutionException e) {

                }
            }

        }

    }

}
