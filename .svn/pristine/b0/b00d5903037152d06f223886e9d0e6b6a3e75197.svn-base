-- 三沙市区划地址信息（三沙区、西沙区）
INSERT INTO `sbc-setting`.`platform_address`(`id`, `addr_id`, `addr_name`, `addr_parent_id`, `sort_no`, `addr_level`, `create_time`, `update_time`, `del_flag`, `delete_time`, `data_type`, `pin_yin`) VALUES ('460301', '460301', '西沙区', '460300', 460301, 2, '2022-08-23 11:13:59', '2022-08-23 11:13:59', 0, NULL, 0, 'xishaqu');
INSERT INTO `sbc-setting`.`platform_address`(`id`, `addr_id`, `addr_name`, `addr_parent_id`, `sort_no`, `addr_level`, `create_time`, `update_time`, `del_flag`, `delete_time`, `data_type`, `pin_yin`) VALUES ('460302', '460302', '南沙区', '460300', 460302, 2, '2022-08-23 11:14:00', '2022-08-23 11:13:59', 0, NULL, 0, 'nanshaqu');

-- mongo脚本  海报页文章页增加限时抢购组价
db.getCollection("tpl_rules").update({ _id: ObjectId("5b67c71830bc7bd45bafd55e") },
    { $push: { "acceptBars": "@wanmi/wechat-rushtobuy" } })

db.getCollection("tpl_rules").update({ _id: ObjectId("5b67c73d30bc7bd45bafd5af") },
    { $push: { "acceptBars": "@wanmi/wechat-rushtobuy" } })