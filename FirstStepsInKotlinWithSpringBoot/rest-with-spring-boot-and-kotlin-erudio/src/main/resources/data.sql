----V1___create_person_table.sql
CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
)

--V2__Populate_Table_Person.sql
INSERT INTO `person` VALUES (2,'Rua dos Anjos10','Lucas','Male','Jesus'),
                            (3,'Rua dos Anjos','Pedro','Male','Luiz'),
                            (5,'Rua dos Anjos11','Luiza','Male','Jesus'),
                            (6,'Rua dos Simpios','Rebeca','female','Andrade'),
                            (8,'Africa do sul','Nelson','Male','Mandela'),
                            (9,'Rua dos Simpios','Nicola','male','Tesla');

--V3__create_books_table.sql
CREATE TABLE `books` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `author` longtext NOT NULL,
  `launch_date` datetime(6),
  `price` decimal(65,2) NOT NULL,
  `title` longtext  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--V4__Insert_Data_into_Books.sql
INSERT INTO `books` (`author`, `launch_date`, `price`, `title`) VALUES
	('Michael C. Feathers', '2017-11-29 13:50:05.878000', 49.00, 'Working effectively with legacy code'),
	('Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm', '2017-11-29 15:15:13.636000', 45.00, 'Design Patterns'),
	('Robert C. Martin', '2009-01-10 00:00:00.000000', 77.00, 'Clean Code'),
	('Crockford', '2017-11-07 15:09:01.674000', 67.00, 'JavaScript'),
	('Steve McConnell', '2017-11-07 15:09:01.674000', 58.00, 'Code complete'),
	('Martin Fowler e Kent Beck', '2017-11-07 15:09:01.674000', 88.00, 'Refactoring'),
	('Eric Freeman, Elisabeth Freeman, Kathy Sierra, Bert Bates', '2017-11-07 15:09:01.674000', 110.00, 'Head First Design Patterns'),
	('Eric Evans', '2017-11-07 15:09:01.674000', 92.00, 'Domain Driven Design'),
	('Brian Goetz e Tim Peierls', '2017-11-07 15:09:01.674000', 80.00, 'Java Concurrency in Practice'),
	('Susan Cain', '2017-11-07 15:09:01.674000', 123.00, 'O poder dos quietos'),
	('Roger S. Pressman', '2017-11-07 15:09:01.674000', 56.00, 'Engenharia de Software: uma abordagem profissional'),
	('Viktor Mayer-Schonberger e Kenneth Kukier', '2017-11-07 15:09:01.674000', 54.00, 'Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana'),
	('Richard Hunter e George Westerman', '2017-11-07 15:09:01.674000', 95.00, 'O verdadeiro valor de TI'),
	('Marc J. Schiller', '2017-11-07 15:09:01.674000', 45.00, 'Os 11 segredos de líderes de TI altamente influentes'),
	('Aguinaldo Aragon Fernandes e Vladimir Ferraz de Abreu', '2017-11-07 15:09:01.674000', 54.00, 'Implantando a governança de TI');

--V5__Create_Table_Permission.sql
CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

--V6__Insert_Data_In_Permission.sql
INSERT INTO `permission` (`description`) VALUES
	('ADMIN'),
	('MANAGER'),
	('COMMON_USER');

--V7__Create_Table_User.sql
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_name`)
) ENGINE=InnoDB;

--V8__Insert_Data_In_User.sql
INSERT INTO `users` (`user_name`, `full_name`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES
	('leandro', 'Leandro Costa', '19bbf735b27066f2f145e602624e1b24a3fbc54cd5dfd3143fc5feea6bdee9e139ca7332d4806b9f', b'1', b'1', b'1', b'1'),
	('flavio', 'Flavio Costa', '75ec349c1b0ef4ee7b249d0b83ae4861853f3aa77bce8c4b15f28cd43c6424ab4f29df431831bb0d', b'1', b'1', b'1', b'1');

--V9__Create_Table_User_Permission.sql
CREATE TABLE IF NOT EXISTS `user_permission` (
  `id_user` bigint(20) NOT NULL,
  `id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`id_user`,`id_permission`),
  KEY `fk_user_permission_permission` (`id_permission`),
  CONSTRAINT `fk_user_permission` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_user_permission_permission` FOREIGN KEY (`id_permission`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB;

--V10__Insert_Data_In_User_Permission.sql
INSERT INTO `user_permission` (`id_user`, `id_permission`) VALUES
	(1, 1),
	(2, 1),
	(1, 2);
