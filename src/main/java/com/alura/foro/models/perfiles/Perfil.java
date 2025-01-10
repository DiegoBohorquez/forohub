package com.alura.foro.models.perfiles;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Table(name = "perfiles")
@Entity(name = "perfil")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    public Perfil() {}

    public Perfil(DatosRegistroPerfil datosRegistroPerfil) {
        this.nombre = datosRegistroPerfil.nombre();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void actualizarPerfil(DatosActualizarPerfil datosActualizarPerfil) {
        this.nombre = datosActualizarPerfil.nombre();
    }
}
