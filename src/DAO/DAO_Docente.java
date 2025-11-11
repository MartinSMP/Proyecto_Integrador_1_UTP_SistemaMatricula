package DAO;

import config.ConexionBD;
import MODELO.MODELO_Docente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Docente
 * @author MartinSoftware
 */
public class DAO_Docente {
    
    private Connection conexion;
    
    /**
     * Constructor que obtiene la conexión a la BD
     */
    public DAO_Docente() {
        this.conexion = ConexionBD.getConexion();
    }
    
    /**
     * Listar todos los docentes
     * @return Lista de docentes
     */
    public List<MODELO_Docente> listarTodos() {
        List<MODELO_Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes ORDER BY apellido_paterno, apellido_materno, nombres";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Docente docente = new MODELO_Docente();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setTipoDocumento(rs.getString("tipo_documento"));
                docente.setNumeroDocumento(rs.getString("numero_documento"));
                docente.setNombres(rs.getString("nombres"));
                docente.setApellidoPaterno(rs.getString("apellido_paterno"));
                docente.setApellidoMaterno(rs.getString("apellido_materno"));
                docente.setTelefono(rs.getString("telefono"));
                docente.setEmail(rs.getString("email"));
                docente.setEspecialidad(rs.getString("especialidad"));
                docente.setEstado(rs.getString("estado"));
                docente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                lista.add(docente);
            }
            
            rs.close();
            st.close();
            
            System.out.println("✓ Se listaron " + lista.size() + " docentes");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar docentes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Listar solo docentes activos
     * @return Lista de docentes activos
     */
    public List<MODELO_Docente> listarActivos() {
        List<MODELO_Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes WHERE estado = 'ACTIVO' ORDER BY apellido_paterno, apellido_materno, nombres";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Docente docente = new MODELO_Docente();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setTipoDocumento(rs.getString("tipo_documento"));
                docente.setNumeroDocumento(rs.getString("numero_documento"));
                docente.setNombres(rs.getString("nombres"));
                docente.setApellidoPaterno(rs.getString("apellido_paterno"));
                docente.setApellidoMaterno(rs.getString("apellido_materno"));
                docente.setTelefono(rs.getString("telefono"));
                docente.setEmail(rs.getString("email"));
                docente.setEspecialidad(rs.getString("especialidad"));
                docente.setEstado(rs.getString("estado"));
                docente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                lista.add(docente);
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar docentes activos: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Buscar docente por ID
     * @param id ID del docente
     * @return Docente encontrado o null
     */
    public MODELO_Docente buscarPorId(int id) {
        MODELO_Docente docente = null;
        String sql = "SELECT * FROM docentes WHERE id_docente = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                docente = new MODELO_Docente();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setTipoDocumento(rs.getString("tipo_documento"));
                docente.setNumeroDocumento(rs.getString("numero_documento"));
                docente.setNombres(rs.getString("nombres"));
                docente.setApellidoPaterno(rs.getString("apellido_paterno"));
                docente.setApellidoMaterno(rs.getString("apellido_materno"));
                docente.setTelefono(rs.getString("telefono"));
                docente.setEmail(rs.getString("email"));
                docente.setEspecialidad(rs.getString("especialidad"));
                docente.setEstado(rs.getString("estado"));
                docente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar docente por ID: " + e.getMessage());
        }
        
        return docente;
    }
    
    /**
     * Buscar docente por número de documento
     * @param numeroDocumento número de documento
     * @return Docente encontrado o null
     */
    public MODELO_Docente buscarPorDocumento(String numeroDocumento) {
        MODELO_Docente docente = null;
        String sql = "SELECT * FROM docentes WHERE numero_documento = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, numeroDocumento);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                docente = new MODELO_Docente();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setTipoDocumento(rs.getString("tipo_documento"));
                docente.setNumeroDocumento(rs.getString("numero_documento"));
                docente.setNombres(rs.getString("nombres"));
                docente.setApellidoPaterno(rs.getString("apellido_paterno"));
                docente.setApellidoMaterno(rs.getString("apellido_materno"));
                docente.setTelefono(rs.getString("telefono"));
                docente.setEmail(rs.getString("email"));
                docente.setEspecialidad(rs.getString("especialidad"));
                docente.setEstado(rs.getString("estado"));
                docente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar docente por documento: " + e.getMessage());
        }
        
        return docente;
    }
    
    /**
     * Buscar docentes por nombre (búsqueda parcial)
     * @param texto texto a buscar en nombres o apellidos
     * @return Lista de docentes encontrados
     */
    public List<MODELO_Docente> buscarPorNombre(String texto) {
        List<MODELO_Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes WHERE " +
                     "CONCAT(nombres, ' ', apellido_paterno, ' ', apellido_materno) LIKE ? " +
                     "ORDER BY apellido_paterno, apellido_materno, nombres";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                MODELO_Docente docente = new MODELO_Docente();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setTipoDocumento(rs.getString("tipo_documento"));
                docente.setNumeroDocumento(rs.getString("numero_documento"));
                docente.setNombres(rs.getString("nombres"));
                docente.setApellidoPaterno(rs.getString("apellido_paterno"));
                docente.setApellidoMaterno(rs.getString("apellido_materno"));
                docente.setTelefono(rs.getString("telefono"));
                docente.setEmail(rs.getString("email"));
                docente.setEspecialidad(rs.getString("especialidad"));
                docente.setEstado(rs.getString("estado"));
                docente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                
                lista.add(docente);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar docentes por nombre: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Insertar nuevo docente
     * @param docente objeto Docente a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(MODELO_Docente docente) {
        String sql = "INSERT INTO docentes (tipo_documento, numero_documento, nombres, " +
                     "apellido_paterno, apellido_materno, telefono, email, especialidad, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, docente.getTipoDocumento());
            ps.setString(2, docente.getNumeroDocumento());
            ps.setString(3, docente.getNombres());
            ps.setString(4, docente.getApellidoPaterno());
            ps.setString(5, docente.getApellidoMaterno());
            ps.setString(6, docente.getTelefono());
            ps.setString(7, docente.getEmail());
            ps.setString(8, docente.getEspecialidad());
            ps.setString(9, docente.getEstado());
            
            int filasInsertadas = ps.executeUpdate();
            ps.close();
            
            if (filasInsertadas > 0) {
                System.out.println("✓ Docente insertado correctamente: " + 
                                   docente.getNombres() + " " + docente.getApellidoPaterno());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar docente: " + e.getMessage());
            
            // Verificar si es error de documento duplicado
            if (e.getMessage().contains("numero_documento")) {
                System.err.println("  El número de documento ya está registrado");
            }
            
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Actualizar docente existente
     * @param docente objeto Docente con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(MODELO_Docente docente) {
        String sql = "UPDATE docentes SET tipo_documento = ?, numero_documento = ?, " +
                     "nombres = ?, apellido_paterno = ?, apellido_materno = ?, " +
                     "telefono = ?, email = ?, especialidad = ?, estado = ? " +
                     "WHERE id_docente = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, docente.getTipoDocumento());
            ps.setString(2, docente.getNumeroDocumento());
            ps.setString(3, docente.getNombres());
            ps.setString(4, docente.getApellidoPaterno());
            ps.setString(5, docente.getApellidoMaterno());
            ps.setString(6, docente.getTelefono());
            ps.setString(7, docente.getEmail());
            ps.setString(8, docente.getEspecialidad());
            ps.setString(9, docente.getEstado());
            ps.setInt(10, docente.getIdDocente());
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Docente actualizado correctamente: " + 
                                   docente.getNombres() + " " + docente.getApellidoPaterno());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar docente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Eliminar docente (cambia estado a INACTIVO)
     * @param id ID del docente a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        // En lugar de eliminar físicamente, cambiamos el estado a INACTIVO
        String sql = "UPDATE docentes SET estado = 'INACTIVO' WHERE id_docente = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Docente desactivado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar docente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean activar(int id) {
        // En lugar de eliminar físicamente, cambiamos el estado a INACTIVO
        String sql = "UPDATE docentes SET estado = 'ACTIVO' WHERE id_docente = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Docente activado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar docente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Verificar si un número de documento ya existe
     * @param numeroDocumento número de documento a verificar
     * @return true si existe, false si no
     */
    public boolean existeDocumento(String numeroDocumento) {
        String sql = "SELECT COUNT(*) FROM docentes WHERE numero_documento = ?";
        
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
     * Verificar si un número de documento ya existe (excluyendo un ID)
     * Para usar al editar
     * @param numeroDocumento número de documento a verificar
     * @param idDocente ID del docente a excluir de la búsqueda
     * @return true si existe, false si no
     */
    public boolean existeDocumentoExcepto(String numeroDocumento, int idDocente) {
        String sql = "SELECT COUNT(*) FROM docentes WHERE numero_documento = ? AND id_docente != ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, numeroDocumento);
            ps.setInt(2, idDocente);
            
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
     * Método de prueba
     */
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE DAO_Docente ===\n");
        
        DAO_Docente dao = new DAO_Docente();
        
        System.out.println("1. Listando todos los docentes...");
        List<MODELO_Docente> docentes = dao.listarTodos();
        for (MODELO_Docente d : docentes) {
            System.out.println("   - " + d.getNombres() + " " + 
                             d.getApellidoPaterno() + " | " + 
                             d.getEspecialidad() + " | " + 
                             d.getEstado());
        }
        
        System.out.println("\n✓ Prueba completada");
    }
}