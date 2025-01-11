package com.alura.foro.controller;

import com.alura.foro.models.cursos.DatosActualizarCurso;
import com.alura.foro.models.cursos.Curso;
import com.alura.foro.models.cursos.CursoRepository;
import com.alura.foro.models.cursos.DatosListadoCurso;
import com.alura.foro.models.cursos.DatosRegistroCurso;
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
@RequestMapping("/cursos")
public class CursosController {

    @Autowired
    CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosListadoCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso,
                                         UriComponentsBuilder uriComponentsBuilder) {
        Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoCurso(curso));
    }

    @GetMapping
    public Page<DatosListadoCurso> listarCursos(@PageableDefault(size = 5) Pageable pageable) {
        return cursoRepository.findAll(pageable).map(DatosListadoCurso::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoCurso> buscarCursoPorId(@PathVariable Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);

        if(curso.isEmpty()) {
           return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosListadoCurso(curso.get()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoCurso> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
        Curso curso = cursoRepository.getReferenceById(datosActualizarCurso.id());
        curso.actualizarCurso(datosActualizarCurso);
        return ResponseEntity.ok(new DatosListadoCurso(curso));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarCurso(@PathVariable Long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        cursoRepository.delete(curso);
        return ResponseEntity.noContent().build();
    }

}
