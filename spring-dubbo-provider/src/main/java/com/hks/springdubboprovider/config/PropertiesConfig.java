package com.hks.springdubboprovider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: hekuangsheng
 * @Date: 2018/11/14
 *
 * 描述: 加载配置
 */
@Configuration
@PropertySource("classpath:dubbo.properties")
@ImportResource({"classpath:dubbo/*.xml"})
public class PropertiesConfig {
}
