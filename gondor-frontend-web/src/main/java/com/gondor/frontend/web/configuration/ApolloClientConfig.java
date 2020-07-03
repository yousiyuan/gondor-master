package com.gondor.frontend.web.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

/**
 * https://github.com/ctripcorp/apollo/wiki/Java客户端使用指南#1241-environment
 */
@Slf4j
public class ApolloClientConfig {

    public ApolloClientConfig() {
        // 注册监听事件，可以通过重载方法，监听特定键的变更
        applicationConfig().addChangeListener(this::applicationOnChangeEvent, interestedKeys);
        datasourceConfig().addChangeListener(this::otherConfigOnChangeEvent);
        businessConfig().addChangeListener(this::otherConfigOnChangeEvent);
    }

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
        // 监听端口号是否变更
        for (String key : this.interestedKeys) {
            ConfigChange chgInfo = event.getChange(key);
            log.info(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
                    chgInfo.getPropertyName(),
                    chgInfo.getOldValue(),
                    chgInfo.getNewValue(),
                    chgInfo.getChangeType()));
        }
    }

    private void otherConfigOnChangeEvent(ConfigChangeEvent changeEvent) {
        log.info("命名空间 {} 的配置信息发生变化", changeEvent.getNamespace());
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            log.info(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
                    change.getPropertyName(),
                    change.getOldValue(),
                    change.getNewValue(),
                    change.getChangeType()));
        }
    }

}
