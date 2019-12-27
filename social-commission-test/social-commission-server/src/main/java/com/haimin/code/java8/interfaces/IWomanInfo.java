package com.haimin.code.java8.interfaces;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  10:30
 */
public interface IWomanInfo {

    int getAge();

    default String getName(){
     return "我是杨幂";
    }
    static void show(){
        System.out.println("Women接口 的静态方法");
    }
}
