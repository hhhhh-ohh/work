INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES ( 1, '0 10 * * * ?', '第三方平台订单重新同步', '2022-05-31 18:08:02', '2022-05-31 18:08:02', '戴倚天', '', 'FIRST', 'thirdPlatformOrderReAddJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化，入参为订单号用逗号,拼凑', '2022-05-31 18:08:02', '');
INSERT INTO `sbc-empower`.`pay_gateway_config` VALUES (16, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-02-26 18:40:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -1);


-- VOP日志脚本
CREATE TABLE `sbc-message`.`vop_log` (
                                         `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `log_type` tinyint(4) DEFAULT NULL COMMENT '类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）',
                                         `date` date DEFAULT NULL COMMENT '日期',
                                         `major_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品或订单id',
                                         `goods_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                         `content` text COMMENT 'JSON内容',
                                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                         `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                         `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                         `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                         `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除、1：删除',
                                         PRIMARY KEY (`id`),
                                         KEY `date_index` (`date`) USING BTREE COMMENT '日期索引',
                                         KEY `major_id_index` (`major_id`) USING BTREE COMMENT '主要信息索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Vop日志表';

CREATE TABLE `sbc-message`.`vop_log_0` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `log_type` tinyint(4) DEFAULT NULL COMMENT '类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）',
                                           `date` date DEFAULT NULL COMMENT '日期',
                                           `major_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品或订单id',
                                           `goods_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `content` text COMMENT 'JSON内容',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                           `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除、1：删除',
                                           PRIMARY KEY (`id`),
                                           KEY `date_index` (`date`) USING BTREE COMMENT '日期索引',
                                           KEY `major_id_index` (`major_id`) USING BTREE COMMENT '主要信息索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Vop日志表';

CREATE TABLE `sbc-message`.`vop_log_1` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `log_type` tinyint(4) DEFAULT NULL COMMENT '类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）',
                                           `date` date DEFAULT NULL COMMENT '日期',
                                           `major_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品或订单id',
                                           `goods_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `content` text COMMENT 'JSON内容',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                           `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除、1：删除',
                                           PRIMARY KEY (`id`),
                                           KEY `date_index` (`date`) USING BTREE COMMENT '日期索引',
                                           KEY `major_id_index` (`major_id`) USING BTREE COMMENT '主要信息索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Vop日志表';


CREATE TABLE `sbc-message`.`vop_log_2` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `log_type` tinyint(4) DEFAULT NULL COMMENT '类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）',
                                           `date` date DEFAULT NULL COMMENT '日期',
                                           `major_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品或订单id',
                                           `goods_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `content` text COMMENT 'JSON内容',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                           `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除、1：删除',
                                           PRIMARY KEY (`id`),
                                           KEY `date_index` (`date`) USING BTREE COMMENT '日期索引',
                                           KEY `major_id_index` (`major_id`) USING BTREE COMMENT '主要信息索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Vop日志表';

CREATE TABLE `sbc-message`.`vop_log_3` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `log_type` tinyint(4) DEFAULT NULL COMMENT '类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）',
                                           `date` date DEFAULT NULL COMMENT '日期',
                                           `major_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品或订单id',
                                           `goods_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `content` text COMMENT 'JSON内容',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                           `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除、1：删除',
                                           PRIMARY KEY (`id`),
                                           KEY `date_index` (`date`) USING BTREE COMMENT '日期索引',
                                           KEY `major_id_index` (`major_id`) USING BTREE COMMENT '主要信息索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Vop日志表';

CREATE TABLE `sbc-message`.`vop_log_4` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                           `log_type` tinyint(4) DEFAULT NULL COMMENT '类型（1:拆单 4:商品上下架变更 6:商品池内商品添加/删除 10:订单取消 12:配送单生成成功 14:支付失败消息 16:商品信息变更）',
                                           `date` date DEFAULT NULL COMMENT '日期',
                                           `major_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品或订单id',
                                           `goods_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `content` text COMMENT 'JSON内容',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                           `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                           `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除、1：删除',
                                           PRIMARY KEY (`id`),
                                           KEY `date_index` (`date`) USING BTREE COMMENT '日期索引',
                                           KEY `major_id_index` (`major_id`) USING BTREE COMMENT '主要信息索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Vop日志表';