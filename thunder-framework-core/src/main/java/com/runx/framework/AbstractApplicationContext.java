package com.runx.framework;

import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.BeanDefinition;
import com.runx.framework.core.AbstractRegisterBeanProcessor;
import com.runx.framework.core.Container;
import com.runx.framework.core.Scan;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
@Slf4j
public abstract class AbstractApplicationContext extends AbstractRegisterBeanProcessor implements Container {


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

    public  <T> T getBean(Class<?> clazz) {
        System.out.println(beanDefinitionMap);
        T t = (T) beanDefinitionMap.values().stream()
                .filter(item->item.getClazz().equals(clazz) || Arrays.asList(item.getInterfaces()).contains(clazz) )
                .findFirst().get().getInstance();
        return t;
    }

    public <T> T getBean(String name){
        BeanDefinition beanDefine = beanDefinitionMap.get(name);
        if (beanDefine == null) {
            return (T) beanDefinitionMap.values().stream()
                    .filter((v)-> v.getAlias().equals(name))
                    .findFirst()
                    .get()
                    .getInstance();
        }else {
            return (T) beanDefine.getInstance();
        }
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
