package com.social.commission.server.controller.app;

import com.social.commission.api.vo.prt.MyCommission;
import com.social.common.model.BaseResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/3  10:16
 */
@Slf4j
@SuppressWarnings("All")
public class CommissionAppComtroller {

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
        MyCache2 myCache2 = new MyCache2();
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
        //countDownLatch.await(3, TimeUnit.SECONDS);

    }









    @Test
    public void updateStatus(){




        AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<>(122, 1);
        new Thread(()->{
            int stamp = integerAtomicStampedReference.getStamp();
            log.info("线程 t1, 获取的时间戳 = {}", stamp);
            try { TimeUnit.SECONDS.sleep(1);; } catch (InterruptedException e) {log.info("t1 异常");  e.printStackTrace(); }
            boolean b1 = integerAtomicStampedReference.compareAndSet(122, 100, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1);
            log.info("线程 t1, 成功否 = {} 获取的时间戳 = {}, 数值 = {}",b1, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getReference());
            boolean b2 = integerAtomicStampedReference.compareAndSet(100, 122, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp()+1);
            log.info("线程 t1,成功否 = {} 获取的时间戳 = {}, 数值 = {}",b2, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getReference());
        }, "t1").start();

        new Thread(()->{
            int stamp = integerAtomicStampedReference.getStamp();
            log.info("线程 t2, 获取的时间戳 = {}", stamp);
            try { TimeUnit.SECONDS.sleep(2);; } catch (InterruptedException e) {log.info("t2 异常"); e.printStackTrace(); }
            log.info("111111");
            boolean b3 = integerAtomicStampedReference.compareAndSet(122, 2019, stamp, integerAtomicStampedReference.getStamp()+1);
            log.info("线程 t2, 成功否 = {} 获取的时间戳 = {}, 数值 = {}",b3, integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getReference());
        }, "t2").start();
        try { TimeUnit.SECONDS.sleep(3);; } catch (InterruptedException e) {log.info("t2 异常"); e.printStackTrace(); }
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
        log.info("over");
    }

    public static class MyCache1{

        private static final Integer value1 = Integer.valueOf(100);
        private static final Integer value2 = Integer.valueOf(200);
        private  AtomicReference<Integer> atomicReference = new AtomicReference<>(value1);
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
//
//    public static void a1(String[] args) {
//        MyCache myCache = new MyCache();
//        new Thread(() -> {
//            myCache.lock();
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            myCache.unLock();
//        }, "t1").start();
//        try {
//            Thread.currentThread().sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(() -> {
//            myCache.lock();
//            myCache.unLock();
//        }, "t2").start();
//    }
}
