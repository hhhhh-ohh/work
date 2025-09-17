CREATE TABLE `sbc-empower`.`wechat_config` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `app_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `secret` varchar(66) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `scene_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '场景；H5微信设置（0：扫码 ，H5，微信网页-JSAPI），1：小程序（miniJSAPI），2：APP',
                                 `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '商户id (平台默认值-1)',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='支付微信配置';

replace into `sbc-empower`.`wechat_config`(id,app_id,secret,scene_type,store_id) select 1,app_id,secret,0,-1 FROM `sbc-empower`.`pay_gateway_config` where id = 3;
replace into `sbc-empower`.`wechat_config`(id,app_id,secret,scene_type,store_id) select 2,open_platform_app_id,open_platform_secret,2,-1 FROM `sbc-empower`.`pay_gateway_config` where id = 3;
replace into `sbc-empower`.`wechat_config`(id,app_id,secret,scene_type,store_id) select 3,app_id,app_secret,1,-1 FROM `sbc-empower`.`mini_program_set` where type = 0 and del_flag=0;

CREATE TABLE `sbc-order`.`pay_time_series` (
                                   `pay_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付流水号',
                                   `business_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单id',
                                   `apply_price` decimal(10,2) DEFAULT NULL COMMENT '申请价格',
                                   `practical_price` decimal(10,2) DEFAULT NULL COMMENT '实际支付价格',
                                   `status` tinyint(2) DEFAULT NULL COMMENT '状态',
                                   `charge_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                   `channel_item_id` int(11) DEFAULT NULL COMMENT '渠道id',
                                   `client_ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求ip',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
                                   `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
                                   `refund_status` tinyint(2) DEFAULT NULL COMMENT '退款状态',
                                   `pay_channel_type` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付渠道，跟订单支付时候的渠道相对应',
                                   `refund_pay_no` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款流水号',
                                   `trade_no` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '第三方支付返回的支付流水号',
                                   PRIMARY KEY (`pay_no`) USING BTREE,
                                   KEY `index_business_id` (`business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付流水记录';

ALTER TABLE `sbc-order`.`pay_trade_record`
    ADD COLUMN `pay_no` varchar(50) NULL AFTER `trade_type`,
    ADD INDEX `idx_pay_no`(`pay_no`) USING BTREE;