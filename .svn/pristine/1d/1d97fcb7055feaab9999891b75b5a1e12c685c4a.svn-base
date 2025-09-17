CREATE TABLE `sbc-marketing`.`bargain_goods` (
    `bargain_goods_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `goods_info_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
    `goods_info_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冗余商品编码',
    `goods_info_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冗余商品名称',
    `goods_cate_id` int(10) DEFAULT NULL COMMENT '冗余平台类目id',
    `market_price` decimal(12,2) unsigned NOT NULL COMMENT '市场价',
    `company_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商家编号',
    `store_id` bigint(20) NOT NULL COMMENT '店铺编号-哪个商家的砍价活动',
    `bargain_price` decimal(12,2) unsigned NOT NULL COMMENT '帮砍金额',
    `target_join_num` tinyint(3) unsigned NOT NULL COMMENT '帮砍人数',
    `bargain_stock` int(10) unsigned NOT NULL COMMENT '砍价库存',
    `leave_stock` int(10) unsigned NOT NULL COMMENT '剩余库存',
    `reason_for_rejection` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '驳回原因',
    `audit_status` tinyint(3) unsigned NOT NULL COMMENT '审核状态，0：待审核，1：已审核，2：审核失败',
    `begin_time` datetime NOT NULL COMMENT '活动开始时间',
    `end_time` datetime NOT NULL COMMENT '活动结束时间',
    `create_time` datetime NOT NULL,
    `update_time` datetime DEFAULT NULL,
    `stoped` tinyint(4) NOT NULL COMMENT '是否手动停止，0，否，1，是',
    `freight_free_flag` tinyint(4) NOT NULL COMMENT '是否包邮，0:不包邮 1:包邮',
    `goods_status` tinyint(4) NOT NULL COMMENT '商品状态，0:不可售 1:可售',
    `del_flag` tinyint(3) NOT NULL,
    PRIMARY KEY (`bargain_goods_id`) USING BTREE,
    KEY `idx_goods_info_id` (`goods_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='砍价商品';

CREATE TABLE `sbc-marketing`.`bargain` (
    `bargain_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `bargain_no` bigint(20) unsigned NOT NULL COMMENT '砍价编号',
    `bargain_goods_id` bigint(20) unsigned NOT NULL COMMENT '砍价商品id',
    `goods_info_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
    `market_price` decimal(20,2) unsigned NOT NULL COMMENT '砍价商品原价',
    `begin_time` datetime NOT NULL COMMENT '发起时间',
    `end_time` datetime NOT NULL COMMENT '结束时间',
    `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发起人id',
    `customer_account` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冗余发起人账号，方便查询',
    `join_num` tinyint(3) unsigned NOT NULL COMMENT '已砍人数',
    `target_join_num` tinyint(3) unsigned NOT NULL COMMENT '目标砍价人数',
    `bargained_amount` decimal(12,2) unsigned NOT NULL COMMENT '已砍金额',
    `target_bargain_price` decimal(12,2) unsigned NOT NULL COMMENT '目标砍价金额',
    `order_id` char(22) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单号',
    `store_id` bigint(20) NOT NULL,
    `create_time` datetime NOT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`bargain_id`),
    UNIQUE KEY `bargain_no` (`bargain_no`) USING BTREE,
    KEY `idx_customerId` (`bargain_goods_id`,`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='砍价';

CREATE TABLE `sbc-marketing`.`bargain_join` (
    `bargain_join_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `bargain_id` bigint(20) unsigned NOT NULL COMMENT '砍价记录id',
    `goods_info_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '砍价商品id',
    `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '砍价的发起人',
    `join_customer_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帮砍人id',
    `bargin_goods_random_words` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '砍价随机语',
    `bargain_amount` decimal(12,2) unsigned NOT NULL COMMENT '帮砍金额',
    `create_time` datetime NOT NULL,
    PRIMARY KEY (`bargain_join_id`),
    KEY `idx_bargain_id` (`bargain_id`,`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帮砍记录';

-- 分表 0 - 9，先创建goods_footmark_0，剩下的1-9由后面的存储过程生成
CREATE TABLE `sbc-customer`.`goods_footmark_0` (
    `footmark_id` bigint(20) NOT NULL,
    `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
    `goods_info_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SKUID',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `del_flag` tinyint(4) NOT NULL COMMENT '删除标识,0:未删除1:已删除',
    `view_times` bigint(11) DEFAULT '0' COMMENT '浏览次数',
    PRIMARY KEY (`footmark_id`),
    UNIQUE KEY `idx_customer_sku_id` (`customer_id`,`goods_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='我的足迹表';


CREATE TABLE `s2b_statistics`.`replay_bargain_goods` (
    `bargain_goods_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `goods_info_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
    `goods_info_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冗余商品编码',
    `goods_info_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冗余商品名称',
    `goods_cate_id` int(10) DEFAULT NULL COMMENT '冗余平台类目id',
    `market_price` decimal(12,2) unsigned NOT NULL COMMENT '市场价',
    `company_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商家编号',
    `store_id` bigint(20) NOT NULL COMMENT '店铺编号-哪个商家的砍价活动',
    `bargain_price` decimal(12,2) unsigned NOT NULL COMMENT '帮砍金额',
    `target_join_num` tinyint(3) unsigned NOT NULL COMMENT '帮砍人数',
    `bargain_stock` int(10) unsigned NOT NULL COMMENT '砍价库存',
    `leave_stock` int(10) unsigned NOT NULL COMMENT '剩余库存',
    `reason_for_rejection` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '驳回原因',
    `audit_status` tinyint(3) unsigned NOT NULL COMMENT '审核状态，0：待审核，1：已审核，2：审核失败',
    `begin_time` datetime NOT NULL COMMENT '活动开始时间',
    `end_time` datetime NOT NULL COMMENT '活动结束时间',
    `create_time` datetime NOT NULL,
    `update_time` datetime DEFAULT NULL,
    `stoped` tinyint(4) NOT NULL COMMENT '是否手动停止，0，否，1，是',
    `freight_free_flag` tinyint(4) NOT NULL COMMENT '是否包邮，0:不包邮 1:包邮',
    `goods_status` tinyint(4) NOT NULL COMMENT '商品状态，0:不可售 1:可售',
    `del_flag` tinyint(3) NOT NULL,
    PRIMARY KEY (`bargain_goods_id`) USING BTREE,
    KEY `idx_goods_info_id` (`goods_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='砍价商品';

CREATE TABLE `s2b_statistics`.`bargain_sale` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `marketing_id` bigint(20) DEFAULT NULL COMMENT '活动ID',
    `store_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
    `customer_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `pay_money` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
    `discount_money` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
    `pay_goods_count` bigint(20) DEFAULT NULL COMMENT '支付件数',
    `goods_info_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `goods_info_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `goods_info_no` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `boss_customer_flag` tinyint(4) DEFAULT NULL COMMENT 'boss端：0：新客户 1:老客户',
    `boss_customer_flag_week` tinyint(4) DEFAULT NULL COMMENT 'boss端周： 0：新客户 1:老客户',
    `store_customer_flag` tinyint(4) DEFAULT NULL COMMENT '商家端： 客户 1:老客户',
    `store_customer_flag_week` tinyint(4) DEFAULT NULL COMMENT '商家端周： 0：新客户 1:老客户',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='砍价活动';

ALTER TABLE `s2b_statistics`.`replay_trade`
    ADD COLUMN `bargain` tinyint(4) NULL COMMENT '是否砍价活动 0:否 1:是';

ALTER TABLE `s2b_statistics`.`replay_trade_item`
    ADD COLUMN `bargain_goods_id` bigint(20) NULL COMMENT '砍价活动' AFTER `supply_price`;


-- 存储过程，动态创建足迹表分表1-9
use `sbc-customer`;
drop procedure if exists  pro_goods_footmark_sub_table;

delimiter $
create procedure pro_goods_footmark_sub_table(tableCount  int)
begin
  declare i int;
  DECLARE table_name VARCHAR(50);
  DECLARE table_pre VARCHAR(50);
  DECLARE sql_text VARCHAR(2048);
  set i = 1;
  SET table_pre = '`sbc-customer`.`goods_footmark_';
  while i <= tableCount  do
    SET @table_name = CONCAT(table_pre,CONCAT(i, '`'));
    SET sql_text = CONCAT('CREATE TABLE ', @table_name, ' like `sbc-customer`.`goods_footmark_0`');
    SET @sql_text=sql_text;
PREPARE stmt FROM @sql_text;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
set i = i + 1;
end while;
end $

call pro_goods_footmark_sub_table(9);
-- 存储过程用完删除
drop procedure if exists  pro_goods_footmark_sub_table;

-- 砍价相关系统配置
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_goods_audit', '砍价商品审核开关', NULL, 1, NULL, '2022-05-25 18:01:06', '2022-09-01 19:37:22', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_goods_random_words', '砍价随机语', NULL, 1, '[{\"word\":\"快去看更多好友来砍价哦\",\"editStatus\":false},{\"word\":\"再接再厉，很快就成功了\",\"editStatus\":false},{\"word\":\"优秀啊，找到小刀砍一刀\",\"editStatus\":false},{\"word\":\"分享给好友还能砍更多\",\"editStatus\":false},{\"word\":\"继续去邀请好友帮砍吧\",\"editStatus\":false},{\"word\":\"运气真好，砍掉一大刀\",\"editStatus\":false},{\"word\":\"比拼拉人，祝你一臂之力\",\"editStatus\":false},{\"word\":\"轻轻一挥刀，深藏功与名\",\"editStatus\":false}]', '2022-05-25 18:01:12', '2022-09-01 19:37:22', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_goods_sale_poster', '砍价频道海报', NULL, 1, '[{\"artworkUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/481f8ba7d0edddf993df6011fdcaf2dc.jpg\",\"resourceId\":\"427398451\",\"link\":{\"linkKey\":\"goodsList\",\"info\":{\"skuId\":\"ff8080817a575683017a577daeb6000b\",\"id\":\"ff8080817a575683017a577dae3d0006\",\"name\":\"%25u8DE8%25u5883%25u96C6%25u8D27002\"},\"target\":false,\"type\":\"link\"}},{\"artworkUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/4300db0861f3aa71e1e6d292f0ef2229.jpg\",\"resourceId\":\"428129253\"},{\"artworkUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/481f8ba7d0edddf993df6011fdcaf2dc.jpg\",\"resourceId\":\"428132227\"},{\"artworkUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/2ce23d9b8b8d3135fe2ca8f253105cd2.jpg\",\"resourceId\":\"428134084\"},{\"artworkUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/8ab7a388d124463ea3846604001c4636.jpg\",\"resourceId\":\"428145778\"},{\"artworkUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/481f8ba7d0edddf993df6011fdcaf2dc.jpg\",\"resourceId\":\"428148983\"}]', '2022-05-25 18:01:16', '2022-09-01 19:37:22', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_activity_time', '砍价活动时长', NULL, 1, '8', '2022-08-24 09:57:29', '2022-09-01 19:37:22', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_use_coupon', '砍价叠加优惠券使用', NULL, 1, NULL, NULL, '2022-09-01 19:37:22', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_maxNum_everyDay', '每人每天最多帮砍次数', NULL, 1, '5', NULL, '2022-09-01 19:37:22', 0);
INSERT INTO `sbc-setting`.`system_config` (`config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES ('bargain_goods_setting', 'bargain_goods_rule', '砍价规则', NULL, 1, '<p style=\"padding-left: 10px; padding-right: 10px;overflow: hidden; font-size:14px;\">2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案</p><p style=\"text-align:center; padding-left: 10px; padding-right: 10px; font-size:14px; overflow: hidden;\"><img src=\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/3e3911f054d39491c10604110a8c46d2.jpg\" title=\"\" alt=\"undefined/\"/></p><p style=\"text-align:center; padding-left: 10px; padding-right: 10px; font-size:14px; overflow: hidden;\"><img src=\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/7f6976d9106b0579dba025ddf9583fa8.jpg\" title=\"\" alt=\"undefined/\"/></p><p style=\"text-align:center; padding-left: 10px; padding-right: 10px; font-size:14px; overflow: hidden;\"><img src=\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/4300db0861f3aa71e1e6d292f0ef2229.jpg\" title=\"\" alt=\"undefined/\"/></p><p style=\"padding-left: 10px; padding-right: 10px; overflow: hidden; font-size: 14px;\">2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案2222这是测试文案</p>', NULL, '2022-09-01 19:37:22', 0);

-- 3个月前足迹定时任务清除
INSERT INTO `xxl-job`.`xxl_job_info` (`job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`) VALUES ( 1, '我的足迹', '2022-09-05 15:03:44', '2022-09-05 15:03:44', '张如坤', '', 'CRON', '0 0 0 L * ?', 'DO_NOTHING', 'FIRST', 'footMarkSyncJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-09-05 15:03:44', '', 0, 0, 0);

ALTER TABLE `sbc-marketing`.`electronic_coupon`
    ADD COLUMN `create_time` datetime DEFAULT NULL COMMENT '创建时间' AFTER `del_flag`;

-- 支付回调处理，回调接口修改为callback
UPDATE `sbc-empower`.`pay_gateway_config` pgc INNER JOIN (
	SELECT
		id,
		REPLACE ( boss_back_url, '/bossbff', '/callback' ) bossBackUrl
	FROM
		`sbc-empower`.`pay_gateway_config`
	WHERE
		boss_back_url LIKE '%/bossbff'
) res ON pgc.id = res.id
SET pgc.boss_back_url = res.bossBackUrl
where pgc.boss_back_url LIKE '%/bossbff';
