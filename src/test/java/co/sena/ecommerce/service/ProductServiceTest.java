package co.sena.ecommerce.service;
import co.sena.ecommerce.dao.ProductDAO;
import co.sena.ecommerce.model.Product;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ProductServiceTest {
    private final ProductDAO dao = mock(ProductDAO.class);
    private final ProductService service = new ProductService(dao);
    @Test void rechazaPrecioNegativo() {
        assertThrows(IllegalArgumentException.class, () -> service.create("Monitor", "Pantalla", new BigDecimal("-1"), 2));
        verifyNoInteractions(dao);
    }
    @Test void rechazaNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.create(" ", "Pantalla", new BigDecimal("100"), 2));
        verifyNoInteractions(dao);
    }
    @Test void rechazaStockNegativo() {
        assertThrows(IllegalArgumentException.class, () -> service.create("Monitor", "Pantalla", new BigDecimal("100"), -2));
        verifyNoInteractions(dao);
    }
    @Test void creaProductoValido() throws Exception {
        Product p=service.create(" Monitor ", " Pantalla ", new BigDecimal("100"), 2);
        assertEquals("Monitor", p.getName());
        verify(dao).insert(any(Product.class));
    }
    @Test void consultaProductoInexistente() throws Exception {
        when(dao.findById(999)).thenReturn(Optional.empty());
        assertTrue(service.findById(999).isEmpty());
    }
    @Test void eliminaProductoExistente() throws Exception {
        when(dao.delete(18)).thenReturn(1);
        assertTrue(service.delete(18));
    }
}
