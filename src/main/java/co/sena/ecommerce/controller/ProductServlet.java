package co.sena.ecommerce.controller;

import co.sena.ecommerce.model.Product;
import co.sena.ecommerce.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/productos")
public class ProductServlet extends HttpServlet {
    private final ProductService service=new ProductService();

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        try{
            if("nuevo".equals(action)){
                request.setAttribute("product",new Product());
                request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request,response);
            }else if("editar".equals(action)){
                int id=Integer.parseInt(request.getParameter("id"));
                Product product=service.find(id).orElseThrow(()->new IllegalArgumentException("Producto no encontrado."));
                request.setAttribute("product",product);
                request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request,response);
            }else{
                request.setAttribute("products",service.list());
                request.getRequestDispatcher("/WEB-INF/views/product-list.jsp").forward(request,response);
            }
        }catch(Exception e){throw new ServletException("No fue posible procesar la consulta.",e);}
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        try{
            if("eliminar".equals(action)){
                service.delete(Integer.parseInt(request.getParameter("id")));
            }else{
                String idValue=request.getParameter("id");
                int id=(idValue==null||idValue.isBlank())?0:Integer.parseInt(idValue);
                Product product=new Product(id,request.getParameter("name"),request.getParameter("description"),
                    new BigDecimal(request.getParameter("price")),Integer.parseInt(request.getParameter("stock")),
                    request.getParameter("active")!=null);
                service.save(product);
            }
            response.sendRedirect(request.getContextPath()+"/productos");
        }catch(Exception e){throw new ServletException("No fue posible procesar el formulario.",e);}
    }
}
