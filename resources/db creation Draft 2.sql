SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`state_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`state_lookup` (
  `stateID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(12) NOT NULL,
  `abbreviation` CHAR(2) NOT NULL,
  PRIMARY KEY (`stateID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_account` (
  `userID` BIGINT NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `fName` VARCHAR(45) NOT NULL,
  `lName` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `addressLine1` VARCHAR(45) NULL,
  `addressLine2` VARCHAR(45) NULL,
  `stateID` INT NOT NULL,
  `city` VARCHAR(45) NULL,
  `ZIP` CHAR(9) NOT NULL,
  `phone` VARCHAR(10) NULL,
  `verified` TINYINT(1) NOT NULL DEFAULT 'FALSE',
  PRIMARY KEY (`userID`, `stateID`),
  INDEX `fk_stateID_idx` (`stateID` ASC),
  CONSTRAINT `fk_stateID`
    FOREIGN KEY (`stateID`)
    REFERENCES `mydb`.`state_lookup` (`stateID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`skill_level`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`skill_level` (
  `skillLevelID` TINYINT NOT NULL,
  `level_name` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`skillLevelID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`skill` (
  `skillID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `levelID` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`skillID`, `levelID`),
  INDEX `fk_skill_levelID_idx` (`levelID` ASC),
  CONSTRAINT `fk_skill_levelID`
    FOREIGN KEY (`levelID`)
    REFERENCES `mydb`.`skill_level` (`skillLevelID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`experience`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`experience` (
  `experienceID` BIGINT NOT NULL AUTO_INCREMENT,
  `exployer` VARCHAR(100) NOT NULL,
  `jobTitle` VARCHAR(120) NULL,
  `startDate` DATE NOT NULL,
  `presentJob` TINYINT(1) NOT NULL DEFAULT true,
  `endDate` DATE NULL,
  `summary` TEXT NULL,
  PRIMARY KEY (`experienceID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`reference`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`reference` (
  `userID` BIGINT NOT NULL,
  `Name` VARCHAR(60) NOT NULL,
  `title` VARCHAR(45) NULL,
  `company` VARCHAR(100) NULL,
  `phone` CHAR(10) NOT NULL,
  `email` VARCHAR(45) NULL,
  `isProfessional` TINYINT(1) NOT NULL DEFAULT false,
  PRIMARY KEY (`userID`),
  CONSTRAINT `fk_reference_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`summary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`summary` (
  `userID` BIGINT NOT NULL,
  `objective` TEXT NULL,
  `experience` TEXT NULL,
  `accomplishments` TEXT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `fk_summary_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB


-- -----------------------------------------------------
-- Table `mydb`.`certification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`certification` (
  `certificationID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `institution` VARCHAR(100) NOT NULL,
  `dateAttained` DATE NOT NULL,
  `summary` TEXT NULL,
  PRIMARY KEY (`certificationID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`membership`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`membership` (
  `membershipID` BIGINT NOT NULL AUTO_INCREMENT,
  `membershipName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`membershipID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`award`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`award` (
  `awardID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`awardID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`interest` (
  `interestID` BIGINT NOT NULL AUTO_INCREMENT,
  `interestName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`interestID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_experience`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_experience` (
  `userID` BIGINT NOT NULL,
  `experieceID` BIGINT NOT NULL,
  PRIMARY KEY (`userID`, `experieceID`),
  INDEX `fk_user_experiece_expID_idx` (`experieceID` ASC),
  CONSTRAINT `fk_user_experiece_expID`
    FOREIGN KEY (`experieceID`)
    REFERENCES `mydb`.`experience` (`experienceID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_experience_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_interest` (
  `userID` BIGINT NOT NULL,
  `interestID` BIGINT NOT NULL,
  PRIMARY KEY (`interestID`, `userID`),
  INDEX `fk_user_interest_userID_idx` (`userID` ASC),
  CONSTRAINT `fk_user_interest_interestID`
    FOREIGN KEY (`interestID`)
    REFERENCES `mydb`.`interest` (`interestID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_interest_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_membership`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_membership` (
  `userID` BIGINT NOT NULL,
  `membershipID` BIGINT NOT NULL,
  PRIMARY KEY (`membershipID`, `userID`),
  INDEX `fk_user_membership_userID_idx` (`userID` ASC),
  CONSTRAINT `fk_user_membership_mID`
    FOREIGN KEY (`membershipID`)
    REFERENCES `mydb`.`membership` (`membershipID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_membership_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_certification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_certification` (
  `userID` BIGINT NOT NULL,
  `certificationID` BIGINT NOT NULL,
  PRIMARY KEY (`certificationID`, `userID`),
  INDEX `fk_user_certification_userID_idx` (`userID` ASC),
  CONSTRAINT `fk_user_certification_certID`
    FOREIGN KEY (`certificationID`)
    REFERENCES `mydb`.`certification` (`certificationID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_certification_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_award`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_award` (
  `userID` BIGINT NOT NULL,
  `awardID` BIGINT NOT NULL,
  PRIMARY KEY (`userID`, `awardID`),
  INDEX `fk_user_award_awardID_idx` (`awardID` ASC),
  CONSTRAINT `fk_user_award_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_award_awardID`
    FOREIGN KEY (`awardID`)
    REFERENCES `mydb`.`award` (`awardID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_skill` (
  `userID` BIGINT NOT NULL,
  `skillID` BIGINT NOT NULL,
  PRIMARY KEY (`skillID`, `userID`),
  CONSTRAINT `fk_user_skill_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `mydb`.`user_account` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_skill_skillID`
    FOREIGN KEY (`skillID`)
    REFERENCES `mydb`.`skill` (`skillID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`employer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`employer` (
  `employerID` BIGINT NOT NULL AUTO_INCREMENT,
  `companyName` VARCHAR(100) NOT NULL,
  `city` VARCHAR(45) NULL,
  `stateID` INT NOT NULL,
  `representativeTitle` VARCHAR(45) NULL,
  `representativeName` VARCHAR(60) NULL,
  `representativeEmail` VARCHAR(45) NOT NULL,
  `representativePhone` VARCHAR(12) NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(32) NOT NULL,
  `verified` TINYINT(1) NOT NULL DEFAULT 'FALSE',
  PRIMARY KEY (`employerID`, `stateID`),
  INDEX `fk_employer_stateID_idx` (`stateID` ASC),
  CONSTRAINT `fk_employer_stateID`
    FOREIGN KEY (`stateID`)
    REFERENCES `mydb`.`state_lookup` (`stateID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`faculty` (
  `facultyID` BIGINT NOT NULL AUTO_INCREMENT,
  `facultyTitle` VARCHAR(45) NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(32) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `verified` TINYINT(1) NOT NULL DEFAULT 'FALSE',
  PRIMARY KEY (`facultyID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
