package com.hks.springthriftserver.serviceImpl;

import com.hks.springthriftapi.service.HelloWorldService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService.Iface {

    @Override
    public String sayHello(String username) throws TException {
        return "hello" + username;
    }

    @Override
    public String getRandom() throws TException {
        return "random";
    }

}
