package entidades;

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
        return "entidades.ListaReproduccion{" +
                "nombre='" + nombre + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", cantidadCanciones=" + canciones.size() +
                '}';
    }
    //metodos
    public boolean agregarCancion(Cancion cancion) {
        if (cancion != null && !canciones.contains(cancion)) {
            canciones.add(cancion);
            cancion.registrarInclusionEnLista();
            calcularCalificacionPromedio();
            return true;
        }
        return false;
    }

    public boolean eliminarCancion(Cancion cancion) {
        if (cancion != null && canciones.remove(cancion)) {
            calcularCalificacionPromedio();
            return true;
        }
        return false;
    }

    public double calcularCalificacionPromedio() {
        if (canciones.isEmpty()) {
            calificacion = 0.0;
            return calificacion;
        }

        double suma = 0.0;

        for (Cancion cancion : canciones) {
            suma += cancion.getCalificacion();
        }

        calificacion = Math.round((suma / canciones.size()) * 10.0) / 10.0;
        return calificacion;
    }

}
