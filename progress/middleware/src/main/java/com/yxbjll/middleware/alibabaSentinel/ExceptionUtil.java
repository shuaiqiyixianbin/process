package com.yxbjll.middleware.alibabaSentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author yxb
 * @date 2019/12/03
 */
public final class ExceptionUtil {
	public static void handleException(BlockException ex) {
		System.out.println("Oops: " + ex.getClass().getCanonicalName());
		System.out.println("我限流了，这是服务降级信息");
	}
}
