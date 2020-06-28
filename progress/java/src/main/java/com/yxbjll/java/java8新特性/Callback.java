package com.yxbjll.java.java8新特性;

/**
 * company: www.dtstack.com
 * author: toutian
 * create: 2018/1/25
 */

@FunctionalInterface
public interface Callback<E> {

    Object submit(E event);
}
