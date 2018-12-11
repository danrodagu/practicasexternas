CREATE TABLE `oferta` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `dotacion` decimal(19,2) NOT NULL,
  `duracion` decimal(19,2) NOT NULL,
  `empresa` varchar(255) NOT NULL,
  `esCurricular` bit(1) NOT NULL,
  `pais` varchar(255) NOT NULL,
  `provincia` varchar(255),
  `localidad` varchar(255),  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci