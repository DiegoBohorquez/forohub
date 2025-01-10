package com.alura.foro.models.respuestas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long idTopico,
        @NotNull
        @Future
        LocalDateTime fechaCreacion,
        @NotNull
        Long idAutor
) {
}
