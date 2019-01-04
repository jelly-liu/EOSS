/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1
 Source Database       : eoss

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 01/04/2019 09:57:27 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_menu`
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
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

-- ----------------------------
--  Records of `admin_menu`
-- ----------------------------
BEGIN;
INSERT INTO `admin_menu` VALUES ('1', '-1', '菜单根目录', '0', '--', '0', null, null, '2012-12-15 18:08:07'), ('2', '1', '系统管理', '0', '1#2', '1', null, null, '2012-12-13 13:46:00'), ('3', '1', '业务管理', '0', '1#3', '1', null, null, '2012-12-13 13:53:10'), ('4', '2', '菜单管理', '0', '1#2#4', '2', null, null, '2012-12-13 16:00:02'), ('5', '2', '用户管理', '0', '1#2#5', '2', null, null, '2012-12-13 16:00:02'), ('6', '2', '角色管理', '0', '1#2#6', '2', null, null, '2012-12-13 16:00:02'), ('7', '4', '菜单添加', '1', '1#2#4#7', '3', '/system/menu/toAdd.ac', 'centerFrame', '2012-12-13 16:00:02'), ('8', '2', '权限管理', '0', '1#2#8', '2', null, null, '2012-12-16 13:59:33'), ('9', '4', '菜单查询', '1', '1#2#4#9', '3', '/system/menu/toList.ac', 'centerFrame', '2012-12-16 14:15:20'), ('10', '8', '权限添加', '1', '1#2#8#10', '3', '/system/permission/toAdd.ac', 'centerFrame', '2012-12-20 14:58:29'), ('13', '8', '权限查询', '1', '1#2#8#13', '3', '/system/permission/toList.ac', 'centerFrame', '2012-12-20 16:28:03'), ('14', '6', '角色添加', '1', '1#2#6#14', '3', '/system/role/toAdd.ac', 'centerFrame', '2012-12-22 12:36:14'), ('15', '6', '角色查询', '1', '1#2#6#15', '3', '/system/role/toList.ac', 'centerFrame', '2012-12-22 14:29:28'), ('16', '5', '用户添加', '1', '1#2#5#16', '3', '/system/user/toAdd.ac', 'centerFrame', '2012-12-22 18:34:26'), ('17', '5', '用户查询', '1', '1#2#5#17', '3', '/system/user/toList.ac', 'centerFrame', '2012-12-22 19:56:13'), ('18', '2', '资源管理', '0', '1#2#18', '2', null, null, '2016-10-21 11:43:59'), ('19', '18', '资源添加', '1', '1#2#18#19', '3', '/system/resource/toAdd.ac', 'centerFrame', '2016-10-21 11:46:46'), ('20', '18', '资源查询', '1', '1#2#18#20', '3', '/system/resource/toList.ac', 'centerFrame', '2016-10-21 11:47:05'), ('21', '2', '安全规则', '0', '1#2#21', '2', null, null, '2016-10-25 12:27:00'), ('22', '21', '规则更新', '1', '1#2#21#22', '3', '/system/filterDefinition/toUpdate.ac', 'centerFrame', '2016-10-25 12:28:26');
COMMIT;

-- ----------------------------
--  Table structure for `admin_permission`
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(128) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_permission`
-- ----------------------------
BEGIN;
INSERT INTO `admin_permission` VALUES ('1', 'permission:add'), ('2', 'permission:list'), ('3', 'permission:delete'), ('4', 'permission:*');
COMMIT;

-- ----------------------------
--  Table structure for `admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `CREATE_DATETIME` varchar(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_role`
-- ----------------------------
BEGIN;
INSERT INTO `admin_role` VALUES ('1', '系统管理员', '2012-12-22 16:27:04'), ('2', '业务员', '2016-10-26 19:01:45');
COMMIT;

-- ----------------------------
--  Table structure for `admin_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission` (
  `PERMISSION_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `ID` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(32) DEFAULT NULL,
  `locked` tinyint(4) DEFAULT '0',
  `disabled` tinyint(4) DEFAULT '0',
  `CREATE_DATETIME` varchar(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_user`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES ('1', 'admin', 'f717c48426a62bad132a9b7718a02ff4', '683', '0', '0', '2012-12-22 19:25:48');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user_menu`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_menu`;
CREATE TABLE `admin_user_menu` (
  `MENU_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_user_menu`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_menu` VALUES ('7', '1'), ('9', '1'), ('16', '1'), ('17', '1'), ('14', '1'), ('15', '1'), ('10', '1'), ('13', '1'), ('19', '1'), ('20', '1'), ('22', '1');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_role` VALUES ('1', '1'), ('1', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
