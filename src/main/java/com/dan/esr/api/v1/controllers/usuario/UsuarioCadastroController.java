package com.dan.esr.api.v1.controllers.usuario;

import com.dan.esr.api.v1.models.input.usuario.UsuarioAtualizadoInput;
import com.dan.esr.api.v1.models.input.usuario.UsuarioInput;
import com.dan.esr.api.v1.models.input.usuario.UsuarioSenhaInput;
import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.v1.openapi.documentation.usuario.UsuarioCadastroDocumentation;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.usuario.UsuarioCadastroService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioCadastroController implements UsuarioCadastroDocumentation {
    private final UsuarioCadastroService usuarioCadastroService;
    private final UsuarioConsultaService usuarioConsultaService;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public UsuarioOutput novoUsuario(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioInput);
        return this.usuarioAssembler.toModel(
                this.usuarioCadastroService.salvarOuAtualizar(usuario)
        );
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UsuarioOutput atualizarUsuario(
            @PathVariable("id") Long usuarioId,
            @RequestBody @Valid UsuarioAtualizadoInput usuarioAtualizado
    ) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioAtualizado);
        usuario.setId(usuarioId);
        this.usuarioCadastroService.validarUsuarioComEmailJaCadastrado(usuario);
        Usuario usuarioRegistro = this.usuarioConsultaService.buscarPor(usuarioId);
        this.usuarioAssembler.copyToDomain(usuarioAtualizado, usuarioRegistro);
        return this.usuarioAssembler.toModel(
                this.usuarioCadastroService.salvarOuAtualizar(usuarioRegistro)
        );
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping(path = "/{id}/senha", produces = APPLICATION_JSON_VALUE)
    public void atualizarSenha(
            @PathVariable("id") Long usuarioId,
            @RequestBody @Valid UsuarioSenhaInput usuarioNovaSenha
    ) {
        String senhaAtual = usuarioNovaSenha.getSenha();
        System.out.printf("Senha atual bruta: %s%n", senhaAtual);
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioNovaSenha);
        usuario.setId(usuarioId);
        this.usuarioCadastroService.atualizarSenha(usuario);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void excluir(@PathVariable("id") Long usuarioId) {
        this.usuarioCadastroService.remover(usuarioId);
    }
}