package com.runx.framework;

import com.runx.framework.bean.BeanDefinition;
import com.runx.framework.core.AnnotationRegisterBeanProcessor;
import com.runx.framework.core.ClassPathResourceScanner;
import com.runx.framework.core.Scan;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    private Scan scan;

    private AnnotationRegisterBeanProcessor annotationRegisterBeanProcessor;

    private ClassLoader classLoader;

    public AnnotationApplicationContext(String basePackage) {
        // 初始化扫描器
        this.scan = new ClassPathResourceScanner();
        // 初始化 BeanProcessor
        annotationRegisterBeanProcessor = new AnnotationRegisterBeanProcessor();
        // 初始化类加载器
        classLoader = Thread.currentThread().getContextClassLoader();
        // 加载资源
        loadResource(basePackage).stream().forEach(className -> {
            // bean定义
            BeanDefinition beanDefinition = annotationRegisterBeanProcessor.define(className,classLoader);
            if (beanDefinition != null) {
                // 处理依赖
                annotationRegisterBeanProcessor.handleDependency(beanDefinition);
                // bean的初始化
                annotationRegisterBeanProcessor.init(beanDefinition);
                // 完成Bean的注册
                annotationRegisterBeanProcessor.register(beanDefinition);
            }
        });
        // 补偿
        annotationRegisterBeanProcessor.compensate();
    }

    @Override
    protected void setScanner(Scan scanner) {
        this.scan = scan;
    }

    @Override
    protected Scan getScanner() {
        return scan;
    }
}
