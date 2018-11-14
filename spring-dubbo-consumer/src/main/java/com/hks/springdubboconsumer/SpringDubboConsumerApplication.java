package com.hks.springdubboconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.hks"})
public class SpringDubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDubboConsumerApplication.class, args);
	}
}
