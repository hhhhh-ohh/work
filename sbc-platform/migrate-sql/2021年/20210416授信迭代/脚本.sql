-- 授信申请记录表
CREATE TABLE `sbc-account`.`customer_apply_record`  (
    `id` varchar(32) NOT NULL COMMENT '申请记录ID',
    `customer_id` varchar(32) NOT NULL  COMMENT '客户主键',
    `apply_notes` varchar(512) NULL COMMENT '申请原因',
    `audit_status` tinyint(4) default 0 COMMENT '审核状态 0待审核 1拒绝 2通过 3变更额度审核',
    `reject_reason` varchar(128) NULL COMMENT '驳回原因',
    `effect_status` tinyint(4) default 0 COMMENT '生效状态 0未生效 1已生效',
    `audit_person` varchar(32) NULL COMMENT '审批人',
    `audit_time` datetime NULL COMMENT '审批时间',
    `create_time` datetime NULL COMMENT '创建时间',
    `create_person` varchar(32) NULL COMMENT '创建人',
    `update_time` datetime NULL COMMENT '更新时间',
    `update_person` varchar(32) NULL COMMENT '更新人',
    `del_flag` tinyint(1) default 0 COMMENT '是否删除标志 0：否，1：是',
    PRIMARY KEY (`id`)
) COMMENT = '授信申请记录表';

-- 授信统计概览表
CREATE TABLE `sbc-account`.`customer_credit_overview`  (
    `id` varchar(32) NOT NULL COMMENT '授信概览ID',
    `total_credit_amount` decimal(18,2) NOT NULL  COMMENT '授信总额',
    `total_customer` int NOT NULL COMMENT '客户数',
    `total_usable_amount` decimal(18,2) NOT NULL COMMENT '可使用总额',
    `total_used_amount` decimal(18,2) NOT NULL COMMENT '已使用总额',
    `total_repay_amount` decimal(18,2) NOT NULL COMMENT '待还款总额',
    `total_repaid_amount` decimal(18,2) NOT NULL COMMENT '已还款总额',
    PRIMARY KEY (`id`)
) COMMENT = '授信统计概览表';

-- 订单关联表
CREATE TABLE `sbc-account`.`customer_credit_order`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键索引',
  `repay_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款单号',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联订单id',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除标志 0：否，1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单关联表' ROW_FORMAT = Compact;

-- 授信账户表
CREATE TABLE `sbc-account`.`customer_credit_account`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户授信信息主键',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户主键',
  `customer_account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户账号',
  `customer_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `apply_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请记录ID',
  `change_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更额度记录ID',
  `credit_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授信记录id',
  `credit_num` int(10) NULL DEFAULT 0 COMMENT '恢复次数',
  `credit_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '授信额度',
  `repay_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '当前周期待还款金额',
  `usable_amount` decimal(18, 2) NULL DEFAULT NULL COMMENT '当前周期剩余额度(可用额度)',
  `used_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '当前周期已使用额度',
  `has_repaid_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '当前周期已还款额度',
  `start_time` datetime NULL DEFAULT NULL COMMENT '授信开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '授信截止时间',
  `used_status` tinyint(4) NULL DEFAULT 0 COMMENT '使用状态 0未使用 1已使用',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用：0未启用 1 已启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除标志 0：否，1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授信账户表' ROW_FORMAT = Compact;

-- 授信记录表
CREATE TABLE `sbc-account`.`customer_credit_record`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授信记录表主键',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员id',
  `credit_amount` decimal(18, 2) NULL DEFAULT NULL COMMENT '授信额度',
  `used_amount` decimal(18, 2) NULL DEFAULT NULL COMMENT '已使用额度',
  `effective_days` tinyint(4) NULL DEFAULT NULL COMMENT '生效天数',
  `start_time` datetime NULL DEFAULT NULL COMMENT '授信开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '授信截止时间',
  `used_status` tinyint(4) NULL DEFAULT 0 COMMENT '启用状态 0未启用 1已启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除标志 0：否，1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授信记录表' ROW_FORMAT = Compact;

-- 授信还款表
CREATE TABLE `sbc-account`.`customer_credit_repay`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
  `credit_record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授信记录id，当前还款记录是还哪一期的账',
  `repay_order_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款单号',
  `repay_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '还款金额',
  `repay_notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '还款说明',
  `repay_status` tinyint(4) NULL DEFAULT 0 COMMENT '还款状态 0还款中 1还款成功 2已作废',
  `repay_type` tinyint(4) NULL DEFAULT 0 COMMENT '还款方式 0银联，1微信，2支付宝',
  `repay_time` datetime NULL DEFAULT NULL COMMENT '还款时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除标志 0：否，1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授信还款表' ROW_FORMAT = Compact;

-- 概览数据初始化
insert into `sbc-account`.`customer_credit_overview` values('OVERVIEW',0,0,0,0,0,0);

-- 新增授信支付方式
INSERT INTO `sbc-pay`.`pay_gateway` VALUES (16, 'CREDIT', '0', '0', now(), -1);

INSERT INTO `sbc-pay`.`pay_gateway_config` VALUES (16, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-02-26 18:40:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -1);

INSERT INTO `sbc-pay`.`pay_channel_item` VALUES (24, '授信支付', 'CREDIT', 16, 'Credit', 1, 0, 'credit_pc', '2021-02-26 18:52:03');
INSERT INTO `sbc-pay`.`pay_channel_item` VALUES (25, '授信H5支付', 'CREDIT', 16, 'Credit', 1, 1, 'credit_h5', '2021-02-26 18:52:49');
INSERT INTO `sbc-pay`.`pay_channel_item` VALUES (26, '授信APP支付', 'CREDIT', 16, 'Credit', 1, 2, 'credit_app', '2021-02-26 18:53:30');

-- 新增支付密码的安全等级
ALTER TABLE `sbc-customer`.`customer`
ADD COLUMN pay_safe_level TINYINT(4) DEFAULT NULL COMMENT '支付密码的安全等级';
--设置老账号已有支付密码初始等级
update `sbc-customer`.`customer` set pay_safe_level = 20 where customer_pay_password is not null and pay_safe_level is null;

-- 修改表字段类型

alter table `sbc-account`.`customer_credit_record` change effective_days effective_days bigint(11)

-- xxl-job
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (127, 1, '0 0 1 * * ?', '授信额度恢复(每日凌晨1点执行)', '2021-04-13 21:34:01', '2021-04-13 21:34:01', '侯帅', '', 'FIRST', 'CreditRecoverJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-04-13 21:34:01', '');

