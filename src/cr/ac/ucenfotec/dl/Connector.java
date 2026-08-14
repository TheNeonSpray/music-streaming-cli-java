package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.utils.Utils;

public class Connector {
    // La clase Connector establece el enlace entre las credenciales de acceso a la base de datos,
    // almacenadas en un archivo leído por la clase Utils, y la clase BDAccess que utiliza dichas
    // credenciales para iniciar la conexión con la base de datos.

    // Atributos
    private static DBAccess dbConnection = null;

    // Métodos
    // Rutina para tomar la información de acceso a la base de datos mediante la clase Utils y
    // pasársela al constructor de la clase DBAccess, el cual genera una instancia de dicha clase
    // que no es más que la conexión misma a la base de datos.
    public static DBAccess getConnection() throws Exception {
        String[] infoAccesoBD = Utils.getProperties();
        String direccion = infoAccesoBD[0] + "//" + infoAccesoBD[1] + "/" + infoAccesoBD[2];
        String usuario = infoAccesoBD[3];
        String contrasenia = infoAccesoBD[4];
        if(dbConnection == null) {
            dbConnection = new DBAccess(direccion, usuario, contrasenia);
        }
        return dbConnection;
    }
}
