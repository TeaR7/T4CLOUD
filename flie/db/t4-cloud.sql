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
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `log_type` tinyint(2) DEFAULT NULL COMMENT '日志类型（1-管理员操作，2-登录日志，3-用户操作，4-定时任务，5-其他日志）',
  `log_content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '日志内容',
  `operate_type` tinyint(2) DEFAULT NULL COMMENT '操作类型(1-增，2-删，3-改，4-查)',
  `result` text CHARACTER SET utf8 COMMENT '操作结果记录',
  `result_type` tinyint(2) DEFAULT NULL COMMENT '是否异常（0-异常，1-正常）',
  `user_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作用户账号',
  `username` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作用户名称',
  `ip` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'IP',
  `method` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求java方法',
  `request_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求路径',
  `request_param` longtext CHARACTER SET utf8 COMMENT '请求参数',
  `request_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求类型',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '耗时',
  `create_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_table_user_id` (`user_id`) USING BTREE,
  KEY `index_logt_ype` (`log_type`) USING BTREE,
  KEY `index_operate_type` (`operate_type`) USING BTREE,
  KEY `index_log_type` (`log_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------


-- ----------------------------
-- Table structure for sys_mock
-- ----------------------------
DROP TABLE IF EXISTS `sys_mock`;
CREATE TABLE `sys_mock` (
  `id` varchar(32) NOT NULL,
  `url` varchar(64) NOT NULL,
  `return` varchar(512) NOT NULL,
  `method` varchar(255) NOT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_mock
-- ----------------------------


-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父id',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单标题',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `open_type` tinyint(2) DEFAULT NULL COMMENT '打开方式（0-框架内打开，1-新的页面打开）',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_type` int(11) DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
  `perms` varchar(255) DEFAULT NULL COMMENT '权限编码',
  `sort_no` double(8,2) DEFAULT NULL COMMENT '菜单排序',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `hidden` tinyint(2) DEFAULT '1' COMMENT '隐藏路由: 0-隐藏,1-展示',
  `status` tinyint(2) DEFAULT NULL COMMENT '按钮权限状态(0无效1有效)',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_prem_pid` (`parent_id`) USING BTREE,
  KEY `index_prem_sort_no` (`sort_no`) USING BTREE,
  KEY `index_menu_type` (`menu_type`) USING BTREE,
  KEY `index_menu_hidden` (`hidden`) USING BTREE,
  KEY `index_menu_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1227815647354331142', '', '菜单权限表', '/system/SysPermission', '0', 'system/SysPermission', '0', 'system:SysPermission', '10.00', null, '菜单权限表-菜单', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331143', '1227815647354331142', '菜单权限表_新增', null, null, null, '0', 'system:SysPermission:ADD', '11.00', 'plus', '菜单权限表-新增按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331144', '1227815647354331142', '菜单权限表_修改', null, null, null, '0', 'system:SysPermission:EDIT', '11.00', 'form', '菜单权限表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331145', '1227815647354331142', '菜单权限表_删除', null, null, null, '0', 'system:SysPermission:DELETE', '11.00', 'delete', '菜单权限表-删除按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331146', '1227815647354331142', '菜单权限表_查看', null, null, null, '0', 'system:SysPermission:VIEW', '11.00', 'file', '菜单权限表-详情按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `role_name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(100) NOT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_sys_role_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'SUPER-ADMIN', null, 'TeaR', '2020-02-12 13:56:36', null, null);
INSERT INTO `sys_role` VALUES ('2', '管理员', 'ADMIN', null, 'TeaR', '2020-02-12 23:40:48', null, null);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(32) DEFAULT NULL COMMENT '权限id',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_group_role_per_id` (`role_id`,`permission_id`) USING BTREE,
  KEY `index_group_role_id` (`role_id`) USING BTREE,
  KEY `index_group_per_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1227815647354331142', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('15', '2', '1227815647354331142', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('16', '2', '1227815647354331143', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('17', '2', '1227815647354331144', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('18', '2', '1227815647354331145', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('19', '2', '1227815647354331146', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('2', '1', '1227815647354331143', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('3', '1', '1227815647354331144', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('4', '1', '1227815647354331145', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('5', '1', '1227815647354331146', 'TeaR', '2020-02-13 12:45:27', null, null);

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



-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index2_groupuu_user_id` (`user_id`) USING BTREE,
  KEY `index2_groupuu_ole_id` (`role_id`) USING BTREE,
  KEY `index2_groupuu_useridandroleid` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', 'TeaR', '2020-01-12 13:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('2', '1', '2', 'TeaR', '2020-01-12 13:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('3', '14f10842654c1e343ec2447462fc3a8d', '1', 'TeaR', '2020-01-12 13:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('4', '14f10842654c1e343ec2447462fc3a8d', '2', 'TeaR', '2020-01-12 13:54:06', null, null);
