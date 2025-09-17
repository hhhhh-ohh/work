--添加供应商商品上架定时任务
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`( `job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 1, '1 0 0/1 * * ?', '供应商商品上架定时任务', '2021-06-28 09:53:18', '2021-06-28 09:53:18', '徐锋', '', 'FIRST', 'providerGoodsAddedTimingJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-06-28 09:53:18', '');
--秒杀销售记录表
CREATE TABLE `sbc-goods`.`flash_sale_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录主键',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员的主键',
  `goods_info_id` varchar(32) DEFAULT NULL COMMENT '货品主键',
  `purchase_num` bigint(20) DEFAULT NULL COMMENT '购买的数量',
  `flash_goods_id` bigint(20) DEFAULT NULL COMMENT '秒杀商品主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `goods_info_id` (`goods_info_id`),
  KEY `flash_goods_id` (`flash_goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀购买记录表';
--删除秒杀活动结束后商品还库存
delete from `xxl-job`.`xxl_job_qrtz_trigger_info` where executor_handler='flashSaleReturnStockJobHandler';