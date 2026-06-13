import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioFinal {
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
                        String nombreUsuario, String contrasenia, double saldo) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = avatar;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.saldo = saldo;

        this.coleccionCanciones = new ArrayList<>();
        this.listasReproduccion = new ArrayList<>();
        this.colaReproduccion = null;
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
        this.avatar = avatar;
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
        this.saldo = saldo;
    }

    public ArrayList<Cancion> getColeccionCanciones() {
        return coleccionCanciones;
    }

    public void setColeccionCanciones(ArrayList<Cancion> coleccionCanciones) {
        this.coleccionCanciones = coleccionCanciones;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
    }

    public void setColaReproduccion(ColaReproduccion colaReproduccion) {
        this.colaReproduccion = colaReproduccion;
    }
}