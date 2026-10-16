
package co.sena.ecommerce.controller;

import co.sena.ecommerce.service.AuthService;

import java.sql.SQLException;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para registro e inicio de sesion.
 *
 * Evidencia: GA7-220501096-AA5-EV01.
 * Aprendiz: Saleth Alejandro Parada Monroy.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService = new AuthService();

    /**
     * Recibe los datos del usuario y realiza el registro.
     */
    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrar(
            @RequestBody RegistroRequest request) throws SQLException {

        String mensaje = authService.registrar(
                request.nombre(),
                request.correo(),
                request.contrasena()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("mensaje", mensaje));
    }

    /**
     * Comprueba las credenciales de inicio de sesion.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest request) throws SQLException {

        String mensaje = authService.iniciarSesion(
                request.correo(),
                request.contrasena()
        );

        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    /**
     * Datos necesarios para registrar un usuario.
     * Los records evitan codigo repetitivo de getters y setters.
     */
    public record RegistroRequest(
            String nombre,
            String correo,
            String contrasena
    ) {}

    /**
     * Datos necesarios para iniciar sesion.
     */
    public record LoginRequest(
            String correo,
            String contrasena
    ) {}
}
