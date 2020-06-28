package MethodProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yxb
 * @desc 方法属性获取测试
 */
public class MethodProperties {

	public static void main(String[] args) {
		//getClassMethodName();
		//getAnnotationOfMethod();
		getAnnotationOfField();
	}


	/**
	 * 获取 Method()上的特定 Annotation(注解)
	 */
	public static void getAnnotationOfMethod(){
		CommonTest commonTest = new CommonTest();
		Method[] methods = commonTest.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")){
				Parameter parameter = method.getAnnotation(Parameter.class);
				if (parameter != null){
					System.out.println(parameter.key());
				}
			}
		}
	}

	/**
	 * 获取 Field上的特定 Annotation(注解)
	 * 可用于底层poi进行字段映射
	 */
	public static void getAnnotationOfField(){
		CommonTest commonTest = new CommonTest();
		Field[] fields = commonTest.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Parameter parameter = field.getAnnotation(Parameter.class);
			if (parameter != null){
				System.out.println(parameter.key());
			}
		}
	}


	/**
	 * 通过 instance.getClass().getMethods()获取所有的 Method
	 */
	@Deprecated
	public static void getClassMethodName(){
		CommonTest commonTest = new CommonTest();
		Method[] methods = commonTest.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")){
				System.out.println(method.getParameterTypes());
			}
		}
	}
}
