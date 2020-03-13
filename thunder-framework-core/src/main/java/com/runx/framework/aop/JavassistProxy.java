package com.runx.framework.aop;


import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author: kisszpy
 * @date: 2020/3/13
 */
public class JavassistProxy  implements MethodHandler {

    public Object getInstance(Class<?> targetClass) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(targetClass);
        proxyFactory.setHandler(this);
        try {
            return proxyFactory.createClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
        System.out.println("before");
        return method1.invoke(o,objects);
    }
}
