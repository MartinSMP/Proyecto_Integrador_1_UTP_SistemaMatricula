package DAO;

import config.ConexionBD;
import MODELO.MODELO_Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Estudiante
 * @author MartinSoftware
 */
public class DAO_Estudiante {
    
    private Connection conexion;
    
    public DAO_Estudiante() {
        this.conexion = ConexionBD.getConexion();
    }
    
    /**
     * Listar todos los estudiantes
     * @return Lista de estudiantes
     */
    public List<MODELO_Estudiante> listarTodos() {
        List<MODELO_Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes ORDER BY apellido_paterno, apellido_materno, nombres";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Estudiante estudiante = new MODELO_Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCodigoEstudiantil(rs.getString("codigo_estudiantil"));
                estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidoPaterno(rs.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(rs.getString("apellido_materno"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setEdad(rs.getInt("edad"));
                estudiante.setSexo(rs.getString("sexo"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setDireccion(rs.getString("direccion"));
                estudiante.setEstado(rs.getString("estado"));
                estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                lista.add(estudiante);
            }
            
            rs.close();
            st.close();
            
            System.out.println("✓ Se listaron " + lista.size() + " estudiantes");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar estudiantes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Listar solo estudiantes activos
     * @return Lista de estudiantes activos
     */
    public List<MODELO_Estudiante> listarActivos() {
        List<MODELO_Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes WHERE estado = 'ACTIVO' " +
                     "ORDER BY apellido_paterno, apellido_materno, nombres";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Estudiante estudiante = new MODELO_Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCodigoEstudiantil(rs.getString("codigo_estudiantil"));
                estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidoPaterno(rs.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(rs.getString("apellido_materno"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setEdad(rs.getInt("edad"));
                estudiante.setSexo(rs.getString("sexo"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setDireccion(rs.getString("direccion"));
                estudiante.setEstado(rs.getString("estado"));
                estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                lista.add(estudiante);
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar estudiantes activos: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Buscar estudiante por ID
     * @param id ID del estudiante
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarPorId(int id) {
        MODELO_Estudiante estudiante = null;
        String sql = "SELECT * FROM estudiantes WHERE id_estudiante = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                estudiante = new MODELO_Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCodigoEstudiantil(rs.getString("codigo_estudiantil"));
                estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidoPaterno(rs.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(rs.getString("apellido_materno"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setEdad(rs.getInt("edad"));
                estudiante.setSexo(rs.getString("sexo"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setDireccion(rs.getString("direccion"));
                estudiante.setEstado(rs.getString("estado"));
                estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar estudiante por ID: " + e.getMessage());
        }
        
        return estudiante;
    }
    
    /**
     * Buscar estudiante por número de documento
     * @param numeroDocumento número de documento
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarPorDocumento(String numeroDocumento) {
        MODELO_Estudiante estudiante = null;
        String sql = "SELECT * FROM estudiantes WHERE numero_documento = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, numeroDocumento);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                estudiante = new MODELO_Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCodigoEstudiantil(rs.getString("codigo_estudiantil"));
                estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidoPaterno(rs.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(rs.getString("apellido_materno"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setEdad(rs.getInt("edad"));
                estudiante.setSexo(rs.getString("sexo"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setDireccion(rs.getString("direccion"));
                estudiante.setEstado(rs.getString("estado"));
                estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar estudiante por documento: " + e.getMessage());
        }
        
        return estudiante;
    }
    
    /**
     * Buscar estudiante por código estudiantil
     * @param codigoEstudiantil código del estudiante
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarPorCodigo(String codigoEstudiantil) {
        MODELO_Estudiante estudiante = null;
        String sql = "SELECT * FROM estudiantes WHERE codigo_estudiantil = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, codigoEstudiantil);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                estudiante = new MODELO_Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCodigoEstudiantil(rs.getString("codigo_estudiantil"));
                estudiante.setTipoDocumento(rs.getString("tipo_documento"));
                estudiante.setNumeroDocumento(rs.getString("numero_documento"));
                estudiante.setNombres(rs.getString("nombres"));
                estudiante.setApellidoPaterno(rs.getString("apellido_paterno"));
                estudiante.setApellidoMaterno(rs.getString("apellido_materno"));
                estudiante.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                estudiante.setEdad(rs.getInt("edad"));
                estudiante.setSexo(rs.getString("sexo"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setDireccion(rs.getString("direccion"));
                estudiante.setEstado(rs.getString("estado"));
                estudiante.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar estudiante por código: " + e.getMessage());
        }
        
        return estudiante;
    }
    
    /**
     * Insertar nuevo estudiante
     * @param estudiante objeto Estudiante a insertar
     * @return ID del estudiante insertado, 0 si falla
     */
    public int insertar(MODELO_Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (codigo_estudiantil, tipo_documento, numero_documento, " +
                     "nombres, apellido_paterno, apellido_materno, fecha_nacimiento, edad, sexo, " +
                     "telefono, email, direccion, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, estudiante.getCodigoEstudiantil());
            ps.setString(2, estudiante.getTipoDocumento());
            ps.setString(3, estudiante.getNumeroDocumento());
            ps.setString(4, estudiante.getNombres());
            ps.setString(5, estudiante.getApellidoPaterno());
            ps.setString(6, estudiante.getApellidoMaterno());
            ps.setDate(7, estudiante.getFechaNacimiento());
            ps.setInt(8, estudiante.getEdad());
            ps.setString(9, estudiante.getSexo());
            ps.setString(10, estudiante.getTelefono());
            ps.setString(11, estudiante.getEmail());
            ps.setString(12, estudiante.getDireccion());
            ps.setString(13, estudiante.getEstado());
            
            int filasInsertadas = ps.executeUpdate();
            
            if (filasInsertadas > 0) {
                // Obtener el ID generado
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    rs.close();
                    ps.close();
                    
                    System.out.println("✓ Estudiante insertado correctamente con ID: " + idGenerado);
                    return idGenerado;
                }
            }
            
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar estudiante: " + e.getMessage());
            
            if (e.getMessage().contains("numero_documento")) {
                System.err.println("  El número de documento ya está registrado");
            }
            
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Actualizar estudiante existente
     * @param estudiante objeto Estudiante con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(MODELO_Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET tipo_documento = ?, numero_documento = ?, " +
                     "nombres = ?, apellido_paterno = ?, apellido_materno = ?, " +
                     "fecha_nacimiento = ?, edad = ?, sexo = ?, telefono = ?, " +
                     "email = ?, direccion = ?, estado = ? " +
                     "WHERE id_estudiante = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, estudiante.getTipoDocumento());
            ps.setString(2, estudiante.getNumeroDocumento());
            ps.setString(3, estudiante.getNombres());
            ps.setString(4, estudiante.getApellidoPaterno());
            ps.setString(5, estudiante.getApellidoMaterno());
            ps.setDate(6, estudiante.getFechaNacimiento());
            ps.setInt(7, estudiante.getEdad());
            ps.setString(8, estudiante.getSexo());
            ps.setString(9, estudiante.getTelefono());
            ps.setString(10, estudiante.getEmail());
            ps.setString(11, estudiante.getDireccion());
            ps.setString(12, estudiante.getEstado());
            ps.setInt(13, estudiante.getIdEstudiante());
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Estudiante actualizado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar estudiante: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Eliminar estudiante (cambia estado a RETIRADO)
     * @param id ID del estudiante
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        String sql = "UPDATE estudiantes SET estado = 'RETIRADO' WHERE id_estudiante = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Estudiante dado de baja correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al dar de baja estudiante: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Verificar si un número de documento ya existe
     * @param numeroDocumento número de documento a verificar
     * @return true si existe, false si no
     */
    public boolean existeDocumento(String numeroDocumento) {
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE numero_documento = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, numeroDocumento);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                rs.close();
                ps.close();
                return count > 0;
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al verificar documento: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Generar código estudiantil único
     * @return Código generado (ej: EST-2025-001)
     */
    public String generarCodigoEstudiantil() {
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE YEAR(fecha_registro) = YEAR(CURDATE())";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                int cantidad = rs.getInt(1);
                int siguiente = cantidad + 1;
                
                // Formato: EST-2025-001
                int anio = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                String codigo = String.format("EST-%d-%03d", anio, siguiente);
                
                rs.close();
                st.close();
                
                return codigo;
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al generar código estudiantil: " + e.getMessage());
        }
        
        return null;
    }
}