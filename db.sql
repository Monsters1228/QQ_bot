-- 图片
CREATE TABLE `qq_bot`.`images`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `url`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
    `is_view` int(11) NULL DEFAULT 0 COMMENT '是否看过',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 295 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 用户
CREATE TABLE `qq_bot`.`user`
(
    `id`    int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `qq`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qq号',
    `count` int(11) NULL DEFAULT 1 COMMENT 'setu计数',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic STORAGE MEMORY;