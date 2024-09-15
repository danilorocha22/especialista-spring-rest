package com.dan.esr.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "v1/cozinhas/**").hasAuthority("EDITAR_COZINHA")
                    .antMatchers(HttpMethod.PUT, "v1/cozinhas/**").hasAuthority("EDITAR_COZINHA")
                    .antMatchers(HttpMethod.GET, "v1/cozinhas/**").authenticated()
                    .anyRequest().denyAll()
                .and()*/
                .csrf().disable()
                .cors() //configurando cors a nível de spring security para permitir verbo http Options
                .and()
                    .oauth2ResourceServer()
                        .jwt()//.opaqueToken();
                        .jwtAuthenticationConverter(getJwtAuthConverter());
    }

    //Configurando o JwtAuthenticationConverter para converter as permissoes de um token jwt
    private JwtAuthenticationConverter getJwtAuthConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                authorities = List.of();
            }

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());
        });

        return jwtAuthenticationConverter;
    }

    //Configurando o JwtDecoder para chave simétrica
    /*@Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec("ds41f60as587f1afs1d3.SF748A13-1GD1A31GKa3s184q9".getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }*/
}