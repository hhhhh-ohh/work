-- 当店铺为null的数据，根据商家id来补充
update `s2b_statistics`.replay_trade t,`s2b_statistics`.replay_store s set t.store_id = s.store_id where t.company_id = s.company_info_id and t.store_id is null;

-- 退单明细 spu_id为null的数据，根据skuId补充spuId
UPDATE `s2b_statistics`.replay_return_item r,`s2b_statistics`.replay_goods_info i SET r.spu_id = i.goods_id, r.spu_name = r.sku_name WHERE r.sku_id = i.goods_info_id AND r.spu_id IS NULL;

CREATE TABLE `s2b_statistics`.`replay_customer_points_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_account` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` tinyint(4) NOT NULL COMMENT '操作类型 0:扣除 1:增长',
  `service_type` tinyint(4) NOT NULL COMMENT '业务类型 0签到 1注册 2分享商品 3分享注册 4分享购买 5评论商品 6晒单 7上传头像/完善个人信息 8绑定微信 9添加收货地址 10关注店铺 11订单完成 12订单抵扣 13优惠券兑换 14积分兑换 15退单返还 16订单取消返还 17过期扣除',
  `points` int(10) NOT NULL COMMENT '积分数量',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `points_available` int(10) DEFAULT NULL COMMENT '积分余额',
  `op_time` datetime NOT NULL COMMENT '操作时间',
  `update_time_stamp` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_customer_id` (`customer_id`) USING BTREE,
  KEY `idx_customer_account` (`customer_account`) USING BTREE,
  KEY `idx_service_type` (`service_type`) USING BTREE,
  KEY `idx_update_time_stamp` (`update_time_stamp`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18605 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员积分明细';
-- 数据复制
insert into `s2b_statistics`.`replay_customer_points_detail` select *,CURRENT_TIMESTAMP(3) as update_time_stamp from
    `sbc-customer`.`customer_points_detail`;

CREATE TABLE `s2b_statistics`.`replay_system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ' 编号',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `config_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '状态,0:未启用1:已启用',
  `context` longtext COMMENT '配置内容，如JSON内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_config_key` (`config_key`(191)),
  KEY `idx_config_type` (`config_type`(191)),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='系统配置表';
-- 数据复制
insert into `s2b_statistics`.`replay_system_config` select * from
                                                                  `sbc-setting`.`system_config`;

CREATE TABLE `s2b_statistics`.`replay_third_login_relation` (
  `third_login_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `third_login_uid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '判断店铺id',
  `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `third_login_type` tinyint(4) DEFAULT NULL COMMENT '第三方类型 0:wechat',
  `third_login_open_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `binding_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `head_img_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '解绑状态, 1:已解绑，0：未解绑',
  `unbinding_time` datetime DEFAULT NULL COMMENT '解绑时间',
  `update_time_stamp` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
  PRIMARY KEY (`third_login_id`) USING BTREE,
  UNIQUE KEY `third_login_id` (`third_login_id`) USING BTREE,
  KEY `third_login_relation_third_login_uid_uindex` (`third_login_uid`(191)) USING BTREE,
  KEY `idx_third_login_relation_customer_id` (`customer_id`) USING BTREE,
  KEY `idx_update_time_stamp` (`update_time_stamp`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第三方关系表';
-- 数据复制
insert into `s2b_statistics`.`replay_third_login_relation` select *,CURRENT_TIMESTAMP(3) as update_time_stamp from
    `sbc-customer`.`third_login_relation`;

CREATE TABLE If Not Exists `s2b_statistics`.`replay_customer_funds` (
  `customer_funds_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_account` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `blocked_balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '冻结余额',
  `withdraw_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '可提现金额',
  `already_draw_amount` decimal(18,2) NOT NULL COMMENT '已提现金额',
  `income` bigint(30) NOT NULL DEFAULT '0' COMMENT '收入笔数',
  `amount_received` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '收入金额',
  `expenditure` bigint(30) NOT NULL DEFAULT '0' COMMENT '支出数',
  `amount_paid` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '支出金额',
  `is_distributor` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否分销员，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`customer_funds_id`) USING BTREE,
  UNIQUE KEY `index_customer_account` (`customer_account`) USING BTREE,
  UNIQUE KEY `index_customer_id` (`customer_id`) USING BTREE,
  KEY `index_customer_name` (`customer_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员资金表';

ALTER TABLE `s2b_statistics`.`replay_customer_funds`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 数据复制
insert into `s2b_statistics`.`replay_customer_funds` select *,CURRENT_TIMESTAMP(3) as update_time_stamp from
    `sbc-account`.`customer_funds`;

CREATE TABLE `s2b_statistics`.`replay_groupon_activity` (
  `groupon_activity_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `groupon_num` tinyint(2) NOT NULL DEFAULT '2' COMMENT '拼团人数',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `groupon_cate_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `auto_groupon` tinyint(1) NOT NULL COMMENT '是否自动成团，0：否，1：是',
  `free_delivery` tinyint(1) NOT NULL COMMENT '是否包邮，0：否，1：是',
  `goods_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `goods_no` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `goods_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `store_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sticky` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精选，0：否，1：是',
  `audit_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否审核通过，0：待审核，1：审核通过，2：审核不通过',
  `audit_fail_reason` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `already_groupon_num` int(20) NOT NULL DEFAULT '0' COMMENT '已成团人数',
  `wait_groupon_num` int(20) NOT NULL DEFAULT '0' COMMENT '待成团人数',
  `fail_groupon_num` int(20) NOT NULL DEFAULT '0' COMMENT '团失败人数',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_time_stamp` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
  PRIMARY KEY (`groupon_activity_id`) USING BTREE,
  KEY `index_start_time` (`start_time`) USING BTREE,
  KEY `index_end_time` (`end_time`) USING BTREE,
  KEY `index_group_cate_id` (`groupon_cate_id`) USING BTREE,
  KEY `index_store_id` (`store_id`) USING BTREE,
  KEY `index_status` (`audit_status`) USING BTREE,
  KEY `inde_spu_name` (`goods_name`(191)) USING BTREE,
  KEY `idx_update_time_stamp` (`update_time_stamp`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='拼团活动表';
-- 数据复制
insert into `s2b_statistics`.`replay_groupon_activity` select *,CURRENT_TIMESTAMP(3) as update_time_stamp from
    `sbc-marketing`.`groupon_activity`;

-- 增加商品规格以及更新时间戳
CREATE TABLE `s2b_statistics`.`replay_goods_spec` (
  `spec_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `old_spec_id` bigint(20) DEFAULT NULL COMMENT '老spec主键',
  `goods_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `spec_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
  PRIMARY KEY (`spec_id`),
  KEY `idx_goods_id` (`goods_id`),
  KEY `idx_del_flag` (`del_flag`),
  KEY `idx_update_time_stamp` (`update_time_stamp`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='商品规格关联表';

CREATE TABLE `s2b_statistics`.`replay_platform_address` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `addr_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr_parent_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort_no` int(10) NOT NULL COMMENT '排序号',
  `addr_level` tinyint(4) NOT NULL COMMENT '地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)',
  `create_time` datetime NOT NULL COMMENT '入库时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除标志 0：否，1：是；默认0',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `data_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '数据类型 0:初始化 1:人工',
  `pin_yin` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址名称拼音',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `addr_id` (`addr_id`) USING BTREE,
  KEY `addr_parent_id` (`addr_parent_id`) USING BTREE,
  KEY `addr_level` (`addr_level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='平台四级地址信息表';
-- 数据复制
insert into `s2b_statistics`.`replay_platform_address` select * from
    `sbc-setting`.`platform_address`;

CREATE TABLE `s2b_statistics`.`replay_coupon_marketing_scope` (
  `marketing_scope_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `coupon_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scope_type` tinyint(4) NOT NULL COMMENT '营销类型(0,1,2,3,4),0全部，1品牌，2boss分类，3.店铺分类，4自定义货品',
  `scope_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cate_grade` tinyint(4) DEFAULT NULL COMMENT '分类级别(1,2,3）',
  `update_time_stamp` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
  PRIMARY KEY (`marketing_scope_id`) USING BTREE,
  KEY `idx_coupon_id` (`coupon_id`) USING BTREE,
  KEY `idx_scope_type` (`scope_type`) USING BTREE,
  KEY `idx_scope_id` (`scope_id`) USING BTREE,
  KEY `idx_update_time_stamp` (`update_time_stamp`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券商品作用范围';
-- 数据复制
insert into `s2b_statistics`.`replay_coupon_marketing_scope` select *,CURRENT_TIMESTAMP(3) as update_time_stamp from
                                                                           `sbc-marketing`.`coupon_marketing_scope`;


-- 复制余额明细
CREATE TABLE `s2b_statistics`.`replay_customer_funds_detail` (
  `customer_funds_detail_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `business_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `funds_type` tinyint(4) NOT NULL COMMENT '资金类型 1：分销佣金；2：佣金提现；3：邀新奖励； 4：佣金提成；5：余额支付',
  `draw_cash_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receipt_payment_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '收支金额',
  `funds_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '资金是否成功入账 0:否，1：成功',
  `account_balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `sub_type` tinyint(4) DEFAULT NULL COMMENT '资金子类型 1：推广返利；2：佣金提现；3：邀新奖励；4:自购返利；5:推广提成；6:余额支付',
  PRIMARY KEY (`customer_funds_detail_id`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE,
  KEY `index_business_id` (`business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- 数据复制
insert into `s2b_statistics`.`replay_customer_funds_detail` select * from `sbc-account`.`customer_funds_detail`;

-- 新增退单
CREATE TABLE `s2b_statistics`.`replay_refund_order` (
  `refund_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `customer_detail_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `return_order_code` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `return_price` decimal(20,2) DEFAULT NULL,
  `return_points` bigint(10) DEFAULT '0' COMMENT '应退积分',
  `refund_code` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '0 在线支付 1线下支付',
  `refund_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '退款单状态：1.待退款 2.拒绝退款 3.已完成',
  `refuse_reason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标志 0：未删除 1:已删除',
  `del_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '公司信息ID',
  PRIMARY KEY (`refund_id`) USING BTREE,
  UNIQUE KEY `refund_code_UNIQUE` (`refund_code`) USING BTREE,
  UNIQUE KEY `refund_order_code_UNIQUE` (`return_order_code`) USING BTREE,
  KEY `idx_customer_detail_id` (`customer_detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款单';
-- 数据复制
insert into `s2b_statistics`.`replay_refund_order` select * from `sbc-order`.`refund_order`;
-- 增加交易收货地址的更新时间戳
ALTER TABLE `s2b_statistics`.`replay_refund_order`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 收货人地址
CREATE TABLE `s2b_statistics`.`replay_customer_delivery_address` (
  `delivery_address_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `consignee_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `consignee_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `province_id` bigint(10) DEFAULT NULL COMMENT '省份',
  `city_id` bigint(10) DEFAULT NULL COMMENT '市',
  `area_id` bigint(10) DEFAULT NULL COMMENT '区',
  `street_id` bigint(20) DEFAULT NULL COMMENT '街道id',
  `delivery_address` varchar(225) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_defalt_address` tinyint(4) DEFAULT NULL COMMENT '是否是默认地址',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `house_num` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '门牌号',
  PRIMARY KEY (`delivery_address_id`) USING BTREE,
  UNIQUE KEY `delivery_address_id_UNIQUE` (`delivery_address_id`) USING BTREE,
  KEY `idx_customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户收货地址表';

-- 数据复制
insert into `s2b_statistics`.`replay_customer_delivery_address` select * from `sbc-customer`.`customer_delivery_address`;

ALTER TABLE `sbc-setting`.`open_api_setting`
    ADD COLUMN `platform_type` tinyint(4) NULL COMMENT '0 boss平台，1商家' AFTER `update_person`,
ADD COLUMN `platform_desc` varchar(255) NULL COMMENT '平台描述' AFTER `platform_type`;

INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`,
                                          `config_name`, `remark`, `status`,
                                          `context`, `create_time`,
                                          `update_time`, `del_flag`) VALUES
                                                                            ('statistics_setting', 'qm_statistics_setting', '千米数谋基本配置', NULL, 1, '{\"appkey\":\"\", \"appsecret\":\"\", \"apiUrl\":\"\"}', now(), NULL, 0);

-- 增加商品更新时间戳
ALTER TABLE `s2b_statistics`.`replay_goods`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加商品SKU更新时间戳
ALTER TABLE `s2b_statistics`.`replay_goods_info`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加商品规格关联的更新时间戳
ALTER TABLE `s2b_statistics`.`replay_goods_info_spec_detail_rel`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加退单的更新时间戳
ALTER TABLE `s2b_statistics`.`replay_return_order`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加退单项的更新时间戳
ALTER TABLE `s2b_statistics`.`replay_return_item`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加交易的新字段以及更新时间戳
ALTER TABLE `s2b_statistics`.`replay_trade`
ADD COLUMN `order_type` tinyint(4) NULL COMMENT '订单类型 0:普通订单 1:积分订单',
ADD COLUMN `deliver_status` tinyint(4) NULL COMMENT '发货状态 0: 未发货 1: 已发货 2:部分发货 3:作废',
ADD COLUMN `final_time` datetime NULL COMMENT '订单入账时间',
ADD COLUMN `end_time` datetime NULL COMMENT '完成时间',
ADD COLUMN `customer_name` varchar(128) NULL COMMENT '会员名称',
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 发货状态为null的数据，根据订单总状态为已发货或已完成，将发货状态=已发货状态
update `s2b_statistics`.replay_trade t set t.deliver_status = 1 where t.flow_state in (5,7) and t.deliver_status is null;

-- 增加交易商品的新字段和更新时间戳
ALTER TABLE `s2b_statistics`.`replay_trade_item`
ADD COLUMN `brand_name` varchar(255) NULL COMMENT '品牌名称' AFTER `brand`,
ADD COLUMN `img` varchar(255) NULL COMMENT '商品图片',
ADD COLUMN `cate_top_id` bigint(20) NULL COMMENT '顶级一级分类',
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加交易收货地址的更新时间戳
ALTER TABLE `s2b_statistics`.`replay_consignee`
ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 数据复制
insert into `s2b_statistics`.`replay_goods_spec`(spec_id,goods_id,spec_name,old_spec_id,create_time,update_time,del_flag)
select spec_id,goods_id,spec_name,old_spec_id,create_time,update_time,del_flag from `sbc-goods`.`goods_spec`;

-- 新增数谋对接时间
CREATE TABLE `s2b_statistics`.`sm_handle_date` (
  `table_name` varchar(50) NOT NULL COMMENT '表名',
  `handle_date` datetime(3) DEFAULT NULL COMMENT '最近处理时间',
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数谋对接时间';

-- 增加最近处理时间记录
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_goods', NULL);
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_goods_info', NULL);
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_goods_info_spec_detail_rel', NULL);
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_goods_spec', NULL);

INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_return_order', NULL);
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_return_item', NULL);

INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_trade', NULL);
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_trade_item', NULL);
INSERT INTO `s2b_statistics`.`sm_handle_date`(`table_name`, `handle_date`) VALUES ('replay_consignee', NULL);

-- 数谋对接错误记录
CREATE TABLE `s2b_statistics`.`sm_handle_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_type` varchar(20) NOT NULL COMMENT '业务类型 COMPANY:公司信息 STORE:店铺信息 BRAND:品牌信息 CATEGORY:分类信息 GOODS:商品信息 TRADE:订单信息 APPLY:售后单信息 ACTIVITY:营销活动 ACTIVITY_CATEGORY:营销活动分类 CUSTOMER:客户信息 CUSTOMER_ADDRESS:客户收货地址 CUSTOMER_LEVEL:客户会员等级 BALANCE_DETAILS:余额明细 INTEGRALS_DETAILS:积分明细',
  `biz_id` varchar(32) NOT NULL COMMENT '业务id',
  `create_time` datetime(3) NOT NULL COMMENT '创建时间',
  `error_message` text COMMENT '错误原因',
  `request_params` text COMMENT '请求参数',
  `update_time_stamp` datetime(3) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
  `error_num` int(2) DEFAULT NULL COMMENT '错误次数',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `biz_id` (`biz_id`),
  KEY `biz_type` (`biz_type`),
  KEY `error_num` (`error_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数谋对接错误记录';

-- 增加执行器
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_group`(`id`,`app_name`, `title`, `order`, `address_type`, `address_list`) VALUES (13, 'xxl-job-data-galaxy', 'datagalaxy', 3, 0, null);


-- 添加缺失的定时任务
INSERT INTO `xxl-job`.xxl_job_qrtz_trigger_info (job_group, job_cron, job_desc, add_time, update_time, author,
                                                 alarm_email, executor_route_strategy, executor_handler, executor_param,
                                                 executor_block_strategy, executor_timeout, executor_fail_retry_count,
                                                 glue_type, glue_source, glue_remark, glue_updatetime, child_jobid)
VALUES (13, '0 0/15 * * * ?', '数谋对接-商品同步数据', '2022-01-10 10:38:03', '2022-01-10 10:38:03', '戴倚天', '', 'FIRST',
        'SmGoodsJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化',
        '2022-01-10 10:38:03', '');


INSERT INTO `xxl-job`.xxl_job_qrtz_trigger_info (job_group, job_cron, job_desc, add_time, update_time, author,
                                                 alarm_email, executor_route_strategy, executor_handler, executor_param,
                                                 executor_block_strategy, executor_timeout, executor_fail_retry_count,
                                                 glue_type, glue_source, glue_remark, glue_updatetime, child_jobid)
VALUES (13, '0 0/15 * * * ?', '数谋对接-退单同步数据', '2022-01-10 10:38:03', '2022-01-10 10:38:03', '戴倚天', '', 'FIRST',
        'SmReturnOrderJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化',
        '2022-01-10 10:38:03', '');


INSERT INTO `xxl-job`.xxl_job_qrtz_trigger_info (job_group, job_cron, job_desc, add_time, update_time, author,
                                                 alarm_email, executor_route_strategy, executor_handler, executor_param,
                                                 executor_block_strategy, executor_timeout, executor_fail_retry_count,
                                                 glue_type, glue_source, glue_remark, glue_updatetime, child_jobid)
VALUES (13, '0 0/2 * * * ?', '数谋对接-补偿业务数据', '2022-01-10 10:38:03', '2022-01-10 10:38:03', '戴倚天', '', 'FIRST',
        'SmCompensateJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化',
        '2022-01-10 10:38:03', '');

-- 增加预约更新时间戳
ALTER TABLE `s2b_statistics`.`replay_appointment_sale`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;
ALTER TABLE `s2b_statistics`.`replay_appointment_sale_goods`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加预售更新时间戳
ALTER TABLE `s2b_statistics`.`replay_booking_sale`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;
ALTER TABLE `s2b_statistics`.`replay_booking_sale_goods`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加优惠券活动更新时间戳
ALTER TABLE `s2b_statistics`.`replay_coupon_activity`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;
ALTER TABLE `s2b_statistics`.`replay_coupon_activity_config`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;
ALTER TABLE `s2b_statistics`.`replay_coupon_info`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加秒杀更新时间戳
ALTER TABLE `s2b_statistics`.`replay_flash_sale_goods`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加拼团更新时间戳
ALTER TABLE `s2b_statistics`.`replay_groupon_goods_info`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 增加营销活动更新时间戳
ALTER TABLE `s2b_statistics`.`replay_marketing`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;
ALTER TABLE `s2b_statistics`.`replay_marketing_scope`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

-- 客户相关更新时间戳
ALTER TABLE `s2b_statistics`.`replay_customer`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;
ALTER TABLE `s2b_statistics`.`replay_customer_detail`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (13, '0 0/15 * * * ?', '数谋对接-会员数据同步', '2022-01-24 14:11:20', '2022-01-24 14:11:20', '张文昌', '', 'FIRST', 'SmCustomerJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-01-24 14:11:20', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (13, '0 0/15 * * * ?', '数谋对接-营销同步数据', '2022-01-24 14:10:39', '2022-01-24 14:10:52', '张文昌', '', 'FIRST', 'SmMarketingJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-01-24 14:10:39', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (13, '0 0 0 * * ?', '数谋对接-初始化数据(紧急使用，默认不启动)', '2022-01-24 14:10:39', '2022-01-24 14:10:52', '戴倚天', '', 'FIRST', 'SmInitJobHandler', 'brand,category,company,customer,customer_address,customer_level,store,integrals_details,balance_details,activity,trade', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-01-24 14:10:39', '');

ALTER TABLE `s2b_statistics`.`sm_handle_record`
    ADD COLUMN `sub_biz_type` varchar(20) NULL COMMENT '业务子类型：' AFTER `del_flag`;

CREATE TABLE `s2b_statistics`.`replay_coupon_marketing_customer_scope` (
                                                          `marketing_customer_scope_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
                                                          `activity_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                                          `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                                          PRIMARY KEY (`marketing_customer_scope_id`) USING BTREE,
                                                          KEY `idx_activity_id` (`activity_id`) USING BTREE,
                                                          KEY `idx_customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券活动目标客户作用范围';
insert into `s2b_statistics`.`replay_coupon_marketing_customer_scope` select  * from `sbc-marketing`.`coupon_marketing_customer_scope`;

CREATE TABLE If Not Exists `s2b_statistics`.`replay_o2o_coupon_marketing_store_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '优惠券id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '门店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券和门店关联表';
insert into `s2b_statistics`.`replay_o2o_coupon_marketing_store_rel` select  * from `sbc-marketing`.`o2o_coupon_marketing_store_rel`;

ALTER TABLE `s2b_statistics`.`replay_o2o_coupon_marketing_store_rel`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;



CREATE TABLE If Not Exists `s2b_statistics`.`replay_o2o_marketing_participate_store` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `marketing_id` bigint(20) DEFAULT NULL COMMENT '营销表关联id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '门店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='营销和门店关联表';
insert into `s2b_statistics`.`replay_o2o_marketing_participate_store` select  * from `sbc-marketing`.`o2o_marketing_participate_store`;

ALTER TABLE `s2b_statistics`.`replay_o2o_marketing_participate_store`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;



-- 只保存已经过期的店铺
CREATE TABLE `s2b_statistics`.`sm_store_expired` (
  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='已过期店铺表';

-- 增加店铺过期处理
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (13, '0 0 0 * * ?', '数谋对接-店铺过期数据', '2022-01-24 14:10:39', '2022-01-24 14:10:52', '戴倚天', '', 'FIRST', 'SmStoreExpiredJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-01-24 14:10:39', '');

-- 优惠券活动增加业务类型
ALTER TABLE `sbc-marketing`.`coupon_activity`
ADD COLUMN `business_source` tinyint(4) NULL DEFAULT 0 COMMENT '业务来源 0:sbc系统产生,1:对外接入产生' AFTER `scan_version`;

ALTER TABLE `s2b_statistics`.`replay_coupon_activity`
ADD COLUMN `business_source` tinyint(4) NULL DEFAULT 0 COMMENT '业务来源 0:sbc系统产生,1:对外接入产生' AFTER `scan_version`;

-- o2o商品SPU扩展表 增加更新时间戳
ALTER TABLE `s2b_statistics`.`replay_o2o_goods_extension`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;

ALTER TABLE `s2b_statistics`.`replay_o2o_goods_sku_extension`
    ADD COLUMN `update_time_stamp` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间戳',
ADD INDEX `idx_update_time_stamp`(`update_time_stamp`) USING BTREE;