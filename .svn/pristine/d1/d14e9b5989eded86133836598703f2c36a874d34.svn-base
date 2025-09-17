-- seata Server端字段扩展
ALTER TABLE `seata`.`lock_table`
    MODIFY COLUMN `pk` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `table_name`;

ALTER TABLE `seata`.`lock_table`
    MODIFY COLUMN `row_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL FIRST;