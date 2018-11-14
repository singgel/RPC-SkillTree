package com.hks.springthriftcommon.thrift.impl;

import com.hks.springthriftcommon.thrift.PoolOperationCallBack;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/13
 */
public class PoolOperationCallBackImpl implements PoolOperationCallBack {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void make(TServiceClient client) {
        logger.info("create");
    }

    @Override
    public void destroy(TServiceClient client) {
        logger.info("destroy");
    }

}