package com.yxbjll.sourcecode.otherProject.medata.interfaceCallBack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yxbjll
 * @date 2020/2/18 19:16
 * @desc InterfaceInstanceTest  函数接口测试
 * 主要用于将接口当做一个对象传入到逻辑处理的地方，之前先做好实例化 new，然后当做参数传入
 * 最后在逻辑处理里面进行直接调用
 * 主要用于如果多个方法逻辑大体相同，只有部分实现逻辑不一样，可以使用接口方法，封装逻辑进行传入
 */
public class InterfaceInstanceTest {

	@Test
	public void InterfaceInstanceTest() {
		List<User> userList = new ArrayList<>();
		userList.add(User.defaultUser().createInstance("w",18));
		userList.add(User.defaultUser().createInstance("x",19));
		userList.add(User.defaultUser().createInstance("y",20));
		userList.add(User.defaultUser().createInstance("z",21));

		//接口直接实现
		CallBack callBack = new CallBack() {
			//相当于定义了一个实现类，里面的代码可以看做是实现类的具体逻辑
			//可以看做 callBack是实例对象，而 接口方法 就是此对象的实现方法，直接调用即可
			@Override
			public void submit(Object event) {
				User user = (User) event;
				System.out.println(user.getAge());
				System.out.println(user.getName());
			}
		};

		//userList.forEach(user -> callBack.submit(user));
		//如果接口不加 @FunctionalInterface 注解，不能通过函数调用
		userList.forEach(callBack::submit);

		//跟单个类似，只不过 -> 方法换成 {}，可以理解为里面就是一个方法，只不过是匿名的
		userList.forEach(event -> {
			System.out.println(event.getName()+event.getAge());
		});

		/**
		 * 总结：函数接口可以单独使用，使用时进行申明。也可以用在多个操作都含有相同的步骤，只在
		 *       某一个方面有差异。如下：
		 *
		 *   //如上代码中，两者都是消费 kafka 消息，并且除了 topic不同，其他的 partition数量 都一样，因此
		     //生产 kafka consumer 的主题逻辑一样，不一样的在配置以及 需要对消息进行不同的处理，因此可以如上操作
		 */
/*
		for (int i = 0; i < jobNum; i++) {
                threadPoolExecutor.submit(new KafkaConsumerCustom().setConsumerId(i).setTopic(environmentContext.getMetaStoreTopic()).setEnvironmentContext(environmentContext).setCallback(event -> {
                    ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) event;
                    dataTableInfoService.parseTableUsageFromLog(record);
                    return null;
                }));

                threadPoolExecutor.submit(new KafkaConsumerCustom().setConsumerId(i).setTopic(environmentContext.getEtlSqlTopic()).setEnvironmentContext(environmentContext).setCallback(event -> {
                    ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) event;
                    dataTableInfoService.parseLineage(record);
                    return null;
                }));
            }

		public void run() {
			log.info("kafkaConsumer brokerList={}", environmentContext);
			KafkaConsumer consumer = KafkaUtil.getConsumerInstance(environmentContext.getBrokerList(), topic, environmentContext.getKafkaGroupId());
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(Integer.valueOf(environmentContext.getKafkaConsumerPollDuration())));
				for (ConsumerRecord<String, String> record : records) {
					log.info("topic={},cousumerId={},key={},value={}", record.topic(), consumerId, record.key(), record.value());
					callback.submit(record);
				}
			}
		}*/








	}


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	protected static class User{
		private String name;
		private int age;

		private static User defaultUser(){
			return new User();
		}

		private User createInstance(String name,int age){
			this.name = name;
			this.age = age;
			return this;
		}
	}
}
