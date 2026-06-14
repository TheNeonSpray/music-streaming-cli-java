import java.time.LocalDate;
import java.util.Arrays;

public class ListaReproduccion {
    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;
    private Cancion[] canciones;

    //Constructor

    public ListaReproduccion(String nombre, LocalDate fechaCreacion, double calificacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
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

    public Cancion[] getCanciones() {
        return canciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
                ", canciones=" + Arrays.toString(canciones) +
                '}';
    }
}
