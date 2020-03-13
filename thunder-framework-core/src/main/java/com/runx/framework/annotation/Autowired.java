package com.runx.framework.annotation;

import java.lang.annotation.*;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Target({ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
