-- 京东vop供应商账号（需要改）
set @phone = '18100000000';
-- linkedmall供应商账号对应的id（需要改）
set @empId = 'ff808jdoeis84lmd0175d3ddc4d202cf';

-- 密码（不用改）
set @pwd = '000000';
-- 密码盐值（不用改）
set @saltVal = '10ce9a5a2b17ef4fa64355cba47a837d80000173a3e6f99c7eb1e0de27c051d9';

-- 密码
set @pwd_md5 = md5(concat(@empId,md5(@pwd),@saltVal));

-- 公司信息表
set @company_id = null;
INSERT INTO `sbc-customer`.`company_info`(`company_name`, `back_ID_card`, `province_id`,
    `city_id`, `area_id`, `street_id`, `detail_address`, `contact_name`, `contact_phone`,
    `copyright`, `company_descript`, `operator`, `create_time`, `update_time`, `company_type`,
    `is_account_checked`, `social_credit_code`, `address`, `legal_representative`,
    `registered_capital`, `found_date`, `business_term_start`, `business_term_end`,
    `business_scope`, `business_licence`, `front_ID_card`, `company_code`, `supplier_name`,
    `del_flag`, `remit_affirm`, `apply_enter_time`, `store_type`, `company_source_type`)
VALUES ('京东vop', '', 110000, 110100, 110115, NULL, '亦庄经济开发区', '京东vop',
    @phone, NULL, NULL, '', now(), now(), 1,
    NULL,
    '91330100799655058B', NULL, NULL, NULL, NULL, NULL, NULL, 'JDvop',
    'https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/bb2ee3fa3465eabcbd67f5f32747bdc6.png',
    '', 'JDvop', 'JDvop', 0, 0, now(), 1, 2);
select @company_id := max(company_info_id) from `sbc-customer`.`company_info`;


-- 店铺信息表
set @store_id = null;
INSERT INTO `sbc-customer`.`store`(`company_info_id`, `freight_template_type`, `contract_start_date`, `contract_end_date`, `store_name`, `store_logo`, `store_sign`, `supplier_name`, `company_type`, `contact_person`, `contact_mobile`, `contact_email`, `province_id`, `city_id`, `area_id`, `street_id`, `address_detail`, `account_day`, `audit_state`, `audit_reason`, `store_closed_reason`, `store_state`, `del_flag`, `apply_enter_time`, `small_program_code`, `store_type`, `store_pinyin_name`, `supplier_pinyin_name`, `company_source_type`)
VALUES (@company_id, 0, now(), DATE_ADD(now(), INTERVAL 3 year),
'京东vop', NULL, NULL, '京东vop', 1, '京东vop', @phone, 'JDvop@jd.com',
110000, 110100, 110115, NULL, '亦庄经济开发区', '15', 1, NULL, NULL, 0, 0,
now(), 'https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/7aec0de00de28b7d9cb4011cae2cb70a.jpg', 0, 'JDvop', 'JDvop', 2);
select @store_id := max(store_id) from `sbc-customer`.`store`;

-- 创建员工
INSERT INTO `sbc-customer`.`employee`(`employee_id`, `employee_name`, `employee_mobile`, `role_ids`, `is_employee`, `account_name`, `account_password`, `employee_salt_val`, `account_state`, `account_disable_reason`, `third_id`, `customer_id`, `del_flag`, `login_error_time`, `login_lock_time`, `login_time`, `company_info_id`, `is_master_account`, `account_type`, `email`, `job_no`, `position`, `birthday`, `sex`, `become_member`, `heir_employee_id`, `create_time`, `create_person`, `update_time`, `update_person`, `delete_time`, `delete_person`, `department_ids`, `manage_department_ids`)
VALUES (@empId, @phone, @phone, NULL, 0, @phone, @pwd_md5, @saltVal, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, @company_id, 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, now(), '', NULL, NULL, NULL, NULL, NULL, '0');


-- 创建账号
INSERT INTO `sbc-account`.`company_account`( `company_info_id`, `is_received`, `is_default_account`, `account_name`, `bank_name`, `bank_branch`, `bank_no`, `bank_code`, `bank_status`, `third_id`, `create_time`, `update_time`, `del_flag`, `del_time`, `remit_price`)
VALUES (@company_id, 0, 0, 'JDvop', '支付宝', '浙江杭州', 'JDvop', 'alipay', 0,
        NULL, now(), NULL, 0, NULL, NULL);

-- 签约所有三级分类
INSERT INTO `sbc-goods`.`contract_cate`(`store_id`, `cate_id`, `cate_rate`, `qualification_pics`)
SELECT @store_id,d.cate_id,null,'' from `sbc-goods`.goods_cate d where d.del_flag = 0 and d.cate_grade = 3;

-- 签约所有品牌
INSERT INTO `sbc-goods`.`contract_brand`(`store_id`, `brand_id`, `check_brand_id`, `authorize_pic`)
SELECT @store_id,b.brand_id,null,'' from `sbc-goods`.goods_brand b where b.del_flag = 0;

-- 默认店铺分类
INSERT INTO `sbc-goods`.`store_cate`(`store_id`, `cate_name`, `cate_parent_id`, `cate_img`, `cate_path`, `cate_grade`, `pin_yin`, `s_pin_yin`, `create_time`, `update_time`, `del_flag`, `sort`, `is_default`)
VALUES (@store_id, '默认分类', 0, NULL, '0|', 1, NULL, NULL, now(), now(), 0, 0, 1);

ALTER TABLE `sbc-customer`.`store`
MODIFY COLUMN `company_source_type` tinyint(1) NULL DEFAULT 0 COMMENT '商家来源类型 0:商城入驻 1:linkMall初始化 2:京东vop初始化' AFTER `supplier_pinyin_name`;
ALTER TABLE `sbc-customer`.`company_info`
MODIFY COLUMN `company_source_type` tinyint(1) NULL DEFAULT 0 COMMENT '商家来源类型 0:商城入驻 1:linkMall初始化 2:京东vop初始化' AFTER `store_type`;


INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`id`, `job_group`,
`job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`,
`executor_route_strategy`, `executor_handler`, `executor_param`,
`executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`,
`glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (NULL, 1, '0 0 1 * * ?', '京东接口token获取', now(),
        now(), '张文昌', '', 'FIRST', 'jdTokenJobHandler', '',
'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', now(), '');

INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`,
                                                   `author`, `alarm_email`, `executor_route_strategy`,
                                                   `executor_handler`, `executor_param`, `executor_block_strategy`,
                                                   `executor_timeout`, `executor_fail_retry_count`, `glue_type`,
                                                   `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, '0 0/10 * * * ?', '处理京东VOP推送的消息', now(), '2021-05-20 10:19:44', '韩伟', '', 'FIRST',
        'jdVopMessageJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', now(), '');

-- 京东商品初始化任务
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info`(`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`,
                                        `author`, `alarm_email`, `executor_route_strategy`,
                                        `executor_handler`, `executor_param`, `executor_block_strategy`,
                                        `executor_timeout`, `executor_fail_retry_count`, `glue_type`,
                                        `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, '0 0 0 1 1 ? 2099', 'VOP商品初始化同步', now(), now(), '郑旸', '', 'FIRST',
        'ChannelGoodsSyncJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', now(), '');

-- 京东VOP地址全量同步
INSERT INTO `xxl-job`.`xxl_job_qrtz_trigger_info` (`job_group`, `job_cron`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES ('1', '0 0 1 * * ?', '京东VOP地址全量同步', now(), now(), '吴瑞', '', 'FIRST', 'thirdVopAddressSyncJobHandler', '', 'SERIAL_EXECUTION', '0', '0', 'BEAN', '', 'GLUE代码初始化', now(), '');
