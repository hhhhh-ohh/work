
-- 招商页新增跨境商家入驻协议
alter table `sbc-setting`.`business_config`
    add cross_supplier_enter longtext null comment '跨境商家入驻协议';

-- 商家新增是否跨境商家类别
alter table `sbc-customer`.`company_info`
    add column `supplier_type` tinyint null comment '商家类型 0 普通商家,1 跨境商家';
alter table `sbc-customer`.`store`
    add column `supplier_type` tinyint null comment '商家类型 0 普通商家,1 跨境商家';

ALTER TABLE `sbc-goods`.`goods_info`
    ADD  `plugin_type` tinyint(4) NULL DEFAULT 0 COMMENT '商品类型；0:一般商品 1:跨境商品'
        AFTER `goods_id`;

ALTER TABLE `sbc-goods`.`goods`
    ADD  `plugin_type` tinyint(4) NULL DEFAULT 0 COMMENT '商品类型；0:一般商品 1:跨境商品';

alter table `sbc-goods`.`goods`
    add column `supplier_type` tinyint null comment '商家类型 0 普通商家,1 跨境商家';
alter table `sbc-goods`.`goods_info`
    add column `supplier_type` tinyint null comment '商家类型 0 普通商家,1 跨境商家';


alter table `sbc-goods`.`goods`
    add column `store_type` tinyint null comment '商家类型：0供应商，1商家，2：O2O商家，3：跨境商家';
alter table `sbc-goods`.`goods_info`
    add column `store_type` tinyint null comment '商家类型：0供应商，1商家，2：O2O商家，3：跨境商家';