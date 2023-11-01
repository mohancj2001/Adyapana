-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for adyapana
CREATE DATABASE IF NOT EXISTS `adyapana` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `adyapana`;

-- Dumping structure for table adyapana.add_students_for_class
CREATE TABLE IF NOT EXISTS `add_students_for_class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_add_students_for_class_class` (`class_id`),
  KEY `FK_add_students_for_class_student` (`student_id`),
  CONSTRAINT `FK_add_students_for_class_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`ClassNo`),
  CONSTRAINT `FK_add_students_for_class_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.add_students_for_class: ~0 rows (approximately)
INSERT INTO `add_students_for_class` (`id`, `class_id`, `student_id`) VALUES
	(1, 1, 1),
	(2, 1, 2);

-- Dumping structure for table adyapana.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `nic` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender_id` int NOT NULL,
  PRIMARY KEY (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.admin: ~1 rows (approximately)
INSERT INTO `admin` (`nic`, `first_name`, `last_name`, `email`, `username`, `password`, `gender_id`) VALUES
	('200109704750', 'mohan', 'chanka', 'mohan@gmail.com', 'mcj', '123', 0);

-- Dumping structure for table adyapana.attendence
CREATE TABLE IF NOT EXISTS `attendence` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `class_id` int NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_attendence_student` (`student_id`),
  KEY `FK_attendence_class` (`class_id`),
  CONSTRAINT `FK_attendence_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`ClassNo`),
  CONSTRAINT `FK_attendence_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.attendence: ~1 rows (approximately)
INSERT INTO `attendence` (`id`, `student_id`, `class_id`, `date`) VALUES
	(1, 1, 1, '2023-07-14');

-- Dumping structure for table adyapana.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.city: ~3 rows (approximately)
INSERT INTO `city` (`id`, `city_name`) VALUES
	(1, 'kurunegala'),
	(2, 'colombo'),
	(3, 'kandy');

-- Dumping structure for table adyapana.class
CREATE TABLE IF NOT EXISTS `class` (
  `ClassNo` int NOT NULL AUTO_INCREMENT,
  `timeslot` time NOT NULL,
  `date` date NOT NULL,
  `teacher_Tno` int NOT NULL,
  `subject_Subno` int NOT NULL,
  PRIMARY KEY (`ClassNo`),
  KEY `fk_class_teacher_idx` (`teacher_Tno`),
  KEY `fk_class_subject1_idx` (`subject_Subno`),
  CONSTRAINT `fk_class_subject1` FOREIGN KEY (`subject_Subno`) REFERENCES `subject` (`Subno`),
  CONSTRAINT `fk_class_teacher` FOREIGN KEY (`teacher_Tno`) REFERENCES `teacher` (`Tno`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.class: ~2 rows (approximately)
INSERT INTO `class` (`ClassNo`, `timeslot`, `date`, `teacher_Tno`, `subject_Subno`) VALUES
	(1, '15:23:14', '2023-07-15', 1, 2),
	(2, '20:30:00', '2023-07-17', 2, 2);

-- Dumping structure for table adyapana.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.gender: ~2 rows (approximately)
INSERT INTO `gender` (`id`, `name`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table adyapana.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `month_id` int NOT NULL,
  `teacher_Tno` int DEFAULT NULL,
  `student_Sno` int NOT NULL,
  `subject_Subno` int NOT NULL,
  `Value` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_teacher1_idx` (`teacher_Tno`),
  KEY `fk_invoice_student1_idx` (`student_Sno`),
  KEY `fk_invoice_subject1_idx` (`subject_Subno`),
  KEY `fk_invoice_month1_idx` (`month_id`),
  CONSTRAINT `fk_invoice_month1` FOREIGN KEY (`month_id`) REFERENCES `month` (`id`),
  CONSTRAINT `fk_invoice_student1` FOREIGN KEY (`student_Sno`) REFERENCES `student` (`Sno`),
  CONSTRAINT `fk_invoice_subject1` FOREIGN KEY (`subject_Subno`) REFERENCES `subject` (`Subno`),
  CONSTRAINT `fk_invoice_teacher1` FOREIGN KEY (`teacher_Tno`) REFERENCES `teacher` (`Tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.invoice: ~0 rows (approximately)

-- Dumping structure for table adyapana.month
CREATE TABLE IF NOT EXISTS `month` (
  `id` int NOT NULL AUTO_INCREMENT,
  `month_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.month: ~12 rows (approximately)
INSERT INTO `month` (`id`, `month_name`) VALUES
	(1, 'January'),
	(2, 'February'),
	(3, 'March'),
	(4, 'April'),
	(5, 'May'),
	(6, 'June'),
	(7, 'July'),
	(8, 'August'),
	(9, 'September'),
	(10, 'October'),
	(11, 'November'),
	(12, 'December');

-- Dumping structure for table adyapana.payment
CREATE TABLE IF NOT EXISTS `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `payment` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.payment: ~2 rows (approximately)
INSERT INTO `payment` (`id`, `payment`) VALUES
	(1, 'Paid'),
	(2, 'Not Paid');

-- Dumping structure for table adyapana.student
CREATE TABLE IF NOT EXISTS `student` (
  `Sno` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) NOT NULL,
  `nic` varchar(50) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `dob` date NOT NULL,
  `gender_id` int NOT NULL,
  `city_id` int unsigned NOT NULL,
  PRIMARY KEY (`Sno`),
  KEY `FK_student_gender` (`gender_id`),
  KEY `fk_student_city1_idx` (`city_id`),
  CONSTRAINT `fk_student_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `FK_student_gender` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.student: ~3 rows (approximately)
INSERT INTO `student` (`Sno`, `first_name`, `last_name`, `email`, `nic`, `mobile`, `address`, `dob`, `gender_id`, `city_id`) VALUES
	(1, 'Mohan', 'Chanka', 'mohan@gmail.com', '200109704750', '0713204027', 'No:57,Thorawthuagama,Kohilegedara', '2001-04-06', 1, 1),
	(2, 'Saman', 'Kumara', 'saman@gmail.com', '200405486197', '0771458412', 'no:65,kandy road,kandy', '2005-11-14', 1, 3),
	(5, 'kamala', 'siri', 'kamala@gmail.com', '199745789654', '0744589654', 'no:06,colombo 7', '1998-07-04', 2, 2);

-- Dumping structure for table adyapana.subject
CREATE TABLE IF NOT EXISTS `subject` (
  `Subno` int NOT NULL AUTO_INCREMENT,
  `Description` varchar(100) NOT NULL,
  `Price` double NOT NULL,
  PRIMARY KEY (`Subno`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.subject: ~3 rows (approximately)
INSERT INTO `subject` (`Subno`, `Description`, `Price`) VALUES
	(1, 'Bio', 4000),
	(2, 'Physics', 3000),
	(4, 'Chemistry', 3000);

-- Dumping structure for table adyapana.teacher
CREATE TABLE IF NOT EXISTS `teacher` (
  `Tno` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nic` varchar(12) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `subject_Subno` int NOT NULL,
  `city_id` int unsigned NOT NULL,
  PRIMARY KEY (`Tno`),
  KEY `fk_teacher_subject1_idx` (`subject_Subno`),
  KEY `fk_teacher_city1_idx` (`city_id`),
  CONSTRAINT `fk_teacher_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_teacher_subject1` FOREIGN KEY (`subject_Subno`) REFERENCES `subject` (`Subno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.teacher: ~2 rows (approximately)
INSERT INTO `teacher` (`Tno`, `first_name`, `last_name`, `address`, `nic`, `mobile`, `subject_Subno`, `city_id`) VALUES
	(1, 'Dahanaka', 'wijesooriya', 'no:5,colombo road,kurunegala', '198515487956', '0714578965', 2, 1),
	(2, 'Mohan', 'Chanaka', 'No:4,colombo road', '200109704750', '0713204027', 2, 1);

-- Dumping structure for table adyapana.test
CREATE TABLE IF NOT EXISTS `test` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.test: ~2 rows (approximately)
INSERT INTO `test` (`id`, `name`) VALUES
	(10000, 'a'),
	(10001, 'aa');

-- Dumping structure for table adyapana.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nic` varchar(50) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table adyapana.user: ~3 rows (approximately)
INSERT INTO `user` (`id`, `first_name`, `last_name`, `username`, `password`, `nic`, `mobile`) VALUES
	(1, 'saman', 'kumara', 'saman', '123', '199945652186', '0714285456'),
	(2, 'mohan', 'chanaka', 'mohan', '123', '200109704750', '0713204027'),
	(3, 'Nayana', 'Kumari', 'nayana', '123', '200015948751', '0774859123');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
