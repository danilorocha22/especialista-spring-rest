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

    void remover(Long id);

    void remover(T t);

    /*****************   CONSULTAS   ****************/
    Optional<T> buscarPor(Long id);

    Optional<T> buscarPorNomeIgual(String nome);
    
    List<T> buscarPorNomeSemelhante(String nome);

    Optional<T> buscarPrimeira();

    Optional<T> buscarPrimeiraComNomeContendo(String nome);

    List<T> buscarTop2ComNomeContendo(String nome);

    List<T> buscarTodos();

    boolean existeRegistroCom(String nome);

}
