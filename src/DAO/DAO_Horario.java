package DAO;

import config.ConexionBD;
import MODELO.MODELO_Horario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Horario
 * @author MartinSoftware
 */
public class DAO_Horario {
    
    private Connection conexion;
    
    public DAO_Horario() {
        this.conexion = ConexionBD.getConexion();
    }
    
    /**
     * Listar todos los horarios
     * @return Lista de horarios
     */
    public List<MODELO_Horario> listarTodos() {
        List<MODELO_Horario> lista = new ArrayList<>();
        String sql = "SELECT * FROM horarios ORDER BY " +
                     "FIELD(dia_semana, 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO'), " +
                     "hora_inicio";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Horario horario = new MODELO_Horario();
                horario.setIdHorario(rs.getInt("id_horario"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHoraInicio(Time.valueOf(rs.getString("hora_inicio")));
                horario.setHoraFin(Time.valueOf(rs.getString("hora_fin")));
                horario.setTurno(rs.getString("turno"));
                horario.setEstado(rs.getString("estado"));
                
                lista.add(horario);
            }
            
            rs.close();
            st.close();
            
            System.out.println("✓ Se listaron " + lista.size() + " horarios");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar horarios: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Listar solo horarios disponibles
     * @return Lista de horarios disponibles
     */
    public List<MODELO_Horario> listarDisponibles() {
        List<MODELO_Horario> lista = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE estado = 'DISPONIBLE' ORDER BY " +
                     "FIELD(dia_semana, 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO'), " +
                     "hora_inicio";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                MODELO_Horario horario = new MODELO_Horario();
                horario.setIdHorario(rs.getInt("id_horario"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHoraInicio(Time.valueOf(rs.getString("hora_inicio")));
                horario.setHoraFin(Time.valueOf(rs.getString("hora_fin")));
                horario.setTurno(rs.getString("turno"));
                horario.setEstado(rs.getString("estado"));
                
                lista.add(horario);
            }
            
            rs.close();
            st.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar horarios disponibles: " + e.getMessage());
        }
        
        return lista;
    }
    
    /**
     * Buscar horario por ID
     * @param id ID del horario
     * @return Horario encontrado o null
     */
    public MODELO_Horario buscarPorId(int id) {
        MODELO_Horario horario = null;
        String sql = "SELECT * FROM horarios WHERE id_horario = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                horario = new MODELO_Horario();
                horario.setIdHorario(rs.getInt("id_horario"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHoraInicio(rs.getTime("hora_inicio"));
                horario.setHoraFin(rs.getTime("hora_fin"));
                horario.setTurno(rs.getString("turno"));
                horario.setEstado(rs.getString("estado"));
            }
            
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar horario por ID: " + e.getMessage());
        }
        
        return horario;
    }
    
    /**
     * Insertar nuevo horario
     * @param horario objeto Horario a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(MODELO_Horario horario) {
        String sql = "INSERT INTO horarios (dia_semana, hora_inicio, hora_fin, turno, estado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, horario.getDiaSemana());
            ps.setTime(2, horario.getHoraInicio());
            ps.setTime(3, horario.getHoraFin());
            ps.setString(4, horario.getTurno());
            ps.setString(5, horario.getEstado());
            
            int filasInsertadas = ps.executeUpdate();
            ps.close();
            
            if (filasInsertadas > 0) {
                System.out.println("✓ Horario insertado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar horario: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Actualizar horario existente
     * @param horario objeto Horario con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(MODELO_Horario horario) {
        String sql = "UPDATE horarios SET dia_semana = ?, hora_inicio = ?, hora_fin = ?, " +
                     "turno = ?, estado = ? WHERE id_horario = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, horario.getDiaSemana());
            ps.setTime(2, horario.getHoraInicio());
            ps.setTime(3, horario.getHoraFin());
            ps.setString(4, horario.getTurno());
            ps.setString(5, horario.getEstado());
            ps.setInt(6, horario.getIdHorario());
            
            int filasActualizadas = ps.executeUpdate();
            ps.close();
            
            if (filasActualizadas > 0) {
                System.out.println("✓ Horario actualizado correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar horario: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Obtener representación en texto del horario
     * @param horario objeto Horario
     * @return String con formato "DIA HH:MM-HH:MM (TURNO)"
     */
    public String obtenerTextoHorario(MODELO_Horario horario) {
        if (horario == null) return "";
        return horario.getDiaSemana() + " " + 
               horario.getHoraInicio().toString().substring(0, 5) + "-" + 
               horario.getHoraFin().toString().substring(0, 5) + 
               " (" + horario.getTurno() + ")";
    }
}