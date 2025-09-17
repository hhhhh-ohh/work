DROP TABLE IF EXISTS `sbc-empower`.`wechat_login_set`;
CREATE TABLE `sbc-empower`.`wechat_login_set` (
  `wechat_set_id` varchar(32) NOT NULL COMMENT '微信授权登录配置主键',
  `mobile_server_status` tinyint(4) DEFAULT NULL COMMENT 'h5-微信授权登录是否启用 0 不启用， 1 启用',
  `mobile_app_id` varchar(60) DEFAULT NULL COMMENT 'h5-AppID(应用ID)',
  `mobile_app_secret` varchar(60) DEFAULT NULL COMMENT 'h5-AppSecret(应用密钥)',
  `pc_server_status` tinyint(4) DEFAULT NULL COMMENT 'pc-微信授权登录是否启用 0 不启用， 1 启用',
  `pc_app_id` varchar(60) DEFAULT NULL COMMENT 'pc-AppID(应用ID)',
  `pc_app_secret` varchar(60) DEFAULT NULL COMMENT 'pc-AppSecret(应用密钥)',
  `app_server_status` tinyint(4) DEFAULT NULL COMMENT 'app-微信授权登录是否启用 0 不启用， 1 启用',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '门店id 平台默认storeId=0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operate_person` varchar(45) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`wechat_set_id`),
  UNIQUE KEY `store_id_uni` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信授权登录配置';

INSERT INTO `sbc-empower`.`wechat_login_set` ( `wechat_set_id`, `mobile_server_status`, `mobile_app_id`, `mobile_app_secret`, `pc_server_status`, `pc_app_id`, `pc_app_secret`, `app_server_status`, `store_id`, `create_time`, `update_time`, `operate_person`)
SELECT
`wechat_set_id`,
`mobile_server_status`,
`mobile_app_id`,
`mobile_app_secret`,
`pc_server_status`,
`pc_app_id`,
`pc_app_secret`,
`app_server_status`,
`store_id`,
`create_time`,
`update_time`,
`operate_person`
FROM
	`sbc-setting`.wechat_login_set;

DROP TABLE IF EXISTS `sbc-empower`.`wechat_share_set`;
CREATE TABLE `sbc-empower`.`wechat_share_set` (
  `share_set_id` varchar(32) NOT NULL COMMENT '微信分享参数配置主键',
  `share_app_id` varchar(60) DEFAULT NULL COMMENT '微信公众号App ID',
  `share_app_secret` varchar(60) DEFAULT NULL COMMENT '微信公众号 App Secret',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '门店id 平台默认storeId=0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operate_person` varchar(45) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`share_set_id`),
  UNIQUE KEY `store_id_uni` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信分享参数配置';

INSERT INTO `sbc-empower`.`wechat_share_set` ( `share_set_id`, `share_app_id`, `share_app_secret`, `store_id`, `create_time`, `update_time`, `operate_person` )
SELECT
`share_set_id`,
`share_app_id`,
`share_app_secret`,
`store_id`,
`create_time`,
`update_time`,
`operate_person`
FROM
	`sbc-setting`.wechat_share_set;

DROP TABLE IF EXISTS `sbc-empower`.`pay_gateway`;
CREATE TABLE `sbc-empower`.`pay_gateway` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '网关名称',
  `is_open` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启:0关闭 1开启',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '网关类型：0单一支付，1聚合支付',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '商户id (平台默认值-1)',
  PRIMARY KEY (`id`,`name`) USING BTREE,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='网关';

INSERT INTO `sbc-empower`.`pay_gateway` ( `id`, `name`, `is_open`, `type`, `create_time`, `store_id` )
SELECT
`id`,
`name`,
`is_open`,
`type`,
`create_time`,
`store_id`
FROM
	`sbc-pay`.pay_gateway;

DROP TABLE IF EXISTS `sbc-empower`.`pay_gateway_config`;
CREATE TABLE `sbc-empower`.`pay_gateway_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gateway_id` int(11) NOT NULL COMMENT '网关id',
  `api_key` varchar(66) DEFAULT NULL COMMENT '身份标识',
  `secret` varchar(66) DEFAULT NULL COMMENT 'secret key',
  `account` varchar(60) DEFAULT NULL COMMENT '收款账号',
  `app_id` varchar(40) DEFAULT NULL COMMENT '第三方应用标识',
  `app_id2` varchar(40) DEFAULT NULL COMMENT '身份标志(聚合支付时代表微信)',
  `private_key` varchar(3000) DEFAULT NULL COMMENT '私钥',
  `public_key` varchar(1200) DEFAULT NULL COMMENT '公钥',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pc_back_url` varchar(255) DEFAULT NULL COMMENT 'PC前端后台接口地址',
  `pc_web_url` varchar(255) DEFAULT NULL COMMENT 'PC前端web地址',
  `boss_back_url` varchar(255) DEFAULT NULL COMMENT 'boss后台接口地址',
  `open_platform_app_id` varchar(40) DEFAULT NULL COMMENT '微信开放平台app_id---微信app支付使用',
  `open_platform_secret` varchar(66) DEFAULT NULL COMMENT '微信开放平台secret---微信app支付使用',
  `open_platform_api_key` varchar(66) DEFAULT NULL COMMENT '微信开放平台api key---微信app支付使用',
  `open_platform_account` varchar(60) DEFAULT NULL COMMENT '微信开放平台商户号---微信app支付使用',
  `wx_pay_certificate` blob COMMENT '微信公众平台支付证书',
  `wx_open_pay_certificate` blob COMMENT '微信开放平台支付证书',
  `store_id` bigint(20) DEFAULT '-1' COMMENT '商户id (平台默认值-1)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='网关配置';

INSERT INTO `sbc-empower`.`pay_gateway_config` (
	`id`,
	`gateway_id`,
	`api_key`,
	`secret`,
	`account`,
	`app_id`,
	`app_id2`,
	`private_key`,
	`public_key`,
	`create_time`,
	`pc_back_url`,
	`pc_web_url`,
	`boss_back_url`,
	`open_platform_app_id`,
	`open_platform_secret`,
	`open_platform_api_key`,
	`open_platform_account`,
	`wx_pay_certificate`,
	`wx_open_pay_certificate`,
	`store_id`
) SELECT
`id`,
`gateway_id`,
`api_key`,
`secret`,
`account`,
`app_id`,
`app_id2`,
`private_key`,
`public_key`,
`create_time`,
`pc_back_url`,
`pc_web_url`,
`boss_back_url`,
`open_platform_app_id`,
`open_platform_secret`,
`open_platform_api_key`,
`open_platform_account`,
`wx_pay_certificate`,
`wx_open_pay_certificate`,
`store_id`
FROM
	`sbc-pay`.pay_gateway_config;

DROP TABLE IF EXISTS `sbc-empower`.`pay_channel_item`;
CREATE TABLE `sbc-empower`.`pay_channel_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '支付渠道项名称',
  `gateway_name` varchar(45) NOT NULL COMMENT '支付渠道项类型-代替gateway_id和gateway做关联',
  `gateway_id` int(11) NOT NULL COMMENT '网关id',
  `channel` varchar(45) NOT NULL COMMENT '支付渠道',
  `is_open` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启:0关闭 1开启',
  `terminal` tinyint(1) DEFAULT NULL COMMENT '支付类型: 0 pc 1 h5 2 app',
  `code` varchar(20) DEFAULT NULL COMMENT '支付渠道项代码，同一支付项唯一',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `item_gateway_id_code_unique` (`gateway_id`,`code`) USING BTREE,
  KEY `pay_item_ibfk_1` (`gateway_id`),
  KEY `pay_item_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='支付渠道项';


INSERT INTO `sbc-empower`.`pay_channel_item` (
	`id`,
	`name`,
	`gateway_name`,
	`gateway_id`,
	`channel`,
	`is_open`,
	`terminal`,
	`code`,
	`create_time`
) SELECT
    `id`,
	`name`,
	`gateway_name`,
	`gateway_id`,
	`channel`,
	`is_open`,
	`terminal`,
	`code`,
	`create_time`
FROM
	`sbc-pay`.pay_channel_item;

DROP TABLE IF EXISTS `sbc-order`.`pay_trade_record`;
CREATE TABLE `sbc-order`.`pay_trade_record` (
                                    `id` varchar(45) CHARACTER SET utf8mb4 NOT NULL,
                                    `business_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
                                    `charge_id` varchar(27) CHARACTER SET utf8mb4 DEFAULT NULL,
                                    `apply_price` decimal(20,2) NOT NULL COMMENT '申请价格',
                                    `practical_price` decimal(20,2) DEFAULT NULL COMMENT '实际成功交易价格',
                                    `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败',
                                    `channel_item_id` int(11) NOT NULL COMMENT '支付渠道项id',
                                    `create_time` datetime NOT NULL COMMENT '创建时间',
                                    `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
                                    `finish_time` datetime DEFAULT NULL COMMENT '交易完成时间',
                                    `client_ip` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
                                    `trade_no` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
                                    `trade_type` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
                                    PRIMARY KEY (`id`),
                                    KEY `business_id` (`business_id`),
                                    KEY `charge_id` (`charge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易记录'
/*!50500 PARTITION BY RANGE  COLUMNS(id)
(PARTITION p2016 VALUES LESS THAN ('TP2017') ENGINE = InnoDB,
 PARTITION p2017 VALUES LESS THAN ('TP2018') ENGINE = InnoDB,
 PARTITION p2018 VALUES LESS THAN ('TP2019') ENGINE = InnoDB,
 PARTITION p2019 VALUES LESS THAN ('TP2020') ENGINE = InnoDB,
 PARTITION p2020 VALUES LESS THAN ('TP2021') ENGINE = InnoDB,
 PARTITION p2021 VALUES LESS THAN ('TP2022') ENGINE = InnoDB,
 PARTITION p2022 VALUES LESS THAN ('TP2023') ENGINE = InnoDB,
 PARTITION p2023 VALUES LESS THAN ('TP2024') ENGINE = InnoDB,
 PARTITION p2024 VALUES LESS THAN ('TP2025') ENGINE = InnoDB,
 PARTITION p2025 VALUES LESS THAN ('TP2026') ENGINE = InnoDB,
 PARTITION p2026 VALUES LESS THAN ('TP2027') ENGINE = InnoDB,
 PARTITION p2027 VALUES LESS THAN ('TP2028') ENGINE = InnoDB,
 PARTITION p2028 VALUES LESS THAN ('TP2029') ENGINE = InnoDB,
 PARTITION p2029 VALUES LESS THAN ('TP2030') ENGINE = InnoDB,
 PARTITION p2030 VALUES LESS THAN ('TP2031') ENGINE = InnoDB,
 PARTITION p2031 VALUES LESS THAN ('TP2032') ENGINE = InnoDB,
 PARTITION p2032 VALUES LESS THAN ('TP2033') ENGINE = InnoDB,
 PARTITION p2033 VALUES LESS THAN ('TP2034') ENGINE = InnoDB,
 PARTITION p2034 VALUES LESS THAN ('TP2035') ENGINE = InnoDB,
 PARTITION p2035 VALUES LESS THAN ('TP2036') ENGINE = InnoDB) */;


INSERT INTO `sbc-order`.`pay_trade_record` (
	`id`,
	`business_id`,
	`charge_id`,
	`apply_price`,
	`practical_price`,
	`status`,
	`channel_item_id`,
	`create_time`,
	`callback_time`,
	`finish_time`,
	`client_ip`,
	`trade_no`,
	`trade_type`
) SELECT
    `id`,
	`business_id`,
	`charge_id`,
	`apply_price`,
	`practical_price`,
	`status`,
	`channel_item_id`,
	`create_time`,
	`callback_time`,
	`finish_time`,
	`client_ip`,
	`trade_no`,
	`trade_type`
FROM
	`sbc-pay`.pay_trade_record;

DROP TABLE IF EXISTS `sbc-empower`.`mini_program_set`;
CREATE TABLE `sbc-empower`.`mini_program_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小程序配置主键',
  `type` tinyint(4) DEFAULT NULL COMMENT '小程序类型 0 微信小程序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态,0:未启用1:已启用',
  `app_id` varchar(60) DEFAULT NULL COMMENT '小程序AppID(应用ID)',
  `app_secret` varchar(60) DEFAULT NULL COMMENT '小程序AppSecret(应用密钥)',
  `del_flag`         tinyint(4)   NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
  `create_time`      datetime     NOT NULL COMMENT '创建时间',
  `create_person`    varchar(32)  NOT NULL COMMENT '创建人',
  `update_time`      datetime     NOT NULL COMMENT '更新时间',
  `update_person`    varchar(32)  NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4  COMMENT='小程序配置';


INSERT INTO `sbc-empower`.mini_program_set (type, remark, status, app_id,
                                             app_secret, del_flag, create_time, create_person,
                                             update_time, update_person)
SELECT 0  as `type`
     , `remark`    as `remark`
     , `status`    as `status`
     , replace(trim(substr(sc.context, locate('appId":', sc.context) + length('appId":'),
                           locate(',', replace(sc.context, '}', ','),
                                  locate('appId":', sc.context) + length('appId":')) -
                           (locate('appId":', sc.context) + length('appId":')))), '"',
               '') as `app_id`
     , replace(trim(substr(sc.context, locate('appSecret":', sc.context) + length('appSecret":'),
                           locate(',', replace(sc.context, '}', ','),
                                  locate('appSecret":', sc.context) + length('appSecret":')) -
                           (locate('appSecret":', sc.context) + length('appSecret":')))), '"',
               '') as `app_secret`
     , `del_flag`
     , `create_time`
     , ''        as `create_person`
     , `update_time`
     , ''        as `update_person`
FROM `sbc-setting`.`system_config` sc
WHERE sc.config_type='small_program_setting_customer';

-- 银联支付相关设置  begin ------------------
INSERT INTO `sbc-empower`.`pay_gateway` (`id`, `name`, `is_open`, `type`, `create_time`, `store_id`)
VALUES (11, 'UNIONPAY', 1, 0, '2021-03-15 09:43:14', -1);
INSERT INTO `sbc-empower`.`pay_channel_item` (`id`, `name`, `gateway_name`, `gateway_id`, `channel`, `is_open`, `terminal`, `code`, `create_time`)
VALUES (27, '银联云闪付PC', 'UNIONPAY', 11, 'unionpay', 1, 0, 'unionpay_pc', now());
INSERT INTO `sbc-empower`.`pay_channel_item` (`id`, `name`, `gateway_name`, `gateway_id`, `channel`, `is_open`, `terminal`, `code`, `create_time`)
VALUES (28, '银联云闪付H5支付', 'UNIONPAY', 11, 'unionpay', 1, 1, 'unionpay_h5', now());
-- 根据 开发 测试环境进行配置  https://shiyuan.kstore.shop
INSERT INTO `sbc-empower`.`pay_gateway_config` (`id`, `gateway_id`, `api_key`, `secret`, `account`, `app_id`,
`app_id2`, `private_key`, `public_key`, `create_time`, `pc_back_url`, `pc_web_url`, `boss_back_url`,
`open_platform_app_id`, `open_platform_secret`, `open_platform_api_key`, `open_platform_account`,
`wx_pay_certificate`, `wx_open_pay_certificate`, `store_id`)
VALUES (11, 11, '', '', '', '', '', '', '', now(),
'https://xxx', 'https://xxx/pcbff', 'https://xxx/bossbff', ' ', '', '', '', NULL, NULL, -1);
-- end ------

-- 验证码脚本
UPDATE `sbc-message`.`sms_template` set `del_flag` = 1 WHERE `template_type` = 0;

INSERT INTO `sbc-message`.`sms_template`(`template_name`, `template_content`, `remark`, `template_type`, `review_status`, `template_code`, `review_reason`, `sms_setting_id`, `del_flag`, `create_time`, `business_type`, `purpose`, `sign_id`, `open_flag`) VALUES ( '验证码模板', '验证码：${code}，有效期5分钟，请勿泄露给他人。', '申请说明：\n用于用户在使用验证码登录、修改密码时向用户发送验证码\n商城地址：https://s2b.wanmi.com/site/', 0, 1, 'SMS_215801041', NULL, 1, 0, '2021-04-23 15:18:31', 'VERIFICATION_CODE', '验证码模板', 48, 1);

-- 通知类脚本
INSERT INTO `sbc-message`.`sms_template`(`template_name`, `template_content`, `remark`, `template_type`, `review_status`, `template_code`, `review_reason`, `sms_setting_id`, `del_flag`, `create_time`, `business_type`, `purpose`, `sign_id`, `open_flag`) VALUES ('订单关闭通知', '很抱歉，您的订单${name}部分供应商商品采购失败，已为您自动退款~', '订单关闭通知', 1, 1, 'SMS_215344672', NULL, 1, 0, '2021-04-21 17:56:07', 'THIRD_PAY_ERROR_AUTO_REFUND', '订单关闭通知', NULL, 1);

INSERT INTO `sbc-message`.`sms_template`(`template_name`, `template_content`, `remark`, `template_type`, `review_status`, `template_code`, `review_reason`, `sms_setting_id`, `del_flag`, `create_time`, `business_type`, `purpose`, `sign_id`, `open_flag`) VALUES ('企业会员新增通知', '恭喜您成为企业会员，您可享受企业会员专享价，您的账号是：${account}，密码是: ${password}，快去商城采购吧~', '企业会员注册成功通知', 1, 1, 'SMS_215332837', NULL, 1, 0, '2021-04-19 14:49:48', 'ENTERPRISE_CUSTOMER_PASSWORD', '企业会员新增通知', 48, 1);


INSERT INTO `sbc-empower`.`pay_channel_item`(`id`, `name`, `gateway_name`, `gateway_id`, `channel`, `is_open`,
`terminal`, `code`, `create_time`) VALUES (29, '银联云闪付APP支付', 'UNIONPAY', 11, 'unionpay', 1, 2, 'unionpay_app', now());

-- 清理迁移表, 迁移数据
DROP TABLE `sbc-setting`.wechat_login_set;
DROP TABLE `sbc-setting`.wechat_share_set;
DELETE
FROM `sbc-setting`.`system_config`
WHERE config_type = 'small_program_setting_customer';

UPDATE `sbc-setting`.`authority`
SET authority_url = '/finance/settlement/detail/page',
request_type = 'POST'
WHERE
  authority_url = '/finance/settlement/detail/list/*';

UPDATE `sbc-setting`.`authority`
SET authority_url = '/finance/provider/settlement/detail/page',
request_type = 'POST'
WHERE
  authority_url = '/finance/provider/settlement/detail/list/*';
