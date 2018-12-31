DROP DATABASE IF EXISTS `gestion-practicas`;

CREATE SCHEMA `gestion-practicas`;

USE `gestion-practicas`;

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255),
  `sequence_next_hi_value` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) NOT NULL,
  FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,  
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `oferta` (
  `id` int(11) NOT NULL,
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
  `expedienteCerrado` tinyint(1) NOT NULL,
  `alumnoAsignado_id` int(11) NOT NULL,
  `tutorAsignado_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`alumnoAsignado_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`tutorAsignado_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `carpeta` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `noModificable` tinyint(1) NOT NULL,
  `actor_id` int(11),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `asunto` varchar(255) NOT NULL,
  `cuerpo` longtext,
  `fecha` datetime NOT NULL,
  `carpeta_id` int(11) NOT NULL,
  `emisor_id` int(11) NOT NULL,
  `receptor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`emisor_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`receptor_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`carpeta_id`) REFERENCES `carpeta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `valoracion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `cuerpo` varchar(255) NOT NULL,
  `esFinal` tinyint(1) NOT NULL,
  `alumno_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`alumno_id`),
  FOREIGN KEY (`alumno_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `documento` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `formato` varchar(10) NOT NULL,
  `archivo` LONGBLOB NOT NULL,
  `uploader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uploader_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

