package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_proyectoIntegrador_matricula?serverTimezone=America/Lima";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // Por defecto XAMPP no tiene password
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection conexion = null;
    
    /**
     * Constructor privado para evitar instanciación directa
     */
    private ConexionBD() {
    }
    
    /**
     * Método para obtener la conexión a la base de datos
     * @return Connection objeto de conexión activa
     */
    public static Connection getConexion() {
        try {
            // Verificar si la conexión ya existe y está activa
            if (conexion == null || conexion.isClosed()) {
                // Cargar el driver de MySQL
                Class.forName(DRIVER);
                
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                
                System.out.println("✓ Conexión exitosa a la base de datos");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Driver MySQL no encontrado");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar con la base de datos");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
    
    /**
     * Método para cerrar la conexión a la base de datos
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar la conexión");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Método para verificar si la conexión está activa
     * @return boolean true si la conexión está activa, false en caso contrario
     */
    public static boolean verificarConexion() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            System.err.println("✗ Error al verificar la conexión");
            return false;
        }
    }
    
    /**
     * Método de prueba para verificar la conexión
     * @param args argumentos de línea de comandos
     */
    /*public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN A LA BASE DE DATOS ===\n");
        
        // Intentar conectar
        Connection conn = ConexionBD.getConexion();
        
        // Verificar conexión
        if (conn != null) {
            System.out.println("\n✓ Estado de conexión: ACTIVA");
            System.out.println("✓ Base de datos: bd_proyectoIntegrador_matricula");
            System.out.println("✓ Servidor: localhost:3306");
            
            // Cerrar conexión
            ConexionBD.cerrarConexion();
        } else {
            System.out.println("\n✗ No se pudo establecer la conexión");
            System.out.println("Verifica que:");
            System.out.println("  1. XAMPP esté corriendo");
            System.out.println("  2. MySQL esté iniciado");
            System.out.println("  3. La base de datos exista");
            System.out.println("  4. Las credenciales sean correctas");
        }
    }*/
}