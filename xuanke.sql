/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : xuanke

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-06-27 14:32:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '$2a$10$T0fMzXn4sMXl8iVLMvPiDO8SyYwhwYePAQnT2BNQw/a9Bu03ubc96', '黄林杰');

-- ----------------------------
-- Table structure for al
-- ----------------------------
DROP TABLE IF EXISTS `al`;
CREATE TABLE `al` (
  `courseid` varchar(20) DEFAULT NULL COMMENT '课程编号',
  `academy` varchar(20) DEFAULT NULL COMMENT '学院',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `FK_ID` (`courseid`),
  CONSTRAINT `FK_ID` FOREIGN KEY (`courseid`) REFERENCES `course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='学院限制';

-- ----------------------------
-- Records of al
-- ----------------------------
INSERT INTO `al` VALUES ('1001', '信息学院', '1');
INSERT INTO `al` VALUES ('1003', '信息学院', '4');
INSERT INTO `al` VALUES ('1004', '经济学院', '5');
INSERT INTO `al` VALUES ('1005', '信息学院', '6');
INSERT INTO `al` VALUES ('1005', '医学院', '7');
INSERT INTO `al` VALUES ('1007', '信息学院', '10');
INSERT INTO `al` VALUES ('1008', '信息学院', '11');
INSERT INTO `al` VALUES ('1002', '管理学院', '25');
INSERT INTO `al` VALUES ('1002', '信息学院', '26');
INSERT INTO `al` VALUES ('1006', '管理学院', '33');
INSERT INTO `al` VALUES ('1006', '金融学院', '34');
INSERT INTO `al` VALUES ('1011', '数统学院', '38');
INSERT INTO `al` VALUES ('1012', '数统学院', '41');

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
  `grade` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1001', 'c语言程序设计', '张老师', '5', '1', '大三');
INSERT INTO `course` VALUES ('1002', '宏观经济学', '李老师', '3', '0', '大一');
INSERT INTO `course` VALUES ('1003', 'java程序设计', '张老师', '4', '0', '大三');
INSERT INTO `course` VALUES ('1004', '市场营销学', '李老师', '6', '0', '大二');
INSERT INTO `course` VALUES ('1005', '大学英语', '王老师', '3', '2', '大三');
INSERT INTO `course` VALUES ('1006', '大学语文', '洪老师', '5', '0', '大二');
INSERT INTO `course` VALUES ('1007', '编译原理', '程老师', '5', '1', '大三');
INSERT INTO `course` VALUES ('1008', '人工智能', '陈老师', '1', '1', '大三');
INSERT INTO `course` VALUES ('1011', '算法', '张老师', '6', '0', '大三');
INSERT INTO `course` VALUES ('1012', '法', '张老师', '6', '1', '大四');

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc` (
  `studentid` varchar(20) DEFAULT NULL COMMENT '学生学号',
  `courseid` varchar(20) DEFAULT NULL COMMENT '课程编号',
  KEY `studentid` (`studentid`),
  KEY `courseid` (`courseid`),
  CONSTRAINT `courseid` FOREIGN KEY (`courseid`) REFERENCES `course` (`id`),
  CONSTRAINT `studentid` FOREIGN KEY (`studentid`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sc
-- ----------------------------
INSERT INTO `sc` VALUES ('16251102276', '1001');
INSERT INTO `sc` VALUES ('16251102276', '1005');
INSERT INTO `sc` VALUES ('16251102276', '1007');
INSERT INTO `sc` VALUES ('16251102277', '1005');
INSERT INTO `sc` VALUES ('1', '1012');
INSERT INTO `sc` VALUES ('16251102276', '1008');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(20) NOT NULL COMMENT '学号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `major` varchar(20) DEFAULT NULL COMMENT '专业',
  `academy` varchar(20) DEFAULT NULL COMMENT '学院',
  `grade` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '$2a$10$am3mFuY8MQa29.hLY7wDP.NEPlpp7OluLAC7waD4cwMHpmbG14C7i', '测试', '2', '数统学院', '大四');
INSERT INTO `student` VALUES ('123', '$2a$10$am3mFuY8MQa29.hLY7wDP.NEPlpp7OluLAC7waD4cwMHpmbG14C7i', 'a', '2', '1', '大三');
INSERT INTO `student` VALUES ('16251102276', '$2a$10$01sugpjhc714fwy3zqQ/OuuwIgarq4kChDHjTViLTwPqHkdXpYsde', '黄林杰', '计算机科学与技术', '信息学院', '大三');
INSERT INTO `student` VALUES ('16251102277', '$2a$10$JsPFAdqulQmZYZ/Wt0HBP.y5NBkhhxWJ0Q5vNZKhSLsdCJydXYdgG', 'ddas', '护理', '医学院', '大三');
