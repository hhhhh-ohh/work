
ALTER TABLE `sbc-goods`.`third_goods_cate`
    ADD COLUMN `qualification_type` tinyint NULL COMMENT '微信类目资质类型,0:不需要,1:必填,2:选填' AFTER `third_platform_type`,
    ADD COLUMN `qualification` varchar(500) NULL DEFAULT '' COMMENT '微信类目资质' AFTER `qualification_type`,
    ADD COLUMN `product_qualification` varchar(500) NULL DEFAULT '' COMMENT '微信商品资质' AFTER `qualification`,
    ADD COLUMN `product_qualification_type` tinyint NULL COMMENT '微信商品资质类型,0:不需要,1:必填,2:选填' AFTER `product_qualification`;
ALTER TABLE `sbc-goods`.`goods_cate_third_cate_rel`
    ADD INDEX `idx_cate_id`(`third_cate_id`,`third_platform_type`,`del_flag`);

ALTER TABLE `sbc-goods`.`third_goods_cate`
    ADD INDEX `idx_cate_id`(`cate_id`, `third_platform_type`, `del_flag`);


ALTER TABLE `s2b_statistics`.`replay_trade`
    ADD COLUMN `video_name` varchar(64) NULL COMMENT '视频号名称' AFTER `customer_name`,
    ADD COLUMN `video_account` varchar(64) NULL COMMENT '视频账号' AFTER `video_name`,
    ADD COLUMN `sell_platform_type` tinyint NULL AFTER `video_account`,
    ADD COLUMN `scene_group` smallint UNSIGNED NULL COMMENT '微信场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）' AFTER `sell_platform_type`;

ALTER TABLE `s2b_statistics`.`replay_return_order`
    ADD COLUMN `video_name` varchar(64) NULL COMMENT '视频号名称' AFTER `return_type`,
    ADD COLUMN `video_account` varchar(64) NULL COMMENT '视频账号' AFTER `video_name`,
    ADD COLUMN `sell_platform_type` tinyint NULL AFTER `video_account`,
    ADD COLUMN `scene_group` smallint UNSIGNED NULL COMMENT '微信场景值:全部、直播间（下单场景值1176、1177）、橱窗（场景值1195）、视频号活动（场景值1191）、视频号商店（场景值1175）' AFTER `sell_platform_type`;

ALTER TABLE `s2b_statistics`.`replay_trade`
    DROP INDEX `pay_time_index`,
    ADD INDEX `pay_time_index`(`pay_time`, `pay_state`, `sell_platform_type`) USING BTREE;

ALTER TABLE `s2b_statistics`.`replay_return_order`
    DROP INDEX `rro_create_time_index`,
    ADD INDEX `rro_create_time_index`(`create_time`, `sell_platform_type`) USING BTREE;


CREATE TABLE `sbc-goods`.`wechat_cate_audit` (
                                     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                     `audit_id` varchar(32) DEFAULT '' COMMENT '微信返回的审核id',
                                     `wechat_cate_id` bigint(20) NOT NULL COMMENT '微信类目id',
                                     `cate_ids` varchar(500) DEFAULT '' COMMENT '映射的平台类目',
                                     `audit_status` tinyint(4) NOT NULL COMMENT '审核状态，0：待审核，1：审核通过，2：审核不通过',
                                     `reject_reason` varchar(255) DEFAULT '' COMMENT '审核不通过原因',
                                     `create_time` datetime NOT NULL,
                                     `create_person` varchar(32) NOT NULL,
                                     `update_time` datetime DEFAULT NULL,
                                     `product_qualification_urls` varchar(2000) DEFAULT '' COMMENT '商品资质',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     KEY `idx_cate_id` (`wechat_cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COMMENT='微信类目审核状态';

CREATE TABLE `sbc-goods`.`wechat_cate_certificate` (
                                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                           `cate_id` bigint(20) unsigned NOT NULL COMMENT '微信类目id',
                                           `certificate_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片路径',
                                           `create_time` datetime NOT NULL,
                                           `create_person` varchar(32) NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE,
                                           KEY `idx_cate_id` (`cate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信类目资质';

CREATE TABLE `sbc-goods`.`wechat_sku` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                              `wechat_sku_id` varchar(32) DEFAULT '' COMMENT '微信端sku_id',
                              `product_id` bigint(20) unsigned DEFAULT NULL COMMENT '微信端商品id',
                              `goods_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                              `goods_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                              `edit_status` tinyint(4) NOT NULL COMMENT '审核状态，1：待审核，2：审核中，3：审核失败，4：审核成功',
                              `wechat_shelve_status` tinyint(4) NOT NULL COMMENT '0初始值5上架11自主下架13违规下架',
                              `reject_reason` varchar(1000) DEFAULT '' COMMENT '审核不通过原因',
                              `img` varchar(1000) NOT NULL DEFAULT '' COMMENT '微信端商品图片',
                              `down_reason` varchar(1000) DEFAULT '' COMMENT '微信端下架原因',
                              `create_time` datetime NOT NULL,
                              `update_time` datetime DEFAULT NULL,
                              `del_flag` tinyint(4) NOT NULL COMMENT '是否删除，0，否，1是',
                              `create_person` varchar(32) NOT NULL DEFAULT '',
                              `update_person` varchar(32) DEFAULT '',
                              PRIMARY KEY (`id`) USING BTREE,
                              KEY `idx_goods_id` (`del_flag`,`goods_id`) USING BTREE,
                              KEY `idx_goods_info_id` (`goods_info_id`) USING BTREE,
                              KEY `idx_audit` (`del_flag`,`edit_status`,`wechat_shelve_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信视频号带货商品';

CREATE TABLE `s2b_statistics`.`wechat_video_company_month` (
                                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                              `company_info_id` int(10) NOT NULL COMMENT '公司ID',
                                              `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                              `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                              `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                              `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                              `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                              `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                              `year` smallint(6) NOT NULL COMMENT '年份',
                                              `month` tinyint(4) NOT NULL COMMENT '月份',
                                              `create_time` datetime NOT NULL,
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2053 DEFAULT CHARSET=utf8mb4 COMMENT='视频号订单公司维度每月统计';

CREATE TABLE `s2b_statistics`.`wechat_video_company_seven` (
                                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                              `company_info_id` int(11) NOT NULL COMMENT '公司id',
                                              `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                              `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                              `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                              `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                              `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                              `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                              `date` date NOT NULL COMMENT '日期',
                                              `create_time` datetime NOT NULL,
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4730 DEFAULT CHARSET=utf8mb4 COMMENT='公司维度每七天统计';

CREATE TABLE `s2b_statistics`.`wechat_video_company_thirty` (
                                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                               `company_info_id` int(11) NOT NULL COMMENT '公司id',
                                               `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                               `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                               `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                               `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                               `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                               `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                               `date` date NOT NULL COMMENT '日期',
                                               `create_time` datetime NOT NULL,
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4711 DEFAULT CHARSET=utf8mb4 COMMENT='公司维度每三十天统计';

CREATE TABLE `s2b_statistics`.`wechat_video_month` (
                                      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                      `video_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频号Id',
                                      `video_name` varchar(255) NOT NULL DEFAULT '' COMMENT '视频号名称',
                                      `company_info_id` int(11) NOT NULL COMMENT '公司id',
                                      `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                      `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                      `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                      `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                      `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                      `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                      `year` smallint(6) NOT NULL COMMENT '年份',
                                      `month` tinyint(4) NOT NULL COMMENT '月份',
                                      `create_time` datetime NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=469 DEFAULT CHARSET=utf8mb4 COMMENT='视频号维度自然月统计';

CREATE TABLE `s2b_statistics`.`wechat_video_seven` (
                                      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                      `video_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频号Id',
                                      `video_name` varchar(255) NOT NULL DEFAULT '' COMMENT '视频号名称',
                                      `company_info_id` int(11) NOT NULL COMMENT '公司id',
                                      `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                      `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                      `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                      `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                      `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                      `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                      `date` date NOT NULL COMMENT '日期',
                                      `create_time` datetime NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1176 DEFAULT CHARSET=utf8mb4 COMMENT='视频号维度每七天统计';

CREATE TABLE `s2b_statistics`.`wechat_video_thirty` (
                                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                       `video_account` varchar(64) NOT NULL COMMENT '视频号Id',
                                       `video_name` varchar(255) NOT NULL DEFAULT '' COMMENT '视频号名称',
                                       `company_info_id` int(11) NOT NULL COMMENT '公司id',
                                       `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                       `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                       `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                       `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                       `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                       `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                       `date` date NOT NULL COMMENT '日期',
                                       `create_time` datetime NOT NULL,
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1010 DEFAULT CHARSET=utf8mb4 COMMENT='视频号维度每三十天统计';

CREATE TABLE `s2b_statistics`.`wechat_video_trade_day` (
                                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                          `company_info_id` int(10) NOT NULL COMMENT '公司ID',
                                          `video_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号销售额',
                                          `live_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间销售额',
                                          `shopwindow_sale_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗销售额',
                                          `video_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '视频号退货额',
                                          `live_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '直播间退货额',
                                          `shopwindow_return_amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '橱窗退货额',
                                          `date` date NOT NULL COMMENT '日期',
                                          `create_time` datetime NOT NULL,
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6390 DEFAULT CHARSET=utf8mb4 COMMENT='视频号订单公司维度每天统计';

CREATE TABLE `s2b_statistics`.`replay_wechat_video_user` (
                                            `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                            `finder_nickname` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推广员视频号昵称',
                                            `promoter_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推广员唯一ID',
                                            `promoter_open_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推广员openid',
                                            `create_time` datetime NOT NULL COMMENT '创建时间',
                                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                            `del_flag` tinyint(4) NOT NULL COMMENT '删除标识,0:未删除1:已删除',
                                            PRIMARY KEY (`id`),
                                            KEY `idx_promoter_id` (`promoter_id`) USING BTREE,
                                            KEY `idx_promoter_open_id` (`promoter_open_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频号同步表';

-- 视频直播带货应用设置
CREATE TABLE `sbc-setting`.`wechat_video_setting` (
                                                      `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT,
                                                      `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设置名称',
                                                      `setting_sort` tinyint(4) NOT NULL COMMENT '排序',
                                                      `setting_type` tinyint(4) NOT NULL COMMENT '设置类型,0:开通小程序自定义交易组件,1:类目申请,2:上传1款商品,等待审核通过,3:发起1笔订单并支付成功,4:完成该订单发货以及确认收货,5:发起该订单售后,6:完成测试,7:发布小程序,8:开通视频号带货场景,9:营业执照',
                                                      `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '设置数据，json格式',
                                                      `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态,0:未启用1:已启用2:禁用3:申请中',
                                                      `fail_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '失败原因',
                                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                                      `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                                      `del_flag` tinyint(4) NOT NULL COMMENT '删除标识,0:未删除1:已删除',
                                                      `create_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
                                                      `update_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
                                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '视频直播带货应用设置' ROW_FORMAT = Compact;


-- 视频号
CREATE TABLE `sbc-customer`.`wechat_video_user` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `finder_nickname` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推广员视频号昵称',
    `promoter_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推广员唯一ID',
    `promoter_open_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '推广员openid',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `del_flag` tinyint(4) NOT NULL COMMENT '删除标识,0:未删除1:已删除',
    PRIMARY KEY (`id`),
    KEY `idx_promoter_id` (`promoter_id`) USING BTREE,
    KEY `idx_promoter_open_id` (`promoter_open_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频号';

-- 视频带货申请
CREATE TABLE `sbc-customer`.`wechat_video_store_audit` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `store_id` bigint(20) NOT NULL COMMENT '店铺id',
    `status` tinyint(4) NOT NULL COMMENT '申请开通状态，0：未审核，1：审核通过，2：驳回，3：禁用',
    `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
    `audit_reason` varchar(512) DEFAULT NULL COMMENT '审核不通过原因',
    `del_flag` tinyint(4) NOT NULL COMMENT '是否删除 0 否  1 是',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `create_person` varchar(255) DEFAULT NULL COMMENT '创建人',
    `update_person` varchar(255) DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    KEY `idx_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='视频带货申请';

-- 第三方代销平台物流公司
CREATE TABLE `sbc-setting`.`third_express_company` (
    `id` bigint(5) NOT NULL AUTO_INCREMENT COMMENT '主键ID,自增',
    `express_name` varchar(125) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物流公司名称',
    `express_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物流公司代码',
    `sell_platform_type` tinyint(3) unsigned NOT NULL COMMENT '第三方代销平台(0:微信视频号)',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标志 默认0：未删除 1：删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_express` (`sell_platform_type`,`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=602 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第三方代销平台物流公司';

-- 平台和第三方代销平台物流公司映射
CREATE TABLE `sbc-setting`.`express_company_third_rel` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID,自增',
    `express_company_id` bigint(5) NOT NULL COMMENT '平台物流公司主键',
    `third_express_company_id` bigint(5) NOT NULL COMMENT '第三方平台物流公司主键',
    `sell_platform_type` tinyint(3) unsigned NOT NULL COMMENT '第三方代销平台(0:微信视频号)',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标志 默认0：未删除 1：删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_express_id` (`express_company_id`,`sell_platform_type`,`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='平台和第三方代销平台物流公司映射';

-- 营销互斥优化迭代初始化脚本
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('marketing_mutex', 'marketing_mutex', '活动互斥开关', '设置-活动互斥开关', 0, NULL, now(), now(), 0);


-- 视频号迭代初始化脚本
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (169, 1, '0 0 2 * * ?', '每天凌晨两点同步微信类目	', '2022-04-08 15:56:54', '2022-04-09 15:56:54', '陈乐', '', 'FIRST', 'WechatCateSyncHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-04-09 15:56:54', '');

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` ( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 2, '0 0 1 1 * ? ', '视频号销售额退单额统计-月结', '2022-04-26 17:29:18', '2022-04-26 17:29:18', '陈乐', '', 'FIRST', 'wechatVideoScheduledGenerateData', '2', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-04-26 17:29:18', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` ( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 2, '0 0 1 * * ? ', '视频号销售额退单额统计-昨日，每七天，每三十天', '2022-04-26 17:24:29', '2022-04-26 17:26:25', '陈乐', '', 'FIRST', 'wechatVideoScheduledGenerateData', '1', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-04-26 17:24:29', '');

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 30 0 * * ?', '视频号推广员同步', '2022-04-28 18:45:04', '2022-05-05 18:49:51', '马连峰', '', 'FIRST', 'WechatVideoUserSyncHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-04-28 18:45:04', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 0 1 * * ?', '全量初始化微信视频号支持的快递公司', '2022-04-28 21:51:43', '2022-05-05 18:49:40', '马连峰', '', 'FIRST', 'WechatVideoExpressCompanyInitHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-04-28 21:51:43', '');


INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (1, '开通小程序自定义交易组件', 0, 0, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (2, '类目申请', 1, 1, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (3, '上传1款商品,等待审核通过', 2, 2, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (4, '发起1笔订单并支付成功', 3, 3, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (5, '完成该订单发货以及确认收货', 4, 4, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (6, '发起该订单售后', 5, 5, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (7, '完成测试', 6, 6, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (8, '开通视频号带货场景', 7, 7, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);
INSERT INTO `sbc-setting`.`wechat_video_setting` VALUES (9, '营业执照', 8, 8, NULL, 0, NULL, '2022-04-01 12:00:00', NULL, 0, NULL, NULL);

INSERT INTO `sbc-setting`.`system_config` ( `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`)
VALUES ( 'value_added_services', 'vas_wechat_channels', '增值服务-微信视频号', '判断商城是否购买增值服务-微信视频号', '1', NULL, '2022-04-14 10:10:58', '2022-04-14 10:10:58', '0');

INSERT INTO `sbc-empower`.`pay_channel_item` (`id`, `name`, `gateway_name`, `gateway_id`, `channel`, `is_open`, `terminal`, `code`, `create_time`) VALUES ('30', '微信视频号支付', 'WECHAT', '3', 'WeChat', '1', '1', 'wx_video', '2022-04-25 14:43:17');

-- 初始化微信支持的物流公司
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (1, 'AAE全球专递', 'AAE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (2, 'ACS雅仕快递', 'ACS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (3, '澳多多', 'ADD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (4, 'ADP Express Tracking', 'ADP', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (5, '安捷快递', 'AJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (6, '阿里跨境电商物流', 'ALKJWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (7, '亚马逊物流', 'AMAZON', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (8, '安能物流', 'ANE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (9, '安能快运', 'ANEKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (10, '安圭拉邮政', 'ANGUILAYOU', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (11, 'AOL（澳通）', 'AOL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (12, '澳门邮政', 'AOMENYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (13, 'APAC', 'APAC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (14, 'Aramex', 'ARAMEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (15, '奥地利邮政', 'AT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (16, 'Australia Post Tracking', 'AUSTRALIA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (17, '安迅物流', 'AX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (18, '安鲜达', 'AXD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (19, '澳邮专线', 'AYCA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (20, '安邮美国', 'AYUS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (21, '巴伦支快递', 'BALUNZHI', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (22, 'BCWELT', 'BCWELT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (23, '八达通', 'BDT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (24, '比利时邮政', 'BEL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (25, '百腾物流', 'BETWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (26, 'BETWL_Crack', 'BETWL_Crack', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (27, '败欧洲', 'BEUROPE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (28, '八方安运', 'BFAY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (29, '百福东方', 'BFDF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (30, '贝海国际', 'BHGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (31, 'BHT快递', 'BHT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (32, '秘鲁邮政', 'BILUYOUZHE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (33, '北极星快运', 'BJXKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (34, '宝凯物流', 'BKWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (35, '巴伦支', 'BLZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (36, '笨鸟国际', 'BN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (37, '奔腾物流', 'BNTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (38, 'BNTWL_Crack', 'BNTWL_Crack', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (39, '北青小红帽', 'BQXHM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (40, '巴西邮政', 'BR', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (41, '邦送物流', 'BSWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (42, '百世快运', 'BTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (43, '不丹邮政', 'BUDANYOUZH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (44, '加拿大邮政', 'CA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (45, 'CBO钏博物流', 'CBO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (46, 'CCES快递', 'CCES', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (47, 'CDEK', 'CDEK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (48, '成都善途速运', 'CDSTKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (49, '春风物流', 'CFWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (50, '程光物流', 'CG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (51, '诚通物流', 'CHTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (52, '城市100', 'CITY100', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (53, '城际快递', 'CJKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (54, '出口易', 'CKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (55, '承诺达', 'CND', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (56, '佳吉快运', 'CNEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (57, 'CNPEX中邮快递', 'CNPEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (58, '新配盟', 'CNXLM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (59, 'COE东方快递', 'COE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (60, '疯狂快递', 'CRAZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (61, '长沙创一', 'CSCY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (62, '联合运通', 'CTG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (63, '传喜物流', 'CXHY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (64, '递四方速递', 'D4PX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (65, '德邦快递', 'DBL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (66, '德邦快运', 'DBLKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (67, '递必易国际物流', 'DBYWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (68, '德创物流', 'DCWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (69, '大道物流', 'DDWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (70, '德坤', 'DEKUN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (71, '德国云快递', 'DGYKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (72, 'DHL', 'DHL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (73, 'DHL Global Mail', 'DHLGM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (74, 'DHL(中国件)', 'DHL_C', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (75, 'DHL德国', 'DHL_DE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (76, 'DHL(英文版)', 'DHL_EN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (77, 'DHL全球', 'DHL_GLB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (78, 'DHL(美国)', 'DHL_USA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (79, '东红物流', 'DHWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (80, '东骏快捷物流', 'DJKJWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (81, '丹麦邮政', 'DK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (82, '到了港', 'DLG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (83, '到乐国际', 'DLGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (84, '大马鹿', 'DML', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (85, 'DPD', 'DPD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (86, 'DPEX', 'DPEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (87, 'D速物流', 'DSWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (88, '店通快递', 'DTKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (89, '大田物流', 'DTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (90, '大洋物流快递', 'DYWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (91, '易客满', 'EKM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (92, 'EMS', 'EMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (93, 'EMS国内', 'EMS2', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (94, 'EMS国际', 'EMSGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (95, 'EPS (联众国际快运)', 'EPS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (96, 'EShipper', 'ESHIPPER', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (97, 'E特快', 'ETK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (98, 'EWE', 'EWE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (99, '速派快递', 'FASTGO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (100, '飞豹快递', 'FBKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (101, '丰巢', 'FBOX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (102, '丰程物流', 'FCWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (103, 'FEDEX联邦(国内件）', 'FEDEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (104, 'FEDEX联邦(国际件）', 'FEDEX_GJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (105, '飞狐快递', 'FHKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (106, '飞康达', 'FKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (107, '芬兰邮政', 'FLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (108, 'FQ', 'FQ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (109, '复融供应链', 'FRGYL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (110, '丰通快运', 'FT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (111, '富腾达', 'FTD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (112, '丰网速运', 'FWX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (113, '法翔速运', 'FX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (114, '凡宇货的', 'FYKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (115, '飞远配送', 'FYPS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (116, '凡宇速递', 'FYSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (117, '方舟国际速递', 'FZGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (118, '迦递快递', 'GAI', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (119, '冠达', 'GD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (120, '广东邮政', 'GDEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (121, '冠达快递', 'GDKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (122, 'GE2D', 'GE2D', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (123, '挂号信', 'GHX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (124, '国际e邮宝', 'GJEYB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (125, '国际邮政包裹', 'GJYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (126, '港快速递', 'GKSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (127, 'GLS', 'GLS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (128, '共速达', 'GSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (129, '冠泰', 'GT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (130, '广通速递', 'GTKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (131, '高铁快运', 'GTKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (132, '广通', 'GTONG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (133, '高铁速递', 'GTSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (134, '河北建华', 'HBJH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (135, '汇丰物流', 'HF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (136, '合肥汇文', 'HFHW', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (137, '黑狗冷链', 'HGLL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (138, '华航快递', 'HHKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (139, '天天快递', 'HHTT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (140, '华翰物流', 'HHWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (141, '皇家物流', 'HJWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (142, '辉隆物流', 'HLONGWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (143, '恒路物流', 'HLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (144, '黄马甲快递', 'HMJKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (145, '海盟速递', 'HMSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (146, '天地华宇', 'HOAU', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (147, '鸿桥供应链', 'HOTSCM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (148, '海派通物流公司', 'HPTEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (149, '华企快递', 'HQKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (150, '华企快运', 'HQKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (151, '环球速运', 'HQSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (152, '韩润物流', 'HRWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (153, '昊盛物流', 'HSWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (154, '青岛恒通快递', 'HTKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (155, '百世快递', 'HTKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (156, '户通物流', 'HTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (157, '华夏龙物流', 'HXLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (158, '豪翔物流', 'HXWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (159, '货运皇物流', 'HYH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (160, '好来运快递', 'HYLSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (161, '安的列斯群岛邮政', 'IADLSQDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (162, '澳大利亚邮政', 'IADLYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (163, '阿尔巴尼亚邮政', 'IAEBNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (164, '阿尔及利亚邮政', 'IAEJLYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (165, '阿富汗邮政', 'IAFHYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (166, '安哥拉邮政', 'IAGLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (167, '阿根廷邮政', 'IAGTYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (168, '埃及邮政', 'IAJYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (169, '阿鲁巴邮政', 'IALBYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (170, '奥兰群岛邮政', 'IALQDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (171, '阿联酋邮政', 'IALYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (172, '阿曼邮政', 'IAMYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (173, '阿塞拜疆邮政', 'IASBJYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (174, '埃塞俄比亚邮政', 'IASEBYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (175, '爱沙尼亚邮政', 'IASNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (176, '阿森松岛邮政', 'IASSDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (177, '博茨瓦纳邮政', 'IBCWNYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (178, '波多黎各邮政', 'IBDLGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (179, '冰岛邮政', 'IBDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (180, '白俄罗斯邮政', 'IBELSYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (181, '波黑邮政', 'IBHYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (182, '保加利亚邮政', 'IBJLYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (183, '巴基斯坦邮政', 'IBJSTYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (184, '黎巴嫩邮政', 'IBLNYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (185, '便利速递', 'IBLSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (186, '巴林邮政', 'IBLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (187, '百慕达邮政', 'IBMDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (188, '波兰邮政', 'IBOLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (189, '宝通达', 'IBTD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (190, '贝邮宝', 'IBYB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (191, '达方物流', 'IDFWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (192, '德国邮政', 'IDGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (193, '爱尔兰邮政', 'IE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (194, '厄瓜多尔邮政', 'IEGDEYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (195, '俄罗斯邮政', 'IELSYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (196, '厄立特里亚邮政', 'IELTLYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (197, '飞特物流', 'IFTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (198, '瓜德罗普岛EMS', 'IGDLPDEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (199, '瓜德罗普岛邮政', 'IGDLPDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (200, '俄速递', 'IGJESD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (201, '哥伦比亚邮政', 'IGLBYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (202, '格陵兰邮政', 'IGLLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (203, '哥斯达黎加邮政', 'IGSDLJYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (204, '韩国邮政', 'IHGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (205, '互联易', 'IHLY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (206, '哈萨克斯坦邮政', 'IHSKSTYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (207, '黑山邮政', 'IHSYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (208, '津巴布韦邮政', 'IJBBWYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (209, '吉尔吉斯斯坦邮政', 'IJEJSSTYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (210, '捷克邮政', 'IJKYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (211, '加纳邮政', 'IJNYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (212, '柬埔寨邮政', 'IJPZYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (213, '克罗地亚邮政', 'IKNDYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (214, '肯尼亚邮政', 'IKNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (215, '科特迪瓦EMS', 'IKTDWEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (216, '科特迪瓦邮政', 'IKTDWYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (217, '卡塔尔邮政', 'IKTEYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (218, '利比亚邮政', 'ILBYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (219, '林克快递', 'ILKKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (220, '罗马尼亚邮政', 'ILMNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (221, '卢森堡邮政', 'ILSBYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (222, '拉脱维亚邮政', 'ILTWYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (223, '立陶宛邮政', 'ILTWYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (224, '列支敦士登邮政', 'ILZDSDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (225, '马尔代夫邮政', 'IMEDFYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (226, '摩尔多瓦邮政', 'IMEDWYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (227, '马耳他邮政', 'IMETYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (228, '孟加拉国EMS', 'IMJLGEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (229, '摩洛哥邮政', 'IMLGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (230, '毛里求斯邮政', 'IMLQSYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (231, '马来西亚EMS', 'IMLXYEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (232, '马来西亚邮政', 'IMLXYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (233, '马其顿邮政', 'IMQDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (234, '马提尼克EMS', 'IMTNKEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (235, '马提尼克邮政', 'IMTNKYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (236, '墨西哥邮政', 'IMXGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (237, '南非邮政', 'INFYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (238, '尼日利亚邮政', 'INRLYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (239, '挪威邮政', 'INWYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (240, '欧洲专线(邮政)', 'IOZYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (241, '葡萄牙邮政', 'IPTYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (242, '全球快递', 'IQQKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (243, '全通物流', 'IQTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (244, '苏丹邮政', 'ISDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (245, '萨尔瓦多邮政', 'ISEWDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (246, '塞尔维亚邮政', 'ISEWYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (247, '斯洛伐克邮政', 'ISLFKYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (248, '斯洛文尼亚邮政', 'ISLWNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (249, '塞浦路斯邮政', 'ISPLSYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (250, '沙特阿拉伯邮政', 'ISTALBYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (251, '土耳其邮政', 'ITEQYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (252, '泰国邮政', 'ITGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (253, '特立尼达和多巴哥EMS', 'ITLNDHDBGE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (254, '突尼斯邮政', 'ITNSYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (255, '坦桑尼亚邮政', 'ITSNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (256, '危地马拉邮政', 'IWDMLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (257, '乌干达邮政', 'IWGDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (258, '乌克兰EMS', 'IWKLEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (259, '乌克兰邮政', 'IWKLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (260, '乌拉圭邮政', 'IWLGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (261, '文莱邮政', 'IWLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (262, '乌兹别克斯坦EMS', 'IWZBKSTEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (263, '乌兹别克斯坦邮政', 'IWZBKSTYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (264, '西班牙邮政', 'IXBYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (265, '小飞龙物流', 'IXFLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (266, '新喀里多尼亚邮政', 'IXGLDNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (267, '新加坡EMS', 'IXJPEMS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (268, '新加坡邮政', 'IXJPYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (269, '叙利亚邮政', 'IXLYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (270, '希腊邮政', 'IXLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (271, '夏浦世纪', 'IXPSJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (272, '夏浦物流', 'IXPWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (273, '新西兰邮政', 'IXXLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (274, '匈牙利邮政', 'IXYLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (275, '意大利邮政', 'IYDLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (276, '印度尼西亚邮政', 'IYDNXYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (277, '印度邮政', 'IYDYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (278, '英国邮政', 'IYGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (279, '伊朗邮政', 'IYLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (280, '亚美尼亚邮政', 'IYMNYYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (281, '也门邮政', 'IYMYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (282, '越南邮政', 'IYNYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (283, '以色列邮政', 'IYSLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (284, '易通关', 'IYTG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (285, '燕文物流', 'IYWWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (286, '直布罗陀邮政', 'IZBLTYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (287, '智利邮政', 'IZLYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (288, '捷安达', 'JAD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (289, '佳成国际', 'JCEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (290, '京东快递', 'JD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (291, '京东快运', 'JDKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (292, '今枫国际', 'JFGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (293, '京广速递', 'JGSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (294, '景光物流', 'JGWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (295, '极光转运', 'JGZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (296, '九曳供应链', 'JIUYE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (297, '嘉里国际', 'JLDT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (298, '日本邮政', 'JP', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (299, '绝配国际速递', 'JPKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (300, '捷特快递', 'JTKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (301, '极兔快递', 'JTSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (302, '急先达', 'JXD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (303, '吉祥邮转运', 'JXYKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (304, '晋越快递', 'JYKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (305, '加运美', 'JYM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (306, '上海久易国际', 'JYSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (307, '精英速运', 'JYSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (308, '佳怡物流', 'JYWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (309, '快8速运', 'KBSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (310, '快服务', 'KFW', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (311, '康力物流', 'KLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (312, '快速递物流', 'KSDWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (313, '快淘快递', 'KTKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (314, '快优达速递', 'KYDSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (315, '跨越速运', 'KYSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (316, '跨越物流', 'KYWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (317, '龙邦快递', 'LB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (318, '蓝弧快递', 'LHKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (319, '联合快递', 'LHKDS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (320, '联昊通速递', 'LHT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (321, '乐捷递', 'LJD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (322, '立即送', 'LJS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (323, '联运通', 'LYT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (324, '民邦快递', 'MB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (325, '迈达', 'MD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (326, '门对门快递', 'MDM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (327, '民航快递', 'MHKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (328, '美快', 'MK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (329, '明亮物流', 'MLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (330, '迈隆递运', 'MRDY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (331, '闽盛快递', 'MSKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (332, '能达速递', 'NEDA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (333, '南方传媒物流', 'NFCM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (334, '南京晟邦物流', 'NJSBWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (335, '荷兰邮政', 'NL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (336, '新顺丰', 'NSF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (337, 'OCS', 'OCS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (338, 'ONTRAC', 'ONTRAC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (339, '平安达腾飞快递', 'PADTF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (340, '泛捷快递', 'PANEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (341, '啪啪供应链', 'PAPA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (342, 'PCA Express', 'PCA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (343, '品骏快递', 'PJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (344, 'POSTEIBE', 'POSTEIBE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (345, '陪行物流', 'PXWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (346, '全晨快递', 'QCKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (347, '全球邮政', 'QQYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (348, '全日通快递', 'QRT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (349, '快客快递', 'QUICK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (350, '全信通', 'QXT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (351, '秦远海运', 'QYHY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (352, '七曜中邮', 'QYZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (353, '瑞典邮政', 'RDSE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (354, '如风达', 'RFD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (355, '瑞丰速递', 'RFEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (356, '日昱物流', 'RLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (357, '荣庆物流', 'RQ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (358, '日日顺物流', 'RRS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (359, '赛澳递', 'SAD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (360, '圣安物流', 'SAWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (361, '晟邦物流', 'SBWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (362, '速呈宅配', 'SCZPDS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (363, '速递e站', 'SDEZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (364, '山东海红', 'SDHH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (365, '首达速运', 'SDSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (366, '上大物流', 'SDWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (367, '顺丰速运', 'SF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (368, '顺丰国际', 'SFGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (369, '盛丰物流', 'SFWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (370, '林道国际', 'SHLDHY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (371, '盛辉物流', 'SHWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (372, '郑州速捷', 'SJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (373, '穗佳物流', 'SJWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (374, '穗空物流', 'SK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (375, 'SKYPOST', 'SKYPOST', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (376, '苏宁物流', 'SNWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (377, '闪送', 'SS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (378, '速通物流', 'ST', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (379, '盛通快递', 'STKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (380, '申通快递', 'STO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (381, '首通快运', 'STONG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (382, '申通快递国际单', 'STO_INTL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (383, '三态速递', 'STSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (384, '速腾快递', 'STWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (385, '速必达物流', 'SUBIDA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (386, '速尔快递', 'SURE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (387, '瑞士邮政', 'SWCH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (388, '顺心捷达', 'SX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (389, '山西红马甲', 'SXHMJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (390, '顺心捷达', 'SXJD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (391, '佳惠尔', 'SYJHE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (392, '世运快递', 'SYKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (393, '泰国138', 'TAILAND138', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (394, '台湾邮政', 'TAIWANYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (395, '华宇物流', 'TDHY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (396, '通和天下', 'THTX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (397, '特急送', 'TJS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (398, '腾林物流', 'TLWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (399, 'TNT快递', 'TNT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (400, '唐山申通', 'TSSTO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (401, '通用物流', 'TYWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (402, '全一快递', 'UAPEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (403, 'UBI', 'UBI', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (404, '优邦国际速运', 'UBONEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (405, '优速快递', 'UC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (406, 'UEQ Express', 'UEQ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (407, 'UEX', 'UEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (408, 'UPS', 'UPS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (409, '万国邮政', 'UPU', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (410, 'USPS美国邮政', 'USPS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (411, '中越国际物流', 'VCTRANS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (412, '启辰国际', 'VENUCIA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (413, '武汉同舟行', 'WHTZX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (414, '万家康', 'WJK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (415, '万家物流', 'WJWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (416, '中粮我买网', 'WM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (417, '维普恩', 'WPE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (418, '微特派', 'WTP', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (419, '温通物流', 'WTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (420, '万象物流', 'WXWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (421, '新邦物流', 'XBWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (422, '迅驰物流', 'XCWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (423, '迅达国际', 'XD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (424, '信丰物流', 'XFEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (425, '香港邮政', 'XGYZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (426, '新杰物流', 'XJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (427, '星空国际', 'XKGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (428, '喜来快递', 'XLKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (429, '祥龙运通', 'XLYT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (430, '鑫世锐达', 'XSRD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (431, '新元国际', 'XYGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (432, 'ADLER雄鹰国际速递', 'XYGJSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (433, '西邮寄', 'XYJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (434, '希优特', 'XYT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (435, '源安达快递', 'YADEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (436, '日本大和运输(Yamato)', 'YAMA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (437, '洋包裹', 'YBG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (438, '邮必佳', 'YBJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (439, '远成快运', 'YCSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (440, '远成物流', 'YCWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (441, '韵达速递', 'YD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (442, '义达国际物流', 'YDH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (443, '韵达快运', 'YDKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (444, '易达通', 'YDT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (445, '耀飞快递', 'YF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (446, '越丰物流', 'YFEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (447, '原飞航物流', 'YFHEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (448, '亚风快递', 'YFSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (449, '驭丰速运', 'YFSUYUN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (450, '一号线', 'YHXGJSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (451, '友家速递', 'YJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (452, '易境达', 'YJD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (453, '银捷速递', 'YJSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (454, '云路', 'YL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (455, '优联吉运', 'YLJY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (456, '亿领速运', 'YLSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (457, '壹米滴答', 'YMDD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (458, '玥玛速运', 'YMSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (459, '英脉物流', 'YMWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (460, 'YODEL', 'YODEL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (461, '余氏东风', 'YSDF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (462, '亿顺航', 'YSH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (463, '音素快运', 'YSKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (464, '易通达', 'YTD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (465, '一统飞鸿', 'YTFH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (466, '运通快递', 'YTKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (467, '圆通速递', 'YTO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (468, '圆通国际', 'YTOGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (469, '约旦邮政', 'YUEDANYOUZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (470, '运东西网', 'YUNDX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (471, '亿翔快递', 'YXKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (472, '宇鑫物流', 'YXWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (473, '鹰运', 'YYSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (474, '邮政国内标快', 'YZBK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (475, '包裹/平邮/挂号信', 'YZGN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (476, '邮政快递包裹', 'YZPY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (477, '一智通', 'YZT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (478, '一站通速运', 'YZTSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (479, '增益快递', 'ZENY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (480, '中驰物流', 'ZH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (481, '汇强快递', 'ZHQKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (482, '中骅物流', 'ZHWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (483, '宅急送', 'ZJS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (484, '芝麻开门', 'ZMKM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (485, '中欧快运', 'ZO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (486, '中睿速递', 'ZRSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (487, '准实快运', 'ZSKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (488, '众通快递', 'ZTE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (489, '中铁快运', 'ZTKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (490, '中通快递', 'ZTO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (491, '中通快运', 'ZTOKY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (492, '中铁物流', 'ZTWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (493, '中天万运', 'ZTWY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (494, '中外速运', 'ZWSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (495, '中外运速递', 'ZWYSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (496, '中邮快递', 'ZYKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (497, '中运全速', 'ZYQS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (498, '中邮物流', 'ZYWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (499, '增速海淘', 'ZYZOOM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (500, '爱购转运', 'ZY_AG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (501, '爱欧洲', 'ZY_AOZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (502, '澳世速递', 'ZY_AUSE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (503, 'AXO', 'ZY_AXO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (504, '澳转运', 'ZY_AZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (505, '八达网', 'ZY_BDA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (506, '蜜蜂速递', 'ZY_BEE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (507, '贝海速递', 'ZY_BH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (508, '百利快递', 'ZY_BL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (509, '斑马物流', 'ZY_BM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (510, '百通物流', 'ZY_BT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (511, '贝易购', 'ZY_BYECO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (512, '策马转运', 'ZY_CM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (513, '赤兔马转运', 'ZY_CTM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (514, 'CUL中美速递', 'ZY_CUL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (515, '德国海淘之家', 'ZY_DGHT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (516, '德运网', 'ZY_DYW', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (517, 'EFS POST', 'ZY_EFS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (518, '宜送转运', 'ZY_ESONG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (519, 'ETD', 'ZY_ETD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (520, '风驰快递', 'ZY_FCKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (521, '飞碟快递', 'ZY_FD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (522, '飞鸽快递', 'ZY_FG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (523, '风雷速递', 'ZY_FLSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (524, '风行快递', 'ZY_FX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (525, '风行速递', 'ZY_FXSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (526, '飞洋快递', 'ZY_FY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (527, '皓晨快递', 'ZY_HC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (528, '皓晨优递', 'ZY_HCYD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (529, '海带宝', 'ZY_HDB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (530, '汇丰美中速递', 'ZY_HFMZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (531, '豪杰速递', 'ZY_HJSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (532, '华美快递', 'ZY_HMKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (533, '360hitao转运', 'ZY_HTAO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (534, '海淘村', 'ZY_HTCUN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (535, '365海淘客', 'ZY_HTKE', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (536, '华通快运', 'ZY_HTONG', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (537, '海星桥快递', 'ZY_HXKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (538, '华兴速运', 'ZY_HXSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (539, '海悦速递', 'ZY_HYSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (540, 'LogisticsY', 'ZY_IHERB', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (541, '君安快递', 'ZY_JA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (542, '时代转运', 'ZY_JD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (543, '骏达快递', 'ZY_JDKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (544, '骏达转运', 'ZY_JDZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (545, '久禾快递', 'ZY_JH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (546, '金海淘', 'ZY_JHT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (547, '联邦转运FedRoad', 'ZY_LBZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (548, '领跑者快递', 'ZY_LPZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (549, '龙象快递', 'ZY_LX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (550, '量子物流', 'ZY_LZWL', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (551, '明邦转运', 'ZY_MBZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (552, '美国转运', 'ZY_MGZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (553, '美嘉快递', 'ZY_MJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (554, '美速通', 'ZY_MST', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (555, '美西转运', 'ZY_MXZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (556, '168 美中快递', 'ZY_MZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (557, '欧e捷', 'ZY_OEJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (558, '欧洲疯', 'ZY_OZF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (559, '欧洲GO', 'ZY_OZGO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (560, '全美通', 'ZY_QMT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (561, 'QQ-EX', 'ZY_QQEX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (562, '润东国际快线', 'ZY_RDGJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (563, '瑞天快递', 'ZY_RT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (564, '瑞天速递', 'ZY_RTSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (565, 'SCS国际物流', 'ZY_SCS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (566, '速达快递', 'ZY_SDKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (567, '四方转运', 'ZY_SFZY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (568, 'SOHO苏豪国际', 'ZY_SOHO', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (569, 'Sonic-Ex速递', 'ZY_SONIC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (570, '上腾快递', 'ZY_ST', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (571, '通诚美中快递', 'ZY_TCM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (572, '天际快递', 'ZY_TJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (573, '天马转运', 'ZY_TM', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (574, '滕牛快递', 'ZY_TN', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (575, 'TrakPak', 'ZY_TPAK', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (576, '太平洋快递', 'ZY_TPY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (577, '唐三藏转运', 'ZY_TSZ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (578, '天天海淘', 'ZY_TTHT', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (579, 'TWC转运世界', 'ZY_TWC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (580, '同心快递', 'ZY_TX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (581, '天翼快递', 'ZY_TY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (582, '同舟快递', 'ZY_TZH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (583, '天泽快递', 'ZY_TZKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (584, 'UCS合众快递', 'ZY_UCS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (585, '文达国际DCS', 'ZY_WDCS', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (586, '星辰快递', 'ZY_XC', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (587, '迅达快递', 'ZY_XDKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (588, '信达速运', 'ZY_XDSY', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (589, '先锋快递', 'ZY_XF', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (590, '新干线快递', 'ZY_XGX', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (591, '信捷转运', 'ZY_XJ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (592, '优购快递', 'ZY_YGKD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (593, '友家速递(UCS)', 'ZY_YJSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (594, '云畔网', 'ZY_YPW', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (595, '云骑快递', 'ZY_YQ', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (596, '优晟速递', 'ZY_YSSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (597, '易送网', 'ZY_YSW', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (598, '运淘美国', 'ZY_YTUSA', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (599, '至诚速递', 'ZY_ZCSD', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (600, '郑州建华', 'ZZJH', 0, '2022-04-26 19:13:05', 0);
INSERT INTO `sbc-setting`.`third_express_company` (`id`, `express_name`, `express_code`, `sell_platform_type`, `create_time`, `del_flag`) VALUES (601, '华强物流', 'hq568', 0, '2022-04-26 19:13:05', 0);
