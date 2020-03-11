package com.runx.framework;

import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.BeanDefinition;
import com.runx.framework.core.AbstractRegisterBeanProcessor;
import com.runx.framework.core.Container;
import com.runx.framework.core.Scan;
import com.runx.framework.resource.PropertiesResourceResolve;
import com.runx.framework.resource.YamlResourceResolve;
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

    protected ClassLoader classLoader;

    public AbstractApplicationContext() {
        log.info("initial framework bean .... ");
        classLoader = Thread.currentThread().getContextClassLoader();
        // 实现框架内部的Bean初始化
        List<BeanDefinition> beanDefinitions = define(PropertiesResourceResolve.class,
                YamlResourceResolve.class);
        beanDefinitions.forEach(beanDefinition->{
            handleDependency(beanDefinition);
            init(beanDefinition);
            register(beanDefinition);
        });
    }

    /**
     * 获取类的扫描器
     * @return
     */
    protected abstract Scan getScanner();

    public List<String> loadResource(String basePackage){
        List<String> resources = getScanner().scan(basePackage,classLoader);
        return resources;
    }

    public  <T> T getBean(Class<?> clazz) {
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
