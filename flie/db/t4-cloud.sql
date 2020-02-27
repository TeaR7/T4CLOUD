/*
Navicat MySQL Data Transfer

Source Server         : T
Source Server Version : 50628
Source Host           : 127.0.0.1:3306
Source Database       : t4-cloud

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2020-02-14 14:03:58
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
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典名称',
  `code` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典编码',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  `type` tinyint(2) unsigned zerofill DEFAULT '00' COMMENT '字典类型0为string,1为number',
  `status` tinyint(2) DEFAULT NULL COMMENT '删除状态',
  `create_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `indextable_dict_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '通用状态', 'common_status', '描述数据状态（0：失效，1：生效）', '01', '1', 'TeaR', '2019-02-09 13:17:15', null, null);

-- ----------------------------
-- Table structure for sys_dict_value
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_value`;
CREATE TABLE `sys_dict_value` (
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `dict_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典id',
  `text` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典项文本',
  `value` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典项值',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  `position` int(10) DEFAULT NULL COMMENT '排序',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态（1启用 0不启用）',
  `create_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_table_dict_id` (`dict_id`) USING BTREE,
  KEY `index_table_sort_order` (`position`) USING BTREE,
  KEY `index_table_dict_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典详细值';

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
  `url` varchar(150) NOT NULL COMMENT 'MOCK对应的URL',
  `data` text NOT NULL COMMENT 'MOCK对应的DATA数据',
  `method` varchar(10) NOT NULL COMMENT 'MOCK对应的URL的请求方法',
  `create_by` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_url_m` (`url`,`method`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Mock数据';

-- ----------------------------
-- Records of sys_mock
-- ----------------------------
INSERT INTO `sys_mock` VALUES ('1', 'hello', '{\"123\":\"测试\"}', 'GET', 'wpl', '2020-02-14 14:03:43', null, '2020-02-14 14:03:43');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父id',
  `name` varchar(100) NOT NULL COMMENT '菜单标题',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `open_type` tinyint(2) DEFAULT NULL COMMENT '打开方式（0-框架内打开，1-新的页面打开）',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_type` int(11) NOT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
  `perms` varchar(255) DEFAULT NULL COMMENT '权限编码',
  `sort_no` double(8,2) NOT NULL DEFAULT '10.00' COMMENT '菜单排序',
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
INSERT INTO `sys_permission` VALUES ('1227815647354331142', '', '菜单权限', '/system/SysPermissionList', '0', 'system/SysPermissionList', '0', 'system:SysPermission:BASE', '11.00', 'el-icon-setting', '菜单权限表-菜单', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', 'admin', '2020-02-26 17:40:03');
INSERT INTO `sys_permission` VALUES ('1227815647354331143', '1227815647354331142', '菜单权限表_新增', null, null, null, '2', 'system:SysPermission:ADD', '11.00', 'plus', '菜单权限表-新增按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331144', '1227815647354331142', '菜单权限表_修改', null, null, null, '2', 'system:SysPermission:EDIT', '11.00', 'form', '菜单权限表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331145', '1227815647354331142', '菜单权限表_删除', null, null, null, '2', 'system:SysPermission:DELETE', '11.00', 'delete', '菜单权限表-删除按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331146', '1227815647354331142', '菜单权限表_查看', null, null, null, '2', 'system:SysPermission:VIEW', '11.00', 'file', '菜单权限表-详情按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715143', null, 'Mock数据', '/mock/SysMockList', '0', 'mock/SysMockList', '0', 'mock:SysMock:BASE', '10.00', null, 'Mock数据-菜单', '1', '1', 'T4Cloud', '2020-02-14 13:20:09', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715144', '1228186789386715143', 'Mock数据_新增', null, null, null, '2', 'mock:SysMock:ADD', '11.00', 'plus', 'Mock数据-新增按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:09', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715145', '1228186789386715143', 'Mock数据_修改', null, null, null, '2', 'mock:SysMock:EDIT', '12.00', 'form', 'Mock数据-编辑按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:09', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715146', '1228186789386715143', 'Mock数据_删除', null, null, null, '2', 'mock:SysMock:DELETE', '13.00', 'delete', 'Mock数据-删除按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:10', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715147', '1228186789386715143', 'Mock数据_查看', null, null, null, '2', 'mock:SysMock:VIEW', '14.00', 'file', 'Mock数据-详情按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:10', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052039', null, '用户管理', '/user/SysUserList', '0', 'user/SysUserList', '0', 'user:SysUser:BASE', '10.00', null, '用户表-菜单', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052040', '1230085853179052039', '用户表_新增', null, null, null, '2', 'user:SysUser:ADD', '11.00', 'plus', '用户表-新增按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052041', '1230085853179052039', '用户表_修改', null, null, null, '2', 'user:SysUser:EDIT', '12.00', 'form', '用户表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052042', '1230085853179052039', '用户表_删除', null, null, null, '2', 'user:SysUser:DELETE', '13.00', 'delete', '用户表-删除按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052043', '1230085853179052039', '用户表_查看', null, null, null, '2', 'user:SysUser:VIEW', '14.00', 'file', '用户表-详情按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712972', null, '角色管理', '/system/SysRole', '0', 'system/SysRoleList', '0', 'system:SysRole:BASE', '10.00', null, '角色表-菜单', '1', '1', 'T4Cloud', '2020-02-21 16:19:29', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712973', '1230768750940712972', '角色表_新增', null, null, null, '2', 'system:SysRole:ADD', '11.00', 'plus', '角色表-新增按钮', '1', '1', 'T4Cloud', '2020-02-21 16:19:29', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712974', '1230768750940712972', '角色表_修改', null, null, null, '2', 'system:SysRole:EDIT', '12.00', 'form', '角色表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712975', '1230768750940712972', '角色表_删除', null, null, null, '2', 'system:SysRole:DELETE', '13.00', 'delete', '角色表-删除按钮', '1', '1', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712976', '1230768750940712972', '角色表_查看', null, null, null, '2', 'system:SysRole:VIEW', '14.00', 'file', '角色表-详情按钮', '1', '1', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_permission` VALUES ('1230769977841688588', null, '角色权限表', '/system/SysRolePermission', '0', 'system/SysRolePermissionList', '0', 'system:SysRolePermission:BASE', '10.00', null, '角色权限表-菜单', '0', '1', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230769977841688589', '1230769977841688588', '角色权限表_新增', null, null, null, '2', 'system:SysRolePermission:ADD', '11.00', 'plus', '角色权限表-新增按钮', '1', '1', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230769977841688590', '1230769977841688588', '角色权限表_修改', null, null, null, '2', 'system:SysRolePermission:EDIT', '12.00', 'form', '角色权限表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230769977841688591', '1230769977841688588', '角色权限表_删除', null, null, null, '2', 'system:SysRolePermission:DELETE', '13.00', 'delete', '角色权限表-删除按钮', '1', '1', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230769977841688592', '1230769977841688588', '角色权限表_查看', null, null, null, '2', 'system:SysRolePermission:VIEW', '14.00', 'file', '角色权限表-详情按钮', '1', '1', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_permission` VALUES ('1231177799351967764', '1230085853179052039', '用户表_导出', null, null, null, '2', 'user:SysUser:EXPORT', '15.00', 'export', '用户表-导出按钮', '1', '1', 'T4Cloud', '2020-02-22 19:24:15', null, null);
INSERT INTO `sys_permission` VALUES ('1231177799351967765', '1230085853179052039', '用户表_导入', null, null, null, '2', 'user:SysUser:IMPORT', '16.00', 'import', '用户表-导入按钮', '1', '1', 'T4Cloud', '2020-02-22 19:24:15', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441743', null, '资源管理表', '/support/SysResource', '0', 'support/SysResourceList', '0', 'support:SysResource:BASE', '10.00', null, '资源管理表-菜单', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441744', '1231551481274441743', '资源管理表_新增', null, null, null, '2', 'support:SysResource:ADD', '11.00', 'plus', '资源管理表-新增按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441745', '1231551481274441743', '资源管理表_修改', null, null, null, '2', 'support:SysResource:EDIT', '12.00', 'form', '资源管理表-编辑按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441746', '1231551481274441743', '资源管理表_删除', null, null, null, '2', 'support:SysResource:DELETE', '13.00', 'delete', '资源管理表-删除按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441747', '1231551481274441743', '资源管理表_查看', null, null, null, '2', 'support:SysResource:VIEW', '14.00', 'file', '资源管理表-详情按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441748', '1231551481274441743', '资源管理表_导出', null, null, null, '2', 'support:SysResource:EXPORT', '15.00', 'export', '资源管理表-导出按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441749', '1231551481274441743', '资源管理表_导入', null, null, null, '2', 'support:SysResource:IMPORT', '16.00', 'import', '资源管理表-导入按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('26d525cc2f53f2a82d5aa98936d40381', '', '测试', '/test/test', '0', '/test/test', '0', null, '13.00', 'el-icon-user-solid', null, '0', '1', 'admin', '2020-02-25 14:33:52', 'admin', '2020-02-25 21:04:06');
INSERT INTO `sys_permission` VALUES ('e1bbd3d6df87333f0b46b880e81b4e0c', '26d525cc2f53f2a82d5aa98936d40381', '子菜单', 'test/test/zi', '0', 'test/test/zi', '1', null, '0.00', 'el-icon-eleme', null, '0', '0', 'admin', '2020-02-26 16:03:15', null, null);

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '资源名称',
  `path` varchar(500) NOT NULL COMMENT '相对路径',
  `url` varchar(1000) NOT NULL COMMENT '完整资源路径',
  `bucket` varchar(50) DEFAULT NULL COMMENT '桶名',
  `mime_type` varchar(50) DEFAULT NULL COMMENT '资源类型',
  `policy` tinyint(2) NOT NULL DEFAULT '1' COMMENT '权限策略（1-公开，2-私有）',
  `expire_time` datetime DEFAULT NULL COMMENT '失效时间',
  `count` int(8) NOT NULL DEFAULT '0' COMMENT '总访问次数',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '最后访问人',
  `update_time` datetime DEFAULT NULL COMMENT '最后访问时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `path` (`path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源管理表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------

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
INSERT INTO `sys_role_permission` VALUES ('001', '1', '1227815647354331142', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('002', '1', '1227815647354331143', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('003', '1', '1227815647354331144', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('004', '1', '1227815647354331145', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('005', '1', '1227815647354331146', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('015', '2', '1227815647354331142', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('016', '2', '1227815647354331143', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('017', '2', '1227815647354331144', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('018', '2', '1227815647354331145', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('019', '2', '1227815647354331146', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('020', '2', '1230085853179052039', 'TeaR', '2020-02-19 19:12:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('021', '2', '1230085853179052040', 'TeaR', '2020-02-19 19:12:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('023', '2', '1230085853179052042', 'TeaR', '2020-02-19 19:12:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('024', '2', '1230085853179052043', 'TeaR', '2020-02-19 19:12:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712977', '2', '1230768750940712972', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712978', '2', '1230768750940712973', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712979', '2', '1230768750940712974', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712980', '2', '1230768750940712975', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712981', '2', '1230768750940712976', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230769977841688593', '2', '1230769977841688588', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230769977841688594', '2', '1230769977841688589', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230769977841688595', '2', '1230769977841688590', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230769977841688596', '2', '1230769977841688591', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230769977841688597', '2', '1230769977841688592', 'T4Cloud', '2020-02-21 16:23:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231177799351967771', '2', '1231177799351967764', 'T4Cloud', '2020-02-22 19:24:21', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231177799351967772', '2', '1231177799351967765', 'T4Cloud', '2020-02-22 19:24:21', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441750', '2', '1231551481274441743', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441751', '2', '1231551481274441744', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441752', '2', '1231551481274441745', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441753', '2', '1231551481274441746', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441754', '2', '1231551481274441747', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441755', '2', '1231551481274441748', 'TeaR', '2020-02-23 20:09:20', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441756', '2', '1231551481274441749', 'TeaR', '2020-02-23 20:09:20', null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '主键id',
  `username` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '登录账号',
  `realname` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT 'md5密码盐',
  `work_no` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '工号，唯一键',
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `gender` tinyint(2) DEFAULT NULL COMMENT '性别(0-默认未知,1-男,2-女)',
  `email` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(11) CHARACTER SET utf8 DEFAULT NULL COMMENT '电话',
  `post` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '职务，关联职务表',
  `id_card` varchar(18) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号',
  `address` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '住址',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '性别(1-正常,2-冻结)',
  `create_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_user_name` (`username`) USING BTREE,
  UNIQUE KEY `uniq_sys_user_work_no` (`work_no`) USING BTREE,
  KEY `index_user_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'TeaR', 'TeaR', '700961d246e6ddf1', 'SDFyT0h3', '001', 'https://git.t4cloud.com/img/favicon.png', '2018-12-05 00:00:00', '1', 'zqr.it@t4cloud.com', '17800001111', null, null, '上海市浦东新区陆家嘴', '1', 'TeaR', '2017-05-02 21:26:48', null, null);
INSERT INTO `sys_user` VALUES ('14f10842654c1e343ec2447462fc3a8d', 'admin', '管理员', 'cb362cfeefbf3d8d', 'RCGTeGiH', '002', 'https://git.t4cloud.com/img/favicon.png', null, '1', 'TEAR1', '', '', '', '', '1', 'TeaR', '2017-05-02 21:26:48', '', null);
INSERT INTO `sys_user` VALUES ('a72406a4656aa48db91ca3bf07d5bd43', 'TESTQ', 'TESTQ', 'TESTQ', null, 'TESTQ', null, '2020-02-06 00:00:00', '2', 'TESTQ', 'TESTQ', null, null, 'TESTQ', '1', 'admin', '2020-02-24 23:52:38', null, null);

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
