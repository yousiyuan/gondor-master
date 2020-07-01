package com.gondor.frontend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.gondor.frontend.client"})
@SpringBootApplication
public class GondorWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GondorWebApplication.class, args);
    }

}
