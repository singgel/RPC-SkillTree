package com.hks.springzookeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 */
@Controller
public class DemoTest {

    @RequestMapping(name = "HelloService", method = RequestMethod.GET, path = "/hello")
    public String hello() {
        return "hello word";
    }

}
