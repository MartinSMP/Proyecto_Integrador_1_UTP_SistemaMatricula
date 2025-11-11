-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-11-2025 a las 00:12:24
-- Versión del servidor: 10.4.13-MariaDB
-- Versión de PHP: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_proyectointegrador_matricula`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cursos`
--

CREATE TABLE `cursos` (
  `id_curso` int(11) NOT NULL,
  `nombre_curso` varchar(100) NOT NULL,
  `nivel` enum('BASICO','INTERMEDIO','AVANZADO') NOT NULL,
  `descripcion` text DEFAULT NULL,
  `duracion_meses` int(11) NOT NULL,
  `cupos_disponibles` int(11) NOT NULL,
  `id_horario` int(11) NOT NULL,
  `id_docente` int(11) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `estado` enum('ACTIVO','INACTIVO','FINALIZADO') DEFAULT 'ACTIVO',
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cursos`
--

INSERT INTO `cursos` (`id_curso`, `nombre_curso`, `nivel`, `descripcion`, `duracion_meses`, `cupos_disponibles`, `id_horario`, `id_docente`, `fecha_inicio`, `fecha_fin`, `estado`, `fecha_creacion`) VALUES
(1, 'Barbería proxsd', 'INTERMEDIO', 'nose', 2, 9, 5, 1, '2025-10-27', '2025-12-27', 'ACTIVO', '2025-10-23 19:59:31'),
(2, 'Barberia', 'BASICO', 'asdsad', 2, 9, 1, 1, '2025-10-26', '2025-12-26', 'ACTIVO', '2025-10-23 20:39:18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `docentes`
--

CREATE TABLE `docentes` (
  `id_docente` int(11) NOT NULL,
  `tipo_documento` enum('DNI','CE','PASAPORTE') NOT NULL,
  `numero_documento` varchar(20) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellido_paterno` varchar(50) NOT NULL,
  `apellido_materno` varchar(50) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `especialidad` varchar(100) NOT NULL,
  `estado` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `docentes`
--

INSERT INTO `docentes` (`id_docente`, `tipo_documento`, `numero_documento`, `nombres`, `apellido_paterno`, `apellido_materno`, `telefono`, `email`, `especialidad`, `estado`, `fecha_registro`) VALUES
(1, 'DNI', '12345678', 'Carlos P', 'Ramirez', 'Lopez', '987654321', 'carlos.ramirez@academia.com', 'Barbería', 'ACTIVO', '2025-10-21 02:52:14'),
(2, 'DNI', '87654321', 'Maria', 'Gonzales', 'Torres', '987654322', 'maria.gonzales@academia.com', 'Diseño de Uñas', 'ACTIVO', '2025-10-21 02:52:14'),
(3, 'DNI', '45678912', 'Ana', 'Flores', 'Medina', '987654323', 'ana.flores@academia.com', 'Extensión de Pestañas', 'ACTIVO', '2025-10-21 02:52:14'),
(4, 'DNI', '72491885', 'Martin Sebastian', 'Martinez', 'Palacios', '944676744', 'martin@gmail.com', 'Informática', 'ACTIVO', '2025-10-23 00:33:19');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiantes`
--

CREATE TABLE `estudiantes` (
  `id_estudiante` int(11) NOT NULL,
  `codigo_estudiantil` varchar(20) NOT NULL,
  `tipo_documento` enum('DNI','CE','PASAPORTE') NOT NULL,
  `numero_documento` varchar(20) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellido_paterno` varchar(50) NOT NULL,
  `apellido_materno` varchar(50) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `edad` int(11) NOT NULL,
  `sexo` enum('M','F') NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `estado` enum('ACTIVO','INACTIVO','RETIRADO') DEFAULT 'ACTIVO',
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `estudiantes`
--

INSERT INTO `estudiantes` (`id_estudiante`, `codigo_estudiantil`, `tipo_documento`, `numero_documento`, `nombres`, `apellido_paterno`, `apellido_materno`, `fecha_nacimiento`, `edad`, `sexo`, `telefono`, `email`, `direccion`, `estado`, `fecha_registro`) VALUES
(1, 'EST-2025-001', 'DNI', '72491885', 'Martín Sebastian', 'Martinez', 'Palacios', '2003-05-30', 22, 'M', '944676744', NULL, 'Avenida Daniel Alcides Carrion', 'ACTIVO', '2025-11-11 22:21:15');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE `horarios` (
  `id_horario` int(11) NOT NULL,
  `dia_semana` enum('LUNES','MARTES','MIERCOLES','JUEVES','VIERNES','SABADO','DOMINGO') NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `turno` varchar(20) NOT NULL,
  `estado` enum('DISPONIBLE','NO_DISPONIBLE') DEFAULT 'DISPONIBLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `horarios`
--

INSERT INTO `horarios` (`id_horario`, `dia_semana`, `hora_inicio`, `hora_fin`, `turno`, `estado`) VALUES
(1, 'LUNES', '08:00:00', '12:00:00', 'MAÑANA', 'DISPONIBLE'),
(2, 'LUNES', '14:00:00', '18:00:00', 'TARDE', 'DISPONIBLE'),
(3, 'LUNES', '18:00:00', '21:00:00', 'NOCHE', 'DISPONIBLE'),
(4, 'MARTES', '08:00:00', '12:00:00', 'MAÑANA', 'DISPONIBLE'),
(5, 'MARTES', '14:00:00', '18:00:00', 'TARDE', 'DISPONIBLE'),
(6, 'MIERCOLES', '08:00:00', '12:00:00', 'MAÑANA', 'DISPONIBLE'),
(7, 'MIERCOLES', '14:00:00', '18:00:00', 'TARDE', 'DISPONIBLE'),
(8, 'JUEVES', '08:00:00', '12:00:00', 'MAÑANA', 'DISPONIBLE'),
(9, 'JUEVES', '14:00:00', '18:00:00', 'TARDE', 'DISPONIBLE'),
(10, 'VIERNES', '08:00:00', '12:00:00', 'MAÑANA', 'DISPONIBLE'),
(11, 'VIERNES', '14:00:00', '18:00:00', 'TARDE', 'DISPONIBLE'),
(12, 'SABADO', '09:00:00', '13:00:00', 'MAÑANA', 'DISPONIBLE'),
(13, 'SABADO', '14:00:00', '17:00:00', 'TARDE', 'DISPONIBLE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matriculas`
--

CREATE TABLE `matriculas` (
  `id_matricula` int(11) NOT NULL,
  `codigo_matricula` varchar(20) NOT NULL,
  `id_estudiante` int(11) NOT NULL,
  `id_curso` int(11) NOT NULL,
  `fecha_matricula` date NOT NULL,
  `observaciones` text DEFAULT NULL,
  `estado` enum('ACTIVO','ANULADO','FINALIZADO') DEFAULT 'ACTIVO',
  `id_usuario_registro` int(11) NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `matriculas`
--

INSERT INTO `matriculas` (`id_matricula`, `codigo_matricula`, `id_estudiante`, `id_curso`, `fecha_matricula`, `observaciones`, `estado`, `id_usuario_registro`, `fecha_registro`) VALUES
(1, 'MAT-2025-001', 1, 1, '2025-11-11', '', 'ACTIVO', 1, '2025-11-11 22:21:47'),
(2, 'MAT-2025-002', 1, 2, '2025-11-11', '', 'ACTIVO', 1, '2025-11-11 22:22:33');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nombre_completo` varchar(100) NOT NULL,
  `rol` varchar(30) NOT NULL,
  `estado` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `ultimo_acceso` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `username`, `password`, `nombre_completo`, `rol`, `estado`, `fecha_creacion`, `ultimo_acceso`) VALUES
(1, 'admin', 'admin123', 'Administrador del Sistema', 'ADMINISTRADOR', 'ACTIVO', '2025-10-21 02:52:14', '2025-11-11 17:20:15'),
(2, 'recepcion', 'recep123', 'Usuario Recepción', 'RECEPCIONISTA', 'ACTIVO', '2025-10-21 02:52:14', NULL);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_cursos_completo`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_cursos_completo` (
`id_curso` int(11)
,`nombre_curso` varchar(100)
,`nivel` enum('BASICO','INTERMEDIO','AVANZADO')
,`duracion_meses` int(11)
,`cupos_disponibles` int(11)
,`nombre_docente` varchar(151)
,`horario` varchar(31)
,`fecha_inicio` date
,`fecha_fin` date
,`estado` enum('ACTIVO','INACTIVO','FINALIZADO')
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_estudiantes_completo`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_estudiantes_completo` (
`id_estudiante` int(11)
,`codigo_estudiantil` varchar(20)
,`tipo_documento` enum('DNI','CE','PASAPORTE')
,`numero_documento` varchar(20)
,`nombre_completo` varchar(202)
,`fecha_nacimiento` date
,`edad` int(11)
,`sexo` enum('M','F')
,`telefono` varchar(15)
,`email` varchar(100)
,`direccion` varchar(200)
,`estado` enum('ACTIVO','INACTIVO','RETIRADO')
,`fecha_registro` timestamp
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_matriculas_completo`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_matriculas_completo` (
`id_matricula` int(11)
,`codigo_matricula` varchar(20)
,`nombre_estudiante` varchar(202)
,`numero_documento` varchar(20)
,`nombre_curso` varchar(100)
,`nivel` enum('BASICO','INTERMEDIO','AVANZADO')
,`fecha_matricula` date
,`estado` enum('ACTIVO','ANULADO','FINALIZADO')
,`registrado_por` varchar(100)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_cursos_completo`
--
DROP TABLE IF EXISTS `vista_cursos_completo`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_cursos_completo`  AS  select `c`.`id_curso` AS `id_curso`,`c`.`nombre_curso` AS `nombre_curso`,`c`.`nivel` AS `nivel`,`c`.`duracion_meses` AS `duracion_meses`,`c`.`cupos_disponibles` AS `cupos_disponibles`,concat(`d`.`nombres`,' ',`d`.`apellido_paterno`) AS `nombre_docente`,concat(`h`.`dia_semana`,' ',`h`.`hora_inicio`,'-',`h`.`hora_fin`) AS `horario`,`c`.`fecha_inicio` AS `fecha_inicio`,`c`.`fecha_fin` AS `fecha_fin`,`c`.`estado` AS `estado` from ((`cursos` `c` join `docentes` `d` on(`c`.`id_docente` = `d`.`id_docente`)) join `horarios` `h` on(`c`.`id_horario` = `h`.`id_horario`)) ;

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_estudiantes_completo`
--
DROP TABLE IF EXISTS `vista_estudiantes_completo`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_estudiantes_completo`  AS  select `e`.`id_estudiante` AS `id_estudiante`,`e`.`codigo_estudiantil` AS `codigo_estudiantil`,`e`.`tipo_documento` AS `tipo_documento`,`e`.`numero_documento` AS `numero_documento`,concat(`e`.`nombres`,' ',`e`.`apellido_paterno`,' ',`e`.`apellido_materno`) AS `nombre_completo`,`e`.`fecha_nacimiento` AS `fecha_nacimiento`,`e`.`edad` AS `edad`,`e`.`sexo` AS `sexo`,`e`.`telefono` AS `telefono`,`e`.`email` AS `email`,`e`.`direccion` AS `direccion`,`e`.`estado` AS `estado`,`e`.`fecha_registro` AS `fecha_registro` from `estudiantes` `e` ;

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_matriculas_completo`
--
DROP TABLE IF EXISTS `vista_matriculas_completo`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_matriculas_completo`  AS  select `m`.`id_matricula` AS `id_matricula`,`m`.`codigo_matricula` AS `codigo_matricula`,concat(`e`.`nombres`,' ',`e`.`apellido_paterno`,' ',`e`.`apellido_materno`) AS `nombre_estudiante`,`e`.`numero_documento` AS `numero_documento`,`c`.`nombre_curso` AS `nombre_curso`,`c`.`nivel` AS `nivel`,`m`.`fecha_matricula` AS `fecha_matricula`,`m`.`estado` AS `estado`,`u`.`nombre_completo` AS `registrado_por` from (((`matriculas` `m` join `estudiantes` `e` on(`m`.`id_estudiante` = `e`.`id_estudiante`)) join `cursos` `c` on(`m`.`id_curso` = `c`.`id_curso`)) join `usuarios` `u` on(`m`.`id_usuario_registro` = `u`.`id_usuario`)) ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cursos`
--
ALTER TABLE `cursos`
  ADD PRIMARY KEY (`id_curso`),
  ADD KEY `id_horario` (`id_horario`),
  ADD KEY `idx_curso_docente` (`id_docente`);

--
-- Indices de la tabla `docentes`
--
ALTER TABLE `docentes`
  ADD PRIMARY KEY (`id_docente`),
  ADD UNIQUE KEY `numero_documento` (`numero_documento`),
  ADD KEY `idx_docente_documento` (`numero_documento`);

--
-- Indices de la tabla `estudiantes`
--
ALTER TABLE `estudiantes`
  ADD PRIMARY KEY (`id_estudiante`),
  ADD UNIQUE KEY `codigo_estudiantil` (`codigo_estudiantil`),
  ADD UNIQUE KEY `numero_documento` (`numero_documento`),
  ADD KEY `idx_estudiante_documento` (`numero_documento`),
  ADD KEY `idx_estudiante_codigo` (`codigo_estudiantil`);

--
-- Indices de la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`id_horario`);

--
-- Indices de la tabla `matriculas`
--
ALTER TABLE `matriculas`
  ADD PRIMARY KEY (`id_matricula`),
  ADD UNIQUE KEY `codigo_matricula` (`codigo_matricula`),
  ADD KEY `id_usuario_registro` (`id_usuario_registro`),
  ADD KEY `idx_matricula_estudiante` (`id_estudiante`),
  ADD KEY `idx_matricula_curso` (`id_curso`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cursos`
--
ALTER TABLE `cursos`
  MODIFY `id_curso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `docentes`
--
ALTER TABLE `docentes`
  MODIFY `id_docente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `estudiantes`
--
ALTER TABLE `estudiantes`
  MODIFY `id_estudiante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `horarios`
--
ALTER TABLE `horarios`
  MODIFY `id_horario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `matriculas`
--
ALTER TABLE `matriculas`
  MODIFY `id_matricula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cursos`
--
ALTER TABLE `cursos`
  ADD CONSTRAINT `cursos_ibfk_1` FOREIGN KEY (`id_horario`) REFERENCES `horarios` (`id_horario`),
  ADD CONSTRAINT `cursos_ibfk_2` FOREIGN KEY (`id_docente`) REFERENCES `docentes` (`id_docente`);

--
-- Filtros para la tabla `matriculas`
--
ALTER TABLE `matriculas`
  ADD CONSTRAINT `matriculas_ibfk_1` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiantes` (`id_estudiante`),
  ADD CONSTRAINT `matriculas_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `cursos` (`id_curso`),
  ADD CONSTRAINT `matriculas_ibfk_3` FOREIGN KEY (`id_usuario_registro`) REFERENCES `usuarios` (`id_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
