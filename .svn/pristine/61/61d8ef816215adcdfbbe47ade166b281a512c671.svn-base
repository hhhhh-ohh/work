alter table `sbc-vas`.`recommend_position_configuration`
    MODIFY COLUMN update_person varchar(100) null comment '更新人';

INSERT INTO `sbc-vas`.`recommend_position_configuration` (id, name, type, title, content, tactics_type, upper_limit,
is_open, create_time, create_person, update_time, update_person) VALUES (9, '分类页推荐频道', 9, '分类页推荐频道坑位', '2', 2, null, 1, null, null, '2021-03-24 01:26:45', '2c8080815cd3a74a015cd3ae86850001');
delete from `sbc-crm`.`auto_tag_sql` where auto_tag_id in (select auto_tag.id from `sbc-crm`.`auto_tag` where
system_flag = 1 and auto_tag.del_flag = 1 and auto_tag.id not in (1,2,3,4,5,6,8,9,10));

delete from `sbc-crm`.`auto_tag` where system_flag = 1 and del_flag = 1 and id not in (1,2,3,4,5,6,8,9,10);


create table `sbc-vas`.`commodity_scoring_algorithm`
(
    id            bigint auto_increment comment '主键'
        primary key,
    key_type      varchar(50)    null comment '列名',
    weight        decimal(10, 2) null comment '权值',
    tag_id        varchar(50)    null comment '标签ID',
    is_selected   tinyint        null comment '0: 否 1：是',
    del_flag      tinyint        null comment '0:否 1:是',
    create_time   datetime       null comment '创建时间',
    create_person varchar(64)    null comment '创建人',
    update_time   datetime       null comment '更新时间',
    update_person varchar(64)    null comment '更新人'
)
    comment '商品评分算法';


INSERT INTO `sbc-vas`.`commodity_scoring_algorithm` (id, key_type, weight, tag_id, is_selected, del_flag, create_time, create_person, update_time, update_person) VALUES (1, 'CATE', 1.00, '2,3,4,6', 1, 0, '2021-03-04 10:54:14', null, '2021-03-04 14:12:43', null);
INSERT INTO `sbc-vas`.`commodity_scoring_algorithm` (id, key_type, weight, tag_id, is_selected, del_flag, create_time, create_person, update_time, update_person) VALUES (2, 'TOP_CATE', 2.00, '2,3,4,6', 1, 0, '2021-03-04 10:54:57', null, '2021-03-04 14:12:00', null);
INSERT INTO `sbc-vas`.`commodity_scoring_algorithm` (id, key_type, weight, tag_id, is_selected, del_flag, create_time, create_person, update_time, update_person) VALUES (3, 'BRAND', 1.00, '2,3,4,6', 1, 0, '2021-03-04 11:08:11', null, '2021-03-04 14:12:00', null);
INSERT INTO `sbc-vas`.`commodity_scoring_algorithm` (id, key_type, weight, tag_id, is_selected, del_flag, create_time, create_person, update_time, update_person) VALUES (4, 'STORE', 1.00, '2,3,4,6', 1, 0, '2021-03-04 10:56:07', '', '2021-03-04 14:12:00', null);
INSERT INTO `sbc-vas`.`commodity_scoring_algorithm` (id, key_type, weight, tag_id, is_selected, del_flag, create_time, create_person, update_time, update_person) VALUES (5, 'SALES', 1.00, '2,3,4,6', 1, 0, '2021-03-04 10:57:04', null, '2021-03-04 14:12:00', null);

ALTER TABLE `sbc-crm`.`customer_base_info`
ADD COLUMN `customer_type` tinyint NULL COMMENT '会员类型 0：普通会员 1：企业会员' AFTER gender,
ADD COLUMN `customer_identity` tinyint NULL COMMENT '会员身份 0：普通会员 1：分销员' AFTER customer_type;

alter table `sbc-order`.`purchase` ADD COLUMN order_sort int default 1 null COMMENT '同组营销排序字段';

INSERT INTO `sbc-setting`.`function_info` (function_id, system_type_cd, menu_id, function_title, function_name, remark, sort, create_time, del_flag) VALUES ('2c9399de78b2565c0178b5fe2fee0002', 4, '2c939a4e7747c83601775321ebad0009', '用户兴趣推荐', 'f_customer_interest', null, 6, '2021-04-09 17:37:18', 0);

INSERT INTO `sbc-setting`.`authority` (authority_id, system_type_cd, function_id, authority_title, authority_name, authority_url, request_type, remark, sort, create_time, del_flag) VALUES ('2c9399de78b2565c0178b5ff09250003', 4, '2c9399de78b2565c0178b5fe2fee0002', '列表查询商品评分算法', null, '/commodity-scoringalg-orithm/list', 'POST', null, 0, '2021-04-09 17:38:13', 0);
INSERT INTO `sbc-setting`.`authority` (authority_id, system_type_cd, function_id, authority_title, authority_name, authority_url, request_type, remark, sort, create_time, del_flag) VALUES ('2c9399de78b2565c0178b5ff978d0004', 4, '2c9399de78b2565c0178b5fe2fee0002', '修改商品评分算法', null, '/commodity-scoringalg-orithm/modify', 'PUT', null, 1, '2021-04-09 17:38:50', 0);
INSERT INTO `sbc-setting`.`authority` (authority_id, system_type_cd, function_id, authority_title, authority_name, authority_url, request_type, remark, sort, create_time, del_flag) VALUES ('2c939a4e78f3f9010178f4c03b620000', 4, '2c9399de78b2565c0178b5fe2fee0002', '策略开关', null, '/recommendsystemconfig/modify', 'PUT', null, 2, '2021-04-21 22:05:42', 0);

INSERT INTO `sbc-vas`.`recommend_system_config` (id, config_key, config_type, config_name, remark, status, context, create_time, create_person, update_time, update_person, del_flag) VALUES (72, 'user_interest_recommend_config', 'user_interest_recommend_config', '基于用户兴趣推荐设置', null, 1, null, null, null, '2021-04-06 05:39:47', null, null);

use `s2b_statistics`;
-- auto-generated definition
create table `replay_enterprise_info`
(
    enterprise_id          tinytext      null,
    enterprise_name        tinytext      null,
    social_credit_code     tinytext      null,
    business_nature_type   int           null,
    business_industry_type int           null,
    business_employee_num  tinyint       null,
    business_license_url   varchar(1024) null,
    customer_id            tinytext      null,
    create_person          tinytext      null,
    create_time            timestamp     null,
    update_person          tinytext      null,
    update_time            timestamp     null,
    del_flag               tinyint       null
)
    comment '企业信息表';

use `sbc-vas`;
-- auto-generated definition
create table undo_log
(
    branch_id     bigint    null,
    xid           tinytext  null,
    context       tinytext  null,
    rollback_info longblob  null,
    log_status    int       null,
    log_created   timestamp null,
    log_modified  timestamp null
);


UPDATE `sbc-setting`.`authority` SET authority_url = '/goodsrelatedrecommend/getGoodsRelatedRecommendDetailInfoList' where authority_id = '2c9399de772ce09801773877420f0026';

UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 1;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 2;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 3;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 4;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 5;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 6;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '0' WHERE id = 7;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '1' WHERE id = 8;
UPDATE `sbc-vas`.`recommend_position_configuration` SET content = '1,2' WHERE id = 9;

-- 授信数据统计比较定时任务
INSERT INTO `xxl-job`.xxl_job_qrtz_trigger_info (job_group, job_cron, job_desc, add_time, update_time, author, alarm_email, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid) VALUES (1, '0 0 3 * * ? ', '授信数据统计比较定时任务', '2021-04-22 16:06:36', '2021-04-22 16:07:40', '陈莉', '', 'FIRST', 'creditStatisticsJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-04-22 16:06:36', '');

