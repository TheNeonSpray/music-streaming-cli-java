package cr.ac.ucenfotec.ui;
import cr.ac.ucenfotec.bl.excepciones.OpcionInvalidaException;
import java.util.Scanner;

public class MenuConsola {
    private static Scanner scanner = new Scanner(System.in);
    // Menú Principal de Entrada (Inicio de Sesión / Registro)
    public static void MostrarMenu() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n========== MUSIC STREAMING APP ==========");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrar Usuario Final");
            System.out.println("0. Salir de la Aplicación");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 2) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                // Controller.procesarSeleccionPrincipal(opcion);
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
            System.out.println("2. Buscar y Reproducir Canciones");
            System.out.println("3. Gestionar Cola de Reproducción");
            System.out.println("4. Ver Top 3s");
            System.out.println("5. Cambiar Contraseña");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 5) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                // Controller.procesarMenuAdministrador(opcion);
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
            System.out.println("\n----- Menú Usuario Final -----");
            System.out.println("1. Ver Catálogo y Comprar / Probar Canción");
            System.out.println("2. Buscar Canciones");
            System.out.println("3. Mi Colección de Canciones Compradas");
            System.out.println("4. Calificar Canción Comprada");
            System.out.println("5. Gestionar Listas de Reproducción");
            System.out.println("6. Gestionar Cola de Reproducción");
            System.out.println("7. Ver Top 3s Automatizados");
            System.out.println("8. Recargar Saldo");
            System.out.println("9. Cambiar Contraseña");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 9) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                //Controller.procesarMenuUsuarioFinal(opcion);
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
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 6) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                //Controller.procesarMenuListas(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // Submenú para la Cola de Reproducción
    public static void menuColaReproduccion() throws Exception {
        byte opcion = -1;
        do {
            System.out.println("\n----- Cola de Reproducción -----");
            System.out.println("1. Ver Elementos en Cola");
            System.out.println("2. Encolar Canción");
            System.out.println("3. Encolar Lista de Reproducción");
            System.out.println("4. Reproducir Siguiente Elemento");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Byte.parseByte(scanner.nextLine());
                if (opcion < 0 || opcion > 4) {
                    throw new OpcionInvalidaException(
                            "La opción indicada no se encuentra entre las ofrecidas.\n");
                }
                //Controller.procesarMenuCola(opcion);
            } catch (NumberFormatException e) {
                System.out.println("El formato del dato ingresado no es válido.\n");
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }
    public static String leerTexto() {
        return scanner.nextLine();
    }
}
