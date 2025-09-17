UPDATE `sbc-setting`.`authority` SET `authority_url` = '/customer/addressAllList/*' WHERE `authority_id` = 'fc9790ad3fe311e9828800163e0fc468';
UPDATE `sbc-setting`.`authority` SET `authority_url` = '/customer/addressAllList/*' WHERE `authority_id` = 'ff80808170d2087a0170d22dafbd0003';

INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818fc6cd18018fc771fb840001', 4, '2c9384e480d676160180d689de9c0006', '视频上传', NULL, '/api/upload/video', 'POST', NULL, 3, '2024-05-30 11:01:27', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818fc6cd18018fc7713a140000', 4, '2c9384e480d676160180d689de9c0006', '视频列表', NULL, '/api/gallery/video/list', 'POST', NULL, 2, '2024-05-30 11:00:38', 0);


UPDATE `sbc-setting`.`authority` SET `authority_url` = '/store/provider/name' WHERE `authority_id` = 'ff80808170f2a69001710a8afd210005';
UPDATE `sbc-setting`.`authority` SET `authority_url` = '/store/supplier/name' WHERE `authority_id` = 'fc97aad63fe311e9828800163e0fc468';

INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818fe5b374018fe64d4a980000', 3, '8a9bc76c6a673f39016a67d571e4000e', '是否展示评价', NULL, '/goods/evaluate/isShow', 'POST', NULL, 7, '2024-06-05 10:49:36', 0);
INSERT INTO `sbc-setting`.`authority` (`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('ff8080818fbc808b018fbd0817e70004', 3, 'ff8080818fbc808b018fbd06ad670002', '会员信息查询', NULL, '/customer/pageBossAll', 'POST', NULL, 2, '2024-05-28 10:29:35', 0);

