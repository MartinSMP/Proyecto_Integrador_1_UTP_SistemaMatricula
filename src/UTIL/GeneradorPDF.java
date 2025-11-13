package UTIL;

import MODELO.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Clase utilitaria para generar reportes en PDF
 * @author MartinSoftware
 */
public class GeneradorPDF {
    
    private static final Font FONT_TITULO = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font FONT_SUBTITULO = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font FONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font FONT_BOLD = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static final Font FONT_SMALL = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    /**
     * Generar constancia de matrícula
     * @param matricula objeto Matrícula
     * @param estudiante objeto Estudiante
     * @param curso objeto Curso
     * @param rutaArchivo ruta donde guardar el PDF
     * @return true si se generó correctamente
     */
    public static boolean generarConstanciaMatricula(MODELO_Matricula matricula, 
                                                     MODELO_Estudiante estudiante,
                                                     MODELO_Curso curso,
                                                     String rutaArchivo) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();
            
            // Membrete
            agregarMembrete(document);
            
            // Título
            Paragraph titulo = new Paragraph("CONSTANCIA DE MATRÍCULA", FONT_TITULO);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            // Código de matrícula
            Paragraph codigo = new Paragraph("Código: " + matricula.getCodigoMatricula(), FONT_BOLD);
            codigo.setAlignment(Element.ALIGN_CENTER);
            codigo.setSpacingAfter(30);
            document.add(codigo);
            
            // Contenido
            Paragraph contenido = new Paragraph();
            contenido.setAlignment(Element.ALIGN_JUSTIFIED);
            contenido.setFont(FONT_NORMAL);
            contenido.setSpacingAfter(20);
            
            contenido.add("La Academia de Belleza y Estética hace constar que el(la) estudiante:\n\n");
            
            document.add(contenido);
            
            // Datos del estudiante
            PdfPTable tablaDatos = new PdfPTable(2);
            tablaDatos.setWidthPercentage(90);
            tablaDatos.setSpacingAfter(20);
            
            agregarCeldaDatos(tablaDatos, "CÓDIGO ESTUDIANTIL:", estudiante.getCodigoEstudiantil());
            agregarCeldaDatos(tablaDatos, "NOMBRES Y APELLIDOS:", 
                estudiante.getNombres() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno());
            agregarCeldaDatos(tablaDatos, "TIPO DE DOCUMENTO:", estudiante.getTipoDocumento());
            agregarCeldaDatos(tablaDatos, "NÚMERO DE DOCUMENTO:", estudiante.getNumeroDocumento());
            
            document.add(tablaDatos);
            
            // Texto intermedio
            Paragraph texto2 = new Paragraph();
            texto2.setAlignment(Element.ALIGN_JUSTIFIED);
            texto2.setFont(FONT_NORMAL);
            texto2.setSpacingAfter(20);
            texto2.add("Se encuentra matriculado(a) en el siguiente curso:\n\n");
            document.add(texto2);
            
            // Datos del curso
            PdfPTable tablaCurso = new PdfPTable(2);
            tablaCurso.setWidthPercentage(90);
            tablaCurso.setSpacingAfter(20);
            
            agregarCeldaDatos(tablaCurso, "CURSO:", curso.getNombreCurso());
            agregarCeldaDatos(tablaCurso, "NIVEL:", curso.getNivel());
            agregarCeldaDatos(tablaCurso, "DOCENTE:", curso.getNombreDocente());
            agregarCeldaDatos(tablaCurso, "HORARIO:", curso.getHorarioTexto());
            agregarCeldaDatos(tablaCurso, "FECHA DE INICIO:", formatoFecha.format(curso.getFechaInicio()));
            agregarCeldaDatos(tablaCurso, "FECHA DE FIN:", formatoFecha.format(curso.getFechaFin()));
            agregarCeldaDatos(tablaCurso, "FECHA DE MATRÍCULA:", formatoFecha.format(matricula.getFechaMatricula()));
            
            document.add(tablaCurso);
            
            // Texto final
            Paragraph textoFinal = new Paragraph();
            textoFinal.setAlignment(Element.ALIGN_JUSTIFIED);
            textoFinal.setFont(FONT_NORMAL);
            textoFinal.setSpacingAfter(40);
            textoFinal.add("\nSe expide la presente constancia a solicitud del(la) interesado(a) para los fines que considere conveniente.\n\n");
            document.add(textoFinal);
            
            // Fecha y lugar
            Paragraph fecha = new Paragraph();
            fecha.setAlignment(Element.ALIGN_RIGHT);
            fecha.setFont(FONT_NORMAL);
            fecha.setSpacingAfter(50);
            fecha.add("Lima, " + formatoFecha.format(new java.util.Date()));
            document.add(fecha);
            
            // Firma
            Paragraph firma = new Paragraph();
            firma.setAlignment(Element.ALIGN_CENTER);
            firma.setFont(FONT_BOLD);
            firma.add("_______________________________\n");
            firma.add("Dirección Académica\n");
            firma.add("Academia de Belleza y Estética");
            document.add(firma);
            
            document.close();
            
            System.out.println("✓ Constancia generada: " + rutaArchivo);
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error al generar constancia: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Generar reporte de estudiantes por curso
     * @param curso objeto Curso
     * @param matriculas lista de matrículas del curso
     * @param rutaArchivo ruta donde guardar el PDF
     * @return true si se generó correctamente
     */
    public static boolean generarReporteEstudiantesPorCurso(MODELO_Curso curso,
                                                             List<MODELO_Matricula> matriculas,
                                                             String rutaArchivo) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();
            
            // Membrete
            agregarMembrete(document);
            
            // Título
            Paragraph titulo = new Paragraph("REPORTE DE ESTUDIANTES POR CURSO", FONT_TITULO);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);
            
            // Datos del curso
            Paragraph infoCurso = new Paragraph();
            infoCurso.setFont(FONT_NORMAL);
            infoCurso.setSpacingAfter(20);
            infoCurso.add("Curso: " + curso.getNombreCurso() + " - " + curso.getNivel() + "\n");
            infoCurso.add("Docente: " + curso.getNombreDocente() + "\n");
            infoCurso.add("Horario: " + curso.getHorarioTexto());
            document.add(infoCurso);
            
            // Tabla de estudiantes
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1.5f, 3f, 1.5f, 2f, 2f, 1.5f});
            
            // Encabezados
            agregarCeldaEncabezado(tabla, "Código");
            agregarCeldaEncabezado(tabla, "Nombre Completo");
            agregarCeldaEncabezado(tabla, "DNI");
            agregarCeldaEncabezado(tabla, "Teléfono");
            agregarCeldaEncabezado(tabla, "Fecha Matrícula");
            agregarCeldaEncabezado(tabla, "Estado");
            
            // Datos
            for (MODELO_Matricula m : matriculas) {
                agregarCeldaNormal(tabla, m.getCodigoMatricula());
                agregarCeldaNormal(tabla, m.getNombreEstudiante());
                agregarCeldaNormal(tabla, ""); // DNI - tendrías que agregarlo al modelo
                agregarCeldaNormal(tabla, ""); // Teléfono - tendrías que agregarlo al modelo
                agregarCeldaNormal(tabla, formatoFecha.format(m.getFechaMatricula()));
                agregarCeldaNormal(tabla, m.getEstado());
            }
            
            document.add(tabla);
            
            // Total
            Paragraph total = new Paragraph();
            total.setFont(FONT_BOLD);
            total.setSpacingBefore(10);
            total.add("Total de estudiantes: " + matriculas.size());
            document.add(total);
            
            // Pie de página
            agregarPiePagina(document);
            
            document.close();
            
            System.out.println("✓ Reporte generado: " + rutaArchivo);
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Generar listado de cursos activos
     * @param cursos lista de cursos
     * @param rutaArchivo ruta donde guardar el PDF
     * @return true si se generó correctamente
     */
    public static boolean generarListadoCursosActivos(List<MODELO_Curso> cursos, String rutaArchivo) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();
            
            // Membrete
            agregarMembrete(document);
            
            // Título
            Paragraph titulo = new Paragraph("LISTADO DE CURSOS ACTIVOS", FONT_TITULO);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            // Tabla de cursos
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{3f, 1.5f, 2.5f, 2.5f, 1.5f, 1.5f, 1.5f});
            
            // Encabezados
            agregarCeldaEncabezado(tabla, "Curso");
            agregarCeldaEncabezado(tabla, "Nivel");
            agregarCeldaEncabezado(tabla, "Docente");
            agregarCeldaEncabezado(tabla, "Horario");
            agregarCeldaEncabezado(tabla, "Duración");
            agregarCeldaEncabezado(tabla, "Cupos");
            agregarCeldaEncabezado(tabla, "Estado");
            
            // Datos
            for (MODELO_Curso c : cursos) {
                agregarCeldaNormal(tabla, c.getNombreCurso());
                agregarCeldaNormal(tabla, c.getNivel());
                agregarCeldaNormal(tabla, c.getNombreDocente());
                agregarCeldaNormal(tabla, c.getHorarioTexto());
                agregarCeldaNormal(tabla, c.getDuracionMeses() + " meses");
                agregarCeldaNormal(tabla, String.valueOf(c.getCuposDisponibles()));
                agregarCeldaNormal(tabla, c.getEstado());
            }
            
            document.add(tabla);
            
            // Total
            Paragraph total = new Paragraph();
            total.setFont(FONT_BOLD);
            total.setSpacingBefore(10);
            total.add("Total de cursos: " + cursos.size());
            document.add(total);
            
            // Pie de página
            agregarPiePagina(document);
            
            document.close();
            
            System.out.println("✓ Listado generado: " + rutaArchivo);
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error al generar listado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Generar reporte general de matrículas
     * @param matriculas lista de matrículas
     * @param rutaArchivo ruta donde guardar el PDF
     * @return true si se generó correctamente
     */
    public static boolean generarReporteGeneralMatriculas(List<MODELO_Matricula> matriculas, String rutaArchivo) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();
            
            // Membrete
            agregarMembrete(document);
            
            // Título
            Paragraph titulo = new Paragraph("REPORTE GENERAL DE MATRÍCULAS", FONT_TITULO);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);
            
            // Estadísticas
            int activas = 0, finalizadas = 0, anuladas = 0;
            for (MODELO_Matricula m : matriculas) {
                if ("ACTIVO".equals(m.getEstado())) activas++;
                else if ("FINALIZADO".equals(m.getEstado())) finalizadas++;
                else if ("ANULADO".equals(m.getEstado())) anuladas++;
            }
            
            Paragraph stats = new Paragraph();
            stats.setFont(FONT_NORMAL);
            stats.setSpacingAfter(15);
            stats.add("Total de matrículas: " + matriculas.size() + " | ");
            stats.add("Activas: " + activas + " | ");
            stats.add("Finalizadas: " + finalizadas + " | ");
            stats.add("Anuladas: " + anuladas);
            document.add(stats);
            
            // Tabla de matrículas
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1.5f, 3f, 2.5f, 1.5f, 1.5f, 1.2f});
            
            // Encabezados
            agregarCeldaEncabezado(tabla, "Código");
            agregarCeldaEncabezado(tabla, "Estudiante");
            agregarCeldaEncabezado(tabla, "Curso");
            agregarCeldaEncabezado(tabla, "Nivel");
            agregarCeldaEncabezado(tabla, "Fecha");
            agregarCeldaEncabezado(tabla, "Estado");
            
            // Datos
            for (MODELO_Matricula m : matriculas) {
                agregarCeldaNormal(tabla, m.getCodigoMatricula());
                agregarCeldaNormal(tabla, m.getNombreEstudiante());
                agregarCeldaNormal(tabla, m.getNombreCurso());
                agregarCeldaNormal(tabla, m.getNivelCurso());
                agregarCeldaNormal(tabla, formatoFecha.format(m.getFechaMatricula()));
                agregarCeldaNormal(tabla, m.getEstado());
            }
            
            document.add(tabla);
            
            // Pie de página
            agregarPiePagina(document);
            
            document.close();
            
            System.out.println("✓ Reporte generado: " + rutaArchivo);
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // ========================================
    // MÉTODOS AUXILIARES
    // ========================================
    
    private static void agregarMembrete(Document document) throws DocumentException {
        Paragraph membrete = new Paragraph();
        membrete.setFont(FONT_SUBTITULO);
        membrete.setAlignment(Element.ALIGN_CENTER);
        membrete.setSpacingAfter(10);
        membrete.add("ACADEMIA DE BELLEZA Y ESTÉTICA\n");
        
        Paragraph subtitulo = new Paragraph();
        subtitulo.setFont(FONT_SMALL);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(20);
        subtitulo.add("Formando profesionales en el arte de la belleza\n");
        subtitulo.add("Lima - Perú");
        
        document.add(membrete);
        document.add(subtitulo);
        document.add(new Paragraph(" "));
    }
    
    private static void agregarPiePagina(Document document) throws DocumentException {
        Paragraph pie = new Paragraph();
        pie.setFont(FONT_SMALL);
        pie.setAlignment(Element.ALIGN_CENTER);
        pie.setSpacingBefore(30);
        pie.add("Documento generado el " + formatoFechaHora.format(new java.util.Date()));
        document.add(pie);
    }
    
    private static void agregarCeldaDatos(PdfPTable tabla, String etiqueta, String valor) {
        PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiqueta, FONT_BOLD));
        celdaEtiqueta.setBorder(Rectangle.NO_BORDER);
        celdaEtiqueta.setPaddingBottom(5);
        tabla.addCell(celdaEtiqueta);
        
        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, FONT_NORMAL));
        celdaValor.setBorder(Rectangle.NO_BORDER);
        celdaValor.setPaddingBottom(5);
        tabla.addCell(celdaValor);
    }
    
    private static void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FONT_BOLD));
        celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(5);
        tabla.addCell(celda);
    }
    
    private static void agregarCeldaNormal(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FONT_SMALL));
        celda.setPadding(3);
        tabla.addCell(celda);
    }
}