package co.sena.ecommerce.controller;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/** Convierte errores de validación en respuestas HTTP comprensibles. */
@RestControllerAdvice
public class ApiErrorHandler {
 @ExceptionHandler({IllegalArgumentException.class})
 public ResponseEntity<Map<String,String>> badRequest(Exception e){
  return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
 }
}
