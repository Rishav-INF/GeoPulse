-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: geopulse
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `students_login`
--

DROP TABLE IF EXISTS `students_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_login` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `class_id` int DEFAULT NULL,
  `class` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2023016 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_login`
--

LOCK TABLES `students_login` WRITE;
/*!40000 ALTER TABLE `students_login` DISABLE KEYS */;
INSERT INTO `students_login` VALUES (2023001,'John Smith','john123',1,'Mathematics','2025-05-12 12:15:02'),(2023002,'Emma Johnson','emma456',2,'Mathematics','2025-05-12 12:15:02'),(2023003,'Liam Williams','liam789',3,'Science','2025-05-12 12:15:02'),(2023004,'Olivia Brown','olivia101',4,'Science','2025-05-12 12:15:02'),(2023005,'Noah Jones','noah202',5,'History','2025-05-12 12:15:02'),(2023006,'Ava Garcia','ava303',6,'History','2025-05-12 12:15:02'),(2023007,'William Martinez','will404',7,'English','2025-05-12 12:15:02'),(2023008,'Sophia Rodriguez','sophia505',8,'English','2025-05-12 12:15:02'),(2023009,'James Wilson','james606',9,'Geography','2025-05-12 12:15:02'),(2023010,'Isabella Lopez','bella707',10,'Geography','2025-05-12 12:15:02'),(2023011,'Benjamin Lee','ben808',11,'Mathematics','2025-05-12 12:15:02'),(2023012,'Mia Gonzalez','mia909',12,'Science','2025-05-12 12:15:02'),(2023013,'Elijah Perez','elijah111',13,'History','2025-05-12 12:15:02'),(2023014,'Charlotte Harris','charl222',14,'English','2025-05-12 12:15:02'),(2023015,'Alexander Clark','alex333',15,'Geography','2025-05-12 12:15:02');
/*!40000 ALTER TABLE `students_login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-12 20:34:10
