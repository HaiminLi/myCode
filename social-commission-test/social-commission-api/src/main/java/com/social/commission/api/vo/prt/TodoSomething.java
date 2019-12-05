package com.social.commission.api.vo.prt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/5  15:38
 */
public class TodoSomething {
    private volatile boolean flag = false;
    private int who = 1;// ？？不加volatile也可以？
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    /**
     *  1.判断，用while
     *  2.处理业务
     *  3.通知其他线程
     **/
    public void doTimes3(){
        lock.lock();
        try {
            while (who != 1) {
                c1.await();
            }
            for (int i = 1; i <= 3 ; i++) {
                System.out.print(i);
            }
//            System.out.print(Thread.currentThread().getName());
            System.out.println();
            c2.signal();
//            TimeUnit.MILLISECONDS.sleep(1000);
            who = 2;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void doTimes6(){
        lock.lock();
        try {
            while (who != 2){
                System.out.print(Thread.currentThread().getName() + "#");
                c2.await();
                System.out.print(Thread.currentThread().getName() + "$");
            }
            for (int i = 1; i <= 6 ; i++) {
                System.out.print(i);
            }
//            System.out.print(Thread.currentThread().getName());
            System.out.println();
            c3.signal();
//            TimeUnit.MILLISECONDS.sleep(1000);
            who = 3;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void doTimes9(){
        lock.lock();
        try {
            while (who != 3){
                c3.await();
            }
            for (int i = 1; i <= 9 ; i++) {
                System.out.print(i);
            }
//            System.out.print(Thread.currentThread().getName());
            System.out.println();
            c1.signal();
//            TimeUnit.MILLISECONDS.sleep(1000);
            who = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void doTimes(int times, int me, int next, Lock lock, Condition wait,Condition signal){
        lock.lock();
        try {
            while (who != me){
                wait.await();
            }
            for (int i = 1; i <= times ; i++) {
                System.out.print(i);
            }
//            System.out.print(Thread.currentThread().getName());
            System.out.println();
            signal.signal();
//            TimeUnit.MILLISECONDS.sleep(1000);
            who = next;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
