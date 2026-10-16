
package co.sena.ecommerce.dao;

import co.sena.ecommerce.config.DatabaseConnection;
import co.sena.ecommerce.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Acceso a datos para el registro y autenticacion de usuarios.
 * Evidencia GA7-220501096-AA5-EV01.
 * Aprendiz: Saleth Alejandro Parada Monroy.
 */
public class UserDAO {

    // Inserta un usuario con su contrasena previamente protegida.
    private static final String INSERT_SQL =
        "INSERT INTO usuarios (nombre, correo, contrasena, rol) " +
        "VALUES (?, ?, ?, 'CLIENTE')";

    // Busca un usuario por su correo electronico.
    private static final String SELECT_BY_EMAIL_SQL =
        "SELECT id_usuario, nombre, correo, contrasena, rol " +
        "FROM usuarios WHERE correo = ?";

    /**
     * Registra un nuevo usuario en MySQL.
     */
    public int insert(User user) throws SQLException {

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(INSERT_SQL)
        ) {
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getCorreo());
            statement.setString(3, user.getContrasena());

            return statement.executeUpdate();
        }
    }

    /**
     * Busca un usuario por correo.
     * Devuelve Optional.empty() si no existe.
     */
    public Optional<User> findByEmail(String correo) throws SQLException {

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(SELECT_BY_EMAIL_SQL)
        ) {
            statement.setString(1, correo);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    User user = new User();

                    user.setIdUsuario(result.getInt("id_usuario"));
                    user.setNombre(result.getString("nombre"));
                    user.setCorreo(result.getString("correo"));
                    user.setContrasena(result.getString("contrasena"));
                    user.setRol(result.getString("rol"));

                    return Optional.of(user);
                }

                return Optional.empty();
            }
        }
    }

    /**
     * Verifica si un correo ya existe en la base de datos.
     */
    public boolean existsByEmail(String correo) throws SQLException {
        return findByEmail(correo).isPresent();
    }
}
