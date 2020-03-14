package com.runx.framework.aop;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Slf4j
public class CglibProxy  {

    private Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    private static final int AOP_INTERCEPTOR_INDEX = 0;


    public Object getInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        MethodInterceptor[] callbacks = {
                new DynamicAopProxyInterceptor()
        };
        enhancer.setCallbacks(callbacks);
        enhancer.setNamingPolicy(new DefaultNamingPolicy());
        enhancer.setCallbackFilter(new DefaultCallbackFilter());
        return enhancer.create();
    }
    private class DefaultCallbackFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            log.info("accept");
            log.info("method name " + method);
            return 0;
        }
    }

    private class AspectProxyInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            log.info("package name is {}",target.getClass().getPackage());
            log.info("method info is " + method.toString());
            return null;
        }
    }

    private class DynamicAopProxyInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // 调用
            return proxy.invoke(target,args);
        }
    }


}
