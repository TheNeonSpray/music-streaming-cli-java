package cr.ac.ucenfotec.bl.entities.UsuarioFinal;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.Date;
import java.sql.ResultSet;

public class DAOUsuarioFinal {

    // Insertar en t_usuarios y luego en t_usuarios_finales usando PreparedStatement seguros
    public static String registrarUsuarioFinal(UsuarioFinal usuario) throws Exception {
        // 1. Insertar credenciales en t_usuarios utilizando parámetros seguros (?)
        String sqlUsuario = "INSERT INTO t_usuarios (correo_electronico, nombre_usuario, contrasenia, tipo_usuario) " +
                "VALUES (?, ?, ?, 'USUARIO_FINAL')";

        int idUsuario = Connector.getConnection().ejecutarInsercion(sqlUsuario,
                usuario.getCorreoElectronico(),
                usuario.getNombreUsuario(),
                usuario.getContrasenia());

        // 2. Insertar los datos personales en t_usuarios_finales relacionando el ID recuperado
        String sqlFinal = "INSERT INTO t_usuarios_finales (id_usuario, nombre_completo, fecha_nacimiento, nacionalidad, cedula, avatar, saldo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connector.getConnection().ejecutarActualizacion(sqlFinal,
                idUsuario,
                usuario.getNombreCompleto(),
                Date.valueOf(usuario.getFechaNacimiento()),
                usuario.getNacionalidad(),
                usuario.getCedula(),
                usuario.getAvatar(),
                usuario.getSaldo());

        return "El usuario final se registró correctamente.";
    }

    // Buscar credenciales uniendo (JOIN) ambas tablas de forma parametrizada
    public static UsuarioFinal buscarPorCredenciales(String nombreUsuario, String contrasenia) throws Exception {
        String sql = "SELECT u.correo_electronico, u.nombre_usuario, u.contrasenia, " +
                "uf.nombre_completo, uf.fecha_nacimiento, uf.nacionalidad, uf.cedula, uf.avatar " +
                "FROM t_usuarios u " +
                "INNER JOIN t_usuarios_finales uf ON u.id = uf.id_usuario " +
                "WHERE u.nombre_usuario = ? " +
                "AND u.contrasenia = ? " +
                "AND u.tipo_usuario = 'USUARIO_FINAL'";

        try (ResultSet rs = Connector.getConnection().ejecutarConsulta(sql, nombreUsuario, contrasenia)) {
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
    }

    // Actualizar la contraseña en t_usuarios de forma segura
    public static void actualizarContrasenia(String nombreUsuario, String nuevaContrasenia) throws Exception {
        String sql = "UPDATE t_usuarios SET contrasenia = ? WHERE nombre_usuario = ?";
        Connector.getConnection().ejecutarActualizacion(sql, nuevaContrasenia, nombreUsuario);
    }

    // Actualizar el saldo en t_usuarios_finales utilizando subconsulta parametrizada
    public static void actualizarSaldo(String nombreUsuario, double nuevoSaldo) throws Exception {
        String sql = "UPDATE t_usuarios_finales SET saldo = ? " +
                "WHERE id_usuario = (SELECT id FROM t_usuarios WHERE nombre_usuario = ?)";
        Connector.getConnection().ejecutarActualizacion(sql, nuevoSaldo, nombreUsuario);
    }

    // Validar si el usuario existe en t_usuarios
    public static boolean existeNombreUsuario(String usuario) throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM t_usuarios WHERE nombre_usuario = ?";
        try (ResultSet rs = Connector.getConnection().ejecutarConsulta(sql, usuario)) {
            return rs.next() && rs.getInt("total") > 0;
        }
    }

    // Validar si el correo existe en t_usuarios
    public static boolean existeCorreo(String correo) throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM t_usuarios WHERE correo_electronico = ?";
        try (ResultSet rs = Connector.getConnection().ejecutarConsulta(sql, correo)) {
            return rs.next() && rs.getInt("total") > 0;
        }
    }
}