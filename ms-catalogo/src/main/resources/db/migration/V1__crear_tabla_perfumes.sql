CREATE TABLE perfumes(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    precio INT NOT NULL,
    descripcion VARCHAR(255),
    stock INT NOT NULL
);