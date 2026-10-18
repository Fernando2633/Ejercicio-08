package mx.ipn.upiiz.pedidos.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Clases que solo sirven para que Swagger muestre bien el formato de cada respuesta
 * (ApiRespuesta es generica y Swagger no sabe que va dentro de "datos").
 */
public final class RespuestasSwagger {

    private RespuestasSwagger() {
    }

    @Schema(name = "RespuestaPedido", description = "Respuesta con un pedido")
    public static class RespuestaPedido {
        @Schema(example = "200") public int estado;
        @Schema(example = "Pedido encontrado") public String mensaje;
        public PedidoDTO datos;
        @Schema(example = "2026-10-18T17:30:00") public String fecha;
    }

    @Schema(name = "RespuestaListaPedidos", description = "Respuesta con una lista de pedidos")
    public static class RespuestaListaPedidos {
        @Schema(example = "200") public int estado;
        @Schema(example = "Se encontraron 3 pedidos") public String mensaje;
        public List<PedidoDTO> datos;
        @Schema(example = "2026-10-18T17:30:00") public String fecha;
    }

    @Schema(name = "RespuestaMensaje", description = "Respuesta solo con mensaje")
    public static class RespuestaMensaje {
        @Schema(example = "200") public int estado;
        @Schema(example = "Pedido eliminado correctamente") public String mensaje;
        @Schema(example = "2026-10-18T17:30:00") public String fecha;
    }

    @Schema(name = "RespuestaError", description = "Respuesta de error")
    public static class RespuestaError {
        @Schema(example = "400") public int estado;
        @Schema(example = "Datos inválidos o incompletos") public String mensaje;
        @ArraySchema(schema = @Schema(example = "El código es obligatorio.")) public List<String> errores;
        @Schema(example = "2026-10-18T17:30:00") public String fecha;
    }
}
