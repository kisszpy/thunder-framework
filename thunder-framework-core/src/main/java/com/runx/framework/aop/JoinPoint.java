package com.runx.framework.aop;

import lombok.Data;
import lombok.experimental.Accessors;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: kisszpy
 * @date: 2020/3/12
 */
@Data
@Accessors(chain = true)
public class JoinPoint {

    private Object o;

    private Method method;

    private Object[] objects;

    private MethodProxy methodProxy;

}
