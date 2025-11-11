package CONTROLADOR;

import DAO.DAO_Curso;
import DAO.DAO_Docente;
import DAO.DAO_Horario;
import MODELO.MODELO_Curso;
import MODELO.MODELO_Docente;
import MODELO.MODELO_Horario;
import java.sql.Date;
import java.util.List;
import java.util.Calendar;

/**
 * Controlador para gestionar la lógica de negocio de Cursos
 * @author MartinSoftware
 */
public class CONTROLADOR_Curso {
    
    private DAO_Curso cursoDAO;
    private DAO_Docente docenteDAO;
    private DAO_Horario horarioDAO;
    
    public CONTROLADOR_Curso() {
        this.cursoDAO = new DAO_Curso();
        this.docenteDAO = new DAO_Docente();
        this.horarioDAO = new DAO_Horario();
    }
    
    /**
     * Listar todos los cursos
     * @return Lista de cursos
     */
    public List<MODELO_Curso> listarTodos() {
        return cursoDAO.listarTodos();
    }
    
    /**
     * Listar solo cursos activos
     * @return Lista de cursos activos
     */
    public List<MODELO_Curso> listarActivos() {
        return cursoDAO.listarActivos();
    }
    
    /**
     * Buscar curso por ID
     * @param id ID del curso
     * @return Curso encontrado o null
     */
    public MODELO_Curso buscarPorId(int id) {
        return cursoDAO.buscarPorId(id);
    }
    
    /**
     * Buscar cursos por nombre
     * @param texto texto a buscar
     * @return Lista de cursos encontrados
     */
    public List<MODELO_Curso> buscarPorNombre(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTodos();
        }
        return cursoDAO.buscarPorNombre(texto);
    }
    
    /**
     * Listar todos los docentes activos
     * @return Lista de docentes activos
     */
    public List<MODELO_Docente> listarDocentesActivos() {
        return docenteDAO.listarActivos();
    }
    
    /**
     * Listar todos los horarios disponibles
     * @return Lista de horarios disponibles
     */
    public List<MODELO_Horario> listarHorariosDisponibles() {
        return horarioDAO.listarDisponibles();
    }
    
    /**
     * Listar todos los horarios
     * @return Lista de todos los horarios
     */
    public List<MODELO_Horario> listarTodosHorarios() {
        return horarioDAO.listarTodos();
    }
    
    /**
     * Buscar docente por ID
     * @param id ID del docente
     * @return Docente encontrado o null
     */
    public MODELO_Docente buscarDocentePorId(int id) {
        return docenteDAO.buscarPorId(id);
    }
    
    /**
     * Buscar horario por ID
     * @param id ID del horario
     * @return Horario encontrado o null
     */
    public MODELO_Horario buscarHorarioPorId(int id) {
        return horarioDAO.buscarPorId(id);
    }
    
    /**
     * Registrar nuevo curso con validaciones
     * @param curso objeto Curso a insertar
     * @return Mensaje de resultado
     */
    public String registrarCurso(MODELO_Curso curso) {
        // Validaciones
        String error = validarCurso(curso);
        if (error != null) {
            return error;
        }
        
        // Verificar conflicto de horario del docente
        if (cursoDAO.verificarConflictoDocente(curso.getIdDocente(), curso.getIdHorario(), 0)) {
            MODELO_Docente docente = docenteDAO.buscarPorId(curso.getIdDocente());
            return "El docente " + docente.getNombres() + " " + docente.getApellidoPaterno() + 
                   " ya tiene asignado otro curso en este horario";
        }
        
        // Insertar
        boolean resultado = cursoDAO.insertar(curso);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al registrar el curso";
        }
    }
    
    /**
     * Actualizar curso con validaciones
     * @param curso objeto Curso con datos actualizados
     * @return Mensaje de resultado
     */
    public String actualizarCurso(MODELO_Curso curso) {
        // Validaciones
        String error = validarCurso(curso);
        if (error != null) {
            return error;
        }
        
        // Verificar conflicto de horario del docente (excluyendo el curso actual)
        if (cursoDAO.verificarConflictoDocente(curso.getIdDocente(), curso.getIdHorario(), curso.getIdCurso())) {
            MODELO_Docente docente = docenteDAO.buscarPorId(curso.getIdDocente());
            return "El docente " + docente.getNombres() + " " + docente.getApellidoPaterno() + 
                   " ya tiene asignado otro curso en este horario";
        }
        
        // Actualizar
        boolean resultado = cursoDAO.actualizar(curso);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al actualizar el curso";
        }
    }
    
    /**
     * Eliminar curso (cambiar estado a INACTIVO)
     * @param id ID del curso
     * @return Mensaje de resultado
     */
    public String eliminarCurso(int id) {
        // Verificar que existe
        MODELO_Curso curso = cursoDAO.buscarPorId(id);
        if (curso == null) {
            return "El curso no existe";
        }
        
        // TODO: Aquí podrías verificar si el curso tiene matrículas activas
        // y evitar eliminarlo o hacer una eliminación lógica más compleja
        
        boolean resultado = cursoDAO.eliminar(id);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al eliminar el curso";
        }
    }
    
    /**
     * Validar datos del curso
     * @param curso objeto a validar
     * @return Mensaje de error o null si es válido
     */
    private String validarCurso(MODELO_Curso curso) {
        // Validar nombre del curso
        if (curso.getNombreCurso() == null || curso.getNombreCurso().trim().isEmpty()) {
            return "El nombre del curso es obligatorio";
        }
        if (curso.getNombreCurso().trim().length() < 3) {
            return "El nombre del curso debe tener al menos 3 caracteres";
        }
        
        // Validar nivel
        if (curso.getNivel() == null || curso.getNivel().trim().isEmpty()) {
            return "Debe seleccionar el nivel del curso";
        }
        if (!curso.getNivel().equals("BASICO") && 
            !curso.getNivel().equals("INTERMEDIO") && 
            !curso.getNivel().equals("AVANZADO")) {
            return "El nivel debe ser BASICO, INTERMEDIO o AVANZADO";
        }
        
        // Validar duración
        if (curso.getDuracionMeses() <= 0) {
            return "La duración debe ser mayor a 0 meses";
        }
        if (curso.getDuracionMeses() > 24) {
            return "La duración no puede ser mayor a 24 meses";
        }
        
        // Validar cupos
        if (curso.getCuposDisponibles() <= 0) {
            return "Los cupos disponibles deben ser mayor a 0";
        }
        if (curso.getCuposDisponibles() > 50) {
            return "Los cupos disponibles no pueden ser mayor a 50";
        }
        
        // Validar horario
        if (curso.getIdHorario() <= 0) {
            return "Debe seleccionar un horario";
        }
        
        // Validar docente
        if (curso.getIdDocente() <= 0) {
            return "Debe seleccionar un docente";
        }
        
        // Validar que el docente existe y está activo
        MODELO_Docente docente = docenteDAO.buscarPorId(curso.getIdDocente());
        if (docente == null) {
            return "El docente seleccionado no existe";
        }
        if (!"ACTIVO".equals(docente.getEstado())) {
            return "El docente seleccionado no está activo";
        }
        
        // Validar fechas
        if (curso.getFechaInicio() == null) {
            return "La fecha de inicio es obligatoria";
        }
        if (curso.getFechaFin() == null) {
            return "La fecha de fin es obligatoria";
        }
        
        // Validar que fecha inicio no sea anterior a hoy
        Date hoy = new Date(System.currentTimeMillis());
        if (curso.getFechaInicio().before(hoy)) {
            return "La fecha de inicio no puede ser anterior a hoy";
        }
        
        // Validar que fecha fin sea posterior a fecha inicio
        if (!curso.getFechaFin().after(curso.getFechaInicio())) {
            return "La fecha de fin debe ser posterior a la fecha de inicio";
        }
        
        // Validar que la duración coincida aproximadamente con las fechas
        long diffEnMilisegundos = curso.getFechaFin().getTime() - curso.getFechaInicio().getTime();
        long diffEnDias = diffEnMilisegundos / (1000 * 60 * 60 * 24);
        long mesesAproximados = diffEnDias / 30;
        
        if (Math.abs(mesesAproximados - curso.getDuracionMeses()) > 1) {
            return "La diferencia entre las fechas no coincide con la duración en meses especificada";
        }
        
        // Validar estado
        if (curso.getEstado() == null || curso.getEstado().trim().isEmpty()) {
            return "Debe seleccionar el estado";
        }
        if (!curso.getEstado().equals("ACTIVO") && 
            !curso.getEstado().equals("INACTIVO") && 
            !curso.getEstado().equals("FINALIZADO")) {
            return "El estado debe ser ACTIVO, INACTIVO o FINALIZADO";
        }
        
        return null; // Todo válido
    }
    
    /**
     * Calcular fecha de fin según fecha inicio y duración
     * @param fechaInicio fecha de inicio
     * @param duracionMeses duración en meses
     * @return Fecha de fin calculada
     */
    public Date calcularFechaFin(Date fechaInicio, int duracionMeses) {
        if (fechaInicio == null || duracionMeses <= 0) {
            return null;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.MONTH, duracionMeses);
        
        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * Obtener representación de texto del horario
     * @param idHorario ID del horario
     * @return String con formato legible del horario
     */
    public String obtenerTextoHorario(int idHorario) {
        MODELO_Horario horario = horarioDAO.buscarPorId(idHorario);
        if (horario == null) return "";
        
        return horarioDAO.obtenerTextoHorario(horario);
    }
    
    /**
     * Obtener nombre completo del docente
     * @param idDocente ID del docente
     * @return Nombre completo del docente
     */
    public String obtenerNombreDocente(int idDocente) {
        MODELO_Docente docente = docenteDAO.buscarPorId(idDocente);
        if (docente == null) return "";
        
        return docente.getNombres() + " " + 
               docente.getApellidoPaterno() + " " + 
               docente.getApellidoMaterno();
    }
}