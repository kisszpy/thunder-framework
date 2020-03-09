package com.runx.framework.bean;

import com.runx.framework.aop.CglibProxy;
import com.runx.framework.aop.JdkProxy;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class CglibProxyBeanFactory {

    private Object target;

    public CglibProxyBeanFactory(Object target) {
        this.target = target;
    }

    public Object getBean() {
        return new CglibProxy(target).getInstance();
    }

}
