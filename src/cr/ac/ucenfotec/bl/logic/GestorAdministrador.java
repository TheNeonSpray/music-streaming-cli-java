package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.Administrador.DAOAdministrador;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.bl.exceptions.CredencialesInvalidasException;

import java.util.ArrayList;


public class GestorAdministrador {
    public static boolean existeAdministrador() throws Exception {
        return DAOAdministrador.existeAdministrador();
    }

    public static String registrarAdministrador(String correo, String usuario, String contrasenia) throws Exception {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío.");
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (contrasenia.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        Administrador nuevoAdmin = new Administrador(correo, usuario, contrasenia);
        return DAOAdministrador.registrarAdministrador(nuevoAdmin);
    }

    public static Administrador iniciarSesion(String usuario, String contrasenia) throws Exception {
        if (usuario == null || usuario.trim().isEmpty() || contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar el usuario y la contraseña.");
        }
        Administrador admin = DAOAdministrador.buscarPorCredenciales(usuario, contrasenia);
        if (admin == null) {
            throw new CredencialesInvalidasException("Usuario o contraseña de administrador incorrectos.");
        }
        return admin;
    }
    public static String cambiarContrasenia(Administrador admin, String actual, String nueva, String confirmacion) throws Exception {
        admin.modificarContrasenia(actual, nueva, confirmacion);
        DAOAdministrador.actualizarContrasenia(admin.getNombreUsuario(), nueva);
        return "Contraseña actualizada exitosamente en la base de datos.";
    }
    public static ArrayList<UsuarioFinal> obtenerUsuariosFinales() throws Exception {
        return DAOAdministrador.listarUsuariosFinales();
    }

}
