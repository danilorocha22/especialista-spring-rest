package com.dan.esr;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.services.CadastroCozinhaService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    public void sucessoCadastroCozinha() {
        //Os testes são divididos em três fases:
        //cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //ação
        novaCozinha = cadastroCozinha.salvarOuAtualizar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test()
    public void falhaCadastroCozinha_SemNome() {
        //validação
        assertThrows(ConstraintViolationException.class, () -> {
            //cenário
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(null);

            //ação
            cadastroCozinha.salvarOuAtualizar(novaCozinha);
        });
    }

}
