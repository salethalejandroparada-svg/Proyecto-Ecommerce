# GUIA DE INSTALACION Y EJECUCION

Evidencia: GA7-220501096-AA5-EV03
Aprendiz: SALETH ALEJANDRO PARADA MONROY
Proyecto: E-Commerce

## 1. Requisitos

- Java JDK 17
- Maven
- MySQL 8
- Node.js y npm (para el frontend React)
- Git

## 2. Preparar la base de datos

Iniciar MySQL y ejecutar el script:

database/ecommerce.sql

Verificar que exista la base de datos ecommerce.

## 3. Configurar la conexion JDBC

Copiar el archivo:

database.properties.example

A la siguiente ubicacion:

src/main/resources/database.properties

No escribir la contrasena de MySQL dentro del archivo.

## 4. Configurar la contrasena

En PowerShell ejecutar:

$env:ECOMMERCE_DB_PASSWORD = Read-Host "Contrasena MySQL"

La variable se utiliza durante la ejecucion del backend.

## 5. Ejecutar Spring Boot

Desde la raiz del proyecto:

mvn spring-boot:run

El backend utiliza el puerto 8080.

## 6. Ejecutar React

Abrir otra terminal:

cd frontend-react
npm install
npm run dev

Abrir en el navegador la direccion que muestre Vite.

## 7. Servicios REST

POST   /api/auth/registro
POST   /api/auth/login
GET    /api/productos
GET    /api/productos/{id}
POST   /api/productos
PUT    /api/productos/{id}
DELETE /api/productos/{id}

Para ejemplos JSON, consultar ENDPOINTS_AA5_EV03.txt.

## 8. Repositorio GitHub

https://github.com/salethalejandroparada-svg/Proyecto-Ecommerce

## 9. Seguridad

No compartir contrasenas ni archivos de configuracion privados.
La contrasena de MySQL se obtiene de ECOMMERCE_DB_PASSWORD.
