package CONTROLADOR;

import DAO.DAO_Estudiante;
import DAO.DAO_Curso;
import DAO.DAO_Matricula;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Curso;
import MODELO.MODELO_Matricula;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Controlador para gestionar la lógica de negocio de Matrículas
 *
 * @author MartinSoftware
 */
public class CONTROLADOR_Matricula {

    private DAO_Estudiante estudianteDAO;
    private DAO_Curso cursoDAO;
    private DAO_Matricula matriculaDAO;

    public CONTROLADOR_Matricula() {
        this.estudianteDAO = new DAO_Estudiante();
        this.cursoDAO = new DAO_Curso();
        this.matriculaDAO = new DAO_Matricula();
    }

    // ========================================
    // MÉTODOS PARA ESTUDIANTES
    // ========================================
    /**
     * Buscar estudiante por número de documento
     *
     * @param numeroDocumento número de documento
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarEstudiantePorDocumento(String numeroDocumento) {
        return estudianteDAO.buscarPorDocumento(numeroDocumento);
    }

    /**
     * Registrar nuevo estudiante con código autogenerado
     *
     * @param estudiante objeto Estudiante a insertar
     * @return ID del estudiante insertado, 0 si falla
     */
    public int registrarEstudiante(MODELO_Estudiante estudiante) {
        // Validar estudiante
        String error = validarEstudiante(estudiante);
        if (error != null) {
            System.err.println("✗ Error de validación: " + error);
            return 0;
        }

        // Verificar si el documento ya existe
        if (estudianteDAO.existeDocumento(estudiante.getNumeroDocumento())) {
            System.err.println("✗ El número de documento ya está registrado");
            return 0;
        }

        // Generar código estudiantil
        String codigoEstudiantil = estudianteDAO.generarCodigoEstudiantil();
        if (codigoEstudiantil == null) {
            System.err.println("✗ Error al generar código estudiantil");
            return 0;
        }

        estudiante.setCodigoEstudiantil(codigoEstudiantil);
        estudiante.setEstado("ACTIVO");

        // Insertar
        return estudianteDAO.insertar(estudiante);
    }

    /**
     * Calcular edad según fecha de nacimiento
     *
     * @param fechaNacimiento fecha de nacimiento
     * @return Edad en años
     */
    public int calcularEdad(Date fechaNacimiento) {
        if (fechaNacimiento == null) {
            return 0;
        }

        Calendar fechaNac = Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);

        Calendar hoy = Calendar.getInstance();

        int edad = hoy.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);

        // Verificar si ya cumplió años este año
        if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNac.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }

        return edad;
    }

    // ========================================
    // MÉTODOS PARA CURSOS
    // ========================================
    /**
     * Listar solo cursos activos
     *
     * @return Lista de cursos activos
     */
    public List<MODELO_Curso> listarCursosActivos() {
        return cursoDAO.listarActivos();
    }

    /**
     * Buscar curso por ID
     *
     * @param id ID del curso
     * @return Curso encontrado o null
     */
    public MODELO_Curso buscarCursoPorId(int id) {
        return cursoDAO.buscarPorId(id);
    }

    /**
     * Verificar si un curso tiene cupos disponibles
     *
     * @param idCurso ID del curso
     * @return true si hay cupos, false si no
     */
    public boolean verificarCuposDisponibles(int idCurso) {
        MODELO_Curso curso = cursoDAO.buscarPorId(idCurso);
        if (curso == null) {
            return false;
        }

        return curso.getCuposDisponibles() > 0;
    }

    /**
     * Decrementar un cupo del curso (cuando se matricula)
     *
     * @param idCurso ID del curso
     * @return true si se decrementó correctamente
     */
    private boolean decrementarCupo(int idCurso) {
        MODELO_Curso curso = cursoDAO.buscarPorId(idCurso);
        if (curso == null) {
            return false;
        }

        int cuposActuales = curso.getCuposDisponibles();
        if (cuposActuales <= 0) {
            return false;
        }

        curso.setCuposDisponibles(cuposActuales - 1);
        return cursoDAO.actualizar(curso);
    }

    /**
     * Incrementar un cupo del curso (cuando se anula matrícula)
     *
     * @param idCurso ID del curso
     * @return true si se incrementó correctamente
     */
    private boolean incrementarCupo(int idCurso) {
        MODELO_Curso curso = cursoDAO.buscarPorId(idCurso);
        if (curso == null) {
            return false;
        }

        curso.setCuposDisponibles(curso.getCuposDisponibles() + 1);
        return cursoDAO.actualizar(curso);
    }

    // ========================================
    // MÉTODOS PARA MATRÍCULAS
    // ========================================
    /**
     * Listar todas las matrículas
     *
     * @return Lista de matrículas
     */
    public List<MODELO_Matricula> listarTodasMatriculas() {
        return matriculaDAO.listarTodas();
    }

    /**
     * Listar solo matrículas activas
     *
     * @return Lista de matrículas activas
     */
    public List<MODELO_Matricula> listarMatriculasActivas() {
        return matriculaDAO.listarActivas();
    }

    /**
     * Listar matrículas por curso
     *
     * @param idCurso ID del curso
     * @return Lista de matrículas del curso
     */
    public List<MODELO_Matricula> listarMatriculasPorCurso(int idCurso) {
        // Necesitas crear este método en DAO_Matricula
        return matriculaDAO.listarPorCurso(idCurso);
    }

    /**
     * Buscar estudiante por ID
     *
     * @param id ID del estudiante
     * @return Estudiante encontrado o null
     */
    public MODELO_Estudiante buscarEstudiantePorId(int id) {
        return estudianteDAO.buscarPorId(id);
    }

    /**
     * Buscar matrícula por ID
     *
     * @param id ID de la matrícula
     * @return Matrícula encontrada o null
     */
    public MODELO_Matricula buscarMatriculaPorId(int id) {
        return matriculaDAO.buscarPorId(id);
    }

    /**
     * Buscar matrícula por código
     *
     * @param codigoMatricula código de matrícula
     * @return Matrícula encontrada o null
     */
    public MODELO_Matricula buscarMatriculaPorCodigo(String codigoMatricula) {
        return matriculaDAO.buscarPorCodigo(codigoMatricula);
    }

    /**
     * Listar matrículas de un estudiante
     *
     * @param idEstudiante ID del estudiante
     * @return Lista de matrículas del estudiante
     */
    public List<MODELO_Matricula> listarMatriculasPorEstudiante(int idEstudiante) {
        return matriculaDAO.listarPorEstudiante(idEstudiante);
    }

    /**
     * Registrar nueva matrícula con validaciones completas
     *
     * @param matricula objeto Matrícula a insertar
     * @return Mensaje de resultado
     */
    public String registrarMatricula(MODELO_Matricula matricula) {
        // Validar matrícula
        String error = validarMatricula(matricula);
        if (error != null) {
            return error;
        }

        // Verificar que el estudiante existe
        MODELO_Estudiante estudiante = estudianteDAO.buscarPorId(matricula.getIdEstudiante());
        if (estudiante == null) {
            return "El estudiante no existe";
        }

        // Verificar que el estudiante está activo
        if (!"ACTIVO".equals(estudiante.getEstado())) {
            return "El estudiante no está activo. Estado: " + estudiante.getEstado();
        }

        // Verificar que el curso existe
        MODELO_Curso curso = cursoDAO.buscarPorId(matricula.getIdCurso());
        if (curso == null) {
            return "El curso seleccionado no existe";
        }

        // Verificar que el curso está activo
        if (!"ACTIVO".equals(curso.getEstado())) {
            return "El curso no está activo. Estado: " + curso.getEstado();
        }

        // Verificar cupos disponibles
        if (curso.getCuposDisponibles() <= 0) {
            return "El curso no tiene cupos disponibles";
        }

        // Verificar que el estudiante no esté ya matriculado en ese curso
        if (matriculaDAO.verificarMatriculaExistente(matricula.getIdEstudiante(), matricula.getIdCurso())) {
            return "El estudiante ya está matriculado en este curso";
        }

        // Generar código de matrícula
        String codigoMatricula = matriculaDAO.generarCodigoMatricula();
        if (codigoMatricula == null) {
            return "Error al generar código de matrícula";
        }

        matricula.setCodigoMatricula(codigoMatricula);

        // Insertar matrícula
        boolean resultadoMatricula = matriculaDAO.insertar(matricula);

        if (resultadoMatricula) {
            // Decrementar cupo del curso
            boolean resultadoCupo = decrementarCupo(matricula.getIdCurso());

            if (!resultadoCupo) {
                System.err.println("⚠ Advertencia: Matrícula registrada pero no se pudo actualizar el cupo");
            }

            return "EXITO:" + codigoMatricula; // Retorna código para mostrarlo
        } else {
            return "Error al registrar la matrícula";
        }
    }

    /**
     * Anular matrícula (devuelve el cupo al curso)
     *
     * @param idMatricula ID de la matrícula
     * @return Mensaje de resultado
     */
    public String anularMatricula(int idMatricula) {
        // Verificar que existe
        MODELO_Matricula matricula = matriculaDAO.buscarPorId(idMatricula);
        if (matricula == null) {
            return "La matrícula no existe";
        }

        // Verificar que está activa
        if (!"ACTIVO".equals(matricula.getEstado())) {
            return "La matrícula ya está " + matricula.getEstado();
        }

        // Anular matrícula
        boolean resultadoAnulacion = matriculaDAO.anular(idMatricula);

        if (resultadoAnulacion) {
            // Devolver cupo al curso
            boolean resultadoCupo = incrementarCupo(matricula.getIdCurso());

            if (!resultadoCupo) {
                System.err.println("⚠ Advertencia: Matrícula anulada pero no se pudo devolver el cupo");
            }

            return "EXITO";
        } else {
            return "Error al anular la matrícula";
        }
    }

    // ========================================
    // VALIDACIONES
    // ========================================
    /**
     * Validar datos del estudiante
     *
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
        }

        // Validar nombres
        if (estudiante.getNombres() == null || estudiante.getNombres().trim().isEmpty()) {
            return "Los nombres son obligatorios";
        }

        // Validar apellidos
        if (estudiante.getApellidoPaterno() == null || estudiante.getApellidoPaterno().trim().isEmpty()) {
            return "El apellido paterno es obligatorio";
        }

        if (estudiante.getApellidoMaterno() == null || estudiante.getApellidoMaterno().trim().isEmpty()) {
            return "El apellido materno es obligatorio";
        }

        // Validar fecha de nacimiento
        if (estudiante.getFechaNacimiento() == null) {
            return "La fecha de nacimiento es obligatoria";
        }

        // Validar edad (debe ser mayor de edad, 18 años)
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

        // Validar teléfono
        if (estudiante.getTelefono() == null || estudiante.getTelefono().trim().isEmpty()) {
            return "El teléfono es obligatorio";
        }
        if (!estudiante.getTelefono().trim().matches("\\d{9}")) {
            return "El teléfono debe tener 9 dígitos";
        }

        return null; // Todo válido
    }

    /**
     * Validar datos de la matrícula
     *
     * @param matricula objeto a validar
     * @return Mensaje de error o null si es válido
     */
    private String validarMatricula(MODELO_Matricula matricula) {
        // Validar estudiante
        if (matricula.getIdEstudiante() <= 0) {
            return "Debe seleccionar un estudiante";
        }

        // Validar curso
        if (matricula.getIdCurso() <= 0) {
            return "Debe seleccionar un curso";
        }

        // Validar fecha de matrícula
        if (matricula.getFechaMatricula() == null) {
            return "La fecha de matrícula es obligatoria";
        }

        // Validar estado
        if (matricula.getEstado() == null || matricula.getEstado().trim().isEmpty()) {
            return "Debe especificar el estado de la matrícula";
        }

        // Validar usuario que registra
        if (matricula.getIdUsuarioRegistro() <= 0) {
            return "Error: No se especificó el usuario que registra";
        }

        return null; // Todo válido
    }

    /**
     * Obtener nombre completo del estudiante
     *
     * @param estudiante objeto Estudiante
     * @return Nombre completo
     */
    public String obtenerNombreCompletoEstudiante(MODELO_Estudiante estudiante) {
        if (estudiante == null) {
            return "";
        }

        return estudiante.getNombres() + " "
                + estudiante.getApellidoPaterno() + " "
                + estudiante.getApellidoMaterno();
    }
}
