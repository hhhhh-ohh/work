CREATE TABLE `s2b_statistics`.`replay_flash_sale_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_date` varchar(10) DEFAULT NULL COMMENT '活动日期：2019-06-11',
  `activity_time` varchar(10) DEFAULT NULL COMMENT '活动时间：13:00',
  `goods_info_id` varchar(32) NOT NULL COMMENT 'skuID',
  `goods_id` varchar(32) NOT NULL COMMENT 'spuID',
  `price` decimal(10,2) NOT NULL COMMENT '抢购价',
  `stock` int(8) NOT NULL COMMENT '上限库存',
  `sales_volume` bigint(20) DEFAULT '0' COMMENT '抢购销量',
  `cate_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `max_num` int(8) NOT NULL COMMENT '限购数量',
  `min_num` int(8) DEFAULT NULL COMMENT '起售数量',
  `store_id` bigint(20) DEFAULT NULL COMMENT '商家ID',
  `postage` tinyint(4) DEFAULT NULL COMMENT '包邮标志，0：不包邮 1:包邮',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标志，0:未删除 1:已删除',
  `activity_full_time` datetime DEFAULT NULL COMMENT '活动日期+时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_goods_info_id` (`goods_info_id`),
  KEY `idx_goods_id` (`goods_id`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2112981 DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';

CREATE TABLE `s2b_statistics`.`replay_booking_sale` (
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
  KEY `booking_type_index` (`booking_type`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预售表';

CREATE TABLE `s2b_statistics`.`replay_booking_sale_goods` (
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
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预售商品表';


CREATE TABLE `s2b_statistics`.`replay_coupon_activity` (
  `activity_id` varchar(32) NOT NULL COMMENT '优惠券活动id',
  `activity_name` varchar(50) NOT NULL COMMENT '优惠券活动名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `activity_type` tinyint(4) NOT NULL COMMENT '优惠券类型，0全场赠券，1指定赠券，2进店赠券，3注册赠券，4权益赠券，5分销邀新赠券，6积分兑换券，7企业会员注册赠券',
  `receive_type` tinyint(4) DEFAULT '0' COMMENT '领取类型，0 每人限领次数不限，1 每人限领N次',
  `receive_count` int(11) DEFAULT NULL COMMENT '优惠券被使用后可再次领取的次数，每次仅限领取1张',
  `terminals` varchar(10) DEFAULT NULL COMMENT '生效终端，逗号分隔 0全部,1.PC,2.移动端,3.APP',
  `join_level` varchar(500) NOT NULL COMMENT '参加会员  -2 指定用户 -1:全部客户 0:全部等级 other:其他等级 -3：指定人群 -4：企业会员',
  `join_level_type` tinyint(4) DEFAULT NULL COMMENT '是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）',
  `store_id` bigint(20) DEFAULT '0' COMMENT '商户id',
  `platform_flag` tinyint(4) DEFAULT '0' COMMENT '是否平台 1平台 0店铺',
  `pause_flag` tinyint(4) DEFAULT '0' COMMENT '是否暂停 0进行 1暂停',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `left_group_num` int(11) DEFAULT NULL COMMENT '剩余优惠券组数',
  `activity_title` varchar(50) DEFAULT NULL COMMENT '参与成功通知标题',
  `activity_desc` varchar(50) DEFAULT NULL COMMENT '参与成功通知描述',
  PRIMARY KEY (`activity_id`),
  UNIQUE KEY `activity_id_UNIQUE` (`activity_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_activity_type` (`activity_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券活动表';


CREATE TABLE `s2b_statistics`.`replay_coupon_activity_config` (
  `activity_config_id` varchar(32) NOT NULL COMMENT '优惠券活动配置表id',
  `activity_id` varchar(32) NOT NULL COMMENT '活动id',
  `coupon_id` varchar(32) NOT NULL COMMENT '优惠券id',
  `total_count` int(11) NOT NULL COMMENT '优惠券总张数',
  `has_left` tinyint(4) DEFAULT NULL COMMENT '是否有剩余，1是，2否',
  PRIMARY KEY (`activity_config_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券活动配置表';


CREATE TABLE `s2b_statistics`.`replay_coupon_code` (
  `coupon_code_id` varchar(32) NOT NULL COMMENT '优惠券码id',
  `coupon_code` varchar(50) NOT NULL COMMENT '券码 生成规则 10位（数字+大写英文）',
  `coupon_id` varchar(32) NOT NULL COMMENT '优惠券id',
  `activity_id` varchar(32) NOT NULL COMMENT '优惠券活动id',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '领取人id,同时表示领取状态',
  `use_status` tinyint(4) DEFAULT '0' COMMENT '使用状态,0(未使用)，1(使用)',
  `acquire_time` datetime DEFAULT NULL COMMENT '获得优惠券时间',
  `use_date` datetime DEFAULT NULL COMMENT '使用时间',
  `order_code` varchar(45) DEFAULT NULL COMMENT '使用的订单号',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  PRIMARY KEY (`coupon_code_id`),
  UNIQUE KEY `code_index` (`coupon_code`) USING BTREE,
  KEY `idx_qm_coupon_code_coupon_id` (`coupon_id`),
  KEY `idx_qm_coupon_code_activity_id` (`activity_id`),
  KEY `idx_acquire_time` (`acquire_time`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券券码表';


CREATE TABLE `s2b_statistics`.`replay_coupon_info` (
  `coupon_id` varchar(32) NOT NULL COMMENT '优惠券主键Id',
  `coupon_name` varchar(20) NOT NULL COMMENT '优惠券名称',
  `range_day_type` tinyint(4) NOT NULL COMMENT '起止时间类型 0：按起止时间，1：按N天有效',
  `start_time` datetime DEFAULT NULL COMMENT '优惠券开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '优惠券结束时间',
  `effective_days` int(11) DEFAULT NULL COMMENT '有效天数',
  `fullbuy_price` decimal(10,2) DEFAULT NULL COMMENT '购满多少钱',
  `fullbuy_type` tinyint(4) NOT NULL COMMENT '购满类型 0：无门槛，1：满N元可使用',
  `denomination` decimal(10,2) NOT NULL COMMENT '优惠券面值',
  `store_id` bigint(20) DEFAULT '0' COMMENT '商户id',
  `platform_flag` tinyint(4) DEFAULT '0' COMMENT '是否平台优惠券 0店铺 1平台',
  `scope_type` tinyint(4) DEFAULT NULL COMMENT '营销类型(0,1,2,3,4),0全部，1品牌，2boss分类，3.店铺分类，4自定义货品（店铺可用）',
  `coupon_desc` text COMMENT '优惠券说明',
  `coupon_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '优惠券类型 0通用券 1店铺券 2运费券',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  PRIMARY KEY (`coupon_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_coupon_type` (`coupon_type`),
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券表';

CREATE TABLE `s2b_statistics`.`replay_appointment_sale` (
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
    KEY `appointment_type_index` (`appointment_type`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预约抢购表';

CREATE TABLE `s2b_statistics`.`replay_appointment_sale_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appointment_sale_id` bigint(20) NOT NULL COMMENT '预约id',
  `store_id` bigint(20) NOT NULL COMMENT '商户id',
  `goods_info_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT 'skuID',
  `goods_id` varchar(32) NOT NULL COMMENT 'spuID',
  `price` decimal(10,2) DEFAULT NULL COMMENT '预约价',
  `appointment_count` int(8) NOT NULL DEFAULT '0' COMMENT '预约数量',
  `buyer_count` int(8) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `appointment_goods_info_id_index` (`goods_info_id`),
  KEY `appointment_goods_store_index` (`store_id`),
  KEY `appointment_sale_id_index` (`appointment_sale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8mb4 COMMENT='预约商品表';

CREATE TABLE `s2b_statistics`.`replay_groupon_goods_info` (
 `groupon_goods_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拼团商品ID',
 `goods_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SKU编号',
 `groupon_price` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '拼团价格',
 `start_selling_num` int(10) DEFAULT '1' COMMENT '起售数量',
 `limit_selling_num` int(10) DEFAULT '1' COMMENT '限购数量',
 `groupon_activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拼团活动ID',
 `groupon_cate_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拼团分类ID',
 `sticky` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精选，0：否，1：是',
 `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '店铺ID',
 `goods_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SPU编号',
 `goods_sales_num` int(20) NOT NULL DEFAULT '0' COMMENT '商品销售数量',
 `order_sales_num` int(20) NOT NULL DEFAULT '0' COMMENT '订单数量',
 `trade_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '交易额',
 `already_groupon_num` int(20) NOT NULL DEFAULT '0' COMMENT '已成团人数',
 `refund_num` int(20) NOT NULL DEFAULT '0' COMMENT '成团后退单数量',
 `refund_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '成团后退单金额',
 `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
 `end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
 `audit_status` int(11) DEFAULT '0' COMMENT '活动审核状态',
 `update_time` datetime NOT NULL COMMENT '更新时间',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 PRIMARY KEY (`groupon_goods_id`),
 KEY `index_group_activity_id` (`groupon_activity_id`) USING BTREE,
 KEY `index_group_cate_id` (`groupon_cate_id`) USING BTREE,
 KEY `index_activity_id_spu_id` (`groupon_activity_id`,`goods_id`) USING BTREE,
 KEY `goods_info_id` (`goods_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团商品表';


DROP TABLE IF EXISTS `s2b_statistics`.`customer_first_pay`;
CREATE TABLE `s2b_statistics`.`customer_first_pay`  (
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会员id',
  `company_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司id',
  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
  `first_pay_time` datetime(0) NULL DEFAULT NULL COMMENT '首次支付时间',
  `parent_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '首次支付的订单的父id',
  PRIMARY KEY (`customer_id`, `store_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员首次支付记录表' ROW_FORMAT = Compact;

CREATE TABLE `s2b_statistics`.`replay_groupon_share_record` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`groupon_activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拼团活动ID',
`customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
`goods_id` varchar(32) NOT NULL COMMENT 'SPU id',
`goods_info_id` varchar(32) NOT NULL COMMENT 'SKU id',
`store_id` bigint(11) NOT NULL COMMENT '店铺ID',
`company_info_id` bigint(11) NOT NULL COMMENT '公司信息ID',
`terminal_source` tinyint(3) DEFAULT NULL COMMENT '终端：1 H5，2pc，3APP，4小程序',
`share_channel` tinyint(1) DEFAULT NULL COMMENT '分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片',
`create_time` datetime NOT NULL COMMENT '创建时间',
`share_customer_id` varchar(32) DEFAULT NULL COMMENT '分享人，通过分享链接访问的时候',
`type` tinyint(4) DEFAULT NULL COMMENT '0分享拼团，1通过分享链接访问拼团',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='拼团分享访问明细记录';

create table `s2b_statistics`.full_money_booking
(
    id              bigint auto_increment comment '主键'
        primary key,
    marketing_id    bigint         null comment '活动ID',
    store_id        bigint         null comment '店铺ID',
    customer_id     varchar(64)    null comment '用户ID',
    pay_money       decimal(10, 2) null comment '营销支付金额',
    discount_money  decimal(10, 2) null comment '营销优惠金额',
    pay_goods_count bigint         null comment '营销支付件数',
    goods_info_id   varchar(32)    null comment 'skuid',
    goods_info_name varchar(255)   null comment '商品名称',
    goods_info_no   varchar(45)    null comment 'SKU编码',
    customer_flag   tinyint        null comment '0：新客户 1:老客户',
    create_time     datetime       null comment '创建时间'
)
    comment '全款预售营销';

create table `s2b_statistics`.deposit_booking
(
    id              bigint auto_increment comment '主键'
        primary key,
    marketing_id    bigint         null comment '活动ID',
    store_id        bigint         null comment '店铺ID',
    customer_id     varchar(64)    null comment '用户ID',
    pay_money       decimal(10, 2) null comment '营销支付金额',
    discount_money  decimal(10, 2) null comment '营销优惠金额',
    pay_goods_count bigint         null comment '营销支付件数',
    goods_info_id   varchar(32)    null comment 'skuid',
    goods_info_name varchar(255)   null comment '商品名称',
    goods_info_no   varchar(45)    null comment 'SKU编码',
    presell_type    tinyint        null comment '0：订金 1:尾款',
    create_time     datetime       null comment '创建时间'
)
    comment '订金预售营销';

-- 预约明细
CREATE TABLE `s2b_statistics`.`appointment_trade_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `marketing_id` bigint(20) NOT NULL COMMENT '活动ID',
  `customer_id` varchar(32) NOT NULL COMMENT '客户id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `tid` varchar(22) DEFAULT NULL COMMENT '订单id',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
  `goods_info_id` varchar(32) DEFAULT NULL COMMENT 'skuid',
  `goods_info_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_info_no` varchar(45) DEFAULT NULL COMMENT 'SKU编码',
  `spec_details` varchar(50) DEFAULT NULL COMMENT '规格信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `marketing_id_index` (`marketing_id`) USING BTREE,
  KEY `customer_id_index` (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预约营销概况';

CREATE TABLE `s2b_statistics`.`replay_appointment_record` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `buyer_id` varchar(32) DEFAULT NULL COMMENT '会员id',
  `appointment_sale_id` bigint(20) DEFAULT NULL COMMENT '活动id',
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '预约价',
  `appointment_type` tinyint(4) DEFAULT NULL COMMENT '预约类型 0：不预约不可购买  1：不预约可购买',
  `goods_info_id` varchar(32) DEFAULT NULL COMMENT 'skuid',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'spuid',
  `sku_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `company_id` bigint(20) DEFAULT NULL COMMENT '商家id',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `appointment_sale_id_index` (`appointment_sale_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='预约记录';
-- 秒杀活动统计表
create table s2b_statistics.flash_sale
(
  id              bigint auto_increment
  comment '主键'
    primary key,
  marketing_id    bigint         null
  comment '活动ID',
  store_id        bigint         null
  comment '店铺ID',
  customer_id     varchar(64)    null
  comment '用户ID',
  pay_money       decimal(10, 2) null
  comment '营销支付金额',
  discount_money  decimal(10, 2) null
  comment '营销优惠金额',
  pay_goods_count bigint         null
  comment '营销支付件数',
  goods_info_id   varchar(32)    null
  comment 'skuid',
  goods_info_name varchar(255)   null
  comment '商品名称',
  goods_info_no   varchar(45)    null
  comment 'SKU编码',
  create_time     datetime       null
  comment '创建时间'
)
  comment '秒杀营销';

CREATE TABLE `s2b_statistics`.`groupon_trade_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `marketing_id` varchar(32) DEFAULT NULL COMMENT '活动ID',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `customer_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
  `goods_info_id` varchar(32) DEFAULT NULL COMMENT 'skuid',
  `goods_info_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `goods_info_no` varchar(45) DEFAULT NULL COMMENT 'SKU编码',
  `customer_flag` tinyint(4) DEFAULT NULL COMMENT '0：新客户 1:老客户',
  `groupon_order_status` tinyint(4) DEFAULT NULL COMMENT '团状态 0待开团，1待成团，2已成团，3拼团失败',
  `groupon_invitee` tinyint(4) DEFAULT NULL COMMENT '邀请拼团',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `group_id` varchar(64) DEFAULT NULL COMMENT '活动ID和skuid拼接一起',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='拼团营销效果';

CREATE TABLE `s2b_statistics`.`replay_trade_groupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tid` varchar(32) DEFAULT NULL COMMENT '订单号',
  `groupon_no` varchar(32) DEFAULT NULL COMMENT '团号',
  `groupon_activity_id` varchar(32) DEFAULT NULL COMMENT '活动id',
  `goods_info_id` varchar(32) DEFAULT NULL COMMENT 'skuid',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'spuid',
  `return_num` bigint(10) DEFAULT NULL COMMENT '退单商品数量',
  `return_price` decimal(10,2) DEFAULT NULL COMMENT '退单金额',
  `groupon_success_time` datetime DEFAULT NULL COMMENT '成团时间',
  `fail_time` datetime DEFAULT NULL COMMENT '团失败时间',
  `groupon_order_status` tinyint(4) DEFAULT NULL COMMENT '团状态 0待开团，1待成团，2已成团，3拼团失败',
  `leader` bigint(20) DEFAULT NULL COMMENT '是否团长 0不是，1是',
  `pay_state` tinyint(4) DEFAULT NULL COMMENT '支付状态 0未支付，1待确认，2已支付，3已支付定金',
  `groupon_invitee_id` varchar(32) DEFAULT NULL COMMENT '拼团邀请人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3379 DEFAULT CHARSET=utf8mb4 COMMENT='预约记录';

ALTER TABLE `s2b_statistics`.`replay_groupon_goods_info`
MODIFY COLUMN `goods_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SKU编号' AFTER `groupon_goods_id`,
MODIFY COLUMN `groupon_activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拼团活动ID' AFTER `limit_selling_num`;
ALTER TABLE `s2b_statistics`.`replay_groupon_share_record`
MODIFY COLUMN `groupon_activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拼团活动ID' AFTER `id`;

ALTER TABLE `s2b_statistics`.`replay_trade`
  ADD COLUMN `coupon_code_id` varbinary(40) NULL COMMENT '优惠券码ID',
  ADD COLUMN `coupon_code` varchar(50) NULL COMMENT '优惠券码',
  ADD COLUMN `coupon_type` tinyint(4) NULL COMMENT '优惠券类型 0通用券 1店铺券 2运费券',
  ADD COLUMN `discounts_amount` decimal(10,2) NULL COMMENT '优惠券面值',
  ADD COLUMN `is_booking_sale_goods` tinyint(4) NULL COMMENT '是否预售 0:否 1:是',
  ADD COLUMN `booking_type` tinyint(4) NULL COMMENT '类型预售 0:全款预售 1:定金预售',
  ADD COLUMN `is_flash_sale_goods` tinyint(4) NULL COMMENT '是否秒杀商品 0:否 1:是',
  ADD COLUMN `groupon_flag` tinyint(4) NULL COMMENT '是否拼团订单0:否 1:是',
  ADD COLUMN `store_id` bigint(20) NULL,
  ADD COLUMN `earnest_price` decimal(10,2) NULL COMMENT '定金',
  ADD COLUMN `swell_price` decimal(10,2) NULL COMMENT '定金膨胀',
  ADD COLUMN `tail_price` decimal(10,2) NULL COMMENT '尾款';

ALTER TABLE `s2b_statistics`.`replay_trade_item`
  ADD COLUMN `is_booking_sale_goods` tinyint(4) NULL COMMENT '是否预售商品 0:否 1:是',
  ADD COLUMN `booking_sale_id` bigint(20) NULL COMMENT '预售活动ID',
  ADD COLUMN `booking_type` tinyint(4) NULL COMMENT '预售类型',
  ADD COLUMN `flash_sale_goods_id` bigint(20) NULL COMMENT '秒杀抢购商品Id',
  ADD COLUMN `oid` varchar(32) NULL,
  ADD COLUMN `is_appointment_sale_goods` tinyint(4) NULL COMMENT '是否是预约商品 0:否 1:是 ',
  ADD COLUMN `appointment_sale_id` bigint(20) NULL COMMENT '预约活动id',
  ADD COLUMN `spec_details` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格信息';

CREATE TABLE `s2b_statistics`.`coupon_activity_effect_recent`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `store_id`               bigint(20) NOT NULL COMMENT '店铺id',
    `activity_id`            varchar(32)    DEFAULT NULL COMMENT '活动id',
    `pay_money`              decimal(12, 2) DEFAULT NULL COMMENT '支付金额',
    `discount_money`         decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
    `pay_goods_count`        bigint(20)     DEFAULT NULL COMMENT '支付件数',
    `pay_trade_count`        bigint(20)     DEFAULT NULL COMMENT '支付订单数',
    `old_customer_count`     bigint(20)     DEFAULT NULL COMMENT '老用户数',
    `new_customer_count`     bigint(20)     DEFAULT NULL COMMENT '新用户数',
    `acquire_count`          bigint(20)     DEFAULT NULL COMMENT '领取张数',
    `acquire_customer_count` bigint(20)     DEFAULT NULL COMMENT '领取人数',
    `use_count`              bigint(20)     DEFAULT NULL COMMENT '使用张数',
    `use_customer_count`     bigint(20)     DEFAULT NULL COMMENT '使用人数',
    `create_time`            datetime       DEFAULT NULL COMMENT '创建时间',
    `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
    `roi` decimal(12,2) DEFAULT NULL COMMENT 'roi',
    `joint_rate` decimal(12,2) DEFAULT NULL COMMENT '连带率',
    `use_rate` decimal(12,2) DEFAULT NULL COMMENT '使用率',
    `customer_price` decimal(12,2) DEFAULT NULL COMMENT '客单价',
    `stat_type` bigint(4) DEFAULT NULL COMMENT '0-最近日期（按照配置的动态时间），1-30天，2-90天',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_store_id` (`store_id`) USING BTREE,
    KEY `idx_activity_id` (`activity_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券活动效果——最近日期';

CREATE TABLE `s2b_statistics`.`coupon_info_effect_recent`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `store_id`               bigint(20) NOT NULL COMMENT '店铺id',
    `coupon_id`              varchar(32)    DEFAULT NULL COMMENT '优惠券id',
    `pay_money`              decimal(12, 2) DEFAULT NULL COMMENT '支付金额',
    `discount_money`         decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
    `pay_goods_count`        bigint(20)     DEFAULT NULL COMMENT '支付件数',
    `pay_trade_count`        bigint(20)     DEFAULT NULL COMMENT '支付订单数',
    `old_customer_count`     bigint(20)     DEFAULT NULL COMMENT '老用户数',
    `new_customer_count`     bigint(20)     DEFAULT NULL COMMENT '新用户数',
    `acquire_count`          bigint(20)     DEFAULT NULL COMMENT '领取张数',
    `acquire_customer_count` bigint(20)     DEFAULT NULL COMMENT '领取人数',
    `use_count`              bigint(20)     DEFAULT NULL COMMENT '使用张数',
    `use_customer_count`     bigint(20)     DEFAULT NULL COMMENT '使用人数',
    `create_time`            datetime       DEFAULT NULL COMMENT '创建时间',
    `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
    `roi` decimal(12,2) DEFAULT NULL COMMENT 'roi',
    `joint_rate` decimal(12,2) DEFAULT NULL COMMENT '连带率',
    `use_rate` decimal(12,2) DEFAULT NULL COMMENT '使用率',
    `customer_price` decimal(12,2) DEFAULT NULL COMMENT '客单价',
    `stat_type` bigint(4) DEFAULT NULL COMMENT '0-最近日期（按照配置的动态时间），1-30天，2-90天',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_store_id` (`store_id`) USING BTREE,
    KEY `idx_coupon_id` (`coupon_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券效果——最近日期';
CREATE TABLE `s2b_statistics`.`coupon_overview_day`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `store_id`               bigint(20) NOT NULL COMMENT '店铺id',
    `stat_date`              date           DEFAULT NULL COMMENT '统计日期',
    `pay_money`              decimal(12, 2) DEFAULT NULL COMMENT '支付金额',
    `discount_money`         decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
    `pay_goods_count`        bigint(20)     DEFAULT NULL COMMENT '支付件数',
    `pay_trade_count`        bigint(20)     DEFAULT NULL COMMENT '支付订单数',
    `old_customer_count`     bigint(20)     DEFAULT NULL COMMENT '老用户数',
    `new_customer_count`     bigint(20)     DEFAULT NULL COMMENT '新用户数',
    `acquire_count`          bigint(20)     DEFAULT NULL COMMENT '领取张数',
    `acquire_customer_count` bigint(20)     DEFAULT NULL COMMENT '领取人数',
    `use_count`              bigint(20)     DEFAULT NULL COMMENT '使用张数',
    `use_customer_count`     bigint(20)     DEFAULT NULL COMMENT '使用人数',
    `create_time`            datetime       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_store_id_and_stat_date` (`store_id`, `stat_date`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券概况——天';

CREATE TABLE `s2b_statistics`.`coupon_overview_recent`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `store_id`               bigint(20) NOT NULL COMMENT '店铺id',
    `pay_money`              decimal(12, 2) DEFAULT NULL COMMENT '支付金额',
    `discount_money`         decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
    `pay_goods_count`        bigint(20)     DEFAULT NULL COMMENT '支付件数',
    `pay_trade_count`        bigint(20)     DEFAULT NULL COMMENT '支付订单数',
    `old_customer_count`     bigint(20)     DEFAULT NULL COMMENT '老用户数',
    `new_customer_count`     bigint(20)     DEFAULT NULL COMMENT '新用户数',
    `acquire_count`          bigint(20)     DEFAULT NULL COMMENT '领取张数',
    `acquire_customer_count` bigint(20)     DEFAULT NULL COMMENT '领取人数',
    `use_count`              bigint(20)     DEFAULT NULL COMMENT '使用张数',
    `use_customer_count`     bigint(20)     DEFAULT NULL COMMENT '使用人数',
    `create_time`            datetime       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_store_id` (`store_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券概况——最近日期（7天30天）';
CREATE TABLE `s2b_statistics`.`coupon_overview_week`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `store_id`               bigint(20) NOT NULL COMMENT '店铺id',
    `week_start_date`        date           DEFAULT NULL COMMENT '周开始时间',
    `week_end_date`          date           DEFAULT NULL COMMENT '周结束时间',
    `pay_money`              decimal(12, 2) DEFAULT NULL COMMENT '支付金额',
    `discount_money`         decimal(12, 2) DEFAULT NULL COMMENT '优惠金额',
    `pay_goods_count`        bigint(20)     DEFAULT NULL COMMENT '支付件数',
    `pay_trade_count`        bigint(20)     DEFAULT NULL COMMENT '支付订单数',
    `old_customer_count`     bigint(20)     DEFAULT NULL COMMENT '老用户数',
    `new_customer_count`     bigint(20)     DEFAULT NULL COMMENT '新用户数',
    `acquire_count`          bigint(20)     DEFAULT NULL COMMENT '领取张数',
    `acquire_customer_count` bigint(20)     DEFAULT NULL COMMENT '领取人数',
    `use_count`              bigint(20)     DEFAULT NULL COMMENT '使用张数',
    `use_customer_count`     bigint(20)     DEFAULT NULL COMMENT '使用人数',
    `create_time`            datetime       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_store_id_and_stat_date` (`store_id`, `week_end_date`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='优惠券概况——月';

CREATE TABLE `s2b_statistics`.`replay_trade_coupon`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `tid`            varchar(32)    DEFAULT NULL,
    `oid`            varchar(32)    DEFAULT NULL COMMENT '订单明细id',
    `coupon_code_id` varbinary(40)  DEFAULT NULL COMMENT '优惠券码ID',
    `coupon_code`    varchar(50)    DEFAULT NULL COMMENT '优惠券码',
    `coupon_type`    tinyint(4)     DEFAULT NULL COMMENT '优惠券类型 0通用券 1店铺券 2运费券',
    `split_price`    decimal(10, 2) DEFAULT NULL COMMENT '除去优惠金额后的商品均摊价',
    `reduce_price`   decimal(10, 2) DEFAULT NULL COMMENT '优惠金额',
    PRIMARY KEY (`id`),
    KEY `idx_tid` (`tid`),
    KEY `idx_coupon_code` (`coupon_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` ( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (2, '10 8 0 * * ?', '营销分析-优惠券分析', '2021-01-18 15:43:45', '2021-01-18 15:43:45', '张高磊', '', 'FIRST', 'couponScheduled', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-18 15:43:45', '');

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 2, '0 30 1 * * ? *', '营销活动分析入库定时任务', '2021-01-12 19:57:47', '2021-01-19 20:20:52', '仲济川', '', 'FAILOVER', 'appointmentAnalysisJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-12 19:57:47', '');

CREATE TABLE `sbc-goods`.`groupon_share_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupon_activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拼团活动ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
  `goods_id` varchar(32) NOT NULL COMMENT 'SPU id',
  `goods_info_id` varchar(32) NOT NULL COMMENT 'SKU id',
  `store_id` bigint(11) NOT NULL COMMENT '店铺ID',
  `company_info_id` bigint(11) NOT NULL COMMENT '公司信息ID',
  `terminal_source` tinyint(3) DEFAULT NULL COMMENT '终端：1 H5，2pc，3APP，4小程序',
  `share_channel` tinyint(1) DEFAULT NULL COMMENT '分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `share_customer_id` varchar(32) DEFAULT NULL COMMENT '分享人，通过分享链接访问的时候',
  `type` tinyint(4) DEFAULT NULL COMMENT '0分享拼团，1通过分享链接访问拼团',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='拼团分享访问明细记录';

ALTER TABLE `sbc-goods`.`groupon_share_record`
MODIFY COLUMN `groupon_activity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '拼团活动ID' AFTER `id`;

CREATE TABLE `s2b_statistics`.`coupon_store_effect_recent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
  `pay_money` decimal(12,2) DEFAULT NULL COMMENT '支付金额',
  `discount_money` decimal(12,2) DEFAULT NULL COMMENT '优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '支付件数',
  `pay_trade_count` bigint(20) DEFAULT NULL COMMENT '支付订单数',
  `old_customer_count` bigint(20) DEFAULT NULL COMMENT '老用户数',
  `new_customer_count` bigint(20) DEFAULT NULL COMMENT '新用户数',
  `acquire_count` bigint(20) DEFAULT NULL COMMENT '领取张数',
  `acquire_customer_count` bigint(20) DEFAULT NULL COMMENT '领取人数',
  `use_count` bigint(20) DEFAULT NULL COMMENT '使用张数',
  `use_customer_count` bigint(20) DEFAULT NULL COMMENT '使用人数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
  `roi` decimal(12,2) DEFAULT NULL COMMENT 'roi',
  `joint_rate` decimal(12,2) DEFAULT NULL COMMENT '连带率',
  `use_rate` decimal(12,2) DEFAULT NULL COMMENT '使用率',
  `customer_price` decimal(12,2) DEFAULT NULL COMMENT '客单价',
  `stat_type` bigint(4) DEFAULT NULL COMMENT '0-最近日期（按照配置的动态时间），1-30天，2-90天',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券店铺效果——最近日期';

CREATE TABLE `s2b_statistics`.`marketing_task_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` date NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销分析定时任务执行记录表';

ALTER TABLE `s2b_statistics`.`groupon_trade_detail`
DROP COLUMN `customer_flag`;

alter table `s2b_statistics`.`full_money_booking`
    ADD COLUMN boss_customer_flag tinyint null comment 'boss端：0：新客户 1:老客户' after goods_info_no,
    ADD COLUMN boss_customer_flag_week tinyint null comment 'boss端周： 0：新客户 1:老客户' after boss_customer_flag,
    ADD COLUMN store_customer_flag tinyint null comment '商家端： 客户 1:老客户' after boss_customer_flag_week,
    ADD COLUMN store_customer_flag_week tinyint null comment '商家端周： 0：新客户 1:老客户' after store_customer_flag;

alter table `s2b_statistics`.`deposit_booking`
    ADD COLUMN boss_customer_flag tinyint null comment 'boss端：0：新客户 1:老客户' after presell_type,
    ADD COLUMN boss_customer_flag_week tinyint null comment 'boss端周： 0：新客户 1:老客户' after boss_customer_flag,
    ADD COLUMN store_customer_flag tinyint null comment '商家端： 客户 1:老客户' after boss_customer_flag_week,
    ADD COLUMN store_customer_flag_week tinyint null comment '商家端周： 0：新客户 1:老客户' after store_customer_flag;

alter table `s2b_statistics`.`export_data_request`
    ADD COLUMN PARAMS_MD5 varchar(255) null comment '参数md5' after EXPORT_STATUS;

-- 秒杀活动，新老客户统计字段
alter table `s2b_statistics`.`flash_sale`
    ADD COLUMN boss_customer_flag tinyint null comment 'boss端：0：新客户 1:老客户' after goods_info_no,
    ADD COLUMN boss_customer_flag_week tinyint null comment 'boss端周： 0：新客户 1:老客户' after boss_customer_flag,
    ADD COLUMN store_customer_flag tinyint null comment '商家端： 客户 1:老客户' after boss_customer_flag_week,
    ADD COLUMN store_customer_flag_week tinyint null comment '商家端周： 0：新客户 1:老客户' after store_customer_flag;

-- 拼团活动，新老客户统计字段
ALTER TABLE `s2b_statistics`.`groupon_trade_detail`
ADD COLUMN `boss_customer_flag` tinyint(4) NULL DEFAULT NULL COMMENT 'boss端：0：新客户 1:老客户' AFTER `group_id`,
ADD COLUMN `boss_customer_flag_week` tinyint(4) NULL DEFAULT NULL COMMENT 'boss端周： 0：新客户 1:老客户' AFTER `boss_customer_flag`,
ADD COLUMN `store_customer_flag` tinyint(4) NULL DEFAULT NULL COMMENT '商家端： 客户 1:老客户' AFTER `boss_customer_flag_week`,
ADD COLUMN `store_customer_flag_week` tinyint(4) NULL DEFAULT NULL COMMENT '商家端周： 0：新客户 1:老客户' AFTER `store_customer_flag`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;

ALTER TABLE `s2b_statistics`.`groupon_trade_detail`
ADD INDEX `idx_store_id`(`store_id`) USING BTREE,
ADD INDEX `idx_customer_id`(`customer_id`) USING BTREE,
ADD INDEX `idx_group_id`(`group_id`) USING BTREE,
ADD INDEX `idx_goods_info_name`(`goods_info_name`) USING BTREE,
ADD INDEX `idx_goods_info_id`(`goods_info_id`) USING BTREE,
ADD INDEX `idx_create_time`(`create_time`) USING BTREE;

ALTER TABLE `s2b_statistics`.`replay_trade_groupon`
ADD INDEX `idx_tid`(`tid`) USING BTREE,
ADD INDEX `idx_groupon_activity_id`(`groupon_activity_id`) USING BTREE,
ADD INDEX `idx_goods_info_id`(`goods_info_id`) USING BTREE;

ALTER TABLE `s2b_statistics`.`customer_first_pay`
ADD INDEX `idx_customer_id`(`customer_id`) USING BTREE,
ADD INDEX `idx_store_id`(`store_id`) USING BTREE,
ADD INDEX `idx_first_pay_time`(`first_pay_time`) USING BTREE;

ALTER TABLE `s2b_statistics`.`replay_groupon_goods_info`
ADD INDEX `index_start_time`(`start_time`) USING BTREE,
ADD INDEX `index_end_time`(`end_time`) USING BTREE;

ALTER TABLE `s2b_statistics`.`marketing_task_record`
ADD COLUMN `marketing_type` tinyint(4) NULL COMMENT '营销类型 0 优惠券 1拼团 2秒杀 3满系 4打包一口价 5第二件半价 6组合购 7预约 8全款预售 9订金预售' AFTER `create_time`;