package com.runx.framework.core;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public interface Container {

    /**
     * 容器启动,为RPC服务提供
     */
    void start();

    /**
     * 容器关闭
     */
    void shutdown();

}
