package com.alura.foro.models.respuestas;


import com.alura.foro.models.topico.Topico;
import com.alura.foro.models.usuarios.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "respuesta")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topico")
    private Topico topico;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Usuario usuario;

    public Respuesta() {}

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta, Topico topico, Usuario usuario) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.topico = topico;
        this.fechaCreacion = datosRegistroRespuesta.fechaCreacion();
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Topico getTopico() {
        return topico;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta) {
        this.mensaje = datosActualizarRespuesta.mensaje();
    }
}