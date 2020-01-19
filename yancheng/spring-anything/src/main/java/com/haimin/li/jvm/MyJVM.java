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
//        javaHeapSpaceTest();
//        GCOverheadLimitExceeded();
//        directBufferMemory();
//        unableToCreateNewNativeThread();
        metaspace();
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
     *              4��
     *
     *      3��
     *      4��
     *      5��
     *
     */
    /**
     * 8.
     *
     *
     *
     */
}
