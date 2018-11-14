package com.hks.springthriftclient.controller;

import com.hks.springthriftapi.service.HelloWorldService;
import com.hks.springthriftclient.util.ApplicationContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 */
@Controller
public class HelloWordController {
    @ResponseBody
    @RequestMapping(value = "/hello")
    public String hello() {
        try {
            HelloWorldService.Iface userBussiness = (HelloWorldService.Iface) ApplicationContextUtil.getBean("thriftServiceClientProxyFactory");
            String str = userBussiness.sayHello("hihao");
            System.out.println(str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
