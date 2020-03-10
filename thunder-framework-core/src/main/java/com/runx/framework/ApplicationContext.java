package com.runx.framework;

import com.runx.framework.annotation.*;
import com.runx.framework.bean.BeanDefine;
import com.runx.framework.bean.ClassPathScanner;
import com.runx.framework.utils.ObjectUtils;
import com.runx.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Slf4j
public class ApplicationContext {

    private ClassPathScanner classPathScanner = new ClassPathScanner();

    private Map<String, BeanDefine> beanDefineMap = new ConcurrentHashMap<>(64);

    private Thread hook;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public ApplicationContext (String basePackage) {
        try {
            classPathScanner.scan(basePackage);
            List<String> clazz = classPathScanner.getResources();
            clazz.forEach(item -> {
                Class<?> cls = classPathScanner.loadClass(item);

                if (    cls.isAnnotationPresent(Service.class)
                        ||
                        cls.isAnnotationPresent(Component.class)
                        ||
                        cls.isAnnotationPresent(Repository.class)
                        ||
                        cls.isAnnotationPresent(Configuration.class)

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
                    // only exists a @Autowired constructor
                    Constructor<?> constructor = Arrays.stream(cls.getConstructors())
                            .filter(s -> s.isAnnotationPresent(Autowired.class))
                            .findFirst()
                            .orElse(null);
                    if (constructor == null) {
                        // 默认无参
                        constructor = Arrays.stream(cls.getConstructors()).filter(s -> s.getParameterCount() == 0).findFirst().get();
                    }
                    beanDefine.setConstructor(constructor);
                    handleDependency(beanDefine);
                    registerBean(beanDefine);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String mapkey : beanDefineMap.keySet()) {
            BeanDefine item = beanDefineMap.get(mapkey);
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
        Object object = null;
        try {
            Constructor<?> constructor = beanDefine.getConstructor();
            if (constructor.getParameterCount() == 0) {
                object = constructor.newInstance();
                beanDefine.setInstance(object);
                beanDefine.setInitial(true);
                // fixme
//                Method m = Arrays.stream(beanDefine.getClazz().getDeclaredMethods())
//                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
//                .findFirst().orElse(null);
//                m.invoke()

            }else {
                // 无法构造
                beanDefine.setInitial(false);
                beanDefine.setInstance(null);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        beanDefineMap.putIfAbsent(beanDefine.getName(), beanDefine);
    }

    public BeanDefine getBeanDefine(Class<?> clazz) {
        return beanDefineMap.get(clazz.getSimpleName());
    }

    private <T> T findOriginInstance(Class<?> clazz) {
        return (T) beanDefineMap.values().stream()
                .filter(item->item.getClazz().equals(clazz) || Arrays.asList(item.getInterfaces()).contains(clazz) )
                .findFirst().get().getInstance();
    }

    public  <T> T getBean(Class<?> clazz) {
        T t = (T) beanDefineMap.values().stream()
                .filter(item->item.getClazz().equals(clazz) || Arrays.asList(item.getInterfaces()).contains(clazz) )
                .findFirst().get().getInstance();
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

    public void start() {
        try {
            countDownLatch.await();
            close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        registerShutdownHook();
    }

    public void registerShutdownHook() {
        hook = new Thread(()->{
            countDownLatch.countDown();
            // fixme 用来释放资源，做到优雅关闭，比如连接池、数据库、线程池等
            System.out.println("容器关闭了");
        });
        Runtime.getRuntime().addShutdownHook(hook);
    }

}
