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

 Date: 03/28/2019 19:07:42 PM
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
INSERT INTO `admin_filterchain_definition` VALUES ('1', '######\n#from first rule to last\n#the url pattern definitions follow a \'first match wins\' paradigm\n#once any one matched, others will not be evaluated\n######\n[urls]\n/static/** = anon\n/toLogin =anon\n/login = anon\n\n#all users whor are logined, have permission to update self password\n/system/user/*PasswordUpdate = myauthc\n\n/system/menu/*add = myauthc, permsOr[菜单:添加]\n/system/menu/*delete = myauthc, permsOr[菜单:删除]\n/system/menu/*list = myauthc, permsOr[菜单:查看]\n\n/system/resource/*add = myauthc, permsOr[资源:添加]\n/system/resource/*delete = myauthc, permsOr[资源:删除]\n/system/resource/*update = myauthc, permsOr[资源:更新]\n/system/resource/*list = myauthc, permsOr[资源:查看]\n\n/system/user/*add = myauthc, permsOr[用户:添加]\n/system/user/*delete = myauthc, permsOr[用户:删除]\n/system/user/*update = myauthc, permsOr[用户:更新]\n/system/user/*list = myauthc, permsOr[用户:查看]\n\n/system/role/*add = myauthc, permsOr[角色:添加]\n/system/role/*delete = myauthc, permsOr[角色:删除]\n/system/role/*update = myauthc, permsOr[角色:更新]\n/system/role/*list = myauthc, permsOr[角色:查看]\n\n/system/permission/*add = myauthc, permsOr[权限:添加]\n/system/permission/*delete = myauthc, permsOr[权限:删除]\n/system/permission/*update = myauthc, permsOr[权限:更新]\n/system/permission/*list = myauthc, permsOr[权限:查看]\n\n/system/filterDefinition/toUpdate = myauthc, permsOr[安全规则:更新]\n\n#all other resource need logied permission\n/** = myauthc');
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
INSERT INTO `admin_menu` VALUES ('1', '-1', '菜单根目录', '0', '--', '0', null, null, '2012-12-15 18:08:07'), ('2', '1', '系统管理', '0', '1#2', '1', null, null, '2012-12-13 13:46:00'), ('3', '1', '业务管理', '0', '1#3', '1', null, null, '2012-12-13 13:53:10'), ('4', '2', '菜单管理', '0', '1#2#4', '2', null, null, '2012-12-13 16:00:02'), ('5', '2', '用户管理', '0', '1#2#5', '2', null, null, '2012-12-13 16:00:02'), ('6', '2', '角色管理', '0', '1#2#6', '2', null, null, '2012-12-13 16:00:02'), ('7', '4', '菜单添加', '1', '1#2#4#7', '3', '/system/menu/toAdd', 'centerFrame', '2012-12-13 16:00:02'), ('8', '2', '权限管理', '0', '1#2#8', '2', null, null, '2012-12-16 13:59:33'), ('9', '4', '菜单查询', '1', '1#2#4#9', '3', '/system/menu/toList', 'centerFrame', '2012-12-16 14:15:20'), ('10', '8', '权限添加', '1', '1#2#8#10', '3', '/system/permission/toAdd', 'centerFrame', '2012-12-20 14:58:29'), ('13', '8', '权限查询', '1', '1#2#8#13', '3', '/system/permission/toList', 'centerFrame', '2012-12-20 16:28:03'), ('14', '6', '角色添加', '1', '1#2#6#14', '3', '/system/role/toAdd', 'centerFrame', '2012-12-22 12:36:14'), ('15', '6', '角色查询', '1', '1#2#6#15', '3', '/system/role/toList', 'centerFrame', '2012-12-22 14:29:28'), ('16', '5', '用户添加', '1', '1#2#5#16', '3', '/system/user/toAdd', 'centerFrame', '2012-12-22 18:34:26'), ('17', '5', '用户查询', '1', '1#2#5#17', '3', '/system/user/toList', 'centerFrame', '2012-12-22 19:56:13'), ('18', '2', '资源管理', '0', '1#2#18', '2', null, null, '2016-10-21 11:43:59'), ('19', '18', '资源添加', '1', '1#2#18#19', '3', '/system/resource/toAdd', 'centerFrame', '2016-10-21 11:46:46'), ('20', '18', '资源查询', '1', '1#2#18#20', '3', '/system/resource/toList', 'centerFrame', '2016-10-21 11:47:05'), ('21', '2', '安全规则', '0', '1#2#21', '2', null, null, '2016-10-25 12:27:00'), ('22', '21', '规则更新', '1', '1#2#21#22', '3', '/system/filterDefinition/toUpdate', 'centerFrame', '2016-10-25 12:28:26'), ('23', '18', 't1231', '0', '1#2#18#23', '3', null, null, '2019-01-12 20:30:39'), ('24', '5', '修改密码', '1', '1#2#5#24', '3', '/system/user/toPasswordUpdate', 'centerFrame', '2019-01-23 19:59:19'), ('25', '3', '权限测试', '0', '1#3#25', '2', null, null, '2019-03-23 11:49:56'), ('26', '25', 'thymeleaf', '1', '1#3#25#26', '3', '/thymeleaf', 'centerFrame', '2019-03-23 11:50:37');
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
INSERT INTO `admin_permission` VALUES ('0', '*'), ('1', '菜单:添加'), ('2', '菜单:删除'), ('3', '菜单:更新'), ('4', '菜单:查看'), ('5', '菜单:*'), ('6', '用户:添加'), ('7', '用户:删除'), ('8', '用户:更新'), ('9', '用户:查看'), ('10', '用户:*'), ('11', '角色:添加'), ('12', '角色:删除'), ('13', '角色:更新'), ('14', '角色:查看'), ('15', '角色:*'), ('16', '权限:添加'), ('17', '权限:删除'), ('18', '权限:更新'), ('19', '权限:查看'), ('20', '权限:*'), ('21', '资源:添加'), ('22', '资源:删除'), ('23', '资源:更新'), ('24', '资源:查看'), ('25', '资源:*'), ('26', '安全规则:更新'), ('27', '安全规则:*'), ('28', '密码:更新');
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
INSERT INTO `admin_role` VALUES ('1', '系统管理员', '2012-12-22 16:27:04'), ('2', '系统查看员', '2019-01-06 11:23:39');
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
INSERT INTO `admin_role_permission` VALUES ('19', '2'), ('9', '2'), ('4', '2'), ('14', '2'), ('24', '2'), ('0', '1');
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
INSERT INTO `admin_user` VALUES ('1', 'admin', 'ec805009268b8b0a484f7fd1bef341a2', '7TVhgiLANVOtAxMAetmyAaMHZBBPVW8xLe7cmvQ6Dhk=', '0', '0', '2012-12-22 19:25:48'), ('2', 'adminList', '24297ba9da00bcb2327a5cb16bd775b4', '1553569248598', '0', '0', '2019-01-06 11:25:57');
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
INSERT INTO `admin_user_menu` VALUES ('7', '1'), ('9', '1'), ('16', '1'), ('17', '1'), ('24', '1'), ('14', '1'), ('15', '1'), ('10', '1'), ('13', '1'), ('19', '1'), ('20', '1'), ('22', '1'), ('7', '2'), ('9', '2'), ('16', '2'), ('17', '2'), ('24', '2'), ('14', '2'), ('15', '2'), ('10', '2'), ('13', '2'), ('19', '2'), ('20', '2'), ('22', '2'), ('26', '2');
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
INSERT INTO `admin_user_role` VALUES ('0', '0'), ('1', '1'), ('2', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
