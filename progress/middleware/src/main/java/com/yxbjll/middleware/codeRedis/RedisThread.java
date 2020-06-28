package com.yxbjll.middleware.codeRedis;

import redis.clients.jedis.Jedis;

/**
 * @author yxb
 * @date 2019/12/02
 * redis 线程
 */
public class RedisThread implements Runnable {


	@Override
	public void run() {
		Jedis jedis = JedisUtils.connectRedis();
		for (int i=0;i<50;i++){
			jedis.lpush(ConcurrentRedisTest.REDIS_KEY,i+"");
		}
		jedis.disconnect();
	}
}
