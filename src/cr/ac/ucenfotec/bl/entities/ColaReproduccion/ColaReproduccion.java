package cr.ac.ucenfotec.bl.entities.ColaReproduccion;

import cr.ac.ucenfotec.bl.entities.Cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion;
import java.util.LinkedList;
import java.util.Queue;

public class ColaReproduccion {
    private Queue<Cancion> elementos;

    public ColaReproduccion() {
        this.elementos = new LinkedList<>();
    }

    public void agregar(Cancion cancion) {
        if (cancion != null) {
            elementos.offer(cancion);
        }
    }

    public void agregarElemento(Cancion cancion) {
        agregar(cancion);
    }

    public void agregarElemento(ListaReproduccion lista) {
        if (lista != null && lista.getCanciones() != null) {
            for (Cancion c : lista.getCanciones()) {
                elementos.offer(c);
            }
        }
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    public Cancion reproducirSiguiente() {
        return elementos.poll();
    }

    public Queue<Cancion> getElementos() {
        return elementos;
    }
}