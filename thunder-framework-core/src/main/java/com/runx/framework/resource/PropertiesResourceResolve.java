package com.runx.framework.resource;

/**
 * 资源文件处理类
 * @author: kisszpy
 * @date: 2020/3/11
 */
public class PropertiesResourceResolve implements ResourceResolve {

    @Override
    public void resolve() {
        System.out.println("处理application.properties的资源");
    }
}
