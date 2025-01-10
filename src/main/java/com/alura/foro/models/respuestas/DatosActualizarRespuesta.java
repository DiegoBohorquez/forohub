package com.alura.foro.models.respuestas;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(
        @NotNull
        Long id,
        String mensaje
) {
}
