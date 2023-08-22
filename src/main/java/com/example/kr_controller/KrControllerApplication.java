package com.example.kr_controller;

import com.example.kr_controller.Component.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
//@EnableConfigurationProperties(JwtProperties.class)
public class KrControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrControllerApplication.class, args);
	}

}
