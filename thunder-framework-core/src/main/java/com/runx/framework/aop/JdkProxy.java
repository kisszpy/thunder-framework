package com.runx.framework.aop;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Slf4j
public class JdkProxy implements InvocationHandler {

    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    public void before(){
        log.info("before invoker");
    }

    public void after() {
        log.info("after invoker");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }
}
