package com.hks.springthriftcommon.thrift;

import org.apache.thrift.TServiceClient;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 */
public interface PoolOperationCallBack {
    // 创建成功是执行
    void make(TServiceClient client);

    // 销毁client之前执行
    void destroy(TServiceClient client);
}
