package com.hks.springzookeeper.config;

import com.hks.springzookeeper.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

/**
 * @Author: hekuangsheng
 * @Date: 2018/10/31
 * <p>
 * 由于我们的服务是以javaweb的形式发布的。
 * 所以在每个应用初始化时，去完成接口的注册是一个好时机。
 * 因此我们新加一个类去实现ServletContextListener，并把这个类交给spring来管理。
 */
@Component
public class WebListener implements ServletContextListener {

    private static Logger logger = LoggerFactory
            .getLogger(WebListener.class);
    @Value("${server.address}")
    private String serverAddress;
    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //获取请求映射
        ServletContext servletContext = servletContextEvent.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> infoMap = mapping.getHandlerMethods();
        for (RequestMappingInfo info : infoMap.keySet()) {
            String serviceName = info.getName();
            logger.debug("-----------" + serviceName);
            if (null != serviceName) {
                serviceRegistry.register(serviceName, String.format("%s:%d/hello", serverAddress, serverPort));
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
