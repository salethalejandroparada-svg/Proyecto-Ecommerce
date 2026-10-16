
package co.sena.ecommerce.model;

/**
 * Modelo que representa un usuario del E-Commerce.
 * Evidencia GA7-220501096-AA5-EV01.
 * Aprendiz: Saleth Alejandro Parada Monroy.
 */
public class User {

    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;

    public User() {
    }

    public User(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = "CLIENTE";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
