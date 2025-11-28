/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VISTA;

import CONTROLADOR.CONTROLADOR_Estudiante;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author MartinSoftware
 */
public class VISTA_GestionEstudiantes extends javax.swing.JFrame {

    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Estudiante controlador;
    private DefaultTableModel modeloTabla;

    /**
     * Creates new form VISTA_GestionEstudiantes
     */
    public VISTA_GestionEstudiantes(MODELO_Usuario usuario) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.controlador = new CONTROLADOR_Estudiante();
        configurarVentana();
        configurarTabla();
        configurarEfectosHover();
        cargarDatos();
    }

    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setTitle("Gestión de Estudiantes");
        btnEditar.setEnabled(false);
        btnHistorial.setEnabled(false);
        btnDarDeBaja.setEnabled(false);
    }

    /**
     * Configurar modelo de la tabla
     */
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Definir columnas
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Tipo Doc");
        modeloTabla.addColumn("Nº Documento");
        modeloTabla.addColumn("Nombres");
        modeloTabla.addColumn("Apellido Paterno");
        modeloTabla.addColumn("Apellido Materno");
        modeloTabla.addColumn("Edad");
        modeloTabla.addColumn("Sexo");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Estado");

        tblEstudiantes.setModel(modeloTabla);

        // Ocultar columna ID
        tblEstudiantes.getColumnModel().getColumn(0).setMinWidth(0);
        tblEstudiantes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblEstudiantes.getColumnModel().getColumn(0).setWidth(0);

        // Ajustar anchos de columnas
        tblEstudiantes.getColumnModel().getColumn(1).setPreferredWidth(100); // Código
        tblEstudiantes.getColumnModel().getColumn(2).setPreferredWidth(70);  // Tipo Doc
        tblEstudiantes.getColumnModel().getColumn(3).setPreferredWidth(100); // Nº Doc
        tblEstudiantes.getColumnModel().getColumn(4).setPreferredWidth(120); // Nombres
        tblEstudiantes.getColumnModel().getColumn(5).setPreferredWidth(120); // Apellido P
        tblEstudiantes.getColumnModel().getColumn(6).setPreferredWidth(120); // Apellido M
        tblEstudiantes.getColumnModel().getColumn(7).setPreferredWidth(50);  // Edad
        tblEstudiantes.getColumnModel().getColumn(8).setPreferredWidth(50);  // Sexo
        tblEstudiantes.getColumnModel().getColumn(9).setPreferredWidth(90);  // Teléfono
        tblEstudiantes.getColumnModel().getColumn(10).setPreferredWidth(150); // Email
        tblEstudiantes.getColumnModel().getColumn(11).setPreferredWidth(80);  // Estado

        // Evento de selección
        tblEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean seleccionado = tblEstudiantes.getSelectedRow() != -1;
                btnEditar.setEnabled(seleccionado);
                btnHistorial.setEnabled(seleccionado);
                btnDarDeBaja.setEnabled(seleccionado);
            }
        });
    }

    /**
     * Cargar datos en la tabla
     */
    private void cargarDatos() {
        limpiarTabla();

        List<MODELO_Estudiante> lista = controlador.listarTodos();

        // Ordenar por código estudiantil ASC
        lista.sort(Comparator.comparing(MODELO_Estudiante::getCodigoEstudiantil));

        for (MODELO_Estudiante estudiante : lista) {
            Object[] fila = new Object[12];
            fila[0] = estudiante.getIdEstudiante();
            fila[1] = estudiante.getCodigoEstudiantil();
            fila[2] = estudiante.getTipoDocumento();
            fila[3] = estudiante.getNumeroDocumento();
            fila[4] = estudiante.getNombres();
            fila[5] = estudiante.getApellidoPaterno();
            fila[6] = estudiante.getApellidoMaterno();
            fila[7] = estudiante.getEdad();
            fila[8] = estudiante.getSexo();
            fila[9] = estudiante.getTelefono();
            fila[10] = estudiante.getEmail() != null ? estudiante.getEmail() : "";
            fila[11] = estudiante.getEstado();

            modeloTabla.addRow(fila);
        }

        System.out.println("✓ Se cargaron " + lista.size() + " estudiantes en la tabla, ordenados por código");
    }

    /**
     * Limpiar tabla
     */
    private void limpiarTabla() {
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
    }

    private void configurarEfectosHover() {
        // Color original de los botones
        Color colorOriginalNuevo = btnHistorial.getBackground();
        Color colorOriginalEditar = btnEditar.getBackground();
        Color colorOriginalEliminar = btnDarDeBaja.getBackground();
        Color colorOriginalActualizar = btnActualizar.getBackground();
        Color colorOriginalVolver = btnVolverPrincipal.getBackground();
        Color colorOriginalBuscar = btnBuscar.getBackground();
        Color colorOriginalLimpiar = btnLimpiar.getBackground();

        Color colorHover = new Color(52, 152, 219); // Azul más claro
        Color bordeHover = new Color(41, 128, 185); // Un azul un poco más oscuro

        // Aplicar efecto a cada botón
        aplicarEfectoHover(btnHistorial, colorOriginalNuevo, colorHover, bordeHover);
        aplicarEfectoHover(btnEditar, colorOriginalEditar, colorHover, bordeHover);
        aplicarEfectoHover(btnDarDeBaja, colorOriginalEliminar, colorHover, bordeHover);
        aplicarEfectoHover(btnActualizar, colorOriginalActualizar, colorHover, bordeHover);
        aplicarEfectoHover(btnVolverPrincipal, colorOriginalVolver, colorHover, bordeHover);
        aplicarEfectoHover(btnBuscar, colorOriginalBuscar, colorHover, bordeHover);
        aplicarEfectoHover(btnLimpiar, colorOriginalLimpiar, colorHover, bordeHover);
    }

    private void aplicarEfectoHover(javax.swing.JButton boton, Color colorOriginal, Color colorHover, Color bordeHover) {

        // Asegurar que el botón respete los colores
        boton.setOpaque(true);
        boton.setBorderPainted(true);

        // Guardar el borde original
        Border bordeOriginal = boton.getBorder();

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
                boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Borde moderno
                boton.setBorder(BorderFactory.createLineBorder(bordeHover, 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorOriginal);
                boton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                // Restaurar borde original
                boton.setBorder(bordeOriginal);
            }
        });
    }

    private void abrirFormularioNuevoEstudiante() {
        VISTA_FormularioEstudiante formulario = new VISTA_FormularioEstudiante(
                this, true, "0", usuarioLogueado
        );
        formulario.setVisible(true);
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
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstudiantes = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDarDeBaja = new javax.swing.JButton();
        btnVolverPrincipal = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();
        btnNuevoEstudiante = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Estudiantes");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("GESTIÓN DE ESTUDIANTES");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 440, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asociación Privada de Patronato");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 440, 20));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("BUSCAR:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 89, -1, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 86, 495, -1));

        btnBuscar.setBackground(new java.awt.Color(153, 255, 153));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(0, 0, 0));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/lupa.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorder(null);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 78, 118, 39));

        btnLimpiar.setBackground(new java.awt.Color(204, 204, 255));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(0, 0, 0));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/herramienta-borrador.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.setBorder(null);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(748, 78, 118, 39));

        tblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Código", "Tipo Doc", "Nº Documento", "Nombres", "Apellido Paterno", "Apellido Materno", "Edad", "Sexo", "Teléfono", "Email", "Estado"
            }
        ));
        tblEstudiantes.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(tblEstudiantes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 850, 370));

        btnActualizar.setBackground(new java.awt.Color(204, 204, 255));
        btnActualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(0, 0, 0));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar Tabla");
        btnActualizar.setBorder(null);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 510, 180, 60));

        btnEditar.setBackground(new java.awt.Color(255, 204, 102));
        btnEditar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(0, 0, 0));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/reportes.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(null);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 510, 130, 60));

        btnDarDeBaja.setBackground(new java.awt.Color(255, 255, 255));
        btnDarDeBaja.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnDarDeBaja.setForeground(new java.awt.Color(0, 0, 0));
        btnDarDeBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/inhabilitar.png"))); // NOI18N
        btnDarDeBaja.setText("Dar de baja");
        btnDarDeBaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarDeBajaActionPerformed(evt);
            }
        });
        jPanel1.add(btnDarDeBaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 150, 60));

        btnVolverPrincipal.setBackground(new java.awt.Color(255, 255, 0));
        btnVolverPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVolverPrincipal.setForeground(new java.awt.Color(0, 0, 0));
        btnVolverPrincipal.setText("VOLVER AL MENÚ PRINCIPAL");
        btnVolverPrincipal.setBorder(null);
        btnVolverPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverPrincipalActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolverPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 590, 350, 40));

        btnHistorial.setBackground(new java.awt.Color(0, 204, 51));
        btnHistorial.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnHistorial.setForeground(new java.awt.Color(0, 0, 0));
        btnHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/students.png"))); // NOI18N
        btnHistorial.setText("Ver Historial");
        btnHistorial.setBorder(null);
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });
        jPanel1.add(btnHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 160, 60));

        btnNuevoEstudiante.setBackground(new java.awt.Color(153, 255, 255));
        btnNuevoEstudiante.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnNuevoEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevoEstudiante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/agregar-usuario.png"))); // NOI18N
        btnNuevoEstudiante.setText("Nuevo");
        btnNuevoEstudiante.setBorder(null);
        btnNuevoEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEstudianteActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 170, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String textoBusqueda = txtBuscar.getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un código, DNI o nombre para buscar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        limpiarTabla();

        // Intentar buscar por código
        MODELO_Estudiante estudiante = controlador.buscarPorCodigo(textoBusqueda);

        // Si no encuentra por código, buscar por documento
        if (estudiante == null) {
            estudiante = controlador.buscarPorDocumento(textoBusqueda);
        }

        // Si encontró un estudiante, agregarlo a la tabla
        if (estudiante != null) {
            Object[] fila = new Object[12];
            fila[0] = estudiante.getIdEstudiante();
            fila[1] = estudiante.getCodigoEstudiantil();
            fila[2] = estudiante.getTipoDocumento();
            fila[3] = estudiante.getNumeroDocumento();
            fila[4] = estudiante.getNombres();
            fila[5] = estudiante.getApellidoPaterno();
            fila[6] = estudiante.getApellidoMaterno();
            fila[7] = estudiante.getEdad();
            fila[8] = estudiante.getSexo();
            fila[9] = estudiante.getTelefono();
            fila[10] = estudiante.getEmail() != null ? estudiante.getEmail() : "";
            fila[11] = estudiante.getEstado();

            modeloTabla.addRow(fila);

            System.out.println("✓ Estudiante encontrado");
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún estudiante con ese criterio",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtBuscar.setText("");
        cargarDatos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatos();
        JOptionPane.showMessageDialog(this,
                "Tabla actualizada correctamente",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tblEstudiantes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un estudiante de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener ID del estudiante seleccionado
        int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Buscar el estudiante completo
        MODELO_Estudiante estudiante = controlador.buscarPorId(idEstudiante);

        if (estudiante == null) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener los datos del estudiante",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Abrir formulario de edición
        VISTA_EditarEstudiante formulario = new VISTA_EditarEstudiante(this, true, estudiante, usuarioLogueado);
        formulario.setVisible(true);

        // Recargar datos
        cargarDatos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarDeBajaActionPerformed
        int filaSeleccionada = tblEstudiantes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un estudiante de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos del estudiante seleccionado
        int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String codigoEstudiante = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String nombreCompleto = modeloTabla.getValueAt(filaSeleccionada, 4) + " "
                + modeloTabla.getValueAt(filaSeleccionada, 5) + " "
                + modeloTabla.getValueAt(filaSeleccionada, 6);
        String estado = (String) modeloTabla.getValueAt(filaSeleccionada, 11);

        // Verificar si ya está retirado
        if ("RETIRADO".equals(estado)) {
            JOptionPane.showMessageDialog(this,
                    "El estudiante ya está dado de baja",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Confirmar baja
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea dar de baja al estudiante?\n\n"
                + "Código: " + codigoEstudiante + "\n"
                + "Nombre: " + nombreCompleto + "\n\n"
                + "Esta acción cambiará el estado a RETIRADO.\n"
                + "No se podrá realizar si tiene matrículas activas.",
                "Confirmar Baja",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String resultado = controlador.darDeBajaEstudiante(idEstudiante);

            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this,
                        "Estudiante dado de baja correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this,
                        resultado,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDarDeBajaActionPerformed

    private void btnVolverPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverPrincipalActionPerformed
        // TODO add your handling code here:
        this.dispose();

    }//GEN-LAST:event_btnVolverPrincipalActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        int filaSeleccionada = tblEstudiantes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un estudiante de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener ID del estudiante seleccionado
        int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreEstudiante = modeloTabla.getValueAt(filaSeleccionada, 4) + " "
                + modeloTabla.getValueAt(filaSeleccionada, 5) + " "
                + modeloTabla.getValueAt(filaSeleccionada, 6);

        // Abrir ventana de historial
        VISTA_HistorialMatriculas ventana = new VISTA_HistorialMatriculas(idEstudiante, nombreEstudiante);
        ventana.setVisible(true);
    }//GEN-LAST:event_btnHistorialActionPerformed

    private void btnNuevoEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEstudianteActionPerformed
        abrirFormularioNuevoEstudiante();

    }//GEN-LAST:event_btnNuevoEstudianteActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDarDeBaja;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevoEstudiante;
    private javax.swing.JButton btnVolverPrincipal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEstudiantes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
