
package co.sena.ecommerce.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Manejo centralizado de errores de los servicios REST.
 * Evidencia GA7-220501096-AA5-EV01.
 * Aprendiz: Saleth Alejandro Parada Monroy.
 */
@RestControllerAdvice
public class ApiErrorHandler {

    /**
     * Responde a errores de validacion de datos.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest()
                .body(Map.of("error", exception.getMessage()));
    }

    /**
     * Conserva los codigos HTTP de validacion y autenticacion.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> statusError(
            ResponseStatusException exception) {

        String mensaje = exception.getReason() != null
                ? exception.getReason()
                : "Error en la solicitud";

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(Map.of("error", mensaje));
    }

    /**
     * Evita mostrar detalles internos de la base de datos.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Map<String, String>> databaseError(
            SQLException exception) {

        // MySQL: error 1062 significa valor duplicado
        // en una columna con restriccion UNIQUE.
        if (exception.getErrorCode() == 1062) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "El registro ya existe"
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Error interno de base de datos"
                ));
    }
}
