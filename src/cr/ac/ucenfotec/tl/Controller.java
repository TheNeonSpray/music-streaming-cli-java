package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.entities.Administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal;
import cr.ac.ucenfotec.bl.logic.GestorAdministrador;
import cr.ac.ucenfotec.bl.logic.GestorUsuarioFinal;
import cr.ac.ucenfotec.ui.MenuConsola;

import java.util.ArrayList;

public class Controller {
    // Guarda la sesión actual
    private static Object usuarioConectado = null;

    //verificar registro de admin
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
        //procesar menuinicial
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

    //iniciio sesion
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

            // Si presiona Enter sin escribir nada, el objeto UsuarioFinal le asigna "img/Avatar-icon.png"
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
    //menu admin
    public static void procesarMenuAdministrador(byte opcion) throws Exception {
        switch (opcion) {
            case 1:
                System.out.println("\n[Opción 1: Subir Canción - En desarrollo]");
                break;
            case 2:
                System.out.println("\n[Opción 2: Modificar Canción - En desarrollo]");
                break;
            case 3:
                System.out.println("\n[Opción 3: Eliminar Canción - En desarrollo]");
                break;
            case 4:
                System.out.println("\n[Opción 4: Ver Catálogo Completo - En desarrollo]");
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

    //opcion listar
    private static void listarUsuariosRegistrados() throws Exception {
        System.out.println("\n----- LISTA DE USUARIOS REGISTRADOS -----");
        ArrayList<UsuarioFinal> usuarios = GestorAdministrador.obtenerUsuariosFinales();

        if (usuarioConectado instanceof Administrador admin) {
            admin.mostrarUsuarios(usuarios);
        } else {
            System.out.println("No hay una sesión activa de administrador.");
        }
    }
    //cambiar contra
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
                break; // Sale del bucle cuando la contraseña se cambia con éxito
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
                System.out.println("\n[Opción 1: Ver Catálogo - En desarrollo]");
                break;
            case 2:
                MenuConsola.menuBuscarCanciones();
                break;
            case 3:
                System.out.println("\n[Opción 3: Reproducir Canción - En desarrollo]");
                break;
            case 4:
                System.out.println("\n[Opción 4: Comprar Canción - En desarrollo]");
                break;
            case 5:
                System.out.println("\n[Opción 5: Ver Mi Colección - En desarrollo]");
                break;
            case 6:
                System.out.println("\n[Opción 6: Calificar Canción - En desarrollo]");
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

    // Opción 10: Recargar Saldo
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
                break; // Sale del ciclo si la recarga es exitosa
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
    // Opción 11: Cambiar Contraseña del Usuario Final
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
        if (usuarioConectado instanceof cr.ac.ucenfotec.bl.entities.UsuarioFinal.UsuarioFinal u) {
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
