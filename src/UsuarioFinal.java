import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioFinal {
    //Inicializacion de variables
    private static final double BONO_BIENVENIDA = 4.99;
    private static final String AVATAR_PREDETERMINADO = "img/Avatar-icon.png";

    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;
    private double saldo;

    private ArrayList<Cancion> coleccionCanciones;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ColaReproduccion colaReproduccion;

    /* Constructor */
    public UsuarioFinal(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad,
                        String cedula, String avatar, String correoElectronico,
                        String nombreUsuario, String contrasenia) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.saldo = BONO_BIENVENIDA;

        this.coleccionCanciones = new ArrayList<>();
        this.listasReproduccion = new ArrayList<>();
        this.colaReproduccion = new ColaReproduccion();

        setAvatar(avatar);
    }

    /* Getters & Setters */

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if (avatar == null || avatar.isBlank()) {
            this.avatar = AVATAR_PREDETERMINADO;
        } else {
            this.avatar = avatar;
        }
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        }
    }

    public ArrayList<Cancion> getColeccionCanciones() {
        return coleccionCanciones;
    }

    public void setColeccionCanciones(ArrayList<Cancion> coleccionCanciones) {
        if (coleccionCanciones != null) {
            this.coleccionCanciones = coleccionCanciones;
        }
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        if (listasReproduccion != null) {
            this.listasReproduccion = listasReproduccion;
        }
    }

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
    }

    public void setColaReproduccion(ColaReproduccion colaReproduccion) {
        if (colaReproduccion != null) {
            this.colaReproduccion = colaReproduccion;
        }
    }

    /* Metodos */

    public boolean agregarCancionAColeccion(Cancion cancion) {
        if (cancion != null && !coleccionCanciones.contains(cancion)) {
            coleccionCanciones.add(cancion);
            return true;
        }
        return false;
    }

    public boolean eliminarCancionDeColeccion(Cancion cancion) {
        return cancion != null && coleccionCanciones.remove(cancion);
    }

    public boolean tieneCancion(Cancion cancion) {
        return cancion != null && coleccionCanciones.contains(cancion);
    }

    public boolean agregarListaReproduccion(ListaReproduccion lista) {
        if (lista != null && !listasReproduccion.contains(lista)) {
            listasReproduccion.add(lista);
            return true;
        }
        return false;
    }

    public boolean eliminarListaReproduccion(ListaReproduccion lista) {
        return lista != null && listasReproduccion.remove(lista);
    }

    public void recargarSaldo(double monto) {
        if (monto > 0) {
            saldo += monto;
        }
    }

    public boolean agregarCancionACola(Cancion cancion) {
        if (tieneCancion(cancion)) {
            colaReproduccion.agregarCancion(cancion);
            return true;
        }
        return false;
    }

    public boolean agregarListaACola(ListaReproduccion lista) {
        if (lista != null && listasReproduccion.contains(lista)) {
            colaReproduccion.agregarListaReproduccion(lista);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "UsuarioFinal{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", cedula='" + cedula + '\'' +
                ", avatar='" + avatar + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", saldo=" + saldo +
                ", cancionesCompradas=" + coleccionCanciones.size() +
                ", listasReproduccion=" + listasReproduccion.size() +
                '}';
    }
}