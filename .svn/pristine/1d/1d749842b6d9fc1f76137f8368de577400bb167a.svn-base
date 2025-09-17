-- 更新同步handler
DELETE FROM `xxl-job`.`xxl_job_qrtz_trigger_info` where executor_handler = 'linkedMallOrderPayJobHandler';
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, '0 0/5 * * * ?', '第三方平台订单补偿定时任务', now(), now(), '戴倚天',
        '', 'FIRST', 'thirdPlatformOrderPayJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', now(), '');