package CONTROLADOR;

import DAO.DAO_Usuario;
import MODELO.MODELO_Usuario;

/**
 * Controlador para el Login
 * @author MartinSoftware
 */
public class CONTROLADOR_Login {
    
    private DAO_Usuario usuarioDAO;
    
    public CONTROLADOR_Login() {
        this.usuarioDAO = new DAO_Usuario();
    }
    /**
     * Validar credenciales de usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario si es válido, null si no
     */
    public MODELO_Usuario validarLogin(String username, String password) {
        // Validaciones básicas
        if (username == null || username.trim().isEmpty()) {
            System.out.println("✗ Username vacío");
            return null;
        }
        
        if (password == null || password.trim().isEmpty()) {
            System.out.println("✗ Password vacío");
            return null;
        }
        
        // Llamar al DAO para validar
        MODELO_Usuario usuario = usuarioDAO.validarLogin(username, password);
        
        if (usuario != null) {
            System.out.println("✓ Login exitoso: " + usuario.getUsername());
        } else {
            System.out.println("✗ Credenciales incorrectas");
        }
        
        return usuario;
    }
}