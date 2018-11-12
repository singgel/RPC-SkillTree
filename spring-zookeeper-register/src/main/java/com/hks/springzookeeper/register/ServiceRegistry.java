package com.hks.springzookeeper.register;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 *
 * 定义服务注册表接口
 */
public interface ServiceRegistry {

    /**
     * 注册服务信息
     * @param serviceName 服务名称
     * @param serviceAddress 服务地址
     */
    void register(String serviceName,String serviceAddress);
}
