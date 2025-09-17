ALTER TABLE `sbc-crm`.`custom_group`
MODIFY COLUMN `group_detail` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分群信息' AFTER `definition`;

CREATE TABLE `sbc-crm`.`customer_preference_tag_detail`
(
    `tag_id` bigint(20) DEFAULT NULL COMMENT '标签id',
    `tag_name` varchar(255) DEFAULT NULL COMMENT '标签名称',
    `tag_type` int(5) DEFAULT NULL COMMENT '标签类型',
    `type` varchar(50) DEFAULT NULL,
    `customer_id` varchar(50) NOT NULL COMMENT '会员id',
    `dimension_type` varchar(50) DEFAULT NULL COMMENT '维度类型',
    `dimension_id` varchar(50) DEFAULT NULL COMMENT '维度id',
    `num` int(11) DEFAULT NULL COMMENT '统计数量',
    `p_date` varchar(50) DEFAULT NULL,
    `last_date` date DEFAULT NULL COMMENT '最后更新数据的日期，使用该日期去查询',
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员偏好标签明细表';

CREATE TABLE `s2b_statistics`.`replay_marketing_sku_pv`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `marketing_id`   varchar(32) DEFAULT NULL COMMENT '活动id',
    `marketing_type` tinyint(4)  DEFAULT NULL COMMENT '活动类型：0,"满减优惠"；1,"满折优惠"；2,"满赠优惠"；3,"一口价优惠"；4,"第二件半价优惠活动"；5,"秒杀"；6,"组合套餐"；101,"拼团"；102,"预约"；103,"全款预售"；104,"分销"；105,"定金预售"；',
    `sku_id`         varchar(32) DEFAULT NULL COMMENT 'skuId',
    `pv`             bigint(20)  DEFAULT NULL COMMENT 'pv数量',
    `stat_date`      date        DEFAULT NULL COMMENT '统计时间',
    `send_time`      datetime    DEFAULT NULL COMMENT '发送时间',
    `receive_time`   datetime    DEFAULT NULL COMMENT '接受时间',
    PRIMARY KEY (`id`),
    KEY `indx_stat_date` (`stat_date`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 71
  DEFAULT CHARSET = utf8mb4 COMMENT ='营销活动商品的pv统计';

CREATE TABLE `s2b_statistics`.`replay_marketing_sku_uv`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `marketing_id`   varchar(32) DEFAULT NULL COMMENT '活动id',
    `marketing_type` tinyint(4)  DEFAULT NULL COMMENT '活动类型：0,"满减优惠"；1,"满折优惠"；2,"满赠优惠"；3,"一口价优惠"；4,"第二件半价优惠活动"；5,"秒杀"；6,"组合套餐"；101,"拼团"；102,"预约"；103,"全款预售"；104,"分销"；105,"全款预售"；',
    `sku_id`         varchar(32) DEFAULT NULL COMMENT 'skuId',
    `customer_id`    varchar(40) DEFAULT NULL COMMENT '会员id',
    `stat_date`      date        DEFAULT NULL COMMENT '统计时间',
    `send_time`      datetime    DEFAULT NULL COMMENT '发送时间',
    `receive_time`   datetime    DEFAULT NULL COMMENT '接受时间',
    PRIMARY KEY (`id`),
    KEY `indx_stat_date` (`stat_date`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 43
  DEFAULT CHARSET = utf8mb4 COMMENT ='营销活动商品的uv统计';

ALTER TABLE `s2b_statistics`.`replay_sku_flow`
ADD COLUMN `miniprogram_pv` bigint(20) NULL COMMENT '小程序端pv' AFTER `app_pv`,
ADD COLUMN `miniprogram_uv` bigint(20) NULL COMMENT '小程序端uv' AFTER `app_uv`;


CREATE TABLE `sbc-crm`.`life_cycle_group_init`
(
    `id`              bigint(11) NOT NULL AUTO_INCREMENT,
    `group_name`      varchar(255)  DEFAULT NULL COMMENT '分群名称',
    `definition`      varchar(4000) DEFAULT NULL COMMENT '人群定义',
    `group_detail`    text COMMENT '分群信息',
    `customer_tags`   varchar(2000) DEFAULT NULL COMMENT '会员标签',
    `preference_tags` text COMMENT '偏好标签，拼接方式为偏好标签组id-标签id，多选以逗号分隔',
    `auto_tags`       varchar(2000) DEFAULT NULL COMMENT '指标值范围标签和综合标签',
    `sort_num`        int(11)       DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='系统生命周期分群';

INSERT INTO `sbc-crm`.`life_cycle_group_init` (`id`, `group_name`, `definition`, `group_detail`, `customer_tags`, `preference_tags`, `auto_tags`) VALUES (1, '引入期会员', '最近60天无付款；入会时间1-60天；', '{\"groupName\":\"引入期会员\",\"gtAdmissionTime\":1,\"ltAdmissionTime\":60,\"noRecentPayTradeTime\":60}', NULL, NULL, NULL);
INSERT INTO `sbc-crm`.`life_cycle_group_init` (`id`, `group_name`, `definition`, `group_detail`, `customer_tags`, `preference_tags`, `auto_tags`) VALUES (2, '成长期会员', '最近60天有付款；', '{\"groupName\":\"成长期会员\",\"recentPayTradeTime\":60}', NULL, NULL, NULL);
INSERT INTO `sbc-crm`.`life_cycle_group_init` (`id`, `group_name`, `definition`, `group_detail`, `customer_tags`, `preference_tags`, `auto_tags`) VALUES (3, '成熟期会员', '近365天累计消费≥10次；最近60天有付款；', '{\"groupName\":\"成熟期会员\",\"gtTradeCount\":10,\"recentPayTradeTime\":60,\"tradeCountTime\":365}', NULL, NULL, NULL);
INSERT INTO `sbc-crm`.`life_cycle_group_init` (`id`, `group_name`, `definition`, `group_detail`, `customer_tags`, `preference_tags`, `auto_tags`) VALUES (4, '衰退期会员', '最近180天有付款；最近60天无付款；', '{\"groupName\":\"衰退期会员\",\"noRecentPayTradeTime\":60,\"recentPayTradeTime\":180}', NULL, NULL, NULL);
INSERT INTO `sbc-crm`.`life_cycle_group_init` (`id`, `group_name`, `definition`, `group_detail`, `customer_tags`, `preference_tags`, `auto_tags`) VALUES (5, '流失期会员', '最近180天无付款；', '{\"groupName\":\"流失期会员\",\"noRecentPayTradeTime\":180}', NULL, NULL, NULL);

alter table `sbc-crm`.`custom_group` add column `group_type` tinyint(4) DEFAULT '0' COMMENT '分群类型：0-自定义分群，1-生命周期分群';
alter table `sbc-crm`.`custom_group` add column `sort_num` int(11) DEFAULT NULL COMMENT '排序';
ALTER TABLE `sbc-crm`.`custom_group` ADD COLUMN `init_id` int NULL COMMENT '引入的系统初始化标签id';

INSERT INTO `sbc-crm`.`custom_group`(`group_name`, `definition`, `group_detail`, `create_time`, `create_person`, `update_time`, `update_person`, `customer_tags`, `preference_tags`, `auto_tags`, `group_type`, `sort_num`, `init_id`) VALUES ('引入期会员', '最近60天无付款；入会时间1-60天；', '{\"groupName\":\"引入期会员\",\"gtAdmissionTime\":1,\"ltAdmissionTime\":60,\"noRecentPayTradeTime\":60}', '2021-03-03 10:16:19', '2c8080815cd3a74a015cd3ae86850001', NULL, NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `sbc-crm`.`custom_group`(`group_name`, `definition`, `group_detail`, `create_time`, `create_person`, `update_time`, `update_person`, `customer_tags`, `preference_tags`, `auto_tags`, `group_type`, `sort_num`, `init_id`) VALUES ('成长期会员', '最近60天有付款；', '{\"groupName\":\"成长期会员\",\"recentPayTradeTime\":60}', '2021-03-03 10:16:19', '2c8080815cd3a74a015cd3ae86850001', NULL, NULL, NULL, NULL, NULL, 1, 2, 2);
INSERT INTO `sbc-crm`.`custom_group`(`group_name`, `definition`, `group_detail`, `create_time`, `create_person`, `update_time`, `update_person`, `customer_tags`, `preference_tags`, `auto_tags`, `group_type`, `sort_num`, `init_id`) VALUES ('成熟期会员', '近365天累计消费≥10次；最近60天有付款；', '{\"groupName\":\"成熟期会员\",\"gtTradeCount\":10,\"recentPayTradeTime\":60,\"tradeCountTime\":365}', '2021-03-03 10:16:19', '2c8080815cd3a74a015cd3ae86850001', NULL, NULL, NULL, NULL, NULL, 1, 3, 3);
INSERT INTO `sbc-crm`.`custom_group`(`group_name`, `definition`, `group_detail`, `create_time`, `create_person`, `update_time`, `update_person`, `customer_tags`, `preference_tags`, `auto_tags`, `group_type`, `sort_num`, `init_id`) VALUES ('衰退期会员', '最近180天有付款；最近60天无付款；', '{\"groupName\":\"衰退期会员\",\"noRecentPayTradeTime\":60,\"recentPayTradeTime\":180}', '2021-03-03 10:16:19', '2c8080815cd3a74a015cd3ae86850001', NULL, NULL, NULL, NULL, NULL, 1, 4, 4);
INSERT INTO `sbc-crm`.`custom_group`(`group_name`, `definition`, `group_detail`, `create_time`, `create_person`, `update_time`, `update_person`, `customer_tags`, `preference_tags`, `auto_tags`, `group_type`, `sort_num`, `init_id`) VALUES ('流失期会员', '最近180天无付款；', '{\"groupName\":\"流失期会员\",\"noRecentPayTradeTime\":180}', '2021-03-03 10:16:19', '2c8080815cd3a74a015cd3ae86850001', NULL, NULL, NULL, NULL, NULL, 1, 5, 5);

CREATE TABLE `sbc-crm`.`life_cycle_group_rel`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `group_id`    int(11)     DEFAULT NULL COMMENT '人群id',
    `customer_id` varchar(32) DEFAULT NULL COMMENT '会员id',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `stat_date`   date        DEFAULT NULL COMMENT '统计时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `index_group_id` (`group_id`) USING BTREE,
    KEY `index_customer_id` (`customer_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='生命周期人群的会员明细';

CREATE TABLE `sbc-crm`.`life_cycle_group_statistics`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `stat_date`      date           DEFAULT NULL COMMENT '统计时间',
    `group_id`       bigint(11)     DEFAULT NULL COMMENT '自定义人群id',
    `customer_count` bigint(11)     DEFAULT NULL COMMENT '会员数量',
    `create_time`    datetime       DEFAULT NULL COMMENT '创建时间',
    `ratio`          decimal(10, 2) DEFAULT NULL COMMENT '会员占比',
    `outflow_count`  bigint(20)     DEFAULT NULL COMMENT '流失人数',
    `inflow_count`   bigint(20)     DEFAULT NULL COMMENT '回归人数',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `INDEX_STAT_DATE` (`stat_date`) USING BTREE,
    KEY `INDEX_GROUP_ID` (`group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='会员生命周期人群统计';

CREATE TABLE `s2b_statistics`.`replay_goods_evaluate` (
  `evaluate_id` varchar(32) NOT NULL COMMENT '评价id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺Id',
  `store_name` varchar(150) DEFAULT NULL COMMENT '店铺名称',
  `goods_id` varchar(32) NOT NULL COMMENT '商品id(spuId)',
  `goods_info_id` varchar(32) NOT NULL COMMENT '货品id(skuId)',
  `goods_info_name` varchar(255) NOT NULL COMMENT '商品名称',
  `order_no` varchar(255) NOT NULL COMMENT '订单号',
  `buy_time` datetime DEFAULT NULL COMMENT '购买时间',
  `goods_img` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `spec_details` varchar(255) DEFAULT NULL COMMENT '规格描述信息',
  `customer_id` varchar(32) NOT NULL COMMENT '会员Id',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '会员名称',
  `customer_account` varchar(20) NOT NULL COMMENT '会员登录账号|手机号',
  `evaluate_score` tinyint(4) NOT NULL DEFAULT '5' COMMENT '商品评分',
  `evaluate_content` varchar(500) DEFAULT NULL COMMENT '商品评价内容',
  `evaluate_time` datetime DEFAULT NULL COMMENT '发表评价时间',
  `evaluate_answer` varchar(500) DEFAULT NULL COMMENT '评论回复',
  `evaluate_answer_time` datetime DEFAULT NULL COMMENT '回复时间',
  `evaluate_answer_account_name` varchar(45) DEFAULT NULL COMMENT '回复人账号',
  `evaluate_answer_employee_id` varchar(32) DEFAULT NULL COMMENT '回复员工Id',
  `history_evaluate_score` tinyint(4) DEFAULT NULL COMMENT '历史商品评分',
  `history_evaluate_content` varchar(500) DEFAULT NULL COMMENT '历史商品评价内容',
  `history_evaluate_time` datetime DEFAULT NULL COMMENT '历史发表评价时间',
  `history_evaluate_answer` varchar(500) DEFAULT NULL COMMENT '历史评论回复',
  `history_evaluate_answer_time` datetime DEFAULT NULL COMMENT '历史回复时间',
  `history_evaluate_answer_account_name` varchar(45) DEFAULT NULL COMMENT '历史回复人账号',
  `history_evaluate_answer_employee_id` varchar(32) DEFAULT NULL COMMENT '历史回复员工Id',
  `good_num` int(11) DEFAULT '0' COMMENT '点赞数',
  `is_anonymous` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否匿名 0：否，1：是',
  `is_answer` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已回复 0:否,1:是',
  `is_edit` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已经修改 0：否，1：是',
  `is_show` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否展示 0：否，1：是',
  `is_upload` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否晒单 0：否，1：是',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `is_sys` tinyint(255) DEFAULT '0' COMMENT '是否系统评价 0：否，1：是',
  `cate_top_id` bigint(20) DEFAULT NULL COMMENT '商品一级分类',
  `cate_id` bigint(20) DEFAULT NULL COMMENT '商品类目',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '商品品牌',
  `terminal_source` tinyint(4) DEFAULT NULL COMMENT '终端来源',
  PRIMARY KEY (`evaluate_id`),
  KEY `customer_id_index` (`customer_id`) USING BTREE COMMENT 'customer_id索引',
  KEY `goods_id_index` (`goods_id`,`create_time`) USING BTREE COMMENT 'goods_id索引',
  KEY `idx_order_no` (`order_no`),
  KEY `idx_evaluate_time` (`evaluate_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评价表';

CREATE TABLE `s2b_statistics`.`replay_distribution_record` (
  `record_id` varchar(32) NOT NULL COMMENT '分销记录表主键',
  `goods_info_id` varchar(32) DEFAULT NULL COMMENT '货品Id',
  `trade_id` varchar(32) DEFAULT NULL COMMENT '订单交易号',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺Id',
  `company_id` bigint(20) DEFAULT NULL COMMENT '商家Id',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
  `distributor_id` varchar(32) DEFAULT NULL COMMENT '分销员标识UUID',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `finish_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `mission_received_time` datetime DEFAULT NULL COMMENT '佣金入账时间',
  `order_goods_price` decimal(20,2) DEFAULT NULL COMMENT '订单商品总金额',
  `order_goods_count` bigint(20) DEFAULT NULL COMMENT '订单商品的数量',
  `commission_goods` decimal(20,2) DEFAULT NULL COMMENT '货品的总佣金',
  `commission_rate` decimal(4,2) DEFAULT NULL COMMENT '分销佣金比例',
  `commission_state` tinyint(2) DEFAULT '0' COMMENT '佣金是否入账 0:未入账  1：已入账',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '是否删除的标志 0：未删除   1：已删除',
  `distributor_customer_id` varchar(32) DEFAULT NULL COMMENT '分销员的客户id',
  PRIMARY KEY (`record_id`),
  KEY `idx_goods_info_id` (`goods_info_id`),
  KEY `idx_trade_id` (`trade_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_company_id` (`company_id`),
  KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销记录表';

CREATE TABLE `s2b_statistics`.`replay_goods_share_record` (
  `share_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
  `goods_id` varchar(32) NOT NULL COMMENT 'SPU id',
  `goods_info_id` varchar(32) NOT NULL COMMENT 'SKU id',
  `store_id` bigint(11) NOT NULL COMMENT '店铺ID',
  `company_info_id` bigint(11) NOT NULL COMMENT '公司信息ID',
  `terminal_source` tinyint(3) NOT NULL COMMENT '终端：1 H5，2pc，3APP，4小程序',
  `share_channel` tinyint(1) DEFAULT NULL COMMENT '分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`share_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=883 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品的分享记录';

CREATE TABLE `s2b_statistics`.`replay_customer_sign_record_action` (
  `sign_record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户签到记录表id',
  `customer_id` varchar(32) NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '签到日期记录',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除区分：0 未删除，1 已删除',
  `sign_ip` varchar(50) DEFAULT NULL COMMENT '签到ip',
  `terminal_source` varchar(10) DEFAULT NULL COMMENT '终端：1 H5，2pc，3APP，4小程序',
  PRIMARY KEY (`sign_record_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8mb4 COMMENT='用户签到记录表';

CREATE TABLE `s2b_statistics`.`replay_store_share_record` (
  `share_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
  `store_id` bigint(11) DEFAULT NULL COMMENT '店铺ID',
  `company_info_id` bigint(11) DEFAULT NULL COMMENT '公司信息ID',
  `index_type` tinyint(1) NOT NULL COMMENT '0分享首页，1分享店铺首页',
  `terminal_source` tinyint(3) NOT NULL COMMENT '终端：1 H5，2pc，3APP，4小程序',
  `share_channel` tinyint(1) DEFAULT NULL COMMENT '分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`share_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商城分享记录';

CREATE TABLE `s2b_statistics`.`replay_store_customer_follow_action` (
  `follow_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(32) NOT NULL COMMENT '会员Id',
  `store_id` bigint(11) NOT NULL COMMENT '商品Id',
  `company_info_id` bigint(11) NOT NULL COMMENT '商家ID',
  `follow_time` datetime NOT NULL COMMENT '收藏时间',
  `terminal_source` tinyint(4) DEFAULT NULL COMMENT '终端',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=820 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='商品的店铺收藏新增数据表';

CREATE TABLE `s2b_statistics`.`replay_invite_new_record` (
  `record_id` varchar(32) NOT NULL DEFAULT '' COMMENT '邀新记录ID,主键',
  `invited_customer_id` varchar(32) NOT NULL COMMENT '受邀人ID',
  `available_distribution` tinyint(4) DEFAULT '0' COMMENT '是否有效邀新，0：否，1：是',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `first_order_time` datetime DEFAULT NULL COMMENT '首次下单时间',
  `order_code` varchar(45) DEFAULT NULL COMMENT '订单编号',
  `order_complete_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `reward_recorded` tinyint(4) DEFAULT '0' COMMENT '奖励是否入账，0：否，1：是',
  `reward_cash_recorded_time` datetime DEFAULT NULL COMMENT '奖励入账时间',
  `reward_cash` decimal(20,2) DEFAULT '0.00' COMMENT '奖励金额(实际入账的金额)',
  `setting_coupons` varchar(350) DEFAULT '' COMMENT '后台配置的奖励优惠券名称，多个以逗号分隔',
  `setting_coupon_ids_counts` text COMMENT '优惠券组集合信息（id：num）',
  `setting_amount` decimal(20,2) DEFAULT '0.00' COMMENT '后台配置的奖励金额',
  `request_customer_id` varchar(32) DEFAULT NULL COMMENT '邀请人id',
  `distributor` tinyint(4) DEFAULT NULL COMMENT '是否分销员，0：否 1：是',
  `fail_reason_flag` tinyint(4) DEFAULT NULL COMMENT '未入账原因,0:非有效邀新，1：奖励达到上限，2：奖励未开启',
  `reward_flag` tinyint(4) DEFAULT NULL COMMENT '入账类型, 0:现金，1：优惠券',
  `reward_coupon` varchar(255) DEFAULT NULL COMMENT '入账的优惠券名称，逗号分隔',
  `terminal_source` tinyint(4) DEFAULT NULL COMMENT '终端来源',
  PRIMARY KEY (`record_id`),
  KEY `index_register_time` (`register_time`) USING BTREE,
  KEY `index_request_customer_id` (`request_customer_id`) USING BTREE,
  KEY `index_invited_customer_id` (`invited_customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀新记录表';

CREATE TABLE `s2b_statistics`.`replay_store_evaluate` (
  `evaluate_id` varchar(32) NOT NULL COMMENT '评价id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺Id',
  `store_name` varchar(150) DEFAULT NULL COMMENT '店铺名称',
  `order_no` varchar(255) NOT NULL COMMENT '订单号',
  `buy_time` datetime DEFAULT NULL COMMENT '购买时间',
  `customer_id` varchar(32) NOT NULL COMMENT '会员Id',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '会员名称',
  `customer_account` varchar(20) NOT NULL COMMENT '会员登录账号|手机号',
  `goods_score` tinyint(4) NOT NULL DEFAULT '5' COMMENT '商品评分',
  `server_score` tinyint(4) NOT NULL DEFAULT '5' COMMENT '服务评分',
  `logistics_score` tinyint(4) NOT NULL DEFAULT '5' COMMENT '物流评分',
  `composite_score` decimal(20,2) DEFAULT NULL COMMENT '综合评分（冗余字段看后面怎么做）',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `del_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `terminal_source` tinyint(4) DEFAULT NULL COMMENT '终端来源',
  PRIMARY KEY (`evaluate_id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺评价表';

drop table `sbc-crm`.`customer_recent_param_statistics`;
CREATE TABLE `sbc-crm`.`customer_recent_param_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `customer_id` varchar(32) NOT NULL COMMENT '会员id',
  `from_day_num` int(11) DEFAULT NULL COMMENT '距离统计当天天数',
  `action_type` tinyint(3) DEFAULT NULL COMMENT '行为类型：0:无,1：有访问,2：有收藏,3：有加购,4：有下单,5：有付款,6：有申请退单,7：有评价商品,8：有评价店铺,9：有关注店铺,10：有分享商品,11：有分享商城,12：有分享店铺,13：有分享赚,14：有邀请好友,15：有签到',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='会员最近的相关指标';


ALTER TABLE `sbc-crm`.`customer_plan`
MODIFY COLUMN `trigger_condition` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '触发条件，以逗号分隔（1-有访问；2-有收藏；3-有加购，4-有下单；5-有付款,6-有申请退单,7-有评价商品,8-有评价店铺,9-有关注店铺,10-有分享商品,11-有分享商城,12-有分享店铺,13-有分享赚,14-有邀请好友,15-有签到）' AFTER `trigger_flag`;

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 6, '0 24 1 * * ?', '清除自动自动标签--关联会员关系数据（清除最近3天之前的数据）', '2021-02-26 14:00:32', '2021-02-26 14:01:05', '吕振伟', '', 'FIRST', 'autoTagCustomerStatisticsCleanHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-02-26 14:00:32', '');

UPDATE `sbc-crm`.`auto_tag_init` SET tag_name = '终端偏好', type = 0, day = 60, relation_type = 0, rule_json =
'{"autoTagSelectMap":{0:{"autoTagSelectValues":{0:{"columnName":"terminal_source","name":"终端分布最多","selectedId":2,"value":["1"]}},"count":0,"dataSource":[{"columnName":"terminal_source","columnType":2,"defaultValue":[{"id":"微信小程序","value":"微信小程序"},{"id":"APP","value":"APP"},{"id":"PC","value":"PC"},{"id":"H5","value":"H5"}],"id":1,"name":"终端","tagDimensionId":1},{"columnName":"time","columnType":2,"defaultValue":[{"id":"00:00-01:00","value":"00:00-01:00"},{"id":"01:00-02:00","value":"01:00-02:00"},{"id":"02:00-03:00","value":"02:00-03:00"},{"id":"03:00-04:00","value":"03:00-04:00"},{"id":"04:00-05:00","value":"04:00-05:00"},{"id":"05:00-06:00","value":"05:00-06:00"},{"id":"06:00-07:00","value":"06:00-07:00"},{"id":"07:00-08:00","value":"07:00-08:00"},{"id":"08:00-09:00","value":"08:00-09:00"},{"id":"09:00-10:00","value":"09:00-10:00"},{"id":"10:00-11:00","value":"10:00-11:00"},{"id":"11:00-12:00","value":"11:00-12:00"},{"id":"12:00-13:00","value":"12:00-13:00"},{"id":"13:00-14:00","value":"13:00-14:00"},{"id":"14:00-15:00","value":"14:00-15:00"},{"id":"15:00-16:00","value":"15:00-16:00"},{"id":"16:00-17:00","value":"16:00-17:00"},{"id":"17:00-18:00","value":"17:00-18:00"},{"id":"18:00-19:00","value":"18:00-19:00"},{"id":"19:00-20:00","value":"19:00-20:00"},{"id":"20:00-21:00","value":"20:00-21:00"},{"id":"21:00-22:00","value":"21:00-22:00"},{"id":"22:00-23:00","value":"22:00-23:00"},{"id":"23:00-24:00","value":"23:00-24:00"}],"id":5,"name":"时间","tagDimensionId":1},{"columnName":"date","columnType":2,"defaultValue":[{"id":"周一","value":"周一"},{"id":"周二","value":"周二"},{"id":"周三","value":"周三"},{"id":"周四","value":"周四"},{"id":"周五","value":"周五"},{"id":"周六","value":"周六"},{"id":"周日","value":"周日"}],"id":7,"name":"日期","tagDimensionId":1},{"columnName":"cate_top_id","columnType":2,"defaultValue":[{"id":"565","value":"食品粮油"},{"id":"566","value":"中外名酒"},{"id":"567","value":"个护化妆"},{"id":"568","value":"电路"},{"id":"569","value":"服饰鞋靴"},{"id":"571","value":"母婴产品"},{"id":"573","value":"汽车用品"},{"id":"574","value":"电工电器"},{"id":"829","value":"燃气"},{"id":"884","value":"电脑/办公"}],"id":9,"name":"商品类目","tagDimensionId":1},{"columnName":"cate_id","columnType":2,"defaultValue":[{"id":"576","value":"卸妆产品"},{"id":"580","value":"大米"},{"id":"584","value":"苹果"},{"id":"585","value":"香蕉"},{"id":"586","value":"火龙果"},{"id":"587","value":"猕猴桃"},{"id":"588","value":"橙子"},{"id":"589","value":"橘子"},{"id":"590","value":"柚子"},{"id":"591","value":"西瓜"}],"id":11,"name":"商品品类","tagDimensionId":1},{"columnName":"brand_id","columnType":2,"defaultValue":[{"id":"181","value":"美赞臣"},{"id":"182","value":"喜宝"},{"id":"183","value":"美素佳儿"},{"id":"186","value":"诺优能"},{"id":"187","value":"好奇"},{"id":"188","value":"帮宝适"},{"id":"189","value":"妈咪宝贝"},{"id":"192","value":"潘苹果"},{"id":"193","value":"李锦记1"},{"id":"194","value":"海天"}],"id":13,"name":"商品品牌","tagDimensionId":1},{"columnName":"goods_id","columnType":2,"defaultValue":[{"id":"ff80808175e0487101761d45c9ed0142","value":"手机12.1"},{"id":"ff80808175e0487101761cef180a0133","value":"德意 一次性使用输液用连通管"},{"id":"ff80808175e0487101761cef16ef0130","value":"小鱼儿"},{"id":"ff80808175e0487101761983e3f80125","value":"22222222"},{"id":"ff80808175e048710176187870560120","value":"shang"},{"id":"ff80808175e0487101761848f590011d","value":"定时商品测试2"},{"id":"ff80808175e04871017617e83ec70117","value":"定时上架商品测试"},{"id":"ff80808175e04871017603d08a8500df","value":"图片链接商品"},{"id":"ff80808175e048710176026416af00b0","value":"评价商品"},{"id":"ff80808175e0487101760249990a00ae","value":"凌度（BLACKVIEW）HS980C 行车记录仪高清 7英寸大屏 多点触控"}],"id":15,"name":"商品","tagDimensionId":1},{"columnName":"store_id","columnType":2,"defaultValue":[{"id":"123456857","value":"中山路精品茶业"},{"id":"123456858","value":"母婴用品体验店"},{"id":"123456859","value":"中山路门店"},{"id":"123456860","value":"酒水饮料自营店"},{"id":"123456861","value":"Blue&Pink旗舰店"},{"id":"123456862","value":"汽车用品体验店"},{"id":"123456863","value":"南京电子"},{"id":"123456864","value":"ONLY旗舰店"},{"id":"123456865","value":"李勇的店"},{"id":"123456868","value":"海鸿"}],"id":17,"name":"店铺","tagDimensionId":1}],"dataSourceFirst":[{"columnName":"terminal_source","columnType":0,"id":2,"name":"终端分布最多","tagDimensionId":1},{"columnName":"time","columnType":0,"id":6,"name":"时间范围分布最多","tagDimensionId":1},{"columnName":"date","columnType":0,"id":8,"name":"日期分布最多","tagDimensionId":1},{"columnName":"cate_top_id","columnType":0,"id":10,"name":"商品类目分布最多","tagDimensionId":1},{"columnName":"cate_id","columnType":0,"id":12,"name":"商品品类分布最多","tagDimensionId":1},{"columnName":"brand_id","columnType":0,"id":14,"name":"商品品牌分布最多","tagDimensionId":1},{"columnName":"goods_id","columnType":0,"id":16,"name":"商品分布最多","tagDimensionId":1},{"columnName":"store_id","columnType":0,"id":18,"name":"店铺分布最多","tagDimensionId":1}],"maxLen":8,"selectedId":1}},"count":0,"dataRange":[],"day":60,"maxLen":14,"relationType":"AND","tagName":"终端偏好","type":"PREFERENCE","updatePerson":"2c8080815cd3a74a015cd3ae86850001"}', rule_json_sql = '{"bigData":false,"dayNum":60,"dimensionInfoList":[{"dimensionName":"ACCESS","dimensionType":"NO_FIRST_LAST","paramInfoList":[],"paramResult":{"dataRange":[],"paramName":"TERMINAL_SOURCE","paramValue":"1"},"relationType":"AND"}],"endStaDate":"2021-03-12","relationType":"AND","startStaDate":"2021-01-11","tagId":1,"tagName":"终端偏好","tagType":"PREFERENCE"}' WHERE id = 1;
UPDATE `sbc-crm`.`auto_tag_init` SET tag_name = '消费日偏好', type = 0, day = 60, relation_type = 0, rule_json = '{"autoTagSelectMap":{0:{"autoTagSelectValues":{0:{"columnName":"date","name":"日期分布最多","selectedId":224,"value":["3"]}},"count":0,"dataSource":[{"columnName":"time","columnType":2,"defaultValue":[{"id":"00:00-01:00","value":"00:00-01:00"},{"id":"01:00-02:00","value":"01:00-02:00"},{"id":"02:00-03:00","value":"02:00-03:00"},{"id":"03:00-04:00","value":"03:00-04:00"},{"id":"04:00-05:00","value":"04:00-05:00"},{"id":"05:00-06:00","value":"05:00-06:00"},{"id":"06:00-07:00","value":"06:00-07:00"},{"id":"07:00-08:00","value":"07:00-08:00"},{"id":"08:00-09:00","value":"08:00-09:00"},{"id":"09:00-10:00","value":"09:00-10:00"},{"id":"10:00-11:00","value":"10:00-11:00"},{"id":"11:00-12:00","value":"11:00-12:00"},{"id":"12:00-13:00","value":"12:00-13:00"},{"id":"13:00-14:00","value":"13:00-14:00"},{"id":"14:00-15:00","value":"14:00-15:00"},{"id":"15:00-16:00","value":"15:00-16:00"},{"id":"16:00-17:00","value":"16:00-17:00"},{"id":"17:00-18:00","value":"17:00-18:00"},{"id":"18:00-19:00","value":"18:00-19:00"},{"id":"19:00-20:00","value":"19:00-20:00"},{"id":"20:00-21:00","value":"20:00-21:00"},{"id":"21:00-22:00","value":"21:00-22:00"},{"id":"22:00-23:00","value":"22:00-23:00"},{"id":"23:00-24:00","value":"23:00-24:00"}],"id":221,"name":"时间","tagDimensionId":13},{"columnName":"date","columnType":2,"defaultValue":[{"id":"周一","value":"周一"},{"id":"周二","value":"周二"},{"id":"周三","value":"周三"},{"id":"周四","value":"周四"},{"id":"周五","value":"周五"},{"id":"周六","value":"周六"},{"id":"周日","value":"周日"}],"id":223,"name":"日期","tagDimensionId":13},{"columnName":"cate_top_id","columnType":2,"defaultValue":[{"id":"565","value":"食品粮油"},{"id":"566","value":"中外名酒"},{"id":"567","value":"个护化妆"},{"id":"568","value":"电路"},{"id":"569","value":"服饰鞋靴"},{"id":"571","value":"母婴产品"},{"id":"573","value":"汽车用品"},{"id":"574","value":"电工电器"},{"id":"829","value":"燃气"},{"id":"884","value":"电脑/办公"}],"id":225,"name":"商品类目","tagDimensionId":13},{"columnName":"cate_id","columnType":2,"defaultValue":[{"id":"576","value":"卸妆产品"},{"id":"580","value":"大米"},{"id":"584","value":"苹果"},{"id":"585","value":"香蕉"},{"id":"586","value":"火龙果"},{"id":"587","value":"猕猴桃"},{"id":"588","value":"橙子"},{"id":"589","value":"橘子"},{"id":"590","value":"柚子"},{"id":"591","value":"西瓜"}],"id":227,"name":"商品品类","tagDimensionId":13},{"columnName":"money","columnType":1,"id":611,"name":"金额","tagDimensionId":13},{"columnName":"brand_id","columnType":2,"defaultValue":[{"id":"181","value":"美赞臣"},{"id":"182","value":"喜宝"},{"id":"183","value":"美素佳儿"},{"id":"186","value":"诺优能"},{"id":"187","value":"好奇"},{"id":"188","value":"帮宝适"},{"id":"189","value":"妈咪宝贝"},{"id":"192","value":"潘苹果"},{"id":"193","value":"李锦记1"},{"id":"194","value":"海天"}],"id":229,"name":"商品品牌","tagDimensionId":13},{"columnName":"goods_id","columnType":2,"defaultValue":[{"id":"ff80808175e0487101761d45c9ed0142","value":"手机12.1"},{"id":"ff80808175e0487101761cef180a0133","value":"德意 一次性使用输液用连通管"},{"id":"ff80808175e0487101761cef16ef0130","value":"小鱼儿"},{"id":"ff80808175e0487101761983e3f80125","value":"22222222"},{"id":"ff80808175e048710176187870560120","value":"shang"},{"id":"ff80808175e0487101761848f590011d","value":"定时商品测试2"},{"id":"ff80808175e04871017617e83ec70117","value":"定时上架商品测试"},{"id":"ff80808175e04871017603d08a8500df","value":"图片链接商品"},{"id":"ff80808175e048710176026416af00b0","value":"评价商品"},{"id":"ff80808175e0487101760249990a00ae","value":"凌度（BLACKVIEW）HS980C 行车记录仪高清 7英寸大屏 多点触控"}],"id":231,"name":"商品","tagDimensionId":13},{"columnName":"store_id","columnType":2,"defaultValue":[{"id":"123456857","value":"中山路精品茶业"},{"id":"123456858","value":"母婴用品体验店"},{"id":"123456859","value":"中山路门店"},{"id":"123456860","value":"酒水饮料自营店"},{"id":"123456861","value":"Blue&Pink旗舰店"},{"id":"123456862","value":"汽车用品体验店"},{"id":"123456863","value":"南京电子"},{"id":"123456864","value":"ONLY旗舰店"},{"id":"123456865","value":"李勇的店"},{"id":"123456868","value":"海鸿"}],"id":233,"name":"店铺","tagDimensionId":13},{"columnName":"terminal_source","columnType":2,"defaultValue":[{"id":"微信小程序","value":"微信小程序"},{"id":"APP","value":"APP"},{"id":"PC","value":"PC"},{"id":"H5","value":"H5"}],"id":215,"name":"终端","tagDimensionId":13}],"dataSourceFirst":[{"columnName":"terminal_source","columnType":0,"id":216,"name":"终端分布最多","tagDimensionId":13},{"columnName":"store_id","columnType":0,"id":234,"name":"店铺分布最多","tagDimensionId":13},{"columnName":"money","columnType":0,"id":220,"name":"金额范围分布最多","tagDimensionId":13},{"columnName":"time","columnType":0,"id":222,"name":"时间范围分布最多","tagDimensionId":13},{"columnName":"date","columnType":0,"id":224,"name":"日期分布最多","tagDimensionId":13},{"columnName":"cate_top_id","columnType":0,"id":226,"name":"商品类目分布最多","tagDimensionId":13},{"columnName":"cate_id","columnType":0,"id":228,"name":"商品品类分布最多","tagDimensionId":13},{"columnName":"brand_id","columnType":0,"id":230,"name":"商品品牌分布最多","tagDimensionId":13},{"columnName":"goods_id","columnType":0,"id":232,"name":"商品分布最多","tagDimensionId":13}],"maxLen":9,"selectedId":13}},"count":0,"dataRange":[],"day":60,"maxLen":14,"relationType":"AND","tagName":"消费日偏好","type":"PREFERENCE","updatePerson":"2c8080815cd3a74a015cd3ae86850001"}', rule_json_sql = '{"bigData":false,"dayNum":60,"dimensionInfoList":[{"dimensionName":"PAY_ORDER","dimensionType":"NO_FIRST_LAST","paramInfoList":[],"paramResult":{"dataRange":[],"paramName":"DATE","paramValue":"3"},"relationType":"AND"}],"endStaDate":"2021-03-12","relationType":"AND","startStaDate":"2021-01-11","tagId":10,"tagName":"消费日偏好","tagType":"PREFERENCE"}' WHERE id = 10;






CREATE TABLE `sbc-customer`.`distribution_performance_day_copy` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL,
  `distribution_id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '分销员id',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '会员id',
  `sale_amount` decimal(18,2) NOT NULL COMMENT '销售额',
  `commission` decimal(18,2) NOT NULL COMMENT '收益',
  `target_date` date NOT NULL COMMENT '统计的目标日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计时间',
  PRIMARY KEY (`id`),
  KEY `idx_distribution_id` (`distribution_id`),
  KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='分销员日业绩记录';

insert into `sbc-customer`.distribution_performance_day_copy select * from `sbc-customer`.distribution_performance_day;

update `sbc-customer`.distribution_performance_day_copy set id=concat('DP',DATE_FORMAT(create_time,'%Y%m%d%H%i%s'),CEILING(RAND()*9000+1000));

ALTER TABLE `sbc-customer`.`distribution_performance_day_copy` PARTITION BY RANGE COLUMNS(id)
(PARTITION p201904 VALUES LESS THAN ('DP201905') ENGINE = InnoDB,
 PARTITION p201905 VALUES LESS THAN ('DP201906') ENGINE = InnoDB,
 PARTITION p201906 VALUES LESS THAN ('DP201907') ENGINE = InnoDB,
 PARTITION p201907 VALUES LESS THAN ('DP201908') ENGINE = InnoDB,
 PARTITION p201908 VALUES LESS THAN ('DP201909') ENGINE = InnoDB,
 PARTITION p201909 VALUES LESS THAN ('DP201910') ENGINE = InnoDB,
 PARTITION p201910 VALUES LESS THAN ('DP201911') ENGINE = InnoDB,
 PARTITION p201911 VALUES LESS THAN ('DP201912') ENGINE = InnoDB,
 PARTITION p201912 VALUES LESS THAN ('DP202001') ENGINE = InnoDB,
 PARTITION p202001 VALUES LESS THAN ('DP202002') ENGINE = InnoDB,
 PARTITION p202002 VALUES LESS THAN ('DP202003') ENGINE = InnoDB,
 PARTITION p202003 VALUES LESS THAN ('DP202004') ENGINE = InnoDB,
 PARTITION p202004 VALUES LESS THAN ('DP202005') ENGINE = InnoDB,
 PARTITION p202005 VALUES LESS THAN ('DP202006') ENGINE = InnoDB,
 PARTITION p202006 VALUES LESS THAN ('DP202007') ENGINE = InnoDB,
 PARTITION p202007 VALUES LESS THAN ('DP202008') ENGINE = InnoDB,
 PARTITION p202008 VALUES LESS THAN ('DP202009') ENGINE = InnoDB,
 PARTITION p202009 VALUES LESS THAN ('DP202010') ENGINE = InnoDB,
 PARTITION p202010 VALUES LESS THAN ('DP202011') ENGINE = InnoDB,
 PARTITION p202011 VALUES LESS THAN ('DP202012') ENGINE = InnoDB,
 PARTITION p202012 VALUES LESS THAN ('DP202101') ENGINE = InnoDB,
 PARTITION p202101 VALUES LESS THAN ('DP202102') ENGINE = InnoDB,
 PARTITION p202102 VALUES LESS THAN ('DP202103') ENGINE = InnoDB,
 PARTITION p202103 VALUES LESS THAN ('DP202104') ENGINE = InnoDB);

drop table `sbc-customer`.distribution_performance_day;

rename table `sbc-customer`.distribution_performance_day_copy to `sbc-customer`.distribution_performance_day;



 CREATE TABLE `sbc-customer`.`distribution_performance_month_copy` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL,
  `distribution_id` varchar(32) NOT NULL COMMENT '分销员id',
  `customer_id` varchar(32) NOT NULL COMMENT '会员id',
  `sale_amount` decimal(18,2) NOT NULL COMMENT '销售额',
  `commission` decimal(18,2) NOT NULL COMMENT '收益',
  `target_date` date NOT NULL COMMENT '统计的目标日期，月报表中记录的是该月最后一天，用于分区',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计时间',
  PRIMARY KEY (`id`),
  KEY `idx_distribution_id` (`distribution_id`),
  KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='分销员月业绩记录,该表为定时任务从日表中聚合生成';

insert into `sbc-customer`.distribution_performance_month_copy select * from `sbc-customer`.distribution_performance_month;

update `sbc-customer`.distribution_performance_month_copy set id=concat('DP',DATE_FORMAT(create_time,'%Y%m%d%H%i%s'),CEILING(RAND()*9000+1000));


ALTER TABLE `sbc-customer`.`distribution_performance_month_copy` PARTITION BY RANGE COLUMNS(id)
(PARTITION p201904 VALUES LESS THAN ('DP201905') ENGINE = InnoDB,
 PARTITION p201905 VALUES LESS THAN ('DP201906') ENGINE = InnoDB,
 PARTITION p201906 VALUES LESS THAN ('DP201907') ENGINE = InnoDB,
 PARTITION p201907 VALUES LESS THAN ('DP201908') ENGINE = InnoDB,
 PARTITION p201908 VALUES LESS THAN ('DP201909') ENGINE = InnoDB,
 PARTITION p201909 VALUES LESS THAN ('DP201910') ENGINE = InnoDB,
 PARTITION p201910 VALUES LESS THAN ('DP201911') ENGINE = InnoDB,
 PARTITION p201911 VALUES LESS THAN ('DP201912') ENGINE = InnoDB,
 PARTITION p201912 VALUES LESS THAN ('DP202001') ENGINE = InnoDB,
 PARTITION p202001 VALUES LESS THAN ('DP202002') ENGINE = InnoDB,
 PARTITION p202002 VALUES LESS THAN ('DP202003') ENGINE = InnoDB,
 PARTITION p202003 VALUES LESS THAN ('DP202004') ENGINE = InnoDB,
 PARTITION p202004 VALUES LESS THAN ('DP202005') ENGINE = InnoDB,
 PARTITION p202005 VALUES LESS THAN ('DP202006') ENGINE = InnoDB,
 PARTITION p202006 VALUES LESS THAN ('DP202007') ENGINE = InnoDB,
 PARTITION p202007 VALUES LESS THAN ('DP202008') ENGINE = InnoDB,
 PARTITION p202008 VALUES LESS THAN ('DP202009') ENGINE = InnoDB,
 PARTITION p202009 VALUES LESS THAN ('DP202010') ENGINE = InnoDB,
 PARTITION p202010 VALUES LESS THAN ('DP202011') ENGINE = InnoDB,
 PARTITION p202011 VALUES LESS THAN ('DP202012') ENGINE = InnoDB,
 PARTITION p202012 VALUES LESS THAN ('DP202101') ENGINE = InnoDB,
 PARTITION p202101 VALUES LESS THAN ('DP202102') ENGINE = InnoDB,
 PARTITION p202102 VALUES LESS THAN ('DP202103') ENGINE = InnoDB,
 PARTITION p202103 VALUES LESS THAN ('DP202104') ENGINE = InnoDB);

drop table `sbc-customer`.distribution_performance_month;

rename table `sbc-customer`.distribution_performance_month_copy to `sbc-customer`.distribution_performance_month;


DROP PROCEDURE IF EXISTS `sbc-customer`.`create_partition_by_year_month`;

CREATE DEFINER=`root`@`%` PROCEDURE `sbc-customer`.`create_partition_by_year_month`(IN_SCHEMANAME VARCHAR(64), IN_TABLENAME VARCHAR(64))
BEGIN
    DECLARE ROWS_CNT INT UNSIGNED;
    DECLARE BEGINTIME DATE;
    DECLARE ENDTIME varchar(50);
    DECLARE PARTITIONNAME VARCHAR(16);
    SET BEGINTIME = DATE(NOW());
    SET PARTITIONNAME = DATE_FORMAT( BEGINTIME, 'p%Y%m' );
    SET ENDTIME = DATE_FORMAT(DATE(BEGINTIME + INTERVAL 1 MONTH),'DP%Y%m');

    SELECT COUNT(*) INTO ROWS_CNT FROM information_schema.partitions
  WHERE table_schema = IN_SCHEMANAME AND table_name = IN_TABLENAME AND partition_name = PARTITIONNAME;
    IF ROWS_CNT = 0 THEN
        SET @SQL = CONCAT( 'ALTER TABLE `', IN_SCHEMANAME, '`.`', IN_TABLENAME, '`',
      ' ADD PARTITION (PARTITION ', PARTITIONNAME, ' VALUES LESS THAN (\'', ENDTIME, '\') ENGINE = InnoDB);' );
        PREPARE STMT FROM @SQL;
        EXECUTE STMT;
        DEALLOCATE PREPARE STMT;
     ELSE
  SELECT CONCAT("partition `", PARTITIONNAME, "` for table `",IN_SCHEMANAME, ".", IN_TABLENAME, "` already exists") AS result;
     END IF;
END;




