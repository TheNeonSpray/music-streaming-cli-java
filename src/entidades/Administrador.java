package entidades;

import java.util.ArrayList;

public class Administrador extends Usuario{

    //constructor
    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        super(correoElectronico, nombreUsuario, contrasenia);
    }

    //metodos
    //Muestra los usuarios registrados
    public void mostrarUsuarios(ArrayList<UsuarioFinal> usuarios){
        if(usuarios == null || usuarios.isEmpty()){
            System.out.println("No hay usuarios registrados.");
            return;
        }
        System.out.println("=== USUARIOS REGISTRADOS ===");
        for(UsuarioFinal usuario : usuarios){
            System.out.println(usuario);
        }
    }
    //se permite la reproduccion completa de cualquier cancion sin restricciones de compra
    @Override
    public boolean puedeReproducirCompleta(Cancion cancion) {
        return true;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Administrador: \n" +
                "Correo Electrónico: " + getCorreoElectronico() + "\n" +
                "Nombre de Usuario: " + getNombreUsuario() + "\n" ;

    }
}
