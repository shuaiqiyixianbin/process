package com.yxbjll.middleware.codeRedis;

import redis.clients.jedis.Jedis;

/**
 * @author yxb
 * @date 2019/12/02
 * 多线程下的 redis 数据结构 线程安全测试
 */
public class ConcurrentRedisTest {
	public static final String REDIS_KEY = "redis_key";




	public static void main(String[] args) {
		Jedis jedis = JedisUtils.connectRedis();
		Thread t1 = new Thread(new RedisThread());
		Thread t2 = new Thread(new RedisThread());
		Thread t3 = new Thread(new RedisThread());
		Thread t4 = new Thread(new RedisThread());
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		jedis.disconnect();
		System.out.println("当前List长度"+jedis.llen(ConcurrentRedisTest.REDIS_KEY));
		jedis.ltrim(ConcurrentRedisTest.REDIS_KEY,1,0);
	}


}
