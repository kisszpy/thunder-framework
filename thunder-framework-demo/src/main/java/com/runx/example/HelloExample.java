package com.runx.example;

import com.runx.example.dao.UserDao;
import com.runx.example.service.UserService;
import com.runx.example.service.impl.UserServiceImpl;
import com.runx.example.user.service.LoginService;
import com.runx.framework.ApplicationContext;
import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Component;
import com.runx.framework.annotation.Service;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */

@Service
public class HelloExample {

//    private String name = "Java";

    @Autowired
    public UserService userService;

    @Autowired
    public UserDao userDao;

    @Autowired
    public LoginService loginService;

    public void exec() {
         userService.say();
        // boolean result = loginService.login("hello","23");
        System.out.println("登录结果：" + false);
    }


}
