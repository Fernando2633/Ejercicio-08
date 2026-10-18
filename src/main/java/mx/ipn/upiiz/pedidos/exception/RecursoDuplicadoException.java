package mx.ipn.upiiz.pedidos.exception;

/** Se traduce a HTTP 409 - el recurso ya existe (duplicado). */
public class RecursoDuplicadoException extends RuntimeException {
    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
