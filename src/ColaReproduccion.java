/* Clase de Kristhel (borrar este comment una vez completado el codigo) */
import java.util.ArrayList;

public class ColaReproduccion {
    //Atributos
    private ArrayList<Cancion> canciones;//Declaramos una lista de canciones para guardarlas, estos objetos deben venir de Clase Cancion
    private ArrayList<ListaReproduccion> listasReproduccion; //Declaramos lista de reproducción, los objetos de esta vienen de Clase ListaReproduccion
    private int posicionActual;
    private int posicionActualLista;

    //Contructores

    public ColaReproduccion() {
        this.canciones = new ArrayList<>();
        this.listasReproduccion = new ArrayList<>();
        this.posicionActual = 0;
        this.posicionActualLista = 0;
    }

    public ColaReproduccion(ArrayList<Cancion> canciones, ArrayList<ListaReproduccion> listasReproduccion) {
        this.canciones = canciones;
        this.listasReproduccion = listasReproduccion;
        this.posicionActual = 0;
        this.posicionActualLista = 0;
    }

    //Getter

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public int getPosicionActual() {
        return posicionActual;
    }

    public int getPosicionActualLista() {
        return posicionActualLista;
    }


    //Setter

    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    public void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

    public void setPosicionActual(int posicionActual) {
        this.posicionActual = posicionActual;
    }

    public void setPosicionActualLista(int posicionActualLista) {
        this.posicionActualLista = posicionActualLista;
    }

    //Metodo
    public void agregarCancion(Cancion cancion) { //Metodo para agregar canciones
        canciones.add(cancion); //Agrega una cancion al final de la lista canciones
    }

    public void agregarListaReproduccion(ListaReproduccion lista) {//Metodo para agregar listas de reproduccion
        listasReproduccion.add(lista);//Agrega una lista de reproduccion al final
    }

    public boolean estaVacia() { //Este metodo nos ayuda a corroborar si canciones y listasreproduccion están vacías
        boolean vacia = canciones.isEmpty() && listasReproduccion.isEmpty(); //Pero tan solo si ambas están vacías la cola estaría vacía, por eso se utiliza &&
        if (vacia) { // de estar vacía envía un mensaje
            System.out.println("No hay canciones ni listas de reproducción :(, " +
                    "Compra canciones y empieza tus listas de reproduccion!");
        }
        return vacia;
    }

    public Cancion reproCancion() { //Metodo para obtener canciones
        if (canciones.isEmpty()) return null;//Si canciones esta vacio, retorna null
        Cancion melodia = canciones.get(posicionActual); //Se obtiene la cancion que este en la posicion actual
        posicionActual++; //A posicionActual le sumamos 1, para que no se repita la misma cancion, ya que el indice sumara 1
        if (posicionActual >= canciones.size()) { //Se verifica que la posicionActual sea igual o mayor que el tamaño de canciones
            posicionActual = 0; // Si es igual o mayor se devuelve al inicio, indice 0
        }
        return melodia;
    }

    public ListaReproduccion reprolistaRepro(){//Se hace los mismo que en el metodo de cancion para este metodo
        if (listasReproduccion.isEmpty()) return null;
        ListaReproduccion lista = listasReproduccion.get(posicionActualLista);
        posicionActualLista++;
        if (posicionActualLista >= listasReproduccion.size()) {
            posicionActualLista = 0; // vuelve al inicio
        }

        return lista;
    }

    public String toString() { //Tenemos el metodo toString, nos devuelve String, texto
        return "ColaReproduccion" + //Este retorna el texto
                "canciones: " + canciones + // Da la lista de canciones
                ", listasReproduccion: " + listasReproduccion; // da la lista de listaReproduccion
    }
}
