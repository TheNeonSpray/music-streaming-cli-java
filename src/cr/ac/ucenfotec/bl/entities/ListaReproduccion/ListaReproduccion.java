package cr.ac.ucenfotec.bl.entities.ListaReproduccion;

import cr.ac.ucenfotec.bl.entities.Cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.Reproducible.Reproducible;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListaReproduccion implements Reproducible {
    private int id; // <--- Atributo de identificación para la base de datos
    private String nombre;
    private LocalDate fechaCreacion;
    private double calificacion;
    private ArrayList<Cancion> canciones;

    // Constructor para crear listas nuevas desde la UI
    public ListaReproduccion(String nombre, LocalDate fechaCreacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = 0.0;
        this.canciones = new ArrayList<>();
    }

    // Constructor para instanciar listas desde la base de datos (con ID)
    public ListaReproduccion(int id, String nombre, LocalDate fechaCreacion, double calificacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.canciones = new ArrayList<>();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    // Se mantiene privado porque la calificación solo se deriva del promedio de las canciones.
    private void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    @Override
    public String toString() {
        return "Lista de reproducción\n" +
                "Nombre: " + nombre + "\n" +
                "Fecha de creación: " + fechaCreacion + "\n" +
                "Calificación promedio: " + calificacion + "\n" +
                "Cantidad de canciones: " + canciones.size();
    }

    // Métodos lógicos
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

        setCalificacion(Math.round((suma / canciones.size()) * 10.0) / 10.0);
        return calificacion;
    }

    @Override
    public String reproducir() {
        if (canciones.isEmpty()) {
            return "La lista '" + nombre + "' no tiene canciones para reproducir.";
        }

        String mensaje = "Reproduciendo la lista '" + nombre + "':";
        for (Cancion cancion : canciones) {
            mensaje += "\n  " + cancion.reproducir();
        }
        return mensaje;
    }
}