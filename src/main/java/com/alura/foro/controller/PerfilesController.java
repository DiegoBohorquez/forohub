package com.alura.foro.controller;

import com.alura.foro.models.perfiles.*;
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
import java.util.Optional;


@RestController
@RequestMapping("/perfiles")
public class PerfilesController {

    @Autowired
    PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<DatosListadoPerfil> registrarPerfil(@RequestBody @Valid DatosRegistroPerfil datosRegistroPerfil,
                                                              UriComponentsBuilder uriComponentsBuilder) {
       Perfil perfil = perfilRepository.save(new Perfil(datosRegistroPerfil));
       URI url = uriComponentsBuilder.path("/perfiles/{id}").buildAndExpand(perfil.getId()).toUri();
       return ResponseEntity.created(url).body(new DatosListadoPerfil(perfil));
    }

    @GetMapping
    public Page<DatosListadoPerfil> listarPerfiles(@PageableDefault(size = 5) Pageable pageable) {
        return perfilRepository.findAll(pageable).map(DatosListadoPerfil::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoPerfil> obtenerPerfil(@PathVariable Long id) {
        Optional<Perfil> perfil = perfilRepository.findById(id);

        if(perfil.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DatosListadoPerfil(perfil.get()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoPerfil> actualizarPerfil(@RequestBody @Valid DatosActualizarPerfil datosActualizarPerfil) { // clase creada por si se cambian los atributos de perfil elegir cual  actualziar
        Perfil perfil = perfilRepository.getReferenceById(datosActualizarPerfil.id());
        perfil.actualizarPerfil(datosActualizarPerfil);
        return ResponseEntity.ok(new DatosListadoPerfil(perfil));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPerfil(@PathVariable Long id) {
        Perfil perfil = perfilRepository.getReferenceById(id);
        perfilRepository.delete(perfil);
        return ResponseEntity.noContent().build();

    }
}
