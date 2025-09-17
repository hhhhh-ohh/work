-- 授信统计sql语句（可解决数据错乱问题）
-- 授信账户数
select count(1) from customer_credit_account where del_flag = '0' and credit_amount is not null;
-- 授信已使用额度（当前周期）
select sum(practical_price) from pay_trade_record where channel_item_id in (24,25,26) and status = '1' AND trade_type = 'PAY';
-- 已还款
select sum(practical_price) from pay_trade_record where status = '1' and business_id like ('CR%');
-- 授信待还款
SELECT sum(repay_amount) FROM customer_credit_account WHERE del_flag='0' AND credit_amount IS NOT NULL;
-- 授信可用额度
SELECT sum(usable_amount) FROM customer_credit_account WHERE del_flag='0' AND start_time< NOW() AND end_time> NOW() AND credit_amount IS NOT NULL;

-- 授信清空语句：

-- MySQL
truncate table customer_apply_record;
truncate table customer_credit_account;
truncate table customer_credit_record;
truncate table customer_credit_order;
truncate table customer_credit_repay;
truncate table customer_credit_overview;

insert into `sbc-account`.`customer_credit_overview` values('OVERVIEW',0,0,0,0,0,0);

-- MongoDB
db.getCollection('trade').updateMany({'needCreditRepayFlag':true},{$set:{'needCreditRepayFlag':false}})
db.getCollection('trade').updateMany({'creditPayInfo.hasRepaid':false},{$set:{'creditPayInfo.hasRepaid':true}})