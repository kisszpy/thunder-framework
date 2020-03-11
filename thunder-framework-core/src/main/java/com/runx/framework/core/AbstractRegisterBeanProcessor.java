package com.runx.framework.core;

import com.runx.framework.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public class AbstractRegisterBeanProcessor implements RegisterBean {
    /**
     * 存放类的定义，以及类的初始化
     */
    protected static final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    @Override
    public void register(BeanDefinition beanDefinition) {
        beanDefinitionMap.putIfAbsent(beanDefinition.getName(),beanDefinition);
    }

}
