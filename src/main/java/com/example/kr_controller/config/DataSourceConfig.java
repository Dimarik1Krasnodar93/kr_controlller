package com.example.kr_controller.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfig {

    @Bean
    public DataSource ds(@Value("${driver}") String driver,
                         @Value("${url}") String url,
                         @Value("${username}") String username,
                         @Value("${password}") String password) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername("postgres");
        ds.setPassword(password);
        return ds;
    }
}
