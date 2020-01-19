package com.haimin.li.spring.design.proxy;

public class StaticProxy {

    /**
     *   静态代理实例
     *
     */
    public static void main(String[] args) {
        //目标对象
        UsersImpl target = new UsersImpl();

        //代理对象,把目标对象传给代理对象,建立代理关系
        UserDaoProxy proxy = new UserDaoProxy(target);

        proxy.save();//执行的是代理的方法
    }

    /**
     * 静态代理要实现相同的接口
     */
    public static class UserDaoProxy implements IUsers{

        private UsersImpl target;

        public UserDaoProxy(UsersImpl target){
            this.target=target;
        }

        public int save() {
            System.out.println("开始事务...");
            target.save();//执行目标对象的方法
            System.out.println("提交事务...");
            return 1;
        }
    }
}
