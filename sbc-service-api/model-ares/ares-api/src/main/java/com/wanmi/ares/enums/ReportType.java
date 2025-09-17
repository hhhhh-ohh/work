package com.wanmi.ares.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import lombok.Getter;

/**
 * 报表导出枚举类
 **/
@ApiEnum
@Getter
public enum ReportType {

    @ApiEnumProperty(" 0：流量报表")
    FLOW(ReportType.BEAN_NAME_FLOW),

    @ApiEnumProperty(" 1：交易报表")
    TRADE(ReportType.BEAN_NAME_TRADE),

    @ApiEnumProperty(" 2：商品销售报表")
    GOODS_TRADE(ReportType.BEAN_NAME_GOODS_TRADE),

    @ApiEnumProperty(" 3：商品分类销售报表")
    GOODS_CATE_TRADE(ReportType.BEAN_NAME_GOODS_CATE_TRADE),

    @ApiEnumProperty(" 4：商品品牌销售报表")
    GOODS_BRAND_TRADE(ReportType.BEAN_NAME_GOODS_BRAND_TRADE),

    @ApiEnumProperty(" 5：客户增长报表")
    CUSTOMER_GROW(ReportType.BEAN_NAME_CUSTOMER_GROW),

    @ApiEnumProperty(" 6：客户交易报表")
    CUSTOMER_TRADE(ReportType.BEAN_NAME_CUSTOMER_TRADE),

    @ApiEnumProperty(" 7：客户等级交易报表")
    CUSTOMER_LEVEL_TRADE(ReportType.BEAN_NAME_CUSTOMER_LEVEL_TRADE),

    @ApiEnumProperty(" 8：客户地区交易报表")
    CUSTOMER_AREA_TRADE(ReportType.BEAN_NAME_CUSTOMER_AREA_TRADE),

    @ApiEnumProperty(" 9：业务员业绩报表")
    SALESMAN_TRADE(ReportType.BEAN_NAME_SALESMAN_TRADE),

    @ApiEnumProperty(" 10：业务员获客报表")
    SALESMAN_CUSTOMER(ReportType.BEAN_NAME_SALESMAN_CUSTOMER),

    @ApiEnumProperty(" 11：商品分类销售报表")
    STORE_CATE_TRADE(ReportType.BEAN_NAME_STORE_CATE_TRADE),

    @ApiEnumProperty(" 12：店铺流量报表")
    STORE_FLOW(ReportType.BEAN_NAME_STORE_FLOW),

    @ApiEnumProperty(" 13：店铺交易报表")
    STORE_TRADE(ReportType.BEAN_NAME_STORE_TRADE),

    @ApiEnumProperty(" 14：拼团效果报表")
    GROUPON_EFFECT(ReportType.BEAN_NAME_GROUPON_EFFECT),

    @ApiEnumProperty(" 15：秒杀效果报表")
    FLASH_EFFECT(ReportType.BEAN_NAME_FLASH_EFFECT),

    @ApiEnumProperty(" 16：预约效果报表")
    APPOINTMENT_EFFECT(ReportType.BEAN_NAME_APPOINTMENT_EFFECT),

    @ApiEnumProperty(" 17：优惠券效果报表--已废弃")
    COUPON_EFFECT(ReportType.BEAN_NAME_COUPON_EFFECT),

    @ApiEnumProperty(" 18：全款预售效果报表")
    FULL_MONEY_BOOK_EFFECT(ReportType.BEAN_NAME_FULL_MONEY_BOOK_EFFECT),

    @ApiEnumProperty(" 19：定金预售效果报表")
    DEPOSIT_BOOK_EFFECT(ReportType.BEAN_NAME_DEPOSIT_BOOK_EFFECT),

    @ApiEnumProperty(" 20：优惠券效果报表")
    COUPON_INFO_EFFECT(ReportType.BEAN_NAME_COUPON_INFO_EFFECT),

    @ApiEnumProperty(" 21：优惠券活动效果报表")
    COUPON_ACTIVITY_EFFECT(ReportType.BEAN_NAME_COUPON_ACTIVITY_EFFECT),

    @ApiEnumProperty(" 22：店铺优惠券效果报表")
    COUPON_STORE_EFFECT(ReportType.BEAN_NAME_COUPON_STORE_EFFECT),

    @ApiEnumProperty(" 23：优惠券活动详情报表")
    COUPON_INFO_EFFECT_ACTIVITY(ReportType.BEAN_NAME_COUPON_INFO_EFFECT_ACTIVITY),

    @ApiEnumProperty(" 24：减折赠效果报表")
    REDUCE_DISCOUNT_GIFT_EFFECT(ReportType.BEAN_NAME_REDUCE_DISCOUNT_GIFT_EFFECT),

    @ApiEnumProperty(" 25：营销概览报表")
    MARKETING_OVERVIEW(ReportType.BEAN_NAME_MARKETING_OVERVIEW),

    @ApiEnumProperty("26：商品数据")
    BUSINESS_GOODS(ReportType.BEAN_NAME_BUSINESS_GOODS),

    @ApiEnumProperty("27：订单数据")
    BUSINESS_TRADE(ReportType.BEAN_NAME_BUSINESS_TRADE),

    @ApiEnumProperty("28：积分订单数据")
    BUSINESS_POINTS_TRADE(ReportType.BEAN_NAME_BUSINESS_POINTS_TRADE),

    @ApiEnumProperty("29：退单数据")
    BUSINESS_RETURN_ORDER(ReportType.BEAN_NAME_BUSINESS_RETURN_ORDER),

    @ApiEnumProperty("30：客户积分")
    BUSINESS_CUSTOMER_POINTS(ReportType.BEAN_NAME_BUSINESS_CUSTOMER_POINTS),

    @ApiEnumProperty("31：财务对账")
    BUSINESS_FINANCE_BILL(ReportType.BEAN_NAME_BUSINESS_FINANCE_BILL),

    @ApiEnumProperty("32：财务对账明细")
    BUSINESS_FINANCE_BILL_DETAIL(ReportType.BEAN_NAME_BUSINESS_FINANCE_BILL_DETAIL),

    @ApiEnumProperty("33：财务结算")
    BUSINESS_SETTLEMENT(ReportType.BEAN_NAME_BUSINESS_SETTLEMENT),

    @ApiEnumProperty("34：财务结算明细")
    BUSINESS_SETTLEMENT_DETAIL(ReportType.BEAN_NAME_BUSINESS_SETTLEMENT_DETAIL),

    @ApiEnumProperty("35：会员资金")
    BUSINESS_CUSTOMER_FUNDS(ReportType.BEAN_NAME_BUSINESS_CUSTOMER_FUNDS),

    @ApiEnumProperty("36：会员资金明细")
    BUSINESS_CUSTOMER_FUNDS_DETAIL(ReportType.BEAN_NAME_BUSINESS_CUSTOMER_FUNDS_DETAIL),

    @ApiEnumProperty("37：分销员佣金")
    BUSINESS_DISTRIBUTION_COMMISSION(ReportType.BEAN_NAME_BUSINESS_DISTRIBUTION_COMMISSION),

    @ApiEnumProperty("38：会员提现")
    BUSINESS_DRAW_CASH(ReportType.BEAN_NAME_BUSINESS_DRAW_CASH),

    @ApiEnumProperty("39：分销员")
    BUSINESS_DISTRIBUTION_CUSTOMER(ReportType.BEAN_NAME_BUSINESS_DISTRIBUTION_CUSTOMER),

    @ApiEnumProperty("40：分销记录")
    BUSINESS_DISTRIBUTION_RECORD(ReportType.BEAN_NAME_BUSINESS_DISTRIBUTION_RECORD),

    @ApiEnumProperty("41：操作日志")
    BUSINESS_OPERATION_LOG(ReportType.BEAN_NAME_BUSINESS_OPERATION_LOG),

    @ApiEnumProperty("42：订单开票")
    BUSINESS_ORDER_TICKET(ReportType.BEAN_NAME_BUSINESS_ORDER_TICKET),

    @ApiEnumProperty("43：邀新记录")
    BUSINESS_INVITE_RECORD(ReportType.BEAN_NAME_BUSINESS_INVITE_RECORD),

    @ApiEnumProperty("44：跨境订单数据")
    BUSINESS_CROSS_TRADE(ReportType.BEAN_NAME_BUSINESS_CROSS_TRADE),

    @ApiEnumProperty("45：跨境退单数据")
    BUSINESS_CROSS_RETURNTRADE(ReportType.BEAN_NAME_BUSINESS_CROSS_RETURNTRADE),

    @ApiEnumProperty("46：礼品卡卡券")
    MARKETING_GIFTCARD_VOUCHERS(ReportType.BEAN_NAME_MARKETING_GIFTCARD_VOUCHERS),

    @ApiEnumProperty("47：待备案商品列表")
    BUSINESS_CROSS_GOODS_INFO(ReportType.BEAN_NAME_BUSINESS_CROSS_GOODS_INFO),

    @ApiEnumProperty("48：O2O商品数据")
    O2O_BUSINESS_GOODS(ReportType.BEAN_NAME_O2O_BUSINESS_GOODS),

    @ApiEnumProperty("49：门店流量报表")
    O2O_FLOW(ReportType.BEAN_NAME_O2O_STORE_FLOW),

    @ApiEnumProperty(" 50：门店交易报表")
    O2O_TRADE(ReportType.BEAN_NAME_STORE_TRADE),

    @ApiEnumProperty("51：门店分类销售报表")
    O2O_CATE_TRADE(ReportType.BEAN_NAME_O2O_CATE_TRADE),

    @ApiEnumProperty("52：自提点数据")
    PICKUP_SETTING_INFO(ReportType.BEAN_NAME_PICKUP_SETTING_INFO),

    @ApiEnumProperty(" 53：o2o减折赠效果报表")
    O2O_REDUCE_DISCOUNT_GIFT_EFFECT(ReportType.BEAN_NAME_O2O_REDUCE_DISCOUNT_GIFT_EFFECT),

    @ApiEnumProperty(" 54：o2o优惠券报表")
    O2O_COUPON_EFFECT_EXPORT(ReportType.BEAN_NAME_O2O_COUPON_EFFECT_EXPORT),

    @ApiEnumProperty(" 55：o2o优惠券活动报表")
    O2O_COUPON_ACTIVITY_EFFECT_EXPORT(ReportType.BEAN_NAME_O2O_COUPON_ACTIVITY_EFFECT_EXPORT),

    @ApiEnumProperty(" 56：o2o门店报表")
    O2O_COUPON_STORE_EFFECT_EXPORT(ReportType.BEAN_NAME_O2O_COUPON_STORE_EFFECT_EXPORT),

    @ApiEnumProperty(" 57：o2o优惠券活动详情报表")
    O2O_COUPON_INFO_EFFECT_ACTIVITY_EXPORT(ReportType.BEAN_NAME_O2O_COUPON_INFO_EFFECT_ACTIVITY_EXPORT),

    @ApiEnumProperty(" 58：电子卡密")
    ELECTRONIC_CARDS(ReportType.BEAN_NAME_ELECTRONIC_CARDS),

    @ApiEnumProperty(" 59：o2o优惠券明细报表")
    O2O_COUPON_DETAIL_EFFECT_EXPORT(ReportType.BEAN_NAME_O2O_COUPON_DETAIL_EFFECT_EXPORT),

    @ApiEnumProperty(" 60：满系门店明细")
    O2O_FULL_STORE_DETAIL(ReportType.BEAN_NAME_O2O_FULL_STORE_DETAIL),

    @ApiEnumProperty(" 61：视频号天报表")
    WECHAT_VIDEO_SALE(ReportType.BEAN_NAME_WECHAT_VIDEO_SALE_EXPORT),

    @ApiEnumProperty(" 62：视频号报表")
    WECHAT_VIDEO_ACCOUNT(ReportType.BEAN_NAME_WECHAT_VIDEO_ACCOUNT_EXPORT),

    @ApiEnumProperty(" 63：视频号店铺报表")
    WECHAT_VIDEO_COMPANY(ReportType.BEAN_NAME_WECHAT_VIDEO_COMPANY_EXPORT),

    @ApiEnumProperty(" 64：付费会员记录报表")
    PAYING_MEMBER_RECORD(ReportType.PAY_MEMBER_RECORD_EXPORT),

    @ApiEnumProperty(" 65: 付费会员新增报表")
    PAYING_MEMBER_GROWTH(ReportType.PAYING_MEMBER_GROWTH_EXPORT),

    @ApiEnumProperty(" 66: 砍价活动分析报表")
    GOODS_GARGAIN_EFFECT(ReportType.BEAN_NAME_GOODS_GARGAIN_EFFECT),

    @ApiEnumProperty("67：抽奖记录信息")
    DRAW_RECORD_INFO(ReportType.BEAN_NAME_DRAW_RECORD_INFO),

    @ApiEnumProperty(" 68: 分账绑定关系报表")
    LEDGER_RECEIVER_REL(ReportType.LEDGER_RECEIVER_REL_EXPORT),

    @ApiEnumProperty("69：拉卡拉财务结算明细")
    LAKALA_BUSINESS_SETTLEMENT_DETAIL(ReportType.LAKALA_BEAN_NAME_BUSINESS_SETTLEMENT_DETAIL),

    @ApiEnumProperty("70：拉卡拉商家和供应商绑定关系列表")
    LAKALA_SUPPLIER_PROVIDER_BIND_STATE_LIST(ReportType.BEAN_NAME_LAKALA_SUPPLIER_PROVIDER_BIND_STATE_LIST),

    @ApiEnumProperty("71：拉卡拉商家和分销员绑定关系列表")
    LAKALA_SUPPLIER_DISTRIBUTION_BIND_STATE_LIST(ReportType.BEAN_NAME_LAKALA_SUPPLIER_DISTRIBUTION_BIND_STATE_LIST),

    @ApiEnumProperty("72：京东vop报表")
    BUSINESS_VOP_GOODS(ReportType.BEAN_NAME_BUSINESS_VOP_GOODS),

    @ApiEnumProperty("73：LinkedMall报表")
    BUSINESS_LM_GOODS(ReportType.BEAN_NAME_BUSINESS_LM_GOODS),

    @ApiEnumProperty("74：供货商商品库报表")
    BUSINESS_PROVIDER_GOODS(ReportType.BEAN_NAME_BUSINESS_PROVIDER_GOODS),

    @ApiEnumProperty("75：打包一口价效果报表")
    BUYOUT_PRICE(ReportType.BEAN_NAME_BUYOUT_PRICE_EFFECT),

    @ApiEnumProperty("76：第二件半价效果报表")
    HALF_PRICE_SECOND_PIECE(ReportType.BEAN_NAME_HALF_PRICE_SECOND_PIECE_EFFECT),

    @ApiEnumProperty("77：组合购效果报表")
    SUITS(ReportType.BEAN_NAME_SUITS_EFFECT),

    @ApiEnumProperty("78：订单收款报表")
    BUSINESS_PAY_ORDER(ReportType.BEAN_NAME_PAY_ORDER_EXPORT),

    @ApiEnumProperty("79：退单退款报表")
    BUSINESS_REFUND_ORDER(ReportType.BEAN_NAME_REFUND_ORDER_EXPORT),

    @ApiEnumProperty("80：加价购效果报表")
    PREFERENTIAL_EFFECT(ReportType.BEAN_NAME_PREFERENTIAL_EFFECT),

    // 此处空缺，为"加价购迭代"预留
    @ApiEnumProperty("81：礼品卡详情记录报表")
    GIFT_CARD_DETAIL(ReportType.BEAN_NAME_GIFT_CARD_DETAIL_EXPORT),

    @ApiEnumProperty("82：礼品卡使用记录报表")
    GIFT_CARD_BILL(ReportType.BEAN_NAME_GIFT_CARD_BILL_EXPORT),

    @ApiEnumProperty("83：礼品卡详情记录报表")
    USER_GIFT_CARD_DETAIL(ReportType.BEAN_NAME_USER_GIFT_CARD_DETAIL_EXPORT),

    @ApiEnumProperty("84：优惠券详情记录报表")
    COUPON_DETAIL(ReportType.BEAN_NAME_COUPON_DETAIL),

    @ApiEnumProperty("85：社区团长佣金")
    COMMUNITY_LEADER_COMMISSION(ReportType.BEAN_NAME_COMMUNITY_LEADER_COMMISSION),

    @ApiEnumProperty("86：社区团长佣金")
    COMMUNITY_LEADER_COMMISSION_DETAIL(ReportType.BEAN_NAME_COMMUNITY_LEADER_COMMISSION_DETAIL),

    @ApiEnumProperty("87：社区团订单列表")
    COMMUNITY_TRADE_LIST(ReportType.BEAN_NAME_COMMUNITY_TRADE_LIST),

    @ApiEnumProperty("88：社区团备货单列表")
    COMMUNITY_STOCK_WORD(ReportType.BEAN_NAME_COMMUNITY_STOCK_WORD),

    @ApiEnumProperty("89：社区团发货单列表")
    COMMUNITY_DELIVERY_WORD(ReportType.BEAN_NAME_COMMUNITY_DELIVERY_WORD);



    public static final String BEAN_NAME_FLOW = "flowExportService";

    public static final String BEAN_NAME_TRADE = "tradeExportService";

    public static final String BEAN_NAME_GOODS_TRADE = "goodsReportExportService";

    public static final String BEAN_NAME_GOODS_CATE_TRADE = "goodsCateTradeExportService";

    public static final String BEAN_NAME_GOODS_BRAND_TRADE = "goodsBrandTradeExportService";

    public static final String BEAN_NAME_CUSTOMER_GROW = "customerReportExportService";

    public static final String BEAN_NAME_CUSTOMER_TRADE = "customerTradeExportService";

    public static final String BEAN_NAME_CUSTOMER_LEVEL_TRADE = "customerLevelTradeExportService";

    public static final String BEAN_NAME_CUSTOMER_AREA_TRADE = "customerAreaTradeExportService";

    public static final String BEAN_NAME_SALESMAN_TRADE = "employeeExportService";

    public static final String BEAN_NAME_SALESMAN_CUSTOMER = "employeeCustomerExportService";

    public static final String BEAN_NAME_STORE_CATE_TRADE = "goodsStoreCateTradeExportService";

    public static final String BEAN_NAME_STORE_FLOW = "storeFlowExportService";

    public static final String BEAN_NAME_O2O_STORE_FLOW = "o2oStoreFlowExportService";

    public static final String BEAN_NAME_O2O_CATE_TRADE = "o2oGoodsStoreCateTradeExportService";

    public static final String BEAN_NAME_STORE_TRADE = "storeTradeExportService";

    public static final String BEAN_NAME_GROUPON_EFFECT = "grouponService";

    public static final String BEAN_NAME_FLASH_EFFECT = "flashSaleService";

    public static final String BEAN_NAME_APPOINTMENT_EFFECT = "appointmentSaleService";

    public static final String BEAN_NAME_COUPON_EFFECT = "couponEffectExportService";

    public static final String BEAN_NAME_FULL_MONEY_BOOK_EFFECT = "bookingSaleService";

    public static final String BEAN_NAME_DEPOSIT_BOOK_EFFECT = "depositBookingSaleService";


    public static final String BEAN_NAME_COUPON_INFO_EFFECT = "couponEffectExportService";

    public static final String BEAN_NAME_COUPON_ACTIVITY_EFFECT = "couponActivityEffectExportService";


    public static final String BEAN_NAME_COUPON_STORE_EFFECT = "couponStoreEffectExportService";

    public static final String BEAN_NAME_COUPON_INFO_EFFECT_ACTIVITY = "couponInfoEffectActivityExportService";


    public static final String BEAN_NAME_REDUCE_DISCOUNT_GIFT_EFFECT = "marketingReduceDiscountGiftService";

    public static final String BEAN_NAME_O2O_REDUCE_DISCOUNT_GIFT_EFFECT = "o2oMarketingReduceDiscountGiftService";

    public static final String BEAN_NAME_O2O_FULL_STORE_DETAIL = "o2oMarketingReduceDiscountGiftService";

    public static final String BEAN_NAME_MARKETING_OVERVIEW = "marketingSituationService";

    public static final String BEAN_NAME_BUSINESS_GOODS = "goodsExportService";

    public static final String BEAN_NAME_BUSINESS_TRADE = "tradeExportService";

    public static final String BEAN_NAME_BUSINESS_CROSS_TRADE = "crossTradeExportService";

    public static final String BEAN_NAME_BUSINESS_CROSS_RETURNTRADE = "crossReturnTradeExportService";

    public static final String BEAN_NAME_BUSINESS_POINTS_TRADE = "pointsTradeExportService";

    public static final String BEAN_NAME_BUSINESS_RETURN_ORDER = "returnOrderExportService";

    public static final String BEAN_NAME_BUSINESS_CUSTOMER_POINTS = "customerPointsExportService";

    public static final String BEAN_NAME_BUSINESS_FINANCE_BILL = "financeBillExportService";

    public static final String BEAN_NAME_BUSINESS_FINANCE_BILL_DETAIL = "financeBillDetailExportService";

    public static final String BEAN_NAME_BUSINESS_SETTLEMENT = "financeSettlementExportService";

    public static final String BEAN_NAME_BUSINESS_SETTLEMENT_DETAIL = "settlementDetailExportService";

    public static final String LAKALA_BEAN_NAME_BUSINESS_SETTLEMENT_DETAIL = "lakalaSettlementDetailExportService";

    public static final String BEAN_NAME_LAKALA_SUPPLIER_PROVIDER_BIND_STATE_LIST =
            "lakalaProviderBindStateExportService";

    public static final String BEAN_NAME_LAKALA_SUPPLIER_DISTRIBUTION_BIND_STATE_LIST =
            "lakalaDistributionBindStateExportService";

    public static final String BEAN_NAME_BUSINESS_CUSTOMER_FUNDS = "customerFundsExportService";

    public static final String BEAN_NAME_BUSINESS_CUSTOMER_FUNDS_DETAIL = "customerFundsDetailExportService";

    public static final String BEAN_NAME_BUSINESS_DISTRIBUTION_COMMISSION = "distributionCommissionExportService";

    public static final String BEAN_NAME_BUSINESS_DRAW_CASH = "drawCashExportService";

    public static final String BEAN_NAME_BUSINESS_DISTRIBUTION_CUSTOMER = "distributionCustomerExportService";

    public static final String BEAN_NAME_BUSINESS_DISTRIBUTION_RECORD = "distributionRecordExportService";

    public static final String BEAN_NAME_BUSINESS_OPERATION_LOG = "operationLogExportService";

    public static final String BEAN_NAME_BUSINESS_ORDER_TICKET = "orderTicketExportService";

    public static final String BEAN_NAME_BUSINESS_INVITE_RECORD = "inviteRecordExportService";

    public static final String BEAN_NAME_BUSINESS_CROSS_GOODS_INFO = "crossGoodsInfoExportService";

    public static final String BEAN_NAME_MARKETING_GIFTCARD_VOUCHERS = "inviteRecordExportService";

    public static final String BEAN_NAME_PICKUP_SETTING_INFO = "pickupSettingExportService";

    public static final String BEAN_NAME_ELECTRONIC_CARDS = "electronicCardsExportService";

    /***
     * O2O商品导出Service
     */
    public static final String BEAN_NAME_O2O_BUSINESS_GOODS = "o2oGoodsExportService";

    public static final String BEAN_NAME_O2O_COUPON_EFFECT_EXPORT = "o2oCouponEffectExportService";

    public static final String BEAN_NAME_O2O_COUPON_ACTIVITY_EFFECT_EXPORT = "o2oCouponActivityEffectExportService";

    public static final String BEAN_NAME_O2O_COUPON_STORE_EFFECT_EXPORT = "o2oCouponStoreEffectExportService";

    public static final String BEAN_NAME_O2O_COUPON_INFO_EFFECT_ACTIVITY_EXPORT = "o2oCouponInfoEffectActivityExportService";

    public static final String BEAN_NAME_O2O_COUPON_DETAIL_EFFECT_EXPORT = "o2oCouponDetailEffectExportService";


    /**
     * 视频号销售统计报表导出Service
     */
    public static final String BEAN_NAME_WECHAT_VIDEO_SALE_EXPORT = "wechatVideoSaleExportService";

    public static final String BEAN_NAME_WECHAT_VIDEO_ACCOUNT_EXPORT = "wechatVideoAccountExportService";

    public static final String BEAN_NAME_WECHAT_VIDEO_COMPANY_EXPORT = "wechatVideoCompanyExportService";

    public static final String PAY_MEMBER_RECORD_EXPORT = "payingMemberRecordExportService";

    public static final String PAYING_MEMBER_GROWTH_EXPORT = "payingMemberGrowthExportService";

    public static final String BEAN_NAME_DRAW_RECORD_INFO = "drawRecordExportService";

    public static final String BEAN_NAME_GOODS_GARGAIN_EFFECT = "goodsBargainService";

    public static final String LEDGER_RECEIVER_REL_EXPORT = "ledgerReceiverRelExportService";

    public static final String BEAN_NAME_BUSINESS_VOP_GOODS = "vopGoodsExportService";

    public static final String BEAN_NAME_BUSINESS_LM_GOODS = "lmGoodsExportService";

    public static final String BEAN_NAME_BUSINESS_PROVIDER_GOODS = "providerGoodsExportService";

    public static final String BEAN_NAME_BUYOUT_PRICE_EFFECT =
            "buyOutPriceService";

    public static final String BEAN_NAME_HALF_PRICE_SECOND_PIECE_EFFECT =
            "halfPriceSecondPieceService";

    public static final String BEAN_NAME_SUITS_EFFECT = "suitsService";

    public static final String BEAN_NAME_PAY_ORDER_EXPORT = "payOrderExportService";

    public static final String BEAN_NAME_REFUND_ORDER_EXPORT = "refundOrderExportService";

    public static final String BEAN_NAME_PREFERENTIAL_EFFECT = "preferentialService";

    public static final String BEAN_NAME_GIFT_CARD_DETAIL_EXPORT = "giftCardDetailExportService";

    public static final String BEAN_NAME_GIFT_CARD_BILL_EXPORT = "giftCardBillExportService";

    public static final String BEAN_NAME_USER_GIFT_CARD_DETAIL_EXPORT = "userGiftCardDetailExportService";

    public static final String BEAN_NAME_COUPON_DETAIL = "couponDetailExportService";

    public static final String BEAN_NAME_COMMUNITY_LEADER_COMMISSION = "communityLeaderCommissionExportService";

    public static final String BEAN_NAME_COMMUNITY_LEADER_COMMISSION_DETAIL = "communityCommissionDetailExportService";

    public static final String BEAN_NAME_COMMUNITY_TRADE_LIST = "communityTradeExportService";

    public static final String BEAN_NAME_COMMUNITY_STOCK_WORD = "communityStockWordExportService";

    public static final String BEAN_NAME_COMMUNITY_DELIVERY_WORD = "communityDeliveryWordExportService";

    private String beanName;

    ReportType(String beanName) {
        this.beanName = beanName;
    }

    @JsonCreator
    public static ReportType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     *
     * @return null if the value is not found.
     */
    public static String findByValue(int value) {
        switch (value) {
            case 0:
                return "FLOW";
            case 1:
                return "TRADE";
            case 2:
                return "GOODS_TRADE";
            case 3:
                return "GOODS_CATE_TRADE";
            case 4:
                return "GOODS_BRAND_TRADE";
            case 5:
                return "CUSTOMER_GROW";
            case 6:
                return "CUSTOMER_TRADE";
            case 7:
                return "CUSTOMER_LEVEL_TRADE";
            case 8:
                return "CUSTOMER_AREA_TRADE";
            case 9:
                return "SALESMAN_TRADE";
            case 10:
                return "SALESMAN_CUSTOMER";
            case 11:
                return "STORE_CATE_TRADE";
            case 12:
                return "STORE_FLOW";
            case 13:
                return "STORE_TRADE";
            case 14:
                return "GROUPON_EFFECT";
            case 15:
                return "FLASH_EFFECT";
            case 16:
                return "APPOINTMENT_EFFECT";
            case 17:
                return "COUPON_EFFECT";
            case 18:
                return "FULL_MONEY_BOOK_EFFECT";
            case 19:
                return "DEPOSIT_BOOK_EFFECT";
            case 20:
                return "COUPON_INFO_EFFECT";
            case 21:
                return "COUPON_ACTIVITY_EFFECT";
            case 22:
                return "COUPON_STORE_EFFECT";
            case 23:
                return "COUPON_INFO_EFFECT_ACTIVITY";
            case 24:
                return "REDUCE_DISCOUNT_GIFT_EFFECT";
            case 25:
                return "MARKETING_OVERVIEW";
            case 26:
                return "BUSINESS_GOODS";
            case 27:
                return "BUSINESS_TRADE";
            case 28:
                return "BUSINESS_POINTS_TRADE";
            case 29:
                return "BUSINESS_RETURN_ORDER";
            case 30:
                return "BUSINESS_CUSTOMER_POINTS";
            case 31:
                return "BUSINESS_FINANCE_BILL";
            case 32:
                return "BUSINESS_FINANCE_BILL_DETAIL";
            case 33:
                return "BUSINESS_SETTLEMENT";
            case 34:
                return "BUSINESS_SETTLEMENT_DETAIL";
            case 35:
                return "BUSINESS_SETTLEMENT_DETAIL";
            case 36:
                return "BUSINESS_CUSTOMER_FUNDS_DETAIL";
            case 37:
                return "BUSINESS_DISTRIBUTION_COMMISSION";
            case 38:
                return "BUSINESS_DRAW_CASH";
            case 39:
                return "BUSINESS_DISTRIBUTION_CUSTOMER";
            case 40:
                return "BUSINESS_DISTRIBUTION_RECORD";
            case 41:
                return "BUSINESS_OPERATION_LOG";
            case 42:
                return "BUSINESS_ORDER_TICKET";
            case 43:
                return "BUSINESS_INVITE_RECORD";
            case 44:
                return "BUSINESS_CROSS_TRADE";
            case 45:
                return "BUSINESS_CROSS_RETURNTRADE";
            case 46:
                return "MARKETING_OVERVIEW";
            case 47:
                return "BUSINESS_CROSS_GOODS_INFO";
            case 48:
                return "O2O_BUSINESS_GOODS";
            case 49:
                return "O2O_FLOW";
            case 50:
                return "O2O_TRADE";
            case 51:
                return "O2O_CATE_TRADE";
            case 52:
                return "PICKUP_SETTING_INFO";
            case 53:
                return "O2O_REDUCE_DISCOUNT_GIFT_EFFECT";
            case 54:
                return "O2O_COUPON_EFFECT_EXPORT";
            case 55:
                return "O2O_COUPON_ACTIVITY_EFFECT_EXPORT";
            case 56:
                return "O2O_COUPON_STORE_EFFECT_EXPORT";
            case 57:
                return "O2O_COUPON_INFO_EFFECT_ACTIVITY_EXPORT";
            case 58:
                return "ELECTRONIC_CARDS";
            case 59:
                return "O2O_COUPON_DETAIL_EFFECT_EXPORT";
            case 60:
                return "O2O_FULL_STORE_DETAIL";
            case 61:
                return "WECHAT_VIDEO_SALE_EXPORT";
            case 62:
                return "WECHAT_VIDEO_ACCOUNT_EXPORT";
            case 63:
                return "WECHAT_VIDEO_COMPANY_EXPORT";
            case 64:
                return "PAY_MEMBER_RECORD_EXPORT";
            case 65:
                return "PAYING_MEMBER_GROWTH_EXPORT";
            case 66:
                return "GOODS_GARGAIN_EFFECT";
            case 67:
                return "BEAN_NAME_DRAW_RECORD_INFO";
            case 68:
                return "LEDGER_RECEIVER_REL_EXPORT";
            case 69:
                return "LAKALA_BEAN_NAME_BUSINESS_SETTLEMENT_DETAIL";
            case 70:
                return "LAKALA_SUPPLIER_PROVIDER_BIND_STATE_LIST";
            case 71:
                return "LAKALA_SUPPLIER_DISTRIBUTION_BIND_STATE_LIST";
            case 72:
                return "BUSINESS_VOP_GOODS";
            case 73:
                return "BUSINESS_LM_GOODS";
            case 74:
                return "BUSINESS_PROVIDER_GOODS";
            case 75:
                return "BUYOUT_PRICE";
            case 76:
                return "HALF_PRICE_SECOND_PIECE";
            case 77:
                return "SUITS";
            case 78:
                return "BUSINESS_PAY_ORDER";
            case 79:
                return "BUSINESS_REFUND_ORDER";
            case 80:
                return "PREFERENTIAL_EFFECT";
            // 此处空缺，为"加价购迭代"预留
            case 81:
                return "GIFT_CARD_DETAIL";
            case 82:
                return "GIFT_CARD_BILL";
            case 83:
                return "USER_GIFT_CARD_DETAIL";
            case 84:
                return "COUPON_DETAIL";
            case 85:
                return "COMMUNITY_LEADER_COMMISSION";
            case 86:
                return "COMMUNITY_LEADER_COMMISSION_DETAIL";
            case 87:
                return "COMMUNITY_TRADE_LIST";
            case 88:
                return "COMMUNITY_STOCK_WORD";
            case 89:
                return "COMMUNITY_DELIVERY_WORD";
            default:
                return null;
        }
    }

}
