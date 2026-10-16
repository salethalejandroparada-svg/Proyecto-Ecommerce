
package co.sena.ecommerce.service;

import co.sena.ecommerce.dao.UserDAO;
import co.sena.ecommerce.model.User;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

/**
 * Servicio de registro y autenticacion del E-Commerce.
 * Evidencia GA7-220501096-AA5-EV01.
 * Aprendiz: Saleth Alejandro Parada Monroy.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    // BCrypt protege las contrasenas mediante un hash con sal.
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    /**
     * Registra un usuario nuevo en la base de datos.
     */
    public String registrar(String nombre, String correo,
                            String contrasena) throws SQLException {

        if (nombre == null || nombre.isBlank()
                || nombre.trim().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre es obligatorio y debe tener maximo 100 caracteres"
            );
        }

        String correoNormalizado = normalizarCorreo(correo);

        validarContrasena(contrasena);

        // Comprueba que el correo no este registrado.
        if (userDAO.existsByEmail(correoNormalizado)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo electronico ya esta registrado"
            );
        }

        // Nunca se almacena la contrasena original.
        String contrasenaProtegida =
                passwordEncoder.encode(contrasena);

        User usuario = new User(
                nombre.trim(),
                correoNormalizado,
                contrasenaProtegida
        );

        userDAO.insert(usuario);

        return "Usuario registrado correctamente";
    }

    /**
     * Verifica el correo y la contrasena de un usuario.
     */
    public String iniciarSesion(String correo, String contrasena)
            throws SQLException {

        String correoNormalizado = normalizarCorreo(correo);

        if (contrasena == null || contrasena.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contrasena es obligatoria"
            );
        }

        Optional<User> usuario =
                userDAO.findByEmail(correoNormalizado);

        // Se usa el mismo mensaje para usuario inexistente
        // y contrasena incorrecta.
        if (usuario.isEmpty()
                || !passwordEncoder.matches(
                        contrasena,
                        usuario.get().getContrasena())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Error en la autenticacion"
            );
        }

        return "Autenticacion satisfactoria";
    }

    /**
     * Normaliza y valida el correo electronico.
     */
    private String normalizarCorreo(String correo) {

        if (correo == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El correo electronico es obligatorio"
            );
        }

        String normalizado =
                correo.trim().toLowerCase(Locale.ROOT);

        if (normalizado.length() > 150
                || !EMAIL_PATTERN.matcher(normalizado).matches()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El correo electronico no es valido"
            );
        }

        return normalizado;
    }

    /**
     * Valida la longitud de la contrasena.
     */
    private void validarContrasena(String contrasena) {

        if (contrasena == null
                || contrasena.length() < 8
                || contrasena.length() > 72) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contrasena debe tener entre 8 y 72 caracteres"
            );
        }
    }
}
