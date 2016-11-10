/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.24 : Database - eoss
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`eoss` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `eoss`;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `ID` int(32) NOT NULL,
  `PID` int(32) DEFAULT NULL,
  `NAME` varchar(100) NOT NULL,
  `LEAF` int(11) NOT NULL,
  `PATH` varchar(1000) DEFAULT NULL,
  `LEV` int(11) DEFAULT NULL,
  `URL` varchar(1000) DEFAULT NULL,
  `TARGET` varchar(100) DEFAULT NULL,
  `CREATE_DATETIME` varchar(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`ID`,`PID`,`NAME`,`LEAF`,`PATH`,`LEV`,`URL`,`TARGET`,`CREATE_DATETIME`) values (1,-1,'菜单根目录',0,'--',0,NULL,NULL,'2012-12-15 18:08:07'),(2,1,'系统管理',0,'1#2',1,NULL,NULL,'2012-12-13 13:46:00'),(3,1,'业务管理',0,'1#3',1,NULL,NULL,'2012-12-13 13:53:10'),(4,2,'菜单管理',0,'1#2#4',2,NULL,NULL,'2012-12-13 16:00:02'),(5,2,'用户管理',0,'1#2#5',2,NULL,NULL,'2012-12-13 16:00:02'),(6,2,'角色管理',0,'1#2#6',2,NULL,NULL,'2012-12-13 16:00:02'),(7,4,'菜单添加',1,'1#2#4#7',3,'/system/menu/toAdd.ac','centerFrame','2012-12-13 16:00:02'),(8,2,'权限管理',0,'1#2#8',2,NULL,NULL,'2012-12-16 13:59:33'),(9,4,'菜单查询',1,'1#2#4#9',3,'/system/menu/toList.ac','centerFrame','2012-12-16 14:15:20'),(10,8,'权限添加',1,'1#2#8#10',3,'/system/permission/toAdd.ac','centerFrame','2012-12-20 14:58:29'),(13,8,'权限查询',1,'1#2#8#13',3,'/system/permission/toList.ac','centerFrame','2012-12-20 16:28:03'),(14,6,'角色添加',1,'1#2#6#14',3,'/system/role/toAdd.ac','centerFrame','2012-12-22 12:36:14'),(15,6,'角色查询',1,'1#2#6#15',3,'/system/role/toList.ac','centerFrame','2012-12-22 14:29:28'),(16,5,'用户添加',1,'1#2#5#16',3,'/system/user/toAdd.ac','centerFrame','2012-12-22 18:34:26'),(17,5,'用户查询',1,'1#2#5#17',3,'/system/user/toList.ac','centerFrame','2012-12-22 19:56:13'),(18,2,'资源管理',0,'1#2#18',2,NULL,NULL,'2016-10-21 11:43:59'),(19,18,'资源添加',1,'1#2#18#19',3,'/system/resource/toAdd.ac','centerFrame','2016-10-21 11:46:46'),(20,18,'资源查询',1,'1#2#18#20',3,'/system/resource/toList.ac','centerFrame','2016-10-21 11:47:05'),(21,2,'安全规则',0,'1#2#21',2,NULL,NULL,'2016-10-25 12:27:00'),(22,21,'规则更新',1,'1#2#21#22',3,'/system/filterDefinition/toUpdate.ac','centerFrame','2016-10-25 12:28:26');

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`ID`,`NAME`) values (1,'permission:add'),(2,'permission:list'),(3,'permission:delete'),(4,'permission:*');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `CREATE_DATETIME` varchar(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`ID`,`NAME`,`CREATE_DATETIME`) values (1,'系统管理员','2012-12-22 16:27:04'),(2,'业务员','2016-10-26 19:01:45');

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `PERMISSION_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_permission` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(32) DEFAULT NULL,
  `locked` tinyint(4) DEFAULT '0',
  `disabled` tinyint(4) DEFAULT '0',
  `CREATE_DATETIME` varchar(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`ID`,`username`,`password`,`salt`,`locked`,`disabled`,`CREATE_DATETIME`) values (1,'admin','f717c48426a62bad132a9b7718a02ff4','683',0,0,'2012-12-22 19:25:48');

/*Table structure for table `user_menu` */

DROP TABLE IF EXISTS `user_menu`;

CREATE TABLE `user_menu` (
  `MENU_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_menu` */

insert  into `user_menu`(`MENU_ID`,`USER_ID`) values (7,1),(9,1),(16,1),(17,1),(14,1),(15,1),(10,1),(13,1),(19,1),(20,1),(22,1);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`USER_ID`,`ROLE_ID`) values (1,1),(1,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
