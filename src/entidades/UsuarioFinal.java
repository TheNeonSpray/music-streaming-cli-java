package entidades;

import excepciones.SaldoInsuficienteException;

import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioFinal {
    // Inicialización de constantes
    private static final double BONO_BIENVENIDA = 4.99;
    private static final String AVATAR_PREDETERMINADO = "img/Avatar-icon.png";

    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia; /* En futuras entregas, la contraseña se almacenará de forma segura mediante hash. */
    private double saldo;

    private ArrayList<Cancion> coleccionCanciones;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ColaReproduccion colaReproduccion;

    /* Constructor */
    public UsuarioFinal(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad,
                        String cedula, String avatar, String correoElectronico,
                        String nombreUsuario, String contrasenia) {
        setNombreCompleto(nombreCompleto);
        setFechaNacimiento(fechaNacimiento);
        setNacionalidad(nacionalidad);
        setCedula(cedula);
        setCorreoElectronico(correoElectronico);
        setNombreUsuario(nombreUsuario);
        setContrasenia(contrasenia);
        setSaldo(BONO_BIENVENIDA);

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
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre completo no puede estar vacio");
        }
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar en el futuro.");
        }

        if (java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad.");
        }

        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new IllegalArgumentException("Nacionalidad es un dato requerido.");
        }
        this.nacionalidad = nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese un numero de identificacion");
        }
        this.cedula = cedula;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if (avatar == null || avatar.trim().isEmpty()) {
            this.avatar = AVATAR_PREDETERMINADO;
        } else {
            this.avatar = avatar;
        }
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es un dato obligatorio.");
        }

        if (!correoElectronico.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }

        this.correoElectronico = correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }

        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        if (contrasenia == null || !contrasenia.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9\\s])\\S{8,12}$")) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres, incluir mayúscula, minúscula, número y carácter especial, sin espacios.");
        }

        this.contrasenia = contrasenia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }

        this.saldo = Math.round(saldo * 100.0) / 100.0;
    }

    public ArrayList<Cancion> getColeccionCanciones() {
        return coleccionCanciones;
    }

    // En futuras entregas se volverá privado para proteger la colección del usuario.
    public void setColeccionCanciones(ArrayList<Cancion> coleccionCanciones) {
        if (coleccionCanciones == null) {
            throw new IllegalArgumentException("La colección de canciones no puede ser nula.");
        }

        this.coleccionCanciones = coleccionCanciones;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    // En futuras entregas se volverá privado para proteger las listas de reproducción del usuario.
    public void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        if (listasReproduccion == null) {
            throw new IllegalArgumentException("Las listas de reproducción no pueden ser nulas.");
        }

        this.listasReproduccion = listasReproduccion;
    }

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
    }

    // En futuras entregas se volverá privado para proteger la cola de reproducción del usuario.
    public void setColaReproduccion(ColaReproduccion colaReproduccion) {
        if (colaReproduccion == null) {
            throw new IllegalArgumentException("La cola de reproducción no puede ser nula.");
        }

        this.colaReproduccion = colaReproduccion;
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
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de recarga debe ser mayor que cero.");
        }

        saldo += monto;
        saldo = Math.round(saldo * 100.0) / 100.0;
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
        return "entidades.UsuarioFinal{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", cedula='" + cedula + '\'' +
                ", avatar='" + avatar + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", saldo=" + saldo +
                ", coleccionCanciones=" + coleccionCanciones.size() +
                ", listasReproduccion=" + listasReproduccion.size() +
                '}';
    }
}