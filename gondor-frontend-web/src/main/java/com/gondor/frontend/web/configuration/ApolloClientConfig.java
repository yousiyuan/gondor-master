package com.gondor.frontend.web.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.gondor.master.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
//import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
//import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://github.com/ctripcorp/apollo/wiki/Java客户端使用指南#1241-environment
 */
@Slf4j
public class ApolloClientConfig {

    //@Autowired
    public ApolloClientConfig() {/*RouteLocator routeLocator*/
        //this.routeLocator = routeLocator;

        // 注册监听事件
        applicationConfig().addChangeListener(this::applicationOnChangeEvent, interestedKeys);//可以通过重载方法，监听特定键的变更
        datasourceConfig().addChangeListener(this::otherConfigOnChangeEvent);
        businessConfig().addChangeListener(this::otherConfigOnChangeEvent);
    }

    //private RouteLocator routeLocator;
    private final Set<String> interestedKeys = new HashSet<>();

    {
        interestedKeys.add("server.port");
    }

    @Bean("application")
    public Config applicationConfig() {
        //获取默认namespace的配置（application）
        return ConfigService.getAppConfig();
    }

    @Bean("datasource")
    public Config datasourceConfig() {
        //获取公共Namespace的配置
        return ConfigService.getConfig("datasource");
    }

    @Bean("business")
    public Config businessConfig() {
        //获取公共Namespace的配置
        return ConfigService.getConfig("business");
    }

    private void applicationOnChangeEvent(ConfigChangeEvent event) {
        log.info("命名空间 {} 的配置信息发生变化：Found change - {}", event.getNamespace(), formatConfigChangeInfo(event));

        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        applicationContext.publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
        //applicationContext.publishEvent(new RoutesRefreshedEvent(routeLocator));
    }

    private void otherConfigOnChangeEvent(ConfigChangeEvent changeEvent) {
        log.info("命名空间 {} 的配置信息发生变化：Found change - {}", changeEvent.getNamespace(), formatConfigChangeInfo(changeEvent));

        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext();
        applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        //applicationContext.publishEvent(new RoutesRefreshedEvent(routeLocator));
    }

    private Object formatConfigChangeInfo(ConfigChangeEvent changeEvent) {
        return changeEvent.changedKeys().stream()
                .map(key -> {
                    ConfigChange chgInfo = changeEvent.getChange(key);
                    Map<String, Object> chgMap = new HashMap<>();
                    chgMap.put("key", chgInfo.getPropertyName());
                    chgMap.put("oldValue", chgInfo.getOldValue());
                    chgMap.put("newValue", chgInfo.getNewValue());
                    chgMap.put("changeType", chgInfo.getChangeType());
                    return chgMap;
                }).collect(Collectors.toList());
    }

}
