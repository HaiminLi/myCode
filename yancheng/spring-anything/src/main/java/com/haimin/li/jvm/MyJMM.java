package com.haimin.li.jvm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class MyJMM {
    private volatile  int a = 0;
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.compareAndSet(0,1);
        AtomicReference<Integer> atomicReference = new AtomicReference<>();

        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(0,1);

        List<String> list = new ArrayList<>();  //线程不安全
        List<String> list1 = Collections.synchronizedList(new ArrayList<>());
        List<String> list2 = new CopyOnWriteArrayList<>();

        for (int i = 1; i <=30; i++){
            new Thread(() ->{
                list2.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(Thread.currentThread().getName() + "  大小  > "+ list2.size());
            }, String.valueOf(i)).start();
        }

    }

    /**
     * 1. JMM 含义：JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（Main Memory）中，
     * 每个线程都有一个私有的本地内存（Local Memory），本地内存中存储了该线程以读/写共享变量的副本
     *      1） 可见性 ： 可见性是指在多线程情况下，当一个线程修改了某一个共享变量的值之后，其他线程是否能够立即知道这个修改
     *      2） 原子性 ： 原子性是指一个操作是不可中断的，即使是在多个线程一起执行的情况下，一个操作一旦开始执行，就不会受到其他线程的干扰
     *      3） 有序性 ： 因为编译器优化的缘故，进行了指令重排的操作，重排后的指令与原指令的顺序未必一致
     */
    /**
     * 2. volatile 特点： java虚拟机提供的轻量级的同步机制
     *      1） 可见性 ： 保证
     *      2） 原子性 ： 不保证  但是可以用原子类解决
     *      3） 有序性 ： 禁止指令重排
     *
     *  单例模式的 volatile 版
     */
    /**
     * 3. CAS -> Compare And Set  是一条CPU并发原语，这个过程是原子的，不会被打断
     *      1) 原理： 核心类 -> UnSafe类， 自旋
     *      2) 缺点：
     *              1） 循环时间长，开销大：如果CAS失败，会一直进行尝试（自旋），如果一直不成功，会给CPU带来很大的开销
     *              2） 只能保证一个共享变量的原子操作 ： 对多个共享变量操作的时候，CAS无法保证操作的原子性，这个时候可以用锁来保证操作的原子性
     *              3） ABA 问题
     *                      1） 现象： 操作对象，获取对象后，执行CAS操作前，被其他线程修改后，且又修改为原来的对象值，导致CAS忽略其他线程的修改，成功执行CAS对象修改
     *                      2） 原子引用 ： AtomicReference<>
     *                      3） 解决办法 ： 加时间戳的原子引用  -> AtomicStampedReference
     *
     */
    /**
     * 4. 集合类不安全的问题
     *      1） 集合类不安全之 并发修改异常  java.util.ConcurrentModificationException，  多个线程修改 ArrayList
     *      2） 导致原因 ： 多个线程修改 ArrayList，一个线程正在修改，另一个线程争抢来修改
     *      3） 解决方案 ：
     *              1) new Vector<>()
     *              2) Collections.synchronizedList(new ArrayList<>())
     *              3) new CopyOnWriteArrayList<>()
     *               注 ： CopyOnWriteArrayList和Collections.synchronizedList是实现线程安全的列表的两种方式。
     *               两种实现方式分别针对不同情况有不同的性能表现，
     *               其中CopyOnWriteArrayList的写操作性能较差，而多线程的读操作性能较好。
     *               而Collections.synchronizedList的写操作性能比CopyOnWriteArrayList在多线程操作的情况下要好很多，而读操作因为是采用了synchronized关键字的方式，
     *               其读操作性能并不如CopyOnWriteArrayList。因此在不同的应用场景下，应该选择不同的多线程安全实现类
     *      4） 优化建议 ：
     *      5） 源码 ： CopyOnWriteArrayList 加lock锁，
     *      CopyOnWriteArraySet 底层还是 CopyOnWriteArrayList
     *      ConcurrentHashMap
     *
     */
    /**
     * 5.
     *
     */
}
q