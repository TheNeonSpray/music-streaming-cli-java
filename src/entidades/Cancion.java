package entidades;

import java.time.LocalDate;

public class Cancion implements Reproducible {
    private static final String CARATULA_PREDETERMINADA = "img/Record-icon.png";

    private String nombre;
    private String genero;
    private String artista;
    private String compositor;
    private LocalDate fechaLanzamiento;
    private String album;
    private String rutaCaratula;
    private double calificacion;
    private double precio;

    private int cantidadCompras;
    private int cantidadInclusionesEnListas;
    private int cantidadCalificaciones;
    private double sumaCalificaciones;

    /* Constructor */
    public Cancion(String nombre, String genero, String artista, String compositor,
                   LocalDate fechaLanzamiento, String album, String rutaCaratula,
                   double calificacion, double precio) {
        setNombre(nombre);
        setGenero(genero);
        setArtista(artista);
        setCompositor(compositor);
        setFechaLanzamiento(fechaLanzamiento);
        setAlbum(album);

        setRutaCaratula(rutaCaratula);
        setPrecio(precio);

        this.cantidadCompras = 0;
        this.cantidadInclusionesEnListas = 0;
        inicializarCalificacion(calificacion);
    }

    /* Getters & Setters */

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la canción es obligatorio.");
        }
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("El género de la canción es obligatorio.");
        }
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("El artista de la canción es obligatorio.");
        }
        this.artista = artista;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        if (compositor == null || compositor.trim().isEmpty()) {
            throw new IllegalArgumentException("El compositor de la canción es obligatorio.");
        }
        this.compositor = compositor;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        if (fechaLanzamiento == null) {
            throw new IllegalArgumentException("La fecha de lanzamiento es obligatoria.");
        }

        if (fechaLanzamiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de lanzamiento no puede estar en el futuro.");
        }

        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getRutaCaratula() {
        return rutaCaratula;
    }

    public void setRutaCaratula(String rutaCaratula) {
        if (rutaCaratula == null || rutaCaratula.trim().isEmpty()) {
            this.rutaCaratula = CARATULA_PREDETERMINADA;
        } else {
            this.rutaCaratula = rutaCaratula;
        }
    }

    public double getCalificacion() {
        return calificacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0.0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        this.precio = Math.round(precio * 100.0) / 100.0;
    }

    public int getCantidadCompras() {
        return cantidadCompras;
    }

    public void setCantidadCompras(int cantidadCompras) {
        if (cantidadCompras < 0) {
            throw new IllegalArgumentException("La cantidad de compras no puede ser negativa.");
        }
        this.cantidadCompras = cantidadCompras;
    }

    public int getCantidadInclusionesEnListas() {
        return cantidadInclusionesEnListas;
    }

    public void setCantidadInclusionesEnListas(int cantidadInclusionesEnListas) {
        if (cantidadInclusionesEnListas < 0) {
            throw new IllegalArgumentException("La cantidad de inclusiones en listas no puede ser negativa.");
        }
        this.cantidadInclusionesEnListas = cantidadInclusionesEnListas;
    }

    public int getCantidadCalificaciones() {
        return cantidadCalificaciones;
    }

    // Se mantiene privado para proteger el historial de calificaciones; se empleará
    // en la reconstrucción de los datos persistidos en la entrega final.
    private void setCantidadCalificaciones(int cantidadCalificaciones) {
        if (cantidadCalificaciones < 0) {
            throw new IllegalArgumentException("La cantidad de calificaciones no puede ser negativa.");
        }

        if (cantidadCalificaciones == 0) {
            this.cantidadCalificaciones = 0;
            this.sumaCalificaciones = 0.0;
            actualizarCalificacionPromedio();
        } else if (sumaCalificaciones <= cantidadCalificaciones * 5.0) {
            this.cantidadCalificaciones = cantidadCalificaciones;
            actualizarCalificacionPromedio();
        } else {
            throw new IllegalArgumentException("La cantidad de calificaciones no coincide con la suma actual.");
        }
    }

    public double getSumaCalificaciones() {
        return sumaCalificaciones;
    }

    // Se mantiene privado para proteger el historial de calificaciones; se empleará
    // en la reconstrucción de los datos persistidos en la entrega final.
    private void setSumaCalificaciones(double sumaCalificaciones) {
        if (sumaCalificaciones < 0.0) {
            throw new IllegalArgumentException("La suma de calificaciones no puede ser negativa.");
        }

        if (cantidadCalificaciones == 0 && sumaCalificaciones == 0.0) {
            this.sumaCalificaciones = sumaCalificaciones;
            actualizarCalificacionPromedio();
        } else if (cantidadCalificaciones > 0 && sumaCalificaciones <= cantidadCalificaciones * 5.0) {
            this.sumaCalificaciones = sumaCalificaciones;
            actualizarCalificacionPromedio();
        } else {
            throw new IllegalArgumentException("La suma de calificaciones no coincide con la cantidad actual.");
        }
    }

    /* Metodos */

    public void registrarCompra() {
        cantidadCompras++;
    }

    public void registrarInclusionEnLista() {
        cantidadInclusionesEnListas++;
    }

    public boolean agregarCalificacion(double nuevaCalificacion) {
        if (nuevaCalificacion >= 0.0 && nuevaCalificacion <= 5.0) {
            sumaCalificaciones += nuevaCalificacion;
            cantidadCalificaciones++;
            actualizarCalificacionPromedio();
            return true;
        }
        return false;
    }

    // Se genera el mensaje simbólico de la reproducción completa de la canción.
    @Override
    public String reproducir() {
        return "Reproduciendo '" + nombre + "' de " + artista + "...";
    }

    // Se genera el mensaje simbólico de la reproducción de prueba de 30 segundos
    // desde una posición al azar de la canción.
    public String reproducirPreview() {
        return "Reproduciendo 30 segundos de prueba, desde una posición al azar, de '"
                + nombre + "' de " + artista + "...";
    }

    private void inicializarCalificacion(double calificacionInicial) {
        if (calificacionInicial < 0.0 || calificacionInicial > 5.0) {
            throw new IllegalArgumentException("La calificación debe estar entre 0.0 y 5.0.");
        }

        if (calificacionInicial > 0.0) {
            this.sumaCalificaciones = calificacionInicial;
            this.cantidadCalificaciones = 1;
            actualizarCalificacionPromedio();
        } else {
            this.sumaCalificaciones = 0.0;
            this.cantidadCalificaciones = 0;
            this.calificacion = 0.0;
        }
    }

    private void actualizarCalificacionPromedio() {
        if (cantidadCalificaciones > 0) {
            calificacion = Math.round((sumaCalificaciones / cantidadCalificaciones) * 10.0) / 10.0;
        } else {
            calificacion = 0.0;
        }
    }

    @Override
    public String toString() {
        return "Cancion: \n" +
                "Nombre: " + nombre + "\n" +
                "Género: " + genero + "\n" +
                "Artista: " + artista + "\n" +
                "Compositor: " + compositor + "\n" +
                "Fecha de lanzamiento: " + fechaLanzamiento + "\n" +
                "Álbum: " + (album == null || album.isBlank() ? "No pertenece a un álbum" : album) + "\n" +
                "Ruta caratula='" + rutaCaratula + "\n" +
                "Calificación: " + calificacion + "\n" +
                "Precio: $ " + precio +"\n"+
                "Compras: " + cantidadCompras +"\n"+
                "Inclusiones en Listas=" + cantidadInclusionesEnListas + "\n" +
                "Cantidad de Calificaciones: " + cantidadCalificaciones ;
    }
}