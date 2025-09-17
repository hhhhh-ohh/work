-- 商品spu表添加定时下架字段
ALTER TABLE `sbc-goods`.`goods`
    ADD COLUMN `takedown_time` datetime DEFAULT NULL COMMENT '定时下架时间' AFTER `is_independent`,
    ADD COLUMN `takedown_time_flag` tinyint(4) DEFAULT  NULL DEFAULT 0 COMMENT '是否定时下架 0:否1:是' AFTER `takedown_time`;

-- 商品sku表添加定时下架字段
ALTER TABLE `sbc-goods`.`goods_info`
    ADD COLUMN `takedown_time` datetime DEFAULT NULL COMMENT '定时下架时间' AFTER `electronic_coupons_id`,
    ADD COLUMN `takedown_time_flag` tinyint(4) DEFAULT NULL DEFAULT 0 COMMENT '是否定时下架 0:否1:是' AFTER `takedown_time`;

-- 第一次从商品详情和列表加入购物时的价格
ALTER TABLE `sbc-order`.`purchase`
    ADD COLUMN `first_purchase_price` decimal(10, 2) NULL DEFAULT 0 COMMENT '第一次从商品详情和列表加入购物时的价格' AFTER `plugin_type`;

-- 商品审核表添加定时下架字段
ALTER TABLE `sbc-goods`.`goods_audit`
    ADD COLUMN `takedown_time` datetime DEFAULT NULL COMMENT '定时下架时间' AFTER `audit_type`,
    ADD COLUMN `takedown_time_flag` tinyint(4) DEFAULT  NULL DEFAULT 0 COMMENT '是否定时下架 0:否1:是' AFTER `takedown_time`;



-- 订单倒计时开关
INSERT INTO `sbc-setting`.`system_config` (`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES (100, 'order_setting', 'order_setting_countdown', '订单倒计时', NULL, 1, '', '2022-05-26 11:11:18', '2022-06-01 10:19:05', 0);

-- xxl job 定时下架脚本
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`id`, `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (157, 1, '1 0 0/1 * * ?', '商品定时下架任务', '2022-06-10 10:56:05', '2022-06-10 10:56:05', '戚元昭', '', 'FIRST', 'goodsTakedownTimeJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-06-10 10:56:05', '');


alter table `sbc-goods`.`third_goods_cate`
    ADD INDEX `idx_cate_id` (`cate_id`) USING BTREE;

alter table `sbc-goods`.`goods_cate_third_cate_rel`
    ADD INDEX `idx_third_cate_id` (`third_cate_id`) USING BTREE;






