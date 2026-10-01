CREATE DATABASE IF NOT EXISTS ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecommerce;

CREATE TABLE usuarios (
 id_usuario INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100) NOT NULL,
 correo VARCHAR(150) NOT NULL UNIQUE,
 contrasena VARCHAR(255) NOT NULL,
 rol ENUM('CLIENTE','ADMIN') NOT NULL DEFAULT 'CLIENTE',
 fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE productos (
 id_producto INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(120) NOT NULL,
 descripcion VARCHAR(255) NOT NULL,
 precio DECIMAL(12,2) NOT NULL,
 stock INT NOT NULL DEFAULT 0,
 activo BOOLEAN NOT NULL DEFAULT TRUE,
 CONSTRAINT chk_precio CHECK(precio>0),
 CONSTRAINT chk_stock CHECK(stock>=0)
);

CREATE TABLE pedidos (
 id_pedido INT AUTO_INCREMENT PRIMARY KEY,
 id_usuario INT NOT NULL,
 fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 estado ENUM('PENDIENTE','PAGADO','ENVIADO','ENTREGADO','CANCELADO') DEFAULT 'PENDIENTE',
 total DECIMAL(12,2) NOT NULL DEFAULT 0,
 FOREIGN KEY(id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE detalle_pedido (
 id_detalle INT AUTO_INCREMENT PRIMARY KEY,
 id_pedido INT NOT NULL,
 id_producto INT NOT NULL,
 cantidad INT NOT NULL,
 precio_unitario DECIMAL(12,2) NOT NULL,
 subtotal DECIMAL(12,2) NOT NULL,
 FOREIGN KEY(id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
 FOREIGN KEY(id_producto) REFERENCES productos(id_producto)
);

INSERT INTO productos(nombre,descripcion,precio,stock) VALUES
('Audífonos inalámbricos','Sonido estéreo y Bluetooth',129900,20),
('Teclado compacto','Ideal para estudio y oficina',89900,15),
('Mouse ergonómico','Diseño cómodo y preciso',64900,30),
('Cargador portátil','Batería externa para dispositivos',109900,12);
