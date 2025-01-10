package com.alura.foro.models.usuarios;

import com.alura.foro.models.perfiles.Perfil;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Table(name = "usuarios")
@Entity(name = "usuario")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String contra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    public Usuario() {}
    public Usuario(DatosRegistroUsuario datosRegistroUsuario, Perfil perfil ) {
        this.nombre = datosRegistroUsuario.nombre();
        this.email = datosRegistroUsuario.email();
        this.contra = datosRegistroUsuario.contra();
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getContra() {
        return contra;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario) {
        if(datosActualizarUsuario.nombre() != null) {
            this.nombre = datosActualizarUsuario.nombre();
        }

        if (datosActualizarUsuario.contra() != null) {
            this.contra = datosActualizarUsuario.contra();
        }

    }
}
