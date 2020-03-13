package com.runx.framework.expression;

import com.runx.framework.contants.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: kisszpy
 * @date: 2020/3/12
 */
public class RegularExpression {
    private static final Pattern pattern = Pattern.compile(Constants.REGULAR_EXECUTE);

    public static String matchExecute(String source){
        Matcher matcher = pattern.matcher(source);
        boolean result = matcher.find();
        if (result) {
            return matcher.group(2);
        }
        return null;
    }

    public static void noCaptureGroup(){

        // (?:(\d+))?\s?([a-zA-Z]+)?.+
        Pattern pattern = Pattern.compile("(execute\\()([a-zA-Z.]+)?.+");
        String source = "execute( fdsdee4333)";
        Matcher matcher = pattern.matcher(source);
        if(matcher.matches()){
            for(int i=0;i<=matcher.groupCount();i++){
                System.out.println("group "+i+":"+matcher.group(i));
            }
        }
    }


}
