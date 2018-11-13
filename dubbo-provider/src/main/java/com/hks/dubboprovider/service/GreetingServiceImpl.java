package com.hks.dubboprovider.service;

import com.hks.dubboapi.service.GreetingService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 */
public class GreetingServiceImpl implements GreetingService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
