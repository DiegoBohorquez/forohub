package com.alura.foro.controller;

import com.alura.foro.ValidacionException;
import com.alura.foro.models.respuestas.*;
import com.alura.foro.models.topico.TopicoRepository;
import com.alura.foro.models.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
@RequestMapping("/respuestas")
public class RespuestasController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosListadoRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
                                                                    UriComponentsBuilder uriComponentsBuilder) {
        if(!topicoRepository.existsById(datosRegistroRespuesta.idTopico())) {
            throw new ValidacionException("El Topico no existe");
        }

        if (!usuarioRepository.existsById(datosRegistroRespuesta.idAutor())) {
            throw new ValidacionException("El Usuario no existe");
        }

        var topico = topicoRepository.findById(datosRegistroRespuesta.idTopico()).get();
        var usuario = usuarioRepository.findById(datosRegistroRespuesta.idAutor()).get();

        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta, topico, usuario));
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosListadoRespuesta(respuesta));

    }

    @GetMapping
    public Page<DatosListadoRespuesta> listarRespuestas(@PageableDefault(size = 5) Pageable pageable) {
        return respuestaRepository.findAll(pageable)
                .map(DatosListadoRespuesta::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoRespuesta> obtenerRespuesta(@PathVariable Long id)  {
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);

        if(respuesta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosListadoRespuesta(respuesta.get()));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosListadoRespuesta> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        respuesta.actualizarRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosListadoRespuesta(respuesta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
