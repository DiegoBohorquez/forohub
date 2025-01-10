package com.alura.foro.models.perfiles;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroPerfil(
        @NotBlank
        String nombre
) {
}
