package com.example.dubbo;

import com.example.dubbo.exception.TestRuntimeException;
import com.reger.dubbo.rpc.filter.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages="com.example.dubbo.provider")
public class DubboProviderApplication implements CommandLineRunner, DisposableBean {

	private  final Logger log = LoggerFactory.getLogger(DubboProviderApplication.class);

	private final static CountDownLatch latch = new CountDownLatch(1);
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) throws InterruptedException {
		Utils.register(TestRuntimeException.class); // 注册允许传递的异常类型
		Utils.register(RuntimeException.class); // 注册允许传递的异常类型
		context = SpringApplication.run(DubboProviderApplication.class, args);
		latch.await();
	}

	@Override
	public void destroy() throws Exception {
		latch.countDown();
		context.close();
		log.info("服务提供者关闭------>>服务关闭");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("服务提供者启动完毕------>>启动完毕");
	}
}
