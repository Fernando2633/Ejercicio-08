package mx.ipn.upiiz.pedidos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Formato estandar de todas las respuestas de la API:
 * estado (codigo HTTP), mensaje, datos (si aplica), errores (si aplica) y fecha.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRespuesta<T> {

    private int estado;
    private String mensaje;
    private T datos;
    private List<String> errores;
    private LocalDateTime fecha;

    public ApiRespuesta(int estado, String mensaje, T datos, List<String> errores) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.datos = datos;
        this.errores = errores;
        this.fecha = LocalDateTime.now(ZoneId.of("America/Mexico_City")).truncatedTo(ChronoUnit.SECONDS);
    }

    public static <T> ApiRespuesta<T> exito(int estado, String mensaje, T datos) {
        return new ApiRespuesta<>(estado, mensaje, datos, null);
    }

    public static ApiRespuesta<Void> error(int estado, String mensaje, List<String> errores) {
        return new ApiRespuesta<>(estado, mensaje, null, errores);
    }

    public int getEstado() { return estado; }
    public String getMensaje() { return mensaje; }
    public T getDatos() { return datos; }
    public List<String> getErrores() { return errores; }
    public LocalDateTime getFecha() { return fecha; }
}
