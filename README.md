# thunder-framework
> 一个类似于SpringIOC和AOP的框架，第一版本比较简单，后续需要再去细化很多场景
* 完成IOC和AOP功能
* AOP支持JDK动态代理和Cglib两种
* com.runx.example 主要模拟使用场景
* com.runx.framework 主要是框架功能
``` text
目前来说实现比较粗糙，但是功能基本已经有了，后续要进行细腻处理
```
# 使用说明
``` java
ApplicationContext context = new ApplicationContext("com.runx.example");
UserDao example = context.getBean(UserDao.class);
```
