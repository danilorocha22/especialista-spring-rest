package com.dan.esr.api.controllers.usuario;

import com.dan.esr.api.openapi.documentation.usuario.UsuarioGruposDocumentation;
import com.dan.esr.domain.services.usuario.UsuarioCadastroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioGruposController implements UsuarioGruposDocumentation {
    private final UsuarioCadastroService usuarioCadastro;

    @Override
    @PutMapping("/{id}/grupos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarGrupo(@PathVariable("id") Long usuarioId, @PathVariable("id") Long grupoId) {
        this.usuarioCadastro.adicionarGrupo(usuarioId, grupoId);
    }

    @Override
    @DeleteMapping("/{id}/grupos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerGrupo(@PathVariable("id") Long usuarioId, @PathVariable("id") Long grupoId) {
        this.usuarioCadastro.removerGrupo(usuarioId, grupoId);
    }
}