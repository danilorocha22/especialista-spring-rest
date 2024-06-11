package com.dan.esr.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

//Esta anotação informa ao Spring para não estanciá-la
@NoRepositoryBean
public interface CustomBaseJpaRepository<T, ID> extends JpaRepository<T, ID> {
    /*****************   CRUD   ****************/
    Optional<T> salvarOuAtualizar(T t);

    //void remover(Long id);

    Optional<Integer> remover(Long id);

    //void remover(T t);

    /*****************   CONSULTAS   ****************/
    Optional<T> com(Long id);

    Optional<T> comNomeIgual(String nome);
    
    List<T> comNomeSemelhante(String nome);

    Optional<T> primeiro();

    Optional<T> primeiroComNomeSemelhante(String nome);

    List<T> top2ComNomeSemelhante(String nome);

    List<T> todos();
}
