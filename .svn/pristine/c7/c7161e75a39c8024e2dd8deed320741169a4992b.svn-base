CREATE DATABASE IF NOT EXISTS `sbc-empower` default charset utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 新建短信配置表
DROP TABLE IF EXISTS `sbc-empower`.`sms_setting`;
CREATE TABLE `sbc-empower`.`sms_setting`
(
    `id`                bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `access_key_id`     varchar(128) NULL DEFAULT NULL COMMENT '调用api参数key，或华信账号',
    `access_key_secret` varchar(128) NULL DEFAULT NULL COMMENT '调用api参数secret，或华信密码',
    `type`              tinyint(4)   NULL DEFAULT NULL COMMENT '短信平台类型：0：阿里云短信平台，1：华信短信平台',
    `status`            tinyint(4)   NULL DEFAULT NULL COMMENT '是否启用：0：未启用；1：启用',
    `huaxin_api_url`    varchar(128) NULL DEFAULT NULL COMMENT '华信接口地址',
    `huaxin_template`   varchar(128) NULL DEFAULT NULL COMMENT '华信短信报备和签名',
    `del_flag`          tinyint(4)   NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`       datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `create_person`     varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `update_time`       datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `update_person`     varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4 COMMENT = '短信配置';

-- 迁移阿里云短信配置
INSERT INTO `sbc-empower`.sms_setting(`id`, `access_key_id`, `access_key_secret`, `type`, `status`, `del_flag`,
                                      `create_time`)
SELECT `id`, `access_key_id`, `access_key_secret`, `type`, `status`, `del_flag`, `creat_time`
from `sbc-message`.sms_setting
where type = 0;

-- 迁移华信短信配置
INSERT INTO `sbc-empower`.sms_setting(`id`, `access_key_id`, `access_key_secret`, `type`, `status`, `del_flag`,
                                      `create_time`, `huaxin_api_url`, `huaxin_template`)
SELECT m.`id`,
       s.sms_pass    as `access_key_id`,
       s.sms_name    as `access_key_secret`,
       m.`type`,
       m.`status`,
       m.`del_flag`,
       s.`modify_time`,
       s.sms_url     as `huaxin_api_url`,
       s.sms_gateway as `huaxin_template`
from `sbc-message`.sms_setting m,
     `sbc-setting`.sys_sms s
where m.type = 1;

DROP TABLE IF EXISTS `sbc-empower`.`logistics_setting`;
CREATE TABLE `sbc-empower`.`logistics_setting`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `logistics_type`   tinyint(4)   NOT NULL COMMENT '配置类型 0: 快递100',
    `customer_key`     varchar(128) NOT NULL COMMENT '用户申请授权key',
    `delivery_key`     varchar(128) NOT NULL COMMENT '授权秘钥key',
    `real_time_status` tinyint(4)   NOT NULL COMMENT '实时查询是否开启 0: 否, 1: 是',
    `subscribe_status` tinyint(4)   NOT NULL COMMENT '是否开启订阅 0: 否, 1: 是',
    `callback_url`     varchar(128) NOT NULL COMMENT '回调地址',
    `del_flag`         tinyint(4)   NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `create_person`    varchar(32)  NOT NULL COMMENT '创建人',
    `update_time`      datetime     NOT NULL COMMENT '更新时间',
    `update_person`    varchar(32)  NOT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT = '物流配置';

-- APP消息推送配置表
DROP TABLE IF EXISTS `sbc-empower`.`app_push_setting`;
CREATE TABLE `sbc-empower`.`app_push_setting`
(
    `id`                 bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `platform_type`      tinyint(4)  NOT NULL COMMENT '推送平台类型：0：友盟',
    `android_key_id`     VARCHAR(64) NULL DEFAULT NULL COMMENT '安卓应用的Appkey',
    `android_msg_secret` VARCHAR(64) NULL DEFAULT NULL COMMENT '安卓应用的Message Secret',
    `android_key_secret` VARCHAR(64) NULL DEFAULT NULL COMMENT '安卓应用的服务器秘钥',
    `ios_key_id`         VARCHAR(64) NULL DEFAULT NULL COMMENT 'IOS应用的Appkey',
    `ios_key_secret`     VARCHAR(64) NULL DEFAULT NULL COMMENT 'IOS应用的服务器秘钥',
    `del_flag`           tinyint(4)  NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`        datetime    NULL DEFAULT NULL COMMENT '创建时间',
    `create_person`      varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `update_time`        datetime    NULL DEFAULT NULL COMMENT '更新时间',
    `update_person`      varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT = 'APP消息推送配置';

-- 迁移友盟推送配置
INSERT INTO `sbc-empower`.`app_push_setting` (`id`, `platform_type`, `android_key_id`, `android_msg_secret`,
                                              `android_key_secret`, `ios_key_id`, `ios_key_secret`, `del_flag`)
SELECT `id`,
       0 as `platform_type`,
       `android_key_id`,
       `android_msg_secret`,
       `android_key_secret`,
       `ios_key_id`,
       `ios_key_secret`,
       0 as `del_flag`
from `sbc-setting`.umeng_push_config;


CREATE TABLE `sbc-empower`.`undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 在线客服配置表
DROP TABLE IF EXISTS `sbc-empower`.`customer_service_setting`;
CREATE TABLE `sbc-empower`.`customer_service_setting`
(
    `id`               BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`         BIGINT(20)  NULL DEFAULT NULL COMMENT '店铺ID',
    `platform_type`    tinyint(4)  NOT NULL COMMENT '推送平台类型：0：QQ客服 1：阿里云客服',
    `status`           TINYINT(4)  NULL DEFAULT NULL COMMENT '在线客服是否启用 0 不启用， 1 启用',
    `service_title`    VARCHAR(64) NULL DEFAULT NULL COMMENT '客服标题',
    `effective_pc`     TINYINT(4)  NULL DEFAULT NULL COMMENT '生效终端pc 0 不生效 1 生效',
    `effective_app`    TINYINT(4)  NULL DEFAULT NULL COMMENT '生效终端App 0 不生效 1 生效',
    `effective_mobile` TINYINT(4)  NULL DEFAULT NULL COMMENT '生效终端移动版 0 不生效 1 生效',
    `service_key`      VARCHAR(64) NULL DEFAULT NULL COMMENT '客服key',
    `service_url`      VARCHAR(64) NULL DEFAULT NULL COMMENT '客服链接',
    `del_flag`         TINYINT(4)  NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`      datetime    NULL DEFAULT NULL COMMENT '创建时间',
    `create_person`    VARCHAR(32) NULL DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime    NULL DEFAULT NULL COMMENT '更新时间',
    `update_person`    VARCHAR(32) NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_store_id` (`store_id`) USING BTREE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4 COMMENT = '在线客服配置';

DROP TABLE IF EXISTS `sbc-empower`.`customer_service_setting_item`;
CREATE TABLE `sbc-empower`.`customer_service_setting_item`
(
    `service_item_id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '在线客服座席id',
    `store_id`                 bigint(20)  NULL DEFAULT NULL COMMENT '店铺ID',
    `customer_service_id`      bigint(20)  NOT NULL COMMENT '在线客服主键',
    `customer_service_name`    varchar(10) NOT NULL COMMENT '客服昵称',
    `customer_service_account` varchar(20) NOT NULL COMMENT '客服账号',
    `del_flag`                 TINYINT(4)  NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`              datetime    NULL DEFAULT NULL COMMENT '创建时间',
    `create_person`            VARCHAR(32) NULL DEFAULT NULL COMMENT '创建人',
    `update_time`              datetime    NULL DEFAULT NULL COMMENT '更新时间',
    `update_person`            VARCHAR(32) NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`service_item_id`) USING BTREE,
    INDEX `idx_store_id` (`store_id`) USING BTREE,
    INDEX `idx_customer_service_id` (`customer_service_id`) USING BTREE
) ENGINE = InnoDB
  CHARSET = utf8mb4 COMMENT = '在线客服座席表';

-- 迁移QQ客服配置
INSERT INTO `sbc-empower`.`customer_service_setting` (`id`, `store_id`, `platform_type`, `status`, `service_title`,
                                                      `effective_pc`, `effective_app`, `effective_mobile`,
                                                      `service_key`, `service_url`, `del_flag`, `create_time`,
                                                      `create_person`, `update_time`, `update_person`)
SELECT `online_service_id`,
       `store_id`,
       0                as `platform_type`,
       `server_status`,
       `service_title`,
       `effective_pc`,
       `effective_app`,
       `effective_mobile`,
       null             as `service_key`,
       null             as `service_url`,
       `del_flag`,
       `create_time`,
       `operate_person` as `create_person`,
       `update_time`,
       `operate_person` as `update_person`
from `sbc-setting`.online_service;

INSERT INTO `sbc-empower`.`customer_service_setting_item` (`service_item_id`, `store_id`, `customer_service_id`,
                                                           `customer_service_name`, `customer_service_account`,
                                                           `del_flag`, `create_time`, `create_person`, `update_time`,
                                                           `update_person`)
SELECT `service_item_id`,
       `store_id`,
       `online_service_id`,
       `customer_service_name`,
       `customer_service_account`,
       `del_flag`,
       `create_time`,
       `operate_person` as `create_person`,
       `update_time`,
       `operate_person` as `update_person`
from `sbc-setting`.online_service_item;

-- 迁移阿里云客服配置
INSERT INTO `sbc-empower`.`customer_service_setting` (`store_id`, `platform_type`, `status`, `service_title`,
                                                      `effective_pc`, `effective_app`, `effective_mobile`,
                                                      `service_key`, `service_url`, `del_flag`, `create_time`,
                                                      `create_person`, `update_time`, `update_person`)
SELECT '0'       as `store_id`
     , 1         as `platform_type`
     , `status`
     , replace(trim(substr(sc.context
    , locate('title":', sc.context) + length('title":')
    , locate(',', replace(sc.context, '}', ','), locate('title":', sc.context) + length('title":'))
                               - (locate('title":', sc.context) + length('title":'))
    )), '"', '') as `service_title`
     , null      as `effective_pc`
     , null      as `effective_app`
     , null      as `effective_mobile`
     , replace(trim(substr(sc.context
    , locate('key":', sc.context) + length('key":')
    , locate(',', replace(sc.context, '}', ','), locate('key":', sc.context) + length('key":'))
                               - (locate('key":', sc.context) + length('key":'))
    )), '"', '') as `service_key`
     , replace(trim(substr(sc.context
    , locate('aliyunChat":', sc.context) + length('aliyunChat":')
    , locate(',', replace(sc.context, '}', ','), locate('aliyunChat":', sc.context) + length('aliyunChat":'))
                               - (locate('aliyunChat":', sc.context) + length('aliyunChat":'))
    )), '"', '') as `service_url`
     , `del_flag`
     , `create_time`
     , null      as `create_person`
     , `update_time`
     , null      as `update_person`
FROM `sbc-setting`.`system_config` sc
WHERE sc.`config_key` = 'online_service';

-- 迁移快递100数据
INSERT INTO `sbc-empower`.logistics_setting (logistics_type, customer_key, delivery_key, real_time_status,
                                             subscribe_status, callback_url, del_flag, create_time, create_person,
                                             update_time, update_person)
SELECT 0           as `logistics_type`

     , replace(trim(substr(sc.context, locate('customerKey":', sc.context) + length('customerKey":'),
                           locate(',', replace(sc.context, '}', ','),
                                  locate('customerKey":', sc.context) + length('customerKey":')) -
                           (locate('customerKey":', sc.context) + length('customerKey":')))), '"',
               '') as `customer_key`

     , replace(trim(substr(sc.context, locate('deliveryKey":', sc.context) + length('deliveryKey":'),
                           locate(',', replace(sc.context, '}', ','),
                                  locate('deliveryKey":', sc.context) + length('deliveryKey":')) -
                           (locate('deliveryKey":', sc.context) + length('deliveryKey":')))), '"',
               '') as `delivery_key`

     , `status`    as `real_time_status`

     , replace(trim(substr(sc.context, locate('subscribeStatus":', sc.context) + length('subscribeStatus":'),
                           locate(',', replace(sc.context, '}', ','),
                                  locate('subscribeStatus":', sc.context) + length('subscribeStatus":')) -
                           (locate('subscribeStatus":', sc.context) + length('subscribeStatus":')))), '"',
               '') as `subscribe_status`
     , replace(trim(substr(sc.context, locate('callBackUrl":', sc.context) + length('callBackUrl":'),
                           locate(',', replace(sc.context, '}', ','),
                                  locate('callBackUrl":', sc.context) + length('callBackUrl":')) -
                           (locate('callBackUrl":', sc.context) + length('callBackUrl":')))), '"',
               '') as `callback_url`
     , `del_flag`
     , `create_time`
     , ''        as `create_person`
     , `update_time`
     , ''        as `update_person`

FROM `sbc-setting`.`system_config` sc
WHERE sc.`config_key` = 'kuaidi100';

-- 删除快递100数据, 逻辑删除, 根据需要可以选择物理删除
/** 慎重选择, 防止操作不当丢失配置数据
delete
from `sbc-setting`.system_config
where config_key = 'kuaidi100';
 */
update `sbc-setting`.system_config
set del_flag = '1'
where config_key = 'kuaidi100';

-- 字段扩容
ALTER TABLE `sbc-message`.`sms_sign`
    MODIFY COLUMN `review_reason` varchar(800) NULL DEFAULT NULL COMMENT '审核原因'
        AFTER `review_status`;

-- 对象存储配置
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('resource_server', 'jdYun', '京东云', NULL, 0, '{}', NOW(), NOW(), 0);
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('resource_server', 'minio', 'minio', NULL, 0, '{}', NOW(), NOW(), 0);

-- 物流记录表
DROP TABLE IF EXISTS `sbc-empower`.`logistics_log`;
CREATE TABLE `sbc-empower`.`logistics_log`
(
    `id`               varchar(32)  NOT NULL COMMENT '主键',
    `store_id`         bigint(20)   NULL DEFAULT NULL COMMENT '店铺id',
    `order_no`         varchar(45)  NULL DEFAULT NULL COMMENT '订单号',
    `logistic_no`      varchar(50)  NULL DEFAULT NULL COMMENT '快递单号',
    `customer_id`      varchar(32)  NULL DEFAULT NULL COMMENT '购买人编号',
    `end_flag`         tinyint(4)   NULL DEFAULT NULL COMMENT '是否结束',
    `status`           varchar(20)  NULL DEFAULT NULL COMMENT '监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑',
    `state`            varchar(20)  NULL NULL COMMENT '快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态',
    `message`          varchar(128) NULL DEFAULT NULL COMMENT '监控状态相关消息，如:3天查询无记录，60天无变化',
    `auto_check`       varchar(128) NULL DEFAULT NULL COMMENT '快递公司编码是否出过错',
    `com_old`          varchar(50)  NULL DEFAULT NULL COMMENT '本地物流公司标准编码',
    `com_new`          varchar(50)  NULL DEFAULT NULL COMMENT '快递纠正新编码',
    `is_check`         varchar(20)  NULL NULL COMMENT '是否签收标记',
    `phone`            varchar(11)  NULL DEFAULT NULL COMMENT '手机号',
    `from`             varchar(200) NULL DEFAULT NULL COMMENT '出发地城市',
    `to`               varchar(200) NULL DEFAULT NULL COMMENT '目的地城市',
    `goods_img`        varchar(250) NULL DEFAULT NULL COMMENT '商品图片',
    `goods_name`       varchar(128) NULL DEFAULT NULL COMMENT '商品名称',
    `success_flag`     tinyint(4)   NULL NULL COMMENT '订阅申请状态',
    `check_time`       datetime     NULL DEFAULT NULL COMMENT '签收时间',
    `deliver_id`       varchar(50)  NULL DEFAULT NULL COMMENT '本地发货单号',
    `has_details_flag` tinyint(4)   NULL NULL COMMENT '是否有物流详细信息',
    `del_flag`         tinyint(4)   NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`      datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `create_person`    varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `update_person`    varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '物流记录';


-- 物流记录明细表
DROP TABLE IF EXISTS `sbc-empower`.`logistics_log_detail`;
CREATE TABLE `sbc-empower`.`logistics_log_detail`
(
    `id`               bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `context`          varchar(128) NULL DEFAULT NULL COMMENT '内容上海分拨中心/装件入车扫',
    `time`             varchar(128) NULL DEFAULT NULL COMMENT '时间，原始格式',
    `status`           tinyint(4)   NULL NULL COMMENT '本数据元对应的签收状态',
    `area_code`        varchar(10)  NULL NULL COMMENT '本数据元对应的行政区域的编码',
    `area_name`        varchar(128) NULL DEFAULT NULL COMMENT '本数据元对应的行政区域的名称',
    `logistics_log_id` varchar(32)  NOT NULL COMMENT '物流记录id',
    `del_flag`         tinyint(4)   NOT NULL COMMENT '删除标识：0：未删除；1：已删除',
    `create_time`      datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `create_person`    varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `update_person`    varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT = '物流记录明细';

-- 修复字段长度不足的bug
ALTER TABLE `sbc-message`.`push_send_node`
    MODIFY COLUMN `node_code` varchar (50) NULL DEFAULT NULL COMMENT '节点code' AFTER `node_type`;
update `sbc-message`.`push_send_node`
set `node_code` = 'FRIEND_REGISTER_SUCCESS_NO_REWARD'
where `node_code` = 'FRIEND_REGISTER_SUCCESS_NO_REWAR';
update `sbc-message`.`push_send_node`
set `node_code` = 'FRIEND_REGISTER_SUCCESS_HAS_REWARD'
where `node_code` = 'FRIEND_REGISTER_SUCCESS_HAS_REWA';

-- xxl调度日志加索引
ALTER TABLE `xxl-job`.`xxl_job_qrtz_trigger_log`
    ADD INDEX `idx_job_id`(`job_id`) USING BTREE;

-- 删除废弃的PresellSale相关表
DROP TABLE IF EXISTS `sbc-goods`.`presell_sale`;
DROP TABLE IF EXISTS `sbc-goods`.`presell_sale_goods`;

-- 开放平台迭代一，权限脚本修改
update `sbc-setting`.`authority` set authority_url = '/open-api-setting/reset-secret/*' where authority_id = 'ff808081791443150179172cc611000d';

-- 开放平台迭代一，权限脚本修改（uat，线上已执行）
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808179550b730179553907d70000', 4, 'ff808081791443150179171f4a350004', '查看开放权限', NULL, '/open-api-setting/*', 'GET', NULL, 2, '2021-05-10 15:41:12', 0);

-- 菜单名修改
update `sbc-setting`.`menu_info` set menu_name='收款账户' where menu_url = '/vendor-payment-account' and system_type_cd = '6';
update `sbc-setting`.`menu_info` set menu_name='拼团活动' where menu_url = '/groupon-activity-list' and system_type_cd = '3';
update `sbc-setting`.`menu_info` set menu_name='预售活动' where menu_url = '/presale-list' and system_type_cd = '4';
update `sbc-setting`.`menu_info` set menu_name='预约购买' where menu_url = '/reservation-list' and system_type_cd = '4';


-- 删除已被迁移到empower中的配置表（最后执行）
DROP TABLE IF EXISTS `sbc-message`.`sms_setting`;
DROP TABLE IF EXISTS `sbc-setting`.`sys_sms`;
DROP TABLE IF EXISTS `sbc-setting`.`umeng_push_config`;
DROP TABLE IF EXISTS `sbc-setting`.`online_service`;
DROP TABLE IF EXISTS `sbc-setting`.`online_service_item`;
update `sbc-setting`.system_config
set del_flag = '1'
where config_key = 'online_service';