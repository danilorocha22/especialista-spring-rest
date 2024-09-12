package com.dan.esr.api.v1.controllers.usuario;

import com.dan.esr.api.v1.models.input.usuario.UsuarioAtualizadoInput;
import com.dan.esr.api.v1.models.input.usuario.UsuarioInput;
import com.dan.esr.api.v1.models.input.usuario.UsuarioSenhaInput;
import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.v1.openapi.documentation.usuario.UsuarioCadastroDocumentation;
import com.dan.esr.api.v1.assemblers.UsuarioAssembler;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.usuario.UsuarioCadastroService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioCadastroController implements UsuarioCadastroDocumentation {
    private final UsuarioCadastroService usuarioCadastro;
    private final UsuarioConsultaService usuarioConsulta;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public UsuarioOutput novoUsuario(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioInput);
        return this.usuarioAssembler.toModel(
                this.usuarioCadastro.salvarOuAtualizar(usuario)
        );
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UsuarioOutput atualizarUsuario(
            @PathVariable("id") Long usuarioId,
            @RequestBody @Valid UsuarioAtualizadoInput usuarioAtualizado
    ) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioAtualizado);
        usuario.setId(usuarioId);
        this.usuarioCadastro.validarUsuarioComEmailJaCadastrado(usuario);

        Usuario usuarioRegistro = this.usuarioConsulta.buscarPor(usuarioId);
        this.usuarioAssembler.copyToDomain(usuarioAtualizado, usuarioRegistro);

        return this.usuarioAssembler.toModel(
                this.usuarioCadastro.salvarOuAtualizar(usuarioRegistro)
        );
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{id}/senha", produces = APPLICATION_JSON_VALUE)
    public void atualizarSenha(
            @PathVariable("id") Long usuarioId,
            @RequestBody @Valid UsuarioSenhaInput usuarioNovaSenha
    ) {
        String senhaAtual = usuarioNovaSenha.getSenha();
        System.out.printf("Senha atual bruta: %s%n", senhaAtual);
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioNovaSenha);
        usuario.setId(usuarioId);
        this.usuarioCadastro.atualizarSenha(usuario);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") Long usuarioId) {
        this.usuarioCadastro.remover(usuarioId);
    }
}