package com.hks.springzookeeper.controller;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/30
 */
@RestController
public class RestBizController {

    @RequestMapping(value = "/zkget", method = RequestMethod.GET)
    public String zkget() {
        Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("receive event：" + event);
            }
        };

        String value = null;
        try {
            final ZooKeeper zookeeper = new ZooKeeper("192.168.70.3:2181", 999999, watcher);
            final byte[] data = zookeeper.getData("/node_1", watcher, null);
            value = new String(data);
            zookeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "get value from zookeeper [" + value + "]";
    }
}
