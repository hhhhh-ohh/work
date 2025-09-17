CREATE TABLE `agent_update_poster_auth` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                            `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
                                            `city_id` bigint DEFAULT NULL COMMENT '城市ID',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `idx_city_id` (`city_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='代理商刷新海报权限表';