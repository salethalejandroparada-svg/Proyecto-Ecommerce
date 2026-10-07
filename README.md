# Proyecto E-Commerce - GA7-220501096-AA2-EV01

**Aprendiz:** SALETH ALEJANDRO PARADA MONROY  
**Evidencia:** Codificación de módulos del software  
**Proyecto:** E-Commerce

## Continuidad del proyecto
Esta entrega amplía el mismo Proyecto-Ecommerce trabajado y versionado en las evidencias anteriores.
No se requiere crear un repositorio GitHub diferente.

## Módulo implementado
Se agregó un módulo Java 17 con JDBC y MySQL para administrar productos mediante CRUD:

- Inserción.
- Consulta general.
- Consulta por ID.
- Actualización.
- Eliminación.

## Arquitectura
- `config`: conexión JDBC.
- `model`: clase `Product`.
- `dao`: acceso a datos con `ProductDAO`.
- `service`: validaciones y reglas.
- `Main`: módulo stand-alone por consola.
- `database`: script MySQL.
- HTML/CSS/JavaScript: interfaces del E-Commerce desarrolladas previamente.

## Estándares
- Paquetes: minúsculas.
- Clases: PascalCase.
- Métodos y variables: camelCase.
- Constantes: MAYUSCULA_CON_GUION_BAJO.
- Consultas parametrizadas con `PreparedStatement`.
- Recursos JDBC cerrados con try-with-resources.

## Preparación de MySQL
1. Ejecutar `database/ecommerce.sql`.
2. Revisar `src/main/resources/database.properties`.
3. Si MySQL tiene contraseña, escribirla en `db.password`.

## Ejecución
```bash
mvn clean compile
mvn exec:java
```

## Subir al repositorio existente
Copiar el contenido de esta entrega dentro de la carpeta local `Proyecto-Ecommerce` que ya está conectada a GitHub.

```bash
git remote -v
git status
git add .
git commit -m "GA7 AA2 EV01 - Modulo JDBC CRUD E-Commerce"
git push origin main
```

No ejecutar `git init` ni `git remote add origin` si el repositorio anterior ya conserva su carpeta `.git`.
