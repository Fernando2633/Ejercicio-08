package mx.ipn.upiiz.pedidos.exception;

import mx.ipn.upiiz.pedidos.dto.ApiRespuesta;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/** Manejo centralizado de errores: todas las respuestas llevan estado y mensaje. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - validaciones del DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRespuesta<Void>> validacion(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage())
                .distinct()
                .toList();
        return responder(HttpStatus.BAD_REQUEST, "Datos inválidos o incompletos", errores);
    }

    // 400 - JSON mal formado, fecha o numero con formato incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiRespuesta<Void>> jsonInvalido(HttpMessageNotReadableException ex) {
        return responder(HttpStatus.BAD_REQUEST, "Datos inválidos o incompletos",
                List.of("El cuerpo de la petición no es un JSON válido o algún campo tiene formato incorrecto (fecha: aaaa-mm-dd, total: número)."));
    }

    // 400 - parametro con tipo incorrecto, por ejemplo /pedidos/abc
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiRespuesta<Void>> tipoInvalido(MethodArgumentTypeMismatchException ex) {
        return responder(HttpStatus.BAD_REQUEST, "Parámetro inválido",
                List.of("El parámetro '" + ex.getName() + "' tiene un valor no válido: " + ex.getValue()));
    }

    // 400 - falta un parametro obligatorio
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiRespuesta<Void>> faltaParametro(MissingServletRequestParameterException ex) {
        return responder(HttpStatus.BAD_REQUEST, "Parámetro inválido",
                List.of("Falta el parámetro obligatorio '" + ex.getParameterName() + "'."));
    }

    // 400 - reglas de la capa de servicio
    @ExceptionHandler(ParametroInvalidoException.class)
    public ResponseEntity<ApiRespuesta<Void>> parametroInvalido(ParametroInvalidoException ex) {
        return responder(HttpStatus.BAD_REQUEST, "Parámetro inválido", List.of(ex.getMessage()));
    }

    // 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiRespuesta<Void>> noEncontrado(RecursoNoEncontradoException ex) {
        return responder(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiRespuesta<Void>> rutaNoEncontrada(NoResourceFoundException ex) {
        return responder(HttpStatus.NOT_FOUND, "La ruta solicitada no existe.", null);
    }

    // 405
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiRespuesta<Void>> metodoNoPermitido(HttpRequestMethodNotSupportedException ex) {
        return responder(HttpStatus.METHOD_NOT_ALLOWED, "Método HTTP no permitido para esta ruta.", null);
    }

    // 409 - duplicados
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiRespuesta<Void>> duplicado(RecursoDuplicadoException ex) {
        return responder(HttpStatus.CONFLICT, "Conflicto (duplicado)", List.of(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiRespuesta<Void>> integridad(DataIntegrityViolationException ex) {
        return responder(HttpStatus.CONFLICT, "Conflicto (duplicado)",
                List.of("Ya existe un pedido con ese código."));
    }

    // 500 - cualquier otro error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRespuesta<Void>> errorGeneral(Exception ex) {
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor",
                List.of("Ocurrió un error inesperado. Intenta más tarde."));
    }

    private ResponseEntity<ApiRespuesta<Void>> responder(HttpStatus status, String mensaje, List<String> errores) {
        return ResponseEntity.status(status).body(ApiRespuesta.error(status.value(), mensaje, errores));
    }
}
