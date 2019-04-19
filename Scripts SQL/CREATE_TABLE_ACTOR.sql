CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nif` varchar(9) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL, 
  `titulacion` varchar(255),
  `departamento` varchar(255), 
  `email` varchar(255) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;