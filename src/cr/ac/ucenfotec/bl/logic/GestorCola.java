package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.Cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.tl.Controller;

import java.util.List;

public class GestorCola {

    private GestorCola() {}

    public static void encolarCancion(Cancion cancion) throws Exception {
        if (cancion == null) {
            throw new IllegalArgumentException("La canción seleccionada no es válida.");
        }
        UsuarioFinal usuario = obtenerUsuarioFinalActivo();
        usuario.getColaReproduccion().agregar(cancion); // O el método que uses para añadir
    }

    public static void encolarLista(List<Cancion> canciones) throws Exception {
        if (canciones == null || canciones.isEmpty()) {
            throw new IllegalArgumentException("La lista seleccionada no contiene canciones.");
        }
        UsuarioFinal usuario = obtenerUsuarioFinalActivo();
        for (Cancion cancion : canciones) {
            usuario.getColaReproduccion().agregar(cancion);
        }
    }

    public static Cancion reproducirSiguiente() throws Exception {
        UsuarioFinal usuario = obtenerUsuarioFinalActivo();
        if (usuario.getColaReproduccion().estaVacia()) {
            throw new Exception("La cola de reproducción está vacía.");
        }
        return usuario.getColaReproduccion().reproducirSiguiente();
    }

    private static UsuarioFinal obtenerUsuarioFinalActivo() throws Exception {
        Object conectado = Controller.getUsuarioConectado();
        if (conectado instanceof UsuarioFinal usuarioFinal) {
            return usuarioFinal;
        }
        throw new Exception("Debe iniciar sesión como usuario final para gestionar la cola.");
    }
}