package MODELO;

import java.sql.Date;
import java.sql.Timestamp;

public class MODELO_Matricula {
    private int idMatricula;
    private String codigoMatricula;
    private int idEstudiante;
    private int idCurso;
    private Date fechaMatricula;
    private String observaciones;
    private String estado;
    private int idUsuarioRegistro;
    private Timestamp fechaRegistro;
    
    private String nombreEstudiante;  // Para mostrar en tablas
    private String nombreCurso;       // Para mostrar en tablas
    private String nivelCurso;        // Para mostrar en tablas
    
    // Constructores
    
    public MODELO_Matricula() {
    }
    
    public MODELO_Matricula(String codigoMatricula, int idEstudiante, 
                           int idCurso, Date fechaMatricula, 
                           String observaciones, String estado, 
                           int idUsuarioRegistro) {
        this.codigoMatricula = codigoMatricula;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.fechaMatricula = fechaMatricula;
        this.observaciones = observaciones;
        this.estado = estado;
        this.idUsuarioRegistro = idUsuarioRegistro;
    }
    
    public MODELO_Matricula(int idMatricula, String codigoMatricula, 
                           int idEstudiante, int idCurso, 
                           Date fechaMatricula, String observaciones, 
                           String estado, int idUsuarioRegistro, 
                           Timestamp fechaRegistro) {
        this.idMatricula = idMatricula;
        this.codigoMatricula = codigoMatricula;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.fechaMatricula = fechaMatricula;
        this.observaciones = observaciones;
        this.estado = estado;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Getters y Setters
    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(String codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(int idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

   
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNivelCurso() {
        return nivelCurso;
    }

    public void setNivelCurso(String nivelCurso) {
        this.nivelCurso = nivelCurso;
    }

    @Override
    public String toString() {
        return "MODELO_Matricula{" + 
               "idMatricula=" + idMatricula + 
               ", codigoMatricula=" + codigoMatricula + 
               ", idEstudiante=" + idEstudiante + 
               ", idCurso=" + idCurso + 
               ", fechaMatricula=" + fechaMatricula + 
               ", observaciones=" + observaciones + 
               ", estado=" + estado + 
               ", idUsuarioRegistro=" + idUsuarioRegistro + 
               ", fechaRegistro=" + fechaRegistro + 
               ", nombreEstudiante=" + nombreEstudiante + 
               ", nombreCurso=" + nombreCurso + 
               ", nivelCurso=" + nivelCurso + 
               '}';
    }
}