package cr.ac.ucenfotec.bl.entities.UsuarioFinal;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.Date;
import java.sql.ResultSet;

public class DAOUsuarioFinal {

    private static String statement;
    private static String query;

    // Insertar en t_usuarios y luego en t_usuarios_finales
    public static String registrarUsuarioFinal(UsuarioFinal usuario) throws Exception {
        // 1. Insertar credenciales en t_usuarios
        statement = "INSERT INTO t_usuarios (correo_electronico, nombre_usuario, contrasenia, tipo_usuario) " +
                "VALUES ('" + usuario.getCorreoElectronico() + "', '" +
                usuario.getNombreUsuario() + "', '" +
                usuario.getContrasenia() + "', 'USUARIO_FINAL');";
        Connector.getConnection().ejecutarStatement(statement);

        // 2. Obtener el ID generado para el usuario
        query = "SELECT id FROM t_usuarios WHERE nombre_usuario = '" + usuario.getNombreUsuario() + "';";
        ResultSet rs = Connector.getConnection().ejecutarQuery(query);

        int idUsuario = 0;
        if (rs.next()) {
            idUsuario = rs.getInt("id");
        }

        // 3. Insertar los datos personales en t_usuarios_finales relacionando el ID
        statement = "INSERT INTO t_usuarios_finales (id_usuario, nombre_completo, fecha_nacimiento, nacionalidad, cedula, avatar, saldo) " +
                "VALUES (" + idUsuario + ", '" +
                usuario.getNombreCompleto() + "', '" +
                Date.valueOf(usuario.getFechaNacimiento()) + "', '" +
                usuario.getNacionalidad() + "', '" +
                usuario.getCedula() + "', '" +
                usuario.getAvatar() + "', " +
                usuario.getSaldo() + ");";

        Connector.getConnection().ejecutarStatement(statement);
        return "El usuario final se registró correctamente.";
    }

    // Buscar credenciales uniendo (JOIN) ambas tablas
    public static UsuarioFinal buscarPorCredenciales(String nombreUsuario, String contrasenia) throws Exception {
        query = "SELECT u.correo_electronico, u.nombre_usuario, u.contrasenia, " +
                "uf.nombre_completo, uf.fecha_nacimiento, uf.nacionalidad, uf.cedula, uf.avatar " +
                "FROM t_usuarios u " +
                "INNER JOIN t_usuarios_finales uf ON u.id = uf.id_usuario " +
                "WHERE u.nombre_usuario = '" + nombreUsuario + "' " +
                "AND u.contrasenia = '" + contrasenia + "' " +
                "AND u.tipo_usuario = 'USUARIO_FINAL';";

        ResultSet rs = Connector.getConnection().ejecutarQuery(query);

        if (!rs.next()) {
            return null;
        }

        return new UsuarioFinal(
                rs.getString("nombre_completo"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("nacionalidad"),
                rs.getString("cedula"),
                rs.getString("avatar"),
                rs.getString("correo_electronico"),
                rs.getString("nombre_usuario"),
                rs.getString("contrasenia")
        );
    }

    // Actualizar la contraseña en t_usuarios
    public static void actualizarContrasenia(String nombreUsuario, String nuevaContrasenia) throws Exception {
        statement = "UPDATE t_usuarios SET contrasenia = '" + nuevaContrasenia +
                "' WHERE nombre_usuario = '" + nombreUsuario + "';";
        Connector.getConnection().ejecutarStatement(statement);
    }

    // Actualizar el saldo en t_usuarios_finales
    public static void actualizarSaldo(String nombreUsuario, double nuevoSaldo) throws Exception {
        statement = "UPDATE t_usuarios_finales SET saldo = " + nuevoSaldo +
                " WHERE id_usuario = (SELECT id FROM t_usuarios WHERE nombre_usuario = '" + nombreUsuario + "');";
        Connector.getConnection().ejecutarStatement(statement);
    }

    // Validar si el usuario existe en t_usuarios
    public static boolean existeNombreUsuario(String usuario) throws Exception {
        query = "SELECT COUNT(*) AS total FROM t_usuarios WHERE nombre_usuario = '" + usuario + "';";
        ResultSet rs = Connector.getConnection().ejecutarQuery(query);
        return rs.next() && rs.getInt("total") > 0;
    }

    // Validar si el correo existe en t_usuarios
    public static boolean existeCorreo(String correo) throws Exception {
        query = "SELECT COUNT(*) AS total FROM t_usuarios WHERE correo_electronico = '" + correo + "';";
        ResultSet rs = Connector.getConnection().ejecutarQuery(query);
        return rs.next() && rs.getInt("total") > 0;
    }
}