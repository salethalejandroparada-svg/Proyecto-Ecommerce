// Cliente del módulo: consume la API REST del controlador Spring Boot.
const api='/api/productos',form=document.querySelector('#form'),box=document.querySelector('#products'),status=document.querySelector('#status');
const field=id=>document.getElementById(id);
async function call(url,options={}){
 const r=await fetch(url,options);if(!r.ok)throw Error('Error HTTP '+r.status);
 return r.status===204?null:r.json();
}
async function load(){
 try{const data=await call(api);box.replaceChildren();
 for(const p of data){
  const row=document.createElement('article'),name=document.createElement('strong'),details=document.createElement('p');
  name.textContent=p.name;details.textContent=`${p.description} | $${p.price} | Stock: ${p.stock}`;
  const edit=document.createElement('button');edit.textContent='Editar';edit.onclick=()=>{
   for(const k of ['id','name','description','price','stock'])field(k).value=p[k];
   field('active').checked=p.active;window.scrollTo(0,0);
  };
  const del=document.createElement('button');del.textContent='Eliminar';del.onclick=async()=>{
   if(!confirm('¿Eliminar producto?'))return;
   try{await call(api+'/'+p.id,{method:'DELETE'});await load();}catch(e){status.textContent=e.message;}
  };
  row.append(name,details,edit,del);box.append(row);
 }if(!data.length)box.textContent='Sin productos.';status.textContent='';
 }catch(e){status.textContent=e.message;}
}
function reset(){form.reset();field('id').value='';field('active').checked=true;}
form.addEventListener('submit',async e=>{
 e.preventDefault();const id=field('id').value;
 const p={name:field('name').value,description:field('description').value,price:Number(field('price').value),stock:Number(field('stock').value),active:field('active').checked};
 try{await call(id?api+'/'+id:api,{method:id?'PUT':'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(p)});reset();await load();}
 catch(error){status.textContent=error.message;}
});
field('cancel').onclick=reset;load();
