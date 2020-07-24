package com.h3c.giovanny.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * @author yys1722
 */
@Slf4j
@Component
public class RedisLock {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 给某坨代码A加锁，返回是否持有这坨代码的锁
     *
     * @param key        令牌
     * @param newExpires 持有锁的到期时间（这里用E来表示）
     * @return 是否持有锁
     */
    public boolean lock(String key, String newExpires) {
        //如果key不存在的话设置k-v,存在的话不设置
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(key, newExpires);
        if (isAbsent != null) {
            //key不存在返回true，先访问A的线程就可以进来并锁上门不让其它线程进入
            if (isAbsent) {
                return true;
            }
            //key存在，获取当前E
            String currentE = redisTemplate.opsForValue().get(key);

            //如果E不为空且过期
            if (!StringUtils.isEmpty(currentE)
                    && Long.parseLong(currentE) < System.currentTimeMillis()) {
                //有可能两个及以上的线程同时到了这一步，但总有一个会捷足先登
                //先到的再获取一次E并设置新的E,返回true持有锁，其它的线程接着执行oldE.equals(currentE)
                //是false最终拒绝进入A
                String oldE = redisTemplate.opsForValue().getAndSet(key, newExpires);
                return !StringUtils.isEmpty(oldE) && oldE.equals(currentE);
            }
            //如果锁没有过期就返回false，不让其它线程进来
            return false;
        }
        throw new RuntimeException("redis lock 错误");
    }

    /**
     * 解锁
     *
     * @param key     key
     * @param expires 锁到期时间
     */
    public void unlock(String key, String expires) {
        try {
            //获取当前锁的到期时间
            String currentE = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentE) && currentE.equals(expires)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }

}
