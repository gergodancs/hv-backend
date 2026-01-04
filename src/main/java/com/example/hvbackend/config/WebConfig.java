package com.example.hvbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Minden /api kezdetű útvonalra
                        .allowedOrigins("http://localhost:4200") // Az Angular szervered címe
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Engedélyezett műveletek
                        .allowedHeaders("*") // Minden header (Authorization stb.)
                        .allowCredentials(true); // Sütik vagy Auth tokenek engedélyezése
            }
        };
    }
}
