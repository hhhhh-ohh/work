CREATE TABLE `sbc-setting`.`app_external_config` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                       `app_name` varchar(30) NOT NULL COMMENT '小程序名称',
                                       `app_id` varchar(20) NOT NULL COMMENT '小程序appId',
                                       `original_id` varchar(255) DEFAULT NULL COMMENT '原始Id',
                                       `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                       `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
                                       `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COMMENT='小程序配置表';

CREATE TABLE `sbc-setting`.`app_external_link` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `config_id` bigint(20) NOT NULL COMMENT '外部链接id',
                                     `page_name` varchar(40) NOT NULL COMMENT '页面名称',
                                     `page_link` text NOT NULL COMMENT '页面链接',
                                     `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                     `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
                                     `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='小程序页面链接表';

CREATE TABLE `sbc-goods`.`goods_template` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `name` varchar(40) NOT NULL COMMENT '名称',
                                  `position` tinyint(4) NOT NULL COMMENT '展示位置 0:顶部 1:底部 2:全选',
                                  `top_content` text COMMENT '顶部内容',
                                  `down_content` text COMMENT '底部内容',
                                  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
                                  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                  `update_person` varchar(32) DEFAULT NULL COMMENT '更新人',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='商品模版';

CREATE TABLE `sbc-goods`.`goods_template_rel` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `template_id` bigint(20) NOT NULL COMMENT '模版id',
                                      `goods_id` varchar(32) NOT NULL COMMENT '商品id',
                                      `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                      `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
                                      `update_person` varchar(36) DEFAULT NULL COMMENT '更新人',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='商品模版关联商品表';

ALTER TABLE `sbc-account`.`customer_credit_repay`
    ADD COLUMN `repay_way` tinyint(4) NULL DEFAULT 0 COMMENT '还款方式 0:线上 1:线下' AFTER `repay_notes`,
ADD COLUMN `repay_file` varchar(512) NULL DEFAULT NULL COMMENT '还款附件 repay_way为1时有值' AFTER `repay_type`,
ADD COLUMN `audit_remark` text NULL COMMENT '驳回理由' AFTER `repay_time`,
ADD COLUMN `audit_person` varchar(32) NULL DEFAULT NULL COMMENT '审核人' AFTER `audit_remark`,
ADD COLUMN `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间' AFTER `audit_person`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;

update `sbc-account`.`customer_credit_repay` set repay_way = 0;

INSERT INTO `sbc-setting`.`system_config` (`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES (NULL, 'mobile_setting', 'personal_config', '个人中心配置', NULL, 1, NULL, '2022-09-27 09:57:33', '2022-09-27 09:57:36', 0);

ALTER TABLE `sbc-setting`.`system_operation_log`
 ADD COLUMN `user_agent` varchar(512) NULL DEFAULT NULL COMMENT '操作UserAgent';

-- 授信脚本
ALTER TABLE `sbc-account`.`customer_credit_account`
    ADD COLUMN `expired_change_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否已经扣减可用额度 0未扣减 1已扣减' AFTER `del_flag`;

