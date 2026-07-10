package excepciones;

/* Excepción lanzada cuando las credenciales ingresadas por un usuario
   no coinciden con las registradas en el sistema. */
public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}

