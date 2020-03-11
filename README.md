# thunder-framework
> 一个类似于SpringIOC和AOP的框架，第一版本比较简单，后续需要再去细化很多场景
* 完成IOC和AOP功能
* AOP支持JDK动态代理和Cglib两种
* com.runx.example 主要模拟使用场景
* com.runx.framework 主要是框架功能
* 实现资源文件加载和处理，支持application.properties、application.yml、application.yaml
* 完成框架内部对于IOC容器的使用
* 完善aop和嵌入式Tomcat的开发 [进行中]
* 完成事务管理 [规划中]
* 使用框架功能开发一个简单的blog系统 [规划中]
* 书写系统使用文档，建立官方网站 [规划中]
``` text
目前来说实现比较粗糙，但是功能基本已经有了，后续要进行细腻处理
```
# 使用说明
``` java
ApplicationContext context = new ApplicationContext("com.runx.example");
UserDao example = context.getBean(UserDao.class);
```
