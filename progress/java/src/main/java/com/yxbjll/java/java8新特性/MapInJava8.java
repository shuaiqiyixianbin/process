package com.yxbjll.java.java8新特性;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author yxbjll
 * @date 2020/2/12 14:19
 * @desc MapInJava8
 */
public class MapInJava8 {


	@Test
	public void methodAboutPutAndPutIfAbsentTest(){
		Map map = new HashMap<>();
		//put() key为空，返回为 null
		System.out.println(map.put("name","pangpang"));
		//key不为空，返回 pangpang，也就是修改之前的值
		System.out.println(map.put("name","pang"));

		System.out.println(map.putIfAbsent("age",18));
		System.out.println(map.get("age"));
		System.out.println(map.putIfAbsent("age",20));
		System.out.println(map.get("age"));
		System.out.println(map.putIfAbsent("age",88));
		System.out.println(map.get("age"));

		assertEquals(1,2);


	}

}
