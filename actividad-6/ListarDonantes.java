import java.sql.*;

/**
 * <p>Title: Trabajo práctico integrador - </p>
 * <p>Description: Ejercicio 6 c</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Universidad Nacional De Rio Cuarto </p>
 * @author Emiliano Bernal
 * @version 1.0
 */

public class ListarDonantes {

    public static void main(String[] args) {

        try {
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://localhost:5432/proyectoBD";
            String username = "postgres";
            String password = "1234";

            // Cargo el driver si no está cargado
            Class.forName(driver);

            // Establezco la conexión
            Connection connection = DriverManager.getConnection(url, username, password);

            // Consulta para listar donantes con aportes mensuales y tipo de medio de pago
            String query = "SELECT d.dni, p.nombre_apellido, d.cuit_cuil, d.ocupacion, " +
                    "a.nomProg AS programa_aporte, a.frecuencia, m.nombre_titular, " +
                    "tc.id_medioPago AS tarjeta, dt.id_medioPago AS debito " +
                    "FROM ciudad_de_los_ninos.Donante d " +
                    "JOIN ciudad_de_los_ninos.Padrino p ON d.dni = p.dni " +
                    "JOIN ciudad_de_los_ninos.Aporte a ON d.dni = a.dni " +
                    "JOIN ciudad_de_los_ninos.MedioPago m ON a.id_medioPago = m.id_medioPago " +
                    "LEFT JOIN ciudad_de_los_ninos.TarjetaCredito tc ON m.id_medioPago = tc.id_medioPago " +
                    "LEFT JOIN ciudad_de_los_ninos.DebitoTransferencia dt ON m.id_medioPago = dt.id_medioPago " +
                    "WHERE a.frecuencia = 'MENSUAL'";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println();
            System.out.println("Listado de donantes con aportes mensuales y tipo de medio de pago");
            System.out.println("------------------------------------------------------------------");

            while (resultSet.next()) {
                String dni = resultSet.getString("dni");
                String nombre = resultSet.getString("nombre_apellido");
                String cuit = resultSet.getString("cuit_cuil");
                String ocupacion = resultSet.getString("ocupacion");
                String programa = resultSet.getString("programa_aporte");
                String frecuencia = resultSet.getString("frecuencia");
                String titular = resultSet.getString("nombre_titular");
                Object tarjeta = resultSet.getObject("tarjeta");
                Object debito = resultSet.getObject("debito");
                String tipoPago;
                if (tarjeta != null) {
                    tipoPago = "Tarjeta de Crédito";
                } else if (debito != null) {
                    tipoPago = "Transferencia/Débito";
                } else {
                    tipoPago = "Otro";
                }
                System.out.println("DNI: " + dni);
                System.out.println("Nombre y Apellido: " + nombre);
                System.out.println("CUIT/CUIL: " + cuit);
                System.out.println("Ocupación: " + ocupacion);
                System.out.println("Programa de Aporte: " + programa);
                System.out.println("Frecuencia: " + frecuencia);
                System.out.println("Nombre Titular Medio de Pago: " + titular);
                System.out.println("Tipo de Medio de Pago: " + tipoPago);
                System.out.println("-----------------------------------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error al cargar el driver: " + cnfe.getMessage());
        } catch (SQLException sqle) {
            System.err.println("Error de conexión o consulta: " + sqle.getMessage());
            sqle.printStackTrace();
        }
    }
}
