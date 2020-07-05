package com.gondor.backend.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启对定时任务的支持
@EnableAsync(proxyTargetClass = true) //开启对异步的支持
@EnableEurekaClient //启用Eureka客户端
@SpringBootApplication
public class GondorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GondorApiApplication.class, args);
    }

}
