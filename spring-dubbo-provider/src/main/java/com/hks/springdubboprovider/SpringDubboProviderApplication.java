package com.hks.springdubboprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.hks"})
public class SpringDubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDubboProviderApplication.class, args);
	}
}
