INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('order_setting', 'order_setting_quick_order', 'PC商城快速下单', NULL, 0, '', now(), null, 0);

ALTER TABLE `sbc-goods`.`goods_info`
    ADD COLUMN `quick_order_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '订货号' AFTER `line_price`;

ALTER TABLE `sbc-goods`.`standard_sku`
    ADD COLUMN `quick_order_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订货号';


ALTER TABLE `sbc-goods`.`goods_info`
    ADD COLUMN `provider_quick_order_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '供应商订货号' AFTER `quick_order_no`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`goods_info_id`) USING BTREE;

ALTER TABLE `sbc-customer`.`employee_department`
MODIFY COLUMN `department_id` varchar(32) NOT NULL COMMENT '部门id' AFTER `employee_id`;

ALTER TABLE `sbc-customer`.`company_info`
MODIFY COLUMN `company_info_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '公司信息ID' FIRST;

ALTER TABLE `sbc-customer`.`store_customer_rela`
MODIFY COLUMN `company_info_id` bigint(11) NULL DEFAULT NULL COMMENT '商家标识' AFTER `store_id`;

ALTER TABLE `sbc-customer`.`store_return_address`
MODIFY COLUMN `company_info_id` bigint(11) NOT NULL COMMENT '公司信息ID' AFTER `address_id`;

drop table `sbc-crm`.`tag_param_value`;
drop table `sbc-crm`.`tag_dimension_setting`;

update `sbc-setting`.base_config set version = 'SBC V5.5.0';
