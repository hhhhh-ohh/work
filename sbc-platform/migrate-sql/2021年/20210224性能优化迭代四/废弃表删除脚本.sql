-- account
drop table if exists `sbc-account`.`demo`;
-- 以下请根据自已的业务谨填删除
drop table if exists `sbc-account`.`channel_settlement`; -- 渠道分销结算表
drop table if exists `sbc-account`.`customer_credit_account`; -- 客户授信账户
drop table if exists `sbc-account`.`customer_credit_order`; -- 授信订单信息
drop table if exists `sbc-account`.`customer_credit_recover`; -- 客户授信恢复
drop table if exists `sbc-account`.`customer_credit_repay`; -- 客户授信还款
drop table if exists `sbc-account`.`customer_credit_setting`; -- 授信配置

-- customer
drop table if exists `sbc-customer`.`budget_setting1`;
drop table if exists `sbc-customer`.`invite_new_record_copy1`;
drop table if exists `sbc-customer`.`purchase_config_1`;
drop table if exists `sbc-customer`.`distribution_task_temp_copy2`;
drop table if exists `sbc-customer`.`customer_copy1`;
-- 以下请根据自已的业务谨填删除
drop table if exists `sbc-customer`.`accounting_subjects`; -- 会计科目
drop table if exists `sbc-customer`.`budget_setting`; -- 预算设置表
drop table if exists `sbc-customer`.`budget_setting_info`; -- 预算设置表
drop table if exists `sbc-customer`.`company_apply_record`; -- 合作申请记录表
drop table if exists `sbc-customer`.`company_contract`; -- 商家合同表
drop table if exists `sbc-customer`.`contract_enclosure`; -- 商家合同附件表
drop table if exists `sbc-customer`.`contract_warning`; -- 合同预警表
drop table if exists `sbc-customer`.`cost_center`; -- 成本中心表
drop table if exists `sbc-customer`.`customer_cost_record`; -- 子账号消费统计记录表
drop table if exists `sbc-customer`.`factory`; -- 厂区自提点
drop table if exists `sbc-customer`.`general_invoice`; -- 普票
drop table if exists `sbc-customer`.`purchase_config`; -- 采购设置
drop table if exists `sbc-customer`.`shop`; -- 门店表
drop table if exists `sbc-customer`.`store_breakcontract`; -- 商城管理回访管理表
drop table if exists `sbc-customer`.`store_factory`; -- 商家厂区关联表
drop table if exists `sbc-customer`.`store_returnvisit`; -- 商城管理回访管理表
drop table if exists `sbc-customer`.`tongcard_company`; -- 通卡公司表
drop table if exists `sbc-customer`.`yz_item_info`; -- 有赞商品信息
drop table if exists `sbc-customer`.`yz_user_info`; -- 有赞会员信息

-- goods
drop table if exists `sbc-goods`.`goods_copy3`;
drop table if exists `sbc-goods`.`goods_copy1`;
drop table if exists `sbc-goods`.`goods_customer_price_copy2`;
drop table if exists `sbc-goods`.`goods_info_spec_detail_rel_copy2`;
drop table if exists `sbc-goods`.`goods_interval_price_copy2`;
drop table if exists `sbc-goods`.`goods_level_price_copy1`;
drop table if exists `sbc-goods`.`goods_prop_detail_rel_copy2`;
drop table if exists `sbc-goods`.`goods_spec_detail_copy2`;
drop table if exists `sbc-goods`.`store_cate_goods_rela_copy2`;
-- 以下请根据自已的业务谨填删除
drop table if exists `sbc-goods`.`blacklist_store_cate`;  -- 黑名单
drop table if exists `sbc-goods`.`brand_store_goods`;  -- 品牌商城商品关联表
drop table if exists `sbc-goods`.`cate_commission_ratio`;  -- 分类佣金比例表
drop table if exists `sbc-goods`.`channel`;  -- 渠道
drop table if exists `sbc-goods`.`goods_price_record`;  -- 商品价格记录
drop table if exists `sbc-goods`.`goods_relation_info`;  -- 商品主动被动关联关系
drop table if exists `sbc-goods`.`goods_ware_stock`;  -- sku关联仓库库存
drop table if exists `sbc-goods`.`goods_ware_stock_detail`;  -- sku关联仓库库存明细数据表
drop table if exists `sbc-goods`.`item`;  -- 项目
drop table if exists `sbc-goods`.`project`;  -- 服务
drop table if exists `sbc-goods`.`project_cate`;  -- 店铺服务分类
drop table if exists `sbc-goods`.`project_item_rel`;  -- 服务项目关联
drop table if exists `sbc-goods`.`purchase_apply`;  -- 采购申请
drop table if exists `sbc-goods`.`purchase_apply_image`;  -- 采购申请图片附件
drop table if exists `sbc-goods`.`renewal_goods`;  -- 合作商更新商品
drop table if exists `sbc-goods`.`renewal_goods_info`;  -- 合作商更新货品
drop table if exists `sbc-goods`.`renewal_goods_rela`;  -- 合作商更新商品关联的货品
drop table if exists `sbc-goods`.`scene`;  -- 场景
drop table if exists `sbc-goods`.`stock_import_detail`;  -- 入库记录明细
drop table if exists `sbc-goods`.`stock_import_record`;  -- 入库记录表
drop table if exists `sbc-goods`.`user`;  -- 用户表
drop table if exists `sbc-goods`.`virtual_voucher`;  -- 虚拟券
drop table if exists `sbc-goods`.`virtual_voucher_batch`;  -- 虚拟券批次信息
drop table if exists `sbc-goods`.`virtual_voucher_batch_detail`;  -- 虚拟券批次明细
drop table if exists `sbc-goods`.`ware_house`;  -- 分仓仓库表
drop table if exists `sbc-goods`.`ware_house_city`;  -- 仓库关联地区表
drop table if exists `sbc-goods`.`whitelist_goods_info`;  -- 白名单

-- marketing
drop table if exists `sbc-marketing`.`coupon_code_copy`;
drop table if exists `sbc-marketing`.`coupon_code_bak`;
drop table if exists `sbc-marketing`.`draw_record_copy1`;
-- 以下请根据自已的业务谨填删除
drop table if exists `sbc-marketing`.`draw_activity`; -- 抽奖活动表
drop table if exists `sbc-marketing`.`draw_prize`; -- 抽奖活动关联奖品表
drop table if exists `sbc-marketing`.`draw_receive_info`; -- 抽奖记录 商品兑换信息
drop table if exists `sbc-marketing`.`draw_record`; -- 抽奖记录
drop table if exists `sbc-marketing`.`goods_info_spec_0`; -- SKU规格
drop table if exists `sbc-marketing`.`goods_info_spec_1`; -- SKU规格
drop table if exists `sbc-marketing`.`goods_info_spec_2`; -- SKU规格
drop table if exists `sbc-marketing`.`goods_info_spec_bak`; -- SKU规格
drop table if exists `sbc-marketing`.`meituan_company_bind`; -- 美团商家
drop table if exists `sbc-marketing`.`meituan_store_bind`; -- 美团门店
drop table if exists `sbc-marketing`.`meituan_store_bind`; -- 美团门店

-- message
drop table if exists `sbc-message`.`sms_template_1`;

-- order
drop table if exists `sbc-order`.`after_sales_evaluate_image_copy1`;
drop table if exists `sbc-order`.`test`;
-- 以下请根据自已的业务谨填删除
drop table if exists `sbc-order`.`after_sales_evaluate`; -- 售后评价表
drop table if exists `sbc-order`.`after_sales_evaluate_image`; -- 售后评价图片
drop table if exists `sbc-order`.`push_order_info_log`; -- 已支付订单信息推送至会员中心日志
drop table if exists `sbc-order`.`reserve_list`; -- 预采清单表
drop table if exists `sbc-order`.`reserve_list_goods_info_rela`; -- 预采清单和sku关联表
drop table if exists `sbc-order`.`scene`; -- 场景

-- pay
drop table if exists `sbc-pay`.`pay_channel_item_copy1`;
drop table if exists `sbc-pay`.`pay_gateway_config_copy1`;
drop table if exists `sbc-pay`.`pay_gateway_copy1`;

-- setting
drop table if exists `sbc-setting`.`authority_copy1`;
drop table if exists `sbc-setting`.`function_info_copy1`;
drop table if exists `sbc-setting`.`menu_info_copy1`;
drop table if exists `sbc-setting`.`system_config_copy`;
drop table if exists `sbc-setting`.`pay_trade_record_copy`;
-- 请根据自已的业务谨填删除
drop table if exists `sbc-setting`.`after_sales_reason`; -- 售后服务原因
drop table if exists `sbc-setting`.`after_sales_type`; -- 售后服务类型
drop table if exists `sbc-setting`.`after_sales_type`; -- 售后服务类型
drop table if exists `sbc-setting`.`base_address`; -- 四级地址信息表
drop table if exists `sbc-setting`.`company_address`; -- 四级地址信息表
drop table if exists `sbc-setting`.`feedback`; -- 意见反馈表
drop table if exists `sbc-setting`.`help_article`; -- 帮助中心文章
drop table if exists `sbc-setting`.`help_article_cate`; -- 文章类目表
drop table if exists `sbc-setting`.`shoukuanbao_login_set`; -- 微信授权登录配置
drop table if exists `sbc-setting`.`article_cate`; -- 内容分类表
drop table if exists `sbc-setting`.`article_info`; -- 文章信息表

-- bff
drop table if exists `sbc-bff`.`t_tx_exception`;
-- 请根据自已的业务谨填删除
drop table if exists `sbc-bff`.`address_gray_rule`;
drop table if exists `sbc-bff`.`customer_gray_rule`;

