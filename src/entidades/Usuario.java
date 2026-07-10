package entidades;

import excepciones.CredencialesInvalidasException;

/* Clase abstracta que centraliza los datos y comportamientos comunes de los
   usuarios del sistema (administrador y usuarios finales). */
public abstract class Usuario {
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;

    //constructor
    public Usuario(String correoElectronico, String nombreUsuario, String contrasenia) {
        setCorreoElectronico(correoElectronico);
        setNombreUsuario(nombreUsuario);
        setContrasenia(contrasenia);
    }
    /* Getters & Setters */

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es un dato obligatorio.");
        }

        if (!correoElectronico.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }

        this.correoElectronico = correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }

        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (contrasenia == null || !contrasenia.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9\\s])\\S{8,12}$")) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres, incluir mayúscula, minúscula, número y carácter especial, sin espacios.");
        }

        this.contrasenia = contrasenia;
    }

    /* Metodos */

    // Se verifica si la contraseña ingresada coincide con la contraseña actual del usuario.
    public boolean verificarContrasenia(String contrasenia) {
        return this.contrasenia.equals(contrasenia);
    }

    // Se modifica la contraseña validando la actual, el doble ingreso de la nueva,
    // las reglas de formato y que la nueva sea distinta a la actual.
    public void modificarContrasenia(String actual, String nueva, String confirmacion)
            throws CredencialesInvalidasException {
        if (!verificarContrasenia(actual)) {
            throw new CredencialesInvalidasException("La contraseña actual ingresada no es correcta.");
        }

        if (nueva == null || !nueva.equals(confirmacion)) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden.");
        }

        if (nueva.equals(actual)) {
            throw new IllegalArgumentException("La nueva contraseña debe ser distinta a la actual.");
        }

        setContrasenia(nueva);
    }

    // Se determina de forma polimórfica si el usuario puede reproducir una canción completa.
    public abstract boolean puedeReproducirCompleta(Cancion cancion);

}
