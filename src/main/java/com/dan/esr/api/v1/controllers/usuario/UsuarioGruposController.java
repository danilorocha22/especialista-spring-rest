package com.dan.esr.api.v1.controllers.usuario;

import com.dan.esr.api.v1.assemblers.UsuarioAssembler;
import com.dan.esr.api.v1.models.output.usuario.UsuarioGruposOutput;
import com.dan.esr.api.v1.openapi.documentation.usuario.UsuarioGruposDocumentation;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.usuario.UsuarioCadastroService;
import com.dan.esr.domain.services.usuario.UsuarioConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioGruposController implements UsuarioGruposDocumentation {
    private final UsuarioCadastroService usuarioCadastroService;
    private final UsuarioConsultaService usuarioConsultaService;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @GetMapping("/{usuarioId}/grupos")
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public EntityModel<UsuarioGruposOutput> gruposDoUsuario(@PathVariable("usuarioId") Long id) {
        Usuario usuario = this.usuarioConsultaService.buscarPor(id);
        return EntityModel.of(
                this.usuarioAssembler.toModelUsuarioGrupos(usuario)
        );
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @PutMapping("/{id}/grupos/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void adicionarUsuarioNoGrupo(@PathVariable("id") Long usuarioId, @PathVariable("id") Long grupoId) {
        this.usuarioCadastroService.adicionarNoGrupo(usuarioId, grupoId);
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}/grupos/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void removerDoGrupo(@PathVariable("id") Long usuarioId, @PathVariable("id") Long grupoId) {
        this.usuarioCadastroService.removerDoGrupo(usuarioId, grupoId);
    }
}