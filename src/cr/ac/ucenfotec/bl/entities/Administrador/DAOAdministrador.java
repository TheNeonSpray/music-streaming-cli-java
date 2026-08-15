package cr.ac.ucenfotec.bl.entities.Administrador;

import cr.ac.ucenfotec.bl.entities.Usuario.Usuario;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.dl.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOAdministrador {
    // ATRIBUTOS
    private static String statement;
    private static String query;
//verificar si ya existe admin
 public static boolean existeAdministrador() throws Exception {
    query = "SELECT COUNT(*) AS total FROM t_usuarios WHERE tipo_usuario = 'ADMINISTRADOR';";
    ResultSet resultado = Connector.getConnection().ejecutarQuery(query);
    if (!resultado.next()) {
        return false;
    }
    return resultado.getInt("total") > 0;
}

    //nuevo admin
    public static String registrarAdministrador(Administrador admin) throws Exception {
        statement = "INSERT INTO t_usuarios (correo_electronico, nombre_usuario, contrasenia, tipo_usuario) " +
                "VALUES ('" + admin.getCorreoElectronico() + "', '" +
                admin.getNombreUsuario() + "', '" +
                admin.getContrasenia() + "', 'ADMINISTRADOR');";
        Connector.getConnection().ejecutarStatement(statement);
        return "El administrador se registró en la base de datos correctamente.";
    }

    //busca las credenciales
    public static Administrador buscarPorCredenciales(String usuario, String contrasenia) throws Exception {
        query = "SELECT correo_electronico, nombre_usuario, contrasenia FROM t_usuarios WHERE nombre_usuario = '"
                + usuario + "' AND contrasenia = '" + contrasenia + "' AND tipo_usuario = 'ADMINISTRADOR';";
        ResultSet resultado = Connector.getConnection().ejecutarQuery(query);

        if (!resultado.next()) {
            return null;
        }
        return new Administrador(
                resultado.getString("correo_electronico"),
                resultado.getString("nombre_usuario"),
                resultado.getString("contrasenia")
        );
    }
    public static void actualizarContrasenia(String usuario, String nuevaContrasenia) throws Exception {
        statement = "UPDATE t_usuarios SET contrasenia = '" + nuevaContrasenia +
                "' WHERE nombre_usuario = '" + usuario + "';";
        Connector.getConnection().ejecutarStatement(statement);
    }
    public static ArrayList<UsuarioFinal> listarUsuariosFinales() throws Exception {
        query = "SELECT * FROM t_usuarios WHERE tipo_usuario = 'USUARIO_FINAL';";
        ResultSet rs = Connector.getConnection().ejecutarQuery(query);
        ArrayList<UsuarioFinal> lista = new ArrayList<>();

        while (rs.next()) {
            UsuarioFinal usuario = new UsuarioFinal(
                    rs.getString("nombre_completo"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("nacionalidad"),
                    rs.getString("cedula"),
                    rs.getString("avatar"),
                    rs.getString("correo_electronico"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasenia")
            );
            lista.add(usuario);
        }
        return lista;
    }

}

