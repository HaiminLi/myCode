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
        long l = Runtime.getRuntime().totalMemory();//����java��������ڴ�����
        long l1 = Runtime.getRuntime().maxMemory();//����java�������ͼʹ�õ�����ڴ�
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
        //-XX��MetaspaceSize=128M -XX��MaxMetaspaceSize=128M
        /*
         * Ԫ�ռ��ŵ������У�
         * 1.��������ص�����Ϣ
         * 2.������
         * 3.��̬����
         * 4.��ʱ�����Ĵ���
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
         * ���쳣 �߲��������������ʱ�򣬾������֣� native thread �쳣�Ͷ�Ӧ��ƽ̨�й�
         *
         * ԭ��
         *      1.һ��Ӧ�ý��̴�������̣߳�����ϵͳ���ؼ���
         *      2.���������������Ӧ�ó��򴴽���ô����̣߳�LinuxĬ����1024���߳���
         * ����취��
         *      1.��취�������Ӧ�ó��򴴽����߳���
         *      2.���Ӧ��ȷʵ��Ҫ��ô����̣߳����Ը���Linux��Ĭ�������߳���
         *          Linux  root�û�����һֱnew �̣߳�������
         *          1���鿴�û����߳����ߣ�ulimit -u
         *          2���޸� vim /etc/security/limits.d/90-nproc.conf
         */
        for (int i = 1; ; i++){
            System.out.println("�߳���" + i);
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
        /* ��Ҫ��NIO���µ�
         * ԭ�� дNIO���򾭳�ʹ��ByteBuffer����ȡ����д�����ݣ�����һ�ֻ���ͨ����channel���ͻ�������buffer����IO��ʽ
         * ������ʹ��native������ֱ�ӷ�������ڴ棬Ȼ��ͨ��һ���洢�ڶ������ DirectByteBuffer ������Ϊ����ڴ�����ý��в���
         * ������һЩ�����п���������ܣ���Ϊ��������java�Ѻ�native�������ظ�������
         *
         * ByteBuffer.allocate��capability�� ����jvm���ڴ棬����GC��Ͻ��Χ��������Ҫ������˵�����ܽϵ�
         * ByteBuffer.allocateDirect��capability�� �������ϵͳ�����ڴ棬������GC��Ͻ��Χ�����ڲ���Ҫ�ڴ濽���������ٶ���ԽϿ�
         *
         * ��� ���Ϸ��䱾���ڴ棬���ڴ����ʹ�ã���ôjvm�Ͳ���Ҫִ��GC DirectByteBuffer ����Ͳ������
         * ���ʱ����ڴ���㣬���Ǳ����ڴ��Ѿ�ʹ�����ˣ��ٴγ��Է��䱾���ڴ��ʱ��ͻ��� OutOfMemoryError ���ǳ������
         */
        // -XX:MaxDirectMemorySize=5M
        System.out.println("���õ�maxDirectMemory "+sun.misc.VM.maxDirectMemory()/(double)1024/1024 + "M");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(40 * 1024 * 1024);
    }

    private static void GCOverheadLimitExceeded() {
        //GC ʱ�������98%��ʱ��������GC�����ǻ��յĶѿռ䲻��2% �ͻ��׳�
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
     * JVM ���
     * 1. ���������㷨
     *      1�����ü���
     *      2������                ->�����
     *      3��������            ->�����
     *      4������������        ->�����
     */
    /**
     * 2. ���ȷ��������
     *      �ڴ����Ѿ�ȷ������ʹ�õĿռ伴Ϊ����
     * 3. ����ж�һ�������Ƿ���Ի��գ�
     *      1�� ���ü����� ��һ���ط������� +1��û������ -1��  ���ѽ��ѭ���������õ����⣩
     *      2�� ö�ٸ��ڵ����ɴ��Է�����������·����  GC roots   GC root��һ������Ծ������
     *              ��Щ���������ΪGC roots��
     *                  1�������ջ�����õĶ���
     *                  2���������е��ྲ̬�������õĶ���
     *                  3���������г������õĶ���
     *                  4�����ط���ջ��JNI��Native���������õĶ���
     */
    /**
     * 4. JVM����
     *      1������̵�鿴JVMϵͳĬ��ֵ��
     *          1��jps -l  �鿴��ǰ���е�java����Ľ��̺�
     *          2��jinfo -flag Ҫ�鿴�Ĳ������磺PrintGCDetails�� ��һ����Ľ��̺�   ->�鿴��ǰ��ֵ�������Ƿ���
     *             jinfo -flags  ��һ����Ľ��̺�  ->�鿴��ǰjvm���в�����ֵ�������Ƿ���
     *      2��JVM�������ͷ��ࣺ
     *          1�����������  -version��  -help��  ÿ���汾��������Ĳ���
     *          2����X����  -Xint������ִ�У�   -Xcomp����һ��˽�оͱ���ɱ��ش��룩  -Xmixed�����ģʽ��   ʵ����java -Xint -version
     *          3����XX����
     *              1��Boolean����
     *                  1�� ��ʽ -XX��+��-�� ����  �� +���� -�ر�
     *                  2�� -XX��+PrintGCDetails  �Ƿ��ӡGC �ռ�ϸ��
     *                  3�� -XX:+UseSerialDC   �Ƿ�ʹ�ô�������������
     *              2��kv��ֵ����
     *              1�� ��ʽ -XX������key=����ֵvalue
     *              2�� ����Ԫ�ռ��С�� -XX��MetaspaceSize=128M
     *              3�� ��ֵ�����������old��: -XX��MaxTenuringThreshold=15
     *              4) ���ڴ��ʼֵ: -Xms1024m   �ȼ��� -XX:InitialHeapSize=1024m
     *              5) ���ڴ����ֵ: -Xmx1024m   �ȼ��� -XX:MaxHeapSize=1024m
     *       3)�̵�ҵײ鿴JVMĬ��ֵ
     *            1��-XX:+PrintFlagsInitial  �鿴JVM��ʼĬ��ֵ
     *            2��-XX:+PrintFlagsFinal   �鿴JVM�޸�֮���Ĭ��ֵ  �У�=�����и��ĵĲ���
     *                  ����java��ͬʱҲ��ӡ������   java -XX:+PrintFlagsFinal -version
     *            3��-XX:+PrintCommandLineFlags  ��ӡ�����в��� ���Բ鿴ʹ�����Ǹ�����������
     *       4��JVM���ò���
     *              1��-Xms   �ȼ��� -XX:InitialHeapSize   Ĭ�������ڴ�� 1/64
     *              2��-Xmx   �ȼ��� -XX:MaxHeapSize       Ĭ�������ڴ�� 1/4
     *              3��-Xss   �ȼ��� -XX:ThreadStackSize   һ��Ĭ��502K ~ 1024K  �����е�ƽ̨�й�ϵ  Linux = 1024K
     *              4��-Xmn   �ȼ��� -XX:NewSize ����������Ĵ�С
     *              5��-XX:MetaspaceSize   ����Ԫ�ռ��С   Ԫ�ռ䲻ͬ�����ô���java7֮ǰ��  Ԫ�ռ��ڱ��������ڴ棬��С�ܱ����ڴ��С������  ��������21M����
     *              6��-XX��+PrintGCDetails
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
                            [GC����  [ʲô�� GCǰʹ�ô�С -> GC��ʹ�õĴ�С���ܴ�С��] jvm��gcǰʹ�ô�С -> GC��ʹ�õĴ�С ��JVM�ܴ�С��  gc��ʱ]  [�û���ʱ��ϵͳ��ʱ��ʵ�ʺ�ʱ]
                            GC��Young�ģ�  Full GC Old��
     *              7��-XX:SurvivorRatio   ������������Eden��S0/S1�ռ���� Ĭ�� -XX:SurvivorRatio=8  -> Eden:S0:S1=8:1:1   �ĳ�-XX:SurvivorRatio=4  -> Eden:S0:S1=4:1:1
     *              8��-XX:NewRatio        ��������������ϴ� ռ�ѵı���     Ĭ�� -XX:NewRatio=2  -> Young:old=1:2   �ĳ� -XX:NewRatio=4  -> Young:old=1:4
     *              9��-XX:MaxTenuringThreshold  �����������������
     */
    /**
     * 5. ǿ �� �� �� ����
     *      1��Reference  ǿ����  ��ʹ�ڴ����Ҳ������ ��������  �󲿷ֵ����ö���ǿ����
     *      2��SoftReference  ������  �ڴ��㹻�������GC���գ��ڴ治����ʱ��ͻ���  �����ڻ���
     *      3��WeakReference ������ �����ڴ��Ƿ��ã�һ��GC�ͻ���
     *          WeakHashMap һ��GC K V������  ���map
     *      4��PhantomReference  ������  ���κ�ʱ���п��ܱ�jvm���գ�get()���Ƿ���null ����Ҫ�����ö�������ʹ�� ReferenceQueue�������»��յ�ʱ���ŵ����ö�������
     */
    /**
     * 6. �ڴ��쳣 -> error����
     *      1�� java.lang.StackOverflowError   ջ���  ->  -Xss  ԭ�򣬷���ѹ��ջ̫��
     *      2�� java.lang.OutOfMemoryError: Java heap space  ���ڴ������ -> -Xms,-Xmx  ԭ��new �ܶ���󣬻����д����
     *      3�� java.lang.OutOfMemoryError: GC overhead limit exceeded  ԭ��GC ʱ�������98%��ʱ��������GC�����ǻ��յĶѿռ䲻��2% �ͻ��׳�,   �޸Ĵ���
     *      4�� java.lang.OutOfMemoryError: Direct buffer memory  ԭ�򣬿����룬 ->  -XX:MaxDirectMemorySize
     *      5�� java.lang.OutOfMemoryError: unable to create new native thread  �������߳����ﵽ����
     *      6�� java.lang.OutOfMemoryError: Metaspace  Ԫ�ռ䱻�ű��������˺ܶ����
     */
    /**
     * 7.GC �����ռ���
     *      1�����������������������㷨�Ĺ�ϵ -> ���������������������㷨�����ʵ��
     *          1�������������ķ��ࣺ
     *              1������������������Serial���� Ϊ���̻߳��������ֻʹ��һ���߳̽����������գ�����ͣ���е��û��̣߳����ʺϷ���������
     *              2������������������Parallel����������������̲߳��й�������ʱ�û��߳�Ҳ����ͣ�ģ������ڿ�ѧ����/�����ݴ�����̨����Ƚ����������ĳ���
     *              3������������������CMS�����û��̺߳������ռ��߳̿���ͬʱִ�У���һ���ǲ��У����ܽ���ִ�У�����Ҫͣ���û��̣߳�ʹ�ö���Ӧʱ����Ҫ��ĳ�����������������������ռ���
     *              ------------ǰ�����������������ᷢ��STW��stop the Word�� ��ͣ����Ӧ�ã�ʱ����ܻ�ܳ�----------------------------------
     *              4��G1����������������G1���������������ڴ�ָ�ɲ�ͬ������Ȼ�󲢷��Ķ��������������
     *              5��ZG1  JAVA 11�Ժ�
     *      2�������鿴������Ĭ�ϵ��������������Ǹ��� ������ã�
     *              1�� �鿴 -> java -XX:PrintCommandLineFlags -version   �ῴ�� -XX:+UseParallelGC(java 8 Ĭ��ʹ�õ�)
     *              2�� java��gc���յ�������Ҫ��
     *                      1��UseSerialGC
     *                      2��UseParallelGC
     *                      3��UseConcMarkSweepGC
     *                      4��UseParNewGC
     *                      5��UseParallelOldGC
     *                      6��UseG1GC
     *                      7��UseSerialOldGC(������)
     *              3�����ã���Ϊ������ʼ +UseConcMarkSweepGC
     *      3��7����������������
     *              1��������������־�л���֣�
     *                      1��DefNew     -> Default New Generation
     *                      2��Tenured    -> Old
     *                      3��ParNew     -> Parallel New Generation
     *                      4��PSYoungGen -> Parallel Scavenge
     *                      5��ParOldGen  -> Parallel Old Generation
     *              2��Young������������
     *                      1������GC��Serial / Serial Copying�� Clientģʽ����������Ĭ�������ռ���
     *                           ���� -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC  ��DefNew + Tenured��
     *                          ������ Serial(Young���� + Serial Old��Old����  ��ʾ���������������ʹ�ô��������ռ�����������ʹ�ø����㷨�������ʹ�ñ��-�����㷨
     *                      2������GC(ParNew) ��ʵ��Serial�����ռ������������Ĳ��ж��г̰汾 �ܶ�java�����������serverģʽ����������Ĭ�������ռ���
     *                          ���� -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC  ��ParNew + Tenured��
     *                          ������ ParNew(Young���� + Serial Old��Old����  ������ʹ�ø����㷨�������ʹ�ñ��-�����㷨
     *                          -XX:ParallelGCThreads  �����߳�������Ĭ�Ͽ�����cpu��Ŀ��ͬ���߳���
     *                      3�����л���GC(Parallel / Parallel Scavenge) java 8 Ĭ��ʹ�� ����������������ǲ��е� �׳������������ռ��� ������ = �����û�����ʱ�� /(�����û�����ʱ�� + GCʱ��)
     *                          ���� -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC  ��PSYoungGen + ParOldGen��
     *                          ����Ӧ���ڲ��ԣ����������ݵ�ǰϵͳ����������ռ����ܼ����Ϣ����̬������Щ�������ṩ���ʵ�ͣ��ʱ�������������
     *                          ������ PSYoungGen(Young���� + ParOldGen��Old����  ������ʹ�ø����㷨�������ʹ�ñ��-�����㷨
     *
     *              3��Old����������� ���������໥����
     *                      1������GC��Serial Old / Serial MSC��
     *                           ���� -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC  �Ѿ��޷�ʶ��
     *                           �˽⼴�ɣ�Ҫ�ϳ���
     *
     *                      2������GC(Parallel Old / Parallel MSC)
     *                          ���� -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC  ��PSYoungGen + ParOldGen��
     *                      3������������GC��CMS�� �ȱ�Ǵ�Ҫ���յĶ����ڻ��գ�
     *                          ���� -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC  ��ParNew + CMS��
     *                          ������ ParNew(Young���� + ParOldGen��Old����+Serial Old   Serial Old ����ΪCMS����ĺ��ռ���������ʹ�ø����㷨�������ʹ�ñ��-���-�����㷨
     *                          ���裺
     *                              1����ʼ��ǣ�initial mark����ֻ�Ǳ��һ�� GC Root��ֱ�ӹ����Ķ����ٶȺܿ죬����Ҫ��ͣ���еĹ����߳�
     *                              2��������ǣ�concurrent mark��������GC Root�ĸ��ٹ��̣����û��߳�һ��ִ�У���Ҫ��ǹ��̣�������ж���
     *                              3�����±�ǣ�remark����������������ڼ䣬���û��̼߳������ж����±�ǲ����䶯����һ���ֵı�Ǽ�¼��Ҫ��ͣ���е��û��߳�
     *                              4�����������concurrent sweep�������GC Root���ɴ���󣬺��û��߳�һ����
     *                          ȱ�㣺1�����ڴ���Ƭ��ʹ��Serial Old ��������  -XX:CMSFullGCSBeForeCompaction (Ĭ�� 0�����ٴ�CMS֮�����Full GC
     *                               2��CMS�����ڶ��ڴ�����֮ǰ���GC �����ʧ�ܣ������������ƣ���ɽϴ�ʱ��ͣ��
     *                          �ŵ㣺�����ռ���ͣ��
     *
     *              4��G1��garbage first�� �����ռ�������һ����������Ӧ�õ��ռ���
     *                      1������ -Xms10m -Xmx10m -XX��+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
     *                      2������ �������ռ������ص�
     *                              1���������������Ǹ��Զ��������������ڴ��
     *                              2��������ռ�ʹ�õ�Eden-s0-s1���и����㷨
     *                              3��������ռ�����ɨ���������������
     *                              4�������Ծ������ٶ����ٵ�ִ��GCΪ���ԭ��
     *                      3����Ƶ�Ŀ����ȡ��CMS����CMS��ȣ�
     *                              1��G1��һ���������ڴ���̵������ռ����������������������Ƭ
     *                              2��G1��STW��stop the Word�����ɿأ�G1 ��ͣ��ʱ���������Ԥ����ƣ��û�����ָ��������ͣ��ʱ��
     *                      4����Ҫ�ı���Eden��surviver�� tenured �ڴ������������ģ����Ǳ��һ������Сһ����region��ÿ��region��1M - 32M ���ȣ�ÿ��region���п�����Eden��surviver�� tenured
     *                      5���ص㣺
     *                              1��������ö�cpu����˻�����Ӳ�����ƣ���������STW
     *                              2�������ϲ��ñ�������㷨���ֲ��ǲ��ø����㷨����������ڴ���Ƭ
     *                              3�������G1�����������������������ڴ滮�ֳɶ��������������region����������С��Χ�ϻ����������������������������ǲ����������ģ�
     *                                 ����һ����region�ļ����Ҳ�Ҫ��region�������ģ����ǻ��ò�ͬ��GC��ʽ������ͬ������
     *                              4��G1Ҳ�Ƿִ��ռ�����ֻ���߼��ϵķִ���ÿ��region���п�������G1 �������ڲ�ͬ�Ĵ��л�
     *              5�� G1�ײ�ԭ��
     *                      1�� �ܸ٣�����Ϊ�㣬����ȫ�ڴ�ɨ�裬ֻ��Ҫ����������ɨ�輴��
     *                      2�� -XX:G1HeapRegionSize=2(1-32 ,2����)�� Ĭ�ϻ���2048������  -XX:MaxGCPauseMillis=n  z���ͣ��ʱ�䣬jvm�ᾡ���ܵ�ͣ��С�����ʱ�䣬��λ����
     *                      3�� G1�㷨��region��Ϊ Eden�� survivor��old��humongous�����������
     *                          ��Щregionһ���ְ������������������������ռ���Ȼ������ͣ����Ӧ���̵߳ķ�ʽ���������󿽱������������survivor�ռ�
     *                          ��Щregionһ���ְ����������G1�ռ���ͨ���������һ�������Ƶ�����һ���������������������˶ѵ�ѹ�������ֶѣ���������CMS�ڴ���Ƭ�Ĳ���
     *                          ��G1����һ�����������Humongous���޴�ģ��������һ������ռ�õĿռ䳬���˷���������50%����Щ����ͻ���䵽������
     *                      4�����ղ��裬�� CMS ���
     *                              1����ʼ��� ��ֻ���GC Roots ��ֱ�ӹ������Ķ���
     *                              2��������� �� ����GC Roots Tracing��׷�٣��Ĺ���
     *                              3�����ձ�ǣ�������������ڼ䣬��������е��µı仯�Ķ���
     *                              4��ɸѡ���գ�����ʱ�������м�ֵ��󻯵Ļ���
     *                              5��Eden����Eden���ľ���ᱻ��������Ҫ�� С�����ռ�+�γ��������ڴ������ڴ���Ƭ
     *                                      1�� Eden ���������ƶ��� survivor ������� survivor �ռ䲻����Eden �����ݲ��ֻ������ old��
     *                                      2�� survivor ���������ƶ����µ� survivor �����������ݻ������old��
     *                                      3�����Eden����ʰ�ɾ���GC����
     *                      5���� CMS �Ƚ�
     *                              1�� G1 û���ڴ���Ƭ
     *                              2�� ���Կ���ͣ��ʱ�䣬
     *
     */
    /**
     * 8. undertow ����
     *
     *
     *
     */
    /**
     * 9. ������������
     *      1�� ���� ��top -> 1�����Կ�����ĸ��أ� 2�� load average  ���� uptime�� 3����1
     *      2�� CPU ��vmstat -> vmstat -n 2 3   ÿ2��ɼ�һ�Σ��ɼ�3��
     *                  r�� ���к͵ȴ�CPUʱ��Ƭ�Ľ�������ԭ���ϲ�Ҫ�����ܺ�����2��������ѹ����
     *                  b�� �����Ľ�����������ȴ�����IO������IO
     *                  cpu->us���û���������CPUʱ��İٷֱȣ����ڴ�Լ50%��Ӧ���Ż�
     *               mpstat -p All 2  û2���ӡһ��ÿ��cup��ʹ�����
     *               pidstat -u 1 -p ���̱�� PID  û1�� �鿴ĳ��pid����ռ��CPU�����
     *      3�� �ڴ� ��free -> free -m  ������B��ʾ�ڴ��ʹ�����
     *                        pidstat -p ���̱�ţ�PID�� -r 2  û2���ӡһ��pidռ���ڴ�����
     *      4�� Ӳ�� ��df -> df -h
     *      5�� ����IO ��iostat -> iostat -xdk 2 3
     *                  pidstat -d ������� -p ���̱�ţ�PID��
     *      6�� ����IO ��ifstat ->
     *      7��
     *
     *
     *
     */
}
