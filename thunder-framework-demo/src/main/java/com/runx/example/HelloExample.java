package com.runx.example;

import com.runx.example.dao.UserDao;
import com.runx.example.service.UserService;
import com.runx.example.user.service.LoginService;
import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Component;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */

@Component
public class HelloExample {

    private String name = "Java";

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginService loginService;

    public void exec() {
        userService.say();
        boolean result = loginService.login("hello","23");
        System.out.println(result);
    }

}
