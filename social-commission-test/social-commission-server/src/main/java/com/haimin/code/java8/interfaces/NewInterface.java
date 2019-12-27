package com.haimin.code.java8.interfaces;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  10:29
 */
public class NewInterface extends Person implements IManInfo, IWomanInfo {
    @Override
    public int getAge() {
        return 1;
    }

    @Override
    public String getName() {
        return IWomanInfo.super.getName();
    }
//    public String getName(){
//        return "我是超人";
//    }
}
