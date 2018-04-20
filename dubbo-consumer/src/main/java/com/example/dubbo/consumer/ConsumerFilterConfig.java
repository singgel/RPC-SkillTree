package com.example.dubbo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.rpc.Result;
import com.reger.dubbo.rpc.filter.ConsumerFilter;
import com.reger.dubbo.rpc.filter.JoinPoint;

@Configuration
public class ConsumerFilterConfig {

    private static final Logger log = LoggerFactory.getLogger(ConsumerFilterConfig.class);

    @Bean
    public ConsumerFilter consumerFilter1() {
        return new ConsumerFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("1.调用接口 ------》》{}.{}", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ConsumerFilter consumerFilter2() {
        return new ConsumerFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("2.调用接口 ------》》{}.{}", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ConsumerFilter consumerFilter3() {
        return new ConsumerFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("3.调用接口 ------》》{}.{}", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }
}
