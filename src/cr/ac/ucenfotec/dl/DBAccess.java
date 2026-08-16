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

    // Ejecuta INSERT, UPDATE o DELETE utilizando parámetros. De esta forma los DAO
    // no tienen que concatenar los valores recibidos directamente en el SQL.
    public int ejecutarActualizacion(String sql, Object... parametros) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        asignarParametros(preparedStatement, parametros);
        return preparedStatement.executeUpdate();
    }

    // Ejecuta un INSERT y devuelve la llave primaria generada por MySQL.
    public int ejecutarInsercion(String sql, Object... parametros) throws SQLException {
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        asignarParametros(preparedStatement, parametros);
        preparedStatement.executeUpdate();

        try (ResultSet llaves = preparedStatement.getGeneratedKeys()) {
            if (llaves.next()) {
                return llaves.getInt(1);
            }
        }
        throw new SQLException("MySQL no devolvió el identificador del registro insertado.");
    }

    // Ejecuta un SELECT utilizando parámetros.
    public ResultSet ejecutarConsulta(String sql, Object... parametros) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        asignarParametros(preparedStatement, parametros);
        return preparedStatement.executeQuery();
    }

    private void asignarParametros(PreparedStatement sentencia, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            Object valor = parametros[i];
            if (valor instanceof java.time.LocalDate) {
                sentencia.setDate(i + 1, Date.valueOf((java.time.LocalDate) valor));
            } else {
                sentencia.setObject(i + 1, valor);
            }
        }
    }

}
