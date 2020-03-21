package com.runx.framework.handler;

import com.runx.framework.annotation.Aspect;
import com.runx.framework.annotation.Pointcut;
import com.runx.framework.bean.BeanDefinition;
import com.runx.framework.core.AbstractRegisterBeanProcessor;
import com.runx.framework.expression.RegularExpression;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author: kisszpy
 * @date: 2020/3/12
 */
@Slf4j
public class AspectHandler extends AbstractRegisterBeanProcessor {

    public boolean methodMatchers(Class<?> clazz,Method method) {
        BeanDefinition beanDefinition = beanDefinitionMap.values().stream()
                .filter(BeanDefinition::isAspect)
                .filter(x->RegularExpression.matchExecute(x.getAspectMetaData().getPointcut(),method.getName()))
                .findFirst().orElse(null);
        if (beanDefinition == null) {
            return false;
        }
        return true;
    }

}
