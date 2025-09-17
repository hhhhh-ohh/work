-- 会员提现
ALTER TABLE `sbc-account`.`customer_draw_cash` ADD COLUMN `batch_id` VARCHAR (255) NULL COMMENT '转账微信批次单号' AFTER  `account_balance`;

ALTER TABLE `sbc-account`.`customer_draw_cash` ADD COLUMN `detail_id` VARCHAR (255) NULL COMMENT '转账微信明细单号' AFTER  `batch_id`;

ALTER TABLE `sbc-empower`.`pay_gateway_config` ADD COLUMN `api_v3_key` VARCHAR (255) NULL COMMENT 'apiV3秘钥' AFTER  `store_id`;

ALTER TABLE `sbc-empower`.`pay_gateway_config` ADD COLUMN `merchant_serial_number` VARCHAR (255) NULL COMMENT '证书编号' AFTER  `api_v3_key`;



-- 抽奖活动表
CREATE TABLE `sbc-marketing`.`draw_activity` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `activity_name` varchar(50) NOT NULL COMMENT '活动名称',
                                 `start_time` datetime NOT NULL COMMENT '开始时间',
                                 `end_time` datetime NOT NULL COMMENT '结束时间',
                                 `form_type` tinyint(4) NOT NULL COMMENT '抽奖形式（0：九宫格，1：大转盘）',
                                 `draw_type` tinyint(4) NOT NULL COMMENT '抽奖类型（0：无限制，1：积分）',
                                 `consume_points` bigint(20) DEFAULT NULL COMMENT '消耗积分 当drawType为1时有值',
                                 `draw_times_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '抽奖次数限制类型（0：每日，1：每人）',
                                 `draw_times` int(5) NOT NULL DEFAULT '0' COMMENT '抽奖次数，默认为0',
                                 `win_times_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '中奖次数限制类型 （0：无限制，1：每人每天）',
                                 `win_times` int(5) DEFAULT '0' COMMENT '每人每天最多中奖次数，默认为0',
                                 `join_level` varchar(255) DEFAULT NULL COMMENT '会员等级',
                                 `not_award_tip` varchar(255) DEFAULT NULL COMMENT '未中奖提示',
                                 `max_award_tip` varchar(255) DEFAULT NULL COMMENT '抽奖次数上限提示',
                                 `activity_content` text NOT NULL COMMENT '活动规则说明',
                                 `draw_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '实际抽奖人/次',
                                 `award_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '实际中奖人/次',
                                 `pause_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否暂停 0进行 1暂停',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                 `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                 `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                 `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='抽奖活动表';

-- 抽奖记录表
CREATE TABLE `sbc-marketing`.`draw_record` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '抽奖记录主键',
                               `activity_id` bigint(20) NOT NULL COMMENT '抽奖活动id',
                               `draw_record_code` varchar(50) DEFAULT NULL COMMENT '抽奖记录编号',
                               `draw_time` datetime NOT NULL COMMENT '抽奖时间',
                               `draw_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '抽奖状态 0 未中奖 1 中奖',
                               `prize_id` bigint(20) DEFAULT NULL COMMENT '奖项id',
                               `prize_type` tinyint(4) DEFAULT NULL COMMENT '奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）',
                               `prize_name` varchar(32) DEFAULT NULL COMMENT '奖品名称',
                               `prize_url` varchar(128) DEFAULT NULL COMMENT '奖品图片',
                               `redeem_prize_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '兑奖状态 0未兑奖  1已兑奖',
                               `deliver_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0未发货  1已发货',
                               `customer_id` varchar(32) NOT NULL COMMENT '会员Id',
                               `customer_account` varchar(32) DEFAULT NULL COMMENT '会员登录账号|手机号',
                               `customer_name` varchar(32) DEFAULT NULL COMMENT '会员名称',
                               `detail_address` varchar(255) DEFAULT NULL COMMENT '详细收货地址(包含省市区）',
                               `consignee_name` varchar(32) DEFAULT NULL COMMENT '收货人',
                               `consignee_number` varchar(32) DEFAULT NULL COMMENT '收货人手机号码',
                               `redeem_prize_time` datetime DEFAULT NULL COMMENT '兑奖时间',
                               `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
                               `logistics_company` varchar(32)  DEFAULT NULL COMMENT '物流公司名称',
                               `logistics_no` varchar(32) DEFAULT NULL COMMENT '物流单号',
                               `logistics_code` varchar(50) DEFAULT NULL COMMENT '物流公司标准编码',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                               `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                               `update_person` varchar(32) DEFAULT NULL COMMENT '编辑人',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `activity_id` (`activity_id`) USING BTREE COMMENT '活动编号',
                               KEY `customer_id` (`customer_id`) USING BTREE COMMENT '用户编号',
                               KEY `draw_record_code` (`draw_record_code`) USING BTREE COMMENT '抽奖记录编号'
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='抽奖记录';

-- 抽奖活动关联奖品表
CREATE TABLE `sbc-marketing`.`draw_prize` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                              `activity_id` bigint(20) NOT NULL COMMENT '抽奖活动id',
                              `prize_name` varchar(32) NOT NULL COMMENT '奖品名称',
                              `prize_type` tinyint(4) DEFAULT NULL COMMENT '奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）',
                              `prize_url` varchar(128) NOT NULL COMMENT '奖品图片',
                              `prize_num` int(9) NOT NULL DEFAULT '0' COMMENT '商品总量（1-99999999）',
                              `win_percent` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '中奖概率0.01-100之间的数字',
                              `points_num` bigint(20) DEFAULT NULL COMMENT '积分数值,当prizeType为0时有值',
                              `coupon_code_id` varchar(32) DEFAULT NULL COMMENT '优惠券奖品Id,当prizeType为1时有值',
                              `customize` text COMMENT '自定义奖品,当prizeType为3时有值',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                              `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                              `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                              `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识',
                              PRIMARY KEY (`id`),
                              KEY `activity_id` (`activity_id`) USING BTREE COMMENT '抽奖活动id'
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='抽奖活动奖品表';

ALTER TABLE `sbc-marketing`.`coupon_activity` ADD COLUMN `draw_activity_id` BIGINT (20) NULL COMMENT '关联抽奖活动Id，activity_type为8时有值' AFTER  `business_source`;

-- 订阅消息
CREATE TABLE `sbc-message`.`mini_program_subscribe_authorization_record` (
                                                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                               `customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
                                                               `order_pay_success_num` tinyint(4) DEFAULT NULL COMMENT '订单支付成功次数',
                                                               `refund_order_success_num` tinyint(4) DEFAULT NULL COMMENT '退单提交成功次数',
                                                               `view_coupon_num` tinyint(4) DEFAULT NULL COMMENT '查看我的优惠券次数',
                                                               `groupon_success_num` tinyint(4) DEFAULT NULL COMMENT '参与/发起拼团成功次数',
                                                               `appointment_success_num` tinyint(4) DEFAULT NULL COMMENT '商品预约成功次数',
                                                               `member_pay_success_num` tinyint(4) DEFAULT NULL COMMENT '付费会员购买成功次数',
                                                               PRIMARY KEY (`id`),
                                                               KEY `idx_customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='客户订阅消息授权记录表';

CREATE TABLE `sbc-message`.`mini_program_subscribe_message_activity_setting` (
                                                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                   `activity_name` varchar(50) DEFAULT NULL COMMENT '活动名称',
                                                                   `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                                                                   `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                                                                   `context` varchar(256) DEFAULT NULL COMMENT '活动内容',
                                                                   `tips` varchar(256) DEFAULT NULL COMMENT '温馨提示',
                                                                   `to_page` text COMMENT '要跳转的页面',
                                                                   `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '推送类型 0 立即发送  1 定时发送',
                                                                   `send_time` datetime DEFAULT NULL COMMENT '定时发送时间',
                                                                   `pre_count` int(10) DEFAULT NULL COMMENT '预计推送人数',
                                                                   `real_count` int(10) DEFAULT NULL COMMENT '实际推送人数',
                                                                   `send_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '推送状态 0：未推送，1：推送中，2：已推送，3：推送失败，4：部分失败',
                                                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                                   `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                                                   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                                   `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                                                   `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='新增活动消息配置表';

CREATE TABLE `sbc-message`.`mini_program_subscribe_message_setting` (
                                                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                          `node_id` tinyint(4) DEFAULT NULL COMMENT '授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 付费会员购买成功',
                                                          `node_name` varchar(50) DEFAULT NULL COMMENT '授权节点名称',
                                                          `num` tinyint(4) DEFAULT NULL COMMENT '授权频次',
                                                          `message` varchar(256) DEFAULT NULL COMMENT '推送结果描述',
                                                          `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否开启 0：否，1：是',
                                                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                          `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                                          `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                          `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='小程序订阅消息配置表';

CREATE TABLE `sbc-message`.`mini_program_subscribe_template_setting` (
                                                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                           `template_id` varchar(50) DEFAULT NULL COMMENT '模版ID',
                                                           `template_name` varchar(40) DEFAULT NULL COMMENT '模版名称',
                                                           `category_id` varchar(50) DEFAULT NULL COMMENT '类目ID',
                                                           `category_name` varchar(40) DEFAULT NULL COMMENT '类目名称',
                                                           `keyword` varchar(256) DEFAULT NULL COMMENT '关键词',
                                                           `trigger_node_id` tinyint(4) NOT NULL DEFAULT '0' COMMENT '触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时',
                                                           `trigger_node_name` varchar(256)  DEFAULT NULL COMMENT '触发节点名称',
                                                           `tips` varchar(256) DEFAULT NULL COMMENT '温馨提示',
                                                           `new_tips` varchar(256) DEFAULT NULL COMMENT '温馨提示-提供修改',
                                                           `to_page` varchar(256) DEFAULT NULL COMMENT '要跳转的页面',
                                                           `tid` varchar(50)  DEFAULT NULL COMMENT '模版标题ID',
                                                           `kid_list` varchar(50) DEFAULT NULL COMMENT '开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如 [3,5,4] 或 [4,5,3]），最多支持5个，最少2个关键词组合',
                                                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                                           `create_person` varchar(32)  DEFAULT NULL COMMENT '创建人',
                                                           `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                                           `update_person` varchar(32)  DEFAULT NULL COMMENT '修改人',
                                                           `to_page_name` varchar(256) DEFAULT NULL COMMENT '要跳转的页面名称',
                                                           PRIMARY KEY (`id`) USING BTREE,
                                                           KEY `idx_trigger_node_id` (`trigger_node_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='小程序订阅消息模版配置表';


CREATE TABLE `sbc-empower`.`mini_program_subscribe_message_customer_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `message_activity_id` bigint(20) DEFAULT NULL COMMENT '推送活动id',
  `template_setting_id` bigint(20) DEFAULT NULL COMMENT '模版触发节点ID',
  `open_id` varchar(36) NOT NULL COMMENT '第三方用户id',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员Id',
  `err_code` varchar(50) DEFAULT NULL COMMENT '推送结果状态码',
  `err_msg` varchar(256) DEFAULT NULL COMMENT '推送结果描述',
  `send_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否推送标志 0：否，1：是',
  `trigger_node_id` tinyint(4) DEFAULT NULL COMMENT '触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时',
  PRIMARY KEY (`id`),
  KEY `idx_message_activity_id` (`message_activity_id`) USING BTREE,
  KEY `idx_template_setting_id` (`template_setting_id`) USING BTREE,
  KEY `idx_customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='客户订阅消息信息表';

INSERT INTO `sbc-message`.`mini_program_subscribe_message_setting`(  `node_id`, `node_name`, `num`, `message`, `status`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( 0, '订单提交成功', NULL, '[null,null,null]', 0, '2022-08-08 15:00:18', 'system', NULL, NULL);
INSERT INTO `sbc-message`.`mini_program_subscribe_message_setting`(  `node_id`, `node_name`, `num`, `message`, `status`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( 1, '退单提交成功', NULL, '[null,null,null]', 0, '2022-08-08 15:00:18', 'system', NULL, NULL);
INSERT INTO `sbc-message`.`mini_program_subscribe_message_setting`(  `node_id`, `node_name`, `num`, `message`, `status`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( 2, '查看我的优惠券', NULL, '[null,null,null]', 0, '2022-08-08 15:00:18', 'system', NULL, NULL);
INSERT INTO `sbc-message`.`mini_program_subscribe_message_setting`(  `node_id`, `node_name`, `num`, `message`, `status`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( 3, '参与/发起拼团活动', NULL, '[null,null,null]', 0, '2022-08-08 15:00:18', 'system', NULL, NULL);
INSERT INTO `sbc-message`.`mini_program_subscribe_message_setting`(  `node_id`, `node_name`, `num`, `message`, `status`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( 4, '商品预约成功', NULL, '[null,null,null]', 0, '2022-08-08 15:00:18', 'system', NULL, NULL);
INSERT INTO `sbc-message`.`mini_program_subscribe_message_setting`(  `node_id`, `node_name`, `num`, `message`, `status`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES ( 5, '付费会员购买', NULL, '[null,null,null]', 0, '2022-08-08 15:00:18', 'system', NULL, NULL);

INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '订单发货提醒', '782', '电商平台', '订单编号；发货时间；快递单号；快递公司', 0, '商家发货', NULL, NULL, '/pages/package-C/order/order-detail/index?id=', '374', '[2,4,13,14]', NULL, NULL, NULL, NULL, '相应订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '自动确认收货提醒', '782', '电商平台', '订单编号；发货时间；确认收货时间；温馨提示', 1, '自动确认收货前24小时', '你的订单即将自动确认收货，点击查看', NULL, '/pages/package-C/order/order-detail/index?id=', '4811', '[2,4,5,1]', NULL, NULL, NULL, NULL, '相应订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '售后申请审核通知', '782', '电商平台', '订单号；商品名称；售后类型；审核结果', 2, '售后申请商家审核通过或失败后', NULL, NULL, '/pages/package-C/order/return-detail/index?id=', '2927', '[1,4,6,2]', NULL, NULL, NULL, NULL, '退单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '退款成功通知', '782', '电商平台', '退款单号；退款时间；退款金额；退还积分；退款方式', 4, '退款回调通知成功', NULL, NULL, '/pages/package-C/order/return-detail/index?id=', '3566', '[8,2,4,9,6]', NULL, NULL, NULL, NULL, '相应订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '优惠券发放通知', '782', '电商平台', '优惠券；金额；使用商品；有效期；数量', 5, '自动发券至用户账户时', NULL, NULL, '/pages/package-A/customer/my-coupon/index', '8952', '[1,2,4,5,7]', NULL, NULL, NULL, NULL, '我的-我的优惠券');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '优惠券使用提醒', '782', '电商平台', '优惠券名称；有效期；优惠力度；温馨提示', 6, '优惠券过期前24小时', '您有一张优惠券即将过期，请尽快使用哦', NULL, '/pages/package-A/customer/my-coupon/index', '5525', '[1,8,9,3]', NULL, NULL, NULL, NULL, '我的-我的优惠券');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '拼团待成团提醒', '782', '电商平台', '商品名称；拼团价格；剩余人数；剩余时间；温馨提示', 7, '距离拼团结束还剩3小时，且未成团', '您的拼团即将结束，未成团时订单将自动取消', NULL, '/pages/package-B/goods/group-details/index?skuId=', '2488', '[1,4,2,8,7]', NULL, NULL, NULL, NULL, '拼团详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '拼团成功通知', '782', '电商平台', '商品名称；拼团价；成团时间；温馨提示', 8, '拼团成功', '您的拼团已成功，请耐心等待商家发货', NULL, '/pages/package-C/order/order-detail/index?id=', '766', '[1,2,7,5]', NULL, NULL, NULL, NULL, '订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '拼团失败通知', '782', '电商平台', '商品名称；订单状态；拼团价；退款金额', 9, '拼团失败', NULL, NULL, '/pages/package-C/order/order-detail/index?id=', '769', '[1,15,6,2]', NULL, NULL, NULL, NULL, '订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '尾款支付提醒', '782', '电商平台', '商品名称；付款金额；尾款支付结束；温馨提示', 10, '尾款开始支付', '商品库存有限，点击立即去支付', NULL, '/pages/package-C/order/order-detail/index?id=', '771', '[1,2,7,4]', NULL, NULL, NULL, NULL, '订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '尾款支付超时提醒', '782', '电商平台', '商品名称；尾款金额；尾款截止时间；温馨提示', 11, '距离尾款结束支付还有3小时', '尾款超时未支付，订单将自动取消且定金不退', NULL, '/pages/package-C/order/order-detail/index?id=', '17669', '[5,6,7,4]', NULL, NULL, NULL, NULL, '订单详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '会员到期提醒', '782', '电商平台', '服务名称；到期时间；温馨提示', 12, '付费会员距离过期前24小时', '您的会员即将到期，续费后继续享受优惠', NULL, '/pages/package-A/customer/paying-member-center/index', '4994', '[1,2,3]', NULL, NULL, NULL, NULL, '付费会员详情');
INSERT INTO `sbc-message`.`mini_program_subscribe_template_setting`(  `template_id`, `template_name`, `category_id`, `category_name`, `keyword`, `trigger_node_id`, `trigger_node_name`, `tips`, `new_tips`, `to_page`, `tid`, `kid_list`, `create_time`, `create_person`, `update_time`, `update_person`, `to_page_name`) VALUES ( NULL, '新活动通知', '782', '电商平台', '活动名称；活动时间；活动截止时间；活动内容；温馨提示', 13, NULL, NULL, NULL, NULL, '9797', '[2,6,9,8,7]', NULL, NULL, NULL, NULL, '用户配置');

ALTER TABLE `sbc-marketing`.`coupon_code_0`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';

ALTER TABLE `sbc-marketing`.`coupon_code_1`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';

ALTER TABLE `sbc-marketing`.`coupon_code_2`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';

ALTER TABLE `sbc-marketing`.`coupon_code_3`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';

ALTER TABLE `sbc-marketing`.`coupon_code_4`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';

-- 定时推送小程序订阅消息
INSERT INTO `xxl-job`.`xxl_job_info`(`job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`) VALUES (1, '定时推送小程序订阅消息', '2022-09-07 10:17:08', '2022-09-07 10:17:18', '徐锋', '', 'CRON', '0 0/5 * * * ?', 'DO_NOTHING', 'FIRST', 'MiniProgramSubscibeMsgJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-09-07 10:17:08', '', 1, 0, 1662517200000);

ALTER TABLE `sbc-message`.`mini_program_subscribe_message_activity_setting`
ADD COLUMN `scan_flag` tinyint(2) DEFAULT 0 COMMENT '是否已经扫描到 false 否  true 是';

-- 优惠券码
ALTER TABLE `sbc-marketing`.`coupon_code`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';

ALTER TABLE `s2b_statistics`.`replay_coupon_code`
ADD COLUMN `coupon_expired_send_flag` tinyint(2) DEFAULT 0 COMMENT '优惠券过期前24小时,是否发送订阅消息 false 否  true 是';