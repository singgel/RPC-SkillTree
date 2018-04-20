package com.example.dubbo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.rpc.Result;
import com.reger.dubbo.rpc.filter.JoinPoint;
import com.reger.dubbo.rpc.filter.ProviderFilter;

@Configuration
public class ProviderFilterConfig {

    private static final Logger log = LoggerFactory.getLogger(ProviderFilterConfig.class);

    @Bean
    public ProviderFilter providerFilter1() {
        return new ProviderFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("1.方法{}.{}被调用 ", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ProviderFilter providerFilter2() {
        return new ProviderFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("2.方法{}.{}被调用 ", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ProviderFilter providerFilter3() {
        return new ProviderFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("3.方法{}.{}被调用 ", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

}
