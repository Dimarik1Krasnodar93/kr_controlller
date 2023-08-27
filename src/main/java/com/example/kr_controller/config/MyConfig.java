package com.example.kr_controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class MyConfig {
    @Value("${source-api-uri}")
    private String uri;
    @Bean
    public WebClient webClient() {
        return WebClient.create(uri);
    }
}
