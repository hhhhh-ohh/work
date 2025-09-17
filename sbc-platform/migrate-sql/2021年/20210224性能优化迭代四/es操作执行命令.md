1. 更新ES的最大分页数
优惠券活动:
curl -XPUT http://localhost:9200/es_coupon_activity/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
优惠券: 
curl -XPUT http://localhost:9200/es_coupon_info/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
会员 / 企业会员: 
curl -XPUT http://localhost:9200/es_customer_detail/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
会员现金提现: 
curl -XPUT http://localhost:9200/es_customer_draw_cash/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
boss会员资金: 
curl -XPUT http://localhost:9200/es_customer_funds/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
增票资质: 
curl -XPUT http://localhost:9200/es_customer_invoice/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
会员积分: 
curl -XPUT http://localhost:9200/es_customer_points_detail/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
分销员: 
curl -XPUT http://localhost:9200/es_distribution_customer/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
分效素材: 
curl -XPUT http://localhost:9200/es_distribution_goods_matter/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
分销记录: 
curl -XPUT http://localhost:9200/es_distribution_record/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
员工: 
curl -XPUT http://localhost:9200/es_employee/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
商品: 
curl -XPUT http://localhost:9200/es_goods/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
商品sku: 
curl -XPUT http://localhost:9200/es_goods_info/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
商品品牌: 
curl -XPUT http://localhost:9200/es_goods_brand/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
评价管理: 
curl -XPUT http://localhost:9200/es_goods_evaluate/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
拼团活动: 
curl -XPUT http://localhost:9200/es_groupon_activity/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
邀新记录: 
curl -XPUT http://localhost:9200/es_invite_new_record/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
订单开票: 
curl -XPUT http://localhost:9200/es_order_invoice/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
boss订单收款: 
curl -XPUT http://localhost:9200/es_pay_order/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
积分商品: 
curl -XPUT http://localhost:9200/es_points_goods_info/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
对账单: 
curl -XPUT http://localhost:9200/es_reconciliation/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
boss退单退款: 
curl -XPUT http://localhost:9200/es_refund_order/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
搜索联想词: 
curl -XPUT http://localhost:9200/es_search_associational_word/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
敏感词库: 
curl -XPUT http://localhost:9200/es_sensitive_words/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
boss的供应商结算 / 商家结算 / 商家端的财务结算: 
curl -XPUT http://localhost:9200/es_settlement/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
商品库: 
curl -XPUT http://localhost:9200/es_standard_goods/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
商家评价: 
curl -XPUT http://localhost:9200/es_store_evaluate_sum/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
boss供应商列表 / 商家列表 / 商家结算账户: 
curl -XPUT http://localhost:9200/es_store_information/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
操作日志: 
curl -XPUT http://localhost:9200/es_system_operation_log/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'
图片库/视频库: 
curl -XPUT http://localhost:9200/es_system_resource/_settings?preserve_existing=true -H 'Content-Type: application/json' -d '{ "max_result_window" : "2000000000" }'

2.保留index,仅删除文档脚本
优惠券活动:
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_coupon_activity/_delete_by_query' -d' { "query": { "match_all": {} } }'
优惠券: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_coupon_info/_delete_by_query' -d' { "query": { "match_all": {} } }'
会员 / 企业会员: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_customer_detail/_delete_by_query' -d' { "query": { "match_all": {} } }'
会员现金提现: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_customer_draw_cash/_delete_by_query' -d' { "query": { "match_all": {} } }'
boss会员资金: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_customer_funds/_delete_by_query' -d' { "query": { "match_all": {} } }'
增票资质: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_customer_invoice/_delete_by_query' -d' { "query": { "match_all": {} } }'
会员积分: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_customer_points_detail/_delete_by_query' -d' { "query": { "match_all": {} } }'
分销员: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_distribution_customer/_delete_by_query' -d' { "query": { "match_all": {} } }'
分效素材: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_distribution_goods_matter/_delete_by_query' -d' { "query": { "match_all": {} } }'
分销记录: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_distribution_record/_delete_by_query' -d' { "query": { "match_all": {} } }'
员工: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_employee/_delete_by_query' -d' { "query": { "match_all": {} } }'
商品: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_goods/_delete_by_query' -d' { "query": { "match_all": {} } }'
商品sku: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_goods_info/_delete_by_query' -d' { "query": { "match_all": {} } }'
商品品牌: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_goods_brand/_delete_by_query' -d' { "query": { "match_all": {} } }'
评价管理: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_goods_evaluate/_delete_by_query' -d' { "query": { "match_all": {} } }'
拼团活动: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_groupon_activity/_delete_by_query' -d' { "query": { "match_all": {} } }'
邀新记录: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_invite_new_record/_delete_by_query' -d' { "query": { "match_all": {} } }'
订单开票: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_order_invoice/_delete_by_query' -d' { "query": { "match_all": {} } }'
boss订单收款: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_pay_order/_delete_by_query' -d' { "query": { "match_all": {} } }'
积分商品: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_points_goods_info/_delete_by_query' -d
' { "query": { "match_all": {} } }'
对账单: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_reconciliation/_delete_by_query' -d' { "query": { "match_all": {} } }'
boss退单退款: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_refund_order/_delete_by_query' -d' { "query": { "match_all": {} } }'
搜索联想词: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_search_associational_word/_delete_by_query' -d' { "query": { "match_all": {} } }'
敏感词库: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_sensitive_words/_delete_by_query' -d' { "query": { "match_all": {} } }'
boss的供应商结算 / 商家结算 / 商家端的财务结算: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_settlement/_delete_by_query' -d' { "query": { "match_all": {} } }'
商品库: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_standard_goods/_delete_by_query' -d' { "query": { "match_all": {} } }'
商家评价: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_store_evaluate_sum/_delete_by_query' -d' { "query": { "match_all": {} } }'
boss供应商列表 / 商家列表 / 商家结算账户: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_store_information/_delete_by_query' -d' { "query": { "match_all": {} } }'
操作日志: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_system_operation_log/_delete_by_query' -d' { "query": { "match_all": {} } }'
图片库/视频库: 
curl  -H "Content-Type: application/json" -XPOST 'http://localhost:9200/es_system_resource/_delete_by_query' -d' { "query": { "match_all": {} } }'
