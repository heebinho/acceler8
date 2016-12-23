-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: acceler8
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `meters` int(11) NOT NULL,
  `team` int(11) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `team` (`team`),
  CONSTRAINT `Task_ibfk_1` FOREIGN KEY (`team`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (2,'Test Task 1',1000,40,'2016-12-10 08:15:52'),(4,'Test Task 2',2000,40,'2016-12-10 19:21:09'),(5,'Test Task 3',3000,40,'2016-12-10 19:21:24'),(6,'Test Task 4',4000,40,'2016-12-10 19:21:35'),(7,'Test Task 5',5000,40,'2016-12-10 19:21:52'),(8,'Test Task 6',6000,40,'2016-12-10 19:22:08'),(9,'Test Task 7',7000,40,'2016-12-10 19:22:23'),(10,'Test Task 8',8000,40,'2016-12-10 19:22:56'),(11,'Test Task 9',9000,40,'2016-12-10 19:23:09'),(12,'Test Task 10',10000,40,'2016-12-10 19:23:22'),(14,'Test Task 2',2000,42,'2016-12-10 19:34:31');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `website` varchar(255) NOT NULL,
  `isPrivate` tinyint(1) NOT NULL,
  `coach` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_coach` (`coach`),
  CONSTRAINT `fk_coach` FOREIGN KEY (`coach`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (40,'Test Team 1','Nicht Privat','Bern','Schweiz','www.testteam1.ch',0,30),(42,'Test Team 2','Test Description 2 - Non Privat','Test City 2','Test Country 2','www.testteam2.ch',0,30),(43,'Test Team 3','Test Description 3 - Non Privat','Test City 3','Test Country 3','www.testteam3.ch',0,30),(44,'Test Team 4','Test Description 4 - Non Privat','Test City 4','Test Country 4','www.testteam4.ch',0,30),(45,'Test Team 5','Test Description 5 - Non Privat','Test City 5','Test Country 5','www.testteam5.ch',0,30),(46,'Test Team 6','Test Description 6 - Privat','Test City 6','Test Country 6','www.testteam6.ch',1,30),(47,'Test Team 7','Test Description 7 - Privat','Test City 7','Test Country 7','www.testteam7.ch',1,30),(48,'Test Team 8','Test Description 8 - Privat','Test City 8','Test Country 8','www.testteam8.ch',1,30),(49,'Test Team 9','Test Description 9 - Privat','Test City 9','Test Country 9','www.testteam9.ch',1,30),(51,'Test Team 10','Test Description 10 - Privat','Test City 10','Test Country 10','www.testteam10.ch',1,30);
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teammember`
--

DROP TABLE IF EXISTS `teammember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teammember` (
  `team_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`team_id`),
  KEY `fk_team` (`team_id`),
  CONSTRAINT `fk_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teammember`
--

LOCK TABLES `teammember` WRITE;
/*!40000 ALTER TABLE `teammember` DISABLE KEYS */;
INSERT INTO `teammember` VALUES (40,30),(40,31),(40,32),(40,33),(40,34),(40,35),(40,36),(40,37),(40,38),(40,39),(42,30),(42,31),(43,30),(44,30),(45,30),(46,30),(47,30),(48,30),(49,30),(51,30);
/*!40000 ALTER TABLE `teammember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `token` char(36) NOT NULL,
  `user_id` int(11) NOT NULL,
  `type` varchar(8) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES ('ee039232-6e68-4643-8a0a-af30f024fb3c',30,'password','2016-12-19 21:02:30','acceler8.user1@public-files.de'),('82f56a1f-6cf8-485f-b902-ece8f2196265',30,'password','2016-12-20 19:38:35','acceler8.user1@public-files.de');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `password` binary(60) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `validated` tinyint(1) NOT NULL,
  `strava_code` varchar(255) DEFAULT NULL,
  `strava_token` varchar(255) DEFAULT NULL,
  `strava_token_public` varchar(255) DEFAULT NULL,
  `strava_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (30,'acceler8.user1@public-files.de','e0c21304-d6df-43e4-a0c1-2af5a375eb95','$2a$10$S3FmqFOOlrcUu5GMrLFTbehyXvYjIefuDIw6e57x37h6vZwYHs9jC','2016-12-20 19:44:22',1,'63f4f8b1265ce5c91ec201751f105484a42b65fe','cc2b0149c8ab534bf810eb8e86d54f947f4bb695','',18901648),(31,'acceler8.user2@public-files.de','66b1ce61-0503-44a8-a85c-9431a0107989','$2a$10$Kxyw6DZ2.FWTQSvWlYwqc.EIYoqrbqNGJsFSUuGTXh4ra.h3/M6/S','2016-12-22 10:27:27',1,'7cb281698fb307f9ce57db9eef17b6bd88b13ccb','6aedcd077bc94638395444a5d12c545c529d3825','',18902830),(32,'acceler8.user3@public-files.de','67b1a6e0-f400-4377-9bec-c33bba10e9f9','$2a$10$bPoiM2tlXCDS7pWT2eMeWeYILMFQ1aSxX/NNjLKa8I7b9vql2eQr2','2016-12-20 19:09:01',1,'7d6cf1ba7a64457f315d80dd279097483cd38c5c','40fc334425705ecefbb8eaa6c0f43f02131ec26e',NULL,18905018),(33,'acceler8.user4@public-files.de','81025c4f-8277-4192-a74a-c7c26736fd54','$2a$10$0lWreC3ZoqKPr3WLFpN2Nu60oxbT52kcjU.U9Ku4jGBicLNJJ10P6','2016-12-20 19:10:15',1,'8f94bddbd44766c6d256f228409043cffce2a2d0','034b72d221bda1a9caae3b51060906401921f696',NULL,18905042),(34,'acceler8.user5@public-files.de','0e2792b6-8a6a-4aae-82bd-f412004bfa10','$2a$10$xkVduzOPTbTTX4YXlWIlGOQAezAb82yOd.AKy9Kgg55YAaF1dtDfe','2016-12-20 19:11:26',1,'e3251cbc75da3b2c46884246580259e3e0d9a5ca','abc0696fa7f39a96c5428ffa3a75a47e1c2d01e8',NULL,18905059),(35,'acceler8.user6@public-files.de','d3b60f01-6465-48ff-8b25-5de06d66d79f','$2a$10$zIGN55cKUcmB8of55vcwWuEYhoXFhn8qKszOcx.ovmCWkpENmKSCm','2016-12-20 19:12:30',1,'18af8564300526003664ce4e0f44191aa55739b1','315fcf0707fbaa889202044bdeab1e09055a8401',NULL,18905074),(36,'acceler8.user7@public-files.de','70abc551-5e33-435d-b263-7fe7e33aaece','$2a$10$EvQzlia9gAd5V8I0gsC3nugNArmfe1jP0G.5jqn2zuqSgStkobNPu','2016-12-20 19:13:44',1,'e67024380e07bd0f422db385f3100c73030aa67a','f8926f51cc12784630f9d93b257a853c18076846',NULL,18905098),(37,'acceler8.user9@public-files.de','716b03fe-ef48-4bf3-80ab-534d10614e01','$2a$10$AiOvFY7BjMQUh/4LPCCYeOrs4HUHrGqL3U752kokMoaJi2zx3AOBa','2016-12-20 19:15:46',1,'1ac535cc90472d4da2950fe0f31eeaddf3c9e94b','ff1a7efef6bd3d77c44825509c09b3378a872548',NULL,18905136),(38,'acceler8.user10@public-files.de','98923906-04ba-47cb-ba5c-70fd8e81ed9f','$2a$10$uf6dOnLH3in2OUtudvA3ae4Rkp2qMZXTRYOKq5gfz4bFV7HWjclsW','2016-12-20 19:16:56',1,'1bac8823a1d452db342a817b163ee54e069e10b5','a5543d3ca1ec61acca883e6051f01b8b94a57e59',NULL,18905164),(39,'acceler8.user8@public-files.de','2be75db9-8803-408d-b503-b1f66d832be1','$2a$10$HPtwKBIO96PgiIL2jAaG0u9zSLHQEIroYh3yVdQpiOyiYQFMFQ9W2','2016-12-20 19:14:53',1,'80fb4344f9a77e81e827d06b937d5c145e4422ae','8183c40b71a301fdec27ad5b0f39630cce0165c1',NULL,18905120);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'acceler8'
--

--
-- Dumping routines for database 'acceler8'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-23  2:51:19
