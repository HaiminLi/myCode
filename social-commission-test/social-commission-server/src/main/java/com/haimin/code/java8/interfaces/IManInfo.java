package com.haimin.code.java8.interfaces;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  10:30
 */
public interface IManInfo {

    int getAge();

    default String getName(){
        return "我是海民";
    }
}
