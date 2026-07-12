package ui;

import entidades.Administrador;
import entidades.Cancion;
import entidades.ColaReproduccion;
import entidades.ListaReproduccion;
import entidades.Reproducible;
import entidades.Usuario;
import entidades.UsuarioFinal;
import excepciones.CredencialesInvalidasException;
import excepciones.SaldoInsuficienteException;
import logica.Aplicacion;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/* Clase de la capa de interfaz: concentra toda la interacción por consola
   y el manejo de excepciones de cara al usuario. */
public class MenuConsola {
    private final Aplicacion aplicacion;
    private final Scanner scanner;
    private final ColaReproduccion colaAdministrador; // Cola de reproducción de la sesión del administrador.

    //constructor
    public MenuConsola(Aplicacion aplicacion) {
        if(aplicacion ==null){
            throw new IllegalArgumentException("La aplicación no puede ser nula.");
        }
        this.aplicacion = aplicacion;
        this.scanner = new Scanner(System.in);
        this.colaAdministrador =new ColaReproduccion();
    }
    //metodos

    //Se inicia la app: exige el registro del admi antes de permitir cualquier otra accion
    //tal como se menciona en la consigna
    public void iniciar(){
        System.out.println("=== BIENVENIDO A MUSIC STREAMING APP ===");
        asegurarAdministrador();
        cicloPrincipal();
        System.out.println("¡Hasta pronto!");
    }
    // Se solicita el registro del administrador mientras no exista uno.
    private void asegurarAdministrador() {
        while (!aplicacion.existeAdministrador()) {
            System.out.println("\nNo hay un administrador registrado. Debe registrarlo para continuar.");
            try {
                System.out.print("Correo electrónico: ");
                String correo = scanner.nextLine().trim();
                System.out.print("Nombre de usuario: ");
                String usuario = scanner.nextLine().trim();
                String contrasenia = leerContraseniaConfirmada();
                aplicacion.registrarAdministrador(correo, usuario, contrasenia);
                System.out.println("Administrador registrado con éxito.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Se muestra el menú inicial de la aplicación.
    private void cicloPrincipal() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse como usuario final");
            System.out.println("0. Salir");
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarUsuarioFinal();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
    // Se autentica al usuario y se despacha el menú según su tipo (polimorfismo).
    private void iniciarSesion() {
        try {
            System.out.print("Nombre de usuario: ");
            String nombreUsuario = scanner.nextLine().trim();
            System.out.print("Contraseña: ");
            String contrasenia = scanner.nextLine();

            Usuario sesion = aplicacion.iniciarSesion(nombreUsuario, contrasenia);
            System.out.println("Sesión iniciada con éxito. ¡Bienvenido, " + sesion.getNombreUsuario() + "!");

            if (sesion instanceof Administrador) {
                menuAdministrador((Administrador) sesion);
            } else {
                menuUsuarioFinal((UsuarioFinal) sesion);
            }
        } catch (CredencialesInvalidasException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Se registra un usuario final solicitando todos los datos de la consigna.
    private void registrarUsuarioFinal() {
        try {
            System.out.print("Nombre completo: ");
            String nombreCompleto = scanner.nextLine().trim();
            LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (AAAA-MM-DD): ");
            String nacionalidad = seleccionarNacionalidad();
            System.out.print("Cédula: ");
            String cedula = scanner.nextLine();
            System.out.print("Ruta del avatar (opcional, Enter para usar el predeterminado): ");
            String avatar = scanner.nextLine();
            System.out.print("Correo electrónico: ");
            String correo = scanner.nextLine().trim();
            System.out.print("Nombre de usuario: ");
            String nombreUsuario = scanner.nextLine().trim();
            String contrasenia = leerContraseniaConfirmada();

            UsuarioFinal nuevoUsuario = new UsuarioFinal(nombreCompleto, fechaNacimiento, nacionalidad,
                    cedula, avatar, correo, nombreUsuario, contrasenia);
            aplicacion.registrarUsuario(nuevoUsuario);
            System.out.println("Usuario registrado con éxito. Se acreditó un bono de bienvenida de $4.99.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Se solicita la contraseña dos veces y se valida que ambas coincidan.
    private String leerContraseniaConfirmada() {
        System.out.print("Contraseña: ");
        String contrasenia = scanner.nextLine();
        System.out.print("Repita la contraseña: ");
        String confirmacion = scanner.nextLine();
        if (!contrasenia.equals(confirmacion)) {
            throw new IllegalArgumentException("Las contraseñas ingresadas no coinciden.");
        }
        return contrasenia;
    }

    // Se muestra la lista predeterminada de territorios y se selecciona uno.
    private String seleccionarNacionalidad() {
        String[] nacionalidades = Aplicacion.getNacionalidadesDisponibles();
        System.out.println("Nacionalidades disponibles:");
        for (int i = 0; i < nacionalidades.length; i++) {
            System.out.println((i + 1) + ". " + nacionalidades[i]);
        }
        int opcion = leerEntero("Seleccione su nacionalidad: ");
        if (opcion < 1 || opcion > nacionalidades.length) {
            throw new IllegalArgumentException("La nacionalidad debe seleccionarse de la lista de territorios disponibles.");
        }
        return nacionalidades[opcion - 1];
    }

    /* Menú del usuario final */

    private void menuUsuarioFinal(UsuarioFinal usuario) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("\n--- MENÚ DE USUARIO (" + usuario.getNombreUsuario() + " | saldo: $" + usuario.getSaldo() + ") ---");
            System.out.println("1. Ver catálogo");
            System.out.println("2. Buscar canciones");
            System.out.println("3. Reproducir una canción");
            System.out.println("4. Comprar una canción");
            System.out.println("5. Ver mi colección");
            System.out.println("6. Calificar una canción");
            System.out.println("7. Mis listas de reproducción");
            System.out.println("8. Cola de reproducción");
            System.out.println("9. Ver top 3s");
            System.out.println("10. Recargar saldo");
            System.out.println("11. Cambiar contraseña");
            System.out.println("12. Ver mi perfil");
            System.out.println("0. Cerrar sesión");
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    mostrarCatalogoDetallado();
                    break;
                case 2:
                    buscarCanciones();
                    break;
                case 3:
                    reproducirCancion(usuario);
                    break;
                case 4:
                    comprarCancion(usuario);
                    break;
                case 5:
                    mostrarColeccion(usuario);
                    break;
                case 6:
                    calificarCancion(usuario);
                    break;
                case 7:
                    menuListas(usuario);
                    break;
                case 8:
                    menuCola(usuario.getColaReproduccion(), usuario);
                    break;
                case 9:
                    mostrarTops();
                    break;
                case 10:
                    recargarSaldo(usuario);
                    break;
                case 11:
                    cambiarContrasenia(usuario);
                    break;
                case 12:
                    System.out.println(usuario);
                    break;
                case 0:
                    cerrarSesion = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Se reproduce una canción del catálogo: completa si el usuario puede (la compró
    // o es administrador), o una prueba de 30 segundos en caso contrario.
    private void reproducirCancion(Usuario usuario) {
        Cancion cancion = seleccionarCancionDe(aplicacion.getCatalogo(), "Seleccione la canción: ");
        if (cancion == null) {
            return;
        }

        if (usuario.puedeReproducirCompleta(cancion)) {
            System.out.println(cancion.reproducir());
        } else {
            System.out.println("No ha comprado esta canción. Se reproducirá una prueba de 30 segundos.");
            System.out.println(cancion.reproducirPreview());
        }
    }

    private void comprarCancion(UsuarioFinal usuario) {
        Cancion cancion = seleccionarCancionDe(aplicacion.getCatalogo(), "Seleccione la canción a comprar: ");
        if (cancion == null) {
            return;
        }

        try {
            if (usuario.comprarCancion(cancion)) {
                System.out.println("Compra realizada. Saldo restante: $" + usuario.getSaldo());
            } else {
                System.out.println("La canción ya se encuentra en su colección.");
            }
        } catch (SaldoInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarColeccion(UsuarioFinal usuario) {
        System.out.println("--- MI COLECCIÓN ---");
        if (usuario.getColeccionCanciones().isEmpty()) {
            System.out.println("Su colección está vacía. Compre canciones del catálogo.");
            return;
        }
        int i = 1;
        for (Cancion cancion : usuario.getColeccionCanciones()) {
            System.out.println(i + ". " + cancion.getNombre());
            System.out.println("   " + cancion.getArtista());
            System.out.println("   Género: " + cancion.getGenero());
            System.out.println("   Calificación: ★ " + cancion.getCalificacion());
            System.out.println("   Precio: $" + cancion.getPrecio());
            System.out.println();

            i++;
        }
    }

    private void calificarCancion(UsuarioFinal usuario) {
        Cancion cancion = seleccionarCancionDe(usuario.getColeccionCanciones(), "Seleccione la canción a calificar: ");
        if (cancion == null) {
            return;
        }

        double calificacion = leerDouble("Calificación (0.0 a 5.0): ");
        if (usuario.calificarCancion(cancion, calificacion)) {
            System.out.println("Calificación registrada. Promedio actual: " + cancion.getCalificacion());
        } else {
            System.out.println("La calificación debe estar entre 0.0 y 5.0 y la canción debe estar comprada.");
        }
    }

    /* Submenú de listas de reproducción del usuario final */

    private void menuListas(UsuarioFinal usuario) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MIS LISTAS DE REPRODUCCIÓN ---");
            System.out.println("1. Ver mis listas");
            System.out.println("2. Crear una lista");
            System.out.println("3. Agregar una canción a una lista");
            System.out.println("4. Eliminar una canción de una lista");
            System.out.println("5. Eliminar una lista");
            System.out.println("6. Reproducir una lista");
            System.out.println("7. Buscar mis listas por nombre");
            System.out.println("0. Volver");
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    mostrarListas(usuario.getListasReproduccion());
                    break;
                case 2:
                    crearLista(usuario);
                    break;
                case 3:
                    agregarCancionALista(usuario);
                    break;
                case 4:
                    eliminarCancionDeLista(usuario);
                    break;
                case 5:
                    eliminarLista(usuario);
                    break;
                case 6:
                    reproducirLista(usuario);
                    break;
                case 7:
                    buscarMisListas(usuario);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void mostrarListas(ArrayList<ListaReproduccion> listas) {
        if (listas.isEmpty()) {
            System.out.println("No hay listas de reproducción.");
            return;
        }
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i));
            for (Cancion cancion : listas.get(i).getCanciones()) {
                System.out.println("     - " + cancion.getNombre() + " (" + cancion.getArtista() + ")");
            }
        }
    }

    private void crearLista(UsuarioFinal usuario) {
        try {
            System.out.print("Nombre de la lista: ");
            String nombre = scanner.nextLine();
            ListaReproduccion lista = new ListaReproduccion(nombre, LocalDate.now());

            if (usuario.agregarListaReproduccion(lista)) {
                System.out.println("Lista creada con éxito.");
            } else {
                System.out.println("Ya existe una lista con ese nombre.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCancionALista(UsuarioFinal usuario) {
        ListaReproduccion lista = seleccionarLista(usuario.getListasReproduccion(), "Seleccione la lista: ");
        if (lista == null) {
            return;
        }
        // Solo pueden agregarse a listas las canciones que ya están en la colección.
        Cancion cancion = seleccionarCancionDe(usuario.getColeccionCanciones(), "Seleccione la canción de su colección: ");
        if (cancion == null) {
            return;
        }
        if (lista.agregarCancion(cancion)) {
            System.out.println("Canción agregada a la lista.");
        } else {
            System.out.println("La canción ya se encuentra en la lista.");
        }
    }

    private void eliminarCancionDeLista(UsuarioFinal usuario) {
        ListaReproduccion lista = seleccionarLista(usuario.getListasReproduccion(), "Seleccione la lista: ");
        if (lista == null) {
            return;
        }
        Cancion cancion = seleccionarCancionDe(lista.getCanciones(), "Seleccione la canción a eliminar: ");
        if (cancion == null) {
            return;
        }
        if (lista.eliminarCancion(cancion)) {
            System.out.println("Canción eliminada de la lista.");
        }
    }

    private void eliminarLista(UsuarioFinal usuario) {
        ListaReproduccion lista = seleccionarLista(usuario.getListasReproduccion(), "Seleccione la lista a eliminar: ");
        if (lista != null && usuario.eliminarListaReproduccion(lista)) {
            System.out.println("Lista eliminada.");
        }
    }

    private void reproducirLista(UsuarioFinal usuario) {
        ListaReproduccion lista = seleccionarLista(usuario.getListasReproduccion(), "Seleccione la lista a reproducir: ");
        if (lista != null) {
            System.out.println(lista.reproducir());
        }
    }

    private void buscarMisListas(UsuarioFinal usuario) {
        System.out.print("Nombre a buscar: ");
        String nombre = scanner.nextLine();
        ArrayList<ListaReproduccion> resultados = new ArrayList<>();
        for (ListaReproduccion lista : usuario.getListasReproduccion()) {
            if (lista.getNombre().toLowerCase().contains(nombre.trim().toLowerCase())) {
                resultados.add(lista);
            }
        }
        mostrarListas(resultados);
    }

    /* Submenú de la cola de reproducción (compartido por usuario final y administrador) */

    private void menuCola(ColaReproduccion cola, UsuarioFinal usuario) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- COLA DE REPRODUCCIÓN ---");
            System.out.println("1. Ver la cola");
            System.out.println("2. Agregar una canción a la cola");
            System.out.println("3. Agregar una lista a la cola");
            System.out.println("4. Reproducir el siguiente elemento");
            System.out.println("0. Volver");
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    if (cola.estaVacia()) {
                        System.out.println("No hay canciones ni listas de reproducción :(, "
                                + "¡Compra canciones y empieza tus listas de reproduccion!");
                    } else {
                        System.out.println(cola);
                    }
                    break;
                case 2:
                    agregarCancionACola(cola, usuario);
                    break;
                case 3:
                    agregarListaACola(cola, usuario);
                    break;
                case 4:
                    reproducirSiguiente(cola);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Se agrega una canción a la cola: el usuario final solo puede encolar canciones
    // compradas; el administrador puede encolar cualquier canción del catálogo.
    private void agregarCancionACola(ColaReproduccion cola, UsuarioFinal usuario) {
        if (usuario != null) {
            Cancion cancion = seleccionarCancionDe(usuario.getColeccionCanciones(), "Seleccione la canción de su colección: ");
            if (cancion != null && usuario.agregarCancionACola(cancion)) {
                System.out.println("Canción agregada a la cola.");
            }
        } else {
            Cancion cancion = seleccionarCancionDe(aplicacion.getCatalogo(), "Seleccione la canción del catálogo: ");
            if (cancion != null) {
                cola.agregarElemento(cancion);
                System.out.println("Canción agregada a la cola.");
            }
        }
    }

    // Se agrega una lista a la cola: el usuario final solo sus propias listas;
    // el administrador cualquier lista encontrada por nombre.
    private void agregarListaACola(ColaReproduccion cola, UsuarioFinal usuario) {
        if (usuario != null) {
            ListaReproduccion lista = seleccionarLista(usuario.getListasReproduccion(), "Seleccione la lista: ");
            if (lista != null && usuario.agregarListaACola(lista)) {
                System.out.println("Lista agregada a la cola.");
            }
        } else {
            System.out.print("Nombre de la lista a buscar: ");
            ListaReproduccion lista = seleccionarLista(aplicacion.buscarListasPorNombre(scanner.nextLine()), "Seleccione la lista: ");
            if (lista != null) {
                cola.agregarElemento(lista);
                System.out.println("Lista agregada a la cola.");
            }
        }
    }

    private void reproducirSiguiente(ColaReproduccion cola) {
        Reproducible siguiente = cola.siguienteElemento();
        if (siguiente == null) {
            System.out.println("La cola está vacía.");
        } else {
            System.out.println(siguiente.reproducir());
        }
    }

    /* Menú del administrador */

    private void menuAdministrador(Administrador administrador) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("\n--- MENÚ DE ADMINISTRADOR (" + administrador.getNombreUsuario() + ") ---");
            System.out.println("1. Subir una canción al catálogo");
            System.out.println("2. Ver catálogo");
            System.out.println("3. Buscar canciones");
            System.out.println("4. Buscar listas de reproducción por nombre");
            System.out.println("5. Reproducir una canción");
            System.out.println("6. Reproducir una lista de cualquier usuario");
            System.out.println("7. Cola de reproducción");
            System.out.println("8. Ver usuarios registrados");
            System.out.println("9. Cambiar contraseña");
            System.out.println("0. Cerrar sesión");
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    subirCancion();
                    break;
                case 2:
                    mostrarCatalogoDetallado();
                    break;
                case 3:
                    buscarCanciones();
                    break;
                case 4:
                    System.out.print("Nombre a buscar: ");
                    mostrarListas(aplicacion.buscarListasPorNombre(scanner.nextLine()));
                    break;
                case 5:
                    reproducirCancion(administrador);
                    break;
                case 6:
                    reproducirListaComoAdministrador();
                    break;
                case 7:
                    menuCola(colaAdministrador, null);
                    break;
                case 8:
                    aplicacion.mostrarUsuarios();
                    break;
                case 9:
                    cambiarContrasenia(administrador);
                    break;
                case 0:
                    cerrarSesion = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Se solicitan los datos de una canción nueva y se agrega al catálogo.
    private void subirCancion() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Género: ");
            String genero = scanner.nextLine();
            System.out.print("Artista (solista o banda): ");
            String artista = scanner.nextLine();
            System.out.print("Compositor: ");
            String compositor = scanner.nextLine();
            LocalDate fechaLanzamiento = leerFecha("Fecha de lanzamiento (AAAA-MM-DD): ");
            System.out.print("Álbum (opcional, Enter si no pertenece a ninguno): ");
            String album = scanner.nextLine();
            System.out.print("Ruta de la carátula (opcional, Enter para usar la predeterminada): ");
            String caratula = scanner.nextLine();
            double precio = leerDouble("Precio en dólares: ");

            // La calificación inicial es 0.0: el promedio se construye con las calificaciones de los usuarios.
            Cancion cancion = new Cancion(nombre, genero, artista, compositor,
                    fechaLanzamiento, album, caratula, 0.0, precio);
            aplicacion.agregarCancionAlCatalogo(cancion);
            System.out.println("Canción subida al catálogo con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Se reproduce cualquier lista de cualquier usuario final, sin restricciones.
    private void reproducirListaComoAdministrador() {
        System.out.print("Nombre de la lista a buscar: ");
        ListaReproduccion lista = seleccionarLista(aplicacion.buscarListasPorNombre(scanner.nextLine()), "Seleccione la lista: ");
        if (lista != null) {
            System.out.println(lista.reproducir());
        }
    }

    /* Metodos compartidos */

    private void mostrarCatalogoDetallado() {
        System.out.println("--- CATÁLOGO GENERAL DE CANCIONES ---");
        if (aplicacion.getCatalogo().isEmpty()) {
            System.out.println("El catálogo está vacío.");
            return;
        }
        int i= 1;
        for (Cancion cancion : aplicacion.getCatalogo()) {
            System.out.println( i + ". "
                    + " - " + cancion.getArtista()
                    + " | Género: " + cancion.getGenero()
                    + " | Precio: $" + cancion.getPrecio()
                    + " | Calificación: " + cancion.getCalificacion());
            i++;
        }
    }

    private void buscarCanciones() {
        System.out.println("Buscar por: 1. Nombre  2. Género  3. Artista");
        int criterio = leerEntero("Seleccione una opción: ");
        System.out.print("Texto a buscar: ");
        String texto = scanner.nextLine();

        ArrayList<Cancion> resultados;
        switch (criterio) {
            case 2:
                resultados = aplicacion.buscarCancionesPorGenero(texto);
                break;
            case 3:
                resultados = aplicacion.buscarCancionesPorArtista(texto);
                break;
            default:
                resultados = aplicacion.buscarCancionesPorNombre(texto);
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron canciones.");
        } else {
            for (Cancion cancion : resultados) {
                System.out.println(cancion);
            }
        }
    }

    private void mostrarTops() {
        System.out.println("--- TOP 3 MEJOR CALIFICADAS ---");
        mostrarCanciones(aplicacion.obtenerTop3MejorCalificadas());
        System.out.println("--- TOP 3 MÁS COMPRADAS ---");
        mostrarCanciones(aplicacion.obtenerTop3MasCompradas());
        System.out.println("--- TOP 3 MÁS INCLUIDAS EN LISTAS ---");
        mostrarCanciones(aplicacion.obtenerTop3MasIncluidasEnListas());
    }

    private void mostrarCanciones(ArrayList<Cancion> canciones) {
        if (canciones.isEmpty()) {
            System.out.println("No hay canciones en el catálogo.");
            return;
        }
        for (int i = 0; i < canciones.size(); i++) {
            Cancion cancion = canciones.get(i);
            System.out.println((i + 1) + ". " + cancion.getNombre() + " - " + cancion.getArtista()
                    + " | calificación: " + cancion.getCalificacion()
                    + " | compras: " + cancion.getCantidadCompras()
                    + " | en listas: " + cancion.getCantidadInclusionesEnListas());
        }
    }

    private void recargarSaldo(UsuarioFinal usuario) {
        try {
            double monto = leerDouble("Monto a recargar: ");
            usuario.recargarSaldo(monto);
            System.out.println("Recarga exitosa. Saldo actual: $" + usuario.getSaldo());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Se cambia la contraseña de cualquier usuario (administrador o final) aplicando
    // las reglas heredadas de la clase abstracta Usuario.
    private void cambiarContrasenia(Usuario usuario) {
        try {
            System.out.print("Contraseña actual: ");
            String actual = scanner.nextLine();
            System.out.print("Nueva contraseña: ");
            String nueva = scanner.nextLine();
            System.out.print("Repita la nueva contraseña: ");
            String confirmacion = scanner.nextLine();
            usuario.modificarContrasenia(actual, nueva, confirmacion);
            System.out.println("Contraseña modificada con éxito.");
        } catch (CredencialesInvalidasException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Se muestra una colección de canciones numerada y se selecciona una por índice.
    private Cancion seleccionarCancionDe(ArrayList<Cancion> canciones, String mensaje) {
        if (canciones.isEmpty()) {
            System.out.println("No hay canciones disponibles.");
            return null;
        }
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i + 1) + ". " + canciones.get(i).getNombre() + " - " + canciones.get(i).getArtista()
                    + " ($" + canciones.get(i).getPrecio() + ")");
        }
        int opcion = leerEntero(mensaje);
        if (opcion < 1 || opcion > canciones.size()) {
            System.out.println("Selección no válida.");
            return null;
        }
        return canciones.get(opcion - 1);
    }

    // Se muestra una colección de listas numerada y se selecciona una por índice.
    private ListaReproduccion seleccionarLista(ArrayList<ListaReproduccion> listas, String mensaje) {
        if (listas.isEmpty()) {
            System.out.println("No hay listas disponibles.");
            return null;
        }
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNombre()
                    + " (" + listas.get(i).getCanciones().size() + " canciones)");
        }
        int opcion = leerEntero(mensaje);
        if (opcion < 1 || opcion > listas.size()) {
            System.out.println("Selección no válida.");
            return null;
        }
        return listas.get(opcion - 1);
    }

    // Se lee un número entero manejando la excepción de formato.
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    // Se lee un número real manejando la excepción de formato.
    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    // Se lee una fecha en formato ISO manejando la excepción de formato.
    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Debe ingresar una fecha válida en formato AAAA-MM-DD.");
            }
        }
    }
}
