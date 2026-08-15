package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Cancion.Cancion;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GestorCola {
    private static Queue<Cancion> colaReproduccion = new LinkedList<>();

    public static Queue<Cancion> obtenerCola() {
        return colaReproduccion;
    }

    public static void encolarCancion(Cancion cancion) throws Exception {
        if (cancion == null) {
            throw new Exception("La canción seleccionada no es válida.");
        }
        colaReproduccion.add(cancion);
    }

    public static void encolarLista(List<Cancion> canciones) throws Exception {
        if (canciones == null || canciones.isEmpty()) {
            throw new Exception("La lista seleccionada no contiene canciones para encolar.");
        }
        colaReproduccion.addAll(canciones);
    }

    public static Cancion reproducirSiguiente() throws Exception {
        if (colaReproduccion.isEmpty()) {
            throw new Exception("La cola de reproducción está vacía. No hay elementos para reproducir.");
        }
        return colaReproduccion.poll();
    }

    public static boolean estaVacia() {
        return colaReproduccion.isEmpty();
    }
}

