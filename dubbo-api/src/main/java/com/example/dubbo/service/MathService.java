package com.example.dubbo.service;

import com.example.dubbo.model.User;

import java.util.List;


public interface MathService {

    /**
     * 简单示例
     * @param a
     * @param b
     * @return
     */
    Integer add(Integer a,Integer b);

    /**
     * 复杂对象示例
     * @param args
     * @return
     */
    List<Object> toList(Object ... args);

    /**
     * 抛出异常测试
     */
    void throwThrowable();

    User getUser(User user);
}
