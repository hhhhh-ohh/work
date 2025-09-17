ALTER TABLE `sbc-customer`.`customer_growth_value`
    MODIFY COLUMN `type` tinyint NOT NULL COMMENT '操作类型 0:扣除 1:增长 2:覆盖' AFTER `customer_id`,
    MODIFY COLUMN `service_type` tinyint NOT NULL COMMENT '业务类型 0签到 1注册 2分享商品 3分享注册 4分享购买  5评论商品 6晒单 7上传头像 8绑定微信 9添加收货地址 10关注店铺 11订单完成 12校服小助手订单完成 13降级' AFTER `type`;