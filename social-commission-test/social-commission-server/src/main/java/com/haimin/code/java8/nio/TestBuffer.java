package com.haimin.code.java8.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  15:21
 */
public class TestBuffer {
    /*
     * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
     * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
     * ByteBuffer
     * CharBuffer
     * ShortBuffer
     * IntBuffer
     * LongBuffer
     * FloatBuffer
     * DoubleBuffer
     * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
     *
     * 二、缓冲区存取数据的两个核心方法：
     * put() : 存入数据到缓冲区中
     * get() : 获取缓冲区中的数据
     *
     * 三、缓冲区中的四个核心属性：
     * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
     * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
     * position : 位置，表示缓冲区中正在操作数据的位置。
     * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
     * 0 <= mark <= position <= limit <= capacity
     *
     * 四、直接缓冲区与非直接缓冲区：
     * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
     * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
     */
    public static void main(String[] args) {
        testBuffer();
        testMark();
    }
    private static void testMark(){
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println(buf.position());
        //mark() : 标记
        buf.mark();
        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());
        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println(buf.position());
        //判断缓冲区中是否还有剩余数据
        if(buf.hasRemaining()){
            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }

    private static void testBuffer() {
        //createBuffer();
        String str = "abcde";
        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-----------------allocate()----------------");
        System.out.println("position = " + buf.position());
        System.out.println("limit = " + buf.limit());
        System.out.println("capacity = " + buf.capacity());
        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());
        System.out.println("-----------------put()----------------");
        System.out.println("position = " + buf.position());
        System.out.println("limit = " + buf.limit());
        System.out.println("capacity = " + buf.capacity());
        //3. 切换读取数据模式
        buf.flip();
        System.out.println("-----------------flip()----------------");
        System.out.println("position = " + buf.position());
        System.out.println("limit = " + buf.limit());
        System.out.println("capacity = " + buf.capacity());
        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("-----------------get()----------------");
        System.out.println("position = " + buf.position());
        System.out.println("limit = " + buf.limit());
        System.out.println("capacity = " + buf.capacity());
        //5. rewind() : 可重复读
        buf.rewind();
        System.out.println("-----------------rewind()----------------");
        System.out.println("position = " + buf.position());
        System.out.println("limit = " + buf.limit());
        System.out.println("capacity = " + buf.capacity());
        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();
        System.out.println("-----------------clear()----------------");
        System.out.println("position = " + buf.position());
        System.out.println("limit = " + buf.limit());
        System.out.println("capacity = " + buf.capacity());
        System.out.println((char)buf.get());
    }

    private static void createBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        ShortBuffer shortBuffer = ShortBuffer.allocate(1024);
        IntBuffer intBuffer = IntBuffer.allocate(1024);
        LongBuffer longBuffer = LongBuffer.allocate(1024);
        FloatBuffer floatBuffer = FloatBuffer.allocate(1024);
        DoubleBuffer doubleBuffer = DoubleBuffer.allocate(1024);
    }
}
