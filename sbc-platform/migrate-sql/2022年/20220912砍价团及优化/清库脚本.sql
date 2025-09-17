
-- 清除足迹表
TRUNCATE TABLE `sbc-customer`.`goods_footmark_0`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_1`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_2`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_3`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_4`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_5`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_6`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_7`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_8`;
TRUNCATE TABLE `sbc-customer`.`goods_footmark_9`;

-- 清除砍价商品表、砍价表、帮砍记录表
TRUNCATE TABLE `sbc-marketing`.`bargain_goods`;
TRUNCATE TABLE `sbc-marketing`.`bargain`;
TRUNCATE TABLE `sbc-marketing`.`bargain_join`;

-- 清除砍价商品replay表、统计表
TRUNCATE TABLE `s2b_statistics`.`replay_bargain_goods`;
TRUNCATE TABLE `s2b_statistics`.`bargain_sale`;