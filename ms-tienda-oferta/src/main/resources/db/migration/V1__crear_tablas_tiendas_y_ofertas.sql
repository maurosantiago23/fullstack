CREATE TABLE tiendas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_tienda VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    comuna VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE ofertas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    perfume_id BIGINT NOT NULL,
    tienda_id BIGINT NOT NULL,
    precio_normal INT NOT NULL,
    precio_oferta INT,
    stock_disponible INT DEFAULT 0,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tienda FOREIGN KEY (tienda_id) REFERENCES tiendas(id)
);

CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    perfume_id BIGINT NOT NULL,
    fecha_venta DATETIME(6),
    total INT NOT NULL,
    metodo_pago VARCHAR(50),
    estado_pago VARCHAR(50)
);