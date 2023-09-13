package com.example.kr_controller.config;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.io.File;

@Configuration
@AllArgsConstructor
public class SessionFactoryConfig {
    public final String postgresUser;
    public final String postgresPassword;
    public final String postgresDb;
    public final String postgresPath;

    @Bean
    public SessionFactory sf() {
        StandardServiceRegistryBuilder sServRegBuilder
                = new StandardServiceRegistryBuilder().configure();
        sServRegBuilder.applySetting("hibernate.connection.url", postgresPath);
        sServRegBuilder.applySetting("hibernate.connection.username", postgresUser);
        sServRegBuilder.applySetting("hibernate.connection.password", postgresPassword);
        StandardServiceRegistry registry
            = sServRegBuilder.build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
