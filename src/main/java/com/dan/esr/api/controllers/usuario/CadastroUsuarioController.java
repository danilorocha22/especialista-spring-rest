package com.dan.esr.api.controllers.usuario;

import com.dan.esr.api.models.input.usuario.UsuarioAtualizadoInput;
import com.dan.esr.api.models.input.usuario.UsuarioInput;
import com.dan.esr.api.models.input.usuario.UsuarioSenhaInput;
import com.dan.esr.api.models.output.usuario.UsuarioGruposOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.dan.esr.core.assemblers.UsuarioAssembler;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.usuario.UsuarioCadastroService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class CadastroUsuarioController {
    private final UsuarioCadastroService usuarioCadastro;
    private final UsuarioConsultaService usuarioConsulta;
    private final UsuarioAssembler usuarioAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioOutput novoUsuario(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioInput);
        this.usuarioCadastro.validarUsuarioComEmailJaCadastrado(usuario);
        return this.usuarioAssembler.toModel(
                this.usuarioCadastro.salvarOuAtualizar(usuario)
        );
    }

    @PutMapping("/{id}")
    public UsuarioOutput atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioAtualizadoInput usuarioAtualizado
    ) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioAtualizado);
        usuario.setId(id);
        this.usuarioCadastro.validarUsuarioComEmailJaCadastrado(usuario);

        Usuario usuarioRegistro = this.usuarioConsulta.buscarPor(id);
        this.usuarioAssembler.copyToDomain(usuarioAtualizado, usuarioRegistro);

        return this.usuarioAssembler.toModel(
                this.usuarioCadastro.salvarOuAtualizar(usuarioRegistro)
        );
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioSenhaInput usuarioNovaSenha
    ) {
        Usuario usuario = this.usuarioAssembler.toDomain(usuarioNovaSenha);
        usuario.setId(id);
        this.usuarioCadastro.atualizarSenha(usuario);
    }

    @PutMapping("/{usuarioId}/grupos/{grupoId}")
    public UsuarioGruposOutput adicionarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        Usuario usuario = this.usuarioCadastro.adicionarGrupo(usuarioId, grupoId);
        return this.usuarioAssembler.toModelUsuarioGrupos(usuario);
    }

    @DeleteMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UsuarioGruposOutput removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        Usuario usuario = this.usuarioCadastro.removerGrupo(usuarioId, grupoId);
        return this.usuarioAssembler.toModelUsuarioGrupos(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirUsuario(@PathVariable Long id) {
        this.usuarioCadastro.remover(id);
    }
}