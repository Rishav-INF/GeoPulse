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
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `teacher_id` int NOT NULL,
  `courses` varchar(100) DEFAULT NULL,
  `monday` text,
  `tuesday` text,
  `wednesday` text,
  `thursday` text,
  `friday` text,
  `saturday` text,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (101,'Mathematics','{\n    \"9:00-10:00\": [1,2,3],\n    \"10:00-11:00\": [2,3],\n    \"11:00-12:00\": [1,3],\n    \"12:00-1:00\": [1,2]\n }','{\n    \"9:00-10:00\": [1,3,4],\n    \"10:00-11:00\": [2,4],\n    \"11:00-12:00\": [1,2],\n    \"1:00-2:00\": [1,4]\n }','{\n    \"8:30-9:30\": [2,4],\n    \"9:30-10:30\": [1,3],\n    \"10:30-11:30\": [2,3],\n    \"11:30-12:30\": [1,4]\n }','{\n    \"9:00-10:00\": [1,2,3,4],\n    \"10:00-11:00\": [1,4],\n    \"11:00-12:00\": [2,3],\n    \"12:00-1:00\": [1,2]\n }','{\n    \"10:00-11:00\": [1,3],\n    \"11:00-12:00\": [2,3],\n    \"12:00-1:00\": [1,4],\n    \"1:00-2:00\": [3,4]\n }','{\n    \"9:00-10:00\": [1,2,3,4],\n    \"10:00-11:00\": [2,3],\n    \"11:00-12:00\": [1,4],\n    \"12:00-1:00\": [3,4]\n }'),(102,'Physics','{\n    \"9:00-10:00\": [5,6],\n    \"10:00-11:00\": [5,6,7],\n    \"11:00-12:00\": [5,7],\n    \"12:00-1:00\": [6,7]\n }','{\n    \"9:00-10:00\": [5,7],\n    \"10:00-11:00\": [6],\n    \"11:00-12:00\": [5,6],\n    \"1:00-2:00\": [6,7]\n }','{\n    \"8:30-9:30\": [6,7],\n    \"9:30-10:30\": [5,6],\n    \"10:30-11:30\": [6,7],\n    \"11:30-12:30\": [5]\n }','{\n    \"9:00-10:00\": [5,6,7],\n    \"10:00-11:00\": [5],\n    \"11:00-12:00\": [6],\n    \"12:00-1:00\": [7]\n }','{\n    \"10:00-11:00\": [6,7],\n    \"11:00-12:00\": [5],\n    \"12:00-1:00\": [5,6],\n    \"1:00-2:00\": [6,7]\n }','{\n    \"9:00-10:00\": [5,6,7],\n    \"10:00-11:00\": [6],\n    \"11:00-12:00\": [5,7],\n    \"12:00-1:00\": [5]\n }');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
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
