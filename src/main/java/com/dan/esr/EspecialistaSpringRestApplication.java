package com.dan.esr;

import com.dan.esr.infrastructure.repositories.impl.CustomBaseJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomBaseJpaRepositoryImpl.class)
public class EspecialistaSpringRestApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(EspecialistaSpringRestApplication.class, args);
	}

}
