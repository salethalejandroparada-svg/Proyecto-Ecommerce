# Proyecto E-Commerce integrado — GA7-220501096-AA3-EV01
**Aprendiz:** Saleth Alejandro Parada Monroy

## Componentes
- E-Commerce original: `index.html`, `login.html`, `admin.html`, `css/`, `js/`.
- Java stand-alone y JDBC: `Main.java`, `Product`, `ProductDAO`, `ProductService`.
- Spring Boot: `EcommerceApplication`, `ProductRestController`, API `/api/productos` y cliente web en `src/main/resources/static/`.
- Evidencia anterior Servlet/JSP preservada en `evidencias-anteriores/GA7-AA2-EV02/` para evitar incompatibilidades de compilación.

## Documentación y estándares de codificación
- Convenciones y comentarios explicativos: [`CONVENCIONES_CODIGO.md`](CONVENCIONES_CODIGO.md).
- El panel REST y el prototipo de carrito son módulos diferentes; ambos `app.js` tienen comentarios sobre su lógica.

## Ejecución en Windows (PowerShell)
1. Importar `database/ecommerce.sql` en MySQL Workbench (si ya lo hizo, no repetir INSERT).
2. En la terminal de VS Code, configurar la contraseña **solo para la sesión**:
   `$env:ECOMMERCE_DB_PASSWORD = [System.Net.NetworkCredential]::new("", (Read-Host "Contraseña MySQL" -AsSecureString)).Password`
3. Ejecutar `mvn clean package` y `mvn spring-boot:run`.
4. Abrir `http://localhost:8080/`.
5. Crear, consultar, editar y eliminar productos; verificar cambios en MySQL.

La contraseña no se guarda en el proyecto. **No subir contraseñas ni capturas con secretos a GitHub.**

## Git
El ZIP de entrega no incluye `.git` ni `target`. Para publicar los cambios en el repositorio local existente, revisar `git status` y `git remote -v` antes de `git add .`, `git commit` y `git push origin main`.

Repositorio: https://github.com/salethalejandroparada-svg/Proyecto-Ecommerce
