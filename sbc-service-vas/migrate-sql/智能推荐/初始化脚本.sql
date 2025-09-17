CREATE TABLE `sbc-vas`.`cate_related_recommend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cate_id` bigint(32) DEFAULT NULL COMMENT '分类id',
  `related_cate_id` bigint(32) DEFAULT NULL COMMENT '关联分类id',
  `lift` decimal(11,2) DEFAULT NULL COMMENT '提升度',
  `weight` decimal(11,2) DEFAULT NULL COMMENT '权重',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型，0：关联分析，1：手动关联',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cate_id_related_uin_index` (`cate_id`,`related_cate_id`) USING BTREE COMMENT '分类id和关联分类id唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='分类相关性推荐表';

CREATE TABLE `sbc-vas`.`filter_rules_setting` (
  `id` smallint(5) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `day_num` smallint(5) DEFAULT NULL COMMENT '多少天内不重复',
  `num` int(11) DEFAULT NULL COMMENT '多少条内不重复',
  `type` tinyint(4) NOT NULL COMMENT ' 过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='过滤规则设置';

CREATE TABLE `sbc-vas`.`goods_correlation_model_setting` (
  `id` smallint(5) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `statistical_range` tinyint(4) NOT NULL DEFAULT '1' COMMENT '统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年',
  `trade_num` bigint(20) DEFAULT '0' COMMENT '预估订单数据量',
  `support` decimal(5,2) DEFAULT NULL COMMENT '支持度',
  `confidence` decimal(5,2) DEFAULT NULL COMMENT '置信度',
  `lift` decimal(5,2) DEFAULT NULL COMMENT '提升度',
  `check_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '选中状态 0：未选中，1：选中',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='商品关联分析模型调参';

CREATE TABLE `sbc-vas`.`goods_related_recommend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '商品id',
  `related_goods_id` varchar(32) DEFAULT NULL COMMENT '关联商品id',
  `lift` decimal(11,2) DEFAULT NULL COMMENT '提升度',
  `weight` decimal(11,2) DEFAULT NULL COMMENT '权重',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型，0：关联分析，1：手动关联',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `goods_id_related_uin_index` (`goods_id`,`related_goods_id`) USING BTREE COMMENT '商品id和关联商品id唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品相关性推荐表';

CREATE TABLE `sbc-vas`.`manual_recommend_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '商品id',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `goods_id_index` (`goods_id`) USING BTREE COMMENT '商品id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='手动推荐商品表';

CREATE TABLE `sbc-vas`.`recommend_cate_manage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cate_id` bigint(32) DEFAULT NULL COMMENT '类目id',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `no_push_type` tinyint(4) DEFAULT NULL COMMENT '\r\n禁推标识 0：可推送；1:禁推',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `cate_id_index` (`cate_id`) COMMENT '商品id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='推荐类目管理表';

CREATE TABLE `sbc-vas`.`recommend_goods_manage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '商品id',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `no_push_type` tinyint(4) DEFAULT NULL COMMENT '\r\n禁推标识 0：可推送；1:禁推',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `goods_id_index` (`goods_id`) COMMENT '商品id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='推荐商品管理表';


CREATE TABLE `sbc-vas`.`recommend_system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ' 编号',
  `config_key` varchar(255) NOT NULL COMMENT '键',
  `config_type` varchar(255) NOT NULL COMMENT '类型',
  `config_name` varchar(255) NOT NULL COMMENT '名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态,0:未启用1:已启用',
  `context` longtext COMMENT '配置内容，如JSON内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(255) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_config_key` (`config_key`),
  KEY `idx_config_type` (`config_type`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='智能推荐系统配置表';


CREATE TABLE `sbc-vas`.`recommend_position_configuration` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) DEFAULT NULL COMMENT '坑位名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类',
  `title` varchar(255) DEFAULT NULL COMMENT '坑位标题',
  `content` varchar(255) DEFAULT NULL COMMENT '推荐内容',
  `tactics_type` tinyint(4) DEFAULT NULL COMMENT '推荐策略类型，0：热门推荐；1：基于商品相关性推荐',
  `upper_limit` int(11) DEFAULT NULL COMMENT '推荐上限',
  `is_open` tinyint(255) DEFAULT NULL COMMENT '坑位开关，0：关闭；1：开启',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(100) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='智能推荐坑位设置表';

INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (1, '购物车', 0, '你可能还需要', '商品', 0, 2, 0, NULL, NULL, '2021-01-07 19:48:08', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (2, '商品详情', 1, '为你推荐', '商品', 0, 2, 0, NULL, NULL, '2021-01-07 16:06:22', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (3, '商品列表', 2, '猜你喜欢', '商品', 0, 2, 0, NULL, NULL, '2021-01-07 16:06:24', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (4, '个人中心', 3, '为你推荐', '商品', 0, NULL, 0, '2020-11-17 15:47:42', 'system', '2021-01-07 11:45:38', NULL);
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (5, '会员中心', 4, '为你推荐', '商品', 0, NULL, 0, '2020-11-17 15:47:42', 'system', '2021-01-05 19:29:03', NULL);
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (6, '收藏商品', 5, '为你推荐', '商品', 0, NULL, 0, '2020-11-17 15:47:42', 'system', NULL, NULL);
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (7, '支付成功页', 6, '你可能还需要', '商品', 0, NULL, 0, '2020-11-17 15:47:42', 'system', NULL, NULL);
INSERT INTO `sbc-vas`.`recommend_position_configuration`(`id`, `name`, `type`, `title`, `content`, `tactics_type`, `upper_limit`, `is_open`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (8, '分类', 7, '为你推荐', '分类', 1, NULL, 0, NULL, NULL, '2020-11-26 00:45:19', '2c8080815cd3a74a015cd3ae86850001');

INSERT INTO `sbc-vas`.`filter_rules_setting`(`id`, `day_num`, `num`, `type`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (13, 1, 1000, 0, 0, '2021-01-06 20:31:13', NULL, '2021-01-07 00:40:37', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`filter_rules_setting`(`id`, `day_num`, `num`, `type`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (14, 7, 500, 1, 0, '2021-01-06 20:31:13', NULL, '2021-01-07 00:40:37', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`filter_rules_setting`(`id`, `day_num`, `num`, `type`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (15, 30, 200, 2, 0, '2021-01-06 20:31:13', NULL, '2021-01-07 00:40:37', '2c8080815cd3a74a015cd3ae86850001');

INSERT INTO `sbc-vas`.`goods_correlation_model_setting`(`id`, `statistical_range`, `trade_num`, `support`, `confidence`, `lift`, `check_status`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (9, 0, 0, 0.00, 0.00, 0.00, 0, 0, '2020-11-28 13:45:40', NULL, '2021-01-05 00:40:14', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`goods_correlation_model_setting`(`id`, `statistical_range`, `trade_num`, `support`, `confidence`, `lift`, `check_status`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (10, 1, 0, 5.00, 10.00, 1.20, 1, 0, '2020-11-28 13:45:40', NULL, '2021-01-05 00:40:14', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`goods_correlation_model_setting`(`id`, `statistical_range`, `trade_num`, `support`, `confidence`, `lift`, `check_status`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (11, 2, 0, 0.00, 0.00, 0.00, 0, 0, '2020-11-28 13:45:40', NULL, '2021-01-05 00:40:14', '2c8080815cd3a74a015cd3ae86850001');
INSERT INTO `sbc-vas`.`goods_correlation_model_setting`(`id`, `statistical_range`, `trade_num`, `support`, `confidence`, `lift`, `check_status`, `del_flag`, `create_time`, `create_person`, `update_time`, `update_person`) VALUES (12, 3, 0, 0.00, 0.00, 0.00, 0, 0, '2020-11-28 13:45:40', NULL, '2021-01-05 00:40:14', '2c8080815cd3a74a015cd3ae86850001');

INSERT INTO `sbc-vas`.`recommend_system_config`(`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`) VALUES (70, 'related_analysis_config', 'related_analysis_config', '智能推荐关联分析设置', '智能推荐关联分析设置', 0, NULL, NULL, NULL, '2021-01-05 05:28:52', NULL, NULL);
INSERT INTO `sbc-vas`.`recommend_system_config`(`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`) VALUES (71, 'manual_recommend_goods_config', 'manual_recommend_goods_config', '智能推荐，手动推荐商品设置', '智能推荐，手动推荐商品设置', 0, NULL, NULL, NULL, '2021-01-06 01:46:45', NULL, NULL);


CREATE TABLE `sbc-vas`.`hot_goods_result` (
  `goods_id` varchar(50) NOT NULL COMMENT '商品id',
  `num` bigint(20) DEFAULT NULL COMMENT '商品的销量',
  `weight` bigint(20) DEFAULT NULL COMMENT '商品权重',
  `type` varchar(1) DEFAULT NULL COMMENT '来源类型,0为通过订单计算的数据，1为手动推荐的数据',
  `create_time` varchar(200) DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  KEY `index` (`goods_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热门商品推荐表(用于未登录并且手动推荐关闭时查询此表)';

CREATE TABLE `sbc-vas`.`hot_cate_result` (
  `cate_id` bigint(20) DEFAULT NULL COMMENT '类目id',
  `num` bigint(20) DEFAULT NULL COMMENT '销量数量',
  `weight` bigint(20) DEFAULT NULL COMMENT '权重',
  `create_time` varchar(20) NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='热门类目推荐表(用于未登录时查询此表)';

INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c83601775321ebad0009', 4, '2c939a4e7747c836017753215e2e0008', 3, '智能推荐', '/intelligent-recommendation', NULL, 0, '2021-01-30 19:51:08', 0);
INSERT INTO `sbc-setting`.`menu_info`(`menu_id`, `system_type_cd`, `parent_menu_id`, `menu_grade`, `menu_name`, `menu_url`, `menu_icon`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017753215e2e0008', 4, 'ff80808176ae86e40176b1c2a6320001', 2, '精准营销', '', NULL, 3, '2021-01-30 19:50:32', 0);


-- INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738c364f00036', 4, '2c939a4e7747c83601775321ebad0009', '智能推荐入口', 'f_recommend', NULL, 0, '2021-01-25 16:57:45', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773853d7db0006', 4, '2c939a4e7747c83601775321ebad0009', '推荐坑位管理', 'f_recommend_position', NULL, 5, '2021-01-25 14:55:55', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773851d5d10005', 4, '2c939a4e7747c83601775321ebad0009', '智能关联分析', 'f_goods_correlation_analysis_model_tuning', NULL, 4, '2021-01-25 14:53:43', 1);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773850c1ad0004', 4, '2c939a4e7747c83601775321ebad0009', '基于商品相关性推荐', 'f_recommend_settings_based_on_product_relevance', NULL, 3, '2021-01-25 14:52:33', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177384feea30003', 4, '2c939a4e7747c83601775321ebad0009', '热门推荐', 'f_hot_recommendation_settings', NULL, 2, '2021-01-25 14:51:39', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177384f40d20002', 4, '2c939a4e7747c83601775321ebad0009', '过滤规则设置', 'f_filter_rule_setting', NULL, 1, '2021-01-25 14:50:54', 0);
INSERT INTO `sbc-setting`.`function_info`(`function_id`, `system_type_cd`, `menu_id`, `function_title`, `function_name`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177384e773c0001', 4, '2c939a4e7747c83601775321ebad0009', '推荐商品管理', 'f_recommend_goods_manage', NULL, 0, '2021-01-25 14:50:02', 0);

INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738c40ee10037', 4, '2c9399de772ce098017738c364f00036', '推荐坑位初始化数据', NULL, '/recommend/position/configuration/list', 'POST', NULL, 0, '2021-01-25 16:58:29', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738891ce60035', 4, '2c9399de772ce09801773850c1ad0004', '添加关联类目 - 保存', NULL, '/caterelatedrecommend/addList', 'POST', NULL, 16, '2021-01-25 15:54:06', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177388806020034', 4, '2c9399de772ce09801773850c1ad0004', '添加关联商品 - 保存', NULL, '/goodsrelatedrecommend/addList', 'POST', NULL, 15, '2021-01-25 15:52:55', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177388761890033', 4, '2c9399de772ce09801773850c1ad0004', '已关联商品列表查询', NULL, '/goodsrelatedrecommend/getGoodsRelatedRecommendDataInfoList', 'POST', NULL, 14, '2021-01-25 15:52:12', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773884f23d0032', 4, '2c9399de772ce09801773850c1ad0004', '添加关联商品--可选商品列表', NULL, '/goodsrelatedrecommend/getGoodsRelatedRecommendChooseList', 'POST', NULL, 13, '2021-01-25 15:49:33', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738823e520031', 4, '2c9399de772ce09801773850c1ad0004', '获取类目', NULL, '/goods/goodsCatesTree', 'GET', NULL, 12, '2021-01-25 15:46:36', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177388183ab0030', 4, '2c9399de772ce09801773850c1ad0004', '按类目查看 - 列表查询 - 逐条查看', NULL, '/caterelatedrecommend/getCateRelateRecommendDetailList', 'POST', NULL, 11, '2021-01-25 15:45:48', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773881039e002f', 4, '2c9399de772ce09801773850c1ad0004', '按类目查看 - 修改权重', NULL, '/caterelatedrecommend/updateWeight', 'PUT', NULL, 10, '2021-01-25 15:45:15', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773880ac90002e', 4, '2c9399de772ce09801773850c1ad0004', '按类目查看 - 删除 - 列表数据', NULL, '/caterelatedrecommend/*', 'DELETE', NULL, 9, '2021-01-25 15:44:53', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738804a4b002d', 4, '2c9399de772ce09801773850c1ad0004', '按商品查看 - 修改权重', NULL, '/goodsrelatedrecommend/updateWeight', 'PUT', NULL, 8, '2021-01-25 15:44:28', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177387ff9ef002c', 4, '2c9399de772ce09801773850c1ad0004', '按商品查看 - 删除 - 列表数据', NULL, '/goodsrelatedrecommend/*', 'DELETE', NULL, 7, '2021-01-25 15:44:07', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177387f67a6002b', 4, '2c9399de772ce09801773850c1ad0004', '获取商品详情', NULL, '/goods/spu/*', 'GET', NULL, 6, '2021-01-25 15:43:30', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773878fd57002a', 4, '2c9399de772ce09801773850c1ad0004', '智能关联分析开闭状态-修改', NULL, '/recommendsystemconfig/modify', 'PUT', NULL, 5, '2021-01-25 15:36:29', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177387898dd0029', 4, '2c9399de772ce09801773850c1ad0004', '智能关联分析开闭状态-查询', NULL, '/recommendsystemconfig/getRecommendSystemConfig', 'POST', NULL, 4, '2021-01-25 15:36:04', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773878278c0028', 4, '2c9399de772ce09801773850c1ad0004', '按类目查看-合并查看', NULL, '/caterelatedrecommend/getCateRelateRecommendInfoList', 'POST', NULL, 3, '2021-01-25 15:35:35', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773877ae130027', 4, '2c9399de772ce09801773850c1ad0004', '查询品牌', NULL, '/goods/allGoodsBrands', 'GET', NULL, 2, '2021-01-25 15:35:03', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773877420f0026', 4, '2c9399de772ce09801773850c1ad0004', '按商品查看-逐条查看', NULL, '/goodsrelatedrecommend/getGoodsRelatedRecommendDetailInfoList', 'POST', NULL, 1, '2021-01-25 15:34:36', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773876d9640025', 4, '2c9399de772ce09801773850c1ad0004', '按商品查看-合并查看', NULL, '/goodsrelatedrecommend/getGoodsRelatedRecommendInfoLis', 'POST', NULL, 0, '2021-01-25 15:34:09', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177387524360024', 4, '2c9399de772ce0980177384feea30003', '查询类目', NULL, 'goods/goodsCatesTree', 'GET', NULL, 8, '2021-01-25 15:32:17', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773874b1b10023', 4, '2c9399de772ce0980177384feea30003', '查询品牌', NULL, '/goods/allGoodsBrands', 'GET', NULL, 7, '2021-01-25 15:31:48', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738745a410022', 4, '2c9399de772ce0980177384feea30003', '添加热门推荐商品', NULL, '/manualrecommendgoods/addList', 'POST', NULL, 6, '2021-01-25 15:31:25', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773873ee200021', 4, '2c9399de772ce0980177384feea30003', '批量删除指手动推荐商品', NULL, '/manualrecommendgoods/delete-by-id-list', 'DELETE', NULL, 5, '2021-01-25 15:30:58', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177387346870020', 4, '2c9399de772ce0980177384feea30003', '删除手动推荐商品', NULL, '/manualrecommendgoods/*', 'DELETE', NULL, 4, '2021-01-25 15:30:15', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773872cf17001f', 4, '2c9399de772ce0980177384feea30003', '手动推荐商品列表查询', NULL, '/manualrecommendgoods/getManualRecommendGoodsInfoList', 'POST', NULL, 3, '2021-01-25 15:29:44', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738721ea7001e', 4, '2c9399de772ce0980177384feea30003', '手动推荐商品列表修改权重', NULL, '/manualrecommendgoods/updateWeight', 'PUT', NULL, 2, '2021-01-25 15:28:59', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738719782001d', 4, '2c9399de772ce0980177384feea30003', '修改手动推荐开闭状态', NULL, '/recommendsystemconfig/modify', 'PUT', NULL, 1, '2021-01-25 15:28:24', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738712255001c', 4, '2c9399de772ce0980177384feea30003', '手动推荐开闭状态查询', NULL, '/recommendsystemconfig/getRecommendSystemConfig', 'POST', NULL, 0, '2021-01-25 15:27:54', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177386a5d3e001b', 4, '2c9399de772ce09801773853d7db0006', '修改推荐坑位设置', NULL, '/recommend/position/configuration/modify', 'PUT', NULL, 3, '2021-01-25 15:20:31', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773869cd87001a', 4, '2c9399de772ce09801773853d7db0006', '查询推荐坑位设置', NULL, '/recommend/position/configuration/*', 'GET', NULL, 2, '2021-01-25 15:19:54', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773868fd5d0019', 4, '2c9399de772ce09801773853d7db0006', '坑位开关设置', NULL, '/recommend/position/configuration/modifyIsOpen', 'PUT', NULL, 1, '2021-01-25 15:19:01', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773868a59e0018', 4, '2c9399de772ce09801773853d7db0006', '查询推荐坑位', NULL, '/recommend/position/configuration/list', 'POST', NULL, 0, '2021-01-25 15:18:38', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773865f86a0017', 4, '2c9399de772ce09801773850c1ad0004', '编辑商品关联分析模型调参数据', NULL, '/goodscorrelationmodelsetting/modify', 'PUT', NULL, 1, '2021-01-25 15:15:43', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177386545c00016', 4, '2c9399de772ce09801773850c1ad0004', '查询商品关联分析模型调参数据', NULL, '/goodscorrelationmodelsetting/list', 'POST', NULL, 0, '2021-01-25 15:14:57', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773863a9a70015', 4, '2c9399de772ce0980177384f40d20002', '保存过滤规则设置', NULL, '/filterrulessetting/modify', 'PUT', NULL, 1, '2021-01-25 15:13:12', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177386341850014', 4, '2c9399de772ce0980177384f40d20002', '查询过滤规则设置', NULL, '/filterrulessetting/list', 'POST', NULL, 0, '2021-01-25 15:12:45', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177386190be0013', 4, '2c9399de772ce0980177384e773c0001', '类目批量禁推', NULL, '/recommendcatemanage/addList', 'POST', NULL, 12, '2021-01-25 15:10:54', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177386123dd0012', 4, '2c9399de772ce0980177384e773c0001', '更新类目权重', NULL, '/recommendcatemanage/updateCateWeight', 'PUT', NULL, 11, '2021-01-25 15:10:26', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738607c110011', 4, '2c9399de772ce0980177384e773c0001', '添加类目权重或禁推', NULL, '/recommendcatemanage/add', 'POST', NULL, 10, '2021-01-25 15:09:43', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177385d99990010', 4, '2c9399de772ce0980177384e773c0001', '更新类目禁推', NULL, '/recommendcatemanage/updateCateNoPushType', 'PUT', NULL, 9, '2021-01-25 15:06:34', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177385d12af000f', 4, '2c9399de772ce0980177384e773c0001', '更新商品权重', NULL, '/recommendgoodsmanage/updateWeight', 'PUT', NULL, 8, '2021-01-25 15:06:00', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177385c25b5000e', 4, '2c9399de772ce0980177384e773c0001', '添加商品权重', NULL, '/recommendgoodsmanage/add', 'POST', NULL, 7, '2021-01-25 15:04:59', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177385aecfe000d', 4, '2c9399de772ce0980177384e773c0001', '更新商品禁推', NULL, '/recommendgoodsmanage/updateNoPush', 'PUT', NULL, 6, '2021-01-25 15:03:39', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177385a8c36000c', 4, '2c9399de772ce0980177384e773c0001', '批量添加商品禁推', NULL, '/recommendgoodsmanage/addList', 'POST', NULL, 5, '2021-01-25 15:03:14', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773859b531000b', 4, '2c9399de772ce0980177384e773c0001', '添加商品禁推', NULL, '/recommendgoodsmanage/add', 'POST', NULL, 4, '2021-01-25 15:02:19', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce098017738595695000a', 4, '2c9399de772ce0980177384e773c0001', '获取类目列表', NULL, '/recommendcatemanage/getRecommendCateInfoList', 'POST', NULL, 3, '2021-01-25 15:01:55', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773858d0780009', 4, '2c9399de772ce0980177384e773c0001', '查询全部分类', NULL, '/goods/goodsCatesTree', 'GET', NULL, 2, '2021-01-25 15:01:21', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce0980177385882c00008', 4, '2c9399de772ce0980177384e773c0001', '查询全部品牌', NULL, '/goods/allGoodsBrands', 'GET', NULL, 1, '2021-01-25 15:01:01', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773856b66d0007', 4, '2c9399de772ce0980177384e773c0001', '推荐商品列表', NULL, '/recommendgoodsmanage/getRecommendGoodsInfoList', 'POST', NULL, 0, '2021-01-25 14:59:03', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c83601774dcea14d0000', 4, '2c9399de772ce0980177384e773c0001', '查看推荐商品详情', NULL, '/goods/spu/*', 'GET', NULL, 12, '2021-01-29 19:02:03', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c9399de772ce09801773dcda15c0038', 4, '2c9399de772ce0980177384feea30003', '添加热门推荐商品--选择商品列表', NULL, '/manualrecommendgoods/getManualRecommendChooseGoodsList', 'POST', NULL, 9, '2021-01-26 16:27:02', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751e079a10007', 4, '2c9399de772ce0980177384e773c0001', '商品详情页商品规格', NULL, '/goods/goodsProp/*', 'GET', NULL, 25, '2021-01-30 14:00:02', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751dc67130006', 4, '2c9399de772ce0980177384e773c0001', '商品详情店铺界别', NULL, '/store/levels/*', 'GET', NULL, 24, '2021-01-30 13:55:35', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751dbc2cd0005', 4, '2c9399de772ce0980177384e773c0001', '商品详情店铺关联分类数据', NULL, '/contract/goods/cate/list/*', 'GET', NULL, 24, '2021-01-30 13:54:53', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751db1f340004', 4, '2c9399de772ce0980177384e773c0001', '商品详情店铺信息', NULL, '/store/store-info/*', 'GET', NULL, 23, '2021-01-30 13:54:11', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751da6b020003', 4, '2c9399de772ce0980177384e773c0001', '店铺类目', NULL, '/storeCate/*', 'GET', NULL, 22, '2021-01-30 13:53:25', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751d9dd660002', 4, '2c9399de772ce0980177384e773c0001', '商品详情店铺会员列表', NULL, '/store/allCustomers/*', 'POST', NULL, 21, '2021-01-30 13:52:48', 0);
INSERT INTO `sbc-setting`.`authority`(`authority_id`, `system_type_cd`, `function_id`, `authority_title`, `authority_name`, `authority_url`, `request_type`, `remark`, `sort`, `create_time`, `del_flag`) VALUES ('2c939a4e7747c836017751d90f3a0001', 4, '2c9399de772ce0980177384e773c0001', '商品详情品牌列表', NULL, '/contract/goods/brand/list/*', 'GET', NULL, 20, '2021-01-30 13:51:56', 0);
update menu_info set menu_name = '分析' where menu_id = 'fc8e07cd3fe311e9828800163e0fc468';
update menu_info set menu_name = '数谋' where menu_id = 'ff80808176ae86e40176b1c2a6320001';
update menu_info set menu_name = '会员画像' where menu_id = 'ff80808176ae86e40176b1c3876b0002';
update menu_info set menu_name = '智能分析' where menu_id = 'ff80808176ae86e40176b1c4035a0004';




INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES ( 14, '0 30 0 * * ?', 'spark应用任务', '2020-12-26 15:12:00', '2021-1-14 14:06:29', '何军红', 'hejunhong@wanmi.com', 'FIRST', 'sparkAppJob', '', 'SERIAL_EXECUTION', 60, 3, 'BEAN', '', 'GLUE代码初始化', '2020-12-26 15:12:00', '');

-- 新建sbc-dw数据库

create schema `sbc-dw` default character set utf8mb4 collate utf8mb4_general_ci;

CREATE TABLE `sbc-dw`.`spark_app_info`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `livyUri`        varchar(255) NOT NULL COMMENT 'livy地址',
    `file`           varchar(255) NOT NULL COMMENT '要提交的jar包地址',
    `proxyUser`      varchar(255) DEFAULT NULL COMMENT '运行任务代理名称',
    `className`      varchar(255) NOT NULL COMMENT '运行主类',
    `args`           varchar(255) DEFAULT NULL COMMENT '运行主类的参数，例如参数1；参数2',
    `thirdJarPath`   varchar(255) DEFAULT NULL COMMENT '第三方依赖jar包路径，必须为hdfs路径',
    `driverMemory`   varchar(20)  DEFAULT NULL COMMENT 'driver内存，例如1g',
    `driverCores`    int(11)      DEFAULT NULL COMMENT 'driver 核数',
    `executorMemory` varchar(200) DEFAULT NULL COMMENT 'executor端的内存 比如:4g',
    `executorCores`  int(11)      DEFAULT NULL COMMENT 'executor端的核数 比如:4',
    `numExecutors`   int(11)      DEFAULT NULL COMMENT '总的executor数，根据集群资源设置',
    `queue`          varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '队列名称',
    `name`           varchar(50)  DEFAULT NULL COMMENT '运行的spark应用名称',
    `conf` text COMMENT 'sparkconf，必须为json格式',
    `create_time`    datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_person`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `sbc-dw`.`spark_app_info`
VALUES (1, 'livy_url', 'hdfs_url/spark/dw/service-dw-spark-1.0.0-release.jar', 'hjh',
        'com.wanmi.sbc.dw.spark.app.BulkLoadTest',
        '[\"com.wanmi.sbc.dw.spark.app.RecommendAnalysisApp\",\"hdfs_url/spark/dw/spark-config.properties\"]',
        'hdfs_url/spark/yarn/', '2g', 2, '2g', 2, 4, 'spark', '智能推荐spark任务', '{"spark.sql.autoBroadcastJoinThreshold":"200","spark.serializer":"org.apache.spark.serializer.KryoSerializer","spark.default.parallelism":"20","spark.shuffle.consolidateFiles":"true","spark.sql.shuffle.partitions":"40","spark.shuffle.file.buffer":"64k","spark.shuffle.sort.bypassMergeThreshold":"250","spark.memory.offHeap.size":"300M","spark.executor.memoryOverhead":"1g"
}', '2020-12-28 14:45:32', 'xiao_he');

CREATE TABLE `sbc-dw`.`sqoop_job_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sqoop_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `job_id` varchar(100) DEFAULT NULL COMMENT 'jobid',
  `appliaction_id` varchar(100) DEFAULT NULL COMMENT 'yarn application id',
  `state` varchar(50) DEFAULT NULL COMMENT '执行状态',
  `start_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建记录时间',
  `date` date DEFAULT NULL COMMENT '统计日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='sqoop执行记录表';

