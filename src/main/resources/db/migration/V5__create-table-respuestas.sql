CREATE TABLE respuestas
(
    id INT AUTO_INCREMENT NOT NULL,
    mensaje VARCHAR(100) NOT NULL,
    id_topico INT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    id_autor INT NOT NULL,
    CONSTRAINT pk_respuestas PRIMARY KEY(id),
    CONSTRAINT fk_respuestas_topicos FOREIGN KEY(id_topico) REFERENCES topicos(id),
    CONSTRAINT fk_respuestas_usuarios FOREIGN KEY(id_autor) REFERENCES usuarios(id)
)