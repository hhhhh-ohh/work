ALTER TABLE `sbc-goods`.`buy_cycle_goods`
ADD COLUMN `optional_num` tinyint(4) NULL COMMENT '自选期数，当delivery_cycle=6或7时有值' AFTER `delivery_cycle`;

ALTER TABLE `sbc-marketing`.`gift_card_batch`
    ADD COLUMN `export_mini_code_type` tinyint(4) NULL COMMENT '是否导出小程序一卡一码URL，0:不导出，1：导出' AFTER `excel_file_path`,
ADD COLUMN `export_web_code_type` tinyint(4) NULL COMMENT '是否导出H5一卡一码URL，0:不导出，1：导出' AFTER `export_mini_code_type`;

update `sbc-setting`.base_config set version = 'SBC V5.9.0';

