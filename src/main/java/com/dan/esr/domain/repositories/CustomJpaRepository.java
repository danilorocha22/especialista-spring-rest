package com.dan.esr.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

//Esta anotação informa ao Spring para não estanciá-la
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {


    Optional<T> buscarPrimeiro();
}
