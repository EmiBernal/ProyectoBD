import java.sql.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class EjemploMetadataMySQL {

  public EjemploMetadataMySQL() {
  
  }
  public static void main(String[] args) {

    try {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://localhost/procedimientos";
        String username = "root";
        String password = "root";

      // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection =
        DriverManager.getConnection(url, username, password);
      String[] tipo = {"TABLE"};
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet resultSetTables = metaData.getTables(null,"prueba", null, tipo);
      System.out.println(" tablas de la base de datos ");
      
      while(resultSetTables.next()) {
          System.out.println(" procedures cat: " + resultSetTables.getString(1));
          System.out.println(" esquema: " + resultSetTables.getString(2));
          System.out.println(" procedimeinto nombre: " + resultSetTables.getString(3));
          System.out.println(" nombre parametro: " + resultSetTables.getString(4));
          System.out.println(" tipo in/out parametro: " + resultSetTables.getString(5));
          
           }
      

      ResultSet resultSetTables1 = metaData.getProcedureColumns(null,"procedimientos", "cursordemoAvanzado", null);
      System.out.println(" tablas de la base de datos ");
      
      while(resultSetTables1.next()) {
          System.out.println(" catalogo: " + resultSetTables1.getString(1));
          System.out.println(" esquema: " + resultSetTables1.getString(2));
          System.out.println(" nombre: " + resultSetTables1.getString(3));
          System.out.println(" tipo: " + resultSetTables1.getString(4));
          System.out.println(" tipo in/out parametro: " + resultSetTables1.getString(5));
          System.out.println(" tipo parametro: " + resultSetTables1.getString(6));
          
           }
           
      
      
      
      
      
     
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
    	sqle.printStackTrace();
      System.err.println("Error connecting: " + sqle);
    }


  }
 
}