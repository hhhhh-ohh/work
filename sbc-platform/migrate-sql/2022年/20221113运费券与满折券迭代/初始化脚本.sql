-- 优惠券表 追加以下字段
ALTER TABLE `sbc-marketing`.`coupon_info`
ADD COLUMN `coupon_marketing_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '优惠券营销类型 0满减券 1满折券 2运费券' AFTER `coupon_type`,
ADD COLUMN `coupon_discount_mode` tinyint(4) DEFAULT '0' COMMENT '优惠券优惠方式 0减免 1包邮' AFTER `coupon_marketing_type`,
ADD COLUMN `max_discount_limit` decimal(10,2) DEFAULT NULL COMMENT '最大优惠金额限制' AFTER `coupon_discount_mode`,
ADD INDEX `idx_coupon_marketing_type`(`coupon_marketing_type`) USING BTREE;

-- 支付密码盐值单独存储
ALTER TABLE `sbc-customer`.`customer`
ADD COLUMN `customer_pay_salt_val` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付密码盐值' AFTER `customer_pay_password`;
-- 将已存在的账号的支付密码的盐值保持和登录密码盐值一致，保证现有账号的支付密码可以正常使用
UPDATE `sbc-customer`.`customer` set customer_pay_salt_val = customer_salt_val;

-- 弃用QQ在线客服，将已开启的QQ客服状态置为"禁用"
UPDATE `sbc-empower`.`customer_service_setting` SET `status` = 0 WHERE `platform_type` = 0;


-- 营销关联用户等级问题修复需要对老数据进行处理
UPDATE `sbc-marketing`.`marketing` m SET m.join_level = '-1'
WHERE m.store_id in (select s.store_id from `sbc-customer`.`store` s where s.company_type=0 and s.del_flag=0) and m.is_boss=0 and m.join_level='0';

UPDATE `sbc-marketing`.`appointment_sale` m SET m.join_level = '-1'
WHERE m.store_id in (select s.store_id from `sbc-customer`.`store` s where s.company_type=0 and s.del_flag=0) and m.join_level='0';

UPDATE `sbc-marketing`.`booking_sale` m SET m.join_level = '-1'
WHERE m.store_id in (select s.store_id from `sbc-customer`.`store` s where s.company_type=0 and s.del_flag=0) and m.join_level='0';
