package com.yxbjll.middleware.elasticJob.practice;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yxbjll
 * @date 2020/2/23 19:59
 * @desc simpleJobConfig
 */
@Configuration
@Component
public class simpleJobConfig {

	@Bean(initMethod = "init")
	public JobScheduler jobScheduler(SimpleJob simpleJob, ZookeeperRegistryCenter zkRegistry){
		SpringJobScheduler springJobScheduler = new SpringJobScheduler(simpleJob,zkRegistry, liteJobConfiguration());
		return springJobScheduler;
	}


	/**
	 * cron ：cron表达式，用于控制作业触发时间。
	 shardingTotalCount ：作业分片总数
	 shardingItemParameters ：分片序列号和参数用等号分隔，多个键值对用逗号分隔分片序列号从0开始，不可大于或等于作业分片总数 如：0=a,1=b,2=c
	 jobParameters ：作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
	 */
	public LiteJobConfiguration liteJobConfiguration() {
		JobCoreConfiguration.Builder jobCoreConfig = JobCoreConfiguration.newBuilder("jobParam1", "0/10 * * * * ?",3)
													  .shardingItemParameters("0=a,1=b,2=c").jobParameter("param");
		SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfig.build(),
															SimpleJobTest.class.getCanonicalName());
		LiteJobConfiguration.Builder builder = LiteJobConfiguration.newBuilder(simpleJobConfiguration);
		return builder.build();
	}

}
