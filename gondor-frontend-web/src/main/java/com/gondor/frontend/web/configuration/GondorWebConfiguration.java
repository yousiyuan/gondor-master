package com.gondor.frontend.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {BalanceConfig.class, JacksonConfig.class})
@ComponentScan(basePackages = {"com.gondor.frontend.fallbackfactory"})
public class GondorWebConfiguration {
}
