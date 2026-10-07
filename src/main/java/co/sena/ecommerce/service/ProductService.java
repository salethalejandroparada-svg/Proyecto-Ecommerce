package co.sena.ecommerce.service;
import co.sena.ecommerce.dao.ProductDAO;
import co.sena.ecommerce.model.Product;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

public class ProductService {
    private final ProductDAO productDAO=new ProductDAO();
    public Product create(String name,String description,BigDecimal price,int stock)throws SQLException{
        validate(name,description,price,stock);Product p=new Product(name.trim(),description.trim(),price,stock,true);productDAO.insert(p);return p;
    }
    public List<Product> findAll()throws SQLException{return productDAO.findAll();}
    public Optional<Product> findById(int id)throws SQLException{return productDAO.findById(id);}
    public boolean update(int id,String name,String description,BigDecimal price,int stock,boolean active)throws SQLException{
        validate(name,description,price,stock);return productDAO.update(new Product(id,name.trim(),description.trim(),price,stock,active))>0;
    }
    public boolean delete(int id)throws SQLException{return productDAO.delete(id)>0;}
    private void validate(String name,String description,BigDecimal price,int stock){
        if(name==null||name.isBlank())throw new IllegalArgumentException("El nombre es obligatorio.");
        if(description==null||description.isBlank())throw new IllegalArgumentException("La descripción es obligatoria.");
        if(price==null||price.signum()<=0)throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        if(stock<0)throw new IllegalArgumentException("El stock no puede ser negativo.");
    }
}
