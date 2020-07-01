package com.gondor.backend.api.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.gondor.backend.dao", "com.gondor.backend.service", "com.gondor.master"})
@MapperScan(basePackages = {"com.gondor.backend.dao.mapper"})
@Import(value = {JacksonConfig.class, RepositoryConfig.class})
public class GondorApiConfiguration {
}
