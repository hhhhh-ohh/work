-- 会员等级索引
ALTER TABLE `sbc-customer`.`customer_level`
ADD INDEX `idx_del_flag`(`del_flag`),
ADD INDEX `idx_create_time`(`create_time`);

-- 预售
ALTER TABLE `sbc-goods`.`booking_sale`
ADD INDEX `idx_hand_sel_start_time`(`hand_sel_start_time`),
ADD INDEX `idx_join_level`(`join_level`),
ADD INDEX `idx_del_flag`(`del_flag`),
ADD INDEX `idx_deliver_time`(`deliver_time`),
ADD INDEX `idx_pause_flag`(`pause_flag`),
ADD INDEX `idx_create_time`(`create_time`),
ADD INDEX `idx_hand_sel_end_time`(`hand_sel_end_time`),
ADD INDEX `idx_tail_start_time`(`tail_start_time`),
ADD INDEX `idx_tail_end_time`(`tail_end_time`),
ADD INDEX `idx_booking_start_time`(`booking_start_time`),
ADD INDEX `idx_booking_end_time`(`booking_end_time`),
ADD INDEX `idx_start_time`(`start_time`),
ADD INDEX `idx_end_time`(`end_time`),
ADD INDEX `idx_join_level_type`(`join_level_type`);

-- 预约
ALTER TABLE `sbc-goods`.`appointment_sale`
ADD INDEX `idx_appointment_start_time`(`appointment_start_time`),
ADD INDEX `idx_appointment_end_time`(`appointment_end_time`),
ADD INDEX `idx_snap_up_start_time`(`snap_up_start_time`),
ADD INDEX `idx_snap_up_end_time`(`snap_up_end_time`),
ADD INDEX `idx_deliver_time`(`deliver_time`),
ADD INDEX `idx_join_level`(`join_level`),
ADD INDEX `idx_join_level_type`(`join_level_type`),
ADD INDEX `idx_del_flag`(`del_flag`),
ADD INDEX `idx_pause_flag`(`pause_flag`),
ADD INDEX `idx_create_time`(`create_time`);

-- 会员提现
ALTER TABLE `sbc-account`.`customer_draw_cash`
ADD INDEX `idx_draw_cash_status`(`draw_cash_status`),
ADD INDEX `idx_audit_status`(`audit_status`),
ADD INDEX `idx_customer_operate_status`(`customer_operate_status`),
ADD INDEX `idx_finish_status`(`finish_status`),
ADD INDEX `idx_del_flag`(`del_flag`),
ADD INDEX `idx_count`(`draw_cash_status`, `audit_status`, `customer_operate_status`, `finish_status`, `del_flag`),
ADD INDEX `idx_apply_time`(`apply_time`);

-- 修改字段长度
ALTER TABLE `seata`.`lock_table`
MODIFY COLUMN `table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `resource_id`;

-- seata升级
ALTER TABLE `sbc-account`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-bff`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-crm`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-customer`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-goods`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-marketing`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-message`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-order`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-pay`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-saas`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;
ALTER TABLE `sbc-setting`.`undo_log` DROP COLUMN `id`, DROP PRIMARY KEY;

-- 去除联合主键
drop table `sbc-goods`.store_cate_goods_rela_copy;

rename table `sbc-goods`.store_cate_goods_rela to `sbc-goods`.store_cate_goods_rela_copy;

drop table `sbc-goods`.store_cate_goods_rela;

CREATE TABLE `sbc-goods`.`store_cate_goods_rela` (
  `id` varchar(32) NOT NULL,
  `goods_id` varchar(32) NOT NULL COMMENT 'SPU编号',
  `store_cate_id` bigint(20) NOT NULL COMMENT '店铺分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP PROCEDURE
IF
  EXISTS `sbc-goods`.`p_migrate_store_cate_goods_rela`;
CREATE PROCEDURE `sbc-goods`.`p_migrate_store_cate_goods_rela`()
BEGIN
  DECLARE
    goodsId VARCHAR ( 32 );
  DECLARE
    storeCateId BIGINT ( 20 );
  DECLARE s int DEFAULT 0;
  DECLARE cur CURSOR FOR
    SELECT
      goods_id AS goodsId,
      store_cate_id AS storeCateId
    FROM
      `sbc-goods`.`store_cate_goods_rela_copy` ;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET s = 1;
  OPEN cur;
  FETCH cur INTO goodsId,storeCateId;
  WHILE
      s <> 1 DO
      INSERT INTO `sbc-goods`.`store_cate_goods_rela` ( `id`, `goods_id`, `store_cate_id` )
    VALUES
      ( REPLACE ( UUID(), '-', '' ), goodsId, storeCateId );
    FETCH cur INTO goodsId,storeCateId;
  END WHILE;
  CLOSE cur;
END;

call `sbc-goods`.`p_migrate_store_cate_goods_rela`();


-- 去除联合主键
drop table `sbc-goods`.goods_tab_rela_copy;

rename table `sbc-goods`.goods_tab_rela to `sbc-goods`.goods_tab_rela_copy;

drop table `sbc-goods`.goods_tab_rela;

CREATE TABLE `sbc-goods`.`goods_tab_rela` (
  `id` varchar(32) NOT NULL,
  `goods_id` varchar(32) NOT NULL COMMENT 'spuId',
  `tab_id` bigint(20) NOT NULL COMMENT '详情模板id',
  `tab_detail` text COMMENT '内容详情',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情模板关联表';


DROP PROCEDURE
IF
  EXISTS `sbc-goods`.`p_goods_tab_rela`;
CREATE PROCEDURE `sbc-goods`.`p_goods_tab_rela`()
BEGIN
  DECLARE
    goodsId VARCHAR ( 32 );
  DECLARE
    tabId BIGINT ( 20 );
  DECLARE
    tabDetail text;
  DECLARE s int DEFAULT 0;
  DECLARE cur CURSOR FOR
    SELECT
      goods_id AS goodsId,
      tab_id AS tabId ,
      tab_detail as tabDetail
    FROM
      `sbc-goods`.`goods_tab_rela_copy` ;
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET s = 1;
  OPEN cur;
  FETCH cur INTO goodsId,tabId,tabDetail;
  WHILE
      s <> 1 DO
      INSERT INTO `sbc-goods`.`goods_tab_rela`(`id`, `goods_id`, `tab_id`, `tab_detail`) VALUES (REPLACE ( UUID(), '-', '' ), goodsId, tabId, tabDetail);

    FETCH cur INTO goodsId,tabId,tabDetail;
  END WHILE;
  CLOSE cur;
END;


call `sbc-goods`.`p_goods_tab_rela`();

drop TABLE `sbc-order`.`refund_call_back_result`;
CREATE TABLE `sbc-order`.`refund_call_back_result` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `out_trade_no` varchar(45) NOT NULL COMMENT '订单号',
  `result_xml` text NOT NULL COMMENT '回调结果xml内容',
  `result_context` text NOT NULL COMMENT '回调结果内容：解密后',
  `result_status` tinyint(3) NOT NULL COMMENT '结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败',
  `error_num` tinyint(3) NOT NULL DEFAULT '0' COMMENT '处理失败次数',
  `pay_type` tinyint(3) NOT NULL COMMENT '退款方式，0：微信；1：支付宝；',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='退款回调结果表';


INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 */1 * * * ?', '退款回调补偿--处理中（默认30分钟之前）', '2021-01-20 10:16:52', '2021-01-20 10:16:52', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '1', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:16:52', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '*/1 * * * * ?', '退款回调补偿--未处理和失败补偿', '2021-01-20 10:15:59', '2021-01-20 10:15:59', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:15:59', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 0 1 * * ?', '退款回调补偿--处理中（指定时间，默认不启动）', '2021-01-20 10:15:00', '2021-01-20 10:15:00', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '1&2020-08-01 10:10:00', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:15:00', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 0 1 * * ?', '退款回调补偿--指定退单补偿（默认不启动）', '2021-01-20 10:13:45', '2021-01-20 10:13:45', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '4&R201708151827465952', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:13:45', '');


-- 修改积分商品表中的goods_info_id和goods_id 字符集，
-- 否则和goods表与goods_info表关联查询导致索引失效，查询积分商品超时
ALTER TABLE `sbc-goods`.`points_goods`
MODIFY COLUMN `goods_info_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'SkuId' AFTER `goods_id`;
ALTER TABLE `sbc-goods`.`points_goods`
MODIFY COLUMN `goods_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'SpuId' AFTER `points_goods_id`;

-- 拼团商品表的goods_info_id和 goods_info表字符集不同导致索引失效
ALTER TABLE `sbc-goods`.`groupon_goods_info`
MODIFY COLUMN `goods_info_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'SKU编号' AFTER `groupon_goods_id`;

-- 分校素材关联goods_info关联字段字符集统一
ALTER TABLE `sbc-goods`.`distribution_goods_matter`
MODIFY COLUMN `goods_info_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku的id' AFTER `id`;

ALTER TABLE `sbc-goods`.`flash_sale_goods`
MODIFY COLUMN `goods_info_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'skuID' AFTER `activity_time`,
MODIFY COLUMN `goods_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'spuID' AFTER `goods_info_id`;

-- 修改支付退款成功后回调表字段
ALTER TABLE `sbc-order`.`refund_call_back_result` CHANGE  out_trade_no business_id VARCHAR(45);

-- 商品分类关联表添加索引
ALTER TABLE `sbc-goods`.`store_cate_goods_rela`
ADD INDEX `idx_store_cate_goods_id`(`goods_id`) USING BTREE,
ADD INDEX `idx_store_cate_id`(`store_cate_id`) USING BTREE;

-- 增加分销添加时间
ALTER TABLE `sbc-goods`.`goods_info`
ADD COLUMN `distribution_create_time` datetime NULL COMMENT '分销添加时间' AFTER `distribution_goods_audit_reason`;

-- 修改linkedmall的key值
update `sbc-setting`.`system_config` set config_key = 'value_added_services' where config_key = 'third_platform_setting';

-- 查询收款单新增接口添加权限
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399bf77ce57ac0177cefad50b0000', 4, 'fc9260a23fe311e9828800163e0fc468', '查询收款单列表', NULL, '/account/payOrdersFromES', 'POST', NULL, 3, '2021-02-23 21:01:21', 0);


INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (110, 1, '0 */1 * * * ?', '退款回调补偿--处理中（默认30分钟之前）', '2021-01-20 10:16:52', '2021-01-20 10:16:52', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '1', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:16:52', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (109, 1, '*/1 * * * * ?', '退款回调补偿--未处理和失败补偿', '2021-01-20 10:15:59', '2021-01-20 10:15:59', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:15:59', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (108, 1, '0 0 1 * * ?', '退款回调补偿--处理中（指定时间，默认不启动）', '2021-01-20 10:15:00', '2021-01-20 10:15:00', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '1&2020-08-01 10:10:00', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:15:00', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (107, 1, '0 0 1 * * ?', '退款回调补偿--指定退单补偿（默认不启动）', '2021-01-20 10:13:45', '2021-01-20 10:13:45', '王刚', '', 'FIRST', 'RefundCallBackJobHandler', '4&R201708151827465952', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-20 10:13:45', '');


ALTER TABLE `sbc-goods`.`flash_sale_goods`
ADD INDEX `idx_activity_full_time`(`activity_full_time`),
ADD INDEX `idx_del_flag`(`del_flag`);