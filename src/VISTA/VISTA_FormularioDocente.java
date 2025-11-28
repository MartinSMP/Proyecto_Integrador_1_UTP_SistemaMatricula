/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VISTA;
import CONTROLADOR.CONTROLADOR_Docente;
import MODELO.MODELO_Docente;
import MODELO.MODELO_Usuario;
import javax.swing.JOptionPane;
/**
 *
 * @author MartinSoftware
 */
public class VISTA_FormularioDocente extends javax.swing.JDialog {
    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Docente controlador;
    private MODELO_Docente docenteEditar; // null si es nuevo, con datos si es edición
    private boolean modoEdicion;
    /**
     * Constructor para nuevo docente o editar docente
     * @param parent ventana padre
     * @param modal si es modal o no
     * @param docente objeto docente (null para nuevo, con datos para editar)
     * @param usuario usuario logueado
     */
    public VISTA_FormularioDocente(java.awt.Frame parent, boolean modal, 
                                   MODELO_Docente docente, MODELO_Usuario usuario) {
        super(parent, modal);
        initComponents();
        
        this.usuarioLogueado = usuario;
        this.docenteEditar = docente;
        this.controlador = new CONTROLADOR_Docente();
        this.modoEdicion = (docente != null);
        
        configurarCampos();
        configurarVentana();
        if (modoEdicion) {
            cargarDatosDocente();
        }
    }
    
    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        if (modoEdicion) {
            this.setTitle("Editar Docente");
            jLabel2.setText("EDITAR DOCENTE");  // ✅ CORRECTO
            btnGuardar.setText("GUARDAR CAMBIOS");
        } else {
            this.setTitle("Nuevo Docente");
            jLabel2.setText("AGREGAR NUEVO DOCENTE");  // ✅ CORRECTO
        }
    }
    
    /**
     * Configurar campos del formulario
     */
    private void configurarCampos() {
        // Configurar ComboBox Tipo Documento
        cboTipoDocumento.removeAllItems();
        cboTipoDocumento.addItem("DNI");
        cboTipoDocumento.addItem("CE");
        cboTipoDocumento.addItem("PASAPORTE");
        cboTipoDocumento.setSelectedIndex(0);
        
        // Configurar ComboBox Estado
        cboEstado.removeAllItems();
        cboEstado.addItem("ACTIVO");
        cboEstado.addItem("INACTIVO");
        cboEstado.setSelectedIndex(0);
        
        // Limitar caracteres en teléfono y solo números
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || txtTelefono.getText().length() >= 9) {
                    evt.consume();
                }
            }
        });
        
        // Enfocar en el primer campo
        txtNumeroDocumento.requestFocus();
    }
    
    /**
     * Cargar datos del docente en modo edición
     */
    private void cargarDatosDocente() {
        if (docenteEditar == null) return;
        
        cboTipoDocumento.setSelectedItem(docenteEditar.getTipoDocumento());
        txtNumeroDocumento.setText(docenteEditar.getNumeroDocumento());
        txtNombres.setText(docenteEditar.getNombres());
        txtApellidoPaterno.setText(docenteEditar.getApellidoPaterno());
        txtApellidoMaterno.setText(docenteEditar.getApellidoMaterno());
        txtTelefono.setText(docenteEditar.getTelefono());
        txtEmail.setText(docenteEditar.getEmail() != null ? docenteEditar.getEmail() : "");
        txtEspecialidad.setText(docenteEditar.getEspecialidad());
        cboEstado.setSelectedItem(docenteEditar.getEstado());
        
        System.out.println("✓ Datos cargados para edición");
    }
    
    /**
     * Obtener datos del formulario y crear objeto Docente
     */
    private MODELO_Docente obtenerDatosFormulario() {
        MODELO_Docente docente = new MODELO_Docente();
        
        // Si es edición, mantener el ID
        if (modoEdicion) {
            docente.setIdDocente(docenteEditar.getIdDocente());
        }
        
        docente.setTipoDocumento(cboTipoDocumento.getSelectedItem().toString());
        docente.setNumeroDocumento(txtNumeroDocumento.getText().trim());
        docente.setNombres(txtNombres.getText().trim());
        docente.setApellidoPaterno(txtApellidoPaterno.getText().trim());
        docente.setApellidoMaterno(txtApellidoMaterno.getText().trim());
        docente.setTelefono(txtTelefono.getText().trim());
        docente.setEmail(txtEmail.getText().trim());
        docente.setEspecialidad(txtEspecialidad.getText().trim());
        docente.setEstado(cboEstado.getSelectedItem().toString());
        
        return docente;
    }
    
    /**
     * Limpiar todos los campos del formulario
     */
    private void limpiarCampos() {
        cboTipoDocumento.setSelectedIndex(0);
        txtNumeroDocumento.setText("");
        txtNombres.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtEspecialidad.setText("");
        cboEstado.setSelectedIndex(0);
        txtNumeroDocumento.requestFocus();
    }
    
    /**
     * Validar campos obligatorios antes de guardar
     */
    private boolean validarCamposObligatorios() {
        if (txtNumeroDocumento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El número de documento es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtNumeroDocumento.requestFocus();
            return false;
        }
        
        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Los nombres son obligatorios", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        
        if (txtApellidoPaterno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El apellido paterno es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtApellidoPaterno.requestFocus();
            return false;
        }
        
        if (txtApellidoMaterno.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El apellido materno es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtApellidoMaterno.requestFocus();
            return false;
        }
        
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El teléfono es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }
        
        if (txtEspecialidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "La especialidad es obligatoria", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtEspecialidad.requestFocus();
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
        cboTipoDocumento = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtNumeroDocumento = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtNombres = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        txtApellidoPaterno = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        txtApellidoMaterno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        txtEspecialidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("AGREGAR NUEVO DOCENTE");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 440, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asociación Privada de Patronato");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 440, 20));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("N° DE DOCUMENTO:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 170, 50));

        cboTipoDocumento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "CE", "PASAPORTE" }));
        jPanel1.add(cboTipoDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 190, 30));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("TIPO DE DOCUMENTO:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 170, 30));

        txtNumeroDocumento.setBackground(new java.awt.Color(255, 255, 255));
        txtNumeroDocumento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNumeroDocumento.setForeground(new java.awt.Color(0, 0, 0));
        txtNumeroDocumento.setBorder(null);
        jPanel1.add(txtNumeroDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 200, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 200, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 200, 10));

        txtNombres.setBackground(new java.awt.Color(255, 255, 255));
        txtNombres.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombres.setForeground(new java.awt.Color(0, 0, 0));
        txtNombres.setBorder(null);
        jPanel1.add(txtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 200, 20));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("NOMBRES:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 170, 50));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 200, 10));

        txtApellidoPaterno.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidoPaterno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtApellidoPaterno.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidoPaterno.setBorder(null);
        jPanel1.add(txtApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 200, 20));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("APELLIDO PATERNO:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 170, 50));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 200, 10));

        txtApellidoMaterno.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidoMaterno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtApellidoMaterno.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidoMaterno.setBorder(null);
        jPanel1.add(txtApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 200, 20));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("APELLIDO MATERNO:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 170, 50));

        txtTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefono.setBorder(null);
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
        });
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 200, 20));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("N° TELÉFONO/CELULAR:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 170, 50));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, 200, 10));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setBorder(null);
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 200, 20));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("EMAIL:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 170, 50));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 200, 10));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("ESPECIALIDAD:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 170, 50));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 490, 200, 10));

        txtEspecialidad.setBackground(new java.awt.Color(255, 255, 255));
        txtEspecialidad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEspecialidad.setForeground(new java.awt.Color(0, 0, 0));
        txtEspecialidad.setBorder(null);
        jPanel1.add(txtEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 200, 20));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("ESTADO");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 170, 50));

        cboEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO" }));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 520, 200, 30));

        btnCancelar.setBackground(new java.awt.Color(231, 76, 60));
        btnCancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 580, 180, 40));

        btnGuardar.setBackground(new java.awt.Color(39, 174, 96));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("AGREGAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 180, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyPressed

    // ========================================
    // EVENTOS DE LOS BOTONES
    // ========================================
    /**
     * Botón Guardar
     */
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        // Validar campos obligatorios
        if (!validarCamposObligatorios()) {
            return;
        }
        
        // Obtener datos del formulario
        MODELO_Docente docente = obtenerDatosFormulario();
        
        String resultado;
        
        if (modoEdicion) {
            // MODO EDICIÓN
            resultado = controlador.actualizarDocente(docente);
            
            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Docente actualizado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose(); // Cerrar el formulario
            } else {
                JOptionPane.showMessageDialog(this, 
                    resultado, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            // MODO NUEVO
            resultado = controlador.registrarDocente(docente);
            
            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Docente registrado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Preguntar si desea registrar otro
                int opcion = JOptionPane.showConfirmDialog(this, 
                    "¿Desea registrar otro docente?", 
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

    /**
     * Botón Cancelar
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        // Confirmar cancelación si hay datos en el formulario
        boolean hayDatos = !txtNumeroDocumento.getText().trim().isEmpty() ||
                          !txtNombres.getText().trim().isEmpty() ||
                          !txtApellidoPaterno.getText().trim().isEmpty();
        
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboTipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
