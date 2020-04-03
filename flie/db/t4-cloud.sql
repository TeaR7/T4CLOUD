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
-- Table structure for example_common
-- ----------------------------
DROP TABLE IF EXISTS `example_common`;
CREATE TABLE `example_common` (
  `id` varchar(32) NOT NULL,
  `str` varchar(150) NOT NULL COMMENT '字符串查询',
  `txt` text NOT NULL COMMENT '长文本查询',
  `num` int(4) NOT NULL COMMENT '数字类型查询',
  `query_date` date DEFAULT NULL COMMENT '日期查询',
  `query_date_time` datetime DEFAULT NULL COMMENT '日期时间查询',
  `query_time` time DEFAULT NULL COMMENT '时间查询',
  `create_by` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通用示例';

-- ----------------------------
-- Records of example_common
-- ----------------------------
INSERT INTO `example_common` VALUES ('efd3ecaedc1ce8dccb374c10723b2ce0', '测试字符串', '测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串', '707', '2020-03-12', '2020-03-31 22:25:51', '22:19:37', 'admin', '2020-03-31 22:23:19', 'admin', '2020-03-31 22:23:20');

-- ----------------------------
-- Table structure for sup_message
-- ----------------------------
DROP TABLE IF EXISTS `sup_message`;
CREATE TABLE `sup_message` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `title` varchar(100) DEFAULT NULL COMMENT '消息标题',
  `message_type` tinyint(1) NOT NULL COMMENT '发送方式：1短信 2邮件 3微信 4站内信 ',
  `target` varchar(100) NOT NULL COMMENT '接收人',
  `param` varchar(1000) DEFAULT NULL COMMENT '动态参数：Json格式',
  `content` longtext NOT NULL COMMENT '内容',
  `send_time` datetime NOT NULL COMMENT '推送时间',
  `send_num` int(11) NOT NULL DEFAULT '0' COMMENT '发送次数 超过5次不再发送',
  `send_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送',
  `send_result` varchar(255) DEFAULT NULL COMMENT '推送失败原因',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人登录名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人登录名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_type` (`message_type`) USING BTREE,
  KEY `index_target` (`target`) USING BTREE,
  KEY `index_status` (`send_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息列表';

-- ----------------------------
-- Records of sup_message
-- ----------------------------
INSERT INTO `sup_message` VALUES ('bdfaee854ad5346b97cd15e19b927f62', 'Hello,tear!', '2', 'zqr.it@t4cloud.com', null, '<h1>Hello,tear!</h1>', '2020-03-31 22:50:55', '5', '-1', 'Authentication failed; nested exception is javax.mail.AuthenticationFailedException: 535 Error: authentication failed, system busy\n', null, 'admin', '2020-03-31 22:35:53', 'mailSender任务', '2020-04-01 12:11:18');
INSERT INTO `sup_message` VALUES ('c8436ecb16029fc7837fe054f860aac8', '前端批量测试', '2', 'zqr.it@t4cloud.com', null, '前端批量测试', '2020-04-01 14:22:46', '1', '1', null, null, 'admin', '2020-04-01 14:22:46', 'mailSender任务', '2020-04-01 14:22:52');

-- ----------------------------
-- Table structure for sup_message_template
-- ----------------------------
DROP TABLE IF EXISTS `sup_message_template`;
CREATE TABLE `sup_message_template` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `template_code` varchar(32) NOT NULL COMMENT '模板CODE',
  `template_name` varchar(50) DEFAULT NULL COMMENT '模板标题',
  `template_type` tinyint(2) NOT NULL COMMENT '模板类型：1短信 2邮件 3微信 4站内信',
  `template_content` longtext COMMENT '模板内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人登录名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人登录名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_templatecode` (`template_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息模板';

-- ----------------------------
-- Records of sup_message_template
-- ----------------------------
INSERT INTO `sup_message_template` VALUES ('f6f311e79c573b3a8652b386cd9b7390', 'TEST', '测试模板', '2', 'hi，${username}，这是一封测试邮件。', '2020-03-26 17:57:18', 'admin', '2020-03-31 17:46:02', 'admin');

-- ----------------------------
-- Table structure for sup_resource
-- ----------------------------
DROP TABLE IF EXISTS `sup_resource`;
CREATE TABLE `sup_resource` (
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
-- Records of sup_resource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------

DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '字典名称',
  `code` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '字典编码',
  `description` text CHARACTER SET utf8 COMMENT '描述',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '删除状态',
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
INSERT INTO `sys_dict` VALUES ('049d8f8d39bfed55fe8d6ccd317c9e78', '消息类型', 'msg_template_type', null, '1', 'admin', '2020-03-26 17:35:59', 'admin', '2020-03-31 22:06:22');
INSERT INTO `sys_dict` VALUES ('1', '通用状态', 'common_status', '通用状态', '1', 'TeaR', '2019-02-09 13:17:15', 'admin', '2020-03-15 19:55:24');
INSERT INTO `sys_dict` VALUES ('1117af12dec6f9ed3adf81a4b0fc3b3b', '日志类型', 'log_type', null, '1', 'admin', '2020-03-27 14:13:00', null, null);
INSERT INTO `sys_dict` VALUES ('1ba37859b072ead1bc8daa03cad669f3', '资源策略', 'policy_type', null, '1', 'admin', '2020-03-26 11:20:08', null, null);
INSERT INTO `sys_dict` VALUES ('2', '性别', 'gender', '性别', '1', 'TeaR', '2020-03-04 14:13:28', 'admin', '2020-03-15 20:37:41');
INSERT INTO `sys_dict` VALUES ('84290353c8b63fb5f76a949cf976890b', '打开方式', 'open_type', null, '1', 'admin', '2020-03-17 21:19:38', null, null);
INSERT INTO `sys_dict` VALUES ('ad2cce5a0c32d8efbd72b718534bcfb4', '消息发送状态', 'msg_send_status', null, '1', 'admin', '2020-03-31 18:26:33', null, null);
INSERT INTO `sys_dict` VALUES ('c6b035b3df1bd679ebe1f943c68aed78', '操作类型', 'operate_type', null, '1', 'admin', '2020-03-27 14:19:09', null, null);
INSERT INTO `sys_dict` VALUES ('d8259873aece0de66f404a3ef5ff5881', '通用结果', 'common_valid', null, '1', 'admin', '2020-03-27 14:22:11', null, null);
INSERT INTO `sys_dict` VALUES ('ed77389295a5d4c90c9ade1b2671c336', '隐藏', 'hidden', null, '1', 'admin', '2020-03-17 21:15:16', 'admin', '2020-03-17 21:15:29');

-- ----------------------------
-- Table structure for sys_dict_value
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_value`;
CREATE TABLE `sys_dict_value` (
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `dict_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典id',
  `text` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '字典项文本',
  `value` int(10) DEFAULT NULL COMMENT '字典项值',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  `position` int(10) DEFAULT NULL COMMENT '排序',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态（1启用 0不启用）',
  `create_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `key_uni` (`dict_id`,`value`) USING BTREE,
  KEY `index_table_dict_id` (`dict_id`) USING BTREE,
  KEY `index_table_sort_order` (`position`) USING BTREE,
  KEY `index_table_dict_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典详细值';

-- ----------------------------
-- Records of sys_dict_value
-- ----------------------------
INSERT INTO `sys_dict_value` VALUES ('01dff238bc819fa50bc86d8ee56c9b05', 'd8259873aece0de66f404a3ef5ff5881', '异常', '0', null, '0', '1', 'admin', '2020-03-27 14:22:28', null, null);
INSERT INTO `sys_dict_value` VALUES ('05915cf6352322cf6b312422d669556d', '84290353c8b63fb5f76a949cf976890b', '新标签打开', '1', null, '10', '1', 'admin', '2020-03-17 21:20:20', null, null);
INSERT INTO `sys_dict_value` VALUES ('1', '1', '生效', '1', null, '1', '1', 'TeaR', '2019-02-09 13:18:47', 'admin', '2020-03-15 20:37:31');
INSERT INTO `sys_dict_value` VALUES ('1753abc515ca2d18ad4d9e1c29d1d27d', 'ad2cce5a0c32d8efbd72b718534bcfb4', '停止发送', '-1', null, '-1', '1', 'admin', '2020-03-31 18:27:39', null, null);
INSERT INTO `sys_dict_value` VALUES ('197ca6841779dd9b8d028d81e4ab3f5c', 'ed77389295a5d4c90c9ade1b2671c336', '隐藏', '0', null, '10', '1', 'admin', '2020-03-17 21:16:11', 'admin', '2020-03-26 10:24:52');
INSERT INTO `sys_dict_value` VALUES ('2', '1', '失效', '0', '', '2', '1', 'TeaR', '2019-02-09 13:18:47', '', null);
INSERT INTO `sys_dict_value` VALUES ('28fa16682964ccb0e792020d153d3197', '1117af12dec6f9ed3adf81a4b0fc3b3b', '用户操作', '3', null, '3', '1', 'admin', '2020-03-27 14:13:29', null, null);
INSERT INTO `sys_dict_value` VALUES ('4', '2', '男', '1', null, '1', '1', 'TeaR', '2020-03-04 14:14:44', 'admin', '2020-03-26 10:25:23');
INSERT INTO `sys_dict_value` VALUES ('408974ceb7cb2bfc8fd36248c91f5504', 'd8259873aece0de66f404a3ef5ff5881', '正常', '1', null, '1', '1', 'admin', '2020-03-27 14:22:21', null, null);
INSERT INTO `sys_dict_value` VALUES ('4abf5aee23a3d29d1607ed04abe00d6f', 'ad2cce5a0c32d8efbd72b718534bcfb4', '推送成功', '1', null, '1', '1', 'admin', '2020-03-31 18:27:11', null, null);
INSERT INTO `sys_dict_value` VALUES ('4e4d3dcf8e89da8410a1a5e2f7ff1695', 'c6b035b3df1bd679ebe1f943c68aed78', '查', '4', null, '4', '1', 'admin', '2020-03-27 14:21:11', null, null);
INSERT INTO `sys_dict_value` VALUES ('5', '2', '女', '2', null, '2', '1', 'TeaR', '2020-03-04 14:14:44', null, null);
INSERT INTO `sys_dict_value` VALUES ('5ec67b8617b5944efed297be465e810b', '049d8f8d39bfed55fe8d6ccd317c9e78', '邮件', '2', null, '2', '1', 'admin', '2020-03-26 17:36:27', 'admin', '2020-04-01 12:24:24');
INSERT INTO `sys_dict_value` VALUES ('6a9cb93f261616fa58464506ba9d1e0a', 'ad2cce5a0c32d8efbd72b718534bcfb4', '推送失败', '2', null, '2', '1', 'admin', '2020-03-31 18:27:19', null, null);
INSERT INTO `sys_dict_value` VALUES ('74400d676a33ddbd4a2573ae0652f2b6', 'c6b035b3df1bd679ebe1f943c68aed78', '删', '2', null, '2', '0', 'admin', '2020-03-27 14:20:15', 'admin', '2020-03-30 18:31:10');
INSERT INTO `sys_dict_value` VALUES ('745c2eae51ccc41e6cda1be80484515e', '1ba37859b072ead1bc8daa03cad669f3', '公开', '1', null, '1', '1', 'admin', '2020-03-26 11:20:26', null, null);
INSERT INTO `sys_dict_value` VALUES ('77c0b3c95e8ed92fa98883b5ed133d34', '1117af12dec6f9ed3adf81a4b0fc3b3b', '登录日志', '2', null, '2', '1', 'admin', '2020-03-27 14:13:18', null, null);
INSERT INTO `sys_dict_value` VALUES ('7aff90e0935fac2e02b92fa36d371f38', '1117af12dec6f9ed3adf81a4b0fc3b3b', '定时任务', '4', null, '4', '1', 'admin', '2020-03-27 14:13:35', null, null);
INSERT INTO `sys_dict_value` VALUES ('85c9c15c5c4af870efa9965cc87b1f9d', '1117af12dec6f9ed3adf81a4b0fc3b3b', 'MQ日志', '5', null, '5', '1', 'admin', '2020-03-30 14:35:05', null, null);
INSERT INTO `sys_dict_value` VALUES ('86635d15e33c382229ec955d17cab2af', 'ed77389295a5d4c90c9ade1b2671c336', '展示', '1', null, '10', '1', 'admin', '2020-03-17 21:16:20', null, null);
INSERT INTO `sys_dict_value` VALUES ('8e7d06da5f809b8f3f55c5a5ca3493ca', '049d8f8d39bfed55fe8d6ccd317c9e78', '微信', '3', null, '3', '0', 'admin', '2020-03-31 23:22:43', 'admin', '2020-04-01 12:24:27');
INSERT INTO `sys_dict_value` VALUES ('953b4afc3a12a866bed18b8d9cbfa983', 'c6b035b3df1bd679ebe1f943c68aed78', '改', '3', null, '3', '1', 'admin', '2020-03-27 14:20:21', null, null);
INSERT INTO `sys_dict_value` VALUES ('ad673c2106e04f2dfe04e4d2b6e4bc0f', '1117af12dec6f9ed3adf81a4b0fc3b3b', '其他日志', '6', null, '6', '1', 'admin', '2020-03-27 14:13:41', 'admin', '2020-03-30 14:34:55');
INSERT INTO `sys_dict_value` VALUES ('b787884dc3fc099fd7236e9ad3cc60b1', '049d8f8d39bfed55fe8d6ccd317c9e78', '站内信', '4', null, '4', '0', 'admin', '2020-03-31 23:23:07', 'admin', '2020-04-01 12:24:31');
INSERT INTO `sys_dict_value` VALUES ('cec4d7deccec648416d069e9046730ff', '1ba37859b072ead1bc8daa03cad669f3', '私有', '2', null, '2', '1', 'admin', '2020-03-26 11:20:32', null, null);
INSERT INTO `sys_dict_value` VALUES ('d69854f5f233fab4c198e67f97d41d1f', 'ad2cce5a0c32d8efbd72b718534bcfb4', '待推送', '0', null, '0', '1', 'admin', '2020-03-31 18:27:00', 'admin', '2020-03-31 23:21:25');
INSERT INTO `sys_dict_value` VALUES ('d71d1657a2706d5d6b4e4dea573587de', 'c6b035b3df1bd679ebe1f943c68aed78', '增', '1', null, '1', '1', 'admin', '2020-03-27 14:20:09', null, null);
INSERT INTO `sys_dict_value` VALUES ('db3b414925124232e8ec15780a7b9563', '049d8f8d39bfed55fe8d6ccd317c9e78', '短信', '1', null, '1', '0', 'admin', '2020-03-26 17:36:17', 'admin', '2020-04-01 12:24:21');
INSERT INTO `sys_dict_value` VALUES ('dc8067157547cb491115aef50d9be6c0', '84290353c8b63fb5f76a949cf976890b', '页面内打开', '0', null, '10', '1', 'admin', '2020-03-17 21:20:10', null, null);
INSERT INTO `sys_dict_value` VALUES ('e72362771956e9b7fbff56963945c8f5', '1117af12dec6f9ed3adf81a4b0fc3b3b', '管理员操作', '1', null, '1', '1', 'admin', '2020-03-27 14:13:12', null, null);


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
  KEY `index_log_type` (`create_time`) USING BTREE,
  KEY `index_logt_ype_and_operate_type` (`log_type`,`operate_type`) USING BTREE
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
INSERT INTO `sys_permission` VALUES ('1227815647354331142', '154e80d6a31e578d2eaa8c4634b3e8da', '菜单权限', '/system/SysPermissionList', '0', 'system/SysPermissionList', '0', null, '9.00', 'el-icon-help', '菜单权限表-菜单', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', 'admin', '2020-03-25 17:41:35');
INSERT INTO `sys_permission` VALUES ('1227815647354331143', '1227815647354331142', '菜单权限表_新增', null, null, null, '2', 'system:SysPermission:ADD', '11.00', 'el-icon-circle-plus-outline', '菜单权限表-新增按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331144', '1227815647354331142', '菜单权限表_修改', null, null, null, '2', 'system:SysPermission:EDIT', '11.00', 'el-icon-edit', '菜单权限表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331145', '1227815647354331142', '菜单权限表_删除', null, null, null, '2', 'system:SysPermission:DELETE', '11.00', 'el-icon-delete', '菜单权限表-删除按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331146', '1227815647354331142', '菜单权限表_查看', null, null, null, '2', 'system:SysPermission:VIEW', '11.00', 'el-icon-view', '菜单权限表-详情按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331147', '1227815647354331142', '菜单权限表_导入', '', null, '', '2', 'system:SysPermission:IMPORT', '11.00', 'el-icon-upload2', '菜单权限表_导入', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1227815647354331148', '1227815647354331142', '菜单权限表_导出', '', '0', '', '2', 'system:SysPermission:EXPORT', '11.00', 'el-icon-download', '菜单权限表_导出', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', 'admin', '2020-03-20 11:02:34');
INSERT INTO `sys_permission` VALUES ('1228186789386715143', '', 'Mock数据', '/mock/SysMockList', '0', 'mock/SysMockList', '0', null, '8.00', 'el-icon-camera', 'Mock数据-菜单', '1', '1', 'T4Cloud', '2020-02-14 13:20:09', 'admin', '2020-03-31 18:20:58');
INSERT INTO `sys_permission` VALUES ('1228186789386715144', '1228186789386715143', 'Mock数据_新增', null, null, null, '2', 'mock:SysMock:ADD', '11.00', 'el-icon-circle-plus-outline', 'Mock数据-新增按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:09', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715145', '1228186789386715143', 'Mock数据_修改', null, null, null, '2', 'mock:SysMock:EDIT', '12.00', 'el-icon-edit', 'Mock数据-编辑按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:09', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715146', '1228186789386715143', 'Mock数据_删除', null, null, null, '2', 'mock:SysMock:DELETE', '13.00', 'el-icon-delete', 'Mock数据-删除按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:10', null, null);
INSERT INTO `sys_permission` VALUES ('1228186789386715147', '1228186789386715143', 'Mock数据_查看', null, null, null, '2', 'mock:SysMock:VIEW', '14.00', 'el-icon-view', 'Mock数据-详情按钮', '1', '1', 'T4Cloud', '2020-02-14 13:20:10', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052039', '154e80d6a31e578d2eaa8c4634b3e8da', '用户管理', '/user/SysUserList', '0', 'user/SysUserList', '0', null, '7.00', 'el-icon-user', '用户表-菜单', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', 'admin', '2020-03-25 17:41:15');
INSERT INTO `sys_permission` VALUES ('1230085853179052040', '1230085853179052039', '用户表_新增', null, null, null, '2', 'user:SysUser:ADD', '11.00', 'el-icon-circle-plus-outline', '用户表-新增按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052041', '1230085853179052039', '用户表_修改', null, null, null, '2', 'user:SysUser:EDIT', '12.00', 'el-icon-edit', '用户表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052042', '1230085853179052039', '用户表_删除', null, null, null, '2', 'user:SysUser:DELETE', '13.00', 'el-icon-delete', '用户表-删除按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230085853179052043', '1230085853179052039', '用户表_查看', null, null, null, '2', 'user:SysUser:VIEW', '14.00', 'el-icon-view', '用户表-详情按钮', '1', '1', 'T4Cloud', '2020-02-19 19:08:35', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712972', '154e80d6a31e578d2eaa8c4634b3e8da', '角色管理', '/system/SysRole', '0', 'system/SysRoleList', '0', null, '10.00', 'el-icon-view', '角色表-菜单', '1', '1', 'T4Cloud', '2020-02-21 16:19:29', 'admin', '2020-03-17 21:27:54');
INSERT INTO `sys_permission` VALUES ('1230768750940712974', '1230768750940712972', '角色表_修改', null, null, null, '2', 'system:SysRole:EDIT', '12.00', 'el-icon-edit', '角色表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_permission` VALUES ('1230768750940712975', '1230768750940712972', '角色表_删除', null, null, null, '2', 'system:SysRole:DELETE', '13.00', 'el-icon-delete', '角色表-删除按钮', '1', '1', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_permission` VALUES ('1231177799351967764', '1230085853179052039', '用户表_导出', null, null, null, '2', 'user:SysUser:EXPORT', '15.00', 'el-icon-download', '用户表-导出按钮', '1', '1', 'T4Cloud', '2020-02-22 19:24:15', null, null);
INSERT INTO `sys_permission` VALUES ('1231177799351967765', '1230085853179052039', '用户表_导入', null, null, null, '2', 'user:SysUser:IMPORT', '16.00', 'el-icon-upload2', '用户表-导入按钮', '1', '1', 'T4Cloud', '2020-02-22 19:24:15', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441743', 'cc17035564771bb76b216fa2d1b7c2bd', '文件资源', '/support/SupResource', '0', 'support/SupResourceList', '0', null, '12.00', 'el-icon-picture-outline', '资源管理表-菜单', '1', '1', 'TeaR', '2020-02-23 20:09:19', 'admin', '2020-03-31 17:21:55');
INSERT INTO `sys_permission` VALUES ('1231551481274441744', '1231551481274441743', '资源管理表_新增', null, '0', null, '2', 'support:SupResource:ADD', '11.00', 'el-icon-circle-plus-outline', '资源管理表-新增按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', 'admin', '2020-03-06 23:17:13');
INSERT INTO `sys_permission` VALUES ('1231551481274441745', '1231551481274441743', '资源管理表_修改', null, '0', null, '2', 'support:SupResource:EDIT', '12.00', 'el-icon-edit', '资源管理表-编辑按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', 'admin', '2020-03-06 23:17:31');
INSERT INTO `sys_permission` VALUES ('1231551481274441746', '1231551481274441743', '资源管理表_删除', null, '0', null, '2', 'support:SupResource:DELETE', '13.00', 'el-icon-delete', '资源管理表-删除按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', 'admin', '2020-03-06 23:17:17');
INSERT INTO `sys_permission` VALUES ('1231551481274441747', '1231551481274441743', '资源管理表_查看', null, null, null, '2', 'support:SupResource:VIEW', '14.00', 'el-icon-view', '资源管理表-详情按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_permission` VALUES ('1231551481274441748', '1231551481274441743', '资源管理表_导出', null, '0', null, '2', 'support:SupResource:EXPORT', '15.00', 'el-icon-download', '资源管理表-导出按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', 'admin', '2020-03-06 23:17:38');
INSERT INTO `sys_permission` VALUES ('1231551481274441749', '1231551481274441743', '资源管理表_导入', null, '0', null, '2', 'support:SupResource:IMPORT', '16.00', 'el-icon-upload2', '资源管理表-导入按钮', '1', '1', 'TeaR', '2020-02-23 20:09:19', 'admin', '2020-03-06 23:17:42');
INSERT INTO `sys_permission` VALUES ('1232984097794093071', '1230768750940712972', '用户角色表', '/system/SysUserRole', '0', 'system/SysUserRoleList', '0', null, '20.00', null, '用户角色表-菜单', '0', '1', 'TeaR', '2020-02-27 19:02:01', 'admin', '2020-03-17 21:45:43');
INSERT INTO `sys_permission` VALUES ('1232984097794093074', '1232984097794093071', '用户角色表_编辑', null, '0', null, '2', 'system:SysUserRole:EDIT', '13.00', 'el-icon-delete', '用户角色表-删除按钮', '1', '1', 'TeaR', '2020-02-27 19:02:02', 'admin', '2020-03-17 21:43:47');
INSERT INTO `sys_permission` VALUES ('1232984097794093075', '1232984097794093071', '用户角色表_查看', null, null, null, '2', 'system:SysUserRole:VIEW', '14.00', 'el-icon-view', '用户角色表-详情按钮', '1', '1', 'TeaR', '2020-02-27 19:02:02', null, null);
INSERT INTO `sys_permission` VALUES ('1235980066630438927', '154e80d6a31e578d2eaa8c4634b3e8da', '字典管理', '/system/SysDict', '0', 'system/SysDictList', '0', '', '8.00', 'el-icon-s-operation', '字典-菜单', '1', '1', 'TeaR', '2020-03-07 01:28:01', 'admin', '2020-03-25 17:41:40');
INSERT INTO `sys_permission` VALUES ('1235980066630438928', '1235980066630438927', '字典_新增', null, null, null, '2', 'system:SysDict:ADD', '11.00', 'el-icon-circle-plus-outline', '字典-新增按钮', '1', '1', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_permission` VALUES ('1235980066630438929', '1235980066630438927', '字典_修改', null, null, null, '2', 'system:SysDict:EDIT', '12.00', 'el-icon-edit', '字典-编辑按钮', '1', '1', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_permission` VALUES ('1235980066630438930', '1235980066630438927', '字典_删除', null, null, null, '2', 'system:SysDict:DELETE', '13.00', 'el-icon-delete', '字典-删除按钮', '1', '1', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_permission` VALUES ('1235980066630438931', '1235980066630438927', '字典_查看', null, null, null, '2', 'system:SysDict:VIEW', '14.00', 'el-icon-view', '字典-详情按钮', '1', '1', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_permission` VALUES ('1235980066630438932', '1235980066630438927', '字典_导出', null, null, null, '2', 'system:SysDict:EXPORT', '15.00', 'el-icon-download', '字典-导出按钮', '1', '1', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_permission` VALUES ('1235980066630438933', '1235980066630438927', '字典_导入', null, null, null, '2', 'system:SysDict:IMPORT', '16.00', 'el-icon-upload2', '字典-导入按钮', '1', '1', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_permission` VALUES ('1238371494773977103', '1235980066630438927', '字典详细值', '/system/SysDictValue', '0', 'system/SysDictValueList', '0', '', '10.00', null, '字典详细值-菜单', '0', '1', 'TeaR', '2020-03-13 16:11:01', 'admin', '2020-03-15 20:36:49');
INSERT INTO `sys_permission` VALUES ('1238371494773977104', '1238371494773977103', '字典详细值_新增', null, null, null, '2', 'system:SysDictValue:ADD', '11.00', 'el-icon-circle-plus-outline', '字典详细值-新增按钮', '1', '1', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_permission` VALUES ('1238371494773977105', '1238371494773977103', '字典详细值_修改', null, null, null, '2', 'system:SysDictValue:EDIT', '12.00', 'el-icon-edit', '字典详细值-编辑按钮', '1', '1', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_permission` VALUES ('1238371494773977106', '1238371494773977103', '字典详细值_删除', null, null, null, '2', 'system:SysDictValue:DELETE', '13.00', 'el-icon-delete', '字典详细值-删除按钮', '1', '1', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_permission` VALUES ('1238371494773977107', '1238371494773977103', '字典详细值_查看', null, null, null, '2', 'system:SysDictValue:VIEW', '14.00', 'el-icon-view', '字典详细值-详情按钮', '1', '1', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_permission` VALUES ('1238371494773977108', '1238371494773977103', '字典详细值_导出', null, null, null, '2', 'system:SysDictValue:EXPORT', '15.00', 'el-icon-download', '字典详细值-导出按钮', '1', '1', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_permission` VALUES ('1238371494773977109', '1238371494773977103', '字典详细值_导入', null, null, null, '2', 'system:SysDictValue:IMPORT', '16.00', 'el-icon-upload2', '字典详细值-导入按钮', '1', '1', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_permission` VALUES ('1239437792124612623', '98384c71fd73ba6e421a7795525b7457', '模板管理', '/support/SupMessageTemplate', '0', 'support/SupMessageTemplateList', '0', '', '6.00', 'el-icon-postcard', '消息模板-菜单', '1', '1', 'TeaR', '2020-03-16 14:27:37', 'admin', '2020-03-31 18:21:19');
INSERT INTO `sys_permission` VALUES ('1239437792124612624', '1239437792124612623', '消息模板_新增', null, null, null, '2', 'support:SupMessageTemplate:ADD', '11.00', 'el-icon-circle-plus-outline', '消息模板-新增按钮', '1', '1', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_permission` VALUES ('1239437792124612625', '1239437792124612623', '消息模板_修改', null, null, null, '2', 'support:SupMessageTemplate:EDIT', '12.00', 'el-icon-edit', '消息模板-编辑按钮', '1', '1', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_permission` VALUES ('1239437792124612626', '1239437792124612623', '消息模板_删除', null, null, null, '2', 'support:SupMessageTemplate:DELETE', '13.00', 'el-icon-delete', '消息模板-删除按钮', '1', '1', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_permission` VALUES ('1239437792124612627', '1239437792124612623', '消息模板_查看', null, null, null, '2', 'support:SupMessageTemplate:VIEW', '14.00', 'el-icon-view', '消息模板-详情按钮', '1', '1', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_permission` VALUES ('1239437792124612628', '1239437792124612623', '消息模板_导出', null, null, null, '2', 'support:SupMessageTemplate:EXPORT', '15.00', 'el-icon-download', '消息模板-导出按钮', '1', '1', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_permission` VALUES ('1239437792124612629', '1239437792124612623', '消息模板_导入', null, null, null, '2', 'support:SupMessageTemplate:IMPORT', '16.00', 'el-icon-upload2', '消息模板-导入按钮', '1', '1', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_permission` VALUES ('1239531890134290447', '1230768750940712972', '角色权限表', '/system/SysRolePermission', '0', 'system/SysRolePermissionList', '0', '', '21.00', null, '角色权限表-菜单', '0', '1', 'TeaR', '2020-03-16 20:40:26', 'admin', '2020-03-17 21:45:47');
INSERT INTO `sys_permission` VALUES ('1239531890134290449', '1239531890134290447', '角色权限表_修改', null, '0', null, '2', 'system:SysRolePermission:EDIT', '18.00', 'el-icon-delete', '角色权限表-编辑按钮', '1', '1', 'TeaR', '2020-03-16 20:40:26', 'admin', '2020-03-17 21:42:10');
INSERT INTO `sys_permission` VALUES ('1239531890134290453', '1239531890134290447', '角色权限表_查看', null, null, null, '2', 'system:SysRolePermission:VIEW', '16.00', 'el-icon-view', '角色权限表-查看权限', '1', '1', 'TeaR', '2020-03-16 20:40:26', null, null);
INSERT INTO `sys_permission` VALUES ('1243420115904008208', 'cc17035564771bb76b216fa2d1b7c2bd', '日志查看', '/support/SysLog', '0', 'support/SysLogList', '0', '', '11.00', 'el-icon-info', '系统日志表-菜单', '1', '1', 'TeaR', '2020-03-27 14:11:17', 'admin', '2020-03-27 14:52:00');
INSERT INTO `sys_permission` VALUES ('1243420115904008209', '1243420115904008208', '系统日志表_新增', null, null, null, '2', 'support:SysLog:ADD', '11.00', 'el-icon-circle-plus-outline', '系统日志表-新增按钮', '1', '1', 'TeaR', '2020-03-27 14:11:17', null, null);
INSERT INTO `sys_permission` VALUES ('1243420115904008210', '1243420115904008208', '系统日志表_修改', null, null, null, '2', 'support:SysLog:EDIT', '12.00', 'el-icon-edit', '系统日志表-编辑按钮', '1', '1', 'TeaR', '2020-03-27 14:11:17', null, null);
INSERT INTO `sys_permission` VALUES ('1243420115904008211', '1243420115904008208', '系统日志表_删除', null, null, null, '2', 'support:SysLog:DELETE', '13.00', 'el-icon-delete', '系统日志表-删除按钮', '1', '1', 'TeaR', '2020-03-27 14:11:17', null, null);
INSERT INTO `sys_permission` VALUES ('1243420115904008212', '1243420115904008208', '系统日志表_查看', null, null, null, '2', 'support:SysLog:VIEW', '14.00', 'el-icon-view', '系统日志表-详情按钮', '1', '1', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_permission` VALUES ('1243420115904008213', '1243420115904008208', '系统日志表_导出', null, null, null, '2', 'support:SysLog:EXPORT', '15.00', 'el-icon-download', '系统日志表-导出按钮', '1', '1', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_permission` VALUES ('1243420115904008214', '1243420115904008208', '系统日志表_导入', null, null, null, '2', 'support:SysLog:IMPORT', '16.00', 'el-icon-upload2', '系统日志表-导入按钮', '1', '1', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_permission` VALUES ('1244832822171615248', '36c11c8b34db218f5fb4894e91e9a5d2', '通用示例', '/user/ExampleCommon', '0', 'user/ExampleCommonList', '0', '', '10.00', 'el-icon-s-cooperation', '通用示例-菜单', '1', '1', 'TeaR', '2020-03-31 11:46:00', 'admin', '2020-03-31 11:48:51');
INSERT INTO `sys_permission` VALUES ('1244832822171615249', '1244832822171615248', '通用示例_新增', null, null, null, '2', 'user:ExampleCommon:ADD', '11.00', 'el-icon-circle-plus-outline', '通用示例-新增按钮', '1', '1', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_permission` VALUES ('1244832822171615250', '1244832822171615248', '通用示例_修改', null, null, null, '2', 'user:ExampleCommon:EDIT', '12.00', 'el-icon-edit', '通用示例-编辑按钮', '1', '1', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_permission` VALUES ('1244832822171615251', '1244832822171615248', '通用示例_删除', null, null, null, '2', 'user:ExampleCommon:DELETE', '13.00', 'el-icon-delete', '通用示例-删除按钮', '1', '1', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_permission` VALUES ('1244832822171615252', '1244832822171615248', '通用示例_查看', null, null, null, '2', 'user:ExampleCommon:VIEW', '14.00', 'el-icon-view', '通用示例-详情按钮', '1', '1', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_permission` VALUES ('1244832822171615253', '1244832822171615248', '通用示例_导出', null, null, null, '2', 'user:ExampleCommon:EXPORT', '15.00', 'el-icon-download', '通用示例-导出按钮', '1', '1', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_permission` VALUES ('1244832822171615254', '1244832822171615248', '通用示例_导入', null, null, null, '2', 'user:ExampleCommon:IMPORT', '16.00', 'el-icon-upload2', '通用示例-导入按钮', '1', '1', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_permission` VALUES ('1244931937098338319', '98384c71fd73ba6e421a7795525b7457', '消息列表', '/support/SupMessage', '0', 'support/SupMessageList', '0', '', '5.00', 'el-icon-message', '消息列表-菜单', '1', '1', 'TeaR', '2020-03-31 18:19:50', 'admin', '2020-03-31 18:22:03');
INSERT INTO `sys_permission` VALUES ('1244931937098338320', '1244931937098338319', '消息列表_新增', null, null, null, '2', 'support:SupMessage:ADD', '11.00', 'el-icon-circle-plus-outline', '消息列表-新增按钮', '1', '1', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_permission` VALUES ('1244931937098338321', '1244931937098338319', '消息列表_修改', null, null, null, '2', 'support:SupMessage:EDIT', '12.00', 'el-icon-edit', '消息列表-编辑按钮', '1', '1', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_permission` VALUES ('1244931937098338322', '1244931937098338319', '消息列表_删除', null, null, null, '2', 'support:SupMessage:DELETE', '13.00', 'el-icon-delete', '消息列表-删除按钮', '1', '1', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_permission` VALUES ('1244931937098338323', '1244931937098338319', '消息列表_查看', null, null, null, '2', 'support:SupMessage:VIEW', '14.00', 'el-icon-view', '消息列表-详情按钮', '1', '1', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_permission` VALUES ('1244931937098338324', '1244931937098338319', '消息列表_导出', null, null, null, '2', 'support:SupMessage:EXPORT', '15.00', 'el-icon-download', '消息列表-导出按钮', '1', '1', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_permission` VALUES ('1244931937098338325', '1244931937098338319', '消息列表_导入', null, null, null, '2', 'support:SupMessage:IMPORT', '16.00', 'el-icon-upload2', '消息列表-导入按钮', '1', '1', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_permission` VALUES ('154e80d6a31e578d2eaa8c4634b3e8da', '', '系统设置', '/system', '0', '', '0', null, '10.00', 'el-icon-setting', null, '1', '1', 'admin', '2020-03-04 00:22:48', 'admin', '2020-03-31 17:45:04');
INSERT INTO `sys_permission` VALUES ('26d525cc2f53f2a82d5aa98936d40381', 'e1bbd3d6df87333f0b46b880e81b4e0c', '测试', '/test/test', '0', '/test/test', '2', null, '13.00', 'el-icon-user-solid', null, '1', '1', 'admin', '2020-02-25 14:33:52', 'admin', '2020-02-27 16:29:59');
INSERT INTO `sys_permission` VALUES ('36c11c8b34db218f5fb4894e91e9a5d2', '', '开发示例', '/example', '0', null, '0', null, '14.00', 'el-icon-question', null, '1', '1', 'admin', '2020-03-31 11:48:24', 'admin', '2020-03-31 11:48:33');
INSERT INTO `sys_permission` VALUES ('38293aa9317d52dfa2ab182a8ad1f35d', '38293aa9317d52dfa2ab182a8ad1f35d', 'test', 'test/test', '0', 'test/test', '0', null, '0.00', 'el-icon-user', null, '0', '1', 'admin', '2020-02-27 16:59:25', 'admin', '2020-02-27 17:05:17');
INSERT INTO `sys_permission` VALUES ('5224609085e98cebd3535c7453155f25', 'fcf943e4d0a36b3e4d66d047d011f2a3', '新增', null, '0', null, '2', 'test:test:ADD', '2.00', null, null, '0', '0', 'admin', '2020-03-02 16:41:13', 'admin', '2020-03-15 22:51:33');
INSERT INTO `sys_permission` VALUES ('5b1bea5d3c5b970662f190de94364216', '1230768750940712972', '角色_导出', null, '0', null, '2', 'system:SysRole:EXPORT', '15.00', 'el-icon-download', '角色_导出', '1', '1', 'admin', '2020-03-17 21:45:38', 'admin', '2020-03-17 21:47:11');
INSERT INTO `sys_permission` VALUES ('64f27a6691edc85a3e2507735c3dd553', '1230768750940712972', '角色_导入', null, '0', null, '2', 'system:SysRole:IMPORT', '14.00', 'el-icon-upload2', '角色_导入', '1', '1', 'admin', '2020-03-17 21:44:39', 'admin', '2020-03-17 21:45:08');
INSERT INTO `sys_permission` VALUES ('675e20a84748ebcd23d61817d4d82183', 'a02944e79cda77f8426044775a98348a', '网易', 'https://www.163.com/', '0', null, '0', null, '0.00', 'el-icon-setting', null, '1', '1', 'admin', '2020-03-26 13:38:33', 'admin', '2020-04-03 13:55:20');
INSERT INTO `sys_permission` VALUES ('98384c71fd73ba6e421a7795525b7457', '', '消息中心', '/message', '0', '', '0', null, '9.00', 'el-icon-chat-dot-square', null, '1', '1', 'admin', '2020-03-16 14:32:09', 'admin', '2020-03-16 15:06:54');
INSERT INTO `sys_permission` VALUES ('9b67244387f654d6fe3f5b5fdcc88927', 'cc17035564771bb76b216fa2d1b7c2bd', '实时性能', '/support/monitor', '0', 'support/Monitor', '0', null, '10.00', 'el-icon-s-operation', null, '1', '1', 'admin', '2020-03-05 11:17:54', 'admin', '2020-03-05 18:51:59');
INSERT INTO `sys_permission` VALUES ('a02944e79cda77f8426044775a98348a', '', '友情外链', '/link', '0', null, '0', null, '12.00', 'el-icon-s-management', null, '1', '1', 'admin', '2020-03-26 17:24:09', 'admin', '2020-03-30 10:16:57');
INSERT INTO `sys_permission` VALUES ('b9f6737ceb8ac97db76e3d28a52eadab', '1230768750940712972', '角色_查看', null, '0', null, '2', 'system:SysRole:VIEW', '1.00', 'el-icon-view', '角色_查看', '1', '1', 'admin', '2020-03-17 21:36:02', null, null);
INSERT INTO `sys_permission` VALUES ('bf53dd9fd060cf81bbe858904dba3771', 'a02944e79cda77f8426044775a98348a', '百度', 'http://www.baidu.com', '0', null, '0', null, '0.00', 'el-icon-platform-eleme', null, '1', '1', 'admin', '2020-03-17 21:23:22', 'admin', '2020-03-27 14:50:30');
INSERT INTO `sys_permission` VALUES ('cc17035564771bb76b216fa2d1b7c2bd', '', '资源监控', '/support', '0', null, '0', null, '11.00', 'el-icon-s-cooperation', null, '1', '1', 'admin', '2020-03-05 18:35:55', 'admin', '2020-03-05 18:51:25');
INSERT INTO `sys_permission` VALUES ('e1bbd3d6df87333f0b46b880e81b4e0c', '26d525cc2f53f2a82d5aa98936d40381', '子菜单', 'test/test/zi', '0', 'test/test/zi', '0', null, '0.00', 'el-icon-eleme', null, '0', '0', 'admin', '2020-02-26 16:03:15', 'admin', '2020-02-27 16:22:07');
INSERT INTO `sys_permission` VALUES ('faa6e3006784bfba0d467cd9b6452a5b', '1230768750940712972', '角色_新增', null, '0', null, '2', 'system:SysRole:ADD', '0.00', 'el-icon-circle-plus-outline', '角色_新增', '1', '1', 'admin', '2020-03-17 21:35:28', 'admin', '2020-03-17 21:38:35');
INSERT INTO `sys_permission` VALUES ('fcf943e4d0a36b3e4d66d047d011f2a3', '', '测试菜单', 'test-1', '0', 'test/test/zi', '0', null, '13.00', 'el-icon-help', null, '0', '1', 'admin', '2020-03-04 15:06:29', 'admin', '2020-03-08 23:22:23');


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `role_name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(100) NOT NULL COMMENT '角色编码',
  `description` tinytext COMMENT '描述',
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
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'SUPER-ADMIN', '', 'TeaR', '2020-02-12 13:56:36', 'admin', '2020-03-04 10:37:10');
INSERT INTO `sys_role` VALUES ('2', '管理员', 'ADMIN', null, 'TeaR', '2020-02-12 23:40:48', 'admin', '2020-03-19 13:25:06');

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
INSERT INTO `sys_role_permission` VALUES ('015', '2', '1227815647354331142', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('016', '2', '1227815647354331143', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('017', '2', '1227815647354331144', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('018', '2', '1227815647354331145', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('019', '2', '1227815647354331146', 'TeaR', '2020-02-13 12:45:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('020', '2', '1230085853179052039', 'TeaR', '2020-02-19 19:12:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('024', '2', '1230085853179052043', 'TeaR', '2020-02-19 19:12:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('025', '2', '154e80d6a31e578d2eaa8c4634b3e8da', 'TeaR', '2020-02-27 19:02:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('026', '2', 'fcf943e4d0a36b3e4d66d047d011f2a3', 'TeaR', '2020-02-27 19:02:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('027', '2', '9b67244387f654d6fe3f5b5fdcc88927', 'TeaR', '2020-03-05 13:34:53', null, null);
INSERT INTO `sys_role_permission` VALUES ('028', '2', 'cc17035564771bb76b216fa2d1b7c2bd', 'TeaR', '2020-03-05 18:39:37', null, null);
INSERT INTO `sys_role_permission` VALUES ('03a0138cffd6c5e49368f0a812b87f84', '2', '1227815647354331148', 'admin', '2020-03-17 21:59:04', null, null);
INSERT INTO `sys_role_permission` VALUES ('0fc01025c43ae18341656baf29aba3c8', '1', '1227815647354331143', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712977', '2', '1230768750940712972', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712979', '2', '1230768750940712974', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1230768750940712980', '2', '1230768750940712975', 'T4Cloud', '2020-02-21 16:19:30', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441750', '2', '1231551481274441743', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441751', '2', '1231551481274441744', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441752', '2', '1231551481274441745', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441753', '2', '1231551481274441746', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441754', '2', '1231551481274441747', 'TeaR', '2020-02-23 20:09:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441755', '2', '1231551481274441748', 'TeaR', '2020-02-23 20:09:20', null, null);
INSERT INTO `sys_role_permission` VALUES ('1231551481274441756', '2', '1231551481274441749', 'TeaR', '2020-02-23 20:09:20', null, null);
INSERT INTO `sys_role_permission` VALUES ('1232984097794093078', '2', '1232984097794093071', 'TeaR', '2020-02-27 19:02:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1232984097794093081', '2', '1232984097794093074', 'TeaR', '2020-02-27 19:02:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1232984097794093082', '2', '1232984097794093075', 'TeaR', '2020-02-27 19:02:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438934', '2', '1235980066630438927', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438935', '2', '1235980066630438928', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438936', '2', '1235980066630438929', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438937', '2', '1235980066630438930', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438938', '2', '1235980066630438931', 'TeaR', '2020-03-07 01:28:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438939', '2', '1235980066630438932', 'TeaR', '2020-03-07 01:28:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1235980066630438940', '2', '1235980066630438933', 'TeaR', '2020-03-07 01:28:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977110', '2', '1238371494773977103', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977111', '2', '1238371494773977104', 'TeaR', '2020-03-13 16:11:01', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977112', '2', '1238371494773977105', 'TeaR', '2020-03-13 16:11:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977113', '2', '1238371494773977106', 'TeaR', '2020-03-13 16:11:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977114', '2', '1238371494773977107', 'TeaR', '2020-03-13 16:11:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977115', '2', '1238371494773977108', 'TeaR', '2020-03-13 16:11:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1238371494773977116', '2', '1238371494773977109', 'TeaR', '2020-03-13 16:11:02', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612631', '2', '1239437792124612624', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612632', '2', '1239437792124612625', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612633', '2', '1239437792124612626', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612634', '2', '1239437792124612627', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612635', '2', '1239437792124612628', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612636', '2', '1239437792124612629', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239437792124612637', '2', '98384c71fd73ba6e421a7795525b7457', 'TeaR', '2020-03-16 14:27:38', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239531890134290454', '2', '1239531890134290447', 'TeaR', '2020-03-16 20:40:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239531890134290456', '2', '1239531890134290449', 'TeaR', '2020-03-16 20:40:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('1239531890134290460', '2', '1239531890134290453', 'TeaR', '2020-03-16 20:40:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008215', '2', '1243420115904008208', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008216', '2', '1243420115904008209', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008217', '2', '1243420115904008210', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008218', '2', '1243420115904008211', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008219', '2', '1243420115904008212', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008220', '2', '1243420115904008213', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1243420115904008221', '2', '1243420115904008214', 'TeaR', '2020-03-27 14:11:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615255', '2', '1244832822171615248', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615256', '2', '1244832822171615249', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615257', '2', '1244832822171615250', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615258', '2', '1244832822171615251', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615259', '2', '1244832822171615252', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615260', '2', '1244832822171615253', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244832822171615261', '2', '1244832822171615254', 'TeaR', '2020-03-31 11:46:00', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338326', '2', '1244931937098338319', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338327', '2', '1244931937098338320', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338328', '2', '1244931937098338321', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338329', '2', '1244931937098338322', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338330', '2', '1244931937098338323', 'TeaR', '2020-03-31 18:19:50', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338331', '2', '1244931937098338324', 'TeaR', '2020-03-31 18:19:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('1244931937098338332', '2', '1244931937098338325', 'TeaR', '2020-03-31 18:19:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('206f7e2053894af50b0593503b63eecd', '2', '1231177799351967764', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('298c9ca430bd537105164536405759dd', '2', '1230085853179052041', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('31a36beaf79b0484bad40b46115558ac', '1', '1227815647354331146', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('4067e4c59c32f8e4dbab5f2ef106a01f', '1', '1227815647354331145', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('4df67bac30958e0f33f5392be389307a', '2', '1231177799351967765', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('4ee1ba6b246b0234ece70fd18c437dbe', '2', '675e20a84748ebcd23d61817d4d82183', 'admin', '2020-03-26 13:38:41', null, null);
INSERT INTO `sys_role_permission` VALUES ('56b3b76e86f4ac12f196236b4734b89c', '2', '1239437792124612623', 'admin', '2020-03-17 20:39:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('6d3189900411c89f2664b4f614febb00', '1', '1239531890134290453', 'admin', '2020-03-17 20:45:48', null, null);
INSERT INTO `sys_role_permission` VALUES ('74fa05756c9a1f9dcafef0fc61ce5d92', '2', '1230085853179052040', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('792977664dfe601825c52b3f2fd09b1b', '2', '36c11c8b34db218f5fb4894e91e9a5d2', 'admin', '2020-03-31 11:49:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('7a6effaff31798340f40c639c3a27e0e', '2', '64f27a6691edc85a3e2507735c3dd553', 'admin', '2020-03-17 21:46:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('8dcde5b3fc6b9c0ec9e74b32e9505dcc', '2', '5b1bea5d3c5b970662f190de94364216', 'admin', '2020-03-17 21:46:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('acab97fea93aefab770c6302146cb11c', '2', '1227815647354331147', 'admin', '2020-03-17 21:59:04', null, null);
INSERT INTO `sys_role_permission` VALUES ('b9f6737ceb8ac97db76e3d28a52eadab', '2', 'b9f6737ceb8ac97db76e3d28a52eadab', 'TeaR', '2020-03-17 21:23:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('e0d957c55345747cf1e2762fe609e428', '1', '1227815647354331142', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('e1050d5e9e79b5e7207d7d08ab76bd65', '2', '1230085853179052042', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('ecb5cc5f1fc48f51763eaba6bc340481', '1', '1227815647354331144', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('faa6e3006784bfba0d467cd9b6452a5b', '2', 'faa6e3006784bfba0d467cd9b6452a5b', 'TeaR', '2020-03-17 21:23:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('fbfb413178330f63495fb2173e987b66', '2', 'a02944e79cda77f8426044775a98348a', 'admin', '2020-03-30 10:17:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('fc3e4c2305bd27e6dcb95cef719c58b3', '2', 'bf53dd9fd060cf81bbe858904dba3771', 'admin', '2020-03-17 21:23:32', null, null);


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
INSERT INTO `sys_user` VALUES ('1', 'TeaR', 'TeaR', '700961d246e6ddf1', 'SDFyT0h3', '001', 'https://git.t4cloud.com/img/favicon.png', '2018-12-05 00:00:00', '2', 'zqr.it@t4cloud.com', '17800001111', null, null, '上海市浦东新区陆家嘴', '1', 'TeaR', '2017-05-02 21:26:48', 'admin', '2020-03-18 11:17:06');
INSERT INTO `sys_user` VALUES ('14f10842654c1e343ec2447462fc3a8d', 'admin', '管理员', 'cb362cfeefbf3d8d', 'RCGTeGiH', '002', '905fdc367b7f37f222aef0adaeedd2ad', null, '1', 'TEAR1', '', '', '', '', '1', 'TeaR', '2017-05-02 21:26:48', 'admin', '2020-04-01 23:55:44');
INSERT INTO `sys_user` VALUES ('94cc921ba37944c41a2b2d4aa2a29d42', 'yxy', 'yuxiuyu', null, null, '112', 'https://git.t4cloud.com/img/favicon.png', null, null, null, null, null, null, null, '1', 'admin', '2020-03-31 16:37:19', 'admin', '2020-04-01 15:44:23');

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
INSERT INTO `sys_user_role` VALUES ('03e7c3b5043c7dcad91141dfdbd28997', '14f10842654c1e343ec2447462fc3a8d', '1', 'admin', '2020-03-23 23:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('2', '1', '2', 'TeaR', '2020-01-12 13:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('4', '14f10842654c1e343ec2447462fc3a8d', '2', 'TeaR', '2020-01-12 13:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('8a1b1b04269c5c42ca1a39afbd0d1e51', 'f4d9c3a25fd658cae08e316c6b115d9e', '2', 'admin', '2020-03-20 12:21:32', null, null);
INSERT INTO `sys_user_role` VALUES ('da0ecd8f61dbcc75eed19fb10ac60705', '1', '1', 'admin', '2020-03-23 23:54:06', null, null);

