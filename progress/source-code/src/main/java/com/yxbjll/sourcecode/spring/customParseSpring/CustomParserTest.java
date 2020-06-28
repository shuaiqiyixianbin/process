package com.yxbjll.sourcecode.spring.customParseSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yxbjll
 * @date 2020/2/9 12:10
 * @desc CustomParserTest
 */
public class CustomParserTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
		People people = (People) ctx.getBean("custom");
		System.out.println(people.getAge());
		System.out.println(people.getId());
		System.out.println(people.getName());
	}

}
