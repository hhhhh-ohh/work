ALTER TABLE `sbc-marketing`.`gift_card_detail`
ADD COLUMN `gift_card_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '礼品卡类型：0.现金卡 1.提货卡' AFTER `batch_no`;

alter table `sbc-marketing`.gift_card
    add gift_card_type TINYINT null comment '礼品卡类型：0.现金卡 1.提货卡' after name;

alter table `sbc-marketing`.gift_card
    add scope_goods_num int null comment '适用商品数量(提货卡字段) -1可选一种 -99可全选 其他代表N种' after scope_type;

alter table `sbc-marketing`.user_gift_card
    add gift_card_type TINYINT null comment '礼品卡类型：0.现金卡 1.提货卡';

alter table `sbc-marketing`.gift_card
    modify par_value bigint(11) null comment '礼品卡面值';


alter table `sbc-marketing`.gift_card
    modify scope_type tinyint null comment '关联商品 0：全部 1:按品牌 2：按分类 3：按店铺 4：自定义商品';

alter table `sbc-marketing`.user_gift_card
    modify par_value bigint(11) null comment '礼品卡面值';



alter table `sbc-marketing`.user_gift_card
    modify balance decimal(10, 2) null comment '礼品卡余额';

alter table `sbc-marketing`.gift_card_bill
    modify trade_balance decimal(10, 2) null comment '交易金额';

alter table `sbc-marketing`.gift_card_bill
    modify before_balance decimal(10, 2) null comment '交易前余额';

alter table `sbc-marketing`.gift_card_bill
    modify after_balance decimal(10, 2) null comment '交易后余额';

alter table `sbc-order`.pay_order
    add gift_card_type tinyint null comment '礼品卡类型：0.现金卡 1.提货卡';

alter table `sbc-account`.settlement
    add pickup_gift_card_price decimal(20, 2) null comment '礼品卡-提货卡抵扣金额';



update `sbc-marketing`.user_gift_card a set a.gift_card_type = 0 where a.gift_card_type is null ;
update `sbc-marketing`.gift_card a set a.gift_card_type = 0 where a.gift_card_type is null ;
update `sbc-order`.pay_order a set a.gift_card_type = 0 where a.gift_card_type is null and a.gift_card_price > 0;

alter table `sbc-account`.reconciliation
    add gift_card_type tinyint null comment '礼品卡类型 0：现金卡 1：提货卡';

update `sbc-setting`.authority a set a.authority_url = '/store/provider/name/' where a.authority_url = '/store/provider/name';

update `sbc-setting`.base_config set version = 'SBC V5.6.0';
