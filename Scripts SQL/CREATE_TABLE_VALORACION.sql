CREATE TABLE `valoracion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `texto` varchar(500) NOT NULL,
  `notaCurricular` decimal(3,1),
  `notaExtracurricular` varchar(10),
  `oferta_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`oferta_id`),
  FOREIGN KEY (`oferta_id`) REFERENCES `oferta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;