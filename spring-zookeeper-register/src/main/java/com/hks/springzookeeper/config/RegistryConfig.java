package com.hks.springzookeeper.config;

import com.hks.springzookeeper.register.ServiceRegistry;
import com.hks.springzookeeper.register.impl.ServiceRegistryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 *
 * 创建一个配置类来读取application.properties的配置
 */
@Configuration
@ConfigurationProperties(prefix="registry")
public class RegistryConfig {

    @Value("registry.servers")
    private String servers;

    @Bean
    public ServiceRegistry serviceRegistry(){
        return new ServiceRegistryImpl(servers);
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

}
