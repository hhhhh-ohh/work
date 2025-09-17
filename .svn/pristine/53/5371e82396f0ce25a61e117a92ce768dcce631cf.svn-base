-- 优惠券重构迭代脚本
-- function_info
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c93999b80e3bb0f0180e4a8427e0000', 4, 'ff80808178153da20178164448ff0002', '初始化活动优惠券', 'f_init_activity_coupon', NULL, 5, '2022-05-21 11:27:54', 0);
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808181838999018184e6a1b00000', '4', 'ff80808178153da20178164448ff0002', '初始化es索引', 'f_ex_init_index', NULL, '1', '2022-06-21 14:15:16', '0');
-- 订单打印
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818192fca701819517db960000', 3, 'fc8dfdfd3fe311e9828800163e0fc468', '订单打印', 'f_order_print', NULL, 8, '2022-06-24 17:42:58', 0);




-- authority
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c93999b80e3bb0f0180e4a9798c0001', 4, '2c93999b80e3bb0f0180e4a8427e0000', '初始化活动优惠券ES', NULL, '/init/initActivityCouponES', 'POST', NULL, 1, '2022-05-21 11:29:14', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808181838999018184e776410001', '4', 'ff80808181838999018184e6a1b00000', '索引初始化', NULL, '/initEsIndex', 'POST', NULL, '1', '2022-06-21 14:16:11', '0');
-- 订单打印
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818192fca701819518e3ec0001', 3, 'ff8080818192fca701819517db960000', '订单打印', NULL, '/trade/supplierPrintPage', 'POST', NULL, 1, '2022-06-24 17:44:05', 0);
-- 批量上下架
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff808081818dd74e01818e7a6e390000', 3, 'fc9263d93fe311e9828800163e0fc468', '批量上下架', NULL, '/goods/spu/sale', 'POST', NULL, 5, '2022-06-23 10:53:17', 0);


-- 补全 订单>代客下单>导入商品 接口权限
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818195aeb10181a315f9e10006', 3, '2c9b8072801be66a01801c5786540000', '下载错误表格', NULL, '/order-for-customer/excel/err/*', 'GET', NULL, 4, '2022-06-27 10:55:35', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818195aeb10181a313dfcc0005', 3, '2c9b8072801be66a01801c5786540000', '查询导入数据', NULL, '/order-for-customer/goods/list', 'POST', NULL, 3, '2022-06-27 10:53:18', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818195aeb10181a30fa18b0004', 3, '2c9b8072801be66a01801c5786540000', '上传商品导入模板', NULL, '/order-for-customer/excel/upload', 'POST', NULL, 2, '2022-06-27 10:48:40', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818195aeb10181a30bd7820003', 3, '2c9b8072801be66a01801c5786540000', '下载商品导入模板', NULL, '/order-for-customer/goods/template', 'GET', NULL, 1, '2022-06-27 10:44:31', 0);





