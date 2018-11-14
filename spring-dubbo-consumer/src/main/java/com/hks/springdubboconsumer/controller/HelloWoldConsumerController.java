package com.hks.springdubboconsumer.controller;

import com.hks.springdubboapi.service.HelloWorldService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 */
@RestController
public class HelloWoldConsumerController {

    @Resource
    private HelloWorldService helloWorldService;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return helloWorldService.sayHello(name);
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

}
