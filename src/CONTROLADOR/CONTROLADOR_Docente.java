package CONTROLADOR;

import DAO.DAO_Docente;
import MODELO.MODELO_Docente;
import java.util.List;

/**
 * Controlador para gestionar la lógica de negocio de Docentes
 * @author MartinSoftware
 */
public class CONTROLADOR_Docente {
    
    private DAO_Docente docenteDAO;
    
    public CONTROLADOR_Docente() {
        this.docenteDAO = new DAO_Docente();
    }
    
    /**
     * Listar todos los docentes
     * @return Lista de docentes
     */
    public List<MODELO_Docente> listarTodos() {
        return docenteDAO.listarTodos();
    }
    
    /**
     * Listar solo docentes activos
     * @return Lista de docentes activos
     */
    public List<MODELO_Docente> listarActivos() {
        return docenteDAO.listarActivos();
    }
    
    /**
     * Buscar docente por ID
     * @param id ID del docente
     * @return Docente encontrado o null
     */
    public MODELO_Docente buscarPorId(int id) {
        return docenteDAO.buscarPorId(id);
    }
    
    /**
     * Buscar docente por documento
     * @param numeroDocumento número de documento
     * @return Docente encontrado o null
     */
    public MODELO_Docente buscarPorDocumento(String numeroDocumento) {
        return docenteDAO.buscarPorDocumento(numeroDocumento);
    }
    
    /**
     * Buscar docentes por nombre
     * @param texto texto a buscar
     * @return Lista de docentes encontrados
     */
    public List<MODELO_Docente> buscarPorNombre(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return listarTodos();
        }
        return docenteDAO.buscarPorNombre(texto);
    }
    
    /**
     * Registrar nuevo docente con validaciones
     * @param docente objeto Docente a insertar
     * @return Mensaje de resultado
     */
    public String registrarDocente(MODELO_Docente docente) {
        // Validaciones
        String error = validarDocente(docente);
        if (error != null) {
            return error;
        }
        
        // Verificar si el documento ya existe
        if (docenteDAO.existeDocumento(docente.getNumeroDocumento())) {
            return "El número de documento ya está registrado";
        }
        
        // Insertar
        boolean resultado = docenteDAO.insertar(docente);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al registrar el docente";
        }
    }
    
    /**
     * Actualizar docente con validaciones
     * @param docente objeto Docente con datos actualizados
     * @return Mensaje de resultado
     */
    public String actualizarDocente(MODELO_Docente docente) {
        // Validaciones
        String error = validarDocente(docente);
        if (error != null) {
            return error;
        }
        
        // Verificar si el documento ya existe (excepto el actual)
        if (docenteDAO.existeDocumentoExcepto(docente.getNumeroDocumento(), docente.getIdDocente())) {
            return "El número de documento ya está registrado en otro docente";
        }
        
        // Actualizar
        boolean resultado = docenteDAO.actualizar(docente);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al actualizar el docente";
        }
    }
    
    /**
     * Eliminar docente (cambiar estado a INACTIVO)
     * @param id ID del docente
     * @return Mensaje de resultado
     */
    public String eliminarDocente(int id) {
        // Verificar que existe
        MODELO_Docente docente = docenteDAO.buscarPorId(id);
        if (docente == null) {
            return "El docente no existe";
        }
        
        // TODO: Aquí podrías verificar si el docente tiene cursos asignados
        // y evitar eliminarlo si es necesario
        
        boolean resultado = docenteDAO.eliminar(id);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al eliminar el docente";
        }
    }
    
    public String activarDocente(int id) {
        // Verificar que existe
        MODELO_Docente docente = docenteDAO.buscarPorId(id);
        if (docente == null) {
            return "El docente no existe";
        }
        
        // TODO: Aquí podrías verificar si el docente tiene cursos asignados
        // y evitar eliminarlo si es necesario
        
        boolean resultado = docenteDAO.activar(id);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al eliminar el docente";
        }
    }
    
    /**
     * Validar datos del docente
     * @param docente objeto a validar
     * @return Mensaje de error o null si es válido
     */
    private String validarDocente(MODELO_Docente docente) {
        // Validar tipo de documento
        if (docente.getTipoDocumento() == null || docente.getTipoDocumento().trim().isEmpty()) {
            return "Debe seleccionar el tipo de documento";
        }
        
        // Validar número de documento
        if (docente.getNumeroDocumento() == null || docente.getNumeroDocumento().trim().isEmpty()) {
            return "El número de documento es obligatorio";
        }
        
        // Validar formato según tipo de documento
        String numero = docente.getNumeroDocumento().trim();
        if ("DNI".equals(docente.getTipoDocumento())) {
            if (!numero.matches("\\d{8}")) {
                return "El DNI debe tener 8 dígitos";
            }
        } else if ("CE".equals(docente.getTipoDocumento()) || "PASAPORTE".equals(docente.getTipoDocumento())) {
            if (numero.length() < 6 || numero.length() > 20) {
                return "El número de documento debe tener entre 6 y 20 caracteres";
            }
        }
        
        // Validar nombres
        if (docente.getNombres() == null || docente.getNombres().trim().isEmpty()) {
            return "Los nombres son obligatorios";
        }
        if (docente.getNombres().trim().length() < 2) {
            return "Los nombres deben tener al menos 2 caracteres";
        }
        
        // Validar apellido paterno
        if (docente.getApellidoPaterno() == null || docente.getApellidoPaterno().trim().isEmpty()) {
            return "El apellido paterno es obligatorio";
        }
        if (docente.getApellidoPaterno().trim().length() < 2) {
            return "El apellido paterno debe tener al menos 2 caracteres";
        }
        
        // Validar apellido materno
        if (docente.getApellidoMaterno() == null || docente.getApellidoMaterno().trim().isEmpty()) {
            return "El apellido materno es obligatorio";
        }
        if (docente.getApellidoMaterno().trim().length() < 2) {
            return "El apellido materno debe tener al menos 2 caracteres";
        }
        
        // Validar teléfono
        if (docente.getTelefono() == null || docente.getTelefono().trim().isEmpty()) {
            return "El teléfono es obligatorio";
        }
        if (!docente.getTelefono().trim().matches("\\d{9}")) {
            return "El teléfono debe tener 9 dígitos";
        }
        
        // Validar email (opcional pero si existe debe ser válido)
        if (docente.getEmail() != null && !docente.getEmail().trim().isEmpty()) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!docente.getEmail().trim().matches(emailRegex)) {
                return "El formato del email no es válido";
            }
        }
        
        // Validar especialidad
        if (docente.getEspecialidad() == null || docente.getEspecialidad().trim().isEmpty()) {
            return "La especialidad es obligatoria";
        }
        if (docente.getEspecialidad().trim().length() < 3) {
            return "La especialidad debe tener al menos 3 caracteres";
        }
        
        // Validar estado
        if (docente.getEstado() == null || docente.getEstado().trim().isEmpty()) {
            return "Debe seleccionar el estado";
        }
        if (!docente.getEstado().equals("ACTIVO") && !docente.getEstado().equals("INACTIVO")) {
            return "El estado debe ser ACTIVO o INACTIVO";
        }
        
        return null; // Todo válido
    }
    
    /**
     * Obtener nombre completo del docente
     * @param docente objeto Docente
     * @return Nombre completo
     */
    public String obtenerNombreCompleto(MODELO_Docente docente) {
        if (docente == null) {
            return "";
        }
        return docente.getNombres() + " " + 
               docente.getApellidoPaterno() + " " + 
               docente.getApellidoMaterno();
    }
}