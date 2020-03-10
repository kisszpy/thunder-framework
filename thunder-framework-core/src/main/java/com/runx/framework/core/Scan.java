package com.runx.framework.core;

import java.util.List;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public interface Scan {

    /**
     * 类路径扫描
     */
    List<String> scan(String basePackage, ClassLoader classLoader);

}
