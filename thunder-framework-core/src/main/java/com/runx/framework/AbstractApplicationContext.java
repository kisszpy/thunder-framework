package com.runx.framework;

import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.BeanDefinition;
import com.runx.framework.core.Container;
import com.runx.framework.core.Scan;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
@Slf4j
public abstract class AbstractApplicationContext implements Container {


    private CountDownLatch countDownLatch = new CountDownLatch(1);


    /**
     * 设置类的扫描器
     * @param scanner
     */
    protected abstract void setScanner(Scan scanner);

    /**
     * 获取类的扫描器
     * @return
     */
    protected abstract Scan getScanner();

    public List<String> loadResource(String basePackage){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<String> resources = getScanner().scan(basePackage,classLoader);
        return resources;
    }


    @Override
    public void start() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            countDownLatch.countDown();
            log.info("application context has been shutdown ");
        }));
    }
}
