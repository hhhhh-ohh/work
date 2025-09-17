ALTER TABLE `sbc-customer`.`customer_detail`
    ADD COLUMN `is_has_child` tinyint(1) NULL DEFAULT 0 COMMENT '是否添加孩子  0：否 1：是' AFTER `agent_id`;

