
ALTER TABLE `sbc-goods`.`goods_info`
    ADD INDEX `idx_electronic_coupons_id`(`electronic_coupons_id`) USING BTREE;

-- 供货价
ALTER TABLE `s2b_statistics`.`replay_trade_item`
    ADD COLUMN `supply_price` decimal(10, 2) NULL DEFAULT 0 COMMENT '供货价' AFTER `cate_top_id`;

ALTER TABLE `s2b_statistics`.`coupon_info_effect_recent`
    ADD COLUMN `supply_price` decimal(10, 2) NULL DEFAULT 0 COMMENT '供货价' AFTER `stat_type`;
ALTER TABLE `s2b_statistics`.`coupon_activity_effect_recent`
    ADD COLUMN `supply_price` decimal(10, 2) NULL DEFAULT 0 COMMENT '供货价' AFTER `stat_type`;
ALTER TABLE `s2b_statistics`.`coupon_store_effect_recent`
    ADD COLUMN `supply_price` decimal(10, 2) NULL DEFAULT 0 COMMENT '供货价' AFTER `stat_type`;

-- xxl job 供应商定时下架脚本
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (185, 1, '1 0 0/1 * * ?', '供应商商品下架定时任务', '2022-06-10 10:56:05', '2022-06-10 10:56:05', '戚元昭', '', 'FIRST', 'providerGoodsTakedownTimingJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-06-10 10:56:05', '');

-- 版权设置
ALTER TABLE `sbc-setting`.base_config ADD COLUMN `copyright` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版权信息';

CREATE TABLE `sbc-setting`.`store_message_node` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `menu_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属菜单名称',
    `type_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点类型名称',
    `push_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推送节点名称',
    `function_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '功能标识，用于鉴权',
    `node_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点标识',
    `node_context` text COLLATE utf8mb4_unicode_ci COMMENT '节点通知内容模板',
    `sort` int(11) DEFAULT NULL COMMENT '排序字段',
    `platform_type` tinyint(4) DEFAULT NULL COMMENT '平台类型 0:平台 1:商家 2:供应商',
    `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标志 0:未删除 1:删除',
    `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_funcation_name` (`function_name`) USING BTREE COMMENT '功能标识索引'
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家消息节点表';

CREATE TABLE `sbc-setting`.`store_message_node_setting` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id` bigint(20) DEFAULT NULL COMMENT '商家id',
    `node_id` bigint(20) DEFAULT NULL COMMENT '消息节点id',
    `status` tinyint(4) DEFAULT NULL COMMENT '启用状态 0:未启用 1:启用',
    `node_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息节点标识',
    `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标志 0:未删除 1:删除',
    `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_store_id` (`store_id`) USING BTREE COMMENT '商家id索引',
    KEY `idx_node_id` (`node_id`) USING BTREE COMMENT '节点id索引',
    KEY `idx_node_code` (`node_code`(191)) USING BTREE COMMENT '节点标识索引'
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家消息节点设置表';


CREATE TABLE `sbc-message`.`store_notice_send` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `title` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
    `content` text COLLATE utf8mb4_unicode_ci COMMENT '公告内容',
    `receive_scope` tinyint(4) DEFAULT NULL COMMENT '接收范围 0：全部 1：商家 2：供应商',
    `supplier_scope` tinyint(4) DEFAULT NULL COMMENT '商家范围 0：全部 1：自定义商家',
    `provider_scope` tinyint(4) DEFAULT NULL COMMENT '供应商范围 0：全部 1：自定义供应商',
    `send_time_type` tinyint(4) DEFAULT NULL COMMENT '推送时间类型 0：立即、1：定时',
    `send_time` datetime DEFAULT NULL COMMENT '发送时间',
    `send_status` tinyint(4) DEFAULT NULL COMMENT '公告发送状态 0：未发送 1：发送中 2：已发送 3：发送失败 4：已撤回',
    `scan_flag` tinyint(4) DEFAULT '0' COMMENT '定时任务扫描标识 0：未扫描 1：已扫描',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除 1：删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家公告发送表';

CREATE TABLE `sbc-message`.`store_notice_scope` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `notice_id` bigint(20) DEFAULT NULL COMMENT '公告id',
    `scope_cate` tinyint(4) DEFAULT NULL COMMENT '范围分类 1：商家 2：供应商',
    `scope_type` tinyint(4) DEFAULT NULL COMMENT '范围类型 1：自定义',
    `scope_id` bigint(20) DEFAULT NULL COMMENT '目标id',
    PRIMARY KEY (`id`),
    KEY `idx_notice_id` (`notice_id`) USING BTREE COMMENT '公告id索引'
) ENGINE=InnoDB AUTO_INCREMENT=886 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家公告发送范围表';


-- 分表 0 - 4，先创建store_message_detail_0，剩下的1-4由后面的存储过程生成
CREATE TABLE `sbc-message`.`store_message_detail_0` (
    `id` varchar(32) NOT NULL COMMENT '主键id',
    `message_type` tinyint(4) DEFAULT NULL COMMENT '消息一级类型 0：消息 1：公告',
    `store_id` bigint(20) DEFAULT NULL COMMENT '商家id',
    `title` varchar(255) DEFAULT NULL COMMENT '消息标题',
    `content` varchar(255) DEFAULT NULL COMMENT '消息内容',
    `route_param` varchar(255) DEFAULT NULL COMMENT '路由参数，json格式',
    `send_time` datetime DEFAULT NULL COMMENT '发送时间',
    `is_read` tinyint(4) DEFAULT NULL COMMENT '是否已读 0：未读 1：已读',
    `join_id` bigint(20) DEFAULT NULL COMMENT '关联的消息节点id或公告id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
    `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识 0：未删除 1：删除',
    PRIMARY KEY (`id`),
    KEY `idx_store_id` (`store_id`) USING BTREE COMMENT '商家id索引',
    KEY `idx_join_id` (`join_id`) USING BTREE COMMENT '消息节点id或公告id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家消息/公告详情表';

-- 存储过程，动态创建商家消息发送分表1-9
use `sbc-message`;
drop procedure if exists  pro_store_message_sub_table;

delimiter $
create procedure pro_store_message_sub_table(tableCount  int)
begin
  declare i int;
  DECLARE table_name VARCHAR(50);
  DECLARE table_pre VARCHAR(50);
  DECLARE sql_text VARCHAR(2048);
  set i = 1;
  SET table_pre = '`sbc-message`.`store_message_detail_';
  while i <= tableCount  do
    SET @table_name = CONCAT(table_pre,CONCAT(i, '`'));
    SET sql_text = CONCAT('CREATE TABLE ', @table_name, ' like `sbc-message`.`store_message_detail_0`');
    SET @sql_text=sql_text;
PREPARE stmt FROM @sql_text;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
set i = i + 1;
end while;
end $

call pro_store_message_sub_table(4);
-- 存储过程用完删除
drop procedure if exists  pro_store_message_sub_table;

-- 初始化消息节点
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (1, '商家', '商家待审核通知', '新商家入驻或商家修改信息提交后', 'f_supplier_detail_1', 'SUPPLIER_WAIT_AUDIT', '有一条商家信息待审核，编号：{编号}，点击去审核', 1, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (2, '商家', '供应商待审核通知', '新供应商入驻或供应商修改信息提交后', 'f_provider_detail_1', 'PROVIDER_WAIT_AUDIT', '有一条供应商信息待审核，编号：{编号}，点击去审核', 1, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (3, '商品', '商家商品待审核通知', '商家新增商品或修改原有商品信息提交后', 'f_goods_detail_2', 'SUPPLIER_GOODS_WAIT_AUDIT', '有一个商家商品待审核，SPU编码：{编码}，点击去审核', 2, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (4, '商品', '供应商商品待审核通知', '供应商新增商品或修改原有商品信息提交后', 'f_goods_detail_2_provider', 'PROVIDER_GOODS_WAIT_AUDIT', '有一个供应商商品待审核，SPU编码：{编码}，点击去审核', 2, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (5, '订单', '待退款订单提醒', '商家同意退款并填写退款金额后', 'rolf001', 'TRADE_WAIT_REFUND', '有一条退款订单待退款，退单编号：{编号}，点击去审核', 3, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (6, '订单', '退款失败提醒', '同意退款后支付平台返回退款失败', 'rolf001', 'REFUND_FAIL', '有一条退款订单退款失败，退单编号：{编号}，点击去查看', 3, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (7, '客户', '待审核客户提醒', '客户注册商城账号提交后', 'f_customer_0', 'CUSTOMER_WAIT_AUDIT', '新增客户注册待审核，账号：{账号}，点击去审核', 4, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (8, '客户', '待审核企业会员提醒', '客户注册企业会员账号提交后', 'f_enterprise_customer_list', 'ENTERPRISE_CUSTOMER_WAIT_AUDIT', '新增企业会员注册待审核，账号：{账号}，点击去审核', 4, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (9, '客户', '客户注销提醒', '客户提交注销申请后', 'f_customer_log_out', 'CUSTOMER_LOGOUT', '客户{手机号}已注销，点击去查看', 4, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (10, '客户', '客户升级提醒', '客户提升等级后', 'f_customer_0', 'CUSTOMER_UPGRADE', '客户{账号}等级提升至{级别}，点击去查看', 4, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (11, '财务', '供应商待结算单生成提醒', '按照供应商结算周期生成结算单后', 'm_finance_manage_provider_settle_1', 'PROVIDER_SETTLEMENT_PRODUCE', '有新的供应商待结算订单生成，结算单号：{结算单号}，点击去结算', 5, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (12, '财务', '商家待结算单生成提醒', '按照商家结算周期生成结算单后', 'm_finance_manage_settle_1', 'SUPPLIER_SETTLEMENT_PRODUCE', '有新的商家待结算订单生成，结算单号：{结算单号}，点击去结算', 5, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (13, '财务', '拉卡拉结算单结算提醒', '拉卡拉结算单自动结算后', NULL, 'LAKALA_SETTLEMENT_SETTLE', '拉卡拉结算单结算{成功/失败/部分成功}，结算单号：{单号}，点击去查看', 5, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (14, '财务', '会员提现审核提醒', '客户发起提现申请后', '0', 'CUSTOMER_WITHDRAW_WAIT_AUDIT', '客户{账号}发起了提现申请，点击去审核', 5, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (15, '财务', '授信账户审核提醒', '客户申请授信账户提交后', 'f_credit_audit_list', 'CREDIT_ACCOUNT_WAIT_AUDIT', '客户{账号}提交了授信账户申请，点击去审核', 5, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (16, '财务', '授信还款成功通知', '客户授信还款成功后', 'f_credit_repay_list', 'CREDIT_REPAYMENT_SUCCESS', '客户{账号}发起了授信还款，点击去查看', 5, 0, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (17, '商品', '商品审核结果提醒', '修改商品提交审核后，平台的审核结果', 'f_goods_detail_1', 'GOODS_AUDIT_RESULT', '{商品名称}{商品编码}，审核{成功/失败}，点击去查看', 1, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (18, '商品', '代销商品变更提醒', '供应商修改商品信息且商家代销了此商品后', 'f_consignment_goods_entity_list', 'COMMISSION_GOODS_CHANGE', '代销的{供应商名称}供应商的{商品名称}{商品编码}，{基础信息已变更}，点击去查看', 1, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (19, '订单', '待审核订单提醒', '客户下单后', 'fOrderList001', 'TRADE_WAIT_AUDIT', '有一条订单待审核，订单编号：{编号}，点击去审核', 2, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (20, '订单', '待发货订单提醒', '客户下单付款后，需要商家发货', 'fOrderList001', 'TRADE_WAIT_DELIVER', '有一条订单待发货，订单编号：{编号}，点击去发货', 2, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (21, '订单', '待审核退单提醒', '客户提交退单后，需商家审核才可通过', 'rolf001', 'RETURN_ORDER_WAIT_AUDIT', '有一条退单待审核，退单编号：{编号}，点击去审核', 2, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (22, '订单', '待商家收货提醒', '客户提交退单填写物流信息后', 'rolf001', 'SUPPLIER_WAIT_RECEIVE', '有一条退单商品用户已寄出，退单编号：{编号}，点击去查看', 2, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (23, '客户', '客户注销提醒', '客户提交注销申请后', 'f_customer_log_out', 'CUSTOMER_LOGOUT', '客户{账号}已注销，点击去查看', 3, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (24, '客户', '客户升级提醒', '客户提升等级后', 'f_customer_0', 'CUSTOMER_UPGRADE', '客户{账号}等级提升至{等级}，点击去查看', 3, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (25, '财务', '待结算单结算提醒', '结算单结算成功后', 'f_finance_manage_settle', 'SETTLEMENT_SETTLED', '有新的待结算订单已结算，结算单号：{单号}，点击去查看', 4, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (26, '财务', '拉卡拉结算单结算提醒', '拉卡拉结算单自动结算后', NULL, 'LAKALA_SETTLEMENT_SETTLE', '拉卡拉结算单结算{成功/失败/部分成功}，结算单号：{单号}，点击去查看', 4, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (27, '商品', '商品审核结果通知', '修改商品提交审核后，平台的审核结果', 'f_goods_detail_1', 'GOODS_AUDIT_RESULT', '{商品名称}{商品编码}，审核{成功/失败}，点击去查看', 1, 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (28, '订单', '待发货订单提醒', '客户下单付款后，需要商家发货', 'fOrderList001', 'TRADE_WAIT_DELIVER', '有一条订单待发货，订单编号：{编号}，点击去发货', 2, 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (30, '订单', '待供应商收货提醒', '客户提交退单填写物流信息后', 'rolf001', 'PROVIDER_WAIT_RECEIVE', '有一条退单商品用户已寄出，退单编号：{编号}，点击去查看', 2, 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (31, '财务', '待结算单结算提醒', '结算单结算成功后', 'f_finance_manage_settle', 'SETTLEMENT_SETTLED', '有新的待结算订单已结算，结算单号：{单号}，点击去查看', 3, 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`store_message_node` (`id`, `menu_name`, `type_name`, `push_name`, `function_name`, `node_code`, `node_context`, `sort`, `platform_type`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES (32, '财务', '拉卡拉结算单结算提醒', '拉卡拉结算单自动结算后', NULL, 'LAKALA_SETTLEMENT_SETTLE', '拉卡拉结算单结算{成功/失败/部分成功}，结算单号：{单号}，点击去查看', 3, 2, 0, NULL, NULL, NULL, NULL);

