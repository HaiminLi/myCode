package com.haimin.code.java8.lambda;

/**
 * @author Haimin Li
 * @description:
 * @date 2019/12/9  14:32
 */
@FunctionalInterface
public interface FunctionInterfaceDemo<A, B> {

    B get(A a);
}
