package com.haimin.code.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/5  17:51
 */
public class BlockQueueExample {

    /*
     *             抛出异常  |  返回 boolean  |  阻塞    |    超时
     * 插入       add(e)     |     offer(e)  |  put(e)  |  offer(e,time, unit)
     * 移除       remove()   |     poll()    |  take()  |    poll(time, unit)
     * 检查       element()  |     peek()    |    -     |         -
     *
     * 阻塞队列常用的方法组
     *
     **/
    public static void main(String[] args) throws Exception {
        testArrayBlockingQueueApi();
        testLinkedBlockingQueueApi();
        testSynchronousQueueApi();
    }


    private static void testArrayBlockingQueueApi() throws InterruptedException {
        /*
         * 数组阻塞队列 解析
         * 继承类： 抽象队列 -> 抽象集合 ->
         * 实现接口：阻塞队列 -> 队列 -> 集合 -> Iterable
         * 包含的属性及含义：
         * final Object[] items; 维护的队列Object数组
         * int takeIndex; 从队列取的角标
         * int putIndex; 从队列插入的角标
         * int count; 队列含有元素的数量
         * final ReentrantLock lock; 主要使用的锁 (可重入锁) 默认使用非公平锁
         * private final Condition notEmpty; 服务“取元素”操作的condition
         * private final Condition notFull; 服务“放元素”操作的condition
         *
         * 构造方法 3个
         * ArrayBlockingQueue(int capacity)
         * ArrayBlockingQueue(int capacity, boolean fair)
         * ArrayBlockingQueue(int capacity, boolean fair,
                              Collection<? extends E> c)
         * 构造方法主要使用容量对items数组完成初始化，所以是有届队列，
         * fair参数用来构造一个公平的或不公平的ReentrantLock，默认是Lock为非公平锁。
         */

        List<Integer> list = Arrays.asList(1,2,3,4,5);

        BlockingQueue<Integer> queue = new ArrayBlockingQueue(5, false, list);

        System.out.println(queue.add(1));
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.offer(2));
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        queue.put(3);
        System.out.println(queue.take());
        System.out.println(queue.offer(4, 10, TimeUnit.SECONDS));
        System.out.println(queue.poll(10, TimeUnit.SECONDS));
    }

    private static void testLinkedBlockingQueueApi() throws InterruptedException {
        /*
         * 列表阻塞队列 解析
         * 继承类： 抽象队列 -> 抽象集合 ->
         * 实现接口：阻塞队列 -> 队列 -> 集合 -> Iterable
         * 包含的属性及含义：
         * private final int capacity 最大的容量，默认是 Integer.MAX_VALUE
         * private final AtomicInteger count = new AtomicInteger(); 列表元素的数量
         * transient Node<E> head; 首节点，此节点只有 nextItem有值，item为null
         * private transient Node<E> last; 尾结点，此节点只有item有值，nextItem为null
         * private final ReentrantLock takeLock = new ReentrantLock(); 两把锁中的一个，取锁
         * private final Condition notEmpty = takeLock.newCondition();
         * private final ReentrantLock putLock = new ReentrantLock();两把锁中的一个，放锁
         * private final Condition notFull = putLock.newCondition();
         *
         * 构造方法 3个
         * LinkedBlockingQueue() 容量为 Integer.MAX_VALUE
         * LinkedBlockingQueue(int capacity) 指定容量
         * LinkedBlockingQueue(Collection<? extends E> c) 将集合放入队列中
         * 队列有两个锁，加元素和取元素互不影响，都是非公平锁
         * 单链表的队列
         *
         */

        List<Integer> list = Arrays.asList(1,2,3,4,5);

        BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(5);

        System.out.println(queue.add(1));
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.offer(2));
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        queue.put(3);
        System.out.println(queue.take());
        System.out.println(queue.offer(4, 10, TimeUnit.SECONDS));
        System.out.println(queue.poll(10, TimeUnit.SECONDS));
    }

    private static void testSynchronousQueueApi() throws InterruptedException {
        /*
         * 数组阻塞队列 解析
         * 继承类： 抽象队列 -> 抽象集合 ->
         * 实现接口：阻塞队列 -> 队列 -> 集合 -> Iterable
         * 包含的属性及含义：
         *
         * 构造方法 3个
         *
         */

        List<Integer> list = Arrays.asList(1,2,3,4,5);

        BlockingQueue<Integer> queue = new SynchronousQueue<>();

        System.out.println(queue.add(1));
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.offer(2));
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        queue.put(3);
        System.out.println(queue.take());
        System.out.println(queue.offer(4, 10, TimeUnit.SECONDS));
        System.out.println(queue.poll(10, TimeUnit.SECONDS));
    }
}
