package com.hks.springthriftserver.config;

import com.hks.springthriftcommon.thrift.ThriftServiceServerFactory;
import com.hks.springthriftcommon.zookeeper.ZookeeperFactory;
import com.hks.springthriftcommon.zookeeper.impl.ThriftServerAddressRegisterZookeeper;
import com.hks.springthriftserver.serviceImpl.HelloWorldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 */
@Configuration
public class ThriftServerConfig {

    @Autowired
    private HelloWorldServiceImpl helloWorldServiceImpl;

    @Bean
    public ZookeeperFactory zookeeperFactory() {
        ZookeeperFactory zookeeperFactory = new ZookeeperFactory();
        zookeeperFactory.setZkHosts("127.0.0.1:2181");
        zookeeperFactory.setNamespace("com.thrift.api");
        zookeeperFactory.setConnectionTimeout(3000);
        zookeeperFactory.setSessionTimeout(3000);
        zookeeperFactory.setSingleton(true);
        return zookeeperFactory;
    }

    @Bean
    public ThriftServerAddressRegisterZookeeper thriftServerAddressRegisterZookeeper() {
        ThriftServerAddressRegisterZookeeper tsarz = new ThriftServerAddressRegisterZookeeper();
        try {
            tsarz.setZkClient(zookeeperFactory().getObject());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tsarz;
    }

    @Bean
    public ThriftServiceServerFactory thriftServiceServerFactory() {
        ThriftServiceServerFactory tssf = new ThriftServiceServerFactory();
        tssf.setService(helloWorldServiceImpl);
        tssf.setPort(9002);
        tssf.setVersion("1.0.0");
        tssf.setWeight(1);
        tssf.setThriftServerAddressRegister(thriftServerAddressRegisterZookeeper());
        return tssf;
    }
}