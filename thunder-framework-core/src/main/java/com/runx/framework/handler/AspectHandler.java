package com.runx.framework.handler;

import com.runx.framework.annotation.Aspect;
import com.runx.framework.annotation.Pointcut;
import com.runx.framework.expression.RegularExpression;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author: kisszpy
 * @date: 2020/3/12
 */
@Slf4j
public class AspectHandler {

    public boolean methodMatchers(Pointcut pointcut, Class<?> clazz) {
        // 目标类包名
        String packageName = clazz.getPackage().getName();
        log.info("package name is {}", packageName);
        log.info(pointcut.value());
        String matchResult = RegularExpression.matchExecute(pointcut.value());
        log.info("match result is {}", matchResult);
        if (matchResult != null) {
            log.info("匹配成功了");
            return true;
        }
        return false;
    }

}
