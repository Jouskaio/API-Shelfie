package com.shelfie.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration
 * Basic configuration for development - should be enhanced for production
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API usage
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/v1/**").permitAll() // Allow all API endpoints
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Allow Swagger UI
                .requestMatchers("/h2-console/**").permitAll() // Allow H2 console for development
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions().disable()); // For H2 console
            
        return http.build();
    }
}