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
        if (rutaCaratula == null || rutaCaratula.trim().isEmpty()) {
            this.rutaCaratula = CARATULA_PREDETERMINADA;
        } else {
            this.rutaCaratula = rutaCaratula;
        }
    }

    public double getCalificacion() {
        return calificacion;
    }

    // En futuras entregas se volverá privado porque reinicia el historial interno de calificaciones.
    public void setCalificacion(double calificacion) {
        inicializarCalificacion(calificacion);
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

    // En futuras entregas se volverá privado.
    public void setCantidadCalificaciones(int cantidadCalificaciones) {
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

    // En futuras entregas se volverá privado.
    public void setSumaCalificaciones(double sumaCalificaciones) {
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
                ", sumaCalificaciones=" + sumaCalificaciones +
                '}';
    }
}