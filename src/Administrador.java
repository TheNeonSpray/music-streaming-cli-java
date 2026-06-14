
public class Administrador {
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;

    //constructor
    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }
    //getter
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    //setter
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    //metodos
    public boolean iniciarSesion(String nombreUsuario, String contrasenia){
        System.out.println("Sesion iniciada con exito");
        return true;
    }
    public boolean modificarContrasenia(String actual, String nueva, String confirmar){
        System.out.println("Contraseña Modificada");
        return true;
    }
    public void subirCancion(Cancion cancion){
        System.out.println("Subiendo canción: " + cancion.getNombre());
    }


    @java.lang.Override
    public java.lang.String toString() {
        return "Administrador{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
