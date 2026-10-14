// Centraliza las solicitudes HTTP para evitar duplicación de código.
const BASE_URL = '/api/productos';

async function request(url, options = {}) {
  let response;
  try {
    response = await fetch(url, {
      ...options,
      headers: { 'Content-Type': 'application/json', ...options.headers }
    });
  } catch {
    throw new Error('No fue posible conectar con Spring Boot. Verifica que esté activo en el puerto 8080.');
  }

  if (!response.ok) {
    const body = await response.text();
    let message = `Error HTTP ${response.status}`;
    try {
      const parsed = JSON.parse(body);
      message = parsed.message || parsed.error || message;
    } catch { /* El servidor puede devolver una respuesta sin JSON. */ }
    throw new Error(message);
  }
  if (response.status === 204) return null;
  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

export const productosApi = {
  listar: () => request(BASE_URL),
  crear: (producto) => request(BASE_URL, { method: 'POST', body: JSON.stringify(producto) }),
  actualizar: (id, producto) => request(`${BASE_URL}/${id}`, { method: 'PUT', body: JSON.stringify(producto) }),
  eliminar: (id) => request(`${BASE_URL}/${id}`, { method: 'DELETE' })
};
