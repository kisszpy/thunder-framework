package com.runx.example.aop;

import lombok.Data;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 * @author: kisszpy
 * @date: 2020/3/13
 */
@Data
public class InstanceDemo {

    private String name;

    public InstanceDemo(String name) {
        this.name = name;
    }

    public void say() {
        System.out.println(name);
        System.out.println("say");
    }

    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisStd(true);
        InstanceDemo target = objenesis.newInstance(InstanceDemo.class);
        System.out.println(target);
        target.say();
    }
}
