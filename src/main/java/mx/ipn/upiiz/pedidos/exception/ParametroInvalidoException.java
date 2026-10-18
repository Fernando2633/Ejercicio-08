package mx.ipn.upiiz.pedidos.exception;

/** Se traduce a HTTP 400 - parametro con valor invalido. */
public class ParametroInvalidoException extends RuntimeException {
    public ParametroInvalidoException(String mensaje) {
        super(mensaje);
    }
}
