CREATE TABLE tiendas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    comuna VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE ofertas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto BIGINT NOT NULL,
    id_tienda BIGINT NOT NULL,
    precio_normal INT NOT NULL,
    precio_oferta INT,
    stock_disponible INT DEFAULT 0,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tienda FOREIGN KEY (id_tienda) REFERENCES tiendas(id)
);

CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_perfume BIGINT NOT NULL,
    fecha_venta DATETIME(6),
    total INT NOT NULL,
    metodo_pago VARCHAR(50),
    estado_pago VARCHAR(50)
);