import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class ListaReproduccion {
    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;
    private ArrayList<Cancion> canciones;

    //Constructor

    public ListaReproduccion(String nombre, LocalDate fechaCreacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = 0.0;
        this.canciones = new ArrayList<>();
    }
    //getter y setter
    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }
    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "ListaReproduccion{" +
                "nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", cantidadCanciones=" + canciones.size() +
                '}';
    }
    //metodos
    public void agregarCancion(Cancion cancion) {
        System.out.println("Agregando " + cancion.getNombre() + " a la lista " + nombre);
    }

    public void eliminarCancion(Cancion cancion) {
        System.out.println("Eliminando " + cancion.getNombre() + " de la lista " + nombre);
    }
    public double calcularCalificacionPromedio() {
        System.out.println("Recorriendo objetos Cancion en '" + this.nombre + "' para dar promedio de calificacion");
        return 4.5; // Retorna un valor de prueba que esté en el rango de 0.0 a 5.0
    }

}
