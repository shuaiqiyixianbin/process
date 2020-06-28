package com.yxbjll.sourcecode.dubbo.spi机制;


/**
 * @author yxbjll
 * @date 2020/2/9 14:52
 * @desc CustomContainer
 */
public class CustomContainer implements Container {
	@Override
	public void start() {
		System.out.println("custom container start");
	}

	@Override
	public void stop() {
		System.out.println("custom container end");
	}
}
