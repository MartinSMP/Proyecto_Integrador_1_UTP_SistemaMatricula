/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package VISTA;

import CONTROLADOR.CONTROLADOR_Estudiante;
import MODELO.MODELO_Estudiante;
import MODELO.MODELO_Matricula;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author MartinSoftware
 */
public class VISTA_HistorialMatriculas extends javax.swing.JDialog {

    /**
     * Creates new form VISTA_HistorialMatriculas
     */
    private CONTROLADOR_Estudiante controlador;
    private int idEstudiante;
    private String nombreEstudiante;
    private DefaultTableModel modeloTabla;
    private SimpleDateFormat formatoFecha;
    
    /**
     * Constructor
     * @param idEstudiante ID del estudiante
     * @param nombreEstudiante Nombre completo del estudiante
     */
    public VISTA_HistorialMatriculas(int idEstudiante, String nombreEstudiante) {
        initComponents();
        
        this.controlador = new CONTROLADOR_Estudiante();
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        configurarVentana();
        configurarTabla();
        cargarHistorial();
        calcularEstadisticas();
    }
    
    /**
     * Configurar ventana
     */
    private void configurarVentana() {
        this.setLocationRelativeTo(null);
        this.setTitle("Historial de Matrículas - " + nombreEstudiante);
        
        // Mostrar información del estudiante
        lblEstudiante.setText("" + nombreEstudiante);
        
        // Obtener código del estudiante
        MODELO_Estudiante estudiante = controlador.buscarPorId(idEstudiante);
        if (estudiante != null) {
            lblCodigo.setText("" + estudiante.getCodigoEstudiantil());
        }
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
        modeloTabla.addColumn("Código Matrícula");
        modeloTabla.addColumn("Curso");
        modeloTabla.addColumn("Nivel");
        modeloTabla.addColumn("Fecha Matrícula");
        modeloTabla.addColumn("Observaciones");
        modeloTabla.addColumn("Estado");
        
        tblHistorial.setModel(modeloTabla);
        
        // Ocultar columna ID
        tblHistorial.getColumnModel().getColumn(0).setMinWidth(0);
        tblHistorial.getColumnModel().getColumn(0).setMaxWidth(0);
        tblHistorial.getColumnModel().getColumn(0).setWidth(0);
        
        // Ajustar anchos de columnas
        tblHistorial.getColumnModel().getColumn(1).setPreferredWidth(120); // Código
        tblHistorial.getColumnModel().getColumn(2).setPreferredWidth(150); // Curso
        tblHistorial.getColumnModel().getColumn(3).setPreferredWidth(90);  // Nivel
        tblHistorial.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha
        tblHistorial.getColumnModel().getColumn(5).setPreferredWidth(200); // Observaciones
        tblHistorial.getColumnModel().getColumn(6).setPreferredWidth(90);  // Estado
        
        // Aplicar renderer personalizado para colorear estados
        tblHistorial.getColumnModel().getColumn(6).setCellRenderer(new EstadoCellRenderer());
    }
    
    /**
     * Cargar historial de matrículas
     */
    private void cargarHistorial() {
        limpiarTabla();
        
        List<MODELO_Matricula> lista = controlador.obtenerHistorialMatriculas(idEstudiante);
        
        for (MODELO_Matricula matricula : lista) {
            Object[] fila = new Object[7];
            fila[0] = matricula.getIdMatricula();
            fila[1] = matricula.getCodigoMatricula();
            fila[2] = matricula.getNombreCurso();
            fila[3] = matricula.getNivelCurso();
            fila[4] = formatoFecha.format(matricula.getFechaMatricula());
            fila[5] = matricula.getObservaciones() != null ? matricula.getObservaciones() : "";
            fila[6] = matricula.getEstado();
            
            modeloTabla.addRow(fila);
        }
        
        System.out.println("✓ Se cargaron " + lista.size() + " matrículas en el historial");
    }
    
    /**
     * Limpiar tabla
     */
    private void limpiarTabla() {
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
    }
    
    /**
     * Calcular y mostrar estadísticas
     */
    private void calcularEstadisticas() {
        List<MODELO_Matricula> lista = controlador.obtenerHistorialMatriculas(idEstudiante);
        
        int total = lista.size();
        int activas = 0;
        int finalizadas = 0;
        int anuladas = 0;
        
        for (MODELO_Matricula m : lista) {
            switch (m.getEstado()) {
                case "ACTIVO":
                    activas++;
                    break;
                case "FINALIZADO":
                    finalizadas++;
                    break;
                case "ANULADO":
                    anuladas++;
                    break;
            }
        }
        
        // Mostrar estadísticas
        lblTotalMatriculas.setText("" + total);
        lblActivas.setText("" + activas);
        lblFinalizadas.setText("" + finalizadas);
        lblAnuladas.setText("" + anuladas);
        
        // Colorear estadísticas
        lblActivas.setForeground(new Color(39, 174, 96));   // Verde
        lblFinalizadas.setForeground(new Color(52, 152, 219)); // Azul
        lblAnuladas.setForeground(new Color(231, 76, 60));   // Rojo
    }
    
    // ========================================
    // RENDERER PERSONALIZADO PARA ESTADOS
    // ========================================
    
    /**
     * Renderer para colorear la columna de estado
     */
    class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component cell = super.getTableCellRendererComponent(table, value, 
                                                                 isSelected, hasFocus, row, column);
            
            if (value != null && !isSelected) {
                String estado = value.toString();
                
                switch (estado) {
                    case "ACTIVO":
                        cell.setForeground(new Color(39, 174, 96)); // Verde
                        break;
                    case "FINALIZADO":
                        cell.setForeground(new Color(52, 152, 219)); // Azul
                        break;
                    case "ANULADO":
                        cell.setForeground(new Color(231, 76, 60)); // Rojo
                        break;
                    default:
                        cell.setForeground(Color.BLACK);
                }
            } else if (isSelected) {
                cell.setForeground(table.getSelectionForeground());
            }
            
            return cell;
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
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        lblTitulo = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblEstudiante = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        lblTotalMatriculas = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblActivas = new javax.swing.JLabel();
        lblFinalizadas = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblAnuladas = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Asociación Privada de Patronato");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 440, 20));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CODIGO ESTUDIANTIL:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 170, 30));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 700, 10));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 700, 10));

        lblTitulo.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 0, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("HISTORIAL DE MATRÍCULAS");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 440, 30));

        lblCodigo.setFont(new java.awt.Font("Malgun Gothic", 2, 14)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(0, 0, 0));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodigo.setText("[CODIGO ESTUDIANTIL]");
        jPanel1.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 330, 30));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("ESTUDIANTE:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 110, 30));

        lblEstudiante.setFont(new java.awt.Font("Malgun Gothic", 2, 14)); // NOI18N
        lblEstudiante.setForeground(new java.awt.Color(0, 0, 0));
        lblEstudiante.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEstudiante.setText("[NOMBRE ESTUDIANTE]");
        jPanel1.add(lblEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 400, 30));

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Código Matricula", "Curso", "Nivel", "Fecha Matrícula", "Observaciones", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tblHistorial);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 660, 260));

        lblTotalMatriculas.setFont(new java.awt.Font("Malgun Gothic", 2, 14)); // NOI18N
        lblTotalMatriculas.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalMatriculas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotalMatriculas.setText("[CONTEO TOTAL DE MATRICULAS]");
        jPanel1.add(lblTotalMatriculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 360, 30));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total de matrículas:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 160, 30));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Activas:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 60, 30));

        lblActivas.setFont(new java.awt.Font("Malgun Gothic", 2, 14)); // NOI18N
        lblActivas.setForeground(new java.awt.Color(0, 0, 0));
        lblActivas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblActivas.setText("[activos]");
        jPanel1.add(lblActivas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 70, 30));

        lblFinalizadas.setFont(new java.awt.Font("Malgun Gothic", 2, 14)); // NOI18N
        lblFinalizadas.setForeground(new java.awt.Color(0, 0, 0));
        lblFinalizadas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFinalizadas.setText("[activos]");
        jPanel1.add(lblFinalizadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 470, 80, 30));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 102, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Finalizadas:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, 90, 30));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Anuladas:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 470, 80, 30));

        lblAnuladas.setFont(new java.awt.Font("Malgun Gothic", 2, 14)); // NOI18N
        lblAnuladas.setForeground(new java.awt.Color(0, 0, 0));
        lblAnuladas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAnuladas.setText("[activos]");
        jPanel1.add(lblAnuladas, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 70, 30));

        btnCerrar.setBackground(new java.awt.Color(255, 51, 102));
        btnCerrar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 520, 220, 40));

        btnActualizar.setBackground(new java.awt.Color(102, 102, 255));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(0, 0, 0));
        btnActualizar.setText("RECARGAR DATOS");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 520, 220, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarHistorial();
        calcularEstadisticas();
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Historial actualizado correctamente", 
            "Información", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblActivas;
    private javax.swing.JLabel lblAnuladas;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEstudiante;
    private javax.swing.JLabel lblFinalizadas;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalMatriculas;
    private javax.swing.JTable tblHistorial;
    // End of variables declaration//GEN-END:variables
}
