package com.runx.example.aop;

import com.runx.framework.annotation.Aspect;
import com.runx.framework.annotation.Before;
import com.runx.framework.annotation.Component;
import com.runx.framework.annotation.Pointcut;
import com.runx.framework.aop.JoinPoint;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
@Aspect
@Slf4j
public class LogHandler {


    @Pointcut("public com.runx.example.*(..)")
    public void pointcut(){}

    @Before("pointcut")
    public void before(JoinPoint joinPoint) {
        log.info("hello this is before advice");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
