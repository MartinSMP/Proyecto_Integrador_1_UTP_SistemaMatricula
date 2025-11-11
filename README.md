# 🧾 Sistema de Matrícula - Proyecto Integrador I

📚 **Carrera:** Ingeniería de Software
🏫 **Universidad:** Universidad Tecnológica del Perú (UTP) - Lima Norte  
👨‍💻 **Desarrollado por:** Martín Sebastian Martinez Palacios @martindevpalacios
📅 **Año:** 2025  

---

## 🧠 Descripción General

El **Sistema de Matrícula** es un software desarrollado íntegramente en **Java** con conexión a **MySQL**, cuyo objetivo es gestionar de manera eficiente los procesos de matrícula estudiantil.  
Este proyecto fue desarrollado como parte del curso **Integrador I - Sistemas Software**, aplicando los conocimientos adquiridos en programación orientada a objetos, bases de datos y diseño de sistemas.

---

## ⚙️ Tecnologías Utilizadas

- ☕ **Java SE 8+**  
- 🛢️ **MySQL** (Base de datos relacional)  
- 🧩 **JDBC** para la conexión entre Java y MySQL  
- 🧰 **NetBeans IDE** para el desarrollo  
- 🖥️ **VS Code + GitHub** para control de versiones y documentación  

---

## 🧮 Funcionalidades Principales

- Registro de estudiantes  
- Gestión de cursos
- Gestión de Docentes  
- Asignación de matrículas  
- Consulta y modificación de registros  
- Validaciones y reportes básicos

---

## 🗂️ Estructura del Proyecto

```bash
Proyecto_INTEGRADOR_1/
├── build/
│   ├── built-jar.properties
│   ├── classes/
│   │   ├── .netbeans_automatic_build
│   │   ├── .netbeans_update_resources
│   │   ├── config/
│   │   │   ├── ConexionBD.class
│   │   ├── CONTROLADOR/
│   │   │   ├── ConexionBD.class
│   │   │   ├── CONTROLADOR_Curso.class
│   │   │   ├── CONTROLADOR_Docente.class
│   │   │   ├── CONTROLADOR_Login.class
│   │   │   ├── CONTROLADOR_Matricula.class
│   │   ├── DAO/
│   │   │   ├── DAO_Curso.class
│   │   │   ├── DAO_Docente.class
│   │   │   ├── DAO_Estudiante.class
│   │   │   ├── DAO_Horario.class
│   │   │   ├── DAO_Matricula.class
│   │   │   ├── DAO_Usuario.class
│   │   ├── Main/
│   │   │   ├── main.class
│   │   ├── MODELO/
│   │   │   ├── MODELO_Curso.class
│   │   │   ├── MODELO_Docente.class
│   │   │   ├── MODELO_Estudiante.class
│   │   │   ├── MODELO_Horario.class
│   │   │   ├── MODELO_Matricula.class
│   │   │   ├── MODELO_Usuario.class
│   │   ├── UTIL/
│   │   │   ├── imagenes/
│   │   │   │   ├── actualizar.png
│   │   │   │   ├── addCurso.png
│   │   │   │   ├── curriculum.png
│   │   │   │   ├── curso-por-internet.png
│   │   │   │   ├── docentes.png
│   │   │   │   ├── icono-pass.png
│   │   │   │   ├── icono-user.png
│   │   │   │   ├── inhabilitar.png
│   │   │   │   ├── logo utp.jpg
│   │   │   │   ├── logo utp.png
│   │   │   │   ├── matricula.png
│   │   │   │   ├── no-autorizado.png
│   │   │   │   ├── profesor.png
│   │   │   │   ├── reportes.png
│   │   │   │   ├── students.png
│   │   ├── VISTA/
│   │   │   ├── VISTA_FormularioCurso$1.class
│   │   │   ├── VISTA_FormularioCurso$2.class
│   │   │   ├── VISTA_FormularioCurso.class
│   │   │   ├── VISTA_FormularioCurso.form
│   │   │   ├── VISTA_FormularioDocente$1.class
│   │   │   ├── VISTA_FormularioDocente$2.class
│   │   │   ├── VISTA_FormularioDocente$3.class
│   │   │   ├── VISTA_FormularioDocente$4.class
│   │   │   ├── VISTA_FormularioDocente.class
│   │   │   ├── VISTA_FormularioDocente.form
│   │   │   ├── VISTA_FormularioEstudiante$1.class
│   │   │   ├── VISTA_FormularioEstudiante$2.class
│   │   │   ├── VISTA_FormularioEstudiante$3.class
│   │   │   ├── VISTA_FormularioEstudiante$4.class
│   │   │   ├── VISTA_FormularioEstudiante.class
│   │   │   ├── VISTA_FormularioEstudiante.form
│   │   │   ├── VISTA_GestionCursos$1.class
│   │   │   ├── VISTA_GestionCursos$10.class
│   │   │   ├── VISTA_GestionCursos$2.class
│   │   │   ├── VISTA_GestionCursos$3.class
│   │   │   ├── VISTA_GestionCursos$4.class
│   │   │   ├── VISTA_GestionCursos$5.class
│   │   │   ├── VISTA_GestionCursos$6.class
│   │   │   ├── VISTA_GestionCursos$7.class
│   │   │   ├── VISTA_GestionCursos$8.class
│   │   │   ├── VISTA_GestionCursos$9.class
│   │   │   ├── VISTA_GestionCursos.class
│   │   │   ├── VISTA_GestionCursos.form
│   │   │   ├── VISTA_GestionDocentes$1.class
│   │   │   ├── VISTA_GestionDocentes$2.class
│   │   │   ├── VISTA_GestionDocentes$3.class
│   │   │   ├── VISTA_GestionDocentes$4.class
│   │   │   ├── VISTA_GestionDocentes$5.class
│   │   │   ├── VISTA_GestionDocentes$6.class
│   │   │   ├── VISTA_GestionDocentes$7.class
│   │   │   ├── VISTA_GestionDocentes$8.class
│   │   │   ├── VISTA_GestionDocentes$9.class
│   │   │   ├── VISTA_GestionDocentes.class
│   │   │   ├── VISTA_GestionDocentes.form
│   │   │   ├── VISTA_Login$1.class
│   │   │   ├── VISTA_Login$2.class
│   │   │   ├── VISTA_Login$3.class
│   │   │   ├── VISTA_Login$4.class
│   │   │   ├── VISTA_Login$5.class
│   │   │   ├── VISTA_Login$6.class
│   │   │   ├── VISTA_Login$7.class
│   │   │   ├── VISTA_Login.class
│   │   │   ├── VISTA_Login.form
│   │   │   ├── VISTA_Matricula$1.class
│   │   │   ├── VISTA_Matricula$2.class
│   │   │   ├── VISTA_Matricula$3.class
│   │   │   ├── VISTA_Matricula$4.class
│   │   │   ├── VISTA_Matricula.class
│   │   │   ├── VISTA_Matricula.form
│   │   │   ├── VISTA_PantallaPrincipal$1.class
│   │   │   ├── VISTA_PantallaPrincipal$2.class
│   │   │   ├── VISTA_PantallaPrincipal$3.class
│   │   │   ├── VISTA_PantallaPrincipal$4.class
│   │   │   ├── VISTA_PantallaPrincipal$5.class
│   │   │   ├── VISTA_PantallaPrincipal$6.class
│   │   │   ├── VISTA_PantallaPrincipal$7.class
│   │   │   ├── VISTA_PantallaPrincipal$8.class
│   │   │   ├── VISTA_PantallaPrincipal.class
│   │   │   ├── VISTA_PantallaPrincipal.form
│   ├── empty/
│   ├── generated-sources/
│   │   ├── ap-source-output/
├── build.xml
├── librerias/
│   ├── easyUML/
│   │   ├── easyUML/
│   │   │   ├── com-github-javaparser.nbm
│   │   │   ├── easyuml.nbm
│   │   │   ├── licenses/
│   │   │   │   ├── B2B74E56.license
│   │   │   │   ├── B84C6956.license
│   │   │   ├── org-uml-dom4j.nbm
│   │   │   ├── org-uml-explorer.nbm
│   │   │   ├── org-uml-filetype.nbm
│   │   │   ├── org-uml-model.nbm
│   │   │   ├── org-uml-newcode.nbm
│   │   │   ├── org-uml-project.nbm
│   │   │   ├── org-uml-reveng.nbm
│   │   │   ├── org-uml-visual.nbm
│   │   │   ├── updates.xml
│   ├── itextpdf-5.4.0.jar
│   ├── jcalendar-1.4.jar
│   ├── jcalendar-1.4.zip
│   ├── jgoodies-common-1.2.0.jar
│   ├── jgoodies-looks-2.4.1.jar
│   ├── junit-4.6.jar
│   ├── mysql-connector-java-8.0.19.jar
├── manifest.mf
├── nbproject/
│   ├── build-impl.xml
│   ├── genfiles.properties
│   ├── private/
│   │   ├── config.properties
│   │   ├── private.properties
│   │   ├── private.xml
│   ├── project.properties
│   ├── project.xml
├── src/
│   ├── config/
│   │   ├── ConexionBD.java
│   ├── CONTROLADOR/
│   │   ├── ConexionBD.java
│   │   ├── CONTROLADOR_Curso.java
│   │   ├── CONTROLADOR_Docente.java
│   │   ├── CONTROLADOR_Login.java
│   │   ├── CONTROLADOR_Matricula.java
│   ├── DAO/
│   │   ├── DAO_Curso.java
│   │   ├── DAO_Docente.java
│   │   ├── DAO_Estudiante.java
│   │   ├── DAO_Horario.java
│   │   ├── DAO_Matricula.java
│   │   ├── DAO_Usuario.java
│   ├── Main/
│   │   ├── main.java
│   ├── MODELO/
│   │   ├── MODELO_Curso.java
│   │   ├── MODELO_Docente.java
│   │   ├── MODELO_Estudiante.java
│   │   ├── MODELO_Horario.java
│   │   ├── MODELO_Matricula.java
│   │   ├── MODELO_Usuario.java
│   ├── UTIL/
│   │   ├── imagenes/
│   │   │   ├── actualizar.png
│   │   │   ├── addCurso.png
│   │   │   ├── curriculum.png
│   │   │   ├── curso-por-internet.png
│   │   │   ├── docentes.png
│   │   │   ├── icono-pass.png
│   │   │   ├── icono-user.png
│   │   │   ├── inhabilitar.png
│   │   │   ├── logo utp.jpg
│   │   │   ├── logo utp.png
│   │   │   ├── matricula.png
│   │   │   ├── no-autorizado.png
│   │   │   ├── profesor.png
│   │   │   ├── reportes.png
│   │   │   ├── students.png
│   ├── VISTA/
│   │   ├── VISTA_FormularioCurso.form
│   │   ├── VISTA_FormularioCurso.java
│   │   ├── VISTA_FormularioDocente.form
│   │   ├── VISTA_FormularioDocente.java
│   │   ├── VISTA_FormularioEstudiante.form
│   │   ├── VISTA_FormularioEstudiante.java
│   │   ├── VISTA_GestionCursos.form
│   │   ├── VISTA_GestionCursos.java
│   │   ├── VISTA_GestionDocentes.form
│   │   ├── VISTA_GestionDocentes.java
│   │   ├── VISTA_Login.form
│   │   ├── VISTA_Login.java
│   │   ├── VISTA_Matricula.form
│   │   ├── VISTA_Matricula.java
│   │   ├── VISTA_PantallaPrincipal.form
│   │   ├── VISTA_PantallaPrincipal.java
├── test/


🧑‍💻 Autor

Desarrollado al 100% por
Martin Sebastian Martinez Palacios – Estudiante de Ingeniería de Software
📧 Contacto: martinsoftwaredev@hotmail.com
🌐 GitHub: https://github.com/MartinSMP
