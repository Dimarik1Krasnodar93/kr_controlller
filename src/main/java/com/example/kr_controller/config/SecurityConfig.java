package com.example.kr_controller.config;

import com.example.kr_controller.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChainOAuth(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                .csrf(csrf -> csrf.disable());
        httpSecurity.addFilterAfter(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        httpSecurity

                .authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/auth/**").permitAll().anyRequest().authenticated());
        return httpSecurity.build();
    }
}
