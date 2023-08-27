package com.example.kr_controller.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.ErrorPageSupport;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Configuration
@RequiredArgsConstructor
public class WebMvc extends WebMvcConfigurationSupport {
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
            return new RequestMappingHandlerMapping();
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
 }
