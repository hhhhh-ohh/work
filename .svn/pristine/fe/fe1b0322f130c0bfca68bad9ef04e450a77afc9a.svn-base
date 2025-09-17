-- base_config表中添加注销协议字段
ALTER TABLE `sbc-setting`.`base_config` ADD cancellation_content longtext COMMENT '注销协议';

-- 修改注册协议长度
ALTER TABLE `sbc-setting`.`base_config`
    MODIFY COLUMN `register_content` longtext  NULL COMMENT '注册协议' AFTER `supplier_website`;

-- 添加logo地址字段
ALTER TABLE `sbc-setting`.`base_config` ADD loading_url text COMMENT '加载地址';

-- 创建注销原因表
CREATE TABLE `sbc-setting`.`cancellation_reason_tbl`  (
  `id` varchar(32)  NOT NULL COMMENT '主键',
  `reason` varchar(20)  NULL DEFAULT NULL COMMENT '原因',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `create_person` varchar(32)  NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_person` varchar(32)  NULL DEFAULT NULL,
  `sort` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = '注销原因表' ROW_FORMAT = Dynamic;


-- 注销原因表初始话数据
INSERT INTO `sbc-setting`.`cancellation_reason_tbl`(`id`, `reason`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `sort`) VALUES ('2c9384df7fd9d432017fd9e2ff870000', '有其他常用账号', 0, '2022-04-02 16:57:20', NULL, NULL, NULL, 1);
INSERT INTO `sbc-setting`.`cancellation_reason_tbl`(`id`, `reason`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `sort`) VALUES ('2c9384df7fd9d432017fd9e4720f0001', '隐私考虑', 0, '2022-04-02 16:57:51', NULL, NULL, NULL, 2);
INSERT INTO `sbc-setting`.`cancellation_reason_tbl`(`id`, `reason`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `sort`) VALUES ('2c9384df7fd9d432017fd9e571000002', '不想接收营销信息', 0, '2022-04-02 16:57:58', NULL, NULL, NULL, 3);
INSERT INTO `sbc-setting`.`cancellation_reason_tbl`(`id`, `reason`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `sort`) VALUES ('2c9384df7fd9d432017fd9e571000003', '更换手机号了', 0, '2022-04-02 16:58:13', NULL, NULL, NULL, 4);
INSERT INTO `sbc-setting`.`cancellation_reason_tbl`(`id`, `reason`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `sort`) VALUES ('2c9384df7fd9d432017fd9e571000006', '其他', 0, '2022-04-02 16:59:37', NULL, NULL, NULL, 5);

-- 满返脚本
CREATE TABLE `sbc-marketing`.`marketing_full_return_detail` (
  `return_detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '满返细节Id',
  `return_level_id` bigint(20) NOT NULL COMMENT '满返多级促销Id',
  `coupon_id` varchar(32)  DEFAULT NULL,
  `coupon_num` bigint(5) NOT NULL COMMENT '赠品数量',
  `marketing_id` bigint(20) NOT NULL COMMENT '满返ID',
  PRIMARY KEY (`return_detail_id`),
  KEY `idx_return_detail_marketingid_levelid` (`marketing_id`,`return_level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='满返细节表';

CREATE TABLE `sbc-marketing`.`marketing_full_return_level` (
  `return_level_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '满返多级促销Id',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销Id',
  `full_amount` decimal(12,2) DEFAULT NULL COMMENT '满金额赠',
  PRIMARY KEY (`return_level_id`),
  KEY `idx_marketing_id` (`marketing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='满返关联表';

-- 会员表新增会员注销标识
ALTER TABLE `sbc-customer`.`customer`
    ADD COLUMN `log_out_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '注销状态 0:正常 1:注销中 2:已注销' AFTER `head_img`,
    ADD COLUMN `cancellation_reason_id` varchar(32)  NULL DEFAULT NULL COMMENT '注销原因Id' AFTER `log_out_status`,
    ADD COLUMN `cancellation_reason` varchar(50)  NULL DEFAULT NULL COMMENT '注销原因' AFTER `cancellation_reason_id`;

-- 魔方支付成功页配置
CREATE TABLE `sbc-setting`.`pay_advertisement` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付广告id',
                                     `advertisement_name` varchar(255) NOT NULL COMMENT '广告名称',
                                     `start_time` datetime NOT NULL COMMENT '投放开始时间',
                                     `end_time` datetime NOT NULL COMMENT '投放结束时间',
                                     `store_type` tinyint(4) NOT NULL COMMENT '1:全部店铺  2:部分店铺\r\n',
                                     `order_price` decimal(10,2) DEFAULT '0.00' COMMENT '订单金额',
                                     `advertisement_img` text NOT NULL COMMENT '广告图片',
                                     `join_level` varchar(500) NOT NULL COMMENT '目标客户 0:全平台客户 other:指定等级',
                                     `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记  0：正常，1：删除',
                                     `is_pause` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否暂停（1：暂停，0：正常）',
                                     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                     `create_person` varchar(32) DEFAULT NULL,
                                     `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                     `update_person` varchar(32) DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COMMENT='支付广告配置表';

-- 支付成功配置关联门店
CREATE TABLE `sbc-setting`.`pay_advertisement_store` (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付广告门店关联id',
                                           `pay_advertisement_id` bigint(20) NOT NULL COMMENT '支付广告id',
                                           `store_id` bigint(20) NOT NULL COMMENT '门店id',
                                           PRIMARY KEY (`id`),
                                           KEY `pay_advertisement_id` (`pay_advertisement_id`) USING BTREE,
                                           KEY `store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sbc-marketing`.`marketing_full_return_store` (
  `return_store_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '满返多级促销Id',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销Id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '商铺Id',
  PRIMARY KEY (`return_store_id`),
  KEY `idx_marketing_id` (`marketing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='满返店铺关联表';


-- 营销表增加字段,重新调整备注
ALTER TABLE `sbc-marketing`.`marketing`
MODIFY COLUMN `marketing_type` tinyint(4) NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐 7满返' AFTER `marketing_name`,
MODIFY COLUMN `sub_type` tinyint(4) NOT NULL COMMENT '促销子类型 0:满金额减 1:满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠 6:一口价 7:第二件半价 8:组合商品  9:满返' AFTER `marketing_type`,
ADD COLUMN `store_type` tinyint(4) NULL COMMENT '参与店铺是：0全部，1指定店铺' AFTER `refuse_reason`;

-- 营销配置脚本重新生成
delete from `sbc-marketing`.`marketing_plugin_config`;

INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('COUPON', 1, 'POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '优惠券');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('POINT_AND_CASH', 2, 'COUPON,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '积分+现金');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('APPOINTMENT_SALE', 3, 'COUPON,RETURN', '预约');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('BOOKING_SALE', 4, 'COUPON,RETURN', '预售');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('FLASH_SALE', 5, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('FLASH_PROMOTION', 6, 'COUPON', '限时购');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('DISTRIBUTION', 7, 'COUPON,RETURN', '分销');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('ENTERPRISE_PRICE', 8, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '企业价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('GROUPON', 9, 'COUPON,RETURN', '拼团');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('CUSTOMER_PRICE', 11, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('CUSTOMER_LEVEL', 12, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员等级价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('REDUCTION', 13, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满减');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('DISCOUNT', 14, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满折');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('GIFT', 15, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满赠');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('BUYOUT_PRICE', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,HALF_PRICE_SECOND_PIECE', '打包一口价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('HALF_PRICE_SECOND_PIECE', 17, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE', '第二件半价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('RETURN', 18, 'COUPON,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满返');
-- vop_goods_temp添加最低起售量
ALTER TABLE `sbc-empower`.`vop_goods_temp` ADD lowest_buy bigint(20) COMMENT '最低起售量';
-- 会员资金表，删除索引
ALTER TABLE `sbc-account`.`customer_funds` DROP INDEX `index_customer_account`;

