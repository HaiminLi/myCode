package com.haimin.code.java8.annotation;

import com.haimin.code.java8.interfaces.Person;

import java.lang.reflect.Method;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/11  11:38
 */
public class TestAnnotation {
    public static void main(String[] args) {
        Class<Person> testAnnotationClass = Person.class;
        Method method = null;
        try {
            Method[] methods = testAnnotationClass.getMethods();
            method = testAnnotationClass.getMethod("test1");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MyAnnotation[] annotationsByType = method.getAnnotationsByType(MyAnnotation.class);
        for (int i = 0; i < annotationsByType.length; i++){
            MyAnnotation myAnnotation = annotationsByType[i];
            System.out.println(myAnnotation.value());
        }

    }

    @MyAnnotation
    @MyAnnotation("这是方法上面的值")
    private void test1(){
    }
}
