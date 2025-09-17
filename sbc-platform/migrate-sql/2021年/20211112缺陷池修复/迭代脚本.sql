/*sbc-setting 库 执行*/
DELETE FROM `sbc-setting`.`store_express_company_rela` WHERE express_company_id in (SELECT express_company_id FROM `sbc-setting`.`express_company` WHERE del_flag=1);

ALTER TABLE `s2b_statistics`.`replay_trade_item`
ADD INDEX `spu_id_index`(`spu_id`) USING BTREE;