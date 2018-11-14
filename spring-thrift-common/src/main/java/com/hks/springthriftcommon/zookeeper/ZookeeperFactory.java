package com.hks.springthriftcommon.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 *
 * 获取zookeeper客户端链接
 */
public class ZookeeperFactory implements FactoryBean<CuratorFramework> {

    private String zkHosts;
    //session超时
    private int sessionTimeout = 30000;
    //connection超时
    private int connectionTimeout = 30000;
    //共享一个zk链接
    private boolean singleton = true;

    private CuratorFramework zkClient;

    private final static String ROOT = "rpc";
    //全局path前缀,常用来区分不同的应用
    private String namespace;

    //注意一定要实现属性的set方法,否则在spring bean注入的地方会拿不到值
    public void setZkHosts(String zkHosts) {
        this.zkHosts = zkHosts;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setZkClient(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public CuratorFramework getObject() throws Exception {
        if (singleton) {
            if (zkClient == null) {
                zkClient = createCuratorFramework();
                zkClient.start();
            }
            return zkClient;
        }
        return createCuratorFramework();
    }

    private CuratorFramework createCuratorFramework() throws Exception {
        if (namespace == null || namespace == "") {
            namespace = ROOT;
        } else {
            namespace = ROOT + "/" + namespace;
        }
        return createCuratorFramework(zkHosts, sessionTimeout, connectionTimeout, namespace);
    }

    public static CuratorFramework createCuratorFramework(String zkHosts, int sessionTimeout, int connectionTimeout, String namespace) {
        return CuratorFrameworkFactory.builder()
                .connectString(zkHosts)
                .sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(connectionTimeout)
                .canBeReadOnly(true)
                .namespace(namespace)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .defaultData(null)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public void close() {
        if (zkClient != null) {
            zkClient.close();
        }
    }
}
