package com.runx.framework.utils;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class ObjectUtils {

    public static boolean isEmpty(Object ... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Object[] el = new Object[] {};
        System.out.println(isEmpty(el));

    }

}
