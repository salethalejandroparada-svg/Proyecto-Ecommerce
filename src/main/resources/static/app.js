/**
 * Panel de administración de productos.
 * Flujo: consultar API REST -> renderizar lista -> crear/editar/eliminar -> refrescar.
 * Convención: constantes UPPER_SNAKE_CASE y funciones/variables camelCase.
 * Los identificadores HTML y las propiedades JSON se conservan por compatibilidad.
 */
const API_PRODUCTOS_URL = '/api/productos';
const formularioProducto = document.querySelector('#form');
const contenedorProductos = document.querySelector('#products');
const mensajeEstado = document.querySelector('#status');

/** Obtiene un campo del formulario por su identificador HTML. */
const obtenerCampo = (id) => document.getElementById(id);

/**
 * Centraliza las peticiones HTTP y transforma la respuesta en JSON.
 * DELETE y PUT devuelven HTTP 204 sin contenido, por eso se retorna null.
 * Los errores HTTP se propagan para mostrarlos en la interfaz.
 */
async function solicitarApi(url, opciones = {}) {
    const respuesta = await fetch(url, opciones);
    if (!respuesta.ok) {
        throw new Error(`Error HTTP ${respuesta.status}`);
    }
    return respuesta.status === 204 ? null : respuesta.json();
}

/**
 * Consulta los productos y reconstruye la lista sin insertar HTML arbitrario.
 * Cada botón de editar carga los datos en el formulario; eliminar solicita
 * confirmación antes de llamar a la API y refrescar el listado.
 */
async function cargarProductos() {
    try {
        const productos = await solicitarApi(API_PRODUCTOS_URL);
        contenedorProductos.replaceChildren();

        for (const producto of productos) {
            const tarjeta = document.createElement('article');
            const nombre = document.createElement('strong');
            const detalles = document.createElement('p');
            nombre.textContent = producto.name;
            detalles.textContent = `${producto.description} | $${producto.price} | Stock: ${producto.stock}`;

            const botonEditar = document.createElement('button');
            botonEditar.textContent = 'Editar';
            botonEditar.onclick = () => {
                // El ID oculto distingue una actualización PUT de un registro POST.
                for (const campo of ['id', 'name', 'description', 'price', 'stock']) {
                    obtenerCampo(campo).value = producto[campo];
                }
                obtenerCampo('active').checked = producto.active;
                window.scrollTo(0, 0);
            };

            const botonEliminar = document.createElement('button');
            botonEliminar.textContent = 'Eliminar';
            botonEliminar.onclick = async () => {
                if (!confirm('¿Eliminar producto?')) return;
                try {
                    await solicitarApi(`${API_PRODUCTOS_URL}/${producto.id}`, { method: 'DELETE' });
                    await cargarProductos();
                } catch (error) {
                    mensajeEstado.textContent = error.message;
                }
            };

            tarjeta.append(nombre, detalles, botonEditar, botonEliminar);
            contenedorProductos.append(tarjeta);
        }

        if (!productos.length) contenedorProductos.textContent = 'Sin productos.';
        mensajeEstado.textContent = '';
    } catch (error) {
        mensajeEstado.textContent = error.message;
    }
}

/** Restablece el formulario al modo de creación y activa el producto por defecto. */
function limpiarFormulario() {
    formularioProducto.reset();
    obtenerCampo('id').value = '';
    obtenerCampo('active').checked = true;
}

/**
 * Envía el formulario a Spring Boot. Si hay ID, actualiza mediante PUT;
 * si no lo hay, crea mediante POST. Tras guardar, limpia y recarga la lista.
 */
async function guardarProducto(evento) {
    evento.preventDefault();
    const idProducto = obtenerCampo('id').value;
    const producto = {
        name: obtenerCampo('name').value,
        description: obtenerCampo('description').value,
        price: Number(obtenerCampo('price').value),
        stock: Number(obtenerCampo('stock').value),
        active: obtenerCampo('active').checked
    };

    try {
        const url = idProducto ? `${API_PRODUCTOS_URL}/${idProducto}` : API_PRODUCTOS_URL;
        await solicitarApi(url, {
            method: idProducto ? 'PUT' : 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(producto)
        });
        limpiarFormulario();
        await cargarProductos();
    } catch (error) {
        mensajeEstado.textContent = error.message;
    }
}

formularioProducto.addEventListener('submit', guardarProducto);
obtenerCampo('cancel').onclick = limpiarFormulario;
cargarProductos();
