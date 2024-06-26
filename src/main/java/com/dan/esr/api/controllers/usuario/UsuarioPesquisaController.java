package com.dan.esr.api.controllers.usuario;

import com.dan.esr.api.models.output.usuario.UsuarioGruposOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.openapi.documentation.usuario.UsuarioPesquisaDocumentation;
import com.dan.esr.core.assemblers.UsuarioAssembler;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarCampoObrigatorio;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/usuarios", produces = APPLICATION_JSON_VALUE)
public class UsuarioPesquisaController implements UsuarioPesquisaDocumentation {
    private final UsuarioConsultaService usuarioConsulta;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @GetMapping("/{id}")
    public UsuarioOutput usuario(@PathVariable Long id) {
        Usuario usuario = this.usuarioConsulta.buscarPor(id);
        return this.usuarioAssembler.toModel(usuario);
    }

    @Override
    @GetMapping("/{usuarioId}/grupos")
    public UsuarioGruposOutput usuarioGrupos(@PathVariable("usuarioId") Long id) {
        Usuario usuario = this.usuarioConsulta.buscarPor(id);
        return this.usuarioAssembler.toModelUsuarioGrupos(usuario);
    }

    @Override
    @GetMapping("/primeiro")
    public UsuarioOutput primeiroUsuario() {
        Usuario usuario = this.usuarioConsulta.buscarPrimeiro();
        return this.usuarioAssembler.toModel(usuario);
    }

    @Override
    @GetMapping("/primeiro-com-nome-semelhante")
    public UsuarioOutput primeiroUsuarioComNomeSemelhante(@RequestParam String nome) {
        validarCampoObrigatorio(nome, "nome");
        Usuario usuario = this.usuarioConsulta.buscarPrimeiroComNomeSemelhante(nome);
        return this.usuarioAssembler.toModel(usuario);
    }

    @Override
    @GetMapping
    public List<UsuarioOutput> usuarios() {
        List<Usuario> usuarios = this.usuarioConsulta.buscarTodos();
        return this.usuarioAssembler.toCollectionModel(usuarios);
    }
}