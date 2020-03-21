package com.runx.reg;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: kisszpy
 * @date: 2020/3/17
 */
public class RegTest {
    public static void main(String[] args) {
        String pattern = "public com.baidu.*(..)";
        String input = "public com.baidu.service()";
        boolean matchResult = "public com.baidu.service()".matches(pattern);
//        System.out.println(matchResult);
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        // matcher.find();
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

    }
}
