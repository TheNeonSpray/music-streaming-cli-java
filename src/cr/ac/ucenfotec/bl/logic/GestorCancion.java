package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.Cancion.DAOCancion;
import cr.ac.ucenfotec.bl.exceptions.CancionDuplicadaException;
import cr.ac.ucenfotec.bl.exceptions.CancionNoEncontradaException;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorCancion {
    private GestorCancion() {
        // Esta clase contiene únicamente operaciones estáticas.
    }

    public static String registrarCancion(String nombre, String genero, String artista,
                                          String compositor, LocalDate fechaLanzamiento,
                                          String album, String rutaCaratula, double precio)
            throws Exception {
        validarTexto(nombre, "nombre");
        validarTexto(genero, "género");
        validarTexto(artista, "artista");
        validarTexto(compositor, "compositor");

        if (DAOCancion.existeCancion(nombre.trim(), artista.trim(), 0)) {
            throw new CancionDuplicadaException(
                    "Ya existe una canción llamada '" + nombre + "' del artista '" + artista + "'.");
        }

        // Una canción nueva todavía no posee calificaciones de usuarios.
        Cancion cancion = new Cancion(nombre.trim(), genero.trim(), artista.trim(),
                compositor.trim(), fechaLanzamiento, normalizarOpcional(album),
                normalizarOpcional(rutaCaratula), 0.0, precio);
        return DAOCancion.registrarCancion(cancion);
    }

    public static ArrayList<Cancion> obtenerCatalogo() throws Exception {
        return DAOCancion.listarCanciones();
    }

    public static ArrayList<Cancion> buscarPorNombre(String nombre) throws Exception {
        validarTexto(nombre, "nombre para buscar");
        return DAOCancion.buscarPorNombre(nombre.trim());
    }

    public static ArrayList<Cancion> buscarPorGenero(String genero) throws Exception {
        validarTexto(genero, "género para buscar");
        return DAOCancion.buscarPorGenero(genero.trim());
    }

    public static ArrayList<Cancion> buscarPorArtista(String artista) throws Exception {
        validarTexto(artista, "artista para buscar");
        return DAOCancion.buscarPorArtista(artista.trim());
    }

    public static String actualizarCancion(Cancion cancion, String nombre, String genero,
                                           String artista, String compositor,
                                           LocalDate fechaLanzamiento, String album,
                                           String rutaCaratula, double precio) throws Exception {
        validarCancionPersistida(cancion);
        validarTexto(nombre, "nombre");
        validarTexto(genero, "género");
        validarTexto(artista, "artista");
        validarTexto(compositor, "compositor");

        if (DAOCancion.existeCancion(nombre.trim(), artista.trim(), cancion.getId())) {
            throw new CancionDuplicadaException(
                    "Ya existe otra canción llamada '" + nombre + "' del artista '" + artista + "'.");
        }

        cancion.setNombre(nombre.trim());
        cancion.setGenero(genero.trim());
        cancion.setArtista(artista.trim());
        cancion.setCompositor(compositor.trim());
        cancion.setFechaLanzamiento(fechaLanzamiento);
        cancion.setAlbum(normalizarOpcional(album));
        cancion.setRutaCaratula(normalizarOpcional(rutaCaratula));
        cancion.setPrecio(precio);

        if (!DAOCancion.actualizarCancion(cancion)) {
            throw new CancionNoEncontradaException("La canción ya no existe en la base de datos.");
        }
        return "La canción se actualizó correctamente.";
    }

    public static String eliminarCancion(Cancion cancion) throws Exception {
        validarCancionPersistida(cancion);
        if (!DAOCancion.eliminarCancion(cancion)) {
            throw new CancionNoEncontradaException("La canción ya no existe en la base de datos.");
        }
        return "La canción se eliminó correctamente del catálogo.";
    }

    private static void validarCancionPersistida(Cancion cancion) {
        if (cancion == null || cancion.getId() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una canción guardada en la base de datos.");
        }
    }

    private static void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }

    private static String normalizarOpcional(String valor) {
        return valor == null || valor.trim().isEmpty() ? null : valor.trim();
    }
}
