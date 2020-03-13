package com.runx.framework.annotation;

import java.lang.annotation.*;

/**
 * @author: kisszpy
 * @date: 2020/3/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pointcut {

    String value() default "";

}
