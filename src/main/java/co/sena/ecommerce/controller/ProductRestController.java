package co.sena.ecommerce.controller;
import co.sena.ecommerce.model.Product;
import co.sena.ecommerce.service.ProductService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/** Controlador REST que reutiliza la capa JDBC de la EV01. */
@RestController
@RequestMapping("/api/productos")
public class ProductRestController {
 private final ProductService service=new ProductService();
 /** Consultar todos los productos. */
 @GetMapping public List<Product> list() throws Exception{return service.findAll();}
 /** Consultar un producto por identificador. */
 @GetMapping("/{id}") public ResponseEntity<Product> find(@PathVariable int id)throws Exception{
  return service.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
 }
 /** Insertar producto. */
 @PostMapping public ResponseEntity<Product> create(@RequestBody Product p)throws Exception{
  Product saved=service.create(p.getName(),p.getDescription(),p.getPrice(),p.getStock());
  return ResponseEntity.status(HttpStatus.CREATED).body(saved);
 }
 /** Actualizar producto existente. */
 @PutMapping("/{id}") public ResponseEntity<Void> update(@PathVariable int id,@RequestBody Product p)throws Exception{
  return service.update(id,p.getName(),p.getDescription(),p.getPrice(),p.getStock(),p.isActive())
   ?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
 }
 /** Eliminar producto existente. */
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable int id)throws Exception{
  return service.delete(id)?ResponseEntity.noContent().build():ResponseEntity.notFound().build();
 }
}
