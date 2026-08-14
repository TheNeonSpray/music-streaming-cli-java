package cr.ac.ucenfotec.dl;

import java.sql.*;

public class DBAccess {
    // La clase DBAccess tiene los miembros necesarios para controlar la conexión con la base de datos.

    // Atributos
    private final Connection connection;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    // Métodos
    // Constructor
    public DBAccess(String direccion, String usuario, String contrasenia) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(direccion, usuario, contrasenia);
    }

    // Rutina que recibe un String que contiene una sentencia de MySQL y la ejecuta utilizando
    // una rutina de un objeto de la clase Connection.
    public void ejecutarStatement(String pStatement) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate(pStatement);
    }
    public ResultSet ejecutarQuery(String pQuery) throws SQLException {
        ResultSet resultado;
        statement = connection.createStatement();
        resultado = statement.executeQuery(pQuery);
        return resultado;
    }
    public ResultSet ejecutarQuery(String pQuery, int pValor) throws SQLException {
        ResultSet resultado;
        preparedStatement = connection.prepareStatement(pQuery);
        preparedStatement.setInt(1, pValor);
        resultado = preparedStatement.executeQuery();
        return resultado;
    }
    // Rutina que recibe un String, un double y un int, que contiene una sentencia de MySQL y la ejecuta utilizando
// una rutina de un objeto de la clase Connection.
    public void ejecutarStatement(String pStatement, double pValor1, int pValor2) throws SQLException {
        preparedStatement = connection.prepareStatement(pStatement);
        preparedStatement.setDouble(1, pValor1);
        preparedStatement.setInt(2, pValor2);
        preparedStatement.executeUpdate();
    }
    // Rutina que recibe un String y un int, que contiene una sentencia de MySQL y la ejecuta utilizando
// una rutina de un objeto de la clase Connection.
    public void ejecutarStatement(String pStatement, int pValor1) throws SQLException {
        preparedStatement = connection.prepareStatement(pStatement);
        preparedStatement.setInt(1, pValor1);
        preparedStatement.executeUpdate();
    }

}
