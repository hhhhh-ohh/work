INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808179b7a0a60179b8411cbf0001', 3, 'fc928a5f3fe311e9828800163e0fc468', '查询渠道订单物流信息', NULL, '/trade/channel/deliveryInfos', 'POST', NULL, 5, now(), 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808179b7a0a60179b84066260000', 4, 'fc92514b3fe311e9828800163e0fc468', '查询渠道订单物流信息', NULL, '/trade/channel/deliveryInfos', 'POST', NULL, 5, now(), 0);

UPDATE `sbc-setting`.`authority` SET `authority_url` = '/third/channel-config/list' WHERE `authority_id` = 'ff808081744f60f8017451e3d7160003';
UPDATE `sbc-setting`.`authority` SET `authority_url` = '/third/channel-config/*' WHERE `authority_id` = 'ff80808174be87b00174bf138a160005';
UPDATE `sbc-setting`.`authority` SET `authority_url` = '/third/channel-config/modify' WHERE `authority_id` = 'ff80808174be87b00174bf14c07e0006';

INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7a0f87bc017a135ca7a80003', 4, 'ff808081740c6e2d01740ec79c1e0005', '查询平台分类', NULL, '/goods/goodsCatesTree', 'GET', NULL, 3, now(), 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7a0f87bc017a135689940002', 4, 'ff80808173f2ae5f0173ff5a4906000d', '更新渠道地址获取到的数据', NULL, '/third/address/mapping', 'PUT', NULL, 5, now(), 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7a0f87bc017a12eeb23f0001', 4, 'ff80808173f2ae5f0173ff5a4906000d', '根据平台地址ID查询地址映射关系', NULL, '/third/address/validate', 'POST', NULL, 5, now(), 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7a0f87bc017a12e6c5250000', 4, 'ff80808173f2ae5f0173ff5a4906000d', 'vop地址列表', NULL, '/third/address/page', 'POST', NULL, 4, now(), 0);

UPDATE `sbc-setting`.`function_info` SET `function_name` = 'f_cate_reflect' where `function_id` = 'ff808081740c6e2d01740ec79c1e0005';
UPDATE `sbc-setting`.`authority` SET `authority_title` = '获取第三方类目列表' WHERE `authority_title` = '获取linkedmall类目列表';

INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`,
                                      `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a82795a1f8d01795e76b84d0000', 4, 'ff80808171303449017130b5f0370000', 3, '京东商品库', '/goods-library-vop-list', NULL, 3, now(), 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a82795ffc8a017963936e870000', 4, '2c939a82795a1f8d01795e76b84d0000', '京东商品库列表', 'f_vop_goods_library_watch', NULL, 1, now(), 0);
