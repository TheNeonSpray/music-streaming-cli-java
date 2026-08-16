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
}
