-- MySQL dump 10.13  Distrib 5.5.17, for Win32 (x86)
--
-- Host: localhost    Database: cws
-- ------------------------------------------------------
-- Server version	5.5.17

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
-- Table structure for table `credit`
--

DROP TABLE IF EXISTS `credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit` (
  `credit_allowed` int(11) DEFAULT NULL,
  `customers_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit`
--

LOCK TABLES `credit` WRITE;
/*!40000 ALTER TABLE `credit` DISABLE KEYS */;
INSERT INTO `credit` VALUES (500,1),(500,2),(800,3),(800,4),(500,5),(800,6),(500,7),(600,8),(200,9),(1000,10),(500,11),(500,12);
/*!40000 ALTER TABLE `credit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_transactions`
--

DROP TABLE IF EXISTS `credit_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` int(11) DEFAULT NULL,
  `items_id` int(11) DEFAULT NULL,
  `items_number` int(11) DEFAULT NULL,
  `info` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_transactions`
--

LOCK TABLES `credit_transactions` WRITE;
/*!40000 ALTER TABLE `credit_transactions` DISABLE KEYS */;
INSERT INTO `credit_transactions` VALUES (1,1,1,1,'data1'),(2,2,3,1,'data2'),(3,3,5,1,'data3'),(4,4,9,5,'data4'),(5,5,10,1,'data5'),(6,6,11,1,'data6'),(7,7,14,1,'data7'),(8,8,4,1,'data8'),(9,9,10,2,'data9'),(10,10,6,1,'data10'),(11,11,12,1,'data11'),(12,12,15,1,'data12'),(13,13,16,2,'data13'),(14,14,10,7,'data14'),(15,15,10,2,'data15'),(16,16,8,1,'data16'),(17,17,2,1,'data17'),(18,18,10,5,'data18'),(19,19,13,1,'data19'),(20,20,17,3,'data20');
/*!40000 ALTER TABLE `credit_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `customers_id` int(11) NOT NULL AUTO_INCREMENT,
  `customers_firstname` varchar(100) DEFAULT NULL,
  `customers_secondname` varchar(100) DEFAULT NULL,
  `customers_address` varchar(255) DEFAULT NULL,
  `images_name` varchar(255) DEFAULT NULL,
  `customers_contact_number` int(11) DEFAULT NULL,
  `joining_day` int(11) DEFAULT NULL,
  `joining_month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`customers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Felicita','Wanjiru','Plot No 2 House No 3','1.gif',711422172,12,11,2011),(2,'Nicodemus','Muiya','Plot No 5 House No 3','1.gif',732421796,12,11,2011),(3,'Titus','Wanyonyi','Plot No 5 House No 2','1.gif',722412020,12,11,2011),(4,'Yusuf','Shamir','Plot No 11 House No 7','1.gif',712221177,12,11,2011),(5,'Otieno','Obadiah','Plot No 2 House No 10','1.gif',732525291,12,11,2011),(6,'John','Karanja','Plot No 7 House No 11','1.gif',713292582,12,11,2011),(7,'Emily','Ruto','Plot No 6 House No 3','1.gif',717442125,12,11,2011),(8,'Rufus','Anyona','Plot No 7 House No 9','1.gif',770224179,12,11,2011),(9,'Sophie','Asebe','Plot No 1 House No 6','1.gif',772412220,12,11,2011),(10,'Wilson','Otti','Plot No 1 House No 2','1.gif',203592142,12,11,2011),(11,'Hamad','Kutto','Plot No 2 House No 11','1.gif',715212020,12,11,2011),(12,'Mary','Weru','Plot No 3 House No 3','1.gif',721221777,12,11,2011);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `debit_transactions`
--

DROP TABLE IF EXISTS `debit_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `debit_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `info` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `debit_transactions`
--

LOCK TABLES `debit_transactions` WRITE;
/*!40000 ALTER TABLE `debit_transactions` DISABLE KEYS */;
INSERT INTO `debit_transactions` VALUES (1,24,300,'data1'),(2,25,200,'data2'),(3,26,69,'data3'),(4,27,200,'data4'),(5,28,200,'data5'),(6,29,100,'data6'),(7,30,20,'data7'),(8,31,200,'data8'),(9,32,50,'data9'),(10,33,500,'data10');
/*!40000 ALTER TABLE `debit_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `items_id` int(11) NOT NULL AUTO_INCREMENT,
  `items_name` varchar(255) DEFAULT NULL,
  `items_cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`items_id`),
  UNIQUE KEY `items_id_UNIQUE` (`items_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'Cooking Oil',326),(2,'half litre Cooking Oil',60),(3,'Two kg Maize Flour',69),(4,'One kg Maize Flour',36),(5,'One kg Sugar',98),(6,'Two kg Sugar',196),(8,'quarter kg Sugar',50),(9,'Phillips Bulbs',50),(10,'Cash',1),(11,'Two kg Wheat Flour',69),(12,'Quarter Kg Rice',30),(13,'Half Kg Rice',58),(14,'Three Kg Rice',360),(15,'Bar Soap',40),(16,'Toilet Soap',30),(17,'Tissue Paper',20);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_types`
--

DROP TABLE IF EXISTS `transaction_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` int(11) DEFAULT NULL,
  `transaction_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_types`
--

LOCK TABLES `transaction_types` WRITE;
/*!40000 ALTER TABLE `transaction_types` DISABLE KEYS */;
INSERT INTO `transaction_types` VALUES (1,1,'Credit Transactions'),(2,2,'Debit Transactions');
/*!40000 ALTER TABLE `transaction_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_type` int(11) DEFAULT NULL,
  `customers_id` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,1,1,9,2011),(2,1,1,1,9,2011),(3,1,1,1,9,2011),(4,1,2,1,9,2011),(5,1,3,4,9,2011),(6,1,4,4,9,2011),(7,1,5,4,9,2011),(8,1,6,4,9,2011),(9,1,7,6,9,2011),(10,1,8,6,9,2011),(11,1,1,12,9,2011),(12,1,6,12,9,2011),(13,1,9,12,9,2011),(14,1,10,15,9,2011),(15,1,12,15,9,2011),(16,1,12,21,9,2011),(17,1,11,17,9,2011),(18,1,6,20,9,2011),(19,1,12,21,9,2011),(20,1,11,21,9,2011),(24,2,1,1,9,2011),(25,2,2,1,9,2011),(26,2,4,4,9,2011),(27,2,5,4,9,2011),(28,2,7,6,9,2011),(29,2,8,6,9,2011),(30,2,1,12,9,2011),(31,2,10,15,9,2011),(32,2,12,16,9,2011),(33,2,6,21,9,2011);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-12-30  3:12:44
