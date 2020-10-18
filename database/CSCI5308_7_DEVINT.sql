-- -----------------------------------------------------
-- Schema CSCI5308_7_DEVINT
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSCI5308_7_DEVINT` DEFAULT CHARACTER SET latin1 ;
USE `CSCI5308_7_DEVINT` ;

-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`User` (
  `idUser` INT(11) NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB
AUTO_INCREMENT = 77
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`League`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`League` (
  `idLeague` INT(11) NOT NULL AUTO_INCREMENT,
  `leagueName` VARCHAR(45) NULL DEFAULT NULL,
  `createdBy` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idLeague`),
  INDEX `createdBy` (`createdBy` ASC) VISIBLE,
  CONSTRAINT `createdBy`
    FOREIGN KEY (`createdBy`)
    REFERENCES `CSCI5308_7_DEVINT`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 117
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Conference`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Conference` (
  `idConference` INT(11) NOT NULL AUTO_INCREMENT,
  `conferenceName` VARCHAR(45) NULL DEFAULT NULL,
  `league` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idConference`),
  INDEX `league` (`league` ASC) VISIBLE,
  CONSTRAINT `league`
    FOREIGN KEY (`league`)
    REFERENCES `CSCI5308_7_DEVINT`.`League` (`idLeague`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Division`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Division` (
  `idDivision` INT(11) NOT NULL AUTO_INCREMENT,
  `divisionName` VARCHAR(45) NULL DEFAULT NULL,
  `conference` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idDivision`),
  INDEX `conference` (`conference` ASC) VISIBLE,
  CONSTRAINT `conference`
    FOREIGN KEY (`conference`)
    REFERENCES `CSCI5308_7_DEVINT`.`Conference` (`idConference`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Season`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Season` (
  `idSeason` INT(11) NOT NULL AUTO_INCREMENT,
  `seasonName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idSeason`))
ENGINE = InnoDB
AUTO_INCREMENT = 60
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`FreeAgent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`FreeAgent` (
  `league` INT(11) NULL DEFAULT NULL,
  `season` INT(11) NULL DEFAULT NULL,
  `freeAgentId` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`freeAgentId`),
  INDEX `league` (`league` ASC) VISIBLE,
  INDEX `season` (`season` ASC) VISIBLE,
  CONSTRAINT `FreeAgent_ibfk_1`
    FOREIGN KEY (`league`)
    REFERENCES `CSCI5308_7_DEVINT`.`League` (`idLeague`),
  CONSTRAINT `FreeAgent_ibfk_2`
    FOREIGN KEY (`season`)
    REFERENCES `CSCI5308_7_DEVINT`.`Season` (`idSeason`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `CSCI5308_7_DEVINT`.`Player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Player` (
  `idPlayer` INT(11) NOT NULL AUTO_INCREMENT,
  `playerName` VARCHAR(45) NULL DEFAULT NULL,
  `playerDOB` VARCHAR(45) NULL DEFAULT NULL,
  `position` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idPlayer`))
ENGINE = InnoDB
AUTO_INCREMENT = 907
DEFAULT CHARACTER SET = latin1;


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
CREATE TABLE IF NOT EXISTS `CSCI5308_7_DEVINT`.`Team` (
  `idTeam` INT(11) NOT NULL AUTO_INCREMENT,
  `teamName` VARCHAR(45) NULL DEFAULT NULL,
  `division` INT(11) NULL DEFAULT NULL,
  `generalManager` VARCHAR(45) NULL DEFAULT NULL,
  `headCoach` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idTeam`),
  INDEX `division` (`division` ASC) VISIBLE,
  CONSTRAINT `division`
    FOREIGN KEY (`division`)
    REFERENCES `CSCI5308_7_DEVINT`.`Division` (`idDivision`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 98
DEFAULT CHARACTER SET = latin1;


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