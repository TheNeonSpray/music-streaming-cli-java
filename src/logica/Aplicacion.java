package logica;

import entidades.Administrador;
import entidades.Cancion;
import entidades.ListaReproduccion;
import entidades.Usuario;
import entidades.UsuarioFinal;
import excepciones.CredencialesInvalidasException;

import java.util.ArrayList;

public class Aplicacion {
    // Lista predeterminada de territorios en los que se ofrece el servicio.
    private static final String[] NACIONALIDADES_DISPONIBLES = {
            "Costa Rica", "Panamá", "Nicaragua", "Guatemala", "El Salvador",
            "Honduras", "México", "Colombia", "Ecuador", "Perú",
            "Chile", "Argentina", "Uruguay", "República Dominicana", "España"
    };

    // Atributos privados completo
    private Administrador administrador;
    private ArrayList<UsuarioFinal> usuarios;
    private ArrayList<Cancion> catalogo;

    // Constructor inicia las listas
    public Aplicacion() {
        this.administrador = null; // Se registra posteriormente
        this.usuarios = new ArrayList<>();
        this.catalogo = new ArrayList<>();
    }

    // Getters y Setters
    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public ArrayList<UsuarioFinal> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioFinal> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Cancion> getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(ArrayList<Cancion> catalogo) {
        this.catalogo = catalogo;
    }

    public static String[] getNacionalidadesDisponibles() {
        return NACIONALIDADES_DISPONIBLES;
    }

    // Métodos originados por las relaciones

    // Se indica si ya existe un administrador registrado; mientras no exista,
    // la aplicación no debe permitir ninguna otra acción.
    public boolean existeAdministrador() {
        return administrador != null;
    }

    public boolean registrarAdministrador(String correo, String usuario, String contrasenia) {
        if (this.administrador == null) {
            this.administrador = new Administrador(correo, usuario, contrasenia);
            return true;
        }
        return false;
    }

    // Se registra un usuario final validando que la nacionalidad pertenezca a los
    // territorios disponibles y que el nombre de usuario y el correo sean únicos.
    public void registrarUsuario(UsuarioFinal nuevoUsuario) {
        if (nuevoUsuario != null) {
            if (!esNacionalidadValida(nuevoUsuario.getNacionalidad())) {
                throw new IllegalArgumentException("La nacionalidad debe seleccionarse de la lista de territorios disponibles.");
            }
            if (existeNombreUsuario(nuevoUsuario.getNombreUsuario())) {
                throw new IllegalArgumentException("El nombre de usuario ya se encuentra registrado.");
            }
            if (existeCorreoElectronico(nuevoUsuario.getCorreoElectronico())) {
                throw new IllegalArgumentException("El correo electrónico ya se encuentra registrado.");
            }
            usuarios.add(nuevoUsuario);
        }
    }

    public void agregarCancionAlCatalogo(Cancion nuevaCancion) {
        if (nuevaCancion != null) {
            catalogo.add(nuevaCancion);
        }
    }

    // Se valida que la nacionalidad indicada pertenezca a los territorios disponibles.
    public boolean esNacionalidadValida(String nacionalidad) {
        if (nacionalidad == null) {
            return false;
        }
        for (String territorio : NACIONALIDADES_DISPONIBLES) {
            if (territorio.equalsIgnoreCase(nacionalidad.trim())) {
                return true;
            }
        }
        return false;
    }

    // Se verifica si el nombre de usuario ya pertenece al administrador o a un usuario final.
    public boolean existeNombreUsuario(String nombreUsuario) {
        if (administrador != null && administrador.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
            return true;
        }
        for (UsuarioFinal usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    // Se verifica si el correo electrónico ya pertenece al administrador o a un usuario final.
    public boolean existeCorreoElectronico(String correoElectronico) {
        if (administrador != null && administrador.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
            return true;
        }
        for (UsuarioFinal usuario : usuarios) {
            if (usuario.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                return true;
            }
        }
        return false;
    }

    // Se autentica un usuario (administrador o final) por nombre de usuario y contraseña.
    // El retorno polimórfico permite a la interfaz decidir qué menú mostrar.
    public Usuario iniciarSesion(String nombreUsuario, String contrasenia) throws CredencialesInvalidasException {
        if (administrador != null && administrador.getNombreUsuario().equals(nombreUsuario)
                && administrador.verificarContrasenia(contrasenia)) {
            return administrador;
        }

        for (UsuarioFinal usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)
                    && usuario.verificarContrasenia(contrasenia)) {
                return usuario;
            }
        }

        throw new CredencialesInvalidasException("Nombre de usuario o contraseña incorrectos.");
    }

    // Búsquedas sobre el catálogo

    public ArrayList<Cancion> buscarCancionesPorNombre(String nombre) {
        return buscarCanciones("nombre", nombre);
    }

    public ArrayList<Cancion> buscarCancionesPorGenero(String genero) {
        return buscarCanciones("genero", genero);
    }

    public ArrayList<Cancion> buscarCancionesPorArtista(String artista) {
        return buscarCanciones("artista", artista);
    }

    // Se buscan canciones cuyo campo indicado contenga el texto, sin distinguir mayúsculas.
    private ArrayList<Cancion> buscarCanciones(String criterio, String valor) {
        ArrayList<Cancion> resultados = new ArrayList<>();
        if (valor == null || valor.trim().isEmpty()) {
            return resultados;
        }

        String buscado = valor.trim().toLowerCase();
        for (Cancion cancion : catalogo) {
            if (obtenerCampo(cancion, criterio).toLowerCase().contains(buscado)) {
                resultados.add(cancion);
            }
        }
        return resultados;
    }

    // Se obtiene el valor del campo de la canción según el criterio de búsqueda.
    private String obtenerCampo(Cancion cancion, String criterio) {
        switch (criterio) {
            case "genero":
                return cancion.getGenero();
            case "artista":
                return cancion.getArtista();
            default:
                return cancion.getNombre();
        }
    }

    // Se buscan listas de reproducción por nombre entre todos los usuarios finales
    // (empleada por el administrador, quien puede acceder a cualquier lista).
    public ArrayList<ListaReproduccion> buscarListasPorNombre(String nombre) {
        ArrayList<ListaReproduccion> resultados = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            return resultados;
        }

        String buscado = nombre.trim().toLowerCase();
        for (UsuarioFinal usuario : usuarios) {
            for (ListaReproduccion lista : usuario.getListasReproduccion()) {
                if (lista.getNombre().toLowerCase().contains(buscado)) {
                    resultados.add(lista);
                }
            }
        }
        return resultados;
    }

    // Listas top 3 generadas de forma automática; se recalculan en cada consulta.

    public ArrayList<Cancion> obtenerTop3MejorCalificadas() {
        return obtenerTop3("calificacion");
    }

    public ArrayList<Cancion> obtenerTop3MasCompradas() {
        return obtenerTop3("compras");
    }

    public ArrayList<Cancion> obtenerTop3MasIncluidasEnListas() {
        return obtenerTop3("inclusiones");
    }

    // Se seleccionan las 3 canciones con mayor valor según el criterio indicado.
    private ArrayList<Cancion> obtenerTop3(String criterio) {
        ArrayList<Cancion> candidatas = new ArrayList<>(catalogo);
        ArrayList<Cancion> top = new ArrayList<>();

        while (top.size() < 3 && !candidatas.isEmpty()) {
            Cancion mejor = candidatas.get(0);
            for (Cancion cancion : candidatas) {
                if (obtenerValorCriterio(cancion, criterio) > obtenerValorCriterio(mejor, criterio)) {
                    mejor = cancion;
                }
            }
            top.add(mejor);
            candidatas.remove(mejor);
        }
        return top;
    }

    // Se obtiene el valor numérico de la canción según el criterio del top 3.
    private double obtenerValorCriterio(Cancion cancion, String criterio) {
        switch (criterio) {
            case "compras":
                return cancion.getCantidadCompras();
            case "inclusiones":
                return cancion.getCantidadInclusionesEnListas();
            default:
                return cancion.getCalificacion();
        }
    }

    // Métodos para cumplir con la funcionalidad mínima en consola
    public void mostrarUsuarios() {
        System.out.println("--- LISTA DE USUARIOS REGISTRADOS ---");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (int i = 0; i < usuarios.size(); i++) {
                System.out.println((i + 1) + ". " + usuarios.get(i).getNombreUsuario());
            }
        }
    }

    public void mostrarCatalogo() {
        System.out.println("--- CATÁLOGO GENERAL DE CANCIONES ---");
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío.");
        } else {
            for (int i = 0; i < catalogo.size(); i++) {
                System.out.println((i + 1) + ". " + catalogo.get(i).getNombre() + " - " + catalogo.get(i).getArtista());
            }
        }
    }

    @Override
    public String toString() {
        return "Aplicacion{" +
                "administrador=" + (administrador != null ? administrador.getNombreUsuario() : "No registrado") +
                ", cantidadUsuarios=" + usuarios.size() +
                ", cantidadCancionesCatalogo=" + catalogo.size() +
                '}';
    }
}