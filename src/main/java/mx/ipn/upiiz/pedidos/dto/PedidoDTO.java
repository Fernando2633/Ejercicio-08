package mx.ipn.upiiz.pedidos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/** DTO que se usa para recibir y regresar pedidos. El id es de solo lectura. */
@Schema(name = "Pedido", description = "Datos de un pedido")
public class PedidoDTO {

    // READ_ONLY: no aparece en el cuerpo del POST/PUT, solo en las respuestas
    @Schema(description = "Identificador del pedido", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Código del pedido", example = "PED-0001", maxLength = 50)
    @NotBlank(message = "El código es obligatorio.")
    @Size(max = 50, message = "El código no puede pasar de 50 caracteres.")
    private String codigo;

    @Schema(description = "Fecha del pedido (aaaa-mm-dd)", example = "2026-10-18", type = "string", format = "date")
    @NotNull(message = "La fecha es obligatoria.")
    @PastOrPresent(message = "La fecha del pedido no puede ser futura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Schema(description = "Monto total del pedido", example = "1250.50")
    @NotNull(message = "El total es obligatorio.")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0.")
    @Digits(integer = 8, fraction = 2, message = "El total debe tener máximo 8 enteros y 2 decimales.")
    private BigDecimal total;

    @Schema(description = "Estado del pedido", example = "Pendiente",
            allowableValues = {"Pendiente", "Enviado", "Entregado"})
    @NotBlank(message = "El estado es obligatorio.")
    @Pattern(regexp = "Pendiente|Enviado|Entregado",
            message = "El estado debe ser Pendiente, Enviado o Entregado.")
    private String estado;

    @Schema(description = "Comentarios adicionales (opcional)", example = "Entregar en horario matutino", maxLength = 255)
    @Size(max = 255, message = "Las observaciones no pueden pasar de 255 caracteres.")
    private String observaciones;

    public PedidoDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
