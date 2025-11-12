package CONTROLADOR;

import DAO.DAO_Estudiante;
import DAO.DAO_Matricula;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Matricula;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Controlador para gestionar la lógica de negocio de Estudiantes
 * @author MartinSoftware
 */
public class CONTROLADOR_Estudiante {
    
    private DAO_Estudiante estudianteDAO;
    private DAO_Matricula matriculaDAO;
    
    public CONTROLADOR_Estudiante() {
        this.estudianteDAO = new DAO_Estudiante();
        this.matriculaDAO = new DAO_Matricula();
    }
    
    /**
     * Listar todos los estudiantes
     * @return Lista de estudiantes
     */
    public List<MODELO_Estudiante> listarTodos() {
        return estudianteDAO.listarTodos();
    }
    
    /**
     * Listar solo estudiantes activos
     * @return Lista de estudiantes activos
     */
    public List<MODELO_Estudiante> listarActivos() {
        return estudianteDAO.listarActivos();
    }
    
    /**
     * Buscar estudiante por ID
     * @param id ID del estudiante
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarPorId(int id) {
        return estudianteDAO.buscarPorId(id);
    }
    
    /**
     * Buscar estudiante por documento
     * @param numeroDocumento número de documento
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarPorDocumento(String numeroDocumento) {
        return estudianteDAO.buscarPorDocumento(numeroDocumento);
    }
    
    /**
     * Buscar estudiante por código
     * @param codigoEstudiantil código del estudiante
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarPorCodigo(String codigoEstudiantil) {
        return estudianteDAO.buscarPorCodigo(codigoEstudiantil);
    }
    
    /**
     * Listar matrículas de un estudiante (historial)
     * @param idEstudiante ID del estudiante
     * @return Lista de matrículas del estudiante
     */
    public List<MODELO_Matricula> obtenerHistorialMatriculas(int idEstudiante) {
        return matriculaDAO.listarPorEstudiante(idEstudiante);
    }
    
    /**
     * Actualizar estudiante con validaciones
     * @param estudiante objeto Estudiante con datos actualizados
     * @return Mensaje de resultado
     */
    public String actualizarEstudiante(MODELO_Estudiante estudiante) {
        // Validaciones
        String error = validarEstudiante(estudiante);
        if (error != null) {
            return error;
        }
        
        // Actualizar
        boolean resultado = estudianteDAO.actualizar(estudiante);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al actualizar el estudiante";
        }
    }
    
    /**
     * Dar de baja a un estudiante (cambiar estado a RETIRADO)
     * @param id ID del estudiante
     * @return Mensaje de resultado
     */
    public String darDeBajaEstudiante(int id) {
        // Verificar que existe
        MODELO_Estudiante estudiante = estudianteDAO.buscarPorId(id);
        if (estudiante == null) {
            return "El estudiante no existe";
        }
        
        // Verificar si tiene matrículas activas
        List<MODELO_Matricula> matriculasActivas = matriculaDAO.listarPorEstudiante(id);
        int activas = 0;
        for (MODELO_Matricula m : matriculasActivas) {
            if ("ACTIVO".equals(m.getEstado())) {
                activas++;
            }
        }
        
        if (activas > 0) {
            return "El estudiante tiene " + activas + " matrícula(s) activa(s).\n" +
                   "Debe anular las matrículas antes de dar de baja al estudiante.";
        }
        
        // Dar de baja
        boolean resultado = estudianteDAO.eliminar(id);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al dar de baja al estudiante";
        }
    }
    
    /**
     * Calcular edad según fecha de nacimiento
     * @param fechaNacimiento fecha de nacimiento
     * @return Edad en años
     */
    public int calcularEdad(Date fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        
        Calendar fechaNac = Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);
        
        Calendar hoy = Calendar.getInstance();
        
        int edad = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        
        if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNac.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }
        
        return edad;
    }
    
    /**
     * Validar datos del estudiante
     * @param estudiante objeto a validar
     * @return Mensaje de error o null si es válido
     */
    private String validarEstudiante(MODELO_Estudiante estudiante) {
        // Validar tipo de documento
        if (estudiante.getTipoDocumento() == null || estudiante.getTipoDocumento().trim().isEmpty()) {
            return "Debe seleccionar el tipo de documento";
        }
        
        // Validar número de documento
        if (estudiante.getNumeroDocumento() == null || estudiante.getNumeroDocumento().trim().isEmpty()) {
            return "El número de documento es obligatorio";
        }
        
        // Validar formato según tipo de documento
        String numero = estudiante.getNumeroDocumento().trim();
        if ("DNI".equals(estudiante.getTipoDocumento())) {
            if (!numero.matches("\\d{8}")) {
                return "El DNI debe tener 8 dígitos";
            }
        } else if ("CE".equals(estudiante.getTipoDocumento()) || "PASAPORTE".equals(estudiante.getTipoDocumento())) {
            if (numero.length() < 6 || numero.length() > 20) {
                return "El número de documento debe tener entre 6 y 20 caracteres";
            }
        }
        
        // Validar nombres
        if (estudiante.getNombres() == null || estudiante.getNombres().trim().isEmpty()) {
            return "Los nombres son obligatorios";
        }
        if (estudiante.getNombres().trim().length() < 2) {
            return "Los nombres deben tener al menos 2 caracteres";
        }
        
        // Validar apellido paterno
        if (estudiante.getApellidoPaterno() == null || estudiante.getApellidoPaterno().trim().isEmpty()) {
            return "El apellido paterno es obligatorio";
        }
        if (estudiante.getApellidoPaterno().trim().length() < 2) {
            return "El apellido paterno debe tener al menos 2 caracteres";
        }
        
        // Validar apellido materno
        if (estudiante.getApellidoMaterno() == null || estudiante.getApellidoMaterno().trim().isEmpty()) {
            return "El apellido materno es obligatorio";
        }
        if (estudiante.getApellidoMaterno().trim().length() < 2) {
            return "El apellido materno debe tener al menos 2 caracteres";
        }
        
        // Validar fecha de nacimiento
        if (estudiante.getFechaNacimiento() == null) {
            return "La fecha de nacimiento es obligatoria";
        }
        
        // Validar edad
        int edad = calcularEdad(estudiante.getFechaNacimiento());
        if (edad < 15) {
            return "El estudiante debe tener al menos 15 años";
        }
        if (edad > 100) {
            return "La fecha de nacimiento no es válida";
        }
        
        // Validar sexo
        if (estudiante.getSexo() == null || estudiante.getSexo().trim().isEmpty()) {
            return "Debe seleccionar el sexo";
        }
        if (!estudiante.getSexo().equals("M") && !estudiante.getSexo().equals("F")) {
            return "El sexo debe ser M o F";
        }
        
        // Validar teléfono
        if (estudiante.getTelefono() == null || estudiante.getTelefono().trim().isEmpty()) {
            return "El teléfono es obligatorio";
        }
        if (!estudiante.getTelefono().trim().matches("\\d{9}")) {
            return "El teléfono debe tener 9 dígitos";
        }
        
        // Validar email (opcional pero si existe debe ser válido)
        if (estudiante.getEmail() != null && !estudiante.getEmail().trim().isEmpty()) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!estudiante.getEmail().trim().matches(emailRegex)) {
                return "El formato del email no es válido";
            }
        }
        
        // Validar estado
        if (estudiante.getEstado() == null || estudiante.getEstado().trim().isEmpty()) {
            return "Debe seleccionar el estado";
        }
        if (!estudiante.getEstado().equals("ACTIVO") && 
            !estudiante.getEstado().equals("INACTIVO") && 
            !estudiante.getEstado().equals("RETIRADO")) {
            return "El estado debe ser ACTIVO, INACTIVO o RETIRADO";
        }
        
        return null; // Todo válido
    }
    
    /**
     * Obtener nombre completo del estudiante
     * @param estudiante objeto Estudiante
     * @return Nombre completo
     */
    public String obtenerNombreCompleto(MODELO_Estudiante estudiante) {
        if (estudiante == null) {
            return "";
        }
        return estudiante.getNombres() + " " + 
               estudiante.getApellidoPaterno() + " " + 
               estudiante.getApellidoMaterno();
    }
}