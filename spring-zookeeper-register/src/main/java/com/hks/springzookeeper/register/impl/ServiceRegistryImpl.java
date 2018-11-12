package com.hks.springzookeeper.register.impl;

import com.hks.springzookeeper.register.ServiceRegistry;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 * <p>
 * 创建一个ServiceRegistry的实现类。实现注册接口
 */
@Component
public class ServiceRegistryImpl implements Watcher, ServiceRegistry {

    private static final int SESSION_TIMEOUT = 5000;
    private static final String REGISTRY_PATH = "/registry";
    private static CountDownLatch latch = new CountDownLatch(1);
    private static Logger logger = LoggerFactory
            .getLogger(ServiceRegistryImpl.class);
    private ZooKeeper zk;

    public ServiceRegistryImpl() {
        logger.debug("初始化类");
    }

    public ServiceRegistryImpl(String zkServers) {
        try {
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await();
            logger.debug("connected to zookeeper");

        } catch (Exception e) {
            logger.error("create zookeeper client failuer", e);
        }
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        String registryPath = REGISTRY_PATH;
        try {
            logger.debug("-zk---------" + zk);
            //创建根节点：持久节点
            if (zk.exists(registryPath, true) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
                logger.debug("create registry node:{}", registryPath);
            }
            //创建服务节点：持久节点
            String servicePath = registryPath + "/" + serviceName;
            if (zk.exists(servicePath, true) == null) {
                zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                logger.debug("create service node :{}" + servicePath);
            }
            //创建地址节点：临时顺序节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.debug("create node address:{}=>{}" + addressNode);
        } catch (Exception e) {
            logger.error("create node failure", e);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }
}
