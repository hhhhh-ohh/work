DROP TABLE IF EXISTS `sbc-marketing`.`marketing_plugin_config`;
CREATE TABLE `sbc-marketing`.`marketing_plugin_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marketing_type` varchar(100) DEFAULT NULL,
  `sort` bigint(4) DEFAULT NULL,
  `coexist` varchar(1000) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_marketing_type` (`marketing_type`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COMMENT='营销插件配置';


delete from `sbc-marketing`.`marketing_plugin_config`;
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (1, 'COUPON', 1, 'POINT_AND_CASH,GROUPON,FLASH_SALE,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '优惠券');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (2, 'POINT_AND_CASH', 2, 'COUPON,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '积分+现金');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (4, 'GROUPON', 4, 'COUPON', '拼团');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (5, 'APPOINTMENT_SALE', 5, 'COUPON', '预约');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (6, 'BOOKING_SALE', 6, 'COUPON', '预售');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (7, 'FLASH_SALE', 7, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (8, 'DISTRIBUTION', 8, 'COUPON', '分销');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (9, 'ENTERPRISE_PRICE', 9, 'COUPON,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '企业价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (10, 'CUSTOMER_PRICE', 10, 'COUPON,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (11, 'CUSTOMER_LEVEL', 11, 'COUPON,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员等级价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (12, 'REDUCTION', 12, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满减');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (13, 'DISCOUNT', 13, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满折');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (14, 'GIFT', 14, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满赠');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (15, 'BUYOUT_PRICE', 15, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,HALF_PRICE_SECOND_PIECE', '打包一口价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (16, 'HALF_PRICE_SECOND_PIECE', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE', '第二件半价');
-- 分销商品菜单缺失
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817d743a39017d78c1df530000', '4', '8a99c40b6a06a8d7016a0afa9b64001e', '获取商品SPU详情信息', NULL, '/goods/skus/spuId', 'POST', NULL, '5', '2021-12-02 09:28:31', '0');

-- 修改积分商品脏数据
UPDATE `sbc-goods`.`points_goods` SET cate_id = NULL WHERE cate_id = 0;

-- 修复关注失败BUG
use `sbc-customer`;
DROP TRIGGER `trigger_store_customer_follow_action`;
CREATE DEFINER = `root`@`%` TRIGGER `trigger_store_customer_follow_action` AFTER INSERT ON `store_customer_follow` FOR EACH ROW BEGIN
		INSERT INTO `sbc-customer`.`store_customer_follow_action`(customer_id, store_id, company_info_id, follow_time, terminal_source,create_time)
		VALUES (NEW.customer_id, NEW.store_id, NEW.company_info_id, NEW.follow_time, NEW.terminal_source,now());
	end;
