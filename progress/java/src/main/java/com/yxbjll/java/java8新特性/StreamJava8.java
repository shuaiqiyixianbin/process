package com.yxbjll.java.java8新特性;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yxbjll
 * @date 2020/2/18 11:22
 * @desc StreamJava8
 * @jiejian https://www.jianshu.com/p/11c925cdba50
 */
public class StreamJava8 {


	/**
	 * map() 方法使用,此处 map() 和 Optional 的 map类似，都是取出里面的对象进行处理
	 */
	@Test
	public void testMap(){
		//lambda 表达式写法
		/*Stream.of("apple","banana","orange","watermaleon","grape")
				//转成单词的长度 int
				.map(e -> e.length())
				.forEach(e -> System.out.println(e))*/;

		//函数引用
		Stream.of("apple","banana","orange","watermaleon","grape")
				.map(String::length)
				.forEach(System.out::println);

		// mapToInt() 这限定了转换的类型Int，最终产生的流为IntStream，及结果只能转化成int。
		// mapToLong mapToDouble  都类似
		Stream.of("apple", "banana", "orange", "waltermaleon", "grape")
				.mapToInt(e -> e.length()) //转成int
				.forEach(e -> System.out.println(e));
	}

	/**
	 * limit 限制元素的个数，只需传入 long 类型 表示限制的最大个数
	 */
	@Test
	public void limitTest(){
		Stream.of(1,2,3,4,5,6)
				.limit(3)
				.forEach(System.out::println);
	}

	/**
	 * distinct 将根据equals 方法进行判断，如果要对自己自定义的bean 去重，则需要 重写equals方法
	 */
	@Test
	public void distinctTest(){
		Stream.of(1,2,3,4,1,2,3,4,55,56,7,3)
				.distinct()
				.forEach(System.out::println);
	}


	/**
	 * filter 对某些元素进行过滤，不符合筛选条件的将无法进入流的下游
	 */
	@Test
	public void filterTest(){
		Stream.of(1,2,3,4,1,2,3,4,55,56,7,3)
				.filter(e->e>=5) //过滤小于5的
				.forEach(e-> System.out.println(e));
	}


	/**
	 * peek 挑选 ，将元素挑选出来，可以理解为提前消费
	 */
	@Test
	public void peekTest(){
		User w = new User("w",10);
		User x = new User("x",11);
		User y = new User("y",12);

		/*Stream.of(w,x,y)
				.peek(e->e.setName(e.getName()+e.getAge()))
				.forEach(e-> System.out.println(e));*/


		Stream.of(w,x,y)
				.map(e->e.getName())
				.forEach(System.out::println);
	}


	@Test
	public void streamListTest(){
		User w = new User("w",10);
		User x = new User("x",11);
		User y = new User("y",12);

		//只有在此处定义了List 的 User 类型，才可以使用 user.getAttribute()；
		List<User> userList = new ArrayList();
		userList.add(w);
		userList.add(x);
		userList.add(y);
		/*userList.stream().filter(e->e.toString().length() > 8)
				.forEach(System.out::print);*/

		userList.stream().filter(user -> "pang".equals(user.getName())).collect(Collectors.toList()).forEach(System.out::print);
	}



	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	protected class User{
		private String name;
		private int age;
	}

}
