package com.runx.dao;

import lombok.Data;

import java.util.Date;

@Data
@Table("user")
public class User {

    private Long id;

    private String username;

    private String password;

    private String token;

    private Date createTime;

    private Date updateTime;

}
