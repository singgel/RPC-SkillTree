package com.hks.springzookeeperweb.consumer.impl;

import com.hks.springzookeeperweb.consumer.ServiceHelp;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 */
@Component
public class ServiceHelpImpl implements Watcher, ServiceHelp {

    private static final int SESSION_TIMEOUT = 5000;
    private static final String REGISTRY_PATH = "/registry";
    private static CountDownLatch latch = new CountDownLatch(1);
    private static Logger logger = LoggerFactory
            .getLogger(ServiceHelpImpl.class);
    private ZooKeeper zk;

    public ServiceHelpImpl() {
        logger.debug("初始化类");
    }

    public ServiceHelpImpl(String zkServers) {
        try {
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await();
            logger.debug("connected to zookeeper");

        } catch (Exception e) {
            logger.error("create zookeeper client failuer", e);
        }
    }

    @Override
    public String getData(String serviceName) {
        try {
            String servicePath = REGISTRY_PATH + "/" + serviceName;
            logger.debug("service_path is :" + servicePath);
            List<String> childPath;

            childPath = zk.getChildren(servicePath, false);

            if (childPath.size() == 0) {
                logger.error("%s address node is not exsited", serviceName);
                throw (new Exception("地址未找到"));
            }
            String addressPath = servicePath + "/";
            if (childPath.size() == 1) {
                addressPath += childPath.get(0);
            }
            if (childPath.size() > 1) {
                addressPath += childPath.get((int) (Math.random() * childPath.size()));
            }
            logger.debug("address node is " + addressPath);
            byte[] data = zk.getData(addressPath, null, null);
            return new String(data);
        } catch (Exception e) {
            logger.error("get data failure", e);
        }
        return "";
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                latch.countDown();
            }
            else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){
                try{
                    System.out.print("reGet child:"+zk.getChildren(watchedEvent.getPath(),true));
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
