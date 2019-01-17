CREATE TABLE `carpeta` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `noModificable` tinyint(1) NOT NULL,
  `actor_id` int(11),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;