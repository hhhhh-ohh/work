
--  平台端-订单-跨境订单列表，接口权限补充
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817b528b9e017b633e6b9a0004', 4, 'ff8080817a806276017acd01ab65008e', '根据主单编号查询子单列表', NULL, '/cross/sub-trade/list', 'POST', NULL, 11, '2021-08-20 19:07:20', 0);

--  商家端-订单-跨境订单列表，接口权限补充
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817b528b9e017b633ca46a0003', 3, 'ff8080817a806276017ace24b28c00c2', '子单一键发货', NULL, '/cross/sub-trade/deliver', 'PUT', NULL, 6, '2021-08-20 19:05:24', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817b528b9e017b633b91890002', 3, 'ff8080817a806276017ace2479b500c1', '子单一键退款', NULL, '/cross/border/sub-trade/refund', 'PUT', NULL, 2, '2021-08-20 19:04:13', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817b528b9e017b633ac4f10001', 3, 'ff8080817a806276017ace2479b500c1', '获取主单一键退款可退金额', NULL, '/cross/border/**/can-return-price', 'GET', NULL, 1, '2021-08-20 19:03:21', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817b528b9e017b63384aca0000', 3, 'ff8080817a806276017acdecdcf800b1', '根据主单编号查询子单列表', NULL, '/cross/sub-trade/list', 'POST', NULL, 9, '2021-08-20 19:00:39', 0);


-- Boss: 商品-跨境商品-待备案商品列表-待备案商品列表查看
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399e17b81c9d9017b8ac699ec0000', 4, 'ff8080817a806276017abddaa5840028', '获取店铺列表', NULL, '/store/storeList', 'POST', NULL, 5, '2021-08-28 11:21:17', 0);
