package com.runx.framework;

import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Component;
import com.runx.framework.annotation.Repository;
import com.runx.framework.annotation.Service;
import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.ClassPathScanner;
import com.runx.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Slf4j
public class ApplicationContext {

    private ClassPathScanner classPathScanner = new ClassPathScanner();

    private Map<String, BeanDefine> beanDefineMap = new ConcurrentHashMap<>(64);

    public ApplicationContext (String basePackage) {
        try {
            classPathScanner.scan(basePackage);
            List<String> clazz = classPathScanner.getResources();
            System.out.println(clazz);
            clazz.forEach(item -> {
                Class<?> cls = classPathScanner.loadClass(item);

                if (    cls.isAnnotationPresent(Service.class)
                        ||
                        cls.isAnnotationPresent(Component.class)
                        ||
                        cls.isAnnotationPresent(Repository.class)

                ) {
                    BeanDefine beanDefine = new BeanDefine();
                    beanDefine.setName(StringUtils.lowerLetterOfFirst(cls.getSimpleName()));
                    beanDefine.setInterfaces(cls.getInterfaces());
                    beanDefine.setClazz(cls);
                    beanDefine.setAlias(new String[]{
                            beanDefine.getName()
                    });
                    beanDefine.setClazzName(cls.getName());
                    beanDefine.setSimpleName(cls.getSimpleName());
                    handleDependency(beanDefine);
                    registerBean(beanDefine);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }



        // 处理依赖关系
        beanDefineMap.values().forEach(item->{

        });
        for (String mapkey : beanDefineMap.keySet()) {
            BeanDefine item = beanDefineMap.get(mapkey);
            Map<String,Class<?>> dependencies = item.getDependencies();
            for(String key : dependencies.keySet()) {
                try {
                    Object instance = item.getInstance();
                    Field field = instance.getClass().getDeclaredField(key);
                    field.setAccessible(true);
                    Object target = findOriginInstance(dependencies.get(key));
                    log.info("setting target");
                    field.set(instance,target);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
//        // 开始处理代理
//        for (String key : beanDefineMap.keySet()) {
//            BeanDefine item = beanDefineMap.get(key);
//            Object proxyInstance = new CglibProxy(item.getInstance()).getInstance();
//            item.setProxyInstance(proxyInstance);
//            System.out.println(item);
//        }
        log.info("beanMap process end .");
    }

    private void handleDependency(BeanDefine beanDefine) {
        Arrays.stream(beanDefine.getClazz().getDeclaredFields())
        .forEach(field -> {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Autowired.class)) {
                // 设置依赖关系
                beanDefine.getDependencies().putIfAbsent(field.getName(),field.getType());
            }
        });
    }


    public void registerBean(BeanDefine beanDefine){
        Class<?> clazz = beanDefine.getClazz();
        Object object = null;
        try {
            object = clazz.newInstance();
            beanDefine.setInstance(object);
            beanDefine.setInitial(true);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        beanDefineMap.putIfAbsent(beanDefine.getName(), beanDefine);


    }

    public BeanDefine getBeanDefine(Class<?> clazz) {
        return beanDefineMap.get(clazz.getSimpleName());
    }

//    public <T> T getBean1(String name){
//        BeanDefine beanDefine = beanDefineMap.get(name);
//        if (beanDefine == null) {
//            return (T) beanDefineMap.values().stream()
//                    .filter((v)-> v.getAlias().equals(name)).findFirst().get().getProxyInstance();
//        }else {
//            return (T) beanDefine.getProxyInstance();
//        }
//    }

//    public  <T> T getBean1(Class<?> clazz) {
//        System.out.println("ffff" + beanDefineMap);
//        T t = (T) beanDefineMap.values().stream()
//                .filter(item->item.getClazz().equals(clazz) || Arrays.asList(item.getInterfaces()).contains(clazz) )
//                .findFirst().get().getProxyInstance();
//        System.out.println(beanDefineMap);
//        return t;
//    }

    private <T> T findOriginInstance(Class<?> clazz) {
        return (T) beanDefineMap.values().stream()
                .filter(item->item.getClazz().equals(clazz) || Arrays.asList(item.getInterfaces()).contains(clazz) )
                .findFirst().get().getInstance();
    }

    public  <T> T getBean(Class<?> clazz) {
        T t = (T) beanDefineMap.values().stream()
                .filter(item->item.getClazz().equals(clazz) || Arrays.asList(item.getInterfaces()).contains(clazz) )
                .findFirst().get().getInstance();
        System.out.println(beanDefineMap);
        return t;
    }

    public <T> T getBean(String name){
        BeanDefine beanDefine = beanDefineMap.get(name);
        if (beanDefine == null) {
            return (T) beanDefineMap.values().stream()
                    .filter((v)-> v.getAlias().equals(name)).findFirst().get().getInstance();
        }else {
            return (T) beanDefine.getInstance();
        }
    }

}
