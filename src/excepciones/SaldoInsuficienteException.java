package excepciones;

/* Excepción lanzada cuando un usuario final no posee saldo suficiente
   para completar la compra de una canción. */
public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
