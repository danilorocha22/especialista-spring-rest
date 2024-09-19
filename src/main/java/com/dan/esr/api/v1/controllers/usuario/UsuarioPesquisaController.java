package com.dan.esr.api.v1.controllers.usuario;

import com.dan.esr.api.v1.assemblers.UsuarioAssembler;
import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.v1.openapi.documentation.usuario.UsuarioPesquisaDocumentation;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static com.dan.esr.core.util.ValidacaoUtil.validarCampoObrigatorio;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/usuarios", produces = APPLICATION_JSON_VALUE)
public class UsuarioPesquisaController implements UsuarioPesquisaDocumentation {
    private final UsuarioConsultaService usuarioConsulta;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @GetMapping("/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultarUsuario
    public EntityModel<UsuarioOutput> usuario(@PathVariable Long id) {
        Usuario usuario = this.usuarioConsulta.buscarPor(id);
        return EntityModel.of(
                this.usuarioAssembler.toModel(usuario)
        );
    }

    @Override
    @GetMapping("/primeiro")
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public UsuarioOutput primeiroUsuario() {
        Usuario usuario = this.usuarioConsulta.buscarPrimeiro();
        return this.usuarioAssembler.toModel(usuario);
    }

    @Override
    @GetMapping("/primeiro-com-nome-semelhante")
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public UsuarioOutput primeiroUsuarioComNomeSemelhante(@RequestParam String nome) {
        validarCampoObrigatorio(nome, "nome");
        Usuario usuario = this.usuarioConsulta.buscarPrimeiroComNomeSemelhante(nome);
        return this.usuarioAssembler.toModel(usuario);
    }

    @Override
    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public CollectionModel<UsuarioOutput> usuarios() {
        return this.usuarioAssembler.toCollectionModel(
                this.usuarioConsulta.buscarTodos()
        );
    }
}