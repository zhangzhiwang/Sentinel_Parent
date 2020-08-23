package com.asiainfo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * 用JMeter发起http请求测试Sentinel的服务降级
 *
 * @author zhangzhiwang
 * @date Aug 22, 2020 1:15:03 PM
 */
@RestController
public class SentinelFlowController {
	private static final String RESOURCE_NAME = "flowControl";

	@GetMapping("flowControl")
	public String flowControl() throws InterruptedException {
//		Thread.sleep(100);
		int i = 1 / 0;
		System.out.println("flowControl");
		return "Hello World!";
	}
}
