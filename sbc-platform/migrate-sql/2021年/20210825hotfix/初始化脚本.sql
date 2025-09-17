-- 优惠券概况统计日期索引
ALTER TABLE `s2b_statistics`.`coupon_overview_day`
ADD INDEX `idx_stat_date`(`stat_date`) USING BTREE;