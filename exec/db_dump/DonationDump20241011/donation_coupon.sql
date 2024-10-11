-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: j11a601.p.ssafy.io    Database: donation
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `coupon_id` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `count` int unsigned NOT NULL,
  `coupon_unit` enum('FOUR','TWO') DEFAULT NULL,
  `franchise_id` int unsigned NOT NULL,
  `message` varchar(255) NOT NULL,
  `donation_id` int unsigned NOT NULL,
  `version` bigint DEFAULT NULL,
  PRIMARY KEY (`coupon_id`),
  KEY `FKsimh1dvymny1wq69qef0pxusf` (`donation_id`),
  CONSTRAINT `FKsimh1dvymny1wq69qef0pxusf` FOREIGN KEY (`donation_id`) REFERENCES `donation` (`donation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (7,'2024-10-11 03:26:57.808332','2024-10-11 03:26:57.808332',0,'TWO',4889,'✨ 오늘도 즐거운 하루',4,0),(8,'2024-10-11 03:26:57.812701','2024-10-11 03:26:57.812701',0,'FOUR',4889,'✨ 오늘도 즐거운 하루',4,0),(9,'2024-10-11 03:41:18.941804','2024-10-11 03:41:18.941804',10,'TWO',599,'? 건강한 식사를 위하여',5,0),(10,'2024-10-11 03:41:18.944096','2024-10-11 03:41:18.944096',10,'FOUR',599,'? 건강한 식사를 위하여',5,0),(11,'2024-10-11 03:44:03.245803','2024-10-11 03:44:03.245803',10,'TWO',1314,'✨ 오늘도 즐거운 하루',6,0),(12,'2024-10-11 03:44:03.247530','2024-10-11 03:44:03.247530',5,'FOUR',1314,'✨ 오늘도 즐거운 하루',6,0),(13,'2024-10-11 03:44:50.984431','2024-10-11 03:44:50.984431',6,'TWO',3758,'화이팅 화이팅 화이팅 ?',7,0),(14,'2024-10-11 03:44:50.986180','2024-10-11 03:44:50.986180',30,'FOUR',3758,'화이팅 화이팅 화이팅 ?',7,0),(15,'2024-10-11 03:46:33.803675','2024-10-11 03:46:33.803675',60,'TWO',1311,'✨ 오늘도 즐거운 하루',8,0),(16,'2024-10-11 03:46:33.805283','2024-10-11 03:46:33.805283',95,'FOUR',1311,'✨ 오늘도 즐거운 하루',8,0),(17,'2024-10-11 03:47:54.623681','2024-10-11 03:47:54.623681',60,'TWO',3435,'✨ 오늘도 즐거운 하루',9,0),(18,'2024-10-11 03:47:54.625424','2024-10-11 03:47:54.625424',100,'FOUR',3435,'✨ 오늘도 즐거운 하루',9,0);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-11  3:48:23
