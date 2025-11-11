/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author MartinSoftware
 */
public class MODELO_Curso {
    private int idCurso;
    private String nombreCurso;
    private String nivel; // BASICO, INTERMEDIO, AVANZADO
    private String descripcion;
    private int duracionMeses;
    private int cuposDisponibles;
    private int idHorario; // FK
    private int idDocente; // FK
    private Date fechaInicio;
    private Date fechaFin;
    private String estado; // ACTIVO, INACTIVO, FINALIZADO
    private Timestamp fechaCreacion;
    
    // Opcional: Para mostrar en las vistas
    private String nombreDocente; // No está en la BD, se llena con JOIN
    private String horarioTexto;  // No está en la BD, se llena con JOIN

    public MODELO_Curso(){
        
    }
    
    public MODELO_Curso(String nombreCurso, String nivel, String descripcion, int duracionMeses, int cuposDisponibles, int idHorario, int idDocente, Date fechaInicio, Date fechaFin, String estado, Timestamp fechaCreacion, String nombreDocente, String horarioTexto) {
        this.nombreCurso = nombreCurso;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.cuposDisponibles = cuposDisponibles;
        this.idHorario = idHorario;
        this.idDocente = idDocente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.nombreDocente = nombreDocente;
        this.horarioTexto = horarioTexto;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getHorarioTexto() {
        return horarioTexto;
    }

    public void setHorarioTexto(String horarioTexto) {
        this.horarioTexto = horarioTexto;
    }
    
    
    
}
