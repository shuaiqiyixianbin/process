package com.yxbjll.java.java8新特性;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.thread.NamedThreadFactory;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author yxbjll
 * @date 2020/2/10 21:58
 * @desc DoubleColon java8 双冒号用法测试
 */
public class DoubleColon {

	public static void printValue(String str) {
		System.out.println("print value : " + str);
	}


	/**
	 * 两种 foreach() 循环写法
	 */
	@Test
	public void doubleColonTest() {
		List<String> list = Arrays.asList("a", "b", "c", "d");
		list.forEach(value -> printValue(value));
		list.forEach(DoubleColon::printValue);
	}


	private static final ScheduledExecutorService DELAY_EXPORT_EXECUTOR = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("DubboServiceDelayExporter", true));

	/**
	 * 高级用法,使用双冒号可以使得普通方法具备了 Runnable 等功能
	 */
	@Test
	public void highLevelUseTest(){
		long start = System.currentTimeMillis();
		System.out.println("当前时间："+start);
		DELAY_EXPORT_EXECUTOR.schedule(this::doExport, 2000, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("响应中断了");
		}
		System.out.println("延迟了："+(System.currentTimeMillis() - start));
	}

	protected void doExport(){
		Thread.interrupted();
		System.out.println("doExport");
	}


	/**
	 * computeIfAbsent
	 */
	@Test
	public void testMapNewAttribute(){
		Map<String,String> map = new HashMap();
		String key = "dubbo";
		String dubbo = map.computeIfAbsent(key,u->new String(""));
		System.out.println(dubbo);
	}


	/**
	 * test computeIfAbsent attr
	 * 使用该方法获取对象之后，可以直接操作对象，相当于对象引用，无需将值重新塞入
	 */
	@Test
	public void testComputeIfAbsent(){
		ConcurrentMap<String,Set<String>> subscribed = new ConcurrentHashMap<>();
		String url = "hh";
		Set<String> listeners = subscribed.computeIfAbsent(url, n -> new ConcurrentHashSet<>());
		System.out.println(listeners.size()+"=before init size");
		listeners.add("66");
		listeners.add("777");
		System.out.println(subscribed.get(url).size()+"=after init size");
	}



}
