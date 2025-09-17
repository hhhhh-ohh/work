-- ***************************************** goods begin *********************************************
-- ***************************************** goods end *********************************************


-- ***************************************** customer begin *********************************************

-- 跨境商家基础信息
create table `sbc-customer`.`store_baseinfo`
(
    `id` int auto_increment comment '主键',
    `store_id` int null comment '关联商家id',
    `company_code` varchar(255) null comment '电商企业编码',
    `company_name` varchar(255) null comment '电商企业名称',
    `create_time` date null comment '创建时间',
    `create_person` varchar(255) null comment '创建人',
    `update_time` date null comment '更新时间',
    `update_person` varchar(255) null comment '更新人',
    `delete_time` date null comment '删除时间',
    `delete_person` varchar(255) null comment '删除人',
    `del_flag` tinyint null comment '删除标识,0:未删除1:已删除',
    constraint store_baseinfo_pk
        primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跨境商家基础信息';


CREATE TABLE `sbc-customer`.`customer_card` (
	`customer_card_id` varchar(32) NOT NULL COMMENT '身份认证主键ID',
	`customer_id` varchar(32) NOT NULL COMMENT '会员ID',
	`customer_card_no` varchar(20) NOT NULL COMMENT '身份证号码18位 冗余长度防止没有trim',
	`customer_card_name` varchar(64) COMMENT '身份证姓名',
	`is_default_card` tinyint(4) DEFAULT NULL COMMENT '是否是默认身份',
	`del_flag` tinyint(4) DEFAULT 0 COMMENT '是否删除标志 0：否，1：是',
	`delete_person` varchar(32) COMMENT '删除人',
	`create_time` datetime COMMENT '创建时间',
	`create_person` varchar(32) COMMENT '创建人',
	`update_time` datetime COMMENT '修改时间',
	`update_person` varchar(32) COMMENT '修改人',
	PRIMARY KEY (`customer_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户身份证认证信息';



-- ***************************************** customer end *********************************************

-- ***************************************** setting begin *********************************************

-- 跨境基本配置
CREATE TABLE `sbc-setting`.`cross_base_config` (
  `base_id` varchar(32) NOT NULL COMMENT '基本配置主键',
  `register_code` varchar(50) DEFAULT NULL COMMENT '电商平台注册编码',
  `register_name` varchar(50) DEFAULT NULL COMMENT '电商平台注册名称',
  `company_code` varchar(50) DEFAULT NULL COMMENT '电商企业编码',
  `company_name` varchar(50) DEFAULT NULL COMMENT '电商企业名称',
  `payment_name` varchar(50) DEFAULT NULL COMMENT '收款企业名称',
  `payment_account` varchar(50) DEFAULT NULL COMMENT '收款账户',
  `social_credit_code` varchar(50) DEFAULT NULL COMMENT '收款企业统一社会信用代码',
  `reduction_rate` double(11,2) DEFAULT NULL COMMENT '减免比例',
  `taxation_desc` text COMMENT '税费说明',
  `policy_desc` text COMMENT '政策说明',
  `mobile_desc` text COMMENT '移动端补税政策',
  `pc_desc` text COMMENT 'pc端补税政策',
  `base_logo` varchar(255) DEFAULT NULL COMMENT '跨境标识',
  `identity_check` tinyint(4) DEFAULT NULL COMMENT '身份校验 0-正则校验',
  `free_reight` decimal(11,2) DEFAULT NULL COMMENT '减免运费',
  `base_freight` decimal(11,2) DEFAULT NULL COMMENT '基本运费',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`base_id`),
  UNIQUE KEY `base_id_UNIQUE` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跨境基本配置';

-- 跨境支付企业

CREATE TABLE `sbc-setting`.`pay_company` (
  `pay_company_id` varchar(32) NOT NULL COMMENT '支付企业id',
  `gateway_id` int(11) DEFAULT NULL COMMENT '支付网关id',
  `pay_company_name` varchar(50) DEFAULT NULL COMMENT '支付企业备案名称',
  `pay_company_code` varchar(50) DEFAULT NULL COMMENT '支付企业备案编号',
  `pay_company_address` varchar(255) DEFAULT NULL COMMENT '报关请求地址',
  `app_id` varchar(50) DEFAULT NULL COMMENT '支付宝App ID',
  `public_key` text COMMENT '支付宝公钥',
  `private_key` text COMMENT '支付宝私钥',
  `public_account` varchar(50) DEFAULT NULL COMMENT '微信公众账号',
  `merchant_account` varchar(50) DEFAULT NULL COMMENT '微信商户号',
  `pay_company_enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`pay_company_id`),
  UNIQUE KEY `pay_company_id_UNIQUE` (`pay_company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付企业';

-- 跨境计量单位

create table `sbc-setting`.`constant_customs_unit`
(
    unit_id              varchar(32)       not null comment '计量单位id',
    unit_name            varchar(20)       null comment '计量单位值',
    unit_sort            bigint(11)        null comment '计量单位排序',
    unit_customs_code    varchar(20)       null comment '海关编码',
    unit_inspection_code varchar(20)       null comment '国检编码',
    create_time          datetime          null comment '创建时间',
    create_person        varchar(32)       null comment '创建人',
    update_time          datetime          null comment '修改时间',
    update_person        varchar(32)       null comment '修改人',
    delete_time          datetime          null comment '删除时间',
    delete_person        varchar(32)       null comment '删除人',
    del_flag             tinyint default 0 null comment '删除标识,0:未删除1:已删除',
    constraint unit_id_UNIQUE
        unique (unit_id)
)
    comment '计量单位';

alter table `sbc-setting`.`constant_customs_unit`
    add primary key (unit_id);

-- 跨境包装单位

create table `sbc-setting`.`constant_packing`
(
    packing_id    varchar(32)       not null comment '打包常量id',
    packing_name  varchar(20)       null comment '打包名词',
    packing_sort  bigint(11)        null comment '打包常量排序',
    create_time   datetime          null comment '创建时间',
    create_person varchar(32)       null comment '创建人',
    update_time   datetime          null comment '修改时间',
    update_person varchar(32)       null comment '修改人',
    delete_time   datetime          null comment '删除时间',
    delete_person varchar(32)       null comment '删除人',
    del_flag      tinyint default 0 null comment '删除标识,0:未删除1:已删除',
    constraint packing_id_UNIQUE
        unique (packing_id)
)
    comment '打包常量';

alter table `sbc-setting`.`constant_packing`
    add primary key (packing_id);

-- 跨境原产国

create table `sbc-setting`.`constant_sys_country`
(
    country_id      varchar(32)            not null comment '原产国id',
    country_name    varchar(20) default '' not null comment '原产国名称',
    country_sort    bigint(11)             null comment '原产国排序',
    country_img     varchar(255)           null comment '原产国图片',
    inspection_code varchar(20)            null comment '原产国code',
    create_time     datetime               null comment '创建时间',
    create_person   varchar(32)            null comment '创建人',
    update_time     datetime               null comment '修改时间',
    update_person   varchar(32)            null comment '修改人',
    delete_time     datetime               null comment '删除时间',
    delete_person   varchar(32)            null comment '删除人',
    del_flag        tinyint     default 0  null comment '删除标识,0:未删除1:已删除',
    constraint np_sys_country_id_uindex
        unique (country_id)
)
    comment '原产国';

alter table `sbc-setting`.`constant_sys_country`
    add primary key (country_id);

-- 物流企业

CREATE TABLE `sbc-setting`.`logistics_company` (
  `logistics_company_id` varchar(32) NOT NULL COMMENT '物流企业id',
  `logistics_company_name` varchar(50) DEFAULT NULL COMMENT '物流企业名称',
  `logistics_company_code` varchar(50) DEFAULT NULL COMMENT '物流企业备案编码',
  `place_order_supplier_code` varchar(255) DEFAULT NULL COMMENT '下单商家代码',
  `place_order_partner_id` varchar(255) DEFAULT NULL COMMENT '下单PartnerID',
  `clearance_supplier_code` varchar(50) DEFAULT NULL COMMENT '清关商家代码',
  `clearance_partner_id` varchar(50) DEFAULT NULL COMMENT '清关PartnerID',
  `place_order_url` varchar(255) DEFAULT NULL COMMENT '电子面单下单URL',
  `waybill_url` varchar(255) DEFAULT NULL COMMENT '运单申报URL',
  `place_order_key` varchar(500) DEFAULT NULL COMMENT '电子面单下单接口秘钥',
  `waybill_key` varchar(500) DEFAULT NULL COMMENT '运单申报接口秘钥',
  `sender_name` varchar(50) DEFAULT NULL COMMENT '发件人姓名',
  `sender_phone` varchar(20) DEFAULT NULL COMMENT '发件人联系电话',
  `sender_province` bigint(10) DEFAULT NULL COMMENT '发件省份',
  `sender_city` bigint(10) DEFAULT NULL COMMENT '发件城市',
  `sender_area` bigint(10) DEFAULT NULL COMMENT '发件区',
  `sender_address` varchar(255) DEFAULT NULL COMMENT '发件详细地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  `params` text COMMENT '配置参数',
  PRIMARY KEY (`logistics_company_id`),
  UNIQUE KEY `logistics_company_id_UNIQUE` (`logistics_company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流企业';

--  报关渠道
CREATE TABLE `sbc-setting`.`declaration_channel` (
  `declaration_channel_id` varchar(32) NOT NULL COMMENT '报关渠道id',
  `declaration_channel_name` varchar(50) DEFAULT NULL COMMENT '报关渠道名称',
  `params` text COMMENT '报关渠道配置参数(JSON形式存储)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`declaration_channel_id`) USING BTREE,
  UNIQUE KEY `declaration_channel_id_UNIQUE` (`declaration_channel_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='报关渠道';

-- 跨境贸易类型

CREATE TABLE `sbc-setting`.`trade_type` (
  `type_id` varchar(32) NOT NULL COMMENT '跨境贸易类型id',
  `type_name` varchar(50) DEFAULT NULL COMMENT '跨境贸易类型名称',
  `single_quota` bigint(11) DEFAULT NULL COMMENT '单笔限额',
  `exempt_quota` bigint(11) DEFAULT NULL COMMENT '免征限额',
  `single_weight` bigint(11) DEFAULT NULL COMMENT '单笔限重',
  `pc_img` varchar(255) DEFAULT NULL COMMENT 'PC贸易流程图',
  `mobile_img` varchar(255) DEFAULT NULL COMMENT '移动端贸易流程图',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_id_UNIQUE` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跨境贸易类型';

-- 电子口岸

CREATE TABLE `sbc-setting`.`electron_port` (
  `electron_port_id` varchar(32) NOT NULL COMMENT '电子口岸id',
  `electron_port_name` varchar(50) DEFAULT NULL COMMENT '电子口岸名称',
  `pay_company_id` varchar(32) DEFAULT NULL COMMENT '支付企业ID',
  `logistics_company_id` varchar(32) DEFAULT NULL COMMENT '物流企业id',
  `declaration_channel_id` varchar(32) DEFAULT NULL COMMENT '报关渠道id',
  `pay_push` tinyint(1) DEFAULT NULL COMMENT '支付单推送开关',
  `logistics_push` tinyint(1) DEFAULT NULL COMMENT '物流单推送开关',
  `type_id` varchar(32) DEFAULT NULL COMMENT '贸易类型id',
  `electron_port_sort` bigint(11) DEFAULT NULL COMMENT '电子口岸排序',
  `electron_port_enable` tinyint(1) DEFAULT NULL COMMENT '电子口岸是否启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`electron_port_id`),
  UNIQUE KEY `electron_port_id_UNIQUE` (`electron_port_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子口岸';

-- 电子口岸关联贸易类型表
CREATE TABLE `sbc-setting`.`electron_port_trade_type` (
  `electron_port_trade_type_id` varchar(32) NOT NULL COMMENT '电子口岸关联贸易类型主键',
  `electron_port_id` varchar(32) DEFAULT NULL COMMENT '电子口岸ID',
  `type_id` varchar(32) DEFAULT NULL COMMENT '贸易类型ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`electron_port_trade_type_id`),
  UNIQUE KEY `electron_port_trade_type_id_UNIQUE` (`electron_port_trade_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='电子口岸关联贸易类型表';


-- 电子口岸关联海关编码
CREATE TABLE `sbc-setting`.`customs_code` (
  `customs_code_id` varchar(32) NOT NULL COMMENT '海关编码id',
  `customs_code_type` tinyint(4) DEFAULT NULL COMMENT '海关编码类型 1-支付企业海关编码 2-物流企业海关编码 3-报关渠道海关编码',
  `company_id` varchar(32) DEFAULT NULL COMMENT '企业id',
  `customs_code_value` varchar(50) DEFAULT NULL COMMENT '海关编码值',
  `electron_port_id` varchar(32) DEFAULT NULL COMMENT '电子口岸id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`customs_code_id`),
  UNIQUE KEY `customs_code_id_UNIQUE` (`customs_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='海关编码';
-- ***************************************** setting end *********************************************

DROP TABLE IF EXISTS `sbc-goods`.`cross_goods_info`;
CREATE TABLE `sbc-goods`.`cross_goods_info` (
                                                `cross_goods_info_id` varchar(32) NOT NULL COMMENT '主键ID',
                                                `cross_goods_id` varchar(32) NOT NULL COMMENT '跨境货品ID',
                                                `goods_info_id` varchar(32) NOT NULL COMMENT '货品ID',
                                                `sub_title` varchar(255) DEFAULT NULL COMMENT '商品上架英文名称',
                                                `store_id` varchar(32) DEFAULT NULL COMMENT '店铺ID',
                                                `trade_type` varchar(32) NOT NULL COMMENT '贸易类型',
                                                `electron_port` varchar(32) DEFAULT NULL COMMENT '电子口岸',
                                                `unit` varchar(32) DEFAULT NULL COMMENT '计量单位',
                                                `country` varchar(32) NOT NULL COMMENT '原产国',
                                                `package_type` varchar(32) DEFAULT NULL COMMENT '包装类型',
                                                `record_no` varchar(20) DEFAULT NULL COMMENT '商品备案编号',
                                                `record_name` varchar(100) DEFAULT NULL COMMENT '商品备案名称',
                                                `record_price` decimal(10,2) DEFAULT NULL COMMENT '商品备案价格',
                                                `hs_code` varchar(20) DEFAULT NULL COMMENT 'HS编码',
                                                `tariff_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '关税税率',
                                                `consumption_tax_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '消费税率',
                                                `value_added_tax_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '增值税率',
                                                `tax_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '行邮税率',
                                                `complex_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '综合税率',
                                                `item_no` varchar(10) DEFAULT NULL COMMENT '关联2010项号',
                                                `business_model` varchar(32) DEFAULT NULL COMMENT '业务模式',
                                                `main_ingredients` varchar(1000) DEFAULT NULL COMMENT '主要成分',
                                                `remark` varchar(50) DEFAULT NULL COMMENT '商品描述',
                                                `efficacy` varchar(255) DEFAULT NULL COMMENT '用途、功效',
                                                `enterprise_registered_number` varchar(32) DEFAULT NULL COMMENT '生产企业注册号',
                                                `enterprise_name` varchar(32) DEFAULT NULL COMMENT '生产企业名称',
                                                `enterprise_countries` varchar(32) DEFAULT NULL COMMENT '生产企业所属国',
                                                `volume` decimal(16,2) DEFAULT NULL COMMENT '体积(cm3)',
                                                `first_unit` varchar(8) DEFAULT NULL COMMENT '法定第一单位编码',
                                                `first_number` decimal(11,2) DEFAULT NULL COMMENT '法定第一数量',
                                                `second_unit` varchar(8) DEFAULT NULL COMMENT '法定第二单位编码',
                                                `second_number` decimal(11,2) DEFAULT NULL COMMENT '法定第二数量',
                                                `gross_weight` decimal(10,2) DEFAULT NULL COMMENT '毛重',
                                                `net_weight` decimal(10,2) DEFAULT NULL COMMENT '净重',
                                                `record_status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '备案状态 0：未备案，1：申请备案,2：备案成功，3：已失败',
                                                `record_reject_reason` varchar(255) DEFAULT '' COMMENT '备案失败原因',
                                                `enterprise_product_id` varchar(50) DEFAULT '' COMMENT '企业商品货号',
                                                `customs_product_code` varchar(50) DEFAULT '' COMMENT '海关商品编码(保税仓货号)',
                                                `customs_record_id` varchar(50) DEFAULT '' COMMENT '海关商品备案号',
                                                `customs_inspection_id` varchar(50) DEFAULT '' COMMENT '检疫商品备案号',
                                                `customs_product_spec` varchar(50) DEFAULT '' COMMENT '海关商品规格型号',
                                                `inspection_product_spec` varchar(50) DEFAULT '' COMMENT '检疫商品规格型号',
                                                `product_tax_code` varchar(50) DEFAULT '' COMMENT '商品税号',
                                                `currency` varchar(10) DEFAULT NULL COMMENT '币制',
                                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                                `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                                `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                                PRIMARY KEY (`cross_goods_info_id`),
                                                KEY `idx_cross_goods_id` (`cross_goods_id`) USING BTREE,
                                                KEY `ids_goods_info_id` (`goods_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跨境商品信息表';



DROP TABLE IF EXISTS `sbc-goods`.`cross_goods`;
CREATE TABLE `sbc-goods`.`cross_goods` (
                                           `cross_goods_id` varchar(32) NOT NULL COMMENT '主键ID',
                                           `goods_id` varchar(32) DEFAULT NULL COMMENT '普通货品ID',
                                           `goods_name` varchar(255) DEFAULT NULL COMMENT '普通货品名',
                                           `goods_no` varchar(45) DEFAULT NULL COMMENT '普通货品SPU',
                                           `trade_type` varchar(32) NOT NULL COMMENT '贸易类型',
                                           `electron_port` varchar(32) DEFAULT NULL COMMENT '电子口岸',
                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                           `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                           `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                           `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                           PRIMARY KEY (`cross_goods_id`),
                                           KEY `idx_goods_id` (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跨境货品信息表';


/*
Navicat MySQL Data Transfer

Source Server         : 114.67.71.101
Source Server Version : 50642
Source Host           : 114.67.71.101:3306
Source Database       : sbc-cross-border

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2021-06-17 19:21:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for trade_getaway
-- ----------------------------
DROP TABLE IF EXISTS `sbc-order`.`trade_getaway`;
CREATE TABLE `sbc-order`.`trade_getaway` (
                                             `trade_getaway_id` varchar(32) NOT NULL COMMENT '订单网关id',
                                             `trade_id` varchar(32) DEFAULT NULL COMMENT '订单id',
                                             `electron_port_id` varchar(32) DEFAULT NULL COMMENT '电子口岸id',
                                             `type_id` varchar(32) DEFAULT NULL COMMENT '贸易类型ID',
                                             `logistics_company_id` varchar(32) DEFAULT NULL COMMENT '物流企业id',
                                             `logistics_push_status` tinyint(4) DEFAULT '0' COMMENT '物流推送状态，0:未推送,1：推送成功,2：推送失败,3：无需推送',
                                             `logistics_push_message` varchar(255) DEFAULT NULL COMMENT '物流推送失败原因',
                                             `logistics_push_time` datetime DEFAULT NULL COMMENT '物流推送时间',
                                             `pay_company_id` varchar(32) DEFAULT NULL COMMENT '支付企业id',
                                             `pay_push_status` tinyint(4) DEFAULT '0' COMMENT '支付推送状态，0:未推送,1：推送成功,2：推送失败,3：无需推送',
                                             `pay_push_message` varchar(255) DEFAULT NULL COMMENT '支付推送失败原因',
                                             `pay_push_time` datetime DEFAULT NULL COMMENT '支付推送时间',
                                             `declaration_channel_id` varchar(32) DEFAULT NULL COMMENT '报关渠道id',
                                             `declaration_push_status` tinyint(4) DEFAULT '0' COMMENT '海关推送状态，0:未推送,1：推送成功,2：推送失败',
                                             `declaration_push_message` varchar(255) DEFAULT NULL COMMENT '海关推送失败原因',
                                             `declaration_push_time` datetime DEFAULT NULL COMMENT '海关推送时间',
                                             `electronic_face_status` tinyint(4) DEFAULT NULL COMMENT '电子面单申报状态： 0：未申报；1：已申报，2：申报失败，3：无需申报',
                                             `declared_status` tinyint(4) DEFAULT '0' COMMENT '清关状态0:未清关 1:清关中 2:已清关 3:清关失败',
                                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                             `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                             `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                             `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                             `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                             `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                             PRIMARY KEY (`trade_getaway_id`),
                                             UNIQUE KEY `trade_getaway_id_UNIQUE` (`trade_getaway_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='跨境订单网关表';

DROP TABLE IF EXISTS `sbc-order`.`pay_push_log`;
CREATE TABLE `sbc-order`.`pay_push_log` (
                                            `pay_push_log_id` varchar(32) NOT NULL COMMENT '支付推送日志主键id',
                                            `trade_id` varchar(50) DEFAULT NULL COMMENT '订单id',
                                            `pay_company_name` varchar(50) DEFAULT NULL COMMENT '支付企业备案名称',
                                            `pay_company_code` varchar(50) DEFAULT NULL COMMENT '支付企业备案编号',
                                            `pay_push_status` tinyint(4) DEFAULT NULL COMMENT '支付推送状态  0-推送成功 1-推送失败',
                                            `pay_push_time` datetime DEFAULT NULL COMMENT '支付推送时间',
                                            `detail_error_code` varchar(255) DEFAULT NULL COMMENT '推送状态失败 详细错误代码',
                                            `detail_error_des` varchar(255) DEFAULT NULL COMMENT '推送状态失败 详细错误描述',
                                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                            `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                            `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                            `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                            `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                            `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                            `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                            PRIMARY KEY (`pay_push_log_id`),
                                            UNIQUE KEY `pay_push_log_id_UNIQUE` (`pay_push_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付推送日志表';

DROP TABLE IF EXISTS `sbc-order`.`logistics_push_log`;
CREATE TABLE `sbc-order`.`logistics_push_log` (
                                                  `logistics_push_log_id` varchar(32) NOT NULL COMMENT '支付推送日志主键id',
                                                  `trade_id` varchar(50) DEFAULT NULL COMMENT '订单id',
                                                  `logistics_company_name` varchar(50) DEFAULT NULL COMMENT '物流企业名称',
                                                  `logistics_company_code` varchar(50) DEFAULT NULL COMMENT '物流企业编码',
                                                  `trade_create_time` datetime DEFAULT NULL COMMENT '电子面单下单时间',
                                                  `trade_create_result` varchar(255) DEFAULT NULL COMMENT '电子面单下单结果',
                                                  `logistics_push_status` tinyint(4) DEFAULT NULL COMMENT '运单推送状态  0-推送成功 1-推送失败',
                                                  `logistics_push_time` datetime DEFAULT NULL COMMENT '运单申报时间',
                                                  `detail_code` varchar(255) DEFAULT NULL COMMENT '推送返回状态码-成功或失败状态码',
                                                  `detail_des` varchar(255) DEFAULT NULL COMMENT '推送返回信息-成功或失败信息',
                                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                                  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                                  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                                  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                                  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                                  PRIMARY KEY (`logistics_push_log_id`),
                                                  UNIQUE KEY `logistics_push_log_id_UNIQUE` (`logistics_push_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运单推送日志表';

DROP TABLE IF EXISTS `sbc-order`.`declaration_push_log`;
CREATE TABLE `sbc-order`.`declaration_push_log` (
                                                    `declaration_push_log_id` varchar(32) NOT NULL COMMENT '海关推送日志主键id',
                                                    `trade_id` varchar(50) DEFAULT NULL COMMENT '订单id',
                                                    `declaration_push_status` tinyint(4) DEFAULT NULL COMMENT '海关推送状态  0-推送成功 1-推送失败',
                                                    `push_time` datetime DEFAULT NULL COMMENT '推送时间',
                                                    `push_info` varchar(50) DEFAULT NULL COMMENT '推送内容',
                                                    `push_result` varchar(255) DEFAULT NULL COMMENT '推送结果',
                                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                    `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                                    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                    `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                                    `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                                    `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                                    `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                                    PRIMARY KEY (`declaration_push_log_id`),
                                                    UNIQUE KEY `declaration_push_log_id` (`declaration_push_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='海关推送日志表';

DROP TABLE IF EXISTS `sbc-order`.`order_push`;
CREATE TABLE `sbc-order`.`order_push` (
                                          `order_push_id` varchar(32) NOT NULL COMMENT '订单推送id',
                                          `session_id` varchar(128) NOT NULL COMMENT '海关请求的sessionId',
                                          `order_code` varchar(40) NOT NULL COMMENT '订单编号',
                                          `service_time` bigint(20) NOT NULL COMMENT '调用系统时间(海关请求时间)',
                                          `order_push_status` tinyint(4) DEFAULT '0' COMMENT '订单推送状态：0待推送(海关已请求)，1已推送，2推送失败',
                                          `guid` varchar(40) DEFAULT NULL COMMENT '系统唯一序号',
                                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                          `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                          `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                          `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                          `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                          `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                          `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                          PRIMARY KEY (`order_push_id`),
                                          UNIQUE KEY `order_push_id_UNIQUE` (`order_push_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='海关原始数据推送';


DROP TABLE IF EXISTS `sbc-order`.`pay_info`;
CREATE TABLE `sbc-order`.`pay_info` (
                                        `pay_info_id` varchar(32) NOT NULL COMMENT '支付信息id',
                                        `order_code` varchar(40) NOT NULL COMMENT '订单编号',
                                        `pay_request` text COMMENT '支付请求',
                                        `pay_response` text COMMENT '支付响应',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                        `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                        `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                        `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                        `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                        `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识,0:未删除1:已删除',
                                        PRIMARY KEY (`pay_info_id`),
                                        UNIQUE KEY `pay_info_id_UNIQUE` (`pay_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付请求响应信息';
