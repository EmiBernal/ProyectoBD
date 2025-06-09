import java.sql.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class EjemploMetadataOracle {

  public EjemploMetadataOracle() {
  
  }
  public static void main(String[] args) {

    try {
      String driver = "oracle.jdbc.driver.OracleDriver";
      String url = "jdbc:oracle:thin:@//localhost:1521/XE";
//      String username = "system";
//      String password = "ciclo10";
      String username = "USUARIO";
      String password = "USUARIO";

      // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection =
        DriverManager.getConnection(url, username, password);

      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet resultSetSchemas = metaData.getSchemas();
      System.out.println(" Esquemas de la Base de datos ");
      while(resultSetSchemas.next()) {
             System.out.println(" esquema: " + resultSetSchemas.getString(1));
           }
     
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
    	sqle.printStackTrace();
      System.err.println("Error connecting: " + sqle);
    }


  }
 
}