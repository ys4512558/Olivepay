-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: j11a601.p.ssafy.io    Database: member
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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `birthdate` varchar(8) NOT NULL,
  `nickname` varchar(30) NOT NULL,
  `pin` varchar(6) NOT NULL,
  `pin_count` tinyint NOT NULL DEFAULT '0',
  `user_key` varchar(60) NOT NULL,
  `member_id` int unsigned NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK9o3wj6v6neithkdwrtelcbo96` (`member_id`),
  CONSTRAINT `FKtcx8a5i8q7mmh7xl2fi44o8v2` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `user_chk_1` CHECK (((`pin_count` <= 3) and (`pin_count` >= 0)))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2024-10-11 03:14:33.679498','2024-10-11 03:21:29.134118','20060325','김모이','123400',0,'4d9913c0-b723-42a0-9e45-c2b3ddbcf8a6',6049),(2,'2024-10-11 03:14:35.395761','2024-10-11 03:14:35.395761','20060325','김페이','123400',0,'ded6be28-6acb-4d46-81b9-2c3138c3184a',6050),(3,'2024-10-11 03:14:50.505218','2024-10-11 03:14:50.505218','20060325','이페이','123400',0,'bdd67da3-16ee-4e9e-b446-b17b51f4dde3',6051),(4,'2024-10-11 03:15:05.529652','2024-10-11 03:15:05.529652','20060325','김페이','123400',0,'95ecde32-ff4f-4832-a315-10895054b54c',6052),(5,'2024-10-11 03:15:20.588889','2024-10-11 03:15:20.588889','20060325','김페이','123400',0,'c8f94e79-ce45-4360-932c-5c2054884d10',6053),(6,'2024-10-11 03:15:35.594007','2024-10-11 03:15:35.594007','20060325','김페이','123400',0,'ccb33dbd-7c4e-460e-9e85-6ab69527a7d8',6054),(7,'2024-10-11 03:15:50.539800','2024-10-11 03:15:50.539800','20060325','김페이','123400',0,'cb0f2875-705b-4c01-aa35-1ae647703c80',6055),(8,'2024-10-11 03:16:05.531741','2024-10-11 03:16:05.531741','20060325','김페이','123400',0,'a8d3d20d-9ee1-4b78-8b71-4607e13ead27',6056),(9,'2024-10-11 03:16:20.553595','2024-10-11 03:16:20.553595','20060325','김페이','123400',0,'857a626c-0280-41cd-9b6d-8a66e1a09ec3',6057),(10,'2024-10-11 03:16:35.614147','2024-10-11 03:16:35.614147','20060325','김페이','123400',0,'65670af8-48db-407a-b2d9-c030ab2f65b5',6058),(11,'2024-10-11 03:16:50.530691','2024-10-11 03:16:50.530691','20060325','김페이','123400',0,'6b56604a-e6a7-42eb-ab84-a95b83f2ec9a',6059),(12,'2024-10-11 03:17:05.362350','2024-10-11 03:17:05.362350','20060325','김페이','123400',0,'2988b9b7-5851-4082-8d5b-909364341aa5',6060),(13,'2024-10-11 03:17:20.198162','2024-10-11 03:17:20.198162','20060325','김페이','123400',0,'daf5221e-a1c5-40d5-9e8f-e7c62361a394',6061),(14,'2024-10-11 03:17:35.173094','2024-10-11 03:17:35.173094','20060325','김페이','123400',0,'9600818d-ef2f-4ecb-a8f2-ddbdcc80b652',6062),(15,'2024-10-11 03:17:50.197951','2024-10-11 03:17:50.197951','20060325','김페이','123400',0,'9e8e1dda-d68b-424f-897f-b1e2fc1e764b',6063),(16,'2024-10-11 03:18:05.146843','2024-10-11 03:18:05.146843','20060325','김페이','123400',0,'5fcfa914-6062-4592-9407-6e087e9d5ea3',6064),(17,'2024-10-11 03:18:19.975347','2024-10-11 03:18:19.975347','20060325','김페이','123400',0,'52f04016-b238-400c-b8f3-547d98bedb77',6065),(18,'2024-10-11 03:18:34.819880','2024-10-11 03:18:34.819880','20060325','김페이','123400',0,'6044944c-86cf-47c0-93de-d64823e1d2d9',6066),(19,'2024-10-11 03:18:49.629366','2024-10-11 03:18:49.629366','20060325','김페이','123400',0,'695609e8-8556-4172-ad9d-22b0780d791f',6067),(20,'2024-10-11 03:19:04.509055','2024-10-11 03:19:04.509055','20060325','김페이','123400',0,'c5f15cad-b442-4302-ae94-08ac881d86f7',6068);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-11  3:38:06
