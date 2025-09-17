
-- 收货地址经纬度
ALTER TABLE `sbc-customer`.`customer_delivery_address`
    ADD COLUMN `latitude` decimal(10, 6) NULL COMMENT '纬度' AFTER
    `delete_person`,
ADD COLUMN `longitude` decimal(10, 6) NULL COMMENT '经度' AFTER `latitude`;

ALTER TABLE `sbc-customer`.`customer_delivery_address`
    ADD COLUMN `house_num` varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '门牌号' AFTER `longitude`;

ALTER TABLE `s2b_statistics`.`trade_month`
    MODIFY COLUMN `order_conversion` decimal(10, 2) NULL DEFAULT NULL COMMENT
    '下单转化率' AFTER `pay_money`;
ALTER TABLE `s2b_statistics`.`trade_seven`
    MODIFY COLUMN `order_conversion` decimal(10, 2) NULL DEFAULT NULL COMMENT '下单转化率' AFTER `pay_money`;
ALTER TABLE `s2b_statistics`.`trade_week`
    MODIFY COLUMN `order_conversion` decimal(10, 2) NULL DEFAULT NULL COMMENT '下单转化率' AFTER `pay_money`;
ALTER TABLE `s2b_statistics`.`trade_thirty`
    MODIFY COLUMN `order_conversion` decimal(10, 2) NULL DEFAULT NULL COMMENT '下单转化率' AFTER `pay_money`;

-- 购物车加入商品了类型
ALTER TABLE `sbc-order`.`purchase`
    ADD COLUMN `plugin_type` tinyint(4) NULL default 0 COMMENT '类型： 0-正常商品 1-跨境商品 2-o2o商品' AFTER `order_sort`;

-- 达达配送设置
ALTER TABLE `sbc-empower`.`logistics_setting`
    MODIFY COLUMN `logistics_type` tinyint(4) NOT NULL COMMENT '配置类型 0: 快递100 1:达达配送' AFTER `id`,
    ADD COLUMN `enable_status` tinyint(4) NOT NULL COMMENT '是否启用 0: 否, 1: 是，暂时给达达使用' AFTER `subscribe_status`;

ALTER TABLE `sbc-goods`.`goods_evaluate`
    ADD COLUMN `store_type` tinyint(4) DEFAULT NULL COMMENT '商家类型0 供应商，1 商家，2 门店' AFTER `terminal_source`;

ALTER TABLE `sbc-goods`.`goods_tobe_evaluate`
    ADD COLUMN `plugin_type` tinyint(4) DEFAULT 0 COMMENT '商品类型：0一般商品，1跨境商品，2o2o商品' AFTER `update_person`;

ALTER TABLE `sbc-empower`.`logistics_setting`
    ADD COLUMN `shop_no` varchar(32) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '达达商户id' AFTER `callback_url`;

-- 限售新增
ALTER TABLE `sbc-goods`.`restricted_record` ADD COLUMN `store_id` bigint(20) COMMENT '门店ID' AFTER `record_id`;

-- 最近30天统计增加主键
ALTER TABLE `s2b_statistics`.`goods_recent_thirty`
    MODIFY COLUMN `SHOP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `PV`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`GOODS_INFO_ID`, `SHOP_ID`) USING BTREE;

-- 最近7天统计增加主键
ALTER TABLE `s2b_statistics`.`goods_recent_seven`
    MODIFY COLUMN `SHOP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `PV`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`GOODS_INFO_ID`, `SHOP_ID`) USING BTREE;

-- 门店商品统计插入时，避免主键重复
ALTER TABLE `s2b_statistics`.`goods_recent_thirty`
    MODIFY COLUMN `SHOP_ID` varchar(50) CHARACTER SET utf8mb4 NOT NULL AFTER `PV`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`GOODS_INFO_ID`, `SHOP_ID`) USING BTREE;

ALTER TABLE `s2b_statistics`.`goods_recent_seven`
    MODIFY COLUMN `SHOP_ID` varchar(50) CHARACTER SET utf8mb4 NOT NULL AFTER `PV`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`GOODS_INFO_ID`, `SHOP_ID`) USING BTREE;

DROP TABLE IF EXISTS `sbc-empower`.`delivery_record_dada`;
CREATE TABLE `sbc-empower`.`delivery_record_dada` (
                                                      `delivery_id` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '订单号',
                                                      `order_code` varchar(45) NOT NULL,
                                                      `city_code` varchar(6) NOT NULL COMMENT '城市编码',
                                                      `cargo_price` decimal(20,2) NOT NULL COMMENT '订单金额;不含配送费',
                                                      `is_prepay` tinyint(4) NOT NULL COMMENT '是否需要垫付;1:是 0:否 (垫付订单金额，非运费)',
                                                      `distance` decimal(20,2) DEFAULT NULL COMMENT '配送距离;单位:米',
                                                      `fee` decimal(20,0) DEFAULT NULL COMMENT '实际运费',
                                                      `deliver_fee` decimal(20,0) DEFAULT NULL COMMENT '运费',
                                                      `tips_fee` decimal(20,2) DEFAULT NULL COMMENT '小费',
                                                      `is_use_insurance` tinyint(4) DEFAULT NULL COMMENT '是否使用保价费;0:不使用,1:使用',
                                                      `insurance_fee` decimal(20,2) DEFAULT NULL COMMENT '保价费',
                                                      `deduct_fee` decimal(20,2) DEFAULT NULL COMMENT '违约金',
                                                      `delivery_status` tinyint(4) NOT NULL COMMENT '1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败',
                                                      `receiver_name` varchar(128) NOT NULL COMMENT '收货人姓名',
                                                      `receiver_address` varchar(255) NOT NULL COMMENT '收货人地址',
                                                      `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
                                                      `receiver_lat` varchar(32) NOT NULL COMMENT '收货人地址维度',
                                                      `receiver_lng` varchar(32) NOT NULL COMMENT '收货人地址经度',
                                                      `cancel_reason_id` int(11) DEFAULT NULL COMMENT '取消理由id',
                                                      `cancel_reason` varchar(255) DEFAULT NULL COMMENT '其他取消理由',
                                                      `dm_name` varchar(10) DEFAULT NULL COMMENT '配送员姓名',
                                                      `dm_mobile` varchar(20) DEFAULT NULL COMMENT '配送员手机号',
                                                      `dada_update_time` int(11) DEFAULT NULL COMMENT '达达平台更新时间戳',
                                                      `del_flag` tinyint(4) NOT NULL COMMENT '删除标识;0:未删除1:已删除',
                                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                                      `create_person` varchar(32) NOT NULL COMMENT '创建人',
                                                      `update_time` datetime NOT NULL COMMENT '更新时间',
                                                      `update_person` varchar(32) NOT NULL COMMENT '更新人',
                                                      PRIMARY KEY (`delivery_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='达达配送记录';

ALTER TABLE `sbc-setting`.`platform_address`
    ADD COLUMN `pin_yin` varchar(255) CHARACTER SET utf8mb4 COLLATE
        utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址名称拼音';

-- 更新平台地址
UPDATE `sbc-setting`.platform_address SET id=320118, addr_id = '320118', addr_name = '高淳区', addr_parent_id = '320100', sort_no = 320118, addr_level = 2, create_time = '2020-08-13 10:53:01', update_time = '2020-08-13 10:53:01', del_flag = 0, delete_time = null, data_type = 0 WHERE id = '320125';

INSERT INTO `sbc-setting`.platform_address (id, addr_id, addr_name, addr_parent_id, sort_no, addr_level, create_time, update_time, del_flag, delete_time, data_type, pin_yin) VALUES ('320118001', '320118001', '南漪路', '320118', 320118001, 3, '2021-09-07 16:46:19', '2021-09-07 16:46:19', 0, null, 1, 'nanyilu');

UPDATE `sbc-setting`.`authority` SET `system_type_cd` = 4, `function_id` = 'fc92683c3fe311e9828800163e0fc468', `authority_title` = '员工列表', `authority_name` = NULL, `authority_url` = '/customer/es/employees', `request_type` = 'POST', `remark` = NULL, `sort` = 1, `create_time` = NULL, `del_flag` = 0 WHERE `authority_id` = 'fc97b0b33fe311e9828800163e0fc468';

DROP TABLE IF EXISTS `s2b_statistics`.`replay_company_info`;
CREATE TABLE `s2b_statistics`.`replay_company_info` (
                                       `company_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司信息ID',
                                       `company_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `back_ID_card` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `province_id` bigint(10) DEFAULT NULL COMMENT '省',
                                       `city_id` bigint(10) DEFAULT NULL COMMENT '市',
                                       `area_id` bigint(10) DEFAULT NULL COMMENT '区',
                                       `street_id` bigint(10) DEFAULT NULL COMMENT '街道id',
                                       `detail_address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `contact_name` text CHARACTER SET utf8mb4 COMMENT '联系人',
                                       `contact_phone` text CHARACTER SET utf8mb4 COMMENT '联系方式',
                                       `copyright` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `company_descript` text CHARACTER SET utf8mb4 COMMENT '公司简介',
                                       `operator` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                       `company_type` tinyint(4) DEFAULT NULL COMMENT '商家类型 0、平台自营 1、第三方商家',
                                       `is_account_checked` tinyint(4) DEFAULT NULL COMMENT '账户是否全部收到打款 0、否 1、是',
                                       `social_credit_code` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `legal_representative` varchar(150) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `registered_capital` decimal(12,2) DEFAULT NULL COMMENT '注册资本',
                                       `found_date` datetime DEFAULT NULL COMMENT ' 成立日期',
                                       `business_term_start` datetime DEFAULT NULL COMMENT '营业期限自',
                                       `business_term_end` datetime DEFAULT NULL COMMENT '营业期限至',
                                       `business_scope` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `business_licence` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `front_ID_card` varchar(1024) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `company_code` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `supplier_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
                                       `del_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除 0 否  1 是',
                                       `remit_affirm` tinyint(1) DEFAULT '0' COMMENT '是否通过打款确认(0:否,1:是)',
                                       `apply_enter_time` datetime DEFAULT NULL COMMENT '入驻时间(第一次审核通过时间)',
                                       `store_type` tinyint(4) DEFAULT NULL COMMENT '商家类型0供应商，1商家',
                                       `company_source_type` tinyint(1) DEFAULT '0' COMMENT '商家来源类型 0:商城入驻 1:linkMall初始化 2:京东vop初始化',
                                       `supplier_type` tinyint(4) DEFAULT NULL COMMENT '商家类型 0 普通商家,1 跨境商家',
                                       PRIMARY KEY (`company_info_id`),
                                       KEY `idx_company_type` (`company_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1274 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公司信息';