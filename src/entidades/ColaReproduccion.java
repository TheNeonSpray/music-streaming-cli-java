package entidades;/* Clase de Kristhel (borrar este comment una vez completado el codigo) */

import java.util.ArrayList;

public class ColaReproduccion {
    //Atributos
    private ArrayList<Reproducible> elementos; // Cola dinámica única de elementos reproducibles (canciones y listas de reproducción).
    private int posicionActual;

    //Contructores

    public ColaReproduccion() {
        this.elementos = new ArrayList<>();
        this.posicionActual = 0;
    }

    public ColaReproduccion(ArrayList<Reproducible> elementos) {
        setElementos(elementos);
        this.posicionActual = 0;
    }
//Getter

    public ArrayList<Reproducible> getElementos() {
        return elementos;
    }

    public int getPosicionActual() {
        return posicionActual;
    }


    //Setter

    public void setElementos(ArrayList<Reproducible> elementos) {
        if (elementos == null) {
            throw new IllegalArgumentException("Los elementos de la cola no pueden ser nulos.");
        }
        this.elementos = elementos;
    }

    public void setPosicionActual(int posicionActual) {
        if (posicionActual < 0) {
            throw new IllegalArgumentException("La posición actual no puede ser negativa.");
        }
        this.posicionActual = posicionActual;
    }

    //Metodo

    public void agregarElemento(Reproducible elemento) { // Se agrega cualquier elemento reproducible (canción o lista) al final de la cola.
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento a encolar no puede ser nulo.");
        }
        elementos.add(elemento);
    }

    public boolean estaVacia() { // Se indica si la cola no contiene ningún elemento.
        return elementos.isEmpty();
    }

    public Reproducible siguienteElemento() { // Se obtiene el siguiente elemento de la cola de forma circular: al llegar
        if (elementos.isEmpty()) {
            return null;
        }
        return elementos.remove(0);
    }

    public String toString() { //Tenemos el metodo toString, nos devuelve String, texto
        return "ColaReproduccion" +  //Este retorna el texto
                "elementos: " + elementos;  // Da la lista de elementos
    }
}
