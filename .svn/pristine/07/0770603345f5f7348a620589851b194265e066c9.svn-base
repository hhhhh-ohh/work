-- 营销表增加字段
ALTER TABLE `sbc-marketing`.`marketing`
ADD COLUMN `participate_type` tinyint(4) NULL COMMENT '参与门店是：0全部，1部分' AFTER `is_pause`,
ADD COLUMN `plugin_type` tinyint(4) NULL default 0 COMMENT '营销类型：0店铺，1跨境(不使用)，2门店' AFTER `participate_type`,
ADD COLUMN `audit_status` tinyint(4) NULL COMMENT '门店营销审核状态：0未审核1审核通过2审核失败' AFTER `plugin_type`,
ADD COLUMN `refuse_reason` varchar(255) NULL COMMENT '审核失败原因' AFTER `audit_status`;

-- 处理营销表老数据,商家老数据默认审核通过
update `sbc-marketing`.`marketing` set `audit_status` = 1, `plugin_type` = 0 where `plugin_type` is null or `audit_status` is null;

-- 优惠券表增加字段
ALTER TABLE `sbc-marketing`.`coupon_info`
ADD COLUMN `participate_type` tinyint(4) NULL  COMMENT '门店营销类型 0全部门店，1自定义门店' AFTER `del_person`;

-- 自提表增加字段
ALTER TABLE `sbc-setting`.`pickup_setting`
ADD COLUMN `store_type` tinyint(4) NULL default 1 COMMENT '店铺类型：0供应商，1商家，2门店' AFTER `delete_person`;

-- 门店优惠券活动 所属平台
ALTER TABLE `sbc-marketing`.`coupon_activity` ADD COLUMN `plugin_type` tinyint(4) NULL default 0 COMMENT '活动类型：0店铺，1跨境(不使用)，2门店' AFTER `activity_desc`;
-- 门店优惠券活动 审核状态
ALTER TABLE `sbc-marketing`.`coupon_activity` ADD COLUMN `audit_state` TINYINT (4) NULL COMMENT '0: 未审核 1: 已审核 2: 已打回' AFTER `plugin_type`;
-- 门店优惠券活动驳回原因
ALTER TABLE `sbc-marketing`.`coupon_activity` ADD COLUMN `refuse_reason` VARCHAR (100) NULL COMMENT '驳回原因' AFTER `audit_state`;

-- 优惠券表 使用说明字符集
ALTER TABLE `sbc-marketing`.`coupon_info`  MODIFY COLUMN `coupon_desc` LONGTEXT  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 处理优惠券活动表老数据,商家老数据默认审核通过
UPDATE `sbc-marketing`.`coupon_activity` SET `audit_state` = 1, `plugin_type` = 0 WHERE `plugin_type` is null or `audit_state` is null;


-- 达达配送记录表运费保留2位小数
ALTER TABLE `sbc-empower`.`delivery_record_dada` MODIFY COLUMN `fee` decimal(20,2) DEFAULT NULL COMMENT '实际运费',
    MODIFY COLUMN `deliver_fee` decimal(20,2) DEFAULT NULL COMMENT '运费';

-- boss地址库更新
UPDATE `sbc-setting`.`platform_address` t SET t.`addr_name` = '上海市', t.`pin_yin` = 'shanghaishi' WHERE t.`id`='310100';
UPDATE `sbc-setting`.`platform_address` t SET t.`addr_name` = '重庆市', t.`pin_yin` = 'chongqingshi' WHERE t.`id`='500100';
UPDATE `sbc-setting`.`platform_address` t SET t.`addr_name` = '北京市', t.`pin_yin` = 'beijingshi' WHERE t.`id` ='110100';
UPDATE `sbc-setting`.`platform_address` t SET t.`addr_name` = '天津市', t.`pin_yin` = 'tianjinshi' WHERE t.`id` ='120100';

-- 四级地址街道
INSERT INTO `sbc-setting`.platform_address (id, addr_id, addr_name, addr_parent_id, sort_no, addr_level, create_time, update_time, del_flag, delete_time, data_type, pin_yin)VALUES ('500101002','500101002','太白街道','500101',500101002,3,'2021-11-03 10:48:19','2021-11-03 10:48:19',0,null,0,'taibaijiedao');
INSERT INTO `sbc-setting`.platform_address (id, addr_id, addr_name, addr_parent_id, sort_no, addr_level, create_time, update_time, del_flag, delete_time, data_type, pin_yin)VALUES ('520102003','520102003','新华路街道','520102',520102003,3,'2021-11-03 10:48:19','2021-11-03 10:48:19',0,null,0,'xinhualujiedao');

-- 增加默认值
ALTER TABLE `sbc-order`.`purchase`
    MODIFY COLUMN `plugin_type` tinyint(4) NULL default 0 COMMENT '类型： 0-正常商品 1-跨境商品 2-o2o商品';
ALTER TABLE `sbc-setting`.`pickup_setting`
    MODIFY COLUMN `store_type` tinyint(4) NULL default 1 COMMENT '店铺类型：0供应商，1商家，2门店';
ALTER TABLE `sbc-marketing`.`coupon_activity`
    MODIFY COLUMN `plugin_type` tinyint(4) NULL default 0 COMMENT '活动类型：0店铺，1跨境(不使用)，2门店';

-- 处理老数据
UPDATE `sbc-order`.`purchase` SET plugin_type=0 WHERE plugin_type IS NULL;
UPDATE `sbc-setting`.`pickup_setting` SET store_type=1 WHERE store_type IS NULL;
UPDATE `sbc-marketing`.`coupon_activity` SET plugin_type=0, audit_state=1 WHERE plugin_type IS NULL OR audit_state IS NULL;