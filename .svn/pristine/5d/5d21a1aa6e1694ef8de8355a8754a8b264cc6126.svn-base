ALTER TABLE `sbc-goods`.`goods_info`
ADD COLUMN `attribute_size` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '尺码' AFTER `provider_quick_order_no`,
ADD COLUMN `attribute_season` int NULL DEFAULT NULL COMMENT '0-春秋装 1-夏装 2-冬装' AFTER `attribute_size`,
ADD COLUMN `attribute_goods_type` int NULL DEFAULT NULL COMMENT '0-校服服饰 1-非校服服饰 2-自营产品' AFTER `attribute_season`,
ADD COLUMN `attribute_sale_type` int NULL DEFAULT NULL COMMENT '0-老款 1-新款' AFTER `attribute_goods_type`,
ADD COLUMN `attribute_sale_region` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '售卖地区' AFTER `attribute_sale_type`,
ADD COLUMN `attribute_school_section` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学段' AFTER `attribute_sale_region`,
ADD COLUMN `attribute_price_silver` decimal(20, 2) NULL DEFAULT NULL COMMENT '银卡价格' AFTER `attribute_school_section`,
ADD COLUMN `attribute_price_gold` decimal(20, 2) NULL DEFAULT NULL COMMENT '金卡价格' AFTER `attribute_price_silver`,
ADD COLUMN `attribute_price_diamond` decimal(20, 2) NULL DEFAULT NULL COMMENT '钻石卡价格' AFTER `attribute_price_gold`,
ADD COLUMN `attribute_price_discount` decimal(20, 2) NULL DEFAULT NULL COMMENT '折扣价格' AFTER `attribute_price_diamond`;