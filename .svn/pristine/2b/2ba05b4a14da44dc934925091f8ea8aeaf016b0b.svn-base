-- 自提配置表
CREATE TABLE `sbc-setting`.`pickup_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `name` varchar(128) DEFAULT NULL COMMENT '自提点名称',
  `area_code` varchar(20) DEFAULT NULL COMMENT '自提点区号',
  `phone` varchar(20) DEFAULT NULL COMMENT '自提点联系电话',
  `province_id` bigint(10) DEFAULT NULL COMMENT '省份',
  `city_id` bigint(10) DEFAULT NULL COMMENT '市',
  `area_id` bigint(10) DEFAULT NULL COMMENT '区',
  `street_id` bigint(10) DEFAULT NULL COMMENT '街道',
  `pickup_address` varchar(200) DEFAULT NULL COMMENT '详细街道地址',
  `is_default_address` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是默认地址',
  `remark` varchar(200) DEFAULT NULL COMMENT '自提时间说明',
  `image_url` text COMMENT '自提点照片',
  `longitude` decimal(20,10) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(20,10) DEFAULT NULL COMMENT '纬度',
  `audit_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态,0:未审核1 审核通过2审核失败',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '驳回理由',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_person` varchar(32) DEFAULT NULL COMMENT '审核人',
  `enable_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否启用 1:启用 0:停用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='自提配置表';
-- 自提员工关系表
CREATE TABLE `sbc-setting`.`pickup_employee_rela` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `employee_id` varchar(50) DEFAULT NULL COMMENT '员工id',
  `pickup_id` bigint(20) DEFAULT NULL COMMENT '自提点id',
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`) USING BTREE,
  KEY `pickup_id` (`pickup_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自提员工关系表';

ALTER TABLE `sbc-customer`.`store`
ADD COLUMN `pickup_state` tinyint(4) NULL DEFAULT 0 COMMENT '自提开关 0 关 1 开' AFTER `supplier_type`;

-- 登录错误次数清0 初始化脚本
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 0 0 * * ?', '登录错误次数清0', '2021-09-22 14:27:21', '2021-09-22 14:27:21', '徐锋', '', 'FIRST', 'loginErrorClear0JobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-09-22 14:27:21', '');

-- 商家开启自提功能设置
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('pickup_setting', 'selfMerchant', '自营商家订单自提设置', NULL, 0, NULL, '2021-09-07 10:40:10', '2021-09-30 13:39:52', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('pickup_setting', 'thirdMerchant', '第三方商家订单自提设置', NULL, 0, NULL, '2021-09-07 10:41:28', '2021-09-30 11:43:18', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('pickup_setting', 'store', '门店订单自提设置', NULL, 0, NULL, '2021-09-07 10:42:21', '2021-09-30 11:43:19', 0);

-- 移动端商品字段展示设置
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'price', '价格', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-18 16:52:26', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'subtitle', '副标题', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-18 16:52:28', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'spec', '规格值', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-16 15:36:55', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'self_label', '自营标签', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-18 10:27:48', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'marketing_label', '营销标签', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-16 15:37:17', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'goods_label', '商品标签', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-16 15:37:18', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'sales_volume', '销量', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-16 15:39:15', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'comments_num', '评论数', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-16 15:39:16', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'praise_probability', '好评率', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-18 16:52:31', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'belonging_store', '所属店铺', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-18 16:52:30', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('goods_column_show', 'shopping_cart', '购物车', NULL, 1, NULL, '2021-09-16 10:42:36', '2021-09-18 16:52:29', 0);

