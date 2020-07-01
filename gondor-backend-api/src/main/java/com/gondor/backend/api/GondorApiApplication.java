package com.gondor.backend.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient //启用Eureka客户端
@SpringBootApplication
public class GondorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GondorApiApplication.class, args);
    }

}
