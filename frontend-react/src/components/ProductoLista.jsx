export default function ProductoLista({ productos, onEditar, onEliminar, cargando }) {
  return (
    <section className="panel" aria-labelledby="titulo-listado">
      <div className="cabecera-listado">
        <h2 id="titulo-listado">Listado de productos</h2>
        <span className="contador">{productos.length} productos</span>
      </div>
      {cargando ? <p role="status">Cargando productos…</p> : productos.length === 0 ? (
        <p>No hay productos registrados.</p>
      ) : (
        <div className="lista">
          {productos.map((producto) => (
            <article className="producto" key={producto.id}>
              <div className="producto-info">
                <h3>{producto.name}</h3>
                <p>{producto.description}</p>
                <div className="detalles">
                  <strong>{Number(producto.price).toLocaleString('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 2 })}</strong>
                  <span>Existencias: {producto.stock}</span>
                  <span className={producto.active ? 'estado activo' : 'estado inactivo'}>{producto.active ? 'Activo' : 'Inactivo'}</span>
                </div>
              </div>
              <div className="acciones-producto">
                <button type="button" onClick={() => onEditar(producto)}>Editar</button>
                <button type="button" className="peligro" onClick={() => onEliminar(producto)}>Eliminar</button>
              </div>
            </article>
          ))}
        </div>
      )}
    </section>
  );
}
