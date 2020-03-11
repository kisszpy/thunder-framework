package com.runx.framework.core;

import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.BeanDefinition;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public interface RegisterBean {

    /**
     * 完成对Bean的定义
     * @param beanDefinition
     */
    void register(BeanDefinition beanDefinition);

}
