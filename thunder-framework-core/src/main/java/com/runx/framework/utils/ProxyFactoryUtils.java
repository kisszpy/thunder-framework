package com.runx.framework.utils;

import com.runx.framework.aop.JavassistProxy;
import com.runx.framework.bean.CglibProxyBeanFactory;

/**
 * @author: kisszpy
 * @date: 2020/3/13
 */
public class ProxyFactoryUtils {

    public static Object getProxyInstance(Object target) {
        return new CglibProxyBeanFactory(target).getBean();
    }
}
