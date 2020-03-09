package com.runx.example.service.impl;

import com.runx.example.dao.UserDao;
import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Service;
import com.runx.example.service.UserService;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void say() {
        System.out.println("我是服务层");
        userDao.save();
    }
}
