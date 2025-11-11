/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VISTA;

import CONTROLADOR.CONTROLADOR_Curso;
import MODELO.MODELO_Curso;
import MODELO.MODELO_Docente;
import MODELO.MODELO_Horario;
import MODELO.MODELO_Usuario;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import java.sql.Date;
import java.util.List;
import java.util.Calendar;
import com.toedter.calendar.JDateChooser;
/**
 *
 * @author MartinSoftware
 */
public class VISTA_FormularioCurso extends javax.swing.JDialog {

    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Curso controlador;
    private MODELO_Curso cursoEditar;
    private boolean modoEdicion;
    /**
     * Creates new form VISTA_FormularioCurso
     */
    public VISTA_FormularioCurso(java.awt.Frame parent, boolean modal, 
                                 MODELO_Curso curso, MODELO_Usuario usuario) {
        super(parent, modal);
        initComponents();
        
        this.usuarioLogueado = usuario;
        this.cursoEditar = curso;
        this.controlador = new CONTROLADOR_Curso();
        this.modoEdicion = (curso != null);
        
        configurarVentana();
        configurarCampos();
        cargarComboBoxes();
        
        if (modoEdicion) {
            cargarDatosCurso();
        }
    }
    
    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        if (modoEdicion) {
            this.setTitle("Editar Curso");
        } else {
            this.setTitle("Nuevo Curso");
        }
    }
    
    /**
     * Configurar campos del formulario
     */
    private void configurarCampos() {
        // Configurar ComboBox Nivel
        cboNivel.removeAllItems();
        cboNivel.addItem("BASICO");
        cboNivel.addItem("INTERMEDIO");
        cboNivel.addItem("AVANZADO");
        cboNivel.setSelectedIndex(0);
        
        // Configurar ComboBox Estado
        cboEstado.removeAllItems();
        cboEstado.addItem("ACTIVO");
        cboEstado.addItem("INACTIVO");
        cboEstado.addItem("FINALIZADO");
        cboEstado.setSelectedIndex(0);
        
        // Configurar Spinner de Duración (1-24 meses)
        SpinnerNumberModel modeloDuracion = new SpinnerNumberModel(3, 1, 24, 1);
        spnDuracionMeses.setModel(modeloDuracion);
        
        // Configurar Spinner de Cupos (1-50)
        SpinnerNumberModel modeloCupos = new SpinnerNumberModel(10, 1, 50, 1);
        spnCupos.setModel(modeloCupos);
        
        // Configurar JDateChooser - Fecha mínima hoy
        jdcFechaInicio.setMinSelectableDate(new java.util.Date());
        
        // Listener para calcular fecha fin automáticamente
        spnDuracionMeses.addChangeListener(e -> calcularFechaFinAutomatica());
        jdcFechaInicio.addPropertyChangeListener("date", e -> calcularFechaFinAutomatica());
        
        // Enfocar en el primer campo
        txtNombreCurso.requestFocus();
    }
    
    /**
     * Cargar ComboBoxes con datos de la BD
     */
    private void cargarComboBoxes() {
        // Cargar Docentes
        cboDocente.removeAllItems();
        List<MODELO_Docente> docentes = controlador.listarDocentesActivos();
        
        if (docentes.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay docentes activos disponibles.\nDebe registrar docentes primero.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            for (MODELO_Docente docente : docentes) {
                String item = docente.getNombres() + " " + 
                             docente.getApellidoPaterno() + " " + 
                             docente.getApellidoMaterno() + 
                             " - " + docente.getEspecialidad();
                cboDocente.addItem(item);
                // Guardar el ID en una estructura paralela o usar un objeto personalizado
            }
        }
        
        // Cargar Horarios
        cboHorario.removeAllItems();
        List<MODELO_Horario> horarios = controlador.listarTodosHorarios();
        System.out.println("xddd");
        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay horarios disponibles.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            
            for (MODELO_Horario horario : horarios) {
                String item = horario.getDiaSemana() + " " + 
                             horario.getHoraInicio().toString().substring(0, 5) + "-" + 
                             horario.getHoraFin().toString().substring(0, 5) + 
                             " (" + horario.getTurno() + ")";
                cboHorario.addItem(item);
            }
        }
    }
    
    /**
     * Cargar datos del curso en modo edición
     */
    private void cargarDatosCurso() {
        if (cursoEditar == null) return;
        
        txtNombreCurso.setText(cursoEditar.getNombreCurso());
        cboNivel.setSelectedItem(cursoEditar.getNivel());
        txtDescripcion.setText(cursoEditar.getDescripcion() != null ? cursoEditar.getDescripcion() : "");
        spnDuracionMeses.setValue(cursoEditar.getDuracionMeses());
        spnCupos.setValue(cursoEditar.getCuposDisponibles());
        
        // Seleccionar docente
        List<MODELO_Docente> docentes = controlador.listarDocentesActivos();
        for (int i = 0; i < docentes.size(); i++) {
            if (docentes.get(i).getIdDocente() == cursoEditar.getIdDocente()) {
                cboDocente.setSelectedIndex(i);
                break;
            }
        }
        
        // Seleccionar horario
        List<MODELO_Horario> horarios = controlador.listarTodosHorarios();
        for (int i = 0; i < horarios.size(); i++) {
            if (horarios.get(i).getIdHorario() == cursoEditar.getIdHorario()) {
                cboHorario.setSelectedIndex(i);
                break;
            }
        }
        
        // Configurar fechas
        jdcFechaInicio.setDate(cursoEditar.getFechaInicio());
        jdcFechaFin.setDate(cursoEditar.getFechaFin());
        
        cboEstado.setSelectedItem(cursoEditar.getEstado());
        
        System.out.println("✓ Datos cargados para edición");
    }
    
    /**
     * Calcular fecha fin automáticamente según duración
     */
    private void calcularFechaFinAutomatica() {
        java.util.Date fechaInicio = jdcFechaInicio.getDate();
        int duracionMeses = (int) spnDuracionMeses.getValue();
        
        if (fechaInicio != null && duracionMeses > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicio);
            calendar.add(Calendar.MONTH, duracionMeses);
            
            jdcFechaFin.setDate(calendar.getTime());
        }
    }
    
    /**
     * Obtener ID del docente seleccionado
     */
    private int obtenerIdDocenteSeleccionado() {
        int index = cboDocente.getSelectedIndex();
        if (index == -1) return 0;
        
        List<MODELO_Docente> docentes = controlador.listarDocentesActivos();
        if (index < docentes.size()) {
            return docentes.get(index).getIdDocente();
        }
        
        return 0;
    }
    
    /**
     * Obtener ID del horario seleccionado
     */
    private int obtenerIdHorarioSeleccionado() {
        int index = cboHorario.getSelectedIndex();
        if (index == -1) return 0;
        
        List<MODELO_Horario> horarios = controlador.listarTodosHorarios();
        if (index < horarios.size()) {
            return horarios.get(index).getIdHorario();
        }
        
        return 0;
    }
    
    /**
     * Obtener datos del formulario y crear objeto Curso
     */
    private MODELO_Curso obtenerDatosFormulario() {
        MODELO_Curso curso = new MODELO_Curso();
        
        // Si es edición, mantener el ID
        if (modoEdicion) {
            curso.setIdCurso(cursoEditar.getIdCurso());
        }
        
        curso.setNombreCurso(txtNombreCurso.getText().trim());
        curso.setNivel(cboNivel.getSelectedItem().toString());
        curso.setDescripcion(txtDescripcion.getText().trim());
        curso.setDuracionMeses((int) spnDuracionMeses.getValue());
        curso.setCuposDisponibles((int) spnCupos.getValue());
        curso.setIdDocente(obtenerIdDocenteSeleccionado());
        curso.setIdHorario(obtenerIdHorarioSeleccionado());
        
        // Convertir java.util.Date a java.sql.Date
        if (jdcFechaInicio.getDate() != null) {
            curso.setFechaInicio(new Date(jdcFechaInicio.getDate().getTime()));
        }
        if (jdcFechaFin.getDate() != null) {
            curso.setFechaFin(new Date(jdcFechaFin.getDate().getTime()));
        }
        
        curso.setEstado(cboEstado.getSelectedItem().toString());
        
        return curso;
    }
    
    /**
     * Limpiar todos los campos del formulario
     */
    private void limpiarCampos() {
        txtNombreCurso.setText("");
        cboNivel.setSelectedIndex(0);
        txtDescripcion.setText("");
        spnDuracionMeses.setValue(3);
        spnCupos.setValue(10);
        cboDocente.setSelectedIndex(0);
        cboHorario.setSelectedIndex(0);
        jdcFechaInicio.setDate(null);
        jdcFechaFin.setDate(null);
        cboEstado.setSelectedIndex(0);
        txtNombreCurso.requestFocus();
    }
    
    /**
     * Validar campos obligatorios antes de guardar
     */
    private boolean validarCamposObligatorios() {
        if (txtNombreCurso.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El nombre del curso es obligatorio", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtNombreCurso.requestFocus();
            return false;
        }
        
        if (cboDocente.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un docente", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            cboDocente.requestFocus();
            return false;
        }
        
        if (cboHorario.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un horario", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            cboHorario.requestFocus();
            return false;
        }
        
        if (jdcFechaInicio.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "La fecha de inicio es obligatoria", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            jdcFechaInicio.requestFocus();
            return false;
        }
        
        if (jdcFechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "La fecha de fin es obligatoria", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            jdcFechaFin.requestFocus();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreCurso = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        cboNivel = new javax.swing.JComboBox<>();
        txtDescripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        spnDuracionMeses = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        spnCupos = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboDocente = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboHorario = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jdcFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jdcFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asociación Privada de Patronato");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 440, 20));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("AGREGAR NUEVO CURSO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 440, 30));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Nivel del Curso:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 120, 50));

        txtNombreCurso.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreCurso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombreCurso.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreCurso.setBorder(null);
        jPanel1.add(txtNombreCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 250, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 250, 10));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Nombre del Curso:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 150, 50));

        cboNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BÁSICO", "INTERMEDIO", "AVANZADO" }));
        jPanel1.add(cboNivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 220, -1));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(0, 0, 0));
        txtDescripcion.setBorder(null);
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 250, 20));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("meses");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 90, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 250, 10));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Fecha de Inicio:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 120, 50));
        jPanel1.add(spnDuracionMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 90, -1));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Duracción:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 90, 50));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Vacantes:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 90, 50));
        jPanel1.add(spnCupos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 90, -1));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("cupos");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 90, 40));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Docente:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 120, 50));

        jPanel1.add(cboDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 220, -1));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Horario:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 120, 50));

        jPanel1.add(cboHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 220, -1));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Descripción:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 150, 50));
        jPanel1.add(jdcFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 220, -1));

        jLabel13.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Fecha de Fin:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 120, 50));
        jPanel1.add(jdcFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 220, -1));

        jLabel14.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Estado;");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 110, 50));

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVO", "INACTIVO", "FINALIZADO" }));
        jPanel1.add(cboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 580, 220, -1));

        btnGuardar.setBackground(new java.awt.Color(39, 174, 96));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("AGREGAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 180, 40));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Validar campos obligatorios
        if (!validarCamposObligatorios()) {
            return;
        }
        
        // Obtener datos del formulario
        MODELO_Curso curso = obtenerDatosFormulario();
        
        String resultado;
        
        if (modoEdicion) {
            // MODO EDICIÓN
            resultado = controlador.actualizarCurso(curso);
            
            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Curso actualizado correctamente", 
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
            resultado = controlador.registrarCurso(curso);
            
            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Curso registrado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Preguntar si desea registrar otro
                int opcion = JOptionPane.showConfirmDialog(this, 
                    "¿Desea registrar otro curso?", 
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
        // Confirmar cancelación si hay datos en el formulario
        boolean hayDatos = !txtNombreCurso.getText().trim().isEmpty() ||
                          jdcFechaInicio.getDate() != null;
        
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
    private javax.swing.JComboBox<String> cboDocente;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JComboBox<String> cboHorario;
    private javax.swing.JComboBox<String> cboNivel;
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
    private com.toedter.calendar.JDateChooser jdcFechaFin;
    private com.toedter.calendar.JDateChooser jdcFechaInicio;
    private javax.swing.JSpinner spnCupos;
    private javax.swing.JSpinner spnDuracionMeses;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombreCurso;
    // End of variables declaration//GEN-END:variables
}
