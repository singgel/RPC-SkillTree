package com.hks.springdubboprovider.service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.hks.springdubboapi.service.HelloWorldService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 *
 * 描述: 提供方实现
 */
@Service("helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

}
