package mx.ipn.upiiz.pedidos.exception;

/** Se traduce a HTTP 404 - no existe el recurso. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
