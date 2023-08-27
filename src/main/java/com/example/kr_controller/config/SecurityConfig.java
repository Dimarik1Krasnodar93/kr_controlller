package com.example.kr_controller.config;

import com.example.kr_controller.security.Exception404Filter;
import com.example.kr_controller.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final Exception404Filter filter;
    @Bean
    public SecurityFilterChain securityFilterChainOAuth(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer
                                .logoutUrl("/logout")
                                .deleteCookies("JWT")
                                .invalidateHttpSession(true))
                .authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry
                        -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/hello").permitAll()
                        .requestMatchers("/404").permitAll()
                        .anyRequest().authenticated());
        return httpSecurity.build();
    }
}
