CREATE TABLE `sbc-marketing`.`marketing_preferential_detail` (
                                                                 `preferential_detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                 `preferential_level_id` bigint(20) NOT NULL COMMENT '加价购档次阶梯ID',
                                                                 `goods_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL comment '商品ID',
                                                                 `preferential_amount` decimal(8,2) NOT NULL COMMENT '加价购活动金额',
                                                                 `marketing_id` bigint(20) NOT NULL COMMENT '加价购活动ID',
                                                                 PRIMARY KEY (`preferential_detail_id`),
                                                                 KEY `idx_marketingid_levelid` (`marketing_id`,`preferential_level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1328 DEFAULT CHARSET=utf8 COMMENT='加价购活动关联商品';

CREATE TABLE `sbc-marketing`.`marketing_preferential_level` (
                                                                `preferential_level_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                `marketing_id` bigint(20) NOT NULL COMMENT '加价购活动ID',
                                                                `full_amount` decimal(8,2) DEFAULT NULL COMMENT '满金额',
                                                                `full_count` bigint(5) DEFAULT NULL COMMENT '满数量',
                                                                `preferential_type` tinyint(4) NOT NULL COMMENT '0:可全选  1：选一个',
                                                                PRIMARY KEY (`preferential_level_id`),
                                                                KEY `idx_marketing_id` (`marketing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=860 DEFAULT CHARSET=utf8 COMMENT='加价购档次阶梯';

TRUNCATE TABLE `sbc-marketing`.`marketing_plugin_config`;

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
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (14, 'REDUCTION', 15, 'COUPON,BUY_CYCLE,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满减');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (15, 'DISCOUNT', 16, 'COUPON,BUY_CYCLE,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满折');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (16, 'GIFT', 17, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满赠');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (17, 'BUYOUT_PRICE', 18, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '打包一口价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (18, 'HALF_PRICE_SECOND_PIECE', 19, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,NEW_COMER_COUPON', '第二件半价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (19, 'RETURN', 20, 'COUPON,BUY_CYCLE,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON,PREFERENTIAL', '满返');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (20, 'NEW_COMER_COUPON', 21, 'POINT_AND_CASH,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '新人专享券');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (54, 'PREFERENTIAL', 14, 'COUPON,POINT_AND_CASH,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT', '加价购');

-- 新增"是否开启邀新"字段
ALTER TABLE `sbc-marketing`.`distribution_setting`
ADD COLUMN `invite_open_flag` tinyint(4) DEFAULT 1 COMMENT '是否开启邀新 0：关闭，1：开启' AFTER `goods_audit_flag`;

-- 新增"买家自助修改收货地址"开关
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('order_setting', 'order_setting_buyer_modify_consignee', '买家自助修改收货地址', NULL, 1, '', '2022-11-16 19:37:57', '2022-11-17 10:12:20', 0);

-- 新增"划线价"字段
ALTER TABLE `sbc-goods`.`goods_info`
ADD COLUMN `line_price` decimal(20,2) DEFAULT NULL COMMENT '划线价' AFTER `is_buy_cycle`;

-- 初始化"收货信息变更提醒"消息节点
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (33, '订单', '收货信息变更提醒', '客户下单后自助修改收货地址', 'fOrderList001', 'TRADE_BUYER_MODIFY_CONSIGNEE', '订单{订单号}，用户修改了收货信息，点击去查看', 2, 1, 0, NULL, '2022-12-02 16:43:54', NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (34, '订单', '收货信息变更提醒', '客户下单后自助修改收货地址', 'fOrderList001', 'TRADE_BUYER_MODIFY_CONSIGNEE', '订单{订单号}，用户修改了收货信息，点击去查看', 2, 2, 0, NULL, '2022-12-02 16:44:15', NULL, NULL);
