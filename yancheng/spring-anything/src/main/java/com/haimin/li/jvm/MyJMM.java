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

        List<String> list = new ArrayList<>();  //�̲߳���ȫ
        List<String> list1 = Collections.synchronizedList(new ArrayList<>());
        List<String> list2 = new CopyOnWriteArrayList<>();

        for (int i = 1; i <=30; i++){
            new Thread(() ->{
                list2.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(Thread.currentThread().getName() + "  ��С  > "+ list2.size());
            }, String.valueOf(i)).start();
        }

    }

    /**
     * 1. JMM ���壺JMM�������̺߳����ڴ�֮��ĳ����ϵ���߳�֮��Ĺ�������洢�����ڴ棨Main Memory���У�
     * ÿ���̶߳���һ��˽�еı����ڴ棨Local Memory���������ڴ��д洢�˸��߳��Զ�/д��������ĸ���
     *      1�� �ɼ��� �� �ɼ�����ָ�ڶ��߳�����£���һ���߳��޸���ĳһ�����������ֵ֮�������߳��Ƿ��ܹ�����֪������޸�
     *      2�� ԭ���� �� ԭ������ָһ�������ǲ����жϵģ���ʹ���ڶ���߳�һ��ִ�е�����£�һ������һ����ʼִ�У��Ͳ����ܵ������̵߳ĸ���
     *      3�� ������ �� ��Ϊ�������Ż���Ե�ʣ�������ָ�����ŵĲ��������ź��ָ����ԭָ���˳��δ��һ��
     */
    /**
     * 2. volatile �ص㣺 java������ṩ����������ͬ������
     *      1�� �ɼ��� �� ��֤
     *      2�� ԭ���� �� ����֤  ���ǿ�����ԭ������
     *      3�� ������ �� ��ָֹ������
     *
     *  ����ģʽ�� volatile ��
     */
    /**
     * 3. CAS -> Compare And Set  ��һ��CPU����ԭ����������ԭ�ӵģ����ᱻ���
     *      1) ԭ�� ������ -> UnSafe�࣬ ����
     *      2) ȱ�㣺
     *              1�� ѭ��ʱ�䳤�����������CASʧ�ܣ���һֱ���г��ԣ������������һֱ���ɹ������CPU�����ܴ�Ŀ���
     *              2�� ֻ�ܱ�֤һ�����������ԭ�Ӳ��� �� �Զ���������������ʱ��CAS�޷���֤������ԭ���ԣ����ʱ�������������֤������ԭ����
     *              3�� ABA ����
     *                      1�� ���� �������󣬻�ȡ�����ִ��CAS����ǰ���������߳��޸ĺ������޸�Ϊԭ���Ķ���ֵ������CAS���������̵߳��޸ģ��ɹ�ִ��CAS�����޸�
     *                      2�� ԭ������ �� AtomicReference<>
     *                      3�� ����취 �� ��ʱ�����ԭ������  -> AtomicStampedReference
     *
     */
    /**
     * 4. �����಻��ȫ������
     *      1�� �����಻��ȫ֮ �����޸��쳣  java.util.ConcurrentModificationException��  ����߳��޸� ArrayList
     *      2�� ����ԭ�� �� ����߳��޸� ArrayList��һ���߳������޸ģ���һ���߳��������޸�
     *      3�� ������� ��
     *              1) new Vector<>()
     *              2) Collections.synchronizedList(new ArrayList<>())
     *              3) new CopyOnWriteArrayList<>()
     *               ע �� CopyOnWriteArrayList��Collections.synchronizedList��ʵ���̰߳�ȫ���б�����ַ�ʽ��
     *               ����ʵ�ַ�ʽ�ֱ���Բ�ͬ����в�ͬ�����ܱ��֣�
     *               ����CopyOnWriteArrayList��д�������ܽϲ�����̵߳Ķ��������ܽϺá�
     *               ��Collections.synchronizedList��д�������ܱ�CopyOnWriteArrayList�ڶ��̲߳����������Ҫ�úܶ࣬����������Ϊ�ǲ�����synchronized�ؼ��ֵķ�ʽ��
     *               ����������ܲ�����CopyOnWriteArrayList������ڲ�ͬ��Ӧ�ó����£�Ӧ��ѡ��ͬ�Ķ��̰߳�ȫʵ����
     *      4�� �Ż����� ��
     *      5�� Դ�� �� CopyOnWriteArrayList ��lock����
     *      CopyOnWriteArraySet �ײ㻹�� CopyOnWriteArrayList
     *      ConcurrentHashMap
     *
     */
    /**
     * 5.
     *
     */
}
q