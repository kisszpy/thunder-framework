package com.runx.example;

import com.runx.example.dao.UserDao;
import com.runx.example.service.UserService;
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

    public void exec() {
        userService.say();
    }

}
