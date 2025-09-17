-- 权限脚本
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a827d285278017d73e2c7690005', 4, 'fc8df5563fe311e9828800163e0fc468', '退款原因编辑', 'f_refund_cause_modify', '', 4, '2021-12-01 10:46:22', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a827d285278017d73e11c1c0003', 4, 'fc8df5563fe311e9828800163e0fc468', '退款原因查看', 'f_refund_cause_list', NULL, 3, '2021-12-01 10:44:32', 0);

INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a827d285278017d73e329760006', 4, '2c939a827d285278017d73e2c7690005', '编辑退款原因', NULL, '/refund/cause-modify', 'POST', NULL, 1, '2021-12-01 10:46:47', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a827d285278017d73e21fcf0004', 4, '2c939a827d285278017d73e11c1c0003', '查看退款原因', NULL, '/refund/cause-list', 'GET', NULL, 1, '2021-12-01 10:45:39', 0);



-- 退款原因表
DROP TABLE IF EXISTS `sbc-setting`.`refund_cause_tbl`;
CREATE TABLE `sbc-setting`.`refund_cause_tbl`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cause` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sort` bigint(11) NOT NULL,
  `del_flag` int(4) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_person` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- 在途退货开关数据初始化
INSERT INTO `sbc-setting`.`system_config`(`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('order_setting', 'order_setting_along_refund', '允许在途退货', '开启时，部分发货、待收货订单也可发起退货申请，关闭时，只有已完成、未发货订单才可以发起退货申请', 0, '', '2021-11-16 10:03:32', '2021-11-16 10:03:35', 0);

-- 退款原因初始化
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('74a3fd68c5254240b258591e66283cdf', '货品与描述不符', 3, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('a6d1779850744e98b4484be5311adb38', '货品质量问题', 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('a9e6cb843ad94ee9826f34df2e503a2b', '没收到货', 5, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('bd79ff2021e44151a9ddc47db7d81ad7', '其他', 7, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('c8602c87057640029457fc3092bddbd0', '商家发错货', 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('de8bf286548547588d0befb57f9e5251', '货品少件/受损/污渍等', 2, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('e24d1d5f5fd84270bb4511d7b25b2670', '不想要了', 6, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sbc-setting`.`refund_cause_tbl`(`id`, `cause`, `sort`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`) VALUES ('e2935a66670946a5b7cccfad9d0bd5d7', '发货时间问题', 4, 0, NULL, NULL, NULL, NULL, NULL, NULL);