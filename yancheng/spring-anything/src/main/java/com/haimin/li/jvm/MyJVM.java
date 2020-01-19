package com.haimin.li.jvm;

import com.haimin.li.spring.annotation.pojo.Red;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyJVM {

    static class OOMTest{}

    public static void main(String[] args) throws InterruptedException {
        System.out.println("GGGGGGGGGGGGGCCCCCCCCCCCCCCCCCCCC");
        long l = Runtime.getRuntime().totalMemory();//返回java虚拟机的内存总量
        long l1 = Runtime.getRuntime().maxMemory();//返回java虚拟机试图使用的最大内存
//        byte[] bytes = new byte[50 * 1024 * 1024];
//        Thread.sleep(Integer.MAX_VALUE);
//        Red red = new Red();
//        Reference<Red> reference = new SoftReference<Red>(red);

//        StackOverflowErrorTest();
//        javaHeapSpaceTest();
//        GCOverheadLimitExceeded();
//        directBufferMemory();
//        unableToCreateNewNativeThread();
        metaspace();
    }

    private static void metaspace() {
        //-XX：MetaspaceSize=128M -XX：MaxMetaspaceSize=128M
        /*
         * 元空间存放的数据有：
         * 1.虚拟机加载的类信息
         * 2.常量池
         * 3.静态变量
         * 4.即时编译后的代码
         */
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMTest.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            enhancer.create();
        }
    }

    private static void unableToCreateNewNativeThread() {
        /*
         * 此异常 高并发请求服务器的时候，经常出现； native thread 异常和对应的平台有关
         *
         * 原因：
         *      1.一个应用进程创建多个线程，超过系统承载极限
         *      2.服务器不允许你的应用程序创建这么多的线程，Linux默认是1024个线程数
         * 解决办法：
         *      1.想办法降低你的应用程序创建的线程数
         *      2.如果应用确实需要这么多的线程，可以更改Linux的默认做大线程数
         *          Linux  root用户可以一直new 线程，无上限
         *          1）查看用户的线程上线：ulimit -u
         *          2）修改 vim /etc/security/limits.d/90-nproc.conf
         */
        for (int i = 1; ; i++){
            System.out.println("线程数" + i);
            new Thread(() -> {
                try {
                    Thread.sleep(999999999);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }

    private static void directBufferMemory() {
        /* 主要是NIO导致的
         * 原因： 写NIO程序经常使用ByteBuffer来读取或者写入数据，这是一种基于通道（channel）和缓冲区（buffer）的IO方式
         * 它可以使用native函数库直接分配堆外内存，然后通过一个存储在堆内里的 DirectByteBuffer 对象作为这个内存的引用进行操作
         * 这样在一些场景中可以提高性能，因为避免了在java堆和native堆中来回复制数据
         *
         * ByteBuffer.allocate（capability） 分配jvm堆内存，属于GC管辖范围，由于需要拷贝，说有性能较低
         * ByteBuffer.allocateDirect（capability） 分配操作系统本地内存，不属于GC管辖范围，由于不需要内存拷贝，所以速度相对较快
         *
         * 如果 不断分配本地内存，堆内存很少使用，那么jvm就不需要执行GC DirectByteBuffer 对象就不会回收
         * 这个时候堆内存充足，但是本地内存已经使用完了，再次尝试分配本地内存的时候就会抛 OutOfMemoryError 倒是程序崩溃
         */
        // -XX:MaxDirectMemorySize=5M
        System.out.println("配置的maxDirectMemory "+sun.misc.VM.maxDirectMemory()/(double)1024/1024 + "M");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(40 * 1024 * 1024);
    }

    private static void GCOverheadLimitExceeded() {
        //GC 时间过长：98%的时间用来做GC，但是回收的堆空间不到2% 就会抛出
        int i = 0;
        List<String> list = new ArrayList<String>();
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }

    private static void javaHeapSpaceTest() {
//        -Xms10m -Xmx10m -XX:+PrintGCDetails
        byte[] bytes = new byte[50 * 1024 * 1024];
    }

    private static void StackOverflowErrorTest() {
        StackOverflowErrorTest();
    }



    /**
     * JVM 相关
     * 1. 垃圾回收算法
     *      1）引用计数
     *      2）复制                ->年轻代
     *      3）标记清楚            ->老年代
     *      4）标记清除整理        ->老年代
     */
    /**
     * 2. 如何确定垃圾？
     *      内存中已经确定不再使用的空间即为垃圾
     * 3. 如何判断一个对象是否可以回收？
     *      1） 引用计数法 （一个地方引用了 +1，没有引用 -1；  很难解决循环互相引用的问题）
     *      2） 枚举根节点做可达性分析（根搜索路径）  GC roots   GC root：一组必须活跃的引用
     *              那些对象可以作为GC roots？
     *                  1）虚拟机栈中引用的对象
     *                  2）方法区中的类静态属性引用的对象
     *                  3）方法区中常量引用的对象
     *                  4）本地方法栈中JNI（Native方法）引用的对象
     */
    /**
     * 4. JVM参数
     *      1）如何盘点查看JVM系统默认值？
     *          1）jps -l  查看当前运行的java程序的进程号
     *          2）jinfo -flag 要查看的参数（如：PrintGCDetails） 上一步活动的进程号   ->查看当前的值，或者是否开启
     *             jinfo -flags  上一步活动的进程号  ->查看当前jvm所有参数的值，或者是否开启
     *      2）JVM参数类型分类：
     *          1）：标配参数  -version；  -help；  每个版本基本不变的参数
     *          2）：X参数  -Xint（解释执行）   -Xcomp（第一次私有就编译成本地代码）  -Xmixed（混合模式）   实例：java -Xint -version
     *          3）：XX参数
     *              1）Boolean类型
     *                  1） 公式 -XX：+（-） 属性  ： +开启 -关闭
     *                  2） -XX：+PrintGCDetails  是否打印GC 收集细节
     *                  3） -XX:+UseSerialDC   是否使用串行垃圾回收器
     *              2）kv设值类型
     *              1） 公式 -XX：属性key=属性值value
     *              2） 设置元空间大小： -XX：MetaspaceSize=128M
     *              3） 数值多大岁数今日old区: -XX：MaxTenuringThreshold=15
     *              4) 堆内存初始值: -Xms1024m   等价于 -XX:InitialHeapSize=1024m
     *              5) 堆内存最大值: -Xmx1024m   等价于 -XX:MaxHeapSize=1024m
     *       3)盘点家底查看JVM默认值
     *            1）-XX:+PrintFlagsInitial  查看JVM初始默认值
     *            2）-XX:+PrintFlagsFinal   查看JVM修改之后的默认值  有：=的是有更改的参数
     *                  运行java是同时也打印出参数   java -XX:+PrintFlagsFinal -version
     *            3）-XX:+PrintCommandLineFlags  打印命令行参数 可以查看使用了那个垃圾回收器
     *       4）JVM常用参数
     *              1）-Xms   等价于 -XX:InitialHeapSize   默认物理内存的 1/64
     *              2）-Xmx   等价于 -XX:MaxHeapSize       默认物理内存的 1/4
     *              3）-Xss   等价于 -XX:ThreadStackSize   一般默认502K ~ 1024K  和运行的平台有关系  Linux = 1024K
     *              4）-Xmn   等价于 -XX:NewSize 设置年轻代的大小
     *              5）-XX:MetaspaceSize   设置元空间大小   元空间不同于永久代（java7之前）  元空间在本地物理内存，大小受本地内存大小的限制  出厂设置21M左右
     *              6）-XX：+PrintGCDetails
     *                      Heap
                               PSYoungGen      total 37888K, used 4597K [0x00000000d6600000, 0x00000000d9000000, 0x0000000100000000)
                                eden space 32768K, 14% used [0x00000000d6600000,0x00000000d6a7d628,0x00000000d8600000)
                                from space 5120K, 0% used [0x00000000d8b00000,0x00000000d8b00000,0x00000000d9000000)
                                to   space 5120K, 0% used [0x00000000d8600000,0x00000000d8600000,0x00000000d8b00000)
                               ParOldGen       total 86016K, used 0K [0x0000000083200000, 0x0000000088600000, 0x00000000d6600000)
                                object space 86016K, 0% used [0x0000000083200000,0x0000000083200000,0x0000000088600000)
                               Metaspace       used 3323K, capacity 4556K, committed 4864K, reserved 1056768K
                                class space    used 355K, capacity 392K, committed 512K, reserved 1048576K
                            [GC (Allocation Failure) [PSYoungGen: 531K->488K(2560K)] 863K->844K(9728K), 0.0008784 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
                            [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 707K->689K(7168K)] 707K->689K(9728K), [Metaspace: 3467K->3467K(1056768K)], 0.0042250 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
                            [GC类型  [什么区 GC前使用大小 -> GC后使用的大小（总大小）] jvm堆gc前使用大小 -> GC后使用的大小 （JVM总大小）  gc耗时]  [用户耗时，系统耗时，实际耗时]
                            GC是Young的，  Full GC Old区
     *              7）-XX:SurvivorRatio   设置新生代中Eden和S0/S1空间比例 默认 -XX:SurvivorRatio=8  -> Eden:S0:S1=8:1:1   改成-XX:SurvivorRatio=4  -> Eden:S0:S1=4:1:1
     *              8）-XX:NewRatio        设置年轻代和年老代 占堆的比例     默认 -XX:NewRatio=2  -> Young:old=1:2   改成 -XX:NewRatio=4  -> Young:old=1:4
     *              9）-XX:MaxTenuringThreshold  设置垃圾的最大年龄
     */
    /**
     * 5. 强 软 弱 虚 引用
     *      1）Reference  强引用  即使内存溢出也不回收 死都不收  大部分的引用都是强引用
     *      2）SoftReference  软引用  内存足够的情况的GC不收，内存不够的时候就回收  常用于缓存
     *      3）WeakReference 弱引用 不管内存是否够用，一单GC就回收
     *          WeakHashMap 一旦GC K V都回收  清空map
     *      4）PhantomReference  虚引用  在任何时候都有可能别jvm回收，get()总是返回null 必须要和引用队列联合使用 ReferenceQueue，对象呗回收的时候会放到引用队列里面
     */
    /**
     * 6. 内存异常 -> error级别
     *      1） java.lang.StackOverflowError   栈溢出  ->  -Xss  原因，方法压入栈太多
     *      2） java.lang.OutOfMemoryError: Java heap space  堆内存溢出， -> -Xms,-Xmx  原因，new 很多对象，或者有大对象
     *      3） java.lang.OutOfMemoryError: GC overhead limit exceeded  原因：GC 时间过长：98%的时间用来做GC，但是回收的堆空间不到2% 就会抛出,   修改代码
     *      4） java.lang.OutOfMemoryError: Direct buffer memory  原因，看代码， ->  -XX:MaxDirectMemorySize
     *      5） java.lang.OutOfMemoryError: unable to create new native thread  创建的线程数达到上限
     *      6） java.lang.OutOfMemoryError: Metaspace  元空间被撑爆，加载了很多的类
     */
    /**
     * 7.GC 垃圾收集器
     *      1）垃圾回收器和垃圾回收算法的关系 -> 垃圾回收器是垃圾回收算法的落地实现
     *          1）垃圾回收器的分类：
     *              1）串行垃圾回收器（Serial）： 为单线程环境设计且只使用一个线程进行垃圾回收，会暂停所有的用户线程，不适合服务器环境
     *              2）并行垃圾回收器（Parallel）：多个垃圾回收线程并行工作，此时用户线程也是暂停的，适用于科学计数/大数据处理首台处理等交换能力弱的场景
     *              3）并发垃圾回收器（CMS）：用户线程和垃圾收集线程可以同时执行（不一定是并行，可能交替执行）不需要停顿用户线程，使用对相应时间有要求的场景，互联网多用这个垃圾收集器
     *              ------------前三种垃圾回收器都会发生STW（stop the Word） 暂停整个应用，时间可能会很长----------------------------------
     *              4）G1垃圾回收器（）：G1垃圾回收器将堆内存分割成不同的区域，然后并发的对其进行垃圾回收
     *              5）ZG1  JAVA 11以后
     *      2）怎样查看服务器默认的垃圾回收器是那个？ 如何配置？
     *              1） 查看 -> java -XX:PrintCommandLineFlags -version   会看到 -XX:+UseParallelGC(java 8 默认使用的)
     *              2） java的gc回收的类型主要有
     *                      1）UseSerialGC
     *                      2）UseParallelGC
     *                      3）UseConcMarkSweepGC
     *                      4）UseParNewGC
     *                      5）UseParallelOldGC
     *                      6）UseG1GC
     *                      7）UseSerialOldGC(被废弃)
     *              3）配置，作为参数开始 +UseConcMarkSweepGC
     *              4）
     *
     *      3）
     *      4）
     *      5）
     *
     */
    /**
     * 8.
     *
     *
     *
     */
}
