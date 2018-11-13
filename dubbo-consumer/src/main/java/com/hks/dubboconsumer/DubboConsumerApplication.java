package com.hks.dubboconsumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.hks.dubboapi.service.GreetingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerApplication.class, args);
		try{
			ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<GreetingService>();
			referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
			referenceConfig.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
			referenceConfig.setInterface(GreetingService.class);
			GreetingService greetingService = referenceConfig.get();
			System.out.println(greetingService.sayHello("world"));
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
