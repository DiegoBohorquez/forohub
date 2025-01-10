CREATE TABLE topicos
(
    id             INT AUTO_INCREMENT NOT NULL,
    titulo         VARCHAR(30)  NOT NULL,
    mensaje        VARCHAR(100) NOT NULL,
    fecha_creacion DATETIME     NOT NULL,
    estado         ENUM('ACTIVO', 'INACTIVO') NOT NULL,
    id_autor       INT          NOT NULL,
    id_curso       INT          NOT NULL,
    CONSTRAINT pk_topicos PRIMARY KEY (id),
    CONSTRAINT fk_topicos_usuarios FOREIGN KEY (id_autor) REFERENCES usuarios (id),
    CONSTRAINT fk_topicos_cursos FOREIGN KEY (id_curso) REFERENCES cursos (id)

)