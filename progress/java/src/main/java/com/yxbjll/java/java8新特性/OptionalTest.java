package com.yxbjll.java.java8新特性;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * @author yxbjll
 * @date 2020/2/11 20:24
 * @desc OptionalTest Optional特性
 */
public class OptionalTest {


	@Test
	public void createOptional() {
		//Optional<User> userOptional = Optional.empty();
		//NoSuchElementException
		//userOptional.get();


		//isPresent() 判断 optional 是否不为null
		//System.out.println(userOptional.isPresent());

		//ifPresent() 如果存在就执行操作
		//Optional<User> user = Optional.ofNullable(User.create().defaultUser());
		/*Optional<User> user = Optional.empty();
		user.ifPresent(u -> u.setName("pangpang"));
		System.out.println(user.get());
*/

		//初始化user, of() 和 ofNullable() 都是赋值，但是 of()如果值为null会报空指针，另一个则不会
		//userOptional = Optional.of(new User());
		//System.out.println(userOptional.get().age);

		//orElse() 返回对象值，如果对象为空返回默认值
		//返回正常对象值
		/*User user = User.create().defaultUser();
		User secondUser = User.create().changeUser("pangpang");
		User resultUser = Optional.ofNullable(user).orElse(secondUser);
		assert (resultUser.getName().equals(secondUser.getName()));//异常*/

		//返回默认值
		/*User user = null;
		User secondUser = User.create().changeUser("pangpang");
		User resultUser = Optional.ofNullable(user).orElse(secondUser);
		assert (resultUser.getName().equals(secondUser.getName()));//正常*/

		//也可在 orElse()里面执行方法
		/*User user = null;
		User secondUser = User.create().changeUser("pangpang");
		User resultUser = Optional.ofNullable(user).orElse(new User());
		assert (resultUser.getName().equals(secondUser.getName()));//正常*/


		//orElseThrow() 返回异常,不返回默认值，自定义抛错
		/*User user = null;
		User secondUser = User.create().changeUser("pangpang");
		//直接抛错
		//User resultUser = Optional.ofNullable(user).orElseThrow(()->new NullPointerException("user 为空"));
		//正常返回user
		User resultUser = Optional.ofNullable(secondUser).orElseThrow(()->new NullPointerException("user 为空"));
		System.out.println(resultUser.getName());*/


		//map()、flatMap() 对值调用(应用)作为参数的函数，然后将返回的值包装在 Optional 中。这就使对返回值进行链试调用的操作成为可能
		/*User defaultUser = User.create().defaultUser();
		//将返回值重新作为参数 set 进 Optional()中,此时对象不是 User 而是 String name
		String name = Optional.ofNullable(defaultUser).map(user -> user.getName()).orElse("pangpang");
		assert name.equals(defaultUser.getName());
		name = Optional.ofNullable(User.create()).map(user -> user.getName()).orElse("pangpang");
		assert name.equals(User.create().getName());*/


		//filter过滤
		/*User user = new User("anna@gmail.com", "1234");
		Optional<User> result = Optional.ofNullable(user)
				.filter(u -> u.getEmail() != null && u.getEmail().contains("@"));

		assertTrue(result.isPresent());*/


		/**
		 * java9 增强
		 */
		// or()
		/*User result = Optional.ofNullable(user)
				.or( () -> Optional.of(new User("default","1234"))).get();

		assertEquals(result.getEmail(), "default");*/


		// ifPresentOrElse() 如果存在返回，不存在直接执行方法，java8下用不了
		/*User user = User.create();
		Optional.ofNullable(user).ifPresent( u -> System.out.println("User is:" + u.getName()));*/

		//  stream() 方法，它通过把实例转换为 Stream 对象，让你从广大的 Stream API 中受益。如果没有值，
		// 它会得到空的 Stream；有值的情况下，Stream 则会包含单一值。
		/*List<String> emails = Optional.ofNullable(user)
				.stream()
				.filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
				.map( u -> u.getEmail())
				.collect(Collectors.toList());

		assertTrue(emails.size() == 1);
		assertEquals(emails.get(0), user.getEmail());*/

	}

	public void throwNullPointException(){
		throw new NullPointerException("user 为空");
	}


	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	protected static class User{
		private String userId;
		private String name;
		private Integer age;
		private String phone;

		//普通内部类不允许有 static方法，只有静态内部类才有静态方法
		public static User create(){
			return new User();
		}

		protected User defaultUser(){
			return new User("137","yxbjll",25,"1888888888");
		}
		protected User changeUser(String name){
			return new User("137",name,25,"1888888888");
		}
	}
}
