package com.haimin.code.concurrent;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/6  16:42
 */
class SelfLock implements Runnable{
    private Object lock1;
    private Object lock2;

    public SelfLock(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1){
            System.out.println(Thread.currentThread().getName() + "持有锁" + lock1 + "试图获取锁" + lock2);
            synchronized (lock2){
                System.out.println(Thread.currentThread().getName() + "持有2锁");
            }
        }
    }
}
public class DeadLockExample {

    /*
     * 此为死锁的demo
     * 定位死锁问题的方法：
     * 一 （1）jps 获取出现问题的代码线程号； （2）jstack 线程号 获取死锁的原因
     * 二 jconsole.exe java bin目录下 查看线程堆栈信息
     * 三 jvisualvm.exe java bin目录下 排查问题
     *
     */
    public static void main(String[] args) {
        Object lock1 = "@@";
        Object lock2 = "##";
        SelfLock selfLock1 = new SelfLock(lock1, lock2);
        SelfLock selfLock2 = new SelfLock(lock2, lock1);
        new Thread(selfLock1).start();
        new Thread(selfLock2).start();
    }
}
