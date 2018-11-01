package com.hks.springzookeeperweb.controller;

import com.hks.springzookeeperweb.consumer.ServiceHelp;
import com.hks.springzookeeperweb.consumer.impl.ServiceHelpImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 */
@Controller
public class ZookeeperRead {

    @RequestMapping(method = RequestMethod.GET, path = "/read")
    @ResponseBody
    public String hello() {
        ServiceHelp serviceHelp = new ServiceHelpImpl("192.168.70.3:2181");
        String data = serviceHelp.getData("HelloService");
        return data;
    }

}
