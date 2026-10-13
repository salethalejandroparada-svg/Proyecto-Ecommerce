# Convenciones de código — Proyecto E-Commerce

**Aprendiz:** Saleth Alejandro Parada Monroy  
**Evidencia:** GA7-220501096-AA3-EV01

## Reglas de nomenclatura

| Elemento | Convención | Ejemplo del proyecto |
|---|---|---|
| Paquetes Java | minúsculas | `co.sena.ecommerce.controller` |
| Clases Java | PascalCase | `ProductRestController`, `ProductService` |
| Métodos y variables Java | camelCase | `findById`, `productDAO` |
| Constantes Java | UPPER_SNAKE_CASE | `INSERT_SQL`, `SELECT_ALL_SQL` |
| Funciones y variables JavaScript | camelCase | `cargarProductos`, `guardarProducto`, `mostrarCarrito` |
| Constantes JavaScript | UPPER_SNAKE_CASE | `API_PRODUCTOS_URL`, `CLAVE_CARRITO` |
| Clases CSS | kebab-case | `cart-item`, `form-card` |
| Archivos web | minúsculas | `index.html`, `app.js`, `styles.css` |
| Tablas y columnas SQL | snake_case | `detalle_pedido`, `id_producto` |

## Nombres externos que deben mantenerse

Los identificadores HTML (`id="name"`, `id="form"`, etc.) y las propiedades JSON de la API (`name`, `description`, `price`, `stock`, `active`) son contratos entre frontend y backend. No se renombran sin modificar ambos lados.

Los métodos estándar de Java (`main`, `getConnection`, `findAll`, `findById`) conservan sus nombres para respetar las interfaces existentes. El directorio `evidencias-anteriores/` contiene código histórico y no forma parte del módulo Spring Boot en ejecución.

## Comentarios y documentación

1. Usar Javadoc `/** ... */` para describir responsabilidades y reglas de negocio de clases y métodos Java relevantes.
2. Usar JSDoc `/** ... */` para documentar funciones JavaScript y comentarios `//` para decisiones poco evidentes.
3. Explicar **por qué** se hace algo, el flujo o el efecto, en lugar de repetir literalmente cada instrucción.
4. Indicar el manejo de errores y los efectos secundarios (por ejemplo, escritura en `localStorage` o cambios en MySQL).
5. Mantener nombres descriptivos y evitar variables de una letra en lógica de negocio; se permiten nombres cortos en iteraciones locales cuando son claros.

## Organización de los módulos

- `src/main/resources/static/app.js`: panel CRUD conectado a Spring Boot mediante `/api/productos`.
- `js/app.js`: demostración independiente del catálogo y carrito almacenados en `localStorage`.
- `controller/`: endpoints HTTP y gestión de errores.
- `service/`: validación y reglas de negocio.
- `dao/`: sentencias SQL parametrizadas y mapeo de resultados.
- `config/`: conexión JDBC y lectura de configuración.

**Verificación recomendada:** `mvn clean package`, `mvn spring-boot:run` y pruebas manuales de crear, consultar, actualizar y eliminar un producto de prueba.
