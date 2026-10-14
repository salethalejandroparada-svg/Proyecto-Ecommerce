import { useEffect, useState } from 'react';

const VACIO = { name: '', description: '', price: '', stock: '', active: true };

export default function ProductoFormulario({ producto, onGuardar, onCancelar, guardando }) {
  const [datos, setDatos] = useState(VACIO);

  // Sincroniza el formulario cuando el usuario selecciona "Editar".
  useEffect(() => {
    setDatos(producto ? {
      name: producto.name ?? '',
      description: producto.description ?? '',
      price: producto.price ?? '',
      stock: producto.stock ?? '',
      active: Boolean(producto.active)
    } : VACIO);
  }, [producto]);

  function cambiar(evento) {
    const { name, value, type, checked } = evento.target;
    setDatos((actual) => ({ ...actual, [name]: type === 'checkbox' ? checked : value }));
  }

  function enviar(evento) {
    evento.preventDefault();
    const name = datos.name.trim();
    const description = datos.description.trim();
    const price = Number(datos.price);
    const stock = Number(datos.stock);
    if (!name || !description || !Number.isFinite(price) || price < 0.01 || !Number.isInteger(stock) || stock < 0) {
      return;
    }
    // Se conservan los nombres de campos de la API Java existente.
    onGuardar({ name, description, price, stock, active: datos.active });
  }

  return (
    <section className="panel" aria-labelledby="titulo-formulario">
      <h2 id="titulo-formulario">{producto ? 'Editar producto' : 'Registrar producto'}</h2>
      <form onSubmit={enviar}>
        <label htmlFor="nombre">Nombre</label>
        <input id="nombre" name="name" value={datos.name} onChange={cambiar} required maxLength={150} />

        <label htmlFor="descripcion">Descripción</label>
        <textarea id="descripcion" name="description" value={datos.description} onChange={cambiar} required rows="3" maxLength={1000} />

        <label htmlFor="precio">Precio (COP)</label>
        <input id="precio" name="price" type="number" min="0.01" step="0.01" value={datos.price} onChange={cambiar} required />

        <label htmlFor="existencias">Existencias</label>
        <input id="existencias" name="stock" type="number" min="0" step="1" value={datos.stock} onChange={cambiar} required />

        <label className="check" htmlFor="activo">
          <input id="activo" name="active" type="checkbox" checked={datos.active} onChange={cambiar} />
          Producto activo
        </label>
        <div className="acciones-formulario">
          <button type="submit" disabled={guardando}>{guardando ? 'Guardando…' : producto ? 'Guardar cambios' : 'Registrar producto'}</button>
          {producto && <button className="secundario" type="button" onClick={onCancelar}>Cancelar edición</button>}
        </div>
      </form>
    </section>
  );
}
