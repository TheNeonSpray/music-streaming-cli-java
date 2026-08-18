package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.entities.Administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.bl.logic.GestorAdministrador;
import cr.ac.ucenfotec.bl.logic.GestorUsuarioFinal;
import cr.ac.ucenfotec.bl.logic.GestorCancion;
import cr.ac.ucenfotec.bl.logic.GestorCola;
import cr.ac.ucenfotec.ui.MenuConsola;

import java.util.ArrayList;

public class Controller {
    // Guarda la sesión actual
    private static Object usuarioConectado = null;

    // Verificar registro de admin
    public static boolean existeAdministrador() throws Exception {
        return GestorAdministrador.existeAdministrador();
    }

    public static void registrarAdministradorObligatorio() throws Exception {
        while (true) {
            try {
                System.out.println("\n---- Registrar Administrador ----");
                System.out.println("No existe ningún administrador registrado en la base de datos.");

                System.out.print("Correo electrónico: ");
                String correo = MenuConsola.leerTexto();

                System.out.print("Nombre de usuario: ");
                String usuario = MenuConsola.leerTexto();

                System.out.print("Contraseña: ");
                String contrasenia = MenuConsola.leerTexto();

                String respuesta = GestorAdministrador.registrarAdministrador(correo, usuario, contrasenia);
                System.out.println("\n" + respuesta);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\nError de validación: " + e.getMessage() + " Intente de nuevo.");
            } catch (Exception e) {
                System.out.println("\nError al guardar: " + e.getMessage() + " Intente de nuevo.");
            }
        }
    }

    // Procesar menú inicial
    public static void procesarSeleccionPrincipal(byte opcion) throws Exception {
        switch (opcion) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarUsuarioFinal();
                break;
            case 0:
                System.out.println("\n¡Gracias por utilizar Music Streaming App!");
                break;
            default:
                System.out.println("La selección realizada no es válida.");
                break;
        }
    }

    // Inicio sesión
    private static void iniciarSesion() {
        System.out.println("\n----- INICIAR SESIÓN -----");
        System.out.print("Ingrese su nombre de usuario: ");
        String usuario = MenuConsola.leerTexto().trim();

        System.out.print("Ingrese su contraseña: ");
        String contrasenia = MenuConsola.leerTexto().trim();
        Object usuarioAutenticado = null;

        try {
            usuarioAutenticado = GestorAdministrador.iniciarSesion(usuario, contrasenia);
        } catch (Exception e) {
        }

        if (usuarioAutenticado == null) {
            try {
                usuarioAutenticado = GestorUsuarioFinal.iniciarSesion(usuario, contrasenia);
            } catch (Exception e) {
            }
        }
        if (usuarioAutenticado != null) {
            usuarioConectado = usuarioAutenticado;
            System.out.println("\n¡Inicio de sesión exitoso!");

            try {
                if (usuarioConectado instanceof Administrador) {
                    MenuConsola.menuAdministrador();
                } else if (usuarioConectado instanceof UsuarioFinal) {
                    MenuConsola.menuUsuarioFinal();
                }
            } catch (Exception e) {
                System.out.println("Error al desplegar el menú: " + e.getMessage());
            }
        } else {
            System.out.println("\nError al iniciar sesión: Nombre de usuario o contraseña incorrectos.");
        }
    }

    // Registrar Usuario Final desde la consola
    private static void registrarUsuarioFinal() {
        System.out.println("\n----- REGISTRO DE USUARIO  -----");
        try {
            System.out.print("Nombre completo: ");
            String nombre = MenuConsola.leerTexto();

            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            String fechaStr = MenuConsola.leerTexto();
            java.time.LocalDate fecha = java.time.LocalDate.parse(fechaStr);

            System.out.print("Nacionalidad: ");
            String nacionalidad = MenuConsola.leerTexto();

            System.out.print("Cédula / Identificación: ");
            String cedula = MenuConsola.leerTexto();

            System.out.print("Avatar (Presione Enter para usar el predeterminado): ");
            String avatar = MenuConsola.leerTexto().trim();

            System.out.print("Correo electrónico: ");
            String correo = MenuConsola.leerTexto();

            System.out.print("Nombre de usuario: ");
            String usuario = MenuConsola.leerTexto();

            System.out.print("Contraseña: ");
            String contrasenia = MenuConsola.leerTexto();

            String mensaje = GestorUsuarioFinal.registrarUsuarioFinal(
                    nombre, fecha, nacionalidad, cedula, avatar, correo, usuario, contrasenia
            );

            System.out.println("\n" + mensaje);

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("\nError: El formato de fecha no es válido. Debe ser AAAA-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.out.println("\nError de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError al registrar el usuario: " + e.getMessage());
        }
    }

    // Menú admin
    public static void procesarMenuAdministrador(byte opcion) throws Exception {
        switch (opcion) {
            case 1:
                subirCancionAdmin();
                break;
            case 2:
                modificarCancionAdmin();
                break;
            case 3:
                eliminarCancionAdmin();
                break;
            case 4:
                verCatalogoAdmin();
                break;
            case 5:
                MenuConsola.menuBuscarCanciones();
                break;
            case 6:
                System.out.println("\n[Opción 6: Reproducir Canción - En desarrollo]");
                break;
            case 7:
                System.out.println("\n[Opción 7: Buscar Listas de Reproducción - En desarrollo]");
                break;
            case 8:
                System.out.println("\n[Opción 8: Reproducir Lista - En desarrollo]");
                break;
            case 9:
                //MenuConsola.menuColaReproduccion();
                break;
            case 10:
                listarUsuariosRegistrados();
                break;
            case 11:
                cambiarContraseniaAdmin();
                break;
            case 0:
                cerrarSesion();
                break;
            default:
                System.out.println("La selección realizada no es válida.");
                break;
        }
    }

    // Opcion listar
    private static void listarUsuariosRegistrados() throws Exception {
        System.out.println("\n----- LISTA DE USUARIOS REGISTRADOS -----");
        ArrayList<UsuarioFinal> usuarios = GestorAdministrador.obtenerUsuariosFinales();

        if (usuarioConectado instanceof Administrador admin) {
            admin.mostrarUsuarios(usuarios);
        } else {
            System.out.println("No hay una sesión activa de administrador.");
        }
    }

    // Cambiar contra admin
    private static void cambiarContraseniaAdmin() throws Exception {
        System.out.println("\n----- CAMBIAR CONTRASEÑA -----");
        if (!(usuarioConectado instanceof Administrador admin)) {
            System.out.println("No hay una sesión activa de administrador.");
            return;
        }

        while (true) {
            try {
                System.out.print("Ingrese su contraseña actual: ");
                String actual = MenuConsola.leerTexto().trim();

                System.out.print("Ingrese su nueva contraseña: ");
                String nueva = MenuConsola.leerTexto().trim();

                System.out.print("Confirme su nueva contraseña: ");
                String confirmacion = MenuConsola.leerTexto().trim();

                String msj = GestorAdministrador.cambiarContrasenia(admin, actual, nueva, confirmacion);
                System.out.println("\n" + msj);
                break;
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.print("¿Desea intentarlo de nuevo? (S/N): ");
                String res = MenuConsola.leerTexto().trim();
                if (!res.equalsIgnoreCase("S")) {
                    break;
                }
            }
        }
    }

    public static void procesarMenuUsuarioFinal(byte opcion) throws Exception {
        switch (opcion) {
            case 1:
                verCatalogoUsuarioFinal();
                break;
            case 2:
                MenuConsola.menuBuscarCanciones();
                break;
            case 3:
                System.out.println("\n[Opción 3: Reproducir Canción - En desarrollo]");
                break;
            case 4:
                comprarCancionUsuarioFinal();
                break;
            case 5:
                verMiColeccionUsuarioFinal();
                break;
            case 6:
                calificarCancionUsuarioFinal();
                break;
            case 7:
                MenuConsola.menuListasReproduccion();
                break;
            case 8:
                //MenuConsola.menuColaReproduccion();
                break;
            case 9:
                System.out.println("\n[Opción 9: Ver Top 3s - En desarrollo]");
                break;
            case 10:
                recargarSaldoUsuarioFinal();
                break;
            case 11:
                cambiarContraseniaUsuarioFinal();
                break;
            case 12:
                verPerfilUsuarioFinal();
                break;
            case 0:
                cerrarSesion();
                break;
            default:
                System.out.println("La selección realizada no es válida.");
                break;
        }
    }

    // Procesador del menú de Listas de Reproducción
    public static void procesarMenuListas(byte opcion) throws Exception {
        switch (opcion) {
            case 1:
                crearListaReproduccion();
                break;
            case 2:
                verMisListasReproduccion();
                break;
            case 3:
                agregarCancionALista();
                break;
            case 4:
                eliminarCancionDeLista();
                break;
            case 5:
                reproducirListaReproduccion();
                break;
            case 6:
                eliminarListaReproduccion();
                break;
            case 7:
                buscarMisListasPorNombre();
                break;
            case 0:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    // --- MÉTODOS DE GESTIÓN DE LISTAS DE REPRODUCCIÓN ---

    private static void crearListaReproduccion() {
        System.out.println("\n----- CREAR NUEVA LISTA DE REPRODUCCIÓN -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            System.out.print("Nombre de la lista de reproducción: ");
            String nombre = MenuConsola.leerTexto().trim();

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }

            cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion nuevaLista =
                    new cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion(nombre, java.time.LocalDate.now());

            cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.registrarLista(usuario, nuevaLista);
            System.out.println("\n¡Lista '" + nombre + "' creada exitosamente!");

        } catch (Exception e) {
            System.out.println("Error al crear la lista: " + e.getMessage());
        }
    }

    private static void verMisListasReproduccion() {
        System.out.println("\n----- MIS LISTAS DE REPRODUCCIÓN -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion> listas =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerListasPorUsuario(usuario);

            if (listas.isEmpty()) {
                System.out.println("Aún no ha creado ninguna lista de reproducción.");
                return;
            }

            for (int i = 0; i < listas.size(); i++) {
                cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion l = listas.get(i);
                System.out.println((i + 1) + ". " + l.getNombre() + " (Creada: " + l.getFechaCreacion() + ")");
            }

        } catch (Exception e) {
            System.out.println("Error al cargar las listas: " + e.getMessage());
        }
    }

    private static void agregarCancionALista() {
        System.out.println("\n----- AGREGAR CANCIÓN A LISTA -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion> listas =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerListasPorUsuario(usuario);

            if (listas.isEmpty()) {
                System.out.println("No tiene listas de reproducción creadas.");
                return;
            }

            System.out.println("Seleccione la lista:");
            for (int i = 0; i < listas.size(); i++) {
                System.out.println((i + 1) + ". " + listas.get(i).getNombre());
            }
            System.out.print("Opción de lista: ");
            int idxLista = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;

            if (idxLista < 0 || idxLista >= listas.size()) {
                System.out.println("Selección de lista inválida.");
                return;
            }
            cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion listaSeleccionada = listas.get(idxLista);

            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> catalogo = GestorCancion.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("El catálogo está vacío.");
                return;
            }

            System.out.println("Seleccione la canción que desea agregar:");
            for (int i = 0; i < catalogo.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = catalogo.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " - " + c.getArtista());
            }
            System.out.print("Opción de canción: ");
            int idxCancion = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;

            if (idxCancion < 0 || idxCancion >= catalogo.size()) {
                System.out.println("Selección de canción inválida.");
                return;
            }
            cr.ac.ucenfotec.bl.entities.Cancion.Cancion cancionSeleccionada = catalogo.get(idxCancion);

            cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.agregarCancionALista(listaSeleccionada.getId(), cancionSeleccionada.getId());
            System.out.println("\n¡Canción agregada a la lista '" + listaSeleccionada.getNombre() + "' con éxito!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        } catch (Exception e) {
            System.out.println("Error al agregar la canción: " + e.getMessage());
        }
    }

    private static void eliminarCancionDeLista() {
        System.out.println("\n----- ELIMINAR CANCIÓN DE LISTA -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion> listas =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerListasPorUsuario(usuario);

            if (listas.isEmpty()) {
                System.out.println("No tiene listas de reproducción.");
                return;
            }

            System.out.println("Seleccione la lista:");
            for (int i = 0; i < listas.size(); i++) {
                System.out.println((i + 1) + ". " + listas.get(i).getNombre());
            }
            System.out.print("Opción: ");
            int idxLista = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;
            if (idxLista < 0 || idxLista >= listas.size()) {
                System.out.println("Lista inválida.");
                return;
            }
            cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion lista = listas.get(idxLista);

            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> cancionesLista =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerCancionesDeLista(lista.getId());

            if (cancionesLista.isEmpty()) {
                System.out.println("Esta lista no contiene canciones.");
                return;
            }

            System.out.println("Seleccione la canción a eliminar de la lista:");
            for (int i = 0; i < cancionesLista.size(); i++) {
                System.out.println((i + 1) + ". " + cancionesLista.get(i).getNombre() + " - " + cancionesLista.get(i).getArtista());
            }
            System.out.print("Opción: ");
            int idxCancion = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;
            if (idxCancion < 0 || idxCancion >= cancionesLista.size()) {
                System.out.println("Canción inválida.");
                return;
            }
            cr.ac.ucenfotec.bl.entities.Cancion.Cancion cancion = cancionesLista.get(idxCancion);

            cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.eliminarCancionDeLista(lista.getId(), cancion.getId());
            System.out.println("\n¡Canción eliminada de la lista con éxito!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reproducirListaReproduccion() {
        System.out.println("\n----- REPRODUCIR LISTA -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion> listas =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerListasPorUsuario(usuario);

            if (listas.isEmpty()) {
                System.out.println("No tiene listas disponibles.");
                return;
            }

            System.out.println("Seleccione la lista que desea encolar para reproducir:");
            for (int i = 0; i < listas.size(); i++) {
                System.out.println((i + 1) + ". " + listas.get(i).getNombre());
            }
            System.out.print("Opción: ");
            int idx = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;
            if (idx < 0 || idx >= listas.size()) {
                System.out.println("Selección inválida.");
                return;
            }
            cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion lista = listas.get(idx);

            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> canciones =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerCancionesDeLista(lista.getId());

            if (canciones.isEmpty()) {
                System.out.println("La lista está vacía.");
                return;
            }

            GestorCola.encolarLista(canciones);
            System.out.println("\n¡Se han agregado las canciones de la lista '" + lista.getNombre() + "' a su cola de reproducción!");

        } catch (Exception e) {
            System.out.println("Error al reproducir la lista: " + e.getMessage());
        }
    }

    private static void eliminarListaReproduccion() {
        System.out.println("\n----- ELIMINAR LISTA DE REPRODUCCIÓN -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion> listas =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerListasPorUsuario(usuario);

            if (listas.isEmpty()) {
                System.out.println("No tiene listas para eliminar.");
                return;
            }

            System.out.println("Seleccione la lista que desea eliminar:");
            for (int i = 0; i < listas.size(); i++) {
                System.out.println((i + 1) + ". " + listas.get(i).getNombre());
            }
            System.out.print("Opción: ");
            int idx = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;
            if (idx < 0 || idx >= listas.size()) {
                System.out.println("Selección inválida.");
                return;
            }
            cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion lista = listas.get(idx);

            cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.eliminarLista(lista.getId());
            System.out.println("\n¡Lista eliminada correctamente!");

        } catch (Exception e) {
            System.out.println("Error al eliminar la lista: " + e.getMessage());
        }
    }

    private static void buscarMisListasPorNombre() {
        System.out.println("\n----- BUSCAR LISTAS POR NOMBRE -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            System.out.print("Ingrese el nombre (o parte del nombre) a buscar: ");
            String criterio = MenuConsola.leerTexto().trim();

            ArrayList<cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion> listas =
                    cr.ac.ucenfotec.bl.entities.ListaReproduccion.DAOListaReproduccion.obtenerListasPorUsuario(usuario);

            System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
            int encontradas = 0;
            for (cr.ac.ucenfotec.bl.entities.ListaReproduccion.ListaReproduccion l : listas) {
                if (l.getNombre().toLowerCase().contains(criterio.toLowerCase())) {
                    System.out.println("- " + l.getNombre() + " (Fecha: " + l.getFechaCreacion() + ")");
                    encontradas++;
                }
            }

            if (encontradas == 0) {
                System.out.println("No se encontraron listas que coincidan con el criterio.");
            }

        } catch (Exception e) {
            System.out.println("Error en la búsqueda: " + e.getMessage());
        }
    }

    // --- MÉTODOS DE GESTIÓN DE CANCIONES (ADMIN) ---

    private static void subirCancionAdmin() {
        System.out.println("\n----- SUBIR NUEVA CANCIÓN -----");
        try {
            System.out.print("Nombre de la canción: ");
            String nombre = MenuConsola.leerTexto();

            System.out.print("Género musical: ");
            String genero = MenuConsola.leerTexto();

            System.out.print("Artista / Grupo: ");
            String artista = MenuConsola.leerTexto();

            System.out.print("Compositor: ");
            String compositor = MenuConsola.leerTexto();

            System.out.print("Fecha de lanzamiento (AAAA-MM-DD): ");
            java.time.LocalDate fechaLanzamiento = java.time.LocalDate.parse(MenuConsola.leerTexto().trim());

            System.out.print("Álbum (Presione Enter si no aplica): ");
            String album = MenuConsola.leerTexto();

            System.out.print("Ruta de carátula (Presione Enter para la predeterminada): ");
            String rutaCaratula = MenuConsola.leerTexto();

            System.out.print("Precio ($): ");
            double precio = Double.parseDouble(MenuConsola.leerTexto().trim());

            String mensaje = GestorCancion.registrarCancion(
                    nombre, genero, artista, compositor, fechaLanzamiento, album, rutaCaratula, precio
            );

            System.out.println("\n" + mensaje);

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("\nError: El formato de fecha no es válido. Debe ser AAAA-MM-DD.");
        } catch (NumberFormatException e) {
            System.out.println("\nError: El precio ingresado debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("\nError al registrar la canción: " + e.getMessage());
        }
    }

    private static void modificarCancionAdmin() {
        System.out.println("\n----- MODIFICAR CANCIÓN -----");
        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> catalogo = GestorCancion.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("El catálogo está vacío. No hay canciones para modificar.");
                return;
            }

            System.out.println("Seleccione el número de la canción que desea modificar:");
            for (int i = 0; i < catalogo.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = catalogo.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " - " + c.getArtista() + " ($" + c.getPrecio() + ")");
            }

            System.out.print("Opción: ");
            int seleccion = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;

            if (seleccion < 0 || seleccion >= catalogo.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            cr.ac.ucenfotec.bl.entities.Cancion.Cancion cancionSeleccionada = catalogo.get(seleccion);

            System.out.println("\nIngrese los nuevos datos (actuales entre paréntesis):");
            System.out.print("Nuevo nombre [" + cancionSeleccionada.getNombre() + "]: ");
            String nombre = MenuConsola.leerTexto();

            System.out.print("Nuevo género [" + cancionSeleccionada.getGenero() + "]: ");
            String genero = MenuConsola.leerTexto();

            System.out.print("Nuevo artista [" + cancionSeleccionada.getArtista() + "]: ");
            String artista = MenuConsola.leerTexto();

            System.out.print("Nuevo compositor [" + cancionSeleccionada.getCompositor() + "]: ");
            String compositor = MenuConsola.leerTexto();

            System.out.print("Nueva fecha de lanzamiento (AAAA-MM-DD) [" + cancionSeleccionada.getFechaLanzamiento() + "]: ");
            String fechaStr = MenuConsola.leerTexto().trim();
            java.time.LocalDate fechaLanzamiento = fechaStr.isEmpty() ? cancionSeleccionada.getFechaLanzamiento() : java.time.LocalDate.parse(fechaStr);

            System.out.print("Nuevo álbum [" + cancionSeleccionada.getAlbum() + "]: ");
            String album = MenuConsola.leerTexto();

            System.out.print("Nueva ruta carátula [" + cancionSeleccionada.getRutaCaratula() + "]: ");
            String rutaCaratula = MenuConsola.leerTexto();

            System.out.print("Nuevo precio [" + cancionSeleccionada.getPrecio() + "]: ");
            String precioStr = MenuConsola.leerTexto().trim();
            double precio = precioStr.isEmpty() ? cancionSeleccionada.getPrecio() : Double.parseDouble(precioStr);

            String mensaje = GestorCancion.actualizarCancion(
                    cancionSeleccionada, nombre, genero, artista, compositor, fechaLanzamiento, album, rutaCaratula, precio
            );

            System.out.println("\n" + mensaje);

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un valor numérico válido.");
        } catch (Exception e) {
            System.out.println("\nError al modificar la canción: " + e.getMessage());
        }
    }

    private static void eliminarCancionAdmin() {
        System.out.println("\n----- ELIMINAR CANCIÓN -----");
        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> catalogo = GestorCancion.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("El catálogo está vacío. No hay canciones para eliminar.");
                return;
            }

            System.out.println("Seleccione el número de la canción que desea eliminar:");
            for (int i = 0; i < catalogo.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = catalogo.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " - " + c.getArtista());
            }

            System.out.print("Opción: ");
            int seleccion = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;

            if (seleccion < 0 || seleccion >= catalogo.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            cr.ac.ucenfotec.bl.entities.Cancion.Cancion cancionSeleccionada = catalogo.get(seleccion);

            System.out.print("¿Está seguro de eliminar '" + cancionSeleccionada.getNombre() + "'? (S/N): ");
            String confirmar = MenuConsola.leerTexto().trim();

            if (confirmar.equalsIgnoreCase("S")) {
                String mensaje = GestorCancion.eliminarCancion(cancionSeleccionada);
                System.out.println("\n" + mensaje);
            } else {
                System.out.println("\nOperación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
        } catch (Exception e) {
            System.out.println("\nError al eliminar la canción: " + e.getMessage());
        }
    }

    private static void verCatalogoAdmin() {
        System.out.println("\n----- CATÁLOGO GENERAL DE CANCIONES -----");
        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> catalogo = GestorCancion.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("El catálogo está vacío.");
                return;
            }

            for (int i = 0; i < catalogo.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = catalogo.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " | Artista: " + c.getArtista() +
                        " | Género: " + c.getGenero() + " | Precio: $" + c.getPrecio() +
                        " | Calificación: " + c.getCalificacion());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el catálogo: " + e.getMessage());
        }
    }

    // --- MÉTODOS DE OPERACIÓN DEL USUARIO FINAL ---

    private static void verCatalogoUsuarioFinal() {
        System.out.println("\n----- CATÁLOGO GENERAL DE CANCIONES -----");
        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> catalogo = GestorCancion.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("El catálogo está vacío.");
                return;
            }

            for (int i = 0; i < catalogo.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = catalogo.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " | Artista: " + c.getArtista() +
                        " | Género: " + c.getGenero() + " | Precio: $" + c.getPrecio() +
                        " | Calificación: " + c.getCalificacion());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el catálogo: " + e.getMessage());
        }
    }

    private static void comprarCancionUsuarioFinal() {
        System.out.println("\n----- COMPRAR CANCIÓN -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> catalogo = GestorCancion.obtenerCatalogo();
            if (catalogo.isEmpty()) {
                System.out.println("El catálogo está vacío.");
                return;
            }

            System.out.println("Su saldo actual es: $" + usuario.getSaldo());
            System.out.println("Seleccione el número de la canción que desea comprar:");
            for (int i = 0; i < catalogo.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = catalogo.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " - " + c.getArtista() + " ($" + c.getPrecio() + ")");
            }

            System.out.print("Opción: ");
            int seleccion = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;

            if (seleccion < 0 || seleccion >= catalogo.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            cr.ac.ucenfotec.bl.entities.Cancion.Cancion cancionSeleccionada = catalogo.get(seleccion);

            if (usuario.getSaldo() < cancionSeleccionada.getPrecio()) {
                throw new cr.ac.ucenfotec.bl.exceptions.SaldoInsuficienteException(
                        "Saldo insuficiente. Su saldo es $" + usuario.getSaldo() + " y la canción cuesta $" + cancionSeleccionada.getPrecio()
                );
            }

            double nuevoSaldo = usuario.getSaldo() - cancionSeleccionada.getPrecio();

            cr.ac.ucenfotec.bl.entities.UsuarioFinal.DAOUsuarioFinal.actualizarSaldo(usuario.getNombreUsuario(), nuevoSaldo);
            usuario.setSaldo(nuevoSaldo);

            cr.ac.ucenfotec.bl.entities.Cancion.DAOCancion.registrarCompraCancion(usuario, cancionSeleccionada);

            System.out.println("\n¡Compra realizada con éxito! La canción se ha agregado a su colección.");
            System.out.println("Nuevo saldo: $" + usuario.getSaldo());

        } catch (cr.ac.ucenfotec.bl.exceptions.SaldoInsuficienteException e) {
            System.out.println("\nError de compra: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número válido.");
        } catch (Exception e) {
            System.out.println("\nError al procesar la compra: " + e.getMessage());
        }
    }

    private static void verMiColeccionUsuarioFinal() {
        System.out.println("\n----- MI COLECCIÓN DE CANCIONES -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> coleccion = cr.ac.ucenfotec.bl.entities.Cancion.DAOCancion.obtenerColeccionUsuario(usuario);
            if (coleccion.isEmpty()) {
                System.out.println("Aún no ha comprado ninguna canción.");
                return;
            }

            for (int i = 0; i < coleccion.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = coleccion.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " | Artista: " + c.getArtista() + " | Género: " + c.getGenero());
            }
        } catch (Exception e) {
            System.out.println("Error al cargar su colección: " + e.getMessage());
        }
    }

    private static void calificarCancionUsuarioFinal() {
        System.out.println("\n----- CALIFICAR CANCIÓN -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        try {
            ArrayList<cr.ac.ucenfotec.bl.entities.Cancion.Cancion> coleccion = cr.ac.ucenfotec.bl.entities.Cancion.DAOCancion.obtenerColeccionUsuario(usuario);
            if (coleccion.isEmpty()) {
                System.out.println("Debe comprar canciones antes de poder calificarlas.");
                return;
            }

            System.out.println("Seleccione la canción que desea calificar (de su colección):");
            for (int i = 0; i < coleccion.size(); i++) {
                cr.ac.ucenfotec.bl.entities.Cancion.Cancion c = coleccion.get(i);
                System.out.println((i + 1) + ". " + c.getNombre() + " - " + c.getArtista() + " (Calificación actual: " + c.getCalificacion() + ")");
            }

            System.out.print("Opción: ");
            int seleccion = Integer.parseInt(MenuConsola.leerTexto().trim()) - 1;

            if (seleccion < 0 || seleccion >= coleccion.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            cr.ac.ucenfotec.bl.entities.Cancion.Cancion cancionSeleccionada = coleccion.get(seleccion);

            System.out.print("Ingrese su calificación (número del 1.0 al 5.0): ");
            double calificacion = Double.parseDouble(MenuConsola.leerTexto().trim());

            if (calificacion < 1.0 || calificacion > 5.0) {
                System.out.println("La calificación debe estar comprendida entre 1.0 y 5.0.");
                return;
            }

            cr.ac.ucenfotec.bl.entities.Cancion.DAOCancion.calificarCancion(cancionSeleccionada, calificacion);

            System.out.println("\n¡Calificación registrada con éxito! Gracias por su opinión.");

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un valor numérico válido.");
        } catch (Exception e) {
            System.out.println("\nError al calificar la canción: " + e.getMessage());
        }
    }

    private static void recargarSaldoUsuarioFinal() {
        System.out.println("\n----- RECARGAR SALDO -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }
        while (true) {
            try {
                System.out.print("Ingrese el monto a recargar ($): ");
                double monto = Double.parseDouble(MenuConsola.leerTexto().trim());

                String msj = GestorUsuarioFinal.recargarSaldo(usuario, monto);
                System.out.println("\n" + msj);
                break;
            } catch (NumberFormatException e) {
                System.out.println("El monto ingresado debe ser un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("¿Desea intentarlo de nuevo? (S/N): ");
            String res = MenuConsola.leerTexto().trim();
            if (!res.equalsIgnoreCase("S")) {
                break;
            }
        }
    }

    private static void cambiarContraseniaUsuarioFinal() {
        System.out.println("\n----- CAMBIAR CONTRASEÑA -----");
        if (!(usuarioConectado instanceof UsuarioFinal usuario)) {
            System.out.println("No hay una sesión activa de usuario final.");
            return;
        }

        while (true) {
            try {
                System.out.print("Ingrese su contraseña actual: ");
                String actual = MenuConsola.leerTexto().trim();

                System.out.print("Ingrese su nueva contraseña: ");
                String nueva = MenuConsola.leerTexto().trim();

                System.out.print("Confirme su nueva contraseña: ");
                String confirmacion = MenuConsola.leerTexto().trim();

                String msj = GestorUsuarioFinal.cambiarContrasenia(usuario, actual, nueva, confirmacion);
                System.out.println("\n" + msj);
                break;
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.print("¿Desea intentarlo de nuevo? (S/N): ");
                String res = MenuConsola.leerTexto().trim();
                if (!res.equalsIgnoreCase("S")) {
                    break;
                }
            }
        }
    }

    private static void verPerfilUsuarioFinal() {
        if (usuarioConectado instanceof UsuarioFinal u) {
            System.out.println("\n----- MI PERFIL -----");
            System.out.println(u.toString());
        } else {
            System.out.println("No hay una sesión activa de usuario final.");
        }
    }

    public static Object getUsuarioConectado() {
        return usuarioConectado;
    }

    public static void cerrarSesion() {
        usuarioConectado = null;
        System.out.println("\nSesión cerrada con éxito.");
    }
}