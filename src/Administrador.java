
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

    @java.lang.Override
    public java.lang.String toString() {
        return "Administrador{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
