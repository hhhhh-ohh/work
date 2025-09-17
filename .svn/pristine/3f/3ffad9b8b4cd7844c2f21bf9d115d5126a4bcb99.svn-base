ALTER TABLE `sbc-marketing`.`draw_activity`
    MODIFY COLUMN `activity_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动规则说明' AFTER `max_award_tip`;


ALTER TABLE `sbc-marketing`.`draw_prize`
    MODIFY COLUMN `customize` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '自定义奖品,当prizeType为3时有值' AFTER `coupon_code_id`;