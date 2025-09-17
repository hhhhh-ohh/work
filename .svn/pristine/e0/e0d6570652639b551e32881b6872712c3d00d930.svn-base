CREATE TABLE `sbc-customer`.`community_leader` (
  `leader_id` varchar(32) NOT NULL COMMENT '团长id',
  `leader_account` varchar(20) NOT NULL COMMENT '团长账号',
  `leader_name` varchar(30) NOT NULL COMMENT '团长名称',
  `leader_description` varchar(200) DEFAULT NULL COMMENT '团长简介',
  `check_status` tinyint(2) NOT NULL COMMENT '审核状态, 0:未审核 1:审核通过 2:审核失败 3:禁用中',
  `check_reason` varchar(255) DEFAULT NULL COMMENT '驳回原因',
  `disable_reason` varchar(255) DEFAULT NULL COMMENT '禁用原因',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `disable_time` datetime DEFAULT NULL COMMENT '禁用时间',
  `assist_flag` tinyint(2) NOT NULL COMMENT '是否帮卖 0:否 1:是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `customer_id` varchar(32) NOT NULL COMMENT '会员id',
  `del_flag` tinyint(2) NOT NULL COMMENT '删除标识 0:未删除 1:已删除',
  PRIMARY KEY (`leader_id`),
  KEY `leader_account` (`leader_account`),
  KEY `check_status` (`check_status`),
  KEY `assist_flag` (`assist_flag`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB  COMMENT='社区团购团长表';

CREATE TABLE `sbc-customer`.`community_leader_pickup_point` (
               `pickup_point_id` varchar(32) NOT NULL COMMENT '自提点id',
               `leader_id` varchar(32) NOT NULL COMMENT '团长id',
               `leader_account` varchar(20) NOT NULL COMMENT '团长账号',
               `leader_name` varchar(30) NOT NULL COMMENT '团长名称',
               `check_status` tinyint(2) NOT NULL COMMENT '审核状态, 0:未审核 1审核通过 2审核失败 3禁用中',
               `pickup_point_name` varchar(20) NOT NULL COMMENT '自提点名称',
               `pickup_province_id` bigint(10) NOT NULL COMMENT '自提点省份',
               `pickup_city_id` bigint(10) NOT NULL COMMENT '自提点城市',
               `pickup_area_id` bigint(10) NOT NULL COMMENT '自提点区县',
               `pickup_street_id` bigint(10) DEFAULT NULL COMMENT '自提点街道',
               `address` varchar(200) NOT NULL COMMENT '详细地址',
               `lng` decimal(20,6) DEFAULT NULL COMMENT '经度',
               `lat` decimal(20,6) DEFAULT NULL COMMENT '纬度',
               `contact_number` varchar(50) NOT NULL COMMENT '联系电话',
               `pickup_time` varchar(200) NOT NULL COMMENT '自提时间',
               `create_time` datetime NOT NULL COMMENT '创建时间',
               `update_time` datetime NOT NULL COMMENT '更新时间',
               `full_address` varchar(255) DEFAULT NULL COMMENT '全详细地址',
               `del_flag` tinyint(2) NOT NULL COMMENT '删除标识 0:未删除 1:已删除',
               `customer_id` varchar(32) DEFAULT NULL COMMENT '会员id',
               `photos` varchar(3000) DEFAULT NULL COMMENT '自提点照片',
               PRIMARY KEY (`pickup_point_id`),
               KEY `leader_id` (`leader_id`),
               KEY `leader_account` (`leader_account`),
               KEY `check_status` (`check_status`),
               KEY `pickup_province_id` (`pickup_province_id`),
               KEY `pickup_city_id` (`pickup_city_id`),
               KEY `pickup_area_id` (`pickup_area_id`),
               KEY `create_time` (`create_time`),
               KEY `del_flag` (`del_flag`)
) ENGINE=InnoDB  COMMENT='团长自提点表';

CREATE TABLE `sbc-marketing`.`community_activity` (
     `activity_id` varchar(32) NOT NULL COMMENT '主键',
     `store_id` bigint(20) NOT NULL COMMENT '店铺id',
     `activity_name` varchar(100) NOT NULL COMMENT '活动名称',
     `description` varchar(400) DEFAULT NULL COMMENT '活动描述',
     `start_time` datetime NOT NULL COMMENT '开始时间',
     `end_time` datetime NOT NULL COMMENT '结束时间',
     `logistics_type` varchar(10) DEFAULT NULL COMMENT '物流方式 0:自提 1:快递 以逗号拼凑',
     `sales_type` varchar(10) DEFAULT NULL COMMENT '销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑',
     `sales_range` tinyint(2) NOT NULL COMMENT '自主销售范围 0：全部 1：地区 2：自定义',
     `leader_range` tinyint(2) NOT NULL COMMENT '帮卖团长范围 0：全部 1：地区 2：自定义',
     `commission_flag` tinyint(2) NOT NULL COMMENT '佣金设置 0：商品 1：按团长/自提点',
     `pickup_commission` decimal(10,2) DEFAULT NULL COMMENT '批量-自提服务佣金',
     `assist_commission` decimal(10,2) DEFAULT NULL COMMENT '批量-帮卖团长佣金',
     `details` mediumtext COMMENT '团详情',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     `update_time` datetime DEFAULT NULL COMMENT '结束时间',
     `generate_flag` tinyint(2) DEFAULT NULL COMMENT '是否生成 0:未生成 1:已生成',
     `generate_time` datetime(3) DEFAULT NULL COMMENT '生成时间',
     `images` text COMMENT '图片',
     `video_url` varchar(200) DEFAULT NULL COMMENT '视频',
     `real_end_time` datetime DEFAULT NULL COMMENT '延时结束时间',
     PRIMARY KEY (`activity_id`),
     KEY `store_id` (`store_id`),
     KEY `activity_name` (`activity_name`)
) ENGINE=InnoDB  COMMENT='社区团购活动表';

CREATE TABLE `sbc-marketing`.`community_area_rel` (
     `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
     `activity_id` varchar(32) NOT NULL COMMENT '活动id',
     `area_id` varchar(32) NOT NULL COMMENT '区域Id',
     `area_name` varchar(100) DEFAULT NULL COMMENT '区域名称',
     `start_time` datetime NOT NULL COMMENT '开始时间',
     `end_time` datetime NOT NULL COMMENT '结束时间',
     `sales_type` tinyint(2) NOT NULL COMMENT '销售渠道 0:自主销售 1:团长帮卖',
     PRIMARY KEY (`id`),
     KEY `activity_id` (`activity_id`),
     KEY `area_id` (`area_id`),
     KEY `sales_type` (`sales_type`),
     KEY `start_time` (`start_time`,`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=57 COMMENT='社区团购活动区域关联表';

CREATE TABLE `sbc-marketing`.`community_assist_record` (
          `id` varchar(32) NOT NULL COMMENT '主键',
          `activity_id` varchar(32) NOT NULL COMMENT '活动id',
          `store_id` bigint(20) NOT NULL COMMENT '店铺id',
          `leader_id` varchar(32) NOT NULL COMMENT '团长id',
          `create_time` datetime NOT NULL COMMENT '创建时间',
          PRIMARY KEY (`id`),
          KEY `idx_activity` (`activity_id`),
          KEY `idx_store` (`store_id`),
          KEY `idx_leader` (`leader_id`),
          KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB  COMMENT='社区团购活动帮卖转发记录表';

CREATE TABLE `sbc-marketing`.`community_commission_area_rel` (
                `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                `activity_id` varchar(32) NOT NULL COMMENT '活动id',
                `area_id` varchar(32) NOT NULL COMMENT '区域Id',
                `area_name` varchar(100) DEFAULT NULL COMMENT '区域名称',
                `pickup_commission` decimal(10,2) DEFAULT NULL COMMENT '自提服务佣金',
                `assist_commission` decimal(10,2) DEFAULT NULL COMMENT '帮卖团长佣金',
                `group_no` int(11) DEFAULT NULL COMMENT '分组号',
                PRIMARY KEY (`id`),
                KEY `idx_activity_id` (`activity_id`),
                KEY `idx_area_id` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 COMMENT='社区团购活动佣金区域关联明细表';

CREATE TABLE `sbc-marketing`.`community_commission_leader_rel` (
                  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                  `activity_id` varchar(32) NOT NULL COMMENT '活动id',
                  `leader_id` varchar(32) DEFAULT NULL COMMENT '团长Id',
                  `pickup_point_id` varchar(32) NOT NULL COMMENT '团长自提点Id',
                  `pickup_commission` decimal(10,2) DEFAULT NULL COMMENT '自提服务佣金',
                  `assist_commission` decimal(10,2) DEFAULT NULL COMMENT '帮卖团长佣金',
                  PRIMARY KEY (`id`),
                  KEY `idx_activity_id` (`activity_id`),
                  KEY `idx_leader_id` (`leader_id`),
                  KEY `idx_pickup_point_id` (`pickup_point_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 COMMENT='社区团购活动佣金团长关联表';

CREATE TABLE `sbc-marketing`.`community_delivery_order` (
           `id` varchar(32) NOT NULL COMMENT 'id',
           `activity_id` varchar(32) NOT NULL COMMENT '活动id',
           `leader_id` varchar(32) DEFAULT NULL COMMENT '团长Id',
           `area_name` varchar(32) DEFAULT NULL COMMENT '区域名称',
           `goods_context` text COMMENT '商品json内容',
           `delivery_status` tinyint(2) NOT NULL COMMENT '发货状态 0:未发货 1:已发货',
           `type` tinyint(2) NOT NULL COMMENT '汇总类型 0:按团长 1:按区域',
           `leader_name` varchar(30) DEFAULT NULL COMMENT '团长名称',
           `pickup_point_name` varchar(30) DEFAULT NULL COMMENT '自提点名称',
           `contact_number` varchar(50) DEFAULT NULL COMMENT '联系电话',
           `full_address` varchar(255) DEFAULT NULL COMMENT '全详细地址',
           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
           PRIMARY KEY (`id`),
           KEY `ix_activity_id` (`activity_id`),
           KEY `ix_leader_id` (`leader_id`),
           KEY `delivery_status` (`delivery_status`),
           KEY `type` (`type`)
) ENGINE=InnoDB  COMMENT='社区团购发货单';

CREATE TABLE `sbc-marketing`.`community_leader_rel` (
       `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
       `activity_id` varchar(32) NOT NULL COMMENT '活动id',
       `leader_id` varchar(32) DEFAULT NULL COMMENT '团长Id',
       `pickup_point_id` varchar(32) NOT NULL COMMENT '团长自提点Id',
       `start_time` datetime NOT NULL COMMENT '开始时间',
       `end_time` datetime NOT NULL COMMENT '结束时间',
       `sales_type` tinyint(2) NOT NULL COMMENT '销售渠道 0:自主销售 1:团长帮卖',
       PRIMARY KEY (`id`),
       KEY `activity_id` (`activity_id`),
       KEY `leader_id` (`leader_id`),
       KEY `pickup_point_id` (`pickup_point_id`),
       KEY `sales_type` (`sales_type`),
       KEY `start_time` (`start_time`,`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=67 COMMENT='社区团购活动团长关联表';

CREATE TABLE `sbc-marketing`.`community_region_area_setting` (
                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                `region_id` bigint(20) NOT NULL COMMENT '区域id',
                `area_id` varchar(32) NOT NULL COMMENT '省市区id',
                `area_name` varchar(200) NOT NULL COMMENT '省市区名称',
                `store_id` bigint(20) NOT NULL COMMENT '店铺id',
                PRIMARY KEY (`id`),
                KEY `idx_region_id` (`region_id`),
                KEY `idx_area_id` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 COMMENT='社区团购区域省市区设置表';

CREATE TABLE `sbc-marketing`.`community_region_leader_setting` (
                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                  `region_id` bigint(20) NOT NULL COMMENT '区域id',
                  `leader_id` varchar(32) DEFAULT NULL COMMENT '团长id',
                  `pickup_point_id` varchar(32) NOT NULL COMMENT '自提点id',
                  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
                  PRIMARY KEY (`id`),
                  KEY `idx_area_id` (`region_id`),
                  KEY `idx_leader_id` (`leader_id`),
                  KEY `idx_pickup_point_id` (`pickup_point_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 COMMENT='社区团购区域团长设置表';

CREATE TABLE `sbc-marketing`.`community_region_setting` (
           `region_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '区域id',
           `store_id` bigint(20) NOT NULL COMMENT '店铺id',
           `region_name` varchar(20) NOT NULL COMMENT '区域名称',
           PRIMARY KEY (`region_id`),
           KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 COMMENT='社区拼团区域设置表';

CREATE TABLE `sbc-marketing`.`community_setting` (
    `store_id` bigint(20) NOT NULL COMMENT '店铺id',
    `delivery_order_type` varchar(10) DEFAULT NULL COMMENT '汇总类型 0:按团长 1:按区域 以逗号拼凑',
    `delivery_area_type` tinyint(2) NOT NULL COMMENT '区域汇总类型 0：省份1：城市2：自定义',
    PRIMARY KEY (`store_id`)
) ENGINE=InnoDB  COMMENT='社区拼团商家设置表';

CREATE TABLE `sbc-marketing`.`community_sku_rel` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `activity_id` varchar(32) NOT NULL COMMENT '活动id',
    `spu_id` varchar(32) NOT NULL COMMENT '商品spuId',
    `sku_id` varchar(32) NOT NULL COMMENT '商品skuId',
    `price` decimal(20,2) DEFAULT NULL COMMENT '活动价',
    `pickup_commission` decimal(10,2) DEFAULT NULL COMMENT '自提服务佣金',
    `assist_commission` decimal(10,2) DEFAULT NULL COMMENT '帮卖佣金',
    `activity_stock` bigint(11) DEFAULT NULL COMMENT '活动库存',
    `sales` bigint(11) DEFAULT NULL COMMENT '已买库存',
    PRIMARY KEY (`id`),
    KEY `idx_activity_id` (`activity_id`),
    KEY `idx_spu_id` (`spu_id`),
    KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 COMMENT='社区团购活动商品表';

CREATE TABLE `sbc-marketing`.`community_statistics` (
       `id` varchar(32) NOT NULL COMMENT 'id',
       `activity_id` varchar(32) NOT NULL COMMENT '活动id',
       `store_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
       `leader_id` varchar(32) NOT NULL COMMENT '团长id',
       `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
       `pay_num` bigint(20) DEFAULT NULL COMMENT '支付订单个数',
       `pay_total` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
       `assist_num` bigint(20) DEFAULT NULL COMMENT '帮卖团长数',
       `assist_pay_num` bigint(20) DEFAULT NULL COMMENT '成团团长数',
       `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
       `assist_order_total` decimal(20,2) DEFAULT NULL COMMENT '帮卖总额',
       `assist_order_ratio` decimal(20,2) DEFAULT NULL COMMENT '帮卖占比',
       `pickup_service_order_num` bigint(20) DEFAULT NULL COMMENT '自提服务订单数',
       `pickup_service_order_total` decimal(20,2) DEFAULT NULL COMMENT '服务订单金额',
       `return_num` bigint(20) DEFAULT NULL COMMENT '退单数',
       `return_total` decimal(20,2) DEFAULT NULL COMMENT '退单总数',
       `assist_return_num` bigint(20) DEFAULT NULL COMMENT '帮卖退单数',
       `assist_return_total` decimal(20,2) DEFAULT NULL COMMENT '帮卖退单总额',
       `commission_received` decimal(20,2) DEFAULT NULL COMMENT '已入账佣金',
       `commission_received_pickup` decimal(20,2) DEFAULT NULL COMMENT '已入账自提佣金',
       `commission_received_assist` decimal(20,2) DEFAULT NULL COMMENT '已入账帮卖佣金',
       `commission_pending` decimal(20,2) DEFAULT NULL COMMENT '未入账佣金',
       `commission_pending_pickup` decimal(20,2) DEFAULT NULL COMMENT '未入账自提佣金',
       `commission_pending_assist` decimal(20,2) DEFAULT NULL COMMENT '未入账帮卖佣金',
       `create_date` date DEFAULT NULL COMMENT '创建时间',
       `return_trade_commission` decimal(20,2) DEFAULT NULL COMMENT '退款佣金',
       PRIMARY KEY (`id`),
       KEY `idx_activity_id` (`activity_id`),
       KEY `idx_store_id` (`store_id`),
       KEY `idx_leader_id` (`leader_id`)
) ENGINE=InnoDB  COMMENT='社区团购活动统计信息表';

CREATE TABLE `sbc-marketing`.`community_statistics_customer` (
                `id` varchar(32) NOT NULL COMMENT '主键',
                `activity_id` varchar(32) NOT NULL COMMENT '活动id',
                `leader_id` varchar(32) NOT NULL COMMENT '团长id',
                `customer_id` varchar(32) NOT NULL COMMENT '会员id',
                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                PRIMARY KEY (`id`),
                KEY `idx_activity_id` (`activity_id`),
                KEY `idx_leader_id` (`leader_id`),
                KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB  COMMENT='社区团购活动会员信息表';

CREATE TABLE `sbc-marketing`.`community_stock_order` (
        `id` varchar(32) NOT NULL COMMENT 'id',
        `activity_id` varchar(32) NOT NULL COMMENT '活动id',
        `sku_id` varchar(32) NOT NULL COMMENT '商品id',
        `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
        `spec_name` varchar(255) DEFAULT NULL COMMENT '规格',
        `num` bigint(20) NOT NULL COMMENT '购买数量',
        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
        `goods_img` varchar(255) DEFAULT NULL COMMENT '商品图片',
        PRIMARY KEY (`id`),
        KEY `ix_activity_id` (`activity_id`),
        KEY `ix_sku_id` (`sku_id`)
) ENGINE=InnoDB  COMMENT='社区团购备货单';

CREATE TABLE `sbc-order`.`leader_trade_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `leader_id` varchar(32) DEFAULT NULL COMMENT '团长ID',
  `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长的会员ID',
  `community_activity_id` varchar(32) DEFAULT NULL COMMENT '社区团购活动ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '订单会员ID',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '会员名称',
  `customer_pic` varchar(128) DEFAULT NULL COMMENT '会员头像',
  `trade_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `goods_info_id` varchar(64) DEFAULT NULL COMMENT '商品ID',
  `goods_info_spec` varchar(64) DEFAULT NULL COMMENT '商品规格',
  `goods_info_num` bigint(20) DEFAULT NULL COMMENT '商品数量',
  `activity_trade_no` bigint(20) DEFAULT NULL COMMENT '跟团号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除 ',
  `bool_flag` tinyint(4) DEFAULT NULL COMMENT '是否帮卖订单',
  PRIMARY KEY (`id`),
  KEY `community_activity_id` (`community_activity_id`),
  KEY `trade_id` (`trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 COMMENT='团长订单详情';

ALTER TABLE `sbc-account`.`customer_funds`
    ADD COLUMN `is_community_leader` tinyint(1) NULL COMMENT '是否社区团长 0：否，1：是' AFTER `is_distributor`;

ALTER TABLE `sbc-account`.`settlement`
    ADD COLUMN community_commission_price decimal(10, 2) NULL COMMENT '社区团购佣金';



CREATE TABLE `s2b_statistics`.`community_customer_day` (
          `create_date` date NOT NULL COMMENT '创建时间',
          `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
          `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
          `customer_name` varchar(64) DEFAULT NULL COMMENT '会员名称',
          `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
          `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
          `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
          `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
          PRIMARY KEY (`create_date`,`store_id`,`customer_id`)
) ENGINE=InnoDB COMMENT='社区团购会员天报表';

CREATE TABLE `s2b_statistics`.`community_customer_month` (
            `create_date` date NOT NULL COMMENT '创建时间',
            `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
            `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
            `customer_name` varchar(64) DEFAULT NULL COMMENT '会员名称',
            `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
            `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
            `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
            `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
            PRIMARY KEY (`create_date`,`store_id`,`customer_id`)
) ENGINE=InnoDB COMMENT='社区团购会员自然月报表';

CREATE TABLE `s2b_statistics`.`community_customer_seven` (
            `create_date` date NOT NULL COMMENT '创建时间',
            `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
            `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
            `customer_name` varchar(64) DEFAULT NULL COMMENT '会员名称',
            `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
            `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
            `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
            `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
            PRIMARY KEY (`create_date`,`store_id`,`customer_id`)
) ENGINE=InnoDB COMMENT='社区团购会员7天报表';

CREATE TABLE `s2b_statistics`.`community_customer_thirty` (
             `create_date` date NOT NULL COMMENT '创建时间',
             `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
             `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
             `customer_name` varchar(64) DEFAULT NULL COMMENT '会员名称',
             `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
             `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
             `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
             `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
             PRIMARY KEY (`create_date`,`store_id`,`customer_id`)
) ENGINE=InnoDB COMMENT='社区团购会员30天报表';

CREATE TABLE `s2b_statistics`.`community_goods_day` (
       `create_date` date NOT NULL COMMENT '创建时间',
       `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
       `sku_id` varchar(32) NOT NULL COMMENT '单品ID',
       `goods_info_name` varchar(64) DEFAULT NULL COMMENT '单品名称',
       `goods_info_no` varchar(32) DEFAULT NULL COMMENT '单品编码',
       `goods_info_img` varchar(128) DEFAULT NULL COMMENT '单品图片',
       `group_num` bigint(20) DEFAULT NULL COMMENT '开团次数',
       `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
       `pay_sku_num` bigint(20) DEFAULT NULL COMMENT '支付件数',
       `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
       `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
       `assist_sku_num` bigint(20) DEFAULT NULL COMMENT '帮卖件数',
       `assist_pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
       `avg_group_sale` decimal(10,2) DEFAULT NULL COMMENT '团均销量',
       `assist_ratio` decimal(10,2) DEFAULT NULL COMMENT '帮卖占比',
       PRIMARY KEY (`create_date`,`store_id`,`sku_id`)
) ENGINE=InnoDB COMMENT='社区团购商品天报表';

CREATE TABLE `s2b_statistics`.`community_goods_month` (
         `create_date` date NOT NULL COMMENT '创建时间',
         `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
         `sku_id` varchar(32) NOT NULL COMMENT '单品ID',
         `goods_info_name` varchar(64) DEFAULT NULL COMMENT '单品名称',
         `goods_info_no` varchar(32) DEFAULT NULL COMMENT '单品编码',
         `goods_info_img` varchar(128) DEFAULT NULL COMMENT '单品图片',
         `group_num` bigint(20) DEFAULT NULL COMMENT '开团次数',
         `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
         `pay_sku_num` bigint(20) DEFAULT NULL COMMENT '支付件数',
         `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
         `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
         `assist_sku_num` bigint(20) DEFAULT NULL COMMENT '帮卖件数',
         `assist_pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
         `avg_group_sale` decimal(10,2) DEFAULT NULL COMMENT '团均销量',
         `assist_ratio` decimal(10,2) DEFAULT NULL COMMENT '帮卖占比',
         PRIMARY KEY (`create_date`,`store_id`,`sku_id`)
) ENGINE=InnoDB COMMENT='社区团购商品自然月报表';

CREATE TABLE `s2b_statistics`.`community_goods_seven` (
         `create_date` date NOT NULL COMMENT '创建时间',
         `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
         `sku_id` varchar(32) NOT NULL COMMENT '单品ID',
         `goods_info_name` varchar(64) DEFAULT NULL COMMENT '单品名称',
         `goods_info_no` varchar(32) DEFAULT NULL COMMENT '单品编码',
         `goods_info_img` varchar(128) DEFAULT NULL COMMENT '单品图片',
         `group_num` bigint(20) DEFAULT NULL COMMENT '开团次数',
         `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
         `pay_sku_num` bigint(20) DEFAULT NULL COMMENT '支付件数',
         `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
         `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
         `assist_sku_num` bigint(20) DEFAULT NULL COMMENT '帮卖件数',
         `assist_pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
         `avg_group_sale` decimal(10,2) DEFAULT NULL COMMENT '团均销量',
         `assist_ratio` decimal(10,2) DEFAULT NULL COMMENT '帮卖占比',
         PRIMARY KEY (`create_date`,`store_id`,`sku_id`)
) ENGINE=InnoDB COMMENT='社区团购商品7天报表';

CREATE TABLE `s2b_statistics`.`community_goods_thirty` (
          `create_date` date NOT NULL COMMENT '创建时间',
          `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
          `sku_id` varchar(32) NOT NULL COMMENT '单品ID',
          `goods_info_name` varchar(64) DEFAULT NULL COMMENT '单品名称',
          `goods_info_no` varchar(32) DEFAULT NULL COMMENT '单品编码',
          `goods_info_img` varchar(128) DEFAULT NULL COMMENT '单品图片',
          `group_num` bigint(20) DEFAULT NULL COMMENT '开团次数',
          `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
          `pay_sku_num` bigint(20) DEFAULT NULL COMMENT '支付件数',
          `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
          `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
          `assist_sku_num` bigint(20) DEFAULT NULL COMMENT '帮卖件数',
          `assist_pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
          `avg_group_sale` decimal(10,2) DEFAULT NULL COMMENT '团均销量',
          `assist_ratio` decimal(10,2) DEFAULT NULL COMMENT '帮卖占比',
          PRIMARY KEY (`create_date`,`store_id`,`sku_id`)
) ENGINE=InnoDB COMMENT='社区团购商品30天报表';

CREATE TABLE `s2b_statistics`.`community_leader_day` (
        `create_date` date NOT NULL COMMENT '创建时间',
        `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
        `leader_id` varchar(32) NOT NULL COMMENT '团长ID',
        `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
        `leader_account` varchar(32) DEFAULT NULL COMMENT '团长账号',
        `leader_name` varchar(64) DEFAULT NULL COMMENT '团长名称',
        `service_order_num` bigint(20) DEFAULT NULL COMMENT '服务订单数',
        `service_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '服务订单金额',
        `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
        `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖订单金额',
        PRIMARY KEY (`create_date`,`store_id`,`leader_id`)
) ENGINE=InnoDB COMMENT='社区团购团长天报表';

CREATE TABLE `s2b_statistics`.`community_leader_month` (
          `create_date` date NOT NULL COMMENT '创建时间',
          `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
          `leader_id` varchar(32) NOT NULL COMMENT '团长ID',
          `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
          `leader_account` varchar(32) DEFAULT NULL COMMENT '团长账号',
          `leader_name` varchar(64) DEFAULT NULL COMMENT '团长名称',
          `service_order_num` bigint(20) DEFAULT NULL COMMENT '服务订单数',
          `service_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '服务订单金额',
          `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
          `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖订单金额',
          PRIMARY KEY (`create_date`,`store_id`,`leader_id`)
) ENGINE=InnoDB COMMENT='社区团购团长自然月报表';

CREATE TABLE `s2b_statistics`.`community_leader_seven` (
          `create_date` date NOT NULL COMMENT '创建时间',
          `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
          `leader_id` varchar(32) NOT NULL COMMENT '团长ID',
          `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
          `leader_account` varchar(32) DEFAULT NULL COMMENT '团长账号',
          `leader_name` varchar(64) DEFAULT NULL COMMENT '团长名称',
          `service_order_num` bigint(20) DEFAULT NULL COMMENT '服务订单数',
          `service_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '服务订单金额',
          `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
          `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖订单金额',
          PRIMARY KEY (`create_date`,`store_id`,`leader_id`)
) ENGINE=InnoDB COMMENT='社区团购团长7天报表';

CREATE TABLE `s2b_statistics`.`community_leader_thirty` (
           `create_date` date NOT NULL COMMENT '创建时间',
           `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
           `leader_id` varchar(32) NOT NULL COMMENT '团长ID',
           `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
           `leader_account` varchar(32) DEFAULT NULL COMMENT '团长账号',
           `leader_name` varchar(64) DEFAULT NULL COMMENT '团长名称',
           `service_order_num` bigint(20) DEFAULT NULL COMMENT '服务订单数',
           `service_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '服务订单金额',
           `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
           `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖订单金额',
           PRIMARY KEY (`create_date`,`store_id`,`leader_id`)
) ENGINE=InnoDB COMMENT='社区团购团长30天报表';

CREATE TABLE `s2b_statistics`.`community_overview_day` (
          `create_date` date NOT NULL COMMENT '创建时间',
          `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
          `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
          `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
          `return_num` bigint(20) DEFAULT NULL COMMENT '退单数',
          `return_total_price` decimal(20,2) DEFAULT NULL COMMENT '退单金额',
          `customer_num` bigint(20) DEFAULT NULL COMMENT '参团人数',
          `leader_num` bigint(20) DEFAULT NULL COMMENT '团长人数',
          `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
          `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
          `assist_return_num` bigint(20) DEFAULT NULL COMMENT '帮卖退单数',
          `assist_return_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖退单金额',
          `assist_order_ratio` decimal(5,2) DEFAULT NULL COMMENT '帮卖订单占比',
          `leader_customer_num` bigint(20) DEFAULT NULL COMMENT '团长发展会员数',
          `commission_received` decimal(20,2) DEFAULT NULL COMMENT '已入账佣金',
          `commission_received_pickup` decimal(20,2) DEFAULT NULL COMMENT '已入账自提佣金',
          `commission_received_assist` decimal(20,2) DEFAULT NULL COMMENT '已入账帮卖佣金',
          `commission_pending` decimal(20,2) DEFAULT NULL COMMENT '未入账佣金',
          `commission_pending_pickup` decimal(20,2) DEFAULT NULL COMMENT '未入账自提佣金',
          `commission_pending_assist` decimal(20,2) DEFAULT NULL COMMENT '未入账帮卖佣金',
          PRIMARY KEY (`create_date`,`store_id`)
) ENGINE=InnoDB COMMENT='社区团购天报表';

CREATE TABLE `s2b_statistics`.`community_overview_month` (
            `create_date` date NOT NULL COMMENT '创建时间',
            `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
            `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
            `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
            `return_num` bigint(20) DEFAULT NULL COMMENT '退单数',
            `return_total_price` decimal(20,2) DEFAULT NULL COMMENT '退单金额',
            `customer_num` bigint(20) DEFAULT NULL COMMENT '参团人数',
            `leader_num` bigint(20) DEFAULT NULL COMMENT '团长人数',
            `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
            `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
            `assist_return_num` bigint(20) DEFAULT NULL COMMENT '帮卖退单数',
            `assist_return_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖退单金额',
            `assist_order_ratio` decimal(5,2) DEFAULT NULL COMMENT '帮卖订单占比',
            `leader_customer_num` bigint(20) DEFAULT NULL COMMENT '团长发展会员数',
            `commission_received` decimal(20,2) DEFAULT NULL COMMENT '已入账佣金',
            `commission_received_pickup` decimal(20,2) DEFAULT NULL COMMENT '已入账自提佣金',
            `commission_received_assist` decimal(20,2) DEFAULT NULL COMMENT '已入账帮卖佣金',
            `commission_pending` decimal(20,2) DEFAULT NULL COMMENT '未入账佣金',
            `commission_pending_pickup` decimal(20,2) DEFAULT NULL COMMENT '未入账自提佣金',
            `commission_pending_assist` decimal(20,2) DEFAULT NULL COMMENT '未入账帮卖佣金',
            PRIMARY KEY (`create_date`,`store_id`)
) ENGINE=InnoDB COMMENT='社区团购自然月报表';

CREATE TABLE `s2b_statistics`.`community_overview_seven` (
            `create_date` date NOT NULL COMMENT '创建时间',
            `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
            `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
            `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
            `return_num` bigint(20) DEFAULT NULL COMMENT '退单数',
            `return_total_price` decimal(20,2) DEFAULT NULL COMMENT '退单金额',
            `customer_num` bigint(20) DEFAULT NULL COMMENT '参团人数',
            `leader_num` bigint(20) DEFAULT NULL COMMENT '团长人数',
            `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
            `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
            `assist_return_num` bigint(20) DEFAULT NULL COMMENT '帮卖退单数',
            `assist_return_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖退单金额',
            `assist_order_ratio` decimal(5,2) DEFAULT NULL COMMENT '帮卖订单占比',
            `leader_customer_num` bigint(20) DEFAULT NULL COMMENT '团长发展会员数',
            `commission_received` decimal(20,2) DEFAULT NULL COMMENT '已入账佣金',
            `commission_received_pickup` decimal(20,2) DEFAULT NULL COMMENT '已入账自提佣金',
            `commission_received_assist` decimal(20,2) DEFAULT NULL COMMENT '已入账帮卖佣金',
            `commission_pending` decimal(20,2) DEFAULT NULL COMMENT '未入账佣金',
            `commission_pending_pickup` decimal(20,2) DEFAULT NULL COMMENT '未入账自提佣金',
            `commission_pending_assist` decimal(20,2) DEFAULT NULL COMMENT '未入账帮卖佣金',
            PRIMARY KEY (`create_date`,`store_id`)
) ENGINE=InnoDB COMMENT='社区团购7天报表';

CREATE TABLE `s2b_statistics`.`community_overview_thirty` (
             `create_date` date NOT NULL COMMENT '创建时间',
             `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
             `pay_order_num` bigint(20) DEFAULT NULL COMMENT '支付订单数',
             `pay_total_price` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
             `return_num` bigint(20) DEFAULT NULL COMMENT '退单数',
             `return_total_price` decimal(20,2) DEFAULT NULL COMMENT '退单金额',
             `customer_num` bigint(20) DEFAULT NULL COMMENT '参团人数',
             `leader_num` bigint(20) DEFAULT NULL COMMENT '团长人数',
             `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
             `assist_order_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖金额',
             `assist_return_num` bigint(20) DEFAULT NULL COMMENT '帮卖退单数',
             `assist_return_total_price` decimal(20,2) DEFAULT NULL COMMENT '帮卖退单金额',
             `assist_order_ratio` decimal(5,2) DEFAULT NULL COMMENT '帮卖订单占比',
             `leader_customer_num` bigint(20) DEFAULT NULL COMMENT '团长发展会员数',
             `commission_received` decimal(20,2) DEFAULT NULL COMMENT '已入账佣金',
             `commission_received_pickup` decimal(20,2) DEFAULT NULL COMMENT '已入账自提佣金',
             `commission_received_assist` decimal(20,2) DEFAULT NULL COMMENT '已入账帮卖佣金',
             `commission_pending` decimal(20,2) DEFAULT NULL COMMENT '未入账佣金',
             `commission_pending_pickup` decimal(20,2) DEFAULT NULL COMMENT '未入账自提佣金',
             `commission_pending_assist` decimal(20,2) DEFAULT NULL COMMENT '未入账帮卖佣金',
             PRIMARY KEY (`create_date`,`store_id`)
) ENGINE=InnoDB COMMENT='社区团购30天报表';

CREATE TABLE `s2b_statistics`.`replay_community_activity` (
             `activity_id` varchar(32) NOT NULL COMMENT '主键',
             `store_id` bigint(20) NOT NULL COMMENT '店铺id',
             `activity_name` varchar(30) NOT NULL COMMENT '活动名称',
             `description` varchar(300) DEFAULT NULL COMMENT '活动描述',
             `start_time` datetime NOT NULL COMMENT '开始时间',
             `end_time` datetime NOT NULL COMMENT '结束时间',
             `logistics_type` varchar(10) DEFAULT NULL COMMENT '物流方式 0:自提 1:快递 以逗号拼凑',
             `sales_type` varchar(10) DEFAULT NULL COMMENT '销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑',
             `sales_range` tinyint(2) NOT NULL COMMENT '自主销售范围 0：全部 1：地区 2：自定义',
             `leader_range` tinyint(2) NOT NULL COMMENT '帮卖团长范围 0：全部 1：地区 2：自定义',
             `commission_flag` tinyint(2) NOT NULL COMMENT '佣金设置 0：商品 1：按团长/自提点',
             `pickup_commission` decimal(10,2) DEFAULT NULL COMMENT '批量-自提服务佣金',
             `assist_commission` decimal(10,2) DEFAULT NULL COMMENT '批量-帮卖团长佣金',
             `details` mediumtext COMMENT '团详情',
             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
             `update_time` datetime DEFAULT NULL COMMENT '结束时间',
             `generate_flag` tinyint(2) DEFAULT NULL COMMENT '是否生成 0:未生成 1:已生成',
             `generate_time` datetime(3) DEFAULT NULL COMMENT '生成时间',
             `images` text COMMENT '图片',
             `video_url` varchar(200) DEFAULT NULL COMMENT '视频',
             `real_end_time` datetime DEFAULT NULL COMMENT '延时结束时间',
             PRIMARY KEY (`activity_id`),
             KEY `activity_name` (`activity_name`),
             KEY `store_id` (`store_id`)
) ENGINE=InnoDB COMMENT='社区团购活动表';

CREATE TABLE `s2b_statistics`.`replay_community_sku_rel` (
            `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
            `activity_id` varchar(32) NOT NULL COMMENT '活动id',
            `spu_id` varchar(32) NOT NULL COMMENT '商品spuId',
            `sku_id` varchar(32) NOT NULL COMMENT '商品skuId',
            `price` decimal(20,2) DEFAULT NULL COMMENT '活动价',
            `pickup_commission` decimal(10,2) DEFAULT NULL COMMENT '自提服务佣金',
            `assist_commission` decimal(10,2) DEFAULT NULL COMMENT '帮卖佣金',
            `activity_stock` bigint(11) DEFAULT NULL COMMENT '活动库存',
            `sales` bigint(11) DEFAULT NULL COMMENT '已买库存',
            PRIMARY KEY (`id`),
            KEY `idx_activity_id` (`activity_id`),
            KEY `idx_sku_id` (`sku_id`),
            KEY `idx_spu_id` (`spu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 COMMENT='社区团购活动商品表';

CREATE TABLE `s2b_statistics`.`replay_community_statistics` (
               `id` varchar(32) NOT NULL COMMENT 'id',
               `activity_id` varchar(32) NOT NULL COMMENT '活动id',
               `store_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
               `leader_id` varchar(32) NOT NULL COMMENT '团长id',
               `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
               `pay_num` bigint(20) DEFAULT NULL COMMENT '支付订单个数',
               `pay_total` decimal(20,2) DEFAULT NULL COMMENT '支付总额',
               `assist_num` bigint(20) DEFAULT NULL COMMENT '帮卖团长数',
               `assist_pay_num` bigint(20) DEFAULT NULL COMMENT '成团团长数',
               `assist_order_num` bigint(20) DEFAULT NULL COMMENT '帮卖订单数',
               `assist_order_total` decimal(20,2) DEFAULT NULL COMMENT '帮卖总额',
               `assist_order_ratio` decimal(20,2) DEFAULT NULL COMMENT '帮卖占比',
               `pickup_service_order_num` bigint(20) DEFAULT NULL COMMENT '自提服务订单数',
               `pickup_service_order_total` decimal(20,2) DEFAULT NULL COMMENT '服务订单金额',
               `return_num` bigint(20) DEFAULT NULL COMMENT '退单数',
               `return_total` bigint(20) DEFAULT NULL COMMENT '退单总数',
               `assist_return_num` bigint(20) DEFAULT NULL COMMENT '帮卖退单数',
               `assist_return_total` decimal(20,2) DEFAULT NULL COMMENT '帮卖退单总额',
               `commission_received` decimal(20,2) DEFAULT NULL COMMENT '已入账佣金',
               `commission_received_pickup` decimal(20,2) DEFAULT NULL COMMENT '已入账自提佣金',
               `commission_received_assist` decimal(20,2) DEFAULT NULL COMMENT '已入账帮卖佣金',
               `commission_pending` decimal(20,2) DEFAULT NULL COMMENT '未入账佣金',
               `commission_pending_pickup` decimal(20,2) DEFAULT NULL COMMENT '未入账自提佣金',
               `commission_pending_assist` decimal(20,2) DEFAULT NULL COMMENT '未入账帮卖佣金',
               `create_date` date DEFAULT NULL COMMENT '创建时间',
               `return_trade_commission` decimal(20,2) DEFAULT NULL COMMENT '退款佣金',
               PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='社区团购活动统计信息表';

CREATE TABLE `s2b_statistics`.`replay_community_statistics_customer` (
                        `id` varchar(32) NOT NULL COMMENT '主键',
                        `activity_id` varchar(32) NOT NULL COMMENT '活动id',
                        `leader_id` varchar(32) NOT NULL COMMENT '团长id',
                        `customer_id` varchar(255) NOT NULL COMMENT '会员id',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='社区团购活动会员信息表';

CREATE TABLE `s2b_statistics`.`replay_leader_trade_detail` (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `leader_id` varchar(32) DEFAULT NULL COMMENT '团长ID',
              `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长的会员ID',
              `community_activity_id` varchar(32) DEFAULT NULL COMMENT '社区团购活动ID',
              `customer_id` varchar(32) DEFAULT NULL COMMENT '订单会员ID',
              `customer_name` varchar(128) DEFAULT NULL COMMENT '会员名称',
              `customer_pic` varchar(128) DEFAULT NULL COMMENT '会员头像',
              `trade_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
              `goods_info_id` varchar(64) DEFAULT NULL COMMENT '商品ID',
              `goods_info_spec` varchar(64) DEFAULT NULL COMMENT '商品规格',
              `goods_info_num` bigint(20) DEFAULT NULL COMMENT '商品数量',
              `activity_trade_no` bigint(20) DEFAULT NULL COMMENT '跟团号',
              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
              `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
              `update_person` varchar(32) DEFAULT NULL COMMENT '更新人',
              `del_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除 ',
              `bool_flag` tinyint(4) DEFAULT NULL COMMENT '是否帮卖订单',
              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 COMMENT='团长订单详情';

CREATE TABLE `s2b_statistics`.`replay_trade_community_customer` (
                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                   `tid` varchar(32) DEFAULT NULL COMMENT '订单ID',
                   `customer_id` varchar(32) DEFAULT NULL COMMENT '会员ID',
                   `activity_id` varchar(32) DEFAULT NULL COMMENT '社区团购活动ID',
                   `leader_id` varchar(32) DEFAULT NULL COMMENT '团长ID',
                   `sales_type` tinyint(4) DEFAULT NULL COMMENT '成团渠道 0：自主  1：帮卖',
                   `total_price` decimal(20,2) DEFAULT NULL COMMENT '订单总金额',
                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                   `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 COMMENT='社区团购会员统计订单信息';

CREATE TABLE `s2b_statistics`.`replay_trade_community_sku` (
              `id` bigint(20) NOT NULL AUTO_INCREMENT,
              `tid` varchar(32) DEFAULT NULL COMMENT '订单ID',
              `activity_id` varchar(32) DEFAULT NULL COMMENT '社区团购ID',
              `leader_id` varchar(32) DEFAULT NULL COMMENT '团长ID',
              `leader_customer_id` varchar(32) DEFAULT NULL COMMENT '团长会员ID',
              `leader_phone` varchar(32) DEFAULT NULL COMMENT '团长账号',
              `sales_type` tinyint(4) DEFAULT NULL COMMENT '成团渠道 0：自主  1：帮卖',
              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
              `sku_id` varchar(32) DEFAULT NULL COMMENT '单品ID',
              `split_price` decimal(20,2) DEFAULT NULL COMMENT '实际成交价格',
              `num` bigint(20) DEFAULT NULL COMMENT '商品数量',
              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 COMMENT='社区团购订单商品信息';


INSERT INTO `xxl-job`.xxl_job_info (job_group, job_desc, add_time, update_time, author, alarm_email, schedule_type, schedule_conf, misfire_strategy, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid, trigger_status, trigger_last_time, trigger_next_time) VALUES (1, '社区团购团长佣金结算', '2023-07-31 10:38:39', '2023-08-14 11:17:45', '高波', '', 'CRON', '0 8 0 * * ?', 'DO_NOTHING', 'FIRST', 'CommunityCommissionJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2023-07-31 10:38:39', '', 1, 0, 1691986080000);
INSERT INTO `xxl-job`.xxl_job_info (job_group, job_desc, add_time, update_time, author, alarm_email, schedule_type, schedule_conf, misfire_strategy, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid, trigger_status, trigger_last_time, trigger_next_time) VALUES (2, '社区团购统计', '2023-08-11 16:51:55', '2023-08-14 10:16:34', '高波', '', 'CRON', '0 3 3 * * ?', 'DO_NOTHING', 'FIRST', 'communityJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2023-08-11 16:51:55', '', 1, 0, 1692039780000);

-- 社区团购开关
INSERT INTO `sbc-setting`.`system_config` (`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES (128, 'community_config', 'community_config', '社区团购开关', '是否开启社区团购', 1, NULL, '2023-08-03 10:49:49', '2023-08-03 10:49:51', 0);

-- 社区拼团生成备货和发货单任务
INSERT INTO `xxl-job`.xxl_job_info (job_group, job_desc, add_time, update_time, author, alarm_email, schedule_type, schedule_conf, misfire_strategy, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid, trigger_status, trigger_last_time, trigger_next_time)
VALUES (1, '社区拼团生成备货和发货单，ids是指定活动id', '2023-08-04 17:38:03', '2023-08-04 17:38:03', '戴倚天', '', 'CRON', '0 0/5 * * * ?', 'DO_NOTHING', 'FIRST', 'communityEndHandleJobHandler', '{"pageSize":10,"ids":""}', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2023-08-04 17:38:03', '', 0, 0, 0);

create table `s2b_statistics`.community_overview
(
    create_date                date           null comment '创建时间',
    store_id                   bigint         not null comment '店铺ID',
    pay_order_num              bigint         null comment '支付订单数',
    pay_total_price            decimal(20, 2) null comment '支付总额',
    return_num                 bigint         null comment '退单数',
    return_total_price         decimal(20, 2) null comment '退单金额',
    customer_num               bigint         null comment '参团人数',
    leader_num                 bigint         null comment '团长人数',
    assist_order_num           bigint         null comment '帮卖订单数',
    assist_order_total_price   decimal(20, 2) null comment '帮卖金额',
    assist_return_num          bigint         null comment '帮卖退单数',
    assist_return_total_price  decimal(20, 2) null comment '帮卖退单金额',
    assist_order_ratio         decimal(5, 2)  null comment '帮卖订单占比',
    leader_customer_num        bigint         null comment '团长发展会员数',
    commission_received        decimal(20, 2) null comment '已入账佣金',
    commission_received_pickup decimal(20, 2) null comment '已入账自提佣金',
    commission_received_assist decimal(20, 2) null comment '已入账帮卖佣金',
    commission_pending         decimal(20, 2) null comment '未入账佣金',
    commission_pending_pickup  decimal(20, 2) null comment '未入账自提佣金',
    commission_pending_assist  decimal(20, 2) null comment '未入账帮卖佣金',
    primary key (store_id)
)
    comment '社区团购概况报表';

alter table `sbc-marketing`.community_statistics
    add return_trade_commission_assist decimal(20, 2) null comment '退款佣金帮卖';

alter table `sbc-marketing`.community_statistics
    add return_trade_commission_pickup decimal(20, 2) null comment '退款佣金自提';

INSERT INTO `xxl-job`.xxl_job_info (job_group, job_desc, add_time, update_time, author, alarm_email, schedule_type, schedule_conf, misfire_strategy, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid, trigger_status, trigger_last_time, trigger_next_time) VALUES (2, '社区团购概览统计', '2023-08-23 18:11:25', '2023-08-24 10:08:41', '高波', '', 'CRON', '0 0/5 * * * ?', 'DO_NOTHING', 'FIRST', 'communityOverviewJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2023-08-23 18:11:25', '', 1, 1692949800000, 1692950100000);

update `sbc-setting`.base_config set version = 'SBC V5.8.0';

alter table `s2b_statistics`.community_overview_day
    add return_trade_commission decimal(20, 2) null comment '退款佣金';

alter table `s2b_statistics`.community_overview_day
    add return_trade_commission_assist decimal(20, 2) null comment '退款佣金帮卖';

alter table `s2b_statistics`.community_overview_day
    add return_trade_commission_pickup decimal(20, 2) null comment '退款佣金自提';

alter table `s2b_statistics`.community_overview
    add COLUMN return_trade_commission decimal(20, 2) null comment '退款佣金';

alter table `s2b_statistics`.community_overview
    add COLUMN return_trade_commission_assist decimal(20, 2) null comment '退款佣金帮卖';

alter table `s2b_statistics`.community_overview
    add COLUMN return_trade_commission_pickup decimal(20, 2) null comment '退款佣金自提';

