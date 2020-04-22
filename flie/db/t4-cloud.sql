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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '租户ID',
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
INSERT INTO `sup_message` VALUES ('bdfaee854ad5346b97cd15e19b927f62', 'Hello,tear!', '2', 'zqr.it@t4cloud.com', null, '<h1>Hello,tear!</h1>', '2020-03-31 22:50:55', '5', '-1', 'Authentication failed; nested exception is javax.mail.AuthenticationFailedException: 535 Error: authentication failed, system busy\n', null, '-1', 'admin', '2020-03-31 22:35:53', 'mailSender任务', '2020-04-01 12:11:18');
INSERT INTO `sup_message` VALUES ('c124036ed98e9fbc8c09132d00e99f88', '用户手机验证码', '1', '17621830000', '{\"code\":\"890571\",\"username\":\"17621830000\"}', '{\"signName\":\"LTBS\",\"code\":\"SMS_186942404\"}', '2020-04-20 15:59:27', '1', '1', null, null, 'bb72c26e144fe6c9a0f5cd54a109e499', null, '2020-04-20 15:59:27', '短信发送任务', null);
INSERT INTO `sup_message` VALUES ('e02347126d10b806b827fa87c942e8f5', '前端批量测试', '2', 'zqr.it@t4cloud.com', null, '前端批量测试', '2020-04-01 14:22:46', '1', '1', null, null, '-1', 'admin', '2020-04-01 14:22:46', 'mailSender任务', '2020-04-01 14:22:53');

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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人登录名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人登录名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_templatecode` (`template_code`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息模板';

-- ----------------------------
-- Records of sup_message_template
-- ----------------------------
INSERT INTO `sup_message_template` VALUES ('775c4c1f38389540d7132abe1557cc7e', 'PHONE-CAPTCHA', '用户手机验证码', '1', '{\"signName\":\"LTBS\",\"code\":\"SMS_186942404\"}', 'bb72c26e144fe6c9a0f5cd54a109e499', '2020-04-08 17:11:47', 'TeaR', '0000-00-00 00:00:00', '');
INSERT INTO `sup_message_template` VALUES ('775c4c1f38389540d7132abe1557cc7f', 'PHONE-CAPTCHA', '用户手机验证码', '1', '{\"signName\":\"LTBS\",\"code\":\"SMS_186942404\"}', '-1', '2020-04-08 17:11:47', 'TeaR', '0000-00-00 00:00:00', '');
INSERT INTO `sup_message_template` VALUES ('875c4c1f38389540d7132abe1557cc7f', 'EMAIL-CAPTCHA', '${username},您的验证码【T4Cloud】', '2', '${username},您的验证码为${code}，三十分钟内有效，请妥善保管。【T4Cloud】', '-1', '2020-04-08 15:08:35', 'admin', '0000-00-00 00:00:00', '');
INSERT INTO `sup_message_template` VALUES ('f6f311e79c573b3a8652b386cd9b7390', 'TEST', '${username}，T4Cloud验证码', '2', 'hi，${username}，请妥善保管您的动态密码${code}，有效期为30分钟。', '-1', '2020-03-26 17:57:18', 'admin', '2020-04-21 16:01:38', 'demo');

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
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(100) NOT NULL COMMENT '公司名',
  `name_en` varchar(500) DEFAULT NULL COMMENT '英文名',
  `name_abbr` varchar(500) DEFAULT NULL COMMENT '公司名缩写',
  `description` text COMMENT '描述',
  `phone` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `fax` varchar(32) DEFAULT NULL COMMENT '传真',
  `address` varchar(300) DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `tenant_id` varchar(640) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '租户ID',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='公司表';

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES ('-1', 'T4Cloud', 'T4Cloud', 'T.4.C', '微服务快速开发框架', '178xxxx1004', '178xxxx1004', '上海', '开源组织', '1', '-1', 'TeaR', '2020-04-15 11:38:43', 'admin', '2020-04-15 12:07:26');
INSERT INTO `sys_company` VALUES ('28cf78459b4cfc1050e8ab13f5b81c00', '广德试车场', 'Vehicle-Test', 'Vehicle-Test', null, null, null, null, null, '1', '-1', 'admin', '2020-04-21 13:32:32', 'admin', '2020-04-21 13:32:40');
INSERT INTO `sys_company` VALUES ('bb72c26e144fe6c9a0f5cd54a109e499', '多里健康', 'Health', 'Health', null, null, null, null, null, '1', '-1', 'admin', '2020-04-16 17:26:17', null, null);

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
INSERT INTO `sys_dict` VALUES ('21a830c22a18df374ca4dbe244bef996', '临床试验报名处理状态', 'health_test_sign_up_status', null, '1', 'admin', '2020-04-19 19:26:15', null, null);
INSERT INTO `sys_dict` VALUES ('26405b1615e6646eb670f34de100dd80', '权限类型', 'menu_type', null, '1', 'admin', '2020-04-14 17:33:20', null, null);
INSERT INTO `sys_dict` VALUES ('26a8a0b7847ea1b63f312c17c5cc0a2b', '公告类型', 'banner_type', null, '1', 'admin', '2020-04-17 12:27:34', null, null);
INSERT INTO `sys_dict` VALUES ('84290353c8b63fb5f76a949cf976890b', '打开方式', 'open_type', null, '1', 'admin', '2020-03-17 21:19:38', null, null);
INSERT INTO `sys_dict` VALUES ('ad2cce5a0c32d8efbd72b718534bcfb4', '消息发送状态', 'msg_send_status', null, '1', 'admin', '2020-03-31 18:26:33', null, null);
INSERT INTO `sys_dict` VALUES ('b160ec9f89fd1caf9c35f4cf53b9816e', 'APP通用信息类型', 'app_information_type', null, '1', 'admin', '2020-04-20 12:14:41', null, null);
INSERT INTO `sys_dict` VALUES ('b4222c4ab58f2d51586dcaab5226f7b5', '医疗-病症是否开启频道', 'health_channel', null, '1', 'admin', '2020-04-16 18:28:01', null, null);
INSERT INTO `sys_dict` VALUES ('c6b035b3df1bd679ebe1f943c68aed78', '操作类型', 'operate_type', null, '1', 'admin', '2020-03-27 14:19:09', null, null);
INSERT INTO `sys_dict` VALUES ('cdd5c29b398e17d5f3476e3e91c72e2e', '室外地图风格', 'gps_map_style', null, '1', 'admin', '2020-04-21 17:15:53', null, null);
INSERT INTO `sys_dict` VALUES ('d508e63de2d3ccb0b8704264483bd7a4', '医疗-临床试验类型', 'clinical_test_type', null, '1', 'admin', '2020-04-16 22:29:50', null, null);
INSERT INTO `sys_dict` VALUES ('d8259873aece0de66f404a3ef5ff5881', '通用结果', 'common_valid', null, '1', 'admin', '2020-03-27 14:22:11', null, null);
INSERT INTO `sys_dict` VALUES ('eb1f3c32a4f6b40279cf3e3ca52b9afd', '医疗-临床试验状态', 'clinical_test_status', null, '1', 'admin', '2020-04-16 22:31:29', null, null);
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
INSERT INTO `sys_dict_value` VALUES ('039a2c0ea6f02a25cf2da2647654f640', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'fresh', '6', '草色青', '6', '1', 'admin', '2020-04-21 17:18:46', null, null);
INSERT INTO `sys_dict_value` VALUES ('05915cf6352322cf6b312422d669556d', '84290353c8b63fb5f76a949cf976890b', '新标签打开', '1', null, '10', '1', 'admin', '2020-03-17 21:20:20', null, null);
INSERT INTO `sys_dict_value` VALUES ('06f8858b6f2626fbdf98cd2ac4bd15ee', 'b160ec9f89fd1caf9c35f4cf53b9816e', '主题', '1', null, '1', '1', 'admin', '2020-04-20 12:14:52', null, null);
INSERT INTO `sys_dict_value` VALUES ('0a897e81bd36126d7ba56c7623f0e365', 'd508e63de2d3ccb0b8704264483bd7a4', '晚期初治', '4', null, '4', '1', 'admin', '2020-04-16 22:31:05', null, null);
INSERT INTO `sys_dict_value` VALUES ('1', '1', '生效', '1', null, '1', '1', 'TeaR', '2019-02-09 13:18:47', 'admin', '2020-03-15 20:37:31');
INSERT INTO `sys_dict_value` VALUES ('171c867f55dd055bb59fe6f3614c4e00', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'normal', '0', '高德风格', '0', '1', 'admin', '2020-04-21 17:19:11', null, null);
INSERT INTO `sys_dict_value` VALUES ('1753abc515ca2d18ad4d9e1c29d1d27d', 'ad2cce5a0c32d8efbd72b718534bcfb4', '停止发送', '-1', null, '-1', '1', 'admin', '2020-03-31 18:27:39', null, null);
INSERT INTO `sys_dict_value` VALUES ('197ca6841779dd9b8d028d81e4ab3f5c', 'ed77389295a5d4c90c9ade1b2671c336', '隐藏', '0', null, '10', '1', 'admin', '2020-03-17 21:16:11', 'admin', '2020-03-26 10:24:52');
INSERT INTO `sys_dict_value` VALUES ('1b151c1318e2f0e4fd0b9cd46a843128', 'b160ec9f89fd1caf9c35f4cf53b9816e', '评论', '2', null, '2', '1', 'admin', '2020-04-20 12:14:58', null, null);
INSERT INTO `sys_dict_value` VALUES ('2', '1', '失效', '0', '', '2', '1', 'TeaR', '2019-02-09 13:18:47', '', null);
INSERT INTO `sys_dict_value` VALUES ('28fa16682964ccb0e792020d153d3197', '1117af12dec6f9ed3adf81a4b0fc3b3b', '用户操作', '3', null, '3', '1', 'admin', '2020-03-27 14:13:29', null, null);
INSERT INTO `sys_dict_value` VALUES ('4', '2', '男', '1', null, '1', '1', 'TeaR', '2020-03-04 14:14:44', 'admin', '2020-03-26 10:25:23');
INSERT INTO `sys_dict_value` VALUES ('403179f518f127d952d05125ad1e8b24', '21a830c22a18df374ca4dbe244bef996', '待处理', '1', null, '1', '1', 'admin', '2020-04-19 19:26:41', 'admin', '2020-04-19 19:27:06');
INSERT INTO `sys_dict_value` VALUES ('408974ceb7cb2bfc8fd36248c91f5504', 'd8259873aece0de66f404a3ef5ff5881', '正常', '1', null, '1', '1', 'admin', '2020-03-27 14:22:21', null, null);
INSERT INTO `sys_dict_value` VALUES ('4abf5aee23a3d29d1607ed04abe00d6f', 'ad2cce5a0c32d8efbd72b718534bcfb4', '推送成功', '1', null, '1', '1', 'admin', '2020-03-31 18:27:11', null, null);
INSERT INTO `sys_dict_value` VALUES ('4bca9857fd415001f3be0d464d0ef54f', '26a8a0b7847ea1b63f312c17c5cc0a2b', 'Banner', '1', null, '1', '1', 'admin', '2020-04-17 12:27:44', null, null);
INSERT INTO `sys_dict_value` VALUES ('4e4d3dcf8e89da8410a1a5e2f7ff1695', 'c6b035b3df1bd679ebe1f943c68aed78', '查', '4', null, '4', '1', 'admin', '2020-03-27 14:21:11', null, null);
INSERT INTO `sys_dict_value` VALUES ('5', '2', '女', '2', null, '2', '1', 'TeaR', '2020-03-04 14:14:44', null, null);
INSERT INTO `sys_dict_value` VALUES ('5ec67b8617b5944efed297be465e810b', '049d8f8d39bfed55fe8d6ccd317c9e78', '邮件', '2', null, '2', '1', 'admin', '2020-03-26 17:36:27', 'admin', '2020-04-01 12:24:24');
INSERT INTO `sys_dict_value` VALUES ('611574ecf9d3252fdbe3d92b1d752ca2', 'd508e63de2d3ccb0b8704264483bd7a4', '晚期多线', '1', null, '1', '1', 'admin', '2020-04-16 22:30:09', null, null);
INSERT INTO `sys_dict_value` VALUES ('6538ad3082c6739a879bbbfbf3c218a8', 'd508e63de2d3ccb0b8704264483bd7a4', '晚期后线', '2', null, '2', '1', 'admin', '2020-04-16 22:30:20', null, null);
INSERT INTO `sys_dict_value` VALUES ('6a9cb93f261616fa58464506ba9d1e0a', 'ad2cce5a0c32d8efbd72b718534bcfb4', '推送失败', '2', null, '2', '1', 'admin', '2020-03-31 18:27:19', null, null);
INSERT INTO `sys_dict_value` VALUES ('6afeef29d4e34bfcf8050a1eb6bd60ee', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'grey', '4', '雅士灰', '4', '1', 'admin', '2020-04-21 17:17:55', null, null);
INSERT INTO `sys_dict_value` VALUES ('74400d676a33ddbd4a2573ae0652f2b6', 'c6b035b3df1bd679ebe1f943c68aed78', '删', '2', null, '2', '0', 'admin', '2020-03-27 14:20:15', 'admin', '2020-03-30 18:31:10');
INSERT INTO `sys_dict_value` VALUES ('745c2eae51ccc41e6cda1be80484515e', '1ba37859b072ead1bc8daa03cad669f3', '公开', '1', null, '1', '1', 'admin', '2020-03-26 11:20:26', null, null);
INSERT INTO `sys_dict_value` VALUES ('77c0b3c95e8ed92fa98883b5ed133d34', '1117af12dec6f9ed3adf81a4b0fc3b3b', '登录日志', '2', null, '2', '1', 'admin', '2020-03-27 14:13:18', null, null);
INSERT INTO `sys_dict_value` VALUES ('7aff90e0935fac2e02b92fa36d371f38', '1117af12dec6f9ed3adf81a4b0fc3b3b', '定时任务', '4', null, '4', '1', 'admin', '2020-03-27 14:13:35', null, null);
INSERT INTO `sys_dict_value` VALUES ('7da9b07911c21677cab5ddec664eff7a', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'darkblue', '1', '极光蓝', '1', '1', 'admin', '2020-04-21 17:16:55', null, null);
INSERT INTO `sys_dict_value` VALUES ('816653f5e2c84ed3c86217cc5434e893', 'd508e63de2d3ccb0b8704264483bd7a4', '晚期一线', '3', null, '3', '1', 'admin', '2020-04-16 22:30:39', null, null);
INSERT INTO `sys_dict_value` VALUES ('83a6a3409bc7f5fe3e6533eb771ee9ed', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'whitesmoke', '3', '远山黛', '3', '1', 'admin', '2020-04-21 17:17:34', null, null);
INSERT INTO `sys_dict_value` VALUES ('85c9c15c5c4af870efa9965cc87b1f9d', '1117af12dec6f9ed3adf81a4b0fc3b3b', 'MQ日志', '5', null, '5', '1', 'admin', '2020-03-30 14:35:05', null, null);
INSERT INTO `sys_dict_value` VALUES ('86635d15e33c382229ec955d17cab2af', 'ed77389295a5d4c90c9ade1b2671c336', '展示', '1', null, '10', '1', 'admin', '2020-03-17 21:16:20', null, null);
INSERT INTO `sys_dict_value` VALUES ('8e7d06da5f809b8f3f55c5a5ca3493ca', '049d8f8d39bfed55fe8d6ccd317c9e78', '微信', '3', null, '3', '0', 'admin', '2020-03-31 23:22:43', 'admin', '2020-04-01 12:24:27');
INSERT INTO `sys_dict_value` VALUES ('8f4cc0e29f4e57441362a9ffd250e335', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'dark', '2', '幻影黑', '2', '1', 'admin', '2020-04-21 17:17:11', null, null);
INSERT INTO `sys_dict_value` VALUES ('90dc29ad82ab90f4ff695b15d1b35f3c', '21a830c22a18df374ca4dbe244bef996', '已拒绝', '3', null, '3', '1', 'admin', '2020-04-19 19:27:18', null, null);
INSERT INTO `sys_dict_value` VALUES ('953b4afc3a12a866bed18b8d9cbfa983', 'c6b035b3df1bd679ebe1f943c68aed78', '改', '3', null, '3', '1', 'admin', '2020-03-27 14:20:21', null, null);
INSERT INTO `sys_dict_value` VALUES ('a3950312546e3fd1a064e982c9b0cec7', 'eb1f3c32a4f6b40279cf3e3ca52b9afd', '重点推荐', '1', null, '1', '1', 'admin', '2020-04-16 22:31:51', null, null);
INSERT INTO `sys_dict_value` VALUES ('ad673c2106e04f2dfe04e4d2b6e4bc0f', '1117af12dec6f9ed3adf81a4b0fc3b3b', '其他日志', '6', null, '6', '1', 'admin', '2020-03-27 14:13:41', 'admin', '2020-03-30 14:34:55');
INSERT INTO `sys_dict_value` VALUES ('b05ea675be6d9c827125aaedae71d613', '21a830c22a18df374ca4dbe244bef996', '已同意', '4', null, '4', '1', 'admin', '2020-04-19 19:27:24', null, null);
INSERT INTO `sys_dict_value` VALUES ('b7295f464425a7384740675b88482e72', '21a830c22a18df374ca4dbe244bef996', '跟进中', '2', null, '2', '1', 'admin', '2020-04-19 19:27:02', null, null);
INSERT INTO `sys_dict_value` VALUES ('b787884dc3fc099fd7236e9ad3cc60b1', '049d8f8d39bfed55fe8d6ccd317c9e78', '站内信', '4', null, '4', '0', 'admin', '2020-03-31 23:23:07', 'admin', '2020-04-01 12:24:31');
INSERT INTO `sys_dict_value` VALUES ('cb9956370eb22276f9a5d26b211c67fe', 'b4222c4ab58f2d51586dcaab5226f7b5', '开启', '1', null, '1', '1', 'admin', '2020-04-16 18:28:09', null, null);
INSERT INTO `sys_dict_value` VALUES ('cdff1c1dc530fade19d4c494c81b7dfd', 'eb1f3c32a4f6b40279cf3e3ca52b9afd', '近期启动', '2', null, '2', '1', 'admin', '2020-04-16 22:32:03', 'admin', '2020-04-16 22:32:10');
INSERT INTO `sys_dict_value` VALUES ('cec4d7deccec648416d069e9046730ff', '1ba37859b072ead1bc8daa03cad669f3', '私有', '2', null, '2', '1', 'admin', '2020-03-26 11:20:32', null, null);
INSERT INTO `sys_dict_value` VALUES ('d69854f5f233fab4c198e67f97d41d1f', 'ad2cce5a0c32d8efbd72b718534bcfb4', '待推送', '0', null, '0', '1', 'admin', '2020-03-31 18:27:00', 'admin', '2020-03-31 23:21:25');
INSERT INTO `sys_dict_value` VALUES ('d71d1657a2706d5d6b4e4dea573587de', 'c6b035b3df1bd679ebe1f943c68aed78', '增', '1', null, '1', '1', 'admin', '2020-03-27 14:20:09', null, null);
INSERT INTO `sys_dict_value` VALUES ('db3b414925124232e8ec15780a7b9563', '049d8f8d39bfed55fe8d6ccd317c9e78', '短信', '1', null, '1', '0', 'admin', '2020-03-26 17:36:17', 'admin', '2020-04-01 12:24:21');
INSERT INTO `sys_dict_value` VALUES ('dc8067157547cb491115aef50d9be6c0', '84290353c8b63fb5f76a949cf976890b', '页面内打开', '0', null, '10', '1', 'admin', '2020-03-17 21:20:10', null, null);
INSERT INTO `sys_dict_value` VALUES ('dd36d4da957ca69a4504c5995d7fe2e2', '26a8a0b7847ea1b63f312c17c5cc0a2b', 'Tips', '2', null, '2', '1', 'admin', '2020-04-17 12:27:50', null, null);
INSERT INTO `sys_dict_value` VALUES ('e72362771956e9b7fbff56963945c8f5', '1117af12dec6f9ed3adf81a4b0fc3b3b', '管理员操作', '1', null, '1', '1', 'admin', '2020-03-27 14:13:12', null, null);
INSERT INTO `sys_dict_value` VALUES ('e8b30d52633fe116b3c7c2496fd29e9a', 'eb1f3c32a4f6b40279cf3e3ca52b9afd', '可推荐', '0', null, '0', '1', 'admin', '2020-04-16 22:31:40', null, null);
INSERT INTO `sys_dict_value` VALUES ('e936e5cba406ecb79b799040e08fa916', 'cdd5c29b398e17d5f3476e3e91c72e2e', 'blue', '5', '靛青蓝', '5', '1', 'admin', '2020-04-21 17:18:21', null, null);
INSERT INTO `sys_dict_value` VALUES ('ea01e1c04c56e1a9de77340f0f074dac', '26405b1615e6646eb670f34de100dd80', '按钮', '2', null, '2', '1', 'admin', '2020-04-14 17:33:46', null, null);
INSERT INTO `sys_dict_value` VALUES ('fcad0268ffcdc5a1d43c128f9a3eebda', 'b4222c4ab58f2d51586dcaab5226f7b5', '关闭', '0', null, '0', '1', 'admin', '2020-04-16 18:28:17', null, null);
INSERT INTO `sys_dict_value` VALUES ('fe858aae02b3df9497c0d21745de9624', '26405b1615e6646eb670f34de100dd80', '菜单', '0', null, '0', '1', 'admin', '2020-04-14 17:33:38', null, null);


-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(32) CHARACTER SET utf8 NOT NULL,
  `log_type` tinyint(2) DEFAULT NULL COMMENT '日志类型（1-管理员操作，2-登录日志，3-用户操作，4-定时任务，5-其他日志）',
  `log_content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '日志内容',
  `operate_type` tinyint(2) DEFAULT NULL COMMENT '操作类型(1-增，2-删，3-改，4-查)',
  `result` longtext CHARACTER SET utf8 COMMENT '操作结果记录',
  `result_type` tinyint(2) DEFAULT NULL COMMENT '是否异常（0-异常，1-正常）',
  `user_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作用户账号',
  `username` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作用户名称',
  `ip` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'IP',
  `method` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求java方法',
  `request_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求路径',
  `request_param` longtext CHARACTER SET utf8 COMMENT '请求参数',
  `request_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求类型',
  `cost_time` bigint(20) DEFAULT NULL COMMENT '耗时',
  `tenant_id` varchar(32) DEFAULT '-1' COMMENT '租户ID',
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
INSERT INTO `sys_permission` VALUES ('0244271e995e63c8c0a9dd6c977adc17', '38b893902c20bc3be8cda64b83395d6c', 'test', '', '0', null, '2', null, '0.00', null, null, '1', '1', 'admin', '2020-04-20 17:05:36', 'admin', '2020-04-20 17:09:03');
INSERT INTO `sys_permission` VALUES ('1227815647354331142', '154e80d6a31e578d2eaa8c4634b3e8da', '菜单权限', '/system/SysPermissionList', '0', 'system/SysPermissionList', '0', null, '9.00', 'el-icon-help', '菜单权限表-菜单', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', 'admin', '2020-03-25 17:41:35');
INSERT INTO `sys_permission` VALUES ('1227815647354331143', '1227815647354331142', '菜单权限表_新增', null, null, null, '2', 'system:SysPermission:ADD', '11.00', 'el-icon-circle-plus-outline', '菜单权限表-新增按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331144', '1227815647354331142', '菜单权限表_修改', null, null, null, '2', 'system:SysPermission:EDIT', '11.00', 'el-icon-edit', '菜单权限表-编辑按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331145', '1227815647354331142', '菜单权限表_删除', null, null, null, '2', 'system:SysPermission:DELETE', '11.00', 'el-icon-delete', '菜单权限表-删除按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331146', '1227815647354331142', '菜单权限表_查看', null, null, null, '2', 'system:SysPermission:VIEW', '11.00', 'el-icon-view', '菜单权限表-详情按钮', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', null, null);
INSERT INTO `sys_permission` VALUES ('1227815647354331147', '1227815647354331142', '菜单权限表_导入', '', null, '', '2', 'system:SysPermission:IMPORT', '11.00', 'el-icon-upload2', '菜单权限表_导入', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1227815647354331148', '1227815647354331142', '菜单权限表_导出', '', '0', '', '2', 'system:SysPermission:EXPORT', '11.00', 'el-icon-download', '菜单权限表_导出', '1', '1', 'T4Cloud', '2020-02-13 12:44:16', 'admin', '2020-03-20 11:02:34');
INSERT INTO `sys_permission` VALUES ('1228186789386715143', '', 'Mock数据', '/mock/SysMockList', '0', 'mock/SysMockList', '0', null, '8.00', 'el-icon-camera', 'Mock数据-菜单', '0', '1', 'T4Cloud', '2020-02-14 13:20:09', 'TeaR', '2020-04-14 17:20:29');
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
INSERT INTO `sys_permission` VALUES ('1246748197888012304', 'fcf943e4d0a36b3e4d66d047d011f2a3', '流程请假业务表', '/user/ProcessLeave', '0', 'user/ProcessLeaveList', '0', '', '10.00', null, '流程请假业务表-菜单', '0', '1', 'TeaR', '2020-04-05 18:36:08', 'admin', '2020-04-17 12:35:14');
INSERT INTO `sys_permission` VALUES ('1246748197888012305', '1246748197888012304', '流程请假业务表_新增', null, null, null, '2', 'user:ProcessLeave:ADD', '11.00', 'el-icon-circle-plus-outline', '流程请假业务表-新增按钮', '1', '1', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_permission` VALUES ('1246748197888012306', '1246748197888012304', '流程请假业务表_修改', null, null, null, '2', 'user:ProcessLeave:EDIT', '12.00', 'el-icon-edit', '流程请假业务表-编辑按钮', '1', '1', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_permission` VALUES ('1246748197888012307', '1246748197888012304', '流程请假业务表_删除', null, null, null, '2', 'user:ProcessLeave:DELETE', '13.00', 'el-icon-delete', '流程请假业务表-删除按钮', '1', '1', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_permission` VALUES ('1246748197888012308', '1246748197888012304', '流程请假业务表_查看', null, null, null, '2', 'user:ProcessLeave:VIEW', '14.00', 'el-icon-view', '流程请假业务表-详情按钮', '1', '1', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_permission` VALUES ('1246748197888012309', '1246748197888012304', '流程请假业务表_导出', null, null, null, '2', 'user:ProcessLeave:EXPORT', '15.00', 'el-icon-download', '流程请假业务表-导出按钮', '1', '1', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_permission` VALUES ('1246748197888012310', '1246748197888012304', '流程请假业务表_导入', null, null, null, '2', 'user:ProcessLeave:IMPORT', '16.00', 'el-icon-upload2', '流程请假业务表-导入按钮', '1', '1', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_permission` VALUES ('1250259609349386255', '154e80d6a31e578d2eaa8c4634b3e8da', '公司管理', '/system/SysCompany', '0', 'system/SysCompanyList', '0', '', '11.00', 'el-icon-office-building', '公司表-菜单', '1', '1', 'TeaR', '2020-04-15 11:08:28', 'admin', '2020-04-15 11:29:21');
INSERT INTO `sys_permission` VALUES ('1250259609349386256', '1250259609349386255', '公司表_新增', null, null, null, '2', 'system:SysCompany:ADD', '11.00', 'el-icon-circle-plus-outline', '公司表-新增按钮', '1', '1', 'TeaR', '2020-04-15 11:08:28', null, null);
INSERT INTO `sys_permission` VALUES ('1250259609349386257', '1250259609349386255', '公司表_修改', null, null, null, '2', 'system:SysCompany:EDIT', '12.00', 'el-icon-edit', '公司表-编辑按钮', '1', '1', 'TeaR', '2020-04-15 11:08:28', null, null);
INSERT INTO `sys_permission` VALUES ('1250259609349386258', '1250259609349386255', '公司表_删除', null, null, null, '2', 'system:SysCompany:DELETE', '13.00', 'el-icon-delete', '公司表-删除按钮', '1', '1', 'TeaR', '2020-04-15 11:08:28', null, null);
INSERT INTO `sys_permission` VALUES ('1250259609349386259', '1250259609349386255', '公司表_查看', null, null, null, '2', 'system:SysCompany:VIEW', '14.00', 'el-icon-view', '公司表-详情按钮', '1', '1', 'TeaR', '2020-04-15 11:08:28', null, null);
INSERT INTO `sys_permission` VALUES ('1250259609349386260', '1250259609349386255', '公司表_导出', null, null, null, '2', 'system:SysCompany:EXPORT', '15.00', 'el-icon-download', '公司表-导出按钮', '1', '1', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_permission` VALUES ('1250259609349386261', '1250259609349386255', '公司表_导入', null, null, null, '2', 'system:SysCompany:IMPORT', '16.00', 'el-icon-upload2', '公司表-导入按钮', '1', '1', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_permission` VALUES ('1250300634419757072', '1250259609349386255', '用户公司表', '/system/SysUserCompany', '0', 'system/SysUserCompanyList', '0', '', '17.00', null, '用户公司表-菜单', '0', '1', 'TeaR', '2020-04-15 13:54:29', 'admin', '2020-04-16 17:02:01');
INSERT INTO `sys_permission` VALUES ('1250300634419757074', '1250300634419757072', '用户公司表_修改', null, null, null, '2', 'system:SysUserCompany:EDIT', '12.00', 'el-icon-edit', '用户公司表-编辑按钮', '1', '1', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_permission` VALUES ('1250300634419757075', '1250300634419757072', '用户公司表_删除', null, null, null, '2', 'system:SysUserCompany:DELETE', '13.00', 'el-icon-delete', '用户公司表-删除按钮', '1', '1', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_permission` VALUES ('1250300634419757076', '1250300634419757072', '用户公司表_查看', null, null, null, '2', 'system:SysUserCompany:VIEW', '14.00', 'el-icon-view', '用户公司表-详情按钮', '1', '1', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_permission` VALUES ('1250722488183799823', 'b1ca2c7f7e9b2b49a5200a7c8e1f88a9', '病症分类', '/health/HealthDisease', '0', 'health/HealthDiseaseList', '0', '', '8.00', 'el-icon-bell', '病症分类-菜单', '1', '1', 'TeaR', '2020-04-16 17:48:23', 'admin', '2020-04-16 22:33:14');
INSERT INTO `sys_permission` VALUES ('1250722488183799824', '1250722488183799823', '病症分类_新增', null, null, null, '2', 'health:HealthDisease:ADD', '11.00', 'el-icon-circle-plus-outline', '病症分类-新增按钮', '1', '1', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_permission` VALUES ('1250722488183799825', '1250722488183799823', '病症分类_修改', null, null, null, '2', 'health:HealthDisease:EDIT', '12.00', 'el-icon-edit', '病症分类-编辑按钮', '1', '1', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_permission` VALUES ('1250722488183799826', '1250722488183799823', '病症分类_删除', null, null, null, '2', 'health:HealthDisease:DELETE', '13.00', 'el-icon-delete', '病症分类-删除按钮', '1', '1', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_permission` VALUES ('1250722488183799827', '1250722488183799823', '病症分类_查看', null, null, null, '2', 'health:HealthDisease:VIEW', '14.00', 'el-icon-view', '病症分类-详情按钮', '1', '1', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_permission` VALUES ('1250722488183799828', '1250722488183799823', '病症分类_导出', null, null, null, '2', 'health:HealthDisease:EXPORT', '15.00', 'el-icon-download', '病症分类-导出按钮', '1', '1', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_permission` VALUES ('1250722488183799829', '1250722488183799823', '病症分类_导入', null, null, null, '2', 'health:HealthDisease:IMPORT', '16.00', 'el-icon-upload2', '病症分类-导入按钮', '1', '1', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_permission` VALUES ('1250791064667193360', 'eec3e8cdcf7bce53f4d38dcc443e6b62', '临床项目', '/health/HealthClinicalTest', '0', 'health/HealthClinicalTestList', '0', '', '10.00', null, '临床项目-菜单', '1', '1', 'TeaR', '2020-04-16 22:20:26', 'admin', '2020-04-17 11:34:02');
INSERT INTO `sys_permission` VALUES ('1250791064667193361', '1250791064667193360', '临床项目_新增', null, null, null, '2', 'health:HealthClinicalTest:ADD', '11.00', 'el-icon-circle-plus-outline', '临床项目-新增按钮', '1', '1', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_permission` VALUES ('1250791064667193362', '1250791064667193360', '临床项目_修改', null, null, null, '2', 'health:HealthClinicalTest:EDIT', '12.00', 'el-icon-edit', '临床项目-编辑按钮', '1', '1', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_permission` VALUES ('1250791064667193363', '1250791064667193360', '临床项目_删除', null, null, null, '2', 'health:HealthClinicalTest:DELETE', '13.00', 'el-icon-delete', '临床项目-删除按钮', '1', '1', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_permission` VALUES ('1250791064667193364', '1250791064667193360', '临床项目_查看', null, null, null, '2', 'health:HealthClinicalTest:VIEW', '14.00', 'el-icon-view', '临床项目-详情按钮', '1', '1', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_permission` VALUES ('1250791064667193365', '1250791064667193360', '临床项目_导出', null, null, null, '2', 'health:HealthClinicalTest:EXPORT', '15.00', 'el-icon-download', '临床项目-导出按钮', '1', '1', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_permission` VALUES ('1250791064667193366', '1250791064667193360', '临床项目_导入', null, null, null, '2', 'health:HealthClinicalTest:IMPORT', '16.00', 'el-icon-upload2', '临床项目-导入按钮', '1', '1', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_permission` VALUES ('1250998909288439809', '5df96be678e347e3fdab6ce43668a64f', '公告管理', '/app/AppBanner', '0', 'app/AppBannerList', '0', '', '10.00', 'el-icon-bell', 'banner&tips表-菜单', '1', '1', 'TeaR', '2020-04-17 12:06:40', 'admin', '2020-04-17 12:34:41');
INSERT INTO `sys_permission` VALUES ('1250998909288439810', '1250998909288439809', 'banner&tips表_新增', null, null, null, '2', 'app:AppBanner:ADD', '11.00', 'el-icon-circle-plus-outline', 'banner&tips表-新增按钮', '1', '1', 'TeaR', '2020-04-17 12:06:40', null, null);
INSERT INTO `sys_permission` VALUES ('1250998909288439811', '1250998909288439809', 'banner&tips表_修改', null, null, null, '2', 'app:AppBanner:EDIT', '12.00', 'el-icon-edit', 'banner&tips表-编辑按钮', '1', '1', 'TeaR', '2020-04-17 12:06:40', null, null);
INSERT INTO `sys_permission` VALUES ('1250998909288439812', '1250998909288439809', 'banner&tips表_删除', null, null, null, '2', 'app:AppBanner:DELETE', '13.00', 'el-icon-delete', 'banner&tips表-删除按钮', '1', '1', 'TeaR', '2020-04-17 12:06:40', null, null);
INSERT INTO `sys_permission` VALUES ('1250998909288439813', '1250998909288439809', 'banner&tips表_查看', null, null, null, '2', 'app:AppBanner:VIEW', '14.00', 'el-icon-view', 'banner&tips表-详情按钮', '1', '1', 'TeaR', '2020-04-17 12:06:40', null, null);
INSERT INTO `sys_permission` VALUES ('1250998909288439814', '1250998909288439809', 'banner&tips表_导出', null, null, null, '2', 'app:AppBanner:EXPORT', '15.00', 'el-icon-download', 'banner&tips表-导出按钮', '1', '1', 'TeaR', '2020-04-17 12:06:40', null, null);
INSERT INTO `sys_permission` VALUES ('1250998909288439815', '1250998909288439809', 'banner&tips表_导入', null, null, null, '2', 'app:AppBanner:IMPORT', '16.00', 'el-icon-upload2', 'banner&tips表-导入按钮', '1', '1', 'TeaR', '2020-04-17 12:06:40', null, null);
INSERT INTO `sys_permission` VALUES ('1251830627578781711', 'eec3e8cdcf7bce53f4d38dcc443e6b62', '报名处理', '/health/HealthClinicalTestSignUp', '0', 'health/HealthClinicalTestSignUpList', '0', '', '11.00', null, '临床项目报名记录-菜单', '1', '1', 'TeaR', '2020-04-19 19:11:34', 'admin', '2020-04-19 19:22:31');
INSERT INTO `sys_permission` VALUES ('1251830627578781712', '1251830627578781711', '临床项目报名记录_新增', null, null, null, '2', 'health:HealthClinicalTestSignUp:ADD', '11.00', 'el-icon-circle-plus-outline', '临床项目报名记录-新增按钮', '1', '1', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_permission` VALUES ('1251830627578781713', '1251830627578781711', '临床项目报名记录_修改', null, null, null, '2', 'health:HealthClinicalTestSignUp:EDIT', '12.00', 'el-icon-edit', '临床项目报名记录-编辑按钮', '1', '1', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_permission` VALUES ('1251830627578781714', '1251830627578781711', '临床项目报名记录_删除', null, null, null, '2', 'health:HealthClinicalTestSignUp:DELETE', '13.00', 'el-icon-delete', '临床项目报名记录-删除按钮', '1', '1', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_permission` VALUES ('1251830627578781715', '1251830627578781711', '临床项目报名记录_查看', null, null, null, '2', 'health:HealthClinicalTestSignUp:VIEW', '14.00', 'el-icon-view', '临床项目报名记录-详情按钮', '1', '1', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_permission` VALUES ('1251830627578781716', '1251830627578781711', '临床项目报名记录_导出', null, null, null, '2', 'health:HealthClinicalTestSignUp:EXPORT', '15.00', 'el-icon-download', '临床项目报名记录-导出按钮', '1', '1', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_permission` VALUES ('1251830627578781717', '1251830627578781711', '临床项目报名记录_导入', null, null, null, '2', 'health:HealthClinicalTestSignUp:IMPORT', '16.00', 'el-icon-upload2', '临床项目报名记录-导入按钮', '1', '1', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451536', '5df96be678e347e3fdab6ce43668a64f', '通用问答', '/app/AppInformation', '0', 'app/AppInformationList', '0', '', '10.00', 'el-icon-s-comment', '通用问答表-菜单', '1', '1', 'TeaR', '2020-04-20 11:53:51', 'admin', '2020-04-20 12:11:34');
INSERT INTO `sys_permission` VALUES ('1252082710953451537', '1252082710953451536', '通用问答表_新增', null, null, null, '2', 'app:AppInformation:ADD', '11.00', 'el-icon-circle-plus-outline', '通用问答表-新增按钮', '1', '1', 'TeaR', '2020-04-20 11:53:51', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451538', '1252082710953451536', '通用问答表_修改', null, null, null, '2', 'app:AppInformation:EDIT', '12.00', 'el-icon-edit', '通用问答表-编辑按钮', '1', '1', 'TeaR', '2020-04-20 11:53:51', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451539', '1252082710953451536', '通用问答表_删除', null, null, null, '2', 'app:AppInformation:DELETE', '13.00', 'el-icon-delete', '通用问答表-删除按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451540', '1252082710953451536', '通用问答表_查看', null, null, null, '2', 'app:AppInformation:VIEW', '14.00', 'el-icon-view', '通用问答表-详情按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451541', '1252082710953451536', '通用问答表_导出', null, null, null, '2', 'app:AppInformation:EXPORT', '15.00', 'el-icon-download', '通用问答表-导出按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451542', '1252082710953451536', '通用问答表_导入', null, null, null, '2', 'app:AppInformation:IMPORT', '16.00', 'el-icon-upload2', '通用问答表-导入按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_permission` VALUES ('1252082710953451547', '38b893902c20bc3be8cda64b83395d6c', '患友圈_新增', '', null, '', '2', 'app:AppInformation:ADD', '11.00', 'el-icon-circle-plus-outline', '患友圈-新增按钮', '1', '1', 'TeaR', '2020-04-20 11:53:51', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1252082710953451548', '38b893902c20bc3be8cda64b83395d6c', '患友圈_修改', '', null, '', '2', 'app:AppInformation:EDIT', '12.00', 'el-icon-edit', '患友圈-编辑按钮', '1', '1', 'TeaR', '2020-04-20 11:53:51', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1252082710953451549', '38b893902c20bc3be8cda64b83395d6c', '患友圈_删除', '', null, '', '2', 'app:AppInformation:DELETE', '13.00', 'el-icon-delete', '患友圈-删除按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1252082710953451550', '38b893902c20bc3be8cda64b83395d6c', '患友圈_查看', '', null, '', '2', 'app:AppInformation:VIEW', '14.00', 'el-icon-view', '患友圈-详情按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1252082710953451551', '38b893902c20bc3be8cda64b83395d6c', '患友圈_导出', '', null, '', '2', 'app:AppInformation:EXPORT', '15.00', 'el-icon-download', '患友圈-导出按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1252082710953451552', '38b893902c20bc3be8cda64b83395d6c', '患友圈_导入', '', null, '', '2', 'app:AppInformation:IMPORT', '16.00', 'el-icon-upload2', '患友圈-导入按钮', '1', '1', 'TeaR', '2020-04-20 11:53:52', '', '0000-00-00 00:00:00');
INSERT INTO `sys_permission` VALUES ('1252514749347282959', '2c61623c7767c4cfb8c2770449b952b7', '地图管理', '/vehicle/VehicleTestGpsMap', '0', 'vehicle/VehicleTestGpsMapList', '0', '', '1.00', null, '室外地图基础信息-菜单', '1', '1', 'TeaR', '2020-04-21 16:29:50', 'admin', '2020-04-22 12:02:21');
INSERT INTO `sys_permission` VALUES ('1252514749347282961', '1252514749347282959', '室外地图基础信息_修改', null, null, null, '2', 'vehicle:VehicleTestGpsMap:EDIT', '12.00', 'el-icon-edit', '室外地图基础信息-编辑按钮', '1', '1', 'TeaR', '2020-04-21 16:29:50', null, null);
INSERT INTO `sys_permission` VALUES ('1252514749347282962', '1252514749347282959', '室外地图基础信息_删除', null, null, null, '2', 'vehicle:VehicleTestGpsMap:DELETE', '13.00', 'el-icon-delete', '室外地图基础信息-删除按钮', '1', '1', 'TeaR', '2020-04-21 16:29:51', null, null);
INSERT INTO `sys_permission` VALUES ('1252514749347282963', '1252514749347282959', '室外地图基础信息_查看', null, null, null, '2', 'vehicle:VehicleTestGpsMap:VIEW', '14.00', 'el-icon-view', '室外地图基础信息-详情按钮', '1', '1', 'TeaR', '2020-04-21 16:29:51', null, null);
INSERT INTO `sys_permission` VALUES ('154e80d6a31e578d2eaa8c4634b3e8da', '', '系统设置', '/system', '0', '', '0', null, '10.00', 'el-icon-setting', null, '1', '1', 'admin', '2020-03-04 00:22:48', 'admin', '2020-03-31 17:45:04');
INSERT INTO `sys_permission` VALUES ('26d525cc2f53f2a82d5aa98936d40381', 'e1bbd3d6df87333f0b46b880e81b4e0c', '测试', '/test/test', '0', '/test/test', '2', null, '13.00', 'el-icon-user-solid', null, '1', '1', 'admin', '2020-02-25 14:33:52', 'admin', '2020-02-27 16:29:59');
INSERT INTO `sys_permission` VALUES ('2c61623c7767c4cfb8c2770449b952b7', '', '试车场', '/vehicle-test', '0', null, '0', null, '7.00', 'el-icon-eleme', null, '1', '1', 'admin', '2020-04-21 13:31:31', 'admin', '2020-04-21 16:54:04');
INSERT INTO `sys_permission` VALUES ('36c11c8b34db218f5fb4894e91e9a5d2', '', '开发示例', '/example', '0', null, '0', null, '14.00', 'el-icon-question', null, '1', '1', 'admin', '2020-03-31 11:48:24', 'admin', '2020-03-31 11:48:33');
INSERT INTO `sys_permission` VALUES ('38293aa9317d52dfa2ab182a8ad1f35d', '38293aa9317d52dfa2ab182a8ad1f35d', 'test', 'test/test', '0', 'test/test', '0', null, '0.00', 'el-icon-user', null, '0', '1', 'admin', '2020-02-27 16:59:25', 'admin', '2020-02-27 17:05:17');
INSERT INTO `sys_permission` VALUES ('38b893902c20bc3be8cda64b83395d6c', 'b1ca2c7f7e9b2b49a5200a7c8e1f88a9', '患友圈', '/health/information', '0', 'health/HealthInformationList', '0', null, '10.00', null, null, '1', '1', 'admin', '2020-04-20 16:28:48', 'admin', '2020-04-20 16:32:11');
INSERT INTO `sys_permission` VALUES ('3be3edf35f70bd0e8922615392240523', '2c61623c7767c4cfb8c2770449b952b7', 'GPS地图', '/vehicle/GpsMapInfo', '0', 'vehicle/GpsMapInfo', '0', null, '2.00', 'el-icon-picture-outline', null, '1', '1', 'admin', '2020-04-21 11:43:15', 'admin', '2020-04-22 12:41:59');
INSERT INTO `sys_permission` VALUES ('5224609085e98cebd3535c7453155f25', 'fcf943e4d0a36b3e4d66d047d011f2a3', '新增', null, '0', null, '2', 'test:test:ADD', '2.00', null, null, '0', '0', 'admin', '2020-03-02 16:41:13', 'admin', '2020-03-15 22:51:33');
INSERT INTO `sys_permission` VALUES ('5b1bea5d3c5b970662f190de94364216', '1230768750940712972', '角色_导出', null, '0', null, '2', 'system:SysRole:EXPORT', '15.00', 'el-icon-download', '角色_导出', '1', '1', 'admin', '2020-03-17 21:45:38', 'admin', '2020-03-17 21:47:11');
INSERT INTO `sys_permission` VALUES ('5df96be678e347e3fdab6ce43668a64f', '', 'APP通用', '/app', '0', null, '0', null, '6.00', 'el-icon-s-open', null, '1', '1', 'admin', '2020-04-17 12:07:44', 'admin', '2020-04-17 12:34:28');
INSERT INTO `sys_permission` VALUES ('64f27a6691edc85a3e2507735c3dd553', '1230768750940712972', '角色_导入', null, '0', null, '2', 'system:SysRole:IMPORT', '14.00', 'el-icon-upload2', '角色_导入', '1', '1', 'admin', '2020-03-17 21:44:39', 'admin', '2020-03-17 21:45:08');
INSERT INTO `sys_permission` VALUES ('675e20a84748ebcd23d61817d4d82183', 'a02944e79cda77f8426044775a98348a', '网易', 'https://www.163.com/', '0', null, '0', null, '0.00', 'el-icon-setting', null, '1', '1', 'admin', '2020-03-26 13:38:33', 'admin', '2020-04-03 13:55:20');
INSERT INTO `sys_permission` VALUES ('98384c71fd73ba6e421a7795525b7457', '', '消息中心', '/message', '0', '', '0', null, '9.00', 'el-icon-chat-dot-square', null, '1', '1', 'admin', '2020-03-16 14:32:09', 'admin', '2020-03-16 15:06:54');
INSERT INTO `sys_permission` VALUES ('9b67244387f654d6fe3f5b5fdcc88927', 'cc17035564771bb76b216fa2d1b7c2bd', '实时性能', '/support/monitor', '0', 'support/Monitor', '0', null, '10.00', 'el-icon-s-operation', null, '1', '1', 'admin', '2020-03-05 11:17:54', 'admin', '2020-03-05 18:51:59');
INSERT INTO `sys_permission` VALUES ('a02944e79cda77f8426044775a98348a', '', '友情外链', '/link', '0', null, '0', null, '12.00', 'el-icon-s-management', null, '1', '1', 'admin', '2020-03-26 17:24:09', 'admin', '2020-03-30 10:16:57');
INSERT INTO `sys_permission` VALUES ('b1ca2c7f7e9b2b49a5200a7c8e1f88a9', '', '多里健康', '/health', '0', null, '0', null, '7.00', 'el-icons-hospital', null, '1', '1', 'admin', '2020-04-16 17:15:23', 'admin', '2020-04-17 13:52:10');
INSERT INTO `sys_permission` VALUES ('b9f6737ceb8ac97db76e3d28a52eadab', '1230768750940712972', '角色_查看', null, '0', null, '2', 'system:SysRole:VIEW', '1.00', 'el-icon-view', '角色_查看', '1', '1', 'admin', '2020-03-17 21:36:02', null, null);
INSERT INTO `sys_permission` VALUES ('bf53dd9fd060cf81bbe858904dba3771', 'a02944e79cda77f8426044775a98348a', '百度', 'http://www.baidu.com', '0', null, '0', null, '0.00', 'el-icon-platform-eleme', null, '1', '1', 'admin', '2020-03-17 21:23:22', 'admin', '2020-03-27 14:50:30');
INSERT INTO `sys_permission` VALUES ('cc17035564771bb76b216fa2d1b7c2bd', '', '资源监控', '/support', '0', null, '0', null, '11.00', 'el-icon-s-cooperation', null, '1', '1', 'admin', '2020-03-05 18:35:55', 'admin', '2020-03-05 18:51:25');
INSERT INTO `sys_permission` VALUES ('e1bbd3d6df87333f0b46b880e81b4e0c', '26d525cc2f53f2a82d5aa98936d40381', '子菜单', 'test/test/zi', '0', 'test/test/zi', '0', null, '0.00', 'el-icon-eleme', null, '0', '0', 'admin', '2020-02-26 16:03:15', 'admin', '2020-02-27 16:22:07');
INSERT INTO `sys_permission` VALUES ('eec3e8cdcf7bce53f4d38dcc443e6b62', 'b1ca2c7f7e9b2b49a5200a7c8e1f88a9', '新药临床', '/drug-test', '0', null, '0', null, '9.00', 'el-icon-camera', null, '1', '1', 'admin', '2020-04-17 11:33:54', 'admin', '2020-04-17 11:35:32');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '租户ID',
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
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'SUPER-ADMIN', '', '-1', 'TeaR', '2020-02-12 13:56:36', 'admin', '2020-03-04 10:37:10');
INSERT INTO `sys_role` VALUES ('2', '管理员', 'ADMIN', null, '-1', 'TeaR', '2020-02-12 23:40:48', 'admin', '2020-04-08 17:12:27');
INSERT INTO `sys_role` VALUES ('202778e8c2747d68fe8b22ffadf42d3b', '2', '2', null, 'bb72c26e144fe6c9a0f5cd54a109e499', 'admin', '2020-04-16 22:14:23', null, null);
INSERT INTO `sys_role` VALUES ('5a2f1a7d9dd99f07d548ca07002e6ab1', '试车场管理员', 'Vehicle-Test-Admin', '试车场项目管理员', '-1', 'admin', '2020-04-21 13:24:56', 'admin', '2020-04-21 13:25:34');
INSERT INTO `sys_role` VALUES ('985c0bdd920309c089c200fb0cc9186a', '演示账号', 'DEMO', null, '-1', 'admin', '2020-04-21 15:37:43', null, null);
INSERT INTO `sys_role` VALUES ('d5fb72db8404d2eb6599b3ecc2090446', '多里健康管理员', 'Health-Admin', '多里健康管理员', '-1', 'admin', '2020-04-16 17:27:45', 'admin', '2020-04-21 13:25:22');

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
INSERT INTO `sys_role_permission` VALUES ('00b7e207560a159e25d69d3336b687f5', '202778e8c2747d68fe8b22ffadf42d3b', '1231177799351967764', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('00f0d4428b40bba63598067c700222f7', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386258', 'admin', '2020-04-16 17:28:58', null, null);
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
INSERT INTO `sys_role_permission` VALUES ('030fc14743d6c48edf9c609e3f770fbe', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386256', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('03843a44f5a7df9250503b99f255458d', '2', '5df96be678e347e3fdab6ce43668a64f', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('03a0138cffd6c5e49368f0a812b87f84', '2', '1227815647354331148', 'admin', '2020-03-17 21:59:04', null, null);
INSERT INTO `sys_role_permission` VALUES ('04727a6ae149aa0e2207c5ba2e4e4a01', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386256', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('05daaff3639f9fb485d01d8260c269b3', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439810', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('072d59750d5c1479cecdef5aba0d677f', '985c0bdd920309c089c200fb0cc9186a', 'b9f6737ceb8ac97db76e3d28a52eadab', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('0873ef0b9342920712540618a21be3a8', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230768750940712975', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('08c428c332fa625f2bf34edae804df24', 'd5fb72db8404d2eb6599b3ecc2090446', '1239531890134290447', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('093c7a3fad151b9c4799dea00d09e963', 'd5fb72db8404d2eb6599b3ecc2090446', '64f27a6691edc85a3e2507735c3dd553', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('0ae642567aae72add2241c21ceacf82b', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386261', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('0b37da9dbbd820be3dfbffa272eac7dc', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439814', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('0b965efd2f10a78222e7582150100d46', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338319', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('0c6677ae56ea8ae612c02060e8aa2c2c', '985c0bdd920309c089c200fb0cc9186a', '1244931937098338323', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('0d83a7a542204fae28e0c4116704839b', 'd5fb72db8404d2eb6599b3ecc2090446', '1239531890134290453', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('0e441e06bf820fe4b20fe9f0237717cb', 'd5fb72db8404d2eb6599b3ecc2090446', '154e80d6a31e578d2eaa8c4634b3e8da', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('0e5d127030cd089ad8a3d9cc8e7c98d6', 'd5fb72db8404d2eb6599b3ecc2090446', '1232984097794093071', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('0fc01025c43ae18341656baf29aba3c8', '1', '1227815647354331143', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('11b4ff016b752d4a0ade9993ae8fb985', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1232984097794093074', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('11cb8ba8626d3343fe6704315e66c078', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386259', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('121d97ff3be385a6ef96e4dce4ce2312', '985c0bdd920309c089c200fb0cc9186a', '1250998909288439809', 'admin', '2020-04-21 15:39:19', null, null);
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
INSERT INTO `sys_role_permission` VALUES ('1246748197888012311', '2', '1246748197888012304', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1246748197888012312', '2', '1246748197888012305', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1246748197888012313', '2', '1246748197888012306', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1246748197888012314', '2', '1246748197888012307', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1246748197888012315', '2', '1246748197888012308', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1246748197888012316', '2', '1246748197888012309', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1246748197888012317', '2', '1246748197888012310', 'TeaR', '2020-04-05 18:36:08', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386262', '2', '1250259609349386255', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386263', '2', '1250259609349386256', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386264', '2', '1250259609349386257', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386265', '2', '1250259609349386258', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386266', '2', '1250259609349386259', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386267', '2', '1250259609349386260', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250259609349386268', '2', '1250259609349386261', 'TeaR', '2020-04-15 11:08:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250300634419757079', '2', '1250300634419757072', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250300634419757081', '2', '1250300634419757074', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250300634419757082', '2', '1250300634419757075', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250300634419757083', '2', '1250300634419757076', 'TeaR', '2020-04-15 13:54:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799830', '2', '1250722488183799823', 'TeaR', '2020-04-16 17:48:23', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799831', '2', '1250722488183799824', 'TeaR', '2020-04-16 17:48:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799832', '2', '1250722488183799825', 'TeaR', '2020-04-16 17:48:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799833', '2', '1250722488183799826', 'TeaR', '2020-04-16 17:48:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799834', '2', '1250722488183799827', 'TeaR', '2020-04-16 17:48:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799835', '2', '1250722488183799828', 'TeaR', '2020-04-16 17:48:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250722488183799836', '2', '1250722488183799829', 'TeaR', '2020-04-16 17:48:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193367', '2', '1250791064667193360', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193368', '2', '1250791064667193361', 'TeaR', '2020-04-16 22:20:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193369', '2', '1250791064667193362', 'TeaR', '2020-04-16 22:20:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193370', '2', '1250791064667193363', 'TeaR', '2020-04-16 22:20:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193371', '2', '1250791064667193364', 'TeaR', '2020-04-16 22:20:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193372', '2', '1250791064667193365', 'TeaR', '2020-04-16 22:20:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('1250791064667193373', '2', '1250791064667193366', 'TeaR', '2020-04-16 22:20:27', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781718', '2', '1251830627578781711', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781719', '2', '1251830627578781712', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781720', '2', '1251830627578781713', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781721', '2', '1251830627578781714', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781722', '2', '1251830627578781715', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781723', '2', '1251830627578781716', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1251830627578781724', '2', '1251830627578781717', 'TeaR', '2020-04-19 19:11:35', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451543', '2', '1252082710953451536', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451544', '2', '1252082710953451537', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451545', '2', '1252082710953451538', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451546', '2', '1252082710953451539', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451547', '2', '1252082710953451540', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451548', '2', '1252082710953451541', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252082710953451549', '2', '1252082710953451542', 'TeaR', '2020-04-20 11:53:52', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252514749347282966', '2', '1252514749347282959', 'TeaR', '2020-04-21 16:29:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252514749347282968', '2', '1252514749347282961', 'TeaR', '2020-04-21 16:29:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252514749347282969', '2', '1252514749347282962', 'TeaR', '2020-04-21 16:29:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('1252514749347282970', '2', '1252514749347282963', 'TeaR', '2020-04-21 16:29:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('12a79869e783bb0d757eb47d1c1c1133', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612624', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('14727decd642121e901732711cdb1099', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338319', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('1584d150847b110ce7122344a3959fa1', '985c0bdd920309c089c200fb0cc9186a', '1239437792124612623', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('15ac938dc15a4abdb1e6bcd815666de6', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439812', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('19226949eb16dd1c5a440a655262120c', '1', '1227815647354331147', 'admin', '2020-04-14 17:17:41', null, null);
INSERT INTO `sys_role_permission` VALUES ('1a5180a7ce31459eebdcbd7384b71183', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338320', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('1abb4a421faa1bcb9a6de198d7c4ba8f', '202778e8c2747d68fe8b22ffadf42d3b', '1250300634419757075', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('1e1ed3aed73fbe52dcd8f97f6c929f40', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338322', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('1e7fb1048598f88f2a72a58b3e1d3173', '5a2f1a7d9dd99f07d548ca07002e6ab1', 'faa6e3006784bfba0d467cd9b6452a5b', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('206f7e2053894af50b0593503b63eecd', '2', '1231177799351967764', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('21c03d01236e612162efa47926c258c7', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799824', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('22bacfa04bfca6dd66894eff4ac4ef71', 'd5fb72db8404d2eb6599b3ecc2090446', '1250300634419757072', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('23ba190ebd3e2250445014b232195d08', '202778e8c2747d68fe8b22ffadf42d3b', 'eec3e8cdcf7bce53f4d38dcc443e6b62', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('279694d0cbeebb7d89639610853d2e67', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008208', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('27a07ecc843f5c3cc6e14215a1c3bfac', 'd5fb72db8404d2eb6599b3ecc2090446', 'faa6e3006784bfba0d467cd9b6452a5b', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('2872e9c9096eb926e1742bff7f5c4e61', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781712', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('28f868e893f4bd800e966e22a5cb38e0', '202778e8c2747d68fe8b22ffadf42d3b', '1232984097794093071', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('298c9ca430bd537105164536405759dd', '2', '1230085853179052041', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('2bd4a55d8b87ad98b4f5be2bb5d200b0', '5a2f1a7d9dd99f07d548ca07002e6ab1', '5b1bea5d3c5b970662f190de94364216', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('2cca23232e000980876529520b720471', 'd5fb72db8404d2eb6599b3ecc2090446', '1230085853179052040', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('2d1652a68d394806d15870f1a2d9dfab', '202778e8c2747d68fe8b22ffadf42d3b', '1239531890134290449', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('2e8abe99f53f0af07a3d85de86c6322f', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612625', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('2ffe9ebfd1203461cbf74a13b0f654a2', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008209', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('30666f2ae8c80a64ceb99677bbe89eca', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612629', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('308e1f41306687d57c721e9d54b8466b', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781713', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('3133eb7b397db62cb4def3d32e54741f', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799826', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('31a36beaf79b0484bad40b46115558ac', '1', '1227815647354331146', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('31feab9a0b5cda90d03f771ffd0bea42', '1', '1228186789386715144', 'admin', '2020-04-14 17:18:28', null, null);
INSERT INTO `sys_role_permission` VALUES ('32b21173b7930c7f92b239f57336e545', '1', '1228186789386715143', 'admin', '2020-04-14 17:18:28', null, null);
INSERT INTO `sys_role_permission` VALUES ('3324af4d76211a74fa0a789f108e80d4', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386261', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('35a2be872590d622779b93296b2c0959', '202778e8c2747d68fe8b22ffadf42d3b', 'faa6e3006784bfba0d467cd9b6452a5b', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('35cd1a3e5a2839040d863fee9934493d', '2', 'eec3e8cdcf7bce53f4d38dcc443e6b62', 'TeaR', '2020-04-17 11:42:48', null, null);
INSERT INTO `sys_role_permission` VALUES ('35e21e48292b20ffd5ea24254f8357da', '985c0bdd920309c089c200fb0cc9186a', '1250300634419757076', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('36c3fea1af735e0e357976664ffa3d45', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193364', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('37905b05c64a4dc154840397b9095bdf', '2', '38b893902c20bc3be8cda64b83395d6c', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('37a88f01426c99eb63699d4c069bd53d', '985c0bdd920309c089c200fb0cc9186a', '1243420115904008208', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('385b774654b1ea34fc85bc55eeb76a95', '202778e8c2747d68fe8b22ffadf42d3b', '1230768750940712972', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('3886b6a39602823b208d612a190c4c03', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193361', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('3897dcf7c6025985de86d404231fb7a3', '985c0bdd920309c089c200fb0cc9186a', '1252082710953451536', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('38fdb7a634ec33caabe16c37af0fbead', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781714', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('3a64b3b1ad63feacc920d6400268f259', 'd5fb72db8404d2eb6599b3ecc2090446', '1250300634419757074', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('3a8a1cfebbf011ec4fcf739977045d97', 'd5fb72db8404d2eb6599b3ecc2090446', 'b1ca2c7f7e9b2b49a5200a7c8e1f88a9', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('3b11482344ec7987a106229d0d2a31ca', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799828', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('3b9ac23aa5527809b912e6c379a07ea2', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781711', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('3c06a2f0b994b12265cd824adc19c4cc', '985c0bdd920309c089c200fb0cc9186a', '1239531890134290453', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('3d3c8b666612e4305ab6089be9de9ca5', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612625', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('405033130a396be2cd581bd69ed902be', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008214', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('4067e4c59c32f8e4dbab5f2ef106a01f', '1', '1227815647354331145', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('418797246a746457e3005a9778ee0542', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338321', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('428d33ee997fcb1cc1fd5d104e706d07', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193365', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('446d11e1ffc3b5428cab145e2a46be1d', '985c0bdd920309c089c200fb0cc9186a', '1235980066630438927', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('44833e7317386bb1dff2f483ad06ab62', 'd5fb72db8404d2eb6599b3ecc2090446', '1239531890134290449', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('4550e3c686142c6bf1c8d36d865e5d85', '202778e8c2747d68fe8b22ffadf42d3b', '1230768750940712974', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('4588de9f74175237f5f9b681a3f94f59', 'd5fb72db8404d2eb6599b3ecc2090446', '1230085853179052042', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('45c2aac80bebfccd936ab8fd23147a7c', '202778e8c2747d68fe8b22ffadf42d3b', '1250300634419757074', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('4651743c046c4a8c8f92fcf8b34dc42f', '985c0bdd920309c089c200fb0cc9186a', '1243420115904008212', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('468a99ff5d91688b2028c00943de4ebb', '1', '1232984097794093071', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('4afb58a13dada112e107ff0cb0b1ae9e', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612627', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('4b34468d59ad0691b79698e312917ace', '2', '1250998909288439815', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('4cbeaf1801a82fe7ed4a16b2092e3522', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008212', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('4df67bac30958e0f33f5392be389307a', '2', '1231177799351967765', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('4e7ac03a2276c1892d2008ca05e2f042', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008214', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('4eacc2fd3b1e99d65fe8bb61aa8002ad', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008209', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('4ee1ba6b246b0234ece70fd18c437dbe', '2', '675e20a84748ebcd23d61817d4d82183', 'admin', '2020-03-26 13:38:41', null, null);
INSERT INTO `sys_role_permission` VALUES ('4fb26ca85361bdca4b82758643bcac66', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338322', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('504805bd2902810a2f2c4e5ae5bd12b0', '2', '1252082710953451549', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('509d618ffee3dc2a5bd1cb5b0ad6ce06', '2', '1250998909288439814', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('522a808766bbcba580f45aa9f354ccbe', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338324', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('52f70789f736a95b8e433cd576fc70cb', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1239531890134290453', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('530770d638632eda7d02faaf169f50d1', '2', '1252082710953451548', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('535341c36e40a75d31d0224ff039baf2', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338321', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('53f0f66a117035efed94ea15151221bc', '985c0bdd920309c089c200fb0cc9186a', '1227815647354331142', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('54271231b9dcc8692e7875b63933f34d', '1', 'b9f6737ceb8ac97db76e3d28a52eadab', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('5451a0fb4ce4acd63d6cae06275b7f94', '985c0bdd920309c089c200fb0cc9186a', '675e20a84748ebcd23d61817d4d82183', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('54e3ca06adf72a11bd81e8176bc9b5d3', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230085853179052041', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('55a8c6ed43e31a1c53ec36a8b89c60c0', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781717', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('56b3b76e86f4ac12f196236b4734b89c', '2', '1239437792124612623', 'admin', '2020-03-17 20:39:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('56b63200354d0246748e68ceaa189ded', 'd5fb72db8404d2eb6599b3ecc2090446', 'cc17035564771bb76b216fa2d1b7c2bd', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('56b831d924ef0500706576cb33558c17', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439811', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('5790885dbc0ece3aeb7c0c39ee0f6031', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615248', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('57d956dc2ec2c056f95837f4963e5dc9', '5a2f1a7d9dd99f07d548ca07002e6ab1', '2c61623c7767c4cfb8c2770449b952b7', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('58a90381e82e9d3a07628cd24e66e8e4', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230085853179052039', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('58dab858a6f756e233c5412e3a21b1c4', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008208', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('59d12fb497ce25fe89c90f6038039a75', '202778e8c2747d68fe8b22ffadf42d3b', '1230085853179052040', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('5b06eae304ac351db0aa50057423d5bb', 'd5fb72db8404d2eb6599b3ecc2090446', '5df96be678e347e3fdab6ce43668a64f', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('5bcdb859a5fe2dc5d24e606fee153651', '5a2f1a7d9dd99f07d548ca07002e6ab1', '154e80d6a31e578d2eaa8c4634b3e8da', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('5ca52ee071ba3e39d0386cbc7b889e95', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008210', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('5cc90503e3c79867854aaec60f1dee2c', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781717', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('5f5bb74c502a5420f4cba6af8165d8db', '5a2f1a7d9dd99f07d548ca07002e6ab1', 'b9f6737ceb8ac97db76e3d28a52eadab', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('606572078bab86342d9ba724d4f3d52b', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230085853179052043', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('6077d2fdd2f3dc15a694b4f68e295b95', '202778e8c2747d68fe8b22ffadf42d3b', '1230085853179052041', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('61555150453e86ca52de596190e76545', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1232984097794093071', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('61f8577dc9fcf03acb14160f7bf0bc1c', '985c0bdd920309c089c200fb0cc9186a', '1235980066630438931', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('6258991cb3f0adb08df134a501aa54ae', 'd5fb72db8404d2eb6599b3ecc2090446', '98384c71fd73ba6e421a7795525b7457', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('638af6d92d584155a0483eaf0d1ac84f', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439811', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('65121d9c6e4b8aaf68eecc529cce94da', 'd5fb72db8404d2eb6599b3ecc2090446', '1231177799351967764', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('659f32b831a35ff36fb52d256c14a450', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439809', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('65c7c3c7003b0e7f7a9c15c1a15d2729', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799825', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('65d6ab5de0e0df060e251e0446b6d447', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799827', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('671afffc76ec1d1ce621d154bcb37fe8', '202778e8c2747d68fe8b22ffadf42d3b', 'b1ca2c7f7e9b2b49a5200a7c8e1f88a9', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('676e428587ed15a2cab8129d8cd5c6ab', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439813', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('684098f765686fe5cdb1c6df0b10ee2b', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338320', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('68d8c77558c0ebff123e09529a1c4207', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439813', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('6a1b4c6167f08827eb8be3d516bdc852', '1', '1230768750940712975', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('6ac89e4b01219ed45f5b7861242495d8', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008208', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('6b0badde896707a1500150d91ad51fd9', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799824', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('6c28ed049a427b0768c770561587d373', '2', '1250998909288439813', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('6d3189900411c89f2664b4f614febb00', '1', '1239531890134290453', 'admin', '2020-03-17 20:45:48', null, null);
INSERT INTO `sys_role_permission` VALUES ('6d97e82c691bb40e7747bf2a9cd9ee65', '202778e8c2747d68fe8b22ffadf42d3b', '98384c71fd73ba6e421a7795525b7457', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('700246e76857de25deead014cd3ecc88', '202778e8c2747d68fe8b22ffadf42d3b', '5df96be678e347e3fdab6ce43668a64f', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('719b5da20474b8b72136c09a109c4e1b', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612628', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('725a05eeca6b90efc82ad5c8738233c3', '985c0bdd920309c089c200fb0cc9186a', '1250998909288439813', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('726e78b3128025d6d78650dcaafd4cbe', '985c0bdd920309c089c200fb0cc9186a', '1231551481274441743', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('7364beea62edef55be0ed6740e70c816', '2', '1252082710953451550', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('738b8b1beb6b8dd0e5a34966b509126d', '202778e8c2747d68fe8b22ffadf42d3b', '1250300634419757076', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('7460cf20f3e28171beb4d4f9f6666d48', '985c0bdd920309c089c200fb0cc9186a', '1239437792124612627', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('74fa05756c9a1f9dcafef0fc61ce5d92', '2', '1230085853179052040', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('759122c01c1c2edf0ff2317077a75a95', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612629', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('78fd5c260fc5206b0a3f8fdbd3da0e7a', '2', '1250998909288439811', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('792977664dfe601825c52b3f2fd09b1b', '2', '36c11c8b34db218f5fb4894e91e9a5d2', 'admin', '2020-03-31 11:49:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('796ec89470ca275ab663d4960247a3d2', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799823', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('7a6effaff31798340f40c639c3a27e0e', '2', '64f27a6691edc85a3e2507735c3dd553', 'admin', '2020-03-17 21:46:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('7bc42777c31f7c2e9964fa229c00b76f', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781716', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('7c0654403f8bbb438f4501113294e2c0', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612623', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('7c9cbfa8dfccea20627e33a8d469f70a', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781716', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('7e48a87d498cb59e40c48b8189e67ce0', 'd5fb72db8404d2eb6599b3ecc2090446', '1232984097794093074', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('7e74579d4e6299371d4faca0ac865ded', '1', '154e80d6a31e578d2eaa8c4634b3e8da', 'admin', '2020-04-14 17:17:41', null, null);
INSERT INTO `sys_role_permission` VALUES ('7fb5ac863751e4cf0fd0831cc987aeef', '202778e8c2747d68fe8b22ffadf42d3b', '1232984097794093075', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('7fc11df95139b7963830dc8b62df79e9', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193363', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('806279f1e31c0de5c725b3aec35bff82', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1232984097794093075', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('8068c6279909de5bb2149f8b89b1ff6a', '985c0bdd920309c089c200fb0cc9186a', '1244931937098338319', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('81628f20897dcad6d8adbb9b6ca0c5c8', '2', '1252082710953451547', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('81ebbf9f467e12c0e4769ef41cfffe9a', '985c0bdd920309c089c200fb0cc9186a', '1250300634419757072', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('83f126bafd23f474c4cb5beb1d72454e', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1239531890134290449', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('8412d766b34096a0bae16547c86a106e', '985c0bdd920309c089c200fb0cc9186a', 'bf53dd9fd060cf81bbe858904dba3771', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('8505fb41bc9fbbbdcd4e87b798d4492e', '1', '1230768750940712974', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('8581628d02bae3b484e30c25cac8c88a', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612626', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('869ba1147f00d9ea29ccd4da9da9a287', '2', '1250998909288439809', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('87d81df47c91c91c009fbaf3ed9e1249', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386259', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('883cb30a5d9397c2d37fe4cf28fdf0c5', 'd5fb72db8404d2eb6599b3ecc2090446', '1230085853179052043', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('888936dd1d2322570a8d5b98675e9b79', 'd5fb72db8404d2eb6599b3ecc2090446', '1230085853179052039', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('897212c4ff2780ff9c4dac21a9034b19', '985c0bdd920309c089c200fb0cc9186a', '1231551481274441747', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('89fd525f53af5369d2135e3108c2f2e2', '202778e8c2747d68fe8b22ffadf42d3b', '154e80d6a31e578d2eaa8c4634b3e8da', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('8a4e4bd1382e7056acf51603d0d7d921', '1', '1230768750940712972', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('8d26a317a02ced3b4171cacbe78a1ed8', '985c0bdd920309c089c200fb0cc9186a', '9b67244387f654d6fe3f5b5fdcc88927', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('8d6c6fe93c35594309acf190d7add3a7', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193365', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('8d70ceee20e29e28026e6209dcab3e51', '1', '1239531890134290449', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('8dcde5b3fc6b9c0ec9e74b32e9505dcc', '2', '5b1bea5d3c5b970662f190de94364216', 'admin', '2020-03-17 21:46:26', null, null);
INSERT INTO `sys_role_permission` VALUES ('8dd91ca528f65803096f3a774f4e9595', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799825', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('8e1ff3d5a6a3517d45872995fd16d89f', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615250', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('8e39976813d0c2778a87e4fad04d1cf6', '1', '1227815647354331148', 'admin', '2020-04-14 17:17:41', null, null);
INSERT INTO `sys_role_permission` VALUES ('8e4fdfd904afadd762f50d1d339bc83d', '985c0bdd920309c089c200fb0cc9186a', '1230768750940712972', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('9023534768220ccfe03fc197b3af39f6', '1', '64f27a6691edc85a3e2507735c3dd553', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('903faa1938dfc6187689f6196b173fc0', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008214', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('906536b96cefca3a43888bf28ed85481', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439810', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('90fd2d166794c64dc1ba501ec120950c', '985c0bdd920309c089c200fb0cc9186a', '1230085853179052043', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('924d2a92d3e36489ad3a2933b4dd2b1b', '202778e8c2747d68fe8b22ffadf42d3b', '1231177799351967765', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('929c33e018cf7f4aba3cc4c7106f0d09', '985c0bdd920309c089c200fb0cc9186a', '3be3edf35f70bd0e8922615392240523', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('95e5a49d05b7401d298cf7a6e929d8e8', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799829', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('96c3054551364e26bcc46998bac732ee', '1', '1232984097794093074', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('9801af048521ba40a44b3f53b0ae1044', 'd5fb72db8404d2eb6599b3ecc2090446', '1230768750940712974', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('9c6129da9f34f9e5a2cd41095660d8fd', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386255', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('9c95643b56cb7814a96db68fec79d120', '202778e8c2747d68fe8b22ffadf42d3b', '1230085853179052039', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('a1b6b5822d8e310f1d5bebf0f2e3f889', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612623', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('a231fffd309de4a362fca648ee7fa531', '985c0bdd920309c089c200fb0cc9186a', '1230085853179052039', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('a24f864f37ec70d10c8d865e8fea6f99', '5a2f1a7d9dd99f07d548ca07002e6ab1', '9b67244387f654d6fe3f5b5fdcc88927', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('a27d820194ba01b026ddebd0ef93ed69', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193366', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('a399918ef1cbf362b63593ba794a0015', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386260', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('a3a0469a7a2fc0f4fdae10034b109bbf', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781712', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('a55ecd363a560d6e07efdf09e7013ca0', '202778e8c2747d68fe8b22ffadf42d3b', '1230085853179052043', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('a5ef30c790a42a3e32e57d251b0c8259', '985c0bdd920309c089c200fb0cc9186a', '1239531890134290447', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('a621108935fcb62e692fc4b5a3f5fa95', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386255', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('a661706cf82e7cd69f9e06909cd6fbb5', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193361', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('a663899aec41d451d862eeb3f1ec78be', '985c0bdd920309c089c200fb0cc9186a', '1252082710953451540', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('a83b4878d75de9f09b3dcc80ae6c85e6', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439815', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('a83c08a91c095b12d692c763c7095909', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193360', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('a92dd31a97a993e7a50cee026abd3d54', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386258', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('aa4e882b90e1ccca84c9a36960d4cf22', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781714', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('abb1a3b0d6967e4f6a609b0aed238db3', '985c0bdd920309c089c200fb0cc9186a', '1250259609349386259', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('acab97fea93aefab770c6302146cb11c', '2', '1227815647354331147', 'admin', '2020-03-17 21:59:04', null, null);
INSERT INTO `sys_role_permission` VALUES ('ad63f79f1253fb40afcd5a3ccd8db646', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193362', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('ad6efd6100adf274f05994025d99e609', '1', '1232984097794093075', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('af619107dcceff62cb5e847fe3f28ae7', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1239531890134290447', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('af6e6e8e10fa6229b0ddce8ef3951b5a', 'd5fb72db8404d2eb6599b3ecc2090446', '1231177799351967765', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('b01e4539d5ae3e8e4496c1b4ce3b8cdc', '985c0bdd920309c089c200fb0cc9186a', '36c11c8b34db218f5fb4894e91e9a5d2', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('b02dca37549ca689847e71791864847b', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439812', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('b04e8971ebd834d7ff7aba8e2e2024f6', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008211', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('b05f8269278c27cdedc16676f608515a', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230768750940712974', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('b202fd2f1fdae5e8b1ba2ba8ebf49304', 'd5fb72db8404d2eb6599b3ecc2090446', '1250300634419757075', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('b2fe266d2ac1a36ec5bd9a02edb7ba54', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615253', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('b31e0c699768a3a5692fa8e8990cb72a', '5a2f1a7d9dd99f07d548ca07002e6ab1', 'cc17035564771bb76b216fa2d1b7c2bd', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('b3af415b5b667da438fb9458bfa7ee95', '985c0bdd920309c089c200fb0cc9186a', '1238371494773977103', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('b41418343fbcd040b9bf7e251decd96e', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338325', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('b6303550eec6402c6b289e5a71b571b9', '1', '1239531890134290447', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('b6cbf7f381e038caa2286caa8f4b298a', '2', 'b1ca2c7f7e9b2b49a5200a7c8e1f88a9', 'admin', '2020-04-16 17:21:18', null, null);
INSERT INTO `sys_role_permission` VALUES ('b6ed6f69563ae913895cb4c4705767b1', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008210', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('b728ee11e1053c160ed423b2629db303', '202778e8c2747d68fe8b22ffadf42d3b', '1', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('b966e3598fce7ecc1167b5516b4b94c1', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615254', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('b9f6737ceb8ac97db76e3d28a52eadab', '2', 'b9f6737ceb8ac97db76e3d28a52eadab', 'TeaR', '2020-03-17 21:23:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('bb65ec148b68dd66ae39791ed2cd620d', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008213', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('bb7f9773f7e95cea91f3197cd7f81f7f', '2', '1250998909288439812', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('bbbf5970f287bdac8e6fd3f460f485ac', '202778e8c2747d68fe8b22ffadf42d3b', '1230768750940712975', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('bc53cb959f34ed55543ba50539c36250', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008211', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('bc8edaee2ddb3ac890450b126d2f23b5', 'd5fb72db8404d2eb6599b3ecc2090446', '1250259609349386257', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('be95d2c6cccb55afcb95f9ef63baa826', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008213', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('bfdadfb5d48738785d89eee68c329e18', 'd5fb72db8404d2eb6599b3ecc2090446', '5b1bea5d3c5b970662f190de94364216', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('c090f2a19a3000ec0818b80350e1e683', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615252', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('c141f35ff963875d17c1f742f3dfcfd4', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230768750940712972', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('c1aaa8beef0b3077734615283484e70a', '1', '5b1bea5d3c5b970662f190de94364216', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('c202bb77b177673c271164702c32da65', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439815', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('c361571bff5bf39255b44ec827119b88', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781715', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('c3ff818f4958b20d294d3efd22635248', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193366', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('c51088f70dbd250fb1fce1ecd3ddbc90', 'd5fb72db8404d2eb6599b3ecc2090446', '1232984097794093075', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('c6a180f4f63ac6474b7eaaf13def7c63', '1', '1228186789386715145', 'admin', '2020-04-14 17:18:28', null, null);
INSERT INTO `sys_role_permission` VALUES ('c6d49d4d6bfc73926edd518e606962a9', '202778e8c2747d68fe8b22ffadf42d3b', '1250998909288439809', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('c7cbcfa7c5303af0cef8043b90ca3e89', '985c0bdd920309c089c200fb0cc9186a', '1232984097794093071', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('c9430f47c97c70001a568564457c9859', '985c0bdd920309c089c200fb0cc9186a', '1250259609349386255', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('c96db9fcc63f4b5cf904e5e2093b4098', '985c0bdd920309c089c200fb0cc9186a', '1232984097794093075', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('c9f73e5ec960eb10a786c78227fe3295', '2', '1250998909288439810', 'admin', '2020-04-17 13:57:06', null, null);
INSERT INTO `sys_role_permission` VALUES ('ca5773a9d85dc65fc122118063fe4f20', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008211', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('cb8e45cf1320f0b7c16c52034a975d34', 'd5fb72db8404d2eb6599b3ecc2090446', '1243420115904008212', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('cbd2df7db3fbb445b26c9bf90d1a7b53', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230085853179052040', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('cbfe78b49861f76fb6924ccc310973e5', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799827', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('ccb852bc96dfd7e28fce570b5f92a5f6', 'd5fb72db8404d2eb6599b3ecc2090446', 'b9f6737ceb8ac97db76e3d28a52eadab', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('cd71f88c3f5197507af9cb9a5fd6bbdf', '202778e8c2747d68fe8b22ffadf42d3b', '64f27a6691edc85a3e2507735c3dd553', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('cdcfb671e72c05fd70d2c68aed5d42dc', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615249', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('cddfaf27258a080db0c7ef2172375ccc', '985c0bdd920309c089c200fb0cc9186a', '1244832822171615251', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('d0884e54c35731ba61849e900b81abd7', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193364', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('d24a26fe9215cbd2e94460ef115ac0a3', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338323', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('d36e9a5700ccb886ddd8688cc3d141a1', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799829', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('d3ded0c45cc633210e79ba5edfdff1e3', '2', '1252082710953451552', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('d44da5478568cd591d58ea1fca31be32', '985c0bdd920309c089c200fb0cc9186a', '1238371494773977107', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('d588b9bb6a9d0776487e2eb5a89efb02', '202778e8c2747d68fe8b22ffadf42d3b', '1250722488183799828', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('d674a8e46a9b294ed690acaa307498d7', '202778e8c2747d68fe8b22ffadf42d3b', '1239531890134290447', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('d71fac0d03a8f28cf52f4ded002c2acd', '202778e8c2747d68fe8b22ffadf42d3b', '1239531890134290453', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('d76613e046a343ab4924f9d1d561d517', '202778e8c2747d68fe8b22ffadf42d3b', 'b9f6737ceb8ac97db76e3d28a52eadab', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('d77fa65719f1425cb2c3b32814911e60', 'd5fb72db8404d2eb6599b3ecc2090446', 'eec3e8cdcf7bce53f4d38dcc443e6b62', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('d83a6eeadc838b51933a95b2e9e9b2d9', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799823', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('d8655673565a5f67425296aeeef02315', '5a2f1a7d9dd99f07d548ca07002e6ab1', '64f27a6691edc85a3e2507735c3dd553', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('da62591a82186fd0fe7e95c4b11e3c35', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612626', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('db678a9ec084d46cc37cad85eee443ed', '985c0bdd920309c089c200fb0cc9186a', '5df96be678e347e3fdab6ce43668a64f', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('dbe617206f69406887d91d6aa8c85460', '202778e8c2747d68fe8b22ffadf42d3b', 'cc17035564771bb76b216fa2d1b7c2bd', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('dc8f795e0104e2ccceec39ec186a0655', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008209', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('dce3a1a9298e2438bf4b8798dd1b3fda', 'd5fb72db8404d2eb6599b3ecc2090446', '1251830627578781713', 'admin', '2020-04-20 10:53:12', null, null);
INSERT INTO `sys_role_permission` VALUES ('ddc43ba267bbc707ac314cd42d681a30', '1', 'faa6e3006784bfba0d467cd9b6452a5b', 'admin', '2020-04-14 17:17:11', null, null);
INSERT INTO `sys_role_permission` VALUES ('dfcb82c11d747cf4397703ce5e1cafcf', '985c0bdd920309c089c200fb0cc9186a', '1227815647354331146', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('dfe9b86c9b51d07688db29b9d53faa60', '202778e8c2747d68fe8b22ffadf42d3b', '1230085853179052042', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('e0d957c55345747cf1e2762fe609e428', '1', '1227815647354331142', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('e1050d5e9e79b5e7207d7d08ab76bd65', '2', '1230085853179052042', 'admin', '2020-03-17 20:45:03', null, null);
INSERT INTO `sys_role_permission` VALUES ('e2b79559efe6de2250d44d6d1c4d8a64', 'd5fb72db8404d2eb6599b3ecc2090446', '1250300634419757076', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('e326d0081a437d669ed27db9d3317607', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338324', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('e360134593509b3c5f07fdc2dd736a6b', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193360', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('e3c254f9a5a1034c88a186396d55209b', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781715', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('e3fb0d51eed5f21356099fb3ac6413aa', '985c0bdd920309c089c200fb0cc9186a', 'a02944e79cda77f8426044775a98348a', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('e40ce352882ddcb0ee84db49ae6c7286', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612627', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('e49a4ae8fa1ec2be781f69936fae1bf4', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008213', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('e50c16a54c010fb58a7c6bfe263fc1e1', 'd5fb72db8404d2eb6599b3ecc2090446', '1250998909288439814', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('e514986a4c8fa4fe07d6b40867b87e26', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1243420115904008210', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('e7a8ac7cd4828c2d7eb5ae31b63206cd', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1230085853179052042', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('e7b04550b2d7139f73951063f446122d', 'd5fb72db8404d2eb6599b3ecc2090446', '1230768750940712975', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('ecb5cc5f1fc48f51763eaba6bc340481', '1', '1227815647354331144', 'admin', '2020-03-17 17:17:51', null, null);
INSERT INTO `sys_role_permission` VALUES ('ed72b7cd1e5ddb732c4d84b074bd4a40', 'd5fb72db8404d2eb6599b3ecc2090446', '1250791064667193362', 'admin', '2020-04-17 17:03:40', null, null);
INSERT INTO `sys_role_permission` VALUES ('ee7a361c5886f5d7c496f4eb7eafae4b', '985c0bdd920309c089c200fb0cc9186a', '98384c71fd73ba6e421a7795525b7457', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('ee86279ac4a3d6d95d8bb484d75f459e', 'd5fb72db8404d2eb6599b3ecc2090446', '1230768750940712972', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('f02d32af539d5e37102bef4ed63988eb', '985c0bdd920309c089c200fb0cc9186a', 'cc17035564771bb76b216fa2d1b7c2bd', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('f13fbdcb0ec612fce7a1e5e4da851c88', '1', '1228186789386715146', 'admin', '2020-04-14 17:18:28', null, null);
INSERT INTO `sys_role_permission` VALUES ('f18ca3b28f5cbc57556d2c9f84a4161f', '2', '1252082710953451551', 'admin', '2020-04-20 16:38:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('f2cb4d7b59d06a79dbca89cb6f473e38', '202778e8c2747d68fe8b22ffadf42d3b', '1250300634419757072', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f360b588876f3e8fb8232cd08e1cdd55', '202778e8c2747d68fe8b22ffadf42d3b', '1244931937098338325', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f4b5b4f459316239a773a2d161bd0ce0', '202778e8c2747d68fe8b22ffadf42d3b', '1243420115904008212', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f4bca519f54951248ee28801b16d7e2a', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386257', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f533e9a5540059f391c33cf9c91fb562', '202778e8c2747d68fe8b22ffadf42d3b', '1232984097794093074', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f637a51ffb839157923de264bf0ddca7', 'd5fb72db8404d2eb6599b3ecc2090446', '1239437792124612628', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('f71e9118385e30bfd11bedb162c9f784', '202778e8c2747d68fe8b22ffadf42d3b', '5b1bea5d3c5b970662f190de94364216', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f72f10d8bc05cb1aa461fbc68a8a1107', '202778e8c2747d68fe8b22ffadf42d3b', '1251830627578781711', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f7cf907dea3f0c6e9b58768cbd1f3a5f', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1231177799351967764', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('f812cee1443f53900a13d66ad10c6414', '202778e8c2747d68fe8b22ffadf42d3b', '1250791064667193363', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('f8d14be129a0fabd8076d90b3198d24a', 'd5fb72db8404d2eb6599b3ecc2090446', '1250722488183799826', 'admin', '2020-04-16 21:38:24', null, null);
INSERT INTO `sys_role_permission` VALUES ('faa6e3006784bfba0d467cd9b6452a5b', '2', 'faa6e3006784bfba0d467cd9b6452a5b', 'TeaR', '2020-03-17 21:23:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('fb0cf9ebfa7d2361a906def882087428', '5a2f1a7d9dd99f07d548ca07002e6ab1', '3be3edf35f70bd0e8922615392240523', 'admin', '2020-04-21 13:53:34', null, null);
INSERT INTO `sys_role_permission` VALUES ('fb496d2af1732084833eed2eb37fabf0', 'd5fb72db8404d2eb6599b3ecc2090446', '1244931937098338323', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('fbfb413178330f63495fb2173e987b66', '2', 'a02944e79cda77f8426044775a98348a', 'admin', '2020-03-30 10:17:29', null, null);
INSERT INTO `sys_role_permission` VALUES ('fc09c711dca6f4f7dd522d8a546ba81d', 'd5fb72db8404d2eb6599b3ecc2090446', '1230085853179052041', 'admin', '2020-04-16 17:28:58', null, null);
INSERT INTO `sys_role_permission` VALUES ('fc3e4c2305bd27e6dcb95cef719c58b3', '2', 'bf53dd9fd060cf81bbe858904dba3771', 'admin', '2020-03-17 21:23:32', null, null);
INSERT INTO `sys_role_permission` VALUES ('fc4dfeee24aec297904e5e4a003bfa93', '985c0bdd920309c089c200fb0cc9186a', '154e80d6a31e578d2eaa8c4634b3e8da', 'admin', '2020-04-21 15:39:19', null, null);
INSERT INTO `sys_role_permission` VALUES ('fce0907b4d297c3c1095945e8653b095', '202778e8c2747d68fe8b22ffadf42d3b', '1239437792124612624', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('fde059270a89857eeea9b7355d305c48', '202778e8c2747d68fe8b22ffadf42d3b', '1250259609349386260', 'Health-Admin', '2020-04-20 15:38:09', null, null);
INSERT INTO `sys_role_permission` VALUES ('fec40510b568fa012990b163c3101ef0', '5a2f1a7d9dd99f07d548ca07002e6ab1', '1231177799351967765', 'admin', '2020-04-21 13:53:34', null, null);

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
  `tenant_id` varchar(640) NOT NULL COMMENT '租户ID',
  `create_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_user_name` (`username`,`tenant_id`) USING BTREE,
  UNIQUE KEY `uniq_sys_user_work_no` (`work_no`,`tenant_id`) USING BTREE,
  KEY `index_user_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'TeaR', 'TeaR', '700961d246e6ddf1', 'SDFyT0h3', '001', 'https://git.t4cloud.com/img/favicon.png', '2018-12-05 00:00:00', '2', 'zqr.it@t4cloud.com', '17800001111', null, null, '上海市浦东新区陆家嘴', '1', '-1', 'TeaR', '2017-05-02 21:26:48', 'admin', '2020-03-18 11:17:06');
INSERT INTO `sys_user` VALUES ('14f10842654c1e343ec2447462fc3a8d', 'admin', '管理员', '7b8ba251023ebc67', 'RCGTeGiH', '002', '905fdc367b7f37f222aef0adaeedd2ad', null, '1', 'TEAR1', '', '', '', '', '1', '-1', 'TeaR', '2017-05-02 21:26:48', 'admin', '2020-04-22 10:26:23');
INSERT INTO `sys_user` VALUES ('731250e6589b6e60e2a9911e58632839', 'user', 'yxy', 'fbbfb2049e42b68c', '3a2zljng', '002', 'cc2ab30c510ae5383360705f9264936a', null, '2', null, '17621830901', null, null, null, '1', 'bb72c26e144fe6c9a0f5cd54a109e499', 'Health-Admin', '2020-04-20 15:36:42', 'user', '2020-04-20 16:03:29');
INSERT INTO `sys_user` VALUES ('f5dff79e29a93c6b9e24a1d02c0f81bd', 'demo', '测试用户', '2cd95b48bba9a47b', '4ip3fqj6', 'TEST-001', null, null, '1', null, null, null, null, null, '1', '-1', 'admin', '2020-04-21 15:39:44', null, null);

-- ----------------------------
-- Table structure for sys_user_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_company`;
CREATE TABLE `sys_user_company` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_user_id` (`user_id`) USING BTREE,
  KEY `index_company_id` (`company_id`) USING BTREE,
  KEY `index_user_and_company` (`user_id`,`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户公司表';

-- ----------------------------
-- Records of sys_user_company
-- ----------------------------
INSERT INTO `sys_user_company` VALUES ('1', '1', '-1', 'TeaR', '2020-04-15 16:21:21', null, null);
INSERT INTO `sys_user_company` VALUES ('105e551d5801db08e900af8cbcfacdeb', '94cc921ba37944c41a2b2d4aa2a29d42', '-1', 'admin', '2020-04-16 16:53:17', null, null);
INSERT INTO `sys_user_company` VALUES ('1d6f9578ec19c62f2cff1101dada6fc9', '94cc921ba37944c41a2b2d4aa2a29d42', '987ff5056ff0d93d06eadc05d51a1c71', 'admin', '2020-04-15 22:19:38', null, null);
INSERT INTO `sys_user_company` VALUES ('2', '14f10842654c1e343ec2447462fc3a8d', '-1', 'TeaR', '2020-04-15 16:32:50', null, null);
INSERT INTO `sys_user_company` VALUES ('2572037d97dece236e19e6f3b3ac4743', '18fc86bff67af5111b94992c977276f7', null, 'admin', '2020-04-15 22:16:07', null, null);
INSERT INTO `sys_user_company` VALUES ('50d79d662b13ebd856243bb3ba4104ea', '14f10842654c1e343ec2447462fc3a8d', '987ff5056ff0d93d06eadc05d51a1c71', 'admin', '2020-04-15 22:20:11', null, null);
INSERT INTO `sys_user_company` VALUES ('a351fdce57e6569aeece136c3163c607', '14f10842654c1e343ec2447462fc3a8d', '28cf78459b4cfc1050e8ab13f5b81c00', 'admin', '2020-04-21 13:32:58', null, null);
INSERT INTO `sys_user_company` VALUES ('bfb7beb9bf7fffc314f90143c3630b8c', 'a16d4a69d142d5f24576eede00f89b25', 'bb72c26e144fe6c9a0f5cd54a109e499', 'admin', '2020-04-16 17:27:14', null, null);
INSERT INTO `sys_user_company` VALUES ('c783ab5ad78dc85b9e5659c597de7d67', '14f10842654c1e343ec2447462fc3a8d', 'bb72c26e144fe6c9a0f5cd54a109e499', 'admin', '2020-04-16 21:58:31', null, null);
INSERT INTO `sys_user_company` VALUES ('e716614f0f9c3ea2ead32a5ee107440f', '18fc86bff67af5111b94992c977276f7', '987ff5056ff0d93d06eadc05d51a1c71', 'admin', '2020-04-15 22:17:23', null, null);

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
INSERT INTO `sys_user_role` VALUES ('098dc2874a3a9e6cd5806aa759883884', '14f10842654c1e343ec2447462fc3a8d', '1', 'admin', '2020-04-16 17:21:12', null, null);
INSERT INTO `sys_user_role` VALUES ('272b9d4f1f2dd3cc6d098aaeef7ff234', '18fc86bff67af5111b94992c977276f7', '2', 'admin', '2020-04-15 16:17:44', null, null);
INSERT INTO `sys_user_role` VALUES ('33fd4d3528d23e161bade4c346febe33', 'cbbee572a60f7d134ac84df7ded73cb4', '5a2f1a7d9dd99f07d548ca07002e6ab1', 'admin', '2020-04-21 14:09:47', null, null);
INSERT INTO `sys_user_role` VALUES ('4', '14f10842654c1e343ec2447462fc3a8d', '2', 'TeaR', '2020-01-12 13:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('8451dd4da81505b8ba32910b71b0b11a', '94cc921ba37944c41a2b2d4aa2a29d42', '2', 'admin', '2020-04-09 20:10:05', null, null);
INSERT INTO `sys_user_role` VALUES ('aa28a2a1676af0b0e14db613dd5a0760', 'f5dff79e29a93c6b9e24a1d02c0f81bd', '985c0bdd920309c089c200fb0cc9186a', 'admin', '2020-04-21 15:51:15', null, null);
INSERT INTO `sys_user_role` VALUES ('bad5a9675c4fb232a45030d33a30997e', '731250e6589b6e60e2a9911e58632839', '202778e8c2747d68fe8b22ffadf42d3b', 'Health-Admin', '2020-04-20 15:36:43', null, null);
INSERT INTO `sys_user_role` VALUES ('cc819d3f35bc4dd7cd424ea0b9350ab5', 'b809f6c4b0a3848deeea6d5f94098aaa', '5a2f1a7d9dd99f07d548ca07002e6ab1', 'admin', '2020-04-21 13:35:15', null, null);
INSERT INTO `sys_user_role` VALUES ('da0ecd8f61dbcc75eed19fb10ac60705', '1', '1', 'admin', '2020-03-23 23:54:06', null, null);
INSERT INTO `sys_user_role` VALUES ('f4d81e17ee6eb8e10454527cac76c40f', 'a16d4a69d142d5f24576eede00f89b25', 'd5fb72db8404d2eb6599b3ecc2090446', 'admin', '2020-04-16 17:29:07', null, null);
