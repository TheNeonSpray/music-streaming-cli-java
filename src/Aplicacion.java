import java.util.ArrayList;

public class Aplicacion {
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

    // Métodos originados por las relaciones

    public boolean registrarAdministrador(String correo, String usuario, String contrasenia) {
        if (this.administrador == null) {
            this.administrador = new Administrador(correo, usuario, contrasenia);
            return true;
        }
        return false;
    }

    public void registrarUsuario(UsuarioFinal nuevoUsuario) {
        if (nuevoUsuario != null) {
            usuarios.add(nuevoUsuario);
        }
    }

    public void agregarCancionAlCatalogo(Cancion nuevaCancion) {
        if (nuevaCancion != null) {
            catalogo.add(nuevaCancion);
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

