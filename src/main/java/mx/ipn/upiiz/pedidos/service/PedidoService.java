package mx.ipn.upiiz.pedidos.service;

import mx.ipn.upiiz.pedidos.dto.PedidoDTO;

import java.util.List;

/** Capa de negocio: operaciones que se pueden hacer con los pedidos. */
public interface PedidoService {

    PedidoDTO crear(PedidoDTO pedido);

    List<PedidoDTO> listar();

    PedidoDTO obtenerPorId(Long id);

    List<PedidoDTO> buscarPorEstado(String estado);

    PedidoDTO actualizar(Long id, PedidoDTO pedido);

    void eliminar(Long id);
}
