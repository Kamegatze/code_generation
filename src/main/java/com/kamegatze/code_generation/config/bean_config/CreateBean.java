package com.kamegatze.code_generation.config.bean_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CreateBean {

    @Bean
    public RestOperations getRestOperations() {
        return new RestTemplate();
    }
}
