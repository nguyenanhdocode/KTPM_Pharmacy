-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: pharmacydb
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `medicines`
--

DROP TABLE IF EXISTS `medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicines` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `BrandName` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ChemicalName` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `UnitID` int NOT NULL,
  `UnitInStock` int NOT NULL DEFAULT '0',
  `UnitPrice` float NOT NULL DEFAULT '0',
  `AllowedUnitInStock` int NOT NULL DEFAULT '0',
  `ProducingCountry` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UnitID` (`UnitID`),
  CONSTRAINT `medicines_ibfk_1` FOREIGN KEY (`UnitID`) REFERENCES `units` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicines`
--

LOCK TABLES `medicines` WRITE;
/*!40000 ALTER TABLE `medicines` DISABLE KEYS */;
INSERT INTO `medicines` VALUES (1,'hạ sốt','kkk',1,5,15000,10,'vn'),(2,'thuốc độc','hhhh',1,5,30000,19,'mỹ'),(3,'cam','jklj',1,2,18989,12,'trung quốc');
/*!40000 ALTER TABLE `medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `LastName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Phone` char(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Address` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Point` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sell_medicines`
--

DROP TABLE IF EXISTS `sell_medicines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sell_medicines` (
  `MedicineID` int NOT NULL,
  `UserID` int NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UnitPrice` float NOT NULL,
  `Quantity` int NOT NULL,
  PRIMARY KEY (`MedicineID`,`UserID`,`Date`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `sell_medicines_ibfk_1` FOREIGN KEY (`MedicineID`) REFERENCES `medicines` (`ID`),
  CONSTRAINT `sell_medicines_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sell_medicines`
--

LOCK TABLES `sell_medicines` WRITE;
/*!40000 ALTER TABLE `sell_medicines` DISABLE KEYS */;
/*!40000 ALTER TABLE `sell_medicines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UnitName` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'ml'),(2,'vien');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Password` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `FirstName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `LastName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Gender` longtext,
  `Address` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=714490475 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'thien','1','thien','phan','Nam','hhhh'),(21,'jkj','c4ca4238a0b923820dcc509a6f75849b','jklljl','jkjlk','Nam','k,jkljlk'),(22,'jkjmklk','c4ca4238a0b923820dcc509a6f75849b','jklljl','jkjlk','Nam','k,jkljlk'),(23,'huyyy','c4ca4238a0b923820dcc509a6f75849b','kjklj','kjlkjlk','Nam','kljk'),(24,'hai','c4ca4238a0b923820dcc509a6f75849b','hai','sf','Nữ','dsad'),(32,'hijk','c4ca4238a0b923820dcc509a6f75849b','jlkjkj','jkjk','Nam','jkjk'),(714490445,'ff','2','cc','22','nam','dsd'),(714490446,'hjhj','c4ca4238a0b923820dcc509a6f75849b','jhjh','jhkj','Nam','jkkj'),(714490447,'ban','c4ca4238a0b923820dcc509a6f75849b','ban','le','Nam','hcm'),(714490448,'hai88','c4ca4238a0b923820dcc509a6f75849b','thjhh','kkk','Nữ','kkk'),(714490470,'hai1','c4ca4238a0b923820dcc509a6f75849b','hghjgh','hghjg','Nữ','kjnk'),(714490471,'hai2','c4ca4238a0b923820dcc509a6f75849b','hghjgh','hghjg','Nữ','kjnk'),(714490472,'hai3','c4ca4238a0b923820dcc509a6f75849b','hghjgh','hghjg','Nữ','kjnk'),(714490473,'hai4','c4ca4238a0b923820dcc509a6f75849b','jkhjh','jhjkh','Nam','jkkl'),(714490474,'hai5','c4ca4238a0b923820dcc509a6f75849b','jhj','jhkj','Nam','jhj');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-04  8:06:17
