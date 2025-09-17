-- 1、修改数据库默认字符集和排序规则
alter database `sbc-account` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-customer` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-goods` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-marketing` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-message` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-order` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-pay` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-setting` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-vas` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-empower` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `s2b_statistics` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-crm` character set utf8mb4 COLLATE utf8mb4_unicode_ci;
alter database `sbc-dw` character set utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2、修改表默认字符集和排序规则
-- bff下保存存储过程
set @@sql_mode ='NO_ENGINE_SUBSTITUTION';
DELIMITER $$
USE `sbc-bff`$$
DROP PROCEDURE IF EXISTS `tableChanageCharSet`$$
CREATE DEFINER=`root`@`%` PROCEDURE `tableChanageCharSet`(datebaseName VARCHAR(100))
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE executeSQL VARCHAR(1024);
    DECLARE _Cur CURSOR FOR (
            SELECT CONCAT('ALTER TABLE  `',TABLE_SCHEMA,'`.`',TABLE_NAME,'` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;') executeSQL
						FROM information_schema.SCHEMATA a,information_schema.TABLES b
						WHERE a.SCHEMA_NAME = b.TABLE_SCHEMA
						AND a.DEFAULT_COLLATION_NAME != b.TABLE_COLLATION
						AND b.TABLE_SCHEMA = datebaseName
    );
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN _Cur;
    REPEAT
        FETCH _Cur INTO executeSQL;
            IF NOT done THEN
                SET @ESQL = executeSQL;
                PREPARE stmt1 FROM @ESQL;
                EXECUTE stmt1;
                DEALLOCATE PREPARE stmt1;
            END IF;
    UNTIL done END REPEAT;
    CLOSE _Cur;
END$$
DELIMITER ;

call tableChanageCharSet('sbc-account');
call tableChanageCharSet('sbc-customer');
call tableChanageCharSet('sbc-goods');
call tableChanageCharSet('sbc-marketing');
call tableChanageCharSet('sbc-message');
call tableChanageCharSet('sbc-order');
call tableChanageCharSet('sbc-pay');
call tableChanageCharSet('sbc-setting');
call tableChanageCharSet('sbc-vas');
call tableChanageCharSet('sbc-empower');
call tableChanageCharSet('s2b_statistics');
call tableChanageCharSet('sbc-crm');
call tableChanageCharSet('sbc-dw');

-- 3、修改表字段字符集和排序规则
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