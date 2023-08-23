package com.example.kr_controller.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Properties;

@Configuration
public class MyConfig {
    @Bean
    public DataSource dataSource() {
        Properties properties = new Properties();
        try (InputStream bf
                     = new BufferedInputStream(new FileInputStream("db/liquibase_test.properties"))) {
            properties.load(bf);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass((String) properties.get("driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl((String) properties.get("url"));
        dataSource.setUser((String) properties.get("username"));
        dataSource.setPassword((String) properties.get("password"));
        return dataSource;
    }
}
