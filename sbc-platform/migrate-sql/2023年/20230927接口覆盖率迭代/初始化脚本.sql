ALTER TABLE `sbc-marketing`.`distribution_record`
    MODIFY COLUMN `commission_rate` decimal(20, 4) NULL DEFAULT NULL COMMENT '分销佣金比例' AFTER `commission_goods`;