package com.haimin.code.java8.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  11:38
 */
@Repeatable(MyAnnotations.class)
@Target(value = {TYPE, METHOD, FIELD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, TYPE_PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
public @interface MyAnnotation {
    String value() default "默认值";
}
/*
 * java 源注解
 * @Target 用于描述注解的范围，即注解在哪用
    CONSTRUCTOR:用于描述构造器
　　FIELD:用于描述域即类成员变量
　　LOCAL_VARIABLE:用于描述局部变量
　　METHOD:用于描述方法
　　PACKAGE:用于描述包
　　PARAMETER:用于描述参数
　　TYPE:用于描述类、接口(包括注解类型) 或enum声明
　　TYPE_PARAMETER:1.8版本开始，描述类、接口或enum参数的声明
　　TYPE_USE:1.8版本开始，描述一种类、接口或enum的使用声明
*
 * @Retention 用于描述注解的生命周期，表示需要在什么级别保存该注解，即保留的时间长短。取值类型（RetentionPolicy）有以下几种：
    SOURCE:在源文件中有效（即源文件保留）
　　CLASS:在class文件中有效（即class保留）
　　RUNTIME:在运行时有效（即运行时保留）
*
 * @Documented 用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。它是一个标记注解，没有成员。
 *
 * @Inherited 用于表示某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。
 */