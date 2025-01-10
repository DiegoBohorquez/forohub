package com.alura.foro.models.usuarios;

import com.alura.foro.models.perfiles.Perfil;

public record DatosListadoUsuario(
        String nombre,
        String email,
        String perfil
) {

    public DatosListadoUsuario (Usuario usuario){
        this(usuario.getNombre(), usuario.getEmail(), usuario.getPerfil().getNombre());
    }
}
