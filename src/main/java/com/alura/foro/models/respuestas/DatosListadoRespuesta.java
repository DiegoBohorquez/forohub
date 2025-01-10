package com.alura.foro.models.respuestas;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(
        Long id,
        String mensaje,
        String topico,
        LocalDateTime fechaCreacion,
        String autor
) {
    public DatosListadoRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getTitulo(),
                respuesta.getFechaCreacion(), respuesta.getUsuario().getNombre());
    }
}
