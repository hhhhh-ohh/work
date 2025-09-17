-- 修改打款金额上线为20位整数
ALTER TABLE `sbc-account`.`company_account`
MODIFY COLUMN `remit_price` decimal(22, 2) NULL DEFAULT NULL COMMENT '打款金额' AFTER `del_time`;
-- 索引优化
ALTER TABLE `sbc-goods`.`goods_customer_price` ADD INDEX idx_goods_info_id (`goods_info_id`);

ALTER TABLE `sbc-goods`.`standard_sku`
ADD COLUMN `goods_info_barcode` varchar(45) CHARACTER SET utf8 COLLATE
utf8_general_ci NULL DEFAULT NULL COMMENT '条形码';

-- 迁移20210407hotfix代码
ALTER TABLE `sbc-goods`.`goods_image`
ADD COLUMN `sort` int(10) NULL COMMENT '图片排序字段' AFTER `big_url`;

update `sbc-setting`.`store_resource_cate` set cate_name = '全部分类' where cate_name='默认分类' and cate_parent_id = 0 and is_default = 1;
update `sbc-setting`.`system_resource_cate` set cate_name = '全部分类' where cate_name='默认分类' and cate_parent_id = 0 and is_default = 1;
