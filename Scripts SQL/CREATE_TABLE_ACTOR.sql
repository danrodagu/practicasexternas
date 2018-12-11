CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidos` varchar(255) NOT NULL,
  `expedienteCerrado` tinyint(1) UNSIGNED NULL,
  `ofertaAsignada_id` int(11),
  `tutorAsignado_id` int(11),
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`ofertaAsignada_id`) REFERENCES `oferta` (`id`),
  FOREIGN KEY (`tutorAsignado_id`) REFERENCES `actor` (`id`),
  FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci