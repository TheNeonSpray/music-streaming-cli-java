package ui;

import logica.Aplicacion;

public class Main {
    public static void main(String[] args) {

        // Se crea la aplicación y se delega toda la interacción a la capa de interfaz.
        Aplicacion aplicacion = new Aplicacion();
        MenuConsola menu = new MenuConsola(aplicacion);
        menu.iniciar();

    }
}