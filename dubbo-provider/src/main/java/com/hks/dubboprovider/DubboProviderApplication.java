package com.hks.dubboprovider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.hks.dubboapi.service.GreetingService;
import com.hks.dubboprovider.service.GreetingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderApplication.class, args);

		try{
			ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<GreetingService>();
			serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
			serviceConfig.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
			serviceConfig.setInterface(GreetingService.class);
			serviceConfig.setRef(new GreetingServiceImpl());
			serviceConfig.export();
			System.in.read();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
