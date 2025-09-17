-- Supplier: 商品-商品管理-商品列表-商品编辑
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399e17b8af4da017b8b5298420004', 3, 'fc924c7c3fe311e9828800163e0fc468', '是否是即将进行或是正在进行的抢购活动', NULL, '/flashsalegoods/list', 'POST', NULL, 28, '2021-08-28 13:54:11', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399e17b8af4da017b8b5242310003', 3, 'fc924c7c3fe311e9828800163e0fc468', '根据三级类目id查询商品属性', NULL, '/goods/prop-detail/**', 'GET', NULL, 27, '2021-08-28 13:53:49', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399e17b8af4da017b8b4d29980002', 3, 'fc924c7c3fe311e9828800163e0fc468', '查询所有运费模板', NULL, '/freightTemplate/freightTemplateGoods', 'GET', NULL, 26, '2021-08-28 13:48:15', 0);

-- Boss: 商品-商家商品-商品列表-商品详情查看
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399e17b8af4da017b8b2226fe0001', 4, 'fc936bd13fe311e9828800163e0fc468', '根据三级类目id查询商品属性', NULL, '/goods/prop/detail/**', 'GET', NULL, 9, '2021-08-28 13:01:16', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399e17b8af4da017b8b21cb4d0000', 4, 'fc936bd13fe311e9828800163e0fc468', '查询国家/地区列表', NULL, '/country/list-all', 'GET', NULL, 8, '2021-08-28 13:00:53', 0);


-- 分销临时表迁移
DROP TABLE IF EXISTS `sbc-bff`.`distribution_task_temp`;

CREATE TABLE `sbc-order`.`distribution_task_temp` (
  `id` varchar(32) NOT NULL,
  `customer_id` varchar(32) NOT NULL COMMENT '购买人id',
  `distribution_customer_id` varchar(32) DEFAULT NULL COMMENT '推荐分销员id',
  `first_valid_buy` tinyint(2) NOT NULL COMMENT '第一次有效完成订单',
  `order_id` varchar(32) NOT NULL COMMENT '订单id',
  `order_disable_time` datetime DEFAULT NULL COMMENT '订单可退时间',
  `distribution_order` tinyint(2) DEFAULT NULL COMMENT '分销订单',
  `return_order_num` tinyint(4) DEFAULT NULL COMMENT '退单中的数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销任务临时表';

ALTER TABLE `sbc-message`.`message_send`
ADD COLUMN `status` tinyint(4) NULL COMMENT '状态：0-未开始，1-进行中，2-已结束，3-任务失败' AFTER `send_time`;

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 */5 * * * ?', '站内信发送任务', '2021-08-23 18:57:10', '2021-08-23 18:57:10', '吕振伟', '', 'FIRST', 'MessageSendJobHandle', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-08-23 18:57:10', '');
