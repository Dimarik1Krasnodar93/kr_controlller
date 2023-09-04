package com.example.kr_controller;

import liquibase.integration.commandline.Main;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
//@EnableConfigurationProperties(JwtProperties.class)
public class KrControllerApplication extends SpringBootServletInitializer {



	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KrControllerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KrControllerApplication.class, args);
	}
}
