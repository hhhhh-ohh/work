ALTER TABLE `sbc-customer`.`customer_invoice`
    ADD COLUMN `invoice_style` tinyint(2) NULL DEFAULT 0 COMMENT '发票种类：0-增值税专用发票；1-个人发票；2-公司发票' AFTER `delete_person`;

ALTER TABLE `sbc-goods`.`goods_cate`
    ADD COLUMN `contains_virtual` tinyint(2) NULL DEFAULT 1 COMMENT '是否可以包含虚拟商品0-不可以；1-可以' AFTER `is_parent_points_rate`;

-- 帮助中心文章信息
CREATE TABLE `sbc-setting`.`help_center_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `article_title` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章标题',
  `article_cate_id` bigint(20) NOT NULL COMMENT '文章分类id',
  `article_content` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '文章内容',
  `article_type` tinyint(4) NOT NULL COMMENT '文章状态，0:展示，1:隐藏',
  `view_num` bigint(11) DEFAULT NULL COMMENT '查看次数',
  `solve_num` bigint(11) DEFAULT '0' COMMENT '解决次数',
  `unresolved_num` bigint(11) DEFAULT '0' COMMENT '未解决次数',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `article_title_index` (`article_title`) USING BTREE COMMENT '文章标题索引'
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帮助中心文章信息';

-- 帮助中心文章分类信息
CREATE TABLE `sbc-setting`.`help_center_article_cate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `cate_name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `cate_sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `default_cate` tinyint(4) DEFAULT NULL COMMENT '是否是默认精选分类 0：否，1：是',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帮助中心文章分类信息';

-- 帮助中心文章记录
CREATE TABLE `sbc-setting`.`help_center_article_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员id',
  `solve_type` tinyint(4) NOT NULL COMMENT '解决状态  0：已解决，1：未解决',
  `solve_time` datetime DEFAULT NULL COMMENT '解决时间',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `article_id_index` (`article_id`) USING BTREE COMMENT '文章id索引',
  KEY `customer_id_index` (`customer_id`) USING BTREE COMMENT '会员id索引'
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帮助中心文章记录';

-- 库存预警信息表
CREATE TABLE `sbc-setting`.`stock_warning` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sku_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品skuId',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺Id',
  `is_warning` tinyint(4) DEFAULT NULL COMMENT '是否预警,0:未预警 1:已预警',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存预警信息记录';


ALTER TABLE `sbc-setting`.`store_message_node_setting`
    ADD COLUMN `warning_stock` bigint(20) NULL DEFAULT 1 COMMENT 'sku库存预警值' AFTER `update_time`;

INSERT INTO `sbc-setting`.`help_center_article_cate`(`cate_name`, `cate_sort`, `default_cate`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( '精选', 1, 1, 0, '2023-03-17 15:07:09', 'system', '2023-03-17 15:07:25', 'system');

INSERT INTO `sbc-setting`.`store_message_node`(`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (35, '商品', '商品库存预警', '正常售卖中的商品任意一SKU库存低于{1}时', 'f_goods_sku_warn_stock', 'GOODS_SKU_WARN_STOCK', '{商品名称}{商品SKU编码}，库存不足{X}件，点击去查看', 1, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node`(`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (36, '商品', '商品库存预警', '正常售卖中的商品任意一SKU库存低于{1}时', 'f_goods_sku_warn_stock', 'GOODS_SKU_WARN_STOCK', '{商品名称}{商品SKU编码}，库存不足{X}件，点击去查看', 1, 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('weak_passwords', 'weak_passwords', '弱密码库', NULL, 1, 'admin12ab,love1314', '2023-03-29 10:37:23', '2023-03-29 10:37:27', 0);

delete from `sbc-marketing`.marketing_plugin_config;
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (1, 'COUPON', 1, 'BUY_CYCLE,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '优惠券');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (2, 'BUY_CYCLE', 2, 'COUPON,PAYING_MEMBER,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN', '周期购');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (3, 'POINT_AND_CASH', 3, 'COUPON,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '积分+现金');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (4, 'APPOINTMENT_SALE', 4, 'COUPON,RETURN', '预约');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (5, 'BOOKING_SALE', 5, 'COUPON,RETURN', '预售');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (6, 'FLASH_SALE', 6, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (7, 'FLASH_PROMOTION', 7, 'COUPON', '限时购');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (8, 'DISTRIBUTION', 8, 'COUPON,RETURN', '分销');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (9, 'ENTERPRISE_PRICE', 9, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,PREFERENTIAL', '企业价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (10, 'GROUPON', 10, 'COUPON,RETURN', '拼团');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (11, 'CUSTOMER_PRICE', 11, 'COUPON,BUY_CYCLE,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '会员价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (12, 'CUSTOMER_LEVEL', 12, 'COUPON,BUY_CYCLE,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,PAYING_MEMBER,NEW_COMER_COUPON,PREFERENTIAL', '会员等级价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (13, 'PAYING_MEMBER', 13, 'COUPON,BUY_CYCLE,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '付费会员价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (14, 'REDUCTION', 14, 'COUPON,BUY_CYCLE,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满减');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (15, 'DISCOUNT', 15, 'COUPON,BUY_CYCLE,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满折');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (16, 'GIFT', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满赠');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (17, 'BUYOUT_PRICE', 17, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '打包一口价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (18, 'HALF_PRICE_SECOND_PIECE', 18, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,NEW_COMER_COUPON', '第二件半价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (19, 'RETURN', 19, 'COUPON,BUY_CYCLE,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满返');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (20, 'NEW_COMER_COUPON', 20, 'POINT_AND_CASH,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '新人专享券');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (54, 'PREFERENTIAL', 21, 'COUPON,POINT_AND_CASH,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN', '加价购');

update `sbc-setting`.base_config set version = 'SBC V5.3.0';
