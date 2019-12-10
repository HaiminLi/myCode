package com.haimin.code.base;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Haimin Li
 * @description: java 规约（阿里巴巴开发手册发现的，未曾意识到的规约）
 * @date 2019/12/9  15:51
 */
public class JavaConventionality {

    public static void main(String[] args) {
//        integerTest();
//        floatTest();
        doubleTest();
        Instant instant;
    }

    private static void doubleTest() {
        /*
         *为了防止精度损失，禁止使用构造方法 BigDecimal(double)的方式把 double 值转
         * 化为 BigDecimal 对象。
         * 说明：BigDecimal(double)存在精度损失风险，在精确计算或值比较的场景中可能会导致业务逻辑异常。
         * 如：BigDecimal g = new BigDecimal(0.1f); 实际的存储值为：0.10000000149
         *
         */
        double  d = 0.1D;
        BigDecimal recommend1 = new BigDecimal("0.1");//0.1
        BigDecimal recommend2 = BigDecimal.valueOf(0.1);//0.1
        BigDecimal recommend3 = new BigDecimal(d);//0.1000000000000000055511151231257827021181583404541015625
        System.out.println(recommend1);
        System.out.println(recommend2);
        System.out.println(recommend3);
    }

    private static void floatTest() {
        /*
         *浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型不能用equals 来判断 转换BigDecimal后进行比较。
         *说明：浮点数采用“尾数+阶码”的编码方式，类似于科学计数法的“有效数字+指数”的表示方式
         * 二进制无法精确表示大部分的十进制小数
         *
         */
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        float diff = 1e-6f;

        boolean boo1 = a == b;
        boolean boo11 = Math.abs(a - b) < diff;
        if (boo1) {
            // 预期进入此代码快，执行其它业务逻辑
            // 但事实上 a==b 的结果为 false
        }
        Float x = Float.valueOf(a);
        Float y = Float.valueOf(b);
        BigDecimal a1 = new BigDecimal("1.0");
        BigDecimal b1 = new BigDecimal("0.9");
        BigDecimal c = new BigDecimal("0.8");
        BigDecimal x1 = a1.subtract(b1);
        BigDecimal y1 = b1.subtract(c);
        boolean boo2 = x.equals(y);
        boolean boo22 = x1.equals(y1);
        if (x.equals(y)) {
            // 预期进入此代码快，执行其它业务逻辑
            // 但事实上 equals 的结果为 false
        }
        System.out.println(boo1);//false
        System.out.println(boo11);//true
        System.out.println(boo2);//false
        System.out.println(boo22);//true
        System.out.println(x.floatValue() == y.floatValue());//false
    }

    private static void integerTest() {
        /*
         *对于 Integer var = ? 在-128 至 127 范围内的赋值，Integer 对象是在 IntegerCache.cache 产
         * 生，会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数
         * 据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 equals 方法进行判断。
         */
        int var1 = 1;
        int var2 = 1;
        int var7 = 500;
        int var8 = 500;
        Integer var3 = 1;
        Integer var4 = 1;
        Integer var5 = 500;
        Integer var6 = 500;
        Integer var9 = new Integer(1);
        Integer var10 = new Integer(1);
        Integer var11 = new Integer(500);
        Integer var12 = new Integer(500);
        System.out.println(var1 == var2);//true
        System.out.println(var7 == var8);//true
        System.out.println(var3 == var4);//true
        System.out.println(var3.equals(var4));//true
        System.out.println(var5 == var6);//false 超过-128 至 127的范围
        System.out.println(var5.equals(var6));//true
        System.out.println(var9 == var10);//false  new出来的对象，
        System.out.println(var9.equals(var10));//true
        System.out.println(var11 == var12);//false
        System.out.println(var11.equals(var12));//true
    }
}
