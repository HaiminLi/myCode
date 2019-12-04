package com.haimin.code.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/4  17:18
 */
public class SpinLockExample
{
    public static class MyCache{

        private static final Integer value1 = Integer.valueOf(100);
        private static final Integer value2 = Integer.valueOf(200);
        //compareAndSet 比较的是地址，不是数值
        private AtomicReference<Integer> atomicReference = new AtomicReference<>(value1);
        public void lock(){
            System.out.println(Thread.currentThread().getName() + "获得锁");
            while (!atomicReference.compareAndSet(value1, value2)){

            }
        }
        public void unLock(){
            System.out.println(Thread.currentThread().getName() + "解除锁");
            System.out.println("解除锁 后" + atomicReference.compareAndSet(value2,value1) + atomicReference.get());
        }
    }

    /**
     * 自旋锁，利用循环做锁，避免了死锁，但是牺牲了cpu，
     **/
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        new Thread(() -> {
            myCache.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myCache.unLock();
        }, "t1").start();
        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            myCache.lock();
            myCache.unLock();
        }, "t2").start();
    }
}
