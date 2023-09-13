package com.example.kr_controller.config;

import lombok.AllArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@AllArgsConstructor
public class DataSourceConfig {
    public final String postgresUser;
    public final String postgresPassword;
    public final String postgresDb;
    public final String postgresPath;
    @Bean
    public DataSource ds(@Value("${driver}") String driver,
                         String postgresPath,
                          String postgresUser,
                          String postgresPassword) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(postgresPath);
        ds.setUsername(postgresUser);
        ds.setPassword(postgresPassword);
        return ds;
    }
}
