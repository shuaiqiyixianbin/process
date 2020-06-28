package com.yxbjll.java.dynamic;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yxbjll
 * @date 2020/2/13 12:31
 * @desc DynamicTest
 */
public class DynamicTest {

	@Test
	public void testProxy() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		//直接用jdk原生的动态代理进行类的获取
		//生成handler,放入真实对象
		LogicHandler handler = LogicHandler.instance(new Logic());
		//生成代理对象
		LogicInterface logicInterface = (LogicInterface) Proxy.newProxyInstance(Logic.class.getClassLoader(),Logic.class.getInterfaces(),handler);
		logicInterface.doProcess();


		//直接通过类调用其方法
		Logic logic = new Logic();
		Method method = logic.getClass().getMethod("doProcess",null);
		method.invoke(logic,null);
	}
}
