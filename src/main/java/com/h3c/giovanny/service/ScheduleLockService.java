package com.h3c.giovanny.service;

/**
 * @author xys1717
 */
public interface ScheduleLockService {
    long DEFAULT_HOLD_TIME_IN_MILLIS = 5000L;

    /**
     * 定时任务加锁，返回是否持有锁
     *
     * @param scheduleName     定时任务名
     * @param holdTimeInMillis 持有毫秒值
     *                         cron形式的只要比执行周期小就行
     *                         fixedRate形式的最好比任务周期小一点点接近任务周期（
     *                         如果不这样的话集群时实际任务执行的周期可能不是你设置的周期了）
     * @return 是否持有锁
     */
    boolean scheduleLock(String scheduleName, long holdTimeInMillis);
}
