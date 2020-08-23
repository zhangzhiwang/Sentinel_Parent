package com.asiainfo;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = { "com.asiainfo.service.impl" })
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
