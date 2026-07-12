package entidades;

import excepciones.SaldoInsuficienteException;

import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioFinal extends Usuario {
    // Inicialización de constantes
    private static final double BONO_BIENVENIDA = 4.99;
    private static final String AVATAR_PREDETERMINADO = "img/Avatar-icon.png";

    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private double saldo;

    private ArrayList<Cancion> coleccionCanciones;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ColaReproduccion colaReproduccion;

    /* Constructor */
    public UsuarioFinal(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad,
                        String cedula, String avatar, String correoElectronico,
                        String nombreUsuario, String contrasenia) {
        super(correoElectronico, nombreUsuario, contrasenia);

        setNombreCompleto(nombreCompleto);
        setFechaNacimiento(fechaNacimiento);
        setNacionalidad(nacionalidad);
        setCedula(cedula);
        setSaldo(BONO_BIENVENIDA);

        setColeccionCanciones(new ArrayList<>());
        setListasReproduccion(new ArrayList<>());
        setColaReproduccion(new ColaReproduccion());

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

    public double getSaldo() {
        return saldo;
    }

    // Se mantiene privado para que el saldo solo cambie mediante recargas y compras.
    private void setSaldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }

        this.saldo = Math.round(saldo * 100.0) / 100.0;
    }

    public ArrayList<Cancion> getColeccionCanciones() {
        return coleccionCanciones;
    }

    // Se mantiene privado para proteger la colección del usuario.
    private void setColeccionCanciones(ArrayList<Cancion> coleccionCanciones) {
        if (coleccionCanciones == null) {
            throw new IllegalArgumentException("La colección de canciones no puede ser nula.");
        }

        this.coleccionCanciones = coleccionCanciones;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    // Se mantiene privado para proteger las listas de reproducción del usuario.
    private void setListasReproduccion(ArrayList<ListaReproduccion> listasReproduccion) {
        if (listasReproduccion == null) {
            throw new IllegalArgumentException("Las listas de reproducción no pueden ser nulas.");
        }

        this.listasReproduccion = listasReproduccion;
    }

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
    }

    // Se mantiene privado para proteger la cola de reproducción del usuario.
    private void setColaReproduccion(ColaReproduccion colaReproduccion) {
        if (colaReproduccion == null) {
            throw new IllegalArgumentException("La cola de reproducción no puede ser nula.");
        }

        this.colaReproduccion = colaReproduccion;
    }

    /* Metodos */

    // Se compra una canción del catálogo: se valida el saldo disponible, se debita
    // el precio, se registra la compra y se agrega la canción a la colección.
    public boolean comprarCancion(Cancion cancion) throws SaldoInsuficienteException {
        if (cancion == null || tieneCancion(cancion)) {
            return false;
        }

        if (saldo < cancion.getPrecio()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para comprar '" + cancion.getNombre()
                    + "'. Saldo actual: $" + saldo + ", precio: $" + cancion.getPrecio() + ".");
        }

        setSaldo(saldo - cancion.getPrecio());
        cancion.registrarCompra();
        coleccionCanciones.add(cancion);
        return true;
    }

    // Se califica una canción únicamente si el usuario la ha comprado previamente.
    public boolean calificarCancion(Cancion cancion, double calificacion) {
        if (tieneCancion(cancion)) {
            return cancion.agregarCalificacion(calificacion);
        }
        return false;
    }

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
            if(lista ==null){
                return false;
            }
            for(ListaReproduccion l : listasReproduccion){
                if(l.getNombre().equalsIgnoreCase(lista.getNombre())){
                    return false;
                }
            }
            listasReproduccion.add(lista);
            return true;
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
            colaReproduccion.agregarElemento(cancion);
            return true;
        }
        return false;
    }

    public boolean agregarListaACola(ListaReproduccion lista) {
        if (lista != null && listasReproduccion.contains(lista)) {
            colaReproduccion.agregarElemento(lista);
            return true;
        }
        return false;
    }

    // Se permite la reproducción completa únicamente de las canciones compradas.
    @Override
    public boolean puedeReproducirCompleta(Cancion cancion) {
        return tieneCancion(cancion);
    }

    @Override
    public String toString() {
        return "Usuario Final: \n" +
                "Nombre completo: " + nombreCompleto + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Nacionalidad: " + nacionalidad + "\n" +
                "Cédula: " + cedula + "\n" +
                "Avatar:" + avatar + "\n" +
                "Correo electrónico: " + getCorreoElectronico() + "\n" +
                "Nombre de usuario: " + getNombreUsuario() + "\n" +
                "Saldo disponible: $" + saldo + "\n" +
                "Canciones compradas: " + coleccionCanciones.size() + "\n" +
                "Listas creadas: " + listasReproduccion.size();
    }
}