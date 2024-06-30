package com.dan.esr;

import com.dan.esr.infrastructure.repositories.impl.CustomBaseJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.TimeZone;

//@EnableWebMvc // Necessária para o funcionamento do SpringFox 3.0.0
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomBaseJpaRepositoryImpl.class) // Habilita o repositório customizado
public class EspecialistaSpringRestApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));// Configura o horário default da aplicação para UTC
		SpringApplication.run(EspecialistaSpringRestApplication.class, args);
	}
}