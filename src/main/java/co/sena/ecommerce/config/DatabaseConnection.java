package co.sena.ecommerce.config;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) throw new IllegalStateException("No se encontró database.properties");
            PROPERTIES.load(input);
        } catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
    private DatabaseConnection() {}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"), System.getenv().getOrDefault("ECOMMERCE_DB_PASSWORD", ""));
    }
}
