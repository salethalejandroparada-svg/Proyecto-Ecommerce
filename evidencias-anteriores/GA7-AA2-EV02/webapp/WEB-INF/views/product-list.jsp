<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Productos</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"></head>
<body>
<header><div class="container nav"><strong>Administración de productos</strong><a class="btn" href="${pageContext.request.contextPath}/productos?action=nuevo">Nuevo producto</a></div></header>
<main class="container section">
 <h1>Productos registrados</h1>
 <p>Consulta realizada mediante Servlet GET y datos obtenidos desde MySQL con JDBC.</p>
 <div class="table-wrap"><table>
  <thead><tr><th>ID</th><th>Nombre</th><th>Descripción</th><th>Precio</th><th>Stock</th><th>Estado</th><th>Acciones</th></tr></thead>
  <tbody>
   <c:forEach var="p" items="${products}">
    <tr>
     <td>${p.id}</td><td>${p.name}</td><td>${p.description}</td><td>$ ${p.price}</td><td>${p.stock}</td>
     <td><c:choose><c:when test="${p.active}">Activo</c:when><c:otherwise>Inactivo</c:otherwise></c:choose></td>
     <td class="actions">
      <a class="btn secondary" href="${pageContext.request.contextPath}/productos?action=editar&id=${p.id}">Editar</a>
      <form method="post" action="${pageContext.request.contextPath}/productos" onsubmit="return confirm('¿Eliminar este producto?');">
       <input type="hidden" name="action" value="eliminar"><input type="hidden" name="id" value="${p.id}">
       <button class="danger" type="submit">Eliminar</button>
      </form>
     </td>
    </tr>
   </c:forEach>
  </tbody>
 </table></div>
</main></body></html>
