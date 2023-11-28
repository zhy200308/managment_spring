/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : localhost:3306
 Source Schema         : managament_system

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 28/11/2023 21:51:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(255) NULL DEFAULT NULL COMMENT '文件大小(kb)',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载链接',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `enable` tinyint(4) NULL DEFAULT 0 COMMENT '是否禁用链接',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('0b234621ab0d40b392d844b3e32af6c6', '136143dca3cf24a3665bdcff029ba0b0', 'fliggycloud.jpg', 'jpg', 6, 'http://localhost:9099/files/c0888c0273f5449597244c927e4202e1.jpg', 1, 0);
INSERT INTO `file` VALUES ('989410347a6b4b1e894f4746f118b32d', '62302b9a8f64d01f859bc151f09094dd', 'avala.png', 'png', 2299, 'http://localhost:8081/files/bae32dbf3a6d477bbe560c7c6e813b11.png', 1, 1);
INSERT INTO `file` VALUES ('cd2add0a575e4857ab95e194171cacdf', '63bbdb5b76b8d349ad35ff4281efbd37', 'slideshow (1).png', 'png', 2025, 'http://localhost:9099/files/c525bd54b8334ec495623c0e2996195b.png', 0, 1);
INSERT INTO `file` VALUES ('e81773df8472473791e588bd2cee1ff3', 'e5788b13546156281bf0a4b38bdd0901', '7z2301-x64.exe', 'exe', 1552, 'http://localhost:8081/files/c564a9bb5667492199b24de866b2fcc5.exe', 1, 0);
INSERT INTO `file` VALUES ('e81c1b03c33842dba015e60d04468de2', '136143dca3cf24a3665bdcff029ba0b0', 'fliggycloud.jpg', 'jpg', 6, 'http://localhost:9099/files/c0888c0273f5449597244c927e4202e1.jpg', 1, 0);
INSERT INTO `file` VALUES ('fbc4e32b75d84f588dc1b5aaa2772362', '0e92bdb629b576b81cefd729f830eb46', 'icon.png', 'png', 6, 'http://localhost:8081/files/902911d9fb1e4f3f89095c297ba61ab9.png', 1, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `role` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `creatTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `avatarUrl` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('176b982e125749a78b6da3bf4b891736', 'CMG', 'Ggh123456', NULL, 'CMG', NULL, '22@qq.com', NULL, '2023-10-31 11:10:00', 'http://localhost:9099/files/c0888c0273f5449597244c927e4202e1.jpg');

SET FOREIGN_KEY_CHECKS = 1;
