-- 系统配置表新增
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('flash_goods_sale', 'flash_promotion_original_price', '限时抢购库存抢完后是否允许原价购买', '判断库存抢完后是否允许原价购买', 0, NULL, NULL, NULL, 0);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('flash_goods_sale', 'flash_sale_original_price', '秒杀活动库存抢完后是否允许原价购买', '判断库存抢完后是否允许原价购买', 0, NULL, NULL, NULL, 0);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('platform_address', 'popular_city', '热门城市', '平台热门城市', NULL, '[{\"cityName\":\"北京市\",\"sort\":1},{\"cityName\":\"上海市\",\"sort\":2},{\"cityName\":\"广州市\",\"sort\":3},{\"cityName\":\"深圳市\",\"sort\":4},{\"cityName\":\"杭州市\",\"sort\":5},{\"cityName\":\"南京市\",\"sort\":6},{\"cityName\":\"苏州市\",\"sort\":7},{\"cityName\":\"天津市\",\"sort\":8},{\"cityName\":\"武汉市\",\"sort\":9},{\"cityName\":\"长沙市\",\"sort\":10},{\"cityName\":\"重庆市\",\"sort\":11},{\"cityName\":\"成都市\",\"sort\":12}]', '2023-04-23 17:26:35', '2023-04-23 17:26:35', 0);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('pickup_setting', 'whether_open_map', '高德地图', '是否开启高德地图', 1, NULL, '2023-04-24 15:10:25', '2023-04-24 15:10:25', 0);
UPDATE `sbc-setting`.`system_config` SET `context` = '{\"minute\":10}' WHERE `config_type` = 'order_setting_timeout_cancel';
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('flash_goods_sale', 'flash_promotion_order_auto_cancel', '限时抢购订单自动取消设置', NULL, 1, '10', '2023-04-17 14:58:26', '2023-04-21 16:34:34', 0);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('flash_goods_sale', 'flash_sale_order_auto_cancel', '秒杀订单自动取消设置', NULL, 1, '10', '2023-04-17 14:58:26', '2023-04-21 16:33:58', 0);

CREATE TABLE `sbc-order`.`pickup_code_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `pickup_code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '自提码',
  `trade_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单id',
  `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `pickup_code_index` (`pickup_code`) USING BTREE COMMENT '自提码索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='自提码记录表';

INSERT INTO `xxl-job`.`xxl_job_info`(`job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`) VALUES (1, '清除自提订单提货码记录', '2023-04-19 17:08:32', '2023-04-19 17:10:24', '吕振伟', '', 'CRON', '0 0 1 1 * ?', 'DO_NOTHING', 'FIRST', 'deleteExpirePickupCodeRecordJobHandle', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2023-04-19 17:08:32', '', 0, 0, 0);

ALTER TABLE `sbc-setting`.`system_resource`
ADD COLUMN `sort` int(11) NULL DEFAULT 0 AFTER `server_type`;

update `sbc-setting`.base_config set version = 'SBC V5.4.0';

-- 合并技术债务脚本
ALTER TABLE `sbc-account`.`lakala_settlement`
    ADD COLUMN `supplier_store_id` bigint(20) NULL DEFAULT NULL COMMENT '代销的商家id' AFTER `gift_card_price`;
ALTER TABLE `sbc-account`.`lakala_settlement`
    ADD INDEX `idx_supplier_store_id`(`supplier_store_id`) USING BTREE;
