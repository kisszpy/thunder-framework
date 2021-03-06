package com.runx.framework.utils;

import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class ReflectUtils {

    /**
     * 获取构造函数集合
     *
     * @param clazz
     * @return
     */
    public static Constructor<?>[] getConstructors(Class<?> clazz) {
        return clazz.getConstructors();
    }

    public static Stream<Constructor> getConstructorsOfStream(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors());
    }

    public static Method getAnnotationBeforeMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x->x.isAnnotationPresent(Before.class))
                .findFirst()
                .orElseGet(null);
    }


    public static Method getMethod(String name, Class<?> clazz) {
        try {
            return clazz.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object instance(Constructor<?> constructor) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
//        HelloExample instance = HelloExample.class.newInstance();
//        Arrays.stream(HelloExample.class.getDeclaredFields())
//                .forEach(field -> {
//                    if (field.isAnnotationPresent(Autowired.class)) {
//                        try {
//                            field.setAccessible(true);
//                            field.set(instance,"1000");
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        System.out.println(instance);
//
//    }
}
