package com.example.kr_controller.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
@Configuration
public class ErrorConfiguration {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new MyErrorPageRegistrar();
    }

    static class MyErrorPageRegistrar implements ErrorPageRegistrar {

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404"); // Зарегистрировать страницу для ошибки 404
            registry.addErrorPages(error404Page);
        }
    }

}
