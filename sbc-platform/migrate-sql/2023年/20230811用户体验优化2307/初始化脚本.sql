ALTER TABLE `sbc-empower`.`customer_service_setting`
ADD COLUMN `service_type` tinyint(4) NULL DEFAULT 1 COMMENT '在线客服类型 0 平台， 1 商家' AFTER `group_status`;

CREATE TABLE `sbc-order`.`wx_pay_upload_shipping_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pay_type` tinyint(3) DEFAULT NULL COMMENT '支付类型，0：商品订单支付；1：授信还款',
  `business_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付订单id',
  `transaction_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付流水id',
  `mch_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户号',
  `result_context` text CHARACTER SET utf8 COMMENT '接口调用返回结果内容',
  `result_status` tinyint(3) DEFAULT NULL COMMENT '结果状态，0：待处理；1：处理成功；2：处理失败',
  `error_num` tinyint(3) DEFAULT NULL COMMENT '处理失败次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `bussness_id_uni_index` (`business_id`) USING BTREE COMMENT '订单编号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信小程序支付发货信息表';

update `sbc-setting`.base_config set version = 'SBC V5.7.0';
