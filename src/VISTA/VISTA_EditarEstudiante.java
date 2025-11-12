/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VISTA;

import CONTROLADOR.CONTROLADOR_Estudiante;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Usuario;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.Calendar;
/**
 *
 * @author MartinSoftware
 */
public class VISTA_EditarEstudiante extends javax.swing.JDialog {

    /**
     * Creates new form VISTA_EditarEstudiante
     */
    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Estudiante controlador;
    private MODELO_Estudiante estudianteEditar;
    
    /**
     * Constructor
     * @param parent ventana padre
     * @param modal si es modal
     * @param estudiante estudiante a editar
     * @param usuario usuario logueado
     */
    public VISTA_EditarEstudiante(java.awt.Frame parent, boolean modal, 
                                  MODELO_Estudiante estudiante, MODELO_Usuario usuario) {
        super(parent, modal);
        initComponents();
        
        this.usuarioLogueado = usuario;
        this.controlador = new CONTROLADOR_Estudiante();
        this.estudianteEditar = estudiante;
        
        configurarVentana();
        configurarCampos();
        cargarDatosEstudiante();
    }
    
    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Editar Datos de Estudiante");
    }
    
    /**
     * Configurar campos del formulario
     */
    private void configurarCampos() {
        // Campos no editables
        txtCodigoEstudiantil.setEditable(false);
        txtCodigoEstudiantil.setEnabled(false);
        txtNumeroDocumento.setEditable(false);
        txtNumeroDocumento.setEnabled(false);
        
        // Configurar ComboBox Tipo Documento
        cboTipoDocumento.removeAllItems();
        cboTipoDocumento.addItem("DNI");
        cboTipoDocumento.addItem("CE");
        cboTipoDocumento.addItem("PASAPORTE");
        
        // Configurar ComboBox Sexo
        cboSexo.removeAllItems();
        cboSexo.addItem("M");
        cboSexo.addItem("F");
        
        // Configurar ComboBox Estado
        cboEstado.removeAllItems();
        cboEstado.addItem("ACTIVO");
        cboEstado.addItem("INACTIVO");
        cboEstado.addItem("RETIRADO");
        
        // Limitar caracteres en teléfono y solo números
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || txtTelefono.getText().length() >= 9) {
                    evt.consume();
                }
            }
        });
        
        // Configurar JDateChooser
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -15);
        jdcFechaNacimiento.setMaxSelectableDate(cal.getTime());
        
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -100);
        jdcFechaNacimiento.setMinSelectableDate(cal.getTime());
        
        // Listener para calcular edad automáticamente
        jdcFechaNacimiento.addPropertyChangeListener("date", e -> calcularYMostrarEdad());
    }
    
    /**
     * Cargar datos del estudiante
     */
    private void cargarDatosEstudiante() {
        if (estudianteEditar == null) return;
        
        txtCodigoEstudiantil.setText(estudianteEditar.getCodigoEstudiantil());
        cboTipoDocumento.setSelectedItem(estudianteEditar.getTipoDocumento());
        txtNumeroDocumento.setText(estudianteEditar.getNumeroDocumento());
        txtNombres.setText(estudianteEditar.getNombres());
        txtApellidoPaterno.setText(estudianteEditar.getApellidoPaterno());
        txtApellidoMaterno.setText(estudianteEditar.getApellidoMaterno());
        jdcFechaNacimiento.setDate(estudianteEditar.getFechaNacimiento());
        cboSexo.setSelectedItem(estudianteEditar.getSexo());
        txtTelefono.setText(estudianteEditar.getTelefono());
        txtEmail.setText(estudianteEditar.getEmail() != null ? estudianteEditar.getEmail() : "");
        txtDireccion.setText(estudianteEditar.getDireccion() != null ? estudianteEditar.getDireccion() : "");
        cboEstado.setSelectedItem(estudianteEditar.getEstado());
        
        calcularYMostrarEdad();
        
        System.out.println("✓ Datos del estudiante cargados para edición");
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
        
        if (edad < 15) {
            lblEdad.setForeground(new java.awt.Color(231, 76, 60)); // Rojo
        } else {
            lblEdad.setForeground(new java.awt.Color(39, 174, 96)); // Verde
        }
    }
    
    /**
     * Obtener datos del formulario y actualizar objeto Estudiante
     */
    private MODELO_Estudiante obtenerDatosFormulario() {
        MODELO_Estudiante estudiante = new MODELO_Estudiante();
        
        // Mantener ID y campos no editables
        estudiante.setIdEstudiante(estudianteEditar.getIdEstudiante());
        estudiante.setCodigoEstudiantil(estudianteEditar.getCodigoEstudiantil());
        estudiante.setNumeroDocumento(estudianteEditar.getNumeroDocumento());
        estudiante.setFechaRegistro(estudianteEditar.getFechaRegistro());
        
        // Campos editables
        estudiante.setTipoDocumento(cboTipoDocumento.getSelectedItem().toString());
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
        estudiante.setEstado(cboEstado.getSelectedItem().toString());
        
        return estudiante;
    }
    
    /**
     * Validar campos obligatorios
     */
    private boolean validarCamposObligatorios() {
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
        jLabel15 = new javax.swing.JLabel();
        txtCodigoEstudiantil = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();

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
        jLabel1.setText("CODIGO ESTUDIANTIL:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 170, 40));

        cboTipoDocumento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "CE", "PASAPORTE" }));
        jPanel1.add(cboTipoDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 190, 30));

        txtNumeroDocumento.setBackground(new java.awt.Color(255, 255, 255));
        txtNumeroDocumento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNumeroDocumento.setForeground(new java.awt.Color(0, 0, 0));
        txtNumeroDocumento.setBorder(null);
        jPanel1.add(txtNumeroDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 200, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 200, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 200, 10));

        txtNombres.setBackground(new java.awt.Color(255, 255, 255));
        txtNombres.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombres.setForeground(new java.awt.Color(0, 0, 0));
        txtNombres.setBorder(null);
        jPanel1.add(txtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 200, 20));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("NOMBRES:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 170, 50));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 200, 10));

        txtApellidoPaterno.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidoPaterno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtApellidoPaterno.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidoPaterno.setBorder(null);
        jPanel1.add(txtApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 200, 20));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("APELLIDO PATERNO:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 170, 50));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 200, 10));

        txtApellidoMaterno.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidoMaterno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtApellidoMaterno.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidoMaterno.setBorder(null);
        jPanel1.add(txtApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 200, 20));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("APELLIDO MATERNO:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 170, 50));

        txtTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefono.setBorder(null);
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyPressed(evt);
            }
        });
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 480, 200, 20));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("N° TELÉFONO/CELULAR:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 170, 30));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 200, 10));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setBorder(null);
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 200, 20));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("EMAIL (OPCIONAL):");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 170, 30));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 200, 10));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("DIRECCIÓN: ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, 170, 30));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 590, 200, 10));

        txtDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(0, 0, 0));
        txtDireccion.setBorder(null);
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 560, 200, 20));

        btnCancelar.setBackground(new java.awt.Color(231, 76, 60));
        btnCancelar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 640, 180, 40));

        btnGuardar.setBackground(new java.awt.Color(39, 174, 96));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 180, 40));
        jPanel1.add(jdcFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 200, -1));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("EDAD:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 170, 50));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("FECHA DE NACIMIENTO");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 170, 50));

        lblEdad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEdad.setForeground(new java.awt.Color(0, 0, 0));
        lblEdad.setText("[EDAD CALCULADA]");
        jPanel1.add(lblEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 190, -1));

        jLabel13.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("TIPO DE DOCUMENTO:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 170, 30));

        jLabel14.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("SEXO:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 170, 30));

        cboSexo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        jPanel1.add(cboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 190, 30));

        jLabel15.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("N° DE DOCUMENTO:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 170, 50));

        txtCodigoEstudiantil.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoEstudiantil.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCodigoEstudiantil.setForeground(new java.awt.Color(0, 0, 0));
        txtCodigoEstudiantil.setBorder(null);
        jPanel1.add(txtCodigoEstudiantil, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 200, 20));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 200, 10));

        jLabel16.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("ESTADO:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 600, 170, 30));

        cboEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO", "RETIRADO" }));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 600, 190, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea cancelar?\nSe perderán los cambios no guardados", 
            "Confirmar Cancelación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
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
        
        // Actualizar estudiante
        String resultado = controlador.actualizarEstudiante(estudiante);
        
        if ("EXITO".equals(resultado)) {
            JOptionPane.showMessageDialog(this, 
                "Datos del estudiante actualizados correctamente\n\n" +
                "Código: " + estudiante.getCodigoEstudiantil() + "\n" +
                "Nombre: " + controlador.obtenerNombreCompleto(estudiante), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                resultado, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboSexo;
    private javax.swing.JComboBox<String> cboTipoDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JSeparator jSeparator8;
    private com.toedter.calendar.JDateChooser jdcFechaNacimiento;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtCodigoEstudiantil;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
