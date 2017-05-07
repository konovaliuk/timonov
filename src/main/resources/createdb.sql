CREATE DATABASE `probe` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `horse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `year` int(11) NOT NULL,
  `totalraces` int(11) NOT NULL,
  `wonraces` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('1', 'Light', '2010', '15', '3');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('2', 'Strong', '2012', '12', '2');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('3', 'Might', '2008', '20', '5');