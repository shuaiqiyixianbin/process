package com.yxbjll.distribute;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author yxbjll
 * @date 2020/2/18 14:08
 * @desc ServerInfo
 */
public class ServerInfo {

	@Test
	public void PIDTest() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		//runtimeMXBean.getName()取得的值包括两个部分：PID和hostname，两者用@连接。如：8892@DESKTOP-0I413TC
		//runtimeMXBean 里面还有许多其他属性可以使用
		System.out.println("PID = "+ runtimeMXBean.getName().split("@")[0]);
	}
}
