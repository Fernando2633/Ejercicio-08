package mx.ipn.upiiz.pedidos.repository;

import mx.ipn.upiiz.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Capa de acceso a datos. */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);

    List<Pedido> findByEstadoIgnoreCaseOrderByIdAsc(String estado);

    List<Pedido> findAllByOrderByIdAsc();
}
