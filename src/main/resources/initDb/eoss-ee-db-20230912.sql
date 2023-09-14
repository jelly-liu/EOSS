-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: eoss_ee
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `sys_perm`
--

DROP TABLE IF EXISTS `sys_perm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_perm` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '权限名称',
  `type` varchar(45) DEFAULT NULL COMMENT '权限类型，menu-菜单，page-页面，func-页面里功能按钮',
  `url` varchar(128) DEFAULT NULL COMMENT '请求地址，对应页面的地址',
  `url_submit` varchar(128) DEFAULT NULL COMMENT '请求地址，对应页面的提交的地址',
  `path` varchar(45) DEFAULT NULL COMMENT '树形节点路径',
  `leaf` tinyint DEFAULT NULL COMMENT '是否叶子节点',
  `level_num` tinyint DEFAULT NULL COMMENT '树的第几级，根是0级',
  `sort_num` tinyint DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_perm`
--

LOCK TABLES `sys_perm` WRITE;
/*!40000 ALTER TABLE `sys_perm` DISABLE KEYS */;
INSERT INTO `sys_perm` VALUES (1,-1,'根目录','menu',NULL,NULL,'1',0,0,1,'2023-02-06 15:45:26','2023-02-06 15:45:26'),(2,1,'系统管理','menu',NULL,NULL,'1#2',0,1,1,'2023-02-06 15:45:26','2023-02-14 16:20:22'),(25,2,'权限管理','menu','/sys/perm/to-list','/sys/perm/list','1#2#25',1,2,3,'2023-02-06 15:45:26','2023-02-14 16:07:46'),(26,2,'角色管理','menu','/sys/role/to-list','/sys/role/list','1#2#26',1,2,2,'2023-02-13 21:55:17','2023-02-14 16:07:33'),(31,2,'角色添加','func','/sys/role/to-add','/sys/role/add','1#2#31',1,2,1,'2023-02-13 21:58:52','2023-02-14 16:29:34'),(32,2,'用户管理','menu','/sys/user/to-list','/sys/user/list','1#2#32',1,2,1,'2023-02-13 21:59:22','2023-02-14 16:05:34'),(38,2,'用户删除','func',NULL,'/sys/user/delete','1#2#38',1,2,1,'2023-02-14 15:05:09','2023-02-14 16:20:32'),(39,2,'角色删除','func',NULL,'/sys/role/delete','1#2#39',1,2,1,'2023-02-14 15:05:30','2023-02-14 16:20:35'),(40,2,'权限删除','func',NULL,'/sys/perm/delete','1#2#40',1,2,1,'2023-02-14 15:05:46','2023-02-14 16:20:39'),(41,2,'用户添加','func','/sys/user/to-add','/sys/user/add','1#2#41',1,2,1,'2023-02-14 16:06:51','2023-02-14 16:06:51'),(42,2,'用户更新','func','/sys/user/to-update','/sys/user/update','1#2#42',1,2,1,'2023-02-14 16:07:20','2023-02-14 16:07:20'),(43,2,'角色更新','func','/sys/role/to-update','/sys/role/update','1#2#43',1,2,1,'2023-02-14 16:09:13','2023-02-14 16:09:13'),(44,2,'权限添加','func','/sys/perm/to-add','/sys/perm/add','1#2#44',1,2,1,'2023-02-14 16:10:02','2023-02-14 16:11:08'),(45,2,'权限更新','func','/sys/perm/to-update','/sys/perm/update','1#2#45',1,2,1,'2023-02-14 16:10:39','2023-02-14 16:10:39'),(49,1,'所有权限','func',NULL,'/**','1#49',1,1,1,'2023-03-07 15:46:06','2023-03-07 15:46:06');
/*!40000 ALTER TABLE `sys_perm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (7,'系统管理员','2023-02-10 15:37:56','2023-03-07 23:46:35'),(8,'只能查看','2023-02-13 11:51:21','2023-02-13 22:03:28');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_perm`
--

DROP TABLE IF EXISTS `sys_role_perm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_perm` (
  `role_id` int NOT NULL,
  `perm_id` int NOT NULL,
  PRIMARY KEY (`role_id`,`perm_id`),
  UNIQUE KEY `index1` (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_perm`
--

LOCK TABLES `sys_role_perm` WRITE;
/*!40000 ALTER TABLE `sys_role_perm` DISABLE KEYS */;
INSERT INTO `sys_role_perm` VALUES (7,25),(7,26),(7,31),(7,32),(7,38),(7,39),(7,40),(7,41),(7,42),(7,43),(7,44),(7,45),(8,3),(8,25),(8,26),(8,27),(8,32),(8,33);
/*!40000 ALTER TABLE `sys_role_perm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_sample`
--

DROP TABLE IF EXISTS `sys_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_sample` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `info` json DEFAULT NULL,
  `version` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_sample`
--

LOCK TABLES `sys_sample` WRITE;
/*!40000 ALTER TABLE `sys_sample` DISABLE KEYS */;
INSERT INTO `sys_sample` VALUES (1,'tom',300,'{\"type\": \"india\"}',NULL,'2023-02-17 19:42:44'),(2,'jerry',100,'{\"type\": \"india1\"}',NULL,NULL),(3,'dog',NULL,'{\"type\": \"india2\"}',NULL,NULL);
/*!40000 ALTER TABLE `sys_sample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(44) DEFAULT NULL,
  `disabled` tinyint DEFAULT '0' COMMENT '是否禁用，0-否，1-是',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'robot','8714da509df71a416e47de5142a34774','1676286377392',0,'2023-02-13 19:06:17','2023-02-13 22:06:57'),(20,'admin','8714da509df71a416e47de5142a34774','1676286377392',0,'2023-02-13 19:06:17','2023-02-13 19:06:17');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_perm`
--

DROP TABLE IF EXISTS `sys_user_perm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_perm` (
  `user_id` int NOT NULL,
  `perm_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`perm_id`),
  UNIQUE KEY `index1` (`user_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_perm`
--

LOCK TABLES `sys_user_perm` WRITE;
/*!40000 ALTER TABLE `sys_user_perm` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_perm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  UNIQUE KEY `index1` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (20,7),(55,7);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-12 18:42:05
