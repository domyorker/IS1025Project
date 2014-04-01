CREATE SCHEMA IF NOT EXISTS `srr` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `srr` ;

-- -----------------------------------------------------
-- Table `srr`.`state_lookup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`state_lookup` ;

CREATE TABLE IF NOT EXISTS `srr`.`state_lookup` (
  `stateID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `abbreviation` CHAR(2) NOT NULL,
  PRIMARY KEY (`stateID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`class_level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`class_level` ;

CREATE TABLE IF NOT EXISTS `srr`.`class_level` (
  `levelID` TINYINT NOT NULL AUTO_INCREMENT,
  `levelName` CHAR(8) NOT NULL,
  PRIMARY KEY (`levelID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`student_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`student_account` ;

CREATE TABLE IF NOT EXISTS `srr`.`student_account` (
  `studentID` BIGINT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(20) NOT NULL,
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
  `classLevelID` TINYINT NOT NULL,
  PRIMARY KEY (`studentID`, `stateID`, `classLevelID`),
  INDEX `fk_stateID_idx` (`stateID` ASC),
  INDEX `fk_educationLevelID_idx` (`classLevelID` ASC),
  CONSTRAINT `fk_stateID`
    FOREIGN KEY (`stateID`)
    REFERENCES `srr`.`state_lookup` (`stateID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_classLevelID`
    FOREIGN KEY (`classLevelID`)
    REFERENCES `srr`.`class_level` (`levelID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`skill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`skill` ;

CREATE TABLE IF NOT EXISTS `srr`.`skill` (
  `skillID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`skillID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`experience`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`experience` ;

CREATE TABLE IF NOT EXISTS `srr`.`experience` (
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
-- Table `srr`.`student_resume`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`student_resume` ;

CREATE TABLE IF NOT EXISTS `srr`.`student_resume` (
  `resumeID` BIGINT NOT NULL AUTO_INCREMENT,
  `userID` BIGINT NOT NULL,
  `title` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`resumeID`, `userID`),
  INDEX `fk_student_resume_userID_idx` (`userID` ASC),
  CONSTRAINT `fk_student_resume_userID`
    FOREIGN KEY (`userID`)
    REFERENCES `srr`.`student_account` (`studentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`reference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`reference` ;

CREATE TABLE IF NOT EXISTS `srr`.`reference` (
  `resumeID` BIGINT NOT NULL,
  `Name` VARCHAR(60) NOT NULL,
  `title` VARCHAR(45) NULL,
  `phone` CHAR(10) NOT NULL,
  `email` VARCHAR(45) NULL,
  `isProfessional` TINYINT(1) NOT NULL DEFAULT false,
  PRIMARY KEY (`resumeID`),
  CONSTRAINT `fk_reference_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`summary`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`summary` ;

CREATE TABLE IF NOT EXISTS `srr`.`summary` (
  `resumeID` BIGINT NOT NULL,
  `objective` TEXT NULL,
  `experience` TEXT NULL,
  `accomplishments` TEXT NULL,
  PRIMARY KEY (`resumeID`),
  CONSTRAINT `fk_summary_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`certification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`certification` ;

CREATE TABLE IF NOT EXISTS `srr`.`certification` (
  `certificationID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `institution` VARCHAR(100) NOT NULL,
  `dateAttained` DATE NOT NULL,
  `summary` TEXT NULL,
  PRIMARY KEY (`certificationID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`membership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`membership` ;

CREATE TABLE IF NOT EXISTS `srr`.`membership` (
  `membershipID` BIGINT NOT NULL AUTO_INCREMENT,
  `membershipName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`membershipID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`award`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`award` ;

CREATE TABLE IF NOT EXISTS `srr`.`award` (
  `awardID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`awardID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`interest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`interest` ;

CREATE TABLE IF NOT EXISTS `srr`.`interest` (
  `interestID` BIGINT NOT NULL AUTO_INCREMENT,
  `interestName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`interestID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_experience`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_experience` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_experience` (
  `resumeID` BIGINT NOT NULL,
  `experieceID` BIGINT NOT NULL,
  PRIMARY KEY (`resumeID`, `experieceID`),
  INDEX `fk_user_experiece_expID_idx` (`experieceID` ASC),
  CONSTRAINT `fk_resume_experiece_expID`
    FOREIGN KEY (`experieceID`)
    REFERENCES `srr`.`experience` (`experienceID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_experience_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_interest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_interest` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_interest` (
  `resumeID` BIGINT NOT NULL,
  `interestID` BIGINT NOT NULL,
  PRIMARY KEY (`interestID`, `resumeID`),
  INDEX `fk_resume_interest_resumeID_idx` (`resumeID` ASC),
  CONSTRAINT `fk_resume_interest_interestID`
    FOREIGN KEY (`interestID`)
    REFERENCES `srr`.`interest` (`interestID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_interest_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_membership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_membership` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_membership` (
  `resumeID` BIGINT NOT NULL,
  `membershipID` BIGINT NOT NULL,
  PRIMARY KEY (`membershipID`, `resumeID`),
  INDEX `fk_resume_membership_resumeID_idx` (`resumeID` ASC),
  CONSTRAINT `fk_resume_membership_mID`
    FOREIGN KEY (`membershipID`)
    REFERENCES `srr`.`membership` (`membershipID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_membership_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_certification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_certification` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_certification` (
  `resumeID` BIGINT NOT NULL,
  `certificationID` BIGINT NOT NULL,
  PRIMARY KEY (`certificationID`, `resumeID`),
  INDEX `fk_user_certification_resumeID_idx` (`resumeID` ASC),
  CONSTRAINT `fk_resume_certification_certID`
    FOREIGN KEY (`certificationID`)
    REFERENCES `srr`.`certification` (`certificationID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_certification_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_award`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_award` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_award` (
  `resumeID` BIGINT NOT NULL,
  `awardID` BIGINT NOT NULL,
  PRIMARY KEY (`resumeID`, `awardID`),
  INDEX `fk_user_award_awardID_idx` (`awardID` ASC),
  CONSTRAINT `fk_resume_award_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_award_awardID`
    FOREIGN KEY (`awardID`)
    REFERENCES `srr`.`award` (`awardID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_skill`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_skill` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_skill` (
  `resumeID` BIGINT NOT NULL,
  `skillID` BIGINT NOT NULL,
  PRIMARY KEY (`skillID`, `resumeID`),
  INDEX `fk_resume_skill_resumeID_idx` (`resumeID` ASC),
  CONSTRAINT `fk_resume_skill_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_resume_skill_skillID`
    FOREIGN KEY (`skillID`)
    REFERENCES `srr`.`skill` (`skillID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`course` ;

CREATE TABLE IF NOT EXISTS `srr`.`course` (
  `courseID` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(65) NULL,
  PRIMARY KEY (`courseID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`admin` ;

CREATE TABLE IF NOT EXISTS `srr`.`admin` (
  `adminID` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(32) NOT NULL,
  `fName` VARCHAR(45) NOT NULL,
  `lName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`adminID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`resume_course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`resume_course` ;

CREATE TABLE IF NOT EXISTS `srr`.`resume_course` (
  `resumeID` BIGINT NOT NULL,
  `courseID` BIGINT NOT NULL,
  PRIMARY KEY (`resumeID`, `courseID`),
  INDEX `fk_course_resumeID_idx` (`courseID` ASC),
  CONSTRAINT `fk_course_resumeID`
    FOREIGN KEY (`courseID`)
    REFERENCES `srr`.`course` (`courseID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_resume_resumeID`
    FOREIGN KEY (`resumeID`)
    REFERENCES `srr`.`student_resume` (`resumeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`degree_type_lookup`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`degree_type_lookup` ;

CREATE TABLE IF NOT EXISTS `srr`.`degree_type_lookup` (
  `typeID` TINYINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(54) NOT NULL,
  `abbreviation` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`typeID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`education`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`education` ;

CREATE TABLE IF NOT EXISTS `srr`.`education` (
  `educationID` BIGINT NOT NULL AUTO_INCREMENT,
  `institution` VARCHAR(45) NULL,
  `graduationMonth` TINYINT NOT NULL,
  `graduationYear` INT NOT NULL,
  `degreeTypeID` TINYINT NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `stateID` INT NOT NULL,
  PRIMARY KEY (`educationID`, `degreeTypeID`),
  INDEX `fk_education_degreeTypeID_idx` (`degreeTypeID` ASC),
  CONSTRAINT `fk_education_degreeTypeID`
    FOREIGN KEY (`degreeTypeID`)
    REFERENCES `srr`.`degree_type_lookup` (`typeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `srr`.`student_education`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `srr`.`student_education` ;

CREATE TABLE IF NOT EXISTS `srr`.`student_education` (
  `studentID` BIGINT NOT NULL,
  `educationID` BIGINT NOT NULL,
  PRIMARY KEY (`studentID`, `educationID`),
  INDEX `fk_studentEducation_educationID_idx` (`educationID` ASC),
  CONSTRAINT `fk_studentEducation_studentID`
    FOREIGN KEY (`studentID`)
    REFERENCES `srr`.`student_account` (`studentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_studentEducation_educationID`
    FOREIGN KEY (`educationID`)
    REFERENCES `srr`.`education` (`educationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

