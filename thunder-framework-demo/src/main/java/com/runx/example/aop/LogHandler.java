package com.runx.example.aop;

import com.runx.framework.annotation.Aspect;
import com.runx.framework.annotation.Before;
import com.runx.framework.annotation.Component;
import com.runx.framework.annotation.Pointcut;
import com.runx.framework.aop.JoinPoint;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
@Aspect
public class LogHandler {


    @Pointcut("execute(public * com.runx.example.*(..))")
    public void pointcut(){}

    @Before("pointcut")
    public void before(JoinPoint joinPoint) {
        System.out.println("hello, this is before aop ");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
