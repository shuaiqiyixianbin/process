package com.yxbjll.sourcecode.otherProject.medata.interfaceCallBack;

/**
 * @author yxbjll
 * @date 2020/2/18 19:15
 * @desc CallBack
 */
@FunctionalInterface
public interface CallBack<E> {
	void submit(E event);
}
