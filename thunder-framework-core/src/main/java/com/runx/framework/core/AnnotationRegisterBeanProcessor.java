package com.runx.framework.core;

import com.runx.framework.annotation.*;
import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.BeanDefinition;
import com.runx.framework.utils.ReflectUtils;
import com.runx.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author: kisszpy
 * @date: 2020/3/10
 */
@Slf4j
public class AnnotationRegisterBeanProcessor extends AbstractRegisterBeanProcessor implements LifeCircle {

    /**
     * 对Bean的定义
     * @param className
     * @param loader
     * @return
     */
    public BeanDefinition define(String className,ClassLoader loader) {
        try {
            Class<?> clazz = loader.loadClass(className);
            Predicate<Class> servicePredicate = x-> x.isAnnotationPresent(Service.class);
            Predicate<Class> componentPredicate = x-> x.isAnnotationPresent(Component.class);
            Predicate<Class> repositoryPredicate = x-> x.isAnnotationPresent(Repository.class);
            Predicate<Class> configurationPredicate = x-> x.isAnnotationPresent(Configuration.class);
            boolean conditionalResult = servicePredicate
                    .or(componentPredicate)
                    .or(repositoryPredicate)
                    .or(configurationPredicate)
                    .test(clazz);
            if (conditionalResult) {
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setName(StringUtils.lowerLetterOfFirst(clazz.getSimpleName()));
                beanDefinition.setInterfaces(clazz.getInterfaces());
                beanDefinition.setClazz(clazz);
                beanDefinition.setAlias(new String[]{
                        beanDefinition.getName()
                });
                beanDefinition.setClazzName(clazz.getName());
                beanDefinition.setSimpleName(clazz.getSimpleName());
                // only exists a @Autowired constructor
                Constructor<?> constructor = ReflectUtils.getConstructorsOfStream(clazz)
                        .filter(s -> s.isAnnotationPresent(Autowired.class))
                        .findFirst()
                        .orElse(null);
                if (constructor == null) {
                    // 默认无参
                    constructor = Arrays
                            .stream(clazz.getConstructors())
                            .filter(s -> s.getParameterCount() == 0)
                            .findFirst()
                            .get();
                }
                beanDefinition.setConstructor(constructor);
                return beanDefinition;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handleDependency(BeanDefinition beanDefinition) {
        Arrays.stream(beanDefinition.getClazz().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Autowired.class)) {
                        // 设置依赖关系
                        beanDefinition.getDependencies().putIfAbsent(field.getName(),field.getType());
                    }
                });
    }

    @Override
    public void init(BeanDefinition beanDefinition) {
        Object object = null;
        Constructor<?> constructor = beanDefinition.getConstructor();
        if (constructor.getParameterCount() == 0) {
            object = ReflectUtils.instance(constructor);
            beanDefinition.setInstance(object);
            beanDefinition.setInitial(true);

        }else {
            // 暂时无法构造
            beanDefinition.setInitial(false);
            beanDefinition.setInstance(null);
        }
    }

    @Override
    public void initPostConstruct() {
        // fixme
    }

    @Override
    public void afterProperties() {
        // fixme
    }

    @Override
    public void destroyMethod() {
        // fixme
    }

    private <T> T findOriginInstance(Class<?> clazz) {
        return (T) beanDefinitionMap.values().stream()
                .filter(item->item.getClazz().equals(clazz)
                        || Arrays.asList(item.getInterfaces()).contains(clazz) )
                .findFirst().get().getInstance();
    }

    public void compensate() {
        // 完成Bean的补偿
        for (String k : beanDefinitionMap.keySet()) {
            BeanDefinition item = beanDefinitionMap.get(k);
            Map<String,Class<?>> dependencies = item.getDependencies();
            for(String key : dependencies.keySet()) {
                try {
                    Object instance = item.getInstance();
                    Field field = instance.getClass().getDeclaredField(key);
                    field.setAccessible(true);
                    Object target = findOriginInstance(dependencies.get(key));
                    field.set(instance,target);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (!item.isInitial()){
                // fixme
                Class<?>[] clazz = item.getConstructor().getParameterTypes();
                Object[] paramValue = new Object[clazz.length];
                // fill values for parameter
                for (int i = 0; i < clazz.length; i++) {
                    paramValue[i] = findOriginInstance(clazz[i]);
                }
                try {
                    Object instance = item.getConstructor().newInstance(paramValue);
                    item.setInstance(instance);
                    item.setInitial(true);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("application context initial success .");
    }
}
