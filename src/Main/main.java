package Main;
import VISTA.VISTA_Login;
import javax.swing.UIManager;
/**
 * Sistema de gestión de matricula
 * PROYECTO DE INTEGRADOR 1 - SISTEMAS | SOFTWARE
 * @author MartinSoftware
 */
public class main {
    public static void main(String[] args) {
        try {
            // Configurar Look and Feel (opcional pero recomendado)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la aplicación con el Login
        java.awt.EventQueue.invokeLater(() -> {
            new VISTA_Login().setVisible(true);
        });
    }
}
