package com.dan.esr.api.v1.controllers.usuario;

import com.dan.esr.api.v1.openapi.documentation.usuario.UsuarioGruposDocumentation;
import com.dan.esr.domain.services.usuario.UsuarioCadastroService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioGruposController implements UsuarioGruposDocumentation {
    private final UsuarioCadastroService usuarioCadastroService;
    private final UsuarioConsultaService usuarioConsultaService;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @PutMapping("/{id}/grupos/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void adicionarUsuarioNoGrupo(@PathVariable("id") Long usuarioId, @PathVariable("id") Long grupoId) {
        this.usuarioCadastroService.adicionarNoGrupo(usuarioId, grupoId);
    }

    @Override
    @DeleteMapping("/{id}/grupos/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void removerDoGrupo(@PathVariable("id") Long usuarioId, @PathVariable("id") Long grupoId) {
        this.usuarioCadastroService.removerDoGrupo(usuarioId, grupoId);
    }
}