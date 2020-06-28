package com.yxbjll.java.java8新特性;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxbjll
 * @desc FunctionTest
 * @since 2020/4/4
 */
public class FunctionTest {

	/**
	 * 第一种方式使用 FunctionalInterface
	 * @param callback
	 * @return
	 */
	protected static Object message(Callback callback){
		//执行submit的时候，其实就是调用lambda -> 后面的代码逻辑，里面的参数只是代码的参数
		return callback.submit(null);
	}


	public static void main(String[] args) {
		/*Callback callback = new Callback() {
			@Override
			public Object submit(Object event) {
				return "hehe";
			}
		};
		System.out.println(message(callback));*/


		//second method
		//lambdaFunction(obj->countX(1));

		//有参函数
		lambdaFunctionHasArgs(param -> getList(param.getStart(),param.getEnd()));

	}

	//FunctionalInterface 里面的 submit() 方法有返回参数，而submit()方法执行的就是我们自定义的方法，因此两边
	//的参数需要保持同步
	private static int countX(int x){
		return x+10;
	}


	/**
	 * 第二种方法使用 FunctionalInterface
	 */

	protected static void lambdaFunction(Callback callback){
		//无需传参
		int count = (int) callback.submit(null);
		System.out.println("count="+count);
	}



	private static List getList(Long start,Long end){
		return new ArrayList();
	}

	private static void lambdaFunctionHasArgs(Callback<LimitParam> callback){
		List result = (List) callback.submit(new LimitParam(0L,100L));
	}



}
