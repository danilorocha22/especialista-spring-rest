package com.dan.esr.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/*
    LocalValidatorFactoryBean é uma classe que faz a configuração e
    integração entre o BeanValidation e Spring Framework para validar
    e usar o mesmo arquivo de messages.properties
*/
@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(MessageSource message) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(message);
        return factoryBean;
    }

}
