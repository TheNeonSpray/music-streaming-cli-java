package cr.ac.ucenfotec;

import cr.ac.ucenfotec.ui.MenuConsola;

public class Main {
    public static void main(String[] args) {
        try {
            // Verifica si existe un admin si no fuerza el registro inicial
            MenuConsola.asegurarAdministrador();

            MenuConsola.MostrarMenu();
        } catch (Exception e) {
            System.out.println("Error crítico en la aplicación: " + e.getMessage());
        }

    }
}