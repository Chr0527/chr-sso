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

 Date: 03/06/2019 18:07:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `encrypt_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('ICy5YqxZB1uWSwcVLSNLcA==', '91d3cd711a594c238478a2103683f4f8');
INSERT INTO `user_password` VALUES ('ICy5YqxZB1uWSwcVLSNLcA==', '87b41f4341614353b14ee2c97da908a4');
INSERT INTO `user_password` VALUES ('f5daVsdh22UG7KCzfObshw==', '0702d6211eb748d49a8829b3dc36d20d');
INSERT INTO `user_password` VALUES ('f5daVsdh22UG7KCzfObshw==', '7be8bb1f08d047b5bf5323946cda02d5');
INSERT INTO `user_password` VALUES ('f5daVsdh22UG7KCzfObshw==', '0c3660fb622e473b88776ca909aeddcc');
INSERT INTO `user_password` VALUES ('RWrJsNFai38ecQcyIQWYhg==', 'ac7d35a0fb8b4738a8aa510fc0377b75');
INSERT INTO `user_password` VALUES ('KkAh5jm7yW04Y0bFiYrpoA==', 'af2e50d9a7fc4a0da335bce9f13bf500');
INSERT INTO `user_password` VALUES ('ICy5YqxZB1uWSwcVLSNLcA==', '2c9c1c0a0ef74ee8802e42116fd31d88');

SET FOREIGN_KEY_CHECKS = 1;
