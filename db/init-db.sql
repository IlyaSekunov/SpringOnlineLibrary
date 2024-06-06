CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `year_of_birth` int NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `fio` varchar(200) NOT NULL,
  `birthday` int NOT NULL,
  PRIMARY KEY (`person_id`)
);

CREATE TABLE `book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `author` varchar(200) NOT NULL,
  `publish_year` int NOT NULL,
  `person_id` int DEFAULT NULL,
  `reservation_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `person_id` (`person_id`)
);