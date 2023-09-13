package com.example.kr_controller.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentsConfig {
    Dotenv dotenv = Dotenv.load();

    @Bean
    public String postgresUser() {
        return dotenv.get("POSTGRES_USER");
    }

    @Bean
    public String postgresPassword() {
        return dotenv.get("POSTGRES_PASSWORD");
    }

    @Bean
    public String postgresDb() {
        return dotenv.get("POSTGRES_DB");
    }

    @Bean
    public String postgresPath() {
        return dotenv.get("POSTGRES_PATH");
    }
}
