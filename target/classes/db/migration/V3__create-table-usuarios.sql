CREATE TABLE usuarios
(
    id INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    email VARCHAR(60) NOT NULL,
    contra VARCHAR(30) NOT NULL,
    id_perfil INT NOT NULL,
    CONSTRAINT pk_usuarios PRIMARY KEY(id),
    CONSTRAINT fk_usuarios_perfiles FOREIGN KEY(id_perfil) REFERENCES perfiles(id),
    CONSTRAINT uk_usuarios_email UNIQUE(email)
)