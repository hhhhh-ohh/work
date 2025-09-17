
create table `sbc-goods`.`goods_commission_config`
(
    `id` varchar(45) NOT NULL COMMENT '主键',
    `store_id` bigint(20) NOT NULL COMMENT '商家Id',
    `syn_price_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '设价类型：0.智能设价 1.手动设价',
    `add_rate` decimal(20,2) DEFAULT NULL COMMENT '加价比例',
    `under_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '低价是否自动下架：0.关 1.开',
    `info_syn_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品信息自动同步：0.手动 1.自动',
    `freight_bear_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '代销商品运费承担：1.买家 2.卖家',
    `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `create_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `update_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品代销设置';

create table `sbc-goods`.`goods_commission_price_config`
(
    `id` varchar(45) NOT NULL COMMENT '主键',
    `store_id` bigint(20) NOT NULL COMMENT '商家Id',
    `target_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '代销加价目标类型 ：0.类目 1.SKU',
    `target_id` varchar(45) NOT NULL COMMENT '主键',
    `add_rate` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '加价比例',
    `enable_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否启用 ：0.否 1.是',
    `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `create_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `update_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `index_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品代销价格设置';

create table `sbc-goods`.`provider_goods_edit_detail`
(
    `id` varchar(45) NOT NULL comment '主键',
    `goods_id` varchar(45) NOT NULL COMMENT '商品Id',
    `endit_type` tinyint(4) NOT NULL DEFAULT 0 comment '操作类型：0.商品信息变更 1.价格变更 2.状态变更 3.其他变更',
    `endit_content` varchar(100) NOT NULL comment '操作内容',
    `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `create_person` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `update_person` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `index_goods_id` (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品变更记录';


CREATE TABLE `sbc-goods`.`supplier_commission_goods` (
                                             `id` varchar(45) NOT NULL COMMENT '主键',
                                             `store_id` bigint(20) NOT NULL COMMENT '商家Id',
                                             `goods_id` varchar(45) NOT NULL COMMENT '商品Id',
                                             `provider_goods_id` varchar(45) NOT NULL,
                                             `syn_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已同步：0：否，1：是',
                                             `update_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有更新：0：否，1：是',
                                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                             `create_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                             `update_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                             `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                             PRIMARY KEY (`id`),
                                             UNIQUE KEY `index_goods_id_pgoods_id` (`goods_id`,`provider_goods_id`) USING BTREE,
                                             KEY `index_store_id_pgoods_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家与代销商品关联表';

alter table `sbc-marketing`.`coupon_activity`
	add scan_type tinyint null comment '1:已扫描 0或空:未扫描';

alter table `sbc-marketing`.`coupon_activity`
	add scan_version char(25)  comment '精准发券任务扫描版本';


ALTER TABLE `s2b_statistics`.`goods_day`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_month`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_recent_seven`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_recent_thirty`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;


ALTER TABLE `s2b_statistics`.`goods_cate_day`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_cate_month`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_cate_recent_seven`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_cate_recent_thirty`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_cate_day`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_cate_month`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_cate_recent_seven`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_cate_recent_thirty`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_day`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_month`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_recent_seven`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_recent_thirty`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_day`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_month`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_recent_seven`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_store_cate_recent_thirty`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;


ALTER TABLE `s2b_statistics`.`goods_brand_day`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_brand_month`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_brand_recent_seven`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_brand_recent_thirty`
ADD COLUMN `CUSTOMER_COUNT` decimal(10, 0) NULL COMMENT '下单人数' AFTER `CREATE_TM`;

ALTER TABLE `s2b_statistics`.`goods_brand_day`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_brand_month`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_brand_recent_seven`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

ALTER TABLE `s2b_statistics`.`goods_brand_recent_thirty`
ADD COLUMN `CUSTOMER_PAY_COUNT` decimal(10, 0) NULL COMMENT '付款人数' AFTER `CUSTOMER_COUNT`;

alter table `sbc-goods`.`price_adjustment_record`
    add scan_type tinyint null comment '1:已扫描 0或空:未扫描';

-- 定时任务日志表
CREATE TABLE `sbc-setting`.`task_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `task_biz_type` tinyint(255) NOT NULL COMMENT '业务类型，0精准发券 1消息发送 2商品调价',
    `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
    `task_result` tinyint(2) NOT NULL COMMENT '任务执行结果，0执行失败 1执行成功',
    `remarks` varchar(255) NOT NULL COMMENT '任务备注信息',
    `stack_message` text COMMENT '异常堆栈信息',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- 扫描各业务表，并分发MQ消息定时任务
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 */28 * * * ?', '扫描各业务表，并分发MQ消息定时任务', '2021-09-10 17:24:39', '2021-09-13 14:03:14', '马连峰', '', 'FIRST', 'ScanAndSendTaskJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-09-10 17:24:39', '');

ALTER TABLE `sbc-account`.`settlement`
ADD COLUMN `third_plat_form_freight` DECIMAL(20,2) DEFAULT 0.00 COMMENT '供货运费' AFTER `PROVIDER_PRICE`;
-- 优惠券活动配置表添加scene字段
ALTER TABLE `sbc-marketing`.`coupon_activity_config`
ADD COLUMN `scene` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成小程序二维码所需的scene参数（16位UUID）';

ALTER TABLE `sbc-goods`.`goods`
ADD COLUMN `is_independent` tinyint(4) DEFAULT 0 COMMENT '商家端编辑供应商商品判断页面是否是独立设置价格 0 ：否  1：是' AFTER `STORE_TYPE`;

ALTER TABLE `s2b_statistics`.`replay_trade_item`
ADD COLUMN `split_price` decimal(10, 2) NULL COMMENT '除去营销优惠金额后的商品均摊价' AFTER `level_price`;


-- 预约预售表从sbc-goods库迁移到了sbc-marketing
CREATE TABLE `sbc-marketing`.`appointment_sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(100) NOT NULL COMMENT '活动名称',
  `store_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商户id',
  `appointment_type` tinyint(4) NOT NULL COMMENT '预约类型 0：不预约不可购买  1：不预约可购买',
  `appointment_start_time` datetime NOT NULL COMMENT '预约开始时间',
  `appointment_end_time` datetime NOT NULL COMMENT '预约结束时间',
  `snap_up_start_time` datetime NOT NULL COMMENT '抢购开始时间',
  `snap_up_end_time` datetime NOT NULL COMMENT '抢购结束时间',
  `deliver_time` varchar(10) NOT NULL COMMENT '发货日期 2020-01-10',
  `join_level` varchar(500) NOT NULL COMMENT '参加会员  -1:全部客户 0:全部等级 other:其他等级',
  `join_level_type` tinyint(4) NOT NULL COMMENT '是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `pause_flag` tinyint(4) DEFAULT '0' COMMENT '是否暂停 0:否 1:是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `appointment_store_index` (`store_id`),
  KEY `appointment_type_index` (`appointment_type`),
  KEY `idx_appointment_start_time` (`appointment_start_time`),
  KEY `idx_appointment_end_time` (`appointment_end_time`),
  KEY `idx_snap_up_start_time` (`snap_up_start_time`),
  KEY `idx_snap_up_end_time` (`snap_up_end_time`),
  KEY `idx_deliver_time` (`deliver_time`),
  KEY `idx_join_level` (`join_level`(191)),
  KEY `idx_join_level_type` (`join_level_type`),
  KEY `idx_del_flag` (`del_flag`),
  KEY `idx_pause_flag` (`pause_flag`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预约抢购表';

CREATE TABLE `sbc-marketing`.`appointment_sale_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appointment_sale_id` bigint(20) NOT NULL COMMENT '预约id',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `goods_info_id` varchar(32) NOT NULL COMMENT 'skuID',
  `goods_id` varchar(32) NOT NULL COMMENT 'spuID',
  `price` decimal(10,2) DEFAULT NULL COMMENT '预约价',
  `appointment_count` int(8) NOT NULL DEFAULT '0' COMMENT '预约数量',
  `buyer_count` int(8) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `appointment_goods_store_index` (`store_id`),
  KEY `appointment_goods_info_id_index` (`goods_info_id`),
  KEY `appointment_sale_id_index` (`appointment_sale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预约商品表';

CREATE TABLE `sbc-marketing`.`booking_sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(100) NOT NULL COMMENT '活动名称',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `booking_type` tinyint(4) NOT NULL COMMENT '预售类型 0：全款预售  1：定金预售',
  `hand_sel_start_time` datetime DEFAULT NULL COMMENT '定金支付开始时间',
  `hand_sel_end_time` datetime DEFAULT NULL COMMENT '定金支付结束时间',
  `tail_start_time` datetime DEFAULT NULL COMMENT '尾款支付开始时间',
  `tail_end_time` datetime DEFAULT NULL COMMENT '尾款支付结束时间',
  `booking_start_time` datetime DEFAULT NULL COMMENT '预售开始时间',
  `booking_end_time` datetime DEFAULT NULL COMMENT '预售结束时间',
  `start_time` datetime NOT NULL COMMENT '预售活动总开始时间',
  `end_time` datetime NOT NULL COMMENT '预售活动总结束时间',
  `deliver_time` varchar(10) NOT NULL COMMENT '发货日期 2020-01-10',
  `join_level` varchar(500) NOT NULL COMMENT '参加会员  -1:全部客户 0:全部等级 other:其他等级',
  `join_level_type` tinyint(4) NOT NULL COMMENT '是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `pause_flag` tinyint(4) DEFAULT '0' COMMENT '是否暂停 0:否 1:是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `booking_store_index` (`store_id`),
  KEY `booking_type_index` (`booking_type`),
  KEY `idx_hand_sel_start_time` (`hand_sel_start_time`),
  KEY `idx_join_level` (`join_level`(191)),
  KEY `idx_del_flag` (`del_flag`),
  KEY `idx_deliver_time` (`deliver_time`),
  KEY `idx_pause_flag` (`pause_flag`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_hand_sel_end_time` (`hand_sel_end_time`),
  KEY `idx_tail_start_time` (`tail_start_time`),
  KEY `idx_tail_end_time` (`tail_end_time`),
  KEY `idx_booking_start_time` (`booking_start_time`),
  KEY `idx_booking_end_time` (`booking_end_time`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_join_level_type` (`join_level_type`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预售表';

CREATE TABLE `sbc-marketing`.`booking_sale_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `booking_sale_id` bigint(20) NOT NULL COMMENT '预售id',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `goods_info_id` varchar(32) NOT NULL COMMENT 'skuID',
  `goods_id` varchar(32) NOT NULL COMMENT 'spuID',
  `hand_sel_price` decimal(10,2) DEFAULT NULL COMMENT '定金',
  `inflation_price` decimal(10,2) DEFAULT NULL COMMENT '膨胀价格',
  `booking_price` decimal(10,2) DEFAULT NULL COMMENT '预售价',
  `booking_count` int(8) DEFAULT NULL COMMENT '预售数量',
  `can_booking_count` int(8) DEFAULT NULL COMMENT '实际可售数量',
  `hand_sel_count` int(8) NOT NULL DEFAULT '0' COMMENT '定金支付数量',
  `tail_count` int(8) NOT NULL DEFAULT '0' COMMENT '尾款支付数量',
  `pay_count` int(8) NOT NULL DEFAULT '0' COMMENT '全款支付数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `booking_goods_info_id_index` (`goods_info_id`),
  KEY `booking_goods_store_index` (`store_id`),
  KEY `booking_sale_id_index` (`booking_sale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预售商品表';


