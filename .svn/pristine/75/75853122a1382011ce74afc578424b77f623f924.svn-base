
ALTER TABLE `sbc-customer`.`ledger_account`
    ADD COLUMN `b2b_add_state` tinyint(4) NULL DEFAULT 0 COMMENT 'B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败' AFTER `bind_contract_id`;

ALTER TABLE `sbc-customer`.`ledger_account`
    ADD COLUMN `b2b_add_apply_id` varchar(255) NULL COMMENT 'B2b网银新增申请id' AFTER `b2b_add_state`;

ALTER TABLE `sbc-customer`.`ledger_account`
    ADD COLUMN `mer_reg_dist_province_code` varchar(32) NULL COMMENT '注册地址省编号' AFTER `b2b_add_apply_id`,
ADD COLUMN `mer_reg_dist_city_code` varchar(32) NULL COMMENT '注册地址市编号' AFTER `mer_reg_dist_province_code`;


ALTER TABLE `sbc-customer`.`ledger_account`
    ADD COLUMN `bank_term_no` varchar(128) NULL COMMENT '银行卡终端号' AFTER `mer_reg_dist_city_code`;

ALTER TABLE `sbc-customer`.`ledger_account`
    ADD COLUMN `quick_pay_term_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '快捷终端号' AFTER `bank_term_no`,
ADD COLUMN `union_term_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '网银终端号' AFTER `quick_pay_term_no`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;

INSERT INTO `sbc-empower`.`pay_gateway` (`id`, `name`, `is_open`, `type`, `create_time`, `store_id`) VALUES ('31', 'LAKALACASHIER', '0', '1', now(), '-1');

INSERT INTO `sbc-empower`.`pay_gateway_config` (`gateway_id`, `api_key`, `secret`, `account`, `app_id`, `app_id2`, `private_key`, `public_key`, `create_time`, `pc_back_url`, `pc_web_url`, `boss_back_url`, `open_platform_app_id`, `open_platform_secret`, `open_platform_api_key`, `open_platform_account`, `wx_pay_certificate`, `wx_open_pay_certificate`, `store_id`, `api_v3_key`, `merchant_serial_number`) VALUES ('31', NULL, NULL, '18000000003', '13123213', '1312321323', NULL, NULL, now(), NULL, NULL, '13213213', NULL, NULL, NULL, NULL, NULL, NULL, '-1', NULL, NULL);

INSERT INTO `sbc-empower`.`pay_channel_item` (`id`, `name`, `gateway_name`, `gateway_id`, `channel`, `is_open`, `terminal`, `code`, `create_time`) VALUES (39, '拉卡拉收银台H5支付', 'LAKALACASHIER', '111', 'LAKALACASHIER', '1', '1', 'lakalacashier_wx', now());
INSERT INTO `sbc-empower`.`pay_channel_item` (`id`, `name`, `gateway_name`, `gateway_id`, `channel`, `is_open`,
                                              `terminal`, `code`, `create_time`) VALUES (40, '拉卡拉收银台PC支付',
                                                                                         'LAKALACASHIER', '111',
                                                                                         'LAKALACASHIER', '1', '0',
                                                                                         'lakalacashier_pc', now());




