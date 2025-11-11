/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;
import java.sql.Timestamp;
/**
 *
 * @author MartinSoftware
 */
public class MODELO_Usuario {
    private int idUsuario;
    private String username;
    private String password;
    private String nombreCompleto;
    private String rol; // ADMINISTRADOR, RECEPCIONISTA
    private String estado; // ACTIVO, INACTIVO
    private Timestamp fechaCreacion;
    private Timestamp ultimoAcceso;

    public MODELO_Usuario(){
        
    }
    
    public MODELO_Usuario(String username, String password, String nombreCompleto, String rol, String estado, Timestamp fechaCreacion, Timestamp ultimoAcceso) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.ultimoAcceso = ultimoAcceso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    @Override
    public String toString() {
        return "MODELO_Usuario{" + "idUsuario=" + idUsuario + ", username=" + username + ", password=" + password + ", nombreCompleto=" + nombreCompleto + ", rol=" + rol + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion + ", ultimoAcceso=" + ultimoAcceso + '}';
    }
    
    
    
}
