UPDATE `sbc-customer`.`company_info` SET `company_code` = 'LinkedMall' WHERE `supplier_name` = 'LinkedMall';

-- 同步商品临时表初始化
DROP TABLE IF EXISTS `sbc-empower`.`vop_spu_temp`;
CREATE TABLE `sbc-empower`.`vop_spu_temp` (
    `id` int(16) NOT NULL AUTO_INCREMENT,
    `spu_id` varchar(16) NULL COMMENT '京东虚拟SPUID',
    `p_name` varchar(512) NULL COMMENT '京东虚拟SPU名称',
    `update_time` datetime NULL COMMENT '更新时间',
    `company_info_id` int(11) NULL COMMENT '公司信息ID',
    `company_name` varchar(255) NULL COMMENT '公司名称',
    `store_id` bigint(20) NULL COMMENT '店铺主键',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='VOP虚拟SPU表';
ALTER TABLE `sbc-empower`.`vop_spu_temp` ADD INDEX idx_vop_spu_temp_spu_id (`spu_id`);

DROP TABLE IF EXISTS `sbc-empower`.`vop_goods_temp`;
CREATE TABLE `sbc-empower`.`vop_goods_temp` (
    `id` int(16) NOT NULL AUTO_INCREMENT,
    `sale_unit` varchar(32) NOT NULL COMMENT '单位',
    `weight` varchar(16) NULL COMMENT '重量',
    `product_area` varchar(128) NULL COMMENT '产地',
    `ware_qd` text NULL COMMENT '包装清单',
    `image_path` varchar(256) NULL COMMENT '主图',
    `param` TEXT NULL COMMENT '规格参数',
    `state` varchar(8) NULL COMMENT '规格参数',
    `sku` varchar(32) NULL COMMENT '商品编号',
    `brand_name` varchar(64) NULL COMMENT '品牌名称',
    `upc` varchar(64) NULL COMMENT 'upc码',
    `category` varchar(32) NULL COMMENT '分类',
    `name` varchar(512) NULL COMMENT '商品名称',
    `introduction` MEDIUMTEXT NULL COMMENT '商品详情页大字段',
    `tax_info` DECIMAL(12,4) NULL COMMENT '税率',
    `nappintroduction` MEDIUMTEXT NULL COMMENT 'H5商品详情页大字段',
    `tax_code` varchar(32) NULL COMMENT '金税编码',
    `spu_id` varchar(32) NULL COMMENT '京东虚拟SPUID',
    `p_name` varchar(256) NULL COMMENT '京东虚拟SPU名称',
    `update_time` datetime NULL COMMENT '更新时间',
    `company_info_id` int(11) NULL COMMENT '公司信息ID',
    `store_id` bigint(20) NULL COMMENT '店铺主键',
    `param_detail_json` text NULL COMMENT '结构化商品属性数据',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='VOP商品中间表';

DROP TABLE IF EXISTS `sbc-empower`.`channel_config`;
CREATE TABLE `sbc-empower`.`channel_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ' 编号',
    `channel_type` tinyint(4) DEFAULT NULL COMMENT '渠道标识0 : linkedMall 1：vop',
    `channel_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '渠道名',
    `remark` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '描述',
    `status` tinyint(1) DEFAULT NULL COMMENT '状态,0:未启用1:已启用',
    `context` longtext CHARACTER SET utf8 COMMENT '配置内容，如JSON内容',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`),
    UNIQUE KEY `idx_channel_type` (`channel_type`),
    KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道配置表';

ALTER TABLE `sbc-empower`.`vop_goods_temp` ADD INDEX idx_vop_goods_temp_sku (`sku`);
ALTER TABLE `sbc-empower`.`vop_goods_temp` ADD INDEX idx_vop_goods_temp_spu_id (`spu_id`);

ALTER TABLE `sbc-goods`.`standard_goods` MODIFY COLUMN goods_detail MEDIUMTEXT comment '商品详情';
ALTER TABLE `sbc-goods`.`standard_goods` MODIFY COLUMN goods_mobile_detail MEDIUMTEXT comment '移动端图文详情';
ALTER TABLE `sbc-goods`.`standard_sku` ADD COLUMN goods_param text comment '京东商品参数' after stock;
ALTER TABLE `sbc-goods`.`goods_info` ADD COLUMN goods_param text comment '京东商品参数' after stock;

ALTER TABLE `sbc-goods`.`goods` MODIFY COLUMN goods_detail MEDIUMTEXT comment '商品详情';
ALTER TABLE `sbc-goods`.`goods` MODIFY COLUMN goods_mobile_detail MEDIUMTEXT comment '移动端图文详情';
-- 同步商品临时表初始化结束

ALTER TABLE `sbc-goods`.`standard_goods`
MODIFY COLUMN `goods_source` tinyint(4) NULL DEFAULT 1 COMMENT
    '商品来源，0供应商，1商家，2linkedmall，4京东vop' AFTER `goods_mobile_detail`;

-- 京东VOP渠道配置
INSERT INTO `sbc-empower`.`channel_config`(`channel_type`, `channel_name`, `remark`, `status`, `context`, `create_time`,
                                           `update_time`, `del_flag`)
VALUES ('1', '第三方平台-京东vop', null, '0',
        '{\"clientId\":\"\",\"platformPwd\":\"\",\"clientSecret\":\"\",\"platformName\":\"\"}', now(),
        now(), '0');

-- 港澳台地址数据更新
INSERT INTO `sbc-setting`.`platform_address` (`id`, `addr_id`, `addr_name`, `addr_parent_id`, `sort_no`, `addr_level`, `create_time`, `update_time`, `del_flag`, `delete_time`, `data_type`) VALUES
('910000', '910000', '港澳', '0', 910000, 0, now(), now(), 0, NULL, 0);
UPDATE `sbc-setting`.`platform_address` SET addr_parent_id = 910000, addr_level=1 WHERE id in (810000, 820000);
UPDATE `sbc-setting`.`platform_address` SET addr_level=2 WHERE id in (810100, 810200,810300,820100,820200);
UPDATE `sbc-setting`.`platform_address` SET addr_level=3 WHERE id in (820101, 820201,810101,810102,810103,810104,810105,810201,810202,810203,810204,810301,810302,810303,810304,810305,810306,810307,810308,810309);

INSERT INTO `sbc-setting`.`platform_address` (`id`, `addr_id`, `addr_name`, `addr_parent_id`, `sort_no`, `addr_level`, `create_time`, `update_time`, `del_flag`, `delete_time`, `data_type`) VALUES
('920000', '920000', '台湾', '0', 920000, 0, now(), now(), 0, NULL, 0);
UPDATE `sbc-setting`.`platform_address` SET addr_parent_id = 920000, addr_level=1 WHERE id = 710000;
UPDATE `sbc-setting`.`platform_address` SET addr_level=2 WHERE id in (710100,710200,710300,710400,710500,710600,710700,710800,710900,711100,711200,711300,711400,711500,711700,711900,712100,712400,712500,712600,712700,712800);
UPDATE `sbc-setting`.`platform_address` SET addr_level=3 WHERE id in (710101,710102,710103,710104,710105,710106,710107,710108,710109,710110,710111,710112,710201,710202,710203,710204,710205,710206,710207,710208,710209,710210,710211,710241,710242,710243,710244,710245,710246,710247,710248,710249,710250,710251,710252,710253,710254,710255,710256,710257,710258,710259,710260,710261,710262,710263,710264,710265,710266,710267,710268,710301,710302,710303,710304,710305,710306,710339,710340,710341,710342,710343,710344,710345,710346,710347,710348,710349,710350,710351,710352,710353,710354,710355,710356,710357,710358,710359,710360,710361,710362,710363,710364,710365,710366,710367,710368,710369,710401,710402,710403,710404,710405,710406,710407,710408,710431,710432,710433,710434,710435,710436,710437,710438,710439,710440,710441,710442,710443,710444,710445,710446,710447,710448,710449,710450,710451,710507,710508,710509,710510,710511,710512,710614,710615,710616,710617,710618,710619,710620,710621,710622,710623,710624,710625,710626,710701,710702,710703,710704,710705,710706,710707,710801,710802,710803,710901,710902,711130,711131,711132,711133,711134,711135,711136,711137,711138,711139,711140,711141,711142,711143,711144,711145,711146,711147,711148,711149,711150,711151,711152,711153,711154,711155,711156,711157,711158,711214,711215,711216,711217,711218,711219,711220,711221,711222,711223,711224,711225,711226,711314,711315,711316,711317,711318,711319,711320,711321,711322,711323,711324,711325,711326,711414,711415,711416,711417,711418,711419,711420,711421,711422,711423,711424,711425,711426,711519,711520,711521,711522,711523,711524,711525,711526,711527,711528,711529,711530,711531,711532,711533,711534,711535,711536,711727,711728,711729,711730,711731,711732,711733,711734,711735,711736,711737,711738,711739,711740,711741,711742,711743,711744,711745,711746,711747,711748,711749,711750,711751,711752,711919,711920,711921,711922,711923,711924,711925,711926,711927,711928,711929,711930,711931,711932,711933,711934,711935,711936,712121,712122,712123,712124,712125,712126,712127,712128,712129,712130,712131,712132,712133,712134,712135,712136,712137,712138,712139,712140,712434,712435,712436,712437,712438,712439,712440,712441,712442,712443,712444,712445,712446,712447,712448,712449,712450,712451,712452,712453,712454,712455,712456,712457,712458,712459,712460,712461,712462,712463,712464,712465,712466,712517,712518,712519,712520,712521,712522,712523,712524,712525,712526,712527,712528,712529,712530,712531,712532,712615,712616,712617,712618,712619,712620,712621,712622,712623,712624,712625,712626,712627,712628,712707,712708,712709,712710,712711,712712,712805,712806,712807,712808);


-- mongoDB执行脚本
-- s2b下
-- 增加thirdPlatformTypes字段
db.getCollection("trade").update({"thirdPlatformType":"LINKED_MALL"},{$set:{thirdPlatformTypes:["LINKED_MALL"]}},{upsert: false,multi: true});
db.getCollection("providerTrade").update({"thirdPlatformType":"LINKED_MALL"},{$set:{thirdPlatformTypes:["LINKED_MALL"]}},{upsert: false,multi: true});
db.getCollection("thirdPlatformTrade").update({"thirdPlatformType":"LINKED_MALL"},{$set:{thirdPlatformTypes:["LINKED_MALL"]}},{upsert: false,multi: true});