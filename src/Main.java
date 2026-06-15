import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Aplicacion aplicacion = new Aplicacion();

        aplicacion.registrarAdministrador(
                "admin@musicapp.com",
                "admin",
                "Admin123!"
        );

        Cancion cancion1 = new Cancion(
                "Yellow",
                "Rock Alternativo",
                "Coldplay",
                "Coldplay",
                LocalDate.of(2000, 6, 26),
                "Parachutes",
                "",
                4.5,
                1.99
        );

        Cancion cancion2 = new Cancion(
                "Billie Jean",
                "Pop",
                "Michael Jackson",
                "Michael Jackson",
                LocalDate.of(1983, 1, 2),
                "Thriller",
                "",
                5.0,
                2.50
        );

        aplicacion.agregarCancionAlCatalogo(cancion1);
        aplicacion.agregarCancionAlCatalogo(cancion2);

        UsuarioFinal usuario = new UsuarioFinal(
                "Ana Rodríguez",
                LocalDate.of(2000, 5, 10),
                "Costa Rica",
                "1-1111-1111",
                "",
                "ana@email.com",
                "anarod",
                "Ana12345!"
        );

        aplicacion.registrarUsuario(usuario);

        usuario.agregarCancionAColeccion(cancion1);
        usuario.agregarCancionAColeccion(cancion2);

        ListaReproduccion lista = new ListaReproduccion(
                "Favoritas",
                LocalDate.now()
        );

        lista.agregarCancion(cancion1);
        lista.agregarCancion(cancion2);
        lista.calcularCalificacionPromedio();

        usuario.agregarListaReproduccion(lista);

        usuario.agregarCancionACola(cancion1);
        usuario.agregarListaACola(lista);

        System.out.println(aplicacion);
        aplicacion.mostrarCatalogo();
        aplicacion.mostrarUsuarios();

        System.out.println(usuario);
        System.out.println(lista);
        System.out.println(usuario.getColaReproduccion());
    }
}