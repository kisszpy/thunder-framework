package com.runx.framework;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
@Slf4j
public class RunxApplication implements ApplicationRunner {

    public static void run(Class<?> mainClass,String[] args) {
        log.info("开始启动");
    }

    @Override
    public void startup() {
        log.info("开始启动");
    }
}
