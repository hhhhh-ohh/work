CREATE TABLE `s2b_statistics`.`replay_marketing_buyout_price_level` (
  `reduction_level_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '打包级别Id',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销Id',
  `full_amount` decimal(12,2) DEFAULT NULL COMMENT '满金额',
  `choice_count` bigint(5) DEFAULT NULL COMMENT '任选数量',
  PRIMARY KEY (`reduction_level_id`),
  KEY `marketing_id` (`marketing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=676 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='营销打包关联表';
INSERT into `s2b_statistics`.`replay_marketing_buyout_price_level` SELECT * from `sbc-marketing`.`marketing_buyout_price_level`;

CREATE TABLE `s2b_statistics`.`replay_marketing_half_price_second_piece` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销Id',
  `number` int(11) DEFAULT NULL COMMENT '件数',
  `discount` decimal(2,1) DEFAULT NULL COMMENT '折扣',
  PRIMARY KEY (`id`),
  KEY `marketing_id` (`marketing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=366 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第二件半价关联表';
INSERT into `s2b_statistics`.`replay_marketing_half_price_second_piece` SELECT * from `sbc-marketing`.`marketing_half_price_second_piece`;


CREATE TABLE `s2b_statistics`.`replay_marketing_suits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `marketing_id` bigint(20) NOT NULL COMMENT '促销id',
  `main_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `suits_price` decimal(10,2) DEFAULT NULL COMMENT '套餐价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组合商品主表';
INSERT into `s2b_statistics`.`replay_marketing_suits` SELECT * from `sbc-marketing`.`marketing_suits`;


CREATE TABLE `s2b_statistics`.`replay_marketing_suits_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `suits_id` bigint(20) DEFAULT NULL COMMENT '组合id',
  `marketing_id` bigint(20) DEFAULT NULL COMMENT '促销活动id',
  `sku_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discount_price` decimal(10,2) DEFAULT NULL COMMENT '单个优惠价格（优惠多少）',
  `num` bigint(20) DEFAULT NULL COMMENT 'sku数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=712 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组合活动-商品sku关联表';
INSERT into `s2b_statistics`.`replay_marketing_suits_sku` SELECT * from `sbc-marketing`.`marketing_suits_sku`;

ALTER TABLE `s2b_statistics`.`replay_trade`
    ADD COLUMN `suit_marketing_flag` tinyint(4) NULL COMMENT '是否组合套装 0:否 1:是';

ALTER TABLE `s2b_statistics`.`replay_trade_item`
    ADD COLUMN `suits_id` bigint(20) NULL COMMENT '组合购活动id';

    ALTER TABLE `s2b_statistics`.`replay_trade`
    ADD COLUMN `suits_id` bigint(20) NULL COMMENT '组合购活动id';

 ALTER TABLE `s2b_statistics`.`replay_marketing`
ADD COLUMN `participate_type` tinyint(4) NULL DEFAULT NULL COMMENT '参与门店是：0全部，1部分' AFTER `update_time_stamp`,
ADD COLUMN `plugin_type` tinyint(4) NULL DEFAULT NULL COMMENT '营销类型：0店铺，1跨境(不使用)，2门店' AFTER `participate_type`,
ADD COLUMN `audit_status` tinyint(4) NULL DEFAULT NULL COMMENT '门店营销审核状态：0未审核1审核通过2审核失败' AFTER `plugin_type`,
ADD COLUMN `refuse_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核失败原因' AFTER `audit_status`,
ADD COLUMN `store_type` tinyint(4) NULL DEFAULT NULL COMMENT '参与店铺是：0全部，1指定店铺' AFTER `refuse_reason`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`marketing_id`) USING BTREE;

ALTER TABLE `s2b_statistics`.`marketing_situation_month`
MODIFY COLUMN `marketing_type` tinyint(4) NULL DEFAULT NULL COMMENT '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售 11：砍价' AFTER `month`;
ALTER TABLE `s2b_statistics`.`marketing_situation_day`
MODIFY COLUMN `marketing_type` tinyint(4) NULL DEFAULT NULL COMMENT '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售 11：砍价' AFTER `id`;
ALTER TABLE `s2b_statistics`.`marketing_situation_seven`
MODIFY COLUMN `marketing_type` tinyint(4) NULL DEFAULT NULL COMMENT '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售 11：砍价' AFTER `id`;
ALTER TABLE `s2b_statistics`.`marketing_situation_thirty`
MODIFY COLUMN `marketing_type` tinyint(4) NULL DEFAULT NULL COMMENT '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售 11：砍价' AFTER `id`;
