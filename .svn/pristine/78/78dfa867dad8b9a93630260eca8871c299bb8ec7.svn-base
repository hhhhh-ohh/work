-- 营销插件配置
TRUNCATE TABLE `sbc-marketing`.`marketing_plugin_config`;
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (35, 'COUPON', 1, 'POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '优惠券');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (36, 'POINT_AND_CASH', 2, 'COUPON,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '积分+现金');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (37, 'APPOINTMENT_SALE', 3, 'COUPON,RETURN', '预约');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (38, 'BOOKING_SALE', 4, 'COUPON,RETURN', '预售');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (39, 'FLASH_SALE', 5, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (40, 'FLASH_PROMOTION', 6, 'COUPON', '限时购');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (41, 'DISTRIBUTION', 7, 'COUPON,RETURN', '分销');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (42, 'ENTERPRISE_PRICE', 8, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '企业价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (43, 'GROUPON', 9, 'COUPON,RETURN', '拼团');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (44, 'CUSTOMER_PRICE', 11, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (45, 'CUSTOMER_LEVEL', 12, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,PAYING_MEMBER,NEW_COMER_COUPON', '会员等级价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (46, 'PAYING_MEMBER', 13, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '付费会员价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (47, 'REDUCTION', 14, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满减');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (48, 'DISCOUNT', 15, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满折');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (49, 'GIFT', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满赠');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (50, 'BUYOUT_PRICE', 17, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '打包一口价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (51, 'HALF_PRICE_SECOND_PIECE', 18, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,NEW_COMER_COUPON', '第二件半价');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (52, 'RETURN', 19, 'COUPON,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满返');
INSERT INTO `sbc-marketing`.`marketing_plugin_config`(`id`, `marketing_type`, `sort`, `coexist`, `description`) VALUES (53, 'NEW_COMER_COUPON', 20, 'POINT_AND_CASH,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '新人专享券');

-- 新人购优惠券
CREATE TABLE `sbc-marketing`.newcomer_purchase_coupon(
                                                         id int(11) NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
                                                         coupon_id VARCHAR(32) UNIQUE  COMMENT '优惠券id' ,
                                                         coupon_name VARCHAR(20)    COMMENT '优惠券名称' ,
                                                         activity_stock bigint(10)    COMMENT '活动原始库存' ,
                                                         coupon_stock bigint(10)    COMMENT '券组库存' ,
                                                         group_of_num int(10)    COMMENT '每组赠送数量' ,
                                                         create_time DATETIME    COMMENT '创建时间' ,
                                                         create_person VARCHAR(32)    COMMENT '' ,
                                                         update_time DATETIME    COMMENT '更新时间' ,
                                                         update_person VARCHAR(32)    COMMENT '' ,
                                                         del_flag TINYINT(1)    COMMENT '删除标识：0：未删除；1：已删除' ,
                                                         PRIMARY KEY (id)
) comment = '新人购优惠券';


-- 新人专享设置
CREATE TABLE `sbc-marketing`.newcomer_purchase_config(
                                                         id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
                                                         poster VARCHAR(2048) COMMENT '海报设置' ,
                                                         coupon_layout TINYINT(4) COMMENT '优惠券样式布局' ,
                                                         goods_layout TINYINT(4) COMMENT '商品样式布局' ,
                                                         rule_detail text COMMENT '活动规则详细' ,
                                                         create_time DATETIME COMMENT '创建时间' ,
                                                         create_person VARCHAR(32) COMMENT '' ,
                                                         update_time DATETIME COMMENT '更新时间' ,
                                                         update_person VARCHAR(32) COMMENT '' ,
                                                         del_flag TINYINT(1) COMMENT '删除标识：0：未删除；1：已删除' ,
                                                         PRIMARY KEY (id)
) COMMENT = '新人专享设置';

-- 新人购商品表
create table `sbc-goods`.newcomer_purchase_goods
(
    id            int auto_increment comment '主键' primary key,
    goods_info_id varchar(32) null comment 'sku ID',
    create_time   datetime    null comment '创建时间',
    create_person varchar(32) null,
    update_time   datetime    null comment '更新时间',
    update_person varchar(32) null,
    del_flag      tinyint(1)  null comment '删除标识：0：未删除；1：已删除'
) comment '新人购商品表';

-- customer表加入是否新人字段
alter table `sbc-customer`.customer   add is_new tinyint(4) null comment '是否新人  0-是  1-否';
-- 数据统计表中加是否新人字段
alter table `s2b_statistics`.replay_customer   add is_new tinyint(4) null comment '是否新人  0-是  1-否';

-- seata
ALTER TABLE `seata`.`branch_table`
    MODIFY COLUMN `application_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL;


-- 数谋报错 缺失字段
ALTER TABLE `s2b_statistics`.`replay_coupon_info`
    ADD COLUMN `participate_type` tinyint(4) NULL  COMMENT '门店营销类型 0全部门店，1自定义门店' AFTER `del_person`;