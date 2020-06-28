package com.yxbjll.middleware.alibabaSentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class SentinelServiceImpl implements SentinelService {

	/**
	 * blockHandler ： 抛出异常对应的方法名
	 * blockHandlerClass : 跑出异常方法所在的类，用于指定异常方法不在同一类的情况
	 * 使用 sentinelAop 时，如果设置了限流，并且接口被限制了，就会抛出 handleException
	 * 因此可以在 blockHandler捕获，并且在对应的 handlerException 中实现限流后的返回信息
	 */
	@Override
	@SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
	public void test() {
		System.out.println("Test");
	}

	/**
	 * fallback : 回退到指定方法执行
	 * @param s
	 * @return
	 */
	@Override
	@SentinelResource(value = "hello", fallback = "helloFallback")
	public String hello(long s) {
		if (s < 0) {
			throw new IllegalArgumentException("invalid arg");
		}
		return String.format("Hello at %d", s);
	}

	/**
	 * defaultFallback : 默认失败回退到指定方法
	 * exceptionsToIgnore ：指定忽略的异常
	 * @param name
	 * @return
	 */
	@Override
	@SentinelResource(value = "helloAnother", defaultFallback = "defaultFallback",
			exceptionsToIgnore = {IllegalStateException.class})
	public String helloAnother(String name) {
		if (name == null || "bad".equals(name)) {
			throw new IllegalArgumentException("oops");
		}
		if ("foo".equals(name)) {
			throw new IllegalStateException("oops");
		}
		return "Hello, " + name;
	}

	public String helloFallback(long s, Throwable ex) {
		// Do some log here.
		ex.printStackTrace();
		return "Oops, error occurred at " + s;
	}

	public String defaultFallback() {
		System.out.println("Go to default fallback");
		return "default_fallback";
	}
}
