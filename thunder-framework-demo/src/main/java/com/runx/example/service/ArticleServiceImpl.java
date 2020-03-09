package com.runx.example.service;

import com.runx.framework.annotation.Autowired;
import com.runx.framework.annotation.Service;

/**
 * @author: kisszpy
 * @date: 2020/3/9
 */
@Service
public class ArticleServiceImpl {

    private final UserService userService;

    private String name;


    @Autowired
    public ArticleServiceImpl(UserService userService) {
        this.name = name;
        this.userService = userService;
    }

    public void publish() {
        int myLikeNums = userService.showMyLikes();
        System.out.println(myLikeNums);
    }

}
