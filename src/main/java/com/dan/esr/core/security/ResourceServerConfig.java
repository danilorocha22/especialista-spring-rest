package com.dan.esr.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .cors() //configurando cors a nível de spring security para permitir verbo http Options
                .and()
                    .oauth2ResourceServer().jwt(); //.opaqueToken();
    }

    //Configurando o JwtDecoder para chave simétrica
    /*@Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec("ds41f60as587f1afs1d3.SF748A13-1GD1A31GKa3s184q9".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }*/
}