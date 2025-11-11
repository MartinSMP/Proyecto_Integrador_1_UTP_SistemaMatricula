package CONTROLADOR;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 * Patrón Singleton para una única instancia de conexi�n
 */
public class ConexionBD {
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/bd_proyectoIntegrador_matricula";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Instancia �nica de conexi�n (Singleton)
    private static Connection conexion = null;
    
    /**
     * Constructor privado para evitar instanciaci�n directa
     */
    private ConexionBD() {
    }
    
    /**
     * M�todo para obtener la conexión a la base de datos
     * @return Connection objeto de conexi�n activa
     */
    public static Connection getConexion() {
        try {
            // Verificar si la conexión ya existe y est� activa
            if (conexion == null || conexion.isClosed()) {
                // Cargar el driver de MySQL
                Class.forName(DRIVER);
                
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                
                System.out.println("? Conexi�n exitosa a la base de datos");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("? Error: Driver MySQL no encontrado");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("? Error al conectar con la base de datos");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return conexion;
    }
    
    /**
     * M�todo para cerrar la conexión a la base de datos
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("? Conexi�n cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("? Error al cerrar la conexi�n");
            System.err.println("Detalles: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * M�todo para verificar si la conexión está activa
     * @return boolean true si la conexión está activa, false en caso contrario
     */
    public static boolean verificarConexion() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            System.err.println("? Error al verificar la conexi�n");
            return false;
        }
    }
    
    /**
     * M�todo de prueba para verificar la conexión
     * @param args argumentos de l�nea de comandos
     */
    /*public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN A LA BASE DE DATOS ===\n");
        
        // Intentar conectar
        Connection conn = ConexionBD.getConexion();
        
        // Verificar conexi�n
        if (conn != null) {
            System.out.println("\n? Estado de conexi�n: ACTIVA");
            System.out.println("? Base de datos: bd_proyectoIntegrador_matricula");
            System.out.println("? Servidor: localhost:3306");
            
            // Cerrar conexi�n
            ConexionBD.cerrarConexion();
        } else {
            System.out.println("\n? No se pudo establecer la conexi�n");
            System.out.println("Verifica que:");
            System.out.println("  1. XAMPP est� corriendo");
            System.out.println("  2. MySQL est� iniciado");
            System.out.println("  3. La base de datos exista");
            System.out.println("  4. Las credenciales sean correctas");
        }
    }*/
}