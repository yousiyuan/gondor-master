package com.gondor.frontend.web.configuration;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *TODO：配置Apollo
 * 设置VM options参数，示例如下：
 * (1)设置读取dev环境的Apollo配置，无默认值
 *    -Denv=dev
 * (2)设置读取哪个集群的配置，默认default
 *    -Dapollo.cluster=default
 */
@EnableApolloConfig
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.gondor.frontend.client"})
@Configuration
@Import(value = {BalanceConfig.class, JacksonConfig.class})
@ComponentScan(basePackages = {"com.gondor.frontend.fallbackfactory"})
public class GondorWebConfiguration {
}
