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
        javaHeapSpaceTest();
//        GCOverheadLimitExceeded();
//        directBufferMemory();
//        unableToCreateNewNativeThread();
//        metaspace();
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
     *      3）7大垃圾回收器概述
     *              1）参数描述（日志中会出现）
     *                      1）DefNew     -> Default New Generation
     *                      2）Tenured    -> Old
     *                      3）ParNew     -> Parallel New Generation
     *                      4）PSYoungGen -> Parallel Scavenge
     *                      5）ParOldGen  -> Parallel Old Generation
     *              2）Young区（新生代）
     *                      1）串行GC（Serial / Serial Copying） Client模式下新生区的默认垃圾收集器
     *                           配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC  （DefNew + Tenured）
     *                          开启后 Serial(Young区） + Serial Old（Old区）  表示新生代和老年代都使用串行垃圾收集器，新生代使用复制算法，老年代使用标记-整理算法
     *                      2）并行GC(ParNew) 其实是Serial垃圾收集器在新生代的并行多行程版本 很多java虚拟机运行在server模式下新生代的默认垃圾收集器
     *                          配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC  （ParNew + Tenured）
     *                          开启后 ParNew(Young区） + Serial Old（Old区）  新生代使用复制算法，老年代使用标记-整理算法
     *                          -XX:ParallelGCThreads  限制线程数量，默认开启和cpu数目相同的线程数
     *                      3）并行回收GC(Parallel / Parallel Scavenge) java 8 默认使用 新生代和老年代都是并行的 俗称吞吐量优先收集器 吞吐量 = 运行用户代码时间 /(运行用户代码时间 + GC时间)
     *                          配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC  （PSYoungGen + ParOldGen）
     *                          自适应调节策略：虚拟机会根据当前系统的运行情况收集性能监控信息，动态调节这些参数以提供合适的停顿时间和最大的吞吐量
     *                          开启后 PSYoungGen(Young区） + ParOldGen（Old区）  新生代使用复制算法，老年代使用标记-整理算法
     *
     *              3）Old区（老年代） 和新生代相互激活
     *                      1）串行GC（Serial Old / Serial MSC）
     *                           配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC  已经无法识别
     *                           了解即可，要废除了
     *
     *                      2）并行GC(Parallel Old / Parallel MSC)
     *                          配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC  （PSYoungGen + ParOldGen）
     *                      3）并发标记清除GC（CMS） 先标记处要回收的对象，在回收，
     *                          配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC  （ParNew + CMS）
     *                          开启后 ParNew(Young区） + ParOldGen（Old区）+Serial Old   Serial Old 将作为CMS出错的后备收集器新生代使用复制算法，老年代使用标记-清楚-整理算法
     *                          步骤：
     *                              1）初始标记（initial mark）：只是标记一下 GC Root能直接关联的对象，速度很快，但是要暂停所有的工程线程
     *                              2）并发标记（concurrent mark）：进行GC Root的跟踪过程，和用户线程一起执行，主要标记过程，标记所有对象
     *                              3）重新标记（remark）：修正并发标记期间，因用户线程继续运行而导致标记产生变动的那一部分的标记记录，要暂停所有的用户线程
     *                              4）并发清除（concurrent sweep）：清除GC Root不可达对象，和用户线程一起工作
     *                          缺点：1）有内存碎片，使用Serial Old 进行整理  -XX:CMSFullGCSBeForeCompaction (默认 0）多少次CMS之后进行Full GC
     *                               2）CMS必须在堆内存用完之前完成GC 否则会失败，出发担保机制，造成较大时间停顿
     *                          优点：并发收集低停顿
     *
     *              4）G1（garbage first） 垃圾收集器，是一款面向服务端应用的收集器
     *                      1）配置 -Xms10m -Xmx10m -XX：+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
     *                      2）上面 的垃圾收集器的特点
     *                              1）年轻代和老年代是各自独立的且连续的内存块
     *                              2）年轻代收集使用单Eden-s0-s1进行复制算法
     *                              3）老年代收集必须扫描整个来年代区域
     *                              4）都是以尽可能少而快速的执行GC为设计原则
     *                      3）设计的目标是取代CMS，和CMS相比：
     *                              1）G1是一个有整理内存过程的垃圾收集器，不会产生过度垃圾碎片
     *                              2）G1的STW（stop the Word）更可控，G1 在停顿时间上添加了预测机制，用户可以指定期望的停顿时间
     *                      4）主要改变是Eden，surviver和 tenured 内存区域不是连续的，而是变成一个个大小一样的region，每个region从1M - 32M 不等，每个region都有可能是Eden，surviver和 tenured
     *                      5）特点：
     *                              1）充分利用多cpu，多核环境的硬件优势，尽量缩短STW
     *                              2）整体上采用标记整理算法，局部是采用复制算法，不会产生内存碎片
     *                              3）宏观上G1不区分年轻代和老年代，把内存划分成多个独立的子区域（region），但是在小范围上还是区分年轻代和老年代，但是他们不是物理隔离的，
     *                                 而是一部分region的集合且不要求region是连续的，还是会用不同的GC方式来处理不同的区域
     *                              4）G1也是分代收集器，只是逻辑上的分代，每个region都有可能随着G1 的运行在不同的代切换
     *              5） G1底层原理
     *                      1） 总纲：化整为零，避免全内存扫描，只需要按照区域来扫描即可
     *                      2） -XX:G1HeapRegionSize=2(1-32 ,2的幂)， 默认划分2048个分区  -XX:MaxGCPauseMillis=n  z最大停顿时间，jvm会尽可能的停顿小于这个时间，单位毫秒
     *                      3） G1算法将region分为 Eden， survivor，old，humongous（大对象区）
     *                          这些region一部分包含新生代，新生代的垃圾收集依然采用暂停所有应用线程的方式，将存活对象拷贝到老年代或者survivor空间
     *                          这些region一部分包含老年代，G1收集器通过将对象从一个区域复制到另外一个区域，完成清理工作，完成了堆的压缩（部分堆），避免了CMS内存碎片的产生
     *                          在G1中有一种特殊的区域，Humongous（巨大的）区，如果一个对象占用的空间超过了分区容量的50%，这些对象就会分配到老年区
     *                      4）回收步骤，和 CMS 差不多
     *                              1）初始标记 ：只标记GC Roots 能直接关联到的对象
     *                              2）并发标记 ： 进行GC Roots Tracing（追踪）的过程
     *                              3）最终标记：修正并发标记期间，因程序导运行导致的变化的对象
     *                              4）筛选回收：根据时间来进行价值最大化的回收
     *                              5）Eden区：Eden区耗尽后会被触发们主要是 小区域收集+形成连续的内存块避免内存碎片
     *                                      1） Eden 区的数据移动到 survivor 区，如果 survivor 空间不够，Eden 区数据部分会晋升到 old区
     *                                      2） survivor 区区数据移动到新的 survivor 区，部分数据会晋升到old区
     *                                      3）最后Eden区收拾干净，GC结束
     *                      5）和 CMS 比较
     *                              1） G1 没有内存碎片
     *                              2） 可以控制停顿时间，
     *
     */
    /**
     * 8. undertow 技术
     *
     *
     *
     */
    /**
     * 9. 服务变慢，诊断
     *      1） 整机 ：top -> 1）可以看程序的负载； 2） load average  或者 uptime； 3）按1
     *      2） CPU ：vmstat -> vmstat -n 2 3   每2秒采集一次，采集3次
     *                  r： 运行和等待CPU时间片的进程数，原则上不要超过总核数的2倍，否则压力大
     *                  b： 阻塞的进程数，比如等待磁盘IO，网络IO
     *                  cpu->us：用户进程消耗CPU时间的百分比，长期大约50%，应该优化
     *               mpstat -p All 2  没2秒打印一次每个cup的使用情况
     *               pidstat -u 1 -p 进程编号 PID  没1秒 查看某个pid程序占用CPU的情况
     *      3） 内存 ：free -> free -m  按照兆B显示内存的使用情况
     *                        pidstat -p 进程编号（PID） -r 2  没2秒打印一次pid占用内存的情况
     *      4） 硬盘 ：df -> df -h
     *      5） 磁盘IO ：iostat -> iostat -xdk 2 3
     *                  pidstat -d 采样间隔 -p 进程编号（PID）
     *      6） 网络IO ：ifstat ->
     *      7）
     *
     *
     *
     */
}
