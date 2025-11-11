package DAO;

import config.ConexionBD;
import MODELO.MODELO_Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Curso
 * @author MartinSoftware
 */
public class DAO_Curso {
    
    private Connection conexion;
    
    public DAO_Curso() {
        this.conexion = ConexionBD.getConexion();
    }
    
    /**
     * Listar todos los cursos con información de docente y horario
     * @return Lista de cursos
     */
    public List<MODELO_Curso> listarTodos() {
        List<MODELO_Curso> lista = new ArrayList<>();
        String sql = "SELECT c.*, " +
                     "CONCAT(d.nombres, ' ', d.apellido_paterno, ' ', d.apellido_materno) AS nombre_docente, " +
                     "CONCAT(h.dia_semana, ' ', h.hora_inicio, '-', h.hora_fin) AS horario_texto " +
                     "FROM cursos c " +
                     "INNER JOIN docentes d ON c.id_docente = d.id_docente " +
                     "INNER JOIN horarios h ON c.id_horario = h.id_horario " +
                     "ORDER BY c.id_curso DESC";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Curso curso = new MODELO_Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setNivel(rs.getString("nivel"));
                curso.setDescripcion(rs.getString("descripcion"));
                curso.setDuracionMeses(rs.getInt("duracion_meses"));
                curso.setCuposDisponibles(rs.getInt("cupos_disponibles"));
                curso.setIdHorario(rs.getInt("id_horario"));
                curso.setIdDocente(rs.getInt("id_docente"));
                curso.setFechaInicio(rs.getDate("fecha_inicio"));
                curso.setFechaFin(rs.getDate("fecha_fin"));
                curso.setEstado(rs.getString("estado"));
                curso.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                
                // Datos adicionales para mostrar
                curso.setNombreDocente(rs.getString("nombre_docente"));
                curso.setHorarioTexto(rs.getString("horario_texto"));
                
                lista.add(curso);
            }
            
            rs.close();
            st.close();
            
            System.out.println("✓ Se listaron " + lista.size() + " cursos");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar cursos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    /**
     * Listar solo cursos activos
     * @return Lista de cursos activos
     */
    public List<MODELO_Curso> listarActivos() {
        List<MODELO_Curso> lista = new ArrayList<>();
        String sql = "SELECT c.*, " +
                     "CONCAT(d.nombres, ' ', d.apellido_paterno, ' ', d.apellido_materno) AS nombre_docente, " +
                     "CONCAT(h.dia_semana, ' ', h.hora_inicio, '-', h.hora_fin) AS horario_texto " +
                     "FROM cursos c " +
                     "INNER JOIN docentes d ON c.id_docente = d.id_docente " +
                     "INNER JOIN horarios h ON c.id_horario = h.id_horario " +
                     "WHERE c.estado = 'ACTIVO' " +
                     "ORDER BY c.fecha_inicio DESC";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Curso curso = new MODELO_Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setNivel(rs.getString("nivel"));
                curso.setDescripcion(rs.getString("descripcion"));
                curso.setDuracionMeses(rs.getInt("duracion_meses"));
                curso.setCuposDisponibles(rs.getInt("cupos_disponibles"));
                curso.setIdHorario(rs.getInt("id_horario"));
                curso.setIdDocente(rs.getInt("id_docente"));
                curso.setFechaInicio(rs.getDate("fecha_inicio"));
                curso.setFechaFin(rs.getDate("fecha_fin"));
                curso.setEstado(rs.getString("estado"));
                curso.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                
                curso.setNombreDocente(rs.getString("nombre_docente"));
                curso.setHorarioTexto(rs.getString("horario_texto"));
                
                lista.add(curso);
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar cursos activos: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Buscar curso por ID
     * @param id ID del curso
     * @return Curso encontrado o null
     */
    public MODELO_Curso buscarPorId(int id) {
        MODELO_Curso curso = null;
        String sql = "SELECT c.*, " +
                     "CONCAT(d.nombres, ' ', d.apellido_paterno, ' ', d.apellido_materno) AS nombre_docente, " +
                     "CONCAT(h.dia_semana, ' ', h.hora_inicio, '-', h.hora_fin) AS horario_texto " +
                     "FROM cursos c " +
                     "INNER JOIN docentes d ON c.id_docente = d.id_docente " +
                     "INNER JOIN horarios h ON c.id_horario = h.id_horario " +
                     "WHERE c.id_curso = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                curso = new MODELO_Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setNivel(rs.getString("nivel"));
                curso.setDescripcion(rs.getString("descripcion"));
                curso.setDuracionMeses(rs.getInt("duracion_meses"));
                curso.setCuposDisponibles(rs.getInt("cupos_disponibles"));
                curso.setIdHorario(rs.getInt("id_horario"));
                curso.setIdDocente(rs.getInt("id_docente"));
                curso.setFechaInicio(rs.getDate("fecha_inicio"));
                curso.setFechaFin(rs.getDate("fecha_fin"));
                curso.setEstado(rs.getString("estado"));
                curso.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                
                curso.setNombreDocente(rs.getString("nombre_docente"));
                curso.setHorarioTexto(rs.getString("horario_texto"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar curso por ID: " + e.getMessage());
        }
        
        return curso;
    }
    
    /**
     * Buscar cursos por nombre (búsqueda parcial)
     * @param texto texto a buscar
     * @return Lista de cursos encontrados
     */
    public List<MODELO_Curso> buscarPorNombre(String texto) {
        List<MODELO_Curso> lista = new ArrayList<>();
        String sql = "SELECT c.*, " +
                     "CONCAT(d.nombres, ' ', d.apellido_paterno, ' ', d.apellido_materno) AS nombre_docente, " +
                     "CONCAT(h.dia_semana, ' ', h.hora_inicio, '-', h.hora_fin) AS horario_texto " +
                     "FROM cursos c " +
                     "INNER JOIN docentes d ON c.id_docente = d.id_docente " +
                     "INNER JOIN horarios h ON c.id_horario = h.id_horario " +
                     "WHERE c.nombre_curso LIKE ? " +
                     "ORDER BY c.id_curso DESC";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                MODELO_Curso curso = new MODELO_Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setNivel(rs.getString("nivel"));
                curso.setDescripcion(rs.getString("descripcion"));
                curso.setDuracionMeses(rs.getInt("duracion_meses"));
                curso.setCuposDisponibles(rs.getInt("cupos_disponibles"));
                curso.setIdHorario(rs.getInt("id_horario"));
                curso.setIdDocente(rs.getInt("id_docente"));
                curso.setFechaInicio(rs.getDate("fecha_inicio"));
                curso.setFechaFin(rs.getDate("fecha_fin"));
                curso.setEstado(rs.getString("estado"));
                curso.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                
                curso.setNombreDocente(rs.getString("nombre_docente"));
                curso.setHorarioTexto(rs.getString("horario_texto"));
                
                lista.add(curso);
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar cursos por nombre: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Insertar nuevo curso
     * @param curso objeto Curso a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(MODELO_Curso curso) {
        String sql = "INSERT INTO cursos (nombre_curso, nivel, descripcion, duracion_meses, " +
                     "cupos_disponibles, id_horario, id_docente, fecha_inicio, fecha_fin, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, curso.getNombreCurso());
            ps.setString(2, curso.getNivel());
            ps.setString(3, curso.getDescripcion());
            ps.setInt(4, curso.getDuracionMeses());
            ps.setInt(5, curso.getCuposDisponibles());
            ps.setInt(6, curso.getIdHorario());
            ps.setInt(7, curso.getIdDocente());
            ps.setDate(8, curso.getFechaInicio());
            ps.setDate(9, curso.getFechaFin());
            ps.setString(10, curso.getEstado());
            
            int filasInsertadas = ps.executeUpdate();
            ps.close();
            
            if (filasInsertadas > 0) {
                System.out.println("✓ Curso insertado correctamente: " + curso.getNombreCurso());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar curso: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Actualizar curso existente
     * @param curso objeto Curso con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(MODELO_Curso curso) {
        String sql = "UPDATE cursos SET nombre_curso = ?, nivel = ?, descripcion = ?, " +
                     "duracion_meses = ?, cupos_disponibles = ?, id_horario = ?, " +
                     "id_docente = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? " +
                     "WHERE id_curso = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, curso.getNombreCurso());
            ps.setString(2, curso.getNivel());
            ps.setString(3, curso.getDescripcion());
            ps.setInt(4, curso.getDuracionMeses());
            ps.setInt(5, curso.getCuposDisponibles());
            ps.setInt(6, curso.getIdHorario());
            ps.setInt(7, curso.getIdDocente());
            ps.setDate(8, curso.getFechaInicio());
            ps.setDate(9, curso.getFechaFin());
            ps.setString(10, curso.getEstado());
            ps.setInt(11, curso.getIdCurso());
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Curso actualizado correctamente: " + curso.getNombreCurso());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar curso: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Eliminar curso (cambia estado a INACTIVO)
     * @param id ID del curso a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        String sql = "UPDATE cursos SET estado = 'INACTIVO' WHERE id_curso = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Curso desactivado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar curso: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Verificar si un docente tiene conflicto de horario
     * @param idDocente ID del docente
     * @param idHorario ID del horario
     * @param idCursoExcluir ID del curso a excluir (para edición, 0 para nuevo)
     * @return true si hay conflicto, false si no
     */
    public boolean verificarConflictoDocente(int idDocente, int idHorario, int idCursoExcluir) {
        String sql = "SELECT COUNT(*) FROM cursos " +
                     "WHERE id_docente = ? AND id_horario = ? AND estado = 'ACTIVO' AND id_curso != ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idDocente);
            ps.setInt(2, idHorario);
            ps.setInt(3, idCursoExcluir);
            
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
            System.err.println("✗ Error al verificar conflicto de docente: " + e.getMessage());
        }
        
        return false;
    }
}