create table `s2b_statistics`.`marketing_situation_day`
(
  id                 bigint auto_increment
    primary key,
  marketing_type     tinyint        null
  comment '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售',
  start_date               date           not null
  comment '统计日期',
  store_id           bigint         null
  comment '店铺ID -1：平台',
  activity_num       int            null
  comment '活动数量',
  pay_money          decimal(10, 2) null
  comment '营销支付金额',
  discount_money     decimal(10, 2) null
  comment '营销优惠金额',
  pay_customer_count bigint         null
  comment '营销支付人数',
  pay_goods_count    bigint         null
  comment '营销支付件数',
  pay_trade_count    bigint         null
  comment '营销支付订单数',
  new_customer       int            null
  comment '新成交客户',
  old_customer       int            null
  comment '老成交客户',
  pay_roi            decimal(10, 2) null
  comment '支付ROI：统计时间内，营销支付金额 / 营销优惠金额',
  join_rate          decimal(10, 2) null
  comment '连带率：统计时间内，营销支付件数 / 营销支付订单数',
  customer_price     decimal(10, 2) null
  comment '客单价：统计时间内，营销支付金额/营销支付人数',
  create_time        datetime       null
  comment '创建时间'

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='营销活动数据概况天维度';

-- 营销活动数据概况-最近7天
-- auto-generated definition
create table `s2b_statistics`.`marketing_situation_seven`
(
  id                 bigint auto_increment
    primary key,
  marketing_type     tinyint        null
  comment '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售',
  store_id           bigint         null
  comment '店铺ID -1：平台',
  activity_num       int            null
  comment '活动数量',
  pay_money          decimal(10, 2) null
  comment '营销支付金额',
  discount_money     decimal(10, 2) null
  comment '营销优惠金额',
  pay_customer_count bigint         null
  comment '营销支付人数',
  pay_goods_count    bigint         null
  comment '营销支付件数',
  pay_trade_count    bigint         null
  comment '营销支付订单数',
  new_customer       int            null
  comment '新成交客户',
  old_customer       int            null
  comment '老成交客户',
  pay_roi            decimal(10, 2) null
  comment '支付ROI：统计时间内，营销支付金额 / 营销优惠金额',
  join_rate          decimal(10, 2) null
  comment '连带率：统计时间内，营销支付件数 / 营销支付订单数',
  customer_price     decimal(10, 2) null
  comment '客单价：统计时间内，营销支付金额/营销支付人数',
  create_time        datetime       null
  comment '创建时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='营销活动数据概况近7天维度';

-- 营销活动数据概况-最近30天
-- auto-generated definition
create table `s2b_statistics`.`marketing_situation_thirty`
(
  id                 bigint auto_increment
    primary key,
  marketing_type     tinyint        null
  comment '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售',
  store_id           bigint         null
  comment '店铺ID -1：平台',
  activity_num       int            null
  comment '活动数量',
  pay_money          decimal(10, 2) null
  comment '营销支付金额',
  discount_money     decimal(10, 2) null
  comment '营销优惠金额',
  pay_customer_count bigint         null
  comment '营销支付人数',
  pay_goods_count    bigint         null
  comment '营销支付件数',
  pay_trade_count    bigint         null
  comment '营销支付订单数',
  new_customer       int            null
  comment '新成交客户',
  old_customer       int            null
  comment '老成交客户',
  pay_roi            decimal(10, 2) null
  comment '支付ROI：统计时间内，营销支付金额 / 营销优惠金额',
  join_rate          decimal(10, 2) null
  comment '连带率：统计时间内，营销支付件数 / 营销支付订单数',
  customer_price     decimal(10, 2) null
  comment '客单价：统计时间内，营销支付金额/营销支付人数',
  create_time        datetime       null
  comment '创建时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='营销活动数据概况近30天维度';

-- 营销活动数据概况自然月维度
-- auto-generated definition
create table `s2b_statistics`.`marketing_situation_month`
(
  id                 bigint auto_increment
    primary key,
  month              varchar(50)    null
  comment '统计月份',
  marketing_type     tinyint        null
  comment '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售',
  store_id           bigint         null
  comment '店铺ID -1：平台',
  activity_num       int            null
  comment '活动数量',
  pay_money          decimal(10, 2) null
  comment '营销支付金额',
  discount_money     decimal(10, 2) null
  comment '营销优惠金额',
  pay_customer_count bigint         null
  comment '营销支付人数',
  pay_goods_count    bigint         null
  comment '营销支付件数',
  pay_trade_count    bigint         null
  comment '营销支付订单数',
  new_customer       int            null
  comment '新成交客户',
  old_customer       int            null
  comment '老成交客户',
  pay_roi            decimal(10, 2) null
  comment '支付ROI：统计时间内，营销支付金额 / 营销优惠金额',
  join_rate          decimal(10, 2) null
  comment '连带率：统计时间内，营销支付件数 / 营销支付订单数',
  customer_price     decimal(10, 2) null
  comment '客单价：统计时间内，营销支付金额/营销支付人数',
  create_time        datetime       null
  comment '创建时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 comment='营销活动数据概况自然月维度';

CREATE TABLE `s2b_statistics`.`replay_marketing` (
  `marketing_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '促销Id',
  `marketing_name` varchar(40) NOT NULL COMMENT '促销名称',
  `marketing_type` tinyint(4) NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐',
  `sub_type` tinyint(4) NOT NULL COMMENT '促销子类型 0:满金额减 1:满数量减 2:满金额折 3:满数量折 4:满金额赠 5:满数量赠 6:一口价 7:第二件半价 8:组合商品',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `scope_type` tinyint(4) NOT NULL COMMENT '参加促销类型 0：全部货品 1：货品 2：品牌 3：分类',
  `join_level` varchar(500) NOT NULL COMMENT '参加会员  -1:全部客户 0:全部等级 other:其他等级',
  `is_boss` tinyint(4) NOT NULL COMMENT '是否是平台，1：boss，0：商家',
  `store_id` bigint(20) DEFAULT NULL COMMENT '商铺Id  0：boss,  other:其他商家',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `is_pause` tinyint(4) NOT NULL COMMENT '是否暂停（1：暂停，0：正常）',
  PRIMARY KEY (`marketing_id`),
  KEY `index_begin_time` (`begin_time`) USING BTREE,
  KEY `index_end_time` (`end_time`) USING BTREE,
  KEY `index_sub_type` (`sub_type`) USING BTREE,
  KEY `index_store_id` (`store_id`) USING BTREE,
  KEY `index_marketing_type` (`marketing_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='营销表';

CREATE TABLE `s2b_statistics`.`replay_marketing_scope` (
  `marketing_scope_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '货品与促销规则表Id',
  `marketing_id` bigint(20) NOT NULL COMMENT '促销Id',
  `scope_id` varchar(32) NOT NULL COMMENT '促销范围Id',
  PRIMARY KEY (`marketing_scope_id`),
  KEY `index_marketing_id` (`marketing_id`) USING BTREE,
  KEY `index_sku_id` (`scope_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='营销和商品关联表';


CREATE TABLE `s2b_statistics`.`marketing_statistics_day` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '订单ID',
  `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
  `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销ID',
  `marketing_type` tinyint(2) unsigned NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐',
  `total_pay_cash` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '营销支付金额',
  `discounts_pay_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '营销优惠金额',
  `order_pay_sum` bigint(20) NOT NULL COMMENT '营销支付订单数',
  `customer_pay_num` bigint(20) NOT NULL COMMENT '营销支付人数',
  `goods_pay_num` bigint(20) NOT NULL COMMENT '营销支付件数',
  `boss_customer_flag` tinyint(2) unsigned DEFAULT NULL COMMENT '按日划分是否平台新老客户，0：新客户，1：老客户',
  `boss_customer_flag_week` tinyint(2) DEFAULT NULL COMMENT '按周划分是否平台新老客户，0：新客户，1：老客户',
  `store_customer_flag` tinyint(2) DEFAULT NULL COMMENT '按日划分是否商家新老客户，0：新客户，1：老客户',
  `store_customer_flag_week` tinyint(2) DEFAULT NULL COMMENT '按周划分是否商家新老客户，0：新客户，1：老客户',
  `create_time` datetime NOT NULL COMMENT '创建时间:订单支付时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_store_id` (`store_id`) USING BTREE,
  KEY `idx_marketing_type` (`marketing_type`) USING BTREE,
  KEY `idx_marketing_id` (`marketing_id`) USING BTREE,
  KEY `idx_tid` (`tid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='减折赠每日统计数据表';


CREATE TABLE `s2b_statistics`.`replay_trade_marketing` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '订单ID',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销ID',
  `marketing_type` tinyint(2) NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐',
  `discounts_amount` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `real_pay_amount` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '该活动关联商品除去优惠金额外的应付金额',
  `customer_id` varchar(32) NOT NULL COMMENT '用户id',
  `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
  `pay_state` tinyint(2) NOT NULL COMMENT '订单支付状态: 0:未支付 1 待确认 2 已支付',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  PRIMARY KEY (`id`),
  KEY `idx_marketing_id` (`marketing_id`) USING BTREE,
  KEY `idx_marketing_type` (`marketing_type`) USING BTREE,
  KEY `idx_pay_time` (`pay_time`) USING BTREE,
  KEY `idx_pay_state` (`pay_state`) USING BTREE,
  KEY `idx_tid` (`tid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单营销信息表';

CREATE TABLE `s2b_statistics`.`replay_trade_marketing_sku` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '订单ID',
  `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销ID',
  `marketing_type` tinyint(2) NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐',
  `sku_id` varchar(32) NOT NULL COMMENT 'SKU编号',
  `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
  `pay_state` tinyint(2) NOT NULL COMMENT '订单支付状态: 0:未支付 1 待确认 2 已支付',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`tid`) USING BTREE,
  KEY `idx_marketing_id` (`marketing_id`) USING BTREE,
  KEY `idx_marketing_type` (`marketing_type`) USING BTREE,
  KEY `idx_pay_time` (`pay_time`) USING BTREE,
  KEY `idx_pay_state` (`pay_state`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单营销商品表';

CREATE TABLE `s2b_statistics`.`replay_trade_item_marketing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '订单ID',
  `sku_id` varchar(32) NOT NULL COMMENT 'SKU编号',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销ID',
  `marketing_type` tinyint(2) NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐',
  `split_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '除去营销优惠金额后的商品均摊价',
  `discounts_amount` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`tid`) USING BTREE,
  KEY `idx_sku_id` (`sku_id`) USING BTREE,
  KEY `idx_marketing_id` (`marketing_id`) USING BTREE,
  KEY `idx_marketing_type` (`marketing_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单商品对应的营销优惠金额';


CREATE TABLE `s2b_statistics`.`trade_marketing_sku_detail_day` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) NOT NULL COMMENT '订单ID',
  `sku_id` varchar(32) NOT NULL COMMENT 'SKU编号',
  `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
  `store_id` bigint(20) NOT NULL COMMENT '店铺ID',
  `marketing_id` bigint(20) NOT NULL COMMENT '营销ID',
  `marketing_type` tinyint(2) NOT NULL COMMENT '促销类型 0：满减 1:满折 2:满赠 3一口价优惠 4第二件半价 5秒杀(无用) 6组合套餐',
  `total_pay_cash` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '订单实际支付金额',
  `discounts_amount` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `create_time` datetime NOT NULL COMMENT '创建时间:订单支付时间',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`tid`) USING BTREE,
  KEY `idx_sku_id` (`sku_id`) USING BTREE,
  KEY `idx_customer_id` (`customer_id`) USING BTREE,
  KEY `idx_store_id` (`store_id`) USING BTREE,
  KEY `idx_marketing_id` (`marketing_id`) USING BTREE,
  KEY `idx_marketing_type` (`marketing_type`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单营销商品明细表';

CREATE TABLE `s2b_statistics`.`marketing_overview_day` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL COMMENT '统计日期',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID -1：平台',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '订单支付金额',
  `marketing_pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
  `pay_trade_count` bigint(20) DEFAULT NULL COMMENT '营销支付订单数',
  `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
  `boss_pay_customer_count` bigint(20) DEFAULT NULL COMMENT '平台营销支付人数',
  `new_customer` bigint(20) DEFAULT NULL COMMENT '新用户数量',
  `old_customer` bigint(20) DEFAULT NULL COMMENT '老用户数量',
  `boss_new_customer` bigint(20) DEFAULT NULL COMMENT '平台新用户',
  `boss_old_customer` bigint(20) DEFAULT NULL COMMENT '平台老用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37870 DEFAULT CHARSET=utf8mb4 COMMENT='营销概览天维度';

CREATE TABLE `s2b_statistics`.`marketing_overview_month` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `month` varchar(50) DEFAULT NULL COMMENT '统计月份 2021-01',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID -1：平台',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '订单支付金额',
  `marketing_pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
  `pay_trade_count` bigint(20) DEFAULT NULL COMMENT '营销支付订单数',
  `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
  `boss_pay_customer_count` bigint(20) DEFAULT NULL COMMENT '平台营销支付人数',
  `new_customer` bigint(20) DEFAULT NULL COMMENT '新用户数量',
  `old_customer` bigint(20) DEFAULT NULL COMMENT '老用户数量',
  `boss_new_customer` bigint(20) DEFAULT NULL COMMENT '平台新用户',
  `boss_old_customer` bigint(20) DEFAULT NULL COMMENT '平台老用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2048 DEFAULT CHARSET=utf8mb4 COMMENT='营销概览自然月维度';

CREATE TABLE `s2b_statistics`.`marketing_overview_seven` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID -1：平台',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '订单支付金额',
  `marketing_pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
  `pay_trade_count` bigint(20) DEFAULT NULL COMMENT '营销支付订单数',
  `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
  `boss_pay_customer_count` bigint(20) DEFAULT NULL COMMENT '平台营销支付人数',
  `new_customer` bigint(20) DEFAULT NULL COMMENT '新用户数量',
  `old_customer` bigint(20) DEFAULT NULL COMMENT '老用户数量',
  `boss_new_customer` bigint(20) DEFAULT NULL COMMENT '平台新用户',
  `boss_old_customer` bigint(20) DEFAULT NULL COMMENT '平台老用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2048 DEFAULT CHARSET=utf8mb4 COMMENT='营销概览7天维度';

CREATE TABLE `s2b_statistics`.`marketing_overview_thirty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID -1：平台',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '订单支付金额',
  `marketing_pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
  `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
  `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
  `pay_trade_count` bigint(20) DEFAULT NULL COMMENT '营销支付订单数',
  `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
  `boss_pay_customer_count` bigint(20) DEFAULT NULL COMMENT '平台营销支付人数',
  `new_customer` bigint(20) DEFAULT NULL COMMENT '新用户数量',
  `old_customer` bigint(20) DEFAULT NULL COMMENT '老用户数量',
  `boss_new_customer` bigint(20) DEFAULT NULL COMMENT '平台新用户',
  `boss_old_customer` bigint(20) DEFAULT NULL COMMENT '平台老用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2048 DEFAULT CHARSET=utf8mb4 COMMENT='营销概览30天维度';

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 2, '0 0 3 * * ? ', '营销活动总概览', '2021-02-19 10:09:42', '2021-02-19 10:09:42', '高波', '', 'FIRST', 'marketingOverviewJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-02-19 10:09:42', '');


INSERT INTO `xxl-job`.xxl_job_qrtz_trigger_info (job_group, job_cron, job_desc, add_time, update_time, author, alarm_email, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid) VALUES (2, '0 0 4 * * ? *', '营销概览-活动数据概况统计任务', '2021-02-18 19:53:51', '2021-02-18 19:53:51', '陈莉', '', 'FIRST', 'marketingSituationJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-02-18 19:53:51', '');

update `xxl-job`.xxl_job_qrtz_trigger_info set executor_handler = 'marketingAnalysisJobHandler' where executor_handler = 'appointmentAnalysisJobHandler';

-- 某个场景下会造成数据重复，暂未复现，临时加个唯一索引
create unique index goods_marketing_goods_info_id_customer_id_marketing_id_uindex on `sbc-goods`.`goods_marketing`(goods_info_id, customer_id, marketing_id);