CREATE TABLE `token` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `confirmationToken` varchar(255),
  `fechaCreacion` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;