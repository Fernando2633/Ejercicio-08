package mx.ipn.upiiz.pedidos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.ipn.upiiz.pedidos.dto.ApiRespuesta;
import mx.ipn.upiiz.pedidos.dto.PedidoDTO;
import mx.ipn.upiiz.pedidos.dto.RespuestasSwagger.RespuestaError;
import mx.ipn.upiiz.pedidos.dto.RespuestasSwagger.RespuestaListaPedidos;
import mx.ipn.upiiz.pedidos.dto.RespuestasSwagger.RespuestaMensaje;
import mx.ipn.upiiz.pedidos.dto.RespuestasSwagger.RespuestaPedido;
import mx.ipn.upiiz.pedidos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Capa de presentacion: expone los endpoints REST de pedidos (version 1). */
@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Alta, consulta, actualización y eliminación de pedidos")
public class PedidoController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    // ------------------------------------------------------------------ POST
    @Operation(
            summary = "Crear un pedido",
            description = "Registra un pedido nuevo. El id lo genera la base de datos y el código no se puede repetir.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pedido a registrar",
                    required = true,
                    content = @Content(mediaType = JSON, schema = @Schema(implementation = PedidoDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso creado correctamente",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaPedido.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "409", description = "Conflicto (duplicado)",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "500", description = "Error del servidor",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class)))
            })
    @PostMapping
    public ResponseEntity<ApiRespuesta<PedidoDTO>> crearPedido(@Valid @RequestBody PedidoDTO pedido) {
        PedidoDTO creado = service.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespuesta.exito(201, "Pedido creado correctamente", creado));
    }

    // ------------------------------------------------------------------ GET (todos)
    @Operation(
            summary = "Listar pedidos",
            description = "Regresa todos los pedidos registrados ordenados por id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaListaPedidos.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class)))
            })
    @GetMapping
    public ResponseEntity<ApiRespuesta<List<PedidoDTO>>> listarPedidos() {
        List<PedidoDTO> pedidos = service.listar();
        String mensaje = pedidos.isEmpty() ? "No hay pedidos registrados"
                : pedidos.size() == 1 ? "Se encontró 1 pedido" : "Se encontraron " + pedidos.size() + " pedidos";
        return ResponseEntity.ok(ApiRespuesta.exito(200, mensaje, pedidos));
    }

    // ------------------------------------------------------------------ GET (por id)
    @Operation(
            summary = "Buscar pedido por id",
            description = "Regresa el pedido que tiene el id indicado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaPedido.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetro inválido",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespuesta<PedidoDTO>> obtenerPedidoPorId(
            @Parameter(description = "Id del pedido", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(ApiRespuesta.exito(200, "Pedido encontrado", service.obtenerPorId(id)));
    }

    // ------------------------------------------------------------------ GET (por estado)
    @Operation(
            summary = "Buscar pedidos por estado",
            description = "Filtra los pedidos por estado: Pendiente, Enviado o Entregado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaListaPedidos.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetro inválido",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class)))
            })
    @GetMapping("/buscar")
    public ResponseEntity<ApiRespuesta<List<PedidoDTO>>> buscarPedidosPorEstado(
            @Parameter(description = "Estado del pedido", example = "Pendiente") @RequestParam String estado) {
        List<PedidoDTO> pedidos = service.buscarPorEstado(estado);
        String mensaje = pedidos.isEmpty()
                ? "No hay pedidos con estado " + estado.trim()
                : pedidos.size() == 1 ? "Se encontró 1 pedido con estado " + estado.trim()
                : "Se encontraron " + pedidos.size() + " pedidos con estado " + estado.trim();
        return ResponseEntity.ok(ApiRespuesta.exito(200, mensaje, pedidos));
    }

    // ------------------------------------------------------------------ PUT
    @Operation(
            summary = "Actualizar un pedido",
            description = "Reemplaza los datos del pedido indicado. El código no puede quedar repetido con otro pedido.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos nuevos del pedido",
                    required = true,
                    content = @Content(mediaType = JSON, schema = @Schema(implementation = PedidoDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso actualizado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaPedido.class))),
                    @ApiResponse(responseCode = "400", description = "Datos o parámetro inválidos",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "409", description = "Conflicto (duplicado)",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<ApiRespuesta<PedidoDTO>> actualizarPedido(
            @Parameter(description = "Id del pedido a actualizar", example = "1") @PathVariable Long id,
            @Valid @RequestBody PedidoDTO pedido) {
        PedidoDTO actualizado = service.actualizar(id, pedido);
        return ResponseEntity.ok(ApiRespuesta.exito(200, "Pedido actualizado correctamente", actualizado));
    }

    // ------------------------------------------------------------------ DELETE
    @Operation(
            summary = "Eliminar un pedido",
            description = "Elimina el pedido con el id indicado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eliminación exitosa",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaMensaje.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetro inválido",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno",
                            content = @Content(mediaType = JSON, schema = @Schema(implementation = RespuestaError.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespuesta<Void>> eliminarPedido(
            @Parameter(description = "Id del pedido a eliminar", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(ApiRespuesta.exito(200, "Pedido eliminado correctamente", null));
    }
}
