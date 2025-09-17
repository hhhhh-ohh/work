boss会员资金:
curl -XDELETE http://localhost:9200/es_customer_funds
会员现金提现:
curl -XDELETE http://localhost:9200/es_customer_draw_cash
会员积分:
curl -XDELETE http://localhost:9200/es_customer_points_detail
boss订单收款:
curl -XDELETE http://localhost:9200/es_pay_order
boss退单退款:
curl -XDELETE http://localhost:9200/es_refund_order
