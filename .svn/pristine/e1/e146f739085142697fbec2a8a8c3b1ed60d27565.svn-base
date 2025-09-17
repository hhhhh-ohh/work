-- 1、笔记分类
CREATE TABLE `sbc-setting`.`recommend_cate` (
                                  `cate_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                  `cate_name` varchar(20) DEFAULT NULL COMMENT '分类名称',
                                  `cate_sort` tinyint(4) DEFAULT '0' COMMENT '排序',
                                  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                                  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                                  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                                  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
                                  `del_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                                  PRIMARY KEY (`cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='笔记分类表';


-- 种草信息表
CREATE TABLE `sbc-setting`.`recommend` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `page_code` varchar(32) NOT NULL,
                             `title` varchar(40) NOT NULL COMMENT '标题',
                             `new_title` varchar(40) DEFAULT NULL COMMENT '新标题',
                             `cate_id` bigint(20) DEFAULT NULL COMMENT '分类id',
                             `new_cate_id` bigint(20) DEFAULT NULL COMMENT '新分类id',
                             `save_status` tinyint(4) DEFAULT NULL COMMENT '保存状态 1:草稿 2:已发布 3:修改已发布',
                             `cover_img` varchar(255) NOT NULL COMMENT '封面',
                             `video` text COMMENT '视频',
                             `read_num` bigint(20) DEFAULT NULL COMMENT '阅读数',
                             `fabulous_num` bigint(20) DEFAULT NULL COMMENT '点赞数',
                             `forward_num` bigint(20) DEFAULT NULL COMMENT '转发数',
                             `forward_type` tinyint(4) DEFAULT NULL COMMENT '是否展示转发数 1:是 0:否',
                             `visitor_num` bigint(20) DEFAULT NULL COMMENT '访客数',
                             `is_top` tinyint(4) DEFAULT NULL COMMENT '是否置顶 0:否 1:是',
                             `top_time` datetime DEFAULT NULL COMMENT '置顶时间',
                             `status` tinyint(4) DEFAULT NULL COMMENT '内容状态 0:隐藏 1:公开',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
                             `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除标志 0：否，1：是',
                             `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
                             `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
                             PRIMARY KEY (`id`),
                             KEY `page_code` (`page_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='种草信息表';

-- 种草开关
INSERT INTO `sbc-setting`.`system_config` (`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES (104, 'recommend', 'recommend_status', '种草开关', NULL, 0, NULL, '2022-05-19 10:27:04', '2022-05-24 10:09:47', 0);

-- 种草直播优先级
INSERT INTO `sbc-setting`.`system_config` (`id`, `config_key`, `config_type`, `config_name`, `remark`, `status`, `context`, `create_time`, `update_time`, `del_flag`) VALUES (105, 'recommend', 'find_show_type', '发现页展示直播种草展示优先级', NULL, 1, NULL, '2022-05-19 15:47:26', '2022-05-24 09:50:44', 0);


