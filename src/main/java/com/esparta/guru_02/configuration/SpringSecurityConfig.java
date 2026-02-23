package com.esparta.guru_02.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

/*
 * Author: M
 * Date: 16-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Configuration
public class SpringSecurityConfig {

    /*
    // Configure to disable CSRF for API endpoints and allow basic authentication for testing purposes
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Disable CSRF for API endpoints
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }
    */
    // Enable OAuth2 Resource Server support with JWT validation for API endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
}
