-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-12-2024 a las 19:57:22
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `shieldlinkmanager`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `herramientas`
--

CREATE TABLE `herramientas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `estado` enum('Disponible','Prestada','Mantenimiento') NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `cantidad` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `herramientas`
--

INSERT INTO `herramientas` (`id`, `nombre`, `descripcion`, `estado`, `tipo`, `cantidad`) VALUES
(1, 'flipper zero', 'herramienta multi uso de pentesting', 'Disponible', 'pentesting', 20),
(2, 'wifipineaple', 'dispositivo que pertime crear redes\nfalsas y obtener todo lo que se ingresa en esa red', 'Disponible', 'pentesting', 0),
(3, 'laptop', 'portatil gamer para competencias de ctf', 'Mantenimiento', 'iot', 20),
(4, 'hack rf', 'analizador de frecuencias rf', 'Disponible', 'pentesting', 1),
(5, 'badusb', 'usb maliciosas', 'Disponible', 'pentesting', 20),
(6, 'usb killer', 'una usb que tiene fuentes de energia \nque sobre carga una computadora', 'Mantenimiento', 'blackhat', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logs`
--

CREATE TABLE `logs` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `rol` varchar(20) DEFAULT NULL,
  `fecha_acceso` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos`
--

CREATE TABLE `prestamos` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `herramienta_id` int(11) NOT NULL,
  `fecha_prestamo` date NOT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  `estado` enum('Activo','Completado','Cancelado','Pendiente','Aprobado','Rechazado') NOT NULL,
  `descripcion_uso` varchar(255) NOT NULL,
  `cantidad_solicitada` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `prestamos`
--

INSERT INTO `prestamos` (`id`, `usuario_id`, `herramienta_id`, `fecha_prestamo`, `fecha_devolucion`, `estado`, `descripcion_uso`, `cantidad_solicitada`) VALUES
(1, 2, 5, '2024-06-04', '2024-06-09', 'Completado', 'prueba a computadora vulnerable', 3),
(2, 3, 5, '2024-11-24', '2024-11-25', 'Pendiente', 'prueba', 9),
(3, 3, 2, '2024-11-24', '2024-11-25', 'Pendiente', 'prueba', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `rol` enum('Estudiante','Profesor','Administrador') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `usuario`, `contrasena`, `rol`) VALUES
(1, 'esteban', 'admin', 'admin', 'Administrador'),
(2, 'marco', 'marc', 'marc', 'Estudiante'),
(3, 'gabriel', 'gab', 'gab', 'Profesor');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `herramientas`
--
ALTER TABLE `herramientas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_estado` (`estado`),
  ADD KEY `idx_tipo` (`tipo`);

--
-- Indices de la tabla `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_usuario_id` (`usuario_id`);

--
-- Indices de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_prestamos_usuario` (`usuario_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usuario` (`usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `herramientas`
--
ALTER TABLE `herramientas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `fk_logs_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD CONSTRAINT `fk_prestamos_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
