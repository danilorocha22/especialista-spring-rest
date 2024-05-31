package com.dan.esr.domain.services.restaurante;

import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestauranteFormaPagamentoService {
    private final RestauranteConsultaService restauranteConsulta;
    private final FormaPagamentoService formaPagamentoService;

    @Transactional
    public Restaurante adicionarFormaPagamento(Long restauranteId, Long formasPagamentoId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(formasPagamentoId);
        restaurante.adicionar(formaPagamento);
        return restaurante;
    }

    @Transactional
    public Restaurante removerFormaPagamento(Long restauranteId, Long formasPagamentoId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(formasPagamentoId);
        restaurante.remover(formaPagamento);
        return restaurante;
    }
}
