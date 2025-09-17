-- 迁移linkedmall渠道配置
INSERT INTO `sbc-empower`.`channel_config` (`channel_type`, `channel_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`)
SELECT 0, sc.config_name, sc.remark, sc.`status`, sc.context, sc.create_time, sc.update_time, sc.del_flag
FROM `sbc-setting`.`system_config` sc
WHERE sc.`config_type` = 'third_platform_linked_mall';

-- 渠道设置-删除LinkedMall的渠道配置
UPDATE `sbc-setting`.`system_config` SET del_flag = 1 WHERE config_type = 'third_platform_linked_mall';

-- linkedmall地址初始化任务改名字
update `xxl-job`.`xxl_job_qrtz_trigger_info` set executor_handler='linkedMallAddressSyncJobHandler' where executor_handler='thirdAddressSyncJobHandler';

-- LinkedMall地址增量映射定时任务
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`,
                                                   `author`, `alarm_email`, `executor_route_strategy`,
                                                   `executor_handler`, `executor_param`, `executor_block_strategy`,
                                                   `executor_timeout`, `executor_fail_retry_count`, `glue_type`,
                                                   `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, '0 0 1 * * ?', 'LinkedMall地址增量映射', now(), now(), '韩伟', '', 'FIRST',
        'linkedMallAddressMappingJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化',
        now(), '');

-- 全量同步linkedmall商品定时任务
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`,
                                                   `author`, `alarm_email`, `executor_route_strategy`,
                                                   `executor_handler`, `executor_param`, `executor_block_strategy`,
                                                   `executor_timeout`, `executor_fail_retry_count`, `glue_type`,
                                                   `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, '0 0 2 * * ?', '全量同步linkedmall商品', now(), now(), '韩伟', '', 'FIRST',
        'LinkedMallSyncGoodsJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', now(),
        '');
