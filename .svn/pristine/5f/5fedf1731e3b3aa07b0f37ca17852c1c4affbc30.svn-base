-- 商品限售增加店铺id索引
ALTER TABLE `sbc-goods`.`goods_restricted_sale`
    ADD INDEX `INDEX_STORE_ID`(`store_id`) USING BTREE COMMENT '增加store_id的索引';


-- 修改表字段字符集和排序规则
-- bff下保存存储过程
set @@sql_mode ='NO_ENGINE_SUBSTITUTION';
DELIMITER $$
USE `sbc-bff`$$
DROP PROCEDURE IF EXISTS `chanageCharSet`$$
CREATE DEFINER=`root`@`%` PROCEDURE `chanageCharSet`(datebaseName VARCHAR(100))
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE scheamName VARCHAR(100);
    DECLARE tableName VARCHAR(100);
    DECLARE columnName VARCHAR(100);
    DECLARE columnType VARCHAR(100);
    DECLARE alertSql VARCHAR(200);
    DECLARE _Cur CURSOR FOR (
            SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, COLUMN_TYPE
            FROM `information_schema`.`COLUMNS`
            WHERE DATA_TYPE='varchar' AND TABLE_SCHEMA = datebaseName
    );
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN _Cur;
    REPEAT
FETCH _Cur INTO scheamName, tableName, columnName, columnType;
            IF NOT done THEN
                SET alertSql = CONCAT(
                    'ALTER TABLE `',
                    scheamName,
                    '`.`',
                    tableName,
                    '` MODIFY COLUMN `',
                    columnName,
                    '` ',
                    columnType,
                    ' CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;'
                );
                SET @ESQL = alertSql;
PREPARE stmt1 FROM @ESQL;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;
END IF;
    UNTIL done END REPEAT;
    CLOSE _Cur;
END$$
DELIMITER ;

call chanageCharSet('sbc-account');
call chanageCharSet('sbc-customer');
call chanageCharSet('sbc-goods');
call chanageCharSet('sbc-marketing');
call chanageCharSet('sbc-message');
call chanageCharSet('sbc-order');
call chanageCharSet('sbc-pay');
call chanageCharSet('sbc-setting');
call chanageCharSet('sbc-vas');
call chanageCharSet('sbc-empower');
call chanageCharSet('s2b_statistics');
call chanageCharSet('sbc-crm');
call chanageCharSet('sbc-dw');