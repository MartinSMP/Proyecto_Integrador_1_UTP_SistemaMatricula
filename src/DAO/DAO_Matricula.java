package DAO;

import config.ConexionBD;
import MODELO.MODELO_Matricula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Matrícula
 *
 * @author MartinSoftware
 */
public class DAO_Matricula {

    private Connection conexion;

    public DAO_Matricula() {
        this.conexion = ConexionBD.getConexion();
    }

    /**
     * Listar todas las matrículas con información completa
     *
     * @return Lista de matrículas
     */
    public List<MODELO_Matricula> listarTodas() {
        List<MODELO_Matricula> lista = new ArrayList<>();
        String sql = "SELECT m.*, "
                + "CONCAT(e.nombres, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
                + "c.nombre_curso, "
                + "c.nivel AS nivel_curso, "
                + "u.nombre_completo AS registrado_por "
                + "FROM matriculas m "
                + "INNER JOIN estudiantes e ON m.id_estudiante = e.id_estudiante "
                + "INNER JOIN cursos c ON m.id_curso = c.id_curso "
                + "INNER JOIN usuarios u ON m.id_usuario_registro = u.id_usuario "
                + "ORDER BY m.id_matricula DESC";

        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                MODELO_Matricula matricula = new MODELO_Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setCodigoMatricula(rs.getString("codigo_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setObservaciones(rs.getString("observaciones"));
                matricula.setEstado(rs.getString("estado"));
                matricula.setIdUsuarioRegistro(rs.getInt("id_usuario_registro"));
                matricula.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                // Datos adicionales para mostrar
                matricula.setNombreEstudiante(rs.getString("nombre_estudiante"));
                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matricula.setNivelCurso(rs.getString("nivel_curso"));

                lista.add(matricula);
            }

            rs.close();
            st.close();

            System.out.println("✓ Se listaron " + lista.size() + " matrículas");

        } catch (SQLException e) {
            System.err.println("✗ Error al listar matrículas: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Listar solo matrículas activas
     *
     * @return Lista de matrículas activas
     */
    public List<MODELO_Matricula> listarActivas() {
        List<MODELO_Matricula> lista = new ArrayList<>();
        String sql = "SELECT m.*, "
                + "CONCAT(e.nombres, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
                + "c.nombre_curso, "
                + "c.nivel AS nivel_curso, "
                + "u.nombre_completo AS registrado_por "
                + "FROM matriculas m "
                + "INNER JOIN estudiantes e ON m.id_estudiante = e.id_estudiante "
                + "INNER JOIN cursos c ON m.id_curso = c.id_curso "
                + "INNER JOIN usuarios u ON m.id_usuario_registro = u.id_usuario "
                + "WHERE m.estado = 'ACTIVO' "
                + "ORDER BY m.fecha_matricula DESC";

        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                MODELO_Matricula matricula = new MODELO_Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setCodigoMatricula(rs.getString("codigo_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setObservaciones(rs.getString("observaciones"));
                matricula.setEstado(rs.getString("estado"));
                matricula.setIdUsuarioRegistro(rs.getInt("id_usuario_registro"));
                matricula.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                matricula.setNombreEstudiante(rs.getString("nombre_estudiante"));
                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matricula.setNivelCurso(rs.getString("nivel_curso"));

                lista.add(matricula);
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println("✗ Error al listar matrículas activas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Buscar matrícula por ID
     *
     * @param id ID de la matrícula
     * @return Matrícula encontrada o null
     */
    public MODELO_Matricula buscarPorId(int id) {
        MODELO_Matricula matricula = null;
        String sql = "SELECT m.*, "
                + "CONCAT(e.nombres, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
                + "c.nombre_curso, "
                + "c.nivel AS nivel_curso "
                + "FROM matriculas m "
                + "INNER JOIN estudiantes e ON m.id_estudiante = e.id_estudiante "
                + "INNER JOIN cursos c ON m.id_curso = c.id_curso "
                + "WHERE m.id_matricula = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                matricula = new MODELO_Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setCodigoMatricula(rs.getString("codigo_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setObservaciones(rs.getString("observaciones"));
                matricula.setEstado(rs.getString("estado"));
                matricula.setIdUsuarioRegistro(rs.getInt("id_usuario_registro"));
                matricula.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                matricula.setNombreEstudiante(rs.getString("nombre_estudiante"));
                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matricula.setNivelCurso(rs.getString("nivel_curso"));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("✗ Error al buscar matrícula por ID: " + e.getMessage());
        }

        return matricula;
    }

    /**
     * Listar matrículas de un curso
     *
     * @param idCurso ID del curso
     * @return Lista de matrículas del curso
     */
    public List<MODELO_Matricula> listarPorCurso(int idCurso) {
        List<MODELO_Matricula> lista = new ArrayList<>();
        String sql = "SELECT m.*, "
                + "CONCAT(e.nombres, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
                + "c.nombre_curso, "
                + "c.nivel AS nivel_curso "
                + "FROM matriculas m "
                + "INNER JOIN estudiantes e ON m.id_estudiante = e.id_estudiante "
                + "INNER JOIN cursos c ON m.id_curso = c.id_curso "
                + "WHERE m.id_curso = ? AND m.estado = 'ACTIVO' "
                + "ORDER BY e.apellido_paterno, e.apellido_materno, e.nombres";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idCurso);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MODELO_Matricula matricula = new MODELO_Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setCodigoMatricula(rs.getString("codigo_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setObservaciones(rs.getString("observaciones"));
                matricula.setEstado(rs.getString("estado"));
                matricula.setIdUsuarioRegistro(rs.getInt("id_usuario_registro"));
                matricula.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                matricula.setNombreEstudiante(rs.getString("nombre_estudiante"));
                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matricula.setNivelCurso(rs.getString("nivel_curso"));

                lista.add(matricula);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("✗ Error al listar matrículas por curso: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Buscar matrícula por código
     *
     * @param codigoMatricula código de matrícula
     * @return Matrícula encontrada o null
     */
    public MODELO_Matricula buscarPorCodigo(String codigoMatricula) {
        MODELO_Matricula matricula = null;
        String sql = "SELECT m.*, "
                + "CONCAT(e.nombres, ' ', e.apellido_paterno, ' ', e.apellido_materno) AS nombre_estudiante, "
                + "c.nombre_curso, "
                + "c.nivel AS nivel_curso "
                + "FROM matriculas m "
                + "INNER JOIN estudiantes e ON m.id_estudiante = e.id_estudiante "
                + "INNER JOIN cursos c ON m.id_curso = c.id_curso "
                + "WHERE m.codigo_matricula = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, codigoMatricula);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                matricula = new MODELO_Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setCodigoMatricula(rs.getString("codigo_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setObservaciones(rs.getString("observaciones"));
                matricula.setEstado(rs.getString("estado"));
                matricula.setIdUsuarioRegistro(rs.getInt("id_usuario_registro"));
                matricula.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                matricula.setNombreEstudiante(rs.getString("nombre_estudiante"));
                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matricula.setNivelCurso(rs.getString("nivel_curso"));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("✗ Error al buscar matrícula por código: " + e.getMessage());
        }

        return matricula;
    }

    /**
     * Listar matrículas de un estudiante
     *
     * @param idEstudiante ID del estudiante
     * @return Lista de matrículas del estudiante
     */
    public List<MODELO_Matricula> listarPorEstudiante(int idEstudiante) {
        List<MODELO_Matricula> lista = new ArrayList<>();
        String sql = "SELECT m.*, "
                + "c.nombre_curso, "
                + "c.nivel AS nivel_curso "
                + "FROM matriculas m "
                + "INNER JOIN cursos c ON m.id_curso = c.id_curso "
                + "WHERE m.id_estudiante = ? "
                + "ORDER BY m.fecha_matricula DESC";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEstudiante);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MODELO_Matricula matricula = new MODELO_Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setCodigoMatricula(rs.getString("codigo_matricula"));
                matricula.setIdEstudiante(rs.getInt("id_estudiante"));
                matricula.setIdCurso(rs.getInt("id_curso"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setObservaciones(rs.getString("observaciones"));
                matricula.setEstado(rs.getString("estado"));
                matricula.setIdUsuarioRegistro(rs.getInt("id_usuario_registro"));
                matricula.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                matricula.setNombreCurso(rs.getString("nombre_curso"));
                matricula.setNivelCurso(rs.getString("nivel_curso"));

                lista.add(matricula);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("✗ Error al listar matrículas por estudiante: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Insertar nueva matrícula
     *
     * @param matricula objeto Matrícula a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(MODELO_Matricula matricula) {
        String sql = "INSERT INTO matriculas (codigo_matricula, id_estudiante, id_curso, "
                + "fecha_matricula, observaciones, estado, id_usuario_registro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, matricula.getCodigoMatricula());
            ps.setInt(2, matricula.getIdEstudiante());
            ps.setInt(3, matricula.getIdCurso());
            ps.setDate(4, matricula.getFechaMatricula());
            ps.setString(5, matricula.getObservaciones());
            ps.setString(6, matricula.getEstado());
            ps.setInt(7, matricula.getIdUsuarioRegistro());

            int filasInsertadas = ps.executeUpdate();
            ps.close();

            if (filasInsertadas > 0) {
                System.out.println("✓ Matrícula insertada correctamente: " + matricula.getCodigoMatricula());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("✗ Error al insertar matrícula: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Actualizar matrícula existente
     *
     * @param matricula objeto Matrícula con datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(MODELO_Matricula matricula) {
        String sql = "UPDATE matriculas SET id_curso = ?, fecha_matricula = ?, "
                + "observaciones = ?, estado = ? WHERE id_matricula = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, matricula.getIdCurso());
            ps.setDate(2, matricula.getFechaMatricula());
            ps.setString(3, matricula.getObservaciones());
            ps.setString(4, matricula.getEstado());
            ps.setInt(5, matricula.getIdMatricula());

            int filasActualizadas = ps.executeUpdate();
            ps.close();

            if (filasActualizadas > 0) {
                System.out.println("✓ Matrícula actualizada correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar matrícula: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Anular matrícula
     *
     * @param id ID de la matrícula
     * @return true si se anuló correctamente
     */
    public boolean anular(int id) {
        String sql = "UPDATE matriculas SET estado = 'ANULADO' WHERE id_matricula = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);

            int filasActualizadas = ps.executeUpdate();
            ps.close();

            if (filasActualizadas > 0) {
                System.out.println("✓ Matrícula anulada correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("✗ Error al anular matrícula: " + e.getMessage());
        }

        return false;
    }

    /**
     * Generar código de matrícula único
     *
     * @return Código generado (ej: MAT-2025-001)
     */
    public String generarCodigoMatricula() {
        String sql = "SELECT COUNT(*) FROM matriculas WHERE YEAR(fecha_registro) = YEAR(CURDATE())";

        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                int cantidad = rs.getInt(1);
                int siguiente = cantidad + 1;

                // Formato: MAT-2025-001
                int anio = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                String codigo = String.format("MAT-%d-%03d", anio, siguiente);

                rs.close();
                st.close();

                return codigo;
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.err.println("✗ Error al generar código de matrícula: " + e.getMessage());
        }

        return null;
    }

    /**
     * Verificar si un estudiante ya está matriculado en un curso
     *
     * @param idEstudiante ID del estudiante
     * @param idCurso ID del curso
     * @return true si ya está matriculado, false si no
     */
    public boolean verificarMatriculaExistente(int idEstudiante, int idCurso) {
        String sql = "SELECT COUNT(*) FROM matriculas "
                + "WHERE id_estudiante = ? AND id_curso = ? AND estado = 'ACTIVO'";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEstudiante);
            ps.setInt(2, idCurso);

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
            System.err.println("✗ Error al verificar matrícula existente: " + e.getMessage());
        }

        return false;
    }
}
