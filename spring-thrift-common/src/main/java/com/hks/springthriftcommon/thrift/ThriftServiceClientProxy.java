package com.hks.springthriftcommon.thrift;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 *
 * 客户端代理类
 */
public class ThriftServiceClientProxy implements InvocationHandler {

    private GenericObjectPool<TServiceClient> pool;

    private TServiceClient client;

    public ThriftServiceClientProxy(GenericObjectPool<TServiceClient> pool) {
        this.pool = pool;
    }

    public ThriftServiceClientProxy(TServiceClient client) {
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //获取一个对象
        TServiceClient client = pool.borrowObject();
        boolean flag = true;
        try {
            return method.invoke(client, args);
        } catch (Exception e) {
            flag = false;
            throw e;
        } finally {
            if (flag) {
                //归还对象
                pool.returnObject(client);
            } else {
                //标记对象不可再用
                pool.invalidateObject(client);
            }
        }
    }
}
