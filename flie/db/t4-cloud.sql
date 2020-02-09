/*
Navicat MySQL Data Transfer

Source Server         : T
Source Server Version : 50628
Source Host           : 127.0.0.1:3306
Source Database       : t4-cloud

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2020-02-09 22:02:24
*/

-- 创建mysql库
create database `t4-cloud` default character set utf8mb4 collate utf8mb4_general_ci;

use `t4-cloud`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `code` varchar(100) DEFAULT NULL COMMENT '字典编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `type` tinyint(2) unsigned zerofill DEFAULT '00' COMMENT '字典类型0为string,1为number',
  `status` tinyint(2) DEFAULT NULL COMMENT '删除状态',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `indextable_dict_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '通用状态', 'common_status', '描述数据状态（0：失效，1：生效）', '01', '1', 'TeaR', '2019-02-09 13:17:15', null, null);

-- ----------------------------
-- Table structure for sys_dict_value
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_value`;
CREATE TABLE `sys_dict_value` (
  `id` varchar(32) NOT NULL,
  `dict_id` varchar(32) DEFAULT NULL COMMENT '字典id',
  `text` varchar(100) DEFAULT NULL COMMENT '字典项文本',
  `value` varchar(100) DEFAULT NULL COMMENT '字典项值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `position` int(10) DEFAULT NULL COMMENT '排序',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态（1启用 0不启用）',
  `create_by` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_table_dict_id` (`dict_id`) USING BTREE,
  KEY `index_table_sort_order` (`position`) USING BTREE,
  KEY `index_table_dict_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC  COMMENT='字典详细值';

-- ----------------------------
-- Records of sys_dict_value
-- ----------------------------
INSERT INTO `sys_dict_value` VALUES ('1', '1', '正常', '1', null, '1', '1', 'TeaR', '2019-02-09 13:18:47', null, null);
INSERT INTO `sys_dict_value` VALUES ('2', '1', '失效', '0', '', '2', '1', 'TeaR', '2019-02-09 13:18:47', '', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `username` varchar(100) NOT NULL COMMENT '登录账号',
  `realname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `work_no` varchar(100) NOT NULL COMMENT '工号，唯一键',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `gender` tinyint(2) DEFAULT NULL COMMENT '性别(0-默认未知,1-男,2-女)',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话',
  `post` varchar(100) DEFAULT NULL COMMENT '职务，关联职务表',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `address` varchar(500) DEFAULT NULL COMMENT '住址',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '性别(1-正常,2-冻结)',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_user_name` (`username`) USING BTREE,
  UNIQUE KEY `uniq_sys_user_work_no` (`work_no`) USING BTREE,
  KEY `index_user_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'TeaR', 'TeaR', '700961d246e6ddf1', 'SDFyT0h3', '001', null, '2018-12-05 00:00:00', '1', 'zqr.it@t4cloud.com', '17800001111', null, null, '上海市浦东新区陆家嘴', '1', 'TeaR', '2017-05-02 21:26:48', null, null);
INSERT INTO `sys_user` VALUES ('14f10842654c1e343ec2447462fc3a8d', 'TR', '', '', '', '002', '', null, '1', '', '', '', '', '', '1', '', null, '', null);
