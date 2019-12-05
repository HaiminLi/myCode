package com.haimin.code.concurrent;

import com.social.commission.api.vo.prt.TodoSomething;
import org.apache.poi.hssf.record.formula.functions.T;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/5  11:42
 */
public class ThredCirculationExample {
    /*
     * 三个线程循环调用
     **/
//    public static class TodoSomething{
//        private volatile boolean flag = false;
//        private int who = 1;//
//        private Lock lock = new ReentrantLock();
//        private Condition c1 = lock.newCondition();
//        private Condition c2 = lock.newCondition();
//        private Condition c3 = lock.newCondition();
//
//        /**
//         *  1.判断，用while
//         *  2.处理业务
//         *  3.通知其他线程
//         **/
//        public void doTimes3(){
//            lock.lock();
//            try {
//                while (who != 1) {
//                    c1.await();
//                }
//                for (int i = 1; i <= 3 ; i++) {
//                    System.out.print(i);
//                }
//                System.out.print(Thread.currentThread().getName());
//                System.out.println();
//                who = 2;
//                c2.signal();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        }
//        public void doTimes6(){
//            lock.lock();
//            try {
//                while (who != 2){
//                    c2.await();
//                }
//                for (int i = 1; i <= 6 ; i++) {
//                    System.out.print(i);
//                }
//                System.out.print(Thread.currentThread().getName());
//                System.out.println();
//                who = 3;
//                c3.signal();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        }
//        public void doTimes9(){
//            lock.lock();
//            try {
//                while (who != 3){
//                    c3.await();
//                }
//                for (int i = 1; i <= 9 ; i++) {
//                    System.out.print(i);
//                }
//                System.out.print(Thread.currentThread().getName());
//                System.out.println();
//                who = 1;
//                c1.signal();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        }
//    }

    public static void main(String[] args) {
        TodoSomething todoSomething = new TodoSomething();
        Lock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();
            for (int i = 1; i <= 10; i++) {
                new Thread(() -> {
                    todoSomething.doTimes(3,1,2,lock, c1, c2);
                }, "AA" + i).start();
                new Thread(() -> {
                    todoSomething.doTimes(6,2,3,lock, c2, c3);
                }, "BB" + i).start();
                new Thread(() -> {
                    todoSomething.doTimes(9,3,1,lock, c3, c1);
                }, "CC" + i).start();
            }

//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                todoSomething.doTimes3();
//            }
//        }, "AA").start();
//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                todoSomething.doTimes6();
//            }
//        }, "BB").start();
//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                todoSomething.doTimes9();
//            }
//        }, "CC").start();
    }
}

