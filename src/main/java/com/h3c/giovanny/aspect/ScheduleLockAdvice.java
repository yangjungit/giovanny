package com.h3c.giovanny.aspect;

import com.h3c.giovanny.annotation.RedisLockAnnotation;
import com.h3c.giovanny.service.ScheduleLockService;
import com.h3c.giovanny.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author xieluoyin
 */
@Aspect
@Component
@Slf4j
public class ScheduleLockAdvice {
    private final
    ScheduleLockService scheduleLockService;

    @Autowired
    public ScheduleLockAdvice(ScheduleLockService scheduleLockService) {
        this.scheduleLockService = scheduleLockService;
    }


    @Pointcut("execution(public * com.h3c.giovanny.schedule..*(..))")
    public void scheduleLock() {

    }


    /**
     * 环绕通知需要携带ProceedingJoinPoint类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
     * 而且环绕通知必须有返回值，返回值即为目标方法的返回值
     */
    @Around("scheduleLock()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        // pjp.getSignature() 获取连接点的方法签名对象
        Method targetMethod = ((MethodSignature) (pjp.getSignature())).getMethod();
        RedisLockAnnotation scheduleLock = targetMethod.getAnnotation(RedisLockAnnotation.class);
        if (scheduleLock == null) {
            // 通过反射 执行 目标对象连接点处的方法
            Object proceed = pjp.proceed();
            log.info("pjp.proceed():{}", proceed);
            return proceed;
        }
        int random = DateUtil.getRandom(1000);
        log.info("random:{}", random);
        Thread.sleep(random);
        String methodName = targetMethod.getName();
        String classSimpleName = pjp.getTarget().getClass().getSimpleName();
        // 获取锁 执行目标方法
        log.info("methodName:{},classSimpleName:{}", methodName, classSimpleName);
        String scheduleName = scheduleLock.lockName();
        if ("".equals(scheduleName)) {
            scheduleName = classSimpleName + File.separator + methodName;
        }
        boolean lock = scheduleLockService.scheduleLock(scheduleName, scheduleLock.holdTimeInMillis());
        log.info("lock:{}", lock);
        if (lock) {
            return pjp.proceed();
        } else {
            log.debug("{}被锁住了此次不执行", classSimpleName + File.separator + methodName);
            return null;

        }


    }


}
