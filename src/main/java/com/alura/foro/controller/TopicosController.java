package com.alura.foro.controller;

import com.alura.foro.infra.errores.ValidacionException;
import com.alura.foro.models.cursos.CursoRepository;
import com.alura.foro.models.topico.*;
import com.alura.foro.models.usuarios.UsuarioRepository;
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
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosListadoTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Boolean existeTopico = topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje());

        if(!usuarioRepository.existsById(datosRegistroTopico.idAutor())){
            throw new ValidacionException("Autor no encontrado");
        }

        if(!cursoRepository.existsById(datosRegistroTopico.idCurso())) {
            throw new ValidacionException("Curso no encontrado");
        }

        if(existeTopico) {
            throw new ValidacionException("Topico ya existe");
        }

        var usuario = usuarioRepository.findById(datosRegistroTopico.idAutor()).get();
        var curso = cursoRepository.findById(datosRegistroTopico.idCurso()).get();

        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, usuario, curso));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoTopico(topico));

    }

    @GetMapping
    public Page<DatosListadoTopico> listarTopicos(@PageableDefault(size = 5) Pageable pageable) {
        return topicoRepository.findByEstado(Status.ACTIVO,pageable)
                .map(DatosListadoTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> buscarTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosListadoTopico(topico.get()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(new DatosListadoTopico(topico));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }
}
