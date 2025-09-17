
-- 增加业务员查询会员id索引
ALTER TABLE `sbc-customer`.`customer_detail`
ADD INDEX `idx_employee_id`(`employee_id`) USING BTREE;

ALTER TABLE `sbc-order`.`pay_order`
ADD INDEX `idx_customer_detail_id`(`customer_detail_id`) USING BTREE;

ALTER TABLE `sbc-order`.`refund_order`
ADD INDEX `idx_customer_detail_id`(`customer_detail_id`) USING BTREE;

ALTER TABLE `sbc-account`.`customer_apply_record`
ADD INDEX `idx_customer_id`(`customer_id`) USING BTREE;

ALTER TABLE `sbc-account`.`customer_credit_account`
ADD INDEX `idx_customer_id`(`customer_id`) USING BTREE;

ALTER TABLE `sbc-account`.`customer_credit_repay`
ADD INDEX `idx_customer_id`(`customer_id`);

-- 商家supplier端，应用-分销记录，显示3条一样编号的记录（所有字段都相同）
DROP PROCEDURE IF EXISTS `sbc-customer`.`create_partition_by_year_month`;

CREATE DEFINER=`root`@`%` PROCEDURE `sbc-customer`.`create_partition_by_year_month`(IN_SCHEMANAME VARCHAR(64), IN_TABLENAME VARCHAR(64))
BEGIN
    DECLARE ROWS_CNT INT UNSIGNED;
    DECLARE BEGINTIME DATE;
    DECLARE ENDTIME varchar(50);
    DECLARE PARTITIONNAME VARCHAR(16);
    SET BEGINTIME = DATE(NOW());
    SET PARTITIONNAME = DATE_FORMAT( BEGINTIME, 'p%Y%m' );
    SET ENDTIME = DATE_FORMAT(DATE(BEGINTIME + INTERVAL 1 MONTH),'DP%Y%m');

    SELECT COUNT(*) INTO ROWS_CNT FROM information_schema.partitions
  WHERE table_schema = IN_SCHEMANAME AND table_name = IN_TABLENAME AND partition_name = PARTITIONNAME;
    IF ROWS_CNT = 0 THEN
        SET @SQL = CONCAT( 'ALTER TABLE `', IN_SCHEMANAME, '`.`', IN_TABLENAME, '`',
      ' ADD PARTITION (PARTITION ', PARTITIONNAME, ' VALUES LESS THAN (\'', ENDTIME, '\') ENGINE = InnoDB);' );
        PREPARE STMT FROM @SQL;
        EXECUTE STMT;
        DEALLOCATE PREPARE STMT;
     ELSE
  SELECT CONCAT("partition `", PARTITIONNAME, "` for table `",IN_SCHEMANAME, ".", IN_TABLENAME, "` already exists") AS result;
     END IF;
END;
