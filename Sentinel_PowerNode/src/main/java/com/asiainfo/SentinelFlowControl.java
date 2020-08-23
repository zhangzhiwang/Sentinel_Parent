package com.asiainfo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * Sentinel是阿里的分布式服务流量控制组件，现已开源。 主要作用： 1、限流 2、熔断降级 3、流量塑形 4、系统负载保护
 *
 * 本类模拟Sentinel限流
 * 
 * @author zhangzhiwang
 * @date Aug 22, 2020 12:16:17 PM
 */
public class SentinelFlowControl {
	private static final String RESOURCE_NAME = "resourceTest";

	public static void main(String[] args) {
		// 1、配置限流规则
		List<FlowRule> flowRuleList = new ArrayList<>();
		FlowRule flowRule = new FlowRule();// 流量规则实体类
		flowRule.setResource(RESOURCE_NAME);// 设置被保护的资源名称（给被保护的资源起一个名），被保护的资源可以是任意资源，比如http接口、Java普通方法、方法体里面的某一部分代码等。
		flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);// 设置限流的规则（等级），这里对QPS进行限制
		flowRule.setCount(3);// 指定规则的阈值。上面指定的是qps，那么这里设置的就是QPS的上限阈值，如果上面指定的RT（平均响应时长），那么这里就是RT阈值
		flowRuleList.add(flowRule);

		// 2、限流管理器家在配置的规则
		FlowRuleManager.loadRules(flowRuleList);

		// 3、编写被Sentinel保护的业务代码（这里将代码片段作为被保护的资源）
		while (true) {
			Entry entry = null;
			try {
				// 3.1 获取Entry，如参是限流规则里面定义的被保护资源名称。只有在获取Entry之后以及entry.exit()之前的代码才会被Sentinel保护。
//				entry = SphU.entry(RESOURCE_NAME);// 类似于ReentrantReadWriteLock.lock()方法——当线程不能获取到锁时就不能运行被保护的代码，SphU.entry()方法也一样，当超过指定阈值时该方法会抛出异常来阻止访问受保护的资源。
				if(SphO.entry(RESOURCE_NAME)) {// SphU.entry(）方法不同的是，SphU.entry(）方法在超过阈值时会抛异常，而SphO.entry()在未超过（<=）阈值时返回true，否则返回false
					// 3.2 编写被保护的业务代码
					System.out.println("执行业务逻辑...");
				} else {
					System.out.println("超过阈值，不能访问被保护的资源");
				}
				
				
//				// 3.2 编写被保护的业务代码
//				System.out.println("执行业务逻辑...");
			} catch (Exception e) {
				System.out.println("已达到限流阈值，e:" + e.getMessage());
			} finally {
				if (entry != null) {
					// 退出保护资源
					entry.exit();
				}
			}
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
