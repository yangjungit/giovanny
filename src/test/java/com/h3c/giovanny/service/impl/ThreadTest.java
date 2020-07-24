package com.h3c.giovanny.service.impl;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @className: ThreadTest
 * @description: 多线程测试
 * @author: YangJun
 * @date: 2019/4/26 9:45
 * @version: v1.0
 **/
public class ThreadTest {
    @Test
    public void helloWorld() {
        System.out.println("hello world");
        final int a;
        a = 1;
        System.out.println(a);
    }

    @Test
    public void workThreadTest() {
        Helper helper = new Helper();
        helper.submit("my work....");
        helper.init();
    }

    class Helper {
        private final BlockingQueue<String> workQueue = new ArrayBlockingQueue<>(100);
        // 用于处理对立workQueue中的任务的工作线程
        private final Thread workerThread = new Thread(() -> {
            String task;
            while (true) {
                try {
                    System.out.println("before take:" + workQueue.size());
                    task = workQueue.take();
                    System.out.println("after take:" + workQueue.size());
                } catch (InterruptedException e) {
                    System.out.println("发生异常了：" + e);
                    break;
                }
                System.out.println(doProcess(task));
            }
        });

        private String doProcess(String task) {
            return task + "-->processed.";
        }

        public void init() {
            workerThread.start();
        }

        public void submit(String task) {
            try {
                System.out.println("before put:" + workQueue.size());
                workQueue.put(task);
                System.out.println("after put:" + workQueue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                System.out.println("发生异常了，自己处理了。");
            }
        }
    }


}
