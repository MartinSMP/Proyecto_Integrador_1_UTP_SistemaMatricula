package DAO;

import MODELO.MODELO_Usuario;
import config.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Usuario
 * @author MartinSoftware
 */
public class DAO_Usuario {
    
    private Connection conexion;
    
    /**
     * Constructor que obtiene la conexión a la BD
     */
    public DAO_Usuario() {
        this.conexion = ConexionBD.getConexion();
    }
    
    /**
     * Método para validar login de usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario si las credenciales son correctas, null si no
     */
    public MODELO_Usuario validarLogin(String username, String password) {
        MODELO_Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ? AND estado = 'ACTIVO'";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new MODELO_Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
                
                // Actualizar último acceso
                actualizarUltimoAcceso(usuario.getIdUsuario());
                
                System.out.println("✓ Login exitoso: " + username);
            } else {
                System.out.println("✗ Credenciales incorrectas");
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error en validarLogin: " + e.getMessage());
            e.printStackTrace();
        }
        
        return usuario;
    }
    
    /**
     * Actualizar la fecha de último acceso del usuario
     * @param idUsuario ID del usuario
     * @return true si se actualizó correctamente
     */
    public boolean actualizarUltimoAcceso(int idUsuario) {
        String sql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id_usuario = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            return filasActualizadas > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar último acceso: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Listar todos los usuarios
     * @return Lista de usuarios
     */
    public List<MODELO_Usuario> listarTodos() {
        List<MODELO_Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id_usuario DESC";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Usuario usuario = new MODELO_Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
                
                lista.add(usuario);
            }
            
            rs.close();
            st.close();
            
            System.out.println("✓ Se listaron " + lista.size() + " usuarios");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Buscar usuario por ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    public MODELO_Usuario buscarPorId(int id) {
        MODELO_Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new MODELO_Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar usuario por ID: " + e.getMessage());
        }
        
        return usuario;
    }
    
    /**
     * Buscar usuario por username
     * @param username nombre de usuario
     * @return Usuario encontrado o null
     */
    public MODELO_Usuario buscarPorUsername(String username) {
        MODELO_Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new MODELO_Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar usuario por username: " + e.getMessage());
        }
        
        return usuario;
    }
    
    /**
     * Insertar nuevo usuario
     * @param usuario objeto Usuario a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(MODELO_Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, nombre_completo, rol, estado) VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getEstado());
            
            int filasInsertadas = ps.executeUpdate();
            ps.close();
            
            if (filasInsertadas > 0) {
                System.out.println("✓ Usuario insertado correctamente: " + usuario.getUsername());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Actualizar usuario existente
     * @param usuario objeto Usuario con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(MODELO_Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, nombre_completo = ?, rol = ?, estado = ? WHERE id_usuario = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getEstado());
            ps.setInt(6, usuario.getIdUsuario());
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Usuario actualizado correctamente: " + usuario.getUsername());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Eliminar usuario (cambia estado a INACTIVO)
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        // En lugar de eliminar físicamente, cambiamos el estado a INACTIVO
        String sql = "UPDATE usuarios SET estado = 'INACTIVO' WHERE id_usuario = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Usuario desactivado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Verificar si un username ya existe
     * @param username nombre de usuario a verificar
     * @return true si existe, false si no
     */
    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, username);
            
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
            System.err.println("✗ Error al verificar username: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Método de prueba
     */
    /*public static void main(String[] args) {
        System.out.println("=== PRUEBA DE DAO_Usuario ===\n");
        
        DAO_Usuario dao = new DAO_Usuario();
        
        // Probar login con usuario de prueba
        System.out.println("1. Probando login...");
        MODELO_Usuario usuario = dao.validarLogin("admin", "admin123");
        
        if (usuario != null) {
            System.out.println("   Usuario logueado: " + usuario.getNombreCompleto());
            System.out.println("   Rol: " + usuario.getRol());
        }
        
        System.out.println("\n2. Listando todos los usuarios...");
        List<MODELO_Usuario> usuarios = dao.listarTodos();
        for (MODELO_Usuario u : usuarios) {
            System.out.println("   - " + u.getUsername() + " | " + u.getNombreCompleto());
        }
        
        System.out.println("\n✓ Prueba completada");
    } */
}