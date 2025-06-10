import java.sql.*;  
import java.util.Scanner;

public class EjemploGetTablesORACLE{

// javac Ejercicio3PSQL.java
// java -cp .:postgresql-42.2.24.jar Ejercicio3PSQL  

  public static void main(String[] args){
//    String driver="oracle.jdbc.OracleDriver"; //oracle.jdbc.driver.OracleDriver
	  String driver="oracle.jdbc.driver.OracleDriver"; //oracle.jdbc.driver.OracleDriver	  
//    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String url = "jdbc:oracle:thin:@//localhost:1521/XE";
    String username = "usuario1";
    String password = "usuario1";
    try{  // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection =
      DriverManager.getConnection(url, username, password);
      
      System.out.print("1) Ver Clientes\n");
      System.out.print("2) Obener Tablas\n");
      System.out.print("3) Salir\n");
      Scanner scanner = new Scanner(System.in);
      int selected = scanner.nextInt();
      while(selected != 3){
        switch (selected){
          case 1: 
            showClients(connection);
            selected=0;
            System.out.print("1) Ver Clientes\n");
            System.out.print("2) Obener Tablas\n");
            System.out.print("3) Salir\n");
            selected = scanner.nextInt();
          break;
          case 2: 
            getTables(connection);
            selected=0;
            System.out.print("1) Ver Clientes\n");
            System.out.print("2) Obener Tablas\n");
            System.out.print("3) Salir\n");
            selected = scanner.nextInt();
          break;
        }
      }
     
    }catch(Exception e){ System.out.println(e);}  
  
 
  }

// le pasaria tambien como parametro el esquema (usuario1 en mi caso)
  public static void getTables(Connection c){
    try{ 
      DatabaseMetaData metaData = c.getMetaData();
      String[] tipo = {"TABLE"};
 //     ResultSet resultSetTables = metaData.getTables(null,"p1ej1c","%", tipo);
      // agregar el catalogo (bases de datos) XE   agregar en todos lados
      ResultSet resultSetTables = metaData.getTables("XE","USUARIO1",null, tipo);
      System.out.println("Tablas de la base de datos ");
      while(resultSetTables.next()) {
        String table= resultSetTables.getString(3);
        System.out.println(" Nombre: " + table+"\n");
        System.out.println("      Primary key: " );
//        ResultSet primaryKeys = metaData.getPrimaryKeys(null,"p1ej1c",table );
        ResultSet primaryKeys = metaData.getPrimaryKeys(null,"usuario1",table );
        while (primaryKeys.next()) {
          System.out.print(primaryKeys.getString("COLUMN_NAME")+", ");
        }
        System.out.println("\n");
        System.out.println("      Foreign keys: " );
//        ResultSet foreignKeys = metaData.getImportedKeys(null, "p1ej1c",table );
        ResultSet foreignKeys = metaData.getImportedKeys(null, "usuario1",table );
        while (foreignKeys.next()) {
          System.out.print(foreignKeys.getString(4)+", ");
        }
        System.out.println("\n\n");
      }
    }
    catch(Exception e){ System.out.println(e);} 
  }

  public static void getColumns(Connection c){
    try{
      Scanner scanner = new Scanner(System.in);
      System.out.print("Inserte el nombre de la tabla\n");
      String name = scanner.nextLine();
      DatabaseMetaData metaData = c.getMetaData();
      String[] tipo = {"TABLE"};
//      ResultSet resultSetTables = metaData.getColumns(null,"p1ej1c", name,"%");
      ResultSet resultSetTables = metaData.getColumns(null,"usuario1", name,"%");
      System.out.println("Columnas: ");
      while(resultSetTables.next()) {
        System.out.println(" Nombre: " + resultSetTables.getString(4)+"\n");
        System.out.println(" Tipo: " + resultSetTables.getString(6)+"\n");
      } 
    }
    catch(Exception e){ System.out.println(e);}
  }

  public static void createClient(Connection c){
    Scanner scanner = new Scanner(System.in);
    System.out.print("Inserte nombre\n");
    String nombre = scanner.nextLine();
    System.out.print("Inserte apellido\n");
    String apellido = scanner.nextLine();
    System.out.print("Inserte direccion\n");
    String direccion = scanner.nextLine();
    System.out.print("Inserte telefono\n");
    String telefono = scanner.nextLine();
    try{ 
    Statement statement = c.createStatement();
    String query = "INSERT INTO Cliente (NroCliente, Nombre, Apellido, Direccion, Telefono) VALUES (18, \""+nombre+"\", \""+apellido+"\", \""+direccion+"\", \""+telefono+"\");";
    int resultInsert = statement.executeUpdate(query);
    if(resultInsert==1){
      System.out.print("Insertado correctamente \n");
    }
    else{
      System.out.print("Error: por favor intente de nuevo \n");
    }
    }
    catch(Exception e){ System.out.println(e);} 
  }

  public static void showClients(Connection c){
    try{
    Statement statement = c.createStatement();
    String query = "SELECT * FROM cliente";
    ResultSet resultSet = statement.executeQuery(query);
    while(resultSet.next())
    {
      System.out.print(" DNI: " + resultSet.getString("DNI"));
      System.out.print(" Nombre: " + resultSet.getString("Nombre"));
      System.out.print(" Apellido: " + resultSet.getString("Apellido"));
      System.out.print(" Direccion: " + resultSet.getString("Direccion"));
      System.out.print("\n ");
      System.out.print("\n ");
    } 
    }
    catch(Exception e){ 
    	e.printStackTrace();
    	System.out.println(e);}
  }


}