const defaults=[
{id:1,name:"Audífonos inalámbricos",price:129900,description:"Sonido estéreo y conexión Bluetooth.",icon:"🎧"},
{id:2,name:"Teclado compacto",price:89900,description:"Ideal para estudio, oficina y programación.",icon:"⌨️"},
{id:3,name:"Mouse ergonómico",price:64900,description:"Diseño cómodo y preciso.",icon:"🖱️"},
{id:4,name:"Cargador portátil",price:109900,description:"Batería externa para dispositivos móviles.",icon:"🔋"}];
function load(k,f){try{const x=JSON.parse(localStorage.getItem(k));return Array.isArray(x)?x:[...f]}catch{return [...f]}}
let products=load("ecommerceProducts",defaults),cart=load("ecommerceCart",[]);
const money=v=>new Intl.NumberFormat("es-CO",{style:"currency",currency:"COP",maximumFractionDigits:0}).format(v);
function save(){localStorage.setItem("ecommerceProducts",JSON.stringify(products));localStorage.setItem("ecommerceCart",JSON.stringify(cart))}
function el(t,txt,c){const e=document.createElement(t);e.textContent=txt;if(c)e.className=c;return e}
function renderProducts(){const g=document.querySelector("#productGrid");if(!g)return;g.replaceChildren();products.forEach(p=>{const c=el("article","","product");c.append(el("div",p.icon||"🛍️","icon"),el("h3",p.name),el("p",p.description),el("div",money(p.price),"price"));const b=el("button","Agregar al carrito");b.onclick=()=>add(p.id);c.append(b);g.append(c)})}
function add(id){const p=products.find(x=>x.id===id);if(!p)return;const x=cart.find(x=>x.id===id);x?x.quantity++:cart.push({id:p.id,name:p.name,price:+p.price,quantity:1});save();renderCart();count();openCart()}
function removeCart(id){cart=cart.filter(x=>x.id!==id);save();renderCart();count()}
function renderCart(){const box=document.querySelector("#cartItems"),total=document.querySelector("#cartTotal");if(!box||!total)return;box.replaceChildren();if(!cart.length)box.append(el("p","Tu carrito está vacío."));cart.forEach(x=>{const row=el("div","","cart-item");row.append(el("strong",x.name),el("p",`Cantidad: ${x.quantity} · ${money(x.price*x.quantity)}`));const b=el("button","Eliminar");b.onclick=()=>removeCart(x.id);row.append(b);box.append(row)});total.textContent=money(cart.reduce((s,x)=>s+x.price*x.quantity,0))}
function count(){const e=document.querySelector("#cartCount");if(e)e.textContent=cart.reduce((s,x)=>s+x.quantity,0)}
function openCart(){const p=document.querySelector("#cartPanel"),o=document.querySelector("#overlay");if(p&&o){p.classList.add("open");p.setAttribute("aria-hidden","false");o.hidden=false}}
function closeCart(){const p=document.querySelector("#cartPanel"),o=document.querySelector("#overlay");if(p&&o){p.classList.remove("open");p.setAttribute("aria-hidden","true");o.hidden=true}}
function setup(){document.querySelector("#cartButton")?.addEventListener("click",openCart);document.querySelector("#closeCart")?.addEventListener("click",closeCart);document.querySelector("#overlay")?.addEventListener("click",closeCart)}
document.addEventListener("DOMContentLoaded",()=>{renderProducts();renderCart();count();setup()});
