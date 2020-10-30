-- -----------------------------------------------------
-- Schema CSCI5308_7_DEVINT
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSCI5308_7_DEVINT` DEFAULT CHARACTER SET latin1 ;
USE `CSCI5308_7_DEVINT` ;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`User`
-- -----------------------------------------------------
CREATE TABLE `User` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`League`
-- -----------------------------------------------------
CREATE TABLE `League` (
  `idLeague` int(11) NOT NULL AUTO_INCREMENT,
  `leagueName` varchar(45) DEFAULT NULL,
  `createdBy` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLeague`),
  KEY `createdBy` (`createdBy`),
  CONSTRAINT `createdBy` FOREIGN KEY (`createdBy`) REFERENCES `User` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Conference`
-- -----------------------------------------------------
CREATE TABLE `Conference` (
  `idConference` int(11) NOT NULL AUTO_INCREMENT,
  `conferenceName` varchar(45) DEFAULT NULL,
  `league` int(11) DEFAULT NULL,
  PRIMARY KEY (`idConference`),
  KEY `league` (`league`),
  CONSTRAINT `league` FOREIGN KEY (`league`) REFERENCES `League` (`idLeague`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Division`
-- -----------------------------------------------------
CREATE TABLE `Division` (
  `idDivision` int(11) NOT NULL AUTO_INCREMENT,
  `divisionName` varchar(45) DEFAULT NULL,
  `conference` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDivision`),
  KEY `conference` (`conference`),
  CONSTRAINT `conference` FOREIGN KEY (`conference`) REFERENCES `Conference` (`idConference`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Season`
-- -----------------------------------------------------
CREATE TABLE `Season` (
  `idSeason` int(11) NOT NULL AUTO_INCREMENT,
  `seasonName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idSeason`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`FreeAgent`
-- -----------------------------------------------------
CREATE TABLE `FreeAgent` (
  `league` int(11) DEFAULT NULL,
  `freeAgentId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`freeAgentId`),
  KEY `league` (`league`),
  CONSTRAINT `FreeAgent_ibfk_1` FOREIGN KEY (`league`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Player`
-- -----------------------------------------------------
CREATE TABLE `Player` (
  `idPlayer` int(11) NOT NULL AUTO_INCREMENT,
  `playerName` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  `captain` tinyint(4) DEFAULT NULL,
  `teamId` int(11) DEFAULT NULL,
  `freeAgentId` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `skating` int(11) DEFAULT NULL,
  `shooting` int(11) DEFAULT NULL,
  `checking` int(11) DEFAULT NULL,
  `saving` int(11) DEFAULT NULL,
  `isInjured` tinyint(4) DEFAULT NULL,
  `injuryStartDate` date DEFAULT NULL,
  `injuryDatesRange` int(11) DEFAULT NULL,
  `strength` double DEFAULT NULL,
  PRIMARY KEY (`idPlayer`)
) ENGINE=InnoDB AUTO_INCREMENT=920 DEFAULT CHARSET=latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Player_stats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Player_stats` (
  `playerId` INT(11) NOT NULL,
  `seasonId` INT(11) NOT NULL,
  `teamId` INT(11) NULL DEFAULT NULL,
  `captain` TINYINT(4) NULL DEFAULT NULL,
  `points` VARCHAR(45) NULL DEFAULT NULL,
  `goals` VARCHAR(45) NULL DEFAULT NULL,
  `assists` VARCHAR(45) NULL DEFAULT NULL,
  `freeAgentId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`playerId`, `seasonId`),
  INDEX `player_season` (`seasonId` ASC) VISIBLE,
  INDEX `player_team` (`teamId` ASC) VISIBLE,
  CONSTRAINT `player`
    FOREIGN KEY (`playerId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Player` (`idPlayer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `player_season`
    FOREIGN KEY (`seasonId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Season` (`idSeason`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Staff` (
  `idStaff` INT(11) NOT NULL AUTO_INCREMENT,
  `staffName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idStaff`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Team`
-- -----------------------------------------------------
CREATE TABLE `Team` (
  `idTeam` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(45) DEFAULT NULL,
  `division` int(11) DEFAULT NULL,
  `aiTeam` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idTeam`),
  KEY `division` (`division`),
  CONSTRAINT `division` FOREIGN KEY (`division`) REFERENCES `Division` (`idDivision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Staff_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Staff_status` (
  `staffId` INT(11) NOT NULL,
  `seasonId` INT(11) NOT NULL,
  `teamId` INT(11) NOT NULL,
  `position` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`staffId`, `seasonId`, `teamId`),
  INDEX `staff_season` (`seasonId` ASC) VISIBLE,
  INDEX `staff_team` (`teamId` ASC) VISIBLE,
  CONSTRAINT `staff`
    FOREIGN KEY (`staffId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Staff` (`idStaff`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `staff_season`
    FOREIGN KEY (`seasonId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Season` (`idSeason`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `staff_team`
    FOREIGN KEY (`teamId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Team` (`idTeam`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Team_stats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Team_stats` (
  `seasonId` INT(11) NOT NULL,
  `teamId` INT(11) NOT NULL,
  `win` INT(11) NULL DEFAULT NULL,
  `lose` INT(11) NULL DEFAULT NULL,
  `rate` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`seasonId`, `teamId`),
  INDEX `team_stats` (`teamId` ASC) VISIBLE,
  CONSTRAINT `team_season`
    FOREIGN KEY (`seasonId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Season` (`idSeason`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `team_stats`
    FOREIGN KEY (`teamId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Team` (`idTeam`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

USE `CSCI5308_7_DEVINT` ;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Aging`
-- -----------------------------------------------------

CREATE TABLE `Aging` (
  `idAging` int(11) NOT NULL AUTO_INCREMENT,
  `averageRetirementAge` int(11) NOT NULL,
  `maximumAge` int(11) NOT NULL,
  `leagueId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAging`),
  KEY `aging_league` (`leagueId`),
  CONSTRAINT `aging_league` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Coach`
-- -----------------------------------------------------

CREATE TABLE `Coach` (
  `idCoach` int(11) NOT NULL AUTO_INCREMENT,
  `teamId` int(11) DEFAULT NULL,
  `coachName` varchar(45) DEFAULT NULL,
  `skating` double DEFAULT NULL,
  `shooting` double DEFAULT NULL,
  `checking` double DEFAULT NULL,
  `saving` double DEFAULT NULL,
  `leagueId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCoach`),
  KEY `coach_league` (`leagueId`),
  CONSTRAINT `coach_league` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Manager`
-- -----------------------------------------------------
CREATE TABLE `Manager` (
  `idManager` int(11) NOT NULL AUTO_INCREMENT,
  `teamId` int(11) DEFAULT NULL,
  `managerName` varchar(45) DEFAULT NULL,
  `leagueId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idManager`),
  KEY `manager_league` (`leagueId`),
  CONSTRAINT `manager_league` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Event`
-- -----------------------------------------------------

CREATE TABLE `Event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seasonStartDate` date DEFAULT NULL,
  `tradeDeadline` date DEFAULT NULL,
  `endOfRegularSeason` date DEFAULT NULL,
  `playOffStartDate` date DEFAULT NULL,
  `lastDayStanleyCup` date DEFAULT NULL,
  `nextSeasonDate` date DEFAULT NULL,
  `leagueid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Game`
-- -----------------------------------------------------
CREATE TABLE `Game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gamedate` date DEFAULT NULL,
  `team1` varchar(45) DEFAULT NULL,
  `team2` varchar(45) DEFAULT NULL,
  `played` tinyint(4) DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `leagueid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`GameResolver`
-- -----------------------------------------------------
CREATE TABLE `GameResolver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leagueid` int(11) DEFAULT NULL,
  `randonWinChance` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Injury`
-- -----------------------------------------------------
CREATE TABLE `Injury` (
  `idInjury` int(11) NOT NULL AUTO_INCREMENT,
  `randomInjuryChance` double DEFAULT NULL,
  `injuryDaysLow` int(11) DEFAULT NULL,
  `injuryDaysHigh` int(11) DEFAULT NULL,
  `leagueId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInjury`),
  KEY `injury_league` (`leagueId`),
  CONSTRAINT `injury_league` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`TeamStanding`
-- -----------------------------------------------------
CREATE TABLE `TeamStanding` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `point` int(11) DEFAULT NULL,
  `teamName` varchar(45) DEFAULT NULL,
  `wins` int(11) DEFAULT NULL,
  `loss` int(11) DEFAULT NULL,
  `ties` int(11) DEFAULT NULL,
  `leagueid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Trading`
-- -----------------------------------------------------
CREATE TABLE `Trading` (
  `idTrading` int(11) NOT NULL AUTO_INCREMENT,
  `leagueId` int(11) DEFAULT NULL,
  `lossPoint` int(11) DEFAULT NULL,
  `randomTradeOfferChance` float DEFAULT NULL,
  `maxPlayersPerTrade` int(11) DEFAULT NULL,
  `randomAcceptanceChance` float DEFAULT NULL,
  PRIMARY KEY (`idTrading`),
  KEY `leagueId` (`leagueId`),
  CONSTRAINT `leagueId` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Training`
-- -----------------------------------------------------
CREATE TABLE `Training` (
  `idTraining` int(11) NOT NULL AUTO_INCREMENT,
  `daysUntilStatCheck` int(11) NOT NULL,
  `leagueId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTraining`),
  KEY `leagueId` (`leagueId`),
  CONSTRAINT `Training_ibfk_1` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`TradingOffer`
-- -----------------------------------------------------
CREATE TABLE `TradingOffer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leagueId` int(11) DEFAULT NULL,
  `tradingId` int(11) DEFAULT NULL,
  `fromTeamId` int(11) DEFAULT NULL,
  `toTeamId` int(11) DEFAULT NULL,
  `fromPlayerId` int(11) DEFAULT NULL,
  `toPlayerId` int(11) DEFAULT NULL,
  `seasonId` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `leagueId` (`leagueId`),
  KEY `tradingId` (`tradingId`),
  KEY `fromTeamId` (`fromTeamId`),
  KEY `toTeamId` (`toTeamId`),
  KEY `fromPlayerId` (`fromPlayerId`),
  KEY `toPlayerId` (`toPlayerId`),
  KEY `seasonId` (`seasonId`),
  CONSTRAINT `TradingOffer_ibfk_1` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`),
  CONSTRAINT `TradingOffer_ibfk_2` FOREIGN KEY (`tradingId`) REFERENCES `Trading` (`idTrading`),
  CONSTRAINT `TradingOffer_ibfk_3` FOREIGN KEY (`fromTeamId`) REFERENCES `Team` (`idTeam`),
  CONSTRAINT `TradingOffer_ibfk_4` FOREIGN KEY (`toTeamId`) REFERENCES `Team` (`idTeam`),
  CONSTRAINT `TradingOffer_ibfk_5` FOREIGN KEY (`fromPlayerId`) REFERENCES `Player` (`idPlayer`),
  CONSTRAINT `TradingOffer_ibfk_6` FOREIGN KEY (`toPlayerId`) REFERENCES `Player` (`idPlayer`),
  CONSTRAINT `TradingOffer_ibfk_7` FOREIGN KEY (`seasonId`) REFERENCES `Season` (`idSeason`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- -----------------------------------------------------
-- procedure AddConference
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddConference`(IN conferenceName VARCHAR(45),IN leagueId INT, OUT conferenceId INT)
BEGIN
	Insert into Conference(conferenceName, league)
    VALUES (conferenceName,leagueId);
    SET conferenceId := LAST_INSERT_ID();
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddDivision
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddDivision`(IN divisionName VARCHAR(45), In conferenceId INT, OUT divisionId INT)
BEGIN
	Insert into Division(divisionName, conference)
    VALUES (divisionName,conferenceId);
    SET divisionId := LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddFreeAgent
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddFreeAgent`( In leagueId INT, In seasonId INT, OUT freeAgentId INT)
BEGIN
	Insert into FreeAgent(league, season)
    VALUES (leagueId,seasonId);
    Set freeAgentId := LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddLeague
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddLeague`(IN leagueName VARCHAR(45), In userId INT, OUT leagueId INT)
BEGIN
	Insert into League(leagueName, createdBy)
    VALUES (leagueName,userId);
    SET leagueId := LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddPlayer
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddPlayer`(IN teamId INT, IN freeAgentId INT, IN seasonId INT, IN playerName VARCHAR(45), In position VARCHAR(45), IN captain boolean, OUT playerId INT)
BEGIN
    
	Insert into Player(playerName,position)
    VALUES (playerName,position);
    
    SET playerId := LAST_INSERT_ID();
    
	Insert into Player_stats(playerId, seasonId, teamId, freeAgentId, captain)
    VALUES (playerId, seasonId, teamId, freeAgentId, captain);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddSeason
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddSeason`(IN seasonName VARCHAR(45), OUT seasonId INT)
BEGIN
	Insert into Season(seasonName)
    VALUES (seasonName);
    SET seasonId := LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddStaff
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddStaff`(IN teamId INT, IN seasonId INT, IN staffName VARCHAR(45), In position VARCHAR(45), OUT staffId INT)
BEGIN

	DECLARE staffId INT;
    
	Insert into Staff(staffName)
    VALUES (staffName);
    
    SET staffId := LAST_INSERT_ID();
    
	Insert into Staff_status(staffId, seasonId, teamId, position)
    VALUES (staffId, seasonId, teamId, position);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddTeam
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddTeam`(IN teamName VARCHAR(45), In divisionId INT, IN headCoach VARCHAR(45),IN generalManager VARCHAR(45), OUT teamId INT)
BEGIN
	Insert into Team(teamName, division,headCoach,generalManager)
    VALUES (teamName,divisionId,headCoach, generalManager);
    SET teamId := LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure AddUser
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddUser`(IN userName VARCHAR(45),IN password VARCHAR(45), OUT userId INT)
BEGIN
	Insert into User(userName, password)
    VALUES (userName, password);
    SET userId := LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadConferenceById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadConferenceById`(IN cId INT, OUT conferenceId VARCHAR(45), OUT conferenceName VARCHAR(45), OUT leagueId INT)
BEGIN
	select Conference.IdConference, Conference.conferenceName, Conference.league from Conference
    where Conference.IdConference = cId
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadConferenceByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadConferenceByName`(IN cName VARCHAR(45),OUT conferenceId INT, OUT conferenceName VARCHAR(45), OUT leagueId INT)
BEGIN
	select Conference.IdConference, Conference.conferenceName, Conference.league into conferenceId, conferenceName, leagueId from Conference
    where Conference.conferenceName = cName
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadConferenceListByLeagueId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadConferenceListByLeagueId`(IN leagueId INT)
BEGIN
	select * from Conference where league = leagueId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadDivisionById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadDivisionById`(IN dId INT, OUT divisionId INT, OUT divisionName varchar(45), OUT conferenceId INT)
BEGIN
	select Division.idDivision, Division.divisionName, Division.conference from Division
    where Division.idDivision = dId
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadDivisionByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadDivisionByName`(IN dName VARCHAR(45), OUT divisionId INT, OUT divisionName varchar(45), OUT conferenceId INT)
BEGIN
	select Division.idDivision, Division.divisionName, Division.conference into divisionId, divisionName,conferenceId from Division
    where Division.divisionName = dName
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadDivisionListByConferenceId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadDivisionListByConferenceId`(IN conferenceId INT)
BEGIN
	select * from Division where conference = conferenceId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadFreeAgentByLeagueId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadFreeAgentByLeagueId`( In lId INT, OUT freeAgentId INT, OUT leagueId INT, OUT seasonId INT)
BEGIN
    SELECT  FreeAgent.freeAgentId, FreeAgent.league, FreeAgent.season into freeAgentId, leagueId, seasonId from FreeAgent
    where FreeAgent.league = lId
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadLeagueById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadLeagueById`(IN lId INT, OUT leagueId INT, OUT leagueName VARCHAR(45), OUT userId INT)
BEGIN
	select League.idLeague, League.leagueName, League.createdBy into 
    @leagueId, @leagueName, @userId from League
    where League.idLeague = lId
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadLeagueByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadLeagueByName`(IN lname VARCHAR(45), OUT leagueId INT, OUT leagueName VARCHAR(45), OUT userId INT)
BEGIN
	select League.idLeague, League.leagueName, League.createdBy into leagueId, leagueName, userId from League
    where League.leagueName = lName
    LIMIT 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadLeagueByNameUserId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadLeagueByNameUserId`(IN lname VARCHAR(45), IN userId INT)
BEGIN
	select League.idLeague, League.leagueName, League.createdBy 
    from League
    where League.leagueName = lName and League.createdBy = userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadLeagueListByUserId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadLeagueListByUserId`(IN userId INT)
BEGIN
	select * from League where createdBy = userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadPlayerById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadPlayerById`(IN pId INT, OUT idPlayer INT, OUT playerName Varchar(45), OUT position varchar(45),OUT captain Boolean)
BEGIN

SELECT Player.idPlayer, Player.playerName, Player.position,Player_stats.captain
FROM Player
INNER JOIN Player_stats ON Player.idPlayer = Player_stats.playerId
where Player.idPlayer = pId
limit 1;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadPlayerByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadPlayerByName`(IN pName varchar(45), OUT idPlayer INT, OUT playerName Varchar(45), OUT position varchar(45),OUT captain Boolean, OUT teamId INT, OUT freeAgentId INT)
BEGIN

SELECT Player.idPlayer, Player.playerName, Player.position,Player_stats.captain, Player_stats.teamId, Player_stats.freeAgentId
into idPlayer, playerName, position, captain, teamId, freeAgentId
FROM Player
INNER JOIN Player_stats ON Player.idPlayer = Player_stats.playerId
where Player.playerName = pName
limit 1;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadPlayerListByFreeAgentId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadPlayerListByFreeAgentId`(IN freeAgentId INT)
BEGIN
	select p.idPlayer, p.playerName, p.position, s.captain from Player p, Player_stats s 
	where p.idPlayer = s.playerId
	and s.freeAgentId = freeAgentId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadPlayerListByTeamId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadPlayerListByTeamId`(IN teamId INT)
BEGIN
	select p.idPlayer, p.playerName, p.position, s.captain from Player p, Player_stats s 
	where p.idPlayer = s.playerId
	and s.teamId = teamId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadSeasonById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadSeasonById`(IN sId VARCHAR(45), OUT seasonId INT, OUT seasonName VARCHAR(45))
BEGIN
	select Season.idSeason, Season.seasonName from Season
    where Season.idSeason = sId
    Limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadSeasonByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadSeasonByName`(IN sName VARCHAR(45), OUT seasonId INT, OUT seasonName VARCHAR(45))
BEGIN
	select Season.idSeason, Season.seasonName into seasonId, seasonName from Season
    where Season.seasonName = sName
    Limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadStaffById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadStaffById`(IN sId INT, OUT idStaff INT, OUT staffName varchar(45), OUT position varchar(45))
BEGIN

SELECT Staff.idStaff, Staff.staffName, Staff_status.position
FROM Staff
INNER JOIN Staff_status ON Staff.idStaff = Staff_status.staffId
where Staff.idStaff = sId
limit 1;
    
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadTeamById
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTeamById`(IN tId INT, OUT teamId INT, OUT teamName varchar(45), OUT divisionId INT)
BEGIN
	select Team.idTeam, Team.teamName, Team.division from Team
    where Team.idTeam = tId
    limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadTeamByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTeamByName`(IN tName VARCHAR(45), OUT teamId INT, OUT teamName varchar(45), OUT divisionId INT, OUT headCoach VARCHAR(45), OUT generalManager VARCHAR(45))
BEGIN
	select Team.idTeam, Team.teamName, Team.division, Team.headCoach, Team.generalManager into teamId,teamName,divisionId,headCoach,generalManager from Team
    where Team.teamName = tName
    Limit 1;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadTeamListByDivisionId
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTeamListByDivisionId`(IN divisionId INT)
BEGIN
	select * from Team where division = divisionId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Related to manager
-- -----------------------------------------------------
DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddManager`(IN teamId INT,  IN managerName varchar(45), IN leagueId INT, OUT managerId INT)
BEGIN

	Insert into Manager(teamId, managerName,leagueId)
    VALUES (teamId, managerName,leagueId);

    SET managerId := LAST_INSERT_ID();

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadManagerByTeamId`(IN tId INT)
BEGIN
	select * from Manager where teamId = tId;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadManagerListByLeagueId`(IN lId INT)
BEGIN
	select * from Manager where leagueId = lId and teamId = 0;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- procedure Related to Training
-- -----------------------------------------------------

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddTraining`(IN daysUntilStatCheck INT, IN leagueId INT , OUT idTraining INT)
BEGIN
	Insert into Training(daysUntilStatCheck,leagueId)
    VALUES (daysUntilStatCheck,leagueId);

    SET idTraining := LAST_INSERT_ID();
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTrainingByLeagueId`(IN idL INT)
BEGIN
	select * from Training where leagueId=idL;
END$$
DELIMITER ;

-- -----------------------------------------------------
-- procedure LoadUserByName
-- -----------------------------------------------------

DELIMITER $$
USE `CSCI5308_7_DEVINT`$$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadUserByName`(IN userName VARCHAR(45), OUT userId INT, OUT password VARCHAR(45))
BEGIN
	select User.idUser, User.password into userId, password from User 
    where User.userName=userName
    LIMIT 1;
END$$

DELIMITER ;

CREATE TABLE `CSCI5308_7_DEVINT`.`Manager` (
  `idManager` INT NOT NULL AUTO_INCREMENT,
  `teamId` INT NULL,
  `freeAgentId` INT NULL,
  `managerName` VARCHAR(45) NULL,
  PRIMARY KEY (`idManager`),
  CONSTRAINT `manager_team`
    FOREIGN KEY (`teamId`)
    REFERENCES `CSCI5308_7_DEVINT`.`Team` (`idTeam`),
  CONSTRAINT `manager_freeagent`
    FOREIGN KEY (`freeAgentId`)
    REFERENCES `CSCI5308_7_DEVINT`.`FreeAgent` (`freeAgentId`));

CREATE TABLE `Coach` (
  `idCoach` int(11) NOT NULL AUTO_INCREMENT,
  `teamId` int(11) DEFAULT NULL,
  `freeAgentId` int(11) DEFAULT NULL,
  `coachName` varchar(45) DEFAULT NULL,
  `skating` float DEFAULT NULL,
  `shooting` float DEFAULT NULL,
  `checking` float DEFAULT NULL,
  `saving` float DEFAULT NULL,
  PRIMARY KEY (`idCoach`),
  KEY `coach_team` (`teamId`),
  KEY `coach_freeagent` (`freeAgentId`),
  CONSTRAINT `coach_freeagent` FOREIGN KEY (`freeAgentId`) REFERENCES `FreeAgent` (`freeAgentId`),
  CONSTRAINT `coach_team` FOREIGN KEY (`teamId`) REFERENCES `Team` (`idTeam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `CSCI5308_7_DEVINT`.`Coach`
DROP FOREIGN KEY `coach_team`,
DROP FOREIGN KEY `coach_freeagent`;
ALTER TABLE `CSCI5308_7_DEVINT`.`Coach`
DROP INDEX `coach_freeagent` ,
DROP INDEX `coach_team` ;

ALTER TABLE `CSCI5308_7_DEVINT`.`Coach`
CHANGE COLUMN `skating` `skating` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `shooting` `shooting` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `checking` `checking` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `saving` `saving` DOUBLE NULL DEFAULT NULL ;

ALTER TABLE `CSCI5308_7_DEVINT`.`Coach`
ADD COLUMN `leagueId` INT NULL AFTER `saving`;

ALTER TABLE `CSCI5308_7_DEVINT`.`Coach`
ADD CONSTRAINT `coach_league`
  FOREIGN KEY (`leagueId`)
  REFERENCES `CSCI5308_7_DEVINT`.`League` (`idLeague`);

ALTER TABLE `CSCI5308_7_DEVINT`.`Manager`
DROP FOREIGN KEY `manager_team`,
DROP FOREIGN KEY `manager_freeagent`;
ALTER TABLE `CSCI5308_7_DEVINT`.`Manager`
DROP INDEX `manager_freeagent` ,
DROP INDEX `manager_team` ;

ALTER TABLE `CSCI5308_7_DEVINT`.`Manager`
ADD COLUMN `leagueId` INT NULL;

ALTER TABLE `CSCI5308_7_DEVINT`.`Manager`
ADD CONSTRAINT `manager_league`
  FOREIGN KEY (`leagueId`)
  REFERENCES `CSCI5308_7_DEVINT`.`League` (`idLeague`);

ALTER TABLE `CSCI5308_7_DEVINT`.`Coach`
DROP COLUMN `freeAgentId`;

ALTER TABLE `CSCI5308_7_DEVINT`.`Manager`
DROP COLUMN `freeAgentId`;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddCoach`(IN teamId INT, IN freeAgentId INT, IN coachName varchar(45), IN skating Double, IN shooting Double, IN checking Double, IN saving Double, IN leagueId INT, OUT coachId INT)
BEGIN

	Insert into Coach(teamId, coachName, skating, shooting, checking, saving, leagueId)
    VALUES (teamId,coachName, skating, shooting, checking, saving, leagueId);

    SET coachId := LAST_INSERT_ID();

END $$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadCoachListByLeagueId`(IN lId INT)
BEGIN
	select * from Coach where leagueId = lId;
END $$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddManager`(IN teamId INT,  IN managerName varchar(45), IN leagueId INT, OUT managerId INT)
BEGIN

	Insert into Manager(teamId, managerName,leagueId)
    VALUES (teamId, managerName,leagueId);

    SET managerId := LAST_INSERT_ID();

END $$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadManagerListByLeagueId`(IN lId INT)
BEGIN
	select * from Manager where leagueId = lId;
END $$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddPlayer`(IN teamId INT, IN freeAgentId INT, IN seasonId INT, IN playerName VARCHAR(45), In position VARCHAR(45), IN captain boolean, IN skating INT, IN shooting INT, IN checking INT, IN saving INT,OUT playerId INT)
BEGIN

	Insert into Player(playerName,position)
    VALUES (playerName,position);

    SET playerId := LAST_INSERT_ID();

	Insert into Player_stats(playerId, seasonId, teamId, captain, age, skating, shooting, freeAgentId, checking, saving)
    VALUES (playerId, seasonId, teamId, captain, age, skating, shooting, freeAgentId, checking, saving);

END $$
DELIMITER ;

ALTER TABLE `Team`
DROP COLUMN `generalManager`;

ALTER TABLE `Team`
DROP COLUMN `headCoach`;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddTeam`(IN teamName Varchar(45), IN divisionId INT, OUT teamId INT)
BEGIN

	Insert into Team(teamName, division)
    VALUES (teamName, divisionId);

    SET teamId := LAST_INSERT_ID();

END $$
DELIMITER ;

ALTER TABLE `CSCI5308_7_DEVINT`.`League`
ADD COLUMN `averageRetirementAge` INT NULL AFTER `createdBy`,
ADD COLUMN `maximumAge` INT NULL AFTER `averageRetirementAge`,
ADD COLUMN `randomWinChance` DOUBLE NULL AFTER `maximumAge`,
ADD COLUMN `randomInjuryChance` DOUBLE NULL AFTER `randomWinChance`,
ADD COLUMN `injuryDaysLow` INT NULL AFTER `randomInjuryChance`,
ADD COLUMN `injuryDaysHigh` INT NULL AFTER `injuryDaysLow`,
ADD COLUMN `daysUntilCheck` INT NULL AFTER `injuryDaysHigh`,
ADD COLUMN `lossPoint` INT NULL AFTER `daysUntilCheck`,
ADD COLUMN `randomTradeOfferChance` DOUBLE NULL AFTER `lossPoint`,
ADD COLUMN `maxPlayersPerTrade` INT NULL AFTER `randomTradeOfferChance`,
ADD COLUMN `randomAcceptanceChance` DOUBLE NULL AFTER `maxPlayersPerTrade`;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddLeague`(IN leagueName VARCHAR(45), In userId INT, IN  averageRetirementAge INT, IN maximumAge INT, IN randomWinChance Double, IN randomInjuryChance Double, IN injuryDaysLow INT, IN injuryDaysHigh INT, IN daysUntilCheck INT, IN lossPoint INT, IN randomTradeOfferChance Double, IN maxPlayersPerTrade INT, IN randomAcceptanceChance Double, OUT leagueId INT)
BEGIN
	Insert into League(leagueName, createdBy, averageRetirementAge, maximumAge, randomWinChance, randomInjuryChance, injuryDaysLow, injuryDaysHigh, daysUntilCheck, lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance)
    VALUES (leagueName,userId, averageRetirementAge, maximumAge, randomWinChance, randomInjuryChance, injuryDaysLow, injuryDaysHigh, daysUntilCheck, lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance);
    SET leagueId := LAST_INSERT_ID();
END $$
DELIMITER ;




/* Trading */
CREATE TABLE `Trading` (
  `idTrading` int(11) NOT NULL AUTO_INCREMENT,
  `leagueId` int(11) DEFAULT NULL,
  `lossPoint` int(11) DEFAULT NULL,
  `randomTradeOfferChance` float DEFAULT NULL,
  `maxPlayersPerTrade` int(11) DEFAULT NULL,
  `randomAcceptanceChance` float DEFAULT NULL,
  PRIMARY KEY (`idTrading`),
  CONSTRAINT `leagueId` FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`)
);


CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddTrading`(IN leagueId INT, IN lossPoint INT, IN maxPlayersPerTrade DOUBLE, IN randomAcceptanceChance DOUBLE, IN randomTradeOfferChance DOUBLE, OUT tradingId INT)
BEGIN
	Insert into Trading(leagueId, lossPoint, maxPlayersPerTrade, randomAcceptanceChance, randomTradeOfferChance)
    VALUES (leagueId, lossPoint, maxPlayersPerTrade, randomAcceptanceChance, randomTradeOfferChance);

    SET tradingId := LAST_INSERT_ID();

END


CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTradingDetailsByLeagueId`(IN leagueId INT)
BEGIN
	select * from Trading where leagueId = leagueId;
END


CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTradingDetailsByTradingId`(IN tradingId INT)
BEGIN
	select * from Trading where idTrading = tradingId;
END

CREATE TABLE `TradingOffer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leagueId` int(11) DEFAULT NULL,
  `tradingId` int(11) DEFAULT NULL,
  `fromTeamId` int(11) DEFAULT NULL,
  `toTeamId` int(11) DEFAULT NULL,
  `fromPlayerId` int(11) DEFAULT NULL,
  `toPlayerId` int(11) DEFAULT NULL,
  `seasonId` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`leagueId`) REFERENCES `League` (`idLeague`),
  FOREIGN KEY (`tradingId`) REFERENCES `Trading` (`idTrading`),
  FOREIGN KEY (`fromTeamId`) REFERENCES `Team` (`idTeam`),
  FOREIGN KEY (`toTeamId`) REFERENCES `Team` (`idTeam`),
  FOREIGN KEY (`fromPlayerId`) REFERENCES `Player` (`idPlayer`),
  FOREIGN KEY (`toPlayerId`) REFERENCES `Player` (`idPlayer`),
  FOREIGN KEY (`seasonId`) REFERENCES `Season` (`idSeason`)
);

CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddTeam`(IN teamName Varchar(45), IN divisionId INT, IN aiTeam boolean, OUT teamId INT)
BEGIN

	Insert into Team(teamName, division, aiTeam)
    VALUES (teamName, divisionId, aiteam);

    SET teamId := LAST_INSERT_ID();

END

alter table Team add column aiTeam tinyint(4);

/* Trading */

/*Games, Events,Teamstanding */

CREATE TABLE `Event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seasonStartDate` date DEFAULT NULL,
  `tradeDeadline` date DEFAULT NULL,
  `endOfRegularSeason` date DEFAULT NULL,
  `playOffStartDate` date DEFAULT NULL,
  `lastDayStanleyCup` date DEFAULT NULL,
  `nextSeasonDate` date DEFAULT NULL,
  `leagueid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `Game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gamedate` date DEFAULT NULL,
  `team1` varchar(45) DEFAULT NULL,
  `team2` varchar(45) DEFAULT NULL,
  `played` tinyint(4) DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `leagueid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `GameResolver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leagueid` int(11) DEFAULT NULL,
  `randonWinChance` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `TeamStanding` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `point` int(11) DEFAULT NULL,
  `teamName` varchar(45) DEFAULT NULL,
  `wins` int(11) DEFAULT NULL,
  `loss` int(11) DEFAULT NULL,
  `ties` int(11) DEFAULT NULL,
  `leagueid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddEvent`(IN leagueId INT, IN seasonStartDate DATE, IN tradeDeadline DATE, IN endOfRegularSeason DATE, IN playOffStartDate DATE,IN lastDayStanleyCup DATE,IN nextSeasonDate DATE, OUT eventId INT)
BEGIN
	Insert into Event(leagueId, seasonStartDate, tradeDeadline, endOfRegularSeason, playOffStartDate,lastDayStanleyCup,nextSeasonDate)
    VALUES (leagueId, seasonStartDate, tradeDeadline, endOfRegularSeason, playOffStartDate,lastDayStanleyCup,nextSeasonDate);

    SET eventId := LAST_INSERT_ID();

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddGameResolver`(IN leagueId INT, IN randonWinChance DOUBLE, IN tradeDeadline DATE, OUT gameResolverId INT)
BEGIN
	Insert into GameResolver(leagueId, randonWinChance)
    VALUES (leagueId, randonWinChance);

    SET gameResolverId := LAST_INSERT_ID();

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddGame`(IN leagueId INT, IN date DATE, IN team1 VARCHAR(45),IN team2 VARCHAR(45),IN played BOOLEAN,IN winner VARCHAR(45), OUT gameId INT)
BEGIN
	Insert into Game(leagueId, date,team1,team2,played,winner)
    VALUES (leagueId, gamedate,team1,team2,played,winner);

    SET gameId := LAST_INSERT_ID();

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `AddTeamStanding`(IN leagueId INT, IN point INT, IN teamName VARCHAR(45),IN wins INT,IN loss INT,IN ties INT, OUT teamStandingId INT)
BEGIN
	Insert into TeamStanding(leagueId,point, teamName,wins,loss,ties)
    VALUES (leagueId,point, teamName,wins,loss,ties);

    SET teamStandingId := LAST_INSERT_ID();

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadEventByLeagueId`(IN leagueid INT)
BEGIN
	select * from Event where Event.leagueid= leagueid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadEventById`(IN id INT)
BEGIN
	select * from Event where Event.id= id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadGameResolverByLeagueId`(IN leagueid INT)
BEGIN
	select * from GameResolver where GameResolver.leagueid= leagueid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadGameResolverById`(IN id INT)
BEGIN
	select * from GameResolver where GameResolver.id= id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadGameByLeagueId`(IN leagueid INT)
BEGIN
	select * from Game where Game.leagueid= leagueid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadGameById`(IN id INT)
BEGIN
	select * from Game where Game.id= id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTeamStandingByLeagueId`(IN leagueid INT)
BEGIN
	select * from TeamStanding where TeamStanding.leagueid= leagueid;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`CSCI5308_7_DEVINT_USER`@`%` PROCEDURE `LoadTeamStandingById`(IN teamstandingid INT)
BEGIN
	select * from TeamStanding where TeamStanding.id= teamstandingid;
END$$
DELIMITER ;





/*Games, Events,Teamstanding */

