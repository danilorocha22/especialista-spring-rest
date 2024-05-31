package com.dan.esr.domain.services.restaurante;

import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoEncontradoException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestauranteResponsavelService {
    private final RestauranteConsultaService restauranteConsulta;
    private final UsuarioConsultaService usuarioConsulta;
    private final RestauranteRepository restauranteRepository;

    @Transactional
    public Restaurante adicionarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Usuario responsavel = this.usuarioConsulta.buscarPor(usuarioId);
        restaurante.adicionar(responsavel);
        return restaurante;
    }

    @Transactional
    public Restaurante removerResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Usuario responsavel = this.usuarioConsulta.buscarPor(usuarioId);
        restaurante.remover(responsavel);
        return restaurante;
    }

    public Restaurante buscarComResponsaveis(Long id) {
        Restaurante restaurante = this.restauranteRepository.buscarComUsuariosResponsaveis(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));

        restaurante.validarResponsaveis();
        return restaurante;
    }
}
