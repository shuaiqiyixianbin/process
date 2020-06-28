package com.yxbjll.java.collection;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author yxbjll
 * @desc CollectionTest
 * @since 2020/3/10
 */
public class CollectionTest {
	public static void main(String[] args) {
		Set set = new LinkedHashSet();
		set.add(1L);
		set.add("1");
		set.add(1);
		set.add(1L);
		System.out.println(set.toString());
	}
}
