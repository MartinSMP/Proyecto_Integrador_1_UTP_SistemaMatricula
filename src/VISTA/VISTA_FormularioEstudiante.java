/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VISTA;
import CONTROLADOR.CONTROLADOR_Matricula;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Usuario;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import java.sql.Date;
import java.util.Calendar;
/**
 *
 * @author MartinSoftware
 */
public class VISTA_FormularioEstudiante extends javax.swing.JDialog {
    
    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Matricula controlador;
    private String dniPrellenado;
    /**
     * Creates new form VISTA_FormularioEstudiante
     */
    public VISTA_FormularioEstudiante(java.awt.Frame parent, boolean modal, 
                                     String dni, MODELO_Usuario usuario) {
        super(parent, modal);
        initComponents();
        
        this.usuarioLogueado = usuario;
        this.controlador = new CONTROLADOR_Matricula();
        this.dniPrellenado = dni;
        
        configurarVentana();
        configurarCampos();
        
        if (dniPrellenado != null && !dniPrellenado.isEmpty()) {
            txtNumeroDocumento.setText(dniPrellenado);
        }
    }
    
    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Registrar Nuevo Estudiante");
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
        
        // Configurar ComboBox Sexo
        cboSexo.removeAllItems();
        cboSexo.addItem("M");
        cboSexo.addItem("F");
        cboSexo.setSelectedIndex(0);
        
        // Limitar caracteres en teléfono y solo números
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || txtTelefono.getText().length() >= 9) {
                    evt.consume();
                }
            }
        });
        
        // Configurar JDateChooser - Fecha máxima: hace 15 años
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -15);
        jdcFechaNacimiento.setMaxSelectableDate(cal.getTime());
        
        // Fecha mínima: hace 100 años
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -100);
        jdcFechaNacimiento.setMinSelectableDate(cal.getTime());
        
        // Listener para calcular edad automáticamente
        jdcFechaNacimiento.addPropertyChangeListener("date", e -> calcularYMostrarEdad());
        
        // Inicializar label de edad
        lblEdad.setText("Edad: -");
        
        // Enfocar en primer campo
        if (dniPrellenado == null || dniPrellenado.isEmpty()) {
            txtNumeroDocumento.requestFocus();
        } else {
            txtNombres.requestFocus();
        }
    }
    
    /**
     * Calcular y mostrar edad automáticamente
     */
    private void calcularYMostrarEdad() {
        java.util.Date fechaNac = jdcFechaNacimiento.getDate();
        
        if (fechaNac == null) {
            lblEdad.setText("Edad: -");
            return;
        }
        
        Date sqlDate = new Date(fechaNac.getTime());
        int edad = controlador.calcularEdad(sqlDate);
        
        lblEdad.setText("Edad: " + edad + " años");
        
        // Cambiar color según validez
        if (edad < 15) {
            lblEdad.setForeground(new java.awt.Color(231, 76, 60)); // Rojo
        } else {
            lblEdad.setForeground(new java.awt.Color(39, 174, 96)); // Verde
        }
    }
    
    /**
     * Obtener datos del formulario y crear objeto Estudiante
     */
    private MODELO_Estudiante obtenerDatosFormulario() {
        MODELO_Estudiante estudiante = new MODELO_Estudiante();
        
        estudiante.setTipoDocumento(cboTipoDocumento.getSelectedItem().toString());
        estudiante.setNumeroDocumento(txtNumeroDocumento.getText().trim());
        estudiante.setNombres(txtNombres.getText().trim());
        estudiante.setApellidoPaterno(txtApellidoPaterno.getText().trim());
        estudiante.setApellidoMaterno(txtApellidoMaterno.getText().trim());
        
        if (jdcFechaNacimiento.getDate() != null) {
            Date fechaNac = new Date(jdcFechaNacimiento.getDate().getTime());
            estudiante.setFechaNacimiento(fechaNac);
            estudiante.setEdad(controlador.calcularEdad(fechaNac));
        }
        
        estudiante.setSexo(cboSexo.getSelectedItem().toString());
        estudiante.setTelefono(txtTelefono.getText().trim());
        estudiante.setEmail(txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
        estudiante.setDireccion(txtDireccion.getText().trim().isEmpty() ? null : txtDireccion.getText().trim());
        
        return estudiante;
    }
    
    /**
     * Limpiar todos los campos
     */
    private void limpiarCampos() {
        cboTipoDocumento.setSelectedIndex(0);
        txtNumeroDocumento.setText("");
        txtNombres.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        jdcFechaNacimiento.setDate(null);
        lblEdad.setText("Edad: -");
        cboSexo.setSelectedIndex(0);
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtNumeroDocumento.requestFocus();
    }
    
    /**
     * Validar campos obligatorios
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
        
        if (jdcFechaNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "La fecha de nacimiento es obligatoria", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            jdcFechaNacimiento.requestFocus();
            return false;
        }
        
        // Validar edad
        Date fechaNac = new Date(jdcFechaNacimiento.getDate().getTime());
        int edad = controlador.calcularEdad(fechaNac);
        
        if (edad < 15) {
            JOptionPane.showMessageDialog(this, 
                "El estudiante debe tener al menos 15 años", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            jdcFechaNacimiento.requestFocus();
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
        
        if (!txtTelefono.getText().trim().matches("\\d{9}")) {
            JOptionPane.showMessageDialog(this, 
                "El teléfono debe tener 9 dígitos", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
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
        txtDireccion = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jdcFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cboSexo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("AGREGAR NUEVO ESTUDIANTE");
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
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 200, 20));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("N° TELÉFONO/CELULAR:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 170, 30));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 480, 200, 10));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setBorder(null);
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 510, 200, 20));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("EMAIL (OPCIONAL):");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 170, 30));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 200, 10));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("DIRECCIÓN: ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 170, 30));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 570, 200, 10));

        txtDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(0, 0, 0));
        txtDireccion.setBorder(null);
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 540, 200, 20));

        btnCancelar.setBackground(new java.awt.Color(231, 76, 60));
        btnCancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 600, 180, 40));

        btnGuardar.setBackground(new java.awt.Color(39, 174, 96));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 600, 180, 40));
        jPanel1.add(jdcFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 200, -1));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("EDAD:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 170, 50));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("FECHA DE NACIMIENTO");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 170, 50));

        lblEdad.setForeground(new java.awt.Color(0, 0, 0));
        lblEdad.setText("[EDAD CALCULADA]");
        jPanel1.add(lblEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 190, -1));

        jLabel13.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("TIPO DE DOCUMENTO:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 170, 30));

        jLabel14.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("SEXO:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 170, 30));

        cboSexo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        jPanel1.add(cboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 190, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        boolean hayDatos = !txtNumeroDocumento.getText().trim().isEmpty() ||
                          !txtNombres.getText().trim().isEmpty();
        
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Validar campos obligatorios
        if (!validarCamposObligatorios()) {
            return;
        }
        
        // Obtener datos del formulario
        MODELO_Estudiante estudiante = obtenerDatosFormulario();
        
        // Registrar estudiante
        int idGenerado = controlador.registrarEstudiante(estudiante);
        
        if (idGenerado > 0) {
            JOptionPane.showMessageDialog(this, 
                "Estudiante registrado exitosamente\n\n" +
                "Nombre: " + controlador.obtenerNombreCompletoEstudiante(estudiante) + "\n" +
                "DNI: " + estudiante.getNumeroDocumento() + "\n" +
                "Código: " + estudiante.getCodigoEstudiantil(), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Cerrar formulario
            this.dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el estudiante.\n" +
                "Verifique que el DNI no esté duplicado.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyPressed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboSexo;
    private javax.swing.JComboBox<String> cboTipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private com.toedter.calendar.JDateChooser jdcFechaNacimiento;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
