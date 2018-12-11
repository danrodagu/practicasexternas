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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci