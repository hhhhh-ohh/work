-- 直播商品
ALTER TABLE `sbc-goods`.`live_goods`
    ADD COLUMN `goods_type` tinyint(2) NULL COMMENT '商品类型 0:实物商品 1:虚拟商品 2:卡券商品';
-- 历史数据——设置直播商品商品类型
USE `sbc-goods`;
UPDATE live_goods l INNER JOIN
    (
    SELECT * FROM (
    SELECT goods_info_id, goods_type
    FROM goods_info
    WHERE goods_info_id IN(
    SELECT goods_info_id FROM live_goods
    )
    ) tmp
    ) g
ON l.goods_info_id = g.goods_info_id
    SET l.goods_type = g.goods_type;

-- 拼团商品
ALTER TABLE `sbc-marketing`.`groupon_activity`
    ADD COLUMN `goods_type` tinyint(2) NULL COMMENT '商品类型 0:实物商品 1:虚拟商品 2:电子卡券';
-- 历史数据——设置拼团商品商品类型
USE `sbc-marketing`;
UPDATE groupon_activity ga INNER JOIN
    (
    SELECT * FROM (
    SELECT goods_id, goods_type
    FROM `sbc-goods`.`goods`
    WHERE goods_id IN(
    SELECT goods_id FROM groupon_activity
    )
    ) tmp
    ) g
ON ga.goods_id = g.goods_id
    SET ga.goods_type = g.goods_type;

-- 卡券订单申请代客退单设置
INSERT INTO `sbc-setting`.system_config (config_key, config_type, config_name, remark, status, context, create_time, update_time, del_flag) VALUES ('order_setting', 'order_setting_virtual_apply_refund', '卡券订单允许代客退单', null, 1, '{"day":15}', NOW(), NOW(), 0);

-- 预约商品表中冗余goods_type字段
ALTER TABLE `sbc-marketing`.`appointment_sale_goods`
    ADD COLUMN `goods_type` tinyint(2) NULL COMMENT '商品类型 0:实物商品 1:虚拟商品 2:卡券商品' AFTER `goods_id`;
-- 初始化刷数据
UPDATE `sbc-marketing`.`appointment_sale_goods` a
    INNER JOIN (
    SELECT * FROM (
    SELECT g.goods_info_id, g.goods_type
    FROM `sbc-goods`.`goods_info` g
    WHERE
    g.goods_info_id COLLATE utf8mb4_unicode_ci IN (SELECT goods_info_id FROM `sbc-marketing`.`appointment_sale_goods`)
    ) tmp
    ) g ON a.goods_info_id COLLATE utf8mb4_unicode_ci = g.goods_info_id
    SET a.goods_type = g.goods_type;

-- 预售商品表中冗余goods_type字段
ALTER TABLE `sbc-marketing`.`booking_sale_goods`
    ADD COLUMN `goods_type` tinyint(2) NULL COMMENT '商品类型 0:实物商品 1:虚拟商品 2:卡券商品' AFTER `goods_id`;
-- 初始化刷数据
UPDATE `sbc-marketing`.`booking_sale_goods` a
    INNER JOIN (
    SELECT * FROM (
    SELECT g.goods_info_id, g.goods_type
    FROM `sbc-goods`.`goods_info` g
    WHERE
    g.goods_info_id COLLATE utf8mb4_unicode_ci IN (SELECT goods_info_id FROM `sbc-marketing`.`booking_sale_goods`)
    ) tmp
    ) g ON a.goods_info_id COLLATE utf8mb4_unicode_ci = g.goods_info_id
    SET a.goods_type = g.goods_type;


-- 删除多次绑定卡券的商品  执行完要同步es
update `sbc-goods`.goods set del_flag = 1 where goods_id in (
    select goods_id From `sbc-goods`.goods_info where goods_type =2 and electronic_coupons_id
        in (select electronic_coupons_id from `sbc-goods`.goods_info group By electronic_coupons_id having Count(*)>1)
);


-- 删除多次绑定卡券的商品  执行完要同步es
update  `sbc-goods`.goods_info set del_flag = 1 where goods_id in (
    select  a.goods_id from
        (select goods_id From `sbc-goods`.goods_info where goods_type =2 and electronic_coupons_id
            in (select electronic_coupons_id from `sbc-goods`.goods_info group By electronic_coupons_id having Count(*)>1)
        ) as a);





