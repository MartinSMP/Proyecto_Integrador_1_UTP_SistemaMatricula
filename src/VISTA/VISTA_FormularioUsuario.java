/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VISTA;

import CONTROLADOR.CONTROLADOR_Usuario;
import MODELO.MODELO_Usuario;
import javax.swing.JOptionPane;

/**
 * Formulario para registrar y editar usuarios
 * @author MartinSoftware
 */
public class VISTA_FormularioUsuario extends javax.swing.JDialog {

    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Usuario controlador;
    private MODELO_Usuario usuarioEditar;
    private boolean modoEdicion;
    
    /**
     * Constructor para nuevo usuario o editar usuario
     * @param parent ventana padre
     * @param modal si es modal
     * @param usuario objeto usuario (null para nuevo, con datos para editar)
     * @param usuarioLog usuario logueado
     */
    public VISTA_FormularioUsuario(java.awt.Frame parent, boolean modal, 
                                   MODELO_Usuario usuario, MODELO_Usuario usuarioLog) {
        super(parent, modal);
        initComponents();
        
        this.usuarioLogueado = usuarioLog;
        this.usuarioEditar = usuario;
        this.controlador = new CONTROLADOR_Usuario();
        this.modoEdicion = (usuario != null);
        
        configurarVentana();
        configurarCampos();
        
        if (modoEdicion) {
            cargarDatosUsuario();
        }
    }
    
    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        if (modoEdicion) {
            this.setTitle("Editar Usuario");
        } else {
            this.setTitle("Nuevo Usuario");
        }
    }
    
    /**
     * Configurar campos del formulario
     */
    private void configurarCampos() {
        // Configurar ComboBox Rol
        cboRol.removeAllItems();
        cboRol.addItem("ADMINISTRADOR");
        cboRol.addItem("RECEPCIONISTA");
        cboRol.setSelectedIndex(0);
        
        // Configurar ComboBox Estado
        cboEstado.removeAllItems();
        cboEstado.addItem("ACTIVO");
        cboEstado.addItem("INACTIVO");
        cboEstado.setSelectedIndex(0);
        
        // Configurar checkbox mostrar contraseña (si lo tienes)
        if (chkMostrarPassword != null) {
            chkMostrarPassword.addActionListener(e -> {
                if (chkMostrarPassword.isSelected()) {
                    txtPassword.setEchoChar((char) 0);
                    txtConfirmarPassword.setEchoChar((char) 0);
                } else {
                    txtPassword.setEchoChar('•');
                    txtConfirmarPassword.setEchoChar('•');
                }
            });
        }
        
        // Enfocar en el primer campo
        txtUsername.requestFocus();
    }
    
    /**
     * Cargar datos del usuario en modo edición
     */
    private void cargarDatosUsuario() {
        if (usuarioEditar == null) return;
        
        txtUsername.setText(usuarioEditar.getUsername());
        txtUsername.setEditable(false); // No se puede cambiar el username
        txtUsername.setEnabled(false);
        
        txtPassword.setText(usuarioEditar.getPassword());
        txtConfirmarPassword.setText(usuarioEditar.getPassword());
        txtNombreCompleto.setText(usuarioEditar.getNombreCompleto());
        cboRol.setSelectedItem(usuarioEditar.getRol());
        cboEstado.setSelectedItem(usuarioEditar.getEstado());
        
        System.out.println("✓ Datos cargados para edición");
    }
    
    /**
     * Obtener datos del formulario y crear objeto Usuario
     */
    private MODELO_Usuario obtenerDatosFormulario() {
        MODELO_Usuario usuario = new MODELO_Usuario();
        
        // Si es edición, mantener el ID
        if (modoEdicion) {
            usuario.setIdUsuario(usuarioEditar.getIdUsuario());
        }
        
        usuario.setUsername(txtUsername.getText().trim());
        usuario.setPassword(new String(txtPassword.getPassword()).trim());
        usuario.setNombreCompleto(txtNombreCompleto.getText().trim());
        usuario.setRol(cboRol.getSelectedItem().toString());
        usuario.setEstado(cboEstado.getSelectedItem().toString());
        
        return usuario;
    }
    
    /**
     * Limpiar todos los campos
     */
    private void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmarPassword.setText("");
        txtNombreCompleto.setText("");
        cboRol.setSelectedIndex(0);
        cboEstado.setSelectedIndex(0);
        txtUsername.requestFocus();
    }
    
    /**
     * Validar campos obligatorios
     */
    private boolean validarCamposObligatorios() {
        if (txtUsername.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre de usuario es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocus();
            return false;
        }
        
        // Validar que username no tenga espacios
        if (txtUsername.getText().contains(" ")) {
            JOptionPane.showMessageDialog(this, 
                "El nombre de usuario no puede contener espacios", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocus();
            return false;
        }
        
        String password = new String(txtPassword.getPassword()).trim();
        String confirmar = new String(txtConfirmarPassword.getPassword()).trim();
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña es obligatoria", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe tener al menos 6 caracteres", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }
        
        if (confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe confirmar la contraseña", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtConfirmarPassword.requestFocus();
            return false;
        }
        
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, 
                "Las contraseñas no coinciden", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtConfirmarPassword.requestFocus();
            return false;
        }
        
        if (txtNombreCompleto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre completo es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtNombreCompleto.requestFocus();
            return false;
        }
        
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        cboEstado = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        txtConfirmarPassword = new javax.swing.JPasswordField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtNombreCompleto = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        cboRol = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        chkMostrarPassword = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("AGREGAR NUEVO USUARIO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 440, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asociación Privada de Patronato");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 440, 20));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("USUARIO:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 170, 30));

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(0, 0, 0));
        txtUsername.setBorder(null);
        jPanel1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 200, 30));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 200, 10));

        cboEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 200, 30));

        btnCancelar.setBackground(new java.awt.Color(231, 76, 60));
        btnCancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 180, 40));

        btnGuardar.setBackground(new java.awt.Color(39, 174, 96));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("AGREGAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 180, 40));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("CONTRASEÑA:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 170, 30));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 200, 10));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 200, 10));

        txtConfirmarPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtConfirmarPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtConfirmarPassword.setBorder(null);
        jPanel1.add(txtConfirmarPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 200, 20));

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtPassword.setBorder(null);
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 200, 20));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("NOMBRE COMPLETO:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 170, 30));

        txtNombreCompleto.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreCompleto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombreCompleto.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreCompleto.setBorder(null);
        jPanel1.add(txtNombreCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 200, 30));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 200, 10));

        cboRol.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMINISTRADOR", "RECEPCIONISTA" }));
        jPanel1.add(cboRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 200, 30));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("ROL:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 170, 30));

        jLabel13.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("ESTADO:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 170, 30));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("CONFIRMAR CONTRASEÑA:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 190, 30));

        chkMostrarPassword.setText("MOSTRAR CONTRASEÑA");
        jPanel1.add(chkMostrarPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 190, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

// Validar campos obligatorios
        if (!validarCamposObligatorios()) {
            return;
        }
        
        // Obtener datos del formulario
        MODELO_Usuario usuario = obtenerDatosFormulario();
        
        String resultado;
        
        if (modoEdicion) {
            // MODO EDICIÓN
            resultado = controlador.actualizarUsuario(usuario);
            
            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Usuario actualizado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    resultado, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            // MODO NUEVO
            resultado = controlador.registrarUsuario(usuario);
            
            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Usuario registrado correctamente\n\n" +
                    "Username: " + usuario.getUsername() + "\n" +
                    "Nombre: " + usuario.getNombreCompleto() + "\n" +
                    "Rol: " + usuario.getRol(), 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Preguntar si desea registrar otro
                int opcion = JOptionPane.showConfirmDialog(this, 
                    "¿Desea registrar otro usuario?", 
                    "Confirmar", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    limpiarCampos();
                } else {
                    this.dispose();
                }
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    resultado, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        boolean hayDatos = !txtUsername.getText().trim().isEmpty() ||
                          !txtNombreCompleto.getText().trim().isEmpty();
        
        if (hayDatos) {
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea cancelar?\nSe perderán los datos ingresados", 
                "Confirmar Cancelación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (opcion == JOptionPane.YES_OPTION) {
                this.dispose();
            }
        } else {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboRol;
    private javax.swing.JCheckBox chkMostrarPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPasswordField txtConfirmarPassword;
    private javax.swing.JTextField txtNombreCompleto;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
