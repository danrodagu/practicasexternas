CREATE TABLE `documento` (
  `id` int(11) NOT NULL,
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