-- 默认默板
ALTER TABLE `sbc-goods`.`freight_template_goods`
    ADD INDEX `idx_default`(`store_id`, `default_flag`, `del_flag`);

-- 店铺分类
ALTER TABLE `sbc-goods`.`store_cate`
    ADD INDEX `idx_store_cate_id`(`store_cate_id`),
ADD INDEX `idx_del_flag`(`del_flag`);

-- 配置索引
ALTER TABLE `sbc-setting`.`system_config`
    ADD INDEX `idx_del_flag`(`del_flag`);

-- 商品图片
ALTER TABLE `sbc-goods`.`goods_image`
    ADD INDEX `idx_del_flag`(`del_flag`);

-- 商品规格
ALTER TABLE `sbc-goods`.`goods_spec`
    ADD INDEX `idx_del_flag`(`del_flag`);

-- 商品规格明细
ALTER TABLE `sbc-goods`.`goods_spec_detail`
    ADD INDEX `idx_del_flag`(`del_flag`);

-- 主账号
ALTER TABLE `sbc-customer`.`employee`
    ADD INDEX `idx_master`(`del_flag`, `company_info_id`, `is_master_account`);

-- 会员等级
ALTER TABLE `sbc-customer`.`customer_level`
    ADD INDEX `idx_default`(`is_defalt`);

-- 会员店铺等级
ALTER TABLE `sbc-customer`.`store_level`
    ADD INDEX `idx_del_flag`(`del_flag`),
ADD INDEX `idx_create_time`(`create_time`);

-- 开放权限表格
CREATE TABLE `sbc-setting`.`open_api_setting`
(
    `id`                  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`            bigint(20) NOT NULL COMMENT '店铺id',
    `store_name`          varchar(150) DEFAULT NULL COMMENT '店铺名称',
    `supplier_name`       varchar(50)  DEFAULT NULL COMMENT '商家名称',
    `store_type`          tinyint(4) NOT NULL COMMENT '商家类型：0:供应商；1:平台自营；2:第三方商家；',
    `contract_start_date` datetime     NOT NULL COMMENT '签约开始日期',
    `contract_end_date`   datetime     DEFAULT NULL COMMENT '签约结束日期',
    `audit_state`         tinyint(4) NOT NULL COMMENT '审核状态 0、待审核 1、已审核 2、审核未通过',
    `audit_reason`        varchar(255) DEFAULT NULL COMMENT '审核未通过原因',
    `disable_state`       tinyint(4) NOT NULL COMMENT '禁用状态:0:禁用；1:启用',
    `disable_reason`      varchar(255) DEFAULT NULL COMMENT '禁用原因',
    `app_key`             varchar(150) NOT NULL COMMENT 'app_key',
    `app_secret`          varchar(150) DEFAULT NULL COMMENT 'app_secret',
    `limiting_num`        bigint(10) DEFAULT NULL COMMENT '限流值',
    `del_flag`            tinyint(4) NOT NULL COMMENT '是否删除 0 否  1 是',
    `create_time`         datetime     DEFAULT NULL COMMENT '创建时间',
    `create_person`       varchar(32)  DEFAULT NULL COMMENT '创建人',
    `update_time`         datetime     DEFAULT NULL COMMENT '修改时间',
    `update_person`       varchar(32)  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    KEY                   `idx_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='开放平台api设置';

alter table `sbc-setting`.`open_api_setting` MODIFY `app_key` varchar(150) DEFAULT NULL COMMENT 'app_key';