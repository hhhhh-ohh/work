-- 1、店铺表
ALTER TABLE `sbc-customer`.`store`
    ADD COLUMN `contract_audit_state` tinyint(4) DEFAULT '1' NULL COMMENT '二次签约审核状态 0待审核  1已审核 2审核拒绝' AFTER `pickup_state`,
ADD COLUMN `contract_audit_reason` varchar(255) NULL COMMENT '二次签约审核拒绝原因' AFTER `contract_audit_state`;

-- 2、二次签约表
CREATE TABLE `sbc-goods`.`contract_brand_audit` (
                                        `contract_brand_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '签约品牌主键',
                                        `store_id` varchar(32) DEFAULT NULL,
                                        `brand_id` bigint(20) DEFAULT NULL COMMENT '商品品牌标识',
                                        `check_brand_id` bigint(20) DEFAULT NULL COMMENT '待审核品牌id',
                                        `authorize_pic` text COMMENT '授权图片路径',
                                        `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记 0不需要删除  1需要删除',
                                        PRIMARY KEY (`contract_brand_id`),
                                        KEY `idx_brand_id` (`brand_id`),
                                        KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签约品牌二次审核表';

CREATE TABLE `sbc-goods`.`contract_cate_audit` (
                                       `contract_cate_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '签约分类主键',
                                       `store_id` bigint(20) DEFAULT NULL COMMENT '店铺主键',
                                       `cate_id` bigint(20) DEFAULT NULL COMMENT '商品分类标识',
                                       `cate_rate` decimal(8,2) DEFAULT NULL COMMENT '分类扣率',
                                       `qualification_pics` text COMMENT '资质图片路径',
                                       `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记 0不需要删除  1需要删除',
                                       PRIMARY KEY (`contract_cate_id`),
                                        KEY `cate_id` (`cate_id`),
                                        KEY `store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签约分类二次审核表';

-- 3.员工增加更新版本时间戳
ALTER TABLE `sbc-customer`.`employee`
ADD COLUMN `version_no` bigint(11) NULL COMMENT '更新版本时间戳';

-- 4.招募规则说明-购买商品
ALTER TABLE `sbc-marketing`.`distribution_setting`
ADD COLUMN `recruit_buy_desc` text  NULL COMMENT '招募规则说明-购买商品' AFTER `commission_unhook_type`;

-- 5.有效邀新订单
UPDATE `sbc-order`.`consume_record` set flow_state = 'AVAILABLE_ORDER' where flow_state is null;

-- 6.分销素材表添加店铺主键,社交分销开关,店铺状态及签约结束日期
ALTER TABLE `sbc-goods`.`distribution_goods_matter`
ADD COLUMN `store_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺主键' AFTER `del_time`,
ADD COLUMN `open_flag` tinyint(4) NULL DEFAULT NULL COMMENT '是否开启社交分销 0：关闭，1：开启' AFTER `store_id`,
ADD COLUMN `store_state` tinyint(4) NULL DEFAULT NULL COMMENT '店铺状态 0、开启 1、关店' AFTER `open_flag`,
ADD COLUMN `contract_end_date` datetime(0) NULL DEFAULT NULL COMMENT '签约结束日期' AFTER `store_state`,
ADD INDEX `idx_store_id`(`store_id`);

-- 7.更新store_id
UPDATE `sbc-goods`.`distribution_goods_matter` m, `sbc-goods`.`goods_info` n set m.store_id = n.store_id where m.goods_info_id = n.goods_info_id;

-- 8.更新open_flag
UPDATE `sbc-goods`.`distribution_goods_matter` m,`sbc-marketing`.distribution_store_setting n set m.open_flag = n.open_flag where m.store_id = n.store_id;

-- 9.更新store_state，contract_end_date
UPDATE `sbc-goods`.`distribution_goods_matter` m,`sbc-customer`.store n set m.store_state = n.store_state, m.contract_end_date = n.contract_end_date where m.store_id = n.store_id;

-- 10.boss端二次审核开关设置
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('s2b_audit', 'supplier_goods_secondary_audit', '商家商品二次审核', NULL, 1, NULL, '2021-12-16 19:37:12', '2021-12-28 15:06:07', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('s2b_audit', 'provider_goods_secondary_audit', '供应商商品二次审核', NULL, 1, NULL, '2021-12-16 19:40:56', '2021-12-28 15:08:35', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('s2b_audit', 'supplier_sign_secondary_audit', '商家签约信息二次审核', NULL, 1, NULL, '2021-12-16 19:42:49', '2021-12-28 15:08:36', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('s2b_audit', 'provider_sign_secondary_audit', '供应商签约信息二次审核', NULL, 1, NULL, '2021-12-16 19:45:28', '2021-12-28 15:08:38', 0);

-- 11.新增商品审核表
CREATE TABLE `sbc-goods`.`goods_audit` (
                               `goods_id` varchar(32)  NOT NULL,
                               `old_goods_id` varchar(32) DEFAULT NULL COMMENT '旧商品Id',
                               `sale_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '销售类别(0:批发,1:零售)',
                               `cate_id` bigint(20) NOT NULL COMMENT '商品分类Id',
                               `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌Id',
                               `goods_name` varchar(255)  DEFAULT NULL,
                               `goods_subtitle` varchar(255)  DEFAULT NULL,
                               `goods_no` varchar(45)  DEFAULT NULL,
                               `goods_unit` varchar(45)  DEFAULT NULL,
                               `goods_img` varchar(255)  DEFAULT NULL,
                               `goods_video` varchar(255)  DEFAULT NULL,
                               `goods_weight` decimal(20,3) DEFAULT NULL COMMENT '商品重量',
                               `goods_cubage` decimal(20,6) DEFAULT NULL COMMENT '商品体积',
                               `freight_temp_id` bigint(12) DEFAULT NULL COMMENT '单品运费模板id',
                               `market_price` decimal(20,2) DEFAULT NULL COMMENT '市场价',
                               `supply_price` decimal(20,2) DEFAULT NULL COMMENT '供货价',
                               `retail_price` decimal(20,2) DEFAULT NULL COMMENT '建议零售价',
                               `goods_type` tinyint(1) DEFAULT '0' COMMENT '商品类型，0：实体商品，1：虚拟商品',
                               `line_price` decimal(20,2) DEFAULT NULL COMMENT '划线价',
                               `cost_price` decimal(20,2) DEFAULT NULL COMMENT '成本价',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `added_time` datetime DEFAULT NULL COMMENT '上下架时间',
                               `goods_source` tinyint(4) DEFAULT '1' COMMENT '商品来源，0供应商，1商家,2linkedmall',
                               `del_flag` tinyint(4) NOT NULL COMMENT '删除标识,0:未删除1:已删除',
                               `added_flag` tinyint(4) NOT NULL COMMENT '上下架状态,0:下架1:上架2:部分上架',
                               `more_spec_flag` tinyint(4) DEFAULT NULL COMMENT '规格类型,0:单规格1:多规格',
                               `price_type` tinyint(4) DEFAULT NULL COMMENT '设价类型,0:按客户1:按订货量2:按市场价',
                               `allow_price_set` tinyint(1) DEFAULT '1' COMMENT '订货量设价时,是否允许sku独立设阶梯价(0:不允许,1:允许)',
                               `custom_flag` tinyint(4) DEFAULT NULL COMMENT '按客户单独定价,0:否1:是',
                               `level_discount_flag` tinyint(4) DEFAULT NULL COMMENT '叠加客户等级折扣，0:否1:是',
                               `store_id` bigint(20) NOT NULL COMMENT '店铺标识',
                               `company_info_id` bigint(11) NOT NULL COMMENT '公司信息ID',
                               `supplier_name` varchar(255)  DEFAULT NULL,
                               `submit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
                               `audit_status` tinyint(4) NOT NULL COMMENT '审核状态,0:未审核1 审核通过2审核失败3禁用中',
                               `audit_reason` varchar(255)  DEFAULT NULL,
                               `goods_detail` mediumtext COMMENT '商品详情',
                               `goods_mobile_detail` mediumtext COMMENT '移动端图文详情',
                               `company_type` tinyint(4) DEFAULT NULL COMMENT '自营标识',
                               `goods_evaluate_num` bigint(11) DEFAULT '0' COMMENT '商品评论数',
                               `goods_collect_num` bigint(11) DEFAULT '0' COMMENT '商品收藏量',
                               `goods_sales_num` bigint(11) DEFAULT '0' COMMENT '商品销量',
                               `goods_favorable_comment_num` bigint(11) DEFAULT '0' COMMENT '商品好评数量',
                               `sham_sales_num` bigint(11) DEFAULT '0' COMMENT '注水销量',
                               `sort_no` bigint(11) DEFAULT '0' COMMENT '排序号',
                               `single_spec_flag` tinyint(4) DEFAULT '1' COMMENT '0:多规格1:单规格',
                               `added_timing_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否定时上架 0:否1:是',
                               `added_timing_time` datetime DEFAULT NULL COMMENT '定时上架时间',
                               `stock` bigint(11) DEFAULT NULL COMMENT '库存（准实时）',
                               `sku_min_market_price` decimal(10,2) DEFAULT NULL COMMENT '最小市场价',
                               `goods_buy_types` varchar(10)  DEFAULT NULL,
                               `provider_goods_id` varchar(45)  DEFAULT NULL,
                               `provider_id` bigint(20) DEFAULT NULL COMMENT '供应商Id',
                               `provider_name` varchar(45)  DEFAULT NULL,
                               `recommended_retail_price` decimal(20,2) DEFAULT NULL COMMENT '建议零售价',
                               `need_synchronize` tinyint(4) DEFAULT NULL COMMENT '是否需要同步 0：不需要同步 1：需要同步',
                               `delete_reason` varchar(100)  DEFAULT NULL,
                               `add_false_reason` varchar(100)  DEFAULT NULL,
                               `vendibility` tinyint(4) DEFAULT '1' COMMENT '是否可售，0不可售，1可售',
                               `third_platform_spu_id` varchar(30)  DEFAULT NULL,
                               `seller_id` bigint(20) DEFAULT NULL COMMENT '第三方卖家id',
                               `third_cate_id` bigint(20) DEFAULT NULL COMMENT '三方渠道类目id',
                               `third_platform_type` tinyint(4) DEFAULT NULL COMMENT '三方平台类型，0，linkedmall',
                               `provider_status` tinyint(4) DEFAULT NULL COMMENT '供应商状态 0: 关店 1:开店',
                               `label_id_str` text COMMENT '标签id，以逗号拼凑',
                               `plugin_type` tinyint(4) DEFAULT '0' COMMENT '商品类型；0:一般商品 1:跨境商品',
                               `supplier_type` tinyint(4) DEFAULT NULL COMMENT '商家类型 0 普通商家,1 跨境商家',
                               `store_type` tinyint(4) DEFAULT NULL COMMENT '商家类型：0供应商，1商家，2：O2O商家，3：跨境商家',
                               `is_independent` tinyint(4) DEFAULT '0' COMMENT '商家端编辑供应商商品判断页面是否是独立设置价格 0 ：否  1：是',
                               `audit_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核类型 0:初次审核 1:二次审核',
                               PRIMARY KEY (`goods_id`),
                               UNIQUE KEY `product_id_UNIQUE` (`goods_id`),
                               KEY `idx_company_info_id` (`company_info_id`),
                               KEY `idx_store_id` (`store_id`),
                               KEY `idx_del_flag` (`del_flag`),
                               KEY `idx_create_time` (`create_time`),
                               KEY `idx_goods_source` (`goods_source`),
                               KEY `idx_audit_status` (`audit_status`),
                               KEY `idx_brand_id` (`brand_id`),
                               KEY `idx_goods_no` (`goods_no`),
                               KEY `provider_goods_id` (`provider_goods_id`),
                               KEY `old_goods_id` (`old_goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审核商品SPU表';

-- 11.商品规格关联表增加字段
ALTER TABLE `sbc-goods`.`goods_spec`
    ADD COLUMN `old_spec_id` bigint(20) NULL DEFAULT NULL COMMENT '老spec主键' AFTER `del_flag`;

-- 12.商品规格值关联表增加字段
ALTER TABLE `sbc-goods`.`goods_spec_detail`
    ADD COLUMN `old_spec_detail_id` bigint(20) NULL DEFAULT NULL COMMENT '老spec_detail_Id' AFTER `del_flag`;

-- 13.商品sku表增加字段
ALTER TABLE `sbc-goods`.`goods_info`
    ADD COLUMN `old_goods_info_id` varchar(32)  NULL DEFAULT NULL COMMENT '老skuId' AFTER `goods_cubage`;

-- 14.完善备注
ALTER TABLE `sbc-marketing`.`coupon_activity`
MODIFY COLUMN `scan_type` tinyint(4) NULL DEFAULT NULL COMMENT '1:已扫描 0或空:未扫描 2:执行失败';

ALTER TABLE `s2b_statistics`.`replay_coupon_activity`
ADD COLUMN `scan_type` tinyint(4) NULL DEFAULT NULL COMMENT '1:已扫描 0或空:未扫描 2:执行失败';

-- 15.任务日志表增加字段
ALTER TABLE `sbc-setting`.`task_log`
ADD COLUMN `biz_id` varchar(255) NULL COMMENT '业务id' AFTER `id`,
ADD INDEX(`biz_id`);

-- 16.定时任务很多是全表扫描，排查加索引
create index idx_rights_type
    on `sbc-customer`.customer_level_rights (rights_type);

create index idx_target_date
	on `sbc-customer`.distribution_performance_day (target_date);

create index idx_activity_date
	on `sbc-goods`.flash_sale_goods (activity_date);

create index idx_activity_time
	on `sbc-goods`.flash_sale_goods (activity_time);

create index idx_return_end_time
	on `sbc-order`.order_growth_value_temp (return_end_time);

create index idx_end_time
	on  `sbc-account`.customer_credit_account (end_time);

create index idx_start_time
	on  `sbc-account`.customer_credit_account (start_time);

create index idx_credit_amount
	on `sbc-account`.customer_credit_account (credit_amount);

create index idx_channel_item_id
	on `sbc-order`.pay_trade_record (channel_item_id);

create index idx_status
	on `sbc-order`.pay_trade_record (status);

create index idx_third_platform_sku_id
	on `sbc-goods`.goods_info (third_platform_sku_id);

create index idx_company_code
	on `sbc-customer`.company_info (company_code);



delete from `sbc-marketing`.`marketing_plugin_config`;
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (1, 'COUPON', 1, 'POINT_AND_CASH,GROUPON,FLASH_SALE,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '优惠券');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (2, 'POINT_AND_CASH', 2, 'COUPON,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '积分+现金');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (4, 'GROUPON', 8, 'COUPON', '拼团');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (5, 'APPOINTMENT_SALE', 3, 'COUPON', '预约');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (6, 'BOOKING_SALE', 4, 'COUPON', '预售');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (7, 'FLASH_SALE', 5, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (8, 'DISTRIBUTION', 6, 'COUPON', '分销');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (9, 'ENTERPRISE_PRICE', 7, 'COUPON,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '企业价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (10, 'CUSTOMER_PRICE', 10, 'COUPON,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (11, 'CUSTOMER_LEVEL', 11, 'COUPON,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员等级价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (12, 'REDUCTION', 12, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满减');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (13, 'DISCOUNT', 13, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满折');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (14, 'GIFT', 14, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满赠');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (15, 'BUYOUT_PRICE', 15, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,HALF_PRICE_SECOND_PIECE', '打包一口价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config` (`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (16, 'HALF_PRICE_SECOND_PIECE', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE', '第二件半价');
-- 将商品待审核数据插入待审核表
INSERT INTO `sbc-goods`.`goods_audit` (`goods_id`, `sale_type`, `cate_id`, `brand_id`, `goods_name`, `goods_subtitle`, `goods_no`, `goods_unit`, `goods_img`, `goods_video`, `goods_weight`, `goods_cubage`, `freight_temp_id`, `market_price`, `supply_price`, `retail_price`, `goods_type`, `line_price`, `cost_price`, `create_time`, `update_time`, `added_time`, `goods_source`, `del_flag`, `added_flag`, `more_spec_flag`, `price_type`, `allow_price_set`, `custom_flag`, `level_discount_flag`, `store_id`, `company_info_id`, `supplier_name`, `submit_time`, `audit_status`, `audit_reason`, `goods_detail`, `goods_mobile_detail`, `company_type`, `goods_evaluate_num`, `goods_collect_num`, `goods_sales_num`, `goods_favorable_comment_num`, `sham_sales_num`, `sort_no`, `single_spec_flag`, `added_timing_flag`, `added_timing_time`, `stock`, `sku_min_market_price`, `goods_buy_types`, `provider_goods_id`, `provider_id`, `provider_name`, `recommended_retail_price`, `need_synchronize`, `delete_reason`, `add_false_reason`, `vendibility`, `third_platform_spu_id`, `seller_id`, `third_cate_id`, `third_platform_type`, `provider_status`, `label_id_str`, `plugin_type`, `supplier_type`, `store_type`, `is_independent`) (select `goods_id`, `sale_type`, `cate_id`, `brand_id`, `goods_name`, `goods_subtitle`, `goods_no`, `goods_unit`, `goods_img`, `goods_video`, `goods_weight`, `goods_cubage`, `freight_temp_id`, `market_price`, `supply_price`, `retail_price`, `goods_type`, `line_price`, `cost_price`, `create_time`, `update_time`, `added_time`, `goods_source`, `del_flag`, `added_flag`, `more_spec_flag`, `price_type`, `allow_price_set`, `custom_flag`, `level_discount_flag`, `store_id`, `company_info_id`, `supplier_name`, `submit_time`, `audit_status`, `audit_reason`, `goods_detail`, `goods_mobile_detail`, `company_type`, `goods_evaluate_num`, `goods_collect_num`, `goods_sales_num`, `goods_favorable_comment_num`, `sham_sales_num`, `sort_no`, `single_spec_flag`, `added_timing_flag`, `added_timing_time`, `stock`, `sku_min_market_price`, `goods_buy_types`, `provider_goods_id`, `provider_id`, `provider_name`, `recommended_retail_price`, `need_synchronize`, `delete_reason`, `add_false_reason`, `vendibility`, `third_platform_spu_id`, `seller_id`, `third_cate_id`, `third_platform_type`, `provider_status`, `label_id_str`, `plugin_type`, `supplier_type`, `store_type`, `is_independent`  from `sbc-goods`.`goods` g WHERE g.audit_status = 0);

-- 将商品禁售数据插入待审核表
INSERT INTO `sbc-goods`.`goods_audit` (`goods_id`,`old_goods_id`, `sale_type`, `cate_id`, `brand_id`, `goods_name`, `goods_subtitle`, `goods_no`, `goods_unit`, `goods_img`, `goods_video`, `goods_weight`, `goods_cubage`, `freight_temp_id`, `market_price`, `supply_price`, `retail_price`, `goods_type`, `line_price`, `cost_price`, `create_time`, `update_time`, `added_time`, `goods_source`, `del_flag`, `added_flag`, `more_spec_flag`, `price_type`, `allow_price_set`, `custom_flag`, `level_discount_flag`, `store_id`, `company_info_id`, `supplier_name`, `submit_time`, `audit_status`, `audit_reason`, `goods_detail`, `goods_mobile_detail`, `company_type`, `goods_evaluate_num`, `goods_collect_num`, `goods_sales_num`, `goods_favorable_comment_num`, `sham_sales_num`, `sort_no`, `single_spec_flag`, `added_timing_flag`, `added_timing_time`, `stock`, `sku_min_market_price`, `goods_buy_types`, `provider_goods_id`, `provider_id`, `provider_name`, `recommended_retail_price`, `need_synchronize`, `delete_reason`, `add_false_reason`, `vendibility`, `third_platform_spu_id`, `seller_id`, `third_cate_id`, `third_platform_type`, `provider_status`, `label_id_str`, `plugin_type`, `supplier_type`, `store_type`, `is_independent`) (select `goods_id`,`goods_id`, `sale_type`, `cate_id`, `brand_id`, `goods_name`, `goods_subtitle`, `goods_no`, `goods_unit`, `goods_img`, `goods_video`, `goods_weight`, `goods_cubage`, `freight_temp_id`, `market_price`, `supply_price`, `retail_price`, `goods_type`, `line_price`, `cost_price`, `create_time`, `update_time`, `added_time`, `goods_source`, `del_flag`, `added_flag`, `more_spec_flag`, `price_type`, `allow_price_set`, `custom_flag`, `level_discount_flag`, `store_id`, `company_info_id`, `supplier_name`, `submit_time`, `audit_status`, `audit_reason`, `goods_detail`, `goods_mobile_detail`, `company_type`, `goods_evaluate_num`, `goods_collect_num`, `goods_sales_num`, `goods_favorable_comment_num`, `sham_sales_num`, `sort_no`, `single_spec_flag`, `added_timing_flag`, `added_timing_time`, `stock`, `sku_min_market_price`, `goods_buy_types`, `provider_goods_id`, `provider_id`, `provider_name`, `recommended_retail_price`, `need_synchronize`, `delete_reason`, `add_false_reason`, `vendibility`, `third_platform_spu_id`, `seller_id`, `third_cate_id`, `third_platform_type`, `provider_status`, `label_id_str`, `plugin_type`, `supplier_type`, `store_type`, `is_independent` from `sbc-goods`.`goods` g WHERE g.audit_status = 3 and goods_id not in(SELECT a.goods_id  FROM `sbc-goods`.`goods` a INNER JOIN `sbc-goods`.`goods_audit` b ON a.goods_id = b.goods_id  WHERE a.audit_status = 3));

-- 将商品表待审核数据删除
DELETE FROM `sbc-goods`.`goods` WHERE audit_status = 0
