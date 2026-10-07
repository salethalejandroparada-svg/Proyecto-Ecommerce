<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head><meta charset="UTF-8"><title>Formulario producto</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"></head>
<body>
<main class="container section narrow">
 <h1>${product.id == 0 ? 'Registrar producto' : 'Actualizar producto'}</h1>
 <p>Formulario HTML enviado al servlet mediante método POST.</p>
 <form class="card" method="post" action="${pageContext.request.contextPath}/productos">
  <input type="hidden" name="action" value="guardar">
  <input type="hidden" name="id" value="${product.id == 0 ? '' : product.id}">
  <label>Nombre</label><input name="name" maxlength="120" required value="${product.name}">
  <label>Descripción</label><textarea name="description" maxlength="255" required>${product.description}</textarea>
  <label>Precio</label><input type="number" name="price" min="1" step="0.01" required value="${product.price}">
  <label>Stock</label><input type="number" name="stock" min="0" required value="${product.stock}">
  <label class="check"><input type="checkbox" name="active" ${product.id == 0 || product.active ? 'checked' : ''}> Producto activo</label>
  <div class="actions"><button type="submit">Guardar</button><a class="btn secondary" href="${pageContext.request.contextPath}/productos">Cancelar</a></div>
 </form>
</main></body></html>
