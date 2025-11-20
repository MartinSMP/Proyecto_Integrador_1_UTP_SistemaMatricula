/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VISTA;
import CONTROLADOR.CONTROLADOR_Matricula;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Curso;
import MODELO.MODELO_Matricula;
import MODELO.MODELO_Usuario;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.util.List;

/**
 * @author MartinSoftware
 */
public class VISTA_Matricula extends javax.swing.JFrame {
    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Matricula controlador;
    private MODELO_Estudiante estudianteSeleccionado;
    private List<MODELO_Curso> listaCursos;
    /**
     * Creates new form VISTA_Matricula
     */
    public VISTA_Matricula(MODELO_Usuario usuario) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.controlador = new CONTROLADOR_Matricula();
        this.estudianteSeleccionado = null;
        configurarVentana();
        configurarCampos();
        cargarCursos();
    }
    
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setTitle("Registro de Matrícula");
        btnMatricular.setEnabled(false);
    }
    
    /**
     * Configurar campos del formulario
     */
    private void configurarCampos() {
        // Campos de estudiante en solo lectura
        txtCodigoEstudiante.setEditable(false);
        txtNombresEstudiante.setEditable(false);
        txtApellidosEstudiante.setEditable(false);
        
        // Configurar fecha de matrícula con fecha actual
        jdcFechaMatricula.setDate(new java.util.Date());
        
        // Deshabilitar botón de nuevo estudiante hasta buscar
        btnNuevoEstudiante.setEnabled(false);
        
        // Listener para el ComboBox de cursos
        cboCurso.addActionListener(e -> mostrarDetallesCurso());
    }
    
    /**
     * Cargar cursos activos en el ComboBox
     */
    private void cargarCursos() {
        cboCurso.removeAllItems();
        listaCursos = controlador.listarCursosActivos();
        
        if (listaCursos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay cursos activos disponibles.\nContacte al administrador.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            cboCurso.setEnabled(false);
        } else {
            cboCurso.addItem("-- Seleccione un curso --");
            for (MODELO_Curso curso : listaCursos) {
                String item = curso.getNombreCurso() + " - " + curso.getNivel();
                cboCurso.addItem(item);
            }
            cboCurso.setSelectedIndex(0);
        }
    }
    
    /**
     * Mostrar detalles del curso seleccionado
     */
    private void mostrarDetallesCurso() {
        int index = cboCurso.getSelectedIndex();
        
        if (index <= 0) {
            // No hay curso seleccionado
            lblNivel.setText("Nivel: -");
            lblDocente.setText("Docente: -");
            lblHorario.setText("Horario: -");
            lblCupos.setText("Cupos: -");
            verificarHabilitarBotonMatricular();
            return;
        }
        
        // Obtener el curso seleccionado (index - 1 porque el primer item es "Seleccione")
        MODELO_Curso cursoSeleccionado = listaCursos.get(index - 1);
        
        // Mostrar detalles
        lblNivel.setText("Nivel: " + cursoSeleccionado.getNivel());
        lblDocente.setText("Docente: " + cursoSeleccionado.getNombreDocente());
        lblHorario.setText("Horario: " + cursoSeleccionado.getHorarioTexto());
        lblCupos.setText("Cupos disponibles: " + cursoSeleccionado.getCuposDisponibles());
        
        // Verificar si tiene cupos
        if (cursoSeleccionado.getCuposDisponibles() <= 0) {
            lblCupos.setForeground(new java.awt.Color(231, 76, 60)); // Rojo
        } else {
            lblCupos.setForeground(new java.awt.Color(39, 174, 96)); // Verde
        }
        
        verificarHabilitarBotonMatricular();
    }
    
    /**
     * Verificar si se puede habilitar el botón de matricular
     */
    private void verificarHabilitarBotonMatricular() {
        boolean hayEstudiante = estudianteSeleccionado != null;
        boolean hayCurso = cboCurso.getSelectedIndex() > 0;
        
        btnMatricular.setEnabled(hayEstudiante && hayCurso);
    }
    
    /**
     * Limpiar campos de estudiante
     */
    private void limpiarCamposEstudiante() {
        txtDNI.setText("");
        txtCodigoEstudiante.setText("");
        txtNombresEstudiante.setText("");
        txtApellidosEstudiante.setText("");
        estudianteSeleccionado = null;
        btnNuevoEstudiante.setEnabled(false);
        verificarHabilitarBotonMatricular();
    }
    
    /**
     * Limpiar todo el formulario
     */
    private void limpiarFormularioCompleto() {
        limpiarCamposEstudiante();
        cboCurso.setSelectedIndex(0);
        jdcFechaMatricula.setDate(new java.util.Date());
        mostrarDetallesCurso();
    }
    
    /**
     * Abrir formulario de nuevo estudiante
     */
    private void abrirFormularioNuevoEstudiante(String dniPrellenado) {
        VISTA_FormularioEstudiante formulario = new VISTA_FormularioEstudiante(
            this, true, dniPrellenado, usuarioLogueado
        );
        formulario.setVisible(true);
        
        // Después de cerrar el formulario, buscar nuevamente
        if (!dniPrellenado.isEmpty()) {
            MODELO_Estudiante estudiante = controlador.buscarEstudiantePorDocumento(dniPrellenado);
            
            if (estudiante != null) {
                estudianteSeleccionado = estudiante;
                txtCodigoEstudiante.setText(estudiante.getCodigoEstudiantil());
                txtNombresEstudiante.setText(estudiante.getNombres());
                txtApellidosEstudiante.setText(estudiante.getApellidoPaterno() + " " + 
                                              estudiante.getApellidoMaterno());
                btnNuevoEstudiante.setEnabled(false);
                verificarHabilitarBotonMatricular();
            }
        }
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
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnBuscarEstudiante = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoEstudiante = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtNombresEstudiante = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtApellidosEstudiante = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        btnNuevoEstudiante = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        cboCurso = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        lblNivel = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        lblDocente = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        lblHorario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        lblCupos = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jdcFechaMatricula = new com.toedter.calendar.JDateChooser();
        btnMatricular = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion de Matrícula");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asociación Privada de Patronato");

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGISTRO DE MATRÍCULA");

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("DATOS DEL ESTUDIANTE");

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("CURSOS:");

        txtDNI.setBackground(new java.awt.Color(255, 255, 255));
        txtDNI.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDNI.setForeground(new java.awt.Color(0, 0, 0));
        txtDNI.setBorder(null);

        btnBuscarEstudiante.setBackground(new java.awt.Color(255, 204, 204));
        btnBuscarEstudiante.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscarEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscarEstudiante.setText("BUSCAR");
        btnBuscarEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEstudianteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("FECHA DE MATRÍCULA:");

        txtCodigoEstudiante.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoEstudiante.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCodigoEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        txtCodigoEstudiante.setBorder(null);

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("NOMBRES DEL ESTUDIANTE:");

        txtNombresEstudiante.setBackground(new java.awt.Color(255, 255, 255));
        txtNombresEstudiante.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombresEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        txtNombresEstudiante.setBorder(null);

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("APELLIDOS DEL ESTUDIANTE:");

        txtApellidosEstudiante.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidosEstudiante.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtApellidosEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidosEstudiante.setBorder(null);

        btnNuevoEstudiante.setBackground(new java.awt.Color(255, 204, 153));
        btnNuevoEstudiante.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNuevoEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevoEstudiante.setText("REGISTRAR NUEVO ESTUDIANTE");
        btnNuevoEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEstudianteActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("SELECCIÓN DEL CURSO");

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("DOCUMENTO DE IDENTIDAD:");

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("NIVEL DEL CURSO:");

        lblNivel.setBackground(new java.awt.Color(255, 255, 255));
        lblNivel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNivel.setForeground(new java.awt.Color(0, 0, 0));
        lblNivel.setBorder(null);

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("DOCENTE:");

        lblDocente.setBackground(new java.awt.Color(255, 255, 255));
        lblDocente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDocente.setForeground(new java.awt.Color(0, 0, 0));
        lblDocente.setBorder(null);

        lblHorario.setBackground(new java.awt.Color(255, 255, 255));
        lblHorario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblHorario.setForeground(new java.awt.Color(0, 0, 0));
        lblHorario.setBorder(null);

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("HORARIO:");

        jLabel13.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("CUPOS DISPONIBLES:");

        lblCupos.setBackground(new java.awt.Color(255, 255, 255));
        lblCupos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblCupos.setForeground(new java.awt.Color(0, 0, 0));
        lblCupos.setBorder(null);

        jLabel14.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("DATOS DE LA MATRÍCULA");

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));

        jLabel15.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("CÓDIGO ESTUDIANTIL:");

        btnMatricular.setBackground(new java.awt.Color(153, 255, 153));
        btnMatricular.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMatricular.setForeground(new java.awt.Color(0, 0, 0));
        btnMatricular.setText(" MATRICULAR");
        btnMatricular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatricularActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 102, 102));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnBuscarEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombresEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellidosEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCupos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcFechaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(btnNuevoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btnMatricular, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btnBuscarEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNombresEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtApellidosEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcFechaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMatricular, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEstudianteActionPerformed
        String dni = txtDNI.getText().trim();
        abrirFormularioNuevoEstudiante(dni);
    }//GEN-LAST:event_btnNuevoEstudianteActionPerformed

    private void btnMatricularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatricularActionPerformed
        // Validar que hay estudiante seleccionado
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe buscar y seleccionar un estudiante", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar que hay curso seleccionado
        int indexCurso = cboCurso.getSelectedIndex();
        if (indexCurso <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un curso", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener curso seleccionado
        MODELO_Curso cursoSeleccionado = listaCursos.get(indexCurso - 1);
        
        // Validar fecha de matrícula
        if (jdcFechaMatricula.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar la fecha de matrícula", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar matrícula
        String mensaje = "¿Confirma la matrícula?\n\n" +
                        "Estudiante: " + controlador.obtenerNombreCompletoEstudiante(estudianteSeleccionado) + "\n" +
                        "DNI: " + estudianteSeleccionado.getNumeroDocumento() + "\n\n" +
                        "Curso: " + cursoSeleccionado.getNombreCurso() + "\n" +
                        "Nivel: " + cursoSeleccionado.getNivel() + "\n" +
                        "Docente: " + cursoSeleccionado.getNombreDocente() + "\n" +
                        "Horario: " + cursoSeleccionado.getHorarioTexto();
        
        int opcion = JOptionPane.showConfirmDialog(this, 
            mensaje, 
            "Confirmar Matrícula", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Crear objeto matrícula
        MODELO_Matricula matricula = new MODELO_Matricula();
        matricula.setIdEstudiante(estudianteSeleccionado.getIdEstudiante());
        matricula.setIdCurso(cursoSeleccionado.getIdCurso());
        matricula.setFechaMatricula(new Date(jdcFechaMatricula.getDate().getTime()));
        matricula.setObservaciones("");
        matricula.setEstado("ACTIVO");
        matricula.setIdUsuarioRegistro(usuarioLogueado.getIdUsuario());
        
        // Registrar matrícula
        String resultado = controlador.registrarMatricula(matricula);
        
        if (resultado.startsWith("EXITO")) {
            String codigoMatricula = resultado.split(":")[1];
            
            JOptionPane.showMessageDialog(this, 
                "¡Matrícula registrada exitosamente!\n\n" +
                "Código de Matrícula: " + codigoMatricula + "\n" +
                "Estudiante: " + controlador.obtenerNombreCompletoEstudiante(estudianteSeleccionado) + "\n" +
                "Curso: " + cursoSeleccionado.getNombreCurso(), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Preguntar si desea matricular otro estudiante
            int otraMatricula = JOptionPane.showConfirmDialog(this, 
                "¿Desea registrar otra matrícula?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (otraMatricula == JOptionPane.YES_OPTION) {
                limpiarFormularioCompleto();
                cargarCursos(); // Recargar para actualizar cupos
                txtDNI.requestFocus();
            } else {
                this.dispose();
            }
            
        } else {
            JOptionPane.showMessageDialog(this, 
                resultado, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnMatricularActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea cancelar?", 
            "Confirmar Cancelación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEstudianteActionPerformed
        String dni = txtDNI.getText().trim();
        
        // Validar DNI
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el DNI del estudiante", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtDNI.requestFocus();
            return;
        }
        
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe tener 8 dígitos", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            txtDNI.requestFocus();
            return;
        }
        
        // Buscar estudiante
        MODELO_Estudiante estudiante = controlador.buscarEstudiantePorDocumento(dni);
        
        if (estudiante != null) {
            // Estudiante encontrado
            estudianteSeleccionado = estudiante;
            
            txtCodigoEstudiante.setText(estudiante.getCodigoEstudiantil());
            txtNombresEstudiante.setText(estudiante.getNombres());
            txtApellidosEstudiante.setText(estudiante.getApellidoPaterno() + " " + 
                                          estudiante.getApellidoMaterno());
            
            btnNuevoEstudiante.setEnabled(false);
            
            JOptionPane.showMessageDialog(this, 
                "Estudiante encontrado:\n" + 
                controlador.obtenerNombreCompletoEstudiante(estudiante), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
            System.out.println("✓ Estudiante encontrado: " + estudiante.getCodigoEstudiantil());
            
        } else {
            // Estudiante NO encontrado
            limpiarCamposEstudiante();
            txtDNI.setText(dni); // Mantener el DNI ingresado
            btnNuevoEstudiante.setEnabled(true);
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "No se encontró ningún estudiante con DNI: " + dni + "\n\n" +
                "¿Desea registrar un nuevo estudiante?", 
                "Estudiante no encontrado", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (opcion == JOptionPane.YES_OPTION) {
                abrirFormularioNuevoEstudiante(dni);
            }
        }
        
        verificarHabilitarBotonMatricular();
    }//GEN-LAST:event_btnBuscarEstudianteActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarEstudiante;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnMatricular;
    private javax.swing.JButton btnNuevoEstudiante;
    private javax.swing.JComboBox<String> cboCurso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private com.toedter.calendar.JDateChooser jdcFechaMatricula;
    private javax.swing.JTextField lblCupos;
    private javax.swing.JTextField lblDocente;
    private javax.swing.JTextField lblHorario;
    private javax.swing.JTextField lblNivel;
    private javax.swing.JTextField txtApellidosEstudiante;
    private javax.swing.JTextField txtCodigoEstudiante;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtNombresEstudiante;
    // End of variables declaration//GEN-END:variables
}
