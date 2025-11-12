/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import DAO.DAO_Usuario;
import MODELO.MODELO_Usuario;
import java.util.List;

/**
 *
 * @author MartinSoftware
 */
public class CONTROLADOR_Usuario {
    private DAO_Usuario usuarioDAO;
    
    public CONTROLADOR_Usuario() {
        this.usuarioDAO = new DAO_Usuario();
    }
    
    /**
     * Listar todos los usuarios
     * @return Lista de usuarios
     */
    public List<MODELO_Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }
    
    /**
     * Buscar usuario por ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    public MODELO_Usuario buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }
    
    /**
     * Buscar usuario por username
     * @param username nombre de usuario
     * @return Usuario encontrado o null
     */
    public MODELO_Usuario buscarPorUsername(String username) {
        return usuarioDAO.buscarPorUsername(username);
    }
    
    /**
     * Registrar nuevo usuario con validaciones
     * @param usuario objeto Usuario a insertar
     * @return Mensaje de resultado
     */
    public String registrarUsuario(MODELO_Usuario usuario) {
        // Validaciones
        String error = validarUsuario(usuario);
        if (error != null) {
            return error;
        }
        
        // Verificar si el username ya existe
        if (usuarioDAO.existeUsername(usuario.getUsername())) {
            return "El nombre de usuario ya está registrado";
        }
        
        // Insertar
        boolean resultado = usuarioDAO.insertar(usuario);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al registrar el usuario";
        }
    }
    
    /**
     * Actualizar usuario con validaciones
     * @param usuario objeto Usuario con datos actualizados
     * @return Mensaje de resultado
     */
    public String actualizarUsuario(MODELO_Usuario usuario) {
        // Validaciones
        String error = validarUsuario(usuario);
        if (error != null) {
            return error;
        }
        
        // Actualizar
        boolean resultado = usuarioDAO.actualizar(usuario);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al actualizar el usuario";
        }
    }
    
    /**
     * Eliminar usuario (cambiar estado a INACTIVO)
     * @param id ID del usuario
     * @return Mensaje de resultado
     */
    public String eliminarUsuario(int id) {
        // Verificar que existe
        MODELO_Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            return "El usuario no existe";
        }
        
        // No permitir eliminar el usuario admin principal
        if ("admin".equals(usuario.getUsername())) {
            return "No se puede desactivar el usuario administrador principal";
        }
        
        boolean resultado = usuarioDAO.eliminar(id);
        
        if (resultado) {
            return "EXITO";
        } else {
            return "Error al desactivar el usuario";
        }
    }
    
    /**
     * Validar datos del usuario
     * @param usuario objeto a validar
     * @return Mensaje de error o null si es válido
     */
    private String validarUsuario(MODELO_Usuario usuario) {
        // Validar username
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            return "El nombre de usuario es obligatorio";
        }
        
        if (usuario.getUsername().trim().length() < 4) {
            return "El nombre de usuario debe tener al menos 4 caracteres";
        }
        
        if (usuario.getUsername().trim().length() > 50) {
            return "El nombre de usuario no puede tener más de 50 caracteres";
        }
        
        // Validar que username no tenga espacios
        if (usuario.getUsername().contains(" ")) {
            return "El nombre de usuario no puede contener espacios";
        }
        
        // Validar password
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            return "La contraseña es obligatoria";
        }
        
        if (usuario.getPassword().trim().length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres";
        }
        
        if (usuario.getPassword().trim().length() > 255) {
            return "La contraseña no puede tener más de 255 caracteres";
        }
        
        // Validar nombre completo
        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()) {
            return "El nombre completo es obligatorio";
        }
        
        if (usuario.getNombreCompleto().trim().length() < 3) {
            return "El nombre completo debe tener al menos 3 caracteres";
        }
        
        // Validar rol
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            return "Debe seleccionar un rol";
        }
        
        if (!usuario.getRol().equals("ADMINISTRADOR") && 
            !usuario.getRol().equals("RECEPCIONISTA")) {
            return "El rol debe ser ADMINISTRADOR o RECEPCIONISTA";
        }
        
        // Validar estado
        if (usuario.getEstado() == null || usuario.getEstado().trim().isEmpty()) {
            return "Debe seleccionar el estado";
        }
        
        if (!usuario.getEstado().equals("ACTIVO") && 
            !usuario.getEstado().equals("INACTIVO")) {
            return "El estado debe ser ACTIVO o INACTIVO";
        }
        
        return null; // Todo válido
    }
}
