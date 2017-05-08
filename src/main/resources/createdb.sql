CREATE DATABASE `probe` /*!40100 DEFAULT CHARACTER SET utf8 */;


CREATE TABLE `probe`.`horse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `year` int(11) NOT NULL,
  `totalraces` int(11) NOT NULL,
  `wonraces` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `probe`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `probe`.`location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `country_id_idx` (`country_id` ASC),
  CONSTRAINT `countryFK`
  FOREIGN KEY (`country_id`)
  REFERENCES `probe`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `probe`.`race_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `probe`.`race` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_id` INT NOT NULL,
  `location_id` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `typeFK_idx` (`type_id` ASC),
  INDEX `locationFK_idx` (`location_id` ASC),
  CONSTRAINT `typeFK`
  FOREIGN KEY (`type_id`)
  REFERENCES `probe`.`race_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `locationFK`
  FOREIGN KEY (`location_id`)
  REFERENCES `probe`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `probe`.`horse_in_race` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `race_id` INT NOT NULL,
  `horse_id` INT NOT NULL,
  `place` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `raceFK_idx` (`race_id` ASC),
  INDEX `horseFK_idx` (`horse_id` ASC),
  CONSTRAINT `raceFK`
  FOREIGN KEY (`race_id`)
  REFERENCES `probe`.`race` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `horseFK`
  FOREIGN KEY (`horse_id`)
  REFERENCES `probe`.`horse` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `probe`.`user_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `probe`.`account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `balance` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `probe`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_id` INT NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `account_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `usertypeFK_idx` (`type_id` ASC),
  INDEX `accountFK_idx` (`account_id` ASC),
  CONSTRAINT `usertypeFK`
  FOREIGN KEY (`type_id`)
  REFERENCES `probe`.`user_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `accountFK`
  FOREIGN KEY (`account_id`)
  REFERENCES `probe`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `probe`.`horse_odds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `horse_in_race_id` INT NOT NULL,
  `bet_type_id` INT NOT NULL,
  `odds_total` INT NOT NULL,
  `odds_chances` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `horseInRaceFK_idx` (`horse_in_race_id` ASC),
  INDEX `betTypeFK_idx` (`bet_type_id` ASC),
  CONSTRAINT `horseInRaceFK`
  FOREIGN KEY (`horse_in_race_id`)
  REFERENCES `probe`.`horse_in_race` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `betTypeFK`
  FOREIGN KEY (`bet_type_id`)
  REFERENCES `probe`.`bet_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `probe`.`bet_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`));


INSERT INTO `probe`.`country` (`id`, `name`) VALUES ('1', 'England');
INSERT INTO `probe`.`country` (`id`, `name`) VALUES ('2', 'Ireland');
INSERT INTO `probe`.`country` (`id`, `name`) VALUES ('3', 'USA');
INSERT INTO `probe`.`country` (`id`, `name`) VALUES ('4', 'South Africa');
INSERT INTO `probe`.`country` (`id`, `name`) VALUES ('5', 'Australia');


INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('1', 'Flamingo Park', '5');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('2', 'Southwell', '1');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('3', 'Chelmsford', '1');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('4', 'Winsdor', '1');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('5', 'Louisiana Downs ', '3');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('6', 'Will Rogers Downs', '3');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('7', 'Houston Race Park', '3');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('8', 'Adelaide River', '4');
INSERT INTO `probe`.`location` (`id`, `name`, `country_id`) VALUES ('9', 'Kilbeggan', '2');


INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('1', 'City Lighting', '2010', '61', '7');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('2', 'Black Beauty', '2012', '35', '3');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('3', 'Mighty Guy', '2011', '51', '6');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('4', 'Western Warrior', '2010', '65', '7');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('5', 'Diamond Mines', '2012', '35', '5');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('6', 'Iron Maden', '2011', '45', '7');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('7', 'Top Salute', '2013', '21', '3');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('8', 'Wings N Things', '2012', '29', '2');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('9', 'Molly Pitcher', '2011', '34', '4');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('10', 'Texas Ranger', '2013', '15', '2');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('11', 'Midnight Storm', '2014', '8', '1');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('12', 'Fire Spirit', '2013', '18', '2');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('13', 'Silver Star', '2012', '25', '4');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('14', 'Flying Rocky', '2011', '46', '5');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('15', 'Rocket Man', '2013', '18', '3');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('16', 'Fast Luna', '2015', '5', '0');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('17', 'Furious Harley', '2013', '30', '5');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('18', 'Lightning King', '2013', '28', '6');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('19', 'Incredible Apache', '2012', '38', '7');
INSERT INTO `probe`.`horse` (`id`, `name`, `year`, `totalraces`, `wonraces`) VALUES ('20', 'Milky Way', '2015', '10', '2');


INSERT INTO `probe`.`race_type` (`id`, `name`) VALUES ('1', 'open to bet');
INSERT INTO `probe`.`race_type` (`id`, `name`) VALUES ('2', 'closed to bet');
INSERT INTO `probe`.`race_type` (`id`, `name`) VALUES ('3', 'in process');
INSERT INTO `probe`.`race_type` (`id`, `name`) VALUES ('4', 'results are fixed');


INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('1', '4', '2', '2017-05-01');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('2', '4', '3', '2017-05-01');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('3', '3', '4', '2017-05-02');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('4', '2', '5', '2017-05-02');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('5', '1', '6', '2017-05-03');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('6', '1', '7', '2017-05-03');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('7', '1', '8', '2017-05-04');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('8', '1', '9', '2017-05-05');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('9', '1', '1', '2017-05-05');
INSERT INTO `probe`.`race` (`id`, `type_id`, `location_id`, `date`) VALUES ('10', '1', '3', '2017-05-06');


INSERT INTO `probe`.`user_type` (`id`, `name`) VALUES ('1', 'admin');
INSERT INTO `probe`.`user_type` (`id`, `name`) VALUES ('2', 'bookie');
INSERT INTO `probe`.`user_type` (`id`, `name`) VALUES ('3', 'client');


INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('1', '200');
INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('2', '500');
INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('3', '750');
INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('4', '1000');
INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('5', '2000');
INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('6', '3000');
INSERT INTO `probe`.`account` (`id`, `balance`) VALUES ('7', '5000');


INSERT INTO `probe`.`bet_type` (`id`, `name`) VALUES ('1', 'winner');
INSERT INTO `probe`.`bet_type` (`id`, `name`) VALUES ('2', 'first or second place');
INSERT INTO `probe`.`bet_type` (`id`, `name`) VALUES ('3', 'prize place');
INSERT INTO `probe`.`bet_type` (`id`, `name`) VALUES ('4', 'double express');
INSERT INTO `probe`.`bet_type` (`id`, `name`) VALUES ('5', 'triple express');


INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`) VALUES ('1', '1', 'admin', 'admin', 'Andrew Ashton');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`) VALUES ('2', '1', 'backup', 'backup', 'Bastian Shweinsteiger');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`) VALUES ('3', '2', 'bookie', 'bookie', 'Bob Dilan');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`) VALUES ('4', '2', 'bookmaker', 'bookmaker', 'Ban McCallister');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`) VALUES ('5', '2', 'reserve', 'reserve', 'Ron Atkinson');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('6', '3', 'user', 'user', 'Usain Bolt', '1');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('7', '3', 'white', 'white', 'Dwhight York', '2');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('8', '3', 'black', 'black', 'Bella Clifford', '3');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('9', '3', 'green', 'green', 'Gregory Tomson', '4');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('10', '3', 'red', 'red', 'Rosa Richards', '5');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('11', '3', 'violet', 'violet', 'Violeta Smith', '6');
INSERT INTO `probe`.`user` (`id`, `type_id`, `login`, `password`, `name`, `account_id`) VALUES ('12', '3', 'orange', 'orange', 'Orlando Forrest', '7');


INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`, `place`) VALUES ('1', '1', '1', '3');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`, `place`) VALUES ('2', '1', '2', '4');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`, `place`) VALUES ('3', '1', '3', '2');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`, `place`) VALUES ('4', '1', '4', '1');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`, `place`) VALUES ('5', '1', '5', '5');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('6', '3', '6');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('7', '3', '7');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('8', '3', '8');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('9', '3', '9');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('10', '3', '10');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('11', '4', '11');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('12', '4', '12');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('13', '4', '13');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('14', '4', '14');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('15', '4', '15');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('16', '5', '16');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('17', '5', '17');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('18', '5', '18');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('19', '5', '19');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('20', '6', '20');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('21', '6', '1');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('22', '6', '3');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('23', '6', '5');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('24', '6', '7');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('25', '6', '9');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('26', '7', '2');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('27', '7', '4');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('28', '7', '6');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('29', '7', '8');
INSERT INTO `probe`.`horse_in_race` (`id`, `race_id`, `horse_id`) VALUES ('30', '7', '10');


INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('1', '1', '1', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('2', '1', '2', '3', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('3', '1', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('4', '2', '1', '8', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('5', '2', '2', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('6', '2', '3', '5', '2');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('7', '3', '1', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('8', '3', '2', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('9', '3', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('10', '4', '1', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('11', '4', '2', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('12', '4', '3', '7', '5');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('13', '5', '1', '10', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('14', '5', '2', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('15', '5', '3', '3', '1');

INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('16', '6', '1', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('17', '6', '2', '3', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('18', '6', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('19', '7', '1', '8', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('20', '7', '2', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('21', '7', '3', '5', '2');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('22', '8', '1', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('23', '8', '2', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('24', '8', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('25', '9', '1', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('26', '9', '2', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('27', '9', '3', '7', '5');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('28', '10', '1', '10', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('29', '10', '2', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('30', '10', '3', '3', '1');

INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('31', '11', '1', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('32', '11', '2', '3', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('33', '11', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('34', '12', '1', '8', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('35', '12', '2', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('36', '12', '3', '5', '2');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('37', '13', '1', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('38', '13', '2', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('39', '13', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('40', '14', '1', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('41', '14', '2', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('42', '14', '3', '7', '5');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('43', '15', '1', '10', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('44', '15', '2', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('45', '15', '3', '3', '1');

INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('46', '16', '1', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('47', '16', '2', '3', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('48', '16', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('49', '17', '1', '8', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('50', '17', '2', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('51', '17', '3', '5', '2');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('52', '18', '1', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('53', '18', '2', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('54', '18', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('55', '19', '1', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('56', '19', '2', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('57', '19', '3', '7', '5');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('58', '20', '1', '10', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('59', '20', '2', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('60', '20', '3', '3', '1');

INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('61', '21', '1', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('62', '21', '2', '3', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('63', '21', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('64', '22', '1', '8', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('65', '22', '2', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('66', '22', '3', '5', '2');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('67', '23', '1', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('68', '23', '2', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('69', '23', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('70', '24', '1', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('71', '24', '2', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('72', '24', '3', '7', '5');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('73', '25', '1', '10', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('74', '25', '2', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('75', '25', '3', '3', '1');

INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('76', '26', '1', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('77', '26', '2', '3', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('78', '26', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('79', '27', '1', '8', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('80', '27', '2', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('81', '27', '3', '5', '2');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('82', '28', '1', '4', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('83', '28', '2', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('84', '28', '3', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('85', '29', '1', '2', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('86', '29', '2', '5', '3');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('87', '29', '3', '7', '5');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('88', '30', '1', '10', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('89', '30', '2', '6', '1');
INSERT INTO `probe`.`horse_odds` (`id`, `horse_in_race_id`, `bet_type_id`, `odds_total`, `odds_chances`) VALUES ('90', '30', '3', '3', '1');