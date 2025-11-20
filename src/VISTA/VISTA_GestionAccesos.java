/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VISTA;

import CONTROLADOR.CONTROLADOR_Usuario;
import MODELO.MODELO_Usuario;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author MartinSoftware
 */
public class VISTA_GestionAccesos extends javax.swing.JFrame {

    private MODELO_Usuario usuarioLogueado;
    private CONTROLADOR_Usuario controlador;
    private DefaultTableModel modeloTabla;
    private SimpleDateFormat formatoFecha;

    /**
     * Constructor que recibe el usuario logueado
     */
    public VISTA_GestionAccesos(MODELO_Usuario usuario) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.controlador = new CONTROLADOR_Usuario();
        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        configurarVentana();
        configurarEfectosHover();
        configurarTabla();
        cargarDatos();
    }

    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
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
        modeloTabla.addColumn("Username");
        modeloTabla.addColumn("Nombre Completo");
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Fecha Creación");
        modeloTabla.addColumn("Último Acceso");

        tblUsuarios.setModel(modeloTabla);

        // Ocultar columna ID
        tblUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
        tblUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tblUsuarios.getColumnModel().getColumn(0).setWidth(0);

        // Ajustar anchos de columnas
        tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(120); // Username
        tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(200); // Nombre
        tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(120); // Rol
        tblUsuarios.getColumnModel().getColumn(4).setPreferredWidth(80);  // Estado
        tblUsuarios.getColumnModel().getColumn(5).setPreferredWidth(130); // Fecha Creación
        tblUsuarios.getColumnModel().getColumn(6).setPreferredWidth(130); // Último Acceso

        // Evento de selección
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean seleccionado = tblUsuarios.getSelectedRow() != -1;
                btnEditar.setEnabled(seleccionado);
                btnEliminar.setEnabled(seleccionado);
            }
        });
    }

    /**
     * Cargar datos en la tabla
     */
    private void cargarDatos() {
        limpiarTabla();

        List<MODELO_Usuario> lista = controlador.listarTodos();

        for (MODELO_Usuario usuario : lista) {
            Object[] fila = new Object[7];
            fila[0] = usuario.getIdUsuario();
            fila[1] = usuario.getUsername();
            fila[2] = usuario.getNombreCompleto();
            fila[3] = usuario.getRol();
            fila[4] = usuario.getEstado();
            fila[5] = usuario.getFechaCreacion() != null
                    ? formatoFecha.format(usuario.getFechaCreacion()) : "";
            fila[6] = usuario.getUltimoAcceso() != null
                    ? formatoFecha.format(usuario.getUltimoAcceso()) : "Nunca";

            modeloTabla.addRow(fila);
        }

        System.out.println("✓ Se cargaron " + lista.size() + " usuarios en la tabla");
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
        Color colorOriginalNuevo = btnNuevo.getBackground();
        Color colorOriginalEditar = btnEditar.getBackground();
        Color colorOriginalEliminar = btnEliminar.getBackground();
        Color colorOriginalActualizar = btnActualizar.getBackground();
        Color colorOriginalVolver = btnVolverPrincipal.getBackground();
        Color colorOriginalBuscar = btnBuscar.getBackground();
        Color colorOriginalLimpiar = btnLimpiar.getBackground();

        Color colorHover = new Color(52, 152, 219); // Azul más claro
        Color bordeHover = new Color(41, 128, 185); // Azul más oscuro

        // Aplicar efecto a cada botón
        aplicarEfectoHover(btnNuevo, colorOriginalNuevo, colorHover, bordeHover);
        aplicarEfectoHover(btnEditar, colorOriginalEditar, colorHover, bordeHover);
        aplicarEfectoHover(btnEliminar, colorOriginalEliminar, colorHover, bordeHover);
        aplicarEfectoHover(btnActualizar, colorOriginalActualizar, colorHover, bordeHover);
        aplicarEfectoHover(btnVolverPrincipal, colorOriginalVolver, colorHover, bordeHover);

        // Nuevos botones agregados
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
        tblUsuarios = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVolverPrincipal = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Usuarios");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("GESTIÓN DE USUARIOS");
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

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Usuario", "Nombre Completo", "Rol", "Estado", "Fecha Creación", "Último Acceso"
            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);

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
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 510, 210, 60));

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
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 510, 200, 60));

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/inhabilitar.png"))); // NOI18N
        btnEliminar.setText("Inhabilitar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 510, 190, 60));

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
        jPanel1.add(btnVolverPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 350, 40));

        btnNuevo.setBackground(new java.awt.Color(0, 204, 51));
        btnNuevo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UTIL/imagenes/profesor.png"))); // NOI18N
        btnNuevo.setText("Nuevo Usuario");
        btnNuevo.setBorder(null);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 200, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 889, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String textoBusqueda = txtBuscar.getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un username para buscar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        limpiarTabla();

        MODELO_Usuario usuario = controlador.buscarPorUsername(textoBusqueda);

        if (usuario != null) {
            Object[] fila = new Object[7];
            fila[0] = usuario.getIdUsuario();
            fila[1] = usuario.getUsername();
            fila[2] = usuario.getNombreCompleto();
            fila[3] = usuario.getRol();
            fila[4] = usuario.getEstado();
            fila[5] = usuario.getFechaCreacion() != null
                    ? formatoFecha.format(usuario.getFechaCreacion()) : "";
            fila[6] = usuario.getUltimoAcceso() != null
                    ? formatoFecha.format(usuario.getUltimoAcceso()) : "Nunca";

            modeloTabla.addRow(fila);

            System.out.println("✓ Usuario encontrado");
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún usuario con ese username",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
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
        int filaSeleccionada = tblUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un usuario de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener ID del usuario seleccionado
        int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Buscar el usuario completo
        MODELO_Usuario usuario = controlador.buscarPorId(idUsuario);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener los datos del usuario",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Abrir formulario de edición
        VISTA_FormularioUsuario formulario = new VISTA_FormularioUsuario(this, true, usuario, usuarioLogueado);
        formulario.setVisible(true);

        // Recargar datos
        cargarDatos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un usuario de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos del usuario seleccionado
        int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String username = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String nombreCompleto = (String) modeloTabla.getValueAt(filaSeleccionada, 2);

        // No permitir desactivar el usuario actual
        if (idUsuario == usuarioLogueado.getIdUsuario()) {
            JOptionPane.showMessageDialog(this,
                    "No puede desactivar su propio usuario",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar desactivación
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea desactivar al usuario?\n\n"
                + "Username: " + username + "\n"
                + "Nombre: " + nombreCompleto + "\n\n"
                + "Esta acción cambiará el estado a INACTIVO",
                "Confirmar Desactivación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            String resultado = controlador.eliminarUsuario(idUsuario);

            if ("EXITO".equals(resultado)) {
                JOptionPane.showMessageDialog(this,
                        "Usuario desactivado correctamente",
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

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVolverPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverPrincipalActionPerformed
        // TODO add your handling code here:
        this.dispose();

    }//GEN-LAST:event_btnVolverPrincipalActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        VISTA_FormularioUsuario formulario = new VISTA_FormularioUsuario(this, true, null, usuarioLogueado);
        formulario.setVisible(true);

        // Recargar datos
        cargarDatos();
    }//GEN-LAST:event_btnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVolverPrincipal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
