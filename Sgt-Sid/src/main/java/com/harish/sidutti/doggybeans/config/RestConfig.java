package com.harish.sidutti.doggybeans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfig {

    @Bean
    public WebClient restTemplate() {
        return WebClient.builder().build();
    }
}
