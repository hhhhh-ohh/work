-- 加价购
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184a14de60184a2529d940000', 3, '2c939a4d7ea90aa6017ed21cda08000b', '加价购-106', 'f_marketing_preferential_list', NULL, 106, '2022-11-23 10:30:34', 0);
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184e5b9cd0184e5fede5a0000', 4, '2c939a4d7ea90aa6017ed21fb99c0013', '加价购-106', 'f_marketing_preferential_list_supplier', NULL, 106, '2022-12-06 13:53:16', 0);
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184e5b9cd0184e601c1810002', 3, '2c939a4d7ea90aa6017ed21cda08000b', '新增加价购-106', 'f_marketing_preferential_add', NULL, 106, '2022-12-06 13:56:26', 0);

INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184e5b9cd0184e60ca2230003', 3, 'ff80808184e5b9cd0184e601c1810002', '新增加价购', NULL, '/marketing/preferential/add', 'POST', NULL, 1, '2022-12-06 14:08:18', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184e5b9cd0184e6128a7d0004', 3, 'ff80808184a14de60184a2529d940000', '编辑加价购', NULL, '/marketing/preferential/modify', 'PUT', NULL, 1, '2022-12-06 14:14:46', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184e5b9cd0184e612fe950005', 3, 'ff80808184a14de60184a2529d940000', '加价购详情', NULL, '/marketing/preferential/detail/**', 'GET', NULL, 0, '2022-12-06 14:15:15', 0);

-- boss财务-订单收款/退单退款导出
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184a673f30184a7b9ceea0003', 4, 'ff80808184a673f30184a7b897720002', '退单退款批量导出', NULL, '/account/export/refund/*', 'GET', NULL, 1, '2022-11-24 11:41:23', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184a673f30184a7b7b4e00001', 4, 'ff80808184a673f30184a7b3fa300000', '订单收款批量导出', NULL, '/account/export/params/*', 'GET', NULL, 1, '2022-11-24 11:39:05', 0);

INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184a673f30184a7b897720002', 4, 'fc8df8813fe311e9828800163e0fc468', '批量导出', 'f_boss_refund_order_export', NULL, 6, '2022-11-24 11:40:03', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff80808184a673f30184a7b3fa300000', 4, 'fc8df8323fe311e9828800163e0fc468', '批量导出', 'f_boss_pay_order_export', NULL, 5, '2022-11-24 11:35:01', 0);



-- 小程序订阅消息权限修改
update `sbc-setting`.`authority` set authority_url = '/minimsgtempsetting/page' where authority_id = 'ff80808182c42cde0182c463fca60006' and authority_url = '/miniprogramsubscribetemplatesetting/page';
update `sbc-setting`.`authority` set authority_url = '/minimsgtempsetting/list' where authority_id = 'ff80808182c42cde0182c466bc500008' and authority_url = '/miniprogramsubscribetemplatesetting/list';
update `sbc-setting`.`authority` set authority_url = '/minimsgtempsetting/initMiniMsgTemp' where authority_id = 'ff80808182c42cde0182c4673e780009' and authority_url = '/miniprogramsubscribetemplatesetting/initMiniProgramSubscribeTemplate';
update `sbc-setting`.`authority` set authority_url = '/minimsgtempsetting/**' where authority_id = 'ff80808182c42cde0182c467db5c000a' and authority_url = '/miniprogramsubscribetemplatesetting/**';
update `sbc-setting`.`authority` set authority_url = '/minimsgtempsetting/checkInitMiniMsgTemp' where authority_id = 'ff80808182c42cde0182c46b5fdf000b' and authority_url = '/miniprogramsubscribetemplatesetting/checkInitMiniProgramSubscribeTemplate';
update `sbc-setting`.`authority` set authority_url = '/minimsgtempsetting/modify' where authority_id = 'ff80808182c42cde0182c46bf8a1000c' and authority_url = '/miniprogramsubscribetemplatesetting/modify';
update `sbc-setting`.`authority` set authority_url = '/minimsgactivitysetting/page' where authority_id = 'ff80808182c42cde0182c4759c80000d' and authority_url = '/miniprogramsubscribemessageactivitysetting/page';
update `sbc-setting`.`authority` set authority_url = '/minimsgactivitysetting/**' where authority_id = 'ff80808182c42cde0182c4773af1000e' and authority_url = '/miniprogramsubscribemessageactivitysetting/**';
update `sbc-setting`.`authority` set authority_url = '/minimsgactivitysetting/add' where authority_id = 'ff80808182c42cde0182c477d462000f' and authority_url = '/miniprogramsubscribemessageactivitysetting/add';
update `sbc-setting`.`authority` set authority_url = '/minimsgactivitysetting/modify' where authority_id = 'ff80808182c42cde0182c4785a210010' and authority_url = '/miniprogramsubscribemessageactivitysetting/modify';
update `sbc-setting`.`authority` set authority_url = '/minimsgactivitysetting/**' where authority_id = 'ff80808182c42cde0182c478e5320011' and authority_url = '/miniprogramsubscribemessageactivitysetting/**';
update `sbc-setting`.`authority` set authority_url = '/minimsgactivitysetting/pushCount' where authority_id = 'ff80808182c42cde0182c47a54330013' and authority_url = '/miniprogramsubscribemessageactivitysetting/pushCount';
update `sbc-setting`.`authority` set authority_url = '/minimsgsetting/list' where authority_id = 'ff80808182c42cde0182c47d0c4b0016' and authority_url = '/miniprogramsubscribemessagesetting/list';
update `sbc-setting`.`authority` set authority_url = '/minimsgsetting/modify' where authority_id = 'ff80808182c42cde0182c47d7d0f0017' and authority_url = '/miniprogramsubscribemessagesetting/modify';
