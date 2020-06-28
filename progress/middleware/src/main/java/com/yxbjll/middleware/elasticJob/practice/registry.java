package com.yxbjll.middleware.elasticJob.practice;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yxbjll
 * @date 2020/2/23 17:10
 * @desc registry 配置 zookeeper注册中心
 */
@Configuration
@Component
public class registry {

	@Bean(initMethod = "init")
	public ZookeeperRegistryCenter zookeeperRegistryCenter(){
		String servers = "localhost:2181";
		String nameSpace = "es-simpleJob";
		ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(servers,nameSpace);
		ZookeeperRegistryCenter zkRegistry = new ZookeeperRegistryCenter(zkConfig);
		return zkRegistry;
	}

}
