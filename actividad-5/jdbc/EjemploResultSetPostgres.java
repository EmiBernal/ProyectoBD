import java.sql.*;

/**
 * <p>Title: Trabajo práctico integrador - </p>
 * <p>Description: Ejercicio 5 c</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Universidad Nacional De Rio Cuarto </p>
 * @author Emiliano Bernal
 * @version 1.0
 */

public class EjemploResultSetPostgres {

  public static void main(String[] args) {

    try {
      String driver = "org.postgresql.Driver";
      String url = "jdbc:postgresql://localhost:5432/proyectoBD";
      String username = "postgres";
      String password = "1234";

      //Cargo el drvie de base de datos si no esta cargado todavia
      Class.forName(driver);
      //Establezco la conexion con la base de datos
      Connection connection =
      DriverManager.getConnection(url, username, password);

      String query = "SELECT * FROM ciudad_de_los_ninos.padrino";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet resultSet = statement.executeQuery();

      // Send query to database and store results.

      // Print results.
      while(resultSet.next()) {
        // Quarter

        System.out.print(" DNI: " + resultSet.getString(1));
        System.out.print("; Nombre: " + resultSet.getString(2));
        System.out.print("; Email: " + resultSet.getString(3)) ;
	      System.out.print("\n   ");
	      System.out.print("\n   ");
      }
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
    	sqle.printStackTrace();
      System.err.println("Error connecting: " + sqle);
    }
  }
}