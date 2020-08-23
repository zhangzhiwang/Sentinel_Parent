package com.asiainfo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;

/**
 * 模拟sentinel服务熔断降级
 * Sentinel 提供以下几种熔断策略：
 * 1、慢调用比例 (SLOW_REQUEST_RATIO)
 * 2、异常比例 (ERROR_RATIO)
 * 3、异常数 (ERROR_COUNT)
 *
 * @author zhangzhiwang
 * @date Aug 22, 2020 3:56:35 PM
 */
public class SentinelDegrade {
	private static final String RESOURCE_NAME = "resourceTest2";
	
	public static void main(String[] args) throws InterruptedException {
		List<DegradeRule> degradeRuleList = new ArrayList<>();
		DegradeRule degradeRule = new DegradeRule();
		degradeRule.setResource(RESOURCE_NAME);
//		degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);// RT-最大响应时间
//		degradeRule.setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType());
		degradeRule.setGrade(CircuitBreakerStrategy.ERROR_COUNT.getType());
		degradeRule.setCount(2);// 单位是ms，即最大响应时间的阈值
		degradeRule.setStatIntervalMs(2000);// 统计时长，单位为 ms，默认为1000ms。
		degradeRule.setMinRequestAmount(10);// 熔断触发的最小请求数，默认是5次
		degradeRule.setSlowRatioThreshold(0.5);// 当慢请求达到的指定百分比时出发熔断
		// timeWindow是指断路器开关打开后到恢复半开状态的时间，即开关恢复时间。注意：如果服务超过阈值则断路器开关打开，此时所有流量都不能通过，等待指定时间（timeWindow设置的值）之后，断路器会尝试进入半开状态，允许一部分流量过去。
		degradeRule.setTimeWindow(3);// 服务降级的时间窗口，单位s。当服务响应时间超过阈值的时候就会进行降级，等待timeWindow时间过后会进入半开状态。
		degradeRuleList.add(degradeRule);
		
		DegradeRuleManager.loadRules(degradeRuleList);
		
		while(true) {
			if(SphO.entry(RESOURCE_NAME)) {
				Thread.sleep(30);
				System.out.println("执行业务逻辑");
			} else {
				System.out.println("	服务降级");
			}
			
			
		}
	}
}
