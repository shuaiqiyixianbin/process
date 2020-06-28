package com.yxbjll.middleware.elasticJob.practice;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yxbjll
 * @date 2020/2/23 19:53
 * @desc SimpleJob 简单任务 实现 SimpleJob 重写 execute() 方法
 */
@Component
@Slf4j
public class SimpleJobTest implements SimpleJob {
	@Override
	public void execute(ShardingContext shardingContext) {
		System.out.println("开始了、、、、、、、、、、");
		log.info("任务开始时间{}",System.currentTimeMillis());
		log.info(shardingContext.toString());
		log.info("任务执行完成时间{}",System.currentTimeMillis());
	}
}
