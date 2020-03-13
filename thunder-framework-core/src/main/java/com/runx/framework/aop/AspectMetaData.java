package com.runx.framework.aop;

import com.runx.framework.annotation.After;
import com.runx.framework.annotation.AfterReturning;
import com.runx.framework.annotation.Around;
import com.runx.framework.annotation.Before;
import com.runx.framework.annotation.Pointcut;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author: kisszpy
 * @date: 2020/3/12
 */
@Data
public class AspectMetaData {

    private Pointcut pointcut;

    private Method before;

    private Method after;

    private Method around;

    private Method afterReturning;

}
