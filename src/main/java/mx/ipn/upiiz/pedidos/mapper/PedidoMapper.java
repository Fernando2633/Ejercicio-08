package mx.ipn.upiiz.pedidos.mapper;

import mx.ipn.upiiz.pedidos.dto.PedidoDTO;
import mx.ipn.upiiz.pedidos.model.Pedido;
import org.springframework.stereotype.Component;

/** Convierte entre la entidad Pedido y el DTO. */
@Component
public class PedidoMapper {

    public PedidoDTO toDto(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setCodigo(pedido.getCodigo());
        dto.setFecha(pedido.getFecha());
        dto.setTotal(pedido.getTotal());
        dto.setEstado(pedido.getEstado());
        dto.setObservaciones(pedido.getObservaciones());
        return dto;
    }

    /** Copia los datos del DTO a la entidad (el id nunca se toma del cliente). */
    public void copiarDatos(PedidoDTO dto, Pedido pedido) {
        pedido.setCodigo(dto.getCodigo().trim());
        pedido.setFecha(dto.getFecha());
        pedido.setTotal(dto.getTotal());
        pedido.setEstado(dto.getEstado());
        pedido.setObservaciones(dto.getObservaciones() == null ? null : dto.getObservaciones().trim());
    }
}
