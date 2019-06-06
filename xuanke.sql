/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : xuanke

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-04 23:46:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', 'admin', '黄林杰');

-- ----------------------------
-- Table structure for al
-- ----------------------------
DROP TABLE IF EXISTS `al`;
CREATE TABLE `al` (
  `courseid` varchar(20) DEFAULT NULL COMMENT '课程编号',
  `academy` varchar(20) DEFAULT NULL COMMENT '学院'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学院限制';

-- ----------------------------
-- Records of al
-- ----------------------------
INSERT INTO `al` VALUES ('1001', '信息学院');
INSERT INTO `al` VALUES ('1002', '管理学院');
INSERT INTO `al` VALUES ('1002', '经济学院');
INSERT INTO `al` VALUES ('1003', '信息学院');
INSERT INTO `al` VALUES ('1004', '经济学院');
INSERT INTO `al` VALUES ('1005', '信息学院');
INSERT INTO `al` VALUES ('1005', '医学院');
INSERT INTO `al` VALUES ('1006', '管理学院');
INSERT INTO `al` VALUES ('1006', '经济学院');
INSERT INTO `al` VALUES ('1007', '信息学院');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(20) NOT NULL COMMENT '课程编号',
  `name` varchar(20) DEFAULT NULL COMMENT '课程名称',
  `teacher` varchar(20) DEFAULT NULL COMMENT '老师名称',
  `number` int(11) DEFAULT NULL COMMENT '人数限制',
  `selected` int(11) DEFAULT NULL COMMENT '已选人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1001', 'c语言程序设计', '张老师', '5', '0');
INSERT INTO `course` VALUES ('1002', '宏观经济学', '李老师', '3', '0');
INSERT INTO `course` VALUES ('1003', 'java程序设计', '张老师', '4', '0');
INSERT INTO `course` VALUES ('1004', '市场营销学', '李老师', '6', '0');
INSERT INTO `course` VALUES ('1005', '大学英语', '王老师', '3', '0');
INSERT INTO `course` VALUES ('1006', '大学语文', '洪老师', '5', '0');
INSERT INTO `course` VALUES ('1007', '编译原理', '程老师', '5', '0');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(20) NOT NULL COMMENT '菜单id',
  `operation` varchar(20) DEFAULT NULL COMMENT '操作',
  `url` varchar(40) DEFAULT NULL COMMENT '请求url',
  `pid` varchar(20) DEFAULT NULL COMMENT '上级菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0', '权限菜单', null, '-1');
INSERT INTO `menu` VALUES ('100', '管理员菜单', null, '0');
INSERT INTO `menu` VALUES ('101', '添加课程', null, '100');
INSERT INTO `menu` VALUES ('102', '修改课程', null, '100');
INSERT INTO `menu` VALUES ('103', '删除课程', null, '100');
INSERT INTO `menu` VALUES ('104', '课程选课情况', null, '100');
INSERT INTO `menu` VALUES ('200', '学生菜单', null, '0');
INSERT INTO `menu` VALUES ('201', '排课情况', null, '200');
INSERT INTO `menu` VALUES ('202', '选课', null, '200');
INSERT INTO `menu` VALUES ('203', '退课', null, '200');
INSERT INTO `menu` VALUES ('204', '个人选课情况', null, '200');

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc` (
  `studentid` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `courseid` varchar(20) DEFAULT NULL COMMENT '课程编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES ('16251102276', '1001');
INSERT INTO `sc` VALUES ('16251102276', '1005');
INSERT INTO `sc` VALUES ('16251102276', '1007');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(20) NOT NULL COMMENT '学号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `major` varchar(20) DEFAULT NULL COMMENT '专业',
  `academy` varchar(20) DEFAULT NULL COMMENT '学院',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('16251102276', '123', '黄林杰', '计算机科学与技术', '信息学院');
