/**
 * Tienda de demostración (HTML/JS sin backend).
 * Mantiene productos y carrito en localStorage; es independiente del CRUD
 * de Spring Boot que se encuentra en src/main/resources/static/app.js.
 */
const PRODUCTOS_INICIALES = [
    { id: 1, name: 'Audífonos inalámbricos', price: 129900, description: 'Sonido estéreo y conexión Bluetooth.', icon: '🎧' },
    { id: 2, name: 'Teclado compacto', price: 89900, description: 'Ideal para estudio, oficina y programación.', icon: '⌨️' },
    { id: 3, name: 'Mouse ergonómico', price: 64900, description: 'Diseño cómodo y preciso.', icon: '🖱️' },
    { id: 4, name: 'Cargador portátil', price: 109900, description: 'Batería externa para dispositivos móviles.', icon: '🔋' }
];
const CLAVE_PRODUCTOS = 'ecommerceProducts';
const CLAVE_CARRITO = 'ecommerceCart';

/** Lee una lista JSON local y utiliza valores predeterminados si no es válida. */
function leerListaGuardada(clave, valoresPredeterminados) {
    try {
        const datos = JSON.parse(localStorage.getItem(clave));
        return Array.isArray(datos) ? datos : [...valoresPredeterminados];
    } catch {
        return [...valoresPredeterminados];
    }
}

let productos = leerListaGuardada(CLAVE_PRODUCTOS, PRODUCTOS_INICIALES);
let carrito = leerListaGuardada(CLAVE_CARRITO, []);
const formatearPrecio = (valor) => new Intl.NumberFormat('es-CO', {
    style: 'currency', currency: 'COP', maximumFractionDigits: 0
}).format(valor);

/** Persiste productos y carrito para conservarlos al recargar el navegador. */
function guardarEstado() {
    localStorage.setItem(CLAVE_PRODUCTOS, JSON.stringify(productos));
    localStorage.setItem(CLAVE_CARRITO, JSON.stringify(carrito));
}

/** Crea nodos con textContent para evitar interpretar descripciones como HTML. */
function crearElemento(etiqueta, texto, claseCss) {
    const elemento = document.createElement(etiqueta);
    elemento.textContent = texto;
    if (claseCss) elemento.className = claseCss;
    return elemento;
}

/** Renderiza el catálogo y asocia el botón de compra a cada producto. */
function mostrarProductos() {
    const cuadrícula = document.querySelector('#productGrid');
    if (!cuadrícula) return;
    cuadrícula.replaceChildren();
    productos.forEach((producto) => {
        const tarjeta = crearElemento('article', '', 'product');
        tarjeta.append(
            crearElemento('div', producto.icon || '🛍️', 'icon'),
            crearElemento('h3', producto.name),
            crearElemento('p', producto.description),
            crearElemento('div', formatearPrecio(producto.price), 'price')
        );
        const botonAgregar = crearElemento('button', 'Agregar al carrito');
        botonAgregar.onclick = () => agregarAlCarrito(producto.id);
        tarjeta.append(botonAgregar);
        cuadrícula.append(tarjeta);
    });
}

/** Suma una unidad si el artículo ya existe o crea una nueva línea del carrito. */
function agregarAlCarrito(idProducto) {
    const producto = productos.find((item) => item.id === idProducto);
    if (!producto) return;
    const linea = carrito.find((item) => item.id === idProducto);
    if (linea) {
        linea.quantity++;
    } else {
        carrito.push({ id: producto.id, name: producto.name, price: +producto.price, quantity: 1 });
    }
    guardarEstado();
    mostrarCarrito();
    actualizarContador();
    abrirCarrito();
}

/** Elimina una línea del carrito y sincroniza vista, contador y almacenamiento. */
function quitarDelCarrito(idProducto) {
    carrito = carrito.filter((item) => item.id !== idProducto);
    guardarEstado();
    mostrarCarrito();
    actualizarContador();
}

/** Calcula el subtotal por artículo y el total acumulado de la compra. */
function mostrarCarrito() {
    const contenedor = document.querySelector('#cartItems');
    const total = document.querySelector('#cartTotal');
    if (!contenedor || !total) return;
    contenedor.replaceChildren();
    if (!carrito.length) contenedor.append(crearElemento('p', 'Tu carrito está vacío.'));
    carrito.forEach((linea) => {
        const fila = crearElemento('div', '', 'cart-item');
        fila.append(
            crearElemento('strong', linea.name),
            crearElemento('p', `Cantidad: ${linea.quantity} · ${formatearPrecio(linea.price * linea.quantity)}`)
        );
        const botonEliminar = crearElemento('button', 'Eliminar');
        botonEliminar.onclick = () => quitarDelCarrito(linea.id);
        fila.append(botonEliminar);
        contenedor.append(fila);
    });
    total.textContent = formatearPrecio(carrito.reduce((suma, linea) => suma + linea.price * linea.quantity, 0));
}

/** Muestra la cantidad total de unidades agregadas al carrito. */
function actualizarContador() {
    const contador = document.querySelector('#cartCount');
    if (contador) contador.textContent = carrito.reduce((suma, linea) => suma + linea.quantity, 0);
}

/** Abre el panel y actualiza el atributo de accesibilidad aria-hidden. */
function abrirCarrito() {
    const panel = document.querySelector('#cartPanel');
    const fondo = document.querySelector('#overlay');
    if (panel && fondo) {
        panel.classList.add('open');
        panel.setAttribute('aria-hidden', 'false');
        fondo.hidden = false;
    }
}

/** Cierra el panel y oculta el fondo superpuesto. */
function cerrarCarrito() {
    const panel = document.querySelector('#cartPanel');
    const fondo = document.querySelector('#overlay');
    if (panel && fondo) {
        panel.classList.remove('open');
        panel.setAttribute('aria-hidden', 'true');
        fondo.hidden = true;
    }
}

/** Registra los eventos de apertura y cierre de la vista de carrito. */
function configurarEventos() {
    document.querySelector('#cartButton')?.addEventListener('click', abrirCarrito);
    document.querySelector('#closeCart')?.addEventListener('click', cerrarCarrito);
    document.querySelector('#overlay')?.addEventListener('click', cerrarCarrito);
}

document.addEventListener('DOMContentLoaded', () => {
    mostrarProductos();
    mostrarCarrito();
    actualizarContador();
    configurarEventos();
});
