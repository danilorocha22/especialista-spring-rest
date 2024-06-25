package com.dan.esr.core.web;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// Permite qualquer request origen
                .allowedMethods("*"); // Permite qualquer método http
                //.allowedOrigins("*") // Permite qualquer request origen
                //.maxAge(30); // Tempo máximo de cache em segundos

    }

    /*
    * Registro do filtro para Entity Tag
    * */
    @Bean
    public Filter shawllowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}