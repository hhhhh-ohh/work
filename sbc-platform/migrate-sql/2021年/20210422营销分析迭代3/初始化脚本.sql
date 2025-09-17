ALTER TABLE `sbc-setting`.`flash_sale_setting` ADD COLUMN img_json TEXT NULL COMMENT '轮播海报JSON内容';

INSERT INTO `sbc-setting`.`system_config` (config_key, config_type, config_name, remark, status, context, create_time, update_time, del_flag) VALUES ('pay_setting', 'offline_pay_setting', '线下支付开关设置', '线下支付开关设置', 1, null, '2021-03-18 16:38:20', '2021-03-19 11:36:46', 0);

INSERT INTO `sbc-setting`.`system_config`(`config_key` , `config_type` , `config_name` , `remark` , `status` , `create_time` , `update_time` , `del_flag` )
VALUES ( 'goods_setting' , 'goods_out_of_stock_show_setting' , '仅展示有货商品' , '打开后前台商城商品列表将不再展示缺货商品' , '0' , now() , NULL , '0' );

ALTER TABLE `s2b_statistics`.`marketing_situation_day`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `customer_price`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;

ALTER TABLE `s2b_statistics`.`marketing_situation_seven`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `customer_price`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;

ALTER TABLE `s2b_statistics`.`marketing_situation_thirty`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `customer_price`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;

ALTER TABLE `s2b_statistics`.`marketing_situation_month`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `customer_price`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;


ALTER TABLE `s2b_statistics`.`marketing_overview_day`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `boss_old_customer`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;

ALTER TABLE `s2b_statistics`.`marketing_overview_seven`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `boss_old_customer`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;

ALTER TABLE `s2b_statistics`.`marketing_overview_thirty`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `boss_old_customer`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;

ALTER TABLE `s2b_statistics`.`marketing_overview_month`
ADD COLUMN `pv` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'PV' AFTER `boss_old_customer`,
ADD COLUMN `uv` bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'UV' AFTER `pv`;