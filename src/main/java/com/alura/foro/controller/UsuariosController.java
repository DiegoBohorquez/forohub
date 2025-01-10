package com.alura.foro.controller;

import com.alura.foro.ValidacionException;
import com.alura.foro.models.cursos.DatosActualizarCurso;
import com.alura.foro.models.cursos.DatosRegistroCurso;
import com.alura.foro.models.perfiles.PerfilRepository;
import com.alura.foro.models.usuarios.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<DatosListadoUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        if(!perfilRepository.existsById(datosRegistroUsuario.idPerfil())){
            throw new ValidacionException("Perfil no encontrado");
        }
        var perfil = perfilRepository.findById(datosRegistroUsuario.idPerfil()).get();
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario, perfil));
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoUsuario(usuario));

    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> listarUsuarios(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(usuarioRepository.findAll(pageable)
                .map(DatosListadoUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuario> obtenerUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosListadoUsuario(usuario.get()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoUsuario> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());
        usuario.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(new DatosListadoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}
