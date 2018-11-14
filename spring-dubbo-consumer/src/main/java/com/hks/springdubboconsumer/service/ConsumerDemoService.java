package com.hks.springdubboconsumer.service;

import com.hks.springdubboapi.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 *
 * 描述: 消费远程方法
 */
@Service("consumerDemoService")
public class ConsumerDemoService {

    @Autowired
    private HelloWorldService helloWorldService;

    public void sayHello(String name) {
        String hello = helloWorldService.sayHello(name); // 执行消费远程方法
        System.out.println(hello); // 显示调用结果
    }

}
