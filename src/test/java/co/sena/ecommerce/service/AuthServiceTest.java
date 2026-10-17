package co.sena.ecommerce.service;
import co.sena.ecommerce.dao.UserDAO;
import co.sena.ecommerce.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuthServiceTest {
    private final UserDAO dao = mock(UserDAO.class);
    private final AuthService service = new AuthService(dao);
    @Test void rechazaCorreoInvalido() {
        ResponseStatusException ex=assertThrows(ResponseStatusException.class,()->service.registrar("Ana","correo-sin-arroba","ClaveSegura2026"));
        assertEquals(HttpStatus.BAD_REQUEST,ex.getStatusCode());
        verifyNoInteractions(dao);
    }
    @Test void rechazaContrasenaCorta() {
        ResponseStatusException ex=assertThrows(ResponseStatusException.class,()->service.registrar("Ana","ana@example.com","123"));
        assertEquals(HttpStatus.BAD_REQUEST,ex.getStatusCode());
        verifyNoInteractions(dao);
    }
    @Test void rechazaCorreoDuplicado() throws Exception {
        when(dao.existsByEmail("ana@example.com")).thenReturn(true);
        ResponseStatusException ex=assertThrows(ResponseStatusException.class,()->service.registrar("Ana","ANA@example.com","ClaveSegura2026"));
        assertEquals(HttpStatus.CONFLICT,ex.getStatusCode());
    }
    @Test void registraConHash() throws Exception {
        String result=service.registrar("Ana","ANA@example.com","ClaveSegura2026");
        assertEquals("Usuario registrado correctamente",result);
        verify(dao).insert(argThat(u->u.getCorreo().equals("ana@example.com") && !u.getContrasena().equals("ClaveSegura2026") && new BCryptPasswordEncoder().matches("ClaveSegura2026",u.getContrasena())));
    }
    @Test void loginCorrecto() throws Exception {
        User u=new User("Ana","ana@example.com",new BCryptPasswordEncoder().encode("ClaveSegura2026"));
        when(dao.findByEmail("ana@example.com")).thenReturn(Optional.of(u));
        assertEquals("Autenticacion satisfactoria",service.iniciarSesion("ANA@example.com","ClaveSegura2026"));
    }
    @Test void loginIncorrecto() throws Exception {
        when(dao.findByEmail("ana@example.com")).thenReturn(Optional.empty());
        ResponseStatusException ex=assertThrows(ResponseStatusException.class,()->service.iniciarSesion("ana@example.com","Incorrecta2026"));
        assertEquals(HttpStatus.UNAUTHORIZED,ex.getStatusCode());
    }
}
