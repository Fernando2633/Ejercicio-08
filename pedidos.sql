-- Script opcional para MySQL (la aplicacion crea la tabla sola con JPA)
-- Fernando Antonio Garcia Ruiz - 7CM1 - Ejercicio 08

CREATE DATABASE IF NOT EXISTS wc_pedidos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wc_pedidos;

CREATE TABLE IF NOT EXISTS pedidos (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    codigo        VARCHAR(50)   NOT NULL,
    fecha         DATE          NOT NULL,
    total         DECIMAL(10,2) NOT NULL,
    estado        VARCHAR(30)   NOT NULL,
    observaciones VARCHAR(255),
    CONSTRAINT pk_pedidos PRIMARY KEY (id),
    CONSTRAINT uk_pedidos_codigo UNIQUE (codigo)
);

INSERT INTO pedidos (codigo, fecha, total, estado, observaciones) VALUES
('PED-0001', '2026-10-10', 1250.50, 'Pendiente', 'Entregar en horario matutino'),
('PED-0002', '2026-10-12',  899.00, 'Enviado',   'Paquete frágil'),
('PED-0003', '2026-10-15', 3420.75, 'Entregado', NULL);
