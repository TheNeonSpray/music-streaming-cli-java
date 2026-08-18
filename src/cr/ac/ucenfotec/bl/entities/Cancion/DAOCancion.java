package cr.ac.ucenfotec.bl.entities.Cancion;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOCancion {
    private static final String COLUMNAS =
            "id, nombre, genero, artista, compositor, fecha_lanzamiento, album, " +
                    "ruta_caratula, precio, cantidad_compras, cantidad_inclusiones_en_listas, " +
                    "cantidad_calificaciones, suma_calificaciones";

    private DAOCancion() {}

    public static String registrarCancion(Cancion cancion) throws Exception {
        String sql = "INSERT INTO t_canciones " +
                "(nombre, genero, artista, compositor, fecha_lanzamiento, album, " +
                "ruta_caratula, calificacion, precio, cantidad_compras, " +
                "cantidad_inclusiones_en_listas, cantidad_calificaciones, suma_calificaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int idGenerado = Connector.getConnection().ejecutarInsercion(sql,
                cancion.getNombre(), cancion.getGenero(), cancion.getArtista(),
                cancion.getCompositor(), cancion.getFechaLanzamiento(), cancion.getAlbum(),
                cancion.getRutaCaratula(), cancion.getCalificacion(), cancion.getPrecio(),
                cancion.getCantidadCompras(), cancion.getCantidadInclusionesEnListas(),
                cancion.getCantidadCalificaciones(), cancion.getSumaCalificaciones());
        cancion.setId(idGenerado);
        return "La canción se registró correctamente en el catálogo.";
    }

    public static ArrayList<Cancion> listarCanciones() throws Exception {
        String sql = "SELECT " + COLUMNAS + " FROM t_canciones ORDER BY nombre, artista";
        return ejecutarBusqueda(sql);
    }

    public static ArrayList<Cancion> buscarPorNombre(String nombre) throws Exception {
        String sql = "SELECT " + COLUMNAS +
                " FROM t_canciones WHERE nombre LIKE ? ORDER BY nombre, artista";
        return ejecutarBusqueda(sql, "%" + nombre + "%");
    }

    public static ArrayList<Cancion> buscarPorGenero(String genero) throws Exception {
        String sql = "SELECT " + COLUMNAS +
                " FROM t_canciones WHERE genero LIKE ? ORDER BY nombre, artista";
        return ejecutarBusqueda(sql, "%" + genero + "%");
    }

    public static ArrayList<Cancion> buscarPorArtista(String artista) throws Exception {
        String sql = "SELECT " + COLUMNAS +
                " FROM t_canciones WHERE artista LIKE ? ORDER BY nombre, artista";
        return ejecutarBusqueda(sql, "%" + artista + "%");
    }

    public static boolean existeCancion(String nombre, String artista, int idExcluido) throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM t_canciones " +
                "WHERE LOWER(nombre) = LOWER(?) AND LOWER(artista) = LOWER(?) AND id <> ?";
        try (ResultSet resultado = Connector.getConnection()
                .ejecutarConsulta(sql, nombre, artista, idExcluido)) {
            return resultado.next() && resultado.getInt("total") > 0;
        }
    }

    public static boolean actualizarCancion(Cancion cancion) throws Exception {
        String sql = "UPDATE t_canciones SET nombre = ?, genero = ?, artista = ?, " +
                "compositor = ?, fecha_lanzamiento = ?, album = ?, ruta_caratula = ?, " +
                "calificacion = ?, precio = ?, cantidad_compras = ?, " +
                "cantidad_inclusiones_en_listas = ?, cantidad_calificaciones = ?, " +
                "suma_calificaciones = ? WHERE id = ?";
        int filas = Connector.getConnection().ejecutarActualizacion(sql,
                cancion.getNombre(), cancion.getGenero(), cancion.getArtista(),
                cancion.getCompositor(), cancion.getFechaLanzamiento(), cancion.getAlbum(),
                cancion.getRutaCaratula(), cancion.getCalificacion(), cancion.getPrecio(),
                cancion.getCantidadCompras(), cancion.getCantidadInclusionesEnListas(),
                cancion.getCantidadCalificaciones(), cancion.getSumaCalificaciones(),
                cancion.getId());
        return filas > 0;
    }

    public static boolean eliminarCancion(Cancion cancion) throws Exception {
        String sql = "DELETE FROM t_canciones WHERE id = ?";
        return Connector.getConnection().ejecutarActualizacion(sql, cancion.getId()) > 0;
    }

    private static ArrayList<Cancion> ejecutarBusqueda(String sql, Object... parametros)
            throws Exception {
        ArrayList<Cancion> canciones = new ArrayList<>();
        try (ResultSet resultado = Connector.getConnection().ejecutarConsulta(sql, parametros)) {
            while (resultado.next()) {
                canciones.add(construirCancion(resultado));
            }
        }
        return canciones;
    }

    private static Cancion construirCancion(ResultSet resultado) throws Exception {
        return new Cancion(
                resultado.getInt("id"),
                resultado.getString("nombre"),
                resultado.getString("genero"),
                resultado.getString("artista"),
                resultado.getString("compositor"),
                resultado.getDate("fecha_lanzamiento").toLocalDate(),
                resultado.getString("album"),
                resultado.getString("ruta_caratula"),
                resultado.getDouble("precio"),
                resultado.getInt("cantidad_compras"),
                resultado.getInt("cantidad_inclusiones_en_listas"),
                resultado.getInt("cantidad_calificaciones"),
                resultado.getDouble("suma_calificaciones")
        );
    }

    public static void registrarCompraCancion(cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal usuario, Cancion cancion) throws Exception {
        String sql = "INSERT INTO t_coleccion_usuario (id_usuario_final, id_cancion) " +
                "VALUES ((SELECT uf.id FROM t_usuarios_finales uf INNER JOIN t_usuarios u ON uf.id_usuario = u.id WHERE u.nombre_usuario = ?), ?)";
        cr.ac.ucenfotec.dl.Connector.getConnection().ejecutarActualizacion(sql, usuario.getNombreUsuario(), cancion.getId());

        String sqlUpdate = "UPDATE t_canciones SET cantidad_compras = cantidad_compras + 1 WHERE id = ?";
        cr.ac.ucenfotec.dl.Connector.getConnection().ejecutarActualizacion(sqlUpdate, cancion.getId());
    }

    public static ArrayList<Cancion> obtenerColeccionUsuario(cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal usuario) throws Exception {
        ArrayList<Cancion> coleccion = new ArrayList<>();
        String sql = "SELECT c.id, c.nombre, c.genero, c.artista, c.compositor, c.fecha_lanzamiento, " +
                "c.album, c.ruta_caratula, c.calificacion, c.precio, c.cantidad_compras, c.cantidad_inclusiones_en_listas, c.cantidad_calificaciones, c.suma_calificaciones " +
                "FROM t_canciones c " +
                "INNER JOIN t_coleccion_usuario cu ON c.id = cu.id_cancion " +
                "INNER JOIN t_usuarios_finales uf ON cu.id_usuario_final = uf.id " +
                "INNER JOIN t_usuarios u ON uf.id_usuario = u.id " +
                "WHERE u.nombre_usuario = ?";

        try (java.sql.ResultSet rs = cr.ac.ucenfotec.dl.Connector.getConnection().ejecutarConsulta(sql, usuario.getNombreUsuario())) {
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
                // Asignamos el precio utilizando su setter
                c.setPrecio(rs.getDouble("precio"));
                coleccion.add(c);
            }
        }
        return coleccion;
    }

    public static void calificarCancion(Cancion cancion, double nuevaCalificacion) throws Exception {
        String sql = "UPDATE t_canciones SET cantidad_calificaciones = cantidad_calificaciones + 1, " +
                "suma_calificaciones = suma_calificaciones + ?, " +
                "calificacion = (suma_calificaciones + ?) / (cantidad_calificaciones + 1) " +
                "WHERE id = ?";
        cr.ac.ucenfotec.dl.Connector.getConnection().ejecutarActualizacion(sql, nuevaCalificacion, nuevaCalificacion, cancion.getId());
    }
}
