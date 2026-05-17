CREATE TABLE resenas(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    perfume_id BIGINT NOT NULL,
    calificacion INT NOT NULL,
    comentario VARCHAR(500) NOT NULL,
    fecha_publicacion DATETIME(6)
);