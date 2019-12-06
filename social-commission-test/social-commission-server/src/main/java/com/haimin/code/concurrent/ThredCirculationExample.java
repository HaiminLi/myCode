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
     * lock condition 的使用
     * 线程的状态 ：
     * 一 NEW（新建）：创建后尚未启动的线程处于这个状态。
     * 意思是这个线程没有被start()启动，或者说还根本不是一个真正意义上的线程，从本质上讲这只是创建了一个Java外壳，还没有真正的线程来运行。
     * 不代表调用了start()，状态就立即改变，中间还有一些步骤，如果在这个启动的过程中有另一个线程来获取它的状态，其实是不确定的，要看那些中间步骤是否已经完成了
     *
     * 二 RUNNABLE（可运行）
     * RUNNABLE状态包括了操作系统线程状态中的Running和Ready，也就是处于此状态的线程可能正在运行，也可能正在等待系统资源，如等待CPU为它分配时间片，如等待网络IO读取数据。
     * RUNNABLE状态也可以理解为存活着正在尝试征用CPU的线程（有可能这个瞬间并没有占用CPU，但是它可能正在发送指令等待系统调度）。
     * 由于在真正的系统中，并不是开启一个线程后，CPU就只为这一个线程服务，它必须使用许多调度算法来达到某种平衡，不过这个时候线程依然处于RUNNABLE状态。
     *
     * 三 BLOCKED（阻塞）
     * BLOCKED称为阻塞状态，或者说线程已经被挂起，它“睡着”了，原因通常是它在等待一个“锁”，当尝试进入一个synchronized语句块/方法时，
     * 锁已经被其它线程占有，就会被阻塞，直到另一个线程走完临界区或发生了相应锁对象的wait()操作后，它才有机会去争夺进入临界区的权利
     *
     * 四 WAITING （无限期等待）
     * 处于这种状态的线程不会被分配CPU执行时间，它们要等待显示的被其它线程唤醒。
     * 这种状态通常是指一个线程拥有对象锁后进入到相应的代码区域后，调用相应的“锁对象”的wait()方法操作后产生的一种结果。
     * 变相的实现还有LockSupport.park()、Thread.join()等，它们也是在等待另一个事件的发生，也就是描述了等待的意思。
     *
     * 五 TIMED_WAITING（限期等待）
     * 处于这种状态的线程也不会被分配CPU执行时间，不过无需等待被其它线程显示的唤醒，在一定时间之后它们会由系统自动的唤醒。
     * 以下方法会让线程进入TIMED_WAITING限期等待状态：
     * （1）Thread.sleep()方法
     * （2）设置了timeout参数的Object.wait()方法
     * （3）设置了timeout参数的Thread.join()方法
     * （4）LockSupport.parkNanos()方法
     * （5）LockSupport.parkUntil()方法
     *
     * 六 TERMINATED（结束）
     * 已终止线程的线程状态，线程已经结束执行。换句话说，run()方法走完了，线程就处于这种状态。
     * 其实这只是Java语言级别的一种状态，在操作系统内部可能已经注销了相应的线程，
     * 或者将它复用给其他需要使用线程的请求，而在Java语言级别只是通过Java代码看到的线程状态而已
     **/

    /*  线程的生命周期大体可分为5种状态

     * 1. 新建(NEW)：新创建了一个线程对象。
     * 2. 可运行(RUNNABLE)：线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取cpu 的使用权 。
     * 3. 运行(RUNNING)：可运行状态(runnable)的线程获得了cpu 时间片（timeslice） ，执行程序代码
     * 4. 阻塞(BLOCKED)：阻塞状态是指线程因为某种原因放弃了cpu 使用权，也即让出了cpu timeslice，暂时停止运行。
     *      直到线程进入可运行(runnable)状态，才有机会再次获得cpu timeslice 转到运行(running)状态。阻塞的情况分三种：
     *      (一). 等待阻塞：运行(running)的线程执行o.wait()方法，JVM会把该线程放入等待队列(waitting queue)中。
     *      (二). 同步阻塞：运行(running)的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池(lock pool)中。
     *      (三). 其他阻塞：运行(running)的线程执行Thread.sleep(long ms)或t.join()方法，或者发出了I/O请求时，
     *      JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入可运行(runnable)状态。
     * 5. 死亡(DEAD)：线程run()、main() 方法执行结束，或者因异常退出了run()方法，则该线程结束生命周期。死亡的线程不可再次复生。
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

