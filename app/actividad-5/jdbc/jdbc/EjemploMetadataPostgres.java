import java.sql.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class EjemploMetadataPostgres {

  public EjemploMetadataPostgres() {

  }
  public static void main(String[] args) {

    try {
      String driver = "org.postgresql.Driver";
      String url = "jdbc:postgresql://localhost:5433/implementacion";
      String username = "postgres";
      String password = "root";

      // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection =
        DriverManager.getConnection(url, username, password);
      String[] tipo = {"TABLE"};
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet resultSetTables = metaData.getTables("procedimientos","public", null, tipo);
      System.out.println(" tablas de la base de datos ");

      while(resultSetTables.next()) {
          System.out.println(" catalogo: " + resultSetTables.getString(1));
          System.out.println(" esquema: " + resultSetTables.getString(2));
          System.out.println(" nombre: " + resultSetTables.getString(3));
          System.out.println(" tipo: " + resultSetTables.getString(4));
          System.out.println(" comentarios: " + resultSetTables.getString(5));
          
           }
      
      
      ResultSet resultSetTables1 = metaData.getProcedures("procedimientos","public",null);
      System.out.println(" tablas de la base de datos ");
      
      while(resultSetTables1.next()) {
          System.out.println(" catalogo: " + resultSetTables1.getString(1));
          System.out.println(" esquema: " + resultSetTables1.getString(2));
          System.out.println(" nombre: " + resultSetTables1.getString(3));
          System.out.println(" tipo: " + resultSetTables1.getString(4));
          System.out.println(" tipo in/out parametro: " + resultSetTables1.getString(5));
          System.out.println(" tipo parametro: " + resultSetTables1.getString(6));
          
           }
           
      ResultSet resultSetTables2 = metaData.getProcedures("procedimientos","public",null);
      System.out.println(" tablas de la base de datos ");
      
      while(resultSetTables2.next()) {
          System.out.println(" catalogo: " + resultSetTables2.getString(1));
          System.out.println(" esquema: " + resultSetTables2.getString(2));
          System.out.println(" nombre: " + resultSetTables2.getString(3));
          System.out.println(" tipo: " + resultSetTables2.getString(4));
          System.out.println(" tipo in/out parametro: " + resultSetTables2.getString(5));
          System.out.println(" tipo parametro: " + resultSetTables2.getString(6));
          
           }
           
      
      ResultSet resultSetTables3 = metaData.getIndexInfo(null,"public", "ninio", true, false);
      System.out.println(" ------------------------------");
      System.out.println(" claveunica de la base de datos ");
      
      while(resultSetTables3.next()) {
          System.out.println(" catalogo: " + resultSetTables3.getString(1));
          System.out.println(" esquema: " + resultSetTables3.getString(2));
          System.out.println(" nombre: " + resultSetTables3.getString(3));
          System.out.println(" tipo: " + resultSetTables3.getString(4));
          System.out.println(" tipo in/out parametro: " + resultSetTables3.getString(5));
          System.out.println(" tipo parametro: " + resultSetTables3.getString(6));
          
           }
           
      
      
     
    } catch(ClassNotFoundException cnfe) {
      System.err.println("Error loading driver: " + cnfe);
    } catch(SQLException sqle) {
    	sqle.printStackTrace();
      System.err.println("Error connecting: " + sqle);
    }


  }
 
}