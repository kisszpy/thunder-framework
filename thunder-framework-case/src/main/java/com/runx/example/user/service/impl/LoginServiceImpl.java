package com.runx.example.user.service.impl;

import com.runx.example.user.service.LoginService;
import com.runx.framework.annotation.Service;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public boolean login(String username, String password) {
        if ("admin".equals(username) && "123".equals(password)) {
            return true;
        }
        return false;
    }
}
