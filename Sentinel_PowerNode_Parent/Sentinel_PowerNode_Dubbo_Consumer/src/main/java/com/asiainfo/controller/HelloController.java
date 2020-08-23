package com.asiainfo.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.asiainfo.service.api.IHelloService;

@RestController
public class HelloController {
	@DubboReference
	private IHelloService helloService;
	
	@GetMapping("sayHello")
	@SentinelResource(value = "sayHelloSentinel")
	public String hello() throws InterruptedException {
		System.out.println("hello");
		return helloService.hello();
	}
}