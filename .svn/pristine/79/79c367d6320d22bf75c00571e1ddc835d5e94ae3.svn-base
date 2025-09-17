--  定时任务
delete from `xxl-job`.`xxl_job_qrtz_trigger_info` where executor_handler in ('autoInsertBorderTradeJobHandler','autoPayPushJobHandler',
'autoElectronicFaceJobHandler','autoLogisticsPushJobHandler','autoPushOrderJobHandler');

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( '1', '0 0 0 1 * ?', '跨境订单清关', '2021-08-19 16:31:41', '2021-08-19 16:31:41', 'wur', '', 'FIRST', 'autoPushCrossJobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2021-08-19 16:31:41', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( '1', '0 0 0 1 * ?', '跨境订单拆单', '2021-08-19 16:31:12', '2021-08-19 16:48:45', 'wur', '', 'FIRST', 'tradeSplitJobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', '2021-08-19 16:31:12', '');


-- 订单拆单失败记录
create table `sbc-order`.`split_trade_error`
(
    `id` varchar(45) NOT NULL comment '主键',
    `trade_id` varchar(32) NOT NULL COMMENT '订单id',
    `split_count` int(11) NOT NULL DEFAULT 0 comment '重试次数',
    `split_status` int(11) NOT NULL DEFAULT 0 comment '拆单状态，0：失败 1：成功',
    `del_flag` tinyint null comment '删除标识,0:未删除1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_trade_id` (`trade_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单拆单失败记录';
