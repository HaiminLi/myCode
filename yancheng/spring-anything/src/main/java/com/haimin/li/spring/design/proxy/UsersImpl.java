package com.haimin.li.spring.design.proxy;

public class UsersImpl implements IUsers {
    public int save() {
        System.out.println("����ɹ�������");
        return 1;
    }
}
