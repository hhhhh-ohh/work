create table `sbc-goods`.`goods_property`
(
  prop_id bigint auto_increment comment '属性id' primary key,
  prop_name varchar(45) null comment '属性名称',
  prop_pin_yin text null comment '拼音',
  prop_short_pin_yin varchar(45) null comment '简拼',
  prop_required tinyint(4) null comment '商品发布时属性是否必填 0否 1是',
  prop_character tinyint(4) null comment '是否行业特性 0否 1是，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等',
  prop_type tinyint(4) not null comment '属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区',
  index_flag tinyint(4) default '1' null comment '是否开启索引 0否 1是',
  prop_sort bigint null comment '排序',
  del_flag tinyint(4) default '0' not null comment '删除标识,0:未删除1:已删除',
  create_time datetime null comment '创建时间',
  create_person varchar(36) null comment '创建人',
  update_time datetime null comment '修改时间',
  update_person varchar(36) null comment '修改人',
  delete_time datetime null comment '删除时间',
  delete_person varchar(36) null comment '删除人'
)
comment '商品属性表'
;

create table `sbc-goods`.goods_property_detail
(
  detail_id bigint auto_increment comment '属性值id' primary key,
  prop_id bigint not null comment '属性id外键',
  detail_name varchar(45) null comment '属性值',
  detail_pin_yin text null default null comment '属性值（拼音）',
  detail_sort int null comment '排序',
  del_flag tinyint default '0' not null comment '删除标识,0:未删除1:已删除',
  create_time datetime null comment '创建时间',
  create_person varchar(36) null comment '创建人',
  update_time datetime null comment '修改时间',
  update_person varchar(36) null comment '修改人',
  delete_time datetime null comment '删除时间',
  delete_person varchar(36) null comment '删除人'
)
comment '商品属性值表'
;
create index idx_prop_id
  on `sbc-goods`.goods_property_detail (prop_id)
;


CREATE TABLE `sbc-goods`.`goods_prop_cate_rel` (
                                       `rel_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联表主键',
                                       `prop_id` bigint(20) NOT NULL COMMENT '属性id',
                                       `cate_id` bigint(20) NOT NULL COMMENT '商品分类id',
                                       `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
                                       `cate_grade` tinyint(4) DEFAULT NULL COMMENT '分类级别',
                                       `cate_path` varchar(50) DEFAULT NULL COMMENT '分类路径',
                                       `rel_sort` int(11) DEFAULT NULL COMMENT '排序',
                                       `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                       `create_person` varchar(36) DEFAULT NULL COMMENT '创建人',
                                       `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                       `update_person` varchar(36) DEFAULT NULL COMMENT '修改人',
                                       `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                                       `delete_person` varchar(36) DEFAULT NULL COMMENT '删除人',
                                       PRIMARY KEY (`rel_id`),
                                       KEY `idx_cate_id` (`cate_id`),
                                       KEY `idx_prop_id` (`prop_id`),
                                       KEY `idx_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11013 DEFAULT CHARSET=utf8mb4 COMMENT='商品类目与属性关联表';


create table `sbc-goods`.goods_property_detail_rel
(
  detail_rel_id bigint auto_increment primary key,
  goods_id varchar(32) null comment '商品id',
  cate_id bigint not null comment '商品分类id',
  prop_id bigint not null comment '属性id',
  detail_id text null comment '属性值id,以逗号隔开',
  goods_type tinyint(4) null comment '商品类型 0商品 1商品库',
  prop_type tinyint(4) not null comment '属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区',
  prop_value_text varchar(50) null comment '输入方式为文本的属性值',
  prop_value_date datetime null comment '输入方式为日期的属性值',
  prop_value_province text null comment '输入方式为省市的属性值，省市id用逗号隔开',
  prop_value_country text null comment '输入方式为国家或地区的属性值，国家和地区用逗号隔开',
  del_flag tinyint default '0' not null comment '是否删除标志 0：否，1：是',
  create_time datetime null comment '创建时间',
  create_person varchar(36) null comment '创建人',
  update_time datetime null comment '修改时间',
  update_person varchar(36) null comment '修改人',
  delete_time datetime null comment '删除时间',
  delete_person varchar(36) null comment '删除人'
)
comment '商品与属性值关联表'
;

create index idx_goods_id
  on `sbc-goods`.goods_property_detail_rel (goods_id)
;
create index idx_cate_id
  on `sbc-goods`.goods_property_detail_rel (cate_id)
;
create index idx_prop_id
  on `sbc-goods`.goods_property_detail_rel (prop_id);


-- 商品品牌新增两个字段
alter table `sbc-goods`.goods_brand add (
  recommend_flag tinyint(4) null default 0 comment '是否推荐品牌 0否 1是',
  brand_sort int null default 0 comment '排序'
);

-- 商品属性初始化数据
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (1, '尺码', 'chima', 'cm', 0, 0, 0, 1, 7, 0, '2021-04-22 13:55:30', 'system', null, null, null, null);
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (2, '颜色', 'yanse', 'ys', 0, 0, 0, 1, 6, 0, '2021-04-22 13:55:30', 'system', null, null, null, null);
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (3, '内存', 'neicun', 'nc', 0, 0, 0, 1, 5, 0, '2021-04-23 10:16:07', null, '2021-04-23 10:16:07', null, null, null);
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (4, '用途', 'yongtu', 'yt', 0, 0, 1, 0, 4, 0, '2021-04-23 10:16:07', null, '2021-04-23 10:16:07', null, null, null);
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (5, '有效期', 'youxiaoqi', 'yxq', 0, 0, 2, 1, 3, 0, '2021-04-23 02:19:53', null, null, null, null, null);
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (6, '发货地', 'fahuodi', 'fhd', 0, 0, 3, 1, 2, 0, '2021-04-23 02:19:53', null, null, null, null, null);
INSERT INTO `sbc-goods`.goods_property (prop_id, prop_name, prop_pin_yin, prop_short_pin_yin, prop_required, prop_character, prop_type, index_flag, prop_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (7, '产地', 'chandi', 'cd', 0, 0, 4, 1, 1, 0, '2021-04-23 02:19:53', null, null, null, null, null);


-- 商品属性值初始化数据
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (1, 1, 'S', 's', 1, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (2, 1, 'M', 'm', 2, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (3, 1, 'L', 'l', 3, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (4, 1, 'XL', 'xl', 4, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (5, 1, 'XXL', 'xxl', 5, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (6, 2, '白色', 'baise', 1, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (7, 2, '黑色', 'heise', 2, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (8, 2, '红色', 'hongse', 3, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (9, 2, '黄色', 'huangse', 4, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (10, 2, '蓝色', 'lanse', 5, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (11, 2, '绿色', 'lvse', 6, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (12, 2, '紫色', 'zise', 7, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (13, 3, '64G', '64g', 1, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (14, 3, '128G', '128g', 2, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);
INSERT INTO `sbc-goods`.goods_property_detail (detail_id, prop_id, detail_name, detail_pin_yin, detail_sort, del_flag, create_time, create_person, update_time, update_person, delete_time, delete_person) VALUES (15, 3, '256G', '256g', 3, 0, '2021-04-23 10:14:32', null, '2021-04-23 10:14:32', null, null, null);


-- 国家地区表
DROP TABLE IF EXISTS `sbc-setting`.`platform_country`;
CREATE TABLE `sbc-setting`.`platform_country` (
  `id` int(11) auto_increment primary key,
  `name` varchar(255) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
-- 国家地区初始化数据
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (1, '美国', 'US');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (2, '加拿大', 'CA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (3, '俄罗斯', 'RU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (4, '埃及', 'EG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (5, '南非', 'ZA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (6, '希腊', 'GR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (7, '荷兰', 'NL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (8, '比利时', 'BE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (9, '法国', 'FR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (10, '西班牙', 'ES');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (11, '匈牙利', 'HU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (12, '意大利', 'IT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (13, '罗马尼亚', 'RO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (14, '瑞士', 'CH');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (15, '奥地利', 'AT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (16, '英国', 'GB');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (17, '丹麦', 'DK');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (18, '瑞典', 'SE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (19, '挪威', 'NO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (20, '波兰', 'PL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (21, '德国', 'DE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (22, '秘鲁', 'PE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (23, '墨西哥', 'MX');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (24, '古巴', 'CU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (25, '阿根廷', 'AR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (26, '巴西', 'BR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (27, '智利', 'CL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (28, '哥伦比亚', 'CO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (29, '委内瑞拉', 'VE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (30, '马来西亚', 'MY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (31, '澳大利亚', 'AU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (32, '印度尼西亚', 'ID');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (33, '菲律宾', 'PH');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (34, '新西兰', 'NZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (35, '新加坡', 'SG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (36, '泰国', 'TH');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (37, '日本', 'JP');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (38, '韩国', 'KR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (39, '越南', 'VN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (40, '中国', 'CN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (41, '土耳其', 'TR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (42, '印度', 'IN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (43, '巴基斯坦', 'PK');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (44, '阿富汗', 'AF');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (45, '斯里兰卡', 'LK');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (46, '缅甸', 'MM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (47, '伊朗', 'IR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (48, '摩洛哥', 'MA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (49, '阿尔及利亚', 'DZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (50, '突尼斯', 'TN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (51, '利比亚', 'LY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (52, '冈比亚', 'GM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (53, '塞内加尔', 'SN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (54, '马里', 'ML');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (55, '几内亚', 'GN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (56, '科特迪瓦共和国', 'KT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (57, '布基纳法索', 'BF');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (58, '多哥', 'TG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (59, '贝宁', 'BJ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (60, '毛里求斯', 'MU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (61, '利比里亚', 'LR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (62, '塞拉利昂', 'SL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (63, '加纳', 'GH');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (64, '尼日利亚', 'NG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (65, '乍得', 'TD');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (66, '中非共和国', 'CF');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (67, '喀麦隆', 'CM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (68, '圣多美和普林西比', 'ST');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (69, '加蓬', 'GA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (70, '刚果', 'CG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (71, '扎伊尔', 'ZR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (72, '安哥拉', 'AO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (73, '阿森松', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (74, '塞舌尔', 'SC');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (75, '苏丹', 'SD');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (76, '埃塞俄比亚', 'ET');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (77, '索马里', 'SO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (78, '吉布提', 'DJ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (79, '肯尼亚', 'KE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (80, '坦桑尼亚', 'TZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (81, '乌干达', 'UG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (82, '布隆迪', 'BI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (83, '莫桑比克', 'MZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (84, '赞比亚', 'ZM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (85, '马达加斯加', 'MG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (86, '留尼旺', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (87, '津巴布韦', 'ZW');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (88, '纳米比亚', 'NA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (89, '马拉维', 'MW');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (90, '莱索托', 'LS');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (91, '博茨瓦纳', 'BW');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (92, '斯威士兰', 'SZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (93, '哈萨克斯坦', 'KZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (94, '吉尔吉斯坦', 'KG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (95, '直布罗陀', 'GI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (96, '葡萄牙', 'PT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (97, '卢森堡', 'LU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (98, '爱尔兰', 'IE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (99, '冰岛', 'IS');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (100, '阿尔巴尼亚', 'AL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (101, '马耳他', 'MT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (102, '塞浦路斯', 'CY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (103, '芬兰', 'FI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (104, '保加利亚', 'BG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (105, '立陶宛', 'LT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (106, '拉脱维亚', 'LV');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (107, '爱沙尼亚', 'EE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (108, '摩尔多瓦', 'MD');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (109, '阿美尼亚', 'AM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (110, '白俄罗斯', 'BY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (111, '安道尔共和国', 'AD');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (112, '摩纳哥', 'MC');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (113, '圣马力诺', 'SM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (114, '乌克兰', 'UA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (115, '南斯拉夫', 'YU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (116, '斯洛文尼亚', 'SI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (117, '捷克', 'CS');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (118, '斯洛伐克', 'SK');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (119, '列支敦士登', 'LI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (120, '伯利兹', 'BZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (121, '危地马拉', 'GT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (122, '萨尔瓦多', 'SV');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (123, '洪都拉斯', 'HN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (124, '尼加拉瓜', 'NI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (125, '哥斯达黎加', 'CR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (126, '巴拿马', 'PA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (127, '海地', 'HT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (128, '玻利维亚', 'BO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (129, '圭亚那', 'GY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (130, '厄瓜多尔', 'EC');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (131, '法属圭亚那', 'GF');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (132, '巴拉圭', 'PY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (133, '马提尼克', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (134, '苏里南', 'SR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (135, '乌拉圭', 'UY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (136, '荷属安的列斯', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (137, '文莱', 'BN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (138, '瑙鲁', 'NR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (139, '巴布亚新几内亚', 'PG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (140, '汤加', 'TO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (141, '所罗门群岛', 'SB');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (142, '斐济', 'FJ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (143, '库克群岛', 'CK');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (144, '东萨摩亚(美)', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (145, '西萨摩亚', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (146, '法属玻利尼西亚', 'PF');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (147, '朝鲜', 'KP');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (148, '香港(中国)', 'HK');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (149, '澳门（中国）', 'MO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (150, '柬埔寨', 'KH');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (151, '老挝', 'LA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (152, '孟加拉国', 'BD');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (153, '台湾（中国）', 'TW');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (154, '马尔代夫', 'MV');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (155, '黎巴嫩', 'LB');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (156, '约旦', 'JO');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (157, '叙利亚', 'SY');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (158, '伊拉克', 'IQ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (159, '科威特', 'KW');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (160, '沙特阿拉伯', 'SA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (161, '也门', 'YE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (162, '阿曼', 'OM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (163, '巴勒斯坦', 'BL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (164, '阿拉伯联合酋长国', 'AE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (165, '以色列', 'IL');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (166, '巴林', 'BH');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (167, '卡塔尔', 'QA');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (168, '蒙古', 'MN');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (169, '尼日尔', 'NE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (170, '尼泊尔', 'NP');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (171, '塔吉克斯坦', 'TJ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (172, '土库曼斯坦', 'TM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (173, '阿塞拜疆', 'AZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (174, '格鲁吉亚', 'GE');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (175, '巴哈马', 'BS');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (176, '巴巴多斯', 'BB');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (177, '安圭拉岛', 'AI');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (178, '安提瓜和巴布达', 'AG');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (179, '开曼群岛', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (180, '百慕大群岛', 'BM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (181, '蒙特塞拉特岛', 'MS');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (182, '马里亚那群岛', '');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (183, '关岛', 'GU');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (184, '圣卢西亚', 'LC');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (185, '圣文森特岛', 'VC');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (186, '波多黎各', 'PR');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (187, '特立尼达和多巴哥', 'TT');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (188, '乌兹别克斯坦', 'UZ');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (189, '格林纳达', 'GD');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (190, '牙买加', 'JM');
INSERT INTO `sbc-setting`.platform_country (id, name, short_name) VALUES (191, '多米尼加共和国', 'DO');


-- 初始化品牌排序和品牌推荐
update `sbc-goods`.`goods_brand` set recommend_flag = 0 where recommend_flag is null;
update `sbc-goods`.`goods_brand` set brand_sort = '0' where brand_sort is null;


-- 授信短信 站内信 push通知脚本
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 0 19 * * ? ', '授信待还款提醒', '2021-04-30 14:26:04', '2021-04-30 14:26:04', '郑旸', '', 'BUSYOVER', 'CreditNoRepayNoticeJobHandler', '', 'SERIAL_EXECUTION', 0, 1, 'BEAN', '', 'GLUE代码初始化', '2021-04-30 14:26:04', '');
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`) VALUES (1, '0 0 9 * * ?	', '授信逾期提醒', '2021-04-30 14:26:46', '2021-04-30 14:26:46', '郑旸', '', 'BUSYOVER', 'CreditOverdueNoticeJobHandler', '', 'SERIAL_EXECUTION', 0, 1, 'BEAN', '', 'GLUE代码初始化', '2021-04-30 14:26:46', '');
-- 短信模板使用必须先根据环境设置sign_id 字段（sms_sign表）
-- 然后到BOSS端-应用-短信触达-通知类-提交备案后方可使用
-- 授信审核成功通知
INSERT INTO `sbc-message`.`message_send_node`(`node_name`, `node_title`, `node_content`, `status`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`, `send_sum`, `open_sum`, `route_name`, `node_type`, `node_code`) VALUES ('授信审核成功通知', '授信审核成功通知', '恭喜您，您的授信申请已处理成功，点击查看>', 1, '2021-04-27 15:30:00', NULL, NULL, NULL, 0, 0, 0, 'CreditNotice', 1, 'CREDIT_AUDIT_PASS_NOTICE');
-- 授信申请驳回通知
INSERT INTO `sbc-message`.`message_send_node`(`node_name`, `node_title`, `node_content`, `status`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`, `send_sum`, `open_sum`, `route_name`, `node_type`, `node_code`) VALUES ('授信申请驳回通知', '授信申请驳回通知', '很抱歉，您的授信申请被驳回，原因是：{驳回原因}，点击查看>', 1, '2021-04-27 15:30:00', NULL, NULL, NULL, 0, 0, 0, 'CreditNotice', 1, 'CREDIT_AUDIT_REJECTED_NOTICE');
-- 授信变更成功通知
INSERT INTO `sbc-message`.`message_send_node`(`node_name`, `node_title`, `node_content`, `status`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`, `send_sum`, `open_sum`, `route_name`, `node_type`, `node_code`) VALUES ('授信变更成功通知', '授信变更成功通知', '恭喜您，您的授信变更申请已处理成功，点击查看>', 1, '2021-04-27 15:30:00', NULL, NULL, NULL, 0, 0, 0, 'CreditNotice', 1, 'CREDIT_CHANGE_PASS_NOTICE');
-- 授信变更驳回通知
INSERT INTO `sbc-message`.`message_send_node`(`node_name`, `node_title`, `node_content`, `status`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`, `send_sum`, `open_sum`, `route_name`, `node_type`, `node_code`) VALUES ('授信变更驳回通知', '授信变更驳回通知', '很抱歉，您的授信变更申请被驳回，原因是：{驳回原因}，点击查看>', 1, '2021-04-27 15:30:00', NULL, NULL, NULL, 0, 0, 0, 'CreditNotice', 1, 'CREDIT_CHANGE_REJECTED_NOTICE');
-- 授信还款提醒
INSERT INTO `sbc-message`.`message_send_node`(`node_name`, `node_title`, `node_content`, `status`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`, `send_sum`, `open_sum`, `route_name`, `node_type`, `node_code`) VALUES ('授信还款提醒', '授信还款提醒', '您的授信当前有待还金额{待还款金额}元，请在3天内还款，以免逾期影响使用~点击查看>', 1, '2021-04-27 15:30:00', NULL, NULL, NULL, 0, 0, 0, 'CreditNotice', 1, 'CREDIT_REPAY_NOTICE');
-- 授信逾期提醒
INSERT INTO `sbc-message`.`message_send_node`(`node_name`, `node_title`, `node_content`, `status`, `create_time`, `create_person`, `update_time`, `update_person`, `del_flag`, `send_sum`, `open_sum`, `route_name`, `node_type`, `node_code`) VALUES ('授信逾期提醒', '授信逾期提醒', '您的授信已逾期，为了不影响您的使用， 请尽快还款~点击查看>', 1, '2021-04-27 15:30:00', NULL, NULL, NULL, 0, 0, 0, 'CreditNotice', 1, 'CREDIT_OVERDUE_NOTICE');
-- 授信审核成功通知
INSERT INTO `sbc-message`.`push_send_node`(`node_name`, `node_title`, `node_type`, `node_code`, `node_context`, `expected_send_count`, `actually_send_count`, `open_count`, `status`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES ('授信审核成功通知','授信审核成功通知',  1, 'CREDIT_AUDIT_PASS_NOTICE', '恭喜您，您的授信申请已处理成功，点击查看>', 0, 0, 0, 1, 0, 'system', '2021-04-27 16:26:38', 'system', '2021-04-27 16:26:38');
-- 授信申请驳回通知
INSERT INTO `sbc-message`.`push_send_node`(`node_name`, `node_title`, `node_type`, `node_code`, `node_context`, `expected_send_count`, `actually_send_count`, `open_count`, `status`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES ('授信申请驳回通知','授信申请驳回通知',  1, 'CREDIT_AUDIT_REJECTED_NOTICE', '很抱歉，您的授信申请被驳回，原因是：{驳回原因}，点击查看>', 0, 0, 0, 1, 0, 'system', '2021-04-27 16:26:38', 'system', '2021-04-27 16:26:38');
-- 授信变更成功通知
INSERT INTO `sbc-message`.`push_send_node`(`node_name`, `node_title`, `node_type`, `node_code`, `node_context`, `expected_send_count`, `actually_send_count`, `open_count`, `status`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES ('授信变更成功通知','授信变更成功通知',  1, 'CREDIT_CHANGE_PASS_NOTICE', '恭喜您，您的授信变更申请已处理成功，点击查看>', 0, 0, 0, 1, 0, 'system', '2021-04-27 16:26:38', 'system', '2021-04-27 16:26:38');
-- 授信变更驳回通知
INSERT INTO `sbc-message`.`push_send_node`(`node_name`, `node_title`, `node_type`, `node_code`, `node_context`, `expected_send_count`, `actually_send_count`, `open_count`, `status`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES ('授信变更驳回通知','授信变更驳回通知',  1, 'CREDIT_CHANGE_REJECTED_NOTICE', '很抱歉，您的授信变更申请被驳回，原因是：{驳回原因}，点击查看>', 0, 0, 0, 1, 0, 'system', '2021-04-27 16:26:38', 'system', '2021-04-27 16:26:38');
-- 授信还款提醒
INSERT INTO `sbc-message`.`push_send_node`(`node_name`, `node_title`, `node_type`, `node_code`, `node_context`, `expected_send_count`, `actually_send_count`, `open_count`, `status`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES ('授信还款提醒','授信还款提醒',  1, 'CREDIT_REPAY_NOTICE', '您的授信当前有待还金额{待还款金额}元，请在3天内还款，以免逾期影响会用~点击查看>', 0, 0, 0, 1, 0, 'system', '2021-04-27 16:26:38', 'system', '2021-04-27 16:26:38');
-- 授信逾期提醒
INSERT INTO `sbc-message`.`push_send_node`(`node_name`, `node_title`, `node_type`, `node_code`, `node_context`, `expected_send_count`, `actually_send_count`, `open_count`, `status`, `del_flag`, `create_person`, `create_time`, `update_person`, `update_time`) VALUES ('授信逾期提醒','授信逾期提醒',  1, 'CREDIT_OVERDUE_NOTICE', '您的授信已逾期，为了不影响您的使用， 请尽快还款~点击查看>', 0, 0, 0, 1, 0, 'system', '2021-04-27 16:26:38', 'system', '2021-04-27 16:26:38');


-- empower 客服字段调整
ALTER TABLE `sbc-empower`.`customer_service_setting`
    MODIFY COLUMN `service_key` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL AFTER `effective_mobile`;

-- 迁移阿里云客服配置
UPDATE `sbc-empower`.`customer_service_setting` set service_key = (SELECT
                                                                       replace(trim(substr(sc.context
                                                                           , locate('key":', sc.context) + length('key":')
                                                                           , locate(',', replace(sc.context, '}', ','), locate('key":', sc.context) + length('key":'))
                                                                                               - (locate('key":', sc.context) + length('key":'))
                                                                           )), '"', '') as `service_key`
                                                                   FROM `sbc-setting`.`system_config` sc
                                                                   WHERE sc.`config_key` = 'online_service')
where store_id='0' and platform_type=1;

ALTER TABLE `sbc-empower`.`customer_service_setting`
    MODIFY COLUMN `service_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL AFTER `service_key`;

UPDATE `sbc-empower`.`customer_service_setting` set service_url = (SELECT
                                                                       replace(trim(substr(sc.context
                                                                           , locate('aliyunChat":', sc.context) + length('aliyunChat":')
                                                                           , locate(',', replace(sc.context, '}', ','), locate('aliyunChat":', sc.context) + length('aliyunChat":'))
                                                                                               - (locate('aliyunChat":', sc.context) + length('aliyunChat":'))
                                                                           )), '"', '') as `service_url`
                                                                   FROM `sbc-setting`.`system_config` sc
                                                                   WHERE sc.`config_key` = 'online_service')
where store_id='0' and platform_type=1;


update `sbc-setting`.`system_config` set context='yv66vgAAADQCFwoAfgEIBQAAAAAACSfACQB1AQkIAQoJAHUBCwoAdQEMCgB1AQ0KAHUBDgkAdQEP

CQB1ARAKAHcBEQoAdwESCwETARQJAHUBFQoBFgEXCgEYARkKARgBGgoAYwEbCgBjARwJAR0BHgcB

HwkAdQEgCAEhCwEiASMSAAABKAoAdwEpCgEqASsKASoBLAcBLQcBLgoAHwEICgAfAS8IATAKAB8B

GgoAHgExBwEyCgEzATQIATUKATYBNwcBOAgBOQgBOgoAKQE7BwE8CgAtAT0IAT4KAT8BQAcBQQgB

QggBQwcBRAgBRQgBRgcBRwkBSAFJCgA3AUoKAUsBTAgA0woBSwFNCAFOCgFLAU8KAB4BUAsBUQFS

CwFTAVQLAVUBVgsBUwFXCgFYAVkKAVoBWwgAyQoBWgFcCADKCgFYAV0IAV4IAV8KAB8BYAoBYQFi

CgAeAWMLAVEBZAkBZQFmCwFnAWgIAWkIAWoHAWsIAWwIAW0HAW4IAW8HAXAHAXEKAXIBcwkBdAF1

CwF2AXcKAXgBeQgBegoAYwF7CgEWAXwKAX0BfgcBfwoAdQGACgBjAYEKAX0BggoBdAGDCgB1AYQK

ACkBhQgBhggBhwoAdQGICAGJCAGKCgB1AYsFAAAAAAAAAAIKAYwBjQcBjgoAcwGPBwGQCgGRAZIH

AZMFAAAAAAAAAB4JAWUBlAcBlQoAewEICgB3AZYHAZcHAZgBAANsb2cBABJMb3JnL3NsZjRqL0xv

Z2dlcjsBAAhleGVjdXRvcgEAKUxqYXZhL3V0aWwvY29uY3VycmVudC9UaHJlYWRQb29sRXhlY3V0

b3I7AQAJcmV0cnlGbGFnAQABSQEADGxhc3RBdXRoVGltZQEAEkxqYXZhL2xhbmcvU3RyaW5nOwEA

CXNsZWVwVGltZQEAAUoBAApsaWNlbnNlVXJsAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVO

dW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBACJMY29tL3dhbm1pL3NiYy9j

b21tb24vdXRpbC9SZWRpc0w7AQAMZ2V0SWZQcmVzZW50AQAmKExqYXZhL2xhbmcvT2JqZWN0OylM

amF2YS9sYW5nL09iamVjdDsBAANrZXkBABJMamF2YS9sYW5nL09iamVjdDsBABBNZXRob2RQYXJh

bWV0ZXJzAQAdUnVudGltZVZpc2libGVUeXBlQW5ub3RhdGlvbnMBADVMb3JnL2NoZWNrZXJmcmFt

ZXdvcmsvY2hlY2tlci9udWxsbmVzcy9xdWFsL051bGxhYmxlOwEAA2dldAEARShMamF2YS9sYW5n

L09iamVjdDtMamF2YS91dGlsL2NvbmN1cnJlbnQvQ2FsbGFibGU7KUxqYXZhL2xhbmcvT2JqZWN0

OwEABmxvYWRlcgEAH0xqYXZhL3V0aWwvY29uY3VycmVudC9DYWxsYWJsZTsBAApFeGNlcHRpb25z

BwGZAQADcHV0AQAnKExqYXZhL2xhbmcvT2JqZWN0O0xqYXZhL2xhbmcvT2JqZWN0OylWAQAFdmFs

dWUBAAZwdXRBbGwBABIoTGphdmEvdXRpbC9NYXA7KVYBAAFtAQAPTGphdmEvdXRpbC9NYXA7AQAK

aW52YWxpZGF0ZQEAFShMamF2YS9sYW5nL09iamVjdDspVgEADWludmFsaWRhdGVBbGwBAARzaXpl

AQADKClKAQAFc3RhdHMBACYoKUxjb20vZ29vZ2xlL2NvbW1vbi9jYWNoZS9DYWNoZVN0YXRzOwEA

BWFzTWFwAQAmKClMamF2YS91dGlsL2NvbmN1cnJlbnQvQ29uY3VycmVudE1hcDsBAAdjbGVhblVw

AQAXKExqYXZhL2xhbmcvSXRlcmFibGU7KVYBAARrZXlzAQAUTGphdmEvbGFuZy9JdGVyYWJsZTsB

AA1nZXRBbGxQcmVzZW50AQA+KExqYXZhL2xhbmcvSXRlcmFibGU7KUxjb20vZ29vZ2xlL2NvbW1v

bi9jb2xsZWN0L0ltbXV0YWJsZU1hcDsBAAdleGVjdXRlAQANU3RhY2tNYXBUYWJsZQEACm5lZWRW

ZXJpZnkBAAMoKVoBAA5zZXRWZXJpZnlDYWNoZQEAAWUBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsH

AR8BAAZ2ZXJpZnkBAAtwb3N0TGljZW5zZQEALygpTGNvbS93YW5taS9zYmMvY29tbW9uL3V0aWwv

YXV0aC9WZXJpZnlSZXN1bHQ7AQAFdmFyMTYBAB9MamF2YS9uZXQvVW5rbm93bkhvc3RFeGNlcHRp

b247AQAFdmFyMTcBABpMamF2YS9uZXQvU29ja2V0RXhjZXB0aW9uOwEACXJlc0VudGl0eQEAHExv

cmcvYXBhY2hlL2h0dHAvSHR0cEVudGl0eTsBAANyZXMBAApqc29uT2JqZWN0AQAhTGNvbS9hbGli

YWJhL2Zhc3Rqc29uL0pTT05PYmplY3Q7AQAEY29kZQEAB2NvbnRleHQBAAZjbGllbnQBACNMb3Jn

L2FwYWNoZS9odHRwL2NsaWVudC9IdHRwQ2xpZW50OwEABHBvc3QBAClMb3JnL2FwYWNoZS9odHRw

L2NsaWVudC9tZXRob2RzL0h0dHBQb3N0OwEAC2lucHV0U3RyZWFtAQAVTGphdmEvaW8vSW5wdXRT

dHJlYW07AQAPaW5wdXRTdHJlYW1Cb2R5AQA1TG9yZy9hcGFjaGUvaHR0cC9lbnRpdHkvbWltZS9j

b250ZW50L0lucHV0U3RyZWFtQm9keTsBAAptYWNBZGRyZXNzAQAHbWFjQm9keQEAMExvcmcvYXBh

Y2hlL2h0dHAvZW50aXR5L21pbWUvY29udGVudC9TdHJpbmdCb2R5OwEAFm11bHRpcGFydEVudGl0

eUJ1aWxkZXIBADRMb3JnL2FwYWNoZS9odHRwL2VudGl0eS9taW1lL011bHRpcGFydEVudGl0eUJ1

aWxkZXI7AQAIcmVzcG9uc2UBAB5Mb3JnL2FwYWNoZS9odHRwL0h0dHBSZXNwb25zZTsBAApzdGF0

dXNDb2RlAQAFdmFyMTgBAAV2YXIxOQEAIExvcmcvYXBhY2hlL2h0dHAvUGFyc2VFeGNlcHRpb247

AQAFdmFyMjABABtMamF2YS9uZXQvQ29ubmVjdEV4Y2VwdGlvbjsBAAV2YXIyMQEAKUxvcmcvYXBh

Y2hlL2h0dHAvTm9IdHRwUmVzcG9uc2VFeGNlcHRpb247AQAFdmFyMjIBABVMamF2YS9pby9JT0V4

Y2VwdGlvbjsBAAV2YXIyMwEADHZlcmlmeVJlc3VsdAEALUxjb20vd2FubWkvc2JjL2NvbW1vbi91

dGlsL2F1dGgvVmVyaWZ5UmVzdWx0OwcBkAcBOAcBmgcBLQcBmwcBPAcBfwcBQQcBRAcBRwcBnAcB

nQcBawcBbgcBcAcBcQEAB2NhbGxDTUQBAA1ydW50aW1lTVhCZWFuAQAkTGphdmEvbGFuZy9tYW5h

Z2VtZW50L1J1bnRpbWVNWEJlYW47AQADcGlkAQAIZ2V0Qnl0ZXMBAAYoW0IpW0IBAAJbQgEAA2Nt

ZAEABWJ5dGVzAQAPbGFtYmRhJHZlcmlmeSQwAQAEdGltZQEABHZhcjQBACBMamF2YS9sYW5nL0lu

dGVycnVwdGVkRXhjZXB0aW9uOwcBjgEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEAC1JlZGlzTC5q

YXZhDACLAIwMAIgAiQEAGWh0dHA6Ly9saWNlbnNlLndhbm1pLmNvbS8MAIoAhwwAtQCMDAC3ALgM

AL0AjAwAhACFDACCAIMMAZ4BnwwBoAGhBwGiDACpAZ8MAIYAhwcBowwBpAGlBwGmDAGnAagMAakB

qgwBqwGsDAD7Aa0HAa4MAa8A/QEAE2phdmEvbGFuZy9FeGNlcHRpb24MAIAAgQEAHmNCeXRlcyBW

YXJpYWJsZSBkb2VzIG5vdCBleGlzdAcBsAwBsQGyAQAQQm9vdHN0cmFwTWV0aG9kcw8GAbMQAIwP

BwG0DAG1AbYMALUBtwcBuAwBuQG6DAG7AbwBACdvcmcvYXBhY2hlL2h0dHAvY2xpZW50L21ldGhv

ZHMvSHR0cFBvc3QBABdqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcgwBvQG+AQAQL2xpY2Vuc2UvcmVj

ZWl2ZQwAiwGyAQAtY29tL3dhbm1pL3NiYy9jb21tb24vdXRpbC9hdXRoL0FwYWNoZUh0dHBVdGls

BwG/DAHAAcEBAA13YW5taS5saWNlbnNlBwHCDAHDAcQBACtjb20vd2FubWkvc2JjL2NvbW1vbi91

dGlsL2F1dGgvVmVyaWZ5UmVzdWx0AQABNgEAFeivt+WuieijheaOiOadg+ivgeS5pgwAiwHFAQAz

b3JnL2FwYWNoZS9odHRwL2VudGl0eS9taW1lL2NvbnRlbnQvSW5wdXRTdHJlYW1Cb2R5DACLAcYB

AAAHAccMAcgBqgEAHWphdmEvbmV0L1Vua25vd25Ib3N0RXhjZXB0aW9uAQABNwEAGOiOt+WPlue9

keWNoeS/oeaBr+Wksei0pQEAGGphdmEvbmV0L1NvY2tldEV4Y2VwdGlvbgEAATgBABXojrflj5Zt

YWPlnLDlnYDlpLHotKUBAC5vcmcvYXBhY2hlL2h0dHAvZW50aXR5L21pbWUvY29udGVudC9TdHJp

bmdCb2R5BwHJDAHKAcsMAIsBzAcBnAwBuQHNDAHOAc8BAAxsaWNlbnNlX2ZpbGUMAbsB0AwB0QHS

BwGaDAC1AdMHAZ0MAdQB1QcB1gwB1wGfDAHYAdAHAdkMAakB2gcB2wwB3AHdDAHeAd8MAeAB0gEA

Ai0xAQAP5o6I5p2D5aSx6LSl77yBDAG9AeEHAZsMAeIAjAwB4wCMDAHkAeUHAeYMAecB6AcB6QwB

6gHrAQABOQEAK+aOiOadg+acjeWKoeWZqOWcsOWdgOmUmeivryzmjojmnYPlpLHotKXvvIEBAB5v

cmcvYXBhY2hlL2h0dHAvUGFyc2VFeGNlcHRpb24BAAIxMAEAIuaOiOadg+aVsOaNruW8guW4uCzm

jojmnYPlpLHotKXvvIEBABlqYXZhL25ldC9Db25uZWN0RXhjZXB0aW9uAQAV5o6I5p2D5aSx6LSl

6YeN6K+V77yBAQAnb3JnL2FwYWNoZS9odHRwL05vSHR0cFJlc3BvbnNlRXhjZXB0aW9uAQATamF2

YS9pby9JT0V4Y2VwdGlvbgcB7AwB7QHuBwHvDAHwAfEHAfIMAfMBqgcB9AwB9QGyAQABQAwB9gH3

DAH4AaUHAfkMAfoB+wEAEGphdmEvbGFuZy9TdHJpbmcMAPsA/AwAiwH8DAH9Af4MAf8CAAwAvgC/

DAIBAaoBAAEwAQAISy0wMDAwMDEMALkAjAEABUxUcnVlAQAGTEZhbHNlDAD3AIwHAgIMAgMCBAEA

HmphdmEvbGFuZy9JbnRlcnJ1cHRlZEV4Y2VwdGlvbgwCBQGqAQAgY29tL3dhbm1pL3NiYy9jb21t

b24vdXRpbC9SZWRpc0wHAgYMAgcCCAEAJ2phdmEvdXRpbC9jb25jdXJyZW50L1RocmVhZFBvb2xF

eGVjdXRvcgwCCQHoAQAoamF2YS91dGlsL2NvbmN1cnJlbnQvTGlua2VkQmxvY2tpbmdRdWV1ZQwA

iwIKAQAQamF2YS9sYW5nL09iamVjdAEAHWNvbS9nb29nbGUvY29tbW9uL2NhY2hlL0NhY2hlAQAn

amF2YS91dGlsL2NvbmN1cnJlbnQvRXhlY3V0aW9uRXhjZXB0aW9uAQAhb3JnL2FwYWNoZS9odHRw

L2NsaWVudC9IdHRwQ2xpZW50AQATamF2YS9pby9JbnB1dFN0cmVhbQEAMm9yZy9hcGFjaGUvaHR0

cC9lbnRpdHkvbWltZS9NdWx0aXBhcnRFbnRpdHlCdWlsZGVyAQAcb3JnL2FwYWNoZS9odHRwL0h0

dHBSZXNwb25zZQEADmdldEFjdGl2ZUNvdW50AQADKClJAQAIZ2V0UXVldWUBACYoKUxqYXZhL3V0

aWwvY29uY3VycmVudC9CbG9ja2luZ1F1ZXVlOwEAImphdmEvdXRpbC9jb25jdXJyZW50L0Jsb2Nr

aW5nUXVldWUBACRvcmcvYXBhY2hlL2NvbW1vbnMvbGFuZzMvU3RyaW5nVXRpbHMBAAdpc0JsYW5r

AQAbKExqYXZhL2xhbmcvQ2hhclNlcXVlbmNlOylaAQATamF2YS90aW1lL0xvY2FsRGF0ZQEAA25v

dwEAFygpTGphdmEvdGltZS9Mb2NhbERhdGU7AQAIdG9TdHJpbmcBABQoKUxqYXZhL2xhbmcvU3Ry

aW5nOwEABmVxdWFscwEAFShMamF2YS9sYW5nL09iamVjdDspWgEABCgpW0IBAC5jb20vd2FubWkv

c2JjL2NvbW1vbi9jb25zdGFudC9SZWRpc0tleUNvbnN0YW50AQAHQ19CWVRFUwEAEG9yZy9zbGY0

ai9Mb2dnZXIBAARpbmZvAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWCgILAgwKAHUCDQEAA3J1bgEA

OChMY29tL3dhbm1pL3NiYy9jb21tb24vdXRpbC9SZWRpc0w7KUxqYXZhL2xhbmcvUnVubmFibGU7

AQAXKExqYXZhL2xhbmcvUnVubmFibGU7KVYBAC1vcmcvYXBhY2hlL2h0dHAvaW1wbC9jbGllbnQv

SHR0cENsaWVudEJ1aWxkZXIBAAZjcmVhdGUBADEoKUxvcmcvYXBhY2hlL2h0dHAvaW1wbC9jbGll

bnQvSHR0cENsaWVudEJ1aWxkZXI7AQAFYnVpbGQBADMoKUxvcmcvYXBhY2hlL2h0dHAvaW1wbC9j

bGllbnQvQ2xvc2VhYmxlSHR0cENsaWVudDsBAAZhcHBlbmQBAC0oTGphdmEvbGFuZy9TdHJpbmc7

KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBAA9qYXZhL2xhbmcvQ2xhc3MBAA5nZXRDbGFzc0xv

YWRlcgEAGSgpTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIB

ABNnZXRSZXNvdXJjZUFzU3RyZWFtAQApKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9pby9JbnB1

dFN0cmVhbTsBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7KVYBACooTGph

dmEvaW8vSW5wdXRTdHJlYW07TGphdmEvbGFuZy9TdHJpbmc7KVYBACpjb20vd2FubWkvc2JjL2Nv

bW1vbi91dGlsL2F1dGgvTmV0d29ya1V0aWwBAAtnZXRMb2NhbE1hYwEAIm9yZy9hcGFjaGUvaHR0

cC9lbnRpdHkvQ29udGVudFR5cGUBAApURVhUX1BMQUlOAQAkTG9yZy9hcGFjaGUvaHR0cC9lbnRp

dHkvQ29udGVudFR5cGU7AQA5KExqYXZhL2xhbmcvU3RyaW5nO0xvcmcvYXBhY2hlL2h0dHAvZW50

aXR5L0NvbnRlbnRUeXBlOylWAQA2KClMb3JnL2FwYWNoZS9odHRwL2VudGl0eS9taW1lL011bHRp

cGFydEVudGl0eUJ1aWxkZXI7AQAHYWRkUGFydAEAeShMamF2YS9sYW5nL1N0cmluZztMb3JnL2Fw

YWNoZS9odHRwL2VudGl0eS9taW1lL2NvbnRlbnQvQ29udGVudEJvZHk7KUxvcmcvYXBhY2hlL2h0

dHAvZW50aXR5L21pbWUvTXVsdGlwYXJ0RW50aXR5QnVpbGRlcjsBAB4oKUxvcmcvYXBhY2hlL2h0

dHAvSHR0cEVudGl0eTsBAAlzZXRFbnRpdHkBAB8oTG9yZy9hcGFjaGUvaHR0cC9IdHRwRW50aXR5

OylWAQBPKExvcmcvYXBhY2hlL2h0dHAvY2xpZW50L21ldGhvZHMvSHR0cFVyaVJlcXVlc3Q7KUxv

cmcvYXBhY2hlL2h0dHAvSHR0cFJlc3BvbnNlOwEADWdldFN0YXR1c0xpbmUBAB4oKUxvcmcvYXBh

Y2hlL2h0dHAvU3RhdHVzTGluZTsBABpvcmcvYXBhY2hlL2h0dHAvU3RhdHVzTGluZQEADWdldFN0

YXR1c0NvZGUBAAlnZXRFbnRpdHkBACBvcmcvYXBhY2hlL2h0dHAvdXRpbC9FbnRpdHlVdGlscwEA

MChMb3JnL2FwYWNoZS9odHRwL0h0dHBFbnRpdHk7KUxqYXZhL2xhbmcvU3RyaW5nOwEAH2NvbS9h

bGliYWJhL2Zhc3Rqc29uL0pTT05PYmplY3QBAAtwYXJzZU9iamVjdAEANShMamF2YS9sYW5nL1N0

cmluZzspTGNvbS9hbGliYWJhL2Zhc3Rqc29uL0pTT05PYmplY3Q7AQAJZ2V0U3RyaW5nAQAmKExq

YXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0cmluZzsBAAdjb25zdW1lAQAcKEkpTGphdmEv

bGFuZy9TdHJpbmdCdWlsZGVyOwEABWNsb3NlAQARcmVsZWFzZUNvbm5lY3Rpb24BABRnZXRDb25u

ZWN0aW9uTWFuYWdlcgEAMCgpTG9yZy9hcGFjaGUvaHR0cC9jb25uL0NsaWVudENvbm5lY3Rpb25N

YW5hZ2VyOwEAHWphdmEvdXRpbC9jb25jdXJyZW50L1RpbWVVbml0AQAMTUlMTElTRUNPTkRTAQAf

TGphdmEvdXRpbC9jb25jdXJyZW50L1RpbWVVbml0OwEALG9yZy9hcGFjaGUvaHR0cC9jb25uL0Ns

aWVudENvbm5lY3Rpb25NYW5hZ2VyAQAUY2xvc2VJZGxlQ29ubmVjdGlvbnMBACMoSkxqYXZhL3V0

aWwvY29uY3VycmVudC9UaW1lVW5pdDspVgEAJmphdmEvbGFuZy9tYW5hZ2VtZW50L01hbmFnZW1l

bnRGYWN0b3J5AQAQZ2V0UnVudGltZU1YQmVhbgEAJigpTGphdmEvbGFuZy9tYW5hZ2VtZW50L1J1

bnRpbWVNWEJlYW47AQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3Ry

ZWFtOwEAImphdmEvbGFuZy9tYW5hZ2VtZW50L1J1bnRpbWVNWEJlYW4BAAdnZXROYW1lAQATamF2

YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BAAVzcGxpdAEAJyhMamF2YS9sYW5nL1N0cmluZzsp

W0xqYXZhL2xhbmcvU3RyaW5nOwEACmlzTm90RW1wdHkBABFqYXZhL2xhbmcvUnVudGltZQEACmdl

dFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAAUoW0IpVgEABGV4ZWMBACcoTGphdmEv

bGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsBAAlhcnJheWNvcHkBACooTGphdmEvbGFu

Zy9PYmplY3Q7SUxqYXZhL2xhbmcvT2JqZWN0O0lJKVYBAAdnZXRDb2RlAQAQamF2YS9sYW5nL1Ro

cmVhZAEABXNsZWVwAQAEKEopVgEACmdldE1lc3NhZ2UBABdvcmcvc2xmNGovTG9nZ2VyRmFjdG9y

eQEACWdldExvZ2dlcgEAJShMamF2YS9sYW5nL0NsYXNzOylMb3JnL3NsZjRqL0xvZ2dlcjsBAAdT

RUNPTkRTAQBJKElJSkxqYXZhL3V0aWwvY29uY3VycmVudC9UaW1lVW5pdDtMamF2YS91dGlsL2Nv

bmN1cnJlbnQvQmxvY2tpbmdRdWV1ZTspVgcCDgwCDwITDAEAAIwBACJqYXZhL2xhbmcvaW52b2tl

L0xhbWJkYU1ldGFmYWN0b3J5AQALbWV0YWZhY3RvcnkHAhUBAAZMb29rdXABAAxJbm5lckNsYXNz

ZXMBAMwoTGphdmEvbGFuZy9pbnZva2UvTWV0aG9kSGFuZGxlcyRMb29rdXA7TGphdmEvbGFuZy9T

dHJpbmc7TGphdmEvbGFuZy9pbnZva2UvTWV0aG9kVHlwZTtMamF2YS9sYW5nL2ludm9rZS9NZXRo

b2RUeXBlO0xqYXZhL2xhbmcvaW52b2tlL01ldGhvZEhhbmRsZTtMamF2YS9sYW5nL2ludm9rZS9N

ZXRob2RUeXBlOylMamF2YS9sYW5nL2ludm9rZS9DYWxsU2l0ZTsHAhYBACVqYXZhL2xhbmcvaW52

b2tlL01ldGhvZEhhbmRsZXMkTG9va3VwAQAeamF2YS9sYW5nL2ludm9rZS9NZXRob2RIYW5kbGVz

ACEAdQB+AAEAfwAGABoAgACBAAAACgCCAIMAAABKAIQAhQAAAAoAhgCHAAAAAgCIAIkAAAACAIoA

hwAAABYAAQCLAIwAAQCNAAAARAADAAEAAAASKrcAASoUAAK1AAQqEgW1AAaxAAAAAgCOAAAADgAD

AAAALQAEAGMACwBkAI8AAAAMAAEAAAASAJAAkQAAAAEAkgCTAAMAjQAAADYAAQACAAAAAgGwAAAA

AgCOAAAABgABAAAAMQCPAAAAFgACAAAAAgCQAJEAAAAAAAIAlACVAAEAlgAAAAUBAJQAAACXAAAA

CAABFAAAmAAAAAEAmQCaAAMAjQAAAEAAAQADAAAAAgGwAAAAAgCOAAAABgABAAAANgCPAAAAIAAD

AAAAAgCQAJEAAAAAAAIAlACVAAEAAAACAJsAnAACAJ0AAAAEAAEAngCWAAAACQIAlAAAAJsAAAAB

AJ8AoAACAI0AAAA/AAAAAwAAAAGxAAAAAgCOAAAABgABAAAAOgCPAAAAIAADAAAAAQCQAJEAAAAA

AAEAlACVAAEAAAABAKEAlQACAJYAAAAJAgCUAAAAoQAAAAEAogCjAAIAjQAAADUAAAACAAAAAbEA

AAACAI4AAAAGAAEAAAA9AI8AAAAWAAIAAAABAJAAkQAAAAAAAQCkAKUAAQCWAAAABQEApAAAAAEA

pgCnAAIAjQAAADUAAAACAAAAAbEAAAACAI4AAAAGAAEAAABAAI8AAAAWAAIAAAABAJAAkQAAAAAA

AQCUAJUAAQCWAAAABQEAlAAAAAEAqACMAAEAjQAAACsAAAABAAAAAbEAAAACAI4AAAAGAAEAAABD

AI8AAAAMAAEAAAABAJAAkQAAAAEAqQCqAAEAjQAAACwAAgABAAAAAgmtAAAAAgCOAAAABgABAAAA

RwCPAAAADAABAAAAAgCQAJEAAAABAKsArAABAI0AAAA0AAEAAQAAAAYqtwAHAbAAAAACAI4AAAAK

AAIAAABMAAQATQCPAAAADAABAAAABgCQAJEAAAABAK0ArgABAI0AAAAsAAEAAQAAAAIBsAAAAAIA

jgAAAAYAAQAAAFIAjwAAAAwAAQAAAAIAkACRAAAAAQCvAIwAAQCNAAAAKwAAAAEAAAABsQAAAAIA

jgAAAAYAAQAAAFYAjwAAAAwAAQAAAAEAkACRAAAAAQCoALAAAgCNAAAANQAAAAIAAAABsQAAAAIA

jgAAAAYAAQAAAFkAjwAAABYAAgAAAAEAkACRAAAAAAABALEAsgABAJYAAAAFAQCxAAAAAQCzALQA

AgCNAAAANgABAAIAAAACAbAAAAACAI4AAAAGAAEAAABdAI8AAAAWAAIAAAACAJAAkQAAAAAAAgCx

ALIAAQCWAAAABQEAsQAAAAIAtQCMAAEAjQAAAEcAAQABAAAADCq3AAiZAAcqtwAJsQAAAAMAjgAA

AA4AAwAAAGcABwBoAAsAagCPAAAADAABAAAADACQAJEAAAC2AAAAAwABCwACALcAuAABAI0AAAC0

AAIAAQAAAFyyAAqeAByyAAu2AAyaABOyAAu2AA25AA4BAJoABQSssgAKnQA4sgALtgAMnQAvsgAL

tgANuQAOAQCdACGyAA+4ABCaABKyAA+4ABG2ABK2ABOaAAcEpwAEA6wDrAAAAAMAjgAAACYACQAA

AG0AHQBuAB8AbwAoAHAAMQBxADwAcgBIAHMAWQByAFoAdQCPAAAADAABAAAAXACQAJEAAAC2AAAA

CAAFHzQDQAEAAAIAuQCMAAEAjQAAAH4AAgACAAAAIbgAEbYAErMAD7IAD7YAFLMAFacADkyyABcS

GLkAGQIAsQABAAAAEgAVABYAAwCOAAAAGgAGAAAAewAJAHwAEgB/ABUAfQAWAH4AIACAAI8AAAAW

AAIAFgAKALoAuwABAAAAIQCQAJEAAAC2AAAABwACVQcAvAoAIgC9AIwAAQCNAAAAUQACAAEAAAAW

sgALtgAMnQAPsgALKroAGgAAtgAbsQAAAAMAjgAAAA4AAwAAAIMACQCFABUAuACPAAAADAABAAAA

FgCQAJEAAAC2AAAAAwABFQACAL4AvwABAI0AAATfAAUAEAAAAZ0BTLgAHLYAHU27AB5ZuwAfWbcA

ICq0AAa2ACESIrYAIbYAI7cAJE4SJbYAJhIntgAoOgQZBMcAD7sAKVkSKhIrtwAssLsALVkZBBIn

twAuOgUSLzoGuAAwOganAB86B7sAKVkSMhIztwAssDoHuwApWRI1Eja3ACywuwA3WRkGsgA4twA5

Oge4ADo6CBkIEjsZB7YAPFcZCBI9GQW2ADxXLRkItgA+tgA/LC25AEACADoJGQm5AEEBALkAQgEA

NgoVChEAyKAAQBkJuQBDAQA6CxkLuABEOgwZDLgARToNGQ0SRrYARzoOGQ0SSLYARzoPuwApWRkO

GQ+3ACxMGQu4AEmnACG7AClZEkq7AB9ZtwAgEku2ACEVCrYATLYAI7cALEwZBLYATS22AE4suQBP

AQAJsgBQuQBRBACnAGBNuwApWRJSElO3ACxMpwBQTbsAKVkSVRJWtwAsTKcAQE27AClZEkoSWLcA

LEynADBNuwApWRJKEli3ACxMpwAgTbsAKVkSShJYtwAsTKcAEE27AClZEkoSWLcALEwrsAAaAFUA

WgBdADEAVQBaAGsANAACAEMBPgAxAEQAagE+ADEAawB4AT4AMQB5ATsBPgAxAAIAQwFOAFQARABq

AU4AVABrAHgBTgBUAHkBOwFOAFQAAgBDAV4AVwBEAGoBXgBXAGsAeAFeAFcAeQE7AV4AVwACAEMB

bgBZAEQAagFuAFkAawB4AW4AWQB5ATsBbgBZAAIAQwF+AFoARABqAX4AWgBrAHgBfgBaAHkBOwF+

AFoAAgBDAY4AFgBEAGoBjgAWAGsAeAGOABYAeQE7AY4AFgADAI4AAADaADYAAAC7AAIAvgAJAL8A

JwDAACkAwQAzAMIAOADDAEQAxgBRAMcAVQDKAFoAzwBdAMsAXwDMAGsAzQBtAM4AeQDRAIcA0gCM

ANMAlgDUAKAA1QCpANYAsgDXAMAA2ADIANkA0QDaANgA2wDfANwA6ADdAPEA3gD9AN8BAgDgAQUA

4wEjAOYBKADnASwA6AE7AP0BPgDpAT8A7AFLAP0BTgDtAU8A7wFbAP0BXgDwAV8A8gFrAP0BbgDz

AW8A9gF7AP0BfgD3AX8A+QGLAP0BjgD6AY8A/AGbAP8AjwAAAPIAGABfAAwAwADBAAcAbQAMAMIA

wwAHANEAMQDEAMUACwDYACoAxgCHAAwA3wAjAMcAyAANAOgAGgDJAIcADgDxABEAygCHAA8ACQEy

AMsAzAACACcBFADNAM4AAwAzAQgAzwDQAAQAUQDqANEA0gAFAFUA5gDTAIcABgCHALQA1ADVAAcA

jACvANYA1wAIALIAiQDYANkACQDAAHsA2gCFAAoBPwAMANsAwQACAU8ADADcAN0AAgFfAAwA3gDf

AAIBbwAMAOAA4QACAX8ADADiAOMAAgGPAAwA5AC7AAIAAAGdAJAAkQAAAAIBmwDlAOYAAQC2AAAA

iAAN/wBEAAUHAOcHAOgHAOkHAOoHAOsAAP8AGAAHBwDnBwDoBwDpBwDqBwDrBwDsBwDtAAEHAO5N

BwDvDf8AiwALBwDnBwDoBwDpBwDqBwDrBwDsBwDtBwDwBwDxBwDyAQAAHf8AGgACBwDnBwDoAAEH

AO5PBwDzTwcA9E8HAPVPBwD2TwcAvAwAAgD3AIwAAQCNAAAAsAAFAAQAAABAuABbTLIAXCu5AF0B

ALYAXiu5AF0BABJftgBgAzJNLLgAYZkAGbgAYrsAY1kqLLYAFLcAZLcAZbYAZk6nAARMsQABAAAA

OwA+ABYAAwCOAAAAIgAIAAABCgAEAQsAEAEMAB4BDQAlAQ4AOwERAD4BEAA/ARIAjwAAACAAAwAE

ADcA+AD5AAEAHgAdAPoAhwACAAAAQACQAJEAAAC2AAAACAADO0IHALwAAAIA+wD8AAIAjQAAAKQA

BQAEAAAATBAIvAhZAxBrVFkEEGlUWQUQbFRZBhBsVFkHECBUWQgQMVRZEAYQNVRZEAcQIFRNK74s

vmC8CE4sAy0DLL64AGcrAy0sviu+uABnLbAAAAACAI4AAAAWAAUAAAEVAC8BFgA3ARcAQAEYAEoB

GQCPAAAAKgAEAAAATACQAJEAAAAAAEwA+gD9AAEALwAdAP4A/QACADcAFQD/AP0AAwCWAAAABQEA

+gAAEAIBAACMAAEAjQAAAfsABAAEAAAA+AOzAAqyAAoEYLMACiq3AGhMK8YAMCu2AGkSarYAE5oA

Dyu2AGkSa7YAE5kAGCq3AGwDswAKsgAXEm25ABkCAKcAuLIAChAGoQAUsgAXEm65ABkCACq3AG+n

AJ8rtgBpEkq2ABOaAE+yABcSbrkAGQIAsgAKBKAAE7IACoUqtAAEaRQAcG2nABayAAqyAAoEZGiF

KrQABGkUAHBtQSC4AHKn/2RNsgAXLLYAdLkAGQIAp/9UsgAXEm65ABkCALIACgSgAA+yAAqFKrQA

BGmnABKyAAqyAAoEZGiFKrQABGlBILgAcqf/IE2yABcstgB0uQAZAgCn/xCxAAIAcQCgAKMAcwC9

AOQA5wBzAAMAjgAAAHoAHgAAAIcABACKAAwAiwARAIwAFgCNACIAjgAtAI8AMQCQADUAkQA/AJIA

QgCUAEoAlQBUAJYAWACXAFsAmQBnAJoAcQCcAJwAogCgAKUAowCjAKQApACwAKYAswCoAL0AqgDg

AK8A5ACyAOcAsADoALEA9ACzAPcAtgCPAAAAPgAGAJwABAEBAIkAAgCkAAwBAgEDAAIA4AAEAQEA

iQACAOgADAECAQMAAgARAOYA5QDmAAEAAAD4AJAAkQAAALYAAAAdAAwE/AAoBwDoFBgsUgRHBwEE

DxxOBEcHAQT6AA8ACAEFAIwAAQCNAAAAUgAJAAAAAAAqEnW4AHazABe7AHdZBAQUAHiyAHq7AHtZ

twB8twB9swALA7MACgGzAA+xAAAAAQCOAAAAFgAFAAAALAAIAQMAIQEEACUBBQApAQYAAwEGAAAA

AgEHAhIAAAAKAAECEAIUAhEAGQEkAAAADAABASUAAwEmAScBJg==' where config_key='other_setting';