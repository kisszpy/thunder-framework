package com.runx.framework.bean;

import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 该类已经过时了，参考 BeanDefinition
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Deprecated
@Data
public class BeanDefine {

    /**
     * 规则第一个字母小写默认
     */
    private String name;

    /**
     * 简单名称
     */
    private String simpleName;

    /**
     * 别名
     */
    private String[] alias;

    /**
     * full name
     */
    private String clazzName;

    /**
     * 所属类型
     */
    private Class<?> clazz;

    /**
     * 所实现的接口
     */
    private Class<?>[] interfaces;

    /**
     * 所有构造函数
     */
    private Constructor<?> constructor;

    /**
     * 是否完成实例化
     */
    private boolean isInitial;

    /**
     * 实例
     */
    private Object instance;

    /**
     * 代理对象
     */
    private Object proxyInstance;

    /**
     * 按照类型来获取，后续再扩展
     */
    private Map<String,Class<?>> dependencies = new ConcurrentHashMap<>(16);

    /**
     * 构造后初始化函数
     */
    private Method postConstructMethod;

    /**
     * 销毁函数
     */
    private Method destroyMethod;





}
