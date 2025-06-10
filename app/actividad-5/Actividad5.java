import java.sql.*;
import java.util.Scanner;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>Title: Trabajo práctico integrador - </p>
 * <p>Description: Ejercicio 5</p>
 * <p>Company: Universidad Nacional De Rio Cuarto</p>
 * @author 
 * @version 1.0
 */
public class Actividad5 {

    public static void main(String[] args){

        Properties props = new Properties();
        String url = "";
        String user = "";
        String password = "";

        try {
            // Carga el archivo de propiedades
            props.load(new FileInputStream("config.properties"));
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            // Carga el driver
            Class.forName("org.postgresql.Driver");

        } catch (IOException e) {
            System.out.println("No se pudo leer config.properties: " + e.getMessage());
            return;
        } catch (ClassNotFoundException e){
            System.out.println("No se pudo cargar el driver PostgreSQL");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            do {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Insertar padrino");
                System.out.println("2. Eliminar donante");
                System.out.println("3. Listar padrinos y aportes");
                System.out.println("4. Salir");
                System.out.print("Elija opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> insertarPadrino(con, sc);
                    case 2 -> eliminarDonante(con, sc);
                    case 3 -> listarPadrinos(con);
                    case 4 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opcion invalida");
                }

            } while (opcion != 4);

        } catch (SQLException e) {
            System.out.println("Error de BD: " + e.getMessage());
        }

        sc.close();
    }

    private static void insertarPadrino(Connection con, Scanner sc) {
    try {
        System.out.println("Ingrese el dni del padrino: ");
        String dni = sc.nextLine();

        // Verificar si el DNI ya existe
        String checkQuery = "SELECT 1 FROM ciudad_de_los_ninos.Padrino WHERE dni = ?";
        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
        checkStmt.setString(1, dni);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            System.out.println("Error: El padrino con DNI " + dni + " ya está registrado.");
            rs.close();
            checkStmt.close();
            return; 
        }
        rs.close();
        checkStmt.close();

        // Si no existe, continuar con la inserción
        String insertQuery = "INSERT INTO ciudad_de_los_ninos.Padrino(dni, nombre_apellido, direccion, email, facebook_user, cod_postal, fecha_nac)" +
                             "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement insertStmt = con.prepareStatement(insertQuery);
        insertStmt.setString(1, dni);

        System.out.println("Ingrese el nombre y apellido del padrino: ");
        String nombreApellido = sc.nextLine();
        insertStmt.setString(2, nombreApellido);

        System.out.println("Ingrese la direccion del padrino: ");
        String direccion = sc.nextLine();
        insertStmt.setString(3, direccion);

        System.out.println("Ingrese el email del padrino: ");
        String email = sc.nextLine();
        insertStmt.setString(4, email);

        System.out.println("Ingrese el usuario de facebook del padrino: ");
        String userFacebook = sc.nextLine();
        insertStmt.setString(5, userFacebook);

        System.out.println("Ingrese el codigo postal del padrino: ");
        int postal = Integer.parseInt(sc.nextLine());
        insertStmt.setInt(6, postal);

        System.out.print("Ingrese la fecha de nacimiento del padrino (yyyy-mm-dd): ");
        String fechaNac = sc.nextLine();
        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaNac);
        insertStmt.setDate(7, fechaSQL);

        int filasInsertadas = insertStmt.executeUpdate();
        if(filasInsertadas > 0){
            System.out.println("Padrino insertado correctamente.");
        }

        insertStmt.close();

    } catch (SQLException e) {
        System.out.println("Error al insertar el padrino: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.out.println("Fecha invalida. Debe tener el formato yyyy-mm-dd");
    }
}


    private static void eliminarDonante(Connection con, Scanner sc) {
       
        try {
            
            System.out.print("Ingrese el DNI del donante a eliminar: ");
            String dni = sc.nextLine();
            String query = "DELETE FROM ciudad_de_los_ninos.Donante WHERE dni = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, dni);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Donante eliminado correctamente.");
            } else {
                System.out.println("No se encontró un donante con ese DNI.");
            }
        
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarPadrinos(Connection con) {
        try {

            String query = "select p.dni, p.nombre_apellido, a.nomProg, a.monto, a.frecuencia " +
                      "from ciudad_de_los_ninos.Padrino p " +
                      "join ciudad_de_los_ninos.Donante d on p.dni = d.dni " +
                      "join ciudad_de_los_ninos.Aporte a on d.dni = a.dni " +
                      "join ciudad_de_los_ninos.Programa pr on a.nomProg = pr.nomProg ";

            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println();
            System.out.println("Listado de padrinos con sus aportes");
            System.out.println("-----------------------------------");
            while(resultSet.next()) {
                System.out.println("DNI: " + resultSet.getString(1));
                System.out.println("Nombre y Apellido: " + resultSet.getString(2));
                System.out.println("Programa: " + resultSet.getString(3));
                System.out.println("Monto: $" + resultSet.getBigDecimal(4)); 
                System.out.println("Frecuencia: " + resultSet.getString(5));
                System.out.println("----------------------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e){
            System.out.println("Error al lista padrinos: " + e.getMessage());
        }
    }


}
