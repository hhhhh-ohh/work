package com.wanmi.sbc.common.constant;

public class MQConstant {
    private MQConstant() {
    }

    /**
     * 记录操作日志MQ KEy
     */
    public static final String OPERATE_LOG_ADD = "q.operate.log.add";

    /**
     * 商家评价
     */
    public static final String STORE_EVALUATE_ADD = "q.store.evaluate.add";

    /**
     * 商品评价
     */
    public static final String GOODS_EVALUATE_ADD = "q.goods.evaluate.add";

    /**
     * 增加成长值MQ Key
     */
    public static final String INCREASE_GROWTH_VALUE = "q.increase.customer.growth.value";

    /**
     * 发放优惠券MQ Key
     */
    public static final String ISSUE_COUPONS = "q.level.rights.issue.coupons";

    /**
     * 统计商品收藏量MQ Key
     */
    public static final String GOODS_COLLECT_NUM = "q.goods.collect.num";

    /**
     * 统计商品销量MQ Key
     */
    public static final String GOODS_SALES_NUM = "q.goods.sales.num";

    /**
     * 统计商品评论数MQ Key
     */
    public static final String GOODS_EVALUATE_NUM = "q.goods.evaluate.num";

    /**
     * 统计积分商品销量MQ Key
     */
    public static final String POINTS_GOODS_SALES_NUM = "q.points_goods.sales.num";

    /**
     * 立刻预约商品mq异步处理同步预约数量--input
     */
    public static final String RUSH_TO_APPOINTMENT_SALE_GOODS_INPUT = "q-rush-to-appointment-sale-goods-input";

    /**
     * 立刻预约商品mq异步处理同步预约数量--output
     */
    public static final String RUSH_TO_APPOINTMENT_SALE_GOODS_OUTPUT = "q-rush-to-appointment-sale-goods-output";

    /**
     * 立刻抢购商品mq异步处理--input
     */
    public static final String RUSH_TO_SALE_GOODS_INPUT = "q-rush-to-sale-goods-input";

    /**
     * 立刻抢购商品mq异步处理--output
     */
    public static final String RUSH_TO_SALE_GOODS_OUTPUT = "q-rush-to-sale-goods-output";

    /**
     * app push短信发送
     */
    public static final String Q_SMS_SERVICE_PUSH_ADD = "q.sms.service.push.add";

    /**
     * 短信发送
     */
    public static final String Q_SMS_SEND_MESSAGE_ADD = "q.sms.send.message.add";

    /**
     * 验证码短信发送
     */
    public static final String Q_SMS_SEND_CODE_MESSAGE_ADD = "q.sms.send.message.code.add";

    /**
     * 消息发送
     */
    public static final String Q_SMS_SERVICE_MESSAGE_SEND = "q.sms.service.message.send";

    /**
     * elastic模块-标品库-初始化
     */
    public static final String Q_ES_STANDARD_INIT = "q.es.standard.init";

    /**
     * elastic模块-商品-初始化
     */
    public static final String Q_ES_GOODS_INIT = "q.es.goods.init";

    /**
     * elastic模块-商品-更新店铺信息
     */
    public static final String Q_ES_GOODS_MODIFY_STORE_STATE = "q.es.goods.modify.store.state";

    /**
     * elastic模块-积分商品-增加销量
     */
    public static final String Q_ES_POINTS_GOODS_ADD_SALES = "q.es.points.goods.add.sales";

    /**
     * elastic模块-积分商品-修改上下架
     */
    public static final String Q_ES_POINTS_GOODS_MODIFY_ADDED_FLAG = "q.es.points.goods.modify.added.flag";

    /**
     * 会员注册成功，发送订单支付MQ消息，同步ES
     */
    public static final String Q_ES_SERVICE_CUSTOMER_REGISTER = "q.es.service.customer.register";

    /**
     * 修改会员基本信息成功，发送订单支付MQ消息，同步ES
     */
    public static final String Q_ES_SERVICE_CUSTOMER_MODIFY_BASE_INFO = "q.es.service.customer.modify.base.info";

    /**
     * 修改会员账号成功，发送订单支付MQ消息，同步ES
     */
    public static final String Q_ES_SERVICE_CUSTOMER_MODIFY_CUSTOMER_ACCOUNT = "q.es.service.customer.modify.customer.account";

    /**
     * 更新会员是否分销员字段，发送订单支付MQ消息，同步ES
     */
    public static final String Q_ES_SERVICE_CUSTOMER_MODIFY_CUSTOMER_DISTRIBUTOR = "q.es.service.customer.modify.customer.isDistributor";

    /**
     * 新增积分兑换券，发送订单支付MQ消息，同步ES
     */
    public static final String Q_ES_SERVICE_COUPON_ADD_POINTS_COUPON = "q.es.service.coupon.add.points.coupon";

    /**
     * 新增会员等級，同步ES
     */
    public static final String Q_ES_SERVICE_CUSTOMER_LEVEL_DETAIL_ADD = "q.es.service.customer.level.detail.add";

    /**
     * 订单开票数据新增同步es
     */
    public static final String Q_ES_SERVICE_ADD_ORDER_INVOICE = "q.es.service.add.order.invoice";

    /**
     * 同步订单状态到开票的es数据中
     */
    public static final String Q_ES_SERVICE_UPDATE_FLOW_STATE_ORDER_INVOICE = "q.es.service.update.flow.state.order.invoice";

    /**
     * 第三方平台，下架SKU商品
     */
    public static final String Q_THIRD_PLATFORM_SKU_OFF_ADDED_FLAG = "q.third.platform.sku.off.added.flag";

    /**
     * 批量导入-商品-初始化
     */
    public static final String Q_BATCH_ADD_GOODS_INIT = "q.batch.add.goods.init";

    /**
     * 批量导出
     */
    public static final String Q_BATCH_BUSINESS_EXPORT = "q.batch.business.export";

    /**
     * 秒杀订单--异步处理销量和个人购买记录
     */
    public static final String Q_DEAL_FLASH_SALE_RECORD = "q.deal.flash.sale.record";

}
