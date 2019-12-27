package com.haimin.code.java8.interfaces;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  11:11
 */
public class TestInterface {
    /*
     * 一 java8支持默认方法，默认方法可以有实现
     * 1.0 NewInterface 类 implements 一个接口 IManInfo
     * 若 NewInterface 没有实现接口的默认方法，则调用接口的默认方法实现
     * 若 NewInterface 实现了接口的默认方法，则调用 NewInterface 的方法实现
     * 2.0 NewInterface 类 implements 两个接口 IManInfo, IWomanInfo 这两个接口的默认方法一模一样，还有相同的抽象方法
     * NewInterface 必须实现这个默认方法，否则编译不过，若实现类有自己的实现方法，则调用实现类的方法，
     * 若想使用接口的默认实现方法，则要指定是那个接口的方法 IWomanInfo.super.getName()
     * 3.0 和2.0的条件一样，并且 NewInterface 类 继承了一个类 这个类里面的方法和接口的一样
     * 若 NewInterface 没有重写这些方法，则使用这个继承类的方法实现
     * 若 NewInterface 重写了这些方法，则使用 NewInterface 的重写方法
     *
     * 二 接口中可以写静态方法，这个静态方法是接口私有，实现接口的类不要使用
     */
    public static void main(String[] args) {
        NewInterface newInterface = new NewInterface();
        int age = newInterface.getAge();
        String name = newInterface.getName();
        System.out.println(age);
        System.out.println(name);
        IWomanInfo.show();

    }
}
