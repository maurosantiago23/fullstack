CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre_completo VARCHAR(150) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(100) NOT NULL,
                          rol VARCHAR(50) NOT NULL
);