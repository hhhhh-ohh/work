-- 电子卡券表
CREATE TABLE `sbc-marketing`.`electronic_coupon` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '电子卡券id',
  `coupon_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电子卡券名称',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除 0 否  1 是',
  `send_num` bigint(20) DEFAULT NULL COMMENT '已发送数',
  `not_send_num` bigint(20) DEFAULT NULL COMMENT '未发送',
  `invalid_num` bigint(20) DEFAULT NULL COMMENT '失效数',
  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`),
  KEY `idx_coupons_name` (`coupon_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='电子卡券表';

-- 电子卡密表
CREATE TABLE `sbc-marketing`.`electronic_card` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '卡密Id',
  `coupon_id` bigint(11) NOT NULL COMMENT '卡券id',
  `card_number` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡号',
  `card_password` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡密',
  `card_promo_code` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '优惠码',
  `card_state` tinyint(4) NOT NULL COMMENT '卡密状态  0、未发送 1、已发送 2、已失效',
  `sale_start_time` datetime DEFAULT NULL COMMENT '卡密销售开始时间',
  `sale_end_time` datetime DEFAULT NULL COMMENT '卡密销售结束时间',
  `record_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '批次id',
  `del_flag` tinyint(4) NOT NULL COMMENT '是否删除 0 否  1 是',
  PRIMARY KEY (`id`),
  KEY `idx_card_state` (`card_state`),
  KEY `idx_record_id` (`record_id`),
  KEY `idx_end_time` (`sale_end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='电子卡密表';

-- 卡密导入记录表
CREATE TABLE `sbc-marketing`.`electronic_import_record` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '批次id',
  `coupon_id` bigint(11) NOT NULL COMMENT '卡券id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `sale_start_time` datetime DEFAULT NULL COMMENT '销售开始时间',
  `sale_end_time` datetime DEFAULT NULL COMMENT '销售结束时间',
  PRIMARY KEY (`id`),
  KEY `idx_coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='卡密导入记录表';

-- 卡密发放记录表
CREATE TABLE `sbc-marketing`.`electronic_send_record` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '记录id',
  `order_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `sku_no` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sku编码',
  `sku_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `account` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收货人',
  `send_time` datetime DEFAULT NULL COMMENT '发放时间',
  `coupon_id` bigint(11) NOT NULL COMMENT '卡券id',
  `coupon_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡券名称',
  `card_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡密id',
  `card_content` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡券内容',
  `send_state` tinyint(4) NOT NULL COMMENT '发放状态  0、发放成功 1、发送中 2、发送失败',
  `fail_reason` tinyint(4) DEFAULT NULL COMMENT '发放失败原因  0、库存不足1、已过销售期 2、其他原因',
  `del_flag` tinyint(4) NOT NULL COMMENT '是否删除 0 否  1 是',
  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_send_state` (`send_state`),
  KEY `idx_consignee_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='卡密发放记录表';

-- 定时任务
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES ( 1, '0 10 * * * ?', '卡券各状态数量统计', '2022-02-09 18:08:02', '2022-02-09 18:08:02', '许云鹏', '', 'FIRST', 'electronicCardNumSyncJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-02-09 18:08:02', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES ( 1, '1 0 * * * ?', '卡密失效', '2022-02-09 18:02:59', '2022-02-09 18:06:17', '许云鹏', '', 'FIRST', 'electronicCardInvalidSyncJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-02-09 18:02:59', '');

-- 批量调价详情
ALTER TABLE `sbc-goods`.`price_adjustment_record_detail`
ADD COLUMN `goods_type` tinyint(2) DEFAULT NULL COMMENT '商品类型，0：实体商品，1：虚拟商品 2：电子卡券';

update `sbc-goods`.`price_adjustment_record_detail` set goods_type = 0;

-- 商品
update `sbc-goods`.goods set goods_type = 0 where goods_type is null;

ALTER TABLE `sbc-goods`.`goods`
MODIFY COLUMN `goods_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '商品类型，0：实体商品，1：虚拟商品 2：电子卡券';

ALTER TABLE `sbc-goods`.`goods_info`
ADD COLUMN `goods_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '商品类型，0：实体商品，1：虚拟商品 2：电子卡券',
ADD COLUMN `electronic_coupons_id` bigint(11) NULL COMMENT '电子卡券id';

INSERT INTO `sbc-goods`.freight_template_store (freight_temp_id, freight_temp_name, deliver_way, destination_area,destination_area_name, freight_type, satisfy_price, satisfy_freight,fixed_freight, store_id, company_info_id, default_flag, create_time,del_flag)
VALUES (-1, '虚拟商品默认模板', 1, '', '未被划分的配送地区自动归于默认运费', 1, 0.00, 0.00, 0.00, -1, -1, 1, NOW(), 0);

INSERT INTO `sbc-goods`.freight_template_goods_express (id, freight_temp_id, destination_area, destination_area_name,valuation_type, freight_start_num, freight_start_price,freight_plus_num, freight_plus_price, default_flag, create_time,del_flag)
VALUES (-1, -1, '', '未被划分的配送地区自动归于默认运费', 0, 1.00, 0.00, 1.00, 0.00, 1, NOW(), 0);

INSERT INTO `sbc-goods`.freight_template_goods (freight_temp_id, freight_temp_name, province_id, city_id, area_id,street_id, freight_free_flag, valuation_type, deliver_way,specify_term_flag, store_id, company_info_id, default_flag, create_time,del_flag)
VALUES (-1, '虚拟商品默认模板', null, null, null, null, 0, 0, 1, 0, -1, -1, 1, NOW(), 0);

-- 商品资料库
ALTER TABLE `sbc-goods`.`standard_goods`
ADD COLUMN `goods_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '商品类型，0：实体商品，1：虚拟商品 2：电子卡券';

ALTER TABLE `sbc-goods`.`standard_sku`
ADD COLUMN `goods_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '商品类型，0：实体商品，1：虚拟商品 2：电子卡券';

-- 优化商品营销查询性能
ALTER TABLE `sbc-marketing`.`marketing_scope`
ADD INDEX `iddex_scope_id`(`scope_id`);

ALTER TABLE `sbc-goods`.`groupon_goods_info`
ADD INDEX `index_goods_info_id`(`goods_info_id`),
ADD INDEX `index_time`(`start_time`, `end_time`);

ALTER TABLE `sbc-goods`.`flash_sale_goods`
ADD INDEX `idx_activity_full_time`(`activity_full_time`);

-- 会员积分过大超限sql脚本
alter table `sbc-customer`.customer_points_detail modify points bigint(10) not null comment '积分数量';
alter table `sbc-customer`.customer_points_detail modify points_available bigint(10) not null comment '积分余额';