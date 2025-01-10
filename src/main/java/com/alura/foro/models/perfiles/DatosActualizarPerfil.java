package com.alura.foro.models.perfiles;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarPerfil(
        @NotNull
        Long id,
        String nombre
) {
}
