package com.haimin.code.concurrent;

import com.social.commission.server.controller.app.CommissionAppComtroller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/4  17:24
 */
public class ReadWriteLockExample {
    public static class MyCache2{
        private static volatile Map<Integer, Object> map = new HashMap();
        private ReentrantReadWriteLock rrwLock = new ReentrantReadWriteLock();
        public void write(int a){
            rrwLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "正在写入");
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(a, a + "###");
                System.out.println(Thread.currentThread().getName() + "写入完成");
            } finally {
                rrwLock.writeLock().unlock();
            }
        }

        public void read(int a){
            rrwLock.readLock().lock();
            try {
                System.out.println("线程" +Thread.currentThread().getName() + "正在读取");
                try {
                    TimeUnit.MILLISECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object o = map.get(a);
                System.out.println("线程" +Thread.currentThread().getName() + "读取完成 读取值为 "+ o);
            } finally {
                rrwLock.readLock().unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CommissionAppComtroller.MyCache2 myCache2 = new CommissionAppComtroller.MyCache2();
        //CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            for (int i = 1; i <= 5; i++) {
                final int intTemp = i;
                new Thread(() -> {
                    myCache2.read(intTemp);
                }, String.valueOf(i)).start();
            }
        });
        for (int i = 1; i <= 5; i++) {
            final int intTemp = i;
            new Thread(() -> {
                myCache2.write(intTemp);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                //countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
    }
}
