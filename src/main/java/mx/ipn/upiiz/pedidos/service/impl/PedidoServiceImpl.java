package mx.ipn.upiiz.pedidos.service.impl;

import mx.ipn.upiiz.pedidos.dto.PedidoDTO;
import mx.ipn.upiiz.pedidos.exception.ParametroInvalidoException;
import mx.ipn.upiiz.pedidos.exception.RecursoDuplicadoException;
import mx.ipn.upiiz.pedidos.exception.RecursoNoEncontradoException;
import mx.ipn.upiiz.pedidos.mapper.PedidoMapper;
import mx.ipn.upiiz.pedidos.model.Pedido;
import mx.ipn.upiiz.pedidos.repository.PedidoRepository;
import mx.ipn.upiiz.pedidos.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private static final List<String> ESTADOS = List.of("Pendiente", "Enviado", "Entregado");

    private final PedidoRepository repository;
    private final PedidoMapper mapper;

    public PedidoServiceImpl(PedidoRepository repository, PedidoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PedidoDTO crear(PedidoDTO dto) {
        String codigo = dto.getCodigo().trim();
        if (repository.existsByCodigoIgnoreCase(codigo)) {
            throw new RecursoDuplicadoException("Ya existe un pedido con el código " + codigo + ".");
        }
        Pedido pedido = new Pedido();
        mapper.copiarDatos(dto, pedido);
        return mapper.toDto(repository.save(pedido));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listar() {
        return repository.findAllByOrderByIdAsc().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO obtenerPorId(Long id) {
        return mapper.toDto(buscarEntidad(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> buscarPorEstado(String estado) {
        if (estado == null || ESTADOS.stream().noneMatch(e -> e.equalsIgnoreCase(estado.trim()))) {
            throw new ParametroInvalidoException("El estado debe ser Pendiente, Enviado o Entregado.");
        }
        return repository.findByEstadoIgnoreCaseOrderByIdAsc(estado.trim()).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public PedidoDTO actualizar(Long id, PedidoDTO dto) {
        Pedido pedido = buscarEntidad(id);
        String codigo = dto.getCodigo().trim();
        if (repository.existsByCodigoIgnoreCaseAndIdNot(codigo, id)) {
            throw new RecursoDuplicadoException("El código " + codigo + " ya lo tiene otro pedido.");
        }
        mapper.copiarDatos(dto, pedido);
        return mapper.toDto(repository.save(pedido));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.delete(buscarEntidad(id));
    }

    /** Valida el id y busca el pedido; 400 si el id no es valido y 404 si no existe. */
    private Pedido buscarEntidad(Long id) {
        if (id == null || id <= 0) {
            throw new ParametroInvalidoException("El id debe ser un número entero mayor a 0.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el pedido con id " + id + "."));
    }
}
