/*
 Navicat Premium Dump SQL

 Source Server         : sbc正式-mysql
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : 172.16.50.31:3306
 Source Schema         : sbc-customer

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 22/08/2025 11:10:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer_child
-- ----------------------------
DROP TABLE IF EXISTS `customer_child`;
CREATE TABLE `customer_child`  (
                                   `child_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小孩ID',
                                   `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户ID',
                                   `parent_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '家长姓名',
                                   `child_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小孩姓名',
                                   `child_birthday` datetime NULL DEFAULT NULL COMMENT '小孩生日',
                                   `child_gender` tinyint NULL DEFAULT NULL COMMENT '性别类型 0女 1男 2保密',
                                   `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`child_id`) USING BTREE,
                                   INDEX `idx_customer_id`(`customer_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '小孩信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
