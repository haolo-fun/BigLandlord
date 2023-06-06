/*
 Navicat Premium Data Transfer

 Source Server         : Tencent
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : big_landlord

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 14/05/2023 20:34:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bl_deposit
-- ----------------------------
DROP TABLE IF EXISTS `bl_deposit`;
CREATE TABLE `bl_deposit`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '房东id',
  `deposit_sn` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '押金单编号',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '租客id',
  `deposit` decimal(10, 2) NULL DEFAULT NULL COMMENT '押金金额',
  `status` smallint NULL DEFAULT 0 COMMENT '押金状态（0->未支付，1->已支付，2->已退款）',
  `pay_id` varbinary(63) NULL DEFAULT NULL COMMENT '支付交易号',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_deposit
-- ----------------------------
INSERT INTO `bl_deposit` VALUES (1, 1, '20230319655078', 1, 1000.00, 1, 0x32303233303331393232303031343732363930353032333939393834, '2023-03-19 17:44:38', '2023-03-19 16:43:07', '2023-03-19 17:44:41', 0);
INSERT INTO `bl_deposit` VALUES (2, 1, '20230323026098', 2, 1000.00, 1, 0x32303233303332333232303031343732363930353032343032333232, '2023-03-23 11:28:24', '2023-03-23 10:55:41', '2023-03-23 11:28:27', 0);
INSERT INTO `bl_deposit` VALUES (3, 1, '20230323668361', 3, 1000.00, 1, 0x32303233303332333232303031343732363930353032343032353037, '2023-03-23 11:47:11', '2023-03-23 11:46:24', '2023-03-23 11:47:14', 0);
INSERT INTO `bl_deposit` VALUES (4, 1, '20230323945918', 4, 1000.00, 2, NULL, NULL, '2023-03-23 17:37:41', '2023-03-23 17:37:41', 0);
INSERT INTO `bl_deposit` VALUES (5, 1, '20230325422236', 5, 1000.00, 0, NULL, NULL, '2023-03-25 15:46:10', '2023-03-25 15:46:10', 0);

-- ----------------------------
-- Table structure for bl_finance
-- ----------------------------
DROP TABLE IF EXISTS `bl_finance`;
CREATE TABLE `bl_finance`  (
  `user_id` bigint NOT NULL,
  `deposit` decimal(10, 2) NOT NULL COMMENT '押金',
  `rent` decimal(10, 2) NOT NULL COMMENT '租金',
  `withdraw_deposit` decimal(10, 2) NOT NULL COMMENT '可提现押金',
  `withdraw_rent` decimal(10, 2) NOT NULL COMMENT '可提现租金',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_finance
-- ----------------------------
INSERT INTO `bl_finance` VALUES (1, 3000.00, 10000.00, 0.00, 0.00, '2022-11-19 15:44:50', '2022-11-19 15:44:50', 0);

-- ----------------------------
-- Table structure for bl_house
-- ----------------------------
DROP TABLE IF EXISTS `bl_house`;
CREATE TABLE `bl_house`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '房屋id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '房东id',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `area` int NULL DEFAULT NULL COMMENT '面积',
  `deposit` decimal(10, 2) NULL DEFAULT NULL COMMENT '押金',
  `price` decimal(10, 2) NOT NULL COMMENT '价格/月',
  `status` smallint NULL DEFAULT 0 COMMENT '房屋状态（空闲->0，已租->1）',
  `tenant_id` bigint NULL DEFAULT NULL COMMENT '当前租客id',
  `due_date` date NULL DEFAULT NULL COMMENT '到期日期',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_house
-- ----------------------------
INSERT INTO `bl_house` VALUES (1, 1, '18-601', 20, 1000.00, 2000.00, 2, 2, '2023-04-23', '2022-10-23 16:22:03', '2022-10-23 16:22:03', 0);
INSERT INTO `bl_house` VALUES (2, 1, '18-602', 30, 1000.00, 2000.00, 2, 3, '2023-04-23', '2022-10-23 16:23:46', '2022-10-23 16:23:46', 0);
INSERT INTO `bl_house` VALUES (3, 1, '18-603', 40, 1000.00, 2000.00, 0, NULL, NULL, '2022-10-23 16:25:14', '2022-10-23 16:25:14', 0);
INSERT INTO `bl_house` VALUES (4, 1, '18-604', 50, 1000.00, 2000.00, 1, 1, '2023-06-19', '2022-10-23 16:25:17', '2022-10-23 16:25:17', 0);
INSERT INTO `bl_house` VALUES (5, 1, '18-605', 60, 1000.00, 2000.00, 0, NULL, NULL, '2022-10-23 16:25:20', '2022-10-23 16:25:20', 0);
INSERT INTO `bl_house` VALUES (6, 1, '18-606', 70, 1000.00, 2000.00, 0, 4, '2023-04-23', '2022-10-23 16:25:31', '2022-10-23 16:25:31', 0);
INSERT INTO `bl_house` VALUES (7, 1, '18-607', 80, 1000.00, 2000.00, 2, 5, '2023-04-25', '2022-10-23 16:25:34', '2022-10-23 16:25:34', 0);
INSERT INTO `bl_house` VALUES (8, 1, '18-608', 90, 1000.00, 2000.00, 0, NULL, NULL, '2022-10-23 16:25:38', '2022-10-23 16:25:38', 0);
INSERT INTO `bl_house` VALUES (9, 1, '18-609', 100, 1000.00, 2000.00, 0, NULL, NULL, '2022-10-23 16:25:54', '2022-11-05 17:47:05', 0);

-- ----------------------------
-- Table structure for bl_order
-- ----------------------------
DROP TABLE IF EXISTS `bl_order`;
CREATE TABLE `bl_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '房东id',
  `tenant_id` bigint NOT NULL COMMENT '租客id',
  `house_id` bigint NOT NULL COMMENT '房源id',
  `order_sn` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租单编号',
  `count` smallint NULL DEFAULT NULL COMMENT '租期',
  `order_status` smallint NULL DEFAULT NULL COMMENT '租单状态（0->未下发，1->已下发，2->已支付)',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总费用',
  `pay_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付交易号',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_order
-- ----------------------------
INSERT INTO `bl_order` VALUES (1, 1, 1, 4, '20230319281680', 3, 2, 6000.00, '2023031922001472690502399873', '2023-03-19 17:45:21', '2023-03-19 16:43:07', '2023-03-19 17:45:24', 0);
INSERT INTO `bl_order` VALUES (2, 1, 2, 1, '20230323360498', 1, 2, 2000.00, '2023032322001472690502402323', '2023-03-23 11:29:43', '2023-03-23 10:55:41', '2023-03-23 11:29:46', 0);
INSERT INTO `bl_order` VALUES (3, 1, 3, 2, '20230323603907', 1, 2, 2000.00, '2023032322001472690502402671', '2023-03-23 11:48:09', '2023-03-23 11:46:24', '2023-03-23 11:48:12', 0);
INSERT INTO `bl_order` VALUES (4, 1, 4, 6, '20230323042215', 1, 4, 2000.00, NULL, NULL, '2023-03-23 17:37:41', '2023-03-23 17:37:41', 0);
INSERT INTO `bl_order` VALUES (5, 1, 5, 7, '20230325550227', 1, 1, 2015.00, NULL, NULL, '2023-03-25 15:46:10', '2023-03-29 17:14:29', 0);
INSERT INTO `bl_order` VALUES (6, 1, 1, 4, '20230419851418', 0, 0, 0.00, NULL, NULL, '2023-04-19 00:00:00', '2023-04-19 00:00:00', 0);
INSERT INTO `bl_order` VALUES (7, 1, 2, 1, '20230423301571', 0, 0, 0.00, NULL, NULL, '2023-04-23 00:00:00', '2023-04-23 00:00:00', 0);
INSERT INTO `bl_order` VALUES (8, 1, 3, 2, '20230423940387', 0, 0, 0.00, NULL, NULL, '2023-04-23 00:00:00', '2023-04-23 00:00:00', 0);
INSERT INTO `bl_order` VALUES (9, 1, 5, 7, '20230425693148', 0, 0, 0.00, NULL, NULL, '2023-04-25 00:00:00', '2023-04-25 00:00:00', 0);

-- ----------------------------
-- Table structure for bl_order_additional
-- ----------------------------
DROP TABLE IF EXISTS `bl_order_additional`;
CREATE TABLE `bl_order_additional`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL COMMENT '租单表id',
  `key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容，如管理费，水费，电费，等等',
  `value` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `count` smallint NULL DEFAULT NULL COMMENT '数量',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_order_additional
-- ----------------------------
INSERT INTO `bl_order_additional` VALUES (1, 5, '垃圾处理费', 15.00, 1, '', '2023-03-25 16:14:11', '2023-03-25 16:14:11', 0);

-- ----------------------------
-- Table structure for bl_role
-- ----------------------------
DROP TABLE IF EXISTS `bl_role`;
CREATE TABLE `bl_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `user_count` int NULL DEFAULT NULL COMMENT '用户数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_role
-- ----------------------------
INSERT INTO `bl_role` VALUES (1, 'admin', '管理员', 1, '2022-10-16 17:08:32', '2022-10-16 17:08:32', 0);
INSERT INTO `bl_role` VALUES (2, 'user', '房东', 0, '2022-11-03 17:21:04', '2022-11-03 17:21:04', 0);

-- ----------------------------
-- Table structure for bl_running_tally
-- ----------------------------
DROP TABLE IF EXISTS `bl_running_tally`;
CREATE TABLE `bl_running_tally`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '房东id',
  `sn` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租单或押金单编号',
  `type` smallint NOT NULL COMMENT '0->押金，1->租金',
  `price` decimal(10, 2) NOT NULL COMMENT '流水',
  `balance` decimal(10, 2) NOT NULL COMMENT '余额',
  `form` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'in or out',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_running_tally
-- ----------------------------
INSERT INTO `bl_running_tally` VALUES (1, 1, '20230319655078', 0, 1000.00, 1000.00, 'in', NULL, '2023-03-19 17:44:41', '2023-03-19 17:44:41', 0);
INSERT INTO `bl_running_tally` VALUES (2, 1, '20230319281680', 1, 6000.00, 7000.00, 'in', NULL, '2023-03-19 17:45:24', '2023-03-19 17:45:24', 0);
INSERT INTO `bl_running_tally` VALUES (3, 1, '20230323026098', 0, 1000.00, 8000.00, 'in', NULL, '2023-03-23 11:28:27', '2023-03-23 11:28:27', 0);
INSERT INTO `bl_running_tally` VALUES (4, 1, '20230323360498', 1, 2000.00, 10000.00, 'in', NULL, '2023-03-23 11:29:46', '2023-03-23 11:29:46', 0);
INSERT INTO `bl_running_tally` VALUES (5, 1, '20230323668361', 0, 1000.00, 11000.00, 'in', NULL, '2023-03-23 11:47:14', '2023-03-23 11:47:14', 0);
INSERT INTO `bl_running_tally` VALUES (6, 1, '20230323603907', 1, 2000.00, 13000.00, 'in', NULL, '2023-03-23 11:48:12', '2023-03-23 11:48:12', 0);

-- ----------------------------
-- Table structure for bl_tenant
-- ----------------------------
DROP TABLE IF EXISTS `bl_tenant`;
CREATE TABLE `bl_tenant`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '房东id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租客姓名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租客电话',
  `idcard` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_tenant
-- ----------------------------
INSERT INTO `bl_tenant` VALUES (1, 1, '张三', '13512345678', '440100202201016735', '2022-10-23 16:56:00', '2022-10-23 16:56:00', 0);
INSERT INTO `bl_tenant` VALUES (2, 1, '李四', '13812344321', '440100202201026374', '2022-10-23 16:57:07', '2022-10-23 16:57:07', 0);
INSERT INTO `bl_tenant` VALUES (3, 1, '王五', '13843214321', '440100202201035638', '2022-10-23 16:58:05', '2022-10-23 16:58:05', 0);
INSERT INTO `bl_tenant` VALUES (4, 1, '赵六', '13856789876', '440100202201047482', '2022-10-23 16:58:36', '2022-10-23 16:58:36', 0);
INSERT INTO `bl_tenant` VALUES (5, 1, '孙七', '13834568765', '440100202201054738', '2022-10-23 16:59:06', '2022-10-23 16:59:06', 0);
INSERT INTO `bl_tenant` VALUES (6, 1, '周八', '13824689753', '440100202201063528', '2022-10-23 16:59:35', '2022-10-23 16:59:35', 0);

-- ----------------------------
-- Table structure for bl_user
-- ----------------------------
DROP TABLE IF EXISTS `bl_user`;
CREATE TABLE `bl_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`, `username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_user
-- ----------------------------
INSERT INTO `bl_user` VALUES (1, 'admin', '$2a$10$GG921.F6sA0mk3cdzHiF2.YFxe5tf2ZHbyiZJFNVo6mPyWjfl9fYS', 'https://cos.haolo.fun/20220810180349.jpg', '13512345678', 'admin@example.com', ' Coming', 1, '2022-10-16 15:45:28', '2022-10-16 15:45:28', 0);

-- ----------------------------
-- Table structure for bl_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `bl_user_role_relation`;
CREATE TABLE `bl_user_role_relation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bl_user_role_relation
-- ----------------------------
INSERT INTO `bl_user_role_relation` VALUES (1, 1, 1, '2022-10-16 17:09:38', '2022-10-16 17:09:38', 0);

SET FOREIGN_KEY_CHECKS = 1;
