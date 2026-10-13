package co.sena.ecommerce.dao;
import co.sena.ecommerce.config.DatabaseConnection;
import co.sena.ecommerce.model.Product;
import java.sql.*;
import java.util.*;

/** Acceso a datos: ejecuta sentencias parametrizadas para evitar concatenar SQL de usuarios. */
public class ProductDAO {
    private static final String INSERT_SQL="INSERT INTO productos(nombre,descripcion,precio,stock,activo) VALUES(?,?,?,?,?)";
    private static final String SELECT_ALL_SQL="SELECT id_producto,nombre,descripcion,precio,stock,activo FROM productos ORDER BY id_producto";
    private static final String SELECT_ID_SQL="SELECT id_producto,nombre,descripcion,precio,stock,activo FROM productos WHERE id_producto=?";
    private static final String UPDATE_SQL="UPDATE productos SET nombre=?,descripcion=?,precio=?,stock=?,activo=? WHERE id_producto=?";
    private static final String DELETE_SQL="DELETE FROM productos WHERE id_producto=?";

    /** Inserta un producto y recupera el identificador autogenerado por MySQL. */
    public int insert(Product p)throws SQLException{
        try(Connection c=DatabaseConnection.getConnection();PreparedStatement s=c.prepareStatement(INSERT_SQL,Statement.RETURN_GENERATED_KEYS)){
            params(s,p);int n=s.executeUpdate();try(ResultSet k=s.getGeneratedKeys()){if(k.next())p.setId(k.getInt(1));}return n;
        }
    }
    public List<Product> findAll()throws SQLException{
        List<Product> list=new ArrayList<>();
        try(Connection c=DatabaseConnection.getConnection();PreparedStatement s=c.prepareStatement(SELECT_ALL_SQL);ResultSet r=s.executeQuery()){while(r.next())list.add(map(r));}
        return list;
    }
    public Optional<Product> findById(int id)throws SQLException{
        try(Connection c=DatabaseConnection.getConnection();PreparedStatement s=c.prepareStatement(SELECT_ID_SQL)){s.setInt(1,id);try(ResultSet r=s.executeQuery()){return r.next()?Optional.of(map(r)):Optional.empty();}}
    }
    public int update(Product p)throws SQLException{
        try(Connection c=DatabaseConnection.getConnection();PreparedStatement s=c.prepareStatement(UPDATE_SQL)){params(s,p);s.setInt(6,p.getId());return s.executeUpdate();}
    }
    public int delete(int id)throws SQLException{
        try(Connection c=DatabaseConnection.getConnection();PreparedStatement s=c.prepareStatement(DELETE_SQL)){s.setInt(1,id);return s.executeUpdate();}
    }
    /** Asigna los cinco parámetros comunes a INSERT y UPDATE en el mismo orden. */
    private void params(PreparedStatement s,Product p)throws SQLException{s.setString(1,p.getName());s.setString(2,p.getDescription());s.setBigDecimal(3,p.getPrice());s.setInt(4,p.getStock());s.setBoolean(5,p.isActive());}
    /** Convierte una fila del ResultSet al modelo Product. */
    private Product map(ResultSet r)throws SQLException{return new Product(r.getInt("id_producto"),r.getString("nombre"),r.getString("descripcion"),r.getBigDecimal("precio"),r.getInt("stock"),r.getBoolean("activo"));}
}
