INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('s2b_audit', 'gift_card_make_card_audit', '礼品卡制卡审核', '开启后，操作礼品卡批量制卡时，需要审核才可制卡', 0, NULL, '2022-12-09 09:05:37', '2022-12-09 09:05:37', 0);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('s2b_audit', 'gift_card_send_card_audit', '礼品卡发卡审核', '开启后，操作礼品卡批量发卡时，需要审核才可发卡', 0, NULL, '2022-12-09 09:05:37', '2022-12-09 09:05:37', 0);

ALTER TABLE `sbc-order`.`pay_order`
    ADD COLUMN `gift_card_price` decimal(20,2) NULL DEFAULT 0 COMMENT '支付单礼品卡抵扣';
ALTER TABLE `sbc-account`.`reconciliation`
    ADD COLUMN `gift_card_price` decimal(20,2) NULL DEFAULT 0 COMMENT '礼品卡抵扣金额';

ALTER TABLE `sbc-order`.`refund_order`
    ADD COLUMN `gift_card_price` decimal(20,2) NULL DEFAULT 0 COMMENT '应退礼品卡抵扣金额';
ALTER TABLE `sbc-account`.`settlement`
    ADD COLUMN `gift_card_price` decimal(20,2) NULL DEFAULT 0 COMMENT '礼品卡抵扣金额';
ALTER TABLE `sbc-account`.`lakala_settlement`
    ADD COLUMN `gift_card_price` decimal(20,2) NULL DEFAULT 0 COMMENT '礼品卡抵扣金额';

CREATE TABLE `sbc-marketing`.`gift_card` (
                             `gift_card_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '礼品卡Id',
                             `name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '礼品卡名称',
                             `background_type` tinyint(4) NOT NULL COMMENT '封面类型 0：指定颜色 1:指定图片',
                             `background_detail` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '封面信息',
                             `par_value` bigint(11) NOT NULL COMMENT '礼品卡面值',
                             `stock_type` tinyint(4) NOT NULL COMMENT '库存标识 0：有限制 1:无限制',
                             `stock` bigint(11) DEFAULT NULL COMMENT '库存',
                             `origin_stock` bigint(11) DEFAULT NULL COMMENT '初始库存',
                             `make_num` bigint(11) DEFAULT '0' COMMENT '已制卡数量',
                             `send_num` bigint(11) DEFAULT '0' COMMENT '已发卡数量',
                             `expiration_type` tinyint(4) NOT NULL COMMENT '有效期类型 0：长期有效 1:激活后天 2：具体时间',
                             `range_month` bigint(11) DEFAULT NULL COMMENT '激活后生效',
                             `expiration_time` datetime DEFAULT NULL COMMENT '有效期',
                             `scope_type` tinyint(4) NOT NULL COMMENT '关联商品 0：全部 1:按品牌 2：按分类 3：按店铺 4：自定义商品',
                             `use_desc` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '使用须知',
                             `contact_type` tinyint(4) NOT NULL COMMENT '客服类型 0：电话 1：座机 2：微信',
                             `contact_phone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客服联系方式',
                             `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                             PRIMARY KEY (`gift_card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼品卡信息';

CREATE TABLE `sbc-marketing`.`gift_card_scope` (
                                   `gift_card_scope_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `gift_card_id` bigint(20) NOT NULL COMMENT '礼品卡Id',
                                   `scope_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   PRIMARY KEY (`gift_card_scope_id`),
                                   KEY `index_gift_card_id` (`gift_card_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼品卡适用商品关联表';

CREATE TABLE `sbc-marketing`.`gift_card_batch` (
                                   `gift_card_batch_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
                                   `gift_card_id` bigint(20) NOT NULL COMMENT '礼品卡Id',
                                   `exchange_mode` tinyint(4) DEFAULT NULL COMMENT '兑换方式 0:卡密模式',
                                   `batch_type` tinyint(4) NOT NULL COMMENT '批次类型 0:制卡 1:发卡',
                                   `batch_num` bigint(11) DEFAULT NULL COMMENT '批次数量(制/发卡数量)',
                                   `batch_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数',
                                   `generate_time` datetime NOT NULL COMMENT '制/发卡时间',
                                   `generate_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '制/发卡人',
                                   `start_card_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '起始卡号',
                                   `end_card_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结束卡号',
                                   `audit_status` tinyint(4) NOT NULL COMMENT '审核状态 0:待审核 1:已审核通过 2:审核不通过',
                                   `audit_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核驳回原因',
                                   `excel_file_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'excel导入的文件oss地址（仅批量发卡时存在）',
                                   `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                   PRIMARY KEY (`gift_card_batch_id`),
                                   KEY `idx_batch_no` (`batch_no`) USING BTREE COMMENT '批次编号索引',
                                   KEY `idx_gift_card_id` (`gift_card_id`) USING BTREE COMMENT '礼品卡Id索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼品卡批次表';

CREATE TABLE `sbc-marketing`.`gift_card_detail` (
                                    `gift_card_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '礼品卡卡号，主键',
                                    `gift_card_id` bigint(20) DEFAULT NULL COMMENT '礼品卡id',
                                    `batch_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '批次编号',
                                    `source_type` tinyint(4) DEFAULT NULL COMMENT '批次类型 0：制卡 1：发卡',
                                    `exchange_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '兑换码',
                                    `expiration_time` datetime DEFAULT NULL COMMENT '有效期',
                                    `acquire_time` datetime DEFAULT NULL COMMENT '会员获卡时间，制卡兑换时间/发卡接收时间',
                                    `activation_time` datetime DEFAULT NULL COMMENT '激活时间',
                                    `belong_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '归属会员，制卡兑换人/发卡接收人',
                                    `activation_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '激活会员',
                                    `card_detail_status` tinyint(4) NOT NULL COMMENT '礼品卡详情状态 0:未兑换 1:未激活 2:已激活 3:已销卡 4:已过期',
                                    `cancel_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '销卡人',
                                    `cancel_time` datetime DEFAULT NULL COMMENT '销卡时间',
                                    `send_status` tinyint(4) DEFAULT NULL COMMENT '发卡状态 0：待发 1：成功 2：失败',
                                    `status_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态变更原因，目前仅针对销卡原因',
                                    `del_flag` tinyint(4) NOT NULL COMMENT '删除标记  0：正常，1：删除',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                    `update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                    PRIMARY KEY (`gift_card_no`) USING BTREE,
                                    KEY `idx_batch_no` (`batch_no`) USING BTREE COMMENT '批次编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼品卡详情表';

CREATE TABLE `sbc-marketing`.`user_gift_card` (
                                  `user_gift_card_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '礼品卡Id',
                                  `customer_id` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户Id',
                                  `gift_card_id` bigint(20) NOT NULL COMMENT '礼品卡Id',
                                  `gift_card_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `batch_no` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡批次号',
                                  `gift_card_no` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '礼品卡卡号',
                                  `par_value` bigint(11) NOT NULL COMMENT '礼品卡面值',
                                  `balance` decimal(10,2) NOT NULL COMMENT '礼品卡余额',
                                  `card_status` tinyint(4) NOT NULL COMMENT '礼品卡状态 0：待激活  1：已激活 2：已销卡',
                                  `expiration_type` tinyint(4) NOT NULL COMMENT '过期时间类型 0：长期有效 1：领取多少月内有效 2：具体时间',
                                  `expiration_time` datetime DEFAULT NULL COMMENT '过期时间',
                                  `acquire_time` datetime DEFAULT NULL COMMENT '会员获卡时间，制卡兑换时间/发卡接收时间',
                                  `belong_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '归属会员，制卡兑换人/发卡接收人',
                                  `activation_time` datetime DEFAULT NULL COMMENT '激活时间',
                                  `cancel_balance` decimal(10,2) DEFAULT NULL COMMENT '销卡时所剩余额',
                                  `cancel_time` datetime DEFAULT NULL COMMENT '销卡时间',
                                  `cancel_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '销卡人',
                                  `cancel_desc` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '销卡描述',
                                  `source_type` tinyint(4) NOT NULL COMMENT '卡的来源 0：制卡 1：发卡',
                                  PRIMARY KEY (`user_gift_card_id`),
                                  UNIQUE KEY `index_customer_card_no` (`customer_id`,`gift_card_no`) USING BTREE,
                                  KEY `index_gift_card_id` (`gift_card_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户礼品卡';

CREATE TABLE `sbc-marketing`.`gift_card_bill` (
                                  `gift_card_bill_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `customer_id` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户Id',
                                  `user_gift_card_id` bigint(20) DEFAULT NULL COMMENT '用户礼品卡Id',
                                  `gift_card_id` bigint(20) DEFAULT NULL COMMENT '礼品卡ID',
                                  `gift_card_no` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '礼品卡卡号',
                                  `business_type` tinyint(4) NOT NULL COMMENT '交易类型 0：使用 1：退换 2: 发卡 3：兑换 4：销卡',
                                  `business_id` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务Id',
                                  `trade_balance` decimal(10,2) NOT NULL COMMENT '交易金额',
                                  `before_balance` decimal(10,2) NOT NULL COMMENT '交易前余额',
                                  `after_balance` decimal(10,2) NOT NULL COMMENT '交易后余额',
                                  `trade_time` datetime DEFAULT NULL COMMENT '交易时间',
                                  `trade_person_type` tinyint(4) DEFAULT NULL COMMENT '交易人类型 0：C端用户 1：B端用户',
                                  `trade_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易人',
                                  PRIMARY KEY (`gift_card_bill_id`),
                                  KEY `index_user_gift_card_id` (`user_gift_card_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='礼品卡交易流水';


update `sbc-marketing`.`marketing` set `join_level` = '-1' WHERE `marketing_type` = '7' AND `store_id` = '-1' AND `join_level` = '0';

update `sbc-marketing`.marketing_plugin_config set coexist = 'COUPON,POINT_AND_CASH,ENTERPRISE_PRICE,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN' where id = 54;

update `sbc-setting`.base_config set version = 'SBC V5.2.0';
