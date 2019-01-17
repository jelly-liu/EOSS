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

 Date: 01/16/2019 19:25:34 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin_filterchain_definition`
-- ----------------------------
DROP TABLE IF EXISTS `admin_filterchain_definition`;
CREATE TABLE `admin_filterchain_definition` (
  `id` int(11) NOT NULL,
  `definition` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `admin_filterchain_definition`
-- ----------------------------
BEGIN;
INSERT INTO `admin_filterchain_definition` VALUES ('1', '[urls]\n/static/** = anon\n/toLogin = anon\n/login = anon\n/system/**/*add* = authc, rolesOr[系统管理员]\n/system/**/*update* = authc, rolesOr[系统管理员]\n/system/**/*delete* = authc, rolesOr[系统管理员]\n/** = authc');
COMMIT;

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
INSERT INTO `admin_menu` VALUES ('1', '-1', '菜单根目录', '0', '--', '0', null, null, '2012-12-15 18:08:07'), ('2', '1', '系统管理', '0', '1#2', '1', null, null, '2012-12-13 13:46:00'), ('3', '1', '业务管理', '0', '1#3', '1', null, null, '2012-12-13 13:53:10'), ('4', '2', '菜单管理', '0', '1#2#4', '2', null, null, '2012-12-13 16:00:02'), ('5', '2', '用户管理', '0', '1#2#5', '2', null, null, '2012-12-13 16:00:02'), ('6', '2', '角色管理', '0', '1#2#6', '2', null, null, '2012-12-13 16:00:02'), ('7', '4', '菜单添加', '1', '1#2#4#7', '3', '/system/menu/toAdd', 'centerFrame', '2012-12-13 16:00:02'), ('8', '2', '权限管理', '0', '1#2#8', '2', null, null, '2012-12-16 13:59:33'), ('9', '4', '菜单查询', '1', '1#2#4#9', '3', '/system/menu/toList', 'centerFrame', '2012-12-16 14:15:20'), ('10', '8', '权限添加', '1', '1#2#8#10', '3', '/system/permission/toAdd', 'centerFrame', '2012-12-20 14:58:29'), ('13', '8', '权限查询', '1', '1#2#8#13', '3', '/system/permission/toList', 'centerFrame', '2012-12-20 16:28:03'), ('14', '6', '角色添加', '1', '1#2#6#14', '3', '/system/role/toAdd', 'centerFrame', '2012-12-22 12:36:14'), ('15', '6', '角色查询', '1', '1#2#6#15', '3', '/system/role/toList', 'centerFrame', '2012-12-22 14:29:28'), ('16', '5', '用户添加', '1', '1#2#5#16', '3', '/system/user/toAdd', 'centerFrame', '2012-12-22 18:34:26'), ('17', '5', '用户查询', '1', '1#2#5#17', '3', '/system/user/toList', 'centerFrame', '2012-12-22 19:56:13'), ('18', '2', '资源管理', '0', '1#2#18', '2', null, null, '2016-10-21 11:43:59'), ('19', '18', '资源添加', '1', '1#2#18#19', '3', '/system/resource/toAdd', 'centerFrame', '2016-10-21 11:46:46'), ('20', '18', '资源查询', '1', '1#2#18#20', '3', '/system/resource/toList', 'centerFrame', '2016-10-21 11:47:05'), ('21', '2', '安全规则', '0', '1#2#21', '2', null, null, '2016-10-25 12:27:00'), ('22', '21', '规则更新', '1', '1#2#21#22', '3', '/system/filterDefinition/toUpdate', 'centerFrame', '2016-10-25 12:28:26'), ('23', '18', 't1231', '0', '1#2#18#23', '3', null, null, '2019-01-12 20:30:39');
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
INSERT INTO `admin_permission` VALUES ('1', 'menu:add'), ('2', 'menu:list'), ('3', 'menu:delete'), ('5', 'menu:*'), ('6', 'user:*'), ('7', 'user:add'), ('8', 'user:delete'), ('9', 'user:list'), ('10', 'role:*'), ('11', 'role:add'), ('12', 'role:delete'), ('13', 'role:list'), ('14', 'perm:*'), ('15', 'perm:add'), ('16', 'perm:delete'), ('17', 'perm:list'), ('18', 'res:*'), ('19', 'res:add'), ('20', 'res:delete'), ('21', 'res:list'), ('22', 'filterDef:*');
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
INSERT INTO `admin_role` VALUES ('1', '系统管理员', '2012-12-22 16:27:04'), ('2', '系统查看员', '2019-01-06 11:23:39'), ('3', 't1', '2019-01-12 22:08:12');
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
--  Records of `admin_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `admin_role_permission` VALUES ('22', '1'), ('5', '1'), ('14', '1'), ('18', '1'), ('10', '1'), ('6', '1'), ('2', '2'), ('17', '2'), ('21', '2'), ('13', '2'), ('9', '2'), ('23', '3'), ('1', '3'), ('15', '3'), ('19', '3'), ('11', '3'), ('7', '3');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `ID` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(44) DEFAULT NULL,
  `locked` tinyint(4) DEFAULT '0',
  `disabled` tinyint(4) DEFAULT '0',
  `CREATE_DATETIME` varchar(19) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_user`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES ('1', 'admin', '503a736f87ec3b18e62314196a1d26fc', '/JRVFniRN/xNGNth2AmqOgeRhGXGITPFlYlOJ5oMXDQ=', '0', '0', '2012-12-22 19:25:48'), ('2', 'adminViewer', '7f5dded2d7b72d09093ba6d4911e91c2', 'gNtOrtPpdrcuGcUNc938RJrNz3iYPWHIeMiJiWSSkY8=', '0', '0', '2019-01-06 11:25:57');
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
INSERT INTO `admin_user_menu` VALUES ('7', '1'), ('9', '1'), ('16', '1'), ('17', '1'), ('14', '1'), ('15', '1'), ('10', '1'), ('13', '1'), ('19', '1'), ('20', '1'), ('22', '1'), ('7', '2'), ('9', '2'), ('16', '2'), ('17', '2'), ('14', '2'), ('15', '2'), ('10', '2'), ('13', '2'), ('19', '2'), ('20', '2'), ('22', '2');
COMMIT;

-- ----------------------------
--  Table structure for `admin_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_role` VALUES ('1', '1'), ('2', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
