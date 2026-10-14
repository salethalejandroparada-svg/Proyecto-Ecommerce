import { useEffect, useState } from 'react';
import ProductoFormulario from './components/ProductoFormulario.jsx';
import ProductoLista from './components/ProductoLista.jsx';
import { productosApi } from './services/productosApi.js';

export default function App() {
  const [productos, setProductos] = useState([]);
  const [editando, setEditando] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [guardando, setGuardando] = useState(false);
  const [mensaje, setMensaje] = useState('');
  const [error, setError] = useState('');

  async function recargar() {
    const resultado = await productosApi.listar();
    if (!Array.isArray(resultado)) throw new Error('La API no devolvió una lista de productos.');
    setProductos(resultado);
  }

  useEffect(() => {
    let vigente = true;
    productosApi.listar()
      .then((resultado) => {
        if (vigente) {
          if (!Array.isArray(resultado)) throw new Error('Respuesta de productos no válida.');
          setProductos(resultado);
        }
      })
      .catch((fallo) => { if (vigente) setError(fallo.message); })
      .finally(() => { if (vigente) setCargando(false); });
    return () => { vigente = false; };
  }, []);

  async function guardar(producto) {
    setGuardando(true);
    setError('');
    setMensaje('');
    try {
      if (editando) await productosApi.actualizar(editando.id, producto);
      else await productosApi.crear(producto);
      await recargar();
      setMensaje(editando ? 'Producto actualizado correctamente.' : 'Producto registrado correctamente.');
      setEditando(null);
    } catch (fallo) {
      setError(fallo.message);
    } finally {
      setGuardando(false);
    }
  }

  function editar(producto) {
    setEditando(producto);
    setError('');
    setMensaje('');
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  async function eliminar(producto) {
    // Evita eliminaciones accidentales mediante confirmación explícita.
    if (!window.confirm(`¿Eliminar el producto "${producto.name}"?`)) return;
    setError('');
    setMensaje('');
    try {
      await productosApi.eliminar(producto.id);
      await recargar();
      if (editando?.id === producto.id) setEditando(null);
      setMensaje('Producto eliminado correctamente.');
    } catch (fallo) {
      setError(fallo.message);
    }
  }

  return (
    <main className="contenedor">
      <header className="encabezado">
        <div className="marca">E-Commerce <span>SENA</span></div>
        <h1>Administración de productos</h1>
        <p>Saleth Alejandro Parada Monroy · React + Spring Boot + MySQL</p>
      </header>
      {error && <div className="alerta error" role="alert">{error}</div>}
      {mensaje && <div className="alerta exito" role="status">{mensaje}</div>}
      <div className="columnas">
        <ProductoFormulario producto={editando} onGuardar={guardar} onCancelar={() => setEditando(null)} guardando={guardando} />
        <ProductoLista productos={productos} onEditar={editar} onEliminar={eliminar} cargando={cargando} />
      </div>
      <footer>Proyecto formativo E-Commerce · Evidencia GA7-220501096-AA4-EV03</footer>
    </main>
  );
}
