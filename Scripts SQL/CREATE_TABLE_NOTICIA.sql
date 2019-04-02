CREATE TABLE `noticia` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `cuerpo` longtext,
  `fechaModificacion` datetime NOT NULL,
  `autor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`autor_id`) REFERENCES `actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;