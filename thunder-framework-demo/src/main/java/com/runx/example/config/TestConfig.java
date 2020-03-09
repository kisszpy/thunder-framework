package com.runx.example.config;

import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Value;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
public class TestConfig {

    @Value("${jdbc.username:com.mysql.jdbc.Driver}")
    private String driverClassName;

}
