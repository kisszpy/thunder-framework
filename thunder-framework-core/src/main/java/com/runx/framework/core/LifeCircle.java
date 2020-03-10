package com.runx.framework.core;

import com.runx.framework.bean.BeanDefinition;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public interface LifeCircle {

    /**
     * 处理依赖
     * @param beanDefinition
     */
    void handleDependency(BeanDefinition beanDefinition);

    /**
     * 构造函数初始化
     * @param beanDefinition
     */
    void init(BeanDefinition beanDefinition);

    /**
     * @PostConstruct
     */
    void initPostConstruct();

    /**
     * 后置
     */
    void afterProperties();

    /**
     * 销毁的方法
     */
    void destroyMethod();



}
