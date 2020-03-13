package com.runx.framework.bean;

import com.runx.framework.aop.AspectMetaData;
import com.runx.framework.aop.JoinPoint;
import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本类为类的定义类
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Data
public class BeanDefinition {

    /**
     * 规则:首字母小写，如：userExample
     */
    private String name;

    /**
     * 简单名称 如：UserExample
     */
    private String simpleName;

    /**
     * 别名: 默认为name
     */
    private String[] alias;

    /**
     * packageName.className
     */
    private String clazzName;

    /**
     * 所属类型 类型
     */
    private Class<?> clazz;

    /**
     * 所实现的接口 该类所实现的接口集合
     */
    private Class<?>[] interfaces;

    /**
     * 需要实例化的目标构造函数
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
    private Map<String, Class<?>> dependencies = new ConcurrentHashMap<>(16);

    /**
     * 构造后初始化函数
     */
    private Method postConstructMethod;

    /**
     * 销毁函数
     */
    private Method destroyMethod;

    /**
     * 判断是否是切面
     */
    private boolean isAspect;

    /**
     * 切面信息
     */
    private AspectMetaData aspectMetaData;


    public void copyFields()  {
        Field[] fields = instance.getClass().getDeclaredFields();
        Field[] proxyFields = proxyInstance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                proxyFields[i].setAccessible(true);
                proxyFields[i].set(proxyInstance,fields[i].get(instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
