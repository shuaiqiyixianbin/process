package com.yxbjll.java.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yxbjll
 * @date 2020/2/13 12:20
 * @desc LogicHandler
 */
public class LogicHandler implements InvocationHandler {

	private Object realObject;
	private long startTime;
	private long endTime;

	private LogicHandler(Object realObject){
		this.realObject = realObject;
	}

	public static LogicHandler instance(Object realObject){
		return new LogicHandler(realObject);
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//前置处理
		preProcess();
		//通过代理类调用具体方法，此处为常规写法，直接调用类方法
		Object result = method.invoke(realObject,args);

		//第二种，通过自己封装的类调用方法，不使用默认的方法
		//此处的 invoker 为 dubbo 封装的类信息，此处就是调用其自定义的方法操作
		//return invoker.invoke(new RpcInvocation(method, args)).recreate();

		//后置处理
		postProcess();
		return result;
	}

	protected void preProcess(){
		startTime = System.currentTimeMillis();
	}

	private void postProcess() {
		endTime = System.currentTimeMillis();
		System.out.println("方法耗时："+(endTime - startTime) +":ms");
	}



}
