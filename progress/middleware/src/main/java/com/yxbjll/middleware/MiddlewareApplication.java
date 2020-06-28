package com.yxbjll.middleware;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MiddlewareApplication {

	public static void main(String[] args) {
		//initFlowRules();
		SpringApplication.run(MiddlewareApplication.class, args);
	}

	/**
	 * 初始化限流规则
	 */
	private static void initFlowRules() {
		List<FlowRule> rules = new ArrayList<>();
		/**
		 * 手动对资源进行限流
		 */
		FlowRule rule = new FlowRule();
		rule.setResource("test");
		//限流类型 QPS 另外一种是：根据并发线程数进行限制
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		//三种限流行为：匀速限流、warm-up:逐步增加流量、
		rule.setControlBehavior(0);
		rule.setCount(2);
		rules.add(rule);

		rule = new FlowRule();
		rule.setResource("hello");
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setCount(1);
		rules.add(rule);

		FlowRuleManager.loadRules(rules);
	}

}
