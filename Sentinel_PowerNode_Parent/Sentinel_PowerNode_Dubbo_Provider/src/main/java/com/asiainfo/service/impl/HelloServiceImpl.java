package com.asiainfo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.asiainfo.service.api.IHelloService;

@DubboService
public class HelloServiceImpl implements IHelloService {

//	@SentinelResource(value = "helloServer", fallback = "fb")// value是受保护资源的名字，fallback和hystrix的类似，是降级时调用的方法
	@Override
	public String hello() throws InterruptedException {
		System.out.println("provider");
//		int i = 1 / 0;
		return "hello";
	}
	
	public String fb() {
		return "fallback方法";
	}
}
