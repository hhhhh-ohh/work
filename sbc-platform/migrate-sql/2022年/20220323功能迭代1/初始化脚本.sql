ALTER TABLE `sbc-setting`.`flash_sale_setting`
    ADD COLUMN `pre_time` int(4) NULL COMMENT '预热时间' AFTER `img_json`;

-- 拼团加入预热开始时间
ALTER TABLE `sbc-goods`.`groupon_goods_info`
  ADD COLUMN  `pre_start_time` datetime DEFAULT NULL COMMENT '预热开始日期';
-- 秒杀商品表新增限时抢购
ALTER TABLE `sbc-goods`.`flash_sale_goods`
 ADD COLUMN  `activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '活动ID',
  ADD COLUMN  `type` tinyint(4) DEFAULT '0' COMMENT '状态 1:限时购 0:秒杀',
  ADD COLUMN  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  ADD COLUMN  `end_time` datetime DEFAULT NULL COMMENT '结束日期',
  ADD COLUMN  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0:开始 1:暂停',
  ADD COLUMN  `pre_start_time` datetime DEFAULT NULL COMMENT '预热开始日期';
-- 刷新秒杀状态
update `sbc-goods`.`flash_sale_goods` set type = 0;
-- 创建限时购活动表
CREATE TABLE `sbc-goods`.`flash_promotion_activity` (
  `activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动ID',
  `activity_name` varchar(200) DEFAULT NULL COMMENT '活动名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '结束日期',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0:开始 1:暂停',
  `store_id` bigint(20) DEFAULT NULL COMMENT '商家ID',
  `pre_time` tinyint(4) unsigned DEFAULT '0' COMMENT '预热时间(0-168)',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标志，0:未删除 1:已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='限时购活动表';

-- 营销插件配置加入限时购
UPDATE `sbc-marketing`.`marketing_plugin_config` SET `sort` = `sort` + 1 WHERE `sort` > 5;
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`marketing_type`, `sort`, `coexist`, `description`) VALUES ('FLASH_PROMOTION', 6, 'COUPON', '限时购');

-- 魔方商品分类表
CREATE TABLE `sbc-goods`.`xsite_goods_cate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `page_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '页面唯一值',
  `cate_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类uuid',
  `goods_info_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '商品skuIds, 限制500条',
  `goods_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '商品spuIds, 限制500条',
  PRIMARY KEY (`id`),
  KEY `idx_cate_uuid` (`cate_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='魔方商品分类表';

-- 调价详情增加审核状态
ALTER TABLE `sbc-goods`.`price_adjustment_record_detail`
    ADD COLUMN `audit_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '审核状态,0:未审核1 审核通过2审核失败' AFTER `confirm_flag`,
    ADD COLUMN `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间' AFTER `audit_status`,
    ADD COLUMN `audit_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核驳回理由' AFTER `audit_time`;

-- 拼团活动增加预热时间
ALTER TABLE `sbc-marketing`.`groupon_activity`
    ADD COLUMN `pre_time` int(20) NULL DEFAULT NULL COMMENT '预热时间' AFTER `update_time`;

-- 限时抢购海报
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('flash_goods_sale', 'flash_goods_sale_poster', '限时抢购海报设置', NULL, 1, NULL, '2022-02-10 14:58:26', '2022-02-10 14:58:29', 0);

-- 用户表增加用户头像
ALTER TABLE `sbc-customer`.`customer`
    ADD COLUMN `head_img` varchar(255) NULL COMMENT '用户头像' AFTER `pay_safe_level`;

update `sbc-marketing`.`marketing_plugin_config`
set `coexist` = 'POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE'
where `marketing_type`='COUPON';

ALTER TABLE `sbc-setting`.`popular_search_terms`
    MODIFY COLUMN `pc_landing_page` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL AFTER `related_landing_page`;

-- 修改优惠券活动店铺类型
UPDATE `sbc-marketing`.`coupon_activity` SET `platform_flag` = 1 WHERE activity_type = 5;

-- 商品退单权限
UPDATE `sbc-setting`.`authority` SET `authority_url` = '/return/audit/**' WHERE `authority_title` = '单个审核';