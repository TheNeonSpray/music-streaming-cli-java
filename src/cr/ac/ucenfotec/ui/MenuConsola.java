package cr.ac.ucenfotec.ui;
import cr.ac.ucenfotec.bl.exceptions.OpcionInvalidaException;
import cr.ac.ucenfotec.tl.Controller;

import java.util.Scanner;

public class MenuConsola {
    private static Scanner scanner = new Scanner(System.in);

    // Verificación inicial del administrador
    public static void asegurarAdministrador() throws Exception {
        if (!Controller.existeAdministrador()) {
            Controller.registrarAdministradorObligatorio();
        }
    }

    // Menú Principal
    public static void MostrarMenu() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n========== MUSIC STREAMING APP ==========");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrar Usuario");
            System.out.println("0. Salir ");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 2) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                 Controller.procesarSeleccionPrincipal(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // Menú de opciones para el Administrador
    public static void menuAdministrador() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n----- Menú Administrador -----");
            System.out.println("1. Subir Canción al Catálogo");
            System.out.println("2. Modificar Canción del Catálogo");
            System.out.println("3. Eliminar Canción del Catálogo");
            System.out.println("4. Ver Catálogo");
            System.out.println("5. Buscar Canciones");
            System.out.println("6. Reproducir una Canción");
            System.out.println("7. Buscar Listas de Reproducción por Nombre");
            System.out.println("8. Reproducir Lista de Cualquier Usuario");
            System.out.println("9. Gestionar Cola de Reproducción");
            System.out.println("10. Ver Usuarios Registrados");
            System.out.println("11. Cambiar Contraseña");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 11) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                 Controller.procesarMenuAdministrador(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // Menú de opciones para el Usuario Final
    public static void menuUsuarioFinal() throws Exception {
        byte opcion = -1;
        do {
            // Se obtiene el usuario conectado desde el Controller
            Object usuario = Controller.getUsuarioConectado();
            String infoUsuario = "";

            if (usuario instanceof cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal u) {
                infoUsuario = " (" + u.getNombreUsuario() + " | Saldo: $" + u.getSaldo() + ")";
            }

            System.out.println("\n----- Menú Usuario -----" + infoUsuario + " -----");
            System.out.println("1. Ver Catálogo");
            System.out.println("2. Buscar Canciones");
            System.out.println("3. Reproducir una Canción");
            System.out.println("4. Comprar una Canción");
            System.out.println("5. Ver Mi Colección");
            System.out.println("6. Calificar Canción Comprada");
            System.out.println("7. Mis Listas de Reproducción");
            System.out.println("8. Cola de Reproducción");
            System.out.println("9. Ver Top 3s");
            System.out.println("10. Recargar Saldo");
            System.out.println("11. Cambiar Contraseña");
            System.out.println("12. Ver Mi Perfil");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 12) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                Controller.procesarMenuUsuarioFinal(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // Submenú de búsqueda de canciones
    public static void menuBuscarCanciones() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n----- Búsqueda de Canciones -----");
            System.out.println("1. Buscar por Nombre");
            System.out.println("2. Buscar por Género");
            System.out.println("3. Buscar por Artista");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 3) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                // Controller.procesarBuscarCanciones(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // Submenú para las Listas de Reproducción
    public static void menuListasReproduccion() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n----- Listas de Reproducción -----");
            System.out.println("1. Crear Nueva Lista");
            System.out.println("2. Ver Mis Listas");
            System.out.println("3. Agregar Canción a una Lista");
            System.out.println("4. Eliminar Canción de una Lista");
            System.out.println("5. Reproducir una Lista");
            System.out.println("6. Eliminar una Lista");
            System.out.println("7. Buscar mis listas por nombre");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 7) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                // Controller.procesarMenuListas(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // Submenú para la Cola de Reproducción
    /**public static void menuColaReproduccion() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n----- Cola de Reproducción -----");
            System.out.println("1. Ver la cola");
            System.out.println("2. Agregar una canción");
            System.out.println("3. Agregar una lista");
            System.out.println("4. Reproducir el siguiente elemento");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 4) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                Controller.procesarMenuCola(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }*/
    public static String leerTexto() {
        return scanner.nextLine();
    }
}
