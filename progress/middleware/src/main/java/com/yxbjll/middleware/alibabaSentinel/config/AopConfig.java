package com.yxbjll.middleware.alibabaSentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yxb
 * @date 2019/12/03
 * @desc sentinel Aop config
 */
@Configuration
public class AopConfig {

	/**
	 * 使用 Aop 实现 sentinel 的配置bean
	 * @return
	 */
	@Bean
	public SentinelResourceAspect sentinelResourceAspect() {
		return new SentinelResourceAspect();
	}


}
