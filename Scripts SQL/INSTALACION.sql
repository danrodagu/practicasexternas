--
-- Creacion de la base de datos
--

DROP DATABASE IF EXISTS `gestion-practicas`;

CREATE SCHEMA `gestion-practicas`;

USE `gestion-practicas`;

--
-- Creacion de tablas
--

-- CREATE TABLE `hibernate_sequences` (
--  `sequence_name` varchar(255),
--  `sequence_next_hi_value` int(11)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) NOT NULL,
  FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `actor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `nif` varchar(9) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL, 
  `titulacion` varchar(255),
  `departamento` varchar(255), 
  `email` varchar(255) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  UNIQUE KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `oferta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `dotacion` decimal(19,2) NOT NULL,
  `duracion` decimal(19,2) NOT NULL,
  `horas` int(3) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `esCurricular` tinyint(1) NOT NULL,
  `pais` varchar(255) NOT NULL,
  `provincia` varchar(255),
  `localidad` varchar(255),
  `empresa` varchar(255) NOT NULL,
  `cifEmp` varchar(255) NOT NULL,
  `telefonoEmp` varchar(255) NOT NULL,
  `emailEmp` varchar(255) NOT NULL,
  `tutorEmp` varchar(255) NOT NULL,
  `enEvaluacion` tinyint(1) NOT NULL,
  `docuCerrada` tinyint(1) NOT NULL,
  `evaluada` tinyint(1) NOT NULL,
  `preacta` tinyint(1) NOT NULL,
  `actaFirmada` tinyint(1) NOT NULL,
  `expedienteCerrado` tinyint(1) NOT NULL,
  `alumnoAsignado_id` int(11) NOT NULL,
  `tutorAsignado_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`alumnoAsignado_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`tutorAsignado_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `carpeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `noModificable` tinyint(1) NOT NULL,
  `actor_id` int(11),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `asunto` varchar(255) NOT NULL,
  `cuerpo` longtext,
  `fecha` datetime NOT NULL,
  `leido` tinyint(1) NOT NULL,
  `carpeta_id` int(11) NOT NULL,
  `emisor_id` int(11) NOT NULL,
  `receptor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`emisor_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`receptor_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`carpeta_id`) REFERENCES `carpeta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `noticia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `cuerpo` longtext,
  `fechaModificacion` datetime NOT NULL,
  `autor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`autor_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `valoracion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `texto` varchar(500) NOT NULL,
  `notaCurricular` decimal(3,1),
  `notaExtracurricular` varchar(10),
  `oferta_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`oferta_id`),
  FOREIGN KEY (`oferta_id`) REFERENCES `oferta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `formato` varchar(10) NOT NULL,
  `archivo` LONGBLOB NOT NULL,
  `uploader_id` int(11) NOT NULL,
  `oferta_id` int(11),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uploader_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`oferta_id`) REFERENCES `oferta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `confirmationToken` varchar(255),
  `fechaCreacion` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Creacion de coordinador
--

INSERT INTO `gestion-practicas`.`useraccount` (`id`, `version`, `password`, `username`, `enabled`) VALUES ('1', '0', '$2a$10$fcf.d6byXUh7Yj9mHwN8bONkj0q5hrBCkNwicxbWAjaCilydY40Y.', 'coordi', '1');
INSERT INTO `gestion-practicas`.`useraccount_authorities` (`UserAccount_id`, `authority`) VALUES ('1','COORDINADOR');
INSERT INTO `gestion-practicas`.`actor` (`id`, `version`, `nombre`, `apellidos`, `userAccount_id`, `nif`, `titulacion`, `departamento`, `email`) VALUES ('2', '0', 'NombreCoordi', 'ApellidoCoordi', '1', 'DNICoordi', '', 'departamentoCoordi', 'coordi@us.es');
INSERT INTO `gestion-practicas`.`carpeta` (`id`, `version`, `nombre`, `noModificable`, `actor_id`) VALUES ('3', '0', 'Recibido', '1', '2');
INSERT INTO `gestion-practicas`.`carpeta` (`id`, `version`, `nombre`, `noModificable`, `actor_id`) VALUES ('4', '0', 'Enviado', '1', '2');
INSERT INTO `gestion-practicas`.`carpeta` (`id`, `version`, `nombre`, `noModificable`, `actor_id`) VALUES ('5', '0', 'Papelera', '1', '2');

--
-- Insertamos proximo valor de hibernate_sequences
--

-- INSERT INTO `gestion-practicas`.`hibernate_sequences` (`sequence_name`, `sequence_next_hi_value`) VALUES ('DomainEntity', '6');
