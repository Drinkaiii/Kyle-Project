-- MySQL dump 10.13  Distrib 8.0.37, for Linux (x86_64)
--
-- Host: localhost    Database: stylish
-- ------------------------------------------------------
-- Server version	8.0.37-0ubuntu0.22.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `story` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `campaign_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,2,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/741e9094-39ab-4a0a-9df6-3fbd1c594a0d.jpg','瞬間\r\n在城市的角落\r\n找到失落多時的記憶。\r\n印象《都會故事集》');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'women'),(2,'men'),(3,'accessories');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkout` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `prime` varchar(255) DEFAULT NULL,
  `status_code` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `comfirmed_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'#ffc0cb','粉紅色'),(2,'#e0ffff','淺藍色'),(3,'#98fb98','灰綠'),(4,'#c0c0c0','銀色'),(5,'#ffffe0','亮黃'),(6,'#fff0f5','薰衣草紫紅'),(7,'#87cefa','亮天藍'),(8,'#fff8dc','玉米絲色'),(9,'#f8f8ff','幽靈白'),(10,'#dcdcdc','庚斯博羅灰'),(11,'#696969','昏灰'),(12,'#696969','昏灰'),(13,'#fff0f5','薰衣草紫紅'),(14,'#191970','午夜藍'),(15,'#ffe4e1','霧玫瑰色'),(16,'#afeeee','灰綠松石色'),(17,'#ffe4b5','鹿皮鞋色'),(18,'#ffa500','橙色'),(19,'#cd853f','秘魯色'),(20,'#fffff0','象牙色'),(21,'#fff0f5','薰衣草紫紅'),(22,'#e6e6fa','薰衣草色'),(23,'#87cefa','亮天藍'),(24,'#f08080','亮珊瑚色'),(25,'#fffacd','檸檬綢色');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot`
--

DROP TABLE IF EXISTS `hot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `hot_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot`
--

LOCK TABLES `hot` WRITE;
/*!40000 ALTER TABLE `hot` DISABLE KEYS */;
/*!40000 ALTER TABLE `hot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `url` text,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,1,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/fb50dcf3-c9a4-4f23-8319-1ec3dedf5f06.jpeg'),(2,1,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/3d66cc8c-c31a-4a91-9fc2-3f045db0bab7.jpeg'),(3,2,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/80bbb234-5f8e-418f-9dba-d2bfb3db1f8c.jpg'),(4,2,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/2a835b08-f855-4471-8bdd-06d1981533d9.jpg'),(5,3,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/304ad868-4105-4827-a447-044701225975.jpg'),(6,3,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/6ed2400d-fd8f-45f7-bc46-850797d25210.jpg'),(7,4,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/1cfe6cf0-95a1-4fe7-85fd-fa77bda650a0.jpg'),(8,4,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/e58d4cad-00bc-4f2d-b457-d6a10d611ad8.jpg'),(9,5,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/69190c13-f952-4b0d-9e12-74b7a0c1c6ee.jpg'),(10,5,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/78e350f8-b967-4365-8d44-3f77126bc8fc.jpg'),(11,6,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/68a22c38-c85b-4e96-93a4-a13f3ac266de.jpg'),(12,6,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/c4638aab-0900-44ff-82fa-dddcdc24f4f1.jpg'),(13,7,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/91faad0f-d5d8-401e-b871-34656446679c.jpeg'),(14,7,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/4bc423b6-5a5c-45f8-be1e-c77ad60a3aa0.jpeg'),(15,8,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/9594d799-694b-4aad-8d9d-63b5984212fb.jpg'),(16,8,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/4f99e4e9-ebdf-48cc-9a2d-7d011af9679a.jpg'),(17,9,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/f61106d1-edad-4b22-b327-8966b8c2844f.jpg'),(18,9,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/183511a5-80a0-4b54-9778-abbe318f2570.jpg'),(19,10,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/9905accc-49b1-4603-9f12-9810700680fe.jpg'),(20,10,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/609aab66-f074-40c9-a422-ba4e24acff75.jpg'),(21,11,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/b54494fa-7775-4e42-945e-5af14cb56d48.jpg'),(22,11,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/6ae76164-c59a-4c6b-8cc9-9702a2f7eaca.jpg'),(23,12,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/21bf649f-79d6-4ebc-af5c-45a4064445ee.jpg'),(24,12,'https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/a778a59a-2f10-4939-a877-34310f2ef368.jpg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `checkout_id` bigint DEFAULT NULL,
  `shipping` varchar(255) DEFAULT NULL,
  `payment` varchar(255) DEFAULT NULL,
  `subtotal` int DEFAULT NULL,
  `freight` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `checkout_id` (`checkout_id`),
  CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`checkout_id`) REFERENCES `checkout` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `place`
--

DROP TABLE IF EXISTS `place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `place` (
  `id` int NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` VALUES (1,'韓國'),(2,'中國'),(3,'越南'),(4,'日本'),(5,'臺灣'),(6,'美國'),(7,'加拿加'),(8,'巴西'),(9,'印尼');
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `des` text,
  `price` int DEFAULT NULL,
  `texture` varchar(255) DEFAULT NULL,
  `wash` varchar(255) DEFAULT NULL,
  `place_id` int DEFAULT NULL,
  `note` text,
  `story` text,
  `main_image` text,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `place_id` (`place_id`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`place_id`) REFERENCES `place` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'前開衩扭結洋裝','輕薄保暖',799,'棉 100%','想怎麼洗就怎麼洗',1,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/ef18b7c5-b383-48ae-8589-b00226eba8aa.jpeg'),(2,1,'透肌澎澎防曬襯衫','輕薄保暖',599,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/890fe33a-0cba-410a-a221-e2c0421075d9.jpg'),(3,1,'小扇紋細織上衣','輕薄保暖',599,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/f7b8c430-53ed-4afe-bb19-43a77d0dc66f.jpg'),(4,1,'活力花紋長筒牛仔褲','輕薄保暖',599,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/56d035c7-6612-4cc1-8b9a-fff336bec9b5.jpg'),(5,2,'純色輕薄百搭襯衫','輕薄保暖',799,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/dbd51708-a3fb-4d04-9824-47b7302d244c.jpg'),(6,2,'時尚輕鬆休閒西裝','輕薄保暖',799,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/970b7f22-ac7d-4f0a-a6ba-8fdf257d0330.jpg'),(7,1,'精緻扭結洋裝','輕薄保暖',999,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/5e121ca6-84f5-4cee-b40b-a628f335ba6c.jpeg'),(8,2,'經典商務西裝','輕薄保暖',3999,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/906dd4bf-0643-4921-9863-b2770cb81728.jpg'),(9,3,'夏日海灘戶外遮陽帽','輕薄保暖',1499,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/81bc713b-c15b-4575-9692-fd16a2452981.jpg'),(10,3,'經典牛仔帽','輕薄保暖',799,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/8f5f609a-73e3-4678-b302-53209f29dae3.jpg'),(11,3,'卡哇伊多功能隨身包','輕巧好帶',1299,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/98b324fa-f6e0-497e-b3cc-aeb810947126.jpg'),(12,3,'柔軟氣質羊毛圍巾','輕巧好帶',1799,'棉 100%','想怎麼洗就怎麼洗',2,'實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','https://bucket-kai-test.s3.ap-northeast-1.amazonaws.com/aa75b9c8-fa40-4ac7-bba5-d3cb3dd9904b.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_color`
--

DROP TABLE IF EXISTS `product_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_color` (
  `product_id` bigint NOT NULL,
  `color_id` bigint NOT NULL,
  PRIMARY KEY (`product_id`,`color_id`),
  KEY `color_id` (`color_id`),
  CONSTRAINT `product_color_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `product_color_ibfk_2` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_color`
--

LOCK TABLES `product_color` WRITE;
/*!40000 ALTER TABLE `product_color` DISABLE KEYS */;
INSERT INTO `product_color` VALUES (1,1),(2,2),(2,3),(3,4),(3,5),(3,6),(4,7),(4,8),(5,9),(5,10),(6,11),(7,12),(7,13),(8,14),(8,15),(8,16),(9,17),(9,18),(10,19),(10,20),(11,21),(11,22),(12,23),(12,24),(12,25);
/*!40000 ALTER TABLE `product_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_list`
--

DROP TABLE IF EXISTS `product_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `color_id` bigint DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_list`
--

LOCK TABLES `product_list` WRITE;
/*!40000 ALTER TABLE `product_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_size`
--

DROP TABLE IF EXISTS `product_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_size` (
  `product_id` bigint DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  KEY `product_id` (`product_id`),
  KEY `size_id` (`size_id`),
  CONSTRAINT `product_size_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE SET NULL,
  CONSTRAINT `product_size_ibfk_2` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_size`
--

LOCK TABLES `product_size` WRITE;
/*!40000 ALTER TABLE `product_size` DISABLE KEYS */;
INSERT INTO `product_size` VALUES (1,1),(1,2),(2,1),(2,2),(2,3),(3,1),(3,2),(3,3),(4,1),(4,2),(4,3),(5,1),(5,2),(5,3),(6,1),(6,2),(6,3),(7,1),(7,2),(7,3),(8,1),(8,2),(8,3),(9,1),(10,2),(10,3),(11,2),(11,3),(12,1),(12,2),(12,3);
/*!40000 ALTER TABLE `product_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipient`
--

DROP TABLE IF EXISTS `recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipient` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `recipient_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipient`
--

LOCK TABLES `recipient` WRITE;
/*!40000 ALTER TABLE `recipient` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `symbol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'S'),(2,'M'),(3,'L');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test2','test2@test.com','$2a$10$98r5F6VxfBJ/qB0eySZ5Ne6gDe.4GIf/.1Cw4p6mVAQ0GqtCcSQN.','http://test.picture','native'),(2,'林仁凱','kyle890316@gmail.com','Facebook Authorization','default picture url','facebook');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `color_code` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (1,1,'#ffc0cb','1',5),(2,1,'#ffc0cb','2',12),(3,2,'#e0ffff','1',3),(4,2,'#e0ffff','2',11),(5,2,'#e0ffff','3',6),(6,2,'#98fb98','1',14),(7,2,'#98fb98','2',8),(8,2,'#98fb98','3',21),(9,3,'#c0c0c0','1',19),(10,3,'#c0c0c0','2',6),(11,3,'#c0c0c0','3',11),(12,3,'#ffffe0','1',16),(13,3,'#ffffe0','2',15),(14,3,'#ffffe0','3',18),(15,3,'#fff0f5','1',6),(16,3,'#fff0f5','2',7),(17,3,'#fff0f5','3',8),(18,4,'#87cefa','1',3),(19,4,'#87cefa','2',7),(20,4,'#87cefa','3',0),(21,4,'#fff8dc','1',0),(22,4,'#fff8dc','2',11),(23,4,'#fff8dc','3',5),(24,5,'#f8f8ff','1',6),(25,5,'#f8f8ff','2',0),(26,5,'#f8f8ff','3',12),(27,5,'#dcdcdc','1',7),(28,5,'#dcdcdc','2',18),(29,5,'#dcdcdc','3',9),(30,6,'#696969','1',0),(31,6,'#696969','2',12),(32,6,'#696969','3',1),(33,7,'#696969','1',0),(34,7,'#696969','2',11),(35,7,'#696969','3',11),(36,7,'#fff0f5','1',11),(37,7,'#fff0f5','2',11),(38,7,'#fff0f5','3',11),(39,8,'#191970','1',12),(40,8,'#191970','2',4),(41,8,'#191970','3',1),(42,8,'#ffe4e1','1',1),(43,8,'#ffe4e1','2',5),(44,8,'#ffe4e1','3',5),(45,8,'#afeeee','1',5),(46,8,'#afeeee','2',5),(47,8,'#afeeee','3',5),(48,9,'#ffe4b5','1',12),(49,9,'#ffa500','1',3),(50,10,'#cd853f','2',3),(51,10,'#cd853f','3',5),(52,10,'#fffff0','2',5),(53,10,'#fffff0','3',11),(54,11,'#fff0f5','2',3),(55,11,'#fff0f5','3',4),(56,11,'#e6e6fa','2',12),(57,11,'#e6e6fa','3',11),(58,12,'#87cefa','1',3),(59,12,'#87cefa','2',5),(60,12,'#87cefa','3',5),(61,12,'#f08080','1',12),(62,12,'#f08080','2',4),(63,12,'#f08080','3',1),(64,12,'#fffacd','1',14),(65,12,'#fffacd','2',15),(66,12,'#fffacd','3',11);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-01 11:06:04
