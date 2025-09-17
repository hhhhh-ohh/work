-- 付费会员配置
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('paying_member', 'paying_member', '付费会员配置', NULL, 1, '{\"enable\":0,\"extendMap\":{},\"hasBackground\":\"\",\"hasColor\":\"#FFE6B3\",\"hasContext\":\"\",\"label\":\"\",\"notHasBackground\":\"\",\"notHasColor\":\"#FFE6B3\",\"notHasContent\":\"\",\"openFlag\":false}', '2022-05-13 10:21:37', NULL, 0);

-- 付费会员协议
ALTER TABLE `sbc-setting`.`base_config`
ADD COLUMN `paying_member_content` longtext NULL COMMENT '付费会员协议';

DROP TABLE IF EXISTS `sbc-customer`.paying_member_level;
CREATE TABLE `sbc-customer`.paying_member_level(
                                                   level_id INT(11) NOT NULL AUTO_INCREMENT  COMMENT '付费会员等级id' ,
                                                   level_name VARCHAR(55)    COMMENT '付费会员等级名称' ,
                                                   level_nick_name VARCHAR(55)    COMMENT '付费会员等级昵称' ,
                                                   level_state TINYINT(1)    COMMENT '付费会员等级状态 0.开启 1.暂停' ,
                                                   level_back_ground_type TINYINT(1)    COMMENT '付费会员等级背景类型：0.背景色 1.背景图' ,
                                                   level_back_ground_detail VARCHAR(128)    COMMENT '付费会员等级背景详情' ,
                                                   level_font_color VARCHAR(55)    COMMENT '付费会员等级字体颜色' ,
                                                   level_store_range TINYINT(1)    COMMENT '付费会员等级商家范围：0.自营商家 1.自定义选择' ,
                                                   level_discount_type TINYINT(1)    COMMENT '付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置' ,
                                                   level_all_discount DECIMAL(11,2)    COMMENT '付费会员等级所有商品统一设置 折扣' ,
                                                   create_time DATETIME    COMMENT '创建时间' ,
                                                   create_person VARCHAR(32)    COMMENT '' ,
                                                   update_time DATETIME    COMMENT '更新时间' ,
                                                   update_person VARCHAR(32)    COMMENT '' ,
                                                   del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                   PRIMARY KEY (level_id)
)  COMMENT = '付费会员等级表';

DROP TABLE IF EXISTS `sbc-customer`.paying_member_price;
CREATE TABLE `sbc-customer`.paying_member_price(
                                                   price_id int(11) NOT NULL AUTO_INCREMENT  COMMENT '付费设置id' ,
                                                   level_id int(11)    COMMENT '付费会员等级id' ,
                                                   price_num int(11)    COMMENT '付费设置数量 ，例如3个月' ,
                                                   price_total DECIMAL(20,2)    COMMENT '付费设置总金额，例如上述3个月90元' ,
                                                   create_time DATETIME    COMMENT '创建时间' ,
                                                   create_person VARCHAR(32)    COMMENT '' ,
                                                   update_time DATETIME    COMMENT '更新时间' ,
                                                   update_person VARCHAR(32)    COMMENT '' ,
                                                   del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                   PRIMARY KEY (price_id)
)  COMMENT = '付费设置表';


DROP TABLE IF EXISTS `sbc-customer`.paying_member_customer_rel;
CREATE TABLE `sbc-customer`.paying_member_customer_rel(
                                                          id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
                                                          level_id INT(11)    COMMENT '付费会员等级id' ,
                                                          customer_id VARCHAR(32)    COMMENT '会员id' ,
                                                          open_time DATETIME    COMMENT '开通时间' ,
                                                          expiration_date DATE    COMMENT '会员到期时间' ,
                                                          discount_amount DECIMAL(20,2)    COMMENT '总共优惠金额' ,
                                                          create_time DATETIME    COMMENT '创建时间' ,
                                                          create_person VARCHAR(32)    COMMENT '' ,
                                                          update_time DATETIME    COMMENT '更新时间' ,
                                                          update_person VARCHAR(32)    COMMENT '' ,
                                                          del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                          PRIMARY KEY (id)
)  COMMENT = '客户与付费会员等级关联表';


DROP TABLE IF EXISTS `sbc-customer`.paying_member_store_rel;
CREATE TABLE `sbc-customer`.paying_member_store_rel(
                                                       id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
                                                       level_id int(11)    COMMENT '付费会员等级id' ,
                                                       store_id bigint    COMMENT '店铺id' ,
                                                       store_name VARCHAR(150)    COMMENT '店铺名称' ,
                                                       company_code VARCHAR(32)    COMMENT '公司编码' ,
                                                       create_time DATETIME    COMMENT '创建时间' ,
                                                       create_person VARCHAR(32)    COMMENT '' ,
                                                       update_time DATETIME    COMMENT '更新时间' ,
                                                       update_person VARCHAR(32)    COMMENT '' ,
                                                       del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                       PRIMARY KEY (id)
)  COMMENT = '商家与付费会员等级关联表';

DROP TABLE IF EXISTS `sbc-customer`.paying_member_rights_rel;
CREATE TABLE `sbc-customer`.paying_member_rights_rel(
                                                        id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
                                                        price_id int(11)    COMMENT '付费设置id' ,
                                                        level_id int(11)    COMMENT '付费会员等级id' ,
                                                        rights_id int(10)    COMMENT '权益id' ,
                                                        create_time DATETIME    COMMENT '创建时间' ,
                                                        create_person VARCHAR(32)    COMMENT '' ,
                                                        update_time DATETIME    COMMENT '更新时间' ,
                                                        update_person VARCHAR(32)    COMMENT '' ,
                                                        del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                        PRIMARY KEY (id)
)  COMMENT = '权益与付费会员等级关联表';

DROP TABLE IF EXISTS `sbc-customer`.paying_member_discount_rel;
CREATE TABLE `sbc-customer`.paying_member_discount_rel(
                                                          id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
                                                          level_id int(11)    COMMENT '付费会员等级id' ,
                                                          goods_info_id VARCHAR(32)    COMMENT 'skuId' ,
                                                          discount DECIMAL(11,2)    COMMENT '折扣' ,
                                                          create_time DATETIME    COMMENT '创建时间' ,
                                                          create_person VARCHAR(32)    COMMENT '' ,
                                                          update_time DATETIME    COMMENT '更新时间' ,
                                                          update_person VARCHAR(32)    COMMENT '' ,
                                                          del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                          PRIMARY KEY (id)
)  COMMENT = '折扣商品与付费会员等级关联表';


DROP TABLE IF EXISTS `sbc-customer`.paying_member_recommend_rel;
CREATE TABLE `sbc-customer`.paying_member_recommend_rel(
                                                           id bigint NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
                                                           level_id int(11)    COMMENT '付费会员等级id' ,
                                                           goods_info_id VARCHAR(32)    COMMENT 'skuId' ,
                                                           create_time DATETIME    COMMENT '创建时间' ,
                                                           create_person VARCHAR(32)    COMMENT '' ,
                                                           update_time DATETIME    COMMENT '更新时间' ,
                                                           update_person VARCHAR(32)    COMMENT '' ,
                                                           del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                           PRIMARY KEY (id)
)  COMMENT = '推荐商品与付费会员等级关联表';

DROP TABLE IF EXISTS `sbc-order`.paying_member_record;
CREATE TABLE `sbc-order`.paying_member_record(
                                                 record_id VARCHAR(45) NOT NULL   COMMENT '记录id' ,
                                                 level_id INT(11)    COMMENT '付费会员等级id' ,
                                                 level_name VARCHAR(55)    COMMENT '付费会员等级名称' ,
                                                 level_nick_name VARCHAR(55)    COMMENT '付费会员等级昵称' ,
                                                 customer_id VARCHAR(32)    COMMENT '会员id' ,
                                                 customer_name VARCHAR(128)    COMMENT '会员名称' ,
                                                 pay_num int(11)    COMMENT '支付数量' ,
                                                 pay_amount DECIMAL(20,2)    COMMENT '支付金额' ,
                                                 pay_time DATETIME    COMMENT '支付时间' ,
                                                 expiration_date DATE    COMMENT '会员到期时间' ,
                                                 level_state TINYINT(4)    COMMENT '等级状态：0.生效中，1.未生效，2.已过期，3.已退款' ,
                                                 already_discount_amount DECIMAL(20,2)    COMMENT '已优惠金额' ,
                                                 already_receive_point bigint(10)    COMMENT '已领积分' ,
                                                 return_amount DECIMAL(20,2)    COMMENT '退款金额' ,
                                                 return_cause VARCHAR(255)  COMMENT '退款原因' ,
                                                 return_coupon TINYINT(1)    COMMENT '退款回收券 0.是，1.否' ,
                                                 return_point TINYINT(1)    COMMENT '退款回收积分 0.是，1.否' ,
                                                 rights_ids VARCHAR(255)    COMMENT '权益id集合' ,
                                                 first_open TINYINT(1)    COMMENT '首次开通 0.是，1.否' ,
                                                 create_time DATETIME    COMMENT '创建时间' ,
                                                 create_person VARCHAR(32)    COMMENT '' ,
                                                 update_time DATETIME    COMMENT '更新时间' ,
                                                 update_person VARCHAR(32)    COMMENT '' ,
                                                 del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                 PRIMARY KEY (record_id)
)  COMMENT = '付费记录表';

DROP TABLE IF EXISTS `sbc-order`.paying_member_record_temp;
CREATE TABLE `sbc-order`.paying_member_record_temp(
                                                     record_id VARCHAR(45) NOT NULL   COMMENT '记录id' ,
                                                     level_id INT(11)    COMMENT '付费会员等级id' ,
                                                     level_name VARCHAR(55)    COMMENT '付费会员等级名称' ,
                                                     level_nick_name VARCHAR(55)    COMMENT '付费会员等级昵称' ,
                                                     customer_id VARCHAR(32)    COMMENT '会员id' ,
                                                     customer_name VARCHAR(128)    COMMENT '会员名称' ,
                                                     pay_num int(11)    COMMENT '支付数量' ,
                                                     pay_amount DECIMAL(20,2)    COMMENT '支付金额' ,
                                                     pay_time DATETIME    COMMENT '支付时间' ,
                                                     expiration_date DATE    COMMENT '会员到期时间' ,
                                                     pay_state TINYINT(4)    COMMENT '支付状态：0.未支付，1.已支付' ,
                                                     rights_ids VARCHAR(255)    COMMENT '权益id集合' ,
                                                     create_time DATETIME    COMMENT '创建时间' ,
                                                     create_person VARCHAR(32)    COMMENT '' ,
                                                     update_time DATETIME    COMMENT '更新时间' ,
                                                     update_person VARCHAR(32)    COMMENT '' ,
                                                     del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                     PRIMARY KEY (record_id)
)  COMMENT = '付费记录临时表';

DROP TABLE IF EXISTS `sbc-order`.paying_member_pay_record;
CREATE TABLE `sbc-order`.paying_member_pay_record(
                                                     id VARCHAR(45) NOT NULL   COMMENT '主键' ,
                                                     business_id VARCHAR(45)    COMMENT '业务id' ,
                                                     charge_id VARCHAR(27)    COMMENT '' ,
                                                     apply_price decimal(20,2) NOT NULL   COMMENT '申请价格' ,
                                                     practical_price decimal(20,2)    COMMENT '实际成功交易价格' ,
                                                     status tinyint(1) NOT NULL  DEFAULT 0 COMMENT '状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败' ,
                                                     channel_item_id int NOT NULL   COMMENT '支付渠道项id' ,
                                                     callback_time datetime    COMMENT '回调时间' ,
                                                     finish_time datetime    COMMENT '交易完成时间' ,
                                                     create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                                                     create_person VARCHAR(32)    COMMENT '' ,
                                                     update_time DATETIME    COMMENT '更新时间' ,
                                                     update_person VARCHAR(32)    COMMENT '' ,
                                                     del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                     PRIMARY KEY (id)
)  COMMENT = '付费会员支付记录表';

DROP TABLE IF EXISTS `sbc-customer`.`paying_member_customer_rel`;
CREATE TABLE `sbc-customer`.`paying_member_customer_rel` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`level_id` int(11) DEFAULT NULL COMMENT '付费会员等级id',
`customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员id',
`open_time` datetime DEFAULT NULL COMMENT '开通时间',
`expiration_date` date DEFAULT NULL COMMENT '会员到期时间',
`discount_amount` decimal(20,2) DEFAULT NULL COMMENT '总共优惠金额',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识：0：未删除；1：已删除',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户与付费会员等级关联表';

-- customer 新增会员等级升级时间
ALTER TABLE `sbc-customer`.`customer`
ADD COLUMN `upgrade_time` datetime DEFAULT NULL COMMENT '等级升级时间';
-- 如果该字段已被同步，可以不执行
ALTER TABLE `s2b_statistics`.`replay_customer`
    ADD COLUMN `upgrade_time` datetime DEFAULT NULL COMMENT '等级升级时间';

-- 优惠券码
ALTER TABLE `sbc-marketing`.`coupon_code_0`
ADD COLUMN `marketing_customer_type` tinyint(2) DEFAULT 0 COMMENT '营销会员类型 0、普通会员 1、付费会员',
ADD COLUMN `paying_member_record_id` varchar(45) NULL COMMENT '付费会员记录id';

ALTER TABLE `sbc-marketing`.`coupon_code_1`
ADD COLUMN `marketing_customer_type` tinyint(2) DEFAULT 0 COMMENT '营销会员类型 0、普通会员 1、付费会员',
ADD COLUMN `paying_member_record_id` varchar(45) NULL COMMENT '付费会员记录id';

ALTER TABLE `sbc-marketing`.`coupon_code_2`
    ADD COLUMN `marketing_customer_type` tinyint(2) DEFAULT 0 COMMENT '营销会员类型 0、普通会员 1、付费会员',
ADD COLUMN `paying_member_record_id` varchar(45) NULL COMMENT '付费会员记录id';

ALTER TABLE `sbc-marketing`.`coupon_code_3`
ADD COLUMN `marketing_customer_type` tinyint(2) DEFAULT 0 COMMENT '营销会员类型 0、普通会员 1、付费会员',
ADD COLUMN `paying_member_record_id` varchar(45) NULL COMMENT '付费会员记录id';

ALTER TABLE `sbc-marketing`.`coupon_code_4`
ADD COLUMN `marketing_customer_type` tinyint(2) DEFAULT 0 COMMENT '营销会员类型 0、普通会员 1、付费会员',
ADD COLUMN `paying_member_record_id` varchar(45) NULL COMMENT '付费会员记录id';

ALTER TABLE `s2b_statistics`.`replay_coupon_code`
    ADD COLUMN `marketing_customer_type` tinyint(2) DEFAULT 0 COMMENT '营销会员类型 0、普通会员 1、付费会员',
ADD COLUMN `paying_member_record_id` varchar(45) NULL COMMENT '付费会员记录id';

-- xxl-job
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, '0 0 1 * * ?', '权益-券礼包周期发放', '2022-05-21 18:05:08', '2022-05-21 18:06:15', '许云鹏', '', 'FIRST', 'customerLevelRightsCouponRepeatJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-05-21 18:05:08', '');

-- 数据
DROP TABLE IF EXISTS  `s2b_statistics`.`replay_paying_member_customer_rel`;
CREATE TABLE `s2b_statistics`.`replay_paying_member_customer_rel` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`level_id` int(11) DEFAULT NULL COMMENT '付费会员等级id',
`customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员id',
`open_time` datetime DEFAULT NULL COMMENT '开通时间',
`expiration_date` date DEFAULT NULL COMMENT '会员到期时间',
`discount_amount` decimal(20,2) DEFAULT NULL COMMENT '总共优惠金额',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识：0：未删除；1：已删除',
PRIMARY KEY (`id`),
KEY `idx_customer_id` (`customer_id`),
KEY `idx_open_time` (`open_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户与付费会员等级关联表';

DROP TABLE IF EXISTS  `s2b_statistics`.`paymember_area_distribute`;
CREATE TABLE `s2b_statistics`.`paymember_area_distribute` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`city_id` bigint(10) NOT NULL COMMENT '客户所在城市id',
`num` bigint(10) NOT NULL COMMENT '当前城市下客户人数',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报表生成时间',
`target_date` date NOT NULL COMMENT '目标数据日期',
PRIMARY KEY (`id`,`target_date`),
KEY `idx_target_date` (`target_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='付费会员地区分布统计报表';

DROP TABLE IF EXISTS  `s2b_statistics`.`paymember_grow`;
CREATE TABLE `s2b_statistics`.`paymember_grow` (
`id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
`base_date` date NOT NULL DEFAULT '2021-08-16' COMMENT '日期',
`all_count` bigint(20) DEFAULT '0' COMMENT '付费会员总数',
`day_growth_count` bigint(20) DEFAULT '0' COMMENT '新增付费会员数',
`day_renewal_count` bigint(20) NULL DEFAULT '0' COMMENT '续费会员数',
`day_overtime_count` bigint(20) NULL DEFAULT '0' COMMENT '到期未续费会员数',
`create_tm` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`,`base_date`),
KEY `idx_base_date` (`base_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='每日付费会员增长统计表';

DROP TABLE IF EXISTS  `s2b_statistics`.`replay_paying_member_record`;
CREATE TABLE `s2b_statistics`.`replay_paying_member_record` (
`record_id` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '记录id',
`level_id` int(11) DEFAULT NULL COMMENT '付费会员等级id',
`level_name` varchar(55) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付费会员等级名称',
`level_nick_name` varchar(55) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付费会员等级昵称',
`customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员id',
`customer_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员名称',
`pay_num` int(11) DEFAULT NULL COMMENT '支付数量',
`pay_amount` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
`pay_time` datetime DEFAULT NULL COMMENT '支付时间',
`expiration_date` date DEFAULT NULL COMMENT '会员到期时间',
`level_state` tinyint(4) DEFAULT NULL COMMENT '等级状态：0.生效中，1.未生效，2.已过期，3.已退款',
`already_discount_amount` decimal(20,2) DEFAULT NULL COMMENT '已优惠金额',
`already_receive_point` bigint(10) DEFAULT NULL COMMENT '已领积分',
`return_amount` decimal(20,2) DEFAULT NULL COMMENT '退款金额',
`return_cause` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款原因',
`return_coupon` tinyint(1) DEFAULT NULL COMMENT '退款回收券 0.是，1.否',
`return_point` tinyint(1) DEFAULT NULL COMMENT '退款回收积分 0.是，1.否',
`rights_ids` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权益id集合',
`first_open` tinyint(1) DEFAULT NULL COMMENT '首次开通 0.是，1.否',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`create_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`update_person` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
`del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识：0：未删除；1：已删除',
PRIMARY KEY (`record_id`),
KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='付费记录表';


INSERT INTO `xxl-job`.xxl_job_qrtz_trigger_info (job_group, job_cron, job_desc, add_time, update_time, author, alarm_email, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid) VALUES (1, '0 0 0 * * ?', '付费会员状态变更', '2022-05-27 10:25:23', '2022-05-27 10:25:23', '张浩', '', 'FIRST', 'PayingMemberUpdateStateJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-05-27 10:25:23', '');
-- 付费会员统计报表
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (2, '0 0 1 * * ?', '付费会员统计报表-昨天', '2022-05-27 11:02:15', '2022-05-27 11:02:15', '许云鹏', '', 'FIRST', 'payMemberJobHandler', '1', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-05-27 11:02:15', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (2, '0 */5 * * * ?', '付费会员统计报表-当天', '2022-05-27 11:00:58', '2022-05-27 11:02:22', '许云鹏', '', 'FIRST', 'payMemberJobHandler', '0', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-05-27 11:00:58', '');

-- 营销配置脚本重新生成
delete from `sbc-marketing`.`marketing_plugin_config`;

INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('COUPON', 1, 'POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '优惠券');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('POINT_AND_CASH', 2, 'COUPON,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '积分+现金');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('APPOINTMENT_SALE', 3, 'COUPON,RETURN', '预约');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('BOOKING_SALE', 4, 'COUPON,RETURN', '预售');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('FLASH_SALE', 5, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('FLASH_PROMOTION', 6, 'COUPON', '限时购');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('DISTRIBUTION', 7, 'COUPON,RETURN', '分销');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('ENTERPRISE_PRICE', 8, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '企业价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('GROUPON', 9, 'COUPON,RETURN', '拼团');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('CUSTOMER_PRICE', 11, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('CUSTOMER_LEVEL', 12, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,PAYING_MEMBER', '会员等级价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('PAYING_MEMBER', 13, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '付费会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('REDUCTION', 14, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满减');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('DISCOUNT', 15, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满折');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('GIFT', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满赠');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('BUYOUT_PRICE', 17, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,HALF_PRICE_SECOND_PIECE', '打包一口价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('HALF_PRICE_SECOND_PIECE', 18, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE', '第二件半价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`marketing_type`, `sort`, `coexist`, `description`) VALUES ('RETURN', 19, 'COUPON,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '满返');



alter table `sbc-marketing`.electronic_coupon
    add freeze_stock bigint default 0 not null comment '冻结库存';

-- 电子卡券绑定关系
ALTER TABLE `sbc-marketing`.`electronic_coupon`
    ADD COLUMN `binding_flag` tinyint(4) NULL DEFAULT 0 COMMENT '绑定标识 0、未绑定 1、已绑定';
update `sbc-marketing`.`electronic_coupon` c left join `sbc-goods`.`goods_info` g
on c.id = g.electronic_coupons_id
    set c.binding_flag = 1
where g.goods_type = 2 and g.del_flag = 0;

