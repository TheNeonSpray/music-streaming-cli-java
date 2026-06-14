import java.time.LocalDate;

public class Cancion {
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
        this.nombre = nombre;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.album = album;

        setRutaCaratula(rutaCaratula);
        setCalificacion(calificacion);
        setPrecio(precio);

        this.cantidadCompras = 0;
        this.cantidadInclusionesEnListas = 0;
        this.cantidadCalificaciones = 0;
        this.sumaCalificaciones = 0.0;
    }

    /* Getters & Setters */

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
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
        if (rutaCaratula == null || rutaCaratula.isBlank()) {
            this.rutaCaratula = CARATULA_PREDETERMINADA;
        } else {
            this.rutaCaratula = rutaCaratula;
        }
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        if (calificacion >= 0.0 && calificacion <= 5.0) {
            this.calificacion = Math.round(calificacion * 10.0) / 10.0;
        }
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0.0) {
            this.precio = Math.round(precio * 100.0) / 100.0;
        }
    }

    public int getCantidadCompras() {
        return cantidadCompras;
    }

    public void setCantidadCompras(int cantidadCompras) {
        if (cantidadCompras >= 0) {
            this.cantidadCompras = cantidadCompras;
        }
    }

    public int getCantidadInclusionesEnListas() {
        return cantidadInclusionesEnListas;
    }

    public void setCantidadInclusionesEnListas(int cantidadInclusionesEnListas) {
        if (cantidadInclusionesEnListas >= 0) {
            this.cantidadInclusionesEnListas = cantidadInclusionesEnListas;
        }
    }

    public int getCantidadCalificaciones() {
        return cantidadCalificaciones;
    }

    public void setCantidadCalificaciones(int cantidadCalificaciones) {
        if (cantidadCalificaciones >= 0) {
            this.cantidadCalificaciones = cantidadCalificaciones;
        }
    }

    public double getSumaCalificaciones() {
        return sumaCalificaciones;
    }

    public void setSumaCalificaciones(double sumaCalificaciones) {
        if (sumaCalificaciones >= 0.0) {
            this.sumaCalificaciones = sumaCalificaciones;
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
            calificacion = Math.round((sumaCalificaciones / cantidadCalificaciones) * 10.0) / 10.0;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", artista='" + artista + '\'' +
                ", compositor='" + compositor + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", album='" + album + '\'' +
                ", rutaCaratula='" + rutaCaratula + '\'' +
                ", calificacion=" + calificacion +
                ", precio=" + precio +
                ", cantidadCompras=" + cantidadCompras +
                ", cantidadInclusionesEnListas=" + cantidadInclusionesEnListas +
                ", cantidadCalificaciones=" + cantidadCalificaciones +
                '}';
    }
}