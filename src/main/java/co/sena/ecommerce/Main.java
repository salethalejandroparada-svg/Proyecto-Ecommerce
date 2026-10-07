package co.sena.ecommerce;
import co.sena.ecommerce.model.Product;
import co.sena.ecommerce.service.ProductService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER=new Scanner(System.in);
    private static final ProductService SERVICE=new ProductService();
    public static void main(String[] args){
        boolean running=true;
        System.out.println("E-COMMERCE SENA - CRUD DE PRODUCTOS");
        while(running){
            System.out.println("\n1.Insertar  2.Consultar todos  3.Consultar ID  4.Actualizar  5.Eliminar  0.Salir");
            System.out.print("Opción: ");
            try{
                switch(SCANNER.nextLine().trim()){
                    case "1"->create(); case "2"->SERVICE.findAll().forEach(System.out::println);
                    case "3"->find(); case "4"->update(); case "5"->delete(); case "0"->running=false;
                    default->System.out.println("Opción no válida.");
                }
            }catch(Exception e){System.out.println("Error: "+e.getMessage());}
        }
    }
    private static void create()throws SQLException{
        System.out.print("Nombre: ");String n=SCANNER.nextLine();
        System.out.print("Descripción: ");String d=SCANNER.nextLine();
        System.out.print("Precio: ");BigDecimal p=new BigDecimal(SCANNER.nextLine());
        System.out.print("Stock: ");int s=Integer.parseInt(SCANNER.nextLine());
        Product product=SERVICE.create(n,d,p,s);System.out.println("Creado. ID: "+product.getId());
    }
    private static void find()throws SQLException{
        System.out.print("ID: ");int id=Integer.parseInt(SCANNER.nextLine());
        SERVICE.findById(id).ifPresentOrElse(System.out::println,()->System.out.println("No encontrado."));
    }
    private static void update()throws SQLException{
        System.out.print("ID: ");int id=Integer.parseInt(SCANNER.nextLine());
        System.out.print("Nombre: ");String n=SCANNER.nextLine();
        System.out.print("Descripción: ");String d=SCANNER.nextLine();
        System.out.print("Precio: ");BigDecimal p=new BigDecimal(SCANNER.nextLine());
        System.out.print("Stock: ");int s=Integer.parseInt(SCANNER.nextLine());
        System.out.print("Activo (S/N): ");boolean a=SCANNER.nextLine().equalsIgnoreCase("S");
        System.out.println(SERVICE.update(id,n,d,p,s,a)?"Actualizado.":"No encontrado.");
    }
    private static void delete()throws SQLException{
        System.out.print("ID: ");int id=Integer.parseInt(SCANNER.nextLine());
        System.out.println(SERVICE.delete(id)?"Eliminado.":"No encontrado.");
    }
}
