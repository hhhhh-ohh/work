-- 菜单
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791443150179174d73640010', '4', 'ff8080817914431501791700064e0001', '分页查询', 'f_open_access_search',
        NULL, '0', '2021-04-28 15:07:02', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff8080817914431501791724ad60000a', '4', 'ff8080817914431501791700064e0001', '通过权限申请', 'f_open_access_pass',
        NULL, '7', '2021-04-28 14:22:30', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791443150179172416a10009', '4', 'ff8080817914431501791700064e0001', '驳回权限申请', 'f_open_access_veto',
        NULL, '6', '2021-04-28 14:21:52', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff8080817914431501791723b3ad0008', '4', 'ff8080817914431501791700064e0001', '重置AppSecret',
        'f_open_access_reset', NULL, '5', '2021-04-28 14:21:26', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff8080817914431501791722feae0007', '4', 'ff8080817914431501791700064e0001', '禁用和启用权限', 'f_open_access_open',
        NULL, '4', '2021-04-28 14:20:40', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791443150179172290830006', '4', 'ff8080817914431501791700064e0001', '删除权限', 'f_open_access_delet',
        NULL, '3', '2021-04-28 14:20:12', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791443150179171f4a350004', '4', 'ff8080817914431501791700064e0001', '编辑权限', 'f_open_access_update',
        NULL, '2', '2021-04-28 14:16:37', '0');
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`,
                                           `function_name`, `remark`, `sort`, `create_time`, `del_flag`)
VALUES ('ff8080817914431501791700ca870002', '4', 'ff8080817914431501791700064e0001', '新增权限', 'f_open_access_add', NULL,
        '1', '2021-04-28 13:43:18', '0');


-- 功能
INSERT INTO `sbc-setting`.`menu_info` (`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`,
                                       `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791b544401791b6ec44a0004', '3', 'ff808081791b544401791b6e92530003', '3', '开放权限', '/open-access', NULL,
        '1', '2021-04-29 10:21:55', '0');
INSERT INTO `sbc-setting`.`menu_info` (`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`,
                                       `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791b544401791b6e92530003', '3', 'fc8e59dd3fe311e9828800163e0fc468', '2', '开放权限', NULL, NULL, '49',
        '2021-04-29 10:21:42', '0');
INSERT INTO `sbc-setting`.`menu_info` (`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`,
                                       `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791b544401791b6037830002', '6', 'ff808081791b544401791b5f74a00000', '3', '开放权限', '/open-access', NULL,
        '1', '2021-04-29 10:06:01', '0');
INSERT INTO `sbc-setting`.`menu_info` (`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`,
                                       `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`)
VALUES ('ff808081791b544401791b5f74a00000', '6', '2c9386c170560beb017056e4ecb20070', '2', '开放权限', NULL, NULL, '49',
        '2021-04-29 10:05:11', '0');
INSERT INTO `sbc-setting`.`menu_info` (`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`,
                                       `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`)
VALUES ('ff8080817914431501791700064e0001', '4', 'ff80808179144315017916ffd25a0000', '3', '开放权限', '/open-access', NULL,
        '1', '2021-04-28 13:42:28', '0');
INSERT INTO `sbc-setting`.`menu_info` (`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`,
                                       `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`)
VALUES ('ff80808179144315017916ffd25a0000', '4', 'fc8df1663fe311e9828800163e0fc468', '2', '开放权限', NULL, NULL, '155',
        '2021-04-28 13:42:15', '0');


-- 权限
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179174e26990012', '4', 'ff808081791443150179174d73640010', '分页查询', NULL,
        '/open-api-setting/page', 'POST', NULL, '1', '2021-04-28 15:07:48', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179174e238c0011', '4', 'ff808081791443150179174d73640010', '分页查询', NULL,
        '/open-api-setting/page', 'POST', NULL, '1', '2021-04-28 15:07:47', '1');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179172e3434000f', '4', 'ff8080817914431501791724ad60000a', '通过权限申请', NULL,
        '/open-api-setting/check-audit-state', 'POST', NULL, '1', '2021-04-28 14:32:55', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179172d8c17000e', '4', 'ff808081791443150179172416a10009', '驳回权限申请', NULL,
        '/open-api-setting/check-audit-state', 'POST', NULL, '1', '2021-04-28 14:32:12', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179172cc611000d', '4', 'ff8080817914431501791723b3ad0008', '重置AppSecret', NULL,
        '/open-api-setting/resetSecret/*', 'GET', NULL, '1', '2021-04-28 14:31:21', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179172ba625000c', '4', 'ff8080817914431501791722feae0007', '禁用或启用权限', NULL,
        '/open-api-setting/change-disable-state', 'POST', NULL, '1', '2021-04-28 14:30:07', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff8080817914431501791726973e000b', '4', 'ff808081791443150179172290830006', '删除开发权限', NULL,
        '/open-api-setting/*', 'DELETE', NULL, '1', '2021-04-28 14:24:36', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff808081791443150179172130940005', '4', 'ff808081791443150179171f4a350004', '编辑权限配置信息', '编辑权限配置信息',
        '/open-api-setting/modify', 'PUT', NULL, '1', '2021-04-28 14:18:42', '0');
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`,
                                       `authority_name`, `authority_url`, `request_type`, `remark`, `sort`,
                                       `create_time`, `del_flag`)
VALUES ('ff8080817914431501791702a9270003', '4', 'ff8080817914431501791700ca870002', '新增权限', NULL,
        '/open-api-setting/add', 'POST', NULL, '1', '2021-04-28 13:45:21', '0');


update `sbc-setting`.`menu_info` set sort  = 49 where menu_id = 'ff808081791b544401791b6e92530003';

update `sbc-setting`.`menu_info` set sort  = 49 where menu_id = 'ff808081791b544401791b5f74a00000';

update `sbc-setting`.`menu_info` set sort  = 155 where menu_id = 'ff80808179144315017916ffd25a0000';

update `sbc-setting`.`authority` set request_type = 'DELETE' where authority_id = 'ff8080817914431501791726973e000b';

update `sbc-setting`.`authority` set request_type = 'PUT' where authority_id = 'ff808081791443150179172130940005';
