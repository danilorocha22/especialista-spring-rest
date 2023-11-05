package com.dan.esr;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.services.CadastroCozinhaService;
import com.dan.esr.domain.services.CadastroRestauranteService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    //Os testes são divididos em três fases: Cenário, Ação e Validação

    @Test
    public void sucesso_CadastroCozinhaCorretamente() {
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
    public void falha_CadastroCozinha_ComNomeNulo() {
        //validação quando erro
        assertThrows(ConstraintViolationException.class, () -> {

            //cenário
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(null);

            //ação
            cadastroCozinha.salvarOuAtualizar(novaCozinha);
        });
    }

    @Test
    public void falha_CadastroCozinha_ComNomeVazio() {
        //validação quando erro
        assertThrows(ConstraintViolationException.class, () -> {
            //cenário
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(" ");

            //ação
            cadastroCozinha.salvarOuAtualizar(novaCozinha);
        });
    }

    @Test
    public void falha_QuandoExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome("Havaiana");
            novaCozinha = this.cadastroCozinha.salvarOuAtualizar(novaCozinha);
            System.out.println("ID da nova cozinha"+novaCozinha.getId());

            Restaurante restaurante = new Restaurante();
            restaurante.setNome("Restaurante Bom de Prato");
            restaurante.setTaxaFrete(new BigDecimal(15));
            restaurante.setCozinha(novaCozinha);
            this.cadastroRestaurante.salvarOuAtualizar(restaurante);

            this.cadastroCozinha.remover(novaCozinha.getId());
        });
    }

    @Test
    public void falha_QuandoExcluirCozinhaInexistente() {
        assertThrows(CozinhaNaoEncontradaException.class, () ->
            this.cadastroCozinha.remover(10L)
        );
    }

}
