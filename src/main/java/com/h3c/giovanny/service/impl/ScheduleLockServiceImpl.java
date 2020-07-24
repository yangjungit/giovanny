package com.h3c.giovanny.service.impl;

import com.h3c.giovanny.lock.RedisLock;
import com.h3c.giovanny.service.ScheduleLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xys1717
 */
@Service
@Slf4j
public class ScheduleLockServiceImpl implements ScheduleLockService {
    private final
    RedisLock redisLock;
    private final
    String PROJECT_NAME = ScheduleLockServiceImpl.class.getName().split("\\.")[2];

    @Autowired
    public ScheduleLockServiceImpl(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    @Override
    public boolean scheduleLock(String scheduleName, long holdTimeInMillis) {
        log.info("projectName:{}", this.getClass().getName());
        return redisLock.lock(PROJECT_NAME + ":" + scheduleName, System.currentTimeMillis() + holdTimeInMillis + "");
    }

}
