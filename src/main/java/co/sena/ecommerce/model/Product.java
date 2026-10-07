package co.sena.ecommerce.model;
import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private boolean active;

    public Product() {}
    public Product(String name,String description,BigDecimal price,int stock,boolean active){
        this.name=name;this.description=description;this.price=price;this.stock=stock;this.active=active;
    }
    public Product(int id,String name,String description,BigDecimal price,int stock,boolean active){
        this(name,description,price,stock,active);this.id=id;
    }
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal price){this.price=price;}
    public int getStock(){return stock;} public void setStock(int stock){this.stock=stock;}
    public boolean isActive(){return active;} public void setActive(boolean active){this.active=active;}
    @Override public String toString(){return "ID: "+id+" | "+name+" | Precio: "+price+" | Stock: "+stock+" | Activo: "+active;}
}
