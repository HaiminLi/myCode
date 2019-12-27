package com.haimin.code.java8.interfaces;

import com.haimin.code.java8.annotation.MyAnnotation;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  10:33
 */
public class Person {

    public int getAge(){
        return 18;
    }
    public String getName(){
        return "我是人类啊";
    }
    @MyAnnotation
    @MyAnnotation("这是方法上面的值")
    public void test1(){
    }
}
