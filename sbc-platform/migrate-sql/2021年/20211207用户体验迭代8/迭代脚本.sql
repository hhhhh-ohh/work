-- 商品库-新增商品库-重量体积调整，商品-新增商品-重量体积调整
-- 1.商品库sku数据及商品sku数据脚本增加商品重量及商品体积，脚本在压测环境18w数据执行10几秒
ALTER TABLE `sbc-goods`.`standard_sku` ADD COLUMN `goods_weight` DECIMAL ( 20, 3 ) DEFAULT NULL COMMENT '商品重量',
ADD COLUMN `goods_cubage` DECIMAL ( 20, 6 ) DEFAULT NULL COMMENT '商品体积';

ALTER TABLE `sbc-goods`.`goods_info` ADD COLUMN `goods_weight` DECIMAL ( 20, 3 ) DEFAULT NULL COMMENT '商品重量',
ADD COLUMN `goods_cubage` DECIMAL ( 20, 6 ) DEFAULT NULL COMMENT '商品体积';

-- 2.将spu重量及体积数据刷入sku，因为goods_id在两张表都是索引，所以相当于单表更新sku，脚本在压测环境18w数据执行10几秒
update `sbc-goods`.`standard_sku` sku
left join (select goods_id,goods_weight,goods_cubage from `sbc-goods`.`standard_goods`) g on sku.goods_id = g.goods_id
set sku.goods_weight = g.goods_weight, sku.goods_cubage = g.goods_cubage;

update `sbc-goods`.`goods_info` sku
left join (select goods_id,goods_weight,goods_cubage from `sbc-goods`.`goods`) g on sku.goods_id = g.goods_id
set sku.goods_weight = g.goods_weight, sku.goods_cubage = g.goods_cubage;

-- 3.代码sku数据库实体及es实体中新增重量体积字段，es数据需初始化。确保发完最新的包之后再执行es，boss端-设置-初始化es   商品和商品库初始化一下

-- 4.商品-商品限售-增加限售地区
-- 对指定会员的限售关系表goods_restricted_customer_rela新增地区字段
ALTER TABLE `sbc-goods`.`goods_restricted_customer_rela` ADD COLUMN `address_id`  varchar(64) NULL COMMENT '地域编码-多级中间用|分割';