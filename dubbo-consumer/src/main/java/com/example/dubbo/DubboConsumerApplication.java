package com.example.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.dubbo.consumer")
public class DubboConsumerApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DubboConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("服务消费者启动完毕------>>启动完毕");
	}
}
