package com.hks.springthriftcommon.thrift;

import com.hks.springthriftcommon.exception.ThriftException;
import com.hks.springthriftcommon.zookeeper.ThriftServerAddressProvider;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 *
 * 客户端代理工厂
 */
public class ThriftServiceClientProxyFactory implements FactoryBean, InitializingBean, Closeable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //最大活跃连接数
    private Integer maxActive = 32;

    //ms,default 3 min,链接空闲时间, -1,关闭空闲检测
    private Integer idleTime = 180000;

    private ThriftServerAddressProvider serverAddressProvider;

    private Object proxyClient;

    private Class<?> objectClass;

    private GenericObjectPool<TServiceClient> pool;

    PoolOperationCallBack callback;

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public void setIdleTime(Integer idleTime) {
        this.idleTime = idleTime;
    }

    public void setServerAddressProvider(ThriftServerAddressProvider serverAddressProvider) {
        this.serverAddressProvider = serverAddressProvider;
    }

    @Override
    public void close() {
        if (pool != null) {
            try {
                pool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (serverAddressProvider != null) {
            serverAddressProvider.close();
        }
    }

    /**
     * 最终返回类型
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return proxyClient;
    }

    /**
     * 返回类型的class信息
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return objectClass;
    }

    /**
     * 是否为单例模式
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 初始化方法
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 加载Iface接口
        objectClass = classLoader.loadClass(serverAddressProvider.getService() + "$Iface");
        // 加载Client.Factory类
        Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader.loadClass(serverAddressProvider.getService() + "$Client$Factory");
        TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();

        ThriftClientPoolFactory clientPool = new ThriftClientPoolFactory(serverAddressProvider, clientFactory, callback);
        pool = new GenericObjectPool<>(clientPool, makePoolConfig());
        InvocationHandler handler = makeProxyHandler1();//方式1
        //InvocationHandler handler = makeProxyHandler2();//方式2

        proxyClient = Proxy.newProxyInstance(classLoader, new Class[]{objectClass}, handler);
    }

    private GenericObjectPool.Config makePoolConfig() {
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = maxActive;
        //pool中最多能保留多少个空闲对象
        poolConfig.maxIdle = 1;
        //最少有多少个空闲对象
        poolConfig.minIdle = 0;
        poolConfig.minEvictableIdleTimeMillis = idleTime;
        poolConfig.timeBetweenEvictionRunsMillis = idleTime * 2L;
        poolConfig.testOnBorrow = true;
        poolConfig.testOnReturn = false;
        poolConfig.testWhileIdle = false;
        return poolConfig;
    }

    private InvocationHandler makeProxyHandler1() throws Exception {
        ThriftServiceClientProxy handler = new ThriftServiceClientProxy(pool);
        return handler;
    }

    private InvocationHandler makeProxyHandler2() throws Exception {
        ThriftServiceClient2Proxy handler;
        TServiceClient client = pool.borrowObject();
        try {
            handler = new ThriftServiceClient2Proxy(client);
            pool.returnObject(client);
        } catch (Exception e) {
            pool.invalidateObject(client);
            throw new ThriftException("获取代理对象失败");
        }
        return handler;
    }
}
