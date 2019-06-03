/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3307
 Source Schema         : testchr

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 03/06/2019 18:07:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sb_info
-- ----------------------------
DROP TABLE IF EXISTS `sb_info`;
CREATE TABLE `sb_info`  (
  `sb_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `sb_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `sb_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `sb_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `sb_protocol_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`sb_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sb_info
-- ----------------------------
INSERT INTO `sb_info` VALUES ('chr3066', '91d3cd711a594c238478a2103683f4f8', '测试设备1', '烟感设备', '无', 1);
INSERT INTO `sb_info` VALUES ('abc001', '87b41f4341614353b14ee2c97da908a4', '测试设备2', '光感设备', '无', 1);
INSERT INTO `sb_info` VALUES ('ak998', '0702d6211eb748d49a8829b3dc36d20d', '测试设备3', '电感设备', '无', 1);
INSERT INTO `sb_info` VALUES ('a-360', '7be8bb1f08d047b5bf5323946cda02d5', '测试设备4', '智能感应', '无', 1);

SET FOREIGN_KEY_CHECKS = 1;
