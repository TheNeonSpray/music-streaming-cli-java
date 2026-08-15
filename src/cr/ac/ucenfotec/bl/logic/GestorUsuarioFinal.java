package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.UsuarioFinal.DAOUsuarioFinal;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.bl.exceptions.CredencialesInvalidasException;

import java.time.LocalDate;
import java.time.Period;

public class GestorUsuarioFinal {
    // Autenticación de Usuario Final
    public static UsuarioFinal iniciarSesion(String usuario, String contrasenia) throws Exception {
        // 1. Validar que los campos no vengan vacíos
        if (usuario == null || usuario.trim().isEmpty() || contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar el nombre de usuario y la contraseña.");
        }
        // 2. Consultar en la base de datos a través del DAO
        UsuarioFinal usuarioFinal = DAOUsuarioFinal.buscarPorCredenciales(usuario, contrasenia);
        // 3. Si no encuentra coincidencia en MySQL, lanza la excepción de credenciales inválidas
        if (usuarioFinal == null) {
            throw new CredencialesInvalidasException("Nombre de usuario o contraseña incorrectos.");
        }
        return usuarioFinal;
    }

    public static String registrarUsuarioFinal(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad, String cedula, String avatar, String correo, String usuario, String contrasenia) throws Exception {
        //Validación de campos vacíos
        if (correo == null || correo.trim().isEmpty()) throw new IllegalArgumentException("El correo no puede estar vacío.");
        if (usuario == null || usuario.trim().isEmpty()) throw new IllegalArgumentException("El usuario no puede estar vacío.");

        // Validaciion edad
        if (fechaNacimiento == null) throw new IllegalArgumentException("Debe ingresar una fecha válida.");
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < 18) throw new IllegalArgumentException("Debe ser mayor de edad para registrarse.");

        //Validación de Usuario Duplicado
        if (DAOUsuarioFinal.existeNombreUsuario(usuario)) {
            throw new IllegalArgumentException("El nombre de usuario '" + usuario + "' ya existe.");
        }
        // 4. Validación de Correo Duplicado
        if (DAOUsuarioFinal.existeCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico '" + correo + "' ya está registrado.");
        }
        UsuarioFinal nuevo = new UsuarioFinal(nombreCompleto, fechaNacimiento, nacionalidad, cedula, avatar, correo, usuario, contrasenia);
        return DAOUsuarioFinal.registrarUsuarioFinal(nuevo);
    }

    // Cambiar contraseña de un Usuario Final
    public static String cambiarContrasenia(UsuarioFinal usuario, String actual, String nueva, String confirmacion) throws Exception {
        if (usuario == null) {
            throw new IllegalArgumentException("No hay una sesión activa de usuario.");
        }

        usuario.modificarContrasenia(actual, nueva, confirmacion);
        DAOUsuarioFinal.actualizarContrasenia(usuario.getNombreUsuario(), nueva);
        return "Contraseña actualizada exitosamente en la base de datos.";
    }

    // Recargar saldo del usuario
    public static String recargarSaldo(UsuarioFinal usuario, double monto) throws Exception {
        if (usuario == null) {
            throw new IllegalArgumentException("No hay una sesión activa de usuario.");
        }

        usuario.recargarSaldo(monto); // Valida monto positivo y actualiza el atributo saldo en Java
        DAOUsuarioFinal.actualizarSaldo(usuario.getNombreUsuario(), usuario.getSaldo());

        return "Recarga exitosa. Su nuevo saldo es: $" + usuario.getSaldo();
    }
}
