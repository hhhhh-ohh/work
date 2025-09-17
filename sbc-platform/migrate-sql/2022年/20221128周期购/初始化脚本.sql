-- 清除 营销插件配置
TRUNCATE TABLE `sbc-marketing`.`marketing_plugin_config`;
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (1, 'COUPON', 1, 'BUY_CYCLE,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '优惠券');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (2, 'BUY_CYCLE', 2, 'COUPON,PAYING_MEMBER,CUSTOMER_PRICE,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN', '周期购');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (3, 'POINT_AND_CASH', 3, 'COUPON,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '积分+现金');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (4, 'APPOINTMENT_SALE', 4, 'COUPON,RETURN', '预约');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (5, 'BOOKING_SALE', 5, 'COUPON,RETURN', '预售');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (6, 'FLASH_SALE', 6, 'COUPON', '秒杀');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (7, 'FLASH_PROMOTION', 7, 'COUPON', '限时购');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (8, 'DISTRIBUTION', 8, 'COUPON,RETURN', '分销');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (9, 'ENTERPRISE_PRICE', 9, 'COUPON,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '企业价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (10, 'GROUPON', 10, 'COUPON,RETURN', '拼团');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (11, 'CUSTOMER_PRICE', 11, 'COUPON,BUY_CYCLE,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '会员价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (12, 'CUSTOMER_LEVEL', 12, 'COUPON,BUY_CYCLE,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,PAYING_MEMBER,NEW_COMER_COUPON', '会员等级价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (13, 'PAYING_MEMBER', 13, 'COUPON,BUY_CYCLE,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '付费会员价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (14, 'REDUCTION', 14, 'COUPON,BUY_CYCLE,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满减');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (15, 'DISCOUNT', 15, 'COUPON,BUY_CYCLE,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满折');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (16, 'GIFT', 16, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满赠');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (17, 'BUYOUT_PRICE', 17, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '打包一口价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (18, 'HALF_PRICE_SECOND_PIECE', 18, 'COUPON,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,NEW_COMER_COUPON', '第二件半价');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (19, 'RETURN', 19, 'COUPON,BUY_CYCLE,POINT_AND_CASH,GROUPON,FLASH_SALE,FLASH_PROMOTION,APPOINTMENT_SALE,BOOKING_SALE,DISTRIBUTION,ENTERPRISE_PRICE,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE,NEW_COMER_COUPON', '满返');
INSERT INTO `sbc-marketing`.marketing_plugin_config (id, marketing_type, sort, coexist, description) VALUES (20, 'NEW_COMER_COUPON', 20, 'POINT_AND_CASH,CUSTOMER_PRICE,PAYING_MEMBER,CUSTOMER_LEVEL,REDUCTION,DISCOUNT,GIFT,RETURN,BUYOUT_PRICE,HALF_PRICE_SECOND_PIECE', '新人专享券');
-- 收藏商品新增字段
ALTER TABLE `sbc-order`.`goods_customer_follow`
    ADD COLUMN `is_buy_cycle` tinyint(4) NULL DEFAULT 0 COMMENT '是否周期购商品 0:否 1:是';

alter table `sbc-goods`.goods   add is_buy_cycle tinyint default 0  comment '是否参与周期购 0 否  1 是';
alter table `sbc-goods`.goods_info  add is_buy_cycle tinyint default 0  comment '是否参与周期购 0 否  1 是';

DROP TABLE IF EXISTS `sbc-goods`.buy_cycle_goods;
create table `sbc-goods`.buy_cycle_goods
(
    id                bigint(11) auto_increment comment '主键'
        primary key,
    goods_id          varchar(32)  not null comment 'spuId',
    delivery_cycle    tinyint      null comment '配送周期',
    delivery_date     varchar(256) null comment '用户可选送达日期',
    reserve_day       int          null comment '预留时间 日期',
    reserve_time      int(8)       null comment '预留时间 时间点',
    postage_threshold int          null comment '包邮门槛',
    cycle_state       tinyint      null comment '周期购商品状态',
    store_id          bigint       not null,
    create_time       datetime     null comment '创建时间',
    create_person     varchar(32)  null,
    update_time       datetime     null,
    update_person     varchar(32)  null,
    del_flag          tinyint(1)   null comment '删除标识：0：未删除；1：已删除'
)
    comment '周期购spu表';

create index idx_goods_id
	on `sbc-goods`.buy_cycle_goods (goods_id);

create index idx_store_id
	on `sbc-goods`.buy_cycle_goods (store_id);


DROP TABLE IF EXISTS `sbc-goods`.buy_cycle_goods_info;
create table `sbc-goods`.buy_cycle_goods_info
(
    id            bigint(11) auto_increment comment '主键'
        primary key,
    goods_info_id varchar(32)    not null comment 'skuId',
    goods_id      varchar(32)    null comment 'spuId',
    buy_cycle_id  bigint(11)     null comment '周期购id',
    min_cycle_num int            null comment '最低期数',
    cycle_price   decimal(20, 2) null comment '每期价格',
    cycle_state   tinyint        null comment '商品周期购状态 0：生效；1：失效',
    create_time   datetime       null comment '创建时间',
    create_person varchar(32)    null,
    update_time   datetime       null,
    update_person varchar(32)    null,
    del_flag      tinyint(1)     null comment '删除标识：0：未删除；1：已删除'
)
    comment '周期购sku表';

create index idx_goods_id
	on `sbc-goods`.buy_cycle_goods_info (goods_id);

create index idx_goods_info_id
	on `sbc-goods`.buy_cycle_goods_info (goods_info_id);

create index idx_buy_cycle_id
	on `sbc-goods`.buy_cycle_goods_info (buy_cycle_id);


alter table `sbc-goods`.buy_cycle_goods
    add goods_name varchar(255) null comment '商品名称' after goods_id;


-- 统计分区——营销活动数据概况天维度
CREATE TABLE `s2b_statistics`.`marketing_situation_day_copy` (
                                                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                 `marketing_type` tinyint(4) DEFAULT NULL COMMENT '0：优惠券 1：拼团 2：秒杀 3：满系 4：打包一口价 5：第二件半价 6：组合购 7：预约 8：全款预售 9：订金预售 10：预售 11：砍价',
                                                                 `start_date` date NOT NULL COMMENT '统计日期',
                                                                 `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID -1：平台',
                                                                 `activity_num` int(11) DEFAULT NULL COMMENT '活动数量',
                                                                 `pay_money` decimal(10,2) DEFAULT NULL COMMENT '营销支付金额',
                                                                 `discount_money` decimal(10,2) DEFAULT NULL COMMENT '营销优惠金额',
                                                                 `pay_customer_count` bigint(20) DEFAULT NULL COMMENT '营销支付人数',
                                                                 `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '营销支付件数',
                                                                 `pay_trade_count` bigint(20) DEFAULT NULL COMMENT '营销支付订单数',
                                                                 `new_customer` int(11) DEFAULT NULL COMMENT '新成交客户',
                                                                 `old_customer` int(11) DEFAULT NULL COMMENT '老成交客户',
                                                                 `pay_roi` decimal(10,2) DEFAULT NULL COMMENT '支付ROI：统计时间内，营销支付金额 / 营销优惠金额',
                                                                 `join_rate` decimal(10,2) DEFAULT NULL COMMENT '连带率：统计时间内，营销支付件数 / 营销支付订单数',
                                                                 `customer_price` decimal(10,2) DEFAULT NULL COMMENT '客单价：统计时间内，营销支付金额/营销支付人数',
                                                                 `pv` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'PV',
                                                                 `uv` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT 'UV',
                                                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                                 PRIMARY KEY (`id`,`start_date`),
                                                                 KEY `idx_start_date` (`start_date`)
) ENGINE=InnoDB AUTO_INCREMENT=2665902 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='营销活动数据概况天维度'
/*!50100 PARTITION BY RANGE (TO_DAYS(start_date))
(PARTITION p202111 VALUES LESS THAN (738490) ENGINE = InnoDB,
 PARTITION p202112 VALUES LESS THAN (738521) ENGINE = InnoDB,
 PARTITION p202201 VALUES LESS THAN (738552) ENGINE = InnoDB,
 PARTITION p202202 VALUES LESS THAN (738580) ENGINE = InnoDB,
 PARTITION p202203 VALUES LESS THAN (738611) ENGINE = InnoDB,
 PARTITION p202204 VALUES LESS THAN (738641) ENGINE = InnoDB,
 PARTITION p202205 VALUES LESS THAN (738672) ENGINE = InnoDB,
 PARTITION p202206 VALUES LESS THAN (738702) ENGINE = InnoDB,
 PARTITION p202207 VALUES LESS THAN (738733) ENGINE = InnoDB,
 PARTITION p202208 VALUES LESS THAN (738764) ENGINE = InnoDB,
 PARTITION p202209 VALUES LESS THAN (738794) ENGINE = InnoDB,
 PARTITION p202210 VALUES LESS THAN (738825) ENGINE = InnoDB,
 PARTITION p202211 VALUES LESS THAN (738855) ENGINE = InnoDB) */;

INSERT into `s2b_statistics`.marketing_situation_day_copy SELECT * from `s2b_statistics`.marketing_situation_day;
DROP TABLE `s2b_statistics`.marketing_situation_day;
RENAME TABLE `s2b_statistics`.marketing_situation_day_copy to `s2b_statistics`.marketing_situation_day;

DELIMITER $$
USE `s2b_statistics`$$
CREATE EVENT IF NOT EXISTS `e_partition_marketing_situation_day`
    ON SCHEDULE EVERY 1 MINUTE
    STARTS now()
    ON COMPLETION PRESERVE
ENABLE
    COMMENT 'Creating marketing_situation_day partitions'
DO BEGIN

    -- 调用存储过程，第一个参数是数据库名称，第二个参数是表名称
    CALL s2b_statistics.create_partition_by_year_month('s2b_statistics','marketing_situation_day');
END$$
DELIMITER ;

-- 管理后台底部版本号信息维护
ALTER TABLE `sbc-setting`.base_config ADD COLUMN `version` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本号';
update `sbc-setting`.base_config set version = 'SBC V5.0.2';


