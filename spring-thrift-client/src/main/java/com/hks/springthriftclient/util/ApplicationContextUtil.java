package com.hks.springthriftclient.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public final static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public final static Object getBean(String beanName, Class<?> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }

}
