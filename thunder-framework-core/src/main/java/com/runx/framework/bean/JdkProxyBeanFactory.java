package com.runx.framework.bean;

import com.runx.framework.aop.JdkProxy;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class JdkProxyBeanFactory {

    private Object target;

    public JdkProxyBeanFactory (Object target) {
        this.target = target;
    }

    public Object getBean() {
        return new JdkProxy(target).getInstance();
    }

}
