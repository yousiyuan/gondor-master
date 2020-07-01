package com.gondor.frontend.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gondor.master.utils.JsonUtils;
import org.springframework.context.annotation.Bean;

public class JacksonConfig {

    @Bean(name = "objectMapper")
    public ObjectMapper jacksonObjectMapper() {
        return JsonUtils.getDefaultMapper();
    }

}
