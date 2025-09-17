-- 1、删除原应用菜单
update `sbc-setting`.`menu_info` set del_flag = 1 where menu_id in
                                                        (
                                                         'fc8e11003fe311e9828800163e0fc468',
                                                         'fc8e20ff3fe311e9828800163e0fc468',
                                                         '2c9386a86b6d7552016b6d7c0c2f0000',
                                                         '2c93889a6ac39087016ac3ab865c0000',
                                                         '40286c826a950c54016a96a3e7510014',
                                                         '8a99c40b696c365401696fe8391a0009',
                                                         '8a99c40b696c365401697000c6fa0019',
                                                         '8a9bc76c6f6b7aa2016f6bdee4e30000',
                                                         'fc8e114f3fe311e9828800163e0fc468',
                                                         'fc8e21533fe311e9828800163e0fc468',
                                                         'ff8080816ac53f1c016ad2d514020000',
                                                         'ff8080816b915d6c016b919166420001',
                                                         'ff8080816fa8b804016fac3cf4790000',
                                                         'ff8080816fa8b804016fac4673d50002',
                                                         'ff80808170860f0801708b4e9ec80001',
                                                         'ff80808170860f0801708b50597c0004',
                                                         'ff808081730fdc4b017313e812510001',
                                                         'ff808081730fdc4b0173142000790011',
                                                         'ff80808173f2ae5f0173ff4522480000',
                                                         'ff808081744bbf8101744cd7167c0000',
                                                         'ff808081744bbf8101744cd8b6e80001',
                                                         '2a103720-5f25-11e9-9a9e-00163e12',
                                                         '2a103720a5f25a11e9a9a9ea00163e12',
                                                         '2c9386a86b6d7552016b6d7d8fa20001',
                                                         '2c93889a6ac39087016ac3ac40200001',
                                                         '8a99c40b696c365401696fe2e46c0007',
                                                         '8a99c40b696c365401696fe8728c000a',
                                                         '8a99c40b696c365401696feba0fe000b',
                                                         '8a99c40b696c365401696ff37e2d0010',
                                                         '8a99c40b696c36540169700126a8001a',
                                                         '8a99c40b696c365401697083baad0020',
                                                         '8a99c40b6970f47f016971730fcf0006',
                                                         '8a99c40b69765dd4016979bc894f0000',
                                                         '8a99c40b69765dd4016979bf9cc90001',
                                                         '8a99c40b6ab95521016abe51c8950000',
                                                         '8a9bc76c6f6b7aa2016f6bdfcce20002',
                                                         '8ab369076abe297e016abe6441620000',
                                                         'fc8e11a13fe311e9828800163e0fc468',
                                                         'fc8e11fa3fe311e9828800163e0fc468',
                                                         'fc8e21a43fe311e9828800163e0fc468',
                                                         'fc8e21f53fe311e9828800163e0fc468',
                                                         'fc8e252b3fe311e9828800163e0fc468',
                                                         'fc8e257c3fe311e9828800163e0fc468',
                                                         'fc8e262b3fe311e9828800163e0fc468',
                                                         'fc8e26a63fe311e9828800163e0fc468',
                                                         'ff8080816a590e49016ac4acf2790000',
                                                         'ff8080816a590e49016ac4ae13190001',
                                                         'ff8080816a590e49016ac4f516880002',
                                                         'ff8080816ac53f1c016ad2d549a20001',
                                                         'ff8080816b915d6c016b9192a5400002',
                                                         'ff8080816fa8b804016fac433c130001',
                                                         'ff8080816fa8b804016fac46e2860003',
                                                         'ff80808170860f0801708b4ef53e0002',
                                                         'ff80808170860f0801708b50875b0005',
                                                         'ff8080817171f3f201718c72794c0003',
                                                         'ff8080817171f3f201718c7394cb0004',
                                                         'ff808081724a86cd017255b64e0d0001',
                                                         'ff80808172b7ec610172cff20fd40000',
                                                         'ff80808172b7ec610172d001b01d0006',
                                                         'ff80808172d9abf50172fe1d6cd20000',
                                                         'ff808081730fdc4b017313ed6b680002',
                                                         'ff808081730fdc4b017313f994b30006',
                                                         'ff808081730fdc4b0173140c2e54000b',
                                                         'ff808081730fdc4b017322db2a3e0022',
                                                         'ff8080817345caf60173462825280000',
                                                         'ff8080817345caf60173462f9aac0007',
                                                         'ff80808173f2ae5f0173ff594f02000c',
                                                         'ff808081744bbf8101744cd9571f0002',
                                                         'ff80808179e9f307017a18145fa30001',
                                                         'ff80808179e9f307017a1815c2910002',
                                                         'ff80808179e9f307017a181635ff0003'
                                                            );

-- 2、拼团设置、分类、活动
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '2c93889a6ac39087016ac3ac40200001',
                                                                                                       'ff8080816a590e49016ac4f516880002',
                                                                                                       'ff8080816a590e49016ac4ae13190001'
    );

-- 3、删除拼团设置
update `sbc-setting`.`function_info` set del_flag = 1 where function_id in (
                                                            '2c9388916b0b86c7016b0bde4b530001',
                                                            '2c9388916b0b86c7016b0bdebc9b0002',
                                                            '2c9388916b0b86c7016b0bdf16000003'
    );

-- 4、分销商品 分销素材 分销记录 分销设置 分销员 邀新记录
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '8a99c40b696c365401696fe8728c000a',
                                                                                                       '2a103720a5f25a11e9a9a9ea00163e12',
                                                                                                       '8a99c40b69765dd4016979bf9cc90001',
                                                                                                       '8a99c40b696c365401697083baad0020',
                                                                                                       '8a99c40b696c365401696ff37e2d0010',
                                                                                                       '8a99c40b696c365401696feba0fe000b'
    );

-- 5、分销素材
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '0e64f07ba60bca11e9a9a9ea00163e12',
                                                                                                       '18309da1a60bca11e9a9a9ea00163e12',
                                                                                                       'a478322ba60bba11e9a9a9ea00163e12',
                                                                                                       'ff8080816bab200e016bab48f0650001',
                                                                                                       'ff8080816bab200e016bab78a9040004'
    );

-- 6、分销记录
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '8a99c40b6a06a8d7016a0a5fd75f000f',
                                                                                                       '8a99c40b6a14ca16016a154a09d90001'
    );

-- 7、分销设置
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '8a99c40b6a06a8d7016a0a5faa66000e',
                                                                                                       '8a99c40b6a06a8d7016a0a5fec930010'
    );

-- 8、分销员
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '8a99c40b696c365401696ffd08bc0012',
                                                                                                       '8a99c40b696c365401696ffd50ce0013',
                                                                                                       '8a99c40b696c365401696ffd89ce0014',
                                                                                                       '8a99c40b696c365401696ffdc0b60015',
                                                                                                       '8a99c40b6a11069d016a1464ca540000'
    );

-- 9、邀新记录
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f68730011' where  menu_id in (
                                                                                                       '8a99c40b696c365401696fed0740000c',
                                                                                                       '8a99c40b6a1f2de1016a28ff75f40000'
    );

-- 10、功能唯一建调整
ALTER TABLE `sbc-setting`.`function_info`
DROP INDEX `function_info_name_uk`,
ADD UNIQUE INDEX `function_info_name_uk`(`system_type_cd`, `function_name`, `del_flag`) USING BTREE;


-- 11、优惠券列表
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f884d0012' where  menu_id in (
    'ff80808179e9f307017a1815c2910002'
    );
-- 12、优惠券活动 + 分类
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f884d0012' where  function_id in (
    'fc9292bc3fe311e9828800163e0fc468',
    'fc9292603fe311e9828800163e0fc468',
    'fc9290373fe311e9828800163e0fc468',
    'fc9290f13fe311e9828800163e0fc468',
    'fc92914d3fe311e9828800163e0fc468'
    );

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f884d0012' where  menu_id in (
                                                                                                       'ff80808179e9f307017a181635ff0003',
                                                                                                       'fc8e252b3fe311e9828800163e0fc468'
    );

-- 13、秒杀
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21f884d0012' where  menu_id in (
    '2c9386a86b6d7552016b6d7d8fa20001'
    );

-- 14、第二件半价 预约 预售 打包一口价
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21fb99c0013' where  menu_id in (
                                                                                                       'ff80808172b7ec610172cff20fd40000',
                                                                                                       'ff80808172b7ec610172d001b01d0006',
                                                                                                       'ff8080817345caf60173462f9aac0007',
                                                                                                       'ff8080817171f3f201718c7394cb0004'
    );

-- 15、积分商城
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21fdfe70014' where  menu_id in (
    '8ab369076abe297e016abe6441620000'
    );

-- 16、短信触达 站内信 app push
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed22002b90015' where  menu_id in (
                                                                                                       '8a9bc76c6f6b7aa2016f6bdfcce20002',
                                                                                                       'ff8080816fa8b804016fac46e2860003',
                                                                                                       'ff8080816fa8b804016fac433c130001'
    );

-- 17、小程序直播、企业购、
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed2203dd40016' where  menu_id in (
                                                                                                       'ff808081730fdc4b017322db2a3e0022',
                                                                                                       'ff80808170860f0801708b4ef53e0002',
                                                                                                       'ff80808173f2ae5f0173ff594f02000c',
                                                                                                       'ff808081744bbf8101744cd9571f0002'
    );

-- 18、深度经营 2c939a4d7ea90aa6017ed21d348c000d
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21d348c000d' where  menu_id in (
                                                                                                       'ff808081730fdc4b017313ed6b680002',
                                                                                                       'ff808081730fdc4b017313f994b30006',
                                                                                                       'ff808081730fdc4b0173140c2e54000b',
                                                                                                       'ff80808170860f0801708b50875b0005'
    );

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21d348c000d' where  function_id in (
    'ff8080817516673801751a5f85530000'
    );
update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed2203dd40016' where function_id in (
                                                                                            'ff8080817516673801751a7f92470002'
    );




-- 19、获客拉新 2c939a4d7ea90aa6017ed21c6a0d0009

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21c6a0d0009' where  menu_id in (
                                                                                                       'ff8080816ac53f1c016ad2d549a20001',
                                                                                                       '8a99c40b6970f47f016971730fcf0006',
                                                                                                       '8a99c40b696c36540169700126a8001a',
                                                                                                       '8a99c40b69765dd4016979bc894f0000'
    );

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21c6a0d0009' where  function_id in (
    'ff8080816b0305a3016b068b6f890000'
    );



-- 20、下单转化 2c939a4d7ea90aa6017ed21c94b9000a

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21c94b9000a' where  menu_id in (
                                                                                                       'fc8e26a63fe311e9828800163e0fc468',
                                                                                                       'fc8e262b3fe311e9828800163e0fc468',
                                                                                                       'ff8080816b915d6c016b9192a5400002'
    );

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21c94b9000a' where  function_id in (
                                                                                                           'fc9294833fe311e9828800163e0fc468',
                                                                                                           'fc9294da3fe311e9828800163e0fc468'
    );


-- 21、提高客单 2c939a4d7ea90aa6017ed21cda08000b

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21cda08000b' where  function_id in (
                                                                                                           'fc927af93fe311e9828800163e0fc468',
                                                                                                           'fc9280af3fe311e9828800163e0fc468',
                                                                                                           'fc9281053fe311e9828800163e0fc468',
                                                                                                           'ff808081717da15601718115c9950002'
    );

update `sbc-setting`.`function_info` set menu_id = '2c939a4d7ea90aa6017ed21cda08000b' where  menu_id in (
                                                                                                       'fc8e11fa3fe311e9828800163e0fc468',
                                                                                                       'ff8080817171f3f201718c72794c0003',
                                                                                                       'ff80808172d9abf50172fe1d6cd20000',
                                                                                                       'ff808081724a86cd017255b64e0d0001',
                                                                                                       'ff8080817345caf60173462825280000'
    );


-- 22、其他补充
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('402880957ae743ef017af60e52f70028', 3, 'ff808081730fdc4b017313fc9eb90007', '查看直播商品列表', NULL, '/livegoods/list', 'POST', NULL, 1, '2021-07-30 14:16:12', 0);
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('402880957ae743ef017af5f65a220025', 3, 'ff808081791b544401791b6ec44a0004', '申请开放平台api设置', 'f_open_access_apply', NULL, 2, '2021-07-30 13:50:01', 0);
INSERT INTO `sbc-setting`.`function_info` (`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('402880957ae743ef017af5f60f8b0024', 3, 'ff808081791b544401791b6ec44a0004', '查看开放权限设置', 'f_open_access_detail', NULL, 1, '2021-07-30 13:49:42', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f954bd2017f95d0e1840003', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '全场赠券查询列表', 'f_coupon_activity_all_present_list', NULL, 14, '2022-03-17 10:59:29', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f954bd2017f95cce8090000', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '进店赠券查询列表', 'f_coupon_activity_store_list', NULL, 12, '2022-03-17 10:55:08', 0);

update `sbc-setting`.`function_info` set function_title = '精准发券查询列表',function_name = 'f_coupon_activity_specify_list' WHERE function_id = 'fc9295343fe311e9828800163e0fc468';


-- 23、菜单权限新增
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ea9163d810002', 3, '0', 1, '应用', NULL, 'marketing.png', 7, '2022-01-30 11:45:14', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21bb69b0007', 3, '2c939a4d7ea90aa6017ea9163d810002', 2, '应用中心', NULL, NULL, 1, '2022-02-07 10:55:39', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21bdf5e0008', 3, '2c939a4d7ea90aa6017ea9163d810002', 2, '高级应用', NULL, NULL, 2, '2022-02-07 10:55:49', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21c6a0d0009', 3, '2c939a4d7ea90aa6017ed21bb69b0007', 3, '获客拉新', '/pull-center', NULL, 1, '2022-02-07 10:56:25', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21c94b9000a', 3, '2c939a4d7ea90aa6017ed21bb69b0007', 3, '下单转化', '/change-center', NULL, 2, '2022-02-07 10:56:36', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21cda08000b', 3, '2c939a4d7ea90aa6017ed21bb69b0007', 3, '提高客单', '/add-center', '', 3, '2022-02-07 10:56:53', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21d0470000c', 3, '2c939a4d7ea90aa6017ed21bb69b0007', 3, '留存复购', '/keep-center', NULL, 4, '2022-02-07 10:57:04', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21d348c000d', 3, '2c939a4d7ea90aa6017ed21bdf5e0008', 3, '深度经营', '/into-center', NULL, 1, '2022-02-07 10:57:16', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21ec44d000e', 4, '0', 1, '应用', NULL, 'marketing.png', 10, '2022-02-07 10:58:59', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21f0775000f', 4, '2c939a4d7ea90aa6017ed21ec44d000e', 2, '应用中心', NULL, NULL, 1, '2022-02-07 10:59:16', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21f30740010', 4, '2c939a4d7ea90aa6017ed21ec44d000e', 2, '高级应用', NULL, NULL, 2, '2022-02-07 10:59:26', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21f68730011', 4, '2c939a4d7ea90aa6017ed21f0775000f', 3, '获客拉新', '/pull-center', NULL, 1, '2022-02-07 10:59:41', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21f884d0012', 4, '2c939a4d7ea90aa6017ed21f0775000f', 3, '下单转化', '/change-center', NULL, 2, '2022-02-07 10:59:49', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21fb99c0013', 4, '2c939a4d7ea90aa6017ed21f0775000f', 3, '提高客单', '/add-center', NULL, 3, '2022-02-07 11:00:02', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed21fdfe70014', 4, '2c939a4d7ea90aa6017ed21f0775000f', 3, '留存复购', '/keep-center', NULL, 4, '2022-02-07 11:00:11', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed22002b90015', 4, '2c939a4d7ea90aa6017ed21f0775000f', 3, '实用工具', '/tool-center', NULL, 5, '2022-02-07 11:00:20', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed2203dd40016', 4, '2c939a4d7ea90aa6017ed21f30740010', 3, '深度经营', '/into-center', NULL, 1, '2022-02-07 11:00:35', 0);

INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed22e6705001e', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '限时抢购列表', 'f_rush_to_buy_list', NULL, 10, '2022-02-07 11:16:03', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed230fa65001f', 3, '2c939a4d7ea90aa6017ed21cda08000b', '满减', 'f_marketing_reduction_list', NULL, 1, '2022-02-07 11:18:52', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed23147960020', 3, '2c939a4d7ea90aa6017ed21cda08000b', '满折', 'f_marketing_discount_list', NULL, 2, '2022-02-07 11:19:12', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed231f0c90021', 3, '2c939a4d7ea90aa6017ed21cda08000b', '满赠', 'f_marketing_gift_list', NULL, 3, '2022-02-07 11:19:55', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed23d93f7002b', 4, '2c939a4d7ea90aa6017ed21f68730011', '小程序', 'f_h_mini_interface', NULL, 1, '2022-02-07 11:32:38', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed2418ca6003a', 4, '2c939a4d7ea90aa6017ed21fb99c0013', '满减', 'f_full_reduce_list_supplier', NULL, 1, '2022-02-07 11:36:58', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed241b9d9003b', 4, '2c939a4d7ea90aa6017ed21fb99c0013', '满折', 'f_full_discount_list_supplier', NULL, 2, '2022-02-07 11:37:10', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed241f47e003c', 4, '2c939a4d7ea90aa6017ed21fb99c0013', '满赠', 'f_full_gift_list_supplier', NULL, 3, '2022-02-07 11:37:25', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed24313ab0042', 4, '2c939a4d7ea90aa6017ed21fb99c0013', '组合购', 'f_suit_list', NULL, 9, '2022-02-07 11:38:38', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed7d455060053', 4, '2c939a4d7ea90aa6017ed21f68730011', '编辑拼团设置', 'f_edit_groupon_setting', '编辑拼团设置', 2, '2022-02-08 13:35:24', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7edc25d8017edc5737a60000', 4, '2c939a4d7ea90aa6017ed21f884d0012', '商家优惠券', 'f_coupon_list_supplier', NULL, 2, '2022-02-09 10:36:50', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7edc25d8017edc8933bf0001', 4, '2c939a4d7ea90aa6017ed21f884d0012', '商家优惠券活动', 'f_coupon-activity-list-supplier', NULL, 3, '2022-02-09 11:31:26', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7edc25d8017ede27f5ae0002', 3, '2c939a4d7ea90aa6017ed21cda08000b', '组合购', 'f_marketing_suit_list', NULL, 4, '2022-02-09 19:04:28', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017efc4370460000', 4, '2c939a4d7ea90aa6017ed21f884d0012', '限时抢购列表', 'f_rush_to_buy_list_supplier', NULL, 10, '2022-02-15 15:23:05', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017efc78e9930001', 4, '2c939a4d7ea90aa6017ed21f884d0012', '限时抢购海报', 'f_rush_to_buy_poster', NULL, 11, '2022-02-15 16:21:30', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017efc795cd20002', 4, '2c939a4d7ea90aa6017ed21f884d0012', '限时抢购活动详情', 'f_rush_to_buy_detail_supplier', NULL, 12, '2022-02-15 16:21:59', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017efcbf75a50003', 4, '2c939a4d7ea90aa6017ed21f884d0012', '限时抢购海报保存', 'f_rush_to_buy_poster_save', NULL, 14, '2022-02-15 17:38:33', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c29d61e0000', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '限时抢购新增编辑', 'f_rush_to_buy_add', NULL, 10, '2022-02-18 17:29:03', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c2b5bba0001', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '限时抢购详情', 'f_rush_to_buy_detail', NULL, 10, '2022-02-18 17:30:42', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c2be4920002', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '限时抢购删除', 'f_rush_to_buy_del', NULL, 10, '2022-02-18 17:31:18', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c2dc3640003', 3, '2c939a4d7ea90aa6017ed21c94b9000a', '限时抢购开始暂停', 'f_rush_to_buy_start', NULL, 10, '2022-02-18 17:33:20', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f2669de0d0000', 4, '2c939a4d7ea90aa6017ed21f884d0012', '营销推广', 'f_marketing_extend', NULL, 1, '2022-02-23 19:49:07', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f29c528b30002', 3, '2c939a4d7ea90aa6017ed21c6a0d0009', '分销商品列表导入商品', 'f_distribution_goods_import', NULL, 100, '2022-02-24 11:27:41', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f29d547100007', 3, '2c939a4d7ea90aa6017ed21d348c000d', '企业购商品导入', 'f_enterprise_goods_import', NULL, 100, '2022-02-24 11:45:18', 0);

INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7ea90aa6017ed7d5323b0054', 4, '2c939a4d7ea90aa6017ed7d455060053', '编辑拼团设置', '编辑拼团设置', '/groupon/setting/save', 'PUT', NULL, 1, '2022-02-08 13:36:21', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f006b89250004', 4, '2c939a4d7edc25d8017edc5737a60000', '商家优惠券列表查询', '商家优惠券列表查询', '/coupon-info/supplier/page', 'POST', NULL, 1, '2022-02-16 10:45:22', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f0071255f0005', 4, '2c939a4d7edc25d8017edc8933bf0001', '商品优惠券活动查询', '商品优惠券活动查询', '/coupon-activity/supplier/page', 'POST', NULL, 2, '2022-02-16 10:51:30', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f00769c300006', 4, '2c939a4d7efb6025017efc4370460000', '限时抢购列表查询', '限时抢购列表查询', '/flashSale/page', 'POST', NULL, 1, '2022-02-16 10:57:28', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f00772f120007', 4, '2c939a4d7efb6025017efc78e9930001', '限时抢购海报查询', '限时抢购海报查询', '/flashSale/poster/list', 'GET', NULL, 2, '2022-02-16 10:58:05', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f0077cd1f0008', 4, '2c939a4d7efb6025017efcbf75a50003', '限时抢购海报保存', '限时抢购海报保存', '/flashSale/poster/edit', 'POST', NULL, 1, '2022-02-16 10:58:46', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f007922cf0009', 4, '2c939a4d7efb6025017efc795cd20002', '限时抢购详情查看', '限时抢购详情查看', '/flashSale/flashPromotion/*', 'GET', NULL, 1, '2022-02-16 11:00:13', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f007b9dee000a', 4, '2c939a4d7ea90aa6017ed2418ca6003a', '商家营销活动查询', '商家营销活动查询', '/marketing/supplier/list', 'POST', NULL, 1, '2022-02-16 11:02:56', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7efb6025017f0088f876000b', 4, '2c939a4d7ea90aa6017ed24313ab0042', '组合购详情查询', '组合购详情查询', '/marketing/getGoodsSuitsDetail/*', 'GET', NULL, 1, '2022-02-16 11:17:31', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c4090ac0004', 3, '2c939a4d7ea90aa6017ed22e6705001e', '限时抢购列表查询', NULL, '/flashSale/page', 'POST', NULL, 1, '2022-02-18 17:53:52', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c4162f20005', 3, '2c939a4d7f0ab77f017f0c2be4920002', '限时抢购删除', NULL, '/flashsalegoods/flashPromotion/*', 'DELETE', NULL, 1, '2022-02-18 17:54:46', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c41d5f20006', 3, '2c939a4d7f0ab77f017f0c2dc3640003', '限时抢购开始暂停', NULL, '/flashsalegoods/flashPromotion/modifyStatus', 'PUT', NULL, 1, '2022-02-18 17:55:16', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c44ce020007', 3, '2c939a4d7f0ab77f017f0c2b5bba0001', '限时抢购详情查询', NULL, '/flashSale/flashPromotion/*', 'GET', NULL, 1, '2022-02-18 17:58:30', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c47ecdb0008', 3, '2c939a4d7f0ab77f017f0c2b5bba0001', '修改弹窗', NULL, '/pageInfoExtend/query', 'POST', NULL, 1, '2022-02-18 18:01:55', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c48b0a20009', 3, '2c939a4d7f0ab77f017f0c2b5bba0001', '编辑页面投放', NULL, '/pageInfoExtend/modify', 'PUT', NULL, 2, '2022-02-18 18:02:45', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c4920ec000a', 3, '2c939a4d7f0ab77f017f0c2b5bba0001', '获取微信配置', NULL, '/third/wechatSet', 'GET', NULL, 1, '2022-02-18 18:03:14', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c4bbdeb000b', 3, '2c939a4d7f0ab77f017f0c29d61e0000', '限时抢购详情查看', NULL, '/flashSale/flashPromotion/*', 'GET', NULL, 1, '2022-02-18 18:06:05', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c4d2f09000c', 3, '2c939a4d7f0ab77f017f0c29d61e0000', '限时抢购新增', NULL, '/flashsalegoods/batchAdd', 'POST', NULL, 2, '2022-02-18 18:07:39', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f0ab77f017f0c4d9553000d', 3, '2c939a4d7f0ab77f017f0c29d61e0000', '限时抢购编辑', NULL, '/flashsalegoods/flashPromotion/modify', 'PUT', NULL, 3, '2022-02-18 18:08:05', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f266a33310001', 4, '2c939a4d7f26331b017f2669de0d0000', '营销推广', NULL, '/marketing/getExtendInfo', 'POST', NULL, 1, '2022-02-23 19:49:28', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f29cb20ae0003', 3, '2c939a4d7f26331b017f29c528b30002', '下载模板', NULL, '/marketing/goods/template/*', 'GET', NULL, 1, '2022-02-24 11:34:12', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f29cbf7120004', 3, '2c939a4d7f26331b017f29c528b30002', '上传文件', NULL, '/marketing/excel/upload', 'POST', NULL, 2, '2022-02-24 11:35:07', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f29cd472f0005', 3, '2c939a4d7f26331b017f29c528b30002', '下载错误文件', NULL, '/marketing/excel/err/*/*', 'GET', NULL, 3, '2022-02-24 11:36:33', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f29ce06b00006', 3, '2c939a4d7f26331b017f29c528b30002', '查询导入数据', NULL, 'marketing/goods/list/*', 'GET', NULL, 4, '2022-02-24 11:37:22', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f2a3ba7f30008', 3, '2c939a4d7f26331b017f29d547100007', '下载模板', NULL, '/marketing/goods/template/*', 'GET', NULL, 1, '2022-02-24 13:37:07', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f2a3c43870009', 3, '2c939a4d7f26331b017f29d547100007', '上传文件', NULL, '/marketing/excel/upload', 'POST', NULL, 2, '2022-02-24 13:37:47', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f2a3d9d58000a', 3, '2c939a4d7f26331b017f29d547100007', '下载错误文件', NULL, '/marketing/excel/err/*/*', 'GET', NULL, 3, '2022-02-24 13:39:15', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4d7f26331b017f2a3ed872000b', 3, '2c939a4d7f26331b017f29d547100007', '查询导入数据', NULL, 'marketing/goods/list/*', 'GET', NULL, 4, '2022-02-24 13:40:36', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f954bd2017f95d5553e0005', 3, 'ff8080817f954bd2017f95d0e1840003', '获取所有客户等级', NULL, '/storelevel/levels', 'GET', NULL, 2, '2022-03-17 11:04:21', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f954bd2017f95d4c77e0004', 3, 'ff8080817f954bd2017f95d0e1840003', '获取活动列表', NULL, '/coupon-activity/page', 'POST', NULL, 1, '2022-03-17 11:03:44', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f954bd2017f95cf73990002', 3, 'ff8080817f954bd2017f95cce8090000', '获取所有客户等级', NULL, '/storelevel/levels', 'GET', NULL, 2, '2022-03-17 10:57:55', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f954bd2017f95cea6d90001', 3, 'ff8080817f954bd2017f95cce8090000', '获取活动列表', NULL, '/coupon-activity/page', 'POST', NULL, 1, '2022-03-17 10:57:03', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f962c0e017f9779b9a90006', 4, '2c939a4d7ea90aa6017ed241f47e003c', '会员等级列表查询', NULL, '/customer/levellist', 'GET', NULL, 2, '2022-03-17 18:43:31', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f962c0e017f97794f410005', 4, '2c939a4d7ea90aa6017ed241f47e003c', '商家营销活动查询', NULL, '/marketing/supplier/list', 'POST', NULL, 1, '2022-03-17 18:43:04', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f962c0e017f9777b4e80004', 4, '2c939a4d7ea90aa6017ed241b9d9003b', '会员等级列表查询', NULL, '/customer/levellist', 'GET', NULL, 2, '2022-03-17 18:41:19', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f962c0e017f9777319d0003', 4, '2c939a4d7ea90aa6017ed241b9d9003b', '商家营销活动查询', NULL, '/marketing/supplier/list', 'POST', NULL, 1, '2022-03-17 18:40:45', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f962c0e017f975a2ed10002', 4, '2c939a4d7ea90aa6017ed2418ca6003a', '会员等级列表查询', NULL, '/customer/levellist', 'GET', NULL, 2, '2022-03-17 18:09:04', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9ba808a80007', 3, '2c939a4d7edc25d8017ede27f5ae0002', '查询店铺等级', NULL, '/store/storeLevel/listBoss', 'GET', NULL, 2, '2022-03-18 14:12:35', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9ba73be40006', 3, '2c939a4d7edc25d8017ede27f5ae0002', '营销活动查询列表', NULL, '/marketing/list', 'POST', NULL, 1, '2022-03-18 14:11:43', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9ba693de0005', 3, '2c939a4d7ea90aa6017ed231f0c90021', '营销活动查询列表', NULL, '/marketing/list', 'POST', NULL, 1, '2022-03-18 14:11:00', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9ba5d8190004', 3, '2c939a4d7ea90aa6017ed231f0c90021', '查询店铺等级', NULL, '/store/storeLevel/listBoss', 'GET', NULL, 2, '2022-03-18 14:10:12', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9ba530040003', 3, '2c939a4d7ea90aa6017ed23147960020', '查询店铺等级', NULL, '/store/storeLevel/listBoss', 'GET', NULL, 2, '2022-03-18 14:09:29', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9ba373f90002', 3, '2c939a4d7ea90aa6017ed23147960020', '营销活动查询列表', NULL, '/marketing/list', 'POST', NULL, 1, '2022-03-18 14:07:35', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9b9851740001', 3, '2c939a4d7ea90aa6017ed230fa65001f', '查询店铺等级', NULL, '/store/storeLevel/listBoss', 'GET', NULL, 2, '2022-03-18 13:55:25', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080817f9b51d8017f9b8cfe4a0000', 3, '2c939a4d7ea90aa6017ed230fa65001f', '营销活动查询列表', NULL, '/marketing/list', 'POST', NULL, 1, '2022-03-18 13:43:03', 0);

