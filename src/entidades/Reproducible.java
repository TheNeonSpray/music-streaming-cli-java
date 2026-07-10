package entidades;

/* Interfaz que define el comportamiento común de los elementos que pueden
   reproducirse dentro de la aplicación (canciones y listas de reproducción).
   La reproducción se realiza de forma simbólica: el método retorna el mensaje
   correspondiente y la capa de interfaz se encarga de mostrarlo. */
public interface Reproducible {

    // Se retorna el mensaje simbólico de reproducción del elemento.
    String reproducir();
}
