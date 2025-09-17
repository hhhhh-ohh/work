package com.wanmi.sbc.account.api.constant;

/**
 * 会员账户常量
 * @author hehu
 * @date 2020-03-28 18:18:50
 */
public class AccountConstants {


    /**
     * 会员授信额度key前缀
     */
    public static final String CREDIT_ACCOUNT_PREFIX = "CREDIT:ACCOUNT:";

    /**
     * 所有会员授信额度累计key前缀
     */
    public static final String CREDIT_TOTAL_PREFIX = "CREDIT:TOTAL:";

    /**
     * 会员授信额度待还额度key
     */
    public static final String CREDIT_ACCOUNT_LEFT_REPAY = "0";

    /**
     * 会员授信额度已还额度key
     */
    public static final String CREDIT_ACCOUNT_REPAID = "1";

    /**
     * 会员授信额度已使用key
     */
    public static final String CREDIT_ACCOUNT_USED = "2";

    /**
     * 会员授信额度
     */
    public static final String CREDIT_ACCOUNT_AMOUNT = "3";

    /**
     * 授信客户数
     */
    public static final String CREDIT_ACCOUNT_COUNT = "4";

    /**
     * 用户还款支付key
     */
    public static final String CUSTOMER_CREDIT_REPAY = "customer-credit-repay-";

    /**
     * 积分数量
     */
    public static final String POINTS_USED = "POINTS_USED";

    /**
     * 礼品卡金额
     */
    public static final String GIFT_CARD_PRICE = "GIFT_CARD_PRICE";
}