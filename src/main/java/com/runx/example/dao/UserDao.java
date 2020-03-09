package com.runx.example.dao;

import com.runx.example.service.UserService;
import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Repository;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Repository
public class UserDao {

    @Autowired
    private UserService userService;

    public void save() {
        System.out.println("save .... ");
    }

}
