package cr.ac.ucenfotec.bl.entities.ListaReproduccion;

import cr.ac.ucenfotec.bl.entities.Cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.dl.Connector;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOListaReproduccion {

    public static void registrarLista(UsuarioFinal usuario, ListaReproduccion lista) throws Exception {
        String sql = "INSERT INTO t_listas_reproduccion (id_usuario_final, nombre, fecha_creacion) " +
                "VALUES ((SELECT uf.id FROM t_usuarios_finales uf INNER JOIN t_usuarios u ON uf.id_usuario = u.id WHERE u.nombre_usuario = ?), ?, ?)";

        Connector.getConnection().ejecutarActualizacion(sql,
                usuario.getNombreUsuario(),
                lista.getNombre(),
                Date.valueOf(lista.getFechaCreacion()));
    }

    public static ArrayList<ListaReproduccion> obtenerListasPorUsuario(UsuarioFinal usuario) throws Exception {
        ArrayList<ListaReproduccion> listas = new ArrayList<>();
        String sql = "SELECT l.id, l.nombre, l.fecha_creacion, l.calificacion " +
                "FROM t_listas_reproduccion l " +
                "INNER JOIN t_usuarios_finales uf ON l.id_usuario_final = uf.id " +
                "INNER JOIN t_usuarios u ON uf.id_usuario = u.id " +
                "WHERE u.nombre_usuario = ?";

        try (ResultSet rs = Connector.getConnection().ejecutarConsulta(sql, usuario.getNombreUsuario())) {
            while (rs.next()) {
                ListaReproduccion l = new ListaReproduccion(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_creacion").toLocalDate(),
                        rs.getDouble("calificacion")
                );
                listas.add(l);
            }
        }
        return listas;
    }

    public static void agregarCancionALista(int idLista, int idCancion) throws Exception {
        String sql = "INSERT INTO t_lista_cancion (id_lista, id_cancion) VALUES (?, ?)";
        Connector.getConnection().ejecutarActualizacion(sql, idLista, idCancion);
    }

    public static void eliminarCancionDeLista(int idLista, int idCancion) throws Exception {
        String sql = "DELETE FROM t_lista_cancion WHERE id_lista = ? AND id_cancion = ?";
        Connector.getConnection().ejecutarActualizacion(sql, idLista, idCancion);
    }

    public static ArrayList<Cancion> obtenerCancionesDeLista(int idLista) throws Exception {
        ArrayList<Cancion> canciones = new ArrayList<>();
        String sql = "SELECT c.id, c.nombre, c.genero, c.artista, c.compositor, c.fecha_lanzamiento, " +
                "c.album, c.ruta_caratula, c.calificacion, c.precio " +
                "FROM t_canciones c " +
                "INNER JOIN t_lista_cancion lc ON c.id = lc.id_cancion " +
                "WHERE lc.id_lista = ?";

        try (ResultSet rs = Connector.getConnection().ejecutarConsulta(sql, idLista)) {
            while (rs.next()) {
                Cancion c = new Cancion(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("genero"),
                        rs.getString("artista"),
                        rs.getString("compositor"),
                        rs.getDate("fecha_lanzamiento").toLocalDate(),
                        rs.getString("album"),
                        rs.getString("ruta_caratula"),
                        rs.getDouble("calificacion"),
                        rs.getInt("cantidad_compras"),
                        rs.getInt("cantidad_inclusiones_en_listas"),
                        rs.getInt("cantidad_calificaciones"),
                        rs.getDouble("suma_calificaciones")
                );

                c.setPrecio(rs.getDouble("precio"));
                canciones.add(c);
            }
        }
        return canciones;
    }

    public static void eliminarLista(int idLista) throws Exception {
        String sql = "DELETE FROM t_listas_reproduccion WHERE id = ?";
        Connector.getConnection().ejecutarActualizacion(sql, idLista);
    }
}