package com.runx.framework.aop;

import com.runx.framework.handler.AspectHandler;
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

    private static final int ASPECT_INTERCEPTOR_INDEX = 1;

    public Object getInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        MethodInterceptor[] callbacks = {
                new DynamicAopProxyInterceptor(),
                new AspectProxyInterceptor()
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
            boolean matchResult = new AspectHandler().methodMatchers(target.getClass(),method);
            if (matchResult) {
                log.info("entry aspect handler .... ");
                return AOP_INTERCEPTOR_INDEX;
            }else {
                return ASPECT_INTERCEPTOR_INDEX;
            }
        }
    }

    private class AspectProxyInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            JoinPoint joinPoint = new JoinPoint();
            joinPoint.setMethod(method);
            joinPoint.setMethodProxy(proxy);
            joinPoint.setObjects(args);
            joinPoint.setTargetSource(target);
            return joinPoint.proceed();
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
