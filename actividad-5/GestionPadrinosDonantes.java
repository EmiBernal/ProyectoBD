import java.sql.*;
import java.util.Scanner;

/**
 * <p>Title: Trabajo práctico integrador - </p>
 * <p>Description: Ejercicio 5</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Universidad Nacional De Rio Cuarto</p>
 * @author Manuel Barbieri, Leonardo Campos, Emiliano Bernal
 * @version 1.0
 */

public class GestionPadrinosDonantes {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/proyectoBD";
    private static final String USUARIO = "postgres";
    private static final String CONTRA = "1234";

    //Ver luego el tema de la contraseña

    public static void main(String[] args){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("No se pudo cargar el driver");
            return;
        }

        //Empieza el programa
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        try(Connection con = DriverManager.getConnection(URL, USUARIO, CONTRA)) {
            do {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Insertar padrino");
                System.out.println("2. Eliminar donante");
                System.out.println("3. Listar padrinos y aportes");
                System.out.println("4. Salir");
                System.out.print("Elija opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch(opcion) {
                    case 1 -> insertarPadrino(con, sc);
                    case 2 -> eliminarDonante(con, sc);
                    case 3 -> listarPadrinos(con);
                    case 4 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opcion invalida");
                }
            } while (opcion != 4);
        }catch (SQLException e) {
            System.out.println("Error de BD: " + e.getMessage());
        }
        sc.close();
    }

    private static void insertarPadrino(Connection con, Scanner sc) {
        //Falta hacer
    }

    private static void eliminarDonante(Connection con, Scanner sc) {
       
        try {
            
            System.out.print("Ingrese el DNI del donante a eliminar: ");
            String dni = sc.nextLine();
            String sql = "DELETE FROM ciudad_de_los_ninos.Donante WHERE dni = ?";
            PreparedStatement statement = con.prepareStatement(sql);
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

            //Envio la consulta a la base de datos y guardo los resultados
            System.out.println();
            System.out.println("Listado de padrinos con sus aportes");
            System.out.println("-----------------------------------");
            while(resultSet.next()) {
                System.out.println("DNI: " + resultSet.getString(1));
                System.out.println("Nombre y Apellido: " + resultSet.getString(2));
                System.out.println("Programa: " + resultSet.getString(3));
                System.out.println("Monto: $" + resultSet.getBigDecimal(4));  // para mostrar bien decimal
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
