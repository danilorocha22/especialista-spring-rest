package com.dan.esr.core.web;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ApiDeprecationHandler apiDeprecationHandler;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// Permite qualquer request origen
                .allowedMethods("*"); // Permite qualquer método http
                //.allowedOrigins("*") // Permite qualquer request origen
                //.maxAge(30); // Tempo máximo de cache em segundos
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiDeprecationHandler);
    }

    /*@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(DanFoodMediaTypes.V2_APPLICATION_JSON);
    }*/

    /*
    * Registro do filtro para Entity Tag
    * */
    @Bean
    public Filter shawllowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}