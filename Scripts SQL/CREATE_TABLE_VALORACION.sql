CREATE TABLE `valoracion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `cuerpo` varchar(255) NOT NULL,
  `esFinal` bit(1) NOT NULL,
  `alumno_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`alumno_id`),
  FOREIGN KEY (`alumno_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci